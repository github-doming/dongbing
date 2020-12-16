package com.ibm.follow.connector.admin.manage3.user.handicap;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;
/**
 * @Description: 查找不存在的盘口
 * @Author: Dongming
 * @Date: 2019-11-11 14:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/handicap/no")
public class HandicapNoAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String appUserId = request.getParameter("appUserId");
		String userCategory = request.getParameter("userCategory");
		if (StringTool.isEmpty(appUserId,userCategory)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		IbmTypeEnum category = IbmTypeEnum.valueOf(userCategory);
		try {
			//获取用户没有的盘口信息
			IbmAdminHandicapUserService handicapUserService = new IbmAdminHandicapUserService();
			List<Map<String, Object>> handicapInfos = handicapUserService.listNoHandicap(appUserId,category);
			if (ContainerTool.isEmpty(handicapInfos)){
				return bean.fail("已添加所有盘口，请勿重复添加。");
			}
			bean.success(handicapInfos);
		} catch (Exception e) {
			log.error("查找不存在的盘口错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
