package com.ibm.connector.core.user;
import all.app.common.service.AppAccountService;
import all.gen.app_account.t.entity.AppAccountT;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: 修改密码
 * @Author: Dongming
 * @Date: 2019-08-28 18:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class UpdatePasswordAction extends CommCoreAction {
	public UpdatePasswordAction() {
		super.isTime = false;
	}
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if(!threadJrb.isSuccess()){
			return returnJson(threadJrb);
		}
		//旧密码
		String oldPassword = dataMap.getOrDefault("oldPassword", "").toString().replace(" ","");
		//新密码
		String newPassword = dataMap.getOrDefault("newPassword", "").toString().replace(" ","");

		if(StringTool.isEmpty(oldPassword,newPassword)){
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}

		//密码比较
		if(oldPassword.equals(newPassword)){
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}

		try {
			//验证密码是否出错
			AppAccountService accountService = new AppAccountService();
			AppAccountT account = accountService.findAppAccountByPassword(appUserId, oldPassword);
			if(account==null){
				bean.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}

			//密码加密
			String commLocalAse = SysConfig.findInstance().findMap().getOrDefault("comm.local.ASE", "false").toString();
			if ("true".equals(commLocalAse)) {
				String key = SysConfig.findInstance().findMap().getOrDefault("comm.local.ASE_key", "").toString();
				newPassword = CommASEUtil.encode(key, newPassword.trim()).trim();
			}

			//修改密码
			account.setPassword(newPassword);
			account.setUpdateUser(appUser.getNickname());
			account.setUpdateTime(new Date());
			account.setUpdateTimeLong(System.currentTimeMillis());
			accountService.update(account);

			bean.success();
		} catch (Exception e) {
			log.error("获取session失败，", e);
			throw e;
		}

		return bean;
	}
}
