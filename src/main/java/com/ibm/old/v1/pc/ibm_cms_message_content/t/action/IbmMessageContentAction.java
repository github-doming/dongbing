package com.ibm.old.v1.pc.ibm_cms_message_content.t.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_cms_message_content.t.service.IbmCmsMessageContentTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import org.doming.core.tools.StringTool;

import java.util.Map;
/**
 * @Description: 系统消息内容
 * @Author: zjj
 * @Date: 2019-06-10 15:54
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmMessageContentAction extends BaseAppAction {


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
		String messageContentId = BeanThreadLocal.find(dataMap.get("IBM_CMS_MESSAGE_CONTENT_ID_"), "");
		if(StringTool.isEmpty(messageContentId)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}

		try {
			//根据ID查询主题
			IbmCmsMessageContentTService messageContentTService=new IbmCmsMessageContentTService();
			Map<String, Object> map =messageContentTService.findContentById(messageContentId,this.getClass().getName());

			jrb.setData(map);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN+"获取消息失败",e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return returnJson(jrb);
	}
}
