package all.upload.sys_bytes.t.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_bytes.t.entity.SysBytesT;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import  c.x.platform.root.common.service.BaseService;
public class SysBytesTService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(SysBytesT entity) throws Exception {
		entity.setState(TaskStateEnum.OPEN.getCode());
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
		String sql = "update sys_bytes set state_='DEL' where SYS_BYTES_ID_=?";
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
		if(ids!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "update sys_bytes set state_='DEL' where SYS_BYTES_ID_ in("
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
		String sql = "delete from sys_bytes where SYS_BYTES_ID_=?";
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
	public void delAllPhysical(String[] ids) throws Exception {
		if(ids!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from sys_bytes where SYS_BYTES_ID_ in("
				+ stringBuffer.toString() + ")";
			dao.execute(sql, null);
		}
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SysBytesT entity) throws Exception {
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
	public SysBytesT find(String id) throws Exception {
		return (SysBytesT) this.dao.find(SysBytesT.class, id);
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
	public PageCoreBean<Map<String, Object>> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM sys_bytes where state_!='DEL'";
			sql = "SELECT * FROM sys_bytes  where state_!='DEL' order by SYS_BYTES_ID_ desc";
		} else {
			sql_count = "SELECT count(*) FROM sys_bytes where state_!='DEL'";
			sql = "SELECT * FROM sys_bytes  where state_!='DEL' order by " + sortFieldName
					+ " " + sortOrderName;
		}
		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex.intValue(), pageSize
				.intValue(), sql_count);
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
	public PageCoreBean<SysBytesT> findObject(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM sys_bytes where state_!='DEL'";
			sql = "SELECT * FROM sys_bytes  where state_!='DEL' order by SYS_BYTES_ID_ desc";
		} else {
			sql_count = "SELECT count(*) FROM sys_bytes where state_!='DEL'";
			sql = "SELECT * FROM sys_bytes  where state_!='DEL' order by " +sortFieldName
					+ " " + sortOrderName;
		}
		PageCoreBean<SysBytesT> basePage = dao.page(SysBytesT.class,
				sql, null, pageIndex.intValue(), pageSize.intValue(), sql_count);
		return basePage;
	}
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM sys_bytes  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList( sql,null);
		return listMap;
	}
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<SysBytesT> findObjectAll() throws Exception {
		String sql = "SELECT * FROM sys_bytes  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<SysBytesT> list = this.dao.findObjectList(SysBytesT.class, sql);
		return list;
	}
}
