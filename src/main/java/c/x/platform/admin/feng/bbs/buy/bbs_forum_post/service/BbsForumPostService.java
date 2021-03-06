package c.x.platform.admin.feng.bbs.buy.bbs_forum_post.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import c.x.platform.admin.feng.bbs.buy.bbs_forum_post.entity.BbsForumPost;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;

public class BbsForumPostService extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BbsForumPost find(String id) throws Exception {
		return (BbsForumPost) this.dao.find(BbsForumPost.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(BbsForumPost entity) throws Exception {
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
		String sql = "delete from bbs_forum_post where id in(" + stringBuffer.toString() + ")";

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

		String sql = "delete from bbs_forum_post where id=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
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
	public void del(String forum_id, String post_id) throws Exception {

		String sql = "delete from bbs_forum_post where forum_id=? and post_id=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(forum_id);
		parameterList.add(post_id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 通过post_id
	 * 
	 * 删除
	 * 
	 * @param user_id
	 * @throws Exception
	 */
	public void del_by_postId(String user_id) throws Exception {

		String sql = "delete from bbs_forum_post where post_id=?";
		List<Object> parameterList = new ArrayList<Object>();

		parameterList.add(user_id);
		dao.execute(sql, parameterList);
	}
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(BbsForumPost entity) throws Exception {

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
	public PageCoreBean<Map<String, Object>> find(String sortOrderName, String sortFieldName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameterList = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM bbs_forum_post ";
			sql = "SELECT * FROM bbs_forum_post order by id desc";
		} else {

			sql_count = "SELECT count(*) FROM bbs_forum_post ";
			sql = "SELECT * FROM bbs_forum_post order by " + sortFieldName + " " + sortOrderName;
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
	public List<BbsForumPost> findAll() throws Exception {

		String sql = "SELECT * FROM bbs_forum_post order by id desc";
		List<BbsForumPost> list = this.dao.findObjectList(BbsForumPost.class, sql);

		return list;
	}
}
