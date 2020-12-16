package com.ibs.plan.connector.pc.member.refresh;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.plan.connector.core.IbspMemberProfitDefine;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 定时刷新盘口信息
 * @Author: null
 * @Date: 2020-05-26 16:23
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/member/refresh/info", method = HttpConfig.Method.GET)
public class HandicapInfoAction  extends CommCoreAction {
	@Override
	public Object run() throws Exception {
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
		try{
			String handicapId = new IbspHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				return bean.put404HandicapMember();
			}
			Map<String, Object> data = new HashMap<>(5);

			// 获取该盘口会员的信息
			Map<String, Object> hmInfo = new IbspHmInfoService().findShowInfo(handicapMemberId);

			hmInfo.put("HANDICAP_PROFIT_", NumberTool.doubleT(hmInfo.remove("HANDICAP_PROFIT_T_")));
			hmInfo.put("AMOUNT_", NumberTool.doubleT(hmInfo.remove("AMOUNT_T_")));
			data.put("hmInfo", hmInfo);

			//盘口会员 盈利信息
			Map<String, Object> showInfo = new IbspMemberProfitDefine().getProfitInfo(handicapMemberId);
			data.putAll(showInfo);

			//盘口游戏信息获取
			data.put("gameInfo", new IbspHmGameSetService().listInfo(handicapMemberId));

			bean.success(data);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "定时刷新盘口信息错误", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
}
