package com.ibm.common.utils.http.utils.entity;

import org.doming.develop.http.httpclient.HttpClientConfig;

/**
 * @Description: 爬虫对象
 * @Author: null
 * @Date: 2020-04-24 10:07
 * @Version: v1.0
 */
public class Crawler {
    /**
     * 已存在盘口会员id
     */
    private String existId;
    /**
     * http请求配置类
     */
    private HttpClientConfig hcConfig;
    /**
     * 主机地址
     */
    private String projectHost;
    /**
     * IDC才有的票证，其他盘口暂不使用
     */
    private String ticket;
    /**
     * 检验时间
     */
    private Long checkTime;
    /**
     * 账号信息
     */
    private AccountInfo accountInfo;
    /**
     * 浏览器码,COM盘口所属
     */
    private String browserCode;

    public Crawler(){
        checkTime=0L;
        accountInfo=new AccountInfo();
    }

    public void setTicket(Object ticket) {
        if (ticket != null) {
            if (ticket instanceof String) {
                this.ticket = (String) ticket;
            }
        }else{
            this.ticket=null;
        }
    }
    public String getTicket() {
        return ticket;
    }


    public void setExistId(Object existId) {
        if (existId != null) {
            if (existId instanceof String) {
                this.existId = (String) existId;
            }else{
                this.existId=existId.toString();
            }
        }else{
            this.existId=null;
        }

    }
    public String getExistId() {
        return existId;
    }


    public void setHcConfig(HttpClientConfig hcConfig){
        this.hcConfig=hcConfig;
    }
    public HttpClientConfig getHcConfig(){
        return this.hcConfig;
    }


    public void setProjectHost(String projectHost){
        this.projectHost=projectHost;
    }
    public void setProjectHost(Object projectHost){
        if(projectHost!=null){
            this.projectHost=projectHost.toString();
        }else {
            this.projectHost=null;
        }
    }
    public String getProjectHost(){
        return this.projectHost;
    }

    public void setCheckTime(Long checkTime){
        this.checkTime=checkTime;
    }
    public Long getCheckTime(){
        return this.checkTime;
    }


    public void setAccountInfo(AccountInfo accountInfo){
        this.accountInfo=accountInfo;
    }
    public AccountInfo getAccountInfo(){
        return this.accountInfo;
    }


    public void setBrowserCode(String browserCode){
        this.browserCode=browserCode;
    }
    public String getBrowserCode(){
        return this.browserCode;
    }
}
