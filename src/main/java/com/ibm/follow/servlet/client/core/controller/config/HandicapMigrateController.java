package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 客户机迁移控制器
 * @Author: null
 * @Date: 2020-02-25 12:29
 * @Version: v1.0
 */
public class HandicapMigrateController implements ClientExecutor {
    private JsonResultBeanPlus bean = new JsonResultBeanPlus();
    private IbmcExistHmService existHmService = new IbmcExistHmService();
    private IbmcExistHaService existHaService = new IbmcExistHaService();

    /**
     * 1，迁移需要一个状态区分是迁入还是迁出
     * 2，迁出的，需要将在客户机的会员和代理都进行登出
     * 3，迁入的，需要将接收到的会员和代理进行登录
     * 4，迁出后的客户机要怎么处理？
     */
    @Override
    public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        String methodType = msgObj.getString("methodType");
        if (StringTool.isEmpty(methodType)) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        IbmTypeEnum customerType = IbmTypeEnum.valueOf(msgObj.getString("customerType"));
        HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(msgObj.getString("handicapCode"));
        IbmMethodEnum method = IbmMethodEnum.valueOf(methodType);
        //返回消息内容
        JSONObject result=new JSONObject();
        result.put("customerType",customerType.name());
        result.put("methodType",method.name());
        switch (method) {
            case SEND:
                //发送端信息登出清除
                clearInfo(customerType, handicapCode);
                break;
            case RECEIVE:
                //接收端信息登录保存
                saveLoginInfo(msgObj, customerType, handicapCode,result);
                break;
            default:
                break;
        }

        bean.setData(result);
        bean.success();
        return bean;
    }

    /**
     * 保存登录信息
     *
     * @param msgObj       消息内容
     * @param customerType 客户类型
     */
    private void saveLoginInfo(JSONObject msgObj, IbmTypeEnum customerType, HandicapUtil.Code handicapCode,JSONObject result) throws Exception {
        switch (customerType) {
            case MEMBER:
                result.put("resultInfos", saveLoginHmInfo(handicapCode,msgObj));
                break;
            case AGENT:
                result.put("resultInfos", saveLoginHaInfo(handicapCode,msgObj));
                break;
            default:
                break;
        }
    }

    /**
     * 保存盘口代理登录信息
     * @param handicapCode      盘口code
     */
    private List<Map<String,Object>> saveLoginHaInfo(HandicapUtil.Code handicapCode,JSONObject msgObj) throws Exception {
        if(!msgObj.containsKey("customerInfos")){
            return new ArrayList<>();
        }
        JSONArray customerInfos = msgObj.getJSONArray("customerInfos");

        List<String> handicapAgentIds=existHaService.save(customerInfos);
        //获取会员存在ids
        Map<String,Object> existInfos=existHaService.findExitsInfo(handicapAgentIds);
        //添加账号信息
        new IbmcHandicapAgentService().save(customerInfos,existInfos);

        //添加盘口代理信息
        new IbmcHaInfoService().save(customerInfos,existInfos);
        //添加盘口代理游戏设置
        IbmcHaGameSetService gameSetService=new IbmcHaGameSetService();
        gameSetService.save(msgObj.getJSONArray("agentGameInfos"),existInfos);
        //添加代理设置信息
        new IbmcHaSetService().save(msgObj.getJSONArray("haSetInfos"),existInfos);

        List<Map<String,Object>> haResultInfos=new ArrayList<>();
        Map<String,Object> haRusultMap;
        BaseAgentUtil agentUtil = CrawlerFactory.getFactory().getAgentCrawler(handicapCode);
        for(int i=0;i<customerInfos.size();i++){
            haRusultMap=new HashMap<>(3);
            JSONObject haInfo=customerInfos.getJSONObject(i);

            String agentPassword = EncryptTool.decode(EncryptTool.Type.ASE, haInfo.getString("AGENT_PASSWORD_"));

            //存在会员ids
            String existHaId=existInfos.get(haInfo.get("HANDICAP_AGENT_ID_")).toString();
            agentUtil.accountInfo(existHaId,haInfo.getString("AGENT_ACCOUNT_"),agentPassword,
                    haInfo.getString("HANDICAP_URL_"),haInfo.getString("HANDICAP_CAPTCHA_"));

            //存到内存
            CustomerCache.stateInfo(existHaId, IbmStateEnum.LOGIN);

            QuartzTool.saveCheckHaJob(existHaId, handicapCode);

            haRusultMap.put("HANDICAP_CODE_",handicapCode);
            haRusultMap.put("HANDICAP_AGENT_ID_",haInfo.get("HANDICAP_AGENT_ID_"));
            haRusultMap.put("EXIST_HA_ID_",existHaId);

            haResultInfos.add(haRusultMap);
        }
        //获取投注状态为true的游戏设置信息
        List<Map<String,Object>> gameInfos=gameSetService.findGameInfo(handicapAgentIds);
        //添加抓取job信息
        for(Map<String,Object> gameInfo:gameInfos){
            //  开启定时获取投注信息工作
            handicapCode = HandicapUtil.Code.valueOf(gameInfo.get("HANDICAP_CODE_").toString());
            QuartzTool.saveGrabBetJob(gameInfo.get("EXIST_HA_ID_").toString(), handicapCode,
                    GameUtil.Code.valueOf(gameInfo.get("GAME_CODE_").toString()));
        }
        return haResultInfos;
    }

    /**
     * 保存盘口会员登录信息
     * @param handicapCode      盘口code
     */
    private List<Map<String,Object>> saveLoginHmInfo(HandicapUtil.Code handicapCode,JSONObject msgObj) throws Exception {
        if(!msgObj.containsKey("customerInfos")){
            return new ArrayList<>();
        }
        JSONArray customerInfos = msgObj.getJSONArray("customerInfos");
        //添加存在会员信息
        List<String> handicapMemberIds=existHmService.save(customerInfos);
        //获取会员存在ids
        Map<String,Object> existInfos=existHmService.findExitsInfo(handicapMemberIds);
        //会员信息
        new IbmcHmInfoService().save(customerInfos,existInfos);
        //会员设置信息
        new IbmcHmSetService().save(msgObj.getJSONArray("hmSetInfos"),existInfos);
        //会员游戏设置
        new IbmcHmGameSetService().save(msgObj.getJSONArray("memberGameInfos"),existInfos);
        //保存账号信息
        new IbmcHandicapMemberService().save(customerInfos,existInfos);

        //处理会员登录信息
        List<Map<String,Object>> hmResultInfos=new ArrayList<>();
        Map<String,Object> hmRusultMap;
        for(int i=0;i<customerInfos.size();i++){
            hmRusultMap=new HashMap<>(3);
            JSONObject hmInfo=customerInfos.getJSONObject(i);

            String memberPassword = EncryptTool.decode(EncryptTool.Type.ASE, hmInfo.getString("MEMBER_PASSWORD_"));

            //存在会员ids
            String existHmId=existInfos.get(hmInfo.get("HANDICAP_MEMBER_ID_")).toString();
            //添加账号信息到爬虫map
			handicapCode.getMemberFactory()
					.userInfo(existHmId, hmInfo.getString("HANDICAP_URL_"), hmInfo.getString("HANDICAP_CAPTCHA_"),
							hmInfo.getString("MEMBER_ACCOUNT_"), memberPassword);
            QuartzTool.saveCheckHmJob(existHmId, handicapCode);
            //存到内存
            CustomerCache.stateInfo(existHmId, IbmStateEnum.LOGIN);
            hmRusultMap.put("HANDICAP_CODE_",handicapCode);
            hmRusultMap.put("HANDICAP_MEMBER_ID_",hmInfo.get("HANDICAP_MEMBER_ID_"));
            hmRusultMap.put("EXIST_HM_ID_",existHmId);

            hmResultInfos.add(hmRusultMap);
        }
        return hmResultInfos;
    }

    /**
     * 清除发送端信息
     *
     * @param customerType 客户类型
     */
    private void clearInfo(IbmTypeEnum customerType, HandicapUtil.Code handicapCode) throws SQLException {
        switch (customerType) {
            case MEMBER:
                List<String> existHmIds=existHmService.findByHandicapCode(handicapCode);
                clearHmInfo(existHmIds,handicapCode);
                break;
            case AGENT:
                List<String> existHaIds=existHaService.findByHandicapCode(handicapCode);
                clearHaInfo(existHaIds,handicapCode);
                break;
            default:
                break;
        }
    }

    /**
     * 清除盘口代理信息
     *
     * @param handicapCode 盘口code
     */
    public static void clearHaInfo(List<String> existHaIds,HandicapUtil.Code handicapCode) throws SQLException {
        if(ContainerTool.isEmpty(existHaIds)){
            return;
        }
        IbmcExistHaService existHaService=new IbmcExistHaService();
        IbmcExistHmService existHmService = new IbmcExistHmService();
        BaseAgentUtil agentUtil = CrawlerFactory.getFactory().getAgentCrawler(handicapCode);
        for(String existHaId:existHaIds){
            //移除定时器，新加游戏需处理
            QuartzTool.removeHaJob(existHaId, handicapCode);
            //清除内存
            CustomerCache.clearUp(existHaId);
            //清除客户盘口存在信息，新加盘口需处理
            agentUtil.removeHaInfo(existHaId);
            //定时任务处理
            existHmService.removeQuertzInfo(existHaId);
        }
        //清除客户客户机上的信息
        existHaService.removeExistHaInfo(existHaIds);
    }

    /**
     * 清除盘口会员信息
     *
     * @param handicapCode 盘口code
     */
    public static void clearHmInfo(List<String> existHmIds, HandicapUtil.Code handicapCode) throws SQLException {
        if(ContainerTool.isEmpty(existHmIds)){
            return;
        }
        IbmcExistHmService existHmService = new IbmcExistHmService();
        for(String existHmId:existHmIds){
            //清除内存
            CustomerCache.clearUp(existHmId);
            //移除定时器，新加游戏需处理
            QuartzTool.removeCheckJob(existHmId, handicapCode);
            //清除客户盘口存在信息，新加盘口需处理
			handicapCode.getMemberFactory().removeCrawler(existHmId);
            //定时任务处理
            existHmService.removeQuertzInfo(existHmId);
        }
        //清除客户客户机上的信息
        existHmService.removeExistHmInfo(existHmIds);

    }
}
