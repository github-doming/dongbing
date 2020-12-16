package com.ibm.follow.servlet.cloud.ibm_hm_profit;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit.service.IbmHmProfitService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_vr.service.IbmHmProfitVrService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 盘口会员盈利
 * @Author: Dongming
 * @Date: 2019-09-04 14:55
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmHmProfitDefine {

	/**
	 * 获取盈利信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 盈利信息
	 */
	public static Map<String, Object> getProfitInfo(String handicapMemberId) throws SQLException {
		Map<String, Object> info = new HashMap<>(2);
		Map<String, Object> profitInfo = new IbmHmProfitService().findInfo(handicapMemberId);
		if (ContainerTool.isEmpty(profitInfo)){
			return info;
		}
		profitInfo.put("PROFIT_", NumberTool.doubleT(profitInfo.remove("PROFIT_T_")));
		profitInfo.put("BET_FUNDS_", NumberTool.doubleT(profitInfo.remove("BET_FUNDS_T_")));
		info.put("profitInfo", profitInfo);

		Map<String, Object> profitInfoVr = new IbmHmProfitVrService().findInfo(handicapMemberId);
		profitInfoVr.put("PROFIT_", NumberTool.doubleT(profitInfoVr.remove("PROFIT_T_")));
		profitInfoVr.put("BET_FUNDS_", NumberTool.doubleT(profitInfoVr.remove("BET_FUNDS_T_")));
		info.put("profitInfoVr", profitInfoVr);
		return info;
	}

	/**
	 * 获取会员信息
	 * @param handicapMemberId 盘口会员主键
	 * @return 会员信息
	 */
	public static Map<String, Object> getMemberInfo(String handicapMemberId) throws SQLException {
		Map<String, Object> hmInfo = new IbmHmInfoService().findShowInfo(handicapMemberId);
		if (ContainerTool.isEmpty(hmInfo)) {
			return null;
		}

		hmInfo.put("HANDICAP_PROFIT_", NumberTool.doubleT(hmInfo.remove("HANDICAP_PROFIT_T_")));
		hmInfo.put("AMOUNT_", NumberTool.doubleT(hmInfo.remove("AMOUNT_T_")));
		return hmInfo;
	}
}
