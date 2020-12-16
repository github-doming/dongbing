package all.gen.sys_quartz_log.t.service;

import all.gen.sys_quartz_log.t.entity.SysQuartzLogT;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SysQuartzLogTService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(SysQuartzLogT entity) throws Exception {
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

		String sql = "update sys_quartz_log set state_='DEL' where SYS_QUARTZ_LOG_ID_=?";
		List<Object> parameters = new ArrayList<>();
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
			String sql = "update sys_quartz_log set state_='DEL' where SYS_QUARTZ_LOG_ID_ in("
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

		String sql = "delete from sys_quartz_log where SYS_QUARTZ_LOG_ID_=?";
		List<Object> parameters = new ArrayList<>();
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
			String sql = "delete from sys_quartz_log where SYS_QUARTZ_LOG_ID_ in("
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
	public void update(SysQuartzLogT entity) throws Exception {
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
	public SysQuartzLogT find(String id) throws Exception {
		return (SysQuartzLogT) this.dao.find(SysQuartzLogT.class, id);

	}

	

	

	/**
	 * 
	 * 分页
	 * 
	 * @param sortFieldName
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sqlCount ;
		String sql ;
		ArrayList<Object> parameters = new ArrayList<>();
		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sqlCount = "SELECT count(*) FROM sys_quartz_log where state_!='DEL'";
			sql = "SELECT * FROM sys_quartz_log  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sqlCount = "SELECT count(*) FROM sys_quartz_log where state_!='DEL'";
			sql = "SELECT * FROM sys_quartz_log  where state_!='DEL' order by " + sortFieldName
					+ " " + sortOrderName;
		}

        return (PageCoreBean<Map<String, Object>>) dao.page(sql, parameters, pageIndex, pageSize, sqlCount);
	}
	
	
	
	/**
	 * 
	 * 分页
	 * 
	 * @param sortFieldName
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<SysQuartzLogT> findObject(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sqlCount ;
		String sql ;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sqlCount = "SELECT count(*) FROM sys_quartz_log where state_!='DEL'";
			sql = "SELECT * FROM sys_quartz_log  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sqlCount = "SELECT count(*) FROM sys_quartz_log where state_!='DEL'";
			sql = "SELECT * FROM sys_quartz_log  where state_!='DEL' order by " +sortFieldName
					+ " " + sortOrderName;
		}
		return  dao.page(SysQuartzLogT.class,
                sql, null, pageIndex, pageSize, sqlCount);
	}
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM sys_quartz_log  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findMapList( sql,null);
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<SysQuartzLogT> findObjectAll() throws Exception {
		String sql = "SELECT * FROM sys_quartz_log  where state_!='DEL' order by UPDATE_TIME_ desc";

        return (List<SysQuartzLogT>) this.dao.findObjectList(SysQuartzLogT.class, sql);
	}
    public void findErrorInfo() {
    }
}
