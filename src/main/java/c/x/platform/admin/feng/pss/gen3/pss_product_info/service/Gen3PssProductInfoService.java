package c.x.platform.admin.feng.pss.gen3.pss_product_info.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import c.x.platform.admin.feng.pss.gen3.pss_product_info.entity.Gen3PssProductInfo;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;

public class Gen3PssProductInfoService extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Gen3PssProductInfo find(String id) throws Exception {
		return (Gen3PssProductInfo) this.dao.find(Gen3PssProductInfo.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(Gen3PssProductInfo entity) throws Exception {
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
		String sql = "delete from pss_product_info where id in(" + stringBuffer.toString() + ")";

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

		String sql = "delete from pss_product_info where id=?";
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
	public String save(Gen3PssProductInfo entity) throws Exception {

		return dao.save(entity);
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
	 * @param order_property
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> query(String first$id, String sortOrderName, String sortFieldName,
			Integer pageIndex, Integer pageSize) throws Exception {

		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) from pss_product_info s left JOIN pss_productgroup_product t on t.user_id=s.id "
					+ "where t.org_id=" + first$id + " ";
			sql = "select *,s.id as id from pss_product_info s left JOIN pss_productgroup_product t on t.user_id=s.id "
					+ "where t.org_id=" + first$id + " order by s.id desc";
		} else {

			sql_count = "SELECT count(*) from pss_product_info s left JOIN pss_productgroup_product t on t.user_id=s.id "
					+ "where t.org_id=" + first$id + "";
			sql = "select *,s.id as id from pss_product_info s left JOIN pss_productgroup_product t on t.user_id=s.id "
					+ "where t.org_id=" + first$id + " order by s." + sortFieldName + " " + sortOrderName;
		}

		PageCoreBean<Map<String, Object>> pageBean = dao.page(sql, parameters, pageIndex.intValue(),
				pageSize.intValue(), sql_count);

		return pageBean;
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
	public PageCoreBean<Map<String, Object>> find(String sortOrderName, String sortFieldName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM pss_product_info ";
			sql = "SELECT * FROM pss_product_info order by id desc";
		} else {

			sql_count = "SELECT count(*) FROM pss_product_info ";
			sql = "SELECT * FROM pss_product_info order by " + sortFieldName + " " + sortOrderName;
		}

		PageCoreBean<Map<String, Object>> pageBean = dao.page(sql, parameters, pageIndex.intValue(),
				pageSize.intValue(), sql_count);

		return pageBean;
	}

	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM pss_product_info order by id desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}
}
