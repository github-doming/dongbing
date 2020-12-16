package c.x.platform.sys.sys_menu.cx.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import all.gen.sys_menu.t.entity.SysMenuT;
public class SysMenuCxService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
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
		// delete FROM SYS_MENU where path like '%1.'
		parameters.add("" + parent_path + "%");
		dao.execute(sql_del, parameters);
	}
	public void save(SysMenuT entity) throws Exception {
		if (StringUtil.isBlank(entity.getState())) {
			entity.setState(TaskStateEnum.OPEN.toString());
		}
		if (entity.getPermissionGrade() == null) {
			entity.setPermissionGrade(0);
		}
		String parentId = entity.getParent();
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
