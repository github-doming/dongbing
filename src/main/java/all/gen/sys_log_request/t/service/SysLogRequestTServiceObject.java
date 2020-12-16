package all.gen.sys_log_request.t.service;

import java.util.ArrayList;
import java.util.List;
import all.gen.sys_log_request.t.entity.SysLogRequestT;
import  c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;

public class SysLogRequestTServiceObject extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SysLogRequestT find(String id) throws Exception {
		return (SysLogRequestT) this.dao.find(SysLogRequestT.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SysLogRequestT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 
	 * 删除所有
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
		String sql = "delete from sys_log_request where SYS_LOG_REQUEST_ID in("
				+ stringBuffer.toString() + ")";

		dao.execute(sql, null);

	}

	/**
	 * 
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {

		String sql = "delete from sys_log_request where SYS_LOG_REQUEST_ID=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(SysLogRequestT entity) throws Exception {

		return dao.save(entity);
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
	public PageCoreBean<SysLogRequestT> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM sys_log_request ";
			sql = "SELECT * FROM sys_log_request order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM sys_log_request ";
			sql = "SELECT * FROM sys_log_request order by " +sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<SysLogRequestT> basePage = dao.page(SysLogRequestT.class,
				sql, null, pageIndex.intValue(), pageSize.intValue(), sql_count);
		return basePage;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<SysLogRequestT> findAll() throws Exception {
		//String sql = "SELECT * FROM sys_log_request order by SYS_LOG_REQUEST_ID desc";
		String sql = "SELECT * FROM sys_log_request order by UPDATE_TIME_ desc";
		List<SysLogRequestT> list = this.dao.findObjectList(SysLogRequestT.class, sql);

		return list;
	}
}
