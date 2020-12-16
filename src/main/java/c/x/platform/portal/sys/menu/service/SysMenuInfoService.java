package c.x.platform.portal.sys.menu.service;
import java.util.ArrayList;
import java.util.List;

import all.gen.sys_menu.t.entity.SysMenuT;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
public class SysMenuInfoService extends BaseService {
	public SysMenuT find(String id) throws Exception {
		return (SysMenuT) this.dao.find(SysMenuT.class, id);
	}
	public void update(SysMenuT s) throws Exception {
		dao.update(s);
	}
	public void delAll(String[] ids) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (String id : ids) {
			sb.append(id).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "delete FROM SYS_MENU  where id in(" + sb.toString() + ")";
		dao.execute(sql, null);
	}
	public void del(String id) throws Exception {
		String sql = "delete FROM SYS_MENU  where id=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}
	public void insert(SysMenuT s) throws Exception {
		dao.save(s);
	}
	/**
	 * 
	 * 分页
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<SysMenuT> find(String sortOrderName, String sortFieldName, String menu_name,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		if (StringUtil.isBlank(menu_name)) {
			if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
				sql_count = "SELECT count(*) FROM SYS_MENU  ";
				sql = "SELECT * FROM SYS_MENU  order by sys_menu_id_ desc";
			} else {
				sql_count = "SELECT count(*) FROM SYS_MENU  ";
				sql = "SELECT * FROM SYS_MENU  order by " + sortFieldName + " " + sortOrderName;
			}
		} else {
			if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
				sql_count = "SELECT count(*) FROM SYS_MENU  where name like '%" + menu_name + "%'";
				sql = "SELECT * FROM SYS_MENU  where name_ like '%" + menu_name + "%'" + "  order by id desc";
			} else {
				sql_count = "SELECT count(*) FROM SYS_MENU  where name like '%" + menu_name + "%'";
				sql = "SELECT * FROM SYS_MENU  where name_ like '%" + menu_name + "%'" + "  order by " + sortFieldName
						+ " " + sortOrderName;
			}
		}
		PageCoreBean<SysMenuT> pageCoreBean = dao.page(SysMenuT.class, sql, null, pageIndex.intValue(),
				pageSize.intValue(), sql_count);
		return pageCoreBean;
	}
}
