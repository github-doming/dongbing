package com.ibm.follow.connector.admin.manage3.handicap.list.game;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;
/**
 * @Description: 查看盘口游戏信息
 * @Author: Dongming
 * @Date: 2019-11-04 17:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/game/info", method = HttpConfig.Method.GET)
public class HandicapGameInfoAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String handicapGameId = request.getParameter("handicapGameId");
		if (StringTool.isEmpty(handicapGameId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			Map<String, Object> gameInfo = new IbmAdminHandicapGameService().findInfo(handicapGameId);
			if (ContainerTool.isEmpty(gameInfo)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			bean.success(gameInfo);
		} catch (Exception e) {
			log.error("查看盘口游戏列表错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
