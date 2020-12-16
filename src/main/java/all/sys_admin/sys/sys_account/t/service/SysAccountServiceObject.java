package all.sys_admin.sys.sys_account.t.service;

import java.util.ArrayList;
import java.util.List;

import all.gen.sys_account.t.entity.SysAccountT;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import  c.x.platform.root.common.service.BaseService;

public class SysAccountServiceObject extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SysAccountT find(String id) throws Exception {
		return (SysAccountT) this.dao.find(SysAccountT.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SysAccountT entity) throws Exception {
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
		String sql = "delete FROM SYS_ACCOUNT where SYS_ACCOUNT_ID_ in("
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

		String sql = "delete FROM SYS_ACCOUNT where SYS_ACCOUNT_ID_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(SysAccountT entity) throws Exception {

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
	public PageCoreBean<SysAccountT> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM SYS_ACCOUNT ";
			sql = "SELECT * FROM SYS_ACCOUNT order by SYS_ACCOUNT_ID_ desc";
		} else {

			sql_count = "SELECT count(*) FROM SYS_ACCOUNT ";
			sql = "SELECT * FROM SYS_ACCOUNT order by " +sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<SysAccountT> basePage = dao.page(SysAccountT.class,
				sql, null, pageIndex.intValue(), pageSize.intValue(), sql_count);
		return basePage;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<SysAccountT> findAll() throws Exception {

		String sql = "SELECT * FROM SYS_ACCOUNT order by UPDATE_TIME_ desc";
		List<SysAccountT> list = this.dao.findObjectList(SysAccountT.class, sql);

		return list;
	}
}
