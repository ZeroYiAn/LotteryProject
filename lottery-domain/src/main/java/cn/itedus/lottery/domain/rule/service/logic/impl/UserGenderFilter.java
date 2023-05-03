package cn.itedus.lottery.domain.rule.service.logic.impl;

import cn.itedus.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.itedus.lottery.domain.rule.model.vo.TreeNodeLineVO;
import cn.itedus.lottery.domain.rule.service.logic.BaseLogic;
import cn.itedus.lottery.domain.rule.service.logic.LogicFilter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: ZeroYiAn
 * @time: 2023/5/2 23:56
 */
@Component
public class UserGenderFilter extends BaseLogic{
    @Override
    public String matterValue(DecisionMatterReq decisionMatter) {
        return decisionMatter.getValMap().get("gender").toString();
    }
}
