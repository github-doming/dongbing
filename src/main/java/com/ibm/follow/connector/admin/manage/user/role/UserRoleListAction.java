package com.ibm.follow.connector.admin.manage.user.role;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.servlet.cloud.ibm_exp_role.service.IbmExpRoleService;
import org.doming.core.common.servlet.ActionMapping;

import java.util.List;
import java.util.Map;
/**
 * 用户角色列表
 *
 * @Author: Dongming
 * @Date: 2020-05-07 11:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/role/list") public class UserRoleListAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		try {
			IbmExpRoleService expRoleService = new IbmExpRoleService();

			List<Map<String, Object>> showInfos = expRoleService.listShow();

			for (Map<String,Object> showInfo:showInfos){
				String[] codes = showInfo.remove("GAME_CODES_").toString().split(",");
				StringBuilder name = new StringBuilder();
				for (String code :codes){
					name.append(BaseGameUtil.Code.valueOf(code).getName()).append(",");
				}
				showInfo.put("GAME_NAMES_",name.toString());

				codes = showInfo.remove("MEMBER_HANDICAP_CODES_").toString().split(",");
				name.delete(0,name.length());
				for (String code :codes){

					name.append(BaseHandicapUtil.Code.valueOf(code).getName()).append(",");
				}
				showInfo.put("MEMBER_HANDICAP_NAMES_",name.toString());

				codes = showInfo.remove("AGENT_HANDICAP_CODES_").toString().split(",");
				name.delete(0,name.length());
				for (String code :codes){
					name.append(BaseHandicapUtil.Code.valueOf(code).getName()).append(",");
				}
				showInfo.put("AGENT_HANDICAP_NAMES_",name.toString());
			}

			bean.success(showInfos);
		} catch (Exception e) {
			log.error("用户角色列表错误", e);
			return bean.error(e.getMessage());
		}
		return bean;

	}
}
