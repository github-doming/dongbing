package com.ibs.plan.connector.admin.manage.base.handicap.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import org.doming.core.common.servlet.ActionMapping;

/**
 * @Description: 盘口游戏初始化内存信息
 * @Author: null
 * @Date: 2020-05-09 11:04
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/handicap/game/init")
public class HandicapGameInitAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {

			bean.success();
		} catch (Exception e) {
			log.error("盘口游戏初始化内存信息错误", e);
			bean.error(e.getMessage());
		}

		return bean.toString();
	}
}
