package com.ibm.follow.connector.admin.manage3.handicap.list.game;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.List;
import java.util.Map;
/**
 * @Description: 获取盘口中不存在的游戏
 * @Author: Dongming
 * @Date: 2019-11-05 13:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/game/no", method = HttpConfig.Method.GET) public class HandicapGameNoAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String handicapId = request.getParameter("handicapId");
		if (StringTool.isEmpty(handicapId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			List<Map<String, Object>> gameInfos = new IbmAdminHandicapGameService().listNoGame(handicapId);
			if (ContainerTool.isEmpty(gameInfos)){
				return bean.fail("已添加所有游戏，请勿重复添加。");
			}
			bean.success(JSON.toJSON(gameInfos));
		} catch (Exception e) {
			log.error("删除盘口游戏错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
