package c.x.platform.admin.gen.gen_double_simple.sys_org.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.x.platform.admin.gen.gen_double_simple.sys_org.entity.SysOrgGenDoubleSimple;
import  c.x.platform.root.common.service.BaseService;

public class SysOrgGenDoubleSimpleService extends BaseService {
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
		sb.append("  select path FROM  sys_org where id= ");
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
		// delete from sys_org where path like '%1.'
		parameters.add("" + parent_path + "%");
		dao.execute(sql_del, parameters);
	}

	public void save(SysOrgGenDoubleSimple entity) throws Exception {
		SysOrgGenDoubleSimple parent = this.find(entity.getParent().toString());
		// 设置主键
		entity.setSysOrgId(this.findPK(entity));
		// 构造path
		String path = parent.getPath() + entity.getSysOrgId() + ".";
		entity.setPath(path);
		// 保存
		dao.savePkNot(entity);
	}
	
	
	
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM sys_org order by SYS_ORG_ID_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList( sql,null);
		return listMap;
	}
}
