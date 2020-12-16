package com.ibm.common.utils.http.utils.entity;

import com.alibaba.fastjson.JSONArray;
import com.ibm.common.utils.game.GameUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 代理爬虫对象
 * @Author: null
 * @Date: 2020-04-24 10:06
 * @Version: v1.0
 */
public class AgentCrawler extends Crawler{
    /**
     * 会员列表信息
     */
    private JSONArray memberArray;
    /**
     * IDC参数
     */
    private String memberno;
    /**
     * COM盘口期数阶段码
     */
    private Map<GameUtil.Code,ComGameInfo> phaseoptionMap;


    public AgentCrawler(){
        super();
        phaseoptionMap=new HashMap<>(10);
    }

    public Map<GameUtil.Code,ComGameInfo> getPhaseoptionMap(){
        return this.phaseoptionMap;
    }



    public void setMemberArray(JSONArray memberArray){
        this.memberArray=memberArray;
    }
    public JSONArray getMemberArray(){
        return this.memberArray;
    }

    public void setMemberno(Object memberno) {
        if (memberno != null) {
            if (memberno instanceof String) {
                this.memberno = (String) memberno;
            }else{
                this.memberno=memberno.toString();
            }
        }else{
            this.memberno=null;
        }

    }
    public String getMemberno() {
        return memberno;
    }

}
