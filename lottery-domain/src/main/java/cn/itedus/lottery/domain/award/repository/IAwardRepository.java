package cn.itedus.lottery.domain.award.repository;

/**
 * @description:奖品表仓储服务接口
 * @author: ZeroYiAn
 * @time: 2023/4/26 15:50
 */
public interface IAwardRepository {
    /**
     * 更新奖品发放状态
     *
     * @param uId               用户ID
     * @param orderId           订单ID
     * @param awardId           奖品ID
     * @param grantState        奖品状态
     */
    void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState);

}
