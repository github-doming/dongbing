package com.ibs.plan.module.server.job.service;

import c.x.platform.root.common.service.BaseService;
import com.ibs.common.enums.IbsStateEnum;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Description: 数据迁移服务类
 * @Author: null
 * @Date: 2020-04-14 17:50
 * @Version: v1.0
 */
public class MigrateService extends BaseService {

	/**
	 * 创建表，搬迁数据
	 *
	 * @param cutDataTime 切割时间
	 * @param tableTime   表时间
	 * @param tableNames  表名
	 * @param baseName    库名
	 */
	public void createTable(Date cutDataTime, String tableTime, List<String> tableNames, String baseName) throws SQLException {
		List<Object> parameters = new ArrayList<>();
		parameters.add(cutDataTime.getTime());
		String sql = "CREATE TABLE %s.%s_" + tableTime + " select * from %s where CREATE_TIME_LONG_<?";
		for (String tableName : tableNames) {
			super.dao.execute(String.format(sql, baseName, tableName, tableName), parameters);
		}

	}
	/**
	 *	检测表数据条数
	 * @param tableNames  表名
	 */
	public void checkTableData(List<String> tableNames) throws SQLException {
		String sql = "select COUNT(*) count from %s";
		int count;
		Iterator<String> iterator =  tableNames.iterator();
		while (iterator.hasNext()){
			String tableName = iterator.next();
			count = NumberTool.getInteger(super.dao.findString("count",String.format(sql, tableName),null));
			if(count<100000){
				iterator.remove();
			}
		}
	}

	/**
	 * 创建表，搬迁数据
	 *
	 * @param tableTime   表时间
	 * @param tableNames  表名
	 */
	public void createTable(String tableTime, List<String> tableNames) throws SQLException {
		String sql = "CREATE TABLE %s_" + tableTime + " select * from %s ";
		for (String tableName : tableNames) {
			super.dao.execute(String.format(sql, tableName, tableName), null);
		}

	}

	/**
	 * 删除表旧数据
	 *
	 * @param cutDataTime 切割时间
	 * @param tableNames  表名
	 */
	public void delMigrateData(Date cutDataTime, List<String> tableNames) throws SQLException {
		List<Object> parameters = new ArrayList<>();
		parameters.add(cutDataTime.getTime());
		String sql = "DELETE FROM %s where CREATE_TIME_LONG_<?";
		for (String tableName : tableNames) {
			super.dao.execute(String.format(sql, tableName), parameters);
		}
	}

	/**
	 * 清空表旧数据
	 *
	 * @param tableNames  表名
	 */
	public void delMigrateData(List<String> tableNames) throws SQLException {
		String sql = "DELETE FROM %s ";
		for (String tableName : tableNames) {
			super.dao.execute(String.format(sql, tableName), null);
		}
	}

	/**
	 * 优化表
	 *
	 * @param tableNames 表名
	 */
	public void optimizeTable(List<String> tableNames) throws SQLException {
		String sql = "OPTIMIZE TABLES %s";
		for (String tableName : tableNames) {
			super.dao.execute(String.format(sql, tableName), null);
		}
	}

	/**
	 * 删除状态为del的数据
	 *
	 * @param cutDataTime 切割时间
	 * @param tableNames  表名
	 */
	public void delData(Date cutDataTime, List<String> tableNames) throws SQLException {
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsStateEnum.DEL.name());
		parameters.add(cutDataTime.getTime());
		String sql = "DELETE FROM %s where STATE_=? and CREATE_TIME_LONG_<?";
		for (String tableName : tableNames) {
			super.dao.execute(String.format(sql, tableName), parameters);
		}
	}

	/**
	 * 删除过期备份数据
	 *
	 * @param tableTime  表时间
	 * @param tableNames 表名
	 */
	public void dropTable(String tableTime, List<String> tableNames) throws SQLException {
		String sql = "DROP TABLE if exists %s_" + tableTime;
		for (String tableName : tableNames) {
			super.dao.execute(String.format(sql, tableName), null);
		}

	}

	/**
	 * 删除所有备份数据
	 *
	 * @param tableNames	表名
	 */
	public void clearByDays(List<String> tableNames,int days) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT table_name FROM information_schema. TABLES "
				+ " WHERE table_name LIKE 'ibmc_ha_follow_bet_%';");
		List<String> list = new ArrayList<>();
		List<String> sentences = super.dao.findStringList("table_name", sql.toString(), null);
		for (int i = 0; i < sentences.size(); i++) {
			String tableTime = DateTool.getMDDate(DateTool.getBeforeDays(new Date(), days + i));
			for(String tableName:tableNames){
				list.add(tableName.concat("_").concat(tableTime));
			}
		}
		sql.delete(0,sql.length());
		sql.append("drop table if exists ");
		for(String str:list){
			sql.append(str).append(",");
		}
		sql.delete(sql.length()-1,sql.length());
		super.dao.execute(sql.toString(),null);
	}
}
