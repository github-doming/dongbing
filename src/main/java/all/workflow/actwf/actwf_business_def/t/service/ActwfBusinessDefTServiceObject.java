package all.workflow.actwf.actwf_business_def.t.service;

import java.util.ArrayList;
import java.util.List;
import all.workflow.actwf.actwf_business_def.t.entity.ActwfBusinessDefT;
import  c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;

public class ActwfBusinessDefTServiceObject extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ActwfBusinessDefT find(String id) throws Exception {
		return (ActwfBusinessDefT) this.dao.find(ActwfBusinessDefT.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(ActwfBusinessDefT entity) throws Exception {
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
		String sql = "delete from actwf_business_def where ACTWF_BUSINESS_DEF_ID_ in("
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

		String sql = "delete from actwf_business_def where ACTWF_BUSINESS_DEF_ID_=?";
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
	public String  save(ActwfBusinessDefT entity) throws Exception {

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
	public PageCoreBean<ActwfBusinessDefT> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM actwf_business_def ";
			sql = "SELECT * FROM actwf_business_def order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM actwf_business_def ";
			sql = "SELECT * FROM actwf_business_def order by " +sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<ActwfBusinessDefT> basePage = dao.page(ActwfBusinessDefT.class,
				sql, null, pageIndex.intValue(), pageSize.intValue(), sql_count);
		return basePage;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<ActwfBusinessDefT> findAll() throws Exception {
		//String sql = "SELECT * FROM actwf_business_def order by ACTWF_BUSINESS_DEF_ID_ desc";
		String sql = "SELECT * FROM actwf_business_def order by UPDATE_TIME_ desc";
		List<ActwfBusinessDefT> list = this.dao.findObjectList(ActwfBusinessDefT.class, sql);

		return list;
	}
}
