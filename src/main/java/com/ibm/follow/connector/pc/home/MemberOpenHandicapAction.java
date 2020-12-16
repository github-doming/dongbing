package com.ibm.follow.connector.pc.home;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
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
 * @Description: 首页，开启会员盘口
 * @Author: Dongming
 * @Date: 2019-08-30 17:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/home/openMemberHandicap", method = HttpConfig.Method.GET) public class MemberOpenHandicapAction
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

			Map<String, Object> account = new IbmHandicapMemberService().findInfo(handicapMemberId);
			if (ContainerTool.isEmpty(account)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			account.remove("MEMBER_PASSWORD_");
			Map<String, Object> data = new HashMap<>(5);

			//获取该用户所有盘口会员账号
			List<Map<String, Object>> allAccount = new IbmHandicapMemberService().listAllAccount(appUserId, handicapId);
			data.put("account", account);
			data.put("allAccount", allAccount);

			// 获取该盘口会员的信息
			//是否登录
			Map<String, Object> hmInfo = new IbmHmInfoService().findShowInfo(handicapMemberId);
			if (ContainerTool.notEmpty(hmInfo)) {
				hmInfo.put("HANDICAP_PROFIT_", NumberTool.doubleT(hmInfo.remove("HANDICAP_PROFIT_T_")));
				hmInfo.put("AMOUNT_", NumberTool.doubleT(hmInfo.remove("AMOUNT_T_")));
				data.put("hmInfo", hmInfo);

				if (IbmStateEnum.LOGIN.equal(hmInfo.get("STATE_"))) {
					// 登录-个人盈利信息
					Map<String, Object> profitInfo = IbmHmProfitDefine.getProfitInfo(handicapMemberId);
					data.putAll(profitInfo);

					//盘口游戏信息获取
					data.put("gameInfo", new IbmHmGameSetService().listGameInfo(handicapMemberId));

				}
			}

			// 盘口会员基础设置
			Map<String, Object> hmSetInfo = new IbmHmSetService().findShowInfo(handicapMemberId);
			if (ContainerTool.notEmpty(hmInfo)) {
				hmSetInfo.put("BET_RATE_", NumberTool.doubleT(hmSetInfo.remove("BET_RATE_T_")) * 100);
				data.put("hmSetInfo", hmSetInfo);
			}

			bean.success(data);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("开启会员盘口错误"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
