package com.cloud.recharge.connector.platform;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.connector.core.BaseUserAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 平台用户列表
 * @Author: null
 * @Date: 2020-09-25 10:48
 */
@ActionMapping(value = "/cloud/recharge/pc/platform/userList")
public class PlatformUserListAction extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		String userState = StringTool.getString(dataMap.get("userState"), "");
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);

		Map<String, Object> data = new HashMap<>(3);
		try {
			CardAdminService adminService = new CardAdminService();
			//获取全部用户数据 ID-用户名
			Map<String,Object> userInfos = adminService.userInfo();
			//用户列表
			PageCoreBean<Map<String, Object>> pageCoreBean = adminService.getAgentInfo(userState,user.getUserPath(),pageIndex,pageSize);
			List<Map<String, Object>> agentInfos = pageCoreBean.getList();
			String parentId,parentUser;
			for (Map<String, Object> agentInfo: agentInfos){
				parentId = agentInfo.get("PARENT_USER_ID_").toString();
				parentUser = "ADMIN".equalsIgnoreCase(parentId) ? parentId : userInfos.get(parentId).toString();
				agentInfo.put("PARENT_USER_",parentUser);

			}
			//回传数据
			data.put("rows",agentInfos );
			data.put("index", pageIndex);
			data.put("total", pageCoreBean.getTotalCount());
		} catch (Exception e) {
			log.error("平台用户列表出错", e);
			data.clear();
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}
}
