package com.ibm.old.v1.cloud.core.tool;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;

import java.sql.SQLException;
import java.util.HashMap;
/**
 * @Description: 盘口工具类
 * @Author: zjj
 * @Date: 2019-05-15 09:59
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HandicapTool {

	private static final HashMap<String, String> HANDICAP_CODE = new HashMap<>(5);

	private static final HashMap<String, String> HANDICAP_ID = new HashMap<>(5);

	/**
	 * 根据盘口id找盘口code
	 *
	 * @param handicapId 盘口id
	 * @return 盘口code
	 */
	public static IbmHandicapEnum findCode(String handicapId) throws Exception {
		if (!HANDICAP_CODE.containsKey(handicapId)) {
			synchronized (HandicapTool.class) {
				if (!HANDICAP_CODE.containsKey(handicapId)) {
					String handicapCode =  new IbmHandicapTService().findCode(handicapId);
					HANDICAP_CODE.put(handicapId, handicapCode);
					HANDICAP_ID.put(handicapCode, handicapId);
				}
			}
		}
		String handicapCode = HANDICAP_CODE.get(handicapId);
		return IbmHandicapEnum.valueOf(handicapCode);
	}

	/**
	 * 根据盘口code找盘口id
	 *
	 * @param handicapCode 盘口code
	 * @return 盘口id
	 */
	public static String findId(String handicapCode) throws SQLException {
		if (!HANDICAP_ID.containsKey(handicapCode)) {
			synchronized (HandicapTool.class) {
				if (!HANDICAP_ID.containsKey(handicapCode)) {
					String handicapId = new IbmHandicapTService().findIdByCode(handicapCode);
					HANDICAP_ID.put(handicapCode, handicapId);
					HANDICAP_CODE.put(handicapId, handicapCode);
				}
			}
		}
		return HANDICAP_ID.get(handicapCode);
	}

}
