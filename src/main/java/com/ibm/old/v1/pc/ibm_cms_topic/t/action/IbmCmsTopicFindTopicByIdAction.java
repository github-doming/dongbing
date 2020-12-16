package com.ibm.old.v1.pc.ibm_cms_topic.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_cms_topic.t.service.CmsPcTopicService;
import com.ibm.old.v1.pc.ibm_cms_topic.t.service.IbmPcCmsTopicTService;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * 
 * 
 * @Description: 通过ID查询消息内容
 * @ClassName: IbmCmsTopicFindTopicById
 * @date 2019年1月16日 上午9:15:21 
 * @author wck
 * @Email: 810160078@qq.com
 * @Copyright (c) 2014-2016 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class IbmCmsTopicFindTopicByIdAction extends BaseAppAction{

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

		String topicId = BeanThreadLocal.find(dataMap.get("TOPIC_ID_"), "");
		if(StringTool.isEmpty(topicId)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		
		try {
			//根据ID查询主题
			CmsPcTopicService cmsTopicWService = new CmsPcTopicService();
			Map<String, Object> map = cmsTopicWService.findTopicById(topicId);
			
			//修改状态为已读
			IbmPcCmsTopicTService cmsTopicTService = new IbmPcCmsTopicTService();
			cmsTopicTService.updateReadState(appUserId,topicId);
			
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
