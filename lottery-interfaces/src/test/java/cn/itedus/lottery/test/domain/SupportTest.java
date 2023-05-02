package cn.itedus.lottery.test.domain;

import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.domain.support.ids.IIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @description:
 * @author: ZeroYiAn
 * @time: 2023/4/27 20:49
 */

/**
 * 如果在Spring项目中的Test测试类要使用注入的类，比如@Autowired注入的类或者spring管理的bean的时候，
 * 测试类在运行前，需要spring容器运行起来，加上这个@RunWith(SpringRunner.class)注解，
 * 就是先运行起来spring容器，再开始运行测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SupportTest {
    private Logger logger = LoggerFactory.getLogger(SupportTest.class);

    @Resource
    private Map<Constants.Ids, IIdGenerator>idGeneratorMap;

    @Test
    public void test_ids(){
        logger.info("雪花算法策略，生成ID：{}", idGeneratorMap.get(Constants.Ids.SnowFlake).nextId());
        logger.info("日期算法策略，生成ID：{}", idGeneratorMap.get(Constants.Ids.ShortCode).nextId());
        logger.info("随机算法策略，生成ID：{}", idGeneratorMap.get(Constants.Ids.RandomNumeric).nextId());
    }
}
