package c.x.platform.root.compo.gencode.util.single_simple.example.v1;

import java.sql.Connection;
import java.util.List;

import org.apache.velocity.VelocityContext;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.util.core.jdbc.bean.nut.ColumnBean;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.tools.velocity.VelocityUtil;

public class Example_GenSingleSimple {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// 1
			String driver = "org.gjt.mm.mysql.Driver";
				String url = "jdbc:mysql://127.0.0.1:3306/cjx?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Hongkong&zeroDateTimeBehavior=CONVERT_TO_NULL";
			String user = "root";
			String password = "";
			IJdbcTool jdbcTool = JdbcToolFactory.createApi(url);
			IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
			Connection conn = jdbcUtil.openConnection(url, user, password);

			List<ColumnBean> list = jdbcUtil.findColumnBeanListByApi(conn,
					null, "sys_menu_info", null);
			for (ColumnBean info : list) {
				// log.trace(info.getColumnName());
				// log.trace(info.getSqlTypeInt());
				// log.trace(info.getSqlTypeStr());
				// log.trace("--------------------");
			}
			// 2
			// 初始化 VelocityContext
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("list_columns", list);
			// 3
			String template_relativePath = "/config/platform/root/gencode/single_simple/java/entity.java.vm";
			String final_folderPath = "d:\\gen";
			String file = "a.txt";
			VelocityUtil velocityUtil = new VelocityUtil();
			velocityUtil.doGen("d:\\", template_relativePath, velocityContext,
					final_folderPath, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// log.trace("end");
	}
}
