package com.ibm.follow.connector.admin.manage3.handicap.list.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
/**
 * @Description: 修改盘口游戏信息
 * @Author: Dongming
 * @Date: 2019-11-04 18:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/game/update", method = HttpConfig.Method.POST) public class HandicapGameUpdateAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}
		String handicapGameId = request.getParameter("HANDICAP_GAME_ID_");
		String gameName = request.getParameter("GAME_NAME");
		String type = request.getParameter("TYPE");
        String closeTimeStr = request.getParameter("CLOSE_TIME");
		String tableName = request.getParameter("TABLE_NAME");
		String icon = request.getParameter("ICON");
		String snStr = request.getParameter("SN");

		if (StringTool.isEmpty(handicapGameId, snStr, closeTimeStr,type)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			int closeTime = NumberTool.getInteger(closeTimeStr);
			int sn = NumberTool.getInteger(snStr);
            new IbmAdminHandicapGameService().updateGameInfo(handicapGameId, closeTime, sn, icon, tableName, gameName,type,adminUser.getUserName().concat(",修改盘口游戏"));

			bean.success();
		} catch (Exception e) {
			log.error("修改盘口游戏信息错误");
			throw e;
		}
		return bean;
	}
}
