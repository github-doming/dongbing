package c.x.platform.admin.feng.wf.util.example.i1.def;

import java.sql.Connection;
import java.util.Calendar;

import c.x.platform.admin.feng.wf.util.bean.WorkFlowXmlBean;
import c.x.platform.admin.feng.wf.util.nut.def.WorkFlowDefineTools;
import c.a.tools.log.custom.common.BaseLog;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.util.core.jdbc.nut.IJdbcUtil;

public class WorkFlowDefineTools_example_e2 {
	public static void main(String[] args) {

		// 时间计算
		Calendar calendar = Calendar.getInstance();
		long long_start = calendar.getTimeInMillis();
		// 执行业务
		try {
			// 数据库连接
				String url = "jdbc:mysql://127.0.0.1:3306/cjx?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Hongkong&zeroDateTimeBehavior=CONVERT_TO_NULL";
			String username = "root";
			String password = "";
			IJdbcTool jdbcTool = JdbcToolFactory.createApi(url);
			IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
			// AyDao dao = new AyDao();
			Connection conn = null;
			conn = jdbcUtil.openConnection(url, username, password);

			// 调用
			WorkFlowDefineTools tools = new WorkFlowDefineTools();
			WorkFlowXmlBean bean = tools.defProcess_parse();
			tools.defProcess_saveBean(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 时间计算
		calendar = Calendar.getInstance();
		long long_end = calendar.getTimeInMillis();
		long long_t = long_end - long_start;
		BaseLog.trace("花费时间t=" + long_t);
	}

}
