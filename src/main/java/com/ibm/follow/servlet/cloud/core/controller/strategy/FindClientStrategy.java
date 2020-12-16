package com.ibm.follow.servlet.cloud.core.controller.strategy;
import com.ibm.common.enums.IbmTypeEnum;

import java.sql.SQLException;
import java.util.Map;
/**
 * @Description: 寻找客户端抽象类
 * @Author: Dongming
 * @Date: 2019-09-11 11:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface FindClientStrategy {
	/**
	 * 查找可用的客户端信息
	 *
	 * @param handicapCode 盘口code
	 * @param type         客户类型
	 * @return 客户端信息
	 * @throws SQLException 查找错误
	 */
	Map<String, Object> findUsableClient(String handicapCode, IbmTypeEnum type) throws SQLException;

	/**
	 * 查找验证客户端信息
	 *
	 * @param handicapCode 盘口code
	 * @param type         客户类型
	 * @return 客户端信息
	 * @throws SQLException 查找错误
	 */
	Map<String, Object> findVerifyClient(String handicapCode, IbmTypeEnum type) throws SQLException;
}
