package com.ibm.follow.servlet.cloud.core.controller.strategy;
import com.ibm.common.enums.IbmTypeEnum;

import java.sql.SQLException;
import java.util.Map;
/**
 * @Description: 客户端策略
 * @Author: Dongming
 * @Date: 2019-09-11 11:36
 * @Email: job.dongming@foxmail.com
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
	 * @param type         客户类型
	 * @return 客户端信息
	 */
	public Map<String, Object> findUsableClient(String handicapCode, IbmTypeEnum type) throws SQLException {
		if (strategy == null){
			throw new IllegalArgumentException("尚未指定获取策略");
		}
		return strategy.findUsableClient(handicapCode,type);
	}
	/**
	 * 查找验证客户端信息
	 *
	 * @param handicapCode 盘口code
	 * @param type         客户类型
	 * @return 客户端信息
	 */
	public Map<String, Object> findVerifyClient(String handicapCode, IbmTypeEnum type) throws SQLException {
		if (strategy == null){
			throw new IllegalArgumentException("尚未指定获取策略");
		}
		return strategy.findVerifyClient(handicapCode,type);
	}


}
