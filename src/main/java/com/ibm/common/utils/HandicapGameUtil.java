package com.ibm.common.utils;

import com.common.util.BaseGameUtil;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口游戏工具类
 * @Author: null
 * @Date: 2020-03-07 16:08
 * @Version: v1.0
 */
public class HandicapGameUtil {
	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 通过游戏code和盘口code获取类型
	 */
	private Map<GameUtil.Code, Map<HandicapUtil.Code, String>> handicapGameType;
	/**
	 * 通过游戏code和类型获取ids
	 */
	private Map<GameUtil.Code, Map<String, List<String>>> handicapIds;
	/**
	 * 通过游戏code和类型获取盘口codes
	 */
	private Map<GameUtil.Code, Map<String, List<String>>> handicapCodes;

	private IbmHandicapGameService handicapGameService;

	private static volatile HandicapGameUtil instance = null;

	public static HandicapGameUtil findInstance() {
		if (instance == null) {
			synchronized (HandicapUtil.class) {
				if (instance == null) {
					HandicapGameUtil handicapUtil = new HandicapGameUtil();
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
			handicapGameService = new IbmHandicapGameService();
			List<Map<String, Object>> list = handicapGameService.listCodeType(IbmTypeEnum.MEMBER.name());
			if (ContainerTool.isEmpty(list)) {
				return;
			}
			handicapGameType = new HashMap<>(10);
			handicapIds = new HashMap<>(10);
			handicapCodes = new HashMap<>(10);
			for (Map<String, Object> map : list) {
				GameUtil.Code gameCode = GameUtil.Code.valueOf(map.get("GAME_CODE_").toString());
				HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(map.get("HANDICAP_CODE_").toString());
				String handicapId = map.get("HANDICAP_ID_").toString();

				if (handicapGameType.containsKey(gameCode)) {
					handicapGameType.get(gameCode).put(handicapCode, map.get("TYPE_").toString());
				} else {
					Map<HandicapUtil.Code, String> codeMap = new HashMap<>(10);
					codeMap.put(handicapCode, map.get("TYPE_").toString());
					handicapGameType.put(gameCode, codeMap);
				}

				if (handicapIds.containsKey(gameCode)) {
					Map<String, List<String>> idMap = handicapIds.get(gameCode);
					Map<String, List<String>> codeMap = handicapCodes.get(gameCode);
					if (idMap.containsKey(map.get("TYPE_").toString())) {
						idMap.get(map.get("TYPE_").toString()).add(handicapId);
						codeMap.get(map.get("TYPE_").toString()).add(handicapCode.name());
					} else {
						List<String> handicapIdList = new ArrayList<>();
						handicapIdList.add(handicapId);
						idMap.put(map.get("TYPE_").toString(), handicapIdList);

						List<String> handicapCodeList = new ArrayList<>();
						handicapCodeList.add(handicapCode.name());
						codeMap.put(map.get("TYPE_").toString(), handicapCodeList);
					}
				} else {
					Map<String, List<String>> idMap = new HashMap<>(10);
					List<String> handicapIdList = new ArrayList<>();
					handicapIdList.add(handicapId);
					idMap.put(map.get("TYPE_").toString(), handicapIdList);
					handicapIds.put(gameCode, idMap);

					Map<String, List<String>> codeMap = new HashMap<>(10);
					List<String> handicapCodeList = new ArrayList<>();
					handicapCodeList.add(handicapCode.name());
					codeMap.put(map.get("TYPE_").toString(), handicapCodeList);
					handicapCodes.put(gameCode, codeMap);
				}
			}
		} catch (SQLException e) {
			log.error("初始化盘口游戏工具类错误", e);
		}
	}

	/**
	 * 初始化内存信息
	 */
	public static void initInfo() {
		findInstance().init();
	}
	/**
	 * 查找类型
	 *
	 * @param game     游戏code
	 * @param handicap 盘口code
	 * @return 类型
	 */
	public static String type(GameUtil.Code game, HandicapUtil.Code handicap) {
		return findInstance().getType(game, handicap);
	}

	/**
	 * 获取相同类型的盘口ids
	 *
	 * @param game     游戏code
	 * @param handicap 盘口code
	 * @return
	 */
	public static List<String> handicapIds(GameUtil.Code game, HandicapUtil.Code handicap) {
		HandicapGameUtil instance = findInstance();
		String type = instance.getType(game, handicap);
		if (StringTool.isEmpty(type) || ContainerTool.isEmpty(instance.handicapIds)) {
			return new ArrayList<>();
		}
		return instance.getHandicapIds(game, type);
	}
	/**
	 * 获取相同类型的盘口codes
	 *
	 * @param game     游戏code
	 * @param handicap 盘口code
	 * @return
	 */
	public static List<String> handicapCodes(GameUtil.Code game, HandicapUtil.Code handicap) {
		HandicapGameUtil instance = findInstance();
		String type = instance.getType(game, handicap);
		if (StringTool.isEmpty(type)||ContainerTool.isEmpty(instance.handicapCodes)) {
			return new ArrayList<>();
		}
		return instance.getHandicapCodes(game,type);
	}

	/**
	 * 查找类型
	 *
	 * @param game     游戏code
	 * @param handicap 盘口code
	 * @return 类型
	 */
	public static String lotteryType(GameUtil.Code game, HandicapUtil.Code handicap) {
		switch (game) {
			case JS10:
			case JSSSC:
				switch (handicap) {
					case HQ:
						return "2";
					case NCOM1:
						return "3";
					case NCOM2:
						return "4";
					default:
						return "1";
				}
			case XYFT:
				switch (handicap) {
					case SGWIN:
					case IDC:
					case COM:
						return "2";
					default:
						return "1";
				}
			default:
				return "1";
		}
	}

	/**
	 * 获取盘口codes
	 * @param game		游戏编码
	 * @param type		游戏类型
	 * @return
	 */
	private List<String> getHandicapCodes(BaseGameUtil.Code game, String type) {
		try {
			Map<String, List<String>> codeMap;
			List<String> handicapCodeList;
			if (handicapCodes.containsKey(game)) {
				codeMap = handicapCodes.get(game);

				if (codeMap.containsKey(type)) {
					handicapCodeList = codeMap.get(type);
				} else {
					handicapCodeList = handicapGameService.findCodes(game.name(), type);
					codeMap.put(type, handicapCodeList);
				}
			} else {
				codeMap = new HashMap<>(10);
				handicapCodeList = handicapGameService.findCodes(game.name(), type);
				codeMap.put(type, handicapCodeList);
				handicapCodes.put(game, codeMap);
			}
			return handicapCodeList;
		} catch (SQLException e) {
			log.error("查找盘口游戏类型错误", e);
			return new ArrayList<>();
		}
	}
	/**
	 * 获取相同类型的盘口ids
	 *
	 * @param game 游戏code
	 * @param type 类型
	 * @return
	 */
	private List<String> getHandicapIds(GameUtil.Code game, String type) {
		try {
			Map<String, List<String>> idMap;
			List<String> handicapIdList;
			if (handicapIds.containsKey(game)) {
				idMap = handicapIds.get(game);
				if (idMap.containsKey(type)) {
					handicapIdList = idMap.get(type);
				} else {
					handicapIdList = handicapGameService.findIds(game.name(), type);
					idMap.put(type, handicapIdList);
				}
			} else {
				idMap = new HashMap<>(10);
				handicapIdList = handicapGameService.findIds(game.name(), type);
				idMap.put(type, handicapIdList);
				handicapIds.put(game, idMap);
			}
			return handicapIdList;
		} catch (SQLException e) {
			log.error("查找盘口游戏类型错误", e);
			return new ArrayList<>();
		}
	}

	/**
	 * 获取类型
	 *
	 * @param game     游戏code
	 * @param handicap 盘口code
	 * @return 类型
	 */
	private String getType(GameUtil.Code game, HandicapUtil.Code handicap) {
		try {
			if (ContainerTool.isEmpty(handicapGameType)) {
				init();
				if (ContainerTool.isEmpty(handicapGameType)) {
					return null;
				}
			}
			if (!handicapGameType.containsKey(game)) {
				Map<HandicapUtil.Code, String> handicapMap = new HashMap<>(10);
				List<Map<String, Object>> list = handicapGameService.findByGameCode(game.name());
				for (Map<String, Object> map : list) {
					HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(map.get("HANDICAP_CODE_").toString());
					handicapMap.put(handicapCode, map.get("TYPE_").toString());
				}
				handicapGameType.put(game, handicapMap);
			}
			if (!handicapGameType.containsKey(game)) {
				log.error("查找盘口游戏类型错误，不存在游戏" + game.name());
				return null;
			}
			if (!handicapGameType.get(game).containsKey(handicap)) {
				List<Map<String, Object>> list = handicapGameService.findByHandicapCode(handicap.name());
				for (Map<String, Object> map : list) {
					GameUtil.Code gameCode = GameUtil.Code.valueOf(map.get("GAME_CODE_").toString());
					if (!handicapGameType.containsKey(gameCode)) {
						continue;
					}
					handicapGameType.get(gameCode).put(handicap, map.get("TYPE_").toString());
				}
			}
			if (!handicapGameType.get(game).containsKey(handicap)) {
				log.error("查找盘口游戏类型错误，不存在盘口{}游戏{}", handicap.name(), game.name());
				return null;
			}
			return handicapGameType.get(game).get(handicap);
		} catch (SQLException e) {
			log.error("查找盘口游戏类型错误", e);
			return null;
		}
	}

}
