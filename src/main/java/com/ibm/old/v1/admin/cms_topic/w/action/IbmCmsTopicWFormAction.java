package com.ibm.old.v1.admin.cms_topic.w.action;

import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_cms_topic.t.entity.IbmCmsTopicT;
import com.ibm.old.v1.cloud.ibm_cms_topic.t.service.IbmCmsTopicTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;

/**
 * @author wck
 * @Description: 查询消息信息
 * @ClassName: IbmCmsTopicWSaveAction
 * @date 2019年1月15日 下午5:59:15
 * @Email: 810160078@qq.com
 * @Copyright (c) 2014-2016 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class IbmCmsTopicWFormAction extends BaseAppAction {
	@Override public String run() throws Exception {
		String id = request.getParameter("id");
		if (id != null) {
			IbmCmsTopicTService ibmCmsTopicTService = new IbmCmsTopicTService();
			IbmCmsTopicT ibmCmsTopicT = ibmCmsTopicTService.find(id);
			request.setAttribute("s", ibmCmsTopicT);
			request.setAttribute("id", id);
		}
		return CommViewEnum.Default.toString();
	}

}
