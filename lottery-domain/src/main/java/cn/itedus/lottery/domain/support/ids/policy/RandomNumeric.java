package cn.itedus.lottery.domain.support.ids.policy;

import cn.itedus.lottery.domain.support.ids.IIdGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * @description: 工具类生成 org.apache.commons.lang3.RandomStringUtils
 * @author: ZeroYiAn
 * @time: 2023/4/27 20:16
 */
@Component
public class RandomNumeric implements IIdGenerator {
    /**
     * 创建一个随机字符串，其长度是指定的字符数，将从数字字符集中选择字符。
     * count:生成随机数的长度 11位
     */
    @Override
    public long nextId() {
        return Long.parseLong(RandomStringUtils.randomNumeric(11));
    }
}
