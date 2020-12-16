package c.x.platform.admin.feng.fas.cx.fas_subject_info.service;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import c.x.platform.admin.feng.fas.cx.fas_subject_info.entity.FasSubjectInfo;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
public class FasSubjectInfoService extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FasSubjectInfo find(String id) throws Exception {
		return (FasSubjectInfo) this.dao.find(FasSubjectInfo.class, id);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(FasSubjectInfo entity) throws Exception {
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
		if (ids != null) {
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {
				stringBuffer.append(id).append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from fas_subject_info where id in("
					+ stringBuffer.toString() + ")";
			dao.execute(sql, null);
		}
	}
	/**
	 * 
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {
		String sql = "delete from fas_subject_info where id=?";
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
	public String insert(FasSubjectInfo entity) throws Exception {
		return dao.save(entity);
	}
	/**
	 * 
	 * 分页
	 * 
	 * @param order
	 * @param order_property
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String sortOrderName,
			String sortFieldName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameterList = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM fas_subject_info ";
			sql = "SELECT * FROM fas_subject_info order by id desc";
		} else {
			sql_count = "SELECT count(*) FROM fas_subject_info ";
			sql = "SELECT * FROM fas_subject_info order by " + sortFieldName
					+ " " + sortOrderName;
		}
		PageCoreBean<Map<String, Object>> pageBean = dao.page(sql,
				parameterList, pageIndex.intValue(), pageSize.intValue(),
				sql_count);
		return pageBean;
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> listAll() throws Exception {
		String sql = "SELECT * FROM fas_subject_info order by id desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}
}
