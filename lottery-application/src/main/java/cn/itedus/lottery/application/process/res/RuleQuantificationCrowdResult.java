package cn.itedus.lottery.application.process.res;

import cn.itedus.lottery.common.Result;

/**
 * @description:  规则量化人群结果
 * @author: ZeroYiAn
 * @time: 2023/5/3 17:15
 */

public class RuleQuantificationCrowdResult extends Result {
    /**
     * 活动ID
     */
    private Long activityId;

    public RuleQuantificationCrowdResult(String code,String info){
        super(code,info);
    }
    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
