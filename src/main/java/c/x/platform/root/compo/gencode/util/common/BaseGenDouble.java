package c.x.platform.root.compo.gencode.util.common;
import java.io.IOException;
import java.sql.SQLException;

import c.x.platform.root.compo.gencode.util.double_simple.common.Gen_Double_Simple;
public class BaseGenDouble extends Gen_Double_Simple {
	/**
	 * 
	 * 三个表相同，而且配置，page,包都相同
	 * 
	 * 功能包需要不同
	 * 
	 * double_简单功能_快速生成
	 * 
	 * @param final_folderPath
	 * @param first_urlWebName
	 * @param first_pagesResourceName
	 * @param first_packageName
	 * @param first_funName
	 * @param first_tableName
	 * @param second_urlWebName
	 * @param second_pagesResourceName
	 * @param second_packageName
	 * @param second_funName
	 * @param second_tableName
	 * @param third_urlWebName
	 * @param third_pagesResourceName
	 * @param third_packageName
	 * @param third_funName
	 * @param third_tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void gen_doubleSimple_sameTable(String final_folderPath,
			String first_urlWebName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_funName, String third_funName)
			throws ClassNotFoundException, SQLException, IOException {
		gen_doubleSimple(final_folderPath, first_urlWebName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, first_urlWebName, first_pagesResourceName,
				first_packageName, second_funName, first_tableName,
				first_urlWebName, first_pagesResourceName, first_packageName,
				third_funName, first_tableName);
	}
	/**
	 *  三个表不同，但是配置，page,包都相同
	 * 
	 * 
	 * double_简单功能_快速生成
	 * @Title: gen_doubleSimple 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param final_folderPath 
	 * @param first_urlResourcesName 主表的资源目录
	 * @param first_pagesResourceName 页面
	 * @param first_packageName 包名
	 * @param first_funName 功能表
	 * @param first_tableName 表名
	 * @param second_tableName
	 * @param third_tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * 返回类型 void
	 */
	public void gen_doubleSimple(String final_folderPath,
			String first_urlResourcesName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_tableName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		gen_doubleSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, first_urlResourcesName, first_pagesResourceName,
				first_packageName, first_funName, second_tableName,
				first_urlResourcesName, first_pagesResourceName, first_packageName,
				first_funName, third_tableName);
	}
	/**
	 * double_简单功能_快速生成
	 * 
	 * @param final_folderPath
	 * @param first_urlResourcesName
	 * @param first_pagesResourceName
	 * @param first_packageName
	 * @param first_funName
	 * @param first_tableName
	 * @param second_urlWebName
	 * @param second_pagesResourceName
	 * @param second_packageName
	 * @param second_funName
	 * @param second_tableName
	 * @param third_urlWebName
	 * @param third_pagesResourceName
	 * @param third_packageName
	 * @param third_funName
	 * @param third_tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void gen_doubleSimple(String final_folderPath,
			String first_urlResourcesName, String first_pagesResourceName,
			String first_packageName, String first_funName,
			String first_tableName, String second_urlWebName,
			String second_pagesResourceName, String second_packageName,
			String second_funName, String second_tableName,
			String third_urlWebName, String third_pagesResourceName,
			String third_packageName, String third_funName,
			String third_tableName) throws ClassNotFoundException,
			SQLException, IOException {
		// 两个表共同的功能，生成jsp
		this.genJspIndex(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第3个表的生成service
		this.genService_doubleSimple_third(final_folderPath,
				first_urlResourcesName, first_pagesResourceName, first_packageName,
				first_funName, first_tableName, second_urlWebName,
				second_pagesResourceName, second_packageName, second_funName,
				second_tableName, third_urlWebName, third_pagesResourceName,
				third_packageName, third_funName, third_tableName);
		// 第3个表
		// 生成注释或别名
		this.genAlias_doubleSimple_third(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第3个表的生成vo
		this.genVo_doubleSimple_third(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第3个表的生成实体
		this.genEntity_doubleSimple_third(final_folderPath,
				first_urlResourcesName, first_pagesResourceName, first_packageName,
				first_funName, first_tableName, second_urlWebName,
				second_pagesResourceName, second_packageName, second_funName,
				second_tableName, third_urlWebName, third_pagesResourceName,
				third_packageName, third_funName, third_tableName);
		// 第2个表的生成配置
		this.genConfig_singleSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第2个表的生成jsp
		this.genJspForm_singleSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		this.genJspList_singleSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第2个表的生成action
		this.genActionSave_singleSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		this.genActionDel_singleSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		this.genActionDelAll_singleSimple(final_folderPath,
				first_urlResourcesName, first_pagesResourceName, first_packageName,
				first_funName, first_tableName, second_urlWebName,
				second_pagesResourceName, second_packageName, second_funName,
				second_tableName, third_urlWebName, third_pagesResourceName,
				third_packageName, third_funName, third_tableName);
		this.genActionForm_singleSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		this.genActionList_singleSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第2个表的生成service
		this.genService_singleSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第2个表
		// 生成注释或别名
		this.genAlias_singleSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第2个表的生成vo
		this.genVo_singleSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第2个表的生成实体
		this.genEntity_singleSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第1个表的
		// 生成注释或别名
		this.genAlias_treeSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第1个表的
		// 生成配置
		this.genConfig_treeSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第1个表的
		// 生成树与jsp
		// 生成jsp
		this.genJspSelect_treeSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		this.genJspParent_treeSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		this.genJspList_treeSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第1个表的
		// 生成vo
		this.genVo_treeSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第1个表的
		// 生成action
		this.genActionSelect_treeSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		this.genActionParent_treeSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		this.genActionList_treeSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第1个表的
		// 生成service
		this.genService_treeSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
		// 第1个表的
		// 生成实体
		this.genEntity_treeSimple(final_folderPath, first_urlResourcesName,
				first_pagesResourceName, first_packageName, first_funName,
				first_tableName, second_urlWebName, second_pagesResourceName,
				second_packageName, second_funName, second_tableName,
				third_urlWebName, third_pagesResourceName, third_packageName,
				third_funName, third_tableName);
	}
}
