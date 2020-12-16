package com.ibm.follow.servlet.cloud.core.controller.strategy;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_client_capacity.service.IbmClientCapacityService;
import org.doming.core.common.CurrentTransaction;

import java.sql.SQLException;
import java.util.Map;
/**
 * @Description: 填充查找策略
 * @Author: Dongming
 * @Date: 2019-09-11 15:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class FindClientByPadding  implements FindClientStrategy {

	@Override public Map<String, Object> findUsableClient(String handicapCode, IbmTypeEnum type) throws SQLException {
		CurrentTransaction.beginTransaction();
		IbmClientCapacityService clientCapacityService = new IbmClientCapacityService();
		Map<String, Object> clientInfo = clientCapacityService.findUsable(handicapCode, type);
		CurrentTransaction.endTransaction();
		return clientInfo;
	}

	@Override public Map<String, Object> findVerifyClient(String handicapCode, IbmTypeEnum type) throws SQLException {
		CurrentTransaction.beginTransaction();
		IbmClientCapacityService clientCapacityService = new IbmClientCapacityService();
		Map<String, Object> clientInfo = clientCapacityService.findVerifyUsable(handicapCode, type);
		CurrentTransaction.endTransaction();
		return clientInfo;
	}
}
