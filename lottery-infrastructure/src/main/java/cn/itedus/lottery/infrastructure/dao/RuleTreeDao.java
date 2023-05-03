package cn.itedus.lottery.infrastructure.dao;

import cn.itedus.lottery.infrastructure.po.RuleTree;
import cn.itedus.lottery.infrastructure.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: ZeroYiAn
 * @time: 2023/5/2 20:16
 */
@Mapper
public interface RuleTreeDao {
    /**
     * 规则书查询
     * @param id id
     * @return 规则树
     */
    RuleTree queryRuleTreeByTreeId(Long id);

    /**
     *  规则树简要信息查询
     * @param treeId 规则树ID
     * @return 规则树
     */
    RuleTree queryTreeSummaryInfo(Long treeId);
}
