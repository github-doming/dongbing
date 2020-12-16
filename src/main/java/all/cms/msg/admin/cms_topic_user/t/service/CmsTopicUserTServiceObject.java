package all.cms.msg.admin.cms_topic_user.t.service;

import java.util.ArrayList;
import java.util.List;

import all.gen.cms_topic_user.t.entity.CmsTopicUserT;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import  c.x.platform.root.common.service.BaseService;

public class CmsTopicUserTServiceObject extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CmsTopicUserT find(String id) throws Exception {
		return (CmsTopicUserT) this.dao.find(CmsTopicUserT.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(CmsTopicUserT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 
	 * 删除所有
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delAll(String[] ids) throws Exception {
		StringBuilder stringBuffer = new StringBuilder();
		for (String id : ids) {

			stringBuffer.append(id).append(",");
		}
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		String sql = "delete from cms_topic_user where CMS_TOPIC_USER_ID_ in("
				+ stringBuffer.toString() + ")";

		dao.execute(sql, null);

	}

	/**
	 * 
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {

		String sql = "delete from cms_topic_user where CMS_TOPIC_USER_ID_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(CmsTopicUserT entity) throws Exception {

		return dao.save(entity);
	}

	/**
	 * 
	 * 分页
	 * 
	 * @param order
	 * @param sortFieldName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<CmsTopicUserT> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM cms_topic_user ";
			sql = "SELECT * FROM cms_topic_user order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM cms_topic_user ";
			sql = "SELECT * FROM cms_topic_user order by " +sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<CmsTopicUserT> basePage = dao.page(CmsTopicUserT.class,
				sql, null, pageIndex.intValue(), pageSize.intValue(), sql_count);
		return basePage;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<CmsTopicUserT> findAll() throws Exception {
		//String sql = "SELECT * FROM cms_topic_user order by CMS_TOPIC_USER_ID_ desc";
		String sql = "SELECT * FROM cms_topic_user order by UPDATE_TIME_ desc";
		List<CmsTopicUserT> list = this.dao.findObjectList(CmsTopicUserT.class, sql);

		return list;
	}
}
