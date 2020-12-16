package com.ibm.follow.connector.admin.manage.base.handicap.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.HandicapGameUtil;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * @Description: 盘口游戏初始化内存信息
 * @Author: null
 * @Date: 2020-05-09 11:04
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/game/init")
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
			//发消息到MQ端
			JSONObject data = new JSONObject();
			data.put("command", IbmMethodEnum.ADMIN_INFO.name());
			data.put("requestType", IbmStateEnum.INIT.name());
			String resultStr = RabbitMqTool.sendConfigReceipt(data.toString(), "set");
			if (StringTool.isEmpty(resultStr)) {
				bean.putEnum(IbmCodeEnum.IBM_403_MQ);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			if (!Boolean.parseBoolean(resultStr)) {
				bean.putEnum(IbmCodeEnum.IBM_403_MQ);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			HandicapGameUtil.initInfo();
			bean.success();
		} catch (Exception e) {
			log.error("盘口游戏初始化内存信息错误", e);
			bean.error(e.getMessage());
		}

		return bean.toJsonString();
	}
}
