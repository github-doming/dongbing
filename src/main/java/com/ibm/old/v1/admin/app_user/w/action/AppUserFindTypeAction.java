package com.ibm.old.v1.admin.app_user.w.action;

import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;

import java.util.List;


/**
 * 
 * 
 * @Description: 查找用户组
 * @date 2019年2月19日 下午6:20:11 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class AppUserFindTypeAction extends BaseAppAction{

	@Override
	public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		
		try {
			//查找用户组
			AppUserService appUserService = new AppUserService();
			List<String> list = appUserService.findType();
			jrb.setData(list);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN,e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
		}
		return returnJson(jrb);
	}

}
