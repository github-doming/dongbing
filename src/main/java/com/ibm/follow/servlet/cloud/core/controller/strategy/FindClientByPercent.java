package com.ibm.follow.servlet.cloud.core.controller.strategy;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_client_capacity.service.IbmClientCapacityService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 百分比查找策略
 * @Author: Dongming
 * @Date: 2019-09-11 11:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class FindClientByPercent implements FindClientStrategy {

	@Override public Map<String, Object> findUsableClient(String handicapCode, IbmTypeEnum type) throws SQLException {
		CurrentTransaction.beginTransaction();
		IbmClientCapacityService clientCapacityService = new IbmClientCapacityService();
		List<Map<String, Object>> capacityInfos = clientCapacityService.listCapacityInfo(handicapCode, type);
		CurrentTransaction.endTransaction();
		return getClientInfo(capacityInfos);
	}

	@Override public Map<String, Object> findVerifyClient(String handicapCode, IbmTypeEnum type) throws SQLException {
		CurrentTransaction.beginTransaction();
		IbmClientCapacityService clientCapacityService = new IbmClientCapacityService();
		List<Map<String, Object>> capacityInfos = clientCapacityService.listVerifyCapacityInfo(handicapCode, type);
		CurrentTransaction.endTransaction();
		return getClientInfo(capacityInfos);
	}

	/**
	 * 根据容量信息获取可开启的客户端信息
	 *
	 * @param capacityInfos 容量信息
	 * @return 容量信息
	 */
	private Map<String, Object> getClientInfo(List<Map<String, Object>> capacityInfos) {
		Map<String, Object> clientInfo = new HashMap<>(2);
		double[] percent = {1, 1, 1};
		double tmp;
		for (Map<String, Object> capacityInfo : capacityInfos) {
			tmp = NumberTool.divInt(capacityInfo.get("CAPACITY_"), capacityInfo.get("CAPACITY_MAX_"));
			if (tmp < percent[0]) {
				percent[0] = tmp;
				clientInfo.put("CLIENT_ID_", capacityInfo.get("CLIENT_ID_"));
				clientInfo.put("CLIENT_CODE_", capacityInfo.get("CLIENT_CODE_"));

			} else if (tmp == percent[0]) {
				tmp = NumberTool
						.divInt(capacityInfo.get("CAPACITY_HANDICAP_"), capacityInfo.get("CAPACITY_HANDICAP_MAX_"));
				if (tmp < percent[1]) {
					percent[1] = tmp;
					clientInfo.put("CLIENT_ID_", capacityInfo.get("CLIENT_ID_"));
					clientInfo.put("CLIENT_CODE_", capacityInfo.get("CLIENT_CODE_"));
				} else if (tmp == percent[1]) {
					tmp = NumberTool
							.divInt(capacityInfo.get("CLIENT_CAPACITY_"), capacityInfo.get("CLIENT_CAPACITY_MAX_"));
					if (tmp < percent[2]) {
						percent[2] = tmp;
						clientInfo.put("CLIENT_ID_", capacityInfo.get("CLIENT_ID_"));
						clientInfo.put("CLIENT_CODE_", capacityInfo.get("CLIENT_CODE_"));
					}
				}
			}

		}
		return clientInfo;
	}
}
