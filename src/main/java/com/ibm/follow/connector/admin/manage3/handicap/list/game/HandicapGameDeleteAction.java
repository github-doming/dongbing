package com.ibm.follow.connector.admin.manage3.handicap.list.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapGameService;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 删除盘口游戏
 * @Author: Dongming
 * @Date: 2019-11-05 10:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/game/delete", method = HttpConfig.Method.POST) public class HandicapGameDeleteAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String handicapGameId = request.getParameter("handicapGameId");
		if (StringTool.isEmpty(handicapGameId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {

			IbmAdminHandicapGameService handicapGameService = new IbmAdminHandicapGameService();
			Map<String, Object> handicapGameInfo = handicapGameService.findIdInfo(handicapGameId);
			if (ContainerTool.isEmpty(handicapGameInfo)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			Date nowTime = new Date();
			String handicapId = handicapGameInfo.get("HANDICAP_ID_").toString();
			String gameId = handicapGameInfo.get("GAME_ID_").toString();
			String gameCode = GameUtil.code(gameId).name();

			JSONObject content = new JSONObject();
			content.put("BET_STATE_", IbmTypeEnum.FALSE);
			content.put("GAME_CODE_", gameCode);
			content.put("SET_ITEM_", IbmMethodEnum.SET_BET_STATE.name());
			IbmTypeEnum category = HandicapUtil.category(handicapId);
			String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName()).concat(",删除盘口游戏");
			if (category.equal(IbmTypeEnum.AGENT)) {
				IbmHaGameSetService gameSetService = new IbmHaGameSetService();
				List<String> handicapAgentIds = gameSetService.listHostingHaId(handicapId, gameId);

				//关闭已登录用户该游戏的托管信息
				content.put("METHOD_", IbmMethodEnum.AGENT_SET.name());
				for (String handicapAgentId : handicapAgentIds) {
					EventThreadDefine.sendClientConfig(content,handicapAgentId,IbmTypeEnum.AGENT,desc);

				}

				//同步删除有关该游戏的数据
				gameSetService.delData(handicapId, gameId,desc,nowTime);
			} else if (category.equal(IbmTypeEnum.MEMBER)) {
				IbmHmGameSetService gameSetService = new IbmHmGameSetService();
				List<String> handicapMemberIds = gameSetService.listHostingHmId(handicapId, gameId);

				//关闭已登录用户该游戏的托管信息
				content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());
				for (String handicapMemberId : handicapMemberIds) {
					EventThreadDefine.sendClientConfig(content,handicapMemberId,IbmTypeEnum.MEMBER,desc);
				}
				//同步删除有关该游戏的数据
				gameSetService.delData(handicapId, gameId,desc,nowTime);
			}

			//删除盘口游戏信息
			handicapGameService.del(handicapGameId);

			bean.success();
		} catch (Exception e) {
			log.error("删除盘口游戏错误");
			throw e;
		}
		return bean;
	}
}
