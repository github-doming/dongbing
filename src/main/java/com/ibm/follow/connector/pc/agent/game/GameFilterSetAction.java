package com.ibm.follow.connector.pc.agent.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_log_ha.entity.IbmLogHa;
import com.ibm.follow.servlet.cloud.ibm_log_ha.service.IbmLogHaService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 设置游戏过滤信息
 * @Author: lxl
 * @Date: 2019-09-12 14:03
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/agent/gameFilterSet", method = HttpConfig.Method.POST)
public class GameFilterSetAction extends CommCoreAction {

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
		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
		String filterProject = dataMap.getOrDefault("FILTER_PROJECT_", "").toString();

		if (StringTool.isEmpty(handicapAgentId, gameCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		//格式校验
		GameUtil.Code code = GameUtil.value(gameCode);
		if (code == null) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
            String filter=GameTool.gameRule(code, filterProject);
			//用户是否登录
			if (!IbmStateEnum.LOGIN.equal(new IbmHaInfoService().findLoginState(handicapAgentId))){
				bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			IbmHaGameSetService haGameSetService = new IbmHaGameSetService();
			// 数据校验
			String gameId =GameUtil.id(gameCode);
			String haGameSetId = haGameSetService.findId(handicapAgentId, gameId);
			if (StringTool.isEmpty(haGameSetId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			// 更新数据
			haGameSetService.updateFilterByGameId(haGameSetId, filter);
			//添加盘口代理日志信息
			saveHaLog(handicapAgentId, haGameSetId, filter);

			//写入事件
			JSONObject content = new JSONObject();
			content.put("GAME_CODE_", gameCode);
			content.put("SET_ITEM_", IbmMethodEnum.SET_FILTER.name());
			content.put("METHOD_", IbmMethodEnum.AGENT_SET.name());
			content.put("FILTER_PROJECT_", filter);

			String desc= this.getClass().getName().concat("，设置游戏过滤信息");
			boolean flag=EventThreadDefine.sendClientConfig(content,handicapAgentId, IbmTypeEnum.AGENT,desc);
			if (!flag){
				bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putEnum(IbmCodeEnum.CODE_403);
				return bean;
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
	 *
	 * @param handicapAgentId 盘口代理主键
	 * @param haGameSetId     盘口代理游戏设置主键
	 * @param filterProject   过滤信息
	 */
	private void saveHaLog(String handicapAgentId, String haGameSetId, String filterProject) throws Exception {
		IbmLogHa logHa = new IbmLogHa();
		logHa.setHandicapAgentId(handicapAgentId);
		logHa.setAppUserId(appUserId);
		logHa.setHandleType("UPDATE");
		logHa.setHandleContent(
				"IBM_HA_GAME_SET_ID_:".concat(haGameSetId).concat(",FILTER_PROJECT_:").concat(filterProject));
		logHa.setCreateTime(new Date());
		logHa.setCreateTimeLong(System.currentTimeMillis());
		logHa.setUpdateTimeLong(System.currentTimeMillis());
		logHa.setState(IbmStateEnum.OPEN.name());
		logHa.setDesc(this.getClass().getName());
		new IbmLogHaService().save(logHa);
	}

}
