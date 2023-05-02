package cn.itedus.lottery.domain.support.ids;

import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.domain.support.ids.policy.RandomNumeric;
import cn.itedus.lottery.domain.support.ids.policy.ShortCode;
import cn.itedus.lottery.domain.support.ids.policy.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: Id 策略模式 上下文配置「在正式的完整的系统架构中，
 * ID 的生成会有单独的服务来完成，其他服务来调用 ID 生成接口即可」
 * @author: ZeroYiAn
 * @time: 2023/4/27 20:16
 */

/**
 * - @Configuration注解可以标注到类上，当标注到类上时，启动Spring就会自动扫描@Configuration注解标注的类，将其注册到IOC容器中，并被实例化成Bean对象。
 * -如果被@Configuration注解标注的类中存在使用@Bean注解标注的创建某个类对象的方法，那么，Spring也会自动执行使用@Bean注解标注的方法，将对应的Bean定义信息注册到IOC容器，并进行实例化。
 *
 * 当某个类被@Configuration注解标注时，说明这个类是配置类，
 * 可以在这个类中使用@Bean注解向IOC容器中注入Bean对象，也可以使用@Autowired、@Inject和@Resource等注解来注入所需的Bean对象
 *
 * 当@Configuration注解中的proxyBeanMethods属性为true时，（默认为true）
 * 每次调用使用@Configuration注解标注的类中被@Bean注解标注的方法时，都会返回同一个Bean实例对象。
 */
@Configuration
public class IdContext {
    /**
     * 创建 ID 生成策略对象，属于策略设计模式的使用方式
     * @param snowFlake 雪花算法，长码，大量
     * @param shortCode 日期算法，短码，少量，全局唯一需要自己保证
     * @param randomNumeric 随机算法，短码，大量，全局唯一需要自己保证
     * @return Map,其 Value为 IIdGenerator 实现类
     */
    @Bean
    public Map<Constants.Ids, IIdGenerator> idGenerator(SnowFlake snowFlake, ShortCode shortCode, RandomNumeric randomNumeric){
        Map<Constants.Ids, IIdGenerator> idGeneratorMap = new HashMap<>(8);
        idGeneratorMap.put(Constants.Ids.SnowFlake, snowFlake);
        idGeneratorMap.put(Constants.Ids.ShortCode, shortCode);
        idGeneratorMap.put(Constants.Ids.RandomNumeric, randomNumeric);
        return idGeneratorMap;
    }

}
