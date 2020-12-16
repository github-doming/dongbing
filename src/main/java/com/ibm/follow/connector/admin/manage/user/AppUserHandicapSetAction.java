package com.ibm.follow.connector.admin.manage.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.controller.process.LoginHmController;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHaController;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHmController;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_exp_user.entity.IbmExpUser;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.service.IbmHandicapAgentMemberService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @Description: 修改盘口信息
 * @Author: null
 * @Date: 2020-03-18 13:57
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/handicapSet")
public class AppUserHandicapSetAction
        extends CommAdminAction {
    private String appUserId;
    private String avaiableGame;
    private String agentUsable;
    private String memberUsable;
    private int onlineHaNumberMax, onlineHmNumberMax;

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        if (valiParameters()) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }

        if (onlineHmNumberMax <= 0 || onlineHmNumberMax >= Byte.MAX_VALUE || onlineHaNumberMax <= 0 || onlineHaNumberMax >= Byte.MAX_VALUE) {
            bean.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return super.returnJson(bean);
        }

        try {
            //修改最大在线会员和代理数
            IbmExpUserService expUserService = new IbmExpUserService();
            IbmExpUser expUser = expUserService.findByUserId(appUserId);
            //修改可用游戏
            updateAvailableGame(expUser.getAvailableGame());

            //修改可用代理盘口
            Map<Object, Object> agentInfo = ContainerTool
                    .list2Map(JSON.parseArray(agentUsable), "HANDICAP_CODE_", "ONLINE_NUMBER_MAX_");
            checkMapValue(agentInfo);
            updateAgentHandicap(agentInfo);

            //修改可用会员盘口
            Map<Object, Object> memberInfo = ContainerTool
                    .list2Map(JSON.parseArray(memberUsable), "HANDICAP_CODE_", "ONLINE_NUMBER_MAX_");
            checkMapValue(memberInfo);
            updateMemberHandicap(memberInfo);

            expUser.setAvailableGame(avaiableGame);
            expUser.setAgentAvailableHandicap(StringUtils.join(agentInfo.keySet(), ","));
            expUser.setAgentOnlineMax(NumberTool.getInteger(onlineHaNumberMax));
            expUser.setMemberAvailableHandicap(StringUtils.join(memberInfo.keySet(), ","));
            expUser.setMemberOnlineMax(NumberTool.getInteger(onlineHmNumberMax));
            expUser.setUpdateUser(adminUser.getUserName());
            expUser.setUpdateTime(new Date());
            expUser.setUpdateTimeLong(System.currentTimeMillis());
            expUserService.update(expUser);

            bean.success();
        } catch (Exception e) {
            log.error("修改用户盘口失败，", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }

    private void checkMapValue(Map<Object, Object> map) {
        Iterator<Map.Entry<Object, Object>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Object, Object> entry = entries.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (NumberTool.getInteger(value) > Byte.MAX_VALUE) {
                map.put(key, Byte.MAX_VALUE);
            }
        }
    }

    /**
     * 修改会员盘口
     */
    private void updateMemberHandicap(Map<Object, Object> memberInfo) throws Exception {
        IbmHandicapService handicapService = new IbmHandicapService();

        //获取用户拥有的所有会员盘口
        IbmHmUserService hmUserService = new IbmHmUserService();
        List<String> hmHandicapCodes = hmUserService.findHandicapCodeByUserId(appUserId);

        //新会员盘口codes-原来盘口codes-需要新加的盘口
        Set<Object> saveHmCodes = new HashSet<>(ContainerTool.getDifferent(memberInfo.keySet(), hmHandicapCodes));

        //原来盘口codes-新会员盘口codes-需要删除的盘口
        Set<Object> delHmCodes = new HashSet<>(ContainerTool.getDifferent(hmHandicapCodes, memberInfo.keySet()));

        Set<String> setStr;
        List<Map<String, Object>> handicaps;
        if (ContainerTool.notEmpty(saveHmCodes)) {
            setStr = new HashSet<>();
            for (Object code : saveHmCodes) {
                setStr.add(code.toString());
            }
            handicaps = handicapService.findHandicap(setStr, IbmTypeEnum.MEMBER);
            hmUserService.save(handicaps, appUserId, memberInfo);
        }

        if (ContainerTool.notEmpty(delHmCodes)) {
            //登出盘口会员
            handicaps = hmUserService.findHandicap(appUserId, delHmCodes);
            LogoutHmController logoutHmController = new LogoutHmController();
            for (Map<String, Object> handicap : handicaps) {
                if (StringTool.notEmpty(handicap.get("ONLINE_MEMBERS_IDS_"))) {
                    String[] handicapMemberIds = handicap.get("ONLINE_MEMBERS_IDS_").toString().split(",");

                    for (String handicapMemberId : handicapMemberIds) {
                        logoutHmController.execute(handicapMemberId);
                    }
                }
                hmUserService.updateByAppUserId(appUserId, handicap.get("HANDICAP_ID_").toString());
            }
        }

        //修改最大在线数
        memberInfo.keySet().retainAll(hmHandicapCodes);
        if (ContainerTool.notEmpty(memberInfo.keySet())) {
            for (Object handicapCode : memberInfo.keySet()) {
                hmUserService.updateOnlineNumMax(appUserId, handicapCode, memberInfo.get(handicapCode));
            }
        }


    }

    /**
     * 修改代理盘口
     */
    private void updateAgentHandicap(Map<Object, Object> agentInfo) throws Exception {
        IbmHandicapService handicapService = new IbmHandicapService();

        //获取用户拥有的所有代理盘口
        IbmHaUserService haUserService = new IbmHaUserService();
        List<String> haHandicapCodes = haUserService.findHandicapCodeByUserId(appUserId);

        //新代理盘口codes-原来盘口codes-需要新加的盘口
        Set<Object> saveHaCodes = new HashSet<>(ContainerTool.getDifferent(agentInfo.keySet(), haHandicapCodes));

        //原来盘口codes-新代理盘口codes-需要删除的盘口
        Set<Object> delHaCodes = new HashSet<>(ContainerTool.getDifferent(haHandicapCodes, agentInfo.keySet()));

        List<Map<String, Object>> handicaps;
        if (ContainerTool.notEmpty(saveHaCodes)) {
            Set<String> setStr = new HashSet<>();
            for (Object code : saveHaCodes) {
                setStr.add(code.toString());
            }
            handicaps = handicapService.findHandicap(setStr, IbmTypeEnum.AGENT);
            haUserService.save(handicaps, appUserId, agentInfo);
        }

        if (ContainerTool.notEmpty(delHaCodes)) {
            //登出盘口代理
            handicaps = haUserService.findHandicap(appUserId, delHaCodes);
            LogoutHaController logoutHaController = new LogoutHaController();
            for (Map<String, Object> handicap : handicaps) {
                if (StringTool.notEmpty(handicap.get("ONLINE_AGENTS_IDS_"))) {
                    String[] handicapAgentIds = handicap.get("ONLINE_AGENTS_IDS_").toString().split(",");
                    for (String handicapAgentId : handicapAgentIds) {
                        logoutHaController.execute(handicapAgentId);
                    }
                }
                haUserService.delByAppUserId(appUserId, handicap.get("HANDICAP_ID_").toString());
            }
        }

        //修改最大在线数
        agentInfo.keySet().retainAll(haHandicapCodes);
        if (ContainerTool.notEmpty(agentInfo.keySet())) {
            for (Object handicapCode : agentInfo.keySet()) {
                haUserService.updateOnlineNumMax(appUserId, handicapCode, agentInfo.get(handicapCode));
            }
        }

    }

    /**
     * 修改可用游戏
     */
    private void updateAvailableGame(String oldGames) throws Exception {
        List<String> usingGame = Arrays.asList(oldGames.split(","));
        List<String> updateGame = Arrays.asList(avaiableGame.split(","));

        //新游戏codes-原来游戏codes-需要新加的游戏
        Set<Object> saveGameCodes = new HashSet<>(ContainerTool.getDifferent(updateGame, usingGame));

        //原来游戏codes-新游戏codes-需要删除的游戏
        Set<Object> delGameCodes = new HashSet<>(ContainerTool.getDifferent(usingGame, updateGame));

        //获取会员信息
        List<Map<String, Object>> memberInfos = new IbmHandicapMemberService().findMemberByUserId(appUserId);
        //获取代理信息
        List<Map<String, Object>> agentInfos = new IbmHandicapAgentService().findAgentByUserId(appUserId);

        IbmHandicapGameService handicapGameService = new IbmHandicapGameService();
        IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();
        IbmHaGameSetService haGameSetService = new IbmHaGameSetService();

        List<String> gameIds = new ArrayList<>();
        if (ContainerTool.notEmpty(saveGameCodes)) {
            Map<String, Object> initMemberGameSet = hmGameSetService.findInitInfo();
            Map<String, Object> initAgentGameSet = haGameSetService.findInitInfo();
            for (Object code : saveGameCodes) {
                gameIds.add(GameUtil.id(code.toString()));
            }
            //新增会员游戏设置
            for (Map<String, Object> memberInfo : memberInfos) {
                //获取存在的游戏id
                List<String> memberGameIds = handicapGameService
                        .findHandicapGameInfo(memberInfo.get("HANDICAP_ID_").toString(), gameIds);
                hmGameSetService.save(appUserId, memberInfo, memberGameIds, initMemberGameSet);
            }
            //新增代理游戏设置
            for (Map<String, Object> agentInfo : agentInfos) {
                List<String> agentGameIds = handicapGameService
                        .findHandicapGameInfo(agentInfo.get("HANDICAP_ID_").toString(), gameIds);
                haGameSetService.save(appUserId, agentInfo, agentGameIds, initAgentGameSet);
            }
        }
		IbmClientHmService clientHmService=new IbmClientHmService();
        JSONObject content = new JSONObject();
        if (ContainerTool.notEmpty(delGameCodes)) {
            gameIds.clear();
            for (Object code : delGameCodes) {
                gameIds.add(GameUtil.id(code.toString()));
            }
            content.put("SET_ITEM_", IbmMethodEnum.SET_BET_STATE.name());
            content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());
            content.put("BET_STATE_", IbmTypeEnum.FALSE.name());
            //删除会员游戏设置
			String desc= this.getClass().getName().concat("，修改投注状态");
			for (Map<String, Object> memberInfo : memberInfos) {
                if (IbmStateEnum.LOGIN.name().equals(memberInfo.get("STATE_"))) {
                    List<Map<String, Object>> hmGameSet = hmGameSetService.findBetInfo(memberInfo, gameIds);
                    //发送消息，停止该游戏投注
                    for (Map<String, Object> map : hmGameSet) {
                        content.put("GAME_CODE_", GameUtil.code(map.get("GAME_ID_")).name());
						EventThreadDefine.sendClientConfig(content,map.get("HANDICAP_MEMBER_ID_").toString(),IbmTypeEnum.MEMBER,desc);
                    }
                }
                hmGameSetService.delGameSet(memberInfo, gameIds);
            }
            content.clear();
            content.put("BET_STATE_", IbmTypeEnum.FALSE.name());
            content.put("SET_ITEM_", IbmMethodEnum.SET_BET_STATE.name());
            content.put("METHOD_", IbmMethodEnum.AGENT_SET.name());
            //删除代理游戏设置
			desc= this.getClass().getName().concat("，跟投游戏跟投状态设置");
            for (Map<String, Object> agenetInfo : agentInfos) {
                if (IbmStateEnum.LOGIN.name().equals(agenetInfo.get("STATE_"))) {
                    List<Map<String, Object>> haGameSet = haGameSetService.findBetInfo(agenetInfo, gameIds);
                    //发送消息，停止该游戏投注
                    for (Map<String, Object> map : haGameSet) {
                        content.put("GAME_CODE_", GameUtil.code(map.get("GAME_ID_")).name());
						EventThreadDefine.sendClientConfig(content,map.get("HANDICAP_AGENT_ID_").toString(),IbmTypeEnum.AGENT,desc);
                    }
                }
                haGameSetService.delGameSet(agenetInfo, gameIds);
            }
        }
        //重新发送游戏信息
        if (ContainerTool.notEmpty(saveGameCodes) || ContainerTool.notEmpty(delGameCodes)) {
            content.clear();
            List<Map<String, Object>> gameInfo;
            content.put("SET_ITEM_", IbmMethodEnum.SET_GAME_INFO.name());
            content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());
            for (Map<String, Object> memberInfo : memberInfos) {
                if (IbmStateEnum.LOGIN.name().equals(memberInfo.get("STATE_"))) {
                    gameInfo = hmGameSetService.findByHmId(memberInfo.get("IBM_HANDICAP_MEMBER_ID_").toString());
                    content.put("GAME_INFO_", gameInfo);

					EventThreadDefine.sendClientConfig(content,memberInfo.get("IBM_HANDICAP_MEMBER_ID_").toString(),IbmTypeEnum.MEMBER,
							this.getClass().getName().concat("发送会员游戏设置信息"));
                }
            }
            content.clear();
            content.put("METHOD_", IbmMethodEnum.AGENT_SET.name());
            content.put("SET_ITEM_", IbmMethodEnum.SET_GAME_INFO.name());
            for (Map<String, Object> agenetInfo : agentInfos) {
                if (IbmStateEnum.LOGIN.name().equals(agenetInfo.get("STATE_"))) {
                    gameInfo = haGameSetService.findByHaId(agenetInfo.get("IBM_HANDICAP_AGENT_ID_").toString());

                    content.put("GAME_INFO_", gameInfo);
					EventThreadDefine.sendClientConfig(content,agenetInfo.get("IBM_HANDICAP_AGENT_ID_").toString(),IbmTypeEnum.AGENT,
							this.getClass().getName().concat("发送代理游戏设置信息"));
                }
            }
            content.clear();
            content.put("SET_ITEM_", IbmMethodEnum.SET_BIND.name());
            content.put("METHOD_", IbmMethodEnum.MANAGE_SET.name());

            Map<String, Object> memberInfo;
            IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
            //重新发送已有绑定信息
            IbmHandicapAgentMemberService handicapAgentMemberService = new IbmHandicapAgentMemberService();
            Map<String, List<String>> map = handicapAgentMemberService.mapHmIdAndHaId(appUserId);
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                //获取绑定信息
                Map<String, Object> bindInfo = clientHmService.findBindInfo(entry.getKey());
                if (ContainerTool.isEmpty(bindInfo)) {
                    continue;
                }
                content.putAll(bindInfo);
                //会员会员信息
                memberInfo = handicapMemberService.findMemberAccountInfo(entry.getKey());
                //投注模式
                List<Map<String, Object>> betModeInfos = new IbmHmGameSetService().listBetModeInfo(entry.getKey());

                content.put("MEMBER_HANDICAP_CODE_", memberInfo.get("HANDICAP_CODE_"));
                content.put("MEMBER_ACCOUNT_", memberInfo.get("MEMBER_ACCOUNT_"));
                for (String handicapAgentId : entry.getValue()) {

                    LoginHmController.bindUserAgent(handicapAgentId, content, betModeInfos);
                }
            }
        }
    }

    private boolean valiParameters() {

        appUserId = dataMap.getOrDefault("appUserId", "").toString();
        //代理可用盘口
        agentUsable = dataMap.getOrDefault("agentUsable", "").toString();
        //会员可用盘口
        memberUsable = dataMap.getOrDefault("memberUsable", "").toString();
        //可用游戏avaiableGame
        avaiableGame = dataMap.getOrDefault("avaiableGame", "").toString();

        //会员最大登录数量
        onlineHmNumberMax = NumberTool.getInteger(dataMap, "onlineHmNumberMax", 10);
        //代理最大登录数量
        onlineHaNumberMax = NumberTool.getInteger(dataMap, "onlineHaNumberMax", 10);
        return StringTool
                .isEmpty(appUserId, onlineHaNumberMax, onlineHmNumberMax, agentUsable, memberUsable, avaiableGame)
                ;
    }
}
