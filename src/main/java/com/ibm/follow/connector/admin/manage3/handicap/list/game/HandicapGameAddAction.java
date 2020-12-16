package com.ibm.follow.connector.admin.manage3.handicap.list.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapAgentService;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapGameService;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapMemberService;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapService;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.entity.IbmHaGameSet;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.entity.IbmHandicapGame;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.entity.IbmHmGameSet;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 添加盘口游戏
 * @Author: Dongming
 * @Date: 2019-11-05 14:29
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/game/add", method = HttpConfig.Method.POST) public class HandicapGameAddAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}
		String handicapId = request.getParameter("HANDICAP_ID");
		String gameId = request.getParameter("GAME_ID_");
		String closeTimeStr = request.getParameter("CLOSE_TIME");
        String type = request.getParameter("TYPE");
		String tableName = request.getParameter("TABLE_NAME");
		String snStr = request.getParameter("SN");
		String icon = request.getParameter("ICON");
		String desc = request.getParameter("DESC_");

		if (StringTool.isEmpty(handicapId, gameId)) {
			bean.putEnum(IbmCodeEnum.IBM_404_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		try {
			//盘口信息
			Map<String, Object> handicapInfo = new IbmAdminHandicapService().findInfo(handicapId);
			if (ContainerTool.isEmpty(handicapInfo)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			Map<String,Object> gameInfo = new IbmGameService().findInfo(gameId);

			Date nowTime = new Date();
			int closeTime = NumberTool.getInteger(closeTimeStr, 0);
			int sn = NumberTool.getInteger(snStr, 0);

			//添加盘口游戏信息
			IbmHandicapGame handicapGame = new IbmHandicapGame();
			handicapGame.setGameName(gameInfo.get("GAME_NAME_"));
			handicapGame.setHandicapId(handicapId);
			handicapGame.setGameId(gameId);
			handicapGame.setHandicapName(handicapInfo.get("HANDICAP_NAME_"));
			handicapGame.setHandicapCode(handicapInfo.get("HANDICAP_CODE_"));
			handicapGame.setCloseTime(closeTime);
			handicapGame.setGameCode(gameInfo.get("GAME_CODE_"));
            handicapGame.setType(type);
			handicapGame.setSubIhbiTableName(tableName);
			handicapGame.setIcon(icon);
			handicapGame.setSn(sn);
			handicapGame.setCreateUser(adminUser.getUserId());
			handicapGame.setCreateTime(new Date());
			handicapGame.setCreateTimeLong(System.currentTimeMillis());
			handicapGame.setUpdateTimeLong(System.currentTimeMillis());
			handicapGame.setState(IbmStateEnum.OPEN.name());
			handicapGame.setDesc(desc);
			new IbmAdminHandicapGameService().save(handicapGame);

			//同时给用户添加游戏
			IbmTypeEnum category = HandicapUtil.category(handicapId);
			if (category.equals(IbmTypeEnum.AGENT)){
				saveHaGameSet(handicapId, gameId,nowTime);
			}else if (category.equals(IbmTypeEnum.MEMBER)){
				saveHmGameSet(handicapId, gameId,nowTime);
			}
			bean.success();
		} catch (Exception e) {
			log.error("添加盘口游戏错误");
			throw e;
		}
		return bean;
	}
	/**
	 * 保存会员游戏设置
	 * @param handicapId 盘口主键
	 * @param gameId 游戏主键
	 * @param nowTime 创建时间
	 */
	private void saveHmGameSet(String handicapId, String gameId, Date nowTime) throws Exception {
		List<Map<String, Object>> hmInfos =  new IbmAdminHandicapMemberService().listInfoByHandicapId(handicapId);
		//该盘口中没有会员
		if (ContainerTool.isEmpty(hmInfos)){
			return;
		}
		IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();
		//获取盘口游戏设置初始化信息
		Map<String, Object> hmGameSetInfo = hmGameSetService.findInitInfo();
		if (ContainerTool.isEmpty(hmGameSetInfo)) {
			throw new RuntimeException("初始化盘口游戏设置信息不存在");
		}
		IbmHmGameSet hmGameSet = new IbmHmGameSet();
		hmGameSet.setGameId(gameId);
		hmGameSet.setHandicapId(handicapId);
		hmGameSet.setBetState(hmGameSetInfo.get("BET_STATE_"));
		hmGameSet.setBetMode(hmGameSetInfo.get("BET_MODE_"));
		hmGameSet.setBetAutoStop(hmGameSetInfo.get("BET_AUTO_STOP_"));
		hmGameSet.setBetAutoStopTimeLong(0);
		hmGameSet.setBetAutoStart(hmGameSetInfo.get("BET_AUTO_START_"));
		hmGameSet.setBetAutoStartTimeLong(0);
		hmGameSet.setBetSecond(NumberTool.getInteger(hmGameSetInfo.get("BET_SECOND_")));
		hmGameSet.setSplitTwoSideAmount(NumberTool.getInteger(hmGameSetInfo.get("SPLIT_TWO_SIDE_AMOUNT_")));
		hmGameSet.setSplitNumberAmount(NumberTool.getInteger(hmGameSetInfo.get("SPLIT_NUMBER_AMOUNT_")));
		hmGameSet.setCreateTime(nowTime);
		hmGameSet.setState(IbmStateEnum.OPEN.name());
		hmGameSet.setDesc("添加游戏");
		for (Map<String, Object> hmInfo : hmInfos) {
			hmGameSet.setIbmHmGameSetId(null);
			hmGameSet.setCreateTimeLong(System.currentTimeMillis());
			hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
			hmGameSet.setHandicapMemberId(hmInfo.get("HANDICAP_MEMBER_ID_"));
			hmGameSet.setUserId(hmInfo.get("APP_USER_ID_"));
			hmGameSetService.save(hmGameSet);
		}


	}

	/**
	 * 保存代理游戏设置
	 * @param handicapId 盘口主键
	 * @param gameId 游戏主键
	 * @param nowTime 创建时间
	 */
	private void saveHaGameSet(String handicapId, String gameId, Date nowTime) throws Exception {
		//该盘口中没有代理
		List<Map<String, Object>> haInfos = new IbmAdminHandicapAgentService().listInfoByHandicapId(handicapId);
		if (ContainerTool.isEmpty(haInfos)) {
			return;
		}
		IbmHaGameSetService haGameSetService = new IbmHaGameSetService();
		//获取盘口游戏设置初始化信息
		Map<String, Object> gameSetDefInfo = haGameSetService.findInitInfo();
		if (ContainerTool.isEmpty(gameSetDefInfo)) {
			throw new RuntimeException("初始化盘口游戏设置信息不存在");
		}
		IbmHaGameSet haGameSet = new IbmHaGameSet();
		haGameSet.setHandicapId(handicapId);
		haGameSet.setGameId(gameId);
		haGameSet.setBetState(gameSetDefInfo.get("BET_STATE_"));
		haGameSet.setBetFollowMultipleT(gameSetDefInfo.get("BET_FOLLOW_MULTIPLE_T_"));
		haGameSet.setBetLeastAmountT(gameSetDefInfo.get("BET_LEAST_AMOUNT_T_"));
		haGameSet.setBetMostAmountT(gameSetDefInfo.get("BET_MOST_AMOUNT_T_"));
		haGameSet.setBetFilterNumber(gameSetDefInfo.get("BET_FILTER_NUMBER_"));
		haGameSet.setBetFilterTwoSide(gameSetDefInfo.get("BET_FILTER_TWO_SIDE_"));
		haGameSet.setNumberOpposing(gameSetDefInfo.get("NUMBER_OPPOSING_"));
		haGameSet.setTwoSideOpposing(gameSetDefInfo.get("TWO_SIDE_OPPOSING_"));
		haGameSet.setBetRecordRows(NumberTool.getInteger(gameSetDefInfo.get("BET_RECORD_ROWS_")));
		haGameSet.setCreateTime(nowTime);
		haGameSet.setState(IbmStateEnum.OPEN.name());
		haGameSet.setDesc("添加游戏");
		for(Map<String,Object> haInfo:haInfos){
			haGameSet.setIbmHaGameSetId(null);
			haGameSet.setCreateTimeLong(System.currentTimeMillis());
			haGameSet.setUpdateTimeLong(System.currentTimeMillis());
			haGameSet.setHandicapAgentId(haInfo.get("APP_USER_ID_"));
			haGameSet.setUserId(haInfo.get("HANDICAP_AGENT_ID_"));
			haGameSetService.save(haGameSet);
		}
	}
}
