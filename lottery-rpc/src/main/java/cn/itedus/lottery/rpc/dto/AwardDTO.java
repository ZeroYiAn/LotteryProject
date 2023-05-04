package cn.itedus.lottery.rpc.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 奖品信息DTO(Data Transfer Object)
 * @author: ZeroYiAn
 * @time: 2023/5/4 15:11
 */

/**
 * 实现Serializable接口的作用：即实现可序列化
 * 1、存储对象在存储介质中，以便在下次使用的时候，可以很快捷的重建一个副本；
 * 2、便于数据传输，尤其是在远程调用的时候。
 */
public class AwardDTO implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
     */
    private Integer awardType;

    /**
     * 奖品名称
     */
    private String awardName;

    /**
     * 奖品内容「描述、奖品码、sku」
     */
    private String awardContent;

    /**
     * 策略方式（1:单项概率、2:总体概率）
     */
    private Integer strategyMode;

    /**
     * 发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）
     */
    private Integer grantType;
    /**
     * 发奖时间
     */
    private Date grantDate;

    public AwardDTO(String userId) {
        this.userId = userId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getAwardId() {
        return awardId;
    }

    public void setAwardId(String awardId) {
        this.awardId = awardId;
    }

    public Integer getAwardType() {
        return awardType;
    }

    public void setAwardType(Integer awardType) {
        this.awardType = awardType;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardContent() {
        return awardContent;
    }

    public void setAwardContent(String awardContent) {
        this.awardContent = awardContent;
    }

    public Integer getStrategyMode() {
        return strategyMode;
    }

    public void setStrategyMode(Integer strategyMode) {
        this.strategyMode = strategyMode;
    }

    public Integer getGrantType() {
        return grantType;
    }

    public void setGrantType(Integer grantType) {
        this.grantType = grantType;
    }

    public Date getGrantDate() {
        return grantDate;
    }

    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }

    @Override
    public String toString() {
        return "AwardDTO{" +
                "userId='" + userId + '\'' +
                ", activityId=" + activityId +
                ", awardId='" + awardId + '\'' +
                ", awardType=" + awardType +
                ", awardName='" + awardName + '\'' +
                ", awardContent='" + awardContent + '\'' +
                ", strategyMode=" + strategyMode +
                ", grantType=" + grantType +
                ", grantDate=" + grantDate +
                '}';
    }

}

