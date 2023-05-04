package cn.itedus.lottery.domain.strategy.model.vo;

import java.math.BigDecimal;

/**
 * @description:奖品概率信息，奖品编号、库存、概率
 * @author: ZeroYiAn
 * @time: 2023/4/26 19:58
 */

public class AwardRateVO {

    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 中奖概率
     */
    private BigDecimal awardRate;

    public AwardRateVO() {
    }

    public AwardRateVO(String awardId, BigDecimal awardRate) {
        this.awardId = awardId;
        this.awardRate = awardRate;
    }

    public String getAwardId() {
        return awardId;
    }

    public void setAwardId(String awardId) {
        this.awardId = awardId;
    }

    public BigDecimal getAwardRate() {
        return awardRate;
    }

    public void setAwardRate(BigDecimal awardRate) {
        this.awardRate = awardRate;
    }

    @Override
    public String toString() {
        return "AwardRateVO{" +
                "awardId='" + awardId + '\'' +
                ", awardRate=" + awardRate +
                '}';
    }
}

