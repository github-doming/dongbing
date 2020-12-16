package com.ibm.common.test.wwj.handicap.logger;
import com.ibm.common.core.BaseServicePlus;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-12-07 14:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SaveLogService extends BaseServicePlus {

	/**
	 * 创建盘口表
	 *
	 * @param tableName 盘口表名称
	 */
	private void createHandicapTable(String tableName) throws SQLException {
		String sqlBuilder = "create table `" + tableName + "` ("
				+ "IBMC_LOG_HTTP_HANDICAP_ID_	char(32)	not null comment 'IBMC客户端_http盘口请求日志主键',"
				+ "HANDICAP_CODE_				char(8)	comment '盘口编码',"
				+ "URL_		                	varchar(512) comment '请求地址',"
				+ "PARAMETER_	           		varchar(2048) comment '请求参数',"
				+ "HTML_	                		text comment '结果页面',"
				+ "STATE_CODE_					smallint comment '状态码',"
				+ "LOCATION_		            	varchar(128) comment '重定向地址',"
				+ "CREATE_TIME_					datetime comment '创建时间',"
				+ "CREATE_TIME_LONG_				bigint(18) comment '创建时间',"
				+ "primary key (IBMC_LOG_HTTP_HANDICAP_ID_)) engine=MyISAM;";
		super.dao.execute(sqlBuilder, null);
	}

	/**
	 * 创建操作表
	 *
	 * @param tableName 操作表名称
	 */
	private void createOptionTable(String tableName) throws SQLException {
		String sqlBuilder = "create table `" + tableName + "` ("
				+ "IBMC_LOG_HTTP_DAY_HANDICAP_CUSTOMER_ID_	char(32)	not null comment 'IBMC客户端_http客户请求日志主键',"
				+ "EXIST_ID_						char(32)	comment '客户存在主键',"
				+ "HANDICAP_CODE_				char(8)	comment '盘口编码',"
				+ "`TYPE_`						char(16)	comment '请求类型',"
				+ "URL_		                	varchar(512) comment '请求地址',"
				+ "PARAMETER_	           		varchar(2048) comment '请求参数',"
				+ "HTML_	                		text comment '结果页面',"
				+ "STATE_CODE_					smallint comment '状态码',"
				+ "LOCATION_		            	varchar(128) comment '重定向地址',"
				+ "CREATE_TIME_					datetime comment '创建时间',"
				+ "CREATE_TIME_LONG_				bigint(18) comment '创建时间',"
				+ "primary key (IBMC_LOG_HTTP_DAY_HANDICAP_CUSTOMER_ID_)) engine=MyISAM;";
		super.execute(sqlBuilder, null);
	}

	/**
	 * 保存盘口日志
	 *
	 * @param handicapLogger 盘口日志
	 */
	public void save(HandicapLogger handicapLogger) throws SQLException {
		Date nowTime = new Date();
		String tableName = "IBMC_LOG_HTTP_" + DateTool.getDay(nowTime) + "_" + handicapLogger.getHandicap().name();
		if (!hasTable(tableName)) {
			createHandicapTable(tableName);
		}
		String sql = "INSERT INTO " + tableName + " ("
				+ "`IBMC_LOG_HTTP_HANDICAP_ID_`,`HANDICAP_CODE_`,`URL_`,`PARAMETER_`,`HTML_`,`STATE_CODE_`,`LOCATION_`,`CREATE_TIME_`,`CREATE_TIME_LONG_`) "
				+ " VALUES(?,?,?,?,?,?,?,?,?)";
		List<Object> parameterList = new ArrayList<>(9);
		parameterList.add(RandomTool.getNumLetter32());
		parameterList.add(handicapLogger.getHandicap().getName());
		parameterList.add(handicapLogger.getUrl());
		parameterList.add(handicapLogger.getParameter());
		parameterList.add(handicapLogger.getHtml());
		parameterList.add(handicapLogger.getState());
		parameterList.add(handicapLogger.getLocation());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		super.execute(sql, parameterList);

	}

	/**
	 * 保存操作日志
	 *
	 * @param optionLogger 操作日志
	 */
	public void save(OptionLogger optionLogger) throws SQLException {
		Date nowTime = new Date();
		String tableName = "IBMC_LOG_HTTP_" + DateTool.getDay(nowTime) + "_z_"
				+ optionLogger.getExistId().substring(0,1);
		if (!hasTable(tableName)) {
			createOptionTable(tableName);
		}
		String sql = "INSERT INTO " + tableName + " ("
				+ "`IBMC_LOG_HTTP_DAY_HANDICAP_CUSTOMER_ID_`,`EXIST_ID_`,`HANDICAP_CODE_`,`TYPE_`,`URL_`,`PARAMETER_`,`HTML_`,`STATE_CODE_`,`LOCATION_`,`CREATE_TIME_`,`CREATE_TIME_LONG_`) "
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		List<Object> parameterList = new ArrayList<>(11);
		parameterList.add(RandomTool.getNumLetter32());
		parameterList.add(optionLogger.getExistId());
		parameterList.add(optionLogger.getHandicap().name());
		parameterList.add(optionLogger.getType().getMsg());
		parameterList.add(optionLogger.getUrl());
		parameterList.add(optionLogger.getParameter());
		parameterList.add(optionLogger.getHtml());
		parameterList.add(optionLogger.getState());
		parameterList.add(optionLogger.getLocation());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		super.execute(sql, parameterList);
	}
}
