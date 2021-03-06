package c.x.platform.admin.feng.bbs.buy.bbs_post_info.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import c.x.platform.admin.feng.bbs.buy.bbs_post_info.entity.BbsPostInfo;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;

public class BbsPostInfoService extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BbsPostInfo find(String id) throws Exception {
		return (BbsPostInfo) this.dao.find(BbsPostInfo.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(BbsPostInfo entity) throws Exception {
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
		String sql = "delete from bbs_post_info where id in(" + stringBuffer.toString() + ")";

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

		String sql = "delete from bbs_post_info where id=?";
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
	public String save(BbsPostInfo entity) throws Exception {

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
		ArrayList<Object> parameterList = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) from bbs_post_info s left JOIN bbs_forum_post t on t.post_id=s.id "
					+ "where t.forum_id=" + first$id + " ";
			sql = "select *,s.id as id from bbs_post_info s left JOIN bbs_forum_post t on t.post_id=s.id "
					+ "where t.forum_id=" + first$id + " order by s.id desc";
		} else {

			sql_count = "SELECT count(*) from bbs_post_info s left JOIN bbs_forum_post t on t.post_id=s.id "
					+ "where t.forum_id=" + first$id + "";
			sql = "select *,s.id as id from bbs_post_info s left JOIN bbs_forum_post t on t.post_id=s.id "
					+ "where t.forum_id=" + first$id + " order by s." + sortFieldName + " " + sortOrderName;
		}

		PageCoreBean<Map<String, Object>> pageBean = dao.page(sql, parameterList, pageIndex.intValue(),
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
		ArrayList<Object> parameterList = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM bbs_post_info ";
			sql = "SELECT * FROM bbs_post_info order by id desc";
		} else {

			sql_count = "SELECT count(*) FROM bbs_post_info ";
			sql = "SELECT * FROM bbs_post_info order by " + sortFieldName + " " + sortOrderName;
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
	public List<BbsPostInfo> findAll() throws Exception {

		String sql = "SELECT * FROM bbs_post_info order by id desc";
		List<BbsPostInfo> list = this.dao.findObjectList(BbsPostInfo.class, sql);

		return list;
	}
}
