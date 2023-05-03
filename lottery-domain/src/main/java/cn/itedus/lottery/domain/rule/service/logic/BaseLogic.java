package cn.itedus.lottery.domain.rule.service.logic;

import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.itedus.lottery.domain.rule.model.vo.TreeNodeLineVO;

import java.util.List;

/**
 * @description: 规则基础抽象类
 * @author: ZeroYiAn
 * @time: 2023/5/2 23:18
 */

public abstract class BaseLogic implements LogicFilter {
    /**
     * 如果存在匹配的节点，则返回该节点的 ID，否则返回常量 Constants.Global.TREE_NULL_NODE(即空节点值)，
     * 是基于比对值与节点连线分别按照连线的规定方式进行对比来获取下一个树节点
     * @param matterValue          决策值
     * @param treeNodeLineInfoList 决策节点
     * @return  下一个去向的结点id
     */
    @Override
    public Long filter(String matterValue, List<TreeNodeLineVO> treeNodeLineInfoList) {
        for(TreeNodeLineVO nodeLine : treeNodeLineInfoList){
            //如果满足比对逻辑
            if(decisionLogic(matterValue,nodeLine)){
                //返回要去向的结点id
                return nodeLine.getNodeIdTo();
            }
        }
        return Constants.Global.TREE_NULL_NODE;
    }

    /**
     * 获取规则比对值
     * @param decisionMatter 决策物料
     * @return 比对值
     */
    @Override
    public abstract String matterValue(DecisionMatterReq decisionMatter);

    /**
     * 确定比对逻辑
     * @param matterValue 物料值
     * @param nodeLine    结点连线
     * @return            是否满足比对逻辑
     */
    private boolean decisionLogic(String matterValue, TreeNodeLineVO nodeLine) {
        switch (nodeLine.getRuleLimitType()) {
            case Constants.RuleLimitType.EQUAL:
                return matterValue.equals(nodeLine.getRuleLimitValue());
            case Constants.RuleLimitType.GT:
                return Double.parseDouble(matterValue) > Double.parseDouble(nodeLine.getRuleLimitValue());
            case Constants.RuleLimitType.LT:
                return Double.parseDouble(matterValue) < Double.parseDouble(nodeLine.getRuleLimitValue());
            case Constants.RuleLimitType.GE:
                return Double.parseDouble(matterValue) >= Double.parseDouble(nodeLine.getRuleLimitValue());
            case Constants.RuleLimitType.LE:
                return Double.parseDouble(matterValue) <= Double.parseDouble(nodeLine.getRuleLimitValue());
            default:
                return false;
        }
    }
}
