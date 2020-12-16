package com.ibm.follow.servlet.client.core.job.bet;

import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Objects;

/**
 * @Description: 摘要
 * @Author: null
 * @Date: 2019-12-12 11:13
 * @Version: v1.0
 */
public class SummaryInfo {

    private Object memberId;
    private String account;
    private Integer betCount;
    private Integer betAmount;
    private Object period;
    private String oddNumber;
    public SummaryInfo() {
    }
    public SummaryInfo(Object memberId, String account) {
        this.memberId = memberId;
        this.account = account;
    }
    public void setMemberId(Object memberId) {
        this.memberId=memberId;
    }
    public Object getMemberId() {
        return this.memberId ;
    }

    public void setAccount(String account){
        this.account=account;
    }
    public void setAccount(Object account){
        if(account!=null){
            this.account=account.toString();
        }else {
            this.account=null;
        }
    }
    public String getAccount(){
        return this.account;
    }


    public void setBetCount(Integer betCount) {
        this.betCount=betCount;
    }
    public void setBetCount(Object betCount) {
        if (betCount != null) {
            if (betCount instanceof Integer) {
                this.betCount= (Integer) betCount;
            }else if (StringTool.isInteger(betCount.toString())) {
                this.betCount = Integer.parseInt(betCount.toString());
            }
        }else{
            this.betCount = null;
        }
    }
    public Integer getBetCount() {
        return this.betCount ;
    }



    public void setBetAmount(Integer betAmount) {
        this.betAmount=betAmount;
    }
    public void setBetAmount(Object betAmount) {
        if (betAmount != null) {
            if (betAmount instanceof Integer) {
                this.betAmount= (Integer) betAmount;
            }else{
                NumberTool.getInteger(betAmount);
                this.betAmount = Integer.parseInt(betAmount.toString());
            }
        }else{
            this.betAmount = null;
        }
    }
    public Integer getBetAmount() {
        return this.betAmount ;
    }



    public void setPeriod(Object period) {
        this.period=period;
    }
    public Object getPeriod() {
        return this.period ;
    }


    public void setOddNumber(String oddNumber){
        this.oddNumber=oddNumber;
    }
    public void setOddNumber(Object oddNumber){
        if(oddNumber!=null){
            this.oddNumber=oddNumber.toString();
        }else {
            this.oddNumber=null;
        }
    }
    public String getOddNumber(){
        return this.oddNumber;
    }

    public boolean equal(SummaryInfo info) {
        if (this == info){
            return true;
        }
        if (info == null ){
            return false;
        }
        return Objects.equals(betCount, info.betCount) && Objects.equals(betAmount, info.betAmount);
    }
    @Override public int hashCode() {
        return Objects.hash(betCount);
    }
    @Override
    public String toString() {
        return "SummaryInfo{" +
                "memberId=" + memberId +
                ", account='" + account + '\'' +
                ", betCount=" + betCount +
                ", betAmount=" + betAmount +
                ", period=" + period +
                ", oddNumber='" + oddNumber + '\'' +
                '}';
    }
}
