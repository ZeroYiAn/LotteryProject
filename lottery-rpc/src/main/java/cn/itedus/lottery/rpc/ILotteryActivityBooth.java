package cn.itedus.lottery.rpc;

import cn.itedus.lottery.rpc.req.DrawReq;
import cn.itedus.lottery.rpc.req.QuantificationDrawReq;
import cn.itedus.lottery.rpc.res.DrawRes;

/**
 * @description: 抽奖活动展台接口 booth:售货棚、摊位
 * @author: ZeroYiAn
 * @time: 2023/5/4 15:07
 */
public interface ILotteryActivityBooth {
    /**
     * 指定活动抽奖
     * @param drawReq 请求参数
     * @return        抽奖结果
     */
    DrawRes doDraw(DrawReq drawReq);

    /**
     * 量化人群抽奖
     * @param quantificationDrawReq 请求参数
     * @return                      抽奖结果
     */
    DrawRes doQuantificationDraw(QuantificationDrawReq quantificationDrawReq);
}
