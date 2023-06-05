package cn.itedus.lottery.test.application;

import cn.itedus.lottery.application.process.draw.IActivityDrawProcess;
import cn.itedus.lottery.application.process.draw.req.DrawProcessReq;
import cn.itedus.lottery.application.process.draw.res.DrawProcessResult;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description: 测试活动抽奖流程
 * @author: ZeroYiAn
 * @time: 2023/5/2 9:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityProcessTest {

    private Logger logger = LoggerFactory.getLogger(ActivityProcessTest.class);

    @Resource
    private IActivityDrawProcess activityProcess;

    @Test
    public void test_doDrawProcess() throws InterruptedException {
        DrawProcessReq req = new DrawProcessReq();
        req.setuId("胡仁杰");
        req.setActivityId(100001L);
        DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(req);

        logger.info("请求入参：{}", JSON.toJSONString(req));
        logger.info("测试结果：{}", JSON.toJSONString(drawProcessResult));

        Thread.sleep(10000);

    }

}

