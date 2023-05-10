package cn.itedus.lottery.application.worker;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.itedus.lottery.application.mq.producer.KafkaProducer;
import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.common.Result;
import cn.itedus.lottery.domain.activity.model.vo.ActivityVO;
import cn.itedus.lottery.domain.activity.model.vo.InvoiceVO;
import cn.itedus.lottery.domain.activity.service.deploy.IActivityDeploy;
import cn.itedus.lottery.domain.activity.service.partake.IActivityPartake;
import cn.itedus.lottery.domain.activity.service.stateflow.IStateHandler;
import com.alibaba.fastjson.JSON;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description: 抽奖业务，任务配置
 * @author: ZeroYiAn
 * @time: 2023/5/7 14:01
 */
@Component
public class LotteryXxlJob {
    private static final Logger logger = LoggerFactory.getLogger(LotteryXxlJob.class);

    @Resource
    private IActivityDeploy activityDeploy;

    @Resource
    private IActivityPartake activityPartake;

    @Resource
    private IDBRouterStrategy dbRouter;

    @Resource
    private IStateHandler stateHandler;

    @Resource
    private KafkaProducer kafkaProducer;


    /**
     * 任务配置：需在对应执行器下添加配置任务
     * 运行模式：Bean模式，任务以JobHandler方式维护在执行器端；需要结合 "JobHandler" 属性匹配执行器中任务；
     * Bean模式又包括 类形式 和 方法形式
     *
     * 此处使用方法形式
     * Bean模式任务，支持基于方法的开发方式，每个任务对应一个方法。
     * 优点：
     *      每个任务只需要开发一个方法，并添加”@XxlJob”注解即可，更加方便、快速。
     *      支持自动扫描任务并注入到执行器容器。
     * 缺点：要求Spring容器环境
     */

    /**
     * 任务1 ： 抽奖活动状态扫描
     */
    @XxlJob("lotteryActivityStateJobHandler")
    public void lotteryActivityStateJobHandler() throws Exception{
        logger.info("开始扫描抽奖活动状态");
        //活动状态：4通过、5运行(审核通过后worker扫描状态)、6拒绝、7关闭
        /*
         * sql命令：每次最多扫描得到10条记录
         *  SELECT activity_id, activity_name, begin_date_time, end_date_time, state, creator
         *         FROM activity
         *         WHERE id >= #{id} AND state in (4,5)
         *         ORDER BY ID ASC
         *             LIMIT 10
         */
        List<ActivityVO> activityVOList = activityDeploy.scanToDoActivityList(0L);
        if(activityVOList.isEmpty()){
            logger.info("扫描活动状态结束，暂无符合状态的活动列表");
            return;
        }
        while (!activityVOList.isEmpty()){
            for (ActivityVO activityVO : activityVOList) {
                Integer state = activityVO.getState();
                switch (state){
                    // 活动状态为审核通过，在临近活动开启时间前，审核活动为活动中。
                    // 在使用活动的时候，需要依照活动状态核时间两个字段进行判断和使用。
                    case 4:
                        //将审核通过的活动 转变状态为 活动中，即开启活动
                        Result state4Result = stateHandler.doing(activityVO.getActivityId(), Constants.ActivityState.PASS);
                        logger.info("扫描活动状态为：活动中！ 结果：{} activityId:{} activityName:{} creator:{}", JSON.toJSONString(state4Result),
                                activityVO.getActivityId(),activityVO.getActivityName(),activityVO.getCreator());
                        break;
                    case 5:
                        //判断正在开启的活动是否过了活动时间
                        if(activityVO.getEndDateTime().before(new Date())){
                            //超过活动时间，将活动关闭
                            Result state5Result = stateHandler.close(activityVO.getActivityId(), Constants.ActivityState.DOING);
                            logger.info("扫描活动状态为：活动已关闭！ 结果：{} activityId:{} activityName:{} creator:{}", JSON.toJSONString(state5Result),
                                    activityVO.getActivityId(),activityVO.getActivityName(),activityVO.getCreator());
                        }
                        break;
                    default:
                        break;
                }

            }
            //获取活动列表中最后一条记录，继续向后扫描后面10条记录（如果有的话）
            ActivityVO activityVO = activityVOList.get(activityVOList.size() - 1);
            activityVOList = activityDeploy.scanToDoActivityList(activityVO.getActivityId());
        }

        logger.info("扫描活动状态结束");
    }

    /**
     * 任务2：扫描库表补偿发货单MQ消息
     */
    @XxlJob("lotteryOrderMQStateJobHandler")
    public void lotteryOrderMQStateJobHandler() throws Exception{
        //验证参数：  通过任务调度中心执行任务传入参数
        String jobParam = XxlJobHelper.getJobParam();
        if(null==jobParam){
            logger.info("扫描用户抽奖奖品发放MQ状态[Table = 2*4] 错误 param is null ");
            return;
        }

        // 获取分布式任务配置参数信息 参数配置格式：1,2,3 即扫描1库，2库，3库...也可以是指定扫描一个，也可以配置多个库，
        // 按照部署的任务集群进行数量配置，均摊分别扫描效率更高
        String[] params = jobParam.split(",");
        logger.info("扫描用户抽奖奖品发放MQ状态[Table = 2*4] 开始 params：{}", JSON.toJSONString(params));

        if(params.length==0){
            logger.info("扫描用户抽奖奖品发放MQ状态[Table = 2*4] 结束 params is null");
            return;
        }

        //获取分库分表配置下的分表的数目：4
        int tbCount = dbRouter.tbCount();

        //循环获取指定扫描的库
        for (String param : params){
            //获取当前任务指定扫描的分库
            int dbCount = Integer.parseInt(param);

            //判断当前分库是否存在
            if(dbCount>dbRouter.dbCount()){
                logger.info("扫描用户抽奖奖品发放MQ状态[Table = 2*4] 结束 dbCount is not exist");
                continue;
            }

            //循环扫描当前库下的分表
            for (int i = 0; i < tbCount; i++) {
                //扫描库表数据,得到未成功发送以及超过30分钟未发送的抽奖单
                List<InvoiceVO> invoiceVOList = activityPartake.scanInvoiceMqState(dbCount, i);
                logger.info("扫描用户抽奖奖品发放MQ状态[Table = 2*4] 扫描库：{} 扫描表：{} 需补偿的MQ消息数：{}", dbCount, i, invoiceVOList.size());

                //补偿(重发)MQ消息
                for (InvoiceVO invoiceVO : invoiceVOList) {
                    ListenableFuture<SendResult<String, Object>> future = kafkaProducer.sendLotteryInvoice(invoiceVO);
                    future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            // MQ 消息发送失败，更新数据库表 user_strategy_export.mq_state = 2 【等待定时任务扫码补偿MQ消息】
                            activityPartake.updateInvoiceMqState(invoiceVO.getuId(), invoiceVO.getOrderId(), Constants.MQState.FAIL.getCode());
                        }

                        @Override
                        public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                            // MQ 消息发送完成，更新数据库表 user_strategy_export.mq_state = 1
                            activityPartake.updateInvoiceMqState(invoiceVO.getuId(), invoiceVO.getOrderId(), Constants.MQState.COMPLETE.getCode());
                        }
                    });
                }

            }
        }
        logger.info("扫描用户抽奖奖品发放MQ状态[Table = 2*4] 完成 param：{}", JSON.toJSONString(params));

    }




}
