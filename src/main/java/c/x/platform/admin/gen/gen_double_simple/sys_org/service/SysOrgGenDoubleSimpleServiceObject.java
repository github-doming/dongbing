package c.x.platform.admin.gen.gen_double_simple.sys_org.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.x.platform.admin.gen.gen_double_simple.sys_org.entity.SysOrgGenDoubleSimple;
import  c.x.platform.root.common.service.BaseService;

public class SysOrgGenDoubleSimpleServiceObject extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	public SysOrgGenDoubleSimple find(String id) throws Exception {
		return (SysOrgGenDoubleSimple) this.dao.find(SysOrgGenDoubleSimple.class, id);
	}

	public void update(SysOrgGenDoubleSimple entity) throws Exception {
		dao.update(entity);
		// 更新所有节点的path
		SysOrgGenDoubleSimple parent = this.find(entity.getParent());
		log.trace("父母id=" + parent.getSysOrgId());
		this.updatePath(" sys_org", "SYS_ORG_ID_",parent.getSysOrgId());
	}

	public void del(String id) throws Exception {
		// 打出本身的path
		StringBuilder sb = new StringBuilder();
		sb.append("  select path FROM  sys_org where SYS_ORG_ID_= ");
		sb.append(id);
		String sql_query = sb.toString();
		SysOrgGenDoubleSimple parent = (SysOrgGenDoubleSimple) dao.findObject(
				SysOrgGenDoubleSimple.class, sql_query, null);
		String parent_path = parent.getPath();
		// 清空 StringBuilder
		sb.setLength(0);
		// 删除所有孩子
		sb.append(" delete  from sys_org where path like ?");
		String sql_del = sb.toString();
		List<Object> parameters = new ArrayList<Object>();
		// delete from sys_org where PATH_ like '%1.'
		parameters.add("" + parent_path + "%");
		dao.execute(sql_del, parameters);
	}

	public void save(SysOrgGenDoubleSimple entity) throws Exception {
		SysOrgGenDoubleSimple parent = this.find(entity.getParent().toString());
		// 保存
		String id = dao.save(entity);
		// 设置主键
		entity.setSysOrgId(id);
		// Baselog.trace("id=" + id);
		// 构造path
		String path = parent.getPath() + id + ".";
		entity.setPath(path);
		// 更新
		dao.update(entity);
	}
	
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgGenDoubleSimple> findAll() throws Exception {

		String sql = "SELECT * FROM sys_org order by SYS_ORG_ID_ desc";
		List<SysOrgGenDoubleSimple> list = this.dao.findObjectList(SysOrgGenDoubleSimple.class, sql);

		return list;
	}
}
