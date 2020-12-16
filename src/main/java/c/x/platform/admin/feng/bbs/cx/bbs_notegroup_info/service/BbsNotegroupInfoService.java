package c.x.platform.admin.feng.bbs.cx.bbs_notegroup_info.service;
import java.util.ArrayList;
import java.util.List;
import c.x.platform.admin.feng.bbs.cx.bbs_notegroup_info.entity.BbsNotegroupInfo;
import c.x.platform.root.common.service.BaseService;
public class BbsNotegroupInfoService extends BaseService {
	public BbsNotegroupInfo find(String id) throws Exception {
		return (BbsNotegroupInfo) this.dao.find(BbsNotegroupInfo.class, id);
	}
	public void update(BbsNotegroupInfo entity) throws Exception {
		dao.update(entity);
		// 更新所有节点的path
		BbsNotegroupInfo parent = this.find(entity.getParent().toString());
		log.trace("父母id=" + parent.getId());
		this.updatePath(" bbs_notegroup_info", "", parent.getId().toString());
	}
	public void del(String id) throws Exception {
		// 打出本身的path
		StringBuilder sb = new StringBuilder();
		sb.append("  select path FROM  bbs_notegroup_info where id= ");
		sb.append(id);
		String sqlQuery = sb.toString();
		BbsNotegroupInfo parent = (BbsNotegroupInfo) dao.findObject(
				BbsNotegroupInfo.class, sqlQuery, null);
		String parent_path = parent.getPath();
		// 清空 StringBuilder
		sb.setLength(0);
		// 删除所有孩子
		sb.append(" delete  from bbs_notegroup_info where path like ?");
		String sql_del = sb.toString();
		List<Object> parameterList = new ArrayList<Object>();
		// delete from bbs_notegroup_info where path like '%1.'
		parameterList.add("" + parent_path + "%");
		dao.execute(sql_del, parameterList);
	}
	public void insert(BbsNotegroupInfo entity) throws Exception {
		BbsNotegroupInfo parent = this.find(entity.getParent().toString());
		// 保存
		String id = dao.save(entity);
		// 设置主键
		entity.setId(id);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<BbsNotegroupInfo> listAll() throws Exception {
		String sql = "SELECT * FROM bbs_notegroup_info order by id desc";
		List<BbsNotegroupInfo> list = this.dao.findObjectList(
				BbsNotegroupInfo.class, sql);
		return list;
	}
}
