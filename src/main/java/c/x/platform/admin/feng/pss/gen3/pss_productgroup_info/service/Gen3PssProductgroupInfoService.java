package c.x.platform.admin.feng.pss.gen3.pss_productgroup_info.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import c.x.platform.admin.feng.pss.gen3.pss_productgroup_info.entity.Gen3PssProductgroupInfo;
import c.x.platform.root.common.service.BaseService;

public class Gen3PssProductgroupInfoService extends BaseService {
	public Gen3PssProductgroupInfo find(String id) throws Exception {
		return (Gen3PssProductgroupInfo) this.dao.find(
				Gen3PssProductgroupInfo.class, id);
	}

	public void update(Gen3PssProductgroupInfo entity) throws Exception {
		dao.update(entity);
		// 更新所有节点的path
		Gen3PssProductgroupInfo parent = this.find(entity.getParent()
				.toString());
		log.trace("父母id=" + parent.getId());
		this.updatePath(" pss_productgroup_info", "", parent.getId().toString());
	}

	public void del(String id) throws Exception {
		// 打出本身的path
		StringBuilder sb = new StringBuilder();
		sb.append("  select path FROM  pss_productgroup_info where id= ");
		sb.append(id);
		String sql_query = sb.toString();
		Gen3PssProductgroupInfo parent = (Gen3PssProductgroupInfo) dao
				.findObject(Gen3PssProductgroupInfo.class, sql_query, null);
		String parent_path = parent.getPath();
		// 清空 StringBuilder
		sb.setLength(0);
		// 删除所有孩子
		sb.append(" delete  from pss_productgroup_info where path like ?");
		String sql_del = sb.toString();
		List<Object> parameters = new ArrayList<Object>();
		// delete from pss_productgroup_info where path like '%1.'
		parameters.add("" + parent_path + "%");
		dao.execute(sql_del, parameters);
	}

	public void save(Gen3PssProductgroupInfo entity) throws Exception {
		Gen3PssProductgroupInfo parent = this.find(entity.getParent()
				.toString());
		// 设置主键
		entity.setId(this.findPK(entity));
		// 构造path
		String path = parent.getPath() + entity.getId() + ".";
		entity.setPath(path);
		// 保存
		dao.savePkNot(entity);
	}

	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM pss_productgroup_info order by id desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}
}
