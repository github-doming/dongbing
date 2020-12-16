package com.ibm.follow.connector.admin.manage3.handicap.list.game;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapGameService;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 查看盘口游戏列表
 * @Author: Dongming
 * @Date: 2019-11-04 16:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/game/list1", method = HttpConfig.Method.GET) public class HandicapGameListAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}
		String handicapId = request.getParameter("handicapId");
		if (StringTool.isEmpty(handicapId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			//盘口信息
			Map<String, Object> handicapInfo = new IbmAdminHandicapService().findInfo(handicapId);
			if (ContainerTool.isEmpty(handicapInfo)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			//盘口游戏信息
			List<Map<String, Object>> handicapGameInfos = new IbmAdminHandicapGameService().listInfoByHandicapId(handicapId);

			Map<String, Object> data = new HashMap<>(2);
			data.put("handicapInfo", handicapInfo);
			data.put("handicapGameInfos", handicapGameInfos);
			bean.success(data);
		} catch (Exception e) {
			log.error("查看盘口游戏列表错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
