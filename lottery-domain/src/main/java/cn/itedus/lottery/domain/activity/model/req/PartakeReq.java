package cn.itedus.lottery.domain.activity.model.req;

import java.util.Date;

/**
 * @description: 参与活动请求
 * @author: ZeroYiAn
 * @time: 2023/4/29 20:42
 */

public class PartakeReq {
    /**
     * 用户id
     */
    private String uId;
    /**
     * 活动id
     */
    private Long activityId;
    /**
     * 活动领取(参与)时间
     */
    private Date partakeDate;

    public PartakeReq() {
    }

    public PartakeReq(String uId, Long activityId) {
        this.uId = uId;
        this.activityId = activityId;
        //注意这里不能直接用快捷方式生成构造函数。不然partakeDate为null
        this.partakeDate = new Date();
    }

    public PartakeReq(String uId, Long activityId, Date partakeDate) {
        this.uId = uId;
        this.activityId = activityId;
        this.partakeDate = partakeDate;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Date getPartakeDate() {
        return partakeDate;
    }

    public void setPartakeDate(Date partakeDate) {
        this.partakeDate = partakeDate;
    }
}
