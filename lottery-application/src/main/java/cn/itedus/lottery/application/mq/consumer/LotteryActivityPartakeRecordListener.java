package cn.itedus.lottery.application.mq.consumer;

import cn.itedus.lottery.domain.activity.model.vo.ActivityPartakeRecordVO;
import cn.itedus.lottery.domain.activity.service.partake.IActivityPartake;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description:  抽奖活动领取记录监听消息
 * @author: ZeroYiAn
 * @time: 2023/5/10 9:38
 */

/**
 * 别忘记注册成组件
 */
@Component
public class LotteryActivityPartakeRecordListener {
    private  Logger LOGGER = LoggerFactory.getLogger(LotteryActivityPartakeRecordListener.class);
    @Resource
    private IActivityPartake activityPartake;
    @KafkaListener(topics = "lottery_activity_partake", groupId = "lottery")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        // 1. 判断消息是否存在
        if (!message.isPresent()) {
            return;
        }
        //2. 处理MQ消息
        try{
            // 1. 转化对象（或者你也可以重写Serializer<T>）
            ActivityPartakeRecordVO activityPartakeRecordVO = JSON.parseObject((String) message.get(), ActivityPartakeRecordVO.class);

            // 2. 更新数据库库存【实际场景业务体量较大，可能也会由于MQ消费引起并发，对数据库产生压力，所以如果并发量较大，可以把库存记录缓存中，并使用定时任务进行处理缓存和数据库库存同步，减少对数据库的操作次数】

            activityPartake.updateActivityStock(activityPartakeRecordVO);
            // 3.打印日志
            LOGGER.info("消费MQ消息，活动已领取，异步扣减活动库存 message：{}", message.get());
            //4.消息消费完成
            ack.acknowledge();
        }catch (Exception e){
            // 发奖环节失败，消息重试。
            LOGGER.error("消费MQ消息，活动领取失败 message：{}",message.get());
            throw e;
        }


    }
}
