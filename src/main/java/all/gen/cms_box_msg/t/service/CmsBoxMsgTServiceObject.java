package all.gen.cms_box_msg.t.service;

import java.util.ArrayList;
import java.util.List;
import all.gen.cms_box_msg.t.entity.CmsBoxMsgT;
import  c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;

public class CmsBoxMsgTServiceObject extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CmsBoxMsgT find(String id) throws Exception {
		return (CmsBoxMsgT) this.dao.find(CmsBoxMsgT.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(CmsBoxMsgT entity) throws Exception {
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
		String sql = "delete from cms_box_msg where CMS_BOX_MSG_ID_ in("
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

		String sql = "delete from cms_box_msg where CMS_BOX_MSG_ID_=?";
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
	public String  save(CmsBoxMsgT entity) throws Exception {

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
	public PageCoreBean<CmsBoxMsgT> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM cms_box_msg ";
			sql = "SELECT * FROM cms_box_msg order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM cms_box_msg ";
			sql = "SELECT * FROM cms_box_msg order by " +sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<CmsBoxMsgT> basePage = dao.page(CmsBoxMsgT.class,
				sql, null, pageIndex.intValue(), pageSize.intValue(), sql_count);
		return basePage;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<CmsBoxMsgT> findAll() throws Exception {
		//String sql = "SELECT * FROM cms_box_msg order by CMS_BOX_MSG_ID_ desc";
		String sql = "SELECT * FROM cms_box_msg order by UPDATE_TIME_ desc";
		List<CmsBoxMsgT> list = this.dao.findObjectList(CmsBoxMsgT.class, sql);

		return list;
	}
}
