package com.ibm.old.v1.pc.ibm_cms_topic.t.action;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_cms_topic.t.service.IbmPcCmsTopicTService;

/**
 * 
 * 
 * @Description: 删除所有消息
 * @ClassName: IbmCmsTopicDelAllAction
 * @date 2019年1月22日 下午3:17:23 
 * @author wck
 * @Email: 810160078@qq.com
 * @Copyright (c) 2014-2016 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class IbmCmsTopicDelAllAction extends BaseAppAction{

	@Override
	public String run() throws Exception {
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
			IbmPcCmsTopicTService cmsTopicTService = new IbmPcCmsTopicTService();
			cmsTopicTService.delAll(appUserId);
			
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN+"用户全部消息删除失败",e);
			throw e;
		}
		return returnJson(jrb);
	}

}
