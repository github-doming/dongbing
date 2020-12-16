package all.task.tms.project_user.app_user.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import all.task.tms.project_user.app_user.entity.AppUserProjectUser;
import  c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.a.util.core.enums.bean.UserStateEnum;
public class AppUserProjectUserService extends BaseService {
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(AppUserProjectUser entity) throws Exception {
		entity.setState(UserStateEnum.OPEN.getCode());
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
		String sql = "update  app_user set state_='DEL' where APP_USER_ID_=?";
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
		String sql = "update  app_user  set state_='DEL'  where id in("
				+ stringBuffer.toString() + ")";
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
		String sql = "delete from app_user where APP_USER_ID_=?";
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
		String sql = "delete from app_user where id in("
				+ stringBuffer.toString() + ")";
		dao.execute(sql, null);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(AppUserProjectUser entity) throws Exception {
		entity.setState(UserStateEnum.OPEN.getCode());
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
	public AppUserProjectUser find(String id) throws Exception {
		return (AppUserProjectUser) this.dao.find(AppUserProjectUser.class, id);
	}
	/**
	 * 
	 * 通过树菜单查询列表
	 * 
	 * 分页
	 * @param first$id
	 *            树节点id
	 * @param order
	 * @param sortFieldName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String first$id,
			String sortFieldName,String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
	if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) from app_user s left JOIN tms_project_user t on t.USER_ID_=s.APP_USER_ID_ "
					+ " where t.state_!='DEL' and t.TMS_PROJECT_ID_='" + first$id + "' ";
			sql = "select *,s.APP_USER_ID_ as id from app_user s left JOIN tms_project_user t on t.USER_ID_=s.APP_USER_ID_ "
					+ " where t.state_!='DEL'  and t.TMS_PROJECT_ID_='" + first$id + "' order by s.APP_USER_ID_ desc";
		} else {
			sql_count = "SELECT count(*) from app_user s left JOIN tms_project_user t on t.USER_ID_=s.APP_USER_ID_"
					+ " where t.state_!='DEL' and t.TMS_PROJECT_ID_='" + first$id + "'";
			sql = "select *,s.APP_USER_ID_ as id from app_user s left JOIN tms_project_user t on t.USER_ID_=s.APP_USER_ID_ "
					+ " where t.state_!='DEL' and  t.TMS_PROJECT_ID_='"
					+ first$id
					+ "' order by s."
					+ sortFieldName + " " +sortOrderName;
		}
		PageCoreBean<Map<String, Object>> basePage = dao.page(sql,
				parameters, pageIndex.intValue(), pageSize.intValue(), sql_count);
		return basePage;
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
	public PageCoreBean<Map<String, Object>> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName)
				|| StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM app_user where state_!='DEL' ";
			sql = "SELECT * FROM app_user where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql_count = "SELECT count(*) FROM app_user where state_!='DEL'";
			sql = "SELECT * FROM app_user where state_!='DEL' order by " + sortFieldName
					+ " " + sortOrderName;
		}
		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex.intValue(), pageSize
				.intValue(), sql_count);
		return basePage;
	}
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		//String sql = "SELECT * FROM app_user  where state_!='DEL' order by APP_USER_ID_ desc";
		String sql = "SELECT * FROM app_user  where state_!='DEL' order by UPDATE_TIME_ desc";
	List<Map<String, Object>> listMap = this.dao.findMapList( sql,null);
		return listMap;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<AppUserProjectUser> findObjectAll() throws Exception {
		//String sql = "SELECT * FROM app_user  where state_!='DEL' order by APP_USER_ID_ desc";
		String sql = "SELECT * FROM app_user  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<AppUserProjectUser> list = this.dao.findObjectList(AppUserProjectUser.class, sql);
		return list;
	}
}
