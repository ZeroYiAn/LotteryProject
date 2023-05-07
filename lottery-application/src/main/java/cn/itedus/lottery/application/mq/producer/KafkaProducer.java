package cn.itedus.lottery.application.mq.producer;

import cn.itedus.lottery.domain.activity.model.vo.InvoiceVO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * @description: 消息生产者
 * @author: ZeroYiAn
 * @time: 2023/5/4 21:01
 */
@Component
public class KafkaProducer {
    private Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    @Resource
    private KafkaTemplate<String,Object>kafkaTemplate;
    /**
     * MQ主题：中奖发货单 lottery_invoice   invoice:发票
     * 启动zookeeper服务： .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
     * 启动kafka服务：.\bin\windows\kafka-server-start.bat .\config\server.properties
     * 创建topic： .\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic lottery_invoice
     */
    public static final String TOPIC_INVOICE ="lottery_invoice";


    public ListenableFuture<SendResult<String, Object>> sendLotteryInvoice(InvoiceVO invoice){
        String objJson = JSON.toJSONString(invoice);
        logger.info("发送MQ消息 topic:{} bizId:{} message:{}",TOPIC_INVOICE,invoice.getuId(),objJson);

        //发送消息
        return kafkaTemplate.send(TOPIC_INVOICE, objJson);

    }
}
