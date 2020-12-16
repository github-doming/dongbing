package all.app_admin.app.app_account.t.service;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import all.gen.app_account.t.entity.AppAccountT;
import  c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
public class AppAccountTService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 通过用户名，找出用户是否存在
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public boolean isUserByName(String name) throws Exception {
		String sql = "select APP_ACCOUNT_NAME_ FROM APP_ACCOUNT where APP_ACCOUNT_NAME_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(name);
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, parameterList);
		if (listMap.size() == 0) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(AppAccountT entity) throws Exception {
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
		String sql = "update app_account set state_='DEL' where APP_ACCOUNT_ID_=?";
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
			String sql = "update app_account set state_='DEL' where APP_ACCOUNT_ID_ in("
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
		String sql = "delete from app_account where APP_ACCOUNT_ID_=?";
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
			String sql = "delete from app_account where APP_ACCOUNT_ID_ in("
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
	public void update(AppAccountT entity) throws Exception {
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
	public AppAccountT find(String id) throws Exception {
		return (AppAccountT) this.dao.find(AppAccountT.class, id);
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
			Integer pageIndex, Integer pageSize,String tenantCode) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM app_account where state_!='DEL' and TENANT_CODE_=?  ";
			sql = "SELECT * FROM app_account  where state_!='DEL' and TENANT_CODE_=? order by UPDATE_TIME_ desc";
		} else {
			sql_count = "SELECT count(*) FROM app_account where state_!='DEL' and TENANT_CODE_=?  ";
			sql = "SELECT * FROM app_account  where state_!='DEL' and TENANT_CODE_=? order by " + sortFieldName
					+ " " + sortOrderName;
		}
		parameters .add(tenantCode);
		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex.intValue(), pageSize
				.intValue(), sql_count,parameters);
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
	public PageCoreBean<AppAccountT> findObject(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM app_account where state_!='DEL'";
			sql = "SELECT * FROM app_account  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql_count = "SELECT count(*) FROM app_account where state_!='DEL'";
			sql = "SELECT * FROM app_account  where state_!='DEL' order by " +sortFieldName
					+ " " + sortOrderName;
		}
		PageCoreBean<AppAccountT> basePage = dao.page(AppAccountT.class,
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
		//String sql = "SELECT * FROM app_account  where state_!='DEL' order by APP_ACCOUNT_ID_ desc";
		String sql = "SELECT * FROM app_account  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList( sql,null);
		return listMap;
	}
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<AppAccountT> findObjectAll() throws Exception {
		//String sql = "SELECT * FROM app_account  where state_!='DEL' order by APP_ACCOUNT_ID_ desc";
		String sql = "SELECT * FROM app_account  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<AppAccountT> list = this.dao.findObjectList(AppAccountT.class, sql);
		return list;
	}

}
