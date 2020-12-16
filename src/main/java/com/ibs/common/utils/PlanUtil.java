package com.ibs.common.utils;

import com.common.util.BasePlanUtil;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 方案工具类
 * @Author: null
 * @Date: 2020-05-19 11:37
 * @Version: v1.0
 */
public final class PlanUtil extends BasePlanUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	private static volatile PlanUtil instance = null;

	private PlanUtil() {
	}

	private IbspPlanService planService;
	private Map<Code, String> codeMap;
	private Map<Code, Integer> snMap;
	private Map<String, Code> idMap;

	public static PlanUtil findInstance() {
		if (instance == null) {
			synchronized (PlanUtil.class) {
				if (instance == null) {
					PlanUtil planUtil = new PlanUtil();
					planUtil.init();
					instance = planUtil;
				}
			}
		}
		return instance;
	}

	private void init() {
		try {
			planService = new IbspPlanService();
			Map<String, String> map = planService.mapIdCode();

			//放入ID
			idMap = new HashedMap(map.size());
			map.forEach((key, val) -> idMap.put(key, Code.valueOf(val)));

			//放入CODE
			codeMap = new HashMap<>(idMap.size());
			idMap.forEach((key, val) -> codeMap.put(val, key));

			//放入SN
			Map<String, Integer> codeSnMap = planService.mapCodeSn();
			snMap = new HashMap<>(codeSnMap.size());
			codeSnMap.forEach((key, val) -> snMap.put(Code.valueOf(key), val));
		} catch (SQLException e) {
			log.error("初始化方案工具类错误", e);
		}
	}

	/**
	 * 查找方案id
	 *
	 * @param planCode 方案code
	 * @return 方案id
	 */
	public static String id(Object planCode) {
		return id(Code.valueOf(planCode.toString()));
	}

	/**
	 * 查找方案id
	 *
	 * @param planCode 方案code
	 * @return 方案id
	 */
	public static String id(PlanUtil.Code planCode) {
		return findInstance().getId(planCode);
	}

	/**
	 * 查找方案编码
	 *
	 * @param planId 方案id
	 * @return 方案id
	 */
	public static Code code(String planId) {
		return findInstance().getCode(planId);
	}

	/**
	 * 查找方案排名
	 *
	 * @param planCode 方案code
	 * @return 方案排名
	 */
	public static Integer sn(Code planCode) {
		return findInstance().getSn(planCode);
	}


	/**
	 * 查找方案id
	 *
	 * @param planCode 方案code
	 * @return 方案id
	 */
	private String getId(Code planCode) {
		if (!codeMap.containsKey(planCode)) {
			try {
				String planId = planService.findId(planCode.name());
				if (StringTool.notEmpty(planId)) {
					codeMap.put(planCode, planId);
					idMap.put(planId, planCode);
				}
			} catch (SQLException e) {
				log.error("查找方案id错误", e);
			}
		}
		return codeMap.get(planCode);
	}

	private Code getCode(String planId) {
		if (!idMap.containsKey(planId)) {
			try {
				String planCode = planService.findCode(planId);
				if (StringTool.notEmpty(planCode)) {
					codeMap.put(Code.valueOf(planCode), planId);
					idMap.put(planId, Code.valueOf(planCode));
				}
			} catch (SQLException e) {
				log.error("查找方案编码错误", e);
			}
		}
		return idMap.get(planId);
	}

	/**
	 * 查找方案排名
	 *
	 * @param planCode 方案code
	 * @return 方案排名
	 */
	private Integer getSn(Code planCode) {
		if (!snMap.containsKey(planCode)) {
			try {
				Integer sn = planService.findSn(planCode.name());
				snMap.put(planCode, sn);
			} catch (SQLException e) {
				log.error("查找方案id错误", e);
			}
		}
		return snMap.get(planCode);
	}
}
