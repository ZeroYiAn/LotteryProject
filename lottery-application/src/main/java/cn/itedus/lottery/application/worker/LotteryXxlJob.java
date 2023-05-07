package cn.itedus.lottery.application.worker;

import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.common.Result;
import cn.itedus.lottery.domain.activity.model.vo.ActivityVO;
import cn.itedus.lottery.domain.activity.service.deploy.IActivityDeploy;
import cn.itedus.lottery.domain.activity.service.stateflow.IStateHandler;
import com.alibaba.fastjson.JSON;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
    private IStateHandler stateHandler;

    @XxlJob("lotteryActivityStateJobHandler")
    public void lotteryActivityStateJobHandler(){
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
}
