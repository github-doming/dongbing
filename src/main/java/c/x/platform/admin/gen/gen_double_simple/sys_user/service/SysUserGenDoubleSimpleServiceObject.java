package c.x.platform.admin.gen.gen_double_simple.sys_user.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.x.platform.admin.gen.gen_double_simple.sys_user.entity.SysUserGenDoubleSimple;
import  c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
public class SysUserGenDoubleSimpleServiceObject extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SysUserGenDoubleSimple find(String id) throws Exception {
		return (SysUserGenDoubleSimple) this.dao.find(SysUserGenDoubleSimple.class, id);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SysUserGenDoubleSimple entity) throws Exception {
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
		String sql = "delete FROM SYS_USER where SYS_USER_ID_ in("
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
		String sql = "delete FROM SYS_USER where SYS_USER_ID_=?";
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
	public String  save(SysUserGenDoubleSimple entity) throws Exception {
		return dao.save(entity);
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
			String sortOrderName, String sortFieldName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM SYS_USER s left JOIN sys_org_user t on t.SYS_USER_ID_=s.SYS_USER_ID_ "
					+ "where t.SYS_ORG_ID_=" + first$id + " ";
			sql = "select *,s.SYS_USER_ID_ as id FROM SYS_USER s left JOIN sys_org_user t on t.SYS_USER_ID_=s.SYS_USER_ID_ "
					+ "where t.SYS_ORG_ID_=" + first$id + " order by s.id desc";
		} else {
			sql_count = "SELECT count(*) FROM SYS_USER s left JOIN sys_org_user t on t.SYS_USER_ID_=s.SYS_USER_ID_ "
					+ "where t.SYS_ORG_ID_=" + first$id + "";
			sql = "select *,s.SYS_USER_ID_ as id FROM SYS_USER s left JOIN sys_org_user t on t.SYS_USER_ID_=s.SYS_USER_ID_ "
					+ "where t.SYS_ORG_ID_="
					+ first$id
					+ " order by s."
					+ sortFieldName + " " + sortOrderName;
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
	public PageCoreBean<Map<String, Object>> find(String sortFieldName,String sortOrderName, 
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName)
				|| StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM SYS_USER ";
			sql = "SELECT * FROM SYS_USER order by id desc";
		} else {
			sql_count = "SELECT count(*) FROM SYS_USER ";
			sql = "SELECT * FROM SYS_USER order by " + sortFieldName
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
	public List<SysUserGenDoubleSimple> findAll() throws Exception {
		String sql = "SELECT * FROM SYS_USER order by SYS_USER_ID_ desc";
		List<SysUserGenDoubleSimple> list = this.dao.findObjectList(SysUserGenDoubleSimple.class, sql);
		return list;
	}
}
