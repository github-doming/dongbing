package all.sys_admin.sys.dict.sys_dict_ref.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import all.sys_admin.sys.dict.sys_dict_ref.entity.SysDictRefT;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import  c.x.platform.root.common.service.BaseService;
public class SysDictRefTService extends BaseService {

	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(SysDictRefT entity) throws Exception {
		entity.setState(UserStateEnum.OPEN.getCode());
		return dao.save(entity);
	}
	
	/**
	 * 
	 * 逻辑删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {
		String sql = "delete from sys_dict_ref where id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	
	
	/**
	 * 
	 * 逻辑删除所有
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delAll(String[] ids) throws Exception {
		StringBuilder stringBuffer = new StringBuilder();
		for (String id : ids) {
			stringBuffer.append(id).append(",");
		}
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		String sql = "delete from sys_dict_ref where id in("
				+ stringBuffer.toString() + ")";
		dao.execute(sql, null);
	}
	/**
	 * 
	 * 物理删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from sys_dict_ref where id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	
	
	/**
	 * 
	 * 物理删除所有
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delPhysicalAll(String[] ids) throws Exception {
		StringBuilder stringBuffer = new StringBuilder();
		for (String id : ids) {
			stringBuffer.append(id).append(",");
		}
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		String sql = "delete from sys_dict_ref where id in("
				+ stringBuffer.toString() + ")";
		dao.execute(sql, null);
	}
	/**
	 * 通过部门ID与人员ID
	 * 
	 * 删除
	 * @param treeId
	 * @param dictId
	 * @throws Exception
	 */
	public void del(String treeId,String dictId) throws Exception {
		String sql = "delete from sys_dict_ref where sys_dict_tree_id_=? and sys_dict_id_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(treeId);
		parameters.add(dictId);
		dao.execute(sql, parameters);
	}
	/**
	 * 通过用户ID
	 * 
	 * 删除
	 * @param user_id
	 * @throws Exception
	 */
	public void del_by_sysDictId(String sysDictId) throws Exception {
		String sql = "delete from sys_dict_ref where  sys_dict_id_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(sysDictId);
		dao.execute(sql, parameters);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SysDictRefT entity) throws Exception {
		entity.setState(UserStateEnum.OPEN.getCode());
		dao.update(entity);
	}
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SysDictRefT find(String id) throws Exception {
		return (SysDictRefT) this.dao.find(SysDictRefT.class, id);
	}
	/**
	 * 
	 * 分页
	 * 
	 * @param order
	 * @param sortFieldName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String sortOrderName, String sortFieldName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName)
				|| StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM sys_dict_ref ";
			sql = "SELECT * FROM sys_dict_ref order by UPDATE_TIME_ desc";
		} else {
			sql_count = "SELECT count(*) FROM sys_dict_ref ";
			sql = "SELECT * FROM sys_dict_ref order by " + sortFieldName
					+ " " + sortOrderName;
		}
		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex.intValue(), pageSize
				.intValue(), sql_count);
		return basePage;
	}
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		//String sql = "SELECT * FROM sys_dict_ref order by SYS_DICT_REF_ID_ desc";
		
		String sql = "SELECT * FROM sys_dict_ref order by UPDATE_TIME_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList( sql,null);
		return listMap;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<SysDictRefT> findObjectAll() throws Exception {
		//String sql = "SELECT * FROM sys_dict_ref order by SYS_DICT_REF_ID_ desc";
		String sql = "SELECT * FROM sys_dict_ref order by UPDATE_TIME_ desc";
		
		List<SysDictRefT> list = this.dao.findObjectList(SysDictRefT.class, sql);
		return list;
	}
}
