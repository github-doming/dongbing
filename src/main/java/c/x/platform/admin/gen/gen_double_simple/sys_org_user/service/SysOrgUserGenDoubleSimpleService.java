package c.x.platform.admin.gen.gen_double_simple.sys_org_user.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.x.platform.admin.gen.gen_double_simple.sys_org_user.entity.SysOrgUserGenDoubleSimple;
import  c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
public class SysOrgUserGenDoubleSimpleService extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SysOrgUserGenDoubleSimple find(String id) throws Exception {
		return (SysOrgUserGenDoubleSimple) this.dao.find(SysOrgUserGenDoubleSimple.class, id);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SysOrgUserGenDoubleSimple entity) throws Exception {
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
		String sql = "delete from sys_org_user where id in("
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
		String sql = "delete from sys_org_user where id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	/**
	 * 通过部门ID与人员ID
	 * 
	 * 删除
	 * @param org_id
	 * @param user_id
	 * @throws Exception
	 */
	public void del(String org_id,String user_id) throws Exception {
		String sql = "delete from sys_org_user where org_id=? and user_id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(org_id);
		parameters.add(user_id);
		dao.execute(sql, parameters);
	}
	/**
	 * 通过用户ID
	 * 
	 * 删除
	 * @param user_id
	 * @throws Exception
	 */
	public void del_by_userId(String user_id) throws Exception {
		String sql = "delete from sys_org_user where  user_id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(user_id);
		dao.execute(sql, parameters);
	}
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(SysOrgUserGenDoubleSimple entity) throws Exception {
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
	public PageCoreBean<Map<String, Object>> find(String sortOrderName, String sortFieldName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName)
				|| StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM sys_org_user ";
			sql = "SELECT * FROM sys_org_user order by id desc";
		} else {
			sql_count = "SELECT count(*) FROM sys_org_user ";
			sql = "SELECT * FROM sys_org_user order by " + sortFieldName
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
				String sql = "SELECT * FROM sys_org_user order by id desc";
		List<Map<String, Object>> listMap = this.dao.findMapList( sql,null);
		return listMap;
	}
}
