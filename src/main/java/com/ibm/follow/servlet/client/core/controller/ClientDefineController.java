package com.ibm.follow.servlet.client.core.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.handicap.crawler.entity.MemberUser;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.CrawlerFactory;
import com.ibm.common.utils.http.utils.agent.BaseAgentUtil;
import com.ibm.follow.servlet.client.ibmc_agent_member_info.entity.IbmcAgentMemberInfo;
import com.ibm.follow.servlet.client.ibmc_agent_member_info.service.IbmcAgentMemberInfoService;
import com.ibm.follow.servlet.client.ibmc_config.service.IbmcConfigService;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
import com.ibm.follow.servlet.client.ibmc_ha_game_set.entity.IbmcHaGameSet;
import com.ibm.follow.servlet.client.ibmc_ha_game_set.service.IbmcHaGameSetService;
import com.ibm.follow.servlet.client.ibmc_ha_info.entity.IbmcHaInfo;
import com.ibm.follow.servlet.client.ibmc_ha_info.service.IbmcHaInfoService;
import com.ibm.follow.servlet.client.ibmc_ha_set.entity.IbmcHaSet;
import com.ibm.follow.servlet.client.ibmc_ha_set.service.IbmcHaSetService;
import com.ibm.follow.servlet.client.ibmc_handicap_agent.entity.IbmcHandicapAgent;
import com.ibm.follow.servlet.client.ibmc_handicap_agent.service.IbmcHandicapAgentService;
import com.ibm.follow.servlet.client.ibmc_handicap_member.entity.IbmcHandicapMember;
import com.ibm.follow.servlet.client.ibmc_handicap_member.service.IbmcHandicapMemberService;
import com.ibm.follow.servlet.client.ibmc_hm_game_set.entity.IbmcHmGameSet;
import com.ibm.follow.servlet.client.ibmc_hm_game_set.service.IbmcHmGameSetService;
import com.ibm.follow.servlet.client.ibmc_hm_info.entity.IbmcHmInfo;
import com.ibm.follow.servlet.client.ibmc_hm_info.service.IbmcHmInfoService;
import com.ibm.follow.servlet.client.ibmc_hm_set.entity.IbmcHmSet;
import com.ibm.follow.servlet.client.ibmc_hm_set.service.IbmcHmSetService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.quartz.SchedulerException;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
/**
 * @Description: 客户端控制器公用方法
 * @Author: Dongming
 * @Date: 2019-09-02 15:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientDefineController {
	private Date nowTime;
	private String existId;
	public ClientDefineController(final String existId,final Date nowTime) {
		this.existId = existId;
		this.nowTime = nowTime;
	}
	/**
	 * 获取容量信息
	 *
	 * @param capacity 容量信息存储map
	 * @return 限容结果
	 */
	public static boolean getCapacity(Map<String, Object> capacity, String handicapCode) throws SQLException {
		IbmcExistHmService existHmService = new IbmcExistHmService();
		IbmcExistHaService existHaService = new IbmcExistHaService();
		capacity.clear();
		IbmcConfigService configService = new IbmcConfigService();
		Map<Object, Object> capacityMax = configService.findMaxCapacity(handicapCode);

		//获取已存在的盘口会员数量
		Map<String, Object> exitsMemberCount = existHmService.findExitsCount(handicapCode);

		//获取已存在的盘口代理数量
		Map<String, Object> exitsAgentCount = existHaService.findExitsCount(handicapCode);
		//会员使用容量
		int memberExistCount = Integer.parseInt(exitsMemberCount.get("exitsCount").toString());
		//代理使用容量
		int agentExistCount = Integer.parseInt(exitsAgentCount.get("exitsCount").toString());
		//盘口会员使用容量
		int capacityHm = Integer.parseInt(exitsMemberCount.get("capacityHm").toString());
		//盘口代理使用容量
		int capacityHa = Integer.parseInt(exitsAgentCount.get("capacityHa").toString());
		//客户端使用容量=会员使用容量+代理使用容量
		int exitsCount = memberExistCount + agentExistCount;
		//盘口使用容量=盘口会员使用容量+盘口代理使用容量
		int capacityHandicap = capacityHm + capacityHa;

		//客户端最大容量
		capacity.put("capacityMax", capacityMax.get("CAPACITY_MAX"));
		//客户端使用容量
		capacity.put("exitsCount", exitsCount);
		//盘口最大容量
		capacity.put("capacityHandicapMax", capacityMax.get(handicapCode.concat("_CAPACITY_MAX")));
		//盘口使用容量
		capacity.put("capacityHandicap", capacityHandicap);
		//会员最大容量
		capacity.put("capacityHmMax", capacityMax.get(handicapCode.concat("_MEMBER_CAPACITY_MAX")));
		//会员使用容量
		capacity.put("capacityHm", capacityHm);
		//代理最大容量
		capacity.put("capacityHaMax", capacityMax.get(handicapCode.concat("_AGENT_CAPACITY_MAX")));
		//代理使用容量
		capacity.put("capacityHa", capacityHa);

		//服务器容量已达上限
		return NumberTool.compareInteger(exitsCount, capacityMax.get("CAPACITY_MAX")) < 0
				&& NumberTool.compareInteger(capacityHandicap, capacityMax.get(handicapCode + "_CAPACITY_MAX")) < 0;
	}
	/**
	 * 盘口会员登录
	 *
	 * @param existHmId       已存在盘口会员id
	 * @param handicapCode    盘口code
	 * @param memberAccount   会员账号
	 * @param memberPassword  会员密码
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @return 会员登录信息
	 */
	public static JsonResultBeanPlus memberLogin(String existHmId, HandicapUtil.Code handicapCode, String memberAccount,
			String memberPassword, String handicapUrl, String handicapCaptcha) {
		return handicapCode.getMemberFactory().login(existHmId,handicapUrl,handicapCaptcha,memberAccount,memberPassword);
	}
	/**
	 * 盘口代理登录
	 *
	 * @param existHaId       已存在盘口代理id
	 * @param handicapCode    盘口code
	 * @param agentAccount    代理账号
	 * @param agentPassword   代理密码
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @return 代理登录信息
	 */
	public static JsonResultBeanPlus agentLogin(String existHaId, HandicapUtil.Code handicapCode, String agentAccount,
			String agentPassword, String handicapUrl, String handicapCaptcha) {
		BaseAgentUtil agentUtil = CrawlerFactory.getFactory().getAgentCrawler(handicapCode);
		return agentUtil.login(existHaId, agentAccount, agentPassword, handicapUrl, handicapCaptcha);
	}
	/**
	 * 盘口会员检验登录
	 *
	 * @param handicapCode    盘口code
	 * @param memberAccount   会员账号
	 * @param memberPassword  会员密码
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @return 检验登录结果
	 */
	public static JsonResultBeanPlus memberVailLogin(HandicapUtil.Code handicapCode, String memberAccount,
		String memberPassword, String handicapUrl, String handicapCaptcha) {
		return handicapCode.getMemberFactory().valiLogin(handicapUrl, handicapCaptcha, memberAccount, memberPassword);

	}
	/**
	 * 盘口代理检验登录
	 *
	 * @param handicapCode    盘口code
	 * @param agentAccount    代理账号
	 * @param agentPassword   代理密码
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @return 校验结果
	 */
	public static JsonResultBeanPlus agentVailLogin(HandicapUtil.Code handicapCode, String agentAccount,
			String agentPassword, String handicapUrl, String handicapCaptcha) {
		BaseAgentUtil agentUtil = CrawlerFactory.getFactory().getAgentCrawler(handicapCode);
		return agentUtil.valiLogin(handicapUrl, handicapCaptcha, agentAccount, agentPassword);
	}

    /**
     * 获取会员用户信息
     *
     * @param existHmId    已存在盘口会员id
     * @param handicapCode 盘口code
     * @return 用户信息
     */
    public static MemberUser getMemberInfo(String existHmId, HandicapUtil.Code handicapCode, boolean flag) {
		return handicapCode.getMemberFactory().memberOption(existHmId).userInfo(flag);
//        BaseMemberUtil memberUtil = CrawlerFactory.getFactory().getMemberCrawler(handicapCode);
//        MemberUserInfo memberUserInfo=memberUtil.getUserInfo(existHmId, flag);
//        return memberUserInfo.getUserInfo();
    }
	/**
	 * 获取代理用户信息
	 *
	 * @param existHaId    已存在盘口代理id
	 * @param handicapCode 盘口code
	 * @return 用户信息
	 */
	private static String getAgentInfo(String existHaId, HandicapUtil.Code handicapCode) {
		BaseAgentUtil agentUtil = CrawlerFactory.getFactory().getAgentCrawler(handicapCode);
		JSONArray memberList = agentUtil.getMemberList(existHaId);
		if (ContainerTool.isEmpty(memberList)) {
			return null;
		}
		return memberList.toString();
	}
	/**
	 * 获取用户信息
	 *
	 * @param existId      已存在用户id
	 * @param customerType 客户类型
	 * @param handicapCode 盘口code
	 * @return 用户信息
	 */
	public static Object getUserInfo(String existId, IbmTypeEnum customerType, HandicapUtil.Code handicapCode) {
		switch (customerType) {
			case MEMBER:
				return getMemberInfo(existId, handicapCode, true);
			case AGENT:
				return getAgentInfo(existId, handicapCode);
			default:
				throw new RuntimeException("错误的角色类型".concat(customerType.name()));
		}
	}

	/**
	 * 添加客户端客户信息
	 *
	 * @param existId      已存在id
	 * @param userInfo     用户信息
	 * @param customerType 客户类型
	 */
	public static void saveClientInfo(String existId, Object userInfo, IbmTypeEnum customerType) throws Exception {
		switch (customerType) {
			case MEMBER:
				MemberUser info;
				IbmcHmInfoService hmInfoService = new IbmcHmInfoService();
				IbmcHmInfo hmInfo = hmInfoService.findExist(existId);
				if (hmInfo == null) {
					hmInfo = new IbmcHmInfo();
					hmInfo.setExistHmId(existId);
					if (userInfo != null) {
						info = (MemberUser) userInfo;
						hmInfo.setMemberAccount(info.memberAccount());
						hmInfo.setCreditQuotaT(NumberTool.longValueT(info.creditQuota()));
						hmInfo.setUsedAmountT(NumberTool.longValueT(info.usedAmount()));
						hmInfo.setProfitAmountT(NumberTool.longValueT(info.profitAmount()));
                    }
					hmInfo.setCreateTime(new Date());
					hmInfo.setCreateTimeLong(System.currentTimeMillis());
					hmInfo.setUpdateTime(new Date());
					hmInfo.setUpdateTimeLong(System.currentTimeMillis());
					hmInfo.setState(IbmStateEnum.LOGIN.name());
					hmInfoService.save(hmInfo);
				} else {
                    if (ContainerTool.notEmpty(userInfo)) {
						info = (MemberUser) userInfo;
						hmInfo.setMemberAccount(info.memberAccount());
						hmInfo.setCreditQuotaT(NumberTool.longValueT(info.creditQuota()));
						hmInfo.setUsedAmountT(NumberTool.longValueT(info.usedAmount()));
						hmInfo.setProfitAmountT(NumberTool.longValueT(info.profitAmount()));
                    }
					hmInfo.setUpdateTime(new Date());
					hmInfo.setUpdateTimeLong(System.currentTimeMillis());
					hmInfo.setState(IbmStateEnum.LOGIN.name());
					hmInfoService.update(hmInfo);
				}
				break;
			case AGENT:
				IbmcHaInfoService haInfoService = new IbmcHaInfoService();
				IbmcHaInfo haInfo = haInfoService.findExist(existId);
				if (haInfo == null) {
					haInfo = new IbmcHaInfo();
					haInfo.setExistHaId(existId);
					haInfo.setMemberListInfo(userInfo);
					haInfo.setCreateTime(new Date());
					haInfo.setCreateTimeLong(System.currentTimeMillis());
					haInfo.setUpdateTime(new Date());
					haInfo.setUpdateTimeLong(System.currentTimeMillis());
					haInfo.setState(IbmStateEnum.LOGIN.name());
					haInfoService.save(haInfo);
				} else {
					haInfo.setMemberListInfo(userInfo);
					haInfo.setUpdateTime(new Date());
					haInfo.setUpdateTimeLong(System.currentTimeMillis());
					haInfo.setState(IbmStateEnum.LOGIN.name());
					haInfoService.update(haInfo);
				}
				break;
			default:
				throw new RuntimeException("错误的角色类型".concat(customerType.name()));
		}
	}

	//region 保存信息
	/**
	 * 添加盘口代理信息
	 *
	 * @param msgObj    消息内容
	 */
	public void saveHandicapAgent(JSONObject msgObj) throws Exception {
		IbmcHandicapAgentService handicapAgentService = new IbmcHandicapAgentService();
		IbmcHandicapAgent handicapAgent = handicapAgentService.findExist(existId);
		if (handicapAgent == null) {
			handicapAgent = new IbmcHandicapAgent();
			handicapAgent.setExistHaId(existId);
			handicapAgent.setHandicapAgentId(msgObj.getString("HANDICAP_AGENT_ID_"));
			handicapAgent.setAgentAccount(msgObj.getString("AGENT_ACCOUNT_"));
			handicapAgent.setAgentPassword(msgObj.getString("AGENT_PASSWORD_"));
			handicapAgent.setHandicapUrl(msgObj.getString("HANDICAP_URL_"));
			handicapAgent.setHandicapCaptcha(msgObj.getString("HANDICAP_CAPTCHA_"));
			handicapAgent.setCreateTime(nowTime);
			handicapAgent.setCreateTimeLong(System.currentTimeMillis());
			handicapAgent.setUpdateTime(nowTime);
			handicapAgent.setUpdateTimeLong(System.currentTimeMillis());
			handicapAgent.setState(IbmStateEnum.OPEN.name());
			handicapAgentService.save(handicapAgent);
		} else {
			handicapAgent.setAgentAccount(msgObj.getString("AGENT_ACCOUNT_"));
			handicapAgent.setAgentPassword(msgObj.getString("AGENT_PASSWORD_"));
			handicapAgent.setHandicapUrl(msgObj.getString("HANDICAP_URL_"));
			handicapAgent.setHandicapCaptcha(msgObj.getString("HANDICAP_CAPTCHA_"));
			handicapAgent.setUpdateTime(nowTime);
			handicapAgent.setUpdateTimeLong(System.currentTimeMillis());
			handicapAgent.setState(IbmStateEnum.OPEN.name());
			handicapAgentService.update(handicapAgent);
		}
	}
	/**
	 * 添加盘口会员信息
	 *
	 * @param msgObj    消息内容
	 */
	public void saveHandicapMember(JSONObject msgObj) throws Exception {
		IbmcHandicapMemberService handicapMemberService = new IbmcHandicapMemberService();
		IbmcHandicapMember handicapMember = handicapMemberService.findExist(existId);
		if (handicapMember == null) {
			handicapMember = new IbmcHandicapMember();
			handicapMember.setExistHmId(existId);
			handicapMember.setHandicapMemberId(msgObj.getString("HANDICAP_MEMBER_ID_"));
			handicapMember.setMemberAccount(msgObj.getString("MEMBER_ACCOUNT_"));
			handicapMember.setMemberPassword(msgObj.getString("MEMBER_PASSWORD_"));
			handicapMember.setHandicapUrl(msgObj.getString("HANDICAP_URL_"));
			handicapMember.setHandicapCaptcha(msgObj.getString("HANDICAP_CAPTCHA_"));
			handicapMember.setCreateTime(new Date());
			handicapMember.setCreateTimeLong(System.currentTimeMillis());
			handicapMember.setUpdateTime(new Date());
			handicapMember.setUpdateTimeLong(System.currentTimeMillis());
			handicapMember.setState(IbmStateEnum.OPEN.name());
			handicapMemberService.save(handicapMember);
		} else {
			handicapMember.setMemberAccount(msgObj.getString("MEMBER_ACCOUNT_"));
			handicapMember.setMemberPassword(msgObj.getString("MEMBER_PASSWORD_"));
			handicapMember.setHandicapUrl(msgObj.getString("HANDICAP_URL_"));
			handicapMember.setHandicapCaptcha(msgObj.getString("HANDICAP_CAPTCHA_"));
			handicapMember.setUpdateTime(new Date());
			handicapMember.setUpdateTimeLong(System.currentTimeMillis());
			handicapMember.setState(IbmStateEnum.OPEN.name());
			handicapMemberService.update(handicapMember);
		}
	}

	/**
	 * 添加会员盘口信息
	 * @param handicapInfo 会员盘口信息
	 */
	public void saveMemberHandicapInfo(JSONObject handicapInfo)
			throws Exception {
		//存入配置信息
		IbmcHmSet hmSet = new IbmcHmSet();
		hmSet.setExistHmId(existId);
		hmSet.setBetRateT(handicapInfo.get("BET_RATE_T_"));
		hmSet.setBetMerger(handicapInfo.get("BET_MERGER_"));
		hmSet.setCreateTime(nowTime);
		hmSet.setCreateTimeLong(System.currentTimeMillis());
		hmSet.setUpdateTimeLong(System.currentTimeMillis());
		hmSet.setState(IbmStateEnum.OPEN.name());
		new IbmcHmSetService().save(hmSet);
	}
	/**
	 * 添加会员游戏信息
	 * @param gameInfos 会员游戏信息
	 */
	public void saveMemberGameInfo( JSONArray gameInfos) throws Exception {
		IbmcHmGameSetService hmGameSetService = new IbmcHmGameSetService();
		IbmcHmGameSet hmGameSet = new IbmcHmGameSet();
		hmGameSet.setExistHmId(existId);
		hmGameSet.setCreateTime(nowTime);
		hmGameSet.setCreateTimeLong(System.currentTimeMillis());
		hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
		hmGameSet.setState(IbmStateEnum.OPEN.name());
		for (int i = 0; i < gameInfos.size(); i++) {
			JSONObject info = gameInfos.getJSONObject(i);
			hmGameSet.setGameCode(info.get("GAME_CODE_"));
			hmGameSet.setBetState(info.get("BET_STATE_"));
			hmGameSet.setBetSecond(info.getInteger("BET_SECOND_"));
			hmGameSet.setSplitTwoSideAmount(info.getInteger("SPLIT_TWO_SIDE_AMOUNT_"));
			hmGameSet.setSplitNumberAmount(info.getInteger("SPLIT_NUMBER_AMOUNT_"));
			hmGameSetService.save(hmGameSet);
		}
	}
    /**
     * 添加会员信息
     * @param customerAccount   会员账号
     */
    public void saveMemberInfo(String customerAccount) throws Exception {
        IbmcHmInfoService hmInfoService=new IbmcHmInfoService();
        IbmcHmInfo hmInfo=new IbmcHmInfo();
        hmInfo.setExistHmId(existId);
        hmInfo.setMemberAccount(customerAccount);
        hmInfo.setCreateTime(nowTime);
        hmInfo.setCreateTimeLong(System.currentTimeMillis());
        hmInfo.setUpdateTimeLong(System.currentTimeMillis());
        hmInfo.setState(IbmStateEnum.OPEN.name());
        hmInfoService.save(hmInfo);
    }
	/**
	 * 添加代理盘口信息
	 * @param handicapInfo 代理盘口信息
	 */
	public void saveAgentHandicapInfo(JSONObject handicapInfo) throws Exception {
		IbmcHaSet haSet = new IbmcHaSet();
		haSet.setExistHaId(existId);
		haSet.setFollowMemberType(handicapInfo.get("FOLLOW_MEMBER_TYPE_"));
		haSet.setMemberListInfo(handicapInfo.get("FOLLOW_MEMBER_LIST_INFO_"));
		haSet.setCreateTime(nowTime);
		haSet.setCreateTimeLong(nowTime.getTime());
		haSet.setUpdateTimeLong(nowTime.getTime());
		haSet.setState(IbmStateEnum.OPEN.name());
		new IbmcHaSetService().save(haSet);
	}
	/**
	 * 添加代理游戏信息
	 * @param gameInfos 代理游戏信息
	 * @param handicapCode 盘口编码
	 */
	public void saveAgentGameInfo(JSONArray gameInfos, HandicapUtil.Code handicapCode) throws Exception {
		IbmcHaGameSetService haGameSetService = new IbmcHaGameSetService();
		IbmcHaGameSet haGameSet = new IbmcHaGameSet();
		haGameSet.setExistHaId(existId);
		haGameSet.setCreateTime(nowTime);
		haGameSet.setCreateTimeLong(System.currentTimeMillis());
		haGameSet.setUpdateTimeLong(System.currentTimeMillis());
		haGameSet.setState(IbmStateEnum.OPEN.name());

		for (int i = 0; i < gameInfos.size(); i++) {
			JSONObject info = gameInfos.getJSONObject(i);
			haGameSet.setGameCode(info.getString("GAME_CODE_"));
			putAgentAttr(handicapCode, haGameSet, info);
			haGameSetService.save(haGameSet);
		}
	}

	/**
	 * 放入配置数据
	 *
	 * @param info       配置信息
	 * @param handicapCode 盘口编码
	 * @param haGameSet    配置类
	 */
	public static void putAgentAttr(HandicapUtil.Code handicapCode, IbmcHaGameSet haGameSet, JSONObject info)
			throws SchedulerException, SQLException {
		String betState = info.getString("BET_STATE_");
		if (IbmTypeEnum.TRUE.equal(betState)) {
			//  开启定时获取投注信息工作
			QuartzTool.saveGrabBetJob(haGameSet.getExistHaId(), handicapCode,
					GameUtil.Code.valueOf(haGameSet.getGameCode()));
		} else if (IbmTypeEnum.FALSE.equal(betState)) {
			//  移除定时获取投注信息工作
			QuartzTool.removeGrabBetJob(haGameSet.getExistHaId(), handicapCode,
					GameUtil.Code.valueOf(haGameSet.getGameCode()));
		}
		haGameSet.setBetState(betState);
		haGameSet.setBetFollowMultipleT(info.getInteger("BET_FOLLOW_MULTIPLE_T_"));
		haGameSet.setBetLeastAmountT(info.getInteger("BET_LEAST_AMOUNT_T_"));
		haGameSet.setBetMostAmountT(info.getInteger("BET_MOST_AMOUNT_T_"));
		haGameSet.setBetFilterNumber(info.getString("BET_FILTER_NUMBER_"));
		haGameSet.setBetFilterTwoSide(info.getString("BET_FILTER_TWO_SIDE_"));
		haGameSet.setNumberOpposing(info.getString("NUMBER_OPPOSING_"));
		haGameSet.setTwoSideOpposing(info.getString("TWO_SIDE_OPPOSING_"));
		if (info.containsKey("FILTER_PROJECT_")) {
			haGameSet.setFilterProject(info.getString("FILTER_PROJECT_"));
		}
	}

    /**
     * 添加代理信息
     */
    public void saveAgentInfo() throws Exception {
        IbmcHaInfoService haInfoService=new IbmcHaInfoService();
        IbmcHaInfo haInfo=new IbmcHaInfo();
        haInfo.setExistHaId(existId);
        haInfo.setCreateTime(nowTime);
        haInfo.setCreateTimeLong(nowTime.getTime());
        haInfo.setUpdateTime(nowTime);
        haInfo.setUpdateTimeLong(nowTime.getTime());
        haInfo.setState(IbmStateEnum.OPEN.name());
        haInfoService.save(haInfo);
    }

    /**
     * 添加绑定信息
     * @param bindInfos     绑定信息
     */
    public void saveBindInfo(JSONArray bindInfos) throws Exception {
        IbmcAgentMemberInfoService agentMemberInfoService=new IbmcAgentMemberInfoService();
        IbmcAgentMemberInfo agentMemberInfo=new IbmcAgentMemberInfo();
        agentMemberInfo.setExistHaId(existId);
        agentMemberInfo.setCreateTime(nowTime);
        agentMemberInfo.setCreateTimeLong(nowTime.getTime());
        agentMemberInfo.setUpdateTime(nowTime);
        agentMemberInfo.setUpdateTimeLong(nowTime.getTime());
        agentMemberInfo.setState(IbmStateEnum.OPEN.name());
        for(int i=0;i<bindInfos.size();i++){
            JSONObject memberInfo=bindInfos.getJSONObject(i);
            agentMemberInfo.setExistHmId(memberInfo.getString("EXIST_HM_ID_"));
            agentMemberInfo.setMemberClientCode(memberInfo.getString("CLIENT_CODE_"));
            agentMemberInfo.setBetModeInfo(memberInfo.get("BET_MODE_INFO_"));
            agentMemberInfo.setMemberHandicapCode(memberInfo.get("MEMBER_HANDICAP_CODE_"));
            agentMemberInfo.setMemberAccount(memberInfo.get("MEMBER_ACCOUNT_"));
            agentMemberInfoService.save(agentMemberInfo);
        }
    }
    //endregion
}
