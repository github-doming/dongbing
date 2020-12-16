package com.ibs.plan.module.cloud.core.controller.strategy;

import java.sql.SQLException;
import java.util.Map;

/**
 * @Description: 寻找客户端抽象类
 * @Author: zjj
 * @Date: 2020-05-23 11:46
 * @Version: v1.0
 */
public interface FindClientStrategy {


	/**
	 * 查找可用的客户端信息
	 *
	 * @param handicapCode 盘口code
	 * @param planType	 方案类型
	 * @return 客户端信息
	 * @throws SQLException 查找错误
	 */
	Map<String, Object> findUsableClient(String handicapCode,String planType) throws SQLException;
	/**
	 * 查找验证客户端信息
	 *
	 * @param handicapCode 盘口code
	 * @return 客户端信息
	 * @throws SQLException 查找错误
	 */
	Map<String, Object> findVerifyClient(String handicapCode) throws SQLException;
}
