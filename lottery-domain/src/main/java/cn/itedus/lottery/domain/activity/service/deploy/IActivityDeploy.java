package cn.itedus.lottery.domain.activity.service.deploy;

import cn.itedus.lottery.domain.activity.model.req.ActivityConfigReq;

import java.util.Queue;
import java.util.zip.ZipEntry;

/**
 * @description: 部署活动配置接口
 * @author:  ZeroYiAn
 * @time:  2023/4/26 21:29
 */

public interface IActivityDeploy {

    /**
     * @Description: 创建活动信息
     * @Param: req
     */
    void createActivity(ActivityConfigReq req);

    /**
     * @Description: 修改活动信息
     * @Param: req 活动配置信息
     */
    void updateActivity(ActivityConfigReq req);

}
