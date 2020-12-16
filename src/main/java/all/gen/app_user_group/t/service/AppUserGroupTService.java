package all.gen.app_user_group.t.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import all.gen.app_user_group.t.entity.AppUserGroupT;
import  c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class AppUserGroupTService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(AppUserGroupT entity) throws Exception {

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

		String sql = "update app_user_group set state_='DEL' where APP_USER_GROUP_ID_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	/**
	 * 
	 * 逻辑删除所有
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delAll(String[] ids) throws Exception {
		if(ids!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "update app_user_group set state_='DEL' where APP_USER_GROUP_ID_ in("
				+ stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}
		

	}

	
	/**
	 * 
	 * 物理删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delPhysical(String id) throws Exception {

		String sql = "delete from app_user_group where APP_USER_GROUP_ID_=?";
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
	public void delAllPhysical(String[] ids) throws Exception {
		if(ids!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from app_user_group where APP_USER_GROUP_ID_ in("
				+ stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}
		

	}
	
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(AppUserGroupT entity) throws Exception {
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
	public AppUserGroupT find(String id) throws Exception {
		return (AppUserGroupT) this.dao.find(AppUserGroupT.class, id);

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
		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM app_user_group where state_!='DEL'";
			sql = "SELECT * FROM app_user_group  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM app_user_group where state_!='DEL'";
			sql = "SELECT * FROM app_user_group  where state_!='DEL' order by " + sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex.intValue(), pageSize
				.intValue(), sql_count);
				
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
	public PageCoreBean<AppUserGroupT> findObject(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM app_user_group where state_!='DEL'";
			sql = "SELECT * FROM app_user_group  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM app_user_group where state_!='DEL'";
			sql = "SELECT * FROM app_user_group  where state_!='DEL' order by " +sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<AppUserGroupT> basePage = dao.page(AppUserGroupT.class,
				sql, null, pageIndex.intValue(), pageSize.intValue(), sql_count);
		return basePage;
	}
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		//String sql = "SELECT * FROM app_user_group  where state_!='DEL' order by APP_USER_GROUP_ID_ desc";
		
		String sql = "SELECT * FROM app_user_group  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList( sql,null);
		return listMap;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<AppUserGroupT> findObjectAll() throws Exception {
		//String sql = "SELECT * FROM app_user_group  where state_!='DEL' order by APP_USER_GROUP_ID_ desc";
		String sql = "SELECT * FROM app_user_group  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<AppUserGroupT> list = this.dao.findObjectList(AppUserGroupT.class, sql);

		return list;
	}
	/**
	 * 
	 * @Title: findByUserId 
	 * @Description: 通过用户id获取用户组信息
	 *
	 * 参数说明 
	 * @param userId
	 * @return 
	 * 返回类型 AppUserGroupT
	 * @throws Exception 
	 */
	public AppUserGroupT findByUserId(String userId) throws Exception {
		String sql="select * from app_user_group where APP_USER_ID_=? ";
		List<Object> parameterList=new ArrayList<>();
		parameterList.add(userId);
		return  (AppUserGroupT) dao.findObject(AppUserGroupT.class, sql, parameterList);
	}
}
