package com.ibs.plan.module.cloud.core.controller.strategy;

import com.ibs.plan.module.cloud.ibsp_client_capacity.service.IbspClientCapacityService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 百分比查找策略
 * @Author: null
 * @Date: 2020-05-23 13:45
 * @Version: v1.0
 */
public class FindClientByPercent implements FindClientStrategy {

	@Override
	public Map<String, Object> findUsableClient(String handicapCode,String planType) throws SQLException {
		CurrentTransaction.beginTransaction();
		IbspClientCapacityService clientCapacityService=new IbspClientCapacityService();
		List<Map<String, Object>> capacityInfos = clientCapacityService.listCapacityInfo(handicapCode,planType);
		CurrentTransaction.endTransaction();
		return getClientInfo(capacityInfos);
	}

	@Override
	public Map<String, Object> findVerifyClient(String handicapCode) throws SQLException {
		CurrentTransaction.beginTransaction();
		IbspClientCapacityService clientCapacityService=new IbspClientCapacityService();
		List<Map<String, Object>> capacityInfos = clientCapacityService.listVerifyCapacityInfo(handicapCode);
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
		double[] percent = {1, 1};
		double tmp;
		for (Map<String, Object> capacityInfo : capacityInfos) {
			tmp = NumberTool.divInt(capacityInfo.get("CAPACITY_HANDICAP_"), capacityInfo.get("CAPACITY_HANDICAP_MAX_"));
			if (tmp < percent[0]) {
				percent[0] = tmp;
				clientInfo.put("CLIENT_ID_", capacityInfo.get("CLIENT_ID_"));
				clientInfo.put("CLIENT_CODE_", capacityInfo.get("CLIENT_CODE_"));
			}else if(tmp == percent[0]){
				tmp = NumberTool.divInt(capacityInfo.get("CLIENT_CAPACITY_"), capacityInfo.get("CLIENT_CAPACITY_MAX_"));
				if (tmp < percent[1]) {
					percent[1] = tmp;
					clientInfo.put("CLIENT_ID_", capacityInfo.get("CLIENT_ID_"));
					clientInfo.put("CLIENT_CODE_", capacityInfo.get("CLIENT_CODE_"));
				}
			}
		}
		return clientInfo;
	}
}
