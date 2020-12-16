package com.ibm.common.utils.http.utils.entity;

/**
 * @Description: 会员和代理账号信息
 * @Author: null
 * @Date: 2020-04-22 14:24
 * @Version: v1.0
 */
public class AccountInfo  {

    private String account;

    private String password;

    private String handicapUrl;

    private String handicapCaptcha;

    public void setItemInfo(String account,String password,String handicapUrl,String handicapCaptcha){
        this.account=account;
        this.password=password;
        this.handicapUrl=handicapUrl;
        this.handicapCaptcha=handicapCaptcha;
    }

    public void setAccount(String account){
        this.account=account;
    }
    public String getAccount(){
        return this.account;
    }


    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return this.password;
    }


    public void setHandicapUrl(String handicapUrl){
        this.handicapUrl=handicapUrl;
    }
    public String getHandicapUrl(){
        return this.handicapUrl;
    }


    public void setHandicapCaptcha(String handicapCaptcha){
        this.handicapCaptcha=handicapCaptcha;
    }
    public String getHandicapCaptcha(){
        return this.handicapCaptcha;
    }
}
