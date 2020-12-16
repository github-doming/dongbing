package com.ibm.old.v1.admin.cms_topic.w.action;

import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
import com.ibm.old.v1.cloud.ibm_cms_topic.t.service.IbmCmsTopicTService;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @Description: 删除消息
 * @date 2019年2月19日 下午2:09:40 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmCmsTopicWDelAction extends BaseAction{

	@Override
	public String execute() throws Exception {
		String userTopicId = request.getParameter("id");
		//删除已发送用户消息
		IbmCmsTopicTService ibmCmsTopicTService = new IbmCmsTopicTService();
		ibmCmsTopicTService.del(userTopicId);
		List<String> msgList = new ArrayList<String>(2);
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}

}
