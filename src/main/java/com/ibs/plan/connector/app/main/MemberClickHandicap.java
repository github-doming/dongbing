package com.ibs.plan.connector.app.main;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页，点击会员盘口
 *
 * @Author: Dongming
 * @Date: 2020-05-23 15:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/main/clickMember", method = HttpConfig.Method.GET) public class MemberClickHandicap
		extends CommCoreAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		Object handicapCode = dataMap.getOrDefault("HANDICAP_CODE_", "");
		if (StringTool.isEmpty(handicapCode)) {
			return bean.put401Data();
		}

		try {
			String handicapId =HandicapUtil.id(handicapCode.toString(),IbsTypeEnum.MEMBER);
			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			//获取在线会员主键数组
			String onlineMembersIds = new IbspHmUserService().findOnlineMembersId(appUserId, handicapId);
			Map<String, Object> data = new HashMap<>(2);
			List<Map<String, Object>> offlineInfo;
			if (StringTool.notEmpty(onlineMembersIds)) {
				//已托管会员
				List<Map<String, Object>> onlineInfo =  new IbspHmInfoService().listOnlineInfo(onlineMembersIds.split(","));
				data.put("onHostingInfo", onlineInfo);
				offlineInfo = new IbspHandicapMemberService().listOfflineInfo(appUserId, handicapId,
						ContainerTool.getValSet4Key(onlineInfo, "HANDICAP_MEMBER_ID_"));
			} else {
				offlineInfo = new IbspHandicapMemberService().listOfflineInfo(appUserId, handicapId, null);
			}
			data.put("offHostingInfo", offlineInfo);

			bean.success(data);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "点击会员盘口", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
