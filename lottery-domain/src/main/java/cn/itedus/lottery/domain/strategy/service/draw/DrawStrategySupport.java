package cn.itedus.lottery.domain.strategy.service.draw;

import cn.itedus.lottery.domain.strategy.model.aggregates.StrategyRich;
import cn.itedus.lottery.domain.strategy.model.vo.AwardBriefVO;
import cn.itedus.lottery.domain.strategy.repository.IStrategyRepository;

import javax.annotation.Resource;

/**
 * @description:抽奖策略数据支撑，一些通用的数据服务
 * @author: ZeroYiAn
 * @time: 2023/4/26 20:09
 */

public class DrawStrategySupport extends DrawConfig{

    @Resource
    protected IStrategyRepository strategyRepository;

    /**
     * 查询策略配置信息
     *
     * @param strategyId 策略ID
     * @return 策略配置信息
     */
    protected StrategyRich queryStrategyRich(Long strategyId){
        return strategyRepository.queryStrategyRich(strategyId);
    }

    /**
     * 查询奖品详情信息
     *
     * @param awardId 奖品ID
     * @return 中奖详情
     */
    protected AwardBriefVO queryAwardInfoByAwardId(String awardId){
        return strategyRepository.queryAwardInfo(awardId);
    }

}

