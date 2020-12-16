package com.ibm.old.v1.pc.ibm_cms_message_content.t.action;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_cms_message_content.t.service.IbmCmsMessageContentTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
/**
 * @Description: 删除所有消息
 * @Author: zjj
 * @Date: 2019-06-10 16:22
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmMessageDelAllAction extends BaseAppAction {


	@Override public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadLocalJrb = LogThreadLocal.findLog();
		if(!threadLocalJrb.isSuccess()){
			return returnJson(threadLocalJrb);
		}

		if(appUserT == null){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(jrb);
		}
		try {
			//删除全部消息
			IbmCmsMessageContentTService messageContentTService=new IbmCmsMessageContentTService();
			messageContentTService.delAll(appUserId,this.getClass().getName());

			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN+"用户全部消息删除失败",e);
			throw e;
		}
		return returnJson(jrb);
	}
}
