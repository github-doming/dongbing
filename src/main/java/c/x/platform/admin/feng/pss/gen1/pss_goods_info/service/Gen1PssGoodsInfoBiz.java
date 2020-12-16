package c.x.platform.admin.feng.pss.gen1.pss_goods_info.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import c.x.platform.admin.feng.pss.gen1.pss_goods_info.entity.Gen1PssGoodsInfo;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;

public class Gen1PssGoodsInfoBiz extends BaseService {
	/**
	 * 统计已用的橱窗数量
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long countShowcase() throws Exception {
		String sql = "SELECT count(*) c FROM pss_goods_info where is_showcase=1 order by id desc";
		List<Object> parameterList = new ArrayList<Object>();
		Map<String, Object> map = this.dao.findMap(sql, parameterList);
		Long c = (Long) map.get("c");
		return c;
	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update$is_showcase$false(String id, Long total) throws Exception {

		String sql = "update  pss_goods_info set is_showcase=0,version=version+1 where id=?";

		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 更新
	 * 
	 * @param id
	 * @param total
	 *            橱窗总数( 超过总数抛出异常)
	 * @throws Exception
	 */
	public void update$is_showcase$true(Long id, Long total) throws Exception {

		String sql = "select count(*) cnt  from pss_goods_info where is_showcase=1";

		List<Object> parameterList = new ArrayList<Object>();
		Map map = dao.findMap(sql, null);
		Long cnt = (Long) map.get("cnt");
		if (cnt == total) {
			throw new java.lang.RuntimeException("cnt == total");
		}

		sql = "update  pss_goods_info set is_showcase=1,version=version+1 where id=?";

		parameterList = new ArrayList<Object>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 
	 * (橱窗中的数据)分页
	 * 
	 * @param order
	 * @param order_property
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> queryShowcase(String sortOrderName, String sortFieldName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameterList = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM pss_goods_info ";
			sql = "SELECT * FROM pss_goods_info where is_showcase=1 order by id desc";
		} else {
			sql_count = "SELECT count(*) FROM pss_goods_info ";
			sql = "SELECT * FROM pss_goods_info is_showcase=1 order by " + sortFieldName + " " + sortOrderName;
		}
		PageCoreBean<Map<String, Object>> pageBean = dao.page(sql, parameterList, pageIndex.intValue(),
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
	public List<Map<String, Object>> queryShowcase() throws Exception {
		String sql = "SELECT * FROM pss_goods_info where is_showcase=1 order by id desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}

}
