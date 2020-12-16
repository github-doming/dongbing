package all.gen.sys_file_ref.t.service;

import java.util.ArrayList;
import java.util.List;
import all.gen.sys_file_ref.t.entity.SysFileRefT;
import  c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;

public class SysFileRefTServiceObject extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SysFileRefT find(String id) throws Exception {
		return (SysFileRefT) this.dao.find(SysFileRefT.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SysFileRefT entity) throws Exception {
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
		String sql = "delete from sys_file_ref where SYS_FILE_REF_ID_ in("
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

		String sql = "delete from sys_file_ref where SYS_FILE_REF_ID_=?";
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
	public String  save(SysFileRefT entity) throws Exception {

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
	public PageCoreBean<SysFileRefT> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM sys_file_ref ";
			sql = "SELECT * FROM sys_file_ref order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM sys_file_ref ";
			sql = "SELECT * FROM sys_file_ref order by " +sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<SysFileRefT> basePage = dao.page(SysFileRefT.class,
				sql, null, pageIndex.intValue(), pageSize.intValue(), sql_count);
		return basePage;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<SysFileRefT> findAll() throws Exception {
		//String sql = "SELECT * FROM sys_file_ref order by SYS_FILE_REF_ID_ desc";
		String sql = "SELECT * FROM sys_file_ref order by UPDATE_TIME_ desc";
		List<SysFileRefT> list = this.dao.findObjectList(SysFileRefT.class, sql);

		return list;
	}
}
