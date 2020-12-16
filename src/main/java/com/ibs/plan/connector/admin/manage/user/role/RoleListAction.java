package com.ibs.plan.connector.admin.manage.user.role;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_exp_role.service.IbspExpRoleService;
import com.ibs.plan.module.cloud.ibsp_exp_user.entity.IbspExpUser;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.List;
import java.util.Map;

/**
 * @Description: 角色列表
 * @Author: null
 * @Date: 2020-08-18 14:29
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/role/roleList",method = HttpConfig.Method.GET)
public class RoleListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String appUserId = StringTool.getString(dataMap ,"appUserId", "");

		try {
			IbspExpRoleService expRoleService = new IbspExpRoleService();
			List<Map<String, Object>> showInfos = expRoleService.listRole();

			if (StringTool.notEmpty(appUserId)) {
				IbspExpUserService expUserService=new IbspExpUserService();
				IbspExpUser expUser=expUserService.findByUserId(appUserId);

				for (Map<String, Object> roleInfo : showInfos) {
					roleInfo.put("hasPlan",false);

					if(roleInfo.get("IBSP_EXP_ROLE_ID_").equals(expUser.getExpRoleId())){
						roleInfo.put("hasPlan",true);
					}
				}
			}
			bean.success(showInfos);
		} catch (Exception e) {
			log.error("获取角色列表", e);
			bean.error(e.getMessage());
		}
		return bean.toString();

	}
}
