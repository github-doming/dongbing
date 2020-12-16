package com.ibm.old.v1.pc.ibm_profit.t.service;

import com.ibm.old.v1.cloud.ibm_profit.t.service.IbmProfitTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IbmPcProfitTService extends IbmProfitTService {

	/**
	 * @param handicapMemberId 盘口会员ID
	 * @return 真实投注盈亏信息
	 * @Description: 根据盘口会员ID查询真实投注盈亏信息
	 * <p>
	 * 参数说明
	 */
	public Map<String, Object> findProfit(String handicapMemberId) throws SQLException {
		String sql = "SELECT HANDICAP_MEMBER_ID_,PROFIT_T_,BET_FUNDS_T_,BET_LEN_,HANDICAP_PROFIT_T_ from ibm_profit where STATE_ = ? AND HANDICAP_MEMBER_ID_ = ? " ;
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(handicapMemberId);
		Map<String, Object> map = super.dao.findMap(sql, parameterList);
		if (ContainerTool.notEmpty(map)) {
			map.put("PROFIT_T_", NumberTool.doubleT(map.get("PROFIT_T_")));
			map.put("BET_FUNDS_T_", NumberTool.doubleT(map.get("BET_FUNDS_T_")));
			map.put("HANDICAP_PROFIT_", NumberTool.doubleT(map.get("HANDICAP_PROFIT_T_")));
			map.remove("ROW_NUM");
			map.remove("HANDICAP_PROFIT_T_");
		}
		return map;
	}


}
