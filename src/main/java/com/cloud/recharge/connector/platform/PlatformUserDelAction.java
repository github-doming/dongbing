package com.cloud.recharge.connector.platform;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.entity.CardAdmin;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_operate_log.entity.CardOperateLog;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.user.app_user.service.AppUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 删除平台用户 用户有子代理的不能删除
 * @Author: null
 * @Date: 2020-09-25 13:58
 */
@ActionMapping(value = "/cloud/recharge/pc/platform/del")
public class PlatformUserDelAction extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String appUserId = StringTool.getString(dataMap.get("appUserId"), "");
		if (StringTool.isEmpty(appUserId)) {
			return bean.put401Data();
		}
		try {
			Date nowTime = new Date();
			CardAdminService adminService = new CardAdminService();
			CardAdmin editUser = adminService.findUserByUserId(appUserId);
			if (editUser == null) {
				return bean.put401Data();
			}
			if(adminService.hasSubUser(editUser.getUserPath())){
				bean.putEnum(CodeEnum.CLOUD_403_EXIST_SUB);
				bean.putSysEnum(CodeEnum.CLOUD_403_EXIST_SUB);
				return bean;
			}

			// 删除价格信息
			new CardAdminPriceService().delByUserId(appUserId,nowTime);
			// 删除账号信息
			new AppUserService().delUser(appUserId, nowTime);
			// 保存操作log
			saveLog(nowTime,editUser.getUserName());
			bean.success();
		} catch (Exception e) {
			log.error("删除用户出错",e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	private void saveLog(Date nowTime,String userName) throws Exception {
		String format ="删除用户{%s}";
		CardOperateLog log = new CardOperateLog();
		log.setUserId(user.getAppUserId());
		log.setUserName(user.getUserName());
		log.setUserPath(user.getUserPath());
		log.setOpertType("DELETE");
		log.setIp(findServletIp());
		log.setOpertContent(String.format(format,userName));
		log.setCreateTime(nowTime);
		log.setCreateTimeLong(System.currentTimeMillis());
		log.setUpdateTime(nowTime);
		log.setUpdateTimeLong(System.currentTimeMillis());
		log.setState(StateEnum.OPEN.name());
		new CardOperateLogService().save(log);
	}
}
