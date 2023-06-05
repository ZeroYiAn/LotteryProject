package cn.itedus.lottery.application.mq.consumer;

import cn.hutool.core.lang.Assert;
import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.domain.activity.model.vo.InvoiceVO;
import cn.itedus.lottery.domain.award.model.req.GoodsReq;
import cn.itedus.lottery.domain.award.model.res.DistributionRes;
import cn.itedus.lottery.domain.award.service.factory.DistributionGoodsFactory;
import cn.itedus.lottery.domain.award.service.goods.IDistributionGoods;
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
import java.util.List;
import java.util.Optional;

/**
 * @description: 中奖发货单监听消息
 * @author: ZeroYiAn
 * @time: 2023/5/5 17:15
 */
@Component
public class LotteryInvoiceListener {

    private static final Logger logger = LoggerFactory.getLogger(LotteryInvoiceListener.class);

    @Resource
    private DistributionGoodsFactory distributionGoodsFactory;

    /**
     * groupId = "lottery"  消费者组 "lottery" 内的一个消费者，负责消费某些分区（Partitions）,即监听的这个topic对应的分区 的数据
     *
     * 一个topic支持多个消费者(一个消费者可消费多个分区，一个分区可被多个消费组消费，但同一消费组内仅能有一个消费者同时消费1个分区)
     *
     * 一个消费者组里可以有多个消费者，每个消费者负责消费各自分区的数据，所有消费者共同合力消费分区内的数据
     * 不同消费者组之间的消息消费是独立的
     *
     *
     * @param record  消息记录
     * @param ack    响应
     * @param topic 主题
     */
    @KafkaListener(topics = "lottery_invoice",groupId = "lottery")
    public void onMessage(ConsumerRecord<?,?>record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC)String topic){
        Optional<?> message = Optional.ofNullable(record.value());
        //1.判断消息是否存在
        if(!message.isPresent()){
            return;
        }
        //2.处理MQ消息
        try{

            // 1. 转化对象（或者你也可以重写Serializer<T>）
            InvoiceVO invoiceVO = JSON.parseObject((String) message.get(), InvoiceVO.class);
            // 2.获取发送奖品工厂，执行发奖
            IDistributionGoods distributionGoodsService = distributionGoodsFactory.getDistributionGoodsService(invoiceVO.getAwardType());
            DistributionRes distributionRes = distributionGoodsService.doDistribution(new GoodsReq(invoiceVO.getuId(), invoiceVO.getOrderId(), invoiceVO.getAwardId(),
                    invoiceVO.getAwardName(), invoiceVO.getAwardContent()));

            //断言方法在入参不满足要求时就会抛出 IllegalArgumentException
            Assert.isTrue(Constants.AwardState.SUCCESS.getCode().equals(distributionRes.getCode()),distributionRes.getInfo());

            //3.通过断言，即发货成功，打印日志
            logger.info("消费MQ消息，完成 topic：{} bizId：{} 发奖结果：{}",topic,invoiceVO.getuId(),JSON.toJSONString(distributionRes));
            //4.消息消费完成   在消费消息时，可以将自动提交偏移量关闭，改为手动提交偏移量。
            // 当消费者成功处理完一个消息时，再手动提交该偏移量。当发生故障或者重启之后，消费者可以从上次提交的偏移量开始重新消费消息，避免重复消费。
            ack.acknowledge();

        }catch (Exception e){
            //发奖环节失败，消息重试。所有的环节：发货、更新库 都要保证幂等性
            logger.error("消费MQ消息，失败 topic:{} message:{}",topic,message.get());
            throw  e;
        }
    }


}
