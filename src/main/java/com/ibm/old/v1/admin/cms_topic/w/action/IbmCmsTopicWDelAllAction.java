package com.ibm.old.v1.admin.cms_topic.w.action;

import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
import com.ibm.old.v1.cloud.ibm_cms_topic.t.service.IbmCmsTopicTService;

/**
 * 
 * 
 * @Description: 删除所有消息
 * @date 2019年3月12日 下午3:40:28 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmCmsTopicWDelAllAction extends BaseAction{

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		IbmCmsTopicTService ibmCmsTopicTService = new IbmCmsTopicTService();
		ibmCmsTopicTService.delAll(ids,this.getClass().getName());
		return CommViewEnum.Default.toString();
	}

}
