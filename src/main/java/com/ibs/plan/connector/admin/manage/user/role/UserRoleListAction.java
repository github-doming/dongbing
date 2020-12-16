package com.ibs.plan.connector.admin.manage.user.role;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.common.utils.PlanUtil;
import com.ibs.plan.module.cloud.ibsp_exp_role.service.IbspExpRoleService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

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
@ActionMapping(value = "/ibs/sys/manage/user/role/list",method = HttpConfig.Method.GET)
public class UserRoleListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		try {
			IbspExpRoleService expRoleService = new IbspExpRoleService();

			List<Map<String, Object>> showInfos = expRoleService.listShow();

			for (Map<String, Object> showInfo : showInfos) {
				String[] codes = showInfo.remove("GAME_CODES_").toString().split(",");
				StringBuilder name = new StringBuilder();
				for (String code : codes) {
					if(StringTool.notEmpty(code)){
						name.append(GameUtil.Code.valueOf(code).getName()).append(",");
					}
				}
				showInfo.put("GAME_NAMES_", name.toString());

				codes = showInfo.remove("HANDICAP_CODES_").toString().split(",");
				name.delete(0, name.length());
				for (String code : codes) {
					if(StringTool.notEmpty(code)){
						name.append(HandicapUtil.Code.valueOf(code).getName()).append(",");
					}
				}
				showInfo.put("HANDICAP_CODES_", name.toString());

				codes = showInfo.remove("PLAN_CODES_").toString().split(",");
				name.delete(0, name.length());
				for (String code : codes) {
					if(StringTool.notEmpty(code)){
						name.append(PlanUtil.Code.valueOf(code).getName()).append(",");
					}
				}
				showInfo.put("PLAN_CODES_", name.toString());

			}

			bean.success(showInfos);
		} catch (Exception e) {
			log.error("用户角色列表错误", e);
			return bean.error(e.getMessage());
		}
		return bean;

	}
}
