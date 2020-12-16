package com.ibm.old.v1.pc.ibm_cms_message_content.t.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_cms_message_content.t.service.IbmCmsMessageContentTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 系统消息列表
 * @Author: zjj
 * @Date: 2019-06-06 17:49
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmMessageListAction extends BaseAppAction {


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

		String pageIndex = BeanThreadLocal.find(dataMap.get("pageIndex"), "1");
		String pageSize = BeanThreadLocal.find(dataMap.get("pageSize"), "10");

		try {

			IbmCmsMessageContentTService messageContentTService=new IbmCmsMessageContentTService();
			//未读消息数
			String unReadCount =messageContentTService.findUnreadCount(appUserT.getAppUserId());
			//通过用户ID查询消息
			PageCoreBean pageCore = messageContentTService.findByUserId(appUserId, Integer.parseInt(pageIndex), Integer.parseInt(pageSize));


			Map<String, Object> data = new HashMap<>(2);

			data.put("pageCore", pageCore);
			data.put("unReadcount", unReadCount);

			jrb.setData(data);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN+"获取消息失败",e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
		}
		return returnJson(jrb);
	}
}
