package com.ibm.old.v1.app.t.action;
import all.app.common.service.AppAccountService;
import all.gen.app_account.t.entity.AppAccountT;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: 修改密码
 * @Author: zjj
 * @Date: 2019-05-05 10:45
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppUpdatePasswordAction extends BaseAppAction {

	@Override
	public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if(!threadJrb.isSuccess()){
			return returnJson(threadJrb);
		}

		if(appUserT == null){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(jrb);
		}

		//旧密码
		String oldPassword = BeanThreadLocal.find(dataMap.get("oldPassword"), "");
		//新密码
		String newPassword = BeanThreadLocal.find(dataMap.get("newPassword"), "");
		if(StringTool.isEmpty(oldPassword,newPassword)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(jrb);
		}

		Date nowTime = new Date();

		try {
			//验证密码是否出错
			AppAccountService accountTService = new AppAccountService();
			AppAccountT accountT = accountTService.findAppAccountByPassword(appUserId, oldPassword);
			if(accountT==null){
				jrb.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				jrb.putSysEnum(IbmCodeEnum.CODE_403);
				return returnJson(jrb);
			}

			if(oldPassword.equals(newPassword)){
				jrb.putEnum(IbmCodeEnum.IBM_404_PASSWORD_NOT_DIFFERENT);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(jrb);
			}

			String commLocalASE = BeanThreadLocal
					.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "false");
			if ("true".equals(commLocalASE)) {
				String key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"), "");
				newPassword = CommASEUtil.encode(key, newPassword.trim()).trim();
			}
			//修改密码
			accountT.setPassword(newPassword);
			accountT.setUpdateUser(appUserT.getAppUserName());
			accountT.setUpdateTime(nowTime);
			accountT.setUpdateTimeLong(nowTime.getTime());
			accountTService.update(accountT);

			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN+"修改密码失败",e);
			throw e;
		}
		return returnJson(jrb);
	}
}
