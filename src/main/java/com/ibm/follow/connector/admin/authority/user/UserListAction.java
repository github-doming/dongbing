package com.ibm.follow.connector.admin.authority.user;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.connector.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作人列表
 *
 * @Author: Dongming
 * @Date: 2020-04-06 15:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/authority/user/list")
public class UserListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		String userName = StringTool.getString(dataMap, "userName", null);
		Integer pageIndex = NumberTool.getInteger(dataMap, "pageIndex", 0);
		Integer pageSize = NumberTool.getInteger(dataMap, "pageSize", 15);
		Map<String, Object> data = new HashMap<>(3);
		try {
			// 列出用户可视的角色列表
			AuthorityService authorityService = new AuthorityService();
			PageCoreBean<Map<String, Object>> basePage = authorityService.listAllUser(adminUser.getUserId(), userName, pageIndex, pageSize);
			List<Map<String, Object>> userList = basePage.getList();
			for (Map<String,Object> userInfo :userList){
				Map<String,Object> loginInfo = authorityService.findLoginInfo(userInfo.get("APP_USER_ID_").toString());
				if (ContainerTool.notEmpty(loginInfo)){
					userInfo.putAll(loginInfo);
				}
			}

			//回传数据
			data.put("rows", userList);
			data.put("index", pageIndex);
			data.put("size", pageSize);
			data.put("total", basePage.getTotalCount());

			return data;
		} catch (Exception e) {
			log.error("操作人列表页面错误", e);
			return bean.error(e.getMessage());
		}
	}
}
