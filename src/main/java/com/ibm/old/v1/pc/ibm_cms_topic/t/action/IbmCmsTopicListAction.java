package com.ibm.old.v1.pc.ibm_cms_topic.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_cms_topic.t.service.IbmPcCmsTopicTService;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @Description: 接收消息
 * @ClassName: IbmCmsTopicListAction
 * @date 2019年1月15日 下午5:58:30 
 * @author wck
 * @Email: 810160078@qq.com
 * @Copyright (c) 2014-2016 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class IbmCmsTopicListAction extends BaseAppAction{

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

		String pageIndex = BeanThreadLocal.find(dataMap.get("pageIndex"), "1");
		String pageSize = BeanThreadLocal.find(dataMap.get("pageSize"), "10");
		
		try {
			
			Map<String, Object> data = new HashMap<>(2);
			//通过用户ID查询消息
			IbmPcCmsTopicTService ibmCmsTopicTService = new IbmPcCmsTopicTService();
			PageCoreBean pageCore = ibmCmsTopicTService.listByUserId(appUserId, Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
			//未读消息数
			String unReadCount = ibmCmsTopicTService.findCountByReadState(appUserId);
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
