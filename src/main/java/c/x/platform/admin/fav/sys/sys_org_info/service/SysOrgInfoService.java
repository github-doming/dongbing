package c.x.platform.admin.fav.sys.sys_org_info.service;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import c.a.tools.log.custom.common.BaseLog;
import c.x.platform.admin.fav.sys.sys_org_info.entity.SysOrgInfo;
import c.x.platform.root.common.service.BaseService;
public class SysOrgInfoService extends BaseService {
	public SysOrgInfo find(String id) throws Exception {
		return (SysOrgInfo) this.dao.find(SysOrgInfo.class, id);
	}
	public void update(SysOrgInfo entity) throws Exception {
		dao.update(entity);
		// 更新所有节点的path
		SysOrgInfo parent = this.find(entity.getParent().toString());
		BaseLog.trace("");
		log.trace("父母id=" + parent.getId());
		this.updatePath("sys_org_info", "", parent.getId().toString());
	}
	public void del(String id) throws Exception {
		// 打出本身的path
		StringBuilder sb = new StringBuilder();
		sb.append("  select path FROM  sys_org where id= ");
		sb.append(id);
		String sqlQuery = sb.toString();
		SysOrgInfo parent = (SysOrgInfo) dao.findObject(SysOrgInfo.class,
				sqlQuery, null);
		String parent_path = parent.getPath();
		// 清空 StringBuilder
		sb.setLength(0);
		// 删除所有孩子
		sb.append(" delete  from sys_org where path like ?");
		String sql_del = sb.toString();
		List<Object> parameterList = new ArrayList<Object>();
		// delete from sys_org where path like '%1.'
		parameterList.add("" + parent_path + "%");
		dao.execute(sql_del, parameterList);
	}
	public void insert(SysOrgInfo entity) throws Exception {
		SysOrgInfo parent = this.find(entity.getParent().toString());
		// 保存
		String id = dao.save(entity);
		// 设置主键
		entity.setId(id);
		// BaseLog.trace("id=" + id);
		// 构造path
		String path = parent.getPath() + id + ".";
		entity.setPath(path);
		// 更新
		dao.update(entity);
	}
}
