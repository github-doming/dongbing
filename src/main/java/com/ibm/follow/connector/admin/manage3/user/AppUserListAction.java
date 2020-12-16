package com.ibm.follow.connector.admin.manage3.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapUserService;
import com.ibm.follow.connector.admin.manage3.handicap.user.IbmAdminAppUserService;
import com.ibm.follow.servlet.cloud.ibm_manage_point.service.IbmManagePointService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.tools.ContainerTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @deprecated
 * @Description: 用户列表
 * @Author: Dongming
 * @Date: 2019-11-07 14:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/list2")
public class AppUserListAction extends CommAdminAction {

	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String userType = request.getParameter("USER_TYPE_");
		String key = request.getParameter("key");

		try {
			Map<String, Object> data = new HashMap<>(4);
			//类型
			List<Map<String, Object>> types = new ArrayList<>(2);
			types.add(HandicapTool.getTypeMap(IbmTypeEnum.FREE));
			types.add(HandicapTool.getTypeMap(IbmTypeEnum.CHARGE));
			types.add(HandicapTool.getTypeMap(IbmTypeEnum.ADMIN));
			types.add(HandicapTool.getTypeMap(IbmTypeEnum.SYS));
			data.put("types", types);

			List<Map<String, Object>> userInfos = new IbmAdminAppUserService().listShow(userType, key);
			IbmManagePointService pointService = new IbmManagePointService();
			IbmManageTimeService timeService = new IbmManageTimeService();
			IbmAdminHandicapUserService handicapUserService = new IbmAdminHandicapUserService();
			List<String> handicapNames;

			//用户信息
			for (Map<String, Object> userInfo : userInfos) {
				IbmTypeEnum type = IbmTypeEnum.valueOf(userInfo.remove("APP_USER_TYPE_").toString());
				userInfo.put("APP_USER_TYPE_", type.getMsg());
				String userId = userInfo.get("APP_USER_ID_").toString();

				//可用积分-结束时间
				userInfo.put("USEABLE_POINT_", pointService.getUseablePoint(userId));
				userInfo.put("END_TIME_", timeService.getEndTime(userId));

				//拥有代理盘口
				handicapNames = handicapUserService.listHandicapNameByUserId(IbmTypeEnum.AGENT, userId);
				if (ContainerTool.notEmpty(handicapNames)) {
					userInfo.put("AGENT_HANDICAP_", String.join(",", handicapNames));
				}

				//拥有会员盘口
				handicapNames = handicapUserService.listHandicapNameByUserId(IbmTypeEnum.MEMBER, userId);
				if (ContainerTool.notEmpty(handicapNames)) {
					userInfo.put("MEMBER_HANDICAP_", String.join(",", handicapNames));
				}

			}
			data.put("userInfos", userInfos);

			//回显数据
			data.put("USER_TYPE_", userType);
			data.put("key", key);

			return new ModelAndView("/pages/com/ibm/admin/manager/user/list.jsp", data);
		} catch (Exception e) {
			log.error("错误", e);
			return new JsonResultBeanPlus().error(e.getMessage());
		}

	}
}
