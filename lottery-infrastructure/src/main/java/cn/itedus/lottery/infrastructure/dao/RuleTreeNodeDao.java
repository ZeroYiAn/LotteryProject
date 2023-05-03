package cn.itedus.lottery.infrastructure.dao;

import cn.itedus.lottery.infrastructure.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.tomcat.util.digester.Rule;

import java.util.List;

/**
 * @description:
 * @author: ZeroYiAn
 * @time: 2023/5/2 22:39
 */
@Mapper
public interface  RuleTreeNodeDao {
    /**
     * 查询规则树结点
     * @param treeId 规则树ID
     * @return 规则树结点集合
     */
    List<RuleTreeNode> queryRuleTreeNodeList(Long treeId);

    /**
     * 查询规则树结点数量
     * @param treeId 规则树ID
     * @return  结点数量
     */
    int queryTreeNodeCount(Long treeId);

    /**
     * 查询规则树节点
     *
     * @param treeId    规则树ID
     * @return          节点集合
     */
    List<RuleTreeNode> queryTreeRulePoint(Long treeId);
}
