package com.ibm.old.v1.admin.ibm_role.w.action;

import com.ibm.old.v1.cloud.ibm_role.w.service.IbmRoleWService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Description: 查询角色权限
 * @date 2019年3月12日 下午3:42:22 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class ModifyPowerAction extends BaseAppAction{

	@Override
	public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		//用户类型
		String userType = request.getParameter("userType");
		if(StringTool.isEmpty(userType)){
			jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			jrb.putSysEnum(IbmCodeEnum.CODE_404);
			return this.returnJson(jrb);
		}
		
		try {
			//查询角色等级
			IbmRoleWService roleWService = new IbmRoleWService();
			String roleLevel = roleWService.findLevel(userType);
			
			List<Map<String, Object>> list= roleWService.listRole(roleLevel);
			jrb.setData(list);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN,e);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
		}
		return returnJson(jrb);
	}

}
