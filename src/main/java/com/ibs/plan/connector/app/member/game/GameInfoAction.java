package com.ibs.plan.connector.app.member.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 获取游戏设置信息
 * @Author: null
 * @Date: 2020-05-29 10:14
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/member/gameInfo", method = HttpConfig.Method.GET)
public class GameInfoAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		if(StringTool.isEmpty(handicapMemberId,gameCode)){
			return bean.put401Data();
		}
		try{
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				return bean.put401Data();
			}
			// 获取该盘口会员的信息
			String loginState= new IbspHmInfoService().findLoginState(handicapMemberId);
			//是否登录
			if (StringTool.isEmpty(loginState) || !IbsStateEnum.LOGIN.name().equals(loginState)) {
				bean.putEnum(CodeEnum.IBS_404_LOGIN);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Map<String, Object> info = new IbspHmGameSetService().findShowInfo(handicapMemberId,gameId);
			if (ContainerTool.isEmpty(info)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			bean.success(info);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "获取游戏设置信息错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
