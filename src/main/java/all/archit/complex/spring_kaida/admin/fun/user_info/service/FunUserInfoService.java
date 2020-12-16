package all.archit.complex.spring_kaida.admin.fun.user_info.service;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import c.x.platform.root.common.service.BaseService;
import c.a.util.core.string.StringUtil;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import all.archit.complex.spring_kaida.admin.fun.user_info.entity.SpringKaidaFunUserInfo;
public class FunUserInfoService extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SpringKaidaFunUserInfo find(String id) throws Exception {
		return (SpringKaidaFunUserInfo) this.dao.find(
				SpringKaidaFunUserInfo.class, id);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SpringKaidaFunUserInfo entity) throws Exception {
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
		String sql = "delete from sys_user_info where id in("
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
		String sql = "delete from sys_user_info where id=?";
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
	public String insert(SpringKaidaFunUserInfo entity) throws Exception {
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
	public PageCoreBean<Map<String, Object>> find(String sortOrderName,
			String sortFieldName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameterList = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT * FROM SYS_USER ";
			sql = "SELECT * FROM SYS_USER order by UPDATE_TIME_ desc";
		} else {
			sql_count = "SELECT * FROM SYS_USER ";
			sql = "SELECT * FROM SYS_USER order by " + sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<Map<String, Object>> pageBean = dao.page(sql,
				parameterList, pageIndex.intValue(), pageSize.intValue(),
				sql_count);
		return pageBean;
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<SpringKaidaFunUserInfo> listAll() throws Exception {
		String sql = "SELECT * FROM SYS_USER order by UPDATE_TIME_ desc";
		List<SpringKaidaFunUserInfo> list = this.dao.findObjectList(
				SpringKaidaFunUserInfo.class, sql);
		return list;
	}
}
