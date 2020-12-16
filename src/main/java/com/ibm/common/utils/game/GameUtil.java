package com.ibm.common.utils.game;

import com.common.util.BaseGameUtil;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 游戏工具类
 * @Author: Dongming
 * @Date: 2019-09-02 11:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GameUtil extends BaseGameUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	private static volatile GameUtil instance = null;

	private IbmGameService gameService;

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

	/**
	 * 初始化
	 */
	private void init() {
		try {
			gameService = new IbmGameService();
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

//	public enum Code {
//		/**
//		 * 游戏
//		 */
//		//号码类游戏
//		PK10("北京赛车") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//		}, XYFT("幸运飞艇") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//		}, JS10("极速赛车") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//		}, SELF_10_2("自有2分彩赛车") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//		}, SELF_10_5("自有5分彩赛车") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//		}, COUNTRY_10("国家赛车") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//
//			//球号类游戏
//		}, CQSSC("重庆时时彩") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//		}, JSSSC("极速时时彩") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//		}, SELF_SSC_5("自有5分彩时时彩") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//		}, COUNTRY_SSC("国家时时彩") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//
//			//快乐类游戏
//		}, XYNC("幸运农场") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//		}, GDKLC("广东快乐十分") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//
//			//快乐十分类游戏
//		}, GXKLSF("广西快乐十分") {
//			@Override public String getId() {
//				return GameUtil.id(this);
//			}
//		};
//
//		String name;
//
//		Code(String name) {
//			this.name = name;
//		}
//
//		public String getName() {
//			return name;
//		}
//
//		public abstract String getId();
//
//		@Override public String toString() {
//			return name();
//		}
//	}

	/**
	 * 查找盘口id
	 *
	 * @param gameCode 游戏code
	 * @return 盘口id
	 */
	public static String id(Code gameCode) {
		return findInstance().getId(gameCode);
	}

	/**
	 * 查找盘口code
	 *
	 * @param gameId 游戏 ID
	 * @return 盘口code
	 */
	public static Code code(String gameId) {
		return findInstance().getCode(gameId);
	}

	/**
	 * 获取游戏编码
	 *
	 * @param gameId 游戏id
	 * @return 游戏编码
	 */
	public static String name(String gameId) {
		return code(gameId).name();
	}

	/**
	 * 获取游戏名称
	 *
	 * @param gameId 游戏id
	 * @return 游戏名称
	 */
	public static String getName(String gameId) {
		return code(gameId).getName();
	}

	/**
	 * 查找盘口id
	 *
	 * @param gameCode 游戏code
	 * @return 盘口id
	 */
	public static String id(String gameCode) {
		return id(Code.valueOf(gameCode));
	}

	/**
	 * 查找盘口code
	 *
	 * @param gameId 游戏 ID
	 * @return 盘口code
	 */
	public static Code code(Object gameId) {
		return code(gameId.toString());
	}

	/**
	 * 获取游戏名称
	 *
	 * @param gameId 游戏id
	 * @return 游戏名称
	 */
	public static String getName(Object gameId) {
		return getName(gameId.toString());
	}

	/**
	 * 查找盘口code
	 *
	 * @param gameId 游戏 ID
	 * @return 盘口code
	 */
	public Code getCode(String gameId) {
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

	/**
	 * 查找盘口id
	 *
	 * @param gameCode 游戏code
	 * @return 盘口id
	 */
	public String getId(Code gameCode) {
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
	 * 获取盘口Code枚举类
	 *
	 * @return 盘口Code
	 */
	public static Code[] codes() {
		return Code.values();
	}

	/**
	 * 获取sgwin 盘口的游戏
	 *
	 * @return sgwin 盘口的游戏
	 */
	public static Code[] sgwinCodes() {
		return new Code[]{Code.PK10, Code.XYFT, Code.CQSSC, Code.JS10, Code.JSSSC};
	}

	/**
	 * 获取 hq 盘口的游戏
	 *
	 * @return hq 盘口的游戏
	 */
	public static Code[] hqCodes() {
		return new Code[]{Code.PK10, Code.XYFT, Code.CQSSC, Code.JS10, Code.JSSSC};
	}

}
