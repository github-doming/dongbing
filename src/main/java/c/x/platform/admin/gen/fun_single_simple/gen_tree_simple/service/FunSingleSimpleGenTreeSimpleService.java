package c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.util.core.string.StringUtil;
import c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.entity.FunSingleSimpleGenTreeSimple;
import c.x.platform.root.common.service.BaseService;

public class FunSingleSimpleGenTreeSimpleService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	public FunSingleSimpleGenTreeSimple find(String id) throws Exception {
		return (FunSingleSimpleGenTreeSimple) this.dao.find(
				FunSingleSimpleGenTreeSimple.class, id);
	}

	public void update(FunSingleSimpleGenTreeSimple entity) throws Exception {
		dao.update(entity);
		// 更新所有节点的path
		if (StringUtil.isNotBlank(entity.getParent())) {
			FunSingleSimpleGenTreeSimple parent = this.find(entity.getParent()
					.toString());
			log.trace("父母id=" + parent.getFunSingleSimpleId());
			this.updatePath(" fun_single_simple", "FUN_SINGLE_SIMPLE_ID_", parent
					.getFunSingleSimpleId().toString());
		}
	}

	public void del(String id) throws Exception {
		// 打出本身的path
		StringBuilder sb = new StringBuilder();
		sb.append("  select PATH_ FROM  fun_single_simple where FUN_SINGLE_SIMPLE_ID_='");
		sb.append(id.trim());
		sb.append("'");
		String sql_query = sb.toString();
		FunSingleSimpleGenTreeSimple parent = (FunSingleSimpleGenTreeSimple) dao
				.findObject(FunSingleSimpleGenTreeSimple.class, sql_query,
						null);
		String parent_path = parent.getPath();
		// 清空 StringBuilder
		sb.setLength(0);
		// 删除所有孩子
		sb.append(" delete  from fun_single_simple where PATH_ like ?");
		String sql_del = sb.toString();
		List<Object> parameters = new ArrayList<Object>();
		// delete from fun_single_simple where path like '%1.'
		parameters.add("" + parent_path + "%");
		dao.execute(sql_del, parameters);
	}

	public void save(FunSingleSimpleGenTreeSimple entity) throws Exception {
		String parentId = entity.getParent();
		// entity.setIsShow(1);
		// entity.setPermissionGrade(0);
		if (StringUtil.isBlank(parentId)) {
			parentId = "1";
			entity.setParent(parentId);

		}
		FunSingleSimpleGenTreeSimple parent = this.find(parentId);
		// 设置主键
		String pk = this.findPK(entity);
		if (StringUtil.isNotBlank(pk)) {
			entity.setFunSingleSimpleId(pk);
			// 构造path
			String path = parent.getPath() + entity.getFunSingleSimpleId() + ".";
			entity.setPath(path);
			// 保存
			dao.savePkNot(entity);
		}

	}

	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM fun_single_simple order by FUN_SINGLE_SIMPLE_ID_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}
}
