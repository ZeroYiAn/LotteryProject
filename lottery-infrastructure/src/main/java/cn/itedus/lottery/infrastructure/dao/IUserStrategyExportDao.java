package cn.itedus.lottery.infrastructure.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import cn.itedus.lottery.infrastructure.po.UserStrategyExport;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:用户策略计算结果表DAO
 * @author: ZeroYiAn
 * @time: 2023/4/28 18:21
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserStrategyExportDao {

   /**
    * 新增数据
    * @param strategyExport 用户策略
    */
   @DBRouter(key = "uId")
   void insert(UserStrategyExport strategyExport);

   /**
    * 查询数据
    * @param uId 用户ID
    * @return 用户策略
    */
   @DBRouter
   UserStrategyExport queryUserStrategyExportByUId(String uId);


   /**
    * 更新发奖状态
    * @param userStrategyExport 发奖信息
    */
   @DBRouter
   void updateUserAwardState(UserStrategyExport userStrategyExport);

   /**
    * 更新发送MQ状态
    * @param userStrategyExport 发送消息
    */
   @DBRouter
   void updateInvoiceMqState(UserStrategyExport userStrategyExport);
}
