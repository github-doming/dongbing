package com.ibm.old.v1.admin.app_user.w.action;

import c.x.platform.root.common.action.BaseAction;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Description: 查询所有盘口
 * @date 2019年2月21日 上午10:40:05 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class AppUserWListJsonAction extends BaseAction{

	@Override
	public String execute() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		try {
			AppUserService appUserService = new AppUserService();
//			List<Map<String, Object>> map = appUserService.findAll();
//
//			jrb.setData(map);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN+"获取用户失败",e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
		}
		return this.returnJson(jrb);
	}

}
