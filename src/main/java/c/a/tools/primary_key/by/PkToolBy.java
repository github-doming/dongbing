package c.a.tools.primary_key.by;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.util.core.asserts.AssertUtil;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
public class PkToolBy {
	protected static Logger log = LogManager.getLogger(PkToolBy.class);
	public synchronized static Long findLong(String tableName) throws Exception {
		AssertUtil.isBlank(tableName, " 找不到表名");
		// 返回的值
		Long nextLong = null;
		AyDao dao = new AyDao();
		String sql = "SELECT * FROM SYS_PK where TABLE_NAME_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(tableName);
		Map<String, Object> resultMap = dao.findMap(sql, parameterList);
		// 如果列表没有记录，表示该表未创建Sequance，则新增表Sequance
		if (resultMap != null) {
			// 得到表的id
			String idx = (String) resultMap.get("SYS_PK_ID_");
			// 得到
			nextLong = (Long) resultMap.get("NEXT_LONG_");
			// 得到
			Integer step = (Integer) resultMap.get("STEP_");
			if (step == null) {
				step = 1;
			}
			// 加1
			nextLong = nextLong + step;
			// 赋值
			// 更新
			parameterList = new ArrayList<Object>();
			sql = "UPDATE SYS_PK  set NEXT_LONG_=? where SYS_PK_ID_=?";
			parameterList.add(nextLong);
			parameterList.add(idx);
			dao.execute(sql, parameterList);
		} else {
			// 设置值为1
			nextLong = 1l;
			// 赋值
			// 新增
			parameterList = new ArrayList<Object>();
			sql = "INSERT INTO SYS_PK (SYS_PK_ID_,TABLE_NAME_,NEXT_LONG_) values(?,?,?)";
			parameterList.add(UUID.randomUUID().toString());
			parameterList.add(tableName);
			parameterList.add(nextLong);
			dao.execute(sql, parameterList);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("表名【" + tableName + "】");
		sb.append("主键业务ID=【" + nextLong + "】");
		log.trace(sb.toString());
		return nextLong;
	}
}
