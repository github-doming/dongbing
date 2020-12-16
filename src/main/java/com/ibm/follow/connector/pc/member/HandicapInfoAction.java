package com.ibm.follow.connector.pc.member;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
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
import java.util.Map;
/**
 * @Description: 获取盘口会员的盘口信息
 * @Author: Dongming
 * @Date: 2019-09-04 14:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/member/handicapInfo", method = HttpConfig.Method.GET) public class HandicapInfoAction
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

			// 获取该盘口会员的信息
			Map<String, Object> hmInfo = IbmHmProfitDefine.getMemberInfo(handicapMemberId);

			//是否登录
			if (ContainerTool.isEmpty(hmInfo) || !IbmStateEnum.LOGIN.name().equals(hmInfo.get("STATE_"))) {
				bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			Map<String, Object> data = new HashMap<>(5);
			data.put("hmInfo", hmInfo);

			// 登录-个人盈利信息
			Map<String, Object> profitInfo = IbmHmProfitDefine.getProfitInfo(handicapMemberId);
			data.putAll(profitInfo);

			// 盘口会员基础设置
			Map<String, Object> hmSetInfo = new IbmHmSetService().findShowInfo(handicapMemberId);
			hmSetInfo.put("BET_RATE_", NumberTool.doubleT(hmSetInfo.remove("BET_RATE_T_"))*100);
			data.put("hmSetInfo", hmSetInfo);

			//盘口游戏信息获取
			data.put("gameInfo", new IbmHmGameSetService().listGameInfo(handicapMemberId));


			bean.success(data);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("获取盘口代理的盘口信息错误"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
