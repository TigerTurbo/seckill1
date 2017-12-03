package org.seckill.dto;

import enums.SeckillStateEnum;
import org.seckill.entity.SuccessKilled;

/*封装秒杀后的结果
* */
public class seckillExecuetion {
    private long seckillId;
    private int state;
    private String stateInfo;
    private SuccessKilled successKilled;

    public seckillExecuetion(long seckillId, int state, SeckillStateEnum seckillStateEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = seckillStateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public seckillExecuetion(long seckillId, int state, SeckillStateEnum seckillStateEnum) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = seckillStateEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
