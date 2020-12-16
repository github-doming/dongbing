package com.ibs.plan.connector.pc.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.connector.core.IbspMemberProfitDefine;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.Map;
/**
 * 获取盘口会员的基础信息 - 登录成功后请求此接口
 *
 * @Author: Dongming
 * @Date: 2020-05-25 14:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/member/info", method = HttpConfig.Method.GET) public class HandicapInfoAction
		extends CommCoreAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		if (StringTool.isEmpty(handicapMemberId)) {
			return bean.put401Data();
		}
		try {
			String handicapId = new IbspHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				return bean.put404HandicapMember();
			}

			// 获取该盘口会员的信息
			Map<String, Object> hmInfo = new IbspHmInfoService().findShowInfo(handicapMemberId);

			//是否登录
			if (ContainerTool.isEmpty(hmInfo) || !IbsStateEnum.LOGIN.name().equals(hmInfo.get("LOGIN_STATE_"))) {
				bean.putEnum(CodeEnum.IBS_404_LOGIN);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			hmInfo.put("HANDICAP_PROFIT_", NumberTool.doubleT(hmInfo.remove("HANDICAP_PROFIT_T_")));
			hmInfo.put("AMOUNT_", NumberTool.doubleT(hmInfo.remove("AMOUNT_T_")));

			Map<String, Object> data = new HashMap<>(5);
			data.put("hmInfo", hmInfo);

			//盘口会员 盈利信息
			Map<String, Object> showInfo = new IbspMemberProfitDefine().getProfitInfo(handicapMemberId);
			data.putAll(showInfo);

			// 盘口会员基础设置
			Map<String, Object> hmSetInfo = new IbspHmSetService().findShowInfo(handicapMemberId);
			hmSetInfo.put("BET_RATE_", NumberTool.doubleT(hmSetInfo.remove("BET_RATE_T_")) * 100);
			data.put("hmSetInfo", hmSetInfo);

			//盘口游戏信息获取
			data.put("gameInfo", new IbspHmGameSetService().listGameInfo(handicapMemberId));

			bean.success(data);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "获取盘口会员的基础信息错误", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
}
