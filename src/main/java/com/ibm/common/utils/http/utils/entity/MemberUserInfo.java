package com.ibm.common.utils.http.utils.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 会员用户信息
 * @Author: null
 * @Date: 2020-04-22 14:19
 * @Version: v1.0
 */
public class MemberUserInfo {
    /**
     * 会员账号
     */
    private String memberAccount;
    /**
     * 会员盘
     */
    private String memberType;
    /**
     * 信用额度
     */
    private String creditQuota;
    /**
     * 使用金额
     */
    private String usedAmount;
    /**
     * 可用额度
     */
    private String usedQuota;
    /**
     * 盈亏金额
     */
    private String profitAmount;
    /**
     * 会员Id
     */
    private String memberId;

    public Map<String, String> getUserInfo(){
        Map<String,String> userInfo=new HashMap<>(8);
        userInfo.put("memberAccount",memberAccount);
        userInfo.put("creditQuota",creditQuota);
        userInfo.put("usedQuota",usedQuota);
        userInfo.put("usedAmount",usedAmount);
        userInfo.put("profitAmount",profitAmount);
        userInfo.put("memberId",memberId);
        return userInfo;
    }


    public String getMemberAccount() {
        return memberAccount;
    }
    public void setMemberAccount(Object memberAccount) {
        if (memberAccount != null) {
            if (memberAccount instanceof String) {
                this.memberAccount = (String) memberAccount;
            }else{
                this.memberAccount=memberAccount.toString();
            }
        }else {
            this.memberAccount=null;
        }
    }


    public String getMemberType() {
        return this.memberType;
    }
    public void setMemberType(Object memberType) {
        if (memberType != null) {
            if (memberType instanceof String) {
                this.memberType = (String) memberType;
            }else{
                this.memberType=memberType.toString();
            }
        }else{
            this.memberType=null;
        }
    }


    public String getProfitAmount() {
        return profitAmount;
    }
    public void setProfitAmount(Object profitAmount) {
        if (profitAmount != null) {
            if (profitAmount instanceof String) {
                this.profitAmount = (String) profitAmount;
            }else{
                this.profitAmount=profitAmount.toString();
            }
        }else {
            this.profitAmount=null;
        }
    }


    public String getUsedQuota() {
        return usedQuota;
    }
    public void setUsedQuota(Object usedQuota) {
        if (usedQuota != null) {
            if (usedQuota instanceof String) {
                this.usedQuota = (String) usedQuota;
            }else{
                this.usedQuota=usedQuota.toString();
            }
        }else {
            this.usedQuota=null;
        }
    }


    public String getCreditQuota() {
        return creditQuota;
    }
    public void setCreditQuota(Object creditQuota) {
        if (creditQuota != null) {
            if (creditQuota instanceof String) {
                this.creditQuota = (String) creditQuota;
            }else{
                this.creditQuota=creditQuota.toString();
            }
        }else{
            this.creditQuota=null;
        }

    }


    public String getUsedAmount() {
        return usedAmount;
    }
    public void setUsedAmount(Object usedAmount) {
        if (usedAmount != null) {
            if (usedAmount instanceof String) {
                this.usedAmount = (String) usedAmount;
            }else {
                this.usedAmount=usedAmount.toString();
            }
        }else{
            this.usedAmount=null;
        }
    }

    public String gettMemberId() {
        return memberId;
    }
    public void setMemberId(Object memberId) {
        if (memberId != null) {
            if (memberId instanceof String) {
                this.memberId = (String) memberId;
            }else {
                this.memberId=memberId.toString();
            }
        }else{
            this.memberId=null;
        }
    }



    @Override
    public String toString() {
        return "MemberUserInfo{" +
                "memberAccount='" + memberAccount + '\'' +
                ", memberType='" + memberType + '\'' +
                ", creditQuota='" + creditQuota + '\'' +
                ", usedAmount='" + usedAmount + '\'' +
                ", usedQuota='" + usedQuota + '\'' +
                ", profitAmount='" + profitAmount + '\'' +
                ", memberId='" + memberId + '\'' +
                '}';
    }
}
