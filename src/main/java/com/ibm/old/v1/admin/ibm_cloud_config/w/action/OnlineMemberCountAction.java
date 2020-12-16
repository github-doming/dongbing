package com.ibm.old.v1.admin.ibm_cloud_config.w.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_cloud_config.t.service.IbmCloudConfigTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import org.doming.core.tools.StringTool;

/**
 * 
 * 
 * @Description: 设置全局盘口会员最大在线数
 * @date 2019年3月12日 下午3:40:56 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class OnlineMemberCountAction extends BaseAppAction{

	@Override
	public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb= LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()){
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_404_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_404);
			return this.returnJson(jrb);
		}
		//会员最大在线数
		String onlineNumberMax = BeanThreadLocal.find(dataMap.get("ONLINE_NUMBER_MAX"), "");
		if(StringTool.isEmpty(onlineNumberMax)){
			jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			jrb.putSysEnum(IbmCodeEnum.CODE_404);
			return this.returnJson(jrb);
		}
		
		try {
			IbmCloudConfigTService configTService = new IbmCloudConfigTService();
			configTService.updateOnlineNumberCount(onlineNumberMax,this.getClass().getName());
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN,e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
		}
		return returnJson(jrb);
	}

}
