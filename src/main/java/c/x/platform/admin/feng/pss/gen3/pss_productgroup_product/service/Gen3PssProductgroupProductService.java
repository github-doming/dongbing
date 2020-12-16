package c.x.platform.admin.feng.pss.gen3.pss_productgroup_product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import c.x.platform.admin.feng.pss.gen3.pss_productgroup_product.entity.Gen3PssProductgroupProduct;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;

public class Gen3PssProductgroupProductService extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Gen3PssProductgroupProduct find(String id) throws Exception {
		return (Gen3PssProductgroupProduct) this.dao.find(
				Gen3PssProductgroupProduct.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(Gen3PssProductgroupProduct entity) throws Exception {
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
		String sql = "delete from pss_productgroup_product where id in("
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

		String sql = "delete from pss_productgroup_product where id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	/**
	 * 通过部门ID与人员ID
	 * 
	 * 删除
	 * 
	 * @param org_id
	 * @param user_id
	 * @throws Exception
	 */
	public void del(String org_id, String user_id) throws Exception {

		String sql = "delete from pss_productgroup_product where org_id=? and user_id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(org_id);
		parameters.add(user_id);
		dao.execute(sql, parameters);
	}

	/**
	 * 通过用户ID
	 * 
	 * 删除
	 * 
	 * @param user_id
	 * @throws Exception
	 */
	public void del_by_userId(String user_id) throws Exception {

		String sql = "delete from pss_productgroup_product where  user_id=?";
		List<Object> parameters = new ArrayList<Object>();

		parameters.add(user_id);
		dao.execute(sql, parameters);
	}
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(Gen3PssProductgroupProduct entity) throws Exception {

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
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM pss_productgroup_product ";
			sql = "SELECT * FROM pss_productgroup_product order by id desc";
		} else {

			sql_count = "SELECT count(*) FROM pss_productgroup_product ";
			sql = "SELECT * FROM pss_productgroup_product order by "
					+ sortFieldName + " " + sortOrderName;
		}

		PageCoreBean<Map<String, Object>> pageBean = dao.page(sql, parameters,
				pageIndex.intValue(), pageSize.intValue(), sql_count);

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
		String sql = "SELECT * FROM pss_productgroup_product order by id desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}
}
