package all.gen.fun_type_str_queue.t.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import all.gen.fun_type_str_queue.t.entity.FunTypeStrQueueT;
import  c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class FunTypeStrQueueTService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(FunTypeStrQueueT entity) throws Exception {

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

		String sql = "update fun_type_str_queue set state_='DEL' where FUN_TYPE_STR_QUEUE_ID_=?";
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
	public void delAll(String[] idArray) throws Exception {
		if(idArray!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : idArray) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "update fun_type_str_queue set state_='DEL' where FUN_TYPE_STR_QUEUE_ID_ in("
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

		String sql = "delete from fun_type_str_queue where FUN_TYPE_STR_QUEUE_ID_=?";
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
	public void delAllPhysical(String[] idArray) throws Exception {
		if(idArray!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : idArray) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from fun_type_str_queue where FUN_TYPE_STR_QUEUE_ID_ in("
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
	public void update(FunTypeStrQueueT entity) throws Exception {
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
	public FunTypeStrQueueT find(String id) throws Exception {
		return (FunTypeStrQueueT) this.dao.find(FunTypeStrQueueT.class, id);

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
			sql_count = "SELECT count(*) FROM fun_type_str_queue where state_!='DEL'";
			sql = "SELECT * FROM fun_type_str_queue  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM fun_type_str_queue where state_!='DEL'";
			sql = "SELECT * FROM fun_type_str_queue  where state_!='DEL' order by " + sortFieldName
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
	public PageCoreBean<FunTypeStrQueueT> findObject(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM fun_type_str_queue where state_!='DEL'";
			sql = "SELECT * FROM fun_type_str_queue  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM fun_type_str_queue where state_!='DEL'";
			sql = "SELECT * FROM fun_type_str_queue  where state_!='DEL' order by " +sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<FunTypeStrQueueT> basePage = dao.page(FunTypeStrQueueT.class,
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
		//String sql = "SELECT * FROM fun_type_str_queue  where state_!='DEL' order by FUN_TYPE_STR_QUEUE_ID_ desc";
		
		String sql = "SELECT * FROM fun_type_str_queue  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList( sql,null);
		return listMap;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<FunTypeStrQueueT> findObjectAll() throws Exception {
		//String sql = "SELECT * FROM fun_type_str_queue  where state_!='DEL' order by FUN_TYPE_STR_QUEUE_ID_ desc";
		String sql = "SELECT * FROM fun_type_str_queue  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<FunTypeStrQueueT> list = this.dao.findObjectList(FunTypeStrQueueT.class, sql);

		return list;
	}
}
