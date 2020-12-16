package com.ibm.old.v1.client.ibm_sys_database.t.service;
import c.x.platform.root.common.service.BaseService;
import org.doming.core.tools.DateTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @Description:
 * @Author: zjj
 * @Date: 2019-05-10 18:07
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmClientSystemService extends BaseService {

	/**
	 * 清理冗余数据
	 * @param tableName		清除表名
	 * @param nowTime			清理时间
	 * @param ruleTime		时间规则
	 */
	public void clearRedundancy(String tableName, Date nowTime, String ruleTime) throws Exception {
		String sql="DELETE FROM ".concat(tableName).concat(" WHERE CREATE_TIME_LONG_<?");
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(DateTool.getAfterRule(nowTime, ruleTime).getTime());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 表碎片整理
	 * @param tableName 表名
	 */
	public void defragmentation(String tableName) throws SQLException {
		String sql = "OPTIMIZE TABLE "+tableName;
		super.dao.execute(sql,null);
	}
}
