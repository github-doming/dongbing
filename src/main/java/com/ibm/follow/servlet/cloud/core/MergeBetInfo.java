package com.ibm.follow.servlet.cloud.core;

/**
 * @Description: 合并投注信息
 * @Author: null
 * @Date: 2019-12-18 16:12
 * @Version: v1.0
 */
public class MergeBetInfo {

    private int[][] betInfo;

    private String followBetIds;

    public int[][] getBetInfo() {
        return betInfo;
    }
    public void setBetInfo(int[][] betInfo){
        this.betInfo = betInfo;
    }
    public void setBetInfo(Object betInfo) {
        if (betInfo != null) {
            if (betInfo instanceof int[][]) {
                this.betInfo = (int[][]) betInfo;
            }
        }
    }

    public String getFollowBetIds() {
        return followBetIds;
    }
    public void setFollowBetIds(String followBetIds){
        this.followBetIds=followBetIds;
    }
    public void setFollowBetIds(Object followBetIds) {
        if (followBetIds != null) {
            if (followBetIds instanceof String) {
                this.followBetIds = (String) followBetIds;
            }
        }
        this.followBetIds=null;
    }
}
