package c.x.platform.admin.fav.sys.sys_area_info.service;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

import c.a.util.core.string.StringUtil;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.admin.fav.sys.sys_area_info.entity.FunAreaInfo;
import c.x.platform.root.common.service.BaseService;
public class FunAreaInfoService extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FunAreaInfo find(String id) throws Exception {
		return (FunAreaInfo) this.dao.find(FunAreaInfo.class, id);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(FunAreaInfo entity) throws Exception {
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
		String sql = "delete from sys_area where id in("
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
		String sql = "delete from sys_area where id=?";
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
	public String insert(FunAreaInfo entity) throws Exception {
		return dao.save(entity);
	}
	/**
	 * 
	 * 分页
	 * 
	 * @param order
	 * @param order_property
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<FunAreaInfo> find(String sortOrderName, String sortFieldName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM sys_area ";
			sql = "SELECT * FROM sys_area order by id desc";
		} else {
			sql_count = "SELECT count(*) FROM sys_area ";
			sql = "SELECT * FROM sys_area order by " + sortFieldName
					+ " " + sortOrderName;
		}
		PageCoreBean<FunAreaInfo> pageCoreBean = dao.page(FunAreaInfo.class,
				sql, null, -1, -1, sql_count);
		return pageCoreBean;
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<FunAreaInfo> listAll() throws Exception {
		String sql = "SELECT * FROM sys_area order by id desc";
		List<FunAreaInfo> list = this.dao
				.findObjectList(FunAreaInfo.class, sql);
		return list;
	}
}
