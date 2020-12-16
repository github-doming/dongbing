package c.x.platform.root.compo.gencode.util.double_simple.common;

import java.io.IOException;
import java.sql.SQLException;

import c.x.platform.root.compo.gencode.util.nut.GenCode;
import c.a.util.core.string.StringUtil;

/**
 * 
 * 生成3个表，代码都相同，最后一行不同
 * 
 * 
 * 
 */
public class Gen_Double_Simple {
	/**
	 * 
	 * 
	 * 
	 * 第3个表的
	 * 
	 * 生成jsp_index
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genJspIndex(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
//		this.gen_doubleSimple("jsp", "jsp", "Index.jsp", final_folderPath,
//				first_urlWebName, first_pagesResourceName, first_packageName,
//				first_funName, first_tableName, second_urlWebName,
//				second_pagesResourceName, second_packageName, second_funName,
//				second_tableName, third_urlWebName, third_pagesResourceName,
//				third_packageName, third_funName, third_tableName);

		this.gen_doubleSimple("jsp", "jsp", "IndexBootstrap.jsp",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
		
//		this.gen_doubleSimple("jsp", "jsp", "IndexEasyui.jsp",
//				final_folderPath, first_urlWebName, first_pagesResourceName,
//				first_packageName, first_funName, first_tableName,
//				second_urlWebName, second_pagesResourceName, second_packageName,
//				second_funName, second_tableName, third_urlWebName,
//				third_pagesResourceName, third_packageName, third_funName,
//				third_tableName);
	}

	/**
	 * 
	 * 第3个表的
	 * 
	 * 
	 * 生成service
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genService_doubleSimple_third(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("service$third", "service", "Service.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);

//		this.gen_doubleSimple("service$third", "service",
//				"ServiceObject.java.vm", final_folderPath, first_urlWebName,
//				first_pagesResourceName, first_packageName, first_funName,
//				first_tableName, second_urlWebName, second_pagesResourceName,
//				second_packageName, second_funName, second_tableName,
//				third_urlWebName, third_pagesResourceName, third_packageName,
//				third_funName, third_tableName);
	}

	/**
	 * 第3个表的
	 * 
	 * 
	 * 生成注释或别名
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genAlias_doubleSimple_third(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("alias$third", "alias", "Alias.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * 第3个表的
	 * 
	 * 
	 * 生成vo
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genVo_doubleSimple_third(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("vo$third", "vo", "Vo.java.vm", final_folderPath,
				first_urlWebName, first_pagesResourceName, first_packageName,
				first_funName, first_tableName, second_urlWebName,
				second_pagesResourceName, second_packageName, second_funName,
				second_tableName, third_urlWebName, third_pagesResourceName,
				third_packageName, third_funName, third_tableName);
	}

	/**
	 * 
	 * 第3个表的
	 * 
	 * 生成实体类
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genEntity_doubleSimple_third(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("entity$third", "entity", "entity.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * 第2个表的
	 * 
	 * 
	 * 生成config
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genConfig_singleSimple(String final_folderPath,
			String first_urlResourcesName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("config$second", "config", "mvc.xml",
				final_folderPath, first_urlResourcesName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * 第2个表的
	 * 
	 * 
	 * 生成jsp_list
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genJspList_singleSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
//		this.gen_doubleSimple("jsp$second", "jsp", "List.jsp",
//				final_folderPath, first_urlWebName, first_pagesResourceName,
//				first_packageName, first_funName, first_tableName,
//				second_urlWebName, second_pagesResourceName, second_packageName,
//				second_funName, second_tableName, third_urlWebName,
//				third_pagesResourceName, third_packageName, third_funName,
//				third_tableName);

		this.gen_doubleSimple("jsp$second", "jsp", "ListBootstrap.jsp",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);

	}

	/**
	 * 
	 * 
	 * 第2个表的
	 * 
	 * 
	 * 生成jsp_form
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genJspForm_singleSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
//		this.gen_doubleSimple("jsp$second", "jsp", "Form.jsp",
//				final_folderPath, first_urlWebName, first_pagesResourceName,
//				first_packageName, first_funName, first_tableName,
//				second_urlWebName, second_pagesResourceName, second_packageName,
//				second_funName, second_tableName, third_urlWebName,
//				third_pagesResourceName, third_packageName, third_funName,
//				third_tableName);

		this.gen_doubleSimple("jsp$second", "jsp", "FormBootstrap.jsp",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);

	}

	/**
	 * 
	 * 第2个表的
	 * 
	 * 
	 * 生成action list
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionList_singleSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("action$second", "action", "ListAction.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
		this.gen_doubleSimple("action$second", "action", "ListJsonAction.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * 第2个表的
	 * 
	 * 生成action form
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionForm_singleSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("action$second", "action", "FormAction.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 第2个表的
	 * 
	 * 
	 * 生成action del all
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionDelAll_singleSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("action$second", "action",
				"DelAllAction.java.vm", final_folderPath, first_urlWebName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
	}

	/**
	 * 第2个表的
	 * 
	 * 生成action del
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionDel_singleSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("action$second", "action", "DelAction.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
		this.gen_doubleSimple("action$second", "action", "DelJsonAction.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 第2个表的 生成action save
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionSave_singleSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("action$second", "action", "SaveAction.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
		this.gen_doubleSimple("action$second", "action", "SaveJsonAction.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * 第2个表的
	 * 
	 * 
	 * 生成service
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genService_singleSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("service$second", "service", "Service.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);

//		this.gen_doubleSimple("service$second", "service",
//				"ServiceObject.java.vm", final_folderPath, first_urlWebName,
//				first_pagesResourceName, first_packageName, first_funName,
//				first_tableName, second_urlWebName, second_pagesResourceName,
//				second_packageName, second_funName, second_tableName,
//				third_urlWebName, third_pagesResourceName, third_packageName,
//				third_funName, third_tableName);
	}

	/**
	 * 第2个表的
	 * 
	 * 
	 * 生成注释或别名
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genAlias_singleSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("alias$second", "alias", "Alias.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * 第2个表的
	 * 
	 * 
	 * 生成vo
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genVo_singleSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("vo$second", "vo", "Vo.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * 第2个表的
	 * 
	 * 生成实体类
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genEntity_singleSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("entity$second", "entity", "entity.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * 第1个表的
	 * 
	 * 
	 * 生成config
	 * 
	 * @param final_folderPath
	 * @param package_name
	 * @param pages_name
	 * @param fun_name
	 * @param table_name
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genConfig_treeSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("config$first", "config", "mvc.xml",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * 第1个表的
	 * 
	 * 
	 * 生成jsp_select
	 * 
	 * @param final_folderPath
	 * @param first_urlWebName
	 * @param first_packageName
	 * @param first_pagesResourceName
	 * @param first_funName
	 * @param first_tableName
	 * @param second_urlWebName
	 * @param second_packageName
	 * @param second_pagesResourceName
	 * @param second_funName
	 * @param second_tableName
	 * @param third_urlWebName
	 * @param third_packageName
	 * @param third_pagesResourceName
	 * @param third_funName
	 * @param third_tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genJspSelect_treeSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("jsp$first", "jsp", "Select.jsp",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);

	}

	/**
	 * 
	 * 第1个表的
	 * 
	 * 
	 * 生成jsp_parent
	 * 
	 * @param final_folderPath
	 * @param first_urlWebName
	 * @param first_packageName
	 * @param first_pagesResourceName
	 * @param first_funName
	 * @param first_tableName
	 * @param second_urlWebName
	 * @param second_packageName
	 * @param second_pagesResourceName
	 * @param second_funName
	 * @param second_tableName
	 * @param third_urlWebName
	 * @param third_packageName
	 * @param third_pagesResourceName
	 * @param third_funName
	 * @param third_tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genJspParent_treeSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("jsp$first", "jsp", "Parent.jsp",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);

		this.gen_doubleSimple("jsp$first", "jsp", "ParentBootstrap.jsp",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);

	}

	/**
	 * 第1个表的
	 * 
	 * 生成jsp_list
	 * 
	 * @param final_folderPath
	 * @param first_urlWebName
	 * @param first_packageName
	 * @param first_pagesResourceName
	 * @param first_funName
	 * @param first_tableName
	 * @param second_urlWebName
	 * @param second_packageName
	 * @param second_pagesResourceName
	 * @param second_funName
	 * @param second_tableName
	 * @param third_urlWebName
	 * @param third_packageName
	 * @param third_pagesResourceName
	 * @param third_funName
	 * @param third_tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genJspList_treeSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
//		this.gen_doubleSimple("jsp$first", "jsp", "List.jsp", final_folderPath,
//				first_urlWebName, first_pagesResourceName, first_packageName,
//				first_funName, first_tableName, second_urlWebName,
//				second_pagesResourceName, second_packageName, second_funName,
//				second_tableName, third_urlWebName, third_pagesResourceName,
//				third_packageName, third_funName, third_tableName);

		this.gen_doubleSimple("jsp$first", "jsp", "ListBootstrap.jsp",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);

	}

	/**
	 * 第1个表的 生成action_parent
	 * 
	 * @param final_folderPath
	 * @param first_urlWebName
	 * @param first_packageName
	 * @param first_pagesResourceName
	 * @param first_funName
	 * @param first_tableName
	 * @param second_urlWebName
	 * @param second_packageName
	 * @param second_pagesResourceName
	 * @param second_funName
	 * @param second_tableName
	 * @param third_urlWebName
	 * @param third_packageName
	 * @param third_pagesResourceName
	 * @param third_funName
	 * @param third_tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionParent_treeSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("action$first", "action", "ParentAction.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 第1个表的 生成action_select
	 * 
	 * @param final_folderPath
	 * @param first_urlWebName
	 * @param first_packageName
	 * @param first_pagesResourceName
	 * @param first_funName
	 * @param first_tableName
	 * @param second_urlWebName
	 * @param second_packageName
	 * @param second_pagesResourceName
	 * @param second_funName
	 * @param second_tableName
	 * @param third_urlWebName
	 * @param third_packageName
	 * @param third_pagesResourceName
	 * @param third_funName
	 * @param third_tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genActionSelect_treeSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("action$first", "action", "SelectAction.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * 第1个表的
	 * 
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
	public void genActionList_treeSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("action$first", "action", "ListAction.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
		this.gen_doubleSimple("action$first", "action", "ListJsonAction.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * 
	 * 第1个表的
	 * 
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
	public void genService_treeSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("service$first", "service", "Service.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);

//		this.gen_doubleSimple("service$first", "service",
//				"ServiceObject.java.vm", final_folderPath, first_urlWebName,
//				first_pagesResourceName, first_packageName, first_funName,
//				first_tableName, second_urlWebName, second_pagesResourceName,
//				second_packageName, second_funName, second_tableName,
//				third_urlWebName, third_pagesResourceName, third_packageName,
//				third_funName, third_tableName);
	}

	/**
	 * 
	 * 第1个表的
	 * 
	 * 生成注释或别名
	 * 
	 * @param final_folderPath
	 * @param tableName_custom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genAlias_treeSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("alias$first", "alias", "Alias.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * 第1个表的
	 * 
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
	public void genVo_treeSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("vo$first", "vo", "Vo.java.vm", final_folderPath,
				first_urlWebName, first_pagesResourceName, first_packageName,
				first_funName, first_tableName, second_urlWebName,
				second_pagesResourceName, second_packageName, second_funName,
				second_tableName, third_urlWebName, third_pagesResourceName,
				third_packageName, third_funName, third_tableName);
	}

	/**
	 * 
	 * 生成第1个表的实体类
	 * 
	 * @param final_folderPath
	 * @param first_urlWebName
	 * @param first_packageName
	 * @param first_pagesResourceName
	 * @param first_funName
	 * @param first_tableName
	 * @param second_urlWebName
	 * @param second_packageName
	 * @param second_pagesResourceName
	 * @param second_funName
	 * @param second_tableName
	 * @param third_urlWebName
	 * @param third_packageName
	 * @param third_pagesResourceName
	 * @param third_funName
	 * @param third_tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genEntity_treeSimple(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		this.gen_doubleSimple("entity$first", "entity", "entity.java.vm",
				final_folderPath, first_urlWebName, first_pagesResourceName,
				first_packageName, first_funName, first_tableName,
				second_urlWebName, second_pagesResourceName, second_packageName,
				second_funName, second_tableName, third_urlWebName,
				third_pagesResourceName, third_packageName, third_funName,
				third_tableName);
	}

	/**
	 * 
	 * @param typeTable
	 *            类型vo,entity
	 * @param template
	 *            模板文件名
	 * @param final_folderPath
	 * @param first_packageName
	 * @param first_pagesResourceName
	 * @param first_funName
	 * @param first_tableName
	 *            表名
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void gen_doubleSimple(String typeTable, String template$type,
			String template, String final_folderPath,
			String first_urlResourcesName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		// 最终生成文件夹路径
		String root_folderPath = null;
		// pages_name分割
		String[] first_pagesResourceNameStrArray = first_pagesResourceName.split("\\.");
		// log.trace("first_pagesResourceNameStrArray.length="+first_pagesResourceNameStrArray.length);
		String[] second_pagesResourceNameStrArray = second_pagesResourceName.split("\\.");
		String[] third_pagesResourceNameStrArray = null;
		if (third_pagesResourceName != null) {
			third_pagesResourceNameStrArray = third_pagesResourceName.split("\\.");
		}
		// resources_name分割
		String[] first_urlWebNameStrArray = first_urlResourcesName.split("\\.");
		String[] second_urlWebNameStrArray = second_urlWebName
				.split("\\.");
		String[] third_urlWebNameStrArray = null;
		if (third_pagesResourceName != null) {
			third_urlWebNameStrArray = third_urlWebName.split("\\.");
		}
		// jsp路径
		String first_path_pagesResourceName_all = "";
		String second_path_pagesResourceName_all = "";
		String third_path_pagesResourceName_all = "";
		// 资源包路径
		String first_path_urlWebName_all = "";
		String second_path_urlWebName_all = "";
		String third_path_urlWebName_all = "";
		// 组装第1个表
		for (int i = 0; i < first_pagesResourceNameStrArray.length; i++) {
			if (i == 0) {
				first_path_pagesResourceName_all = first_pagesResourceNameStrArray[i];
			} else {
				first_path_pagesResourceName_all = first_path_pagesResourceName_all + "/"
						+ first_pagesResourceNameStrArray[i];
			}
		}
		for (int i = 0; i < first_urlWebNameStrArray.length; i++) {
			if (i == 0) {
				first_path_urlWebName_all = first_urlWebNameStrArray[i];
			} else {
				first_path_urlWebName_all = first_path_urlWebName_all
						+ "/" + first_urlWebNameStrArray[i];
			}
		}
		// 组装第2个表
		for (int i = 0; i < second_pagesResourceNameStrArray.length; i++) {
			if (i == 0) {
				second_path_pagesResourceName_all = second_pagesResourceNameStrArray[i];
			} else {
				second_path_pagesResourceName_all = second_path_pagesResourceName_all + "/"
						+ second_pagesResourceNameStrArray[i];
			}
		}
		for (int i = 0; i < second_urlWebNameStrArray.length; i++) {
			if (i == 0) {
				second_path_urlWebName_all = second_urlWebNameStrArray[i];
			} else {
				second_path_urlWebName_all = second_path_urlWebName_all
						+ "/" + second_urlWebNameStrArray[i];
			}
		}
		// 组装第3个表
		if (third_urlWebName != null) {
			for (int i = 0; i < third_pagesResourceNameStrArray.length; i++) {
				if (i == 0) {
					third_path_urlWebName_all = third_pagesResourceNameStrArray[i];
				} else {
					third_path_urlWebName_all = third_path_urlWebName_all
							+ "/" + third_pagesResourceNameStrArray[i];
				}
			}
		}
		if (third_pagesResourceName != null) {
			for (int i = 0; i < third_urlWebNameStrArray.length; i++) {
				third_path_pagesResourceName_all = third_path_pagesResourceName_all + "/"
						+ third_urlWebNameStrArray[i];
			}
		}
		// 模板名分割
		int index = template.indexOf(".");
		int length = template.length();
		String templateName_module = template.substring(0, index);
		// 包名
		String first_packageName_all = first_packageName + "." + first_funName
				+ "." + first_tableName;
		String second_packageName_all = second_packageName + "."
				+ second_funName + "." + second_tableName;
		String third_packageName$all = third_packageName + "." + third_funName
				+ "." + third_tableName;
		GenCode genCode = new GenCode();

		// 模板
		String template_relativePath = null;
		if (typeTable.equals("config$first")) {
			template_relativePath = "config/platform/root/gencode/double_simple/first/config/"
					+ template;
		}
		if (typeTable.equals("config$second")) {
			template_relativePath = "config/platform/root/gencode/double_simple/second/config/"
					+ template;
		}
		if (typeTable.equals("jsp")) {
			template_relativePath = "config/platform/root/gencode/double_simple/jsp/"
					+ template;
		}
		if (typeTable.equals("jsp$first")) {
			template_relativePath = "config/platform/root/gencode/double_simple/first/jsp/"
					+ template;
		}
		if (typeTable.equals("jsp$second")) {
			template_relativePath = "config/platform/root/gencode/double_simple/second/jsp/"
					+ template;
		}
		if (typeTable.equals("alias") || typeTable.equals("vo")
				|| typeTable.equals("action") || typeTable.equals("service")
				|| typeTable.equals("entity")) {
		}
		if (typeTable.equals("alias$first") || typeTable.equals("vo$first")
				|| typeTable.equals("action$first")
				|| typeTable.equals("service$first")
				|| typeTable.equals("entity$first")) {
			template_relativePath = "config/platform/root/gencode/double_simple/first/java/"
					+ template;
		}
		if (typeTable.equals("alias$second") || typeTable.equals("vo$second")
				|| typeTable.equals("action$second")
				|| typeTable.equals("service$second")
				|| typeTable.equals("entity$second")) {
			template_relativePath = "config/platform/root/gencode/double_simple/second/java/"
					+ template;
		}
		if (typeTable.equals("alias$third") || typeTable.equals("vo$third")
				|| typeTable.equals("action$third")
				|| typeTable.equals("service$third")
				|| typeTable.equals("entity$third")) {
			template_relativePath = "config/platform/root/gencode/double_simple/third/java/"
					+ template;
		}
		String root = null;
		if (true) {
			if (typeTable.equals("config")) {
			}
			if (typeTable.equals("config$first")) {
				if(false){
				root = "\\src\\main\\resources\\"+"\\config\\mvc\\"
						+ first_path_urlWebName_all + "";
				}
				root = "\\src\\main\\resources\\" + "config\\mvc\\" + first_path_pagesResourceName_all + "";
				
				
				root_folderPath = final_folderPath + root + "\\"
						+ first_funName;
			}
			if (typeTable.equals("config$second")) {
				if(false){
				root = "\\src\\main\\resources\\"+"\\config\\mvc\\"
						+ second_path_urlWebName_all + "";
				}
				root = "\\src\\main\\resources\\" + "config\\mvc\\" + second_path_pagesResourceName_all + "";
				
				root_folderPath = final_folderPath + root + "\\"
						+ second_funName;
			}
			if (typeTable.equals("jsp")) {
				root = "\\src\\main\\webapp\\pages\\"
						+ first_path_pagesResourceName_all + "";
				root_folderPath = final_folderPath + root + "\\"
						+ first_funName;
			}
			if (typeTable.equals("jsp$first")) {
				root = "\\src\\main\\webapp\\pages\\"
						+ first_path_pagesResourceName_all + "";
				root_folderPath = final_folderPath + root + "\\"
						+ first_funName + "\\" + first_tableName;
			}
			if (typeTable.equals("jsp$second")) {
				root = "\\src\\main\\webapp\\pages\\"
						+ first_path_pagesResourceName_all + "";
				root_folderPath = final_folderPath + root + "\\"
						+ second_funName + "\\" + second_tableName;
			}
			if (typeTable.equals("alias") || typeTable.equals("vo")
					|| typeTable.equals("action")
					|| typeTable.equals("service")
					|| typeTable.equals("entity")) {
			}
			if (typeTable.equals("alias$first") || typeTable.equals("vo$first")
					|| typeTable.equals("action$first")
					|| typeTable.equals("service$first")
					|| typeTable.equals("entity$first")) {
				String[] packageNameStrArray = first_packageName.split("\\.");
				StringBuilder path_packageName_all_StringBuilder = new StringBuilder();
				for (String path_packageName_str : packageNameStrArray) {
					path_packageName_all_StringBuilder
							.append(path_packageName_str);
					path_packageName_all_StringBuilder.append("\\\\");
				}
				path_packageName_all_StringBuilder
						.deleteCharAt(path_packageName_all_StringBuilder
								.length() - 1);
				path_packageName_all_StringBuilder
						.deleteCharAt(path_packageName_all_StringBuilder
								.length() - 1);
				root = "\\src\\main\\java\\"
						+ path_packageName_all_StringBuilder.toString() + "";
				root_folderPath = final_folderPath + root + "\\"
						+ first_funName + "\\" + first_tableName + "\\"
						+ template$type;
			}
			if (typeTable.equals("alias$second")
					|| typeTable.equals("vo$second")
					|| typeTable.equals("action$second")
					|| typeTable.equals("service$second")
					|| typeTable.equals("entity$second")) {
				String[] packageNameStrArray = second_packageName.split("\\.");
				StringBuilder path_packageName_all_StringBuilder = new StringBuilder();
				for (String path_packageName_str : packageNameStrArray) {
					path_packageName_all_StringBuilder
							.append(path_packageName_str);
					path_packageName_all_StringBuilder.append("\\\\");
				}
				path_packageName_all_StringBuilder
						.deleteCharAt(path_packageName_all_StringBuilder
								.length() - 1);
				path_packageName_all_StringBuilder
						.deleteCharAt(path_packageName_all_StringBuilder
								.length() - 1);
				root = "\\src\\main\\java\\"
						+ path_packageName_all_StringBuilder.toString() + "";
				root_folderPath = final_folderPath + root + "\\"
						+ second_funName + "\\" + second_tableName + "\\"
						+ template$type;
			}
			if (typeTable.equals("alias$third") || typeTable.equals("vo$third")
					|| typeTable.equals("action$third")
					|| typeTable.equals("service$third")
					|| typeTable.equals("entity$third")) {
				String[] packageNameStrArray = second_packageName.split("\\.");
				StringBuilder path_packageName_all_StringBuilder = new StringBuilder();
				for (String path_packageName_str : packageNameStrArray) {
					path_packageName_all_StringBuilder
							.append(path_packageName_str);
					path_packageName_all_StringBuilder.append("\\\\");
				}
				path_packageName_all_StringBuilder
						.deleteCharAt(path_packageName_all_StringBuilder
								.length() - 1);
				path_packageName_all_StringBuilder
						.deleteCharAt(path_packageName_all_StringBuilder
								.length() - 1);
				root = "\\src\\main\\java\\"
						+ path_packageName_all_StringBuilder.toString() + "";
				root_folderPath = final_folderPath + root + "\\"
						+ third_funName + "\\" + third_tableName + "\\"
						+ template$type;
			}
		}
		// 生成代码的文件
		String file = null;
		if (typeTable.equals("config$first")) {
			file = first_tableName + ".xml";
		}
		if (typeTable.equals("config$second")) {
			file = second_tableName + ".xml";
		}
		if (typeTable.equals("jsp")) {
			// file = template;
			file = StringUtil.findClassName(first_tableName)
					+ StringUtil.findClassName(first_funName) + ""
					+ templateName_module + ".jsp";
		}
		if (typeTable.equals("jsp$first")) {
			// file = template;
			file = StringUtil.findClassName(first_tableName)
					+ StringUtil.findClassName(first_funName) + ""
					+ templateName_module + ".jsp";
		}
		if (typeTable.equals("jsp$second")) {
			// file = template;
			file = StringUtil.findClassName(second_tableName)
					+ StringUtil.findClassName(second_funName) + ""
					+ templateName_module + ".jsp";
		}
		if (typeTable.equals("jsp$third")) {
			// file = template;
			file = StringUtil.findClassName(third_tableName)
					+ StringUtil.findClassName(third_funName) + ""
					+ templateName_module + ".jsp";
		}
		if (typeTable.equals("alias") || typeTable.equals("vo")
				|| typeTable.equals("action") || typeTable.equals("service")) {
		}
		if (typeTable.equals("alias$first") || typeTable.equals("vo$first")
				|| typeTable.equals("action$first")
				|| typeTable.equals("service$first")) {
			file = StringUtil.findClassName(first_tableName)
					+ StringUtil.findClassName(first_funName)
					+ templateName_module + ".java";
		}
		if (typeTable.equals("alias$second") || typeTable.equals("vo$second")
				|| typeTable.equals("action$second")
				|| typeTable.equals("service$second")) {
			file = StringUtil.findClassName(second_tableName)
					+ StringUtil.findClassName(second_funName)
					+ templateName_module + ".java";
		}
		if (typeTable.equals("alias$third") || typeTable.equals("vo$third")
				|| typeTable.equals("action$third")
				|| typeTable.equals("service$third")) {
			file = StringUtil.findClassName(third_tableName)
					+ StringUtil.findClassName(third_funName)
					+ templateName_module + ".java";
		}
		if (typeTable.equals("entity$first")) {
			file = StringUtil.findClassName(first_tableName)
					+ StringUtil.findClassName(first_funName) + ".java";
		}
		if (typeTable.equals("entity$second")) {
			file = StringUtil.findClassName(second_tableName)
					+ StringUtil.findClassName(second_funName) + ".java";
		}
		if (typeTable.equals("entity$third")) {
			file = StringUtil.findClassName(third_tableName)
					+ StringUtil.findClassName(third_funName) + ".java";
		}

		genCode.gen_double(final_folderPath, first_path_urlWebName_all,
				first_path_pagesResourceName_all, first_packageName_all, first_funName,
				first_tableName, second_path_urlWebName_all,
				second_path_pagesResourceName_all, second_packageName_all,
				second_funName, second_tableName, third_path_urlWebName_all,
				third_path_pagesResourceName_all, third_packageName$all, third_funName,
				third_tableName, template_relativePath, root_folderPath, file);
	}
}
