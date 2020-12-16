package com.ibm.old.v1.cloud.core.tool;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.service.IbmHandicapGameTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.HashMap;
/**
 * @Description: 盘口游戏工具类
 * @Author: zjj
 * @Date: 2019-05-15 10:32
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HandicapGameTool {

	private static final HashMap<String, String> SUB_IEBI_TABLE_NAME = new HashMap<>(5);

	/**
	 * 根据盘口code和游戏code获取表名
	 * @param gameCode		游戏code
	 * @param handicapCode	盘口code
	 * @return 表名
	 */
	public static String getTableName(IbmGameEnum gameCode, IbmHandicapEnum handicapCode) throws SQLException {
		if(gameCode==null||handicapCode==null){
			return null;
		}
		String key = handicapCode.name().concat("#").concat(gameCode.name());
		if(!SUB_IEBI_TABLE_NAME.containsKey(key)){
			synchronized (HandicapGameTool.class) {
				if(!SUB_IEBI_TABLE_NAME.containsKey(key)){
					String tableName =  new IbmHandicapGameTService().findSubIebiTableName(gameCode.name(), handicapCode.name());
					if (StringTool.isEmpty(tableName)) {
						return null;
					}
					SUB_IEBI_TABLE_NAME.put(key,tableName);
				}
			}
		}
		return SUB_IEBI_TABLE_NAME.get(key);
	}
	/**
	 * 根据盘口code和游戏code获取表名
	 * @param gameCode		游戏code
	 * @param handicapCode	盘口code
	 * @return 表名
	 */
	public static String getTableName(String gameCode, String handicapCode) throws SQLException {
		if (StringTool.isEmpty(gameCode,handicapCode)) {
			return null;
		}
		String key = handicapCode.concat("#").concat(gameCode);
		if(!SUB_IEBI_TABLE_NAME.containsKey(key)){
			synchronized (HandicapGameTool.class) {
				if(!SUB_IEBI_TABLE_NAME.containsKey(key)){
					String tableName = new IbmHandicapGameTService().findSubIebiTableName(gameCode, handicapCode);
					if (StringTool.isEmpty(tableName)) {
						return null;
					}
					SUB_IEBI_TABLE_NAME.put(key,tableName);
				}
			}
		}
		return SUB_IEBI_TABLE_NAME.get(key);
	}
	/**
	 * 根据盘口id和游戏id获取
	 * @param gameId		游戏id
	 * @param handicapId	盘口id
	 * @return 表名
	 */
	public static String getTableNameById(String gameId, String handicapId) throws Exception {
		if(StringTool.isEmpty(gameId,handicapId)){
			return null;
		}
		IbmGameEnum gameCode=GameTool.findCode(gameId);

		IbmHandicapEnum handicapCode=HandicapTool.findCode(handicapId);

		return getTableName(gameCode,handicapCode);
	}
	/**
	 * 根据盘口id和游戏id获取
	 * @param gameCode	游戏code
	 * @param handicapId	盘口id
	 * @return 表名
	 */
	public static String getTableNameByCodeAndId(String gameCode, String handicapId) throws Exception {
		if(StringTool.isEmpty(gameCode,handicapId)){
			return null;
		}
		IbmGameEnum game=IbmGameEnum.valueOf(gameCode);

		IbmHandicapEnum handicapCode=HandicapTool.findCode(handicapId);

		return getTableName(game,handicapCode);
	}
}
