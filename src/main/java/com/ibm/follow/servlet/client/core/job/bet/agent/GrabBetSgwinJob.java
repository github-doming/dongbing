package com.ibm.follow.servlet.client.core.job.bet.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.utils.agent.SgWinAgentUtil;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.DetailInfo;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 抓取SgWin盘口投注项
 * @Author: null
 * @Date: 2019-12-09 10:22
 * @Version: v1.0
 */
public class GrabBetSgwinJob extends GrabBetJob {

    private static final Map<GameUtil.Code ,Map<String, Boolean>> GRAB_CHECK_MAP = new HashMap<>(GameUtil.codes().length);
    static {
        for (GameUtil.Code code : GameUtil.codes()){
            GRAB_CHECK_MAP.put(code,new ConcurrentHashMap<>(10));
        }
    }

    @Override public void executeJob(JobExecutionContext context) throws Exception {
        handicapCode=HandicapUtil.Code.SGWIN;
        super.executeJob(context);
        if(StringTool.isEmpty(new IbmcExistHaService().findState(existHaId))){
            log.error(message,handicapCode,existHaId,"抓取盘口投注项异常，清除盘口抓取");
            GRAB_CHECK_MAP.get(gameCode).remove(existHaId);
            QuartzTool.removeGrabBetJob(existHaId, handicapCode, gameCode);
            return ;
        }

        if (GRAB_CHECK_MAP.get(gameCode).getOrDefault(existHaId, false)) {
            log.info(message,handicapCode,existHaId,gameCode.name(),period,"上一次尚未抓取完成");
            return;
        }
        GRAB_CHECK_MAP.get(gameCode).put(existHaId, true);
        try {
            if (gameCode.getGameFactory().period(handicapCode).getDrawTime() - System.currentTimeMillis() > gameCode.getGameFactory().getInterval()) {
                log.info(message,handicapCode,existHaId,gameCode.name(),period,"未到可以开始抓取的时间");
                return;
            }
            if(initInfo()){
                return;
            }
            execute();
        } catch (Exception e) {
            log.error(message,handicapCode,existHaId,gameCode.name(),period,e);
            throw e;
        } finally {
            GRAB_CHECK_MAP.get(gameCode).remove(existHaId);
            log.info(message,handicapCode,existHaId,gameCode.name(),period,"抓取投注项完成");
        }
    }

    private void execute() throws Exception {
        JSONObject memberInfo = new JSONObject();
        //判断跟随会员类型
        if (IbmTypeEnum.APPOINT.equal(followInfo.get("FOLLOW_MEMBER_TYPE_"))) {
            if (StringTool.isEmpty(followInfo.get("MEMBER_LIST_INFO_"))) {
                log.debug(message,handicapCode,existHaId,gameCode.name(),period,"没有跟单的会员");
                return;
            }
            memberInfo = JSON.parseObject(followInfo.get("MEMBER_LIST_INFO_").toString());
        } else if (IbmTypeEnum.ALL.equal(followInfo.get("FOLLOW_MEMBER_TYPE_"))) {
            memberInfo = null;
        }

        //内存中的代理信息
        Map<String, SummaryInfo> agent = GrabBetInfo.getAgent(existHaId, gameCode);
        //盘口时间字符串
        String date = PeriodUtil.getHandicapGameDateStr(handicapCode, gameCode, period);
        // 获取跟投会员的未结算摘要信息
        SgWinAgentUtil agentUtil = SgWinAgentUtil.findInstance();
        JsonResultBeanPlus bean = agentUtil.getBetSummary(existHaId, gameCode, agent, date);
        if (!bean.isSuccess()) {
            log.warn(message,handicapCode,existHaId,gameCode.name(),period,"获取未结算摘要信息失败:"+bean.toJsonString());
            return;
        }
        if (ContainerTool.isEmpty(bean.getData())) {
            //没信息，说明上一期的已经结算了，历史数据(投注额，投注数，注单号，期数)都没有用了，可以清除历史的信息
            GrabBetInfo.clearInfo(existHaId,gameCode);
            log.debug(message,handicapCode,existHaId,gameCode.name(),period,"获取投注摘要为空");
            return;
        }
        Map<String, Map<String, SummaryInfo>> betSummary = (Map<String, Map<String, SummaryInfo>>) bean.getData();
        agent = betSummary.get("agent");

        if(ContainerTool.notEmpty(agent)){
            GrabBetInfo.putAgent(existHaId, gameCode, agent);
        }

        Map<String, SummaryInfo> member = betSummary.get("member");
        // 跟投数据
        Map<String, double[][]> betMap = new HashMap<>(member.size() * 3 / 4 + 1);
        //期数字符串
        String roundno = PeriodUtil.getHandicapGamePeriod(handicapCode, gameCode, period);
        if (memberInfo == null) {
            for (Map.Entry<String, SummaryInfo> entry : member.entrySet()) {
                if (handicapAccount.contains(entry.getKey())) {
                    continue;
                }
                putBetMap(agentUtil, betMap, entry.getValue(), roundno, date);
            }
        } else {
            for (String memberAccount : memberInfo.keySet()) {
                //该跟投会员没有投注数据
                if (!member.containsKey(memberAccount) || handicapAccount.contains(memberAccount)) {
                    continue;
                }
                putBetMap(agentUtil, betMap,member.get(memberAccount), roundno, date);
            }
        }
        //获取到的数据为空
        if (ContainerTool.isEmpty(betMap)) {
            log.debug(message,handicapCode,existHaId,gameCode.name(),period,"获取未结算投注明细为空");
            return;
        }
        // 处理和发送投注结合
        new GrabBet(existHaId, gameCode, period).processBetMap(setInfo, hmSendInfos, memberInfo, betMap);
    }


    private void putBetMap(SgWinAgentUtil agentUtil, Map<String, double[][]> betMap,
                           SummaryInfo summaryInfo, String roundno, String date) {
        JsonResultBeanPlus bean;
        summaryInfo.setPeriod(period);
        SummaryInfo ramMemberInfo = GrabBetInfo.getMember(existHaId, gameCode, summaryInfo);
        //内存中不存在数据
        int ramBetCount =0;
        if (ramMemberInfo==null) {
            bean = agentUtil.getBetDetail(existHaId, gameCode, summaryInfo.getAccount(), "", roundno, date);
        } else {
            if (StringTool.isEmpty(ramMemberInfo.getOddNumber())) {
                log.error(message,handicapCode,existHaId,gameCode.name(),period,"没有抓取详情成功，内存数据为="+ramMemberInfo.toString());
                return;
            }
            //投注数没变,投注额没变
            ramBetCount =  ramMemberInfo.getBetCount();
            int ramBetAmount= ramMemberInfo.getBetAmount();
            if (ramBetCount - summaryInfo.getBetCount() == 0&&ramBetAmount-summaryInfo.getBetAmount()==0) {
                return;
            }
            log.error("代理【"+existHaId+"】期数【"+roundno+"】重复抓取,旧内存信息【"+ramMemberInfo+"】,新内存信息【"+summaryInfo);
            bean = agentUtil
                    .getBetDetail(existHaId, gameCode,  summaryInfo.getAccount(), ramMemberInfo.getOddNumber(), null,
                            date);
        }
        if (!bean.isSuccess()) {
            log.warn(message,handicapCode,existHaId,gameCode.name(),period,"获取投注详情失败:"+bean.toJsonString());
            return;
        }
        //投注详情为空
        if (ContainerTool.isEmpty(bean.getData())) {
            return;
        }
        DetailBox betDetail = (DetailBox) bean.getData();

        List<DetailInfo> details = betDetail.details();

        //放入最大注单号
        summaryInfo.setBetCount(details.size() + ramBetCount);
        log.info("代理【"+existHaId+"】期数【"+roundno+"】抓取,旧内存信息【"+ramMemberInfo+"】,新内存信息【"+summaryInfo);
        GrabBetInfo.putMember(existHaId, gameCode, summaryInfo,betDetail.maxOddNumber());
        for (DetailInfo detail : details) {
            int[] item = GameTool.item(gameCode, detail.betItem());

            //存入投注资金
            if (betMap.containsKey(summaryInfo.getAccount())) {
                betMap.get(summaryInfo.getAccount())[item[0]][item[1]] +=  detail.funds();
            } else {
                double[][] fundsMatrix = GameTool.getFundsMatrix(gameCode);
                fundsMatrix[item[0]][item[1]] +=  detail.funds();
                betMap.put(summaryInfo.getAccount(), fundsMatrix);
            }
        }
    }
}
