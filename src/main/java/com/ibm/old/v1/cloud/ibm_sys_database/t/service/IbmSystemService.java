package com.ibm.old.v1.cloud.ibm_sys_database.t.service;
import c.x.platform.root.common.service.BaseService;

import java.sql.SQLException;
/**
 * @Description: 系统服务类
 * @Author: Dongming
 * @Date: 2019-05-10 10:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmSystemService extends BaseService {

	/**
	 * 表碎片整理
	 * @param tableName 表名
	 */
	public void defragmentation(String tableName) throws SQLException {
		String sql = "OPTIMIZE TABLE "+tableName;
		super.dao.execute(sql,null);
	}
}
