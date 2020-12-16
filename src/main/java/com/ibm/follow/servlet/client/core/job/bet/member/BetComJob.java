package com.ibm.follow.servlet.client.core.job.bet.member;

import com.common.core.JsonResultBeanPlus;
import com.common.handicap.MemberOption;
import com.ibm.common.core.configs.ComConfig;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.follow.servlet.client.ibmc_hm_bet_fail.service.IbmcHmBetFailService;
import com.ibm.follow.servlet.client.ibmc_hm_bet_info.service.IbmcHmBetInfoService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

import java.util.*;

/**
 * @Description: COM投注JOB
 * @Author: null
 * @Date: 2020-04-25 10:18
 * @Version: v1.0
 */
public class BetComJob extends BetJob  {

    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        try {
            handicapCode= HandicapUtil.Code.COM;
            super.executeJob(context);
            if (ContainerTool.isEmpty(hmBetMap)||ContainerTool.isEmpty(hmGameSet)) {
                return;
            }
            //处理投注信息
            betProcess();
            log.info("盘口【{}】盘口会员【{}】游戏【{}】期数【{}】投注完成",handicapCode,existHmId,gameCode,period);
        } catch (Exception e) {
            log.error("盘口【{}】盘口会员【{}】游戏【{}】期数【{}】投注异常:{}",handicapCode,existHmId,gameCode,period,e.getMessage());
            throw e;
        }
    }
    /**
     * 投注信息处理
     *
     */
    private void betProcess() throws Exception {
        //总投注项
        Map<String, List<String>> betItemMap = new HashMap<>(3);
        //投注id
        Map<String, String> hmBetIdMap = new HashMap<>(3);
        //投注总金额
        Map<String, Long> fundMap = new HashMap<>(3);
        //保存错误投注项
        Map<String, Object> errorMap = new HashMap<>(10);
        //投注限制code
        Map<String, String> limitTypeCodes = BallCodeTool.getItemTypeCodes(gameCode);
        Map<String, String> ballCode = BallCodeTool.getBallLimitCode(handicapCode, gameCode);
        for (Map.Entry<String, Object> entry : hmBetMap.entrySet()) {
            String[] betInfos = entry.getValue().toString().split("#");
            for (String info : betInfos) {
                //分投处理
                String[] items = BetJobDefine.classifyProcess(hmGameSet, info, ballCode, limitTypeCodes, errorMap, entry.getKey());
                if (ContainerTool.isEmpty(items)) {
                    continue;
                }
                //保存投注信息
                saveBetInfo(betItemMap, fundMap, hmBetIdMap, items, entry.getKey());
            }
        }
        String methodType = StringTool.isEmpty(hmBetId) ? IbmMethodEnum.FOLLOW_BET.name() : IbmMethodEnum.MANUAL_BET.name();
        //错误信息处理
        BetJobDefine.errorProcess(existHmId,gameCode,period,errorMap,methodType);
        if (ContainerTool.isEmpty(betItemMap)) {
            return;
        }
		MemberOption memberOption = handicapCode.getMemberFactory().memberOption(existHmId);
        //投注
        firstBet(memberOption, betItemMap, hmBetIdMap, fundMap);
        //补投
        IbmcHmBetFailService hmBetFailService = new IbmcHmBetFailService();
        List<Map<String, Object>> errorBetInfos = hmBetFailService.findAgainBetInfo(existHmId, period, gameCode);
        if (ContainerTool.notEmpty(errorBetInfos)) {
            againBet(memberOption, errorBetInfos);
        }
        //发送结果信息
        sendResultReceipt(memberOption, methodType);
    }
    /**
     * 分批保存投注信息
     *
     * @param betItemMap 投注信息map
     * @param fundMap    金额map
     * @param hmBetIdMap 投注主键map
     * @param items      投注项
     * @param hmBetId    投注主键
     */
    private void saveBetInfo(Map<String, List<String>> betItemMap, Map<String, Long> fundMap, Map<String, String> hmBetIdMap, String[] items, String hmBetId) {
        for (String item : items) {
            String[] info = item.split("\\|");
            //投注项
            String bet = info[0].concat("|").concat(info[1]);
            //判断投注类型，双面，冠亚，特号
            if (StringTool.isContains(ComConfig.DOUBLE_SIDE.toString(), info[1])) {
                BetJobDefine.saveBetItem(betItemMap, fundMap, hmBetIdMap, "dobleSides", item, Long.parseLong(info[2]), hmBetId);
            } else if (StringTool.isContains(ComConfig.SUM_DT.toString(), bet)||ComConfig.SUM_DT.contains(info[1])) {
                BetJobDefine.saveBetItem(betItemMap, fundMap, hmBetIdMap, "sumDT", item, Long.parseLong(info[2]), hmBetId);
            } else {
                BetJobDefine.saveBetItem(betItemMap, fundMap, hmBetIdMap, "ballNO", item, Long.parseLong(info[2]), hmBetId);
            }
        }
    }

    private void againBet(MemberOption memberOption, List<Map<String, Object>> errorBetInfos) throws Exception {
        for (Map<String, Object> betInfoMap : errorBetInfos) {
            List<String> betItems = Arrays.asList(betInfoMap.get("betInfo").toString().split(","));
            double fund = 0;
            for (String betItem : betItems) {
                String[] infos = betItem.split("\\|");
                fund += NumberTool.doubleT(infos[2]);
            }
            //防止throw UnsupportedOperationException
            List<String> bet = new ArrayList<>(betItems);
            String hmBetInfoId = betInfoMap.get("betInfoId").toString();
            //投注
            bet(memberOption, bet, betInfoMap.get("betType").toString(), fund, hmBetInfoId, false);
        }
    }

    private void firstBet(MemberOption memberOption, Map<String, List<String>> betItemMap,
                          Map<String, String> hmBetIdMap, Map<String, Long> fundMap) throws Exception {
        IbmcHmBetInfoService hmBetInfoService = new IbmcHmBetInfoService();
        List<String> betItems;
        for (Map.Entry<String, List<String>> betItem : betItemMap.entrySet()) {
            betItems = betItem.getValue();
            long fund = fundMap.get(betItem.getKey());
            //保存投注信息
            String hmBetInfoId = hmBetInfoService
                    .save(existHmId, hmBetIdMap.get(betItem.getKey()), period, gameCode, betItems, fund);
            //投注
            bet(memberOption, betItems, betItem.getKey(), NumberTool.doubleT(fund), hmBetInfoId, true);
        }
    }
    private void bet(MemberOption memberOption, List<String> betItems, String betType, double fund,
                     String hmBetInfoId, boolean flag) throws Exception {
        //进行信息校验
        if(verify(memberOption,hmBetInfoId,betItems,betType,flag,fund)){
            return;
        }
        //获取期数字符串
        String roundno = PeriodUtil.getHandicapGamePeriod(handicapCode, gameCode, period);
        //获取赔率信息
		memberOption.oddsInfo(gameCode,betType);
        //投注
        JsonResultBeanPlus bean =memberOption.betting(gameCode,roundno,betType,betItems,new ArrayList<>());
        if (!bean.isSuccess()) {
            errorProcess(hmBetInfoId, betItems, betType, flag, bean.getCode(),handicapCode);
            return;
        }
        //TODO 注单号尚未获取
        new IbmcHmBetInfoService().updateState(hmBetInfoId, IbmStateEnum.SUCCESS.name());
    }

}
