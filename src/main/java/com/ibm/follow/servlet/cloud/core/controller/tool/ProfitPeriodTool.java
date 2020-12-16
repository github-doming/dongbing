package com.ibm.follow.servlet.cloud.core.controller.tool;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service.IbmHmBetItemService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period.entity.IbmHmProfitPeriod;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period.service.IbmHmProfitPeriodService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period_vr.entity.IbmHmProfitPeriodVr;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period_vr.service.IbmHmProfitPeriodVrService;
import org.doming.core.tools.ContainerTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 当期盈亏统计工具类
 * @Author: null
 * @Date: 2019-11-28 16:19
 * @Version: v1.0
 */
public class ProfitPeriodTool {

    /**
     * 统计当期投注信息
     * @param hmBetItems        投注详情
     * @param hmInfo            会员信息
     * @param gameId            游戏id
     * @param period            期数
     * @param hmBetItemService  服务类
     * @throws Exception
     */
    public static void saveHmProfitPeriod(Map<String, Object> hmBetItems,String gameId, Map<String,Object> hmInfo,
                                    String period,IbmHmBetItemService hmBetItemService) throws Exception {
        if(ContainerTool.isEmpty(hmBetItems)){
            return ;
        }
        //获取当期投注信息
        Map<String, Object> sumMap=hmBetItemService.findSum(hmBetItems);

        IbmHmProfitPeriodService hmProfitPeriodService=new IbmHmProfitPeriodService();
        IbmHmProfitPeriod hmProfitPeriod=hmProfitPeriodService.findByHmIdAndPeriod(hmInfo.get("HANDICAP_MEMBER_ID_").toString(),period);
        if(hmProfitPeriod==null){
            hmProfitPeriod=new IbmHmProfitPeriod();
            hmProfitPeriod.setHandicapMemberId(hmInfo.get("HANDICAP_MEMBER_ID_").toString());
            hmProfitPeriod.setGameId(gameId);
            hmProfitPeriod.setPeriod(period);
            hmProfitPeriod.setBetFundsT(Integer.parseInt(sumMap.get("amount").toString()));
            hmProfitPeriod.setBetLen(Integer.parseInt(sumMap.get("number").toString()));
            hmProfitPeriod.setCreateTime(new Date());
            hmProfitPeriod.setCreateTimeLong(System.currentTimeMillis());
            hmProfitPeriod.setState(IbmStateEnum.OPEN.name());
            hmProfitPeriodService.save(hmProfitPeriod);
        }else{
            hmProfitPeriod.setBetFundsT(hmProfitPeriod.getBetFundsT()+Integer.parseInt(sumMap.get("amount").toString()));
            hmProfitPeriod.setBetLen(hmProfitPeriod.getBetLen()+Integer.parseInt(sumMap.get("number").toString()));
            hmProfitPeriod.setUpdateTimeLong(System.currentTimeMillis());
            hmProfitPeriodService.update(hmProfitPeriod);
        }
    }

    /**
     * 统计当期模拟投注信息
     * @param handicapMemberId      盘口会员id
     * @param handicapId            盘口id
     * @param period                期数
     * @param fundsTh               投注额
     * @param betLen                投注数
     */
    public static void saveHmProfitPeriodVr(String handicapMemberId,String handicapId,String gameId, Object period, int fundsTh, int betLen) throws Exception {
        IbmHmProfitPeriodVrService profitPeriodVrService=new IbmHmProfitPeriodVrService();
        IbmHmProfitPeriodVr profitPeriod=profitPeriodVrService.findByHmIdAndPeriod(handicapMemberId,period.toString());
        if(profitPeriod==null){
            profitPeriod=new IbmHmProfitPeriodVr();
            profitPeriod.setHandicapMemberId(handicapMemberId);
            profitPeriod.setGameId(gameId);
            profitPeriod.setPeriod(period);
            profitPeriod.setBetFundsT(fundsTh);
            profitPeriod.setBetLen(betLen);
            profitPeriod.setCreateTime(new Date());
            profitPeriod.setCreateTimeLong(System.currentTimeMillis());
            profitPeriod.setState(IbmStateEnum.OPEN.name());
            profitPeriodVrService.save(profitPeriod);
        }else{
            profitPeriod.setBetFundsT(profitPeriod.getBetFundsT()+fundsTh);
            profitPeriod.setBetLen(profitPeriod.getBetLen()+betLen);
            profitPeriod.setUpdateTimeLong(System.currentTimeMillis());
            profitPeriodVrService.update(profitPeriod);
        }
    }
}
