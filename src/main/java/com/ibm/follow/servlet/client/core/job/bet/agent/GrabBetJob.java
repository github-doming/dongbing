package com.ibm.follow.servlet.client.core.job.bet.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.ibmc_agent_member_info.service.IbmcAgentMemberInfoService;
import com.ibm.follow.servlet.client.ibmc_ha_game_set.service.IbmcHaGameSetService;
import com.ibm.follow.servlet.client.ibmc_ha_set.service.IbmcHaSetService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 抓取盘口投注项
 * @Author: null
 * @Date: 2020-04-10 14:54
 * @Version: v1.0
 */
public class GrabBetJob extends BaseCommJob {
    public String message="盘口【{}】盘口代理【{}】抓取【{}】游戏【{}】期,结果信息:{}";
    public String existHaId;
    public GameUtil.Code gameCode;
    public Object period;
    public HandicapUtil.Code handicapCode;

    public Map<String, Object> setInfo;
    public Map<String, Object> followInfo;
    public List<Map<String, Object>> hmSendInfos;
    public List<String> handicapAccount;

    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        JobDataMap dataMap = context.getMergedJobDataMap();
        existHaId = dataMap.getString("existHaId");
        if(dataMap.get("gameCode") instanceof Enum){
            gameCode = (GameUtil.Code) dataMap.get("gameCode");
        }else{
            gameCode=GameUtil.Code.valueOf(dataMap.get("gameCode").toString());
        }
		period =gameCode.getGameFactory().period(handicapCode).findPeriod();
        log.info(String.format("用户【%s】的【%s】盘口，抓取【%s】游戏【%s】期,开始抓取投注项", existHaId, handicapCode.name(),
                gameCode.name(), period));
    }

    public boolean initInfo() throws SQLException {
        // 获取代理游戏设置信息
        setInfo = new IbmcHaGameSetService().findSet(existHaId, gameCode.name());
        //没有开启投注状态
        if (ContainerTool.isEmpty(setInfo) || !Boolean.parseBoolean(setInfo.get("BET_STATE_").toString())) {
            log.debug(String.format("用户【%s】的【%s】盘口，抓取【%s】游戏【%s】期,没有开启投注", existHaId, handicapCode.name(),
                    gameCode.name(), period));
            return true;
        }
        // 获取代理跟投的会员
        followInfo = new IbmcHaSetService().findFollowInfo(existHaId);
        if (ContainerTool.isEmpty(followInfo)) {
            log.debug(String.format("用户【%s】的【%s】盘口，抓取【%s】游戏【%s】期,设置信息为空", existHaId, handicapCode.name(),
                    gameCode.name(), period));
            return true;
        }
        //代理-发送到的会员信息
        hmSendInfos = new IbmcAgentMemberInfoService().listHmInfo4SendBet(existHaId);
        if (ContainerTool.isEmpty(hmSendInfos)) {
            log.debug(String.format("用户【%s】的【%s】盘口，抓取【%s】游戏【%s】期,没有需要接收的会员", existHaId, handicapCode.name(),
                    gameCode.name(), period));
            return true;
        }
        //剔除该盘口在本平台登录的会员
        handicapAccount = new ArrayList<>();
        //解析投注模式
        List<Map<String, Object>> newInfos= new ArrayList<>(hmSendInfos);
        for (Map<String, Object> hmSendInfo : hmSendInfos) {
            JSONObject betModeInfo = JSON.parseObject(hmSendInfo.remove("BET_MODE_INFO_").toString());
            if (!betModeInfo.containsKey(gameCode.name())){
                newInfos.remove(hmSendInfo);
                continue;
            }
            hmSendInfo.put("BET_MODE_", betModeInfo.get(gameCode.name()));
            if (handicapCode.name().equals(hmSendInfo.get("MEMBER_HANDICAP_CODE_"))) {
                handicapAccount.add(hmSendInfo.get("MEMBER_ACCOUNT_").toString());
            }
        }
        hmSendInfos = newInfos;
        return false;
    }
}
