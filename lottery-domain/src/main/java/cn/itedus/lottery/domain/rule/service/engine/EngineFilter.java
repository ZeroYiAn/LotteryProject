package cn.itedus.lottery.domain.rule.service.engine;

import cn.itedus.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.itedus.lottery.domain.rule.model.res.EngineResult;

/**
 * @description:  规则过滤器引擎
 * @author: ZeroYiAn
 * @time: 2023/5/2 23:12
 */
public interface EngineFilter {
    /**
     * 规则过滤接口
     * @param matter  规则决策物料
     * @return        规则决策结果
     */
    EngineResult process(final DecisionMatterReq matter);
}
