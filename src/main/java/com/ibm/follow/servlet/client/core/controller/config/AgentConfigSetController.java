package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.ClientDefineController;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import com.ibm.follow.servlet.client.ibmc_ha_game_set.entity.IbmcHaGameSet;
import com.ibm.follow.servlet.client.ibmc_ha_game_set.service.IbmcHaGameSetService;
import com.ibm.follow.servlet.client.ibmc_ha_set.entity.IbmcHaSet;
import com.ibm.follow.servlet.client.ibmc_ha_set.service.IbmcHaSetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.LogTool;
import org.doming.core.tools.StringTool;
import org.quartz.SchedulerException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 代理设置信息
 * @Author: zjj
 * @Date: 2019-09-07 15:31
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AgentConfigSetController implements ClientExecutor {
	protected static final Logger log = LogManager.getLogger(AgentConfigSetController.class);

	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		String existHaId = msgObj.getString("EXIST_HA_ID_");
		//验证内存
		if (CustomerCache.stateInfo(existHaId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		String handicapCode = new IbmcExistHaService().findHandicapCode(existHaId);
		if (StringTool.isEmpty(handicapCode)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		HandicapUtil.Code code = HandicapUtil.Code.valueOf(handicapCode);
		switch (IbmMethodEnum.valueOf(msgObj.getString("SET_ITEM_"))) {
			case SET_HANDICAP:
				setHandicap(existHaId, msgObj);
				break;
			case SET_GAME_INFO:
				setGameInfo(existHaId, code, msgObj);
				break;
			case SET_GAME:
				setGame(existHaId, code, msgObj);
				break;
			case SET_BET_STATE:
				setBetState(existHaId, code, msgObj);
				break;
			case SET_FILTER:
				setFilter(existHaId, msgObj);
				break;
			default:
				bean.putEnum(CodeEnum.IBS_404_METHOD);
				bean.putSysEnum(CodeEnum.CODE_404);
				LogTool.error(log, this, "错误的代理配置方法接收".concat(msgObj.getString("SET_ITEM_")));
		}
		return bean;
	}

	/**
	 * 设置所有游戏设置信息
	 * @param existHaId		已存在盘口代理id
	 * @param handicapCode	盘口code
	 * @param msgObj			游戏设置信息
	 */
	private void setGameInfo(String existHaId, HandicapUtil.Code handicapCode, JSONObject msgObj) throws Exception {
		JSONArray gameInfo = msgObj.getJSONArray("GAME_INFO_");
		if(ContainerTool.isEmpty(gameInfo)){
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return ;
		}
		IbmcHaGameSetService haGameSetService=new IbmcHaGameSetService();
		List<String> haGameSetIds=haGameSetService.findIds(existHaId);
		if (ContainerTool.notEmpty(haGameSetIds)) {
			bean.putEnum(CodeEnum.IBS_403_DATA_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return;
		}
		IbmcHaGameSet haGameSet=new IbmcHaGameSet();
		haGameSet.setExistHaId(existHaId);
		haGameSet.setCreateTime(new Date());
		haGameSet.setCreateTimeLong(System.currentTimeMillis());
		haGameSet.setUpdateTime(new Date());
		haGameSet.setUpdateTimeLong(System.currentTimeMillis());
		haGameSet.setState(IbmStateEnum.OPEN.name());
		for (int i = 0; i < gameInfo.size(); i++) {
			JSONObject info = gameInfo.getJSONObject(i);
			haGameSet.setGameCode(info.getString("GAME_CODE_"));
			ClientDefineController.putAgentAttr(handicapCode,haGameSet,info);
			haGameSetService.save(haGameSet);
		}
		bean.success();
	}

	/**
	 * 设置盘口信息
	 *
	 * @param existHaId 存在代理主键
	 * @param msgObj    消息主体
	 */
	private void setHandicap(String existHaId, JSONObject msgObj) throws Exception {
		Object followMemberListInfo = msgObj.getOrDefault("FOLLOW_MEMBER_LIST_INFO_", "");
		String followMemberType = msgObj.getString("FOLLOW_MEMBER_TYPE_");
		if (StringTool.isEmpty(followMemberType)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}
		IbmcHaSetService haSetService = new IbmcHaSetService();
		Map<String, Object> followInfo = haSetService.findFollowInfo(existHaId);
		Date nowTime = new Date();
		if (ContainerTool.isEmpty(followInfo)) {
			IbmcHaSet haSet = new IbmcHaSet();
			haSet.setExistHaId(existHaId);
			haSet.setFollowMemberType(followMemberType);
			haSet.setMemberListInfo(followMemberListInfo);
			haSet.setCreateTime(nowTime);
			haSet.setCreateTimeLong(nowTime.getTime());
			haSet.setUpdateTimeLong(nowTime.getTime());
			haSet.setState(IbmStateEnum.OPEN.name());
			haSetService.save(haSet);
		} else {
			if (!ContainerTool.equals(followInfo, "FOLLOW_MEMBER_TYPE_", followMemberListInfo) || !ContainerTool
					.equals(followInfo, "MEMBER_LIST_INFO_", followMemberType)) {

				haSetService.update(existHaId, followMemberListInfo, followMemberType, nowTime);
			}
		}
		bean.success();
	}

	/**
	 * 设置盘口游戏信息
	 *
	 * @param existHaId    存在代理主键
	 * @param handicapCode 盘口编码
	 * @param msgObj       消息主体
	 */
	private void setGame(String existHaId, HandicapUtil.Code handicapCode, JSONObject msgObj) throws Exception {
		String gameCode = msgObj.getString("GAME_CODE_");
		if (StringTool.isEmpty(gameCode)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}
		IbmcHaGameSetService haGameSetService = new IbmcHaGameSetService();
		IbmcHaGameSet haGameSet = haGameSetService.findExist(existHaId, gameCode);
		if (haGameSet == null) {
			haGameSet = new IbmcHaGameSet();
			haGameSet.setExistHaId(existHaId);
			haGameSet.setGameCode(gameCode);
			ClientDefineController.putAgentAttr(handicapCode,haGameSet,msgObj);
			haGameSet.setCreateTime(new Date());
			haGameSet.setCreateTimeLong(System.currentTimeMillis());
			haGameSet.setUpdateTimeLong(System.currentTimeMillis());
			haGameSet.setState(IbmStateEnum.OPEN.name());
			haGameSetService.save(haGameSet);
		} else {
			ClientDefineController.putAgentAttr(handicapCode,haGameSet,msgObj);
			haGameSet.setUpdateTime(new Date());
			haGameSet.setUpdateTimeLong(System.currentTimeMillis());
			haGameSetService.update(haGameSet);
		}
		bean.success();
	}


	/**
	 * 设置投注状态信息
	 *
	 * @param existHaId    存在代理主键
	 * @param handicapCode 盘口编码
	 * @param msgObj       消息主体
	 */
	private void setBetState(String existHaId, HandicapUtil.Code handicapCode, JSONObject msgObj)
			throws SQLException, SchedulerException {
		String gameCode = msgObj.getString("GAME_CODE_");
		String betState = msgObj.getString("BET_STATE_");
		if (StringTool.isEmpty(gameCode, betState)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}

		IbmcHaGameSetService haGameSetService = new IbmcHaGameSetService();
		String haGameSetId = haGameSetService.findId(existHaId, gameCode);
		if (StringTool.isEmpty(haGameSetId)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}
		haGameSetService.updateBetState(haGameSetId, betState);
		if (IbmTypeEnum.TRUE.equal(betState)) {
			//  开启定时获取投注信息工作
			QuartzTool.saveGrabBetJob(existHaId, handicapCode, GameUtil.Code.valueOf(gameCode));
		} else if (IbmTypeEnum.FALSE.equal(betState)) {
			// 移除定时获取投注信息工作
			QuartzTool.removeGrabBetJob(existHaId, handicapCode, GameUtil.Code.valueOf(gameCode));
		}
		bean.success();
	}

	/**
	 * 设置过滤项目信息
	 *
	 * @param existHaId    存在代理主键
	 * @param msgObj       消息主体
	 */
	private void setFilter(String existHaId, JSONObject msgObj) throws SQLException {
		String gameCode = msgObj.getString("GAME_CODE_");
		String filterProject = msgObj.getString("FILTER_PROJECT_");
		if (StringTool.isEmpty(gameCode)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}
		IbmcHaGameSetService haGameSetService = new IbmcHaGameSetService();
		String haGameSetId = haGameSetService.findId(existHaId, gameCode);
		if (StringTool.isEmpty(haGameSetId)){
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}
		haGameSetService.updateFilter(haGameSetId, filterProject);
		bean.success();
	}



}
