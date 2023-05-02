package cn.itedus.lottery.application.process.res;


import cn.itedus.lottery.common.Result;
import cn.itedus.lottery.domain.strategy.model.vo.DrawAwardInfo;

/**
 * @description: 活动抽奖结果
 * @author: ZeroYiAn
 * @time: 2023/5/2 9:03
 */

public class DrawProcessResult extends Result {

    private DrawAwardInfo drawAwardInfo;

    public DrawProcessResult(String code, String info) {
        super(code, info);
    }

    public DrawProcessResult(String code, String info, DrawAwardInfo drawAwardInfo) {
        super(code, info);
        this.drawAwardInfo = drawAwardInfo;
    }

    public DrawAwardInfo getDrawAwardInfo() {
        return drawAwardInfo;
    }

    public void setDrawAwardInfo(DrawAwardInfo drawAwardInfo) {
        this.drawAwardInfo = drawAwardInfo;
    }
}
