package all.task.tms.project_user.tms_project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.task.tms.project_user.tms_project.entity.TmsProjectProjectUser;
import  c.x.platform.root.common.service.BaseService;

public class TmsProjectProjectUserService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	public void save(TmsProjectProjectUser entity) throws Exception {
		TmsProjectProjectUser parent = this.find(entity.getParent().toString());
		// 设置主键
		entity.setTmsProjectId(this.findPK(entity));
		// 构造path
		String path = parent.getPath() + entity.getTmsProjectId() + ".";
		entity.setPath(path);
		// 保存
		dao.savePkNot(entity);
	}
	/**
	 * 
	 * 逻辑删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {
		// 打出本身的path
		StringBuilder sb = new StringBuilder();
		sb.append("  select path FROM  tms_project where id= ");
		sb.append(id);
		String sql_query = sb.toString();
		TmsProjectProjectUser parent = (TmsProjectProjectUser) dao.findObject(
				TmsProjectProjectUser.class, sql_query, null);
		String parent_path = parent.getPath();
		// 清空 StringBuilder
		sb.setLength(0);
		// 删除所有孩子
		//sb.append(" delete  from tms_project where path like ?");
		sb.append(" update  tms_project set state_='DEL' where path like ?");
		String sql_del = sb.toString();
		List<Object> parameters = new ArrayList<Object>();
		// delete from tms_project where path like '%1.'
		parameters.add("" + parent_path + "%");
		dao.execute(sql_del, parameters);
	}
	
	/**
	 * 
	 * 物理删除
	 * 
	 * @param idd
	 * @throws Exception
	 */
	public void delPhysical(String id) throws Exception {
		// 打出本身的path
		StringBuilder sb = new StringBuilder();
		sb.append("  select path FROM  tms_project where id= ");
		sb.append(id);
		String sql_query = sb.toString();
		TmsProjectProjectUser parent = (TmsProjectProjectUser) dao.findObject(
				TmsProjectProjectUser.class, sql_query, null);
		String parent_path = parent.getPath();
		// 清空 StringBuilder
		sb.setLength(0);
		// 删除所有孩子
		sb.append(" delete  from tms_project where path like ?");
		String sql_del = sb.toString();
		List<Object> parameters = new ArrayList<Object>();
		// delete from tms_project where path like '%1.'
		parameters.add("" + parent_path + "%");
		dao.execute(sql_del, parameters);
	}
	
	
	
	public void update(TmsProjectProjectUser entity) throws Exception {
		dao.update(entity);
		// 更新所有节点的path
		TmsProjectProjectUser parent = this.find(entity.getParent());
		log.trace("父母id=" + parent.getTmsProjectId());
		this.updatePath(" tms_project", "TMS_PROJECT_ID_",parent.getTmsProjectId());
	}
	
	
	
	
	public TmsProjectProjectUser find(String id) throws Exception {
		return (TmsProjectProjectUser) this.dao.find(TmsProjectProjectUser.class, id);
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM tms_project  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList( sql,null);
		return listMap;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<TmsProjectProjectUser> findObjectAll() throws Exception {

		String sql = "SELECT * FROM tms_project  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<TmsProjectProjectUser> list = this.dao.findObjectList(TmsProjectProjectUser.class, sql);

		return list;
	}
}
