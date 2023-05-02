package cn.itedus.lottery.domain.support.ids.policy;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.itedus.lottery.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description:hutool 工具包下的雪花算法，这里生成的id是19位
 * 15位雪花算法推荐：https://github.com/yitter/idgenerator/blob/master/Java/source/src/main/java/com/github/yitter/core/SnowWorkerM1.java
 * @author: ZeroYiAn
 * @time: 2023/4/27 20:17
 */
@Component
public class SnowFlake implements IIdGenerator {
    /**
     * 这里的Snowflake是cn.hutool.core.lang.Snowflake类
     */
    private Snowflake snowflake;

    @PostConstruct
    public void init(){
        //0~31位，可以采用配置的方式使用
        long workerId;
        try{
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        }catch (Exception e){
            workerId = NetUtil.getLocalhostStr().hashCode();
        }

        workerId = workerId >> 16 & 31;

        long dataCenterId = 1L;
        snowflake = IdUtil.createSnowflake(workerId,dataCenterId);
    }
    @Override
    public long nextId() {
        return snowflake.nextId();
    }
}
