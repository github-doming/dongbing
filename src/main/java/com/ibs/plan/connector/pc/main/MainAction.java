package com.ibs.plan.connector.pc.main;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import com.ibs.plan.module.cloud.ibsp_sys_manage_time.service.IbspSysManageTimeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 首页 加载主体框架
 *
 * @Author: null
 * @Date: 2020-05-23 11:21
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/home/main", method = HttpConfig.Method.GET) public class MainAction
		extends CommCoreAction {
	public MainAction() {
		super.isTime = false;
	}
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		try {
			Map<String, Object> data = new HashMap<>(3);

			// 会员盘口
			List<Map<String, Object>> memberHandicapInfos = new IbspHmUserService().listHandicapShowInfo(appUserId);
			data.put("memberHandicapInfos", memberHandicapInfos);

			//游戏列表
			List<Map<String, Object>> gameInfos = new IbspExpUserService().listGameShowInfo(appUserId);
			data.put("gameInfos", gameInfos);

			// 用户信息
			data.put("endTimeLong", new IbspSysManageTimeService().getEndLongTime(appUserId));

			bean.success(data);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "加载主题框架错误", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
}
