package com.ibs.plan.module.cloud.core.controller.strategy;

import java.sql.SQLException;
import java.util.Map;

/**
 * @Description: 客户端策略
 * @Author: null
 * @Date: 2020-05-23 13:44
 * @Version: v1.0
 */
public class FindClientController {
	private FindClientStrategy strategy;

	public FindClientController strategy(FindClientStrategy strategy) {
		this.strategy = strategy;
		return this;
	}
	/**
	 * 获取可用的客户端信息
	 *
	 * @param handicapCode 盘口code
	 * @param planType	 方案类型
	 * @return 客户端信息
	 */
	public Map<String, Object> findUsableClient(String handicapCode,String planType) throws SQLException {
		if (strategy == null){
			throw new IllegalArgumentException("尚未指定获取策略");
		}
		return strategy.findUsableClient(handicapCode,planType);
	}
	/**
	 * 查找验证客户端信息
	 *
	 * @param handicapCode 盘口code
	 * @return 客户端信息
	 */
	public Map<String, Object> findVerifyClient(String handicapCode) throws SQLException {
		if (strategy == null){
			throw new IllegalArgumentException("尚未指定获取策略");
		}
		return strategy.findVerifyClient(handicapCode);
	}
}
