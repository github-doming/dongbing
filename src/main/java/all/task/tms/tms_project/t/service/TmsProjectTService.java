package all.task.tms.tms_project.t.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.util.core.string.StringUtil;
import all.task.tms.tms_project.t.entity.TmsProjectT;
import c.x.platform.root.common.service.BaseService;

public class TmsProjectTService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	public void save(TmsProjectT entity) throws Exception {
		String parentId = entity.getParent();
		// entity.setIsShow(1);
		// entity.setPermissionGrade(0);
		if (StringUtil.isBlank(parentId)) {
			parentId = "1";
			entity.setParent(parentId);

		}
		TmsProjectT parent = this.find(parentId);
		// 设置主键
		String pk = this.findPK(entity);
		if (StringUtil.isNotBlank(pk)) {
			entity.setTmsProjectId(pk);
			// 构造path
			String path = parent.getPath() + entity.getTmsProjectId() + ".";
			entity.setPath(path);
			// 保存
			dao.savePkNot(entity);
		}

	}
	/**
	 * 
	 * 逻辑删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {
		// 找出本身的path
		StringBuilder sb = new StringBuilder();
		sb.append("  select PATH_ FROM  tms_project where TMS_PROJECT_ID_='");
		sb.append(id.trim());
		sb.append("'");
		String sql_query = sb.toString();
		TmsProjectT parent = (TmsProjectT) dao
				.findObject(TmsProjectT.class, sql_query,
						null);
		String parent_path = parent.getPath();
		// 清空 StringBuilder
		sb.setLength(0);
		// 逻辑删除所有孩子
		//sb.append(" delete  from tms_project where PATH_ like ?");
		sb.append(" update  tms_project set state_='DEL' where PATH_ like ?");
		String sql_del = sb.toString();
		List<Object> parameters = new ArrayList<Object>();
		// delete from tms_project where path like '%1.'
		parameters.add("" + parent_path + "%");
		dao.execute(sql_del, parameters);
	}
	
/**
	 * 
	 * 逻辑删除所有
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delAll(String[] ids) throws Exception {
		if(ids!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "update tms_project set state_='DEL' where TMS_PROJECT_ID_ in("
				+ stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}
		

	}
	
	/**
	 * 
	 * 物理删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delPhysical(String id) throws Exception {
		// 打出本身的path
		StringBuilder sb = new StringBuilder();
		sb.append("  select PATH_ FROM  tms_project where TMS_PROJECT_ID_='");
		sb.append(id.trim());
		sb.append("'");
		String sql_query = sb.toString();
		TmsProjectT parent = (TmsProjectT) dao
				.findObject(TmsProjectT.class, sql_query,
						null);
		String parent_path = parent.getPath();
		// 清空 StringBuilder
		sb.setLength(0);
		// 删除所有孩子
		sb.append(" delete  from tms_project where PATH_ like ?");
		String sql_del = sb.toString();
		List<Object> parameters = new ArrayList<Object>();
		// delete from tms_project where path like '%1.'
		parameters.add("" + parent_path + "%");
		dao.execute(sql_del, parameters);
	}
	
	/**
	 * 
	 * 物理删除所有
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delAllPhysical(String[] ids) throws Exception {
		if(ids!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from tms_project where TMS_PROJECT_ID_ in("
				+ stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}
		

	}
	public void update(TmsProjectT entity) throws Exception {
		dao.update(entity);
		// 更新所有节点的path
		if (StringUtil.isNotBlank(entity.getParent())) {
			TmsProjectT parent = this.find(entity.getParent()
					.toString());
			log.trace("父母id=" + parent.getTmsProjectId());
			this.updatePath(" tms_project", "TMS_PROJECT_ID_", parent
					.getTmsProjectId().toString());
		}
	}
	public TmsProjectT find(String id) throws Exception {
		return (TmsProjectT) this.dao.find(
				TmsProjectT.class, id);
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		//String sql = "SELECT * FROM tms_project where state_!='DEL' order by TMS_PROJECT_ID_ desc";
		String sql = "SELECT * FROM tms_project where state_!='DEL' order by UPDATE_TIME_ desc";
		
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<TmsProjectT> findObjectAll() throws Exception {
		//String sql = "SELECT * FROM tms_project where state_!='DEL' order by TMS_PROJECT_ID_ desc";
		String sql = "SELECT * FROM tms_project where state_!='DEL' order by UPDATE_TIME_ desc";
		List<TmsProjectT> list = this.dao.findObjectList(TmsProjectT.class, sql);

		return list;
	}
}
