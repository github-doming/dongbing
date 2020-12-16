package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
import com.ibm.follow.servlet.client.ibmc_hm_game_set.entity.IbmcHmGameSet;
import com.ibm.follow.servlet.client.ibmc_hm_game_set.service.IbmcHmGameSetService;
import com.ibm.follow.servlet.client.ibmc_hm_set.entity.IbmcHmSet;
import com.ibm.follow.servlet.client.ibmc_hm_set.service.IbmcHmSetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.LogTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 会员设置信息
 * @Author: zjj
 * @Date: 2019-09-07 15:30
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class MemberConfigSetController implements ClientExecutor {
	protected static final Logger log = LogManager.getLogger(MemberConfigSetController.class);
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		String existHmId = msgObj.getString("EXIST_HM_ID_");
		if (CustomerCache.stateInfo(existHmId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		String handicapCode = new IbmcExistHmService().findHandicapCode(existHmId);
		if (StringTool.isEmpty(handicapCode)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		HandicapUtil.Code code = HandicapUtil.Code.valueOf(handicapCode);
		switch (IbmMethodEnum.valueOf(msgObj.getString("SET_ITEM_"))) {
			case SET_HANDICAP:
				setHandicap(existHmId, msgObj);
				break;
			case SET_GAME_INFO:
				setGameInfo(existHmId, code, msgObj);
				break;
			case SET_GAME:
				setGame(existHmId, code, msgObj);
				break;
			case SET_BET_STATE:
				setBetState(existHmId, code, msgObj);
				break;
			default:
				bean.putEnum(CodeEnum.IBS_404_METHOD);
				bean.putSysEnum(CodeEnum.CODE_404);
				LogTool.error(log, this, "错误的会员配置方法接收".concat(msgObj.getString("SET_ITEM_")));
		}
		return bean;

	}
	/**
	 * 添加所有游戏设置信息
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param handicapCode 盘口code
	 * @param msgObj       游戏设置信息
	 */
	private void setGameInfo(String existHmId, HandicapUtil.Code handicapCode, JSONObject msgObj) throws Exception {
		JSONArray gameInfo = msgObj.getJSONArray("GAME_INFO_");
		if (ContainerTool.isEmpty(gameInfo)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}
		IbmcHmGameSetService hmGameSetService = new IbmcHmGameSetService();
		List<String> hmGameSetIds = hmGameSetService.findIds(existHmId);
		if (ContainerTool.notEmpty(hmGameSetIds)) {
			bean.putEnum(CodeEnum.IBS_403_DATA_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return;
		}
		IbmcHmGameSet hmGameSet = new IbmcHmGameSet();
		hmGameSet.setExistHmId(existHmId);
		hmGameSet.setCreateTime(new Date());
		hmGameSet.setCreateTimeLong(System.currentTimeMillis());
		hmGameSet.setUpdateTime(new Date());
		hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
		hmGameSet.setState(IbmStateEnum.OPEN.name());
		for (int i = 0; i < gameInfo.size(); i++) {
			JSONObject info = gameInfo.getJSONObject(i);
			hmGameSet.setGameCode(info.get("GAME_CODE_"));
			hmGameSet.setBetState(info.get("BET_STATE_"));
			hmGameSet.setBetSecond(info.getInteger("BET_SECOND_"));
			hmGameSet.setSplitTwoSideAmount(info.getInteger("SPLIT_TWO_SIDE_AMOUNT_"));
			hmGameSet.setSplitNumberAmount(info.getInteger("SPLIT_NUMBER_AMOUNT_"));
			String hmGameSetId = hmGameSetService.save(hmGameSet);

			if (IbmTypeEnum.TRUE.name().equals(info.get("BET_STATE_"))) {
				bean = SetToolController
						.gameLimit(existHmId, handicapCode, GameUtil.Code.valueOf(info.getString("GAME_CODE_")),
								hmGameSetId);
			}
		}
		bean.success();
	}

	/**
	 * 设置盘口信息
	 *
	 * @param existHmId 存在会员主键
	 * @param msgObj    消息主体
	 */
	private void setHandicap(String existHmId, JSONObject msgObj) throws Exception {
		String betMerger = msgObj.getString("BET_MERGER_");
		int betRateTh = msgObj.getInteger("BET_RATE_T_");
		if (StringTool.isEmpty(betMerger, betRateTh)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}

		IbmcHmSetService hmSetService = new IbmcHmSetService();
		Map<String, Object> setInfo = hmSetService.findInfo(existHmId);
		Date nowTime = new Date();
		if (ContainerTool.isEmpty(setInfo)) {
			IbmcHmSet hmSet = new IbmcHmSet();
			hmSet.setExistHmId(existHmId);
			hmSet.setBetRateT(betRateTh);
			hmSet.setBetMerger(betMerger);
			hmSet.setCreateTime(nowTime);
			hmSet.setCreateTimeLong(System.currentTimeMillis());
			hmSet.setUpdateTimeLong(System.currentTimeMillis());
			hmSet.setState(IbmStateEnum.OPEN.name());
			hmSetService.save(hmSet);
		} else {
			if (!ContainerTool.equals(setInfo, "BET_RATE_T_", betRateTh) || !ContainerTool
					.equals(setInfo, "BET_MERGER_", betMerger)) {

				hmSetService.update(existHmId, betRateTh, betMerger, nowTime);
			}
		}
		bean.success();
	}

	/**
	 * 设置游戏信息
	 *
	 * @param existHmId    存在会员主键
	 * @param handicapCode 盘口编码
	 * @param msgObj       消息主体
	 */
	private void setGame(String existHmId, HandicapUtil.Code handicapCode, JSONObject msgObj) throws Exception {
		String gameCode = msgObj.getString("GAME_CODE_");
		String betState = msgObj.getString("BET_STATE_");
		Integer betSecond = msgObj.getInteger("BET_SECOND_");
		Integer splitTwoSideAmount = msgObj.getInteger("SPLIT_TWO_SIDE_AMOUNT_");
		Integer splitNumberAmount = msgObj.getInteger("SPLIT_NUMBER_AMOUNT_");
		if (StringTool.isEmpty(gameCode, betState, betSecond, splitTwoSideAmount, splitNumberAmount)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}

		IbmcHmGameSetService hmGameSetService = new IbmcHmGameSetService();
		String hmGameSetId = hmGameSetService.findId(existHmId, gameCode);
		Date nowTime = new Date();
		if (StringTool.isEmpty(hmGameSetId)) {
			IbmcHmGameSet hmGameSet = new IbmcHmGameSet();
			hmGameSet.setExistHmId(existHmId);
			hmGameSet.setGameCode(gameCode);
			hmGameSet.setBetState(betState);
			hmGameSet.setBetSecond(betSecond);
			hmGameSet.setSplitTwoSideAmount(splitTwoSideAmount);
			hmGameSet.setSplitNumberAmount(splitNumberAmount);
			hmGameSet.setCreateTime(nowTime);
			hmGameSet.setCreateTimeLong(System.currentTimeMillis());
			hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
			hmGameSet.setState(IbmStateEnum.OPEN.name());
			hmGameSetId = hmGameSetService.save(hmGameSet);
		} else {
			hmGameSetService.update(hmGameSetId, betState, betSecond, splitTwoSideAmount, splitNumberAmount, nowTime);
		}
		if (IbmTypeEnum.TRUE.name().equals(betState)) {
			bean = SetToolController.gameLimit(existHmId, handicapCode, GameUtil.Code.valueOf(gameCode), hmGameSetId);
			return;
		}
		bean.success();
	}

	/**
	 * 设置投注状态信息
	 *
	 * @param existHmId    存在会员主键
	 * @param handicapCode 盘口编码
	 * @param msgObj       消息主体
	 */
	private void setBetState(String existHmId, HandicapUtil.Code handicapCode, JSONObject msgObj) throws SQLException {
		String gameCode = msgObj.getString("GAME_CODE_");
		String betState = msgObj.getString("BET_STATE_");
		if (StringTool.isEmpty(gameCode, betState)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}

		IbmcHmGameSetService hmGameSetService = new IbmcHmGameSetService();
		String hmGameSetId = hmGameSetService.findId(existHmId, gameCode);
		if (StringTool.isEmpty(hmGameSetId)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}
		hmGameSetService.update(hmGameSetId, betState);
		if (IbmTypeEnum.TRUE.name().equals(betState)) {
			bean = SetToolController.gameLimit(existHmId, handicapCode, GameUtil.Code.valueOf(gameCode), hmGameSetId);
			return;
		}
		bean.success();
	}

}
