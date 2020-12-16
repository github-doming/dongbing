package c.x.platform.root.compo.gencode.util.common;
import java.io.IOException;
import java.sql.SQLException;
import c.x.platform.root.compo.gencode.util.tree_simple.common.Gen_Tree_Simple;
public class BaseGen extends Gen_Tree_Simple {
	/**
	 *
	 * 单表_简单功能_快速生成
	 * 
	 * @Title: gen_singleSimple
	 * @Description:
	 *
	 * 				参数说明
	 * @param final_folderPath
	 *            最终路径
	 * @param urlWebName
	 *            url配置
	 * @param pagesResourceName
	 *            页面和mvc配置
	 * @param packageName
	 *            包
	 * @param funName
	 *            功能
	 * @param tableName
	 *            表
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 *             返回类型 void
	 */
	public void gen_singleSimple(String final_folderPath, String urlWebName, String pagesResourceName, String packageName,
			String funName, String tableName) throws ClassNotFoundException, SQLException, IOException {
		tableName = tableName.toLowerCase();
		// 生成配置
		this.genConfig_singleSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成jsp
		this.genJspForm_singleSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		this.genJspList_singleSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成action
		this.genActionSave_singleSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		this.genActionDel_singleSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		this.genActionDelAll_singleSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName,
				tableName);
		this.genActionForm_singleSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		this.genActionList_singleSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成service
		this.genService_singleSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成注释或别名
		this.genAlias_singleSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成vo
		this.genVo_singleSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成实体
		this.genEntity_singleSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
	}
	/**
	 * 树_简单功能_快速生成
	 * 
	 * @Title: gen_treeSimple
	 * @Description:
	 *
	 * 				参数说明
	 * @param final_folderPath
	 *            最终路径
	 * @param urlWebName
	 *            url配置
	 * @param pagesResourceName
	 *            页面和mvc配置
	 * @param packageName
	 *            包
	 * @param funName
	 *            功能
	 * @param tableName
	 *            表
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 *             返回类型 void
	 */
	public void gen_treeSimple(String final_folderPath, String urlWebName, String pagesResourceName, String packageName,
			String funName, String tableName) throws ClassNotFoundException, SQLException, IOException {
		tableName = tableName.toLowerCase();
		// 生成配置
		this.genConfig_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成树与jsp
		this.genJspSelect_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		this.genJspParent_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成jsp
		this.genJspForm_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		this.genJspList_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成tree与action
		this.genActionParent_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		this.genActionSelect_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成action
		this.genActionSave_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		this.genActionDel_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		this.genActionDelAll_treesimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		this.genActionForm_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		this.genActionList_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成service
		this.genService_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成注释或别名
		this.genAlias_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成vo
		this.genVo_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成实体
		this.genEntity_treeSimple(final_folderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
	}
}
