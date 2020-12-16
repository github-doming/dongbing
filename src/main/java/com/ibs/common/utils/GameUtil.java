package com.ibs.common.utils;

import com.common.util.BaseGameUtil;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 游戏工具类
 *
 * @Author: Dongming
 * @Date: 2020-05-09 16:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public final class GameUtil extends BaseGameUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	//region 初始化

	private static volatile GameUtil instance = null;

	private GameUtil() {
	}

	private IbspGameService gameService;

	private Map<Code, String> codeMap;
	private Map<String, Code> idMap;

	public static GameUtil findInstance() {
		if (instance == null) {
			synchronized (GameUtil.class) {
				if (instance == null) {
					GameUtil gameUtil = new GameUtil();
					gameUtil.init();
					instance = gameUtil;
				}
			}
		}
		return instance;
	}

	private void init() {
		try {
			gameService = new IbspGameService();
			Map<String, String> map = gameService.mapIdCode();
			//放入ID
			idMap = new HashedMap(map.size());
			map.forEach((key, val) -> idMap.put(key, Code.valueOf(val)));
			//放入CODE
			codeMap = new HashMap<>(idMap.size());
			idMap.forEach((key, val) -> codeMap.put(val, key));
		} catch (SQLException e) {
			log.error("初始化游戏工具类错误", e);
		}

	}
	//endregion

	//region 基础方法

	/**
	 * 查找游戏id
	 *
	 * @param gameCode 游戏code
	 * @return 盘口id
	 */
	public static String id(Code gameCode) {
		return findInstance().getId(gameCode);
	}

	/**
	 * 查找游戏id
	 *
	 * @param gameCode 游戏code
	 * @return 盘口id
	 */
	public static String id(String gameCode) {
		return id(Code.valueOf(gameCode));
	}

	/**
	 * 查找游戏code
	 *
	 * @param gameId 游戏 ID
	 * @return 盘口code
	 */
	public static Code code(String gameId) {
		return findInstance().getCode(gameId);
	}

	/**
	 * 游戏类型
	 *
	 * @param gameId 游戏id
	 * @return
	 */
	public static String type(String gameId) {
		Code gameCode = findInstance().getCode(gameId);
		switch (gameCode) {
			case JSSSC:
			case CQSSC:
			case COUNTRY_SSC:
			case SELF_SSC_5:
				return IbsTypeEnum.BALL.name();
			case XYNC:
			case GDKLC:
			case GXKLSF:
				return IbsTypeEnum.HAPPY.name();
			case XYFT:
			case JS10:
			case PK10:
			case COUNTRY_10:
			case SELF_10_2:
			default:
				return IbsTypeEnum.NUMBER.name();
		}
	}

	/**
	 * 查找盘口id
	 *
	 * @param gameCode 游戏code
	 * @return 盘口id
	 */
	private String getId(Code gameCode) {
		if (!codeMap.containsKey(gameCode)) {
			try {
				String gameId = gameService.findId(gameCode.name());
				if (StringTool.notEmpty(gameId)) {
					codeMap.put(gameCode, gameId);
					idMap.put(gameId, gameCode);
				}
			} catch (SQLException e) {
				log.error("查找游戏id错误", e);
			}
		}
		return codeMap.get(gameCode);
	}

	/**
	 * 查找盘口code
	 *
	 * @param gameId 游戏 ID
	 * @return 盘口code
	 */
	private Code getCode(String gameId) {
		if (!idMap.containsKey(gameId)) {
			try {
				String gameCode = gameService.findCode(gameId);
				Code code = Code.valueOf(gameCode);
				idMap.put(gameId, code);
				codeMap.put(code, gameId);
			} catch (SQLException e) {
				log.error("查找游戏code错误", e);
			}
		}
		return idMap.get(gameId);
	}
	//endregion

}
