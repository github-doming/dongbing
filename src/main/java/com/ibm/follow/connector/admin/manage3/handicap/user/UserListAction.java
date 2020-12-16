package com.ibm.follow.connector.admin.manage3.handicap.user;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 盘口用户列表
 * @Author: Dongming
 * @Date: 2019-11-08 09:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/user/list", method = HttpConfig.Method.GET) public class UserListAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}
		String handicapCategory = request.getParameter("HANDICAP_CATEGORY_");
		String handicapCode = request.getParameter("HANDICAP_CODE_");
		String handicapId = null;
		String key = request.getParameter("key");

		try {
			Map<String, Object> data = new HashMap<>(6);

			//盘口
			List<Map<String, Object>> handicaps = new ArrayList<>();
			for (HandicapUtil.Code code : HandicapUtil.codes()) {
				Map<String, Object> handicap = new HashMap<>(2);
				handicap.put("code", code.name());
				handicap.put("name", code.getName());
				handicaps.add(handicap);
			}
			data.put("handicaps", handicaps);

			//类别
			List<Map<String, Object>> categories = new ArrayList<>(2);
			categories.add(HandicapTool.getTypeMap(IbmTypeEnum.AGENT));
			categories.add(HandicapTool.getTypeMap(IbmTypeEnum.MEMBER));
			data.put("categories", categories);

			//展示信息
			IbmTypeEnum category = IbmTypeEnum.valueCustomerTypeOf(handicapCategory);
			IbmAdminHandicapUserService handicapUserService = new IbmAdminHandicapUserService();

			List<Map<String, Object>> handicapUsers;
			if (category == null) {
				if (StringTool.notEmpty(handicapCode)) {
					handicapId = HandicapUtil.id(handicapCode, IbmTypeEnum.AGENT);
				}
				handicapUsers = handicapUserService.listShow(IbmTypeEnum.AGENT, handicapId, key);
				if (StringTool.notEmpty(handicapCode)) {
					handicapId = HandicapUtil.id(handicapCode, IbmTypeEnum.MEMBER);
				}
				handicapUsers.addAll(handicapUserService.listShow(IbmTypeEnum.MEMBER, handicapId, key));
			}else {
				if (StringTool.notEmpty(handicapCode)) {
					handicapId = HandicapUtil.id(handicapCode, category);
				}
				handicapUsers = handicapUserService.listShow(category, handicapId, key);
			}

			data.put("handicapUsers", handicapUsers);

			//回显数据
			data.put("HANDICAP_CATEGORY_", handicapCategory);
			data.put("HANDICAP_CODE_", handicapCode);
			data.put("key", key);
			return new ModelAndView("/pages/com/ibm/admin/manager/handicap/user.jsp", data);
		} catch (Exception e) {
			log.error("盘口用户列表错误", e);
			return new JsonResultBeanPlus().error(e.getMessage());
		}
	}
}
