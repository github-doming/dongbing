package com.ibm.follow.servlet.client.core.job.bet.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.utils.agent.IdcAgentApiUtil;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 抓取IDC盘口投注项
 * @Author: Dongming
 * @Date: 2019-09-17 15:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GrabBetIdcJob extends GrabBetJob {
    private static final Map<GameUtil.Code ,Map<String, Boolean> > GRAB_CHECK_MAP = new HashMap<>(GameUtil.codes().length);
    static {
        for (GameUtil.Code code : GameUtil.codes()){
            GRAB_CHECK_MAP.put(code,new ConcurrentHashMap<>(10));
        }
    }

    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        handicapCode=HandicapUtil.Code.IDC;
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
            if (gameCode.getGameFactory().period(handicapCode).getDrawTime() - System.currentTimeMillis()> gameCode.getGameFactory().getInterval()){
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
        }finally {
            GRAB_CHECK_MAP.get(gameCode).remove(existHaId);
            log.info(message,handicapCode,existHaId,gameCode.name(),period,"抓取投注项完成");
        }
    }
    private void execute() throws Exception {
        int maxIdno = 0;
        String memberList = "";
        JSONObject memberInfo = null;
        //判断跟随会员类型
        if (IbmTypeEnum.ALL.equal(followInfo.get("FOLLOW_MEMBER_TYPE_"))) {
            maxIdno = GrabBetInfo.getIdno(existHaId, IbmTypeEnum.ALL.name());
        } else {
            //没有跟投的会员
            if (StringTool.isEmpty(followInfo.get("MEMBER_LIST_INFO_"))) {
                log.debug(message,handicapCode,existHaId,gameCode.name(),period,"没有跟单的会员");
                return;
            }
            memberInfo = JSON.parseObject(followInfo.get("MEMBER_LIST_INFO_").toString());
            int idno;
            for (String member : memberInfo.keySet()) {
                idno = GrabBetInfo.getIdno(existHaId, member);
                memberList = memberList.concat(member).concat(":").concat(Integer.toString(idno)).concat(";");
                maxIdno = Math.max(maxIdno, idno);
            }
        }
        // 获取跟投会员的投注数据
        IdcAgentApiUtil agentApiUtil = IdcAgentApiUtil.findInstance();

        JsonResultBeanPlus bean = agentApiUtil
                .getUnsettledDetailed(existHaId, gameCode, memberList, Integer.toString(maxIdno));
        if (!bean.isSuccess()) {
            log.warn(message,handicapCode,existHaId,gameCode.name(),period,"获取未结算摘要信息失败:"+bean.toJsonString());
            return;
        }
		Map<String,Object> data= (Map<String, Object>) bean.getData();

        JSONArray betDetails = (JSONArray) data.get("unsettledDetailed");

		int oddNumberMax= (int) data.get("oddNumberMax");
		GrabBetInfo.putIdno(existHaId,IbmTypeEnum.ALL.name(),oddNumberMax);

        // 跟投数据
        Map<String, double[][]> betMap = new HashMap<>(betDetails.size() / 4);

        String rno = PeriodUtil.getHandicapGamePeriod(handicapCode, gameCode, period);
        for (int i = 0; i < betDetails.size(); i++) {
            JSONObject details = betDetails.getJSONObject(i);
            //判断期数是否相同
            if (!rno.equals(details.get("rno"))) {
                continue;
            }
            String memberAccount = details.getString("memberAccount");
            if(handicapAccount.contains(memberAccount)){
                continue;
            }
            int idno = details.getInteger("oddNumber");
            //注单号比历史注单号小
            if(maxIdno>=idno){
				continue;
			}
            String betItem = details.getString("betItem");
            double funds = details.getDouble("funds");
            int[] item = GameTool.item(gameCode, betItem);

            //放入最大注单号
            GrabBetInfo.putIdno(existHaId, memberInfo == null ? IbmTypeEnum.ALL.name() : memberAccount, idno);

            //存入投注资金
            if (betMap.containsKey(memberAccount)) {
                betMap.get(memberAccount)[item[0]][item[1]] += funds;
            } else {
                double[][] fundsMatrix = GameTool.getFundsMatrix(gameCode);
                fundsMatrix[item[0]][item[1]] += funds;
                betMap.put(memberAccount, fundsMatrix);
            }
        }
        //获取到的数据为空
        if (ContainerTool.isEmpty(betMap)) {
            return;
        }
        new GrabBet(existHaId, gameCode, period).processBetMap(setInfo, hmSendInfos, memberInfo, betMap);
    }
}
