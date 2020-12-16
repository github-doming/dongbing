package com.ibm.follow.connector.pc.agent.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_log_ha.entity.IbmLogHa;
import com.ibm.follow.servlet.cloud.ibm_log_ha.service.IbmLogHaService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 游戏跟投设置
 * @Author: null
 * @Date: 2019-09-12 14:03
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/agent/gameFollowSet", method = HttpConfig.Method.POST) public class GameFollowSetAction
		extends CommCoreAction {

	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }
		String handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();

		Object followSet = dataMap.getOrDefault("FOLLOW_SET_", "");

		if (StringTool.isEmpty(handicapAgentId) || ContainerTool.isEmpty(followSet)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			//用户是否登录
			if (!IbmStateEnum.LOGIN.equal( new IbmHaInfoService().findLoginState(handicapAgentId))){
				bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			JSONArray gameSets = JSONArray.parseArray(followSet.toString());

			IbmHaGameSetService haGameSetService = new IbmHaGameSetService();

			SetBean setBean = new SetBean();

			JSONObject content = new JSONObject();
			content.put("HANDICAP_AGENT_ID_", handicapAgentId);
			content.put("SET_ITEM_", IbmMethodEnum.SET_GAME.name());
			content.put("METHOD_", IbmMethodEnum.AGENT_SET.name());

			Date nowTime = new Date();
			for (int i = 0; i < gameSets.size(); i++) {
				JSONObject jsonObject = gameSets.getJSONObject(i);
				String gameCode = jsonObject.getString("GAME_CODE_");
				String gameId = GameUtil.id(gameCode);

				setBean.analyze(jsonObject);
				if (!setBean.vali()) {
					bean.putEnum(IbmCodeEnum.IBM_401_DATA);
					bean.putSysEnum(IbmCodeEnum.CODE_401);
					return super.returnJson(bean);
				}

				String haGameSetId = haGameSetService.findId(handicapAgentId, gameId);
				if (StringTool.isEmpty(haGameSetId)) {
					bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return super.returnJson(bean);
				} else {
					haGameSetService.update(haGameSetId, setBean.betState, setBean.betFollowMultipleTh,
							setBean.betLeastAmountTh, setBean.betMostAmountTh, setBean.betFilterNumber,
							setBean.betFilterTwoSide, setBean.numberOpposing, setBean.twoSideOpposing, nowTime);

				}
				//添加盘口代理日志信息
				saveHaLog(handicapAgentId, haGameSetId, setBean);

				//写入客户设置事件
				content.put("GAME_CODE_", gameCode);
				content.put("BET_STATE_", setBean.betState);
				content.put("BET_FOLLOW_MULTIPLE_T_", setBean.betFollowMultipleTh);
				content.put("BET_LEAST_AMOUNT_T_", setBean.betLeastAmountTh);
				content.put("BET_MOST_AMOUNT_T_", setBean.betMostAmountTh);
				content.put("BET_FILTER_NUMBER_", setBean.betFilterNumber);
				content.put("BET_FILTER_TWO_SIDE_", setBean.betFilterTwoSide);
				content.put("NUMBER_OPPOSING_", setBean.numberOpposing);
				content.put("TWO_SIDE_OPPOSING_", setBean.twoSideOpposing);

				String desc= this.getClass().getName().concat("，添加跟投设置");
				boolean flag=EventThreadDefine.sendClientConfig(content,handicapAgentId,IbmTypeEnum.AGENT,desc);
				if (!flag){
					bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
					bean.putEnum(IbmCodeEnum.CODE_403);
					return bean;
				}
			}
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("修改跟投信息失败"),e);
			bean.error(e.getMessage());
		}
		bean.success();
		return bean;
	}

	/**
	 * 保存代理日志
	 * @param handicapAgentId  盘口代理主键
	 * @param haGameSetId 盘口代理游戏设置主键
	 * @param bean 设置信息
	 */
	private void saveHaLog(String handicapAgentId, String haGameSetId, SetBean bean) throws Exception {
		IbmLogHa logHa = new IbmLogHa();
		logHa.setHandicapAgentId(handicapAgentId);
		logHa.setAppUserId(appUserId);
		logHa.setHandleType("UPDATE");
		logHa.setHandleContent("IBM_HA_GAME_SET_ID_:".concat(haGameSetId).concat(",BET_STATE_:").concat(bean.betState)
				.concat(",BET_FOLLOW_MULTIPLE_:").concat(bean.betFollowMultipleTh.toString())
				.concat(",BET_LEAST_AMOUNT_:").concat(bean.betLeastAmountTh.toString()).concat(",BET_MOST_AMOUNT_:")
				.concat(bean.betMostAmountTh.toString()).concat(",BET_FILTER_NUMBER_:").concat(bean.betFilterNumber)
				.concat(",BET_FILTER_TWO_SIDE_:").concat(bean.betFilterTwoSide).concat(",NUMBER_OPPOSING_:")
				.concat(bean.numberOpposing).concat(",TWO_SIDE_OPPOSING_:").concat(bean.twoSideOpposing));
		logHa.setCreateTime(new Date());
		logHa.setCreateTimeLong(System.currentTimeMillis());
		logHa.setUpdateTimeLong(System.currentTimeMillis());
		logHa.setState(IbmStateEnum.OPEN.name());
		logHa.setDesc(this.getClass().getName());
		new IbmLogHaService().save(logHa);
	}


	private static class SetBean {
		String betState;
		Integer betFollowMultipleTh;
		Integer betLeastAmountTh;
		Integer betMostAmountTh;
		String betFilterNumber;
		String betFilterTwoSide;
		String numberOpposing;
		String twoSideOpposing;

		/**
		 * 解析设置bean
		 *
		 * @param json 设置json信息
		 * @return 设置bean
		 */
		public SetBean analyze(JSONObject json) {
			betState = json.getString("BET_STATE_");
			betFollowMultipleTh = NumberTool.intValueT(json.getDouble("BET_FOLLOW_MULTIPLE_"));
			betLeastAmountTh = NumberTool.intValueT(json.getInteger("BET_LEAST_AMOUNT_"));
			betMostAmountTh = NumberTool.intValueT(json.getInteger("BET_MOST_AMOUNT_"));
			betFilterNumber = json.getString("BET_FILTER_NUMBER_");
			betFilterTwoSide = json.getString("BET_FILTER_TWO_SIDE_");
			numberOpposing = json.getString("NUMBER_OPPOSING_");
			twoSideOpposing = json.getString("TWO_SIDE_OPPOSING_");
			return this;
		}

		/**
		 * 验证参数是否合法
		 *
		 * @return 合法 true
		 */
		public boolean vali() {
			return IbmTypeEnum.booleanType(betState) && IbmTypeEnum.booleanType(betFilterNumber) && IbmTypeEnum
					.booleanType(betFilterTwoSide) && IbmTypeEnum.booleanType(numberOpposing) && IbmTypeEnum
					.booleanType(twoSideOpposing);
		}

	}
}
