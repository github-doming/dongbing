package com.ibm.follow.connector.pc.member.refresh;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommQueryAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit.IbmHmProfitDefine;
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 定时刷新盘口信息
 * @Author: Dongming
 * @Date: 2019-09-10 10:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/member/refresh/info", method = HttpConfig.Method.GET)
public class HandicapInfoAction extends CommQueryAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		if (StringTool.isEmpty(handicapMemberId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			String handicapId = new IbmHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}

			Map<String, Object> data = new HashMap<>(4);
			// 盘口会员信息
			data.put("hmInfo", IbmHmProfitDefine.getMemberInfo(handicapMemberId));

			// 盈亏信息
			Map<String, Object> profitInfo = IbmHmProfitDefine.getProfitInfo(handicapMemberId);
			data.putAll(profitInfo);

			// 盘口游戏信息
			List<Map<String, Object>> gameInfos = new IbmHmGameSetService().listInfo(handicapMemberId);
			if (ContainerTool.notEmpty(gameInfos)) {
				data.put("gameInfos", gameInfos);
			}
			// 盘口会员基础设置
			Map<String, Object> hmSetInfo = new IbmHmSetService().findShowInfo(handicapMemberId);
			hmSetInfo.put("BET_RATE_", NumberTool.doubleT(hmSetInfo.remove("BET_RATE_T_")) * 100);
			data.put("hmSetInfo", hmSetInfo);

			bean.success(data);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("定时刷新盘口信息错误"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
