package com.ibs.common.utils;

import com.common.handicap.Handicap;
import com.common.util.BaseHandicapUtil;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 * 盘口工具类
 *
 * @Author: null
 * @Date: 2020-05-09 17:09
 * @Version: v1.0
 */
public class HandicapUtil extends BaseHandicapUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	//region 初始化

	private static volatile HandicapUtil instance = null;
	private HandicapUtil() {
	}

	private Map<Code, Map<IbsTypeEnum, String>> idMap;
	private Map<String, Code> codeMap;
	private IbspHandicapService handicapService;

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
			handicapService = new IbspHandicapService();
			Map<String, String> map = handicapService.mapIdCode();

			//放入ID
			codeMap = new HashedMap(map.size());
			map.forEach((key, val) -> codeMap.put(key, Code.valueOf(val)));

			idMap = new HashedMap(Code.values().length);
			codeMap.forEach((key, val) -> {
				IbsTypeEnum category = IbsTypeEnum.MEMBER;
				if (idMap.containsKey(val)) {
					idMap.get(val).put(category, key);
				} else {
					Map<IbsTypeEnum, String> categoryMap = new HashMap<>(2);
					categoryMap.put(category, key);
					idMap.put(val, categoryMap);
				}
			});
		} catch (SQLException e) {
			log.error("初始化盘口工具类错误", e);
		}

	}
	//endregion

	//region 基础方法
	/**
	 * 查找 盘口id
	 *
	 * @param code   盘口code
	 * @param method 盘口类别
	 * @return 盘口id
	 */
	private String getId(Code code, IbsTypeEnum method) {
		if (!idMap.containsKey(code)) {
			try {
				String handicapId = handicapService.findId(code.name());
				Map<IbsTypeEnum, String> map = new HashedMap(2);
				map.put(method, handicapId);
				idMap.put(code, map);
				codeMap.put(handicapId, code);
			} catch (SQLException e) {
				log.error("查找盘口id错误", e);
			}
		}
		Map<IbsTypeEnum, String> map = idMap.get(code);
		if (!map.containsKey(method)) {
			try {
				String handicapId = handicapService.findId(code.name());
				map.put(method, handicapId);
				codeMap.put(handicapId, code);
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
			try {
				Code code = Code.valueOf(handicapService.findCode(handicapId));
				if (idMap.containsKey(code)) {
					idMap.get(code).put(IbsTypeEnum.MEMBER, handicapId);
				} else {
					Map<IbsTypeEnum, String> categoryMap = new HashMap<>(1);
					categoryMap.put(IbsTypeEnum.MEMBER, handicapId);
					idMap.put(code, categoryMap);
				}
				codeMap.put(handicapId, code);
			} catch (SQLException e) {
				log.error("查找盘口编码错误", e);
			}
		}
		return codeMap.get(handicapId);
	}
	//endregion

	//region 快速获取方法
	/**
	 * 获取盘口类
	 *
	 * @param handicapCode 盘口编码
	 * @return 盘口类
	 */
	public static Handicap handicap(Code handicapCode) {
		return handicapCode.getMemberFactory().handicap();
	}

	/**
	 * 查找盘口id
	 *
	 * @param handicapCode 盘口code
	 * @param method       盘口类别
	 * @return 盘口id
	 */
	public static String id(String handicapCode, IbsTypeEnum method) {
		return id(Code.valueOf(handicapCode), method);
	}
	/**
	 * 查找盘口id
	 *
	 * @param code   盘口code
	 * @param method 盘口类别
	 * @return 盘口id
	 */
	public static String id(Code code, IbsTypeEnum method) {
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
	//endregion

}
