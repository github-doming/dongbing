package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.EncryptTool;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.CrawlerFactory;
import com.ibm.common.utils.http.utils.agent.BaseAgentUtil;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
import com.ibm.follow.servlet.client.ibmc_ha_game_set.service.IbmcHaGameSetService;
import com.ibm.follow.servlet.client.ibmc_ha_info.service.IbmcHaInfoService;
import com.ibm.follow.servlet.client.ibmc_ha_set.service.IbmcHaSetService;
import com.ibm.follow.servlet.client.ibmc_handicap_agent.service.IbmcHandicapAgentService;
import com.ibm.follow.servlet.client.ibmc_handicap_member.service.IbmcHandicapMemberService;
import com.ibm.follow.servlet.client.ibmc_hm_game_set.service.IbmcHmGameSetService;
import com.ibm.follow.servlet.client.ibmc_hm_info.service.IbmcHmInfoService;
import com.ibm.follow.servlet.client.ibmc_hm_set.service.IbmcHmSetService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 客户机切换控制器
 * @Author: null
 * @Date: 2020-02-29 14:53
 * @Version: v1.0
 */
public class ClientReplaceController implements ClientExecutor {
    private IbmcExistHmService existHmService = new IbmcExistHmService();
    private IbmcExistHaService existHaService = new IbmcExistHaService();

    @Override
    public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        String methodType = msgObj.getString("methodType");
        if (StringTool.isEmpty(methodType)) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        IbmMethodEnum method = IbmMethodEnum.valueOf(methodType);
        //返回消息内容
        JSONObject result = new JSONObject();
        result.put("methodType", method.name());
        switch (method) {
            case SEND:
                //发送端信息登出清除
                clearMemberInfo();
                clearAgentInfo();
                //删除所有定时任务处理
                existHmService.removeQuertzInfo();
                break;
            case RECEIVE:
                //接收端信息登录保存
                result.put("hmResultInfos", saveLoginHmInfo(msgObj));
                result.put("haResultInfos", saveLoginHaInfo(msgObj));
                break;
            default:
                break;
        }

        bean.setData(result);
        bean.success();
        return bean;
    }

    public static void clearAgentInfo() throws Exception {
        IbmcExistHaService existHaService=new IbmcExistHaService();
        List<Map<String,Object>> existHaInfos=existHaService.findAll();
        if(ContainerTool.isEmpty(existHaInfos)){
            return ;
        }
        BaseAgentUtil agentUtil;
        List<String> existIds=new ArrayList<>();
        for(Map<String,Object> map:existHaInfos){
            String existHaId =map.get("existHaId").toString();
            existIds.add(existHaId);
            HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(map.get("handicapCode").toString());
            agentUtil = CrawlerFactory.getFactory().getAgentCrawler(handicapCode);
            //清除内存
            CustomerCache.clearUp(existHaId);
            //移除定时器，新加游戏需处理
            QuartzTool.removeHaJob(existHaId, handicapCode);
            //清除客户盘口存在信息，新加盘口需处理
            agentUtil.removeHaInfo(existHaId);
        }
        //清除客户客户机上的信息
        existHaService.removeExistHaInfo(existIds);
    }

    public static void clearMemberInfo() throws Exception {
        IbmcExistHmService existHmService=new IbmcExistHmService();
        List<Map<String,Object>> existHmInfos=existHmService.findAll();
        if(ContainerTool.isEmpty(existHmInfos)){
            return ;
        }
        List<String> existIds=new ArrayList<>();
        for(Map<String,Object> map:existHmInfos){
            String existHmId =map.get("existHmId").toString();
            existIds.add(existHmId);
            HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(map.get("handicapCode").toString());
            //清除内存
            CustomerCache.clearUp(existHmId);
            //移除定时器，新加游戏需处理
            QuartzTool.removeCheckJob(existHmId,handicapCode);
            //清除客户盘口存在信息，新加盘口需处理
			handicapCode.getMemberFactory().removeCrawler(existHmId);
//         memberUtil.removeHmInfo(existHmId);
        }
        //清除客户客户机上的信息
        existHmService.removeExistHmInfo(existIds);
    }

    /**
     * 保存盘口代理登录信息
     * @param msgObj    信息
     * @return
     */
    private List<Map<String,Object>> saveLoginHaInfo(JSONObject msgObj) throws Exception {
        if(!msgObj.containsKey("handicapAgentInfos")){
            return new ArrayList<>();
        }
        JSONArray handicapAgentInfos=msgObj.getJSONArray("handicapAgentInfos");

        List<String> handicapAgentIds=existHaService.save(handicapAgentInfos);
        //获取会员存在ids
        Map<String,Object> existInfos=existHaService.findExitsInfo(handicapAgentIds);
        //添加账号信息
        new IbmcHandicapAgentService().save(handicapAgentInfos,existInfos);

        //添加盘口代理信息
        new IbmcHaInfoService().save(handicapAgentInfos,existInfos);
        //添加盘口代理游戏设置
        IbmcHaGameSetService gameSetService=new IbmcHaGameSetService();
        gameSetService.save(msgObj.getJSONArray("agentGameInfos"),existInfos);
        //添加代理设置信息
        new IbmcHaSetService().save(msgObj.getJSONArray("haSetInfos"),existInfos);

        //处理代理登录信息
        List<Map<String,Object>> haResultInfos=new ArrayList<>();
        Map<String,Object> haRusultMap;
        BaseAgentUtil agentUtil;
        for(int i=0;i<handicapAgentInfos.size();i++){
            haRusultMap=new HashMap<>(3);
            JSONObject haInfo=handicapAgentInfos.getJSONObject(i);

            HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(haInfo.getString("HANDICAP_CODE_"));
            agentUtil = CrawlerFactory.getFactory().getAgentCrawler(handicapCode);

            String agentPassword = EncryptTool.decode(EncryptTool.Type.ASE, haInfo.getString("AGENT_PASSWORD_"));

            //存在会员ids
            String existHaId=existInfos.get(haInfo.get("HANDICAP_AGENT_ID_")).toString();
            agentUtil.accountInfo(existHaId,haInfo.getString("AGENT_ACCOUNT_"),agentPassword,
                    haInfo.getString("HANDICAP_URL_"),haInfo.getString("HANDICAP_CAPTCHA_"));
            //存到内存
            CustomerCache.stateInfo(existHaId, IbmStateEnum.LOGIN);

            QuartzTool.saveCheckHaJob(existHaId, handicapCode);

            haRusultMap.put("HANDICAP_CODE_",haInfo.getString("HANDICAP_CODE_"));
            haRusultMap.put("HANDICAP_AGENT_ID_",haInfo.get("HANDICAP_AGENT_ID_"));
            haRusultMap.put("EXIST_HA_ID_",existHaId);

            haResultInfos.add(haRusultMap);
        }
        //获取投注状态为true的游戏设置信息
        List<Map<String,Object>> gameInfos=gameSetService.findGameInfo(handicapAgentIds);
        //添加抓取job信息
        for(Map<String,Object> gameInfo:gameInfos){
            //  开启定时获取投注信息工作
            HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(gameInfo.get("HANDICAP_CODE_").toString());
            QuartzTool.saveGrabBetJob(gameInfo.get("EXIST_HA_ID_").toString(), handicapCode,
                    GameUtil.Code.valueOf(gameInfo.get("GAME_CODE_").toString()));
        }
        return haResultInfos;
    }

    /**
     * 保存盘口会员登录信息
     * @param msgObj   信息
     * @return
     */
    private List<Map<String,Object>> saveLoginHmInfo(JSONObject msgObj) throws Exception {
        if(!msgObj.containsKey("handicapMemberInfos")){
            return new ArrayList<>();
        }
        JSONArray handicapMemberInfos=msgObj.getJSONArray("handicapMemberInfos");
        //添加存在会员信息
        List<String> handicapMemberIds=existHmService.save(handicapMemberInfos);
        //获取会员存在ids
        Map<String,Object> existInfos=existHmService.findExitsInfo(handicapMemberIds);

        //会员信息
        new IbmcHmInfoService().save(handicapMemberInfos,existInfos);
        //会员设置信息
        new IbmcHmSetService().save(msgObj.getJSONArray("hmSetInfos"),existInfos);
        //会员游戏设置
        new IbmcHmGameSetService().save(msgObj.getJSONArray("memberGameInfos"),existInfos);
        //保存账号信息
        new IbmcHandicapMemberService().save(handicapMemberInfos,existInfos);

        //处理会员登录信息
        List<Map<String,Object>> hmResultInfos=new ArrayList<>();
        Map<String,Object> hmRusultMap;
        for(int i=0;i<handicapMemberInfos.size();i++){
            hmRusultMap=new HashMap<>(3);
            JSONObject hmInfo=handicapMemberInfos.getJSONObject(i);

            HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(hmInfo.getString("HANDICAP_CODE_"));

            String memberPassword = EncryptTool.decode(EncryptTool.Type.ASE, hmInfo.getString("MEMBER_PASSWORD_"));

            //存在会员ids
            String existHmId=existInfos.get(hmInfo.get("HANDICAP_MEMBER_ID_")).toString();
            //添加账号信息到爬虫map
			handicapCode.getMemberFactory()
					.userInfo(existHmId, hmInfo.getString("HANDICAP_URL_"), hmInfo.getString("HANDICAP_CAPTCHA_"),
							hmInfo.getString("MEMBER_ACCOUNT_"), memberPassword);
            //添加定时检验quertz
            QuartzTool.saveCheckHmJob(existHmId, handicapCode);
            //存到内存
            CustomerCache.stateInfo(existHmId, IbmStateEnum.LOGIN);

            hmRusultMap.put("HANDICAP_CODE_",hmInfo.getString("HANDICAP_CODE_"));
            hmRusultMap.put("HANDICAP_MEMBER_ID_",hmInfo.get("HANDICAP_MEMBER_ID_"));
            hmRusultMap.put("EXIST_HM_ID_",existHmId);

            hmResultInfos.add(hmRusultMap);
        }
        return hmResultInfos;
    }



}
