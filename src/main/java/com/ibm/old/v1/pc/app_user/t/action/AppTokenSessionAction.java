package com.ibm.old.v1.pc.app_user.t.action;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.pc.app_user.t.controller.AppTokenController;
/**
 * @Description: 得到session
 * @Author: zjj
 * @Date: 2019-05-05 10:41
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppTokenSessionAction extends BaseAppAction {

	@Override public String run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			//获取一个新的用户session
			AppTokenController controller = new AppTokenController();
			String sessionId = controller.findNewAppSession();
			bean.setData(sessionId);
			bean.success();
		} catch (Exception e) {
			log.error("获取session失败，", e);
			throw e;
		}
		return this.returnJson(bean);
	}
}
