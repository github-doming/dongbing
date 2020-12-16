package com.ibm.common.utils.http.utils.entity;

/**
 * @Description: Com游戏信息
 * @Author: null
 * @Date: 2020-04-23 10:12
 * @Version: v1.0
 */
public class ComGameInfo {

    /**
     * 会员游戏投注phaseid
     */
    private String pId;
    /**
     * 游戏期数
     */
    private Object period;
    /**
     * 代理期数阶段码
     */
    private String phaseoption;


    public void setPId(String pId){
        this.pId=pId;
    }
    public String getPId(){
        return this.pId;
    }


    public void setPeriod(Object period){
        this.period=period;
    }
    public Object getPeriod(){
        return this.period;
    }


    public void setPhaseoption(String phaseoption){
        this.phaseoption=phaseoption;
    }
    public String getPhaseoption(){
        return this.phaseoption;
    }

}
