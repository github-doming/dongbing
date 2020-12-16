package com.ibm.old.v1.pc.ibm_cms_topic.t.service;

import com.ibm.old.v1.admin.cms_topic.w.service.CmsTopicWService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class CmsPcTopicService extends CmsTopicWService {
	/**
	 * 查找主题内容
	 *
	 * @param id 消息ID
	 * @return 主题内容
	 */
	public Map<String, Object> findTopicById(String id) throws Exception {
		String sql = "select TITLE_,CONTENT_,CREATE_TIME_ from cms_topic where CMS_TOPIC_ID_ = ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(id);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}
}
