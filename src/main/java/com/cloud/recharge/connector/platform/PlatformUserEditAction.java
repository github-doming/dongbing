package com.cloud.recharge.connector.platform;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.common.tool.EncryptTool;
import com.cloud.recharge.card_admin.entity.CardAdmin;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_operate_log.entity.CardOperateLog;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.user.app_account.service.AppAccountService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 平台用户修改
 * @Author: admin1
 * @Date: 2020-09-26 10:27
 */
@ActionMapping(value = "/cloud/recharge/pc/platform/edit")
public class PlatformUserEditAction extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String subUserId = StringTool.getString(dataMap, "appUserId", "");
		String userPassWord = StringTool.getString(dataMap, "password", "");
		String isAdd = StringTool.getString(dataMap, "isAdd", "false");
		String userState = StringTool.getString(dataMap, "userState", "");
		String desc = StringTool.getString(dataMap.get("desc"), "");
		if (StringTool.notEmpty(userPassWord)) {
			String regExpPwd = "[a-zA-Z](?![a-zA-Z]+$)\\w{5,20}";
			if (!userPassWord.matches(regExpPwd)) {
				bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
				bean.putSysEnum(ReturnCodeEnum.code409);
				return bean;
			}
		}
		try {
			Date nowTime = new Date();
			CardAdminService adminService = new CardAdminService();
			CardAdmin editUser = adminService.findUserByUserId(subUserId);
			if(editUser==null){
				bean.putEnum(CodeEnum.CLOUD_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			boolean flag = false;
			StringBuilder sb = new StringBuilder();
			if(StringTool.notEmpty(isAdd)){
				editUser.setIsAdd(Boolean.parseBoolean(isAdd));
				sb.append("允加子代:").append(isAdd).append(",");
				flag = true;
			}
			if(StringTool.notEmpty(userState)&&!userState.equalsIgnoreCase(editUser.getUserState())){
				editUser.setUserState(userState);
				sb.append("用户状态:").append(userState).append(",");
				flag = true;
			}
			if(StringTool.notEmpty(desc)){
				if(desc.length()>20){
					desc=desc.substring(0,20);
				}
				editUser.setDesc(desc);
				sb.append("备注:").append(desc).append(",");
				flag = true;
			}
			if(flag){
				editUser.setUpdateTime(nowTime);
				editUser.setUpdateTimeLong(System.currentTimeMillis());
				adminService.update(editUser);
			}
			if (StringTool.notEmpty(userPassWord)) {
				userPassWord = EncryptTool.encode(EncryptTool.Type.ASE,userPassWord);
				//修改密码
				new AppAccountService().update(subUserId, userPassWord, userId);
				sb.append("密码").append(",");
				flag = true;
			}
			if(flag){
				sb.deleteCharAt(sb.lastIndexOf(","));
				saveLog(nowTime,sb.toString(),editUser.getUserName());
			}

			bean.success();
		} catch (Exception e) {
			log.error("修改充值卡管理员基本信息出错",e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	private void saveLog(Date nowTime,String msg,String userName) throws Exception {
		String format ="修改{%s}的用户信息,修改内容:{%s}";
		CardOperateLog log = new CardOperateLog();
		log.setUserId(user.getAppUserId());
		log.setUserName(user.getUserName());
		log.setUserPath(user.getUserPath());
		log.setOpertType("UPDATE");
		log.setIp(findServletIp());
		log.setOpertContent(String.format(format,userName,msg));
		log.setCreateTime(nowTime);
		log.setCreateTimeLong(System.currentTimeMillis());
		log.setUpdateTime(nowTime);
		log.setUpdateTimeLong(System.currentTimeMillis());
		log.setState(StateEnum.OPEN.name());
		new CardOperateLogService().save(log);
	}
}
