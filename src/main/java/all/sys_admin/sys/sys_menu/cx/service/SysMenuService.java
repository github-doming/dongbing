package all.sys_admin.sys.sys_menu.cx.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import c.x.platform.root.login_not.current.CurrentSysUser;
import all.gen.sys_menu.t.entity.SysMenuT;
public class SysMenuService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
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
	public PageCoreBean<Map<String, Object>> find(CurrentSysUser currentUser, String sortOrderName, String sortFieldName,
			Integer pageIndex, Integer pageSize) throws Exception {
		Integer permissionGrade = currentUser.getPermissionGrade();
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM SYS_MENU ";
			sql = "SELECT * FROM SYS_MENU where PERMISSION_GRADE_>=? order by SYS_MENU_ID_ desc";
		} else {
			sql_count = "SELECT count(*) FROM SYS_MENU ";
			sql = "SELECT * FROM SYS_MENU where PERMISSION_GRADE_>=?  order by " + sortFieldName + " " + sortOrderName;
		}
		parameters.add(permissionGrade);
		PageCoreBean<Map<String, Object>> page = dao.page(sql, parameters, pageIndex.intValue(), pageSize.intValue(),
				sql_count);
		List<Map<String, Object>> listMap = page.getList();
		for (Map<String, Object> map : listMap) {
			Object desc = map.get("DESC_");
			if (desc == null) {
				map.put("DESC_", "");
			}
		}
		return page;
	}
	public SysMenuT find(String id) throws Exception {
		return (SysMenuT) this.dao.find(SysMenuT.class, id);
	}
	public void update(SysMenuT entity) throws Exception {
		entity.setUpdateTime(new Date());
		// 更新所有节点的path
		String parentId = "0";
		SysMenuT parent = this.find(entity.getParent().toString());
		if (parent != null) {
			parentId = parent.getSysMenuId();
		}
		log.trace("父母id=" + parentId);
		entity.setParent(parentId);
		dao.update(entity);
		this.updatePath("sys_menu", "SYS_MENU_ID_", parentId);
	}
	public void del(String id) throws Exception {
		// 打出本身的path
		StringBuilder sb = new StringBuilder();
		sb.append("  select PATH_ FROM  SYS_MENU where SYS_MENU_ID_='");
		sb.append(id.trim());
		sb.append("'");
		String sql_query = sb.toString();
		SysMenuT parent = (SysMenuT) dao.findObject(SysMenuT.class, sql_query, null);
		String parent_path = parent.getPath();
		// 清空 StringBuilder
		sb.setLength(0);
		// 删除所有孩子
		sb.append(" delete  FROM SYS_MENU  where PATH_ like ?");
		String sql_del = sb.toString();
		List<Object> parameters = new ArrayList<Object>();
		// delete FROM SYS_MENU  where path like '%1.'
		parameters.add("" + parent_path + "%");
		dao.execute(sql_del, parameters);
	}
	public void save(SysMenuT entity) throws Exception {
		String parentId = entity.getParent();
		entity.setState(TaskStateEnum.OPEN.toString());
		entity.setPermissionGrade(0);
		if (StringUtil.isBlank(parentId)) {
			parentId = "1";
			entity.setParent(parentId);
		}
		SysMenuT parent = this.find(parentId);
		// 设置主键
		String pk = this.findPK(entity);
		if (StringUtil.isNotBlank(pk)) {
			entity.setSysMenuId(this.findPK(entity));
			// 构造path
			String path = parent.getPath() + entity.getSysMenuId() + ".";
			entity.setPath(path);
			// 保存
			dao.savePkNot(entity);
		}
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM SYS_MENU  order by SYS_MENU_ID_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}
}
