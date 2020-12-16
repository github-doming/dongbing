package c.x.platform.root.compo.gencode.run.single_simple.i1;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.x.platform.root.compo.gencode.util.common.BaseGen;
import c.a.util.core.file.FileUtil;
import c.a.util.core.jdbc.bean.create.TableBean;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.test.CommTest;
import c.a.tools.velocity.VelocityConfig;
public class Gen_single_simple_Example_e5 extends CommTest {
	@Test
	public void execute() {
		// 数据库设置
		String driver = c.x.platform.root.compo.gencode.util.config.Database.driverT;
		String url = c.x.platform.root.compo.gencode.util.config.Database.urlT;
		String username = c.x.platform.root.compo.gencode.util.config.Database.userT;
		String password = c.x.platform.root.compo.gencode.util.config.Database.pwdT;
		IJdbcTool jdbcTool = JdbcToolFactory.createApi(url);
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = null;
		try {
			conn = jdbcUtil.openConnection(url, username, password);
			jdbcUtil.setConnection(conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 保存连接到ThreadLocal
		 */
		JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
		VelocityConfig.velocityProperties = "/config/platform/root/velocity/velocity.properties";
		BaseGen gen = new BaseGen();
		String final_folderPath = "d:\\gen";
		try {

			// 快速开发框架
			// 参数
			String sql = null;
			ArrayList<Object> parameterList = null;
			String filePath = "d://gen//mvc.txt";
			FileUtil fileUtil = new FileUtil();
			String schemata = "c2";
			String tableName = null;
			String tableComment = null;
			// 业务
			Connection connDB = jdbcUtil.openConnection(url, username, password);
			List<TableBean> tableBeanList = jdbcUtil.findTableBeanListByApi(connDB, schemata, null);
			for (TableBean table : tableBeanList) {
				log.trace("table name=" + table.getTableName());
				if (table.getTableName().indexOf("WF_") ==0) {
					
				}else{
					continue;
				}
				
//				if (table.getTableName().indexOf("qrtz_") != -1) {
//					continue;
//				}
//				if (table.getTableName().indexOf("cnt_") != -1) {
//					continue;
//				}
				tableName = table.getTableName().toLowerCase();
				tableComment = table.getComment();
				if (StringUtil.isBlank(tableComment)) {
					tableComment = tableName;
				}
				String tableNameClass = StringUtil.findClassName(tableName);
				String content = "/config/mvc/all/gen/" + tableName + "/" + tableNameClass + "T.xml";
				fileUtil.appendByAbsolutePath(filePath, content);

				// 生成代码
				gen.gen_singleSimple(final_folderPath, "all.gen", "all.gen", "all.gen", "t", tableName);
				// 菜单生成
				sql = "insert into SYS_MENU (SYS_MENU_ID_,PARENT_,PATH_,NAME_,URL_,PERMISSION_GRADE_,IS_SHOW_) values(?,?,?,?,?,?,?) ";
				parameterList = new ArrayList<Object>();
				String id = UUID.randomUUID().toString();
				String parent = "fdaaa222-ad6b-44a6-87e9-4cd76b1bb8b4";
				String parentPath = "1.a0c9a158-3ba3-467d-83af-5d6ed25713b4.fdaaa222-ad6b-44a6-87e9-4cd76b1bb8b4.";
				String menuUrl = "/wsd/" + tableName + "/cx/list.do";
				parameterList.add(id);
				parameterList.add(parent);
				parameterList.add(parentPath + id + ".");
				parameterList.add(tableComment);
				parameterList.add(menuUrl);
				parameterList.add("10");
				parameterList.add("1");
				// jdbcUtil.execute(conn, sql, parameterList);
			}

		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		log.trace("end");
	}
}
