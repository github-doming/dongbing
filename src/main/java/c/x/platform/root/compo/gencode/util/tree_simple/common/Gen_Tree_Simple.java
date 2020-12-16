package c.x.platform.root.compo.gencode.util.tree_simple.common;
import java.io.IOException;
import java.sql.SQLException;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.compo.gencode.util.nut.GenCode;
import c.x.platform.root.compo.gencode.util.single_simple.common.Gen_Single_Simple;
public class Gen_Tree_Simple extends Gen_Single_Simple {
	/**
	 * 生成config
	 * 
	 * @param root_folderPath
	 * @param package_name
	 * @param pages_name
	 * @param fun_name
	 * @param table_name
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genConfig_treeSimple(String root_folderPath, String urlResourcesName, String pagesResourceName, String packageName,
			String funName, String tableName) throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("config", "mvc.xml", root_folderPath, urlResourcesName, pagesResourceName, packageName, funName,
				tableName);
	}
	/**
	 * 生成jsp_select
	 * 
	 * @param root_folderPath
	 * @param package_name
	 * @param pages_name
	 * @param fun_name
	 * @param table_name
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genJspSelect_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("jsp", "Select.jsp", root_folderPath, urlWebName, pagesResourceName, packageName, funName,
				tableName);
	}
	/**
	 * 生成jsp_parent
	 * 
	 * @param root_folderPath
	 * @param package_name
	 * @param pages_name
	 * @param fun_name
	 * @param table_name
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genJspParent_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		// this.gen_treeSimple("jsp", "Parent.jsp", root_folderPath,
		// urlWebName, pagesResourceName, packageName, funName, tableName);
		this.gen_treeSimple("jsp", "ParentBootstrap.jsp", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
	/**
	 * 
	 * 生成jsp_list
	 * 
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genJspList_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		// this.gen_treeSimple("jsp", "List.jsp", root_folderPath,
		// urlWebName,
		// pagesResourceName, packageName, funName, tableName);
		this.gen_treeSimple("jsp", "ListBootstrap.jsp", root_folderPath, urlWebName, pagesResourceName, packageName, funName,
				tableName);
				// this.gen_treeSimple("jsp", "ListEasyui.jsp", root_folderPath,
				// urlWebName, pagesResourceName, packageName, funName,
				// tableName);
		// this.gen_treeSimple("jsp", "ListMiniui.jsp", root_folderPath,
		// urlWebName, pagesResourceName, packageName, funName, tableName);
		// this.gen_treeSimple("jsp", "ListKaida.jsp", root_folderPath,
		// urlWebName,
		// pagesResourceName, packageName, funName, tableName);
	}
	/**
	 * 
	 * 生成jsp_form
	 * 
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genJspForm_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		// this.gen_treeSimple("jsp", "Form.jsp", root_folderPath,
		// urlWebName,
		// pagesResourceName, packageName, funName, tableName);
		// this.gen_treeSimple("jsp", "FormMiniui.jsp", root_folderPath,
		// urlWebName,
		// pagesResourceName, packageName, funName, tableName);
		this.gen_treeSimple("jsp", "FormBootstrap.jsp", root_folderPath, urlWebName, pagesResourceName, packageName, funName,
				tableName);
		// this.gen_treeSimple("jsp", "FormKaida.jsp", root_folderPath,
		// urlWebName,
		// pagesResourceName, packageName, funName, tableName);
	}
	/**
	 * 
	 * 
	 * 生成action_parent
	 * 
	 * @param root_folderPath
	 * @param package_name
	 * @param pages_name
	 * @param fun_name
	 * @param table_name
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionParent_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("action", "ParentAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
	/**
	 * 生成action_select
	 * 
	 * @param root_folderPath
	 * @param package_name
	 * @param pages_name
	 * @param fun_name
	 * @param table_name
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionSelect_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("action", "SelectAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
	/**
	 * 
	 * 生成action list
	 * 
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionList_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("action", "ListAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
		this.gen_treeSimple("action", "ListJsonAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
		this.gen_treeSimple("action", "IndexAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
	/**
	 * 
	 * 生成action form
	 * 
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionForm_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("action", "FormAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
	/**
	 * 
	 * 生成action del all
	 * 
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionDelAll_treesimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("action", "DelAllAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
	/**
	 * 
	 * 生成action del
	 * 
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionDel_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("action", "DelAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
	/**
	 * 
	 * 生成action save
	 * 
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionSave_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("action", "SaveAction.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
	/**
	 * 
	 * 生成service
	 * 
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genService_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("service", "Service.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
		// this.gen_treeSimple("service", "ServiceObject.java.vm",
		// root_folderPath,
		// urlWebName, pagesResourceName, packageName, funName, tableName);
	}
	/**
	 * 
	 * 生成注释或别名
	 * 
	 * @param root_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genAlias_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName, String packageName,
			String funName, String tableName) throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("alias", "Alias.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName, funName,
				tableName);
	}
	/**
	 * 
	 * 生成vo
	 * 
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genVo_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName, String packageName,
			String funName, String tableName) throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("vo", "Vo.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName, funName,
				tableName);
	}
	/**
	 * 
	 * 生成实体类
	 * 
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genEntity_treeSimple(String root_folderPath, String urlWebName, String pagesResourceName, String packageName,
			String funName, String tableName) throws ClassNotFoundException, SQLException, IOException {
		this.gen_treeSimple("entity", "entity.java.vm", root_folderPath, urlWebName, pagesResourceName, packageName, funName,
				tableName);
	}
	/**
	 * 
	 * 生成代码的最后一个文件夹有下划线
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param type
	 *            类型vo,entity
	 * @param template
	 *            模板文件名
	 * @param final_folderPath
	 * @param packageName
	 * @param pagesResourceName
	 * @param funName
	 * @param tableName
	 *            表名
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void gen_treeSimple_v1(String type, String template, String final_folderPath, String packageName,
			String pagesResourceName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		// 最终生成文件夹路径
		String root_folderPath = null;
		// package_name$custom分割
		String[] pagesResourceNameStrArray = pagesResourceName.split("\\.");
		// jsp路径
		String path_pagesResourceName_all = pagesResourceNameStrArray[0];
		// 资源包路径
		// if(false){
		// String path_urlWebName_all = pagesResourceNameStrArray[0];
		// }
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
				// if(false){
				path_pagesResourceName_all = path_pagesResourceName_all + "\\\\" + pagesResourceNameStrArray[i];
				// }
				// path_pagesResourceName_all = path_pagesResourceName_all + "/"
				// + pagesResourceNameStrArray[i];
				//
				path_urlWebName_all = path_urlWebName_all + "/" + pagesResourceNameStrArray[i];
				pagesResourceNameEnd = pagesResourceNameEnd + "_" + pagesResourceNameStrArray[i];
			}
		}
		// if(false){
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
			// String password = "";
		}
		//
		// String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
		// String url =
		// "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=pushsms0750";
		// String user = "sa";
		// String password = "1";
		// 模板
		String template_relativePath = null;
		if (type.equals("config")) {
			template_relativePath = "config/platform/root/gencode/tree_simple/config/" + template;
		}
		if (type.equals("jsp")) {
			template_relativePath = "config/platform/root/gencode/tree_simple/jsp/" + template;
		}
		if (type.equals("alias") || type.equals("vo") || type.equals("action") || type.equals("service")
				|| type.equals("entity")) {
			template_relativePath = "config/platform/root/gencode/tree_simple/java/" + template;
		}
		// 生成代码的文件夹
		// String root_folderPath = "x:\\gen";
		String root = null;
		if (true) {
			if (type.equals("config")) {
				root = "\\src\\main\\resources\\" + path_urlWebName_all + "\\config\\mvc\\admin";
				root_folderPath = final_folderPath + root + "\\" + funName;
			}
			if (type.equals("jsp")) {
				root = "\\src\\main\\webapp\\pages\\" + path_pagesResourceName_all + "\\admin";
				root_folderPath = final_folderPath + root + "\\" + funName + "\\" + tableName;
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
				root_folderPath = final_folderPath + root + "\\" + funName + "\\" + tableName + "\\" + type;
			}
		}
		if (false) {
			if (type.equals("config")) {
				root = "\\src\\main\\resources\\" + path_pagesResourceName_all + "\\config\\mvc\\admin";
				root_folderPath = final_folderPath + root + "\\" + funName;
			}
			if (type.equals("jsp")) {
				root = "\\src\\main\\webapp\\" + path_pagesResourceName_all + "\\admin";
				root_folderPath = final_folderPath + root + "\\" + funName + "\\" + tableName;
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
				root_folderPath = final_folderPath + root + "\\" + funName + "\\" + tableName + "\\" + type;
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
		genCode.gen_single_tree(final_folderPath, path_urlWebName_all, path_pagesResourceName_all, packageName_all, funName,
				tableName, template_relativePath, root_folderPath, file);
	}
	/**
	 * 
	 * @param type
	 *            类型vo,entity
	 * @param template
	 *            模板文件名
	 * @param final_folderPath
	 * @param packageName
	 * @param pagesResourceName
	 * @param funName
	 * @param tableName
	 *            表名
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void gen_treeSimple(String type, String template, String final_folderPath, String urlResourcesName,
			String pagesResourceName, String packageName, String funName, String tableName)
					throws ClassNotFoundException, SQLException, IOException {
		// 最终生成文件夹路径
		String root_folderPath = null;
		// package_name$custom分割
		String[] pagesResourceNameStrArray = pagesResourceName.split("\\.");
		// resources_name分割
		String[] urlResourcesNameStrArray = urlResourcesName.split("\\.");
		// jsp路径
		String pagePath_pagesResourceNameAll = "";
		// 资源包路径
		String urlPath_urlWebNameAll = "";
		// 组装
		for (int i = 0; i < pagesResourceNameStrArray.length; i++) {
			if (i == 0) {
				pagePath_pagesResourceNameAll = pagesResourceNameStrArray[i];
			} else {
				pagePath_pagesResourceNameAll = pagePath_pagesResourceNameAll + "/" + pagesResourceNameStrArray[i];
			}
		}
		for (int i = 0; i < urlResourcesNameStrArray.length; i++) {
			if (i == 0) {
				urlPath_urlWebNameAll = urlResourcesNameStrArray[i];
			} else {
				urlPath_urlWebNameAll = urlPath_urlWebNameAll + "/" + urlResourcesNameStrArray[i];
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
			template_relativePath = "config/platform/root/gencode/tree_simple/config/" + template;
		}
		if (type.equals("jsp")) {
			template_relativePath = "config/platform/root/gencode/tree_simple/jsp/" + template;
		}
		if (type.equals("alias") || type.equals("vo") || type.equals("action") || type.equals("service")
				|| type.equals("entity")) {
			template_relativePath = "config/platform/root/gencode/tree_simple/java/" + template;
		}
		// 生成代码的文件夹
		// String root_folderPath = "x:\\gen";
		String root = null;
		if (true) {
			if (type.equals("config")) {
				if (false) {
					root = "\\src\\main\\resources\\" + "config\\mvc\\" + urlPath_urlWebNameAll + "";
				}
				root = "\\src\\main\\resources\\" + "config\\mvc\\" + pagePath_pagesResourceNameAll + "";
				root_folderPath = final_folderPath + root + "\\" + tableName;
			}
			if (type.equals("jsp")) {
				root = "\\src\\main\\webapp\\pages\\" + pagePath_pagesResourceNameAll + "";
				root_folderPath = final_folderPath + root + "\\" + tableName + "\\" + funName;
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
				root = "\\src\\main\\java\\" + path_packageName_all_StringBuilder.toString() + "";
				root_folderPath = final_folderPath + root + "\\" + tableName + "\\" + funName + "\\" + type;
			}
		}
		// 生成代码的文件
		String file = null;
		if (type.equals("config")) {
			file = StringUtil.findClassName(tableName) + StringUtil.findClassName(funName) + ".xml";
		}
		if (type.equals("jsp")) {
			// file = template;
			file = StringUtil.findClassName(tableName) + StringUtil.findClassName(funName) + "" + templateName_module
					+ ".jsp";
		}
		if (type.equals("alias") || type.equals("vo") || type.equals("action") || type.equals("service")) {
			file = StringUtil.findClassName(tableName) + StringUtil.findClassName(funName) + templateName_module
					+ ".java";
		}
		if (type.equals("entity")) {
			file = StringUtil.findClassName(tableName) + StringUtil.findClassName(funName) + ".java";
		}
		genCode.gen_single_tree(final_folderPath, urlPath_urlWebNameAll, pagePath_pagesResourceNameAll, packageName_all, funName,
				tableName, template_relativePath, root_folderPath, file);
	}
}
