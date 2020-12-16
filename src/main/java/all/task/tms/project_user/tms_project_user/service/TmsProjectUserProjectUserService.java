package all.task.tms.project_user.tms_project_user.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.task.tms.project_user.tms_project_user.entity.TmsProjectUserProjectUser;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
public class TmsProjectUserProjectUserService extends BaseService {

	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(TmsProjectUserProjectUser entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 
	 * 逻辑删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {
		String sql = "delete from tms_project_user where id=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 
	 * 逻辑删除所有
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
		String sql = "delete from tms_project_user where id in(" + stringBuffer.toString() + ")";
		dao.execute(sql, null);
	}
	/**
	 * 
	 * 物理删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from tms_project_user where id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 
	 * 物理删除所有
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delPhysicalAll(String[] ids) throws Exception {
		StringBuilder stringBuffer = new StringBuilder();
		for (String id : ids) {
			stringBuffer.append(id).append(",");
		}
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		String sql = "delete from tms_project_user where id in(" + stringBuffer.toString() + ")";
		dao.execute(sql, null);
	}
	/**
	 * 通过部门ID与人员ID
	 * 
	 * 删除
	 * 
	 * @param tms_project_id
	 * @param user_id
	 * @throws Exception
	 */
	public void del(String tms_project_id, String user_id) throws Exception {
		String sql = "delete from tms_project_user where tms_project_id_=? and user_id_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(tms_project_id);
		parameters.add(user_id);
		dao.execute(sql, parameters);
	}
	/**
	 * 通过用户ID
	 * 
	 * 删除
	 * 
	 * @param user_id
	 * @throws Exception
	 */
	public void del_by_userId(String user_id) throws Exception {
		String sql = "delete from tms_project_user where  user_id_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(user_id);
		dao.execute(sql, parameters);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(TmsProjectUserProjectUser entity) throws Exception {
		dao.update(entity);
	}
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TmsProjectUserProjectUser find(String id) throws Exception {
		return (TmsProjectUserProjectUser) this.dao.find(TmsProjectUserProjectUser.class, id);
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
	public PageCoreBean<Map<String, Object>> find(String sortOrderName, String sortFieldName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM tms_project_user ";
			sql = "SELECT * FROM tms_project_user order by UPDATE_TIME_ desc";
		} else {
			sql_count = "SELECT count(*) FROM tms_project_user ";
			sql = "SELECT * FROM tms_project_user order by " + sortFieldName + " " + sortOrderName;
		}
		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex.intValue(),
				pageSize.intValue(), sql_count);
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
		// String sql = "SELECT * FROM tms_project_user order by
		// TMS_PROJECT_USER_ID_ desc";

		String sql = "SELECT pu.TMS_PROJECT_ID_,pu.USER_ID_,u.APP_USER_NAME_ " + " FROM tms_project_user pu "
				+ "LEFT JOIN app_user u ON pu.USER_ID_ = u.APP_USER_ID_";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}

	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<TmsProjectUserProjectUser> findObjectAll() throws Exception {
		// String sql = "SELECT * FROM tms_project_user order by
		// TMS_PROJECT_USER_ID_ desc";
		String sql = "SELECT * FROM tms_project_user order by UPDATE_TIME_ desc";

		List<TmsProjectUserProjectUser> list = this.dao.findObjectList(TmsProjectUserProjectUser.class, sql);
		return list;
	}
}
