package com.ibm.common.utils.http.utils.entity;

import com.ibm.common.utils.game.GameUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 会员对象
 * @Author: null
 * @Date: 2020-04-22 14:05
 * @Version: v1.0
 */
public class MemberCrawler extends Crawler{
    /**
     * 赔率信息
     */
    private Map<GameUtil.Code, Map<String, Object>> odds;
    /**
     * 用户信息
     */
    private MemberUserInfo memberUserInfo;
    /**
     * COM盘口游戏投注phaseid
     */
    private Map<GameUtil.Code,ComGameInfo> pIdMap;
    /**
     * COM盘口检验码
     */
    private String jeuValidate;
    /**
     * COM盘口检验码使用状态
     */
    private boolean jeuValidateStatus;

    public MemberCrawler(){
        super();
        odds=new HashMap<>();
        memberUserInfo=new MemberUserInfo();
        pIdMap=new HashMap<>();
        jeuValidateStatus=true;
    }

    public MemberUserInfo getMemberUserInfo() {
        return memberUserInfo;
    }


    public void setOdds(Map<GameUtil.Code, Map<String, Object>> odds){
        this.odds=odds;
    }
    public Map<GameUtil.Code, Map<String, Object>> getOdds(){
        return this.odds;
    }


    public Map<GameUtil.Code,ComGameInfo> getPIdMap(){
        return this.pIdMap;
    }


    public void setJeuValidate(String jeuValidate){
        this.jeuValidate=jeuValidate;
    }
    public String getJeuValidate(){
        return this.jeuValidate;
    }


    public void setJeuValidateStatus(boolean jeuValidateStatus){
        this.jeuValidateStatus=jeuValidateStatus;
    }
    public boolean getJeuValidateStatus(){
        return this.jeuValidateStatus;
    }
}
