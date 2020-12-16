package com.ibm.common.utils;

import com.common.util.BaseHandicapUtil;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 盘口工具类
 * @Author: zjj
 * @Date: 2019-08-28 17:23
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HandicapUtil extends BaseHandicapUtil {
	protected Logger log = LogManager.getLogger(this.getClass());

	private Map<Code, Map<IbmTypeEnum, String>> idMap;
	private Map<String, Code> codeMap;
	private Map<String, IbmTypeEnum> categoryMap;

	private IbmHandicapService handicapService;

	private static volatile HandicapUtil instance = null;

	public static HandicapUtil findInstance() {
		if (instance == null) {
			synchronized (HandicapUtil.class) {
				if (instance == null) {
					HandicapUtil handicapUtil = new HandicapUtil();
					handicapUtil.init();
					instance = handicapUtil;
				}
			}
		}
		return instance;
	}
	/**
	 * 初始化
	 */
	private void init() {
		try {
			handicapService = new IbmHandicapService();
			Map<String, String> map = handicapService.mapIdCode();
			if (ContainerTool.isEmpty(map)){
				return;
			}
			//放入ID
			codeMap = new HashedMap(map.size());
			map.forEach((key, val) -> codeMap.put(key, Code.valueOf(val)));

			Map<String, String> kvMap = handicapService.mapIdCategory();
			categoryMap = new HashedMap(kvMap.size());

			idMap = new HashedMap(Code.values().length);
			codeMap.forEach((key, val) -> {
				IbmTypeEnum category = IbmTypeEnum.valueCustomerTypeOf(kvMap.get(key));
				if (idMap.containsKey(val)) {
					idMap.get(val).put(category, key);
				} else {
					Map<IbmTypeEnum, String> categoryMap = new HashMap<>(2);
					categoryMap.put(IbmTypeEnum.valueCustomerTypeOf(kvMap.get(key)), key);
					idMap.put(val, categoryMap);
				}
				categoryMap.put(key, category);
			});
		} catch (SQLException e) {
			log.error("初始化盘口工具类错误", e);
		}
	}

	/**
	 * 查找盘口id
	 *
	 * @param code   盘口code
	 * @param method 盘口类别
	 * @return 盘口id
	 */
	public static String id(Code code, IbmTypeEnum method) {
		return findInstance().getId(code, method);
	}

	/**
	 * 查找盘口code
	 *
	 * @param handicapId 盘口id
	 * @return 盘口code
	 */
	public static Code code(String handicapId) {
		return findInstance().getCode(handicapId);
	}
	public static IbmTypeEnum category(String handicapId) {
		return findInstance().getCategory(handicapId);
	}

	/**
	 * 获取盘口Code枚举类
	 *
	 * @return 盘口Code
	 */
	public static Code[] codes() {
		return Code.values();
	}

	/**
	 * 查找盘口id
	 *
	 * @param handicapCode     盘口code
	 * @param handicapCategory 盘口类别
	 * @return 盘口id
	 */
	public static String id(String handicapCode, IbmTypeEnum handicapCategory) {
		return id(Code.valueOf(handicapCode), handicapCategory);

	}
	/**
	 * 查找盘口id
	 *
	 * @param handicapCode     盘口code
	 * @param handicapCategory 盘口类别
	 * @return 盘口id
	 */
	public static String id(String handicapCode, String handicapCategory) {
		return id(Code.valueOf(handicapCode), IbmTypeEnum.valueOf(handicapCategory));
	}

	/**
	 * 查找 盘口id
	 *
	 * @param code   盘口code
	 * @param method 盘口类别
	 * @return 盘口id
	 */
	private String getId(Code code, IbmTypeEnum method) {
		if (!idMap.containsKey(code)) {
			try {
				String handicapId = handicapService.findId(code.name(), method.name());
				Map<IbmTypeEnum, String> map = new HashedMap(2);
				map.put(method, handicapId);
				idMap.put(code, map);
				codeMap.put(handicapId, code);
				categoryMap.put(handicapId, method);
			} catch (SQLException e) {
				log.error("查找盘口id错误", e);
			}
		}
		Map<IbmTypeEnum, String> map = idMap.get(code);
		if (!map.containsKey(method)) {
			try {
				String handicapId = handicapService.findId(code.name(), method.name());
				map.put(method, handicapId);
				codeMap.put(handicapId, code);
				categoryMap.put(handicapId, method);
			} catch (SQLException e) {
				log.error("查找盘口id错误", e);
			}

		}
		return map.get(method);
	}

	/**
	 * 查找盘口code
	 *
	 * @param handicapId 盘口id
	 * @return 盘口code
	 */
	private Code getCode(String handicapId) {
		if (!codeMap.containsKey(handicapId)) {
			putData(handicapId);
		}
		return codeMap.get(handicapId);
	}

	/**
	 * 查找盘口类别
	 *
	 * @param handicapId 盘口id
	 * @return 盘口类别
	 */
	private IbmTypeEnum getCategory(String handicapId) {
		if (!categoryMap.containsKey(handicapId)) {
			putData(handicapId);
		}
		return categoryMap.get(handicapId);
	}

	/**
	 * 放入数据
	 *
	 * @param handicapId 盘口id
	 */
	private void putData(String handicapId) {
		try {
			Map<String, Object> map = handicapService.findCodeAndCategory(handicapId);
			Code code = Code.valueOf(map.getOrDefault("HANDICAP_CODE_", "").toString());
			IbmTypeEnum category = IbmTypeEnum
					.valueCustomerTypeOf(map.getOrDefault("HANDICAP_CATEGORY_", "").toString());
			if (idMap.containsKey(code)) {
				idMap.get(code).put(category, handicapId);
			} else {
				Map<IbmTypeEnum, String> categoryMap = new HashMap<>(2);
				categoryMap.put(category, handicapId);
				idMap.put(code, categoryMap);
			}
			categoryMap.put(handicapId, category);
			codeMap.put(handicapId, code);
		} catch (SQLException e) {
			log.error("放入数据错误", e);
		}
	}
}
