package c.x.platform.admin.feng.edu.edu_teacher_t.cx.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import c.x.platform.admin.feng.edu.edu_teacher_t.cx.entity.EduTeacherTCx;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class EduTeacherTCxService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public EduTeacherTCx find(String id) throws Exception {
		return (EduTeacherTCx) this.dao.find(EduTeacherTCx.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(EduTeacherTCx entity) throws Exception {
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
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from edu_teacher_t where id in("
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

		String sql = "delete from edu_teacher_t where id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(EduTeacherTCx entity) throws Exception {

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
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM edu_teacher_t ";
			sql = "SELECT * FROM edu_teacher_t order by id desc";
		} else {

			sql_count = "SELECT count(*) FROM edu_teacher_t ";
			sql = "SELECT * FROM edu_teacher_t order by " + sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters,
				pageIndex.intValue(), pageSize.intValue(), sql_count);

		return basePage;
	}

	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM edu_teacher_t order by id desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}
}
