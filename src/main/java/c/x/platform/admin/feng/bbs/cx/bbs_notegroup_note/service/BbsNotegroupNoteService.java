package c.x.platform.admin.feng.bbs.cx.bbs_notegroup_note.service;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import c.x.platform.admin.feng.bbs.cx.bbs_notegroup_note.entity.BbsNotegroupNote;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
public class BbsNotegroupNoteService extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BbsNotegroupNote find(String id) throws Exception {
		return (BbsNotegroupNote) this.dao.find(BbsNotegroupNote.class, id);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(BbsNotegroupNote entity) throws Exception {
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
		String sql = "delete from bbs_notegroup_note where id in("
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
		String sql = "delete from bbs_notegroup_note where id=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}
	/**
	 * 通过部门ID与人员ID
	 * 
	 * 删除
	 * 
	 * @param notegroup_id
	 * @param note_id
	 * @throws Exception
	 */
	public void del(String notegroup_id, String note_id) throws Exception {
		String sql = "delete from bbs_notegroup_note where notegroup_id=? and note_id=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(notegroup_id);
		parameterList.add(note_id);
		dao.execute(sql, parameterList);
	}
	/**
	 * 通过用户ID
	 * 
	 * 删除
	 * 
	 * @param note_id
	 * @throws Exception
	 */
	public void del_by_userId(String note_id) throws Exception {
		String sql = "delete from bbs_notegroup_note where  note_id=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(note_id);
		dao.execute(sql, parameterList);
	}
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String insert(BbsNotegroupNote entity) throws Exception {
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
			sql_count = "SELECT count(*) FROM bbs_notegroup_note ";
			sql = "SELECT * FROM bbs_notegroup_note order by id desc";
		} else {
			sql_count = "SELECT count(*) FROM bbs_notegroup_note ";
			sql = "SELECT * FROM bbs_notegroup_note order by " + sortFieldName
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
	public List<BbsNotegroupNote> listAll() throws Exception {
		String sql = "SELECT * FROM bbs_notegroup_note order by id desc";
		List<BbsNotegroupNote> list = this.dao.findObjectList(
				BbsNotegroupNote.class, sql);
		return list;
	}
}
