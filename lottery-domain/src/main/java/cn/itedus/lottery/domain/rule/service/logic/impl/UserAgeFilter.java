package cn.itedus.lottery.domain.rule.service.logic.impl;

import cn.itedus.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.itedus.lottery.domain.rule.service.logic.BaseLogic;
import cn.itedus.lottery.domain.rule.service.logic.LogicFilter;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ZeroYiAn
 * @time: 2023/5/2 23:55
 */
@Component
public class UserAgeFilter extends BaseLogic{
    @Override
    public String matterValue(DecisionMatterReq decisionMatter) {
        return decisionMatter.getValMap().get("age").toString();
    }
}
