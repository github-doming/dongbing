package c.x.platform.root.compo.gencode.util.nut;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import org.apache.velocity.VelocityContext;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.jdbc.bean.create.TableBean;
import c.a.util.core.jdbc.bean.nut.ColumnBean;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.compo.gencode.util.single_simple.core.bean.tabel.GenTable_single_simple;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.tools.velocity.VelocityUtil;
/**
 * 
 * 生成代码的核心api
 * 
 * @Description:
 * @ClassName: GenCode
 * @date 2017年3月10日 上午10:49:54
 * @author cxy
 * @Email: 
 * @Copyright 
 * 
 */
public class GenCode {
	/**
	 * 生成第1个表的代码
	 * 
	 * @Description:
	 * @Title: gen
	 * @param final_folderPath
	 * @param first_urlPathNameAll
	 * @param first_path_pagesResourceName_all
	 * @param first_packageName_all
	 * @param first_funName
	 * @param first_tableName
	 * @param driver
	 * @param url
	 * @param user
	 * @param password
	 * @param template_relativePath
	 * @param root_folderPath
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void gen_first(String final_folderPath,
			String first_urlPathNameAll,
			String first_path_pagesResourceName_all, String first_packageName_all,
			String first_funName, String first_tableName, String driver,
			String url, String user, String password,
			String template_relativePath, String root_folderPath, String file)
			throws ClassNotFoundException, SQLException, IOException {
		this.gen_double(final_folderPath, first_urlPathNameAll,
				first_path_pagesResourceName_all, first_packageName_all, first_funName,
				first_tableName, null, null, null, null, null, null, null,
				null, null, null, template_relativePath, root_folderPath, file);
	}
	/**
	 * 生成第2个表的代码
	 * 
	 * @Description:
	 * @Title: gen$second
	 * @param final_folderPath
	 * @param second_urlPathNameAll
	 * @param second_path_pagesResourceName_all
	 * @param second_packageName_all
	 * @param second_funName
	 * @param second_tableName
	 * @param template_relativePath
	 * @param root_folderPath
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void gen_second(String final_folderPath,
			String second_urlPathNameAll,
			String second_path_pagesResourceName_all, String second_packageName_all,
			String second_funName, String second_tableName,
			String template_relativePath, String root_folderPath, String file)
			throws ClassNotFoundException, SQLException, IOException {
		this.gen_double(final_folderPath, null, null, null, null, null,
				second_urlPathNameAll, second_path_pagesResourceName_all,
				second_packageName_all, second_funName, second_tableName, null,
				null, null, null, null, template_relativePath, root_folderPath,
				file);
	}
	/**
	 *  生成代码(2表)
	 * 
	 * @Description
	 * @Title gen_double 
	 * @param final_folderPath
	 * @param first_urlPathNameAll
	 * @param first_path_pagesResourceName_all
	 * @param first_packageName_all
	 * @param first_funName
	 * @param first_tableName
	 * @param second_urlPathNameAll
	 * @param second_path_pagesResourceName_all
	 * @param second_packageName_all
	 * @param second_funName
	 * @param second_tableName
	 * @param third_urlPathNameAll
	 * @param third_path_pagesResourceName_all
	 * @param third_packageName_all
	 * @param third_funName
	 * @param third_tableName
	 * @param template_relativePath
	 * @param root_folderPath
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException  参数说明 
	 * @return void    返回类型 
	 * @throws
	 */
	public void gen_double(String final_folderPath,
			String first_urlPathNameAll,
			String first_path_pagesResourceName_all, String first_packageName_all,
			String first_funName, String first_tableName,
			String second_urlPathNameAll,
			String second_path_pagesResourceName_all, String second_packageName_all,
			String second_funName, String second_tableName,
			String third_urlPathNameAll,
			String third_path_pagesResourceName_all, String third_packageName_all,
			String third_funName, String third_tableName,
			String template_relativePath, String root_folderPath, String file)
			throws ClassNotFoundException, SQLException, IOException {
		// 1
		// 初始化 VelocityContext
		VelocityContext velocityContext = new VelocityContext();
		// 特殊符号;
		velocityContext.put("ay_s", "$");
		// 2
		if (true) {
			if (first_tableName != null) {
				// 得到第1个表的 所有列信息
				GenTable_single_simple firstTable = findColumnList(first_tableName);
				// 第1个表的主键
				velocityContext.put("first_fieldNamePk",
						firstTable.getFieldNamePk());
				velocityContext.put("first_columnNamePk",
						firstTable.getColumnNamePk());
				velocityContext.put("first_methodNamePk",
						firstTable.getMethodNamePk());
				// 第1个表的所有列
				velocityContext.put("first_list_column",
						firstTable.getColumnBeanList());
				// 第1个表的自定义表名
				velocityContext.put("first_table_name_custom", first_tableName);
				// 第1个表的表名
				velocityContext.put("first_table_name", first_tableName);
				velocityContext.put("first_table_name_module", first_funName);
				velocityContext.put("first_table_name_child", first_tableName);
				velocityContext.put(
						"first_table_class",
						firstTable.getClassName()
								+ StringUtil.findClassName(first_funName));
				// 第1个表的自定义包名
				velocityContext.put("first_package_name_all",
						first_packageName_all);
				velocityContext.put("first_resources_name_all",
						first_urlPathNameAll);
				velocityContext.put("first_pages_name_all",
						first_path_pagesResourceName_all);
			}
		}
		if (true) {
			if (second_tableName != null) {
				// 得到第2个表的 所有列信息
				GenTable_single_simple second_table = findColumnList(second_tableName);
				// 第2个表的主键
				velocityContext.put("second_fieldNamePk",
						second_table.getFieldNamePk());
				velocityContext.put("second_columnNamePk",
						second_table.getColumnNamePk());
				velocityContext.put("second_methodNamePk",
						second_table.getMethodNamePk());
				// 第2个表的所有列
				velocityContext.put("second_list_column",
						second_table.getColumnBeanList());
				// 第2个表的自定义表名
				velocityContext.put("second_table_name_custom",
						second_tableName);
				// 第2个表的表名
				velocityContext.put("second_table_name", second_tableName);
				velocityContext.put("second_table_name_module", second_funName);
				velocityContext
						.put("second_table_name_child", second_tableName);
				velocityContext.put(
						"second_table_class",
						second_table.getClassName()
								+ StringUtil.findClassName(second_funName));
				// 第2个表的自定义包名
				velocityContext.put("second_package_name_all",
						second_packageName_all);
				velocityContext.put("second_resources_name_all",
						second_urlPathNameAll);
				velocityContext.put("second_pages_name_all",
						second_path_pagesResourceName_all);
			}
		}
		if (true) {
			if (third_tableName != null) {
				// 得到第3个表的 所有列信息
				GenTable_single_simple third_table = findColumnList(third_tableName);
				// 第3个表的主键
				velocityContext.put("third_fieldNamePk",
						third_table.getFieldNamePk());
				velocityContext.put("third_columnNamePk",
						third_table.getColumnNamePk());
				velocityContext.put("third_methodNamePk",
						third_table.getMethodNamePk());
				// 第3个表的所有列
				velocityContext.put("third_list_column",
						third_table.getColumnBeanList());
				// 第3个表的自定义表名
				velocityContext.put("third_table_name_custom", third_tableName);
				// 第3个表的表名
				velocityContext.put("third_table_name", third_tableName);
				velocityContext.put("third_table_name_module", third_funName);
				velocityContext.put("third_table_name_child", third_tableName);
				velocityContext.put(
						"third_table_class",
						third_table.getClassName()
								+ StringUtil.findClassName(third_funName));
				// 第3个表的自定义包名
				velocityContext.put("third_package_name_all",
						third_packageName_all);
				velocityContext.put("third_resources_name_all",
						third_urlPathNameAll);
				velocityContext.put("third_pages_name_all",
						third_path_pagesResourceName_all);
			}
		}
		// 3
		// 生成代码
		VelocityUtil velocityUtil = new VelocityUtil();
		velocityUtil.doGen(final_folderPath, template_relativePath, velocityContext,
				root_folderPath, file);
	}
	/**
	 * 生成代码 single或tree
	 * 
	 * @Description:
	 * @Title: gen_single_tree
	 * @param final_folderPath
	 * @param urlPathNameAll
	 * @param path_pagesResourceName_all
	 * @param packageName_all
	 * @param funName
	 * @param tableName
	 * @param template_relativePath
	 * @param root_folderPath
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void gen_single_tree(String final_folderPath,
			String urlPathNameAll, String path_pagesResourceName_all,
			String packageName_all, String funName, String tableName,
			String template_relativePath, String root_folderPath, String file)
			throws ClassNotFoundException, SQLException, IOException {
		// 1
		// 得到所有列信息
		GenTable_single_simple table = findColumnList(tableName);
		// 2
		// 初始化 VelocityContext
		VelocityContext velocityContext = new VelocityContext();
		// 特殊符号;
		velocityContext.put("ay_s", "$");
		// 主键
		velocityContext.put("fieldNamePk", table.getFieldNamePk());
		velocityContext.put("columnNamePk", table.getColumnNamePk());
		velocityContext.put("methodNamePk", table.getMethodNamePk());
		// 所有列
		velocityContext.put("ay_list_column", table.getColumnBeanList());
		// 自定义表名
		velocityContext.put("ay_table_name_custom", tableName);
		// 表注释
		velocityContext.put("ay_table_comment", table.getTableComment());
		// 表名
		velocityContext.put("ay_table_name", tableName);
		velocityContext.put("ay_table_name_module", funName);
		velocityContext.put("ay_table_name_child", tableName);
		velocityContext.put("ay_table_class",
				table.getClassName() + StringUtil.findClassName(funName));
		// 自定义包名
		velocityContext.put("ay_package_name", packageName_all);
		velocityContext.put("resources_name_all", urlPathNameAll);
		velocityContext.put("pages_name_all", path_pagesResourceName_all);
		// 3
		// 生成代码
		VelocityUtil velocityUtil = new VelocityUtil();
		velocityUtil.doGen(final_folderPath, template_relativePath,
				velocityContext, root_folderPath, file);
	}
	/**
	 * 
	 * 得到所有列信息(转换)
	 * 
	 * @Description:
	 * @Title: findColumnList
	 * @param table_name
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException 参数说明
	 * @return GenTable_single_simple 返回类型
	 * @throws
	 */
	public GenTable_single_simple findColumnList(String tableName)
			throws ClassNotFoundException, SQLException {
		// 1引用
		IJdbcTool jdbcTools = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTools.getJdbcUtil();
		// 2得到连接
		Connection conn = jdbcUtil.getConnection();
		// 3返回值
		GenTable_single_simple genTable = new GenTable_single_simple();
		// 表注释
		List<TableBean> tableBeanList = jdbcUtil.findTableBeanListByApi(conn,
				null, tableName);
		//System.out.println("tableName="+tableName);
		//System.out.println("tableBeanList.size()="+tableBeanList.size());
		AssertUtil.equalNot(1, tableBeanList.size(), "表个数不为1或找不到表,tableName="+tableName);
		TableBean tableBean = tableBeanList.get(0);
		// 5得到所有列
		List<ColumnBean> columnBeanList = jdbcUtil.findColumnBeanListByApi(
				conn, null, tableName, null);
		// 6得到主键
		String primary_key = jdbcUtil.findTablePrimaryKeyByApi(conn, null,
				tableName);
		for (ColumnBean columnBean : columnBeanList) {
			// 是否主键
			if (primary_key.equals(columnBean.getColumnName())) {
				columnBean.setIsPk(true);
				genTable.setFieldNamePk(columnBean.getFieldName());
				genTable.setMethodNamePk(columnBean.getMethodName());
				genTable.setColumnNamePk(columnBean.getColumnName());
			} else {
				columnBean.setIsPk(false);
			}
			// 注释或别名
			if (StringUtil.isBlank(columnBean.getComment())) {
				columnBean.setComment(columnBean.getColumnName());
			} else {
				columnBean.setComment(columnBean.getComment());
			}
			// 是否为空
			if (columnBean.getIsNullInt() == ResultSetMetaData.columnNullable) {
				columnBean.setIsNullStr("yes");
			} else {
				columnBean.setIsNullStr("no"); // NOT NULL
			}
			// 列名长度
			// columnBean.setLength(columnBean.getLength());
			// 小数长度
			// columnBean.setScale(columnBean.getScale());
			// 列的默认值
			// columnBean.setColumnDef(columnBean.getColumnDef());
			// list加列
			// columnBeanList.add(columnBean);
		}
		// table加list
		genTable.setColumnBeanList(columnBeanList);
		// 自定义表名
		genTable.setTableNameCustom(tableName);
		// 表名
		genTable.setTableName(tableName);
		// 表名转换
		genTable.setClassName(tableName);
		genTable.setPackageName(tableName);
		genTable.setObjectName(tableName);
		genTable.setTableComment(tableBean.getComment());
		return genTable;
	}
}
