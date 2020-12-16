package all.sys_admin.sys.dict.sys_dict.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import all.sys_admin.sys.dict.sys_dict.entity.SysDictT;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
public class SysDictTService extends BaseService {

	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findListByCode(String code) throws Exception {
		ArrayList<Object> parameterList = new ArrayList<Object>();
		String sql = "SELECT table_key_,value_cn_,def_ FROM sys_dict  where sys_dict_code_=? and state_!='DEL' order by UPDATE_TIME_ desc";
		parameterList.add(code);

		List<Map<String, Object>> listMap = this.dao.findMapList(sql, parameterList);
		return listMap;
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(SysDictT entity) throws Exception {
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
		String sql = "update  sys_dict set state_='DEL' where SYS_DICT_ID_=?";
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
		String sql = "update  sys_dict  set state_='DEL'  where id in(" + stringBuffer.toString() + ")";
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
		String sql = "delete from sys_dict where SYS_DICT_ID_=?";
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
		String sql = "delete from sys_dict where id in(" + stringBuffer.toString() + ")";
		dao.execute(sql, null);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SysDictT entity) throws Exception {
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
	public SysDictT find(String id) throws Exception {
		return (SysDictT) this.dao.find(SysDictT.class, id);
	}
	/**
	 * 
	 * 通过树菜单查询列表
	 * 
	 * 分页
	 * 
	 * @param first$id
	 *            树节点id
	 * @param order
	 * @param sortFieldName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String first$id, String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) from sys_dict s left JOIN sys_dict_ref t on t.SYS_DICT_ID_=s.SYS_DICT_ID_ "
					+ " where t.state_!='DEL' and t.SYS_DICT_TREE_ID_='" + first$id + "' ";
			sql = "select *,s.SYS_DICT_ID_ as id from sys_dict s left JOIN sys_dict_ref t on t.SYS_DICT_ID_=s.SYS_DICT_ID_ "
					+ " where t.state_!='DEL'  and t.SYS_DICT_TREE_ID_='" + first$id + "' order by s.SYS_DICT_ID_ desc";
		} else {
			sql_count = "SELECT count(*) from sys_dict s left JOIN sys_dict_ref t on t.SYS_DICT_ID_=s.SYS_DICT_ID_"
					+ " where t.state_!='DEL' and t.SYS_DICT_TREE_ID_='" + first$id + "'";
			sql = "select *,s.SYS_DICT_ID_ as id from sys_dict s left JOIN sys_dict_ref t on t.SYS_DICT_ID_=s.SYS_DICT_ID_ "
					+ " where t.state_!='DEL' and  t.SYS_DICT_TREE_ID_='" + first$id + "' order by s." + sortFieldName
					+ " " + sortOrderName;
		}
		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex.intValue(),
				pageSize.intValue(), sql_count);
		return basePage;
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
	public PageCoreBean<Map<String, Object>> find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM sys_dict where state_!='DEL' ";
			sql = "SELECT * FROM sys_dict where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql_count = "SELECT count(*) FROM sys_dict where state_!='DEL'";
			sql = "SELECT * FROM sys_dict where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex.intValue(),
				pageSize.intValue(), sql_count);
		return basePage;
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		// String sql = "SELECT * FROM sys_dict where state_!='DEL' order by
		// SYS_DICT_ID_ desc";
		String sql = "SELECT * FROM sys_dict  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}

	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<SysDictT> findObjectAll() throws Exception {
		// String sql = "SELECT * FROM sys_dict where state_!='DEL' order by
		// SYS_DICT_ID_ desc";
		String sql = "SELECT * FROM sys_dict  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<SysDictT> list = this.dao.findObjectList(SysDictT.class, sql);
		return list;
	}
}
