package com.ibm.old.v1.admin.app_user.w.action;

import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.wck.init.InitUserDataInfo;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;
import org.doming.core.tools.StringTool;

/**
 * 
 * 
 * @Description: 修改用户等级
 * @date 2019年2月19日 上午10:22:24 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class ModifyUserTypeAction extends BaseAppAction{

	@Override
	public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();

		//角色CODE
		String roleCode = request.getParameter("roleCode");
		appUserId = request.getParameter("userId");
		if(StringTool.isEmpty(roleCode)){
			jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			jrb.putSysEnum(IbmCodeEnum.CODE_404);
			return this.returnJson(jrb);
		}
		
		try {
			//修改用户等级
			AppUserService userService = new AppUserService();
			int count = userService.updateUserType(roleCode, appUserId);
			if(count==1){
				//更新用户信息
				new InitUserDataInfo(appUserId);
				jrb.success();
			}else{
				throw new RuntimeException("修改用户组失败");
			}
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN,e);
			throw e;
		}
		return returnJson(jrb);
	}

}
