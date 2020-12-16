package com.cloud.recharge.connector.account;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.user.app_account.service.AppAccountService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * 修改充值卡管理员基本信息
 *
 * @Author: Dongming
 * @Date: 2020-06-17 18:51
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/account/edit", method = HttpConfig.Method.POST)
public class CardAccountInfoEditAction extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String subUserId = StringTool.getString(dataMap, "subUserId", "");
		String userPassWord = StringTool.getString(dataMap, "password", "");
		String isAdd = StringTool.getString(dataMap, "isAdd", "false");
		String userState = StringTool.getString(dataMap, "userState", "");
		String userName = StringTool.getString(dataMap.get("nikeName"), "");
		if (StringTool.isEmpty(subUserId)) {
			subUserId = userId;
		}
		if (StringTool.notEmpty(userPassWord)) {
			String regExpPwd = "[a-zA-Z](?![a-zA-Z]+$)\\w{5,20}";
			if (!userPassWord.matches(regExpPwd)) {
				bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
				bean.putSysEnum(ReturnCodeEnum.code409);
				return bean;
			}
		}
		try {
			//加载查询人资料
			CardAdminService adminService = new CardAdminService();
			if (adminService.hasEditPermission(bean, subUserId, userId)) {
				return bean;
			}
			Map<String, Object> data = (Map<String, Object>) bean.getData();
			if (!(boolean) data.get("isSelf")) {
				Boolean isAddBool = null;
				if (StringTool.notEmpty(isAdd)) {
					isAddBool = Boolean.parseBoolean(isAdd);
				}
				adminService.update(subUserId, userName, isAddBool, userState);
			}
			if (StringTool.notEmpty(userPassWord)) {
				//修改密码
				new AppAccountService().update(subUserId, userPassWord, userId);
			}
			// 保存操作log
			new CardOperateLogService().save(userId, findServletIp(),userName,"UPDATE", JSONObject.toJSONString(dataMap), new Date());
			bean.success();
		} catch (Exception e) {
			log.error("修改充值卡管理员基本信息出错");
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
