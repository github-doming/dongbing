package com.ibs.plan.connector.core;
import com.ibs.plan.module.cloud.ibsp_profit.service.IbspProfitService;
import com.ibs.plan.module.cloud.ibsp_profit_vr.service.IbspProfitVrService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 * 盘口会员盈利
 *
 * @Author: null
 * @Date: 2020-05-23 16:15
 * @Version: v1.0
 */
public class IbspMemberProfitDefine {
	/**
	 * 获取盈利信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 盈利信息
	 */
	public Map<String, Object> getProfitInfo(String handicapMemberId) throws SQLException {
		Map<String, Object> info = new HashMap<>(2);
		Map<String, Object> profitInfo = new IbspProfitService().findInfo(handicapMemberId);
		if (ContainerTool.isEmpty(profitInfo)) {
			return info;
		}
		profitInfo.put("PROFIT_", NumberTool.doubleT(profitInfo.remove("PROFIT_T_")));
		profitInfo.put("BET_FUNDS_", NumberTool.doubleT(profitInfo.remove("BET_FUNDS_T_")));
		info.put("profitInfo", profitInfo);

		Map<String, Object> profitInfoVr = new IbspProfitVrService().findInfo(handicapMemberId);
		profitInfoVr.put("PROFIT_", NumberTool.doubleT(profitInfoVr.remove("PROFIT_T_")));
		profitInfoVr.put("BET_FUNDS_", NumberTool.doubleT(profitInfoVr.remove("BET_FUNDS_T_")));
		info.put("profitInfoVr", profitInfoVr);
		return info;
	}

}
