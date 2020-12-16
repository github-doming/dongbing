package com.ibs.plan.connector.pc.main;

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
import java.util.List;
import java.util.Map;
/**
 * 开启会员盘口
 *
 * @Author: null
 * @Date: 2020-05-23 15:57
 * @Version: v1.0
 */

@ActionMapping(value = "/ibs/pc/home/openMember", method = HttpConfig.Method.GET) public class MemberOpenHandicapAction
		extends CommCoreAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
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

			//获取该用户盘口会员账号信息
			Map<String, Object> account = new IbspHandicapMemberService().findInfo(handicapMemberId);
			if (ContainerTool.isEmpty(account)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			account.remove("MEMBER_PASSWORD_");
			Map<String, Object> data = new HashMap<>(5);


			//获取该用户所有盘口会员账号
			List<Map<String, Object>> allAccount = new IbspHandicapMemberService().listAllAccount(appUserId, handicapId);
			data.put("account", account);
			data.put("allAccount", allAccount);

			// 获取该盘口会员的信息
			//是否登录
			Map<String, Object> hmInfo = new IbspHmInfoService().findShowInfo(handicapMemberId);
			if (ContainerTool.notEmpty(hmInfo)) {
				hmInfo.put("HANDICAP_PROFIT_", NumberTool.doubleT(hmInfo.remove("HANDICAP_PROFIT_T_")));
				hmInfo.put("AMOUNT_", NumberTool.doubleT(hmInfo.remove("AMOUNT_T_")));
				data.put("hmInfo", hmInfo);

				if (IbsStateEnum.LOGIN.equal(hmInfo.get("LOGIN_STATE_"))) {
					// 登录-个人盈利信息
					Map<String, Object> profitInfo = new IbspMemberProfitDefine().getProfitInfo(handicapMemberId);
					data.putAll(profitInfo);

					//盘口游戏信息获取
					data.put("gameInfo", new IbspHmGameSetService().listGameInfo(handicapMemberId));

				}
			}
			// 盘口会员基础设置
			Map<String, Object> hmSetInfo = new IbspHmSetService().findShowInfo(handicapMemberId);
			if (ContainerTool.notEmpty(hmSetInfo)) {
				hmSetInfo.put("BET_RATE_", NumberTool.doubleT(hmSetInfo.remove("BET_RATE_T_")) * 100);
				data.put("hmSetInfo", hmSetInfo);
			}

			bean.success(data);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "开启会员盘口错误", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
}
