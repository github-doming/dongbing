package c.x.platform.root.compo.gencode.util.single_simple.common;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.compo.gencode.util.nut.GenCode;
public class Gen_Single_Simple {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 生成config
	 * @Title: genConfig_singleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param final_folderPath
	 * @param urlWebName
	 * @param pagesResourceName
	 * @param packageName
	 * @param funName
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void genConfig_singleSimple(String final_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("config", "mvc.xml", final_folderPath, urlWebName, pagesResourceName, packageName, funName,
				tableName);
	}
	/**
	 *  生成jsp_list
	 * @Title: genJspList_singleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param root_folderPath
	 * @param urlWebName
	 * @param pagesResourceName
	 * @param packageName
	 * @param funName
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void genJspList_singleSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("jsp", "ListBootstrap.jsp", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
		if(true){
			this.gen_singleSimple("jsp", "List.jsp", root_folderPath, urlWebName, pagesResourceName, packageName, funName,
					tableName);
			this.gen_singleSimple("jsp", "ListEasyui.jsp", root_folderPath, urlWebName, pagesResourceName, packageName,
					funName, tableName);
			this.gen_singleSimple("jsp", "ListMiniui.jsp", root_folderPath, urlWebName, pagesResourceName, packageName,
					funName, tableName);
		}
		if(true){
			this.gen_singleSimple("jsp", "ListObject.jsp", root_folderPath, urlWebName, pagesResourceName, packageName,
					funName, tableName);
			this.gen_singleSimple("jsp", "ListBootstrapObject.jsp", root_folderPath, urlWebName, pagesResourceName,
					packageName, funName, tableName);
			this.gen_singleSimple("jsp", "ListKaida.jsp", root_folderPath, urlWebName, pagesResourceName, packageName,
					funName, tableName);
			this.gen_singleSimple("jsp", "ListKaidaObject.jsp", root_folderPath, urlWebName, pagesResourceName,
					packageName, funName, tableName);
		}
	}
	/**
	 *  生成jsp_form
	 * @Title: genJspForm_singleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param root_folderPath
	 * @param urlWebName
	 * @param pagesResourceName
	 * @param packageName
	 * @param funName
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void genJspForm_singleSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("jsp", "FormBootstrap.jsp", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
		if(true){
			this.gen_singleSimple("jsp", "Form.jsp", root_folderPath, urlWebName, pagesResourceName, packageName, funName,
					tableName);
			this.gen_singleSimple("jsp", "FormMiniui.jsp", root_folderPath, urlWebName, pagesResourceName, packageName,
					funName, tableName);
			this.gen_singleSimple("jsp", "FormKaida.jsp", root_folderPath, urlWebName, pagesResourceName, packageName,
					funName, tableName);
		}
	}
	/**
	 * 生成action list
	 * @Title: genActionList_singleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param root_folderPath
	 * @param urlWebName
	 * @param pagesResourceName
	 * @param packageName
	 * @param funName
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void genActionList_singleSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("action", "ListAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
		this.gen_singleSimple("action", "ListJsonAction.java.vm", root_folderPath, urlWebName, pagesResourceName,
				packageName, funName, tableName);
	}
	/**
	 *  生成action form
	 * @Title: genActionForm_singleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param root_folderPath
	 * @param urlWebName
	 * @param pagesResourceName
	 * @param packageName
	 * @param funName
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void genActionForm_singleSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("action", "FormAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
	/**
	 * 生成action del all
	 * @Title: genActionDelAll_singleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param root_folderPath
	 * @param urlWebName
	 * @param pagesResourceName
	 * @param packageName
	 * @param funName
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void genActionDelAll_singleSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("action", "DelAllAction.java.vm", root_folderPath, urlWebName, pagesResourceName,
				packageName, funName, tableName);
		this.gen_singleSimple("action", "DelAllJsonAction.java.vm", root_folderPath, urlWebName, pagesResourceName,
				packageName, funName, tableName);
	}
	/**
	 * 生成action del
	 * @Title: genActionDel_singleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param root_folderPath
	 * @param urlWebName
	 * @param pagesResourceName
	 * @param packageName
	 * @param funName
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void genActionDel_singleSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("action", "DelAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
		this.gen_singleSimple("action", "DelJsonAction.java.vm", root_folderPath, urlWebName, pagesResourceName,
				packageName, funName, tableName);
	}
	/**
	 * 生成action save
	 * @Title: genActionSave_singleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param root_folderPath
	 * @param urlWebName
	 * @param pagesResourceName
	 * @param packageName
	 * @param funName
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void genActionSave_singleSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("action", "SaveAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
		this.gen_singleSimple("action", "SaveJsonAction.java.vm", root_folderPath, urlWebName, pagesResourceName,
				packageName, funName, tableName);
	}
	/**
	 *  生成service
	 * @Title: genService_singleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param root_folderPath
	 * @param urlWebName
	 * @param pagesResourceName
	 * @param packageName
	 * @param funName
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void genService_singleSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("service", "Service.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
		if(true){
			this.gen_singleSimple("service", "ServiceObject.java.vm", root_folderPath, urlWebName, pagesResourceName,
					packageName, funName, tableName);
		}
	}
	/**
	 * 生成注释或别名
	 * @Title: genAlias_singleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param root_folderPath
	 * @param urlWebName
	 * @param pagesResourceName
	 * @param packageName
	 * @param funName
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void genAlias_singleSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("alias", "Alias.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
	/**
	 *  生成vo
	 * @Title: genVo_singleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param root_folderPath
	 * @param urlWebName
	 * @param pagesResourceName
	 * @param packageName
	 * @param funName
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void genVo_singleSimple(String root_folderPath, String urlWebName, String pagesResourceName, String packageName,
			String funName, String tableName) throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("vo", "Vo.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName, funName,
				tableName);
	}
	/**
	 * 生成实体类
	 * @Title: genEntity_singleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param root_folderPath
	 * @param urlWebName
	 * @param pagesResourceName
	 * @param packageName
	 * @param funName
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void genEntity_singleSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("entity", "entity.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
	/**
	 * 
	 * 
	 * 生成代码的最后一个文件夹有下划线
	 * 
	 * 生成代码
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param type
	 *            类型vo,entity
	 * @param template
	 *            模板文件名
	 * @param root_folderPath
	 * @param tableName
	 *            表名
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void gen_singleSimple_v2(String type, String template, String root_folderPath, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		// 最终生成文件夹路径
		String final_folderPath = null;
		// package_name$custom分割
		String[] pagesResourceNameStrArray = pagesResourceName.split("\\.");
		// jsp路径
		String path_pagesResourceName_all = pagesResourceNameStrArray[0];
		// 资源包路径
		if (false) {
			String path_urlWebName_all = pagesResourceNameStrArray[0];
		}
		String path_urlWebName_all = "config";
		// 最后一个文件夹名称
		String pagesResourceNameEnd = pagesResourceNameStrArray[0];
		for (int i = 1; i < pagesResourceNameStrArray.length; i++) {
			if (i == pagesResourceNameStrArray.length - 1) {
				// if (false) {
				// 不组装
				pagesResourceNameEnd = pagesResourceNameEnd + "_" + pagesResourceNameStrArray[i];
				// }
				// pagesResourceNameEnd = pagesResourceNameStrArray[i];
			} else {
				// if (false) {
				path_pagesResourceName_all = path_pagesResourceName_all + "\\\\" + pagesResourceNameStrArray[i];
				// }
				// path_pagesResourceName_all = path_pagesResourceName_all + "/"
				// + pagesResourceNameStrArray[i];
				path_urlWebName_all = path_urlWebName_all + "/" + pagesResourceNameStrArray[i];
				pagesResourceNameEnd = pagesResourceNameEnd + "_" + pagesResourceNameStrArray[i];
			}
		}
		// if (false) {
		path_pagesResourceName_all = path_pagesResourceName_all + "\\\\" + pagesResourceNameEnd;
		// }
		// path_pagesResourceName_all = path_pagesResourceName_all + "/" + pagesResourceNameEnd;
		path_urlWebName_all = path_urlWebName_all + "/" + pagesResourceNameEnd;
		if (false) {
			log.trace(path_pagesResourceName_all);
		}
		// 模板名分割
		int index = template.indexOf(".");
		int length = template.length();
		String templateName_module = template.substring(0, index);
		if (false) {
			log.trace("模板 templateName_module=" + templateName_module);
		}
		if (false) {
			// 表名分割
		}
		// 包名
		String packageName_all = packageName + ".admin." + funName + "." + tableName;
		GenCode genCode = new GenCode();
		// 数据库设置
		String driver = c.x.platform.root.compo.gencode.util.config.Database.driver;
		String url = c.x.platform.root.compo.gencode.util.config.Database.url;
		String user = c.x.platform.root.compo.gencode.util.config.Database.user;
		String password = c.x.platform.root.compo.gencode.util.config.Database.pwd;
		if (false) {
			// String driver = "org.gjt.mm.mysql.Driver";
			// String url = "jdbc:mysql://localhost:4306/simple";
			// String user = "root";
			// String password = "1";
		}
		if (false) {
			// String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
			// String url =
			// "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=pushsms0750";
			// String user = "sa";
			// String password = "1";
		}
		// 模板
		String template_relativePath = null;
		if (type.equals("config")) {
			template_relativePath = "config/platform/root/gencode/single_simple/config/" + template;
		}
		if (type.equals("jsp")) {
			template_relativePath = "config/platform/root/gencode/single_simple/jsp/" + template;
		}
		if (type.equals("alias") || type.equals("vo") || type.equals("action") || type.equals("service")
				|| type.equals("entity")) {
			template_relativePath = "config/platform/root/gencode/single_simple/java/" + template;
		}
		// 生成代码的文件夹
		// String root_folderPath = "x:\\gen";
		String root = null;
		if (true) {
			if (type.equals("config")) {
				root = "\\src\\main\\resources\\" + path_urlWebName_all + "\\config\\mvc\\admin";
				final_folderPath = root_folderPath + root + "\\" + funName;
			}
			if (type.equals("jsp")) {
				root = "\\src\\main\\webapp\\" + path_pagesResourceName_all + "\\admin";
				final_folderPath = root_folderPath + root + "\\" + funName + "\\" + tableName;
			}
			if (type.equals("alias") || type.equals("vo") || type.equals("action") || type.equals("service")
					|| type.equals("entity")) {
				String[] packageNameStrArray = packageName.split("\\.");
				StringBuilder path_packageName_all_StringBuilder = new StringBuilder();
				for (String path_packageName_str : packageNameStrArray) {
					path_packageName_all_StringBuilder.append(path_packageName_str);
					path_packageName_all_StringBuilder.append("\\\\");
				}
				path_packageName_all_StringBuilder.deleteCharAt(path_packageName_all_StringBuilder.length() - 1);
				path_packageName_all_StringBuilder.deleteCharAt(path_packageName_all_StringBuilder.length() - 1);
				root = "\\src\\main\\java\\" + path_packageName_all_StringBuilder.toString() + "\\admin";
				final_folderPath = root_folderPath + root + "\\" + funName + "\\" + tableName + "\\" + type;
			}
		}
		if (false) {
			if (type.equals("config")) {
				root = "\\src\\main\\resources\\" + path_pagesResourceName_all + "\\config\\mvc\\admin";
				final_folderPath = root_folderPath + root + "\\" + funName;
			}
			if (type.equals("jsp")) {
				root = "\\src\\main\\webapp\\" + path_pagesResourceName_all + "\\admin";
				final_folderPath = root_folderPath + root + "\\" + funName + "\\" + tableName;
			}
			if (type.equals("alias") || type.equals("vo") || type.equals("action") || type.equals("service")
					|| type.equals("entity")) {
				String[] packageNameStrArray = packageName.split("\\.");
				StringBuilder path_packageName_all_StringBuilder = new StringBuilder();
				for (String path_packageName_str : packageNameStrArray) {
					path_packageName_all_StringBuilder.append(path_packageName_str);
					path_packageName_all_StringBuilder.append("\\\\");
				}
				path_packageName_all_StringBuilder.deleteCharAt(path_packageName_all_StringBuilder.length() - 1);
				path_packageName_all_StringBuilder.deleteCharAt(path_packageName_all_StringBuilder.length() - 1);
				root = "\\src\\main\\java\\" + path_packageName_all_StringBuilder.toString() + "\\admin";
				final_folderPath = root_folderPath + root + "\\" + funName + "\\" + tableName + "\\" + type;
			}
		}
		// 生成代码的文件
		String file = null;
		if (type.equals("config")) {
			file = tableName + ".xml";
		}
		if (type.equals("jsp")) {
			file = template;
		}
		if (type.equals("alias") || type.equals("vo") || type.equals("action") || type.equals("service")) {
			file = StringUtil.findClassName(tableName) + templateName_module + ".java";
		}
		if (type.equals("entity")) {
			file = StringUtil.findClassName(tableName) + ".java";
		}
		genCode.gen_single_tree(root_folderPath, path_urlWebName_all, path_pagesResourceName_all, packageName_all, funName,
				tableName, template_relativePath, final_folderPath, file);
	}
	/**
	 * 
	 * 生成代码
	 * 
	 * @Description: desc
	 * 
	 * @Title: gen_singleSimple
	 * 
	 * @param type
	 *            类型vo,entity
	 * 
	 * @param template
	 *            模板文件名
	 * 
	 * @param final_folderPath
	 * 
	 * @param urlWebName
	 *            url配置
	 * 
	 * @param pagesResourceName
	 *            页面和mvc配置
	 * @param packageName
	 *            包
	 * @param funName
	 *            功能名
	 * 
	 * @param tableName
	 *            表名
	 * 
	 * @throws ClassNotFoundException
	 * 
	 * @throws SQLException
	 * 
	 * @throws IOException
	 *             参数说明
	 * 
	 * @return void 返回类型
	 */
	public void gen_singleSimple(String type, String template, String final_folderPath, String urlWebName,
			String pagesResourceName, String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		// 最终生成文件夹路径
		String root_folderPath = null;
		// package_name$custom分割
		String[] pagesResourceNameStrArray = pagesResourceName.split("\\.");
		// resources_name分割
		String[] urlWebNameStrArray = urlWebName.split("\\.");
		// jsp路径
		String path_pagesResourceName_all = "";
		// 资源包路径
		String path_urlWebName_all = "";
		// 组装
		for (int i = 0; i < pagesResourceNameStrArray.length; i++) {
			if (i == 0) {
				path_pagesResourceName_all = pagesResourceNameStrArray[i];
			} else {
				path_pagesResourceName_all = path_pagesResourceName_all + "/" + pagesResourceNameStrArray[i];
			}
		}
		for (int i = 0; i < urlWebNameStrArray.length; i++) {
			if (i == 0) {
				path_urlWebName_all = urlWebNameStrArray[i];
			} else {
				path_urlWebName_all = path_urlWebName_all + "/" + urlWebNameStrArray[i];
			}
		}
		// 模板名分割
		int index = template.indexOf(".");
		int length = template.length();
		String templateName_module = template.substring(0, index);
		// 包名
		String packageName_all = packageName + "." + tableName + "." + funName;
		GenCode genCode = new GenCode();
		// 模板
		String template_relativePath = null;
		if (type.equals("config")) {
			template_relativePath = "config/platform/root/gencode/single_simple/config/" + template;
		}
		if (type.equals("jsp")) {
			template_relativePath = "config/platform/root/gencode/single_simple/jsp/" + template;
		}
		if (type.equals("alias") || type.equals("vo") || type.equals("action") || type.equals("service")
				|| type.equals("entity")) {
			template_relativePath = "config/platform/root/gencode/single_simple/java/" + template;
		}
		// 生成代码的文件夹
		// String root_folderPath = "d:\\gen";
		String root = null;
		if (true) {
			if (type.equals("config")) {
				// log.trace("配置final_folderPath=" + final_folderPath);
				// log.trace("配置path_urlWebName_all=" +
				// path_urlWebName_all);
				if (false) {
					root = "\\src\\main\\resources\\" + "config\\mvc\\" + path_urlWebName_all + "";
				}
				root = "\\src\\main\\resources\\" + "config\\mvc\\" + path_pagesResourceName_all + "";
				root_folderPath = final_folderPath + root + "\\" + tableName;
				// log.trace("配置root_folderPath=" + root_folderPath);
			}
			if (type.equals("jsp")) {
				root = "\\src\\main\\webapp\\pages\\" + path_pagesResourceName_all + "";
				root_folderPath = final_folderPath + root + "\\" + tableName + "\\" + funName;
			}
			if (type.equals("alias") || type.equals("vo") || type.equals("action") || type.equals("service")
					|| type.equals("entity")) {
				String[] packageNameStrArray = packageName.split("\\.");
				StringBuilder path_packageName_all_stringBuffer = new StringBuilder();
				for (String path_packageName_str : packageNameStrArray) {
					path_packageName_all_stringBuffer.append(path_packageName_str);
					path_packageName_all_stringBuffer.append("\\\\");
				}
				path_packageName_all_stringBuffer.deleteCharAt(path_packageName_all_stringBuffer.length() - 1);
				path_packageName_all_stringBuffer.deleteCharAt(path_packageName_all_stringBuffer.length() - 1);
				root = "\\src\\main\\java\\" + path_packageName_all_stringBuffer.toString() + "";
				root_folderPath = final_folderPath + root + "\\" + tableName + "\\" + funName + "\\" + type;
			}
		}
		// 生成代码的文件
		String file = null;
		if (type.equals("config")) {
			file = StringUtil.findClassName(tableName) + StringUtil.findClassName(funName) + ".xml";
		}
		if (type.equals("jsp")) {
			file = StringUtil.findClassName(tableName) + StringUtil.findClassName(funName) + templateName_module
					+ ".jsp";
		}
		if (type.equals("alias") || type.equals("vo") || type.equals("action") || type.equals("service")) {
			file = StringUtil.findClassName(tableName) + StringUtil.findClassName(funName) + templateName_module
					+ ".java";
		}
		if (type.equals("entity")) {
			file = StringUtil.findClassName(tableName) + StringUtil.findClassName(funName) + ".java";
		}
		genCode.gen_single_tree(final_folderPath, path_urlWebName_all, path_pagesResourceName_all, packageName_all,
				funName, tableName, template_relativePath, root_folderPath, file);
	}
}
