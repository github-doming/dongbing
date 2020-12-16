package com.ibs.common.core;
import c.x.platform.root.compo.gencode.util.tree_simple.common.Gen_Tree_Simple;

import java.io.IOException;
import java.sql.SQLException;
/**
 * 代码生成类
 * @Author: null
 * @Date: 2020-05-19 17:57
 * @Version: v1.0
 */
public class BaseGen extends Gen_Tree_Simple {

	/**
	 * @param finalFolderPath  最终路径
	 * @param urlWebName        url配置
	 * @param pagesResourceName 页面和mvc配置
	 * @param packageName       包
	 * @param funName           功能
	 * @param tableName         表
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genSingleSimple(String finalFolderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
			throws ClassNotFoundException, SQLException, IOException {
		tableName = tableName.toLowerCase();
		// 生成service
		this.genServiceSingleSimple(finalFolderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
		// 生成实体
		this.genEntitySingleSimple(finalFolderPath, urlWebName, pagesResourceName, packageName, funName, tableName);
	}


	private void genServiceSingleSimple(String finalFolderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
			throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("service", "Service.java.vm", finalFolderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
	private void genEntitySingleSimple(String finalFolderPath, String urlWebName, String pagesResourceName,
			String packageName, String funName, String tableName)
			throws ClassNotFoundException, SQLException, IOException {
		this.gen_singleSimple("entity", "entity.java.vm", finalFolderPath, urlWebName, pagesResourceName, packageName,
				funName, tableName);
	}
}
