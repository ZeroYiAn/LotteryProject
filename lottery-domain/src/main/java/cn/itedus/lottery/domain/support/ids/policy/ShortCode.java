package cn.itedus.lottery.domain.support.ids.policy;

import cn.itedus.lottery.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Random;

/**
 * @description: 短码 生成策略：日期拼接，9位，仅支持很小的调用量，用于生成活动配置类编号，保证全局唯一
 * @author: ZeroYiAn
 * @time: 2023/4/27 20:16
 */
@Component
public class ShortCode implements IIdGenerator {
    @Override
    public synchronized long nextId() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        //打乱排序：2020年为准(1位) + 小时(2位) + 周期(2位) + 日(1位)+ 三位随机数
        StringBuilder idStr = new StringBuilder();
        idStr.append(year- 2020);
        idStr.append(hour);
        idStr.append(String.format("%02d",week));
        idStr.append(day);
        //1000以内的随机数
        idStr.append(String.format("%03d",new Random().nextInt(1000)));
        return Long.parseLong(idStr.toString());
    }
}
