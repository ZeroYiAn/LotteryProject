package cn.itedus.lottery.application.process;

import cn.itedus.lottery.application.process.req.DrawProcessReq;
import cn.itedus.lottery.application.process.res.DrawProcessResult;
import cn.itedus.lottery.application.process.res.RuleQuantificationCrowdResult;
import cn.itedus.lottery.domain.rule.model.req.DecisionMatterReq;

/**
 * @description: 活动抽奖流程编排接口
 * @author: ZeroYiAn
 * @time: 2023/5/2 9:01
 */
public interface IActivityProcess {

    /**
     * 执行抽奖流程
     * @param req 抽奖请求
     * @return    抽奖结果
     */
    DrawProcessResult doDrawProcess(DrawProcessReq req);

    /**
     * 返回可参与的活动ID
     * @param req 规则请求
     * @return 量化结果，用户可以参与的活动ID
     */
    RuleQuantificationCrowdResult doRuleQuantificationCrowd(DecisionMatterReq req);

}
