package com.ibm.old.v1.cloud.core.tool;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_rep_draw_cqssc.t.entity.IbmRepDrawCqsscT;
import com.ibm.old.v1.cloud.ibm_rep_draw_pk10.t.entity.IbmRepDrawPk10T;
import com.ibm.old.v1.cloud.ibm_rep_draw_xyft.t.entity.IbmRepDrawXyftT;
import com.ibm.old.v1.cloud.ibm_rep_grab_cqssc.t.entity.IbmRepGrabCqsscT;
import com.ibm.old.v1.cloud.ibm_rep_grab_pk10.t.entity.IbmRepGrabPk10T;
import com.ibm.old.v1.cloud.ibm_rep_grab_xyft.t.entity.IbmRepGrabXyftT;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.*;

import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 游戏工具类
 * @Author: Dongming
 * @Date: 2019-02-14 10:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GameTool {

	private static final HashMap<String, String> GAME_ID = new HashMap<>(5);
	private static final HashMap<String, String> GAME_CODE = new HashMap<>(5);

	private static final String[] CHAMPION_SUM = {"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
			"16", "17", "18", "19", "小", "大", "单", "双"};

	private static final String[] SIZE = {"大", "小"};
	private static final String[] PARITY = {"单", "双"};
	private static final String[] DRAGON = {"龙", "虎"};

	/**
	 * 获取结果数据
	 *
	 * @param grabId   爬取id
	 * @param grabPk10 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawPk10T draw(String grabId, IbmRepGrabPk10T grabPk10) throws SocketException {
		String[] drawNumberStrs = grabPk10.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 10 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawPk10T drawPk10 = new IbmRepDrawPk10T();
		drawPk10.setRepGrabPk10Id(grabId);
		drawPk10.setGameId(grabPk10.getGameId());
		drawPk10.setPeriod(grabPk10.getPeriod());
		drawPk10.setDrawTime(grabPk10.getDrawTime());
		drawPk10.setDrawTimeLong(grabPk10.getDrawTimeLong());
		drawPk10.setDrawNumber(grabPk10.getDrawNumber());
		for (int i = 0; i < 10; i++) {
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "Size", PK10Tool.size(drawNumbers[i]));
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "SizeEn", PK10Tool.sizeEn(drawNumbers[i]));
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "Single", PK10Tool.single(drawNumbers[i]));
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "SingleEn", PK10Tool.singleEn(drawNumbers[i]));
		}
		for (int i = 0; i < 5; i++) {
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "Dragon", PK10Tool.dragon(drawNumbers[i], drawNumbers[9 - i]));
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "DragonEn",
					PK10Tool.dragonEn(drawNumbers[i], drawNumbers[9 - i]));
		}
		int championSum = drawNumbers[0] + drawNumbers[1];
		drawPk10.setChampionSumNunmber(championSum);
		drawPk10.setChampionSumNunmberEn(NumberTool.getEn(championSum));
		drawPk10.setChampionSumSize(PK10Tool.sizeChampionSum(championSum));
		drawPk10.setChampionSumSizeEn(PK10Tool.sizeChampionSumEn(championSum));
		drawPk10.setChampionSumSingle(PK10Tool.singleChampionSum(championSum));
		drawPk10.setChampionSumSingleEn(PK10Tool.singleChampionSumEn(championSum));
		drawPk10.setCreateTime(new Date());
		drawPk10.setCreateTimeLong(drawPk10.getCreateTime().getTime());
		drawPk10.setUpdateTime(new Date());
		drawPk10.setUpdateTimeLong(drawPk10.getUpdateTime().getTime());
		drawPk10.setState(IbmStateEnum.OPEN.name());
		drawPk10.setDesc(IpTool.getLocalIpList().toString());
		return drawPk10;
	}
	/**
	 * 获取结果数据
	 *
	 * @param grabId   爬取id
	 * @param grabXyft 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawXyftT draw(String grabId, IbmRepGrabXyftT grabXyft) throws SocketException {
		String[] drawNumberStrs = grabXyft.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 10 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawXyftT drawXyft = new IbmRepDrawXyftT();
		drawXyft.setRepGrabXyftId(grabId);
		drawXyft.setGameId(grabXyft.getGameId());
		drawXyft.setPeriod(grabXyft.getPeriod());
		drawXyft.setDrawTime(grabXyft.getDrawTime());
		drawXyft.setDrawTimeLong(grabXyft.getDrawTimeLong());
		drawXyft.setDrawNumber(grabXyft.getDrawNumber());
		for (int i = 0; i < 10; i++) {
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "Size", XYFTTool.size(drawNumbers[i]));
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "SizeEn", XYFTTool.sizeEn(drawNumbers[i]));
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "Single", XYFTTool.single(drawNumbers[i]));
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "SingleEn", XYFTTool.singleEn(drawNumbers[i]));
		}
		for (int i = 0; i < 5; i++) {
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "Dragon", XYFTTool.dragon(drawNumbers[i], drawNumbers[9 - i]));
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "DragonEn",
					XYFTTool.dragonEn(drawNumbers[i], drawNumbers[9 - i]));
		}
		int championSum = drawNumbers[0] + drawNumbers[1];
		drawXyft.setChampionSumNunmber(championSum);
		drawXyft.setChampionSumNunmberEn(NumberTool.getEn(championSum));
		drawXyft.setChampionSumSize(XYFTTool.sizeChampionSum(championSum));
		drawXyft.setChampionSumSizeEn(XYFTTool.sizeChampionSumEn(championSum));
		drawXyft.setChampionSumSingle(XYFTTool.singleChampionSum(championSum));
		drawXyft.setChampionSumSingleEn(XYFTTool.singleChampionSumEn(championSum));
		drawXyft.setCreateTime(new Date());
		drawXyft.setCreateTimeLong(drawXyft.getCreateTime().getTime());
		drawXyft.setUpdateTime(new Date());
		drawXyft.setUpdateTimeLong(drawXyft.getUpdateTime().getTime());
		drawXyft.setState(IbmStateEnum.OPEN.name());
		drawXyft.setDesc(IpTool.getLocalIpList().toString());
		return drawXyft;
	}

	/**
	 * 获取结果数据
	 *
	 * @param grabId    爬取id
	 * @param grabCqssc 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawCqsscT draw(String grabId, IbmRepGrabCqsscT grabCqssc) throws SocketException {
		String[] drawNumberStrs = grabCqssc.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 5 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawCqsscT drawCqssc = new IbmRepDrawCqsscT();
		drawCqssc.setRepGrabCqsscId(grabId);
		drawCqssc.setGameId(grabCqssc.getGameId());
		drawCqssc.setPeriod(grabCqssc.getPeriod());
		drawCqssc.setDrawTime(grabCqssc.getDrawTime());
		drawCqssc.setDrawTimeLong(grabCqssc.getDrawTimeLong());
		drawCqssc.setDrawNumber(grabCqssc.getDrawNumber());
		for (int i = 0; i < 5; i++) {
			ReflectTool.set(drawCqssc, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawCqssc, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawCqssc, "setP" + (i + 1) + "Size", CQSSCTool.size(drawNumbers[i]));
			ReflectTool.set(drawCqssc, "setP" + (i + 1) + "SizeEn", CQSSCTool.sizeEn(drawNumbers[i]));
			ReflectTool.set(drawCqssc, "setP" + (i + 1) + "Single", CQSSCTool.single(drawNumbers[i]));
			ReflectTool.set(drawCqssc, "setP" + (i + 1) + "SingleEn", CQSSCTool.singleEn(drawNumbers[i]));
		}
		drawCqssc.setDragonTiger(CQSSCTool.dragon(drawNumbers[0], drawNumbers[4]));
		drawCqssc.setDragonTigerEn(CQSSCTool.dragonEn(drawNumbers[0], drawNumbers[4]));

		int total = 0;
		for (int i = 0; i < 5; i++) {
			total += drawNumbers[i];
		}
		drawCqssc.setTotal(total);
		drawCqssc.setTopEn(NumberTool.getEn(total));
		drawCqssc.setTotalSingle(CQSSCTool.single(total));
		drawCqssc.setTotalSingleEn(CQSSCTool.singleEn(total));
		drawCqssc.setTotalSize(CQSSCTool.sizeTotal(total));
		drawCqssc.setTotalSizeEn(CQSSCTool.sizeTotalEn(total));
		drawCqssc.setTop(CQSSCTool.threeBalls(drawNumbers[0], drawNumbers[1], drawNumbers[2]));
		drawCqssc.setTopEn(CQSSCTool.threeBallsEn(drawCqssc.getTop()));
		drawCqssc.setCentre(CQSSCTool.threeBalls(drawNumbers[1], drawNumbers[2], drawNumbers[3]));
		drawCqssc.setCentreEn(CQSSCTool.threeBallsEn(drawCqssc.getCentre()));
		drawCqssc.setLater(CQSSCTool.threeBalls(drawNumbers[2], drawNumbers[3], drawNumbers[4]));
		drawCqssc.setLaterEn(CQSSCTool.threeBallsEn(drawCqssc.getLater()));
		drawCqssc.setCreateTime(new Date());
		drawCqssc.setCreateTimeLong(drawCqssc.getCreateTime().getTime());
		drawCqssc.setUpdateTime(new Date());
		drawCqssc.setUpdateTimeLong(drawCqssc.getUpdateTime().getTime());
		drawCqssc.setState(IbmStateEnum.OPEN.name());
		drawCqssc.setDesc(IpTool.getLocalIpList().toString());
		return drawCqssc;
	}

	/**
	 * 获取开奖信息sql语句
	 *
	 * @param game 游戏
	 * @return 开奖信息sql语句
	 */
	public static String findDrawInfoSql(IbmGameEnum game) {
		switch (game) {
			case PK10:
				return getDrawInfoBallSql("ibm_rep_draw_pk10");
			case XYFT:
				return getDrawInfoBallSql("ibm_rep_draw_xyft");
			default:
				throw new RuntimeException("不存在的游戏" + game.getName());
		}
	}
	/**
	 * 获取球的sql语句
	 *
	 * @param tableName 开奖表名
	 * @return 球的开奖信息sql语句
	 */
	private static String getDrawInfoBallSql(String tableName) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT ");
		for (int i = 1; i <= 10; i++) {
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[i]).append("|',").append("P").append(i)
					.append("_NUMBER_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[i]).append("|',").append("P").append(i)
					.append("_SIZE_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[i]).append("|',").append("P").append(i)
					.append("_SINGLE_),");
		}
		for (int i = 1; i <= 5; i++) {
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[i]).append("|',").append("P").append(i)
					.append("_DRAGON_),");
		}
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[0]).append("|',")
				.append("CHAMPION_SUM_NUNMBER_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[0]).append("|',").append("CHAMPION_SUM_SIZE_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[0]).append("|',").append("CHAMPION_SUM_SINGLE_),");
		sqlBuilder.append("DRAW_NUMBER_ FROM ").append(tableName).append(" WHERE PERIOD_ = ? AND STATE_ = ? ");
		return sqlBuilder.toString();
	}

	/**
	 * 匹配号码
	 *
	 * @param baseData  基准开奖数据
	 * @param valiData  验证开奖数据
	 * @param state     正投/反投
	 * @param code      方案组code
	 * @param threshold 大小阈值
	 * @return 匹配成功
	 */
	public static boolean matchNum(String[] baseData, String[] valiData, boolean state, String code, int threshold) {
		Integer[] index = GameTool.findFollowTwoSide(code);
		int numIndex = index[0];
		int typeIndex = index[1];

		int baseNum;
		int valiNum;

		if (numIndex == 0) {
			//冠亚和
			baseNum = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]);
			valiNum = Integer.parseInt(valiData[0]) + Integer.parseInt(valiData[1]);
			threshold = 11;
		} else if (numIndex < 0 || numIndex > 10) {
			throw new RuntimeException("非法的排序捕捉，索引为" + numIndex);
		} else {
			baseNum = Integer.parseInt(baseData[numIndex - 1]);
			valiNum = Integer.parseInt(valiData[numIndex - 1]);
		}

		switch (typeIndex) {
			//大小
			case 1:
				return isBoS(state, baseNum, valiNum, threshold);
			//单双
			case 2:
				return isParity(state, baseNum, valiNum);
			//龙虎
			case 3:
				if (numIndex <= 0 || numIndex > 5) {
					throw new RuntimeException("非法的排序捕捉，索引为" + numIndex);
				}
				//1-10	0-9   11-9 ___________ 4-1    length-1-(index-1)
				int base2Num = Integer.parseInt(baseData[baseData.length - numIndex]);
				int vali2Num = Integer.parseInt(valiData[baseData.length - numIndex]);
				return isDoT(state, baseNum, valiNum, base2Num, vali2Num);
			default:
				throw new RuntimeException("非法的类型捕捉，类型为" + typeIndex);
		}
	}

	/**
	 * 匹配号码 - 反投
	 *
	 * @param baseData 基准开奖数据
	 * @param valiData 验证开奖数据
	 * @param selects  选择位置《验证开奖数据》
	 * @param bets     投注位置《基准开奖数据》
	 * @return 匹配成功
	 */
	public static boolean matchNum(String[] baseData, String[] valiData, String[] selects, String[] bets) {
		for (String bet : bets) {
			String open = baseData[Integer.parseInt(bet) - 1];
			for (String select : selects) {
				String vali = valiData[Integer.parseInt(select) - 1];
				if (open.equals(vali)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 开某投某
	 *
	 * @param open    开奖号码
	 * @param vali    验证开奖号码
	 * @param selects 方案组详情
	 * @return 匹配成功
	 */
	public static boolean matchNum(String open, String vali, JSONArray selects) {
		for (Object data : selects) {
			JSONObject item = (JSONObject) data;
			String select = item.getString("select");
			if (ContainerTool.isEmpty(select.split(",")) || select.split(",").length >= 2 || !vali.equals(select)) {
				continue;
			}
			String bets = item.getString("bet");
			if (ContainerTool.isEmpty(bets.split(","))) {
				continue;
			}
			for (String bet : bets.split(",")) {
				if (open.equals(bet)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 是否匹配中---号码追踪
	 *
	 * @param baseData 基准开奖数据
	 * @param valiData 验证开奖数据
	 * @param track    追踪号码
	 * @param bets     投注号码
	 * @return 匹配 中 true
	 */
	public static boolean matchNum(String[] baseData, String[] valiData, String track, String[] bets) {
		//追踪号码在验证数据所在的位置
		int index = ContainerTool.findIndex(valiData, track);
		//基础投注比较数据
		String base = baseData[index];
		for (String bet : bets) {
			//中
			if (bet.equals(base)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 获取双面的编码 所对应的索引
	 *
	 * @param code 双面的编码
	 * @return 索引
	 */
	public static Integer[] findFollowTwoSide(String code) {
		String[] strs = code.split("_");
		Integer[] results = new Integer[2];
		results[0] = StringTool.getRankCn(strs[0]);
		results[1] = StringTool.getPKTypeCn(strs[1]);
		return results;
	}

	/**
	 * 大小匹配
	 *
	 * @param state   正反
	 * @param baseNum 基准号码
	 * @param valiNum 验证号码
	 * @return 匹配大小+单双
	 */
	private static boolean isBoS(boolean state, int baseNum, int valiNum, int i) {
		return (baseNum > i) == (state == (valiNum > i));
	}

	/**
	 * 单双匹配
	 *
	 * @param state   正反
	 * @param baseNum 基准号码
	 * @param valiNum 验证号码
	 * @return 匹配正反+单双
	 */
	private static boolean isParity(boolean state, int baseNum, int valiNum) {
		return (baseNum % 2 != 0) == (state == (valiNum % 2 != 0));
	}

	/**
	 * 龙虎匹配
	 *
	 * @param state    正反
	 * @param baseNum  基准号码
	 * @param base2Num 基准号码2
	 * @param valiNum  验证号码
	 * @param vali2Num 验证号码2
	 * @return 匹配龙虎+单双
	 */
	private static boolean isDoT(boolean state, int baseNum, int valiNum, int base2Num, int vali2Num) {
		return (baseNum > base2Num) == (state == (valiNum > vali2Num));
	}
	/**
	 * 获取大小
	 *
	 * @param number    数值
	 * @param threshold 阈值
	 * @param state     正反投
	 * @return '大' or '小'
	 */
	public static String getBoS(int number, int threshold, boolean state) {
		if (state) {
			if (number > threshold) {
				return "大" ;
			}
			return "小" ;
		}
		if (number > threshold) {
			return "小" ;
		}
		return "大" ;
	}

	/**
	 * 获取单双
	 *
	 * @param number 数值
	 * @param state  正反投
	 * @return '单' or '双'
	 */
	public static String getParity(int number, boolean state) {
		//正投
		if (state) {
			if (number % 2 != 0) {
				return "单" ;
			}
			return "双" ;
		}
		//反投
		if (number % 2 != 0) {
			return "双" ;
		}
		return "单" ;
	}

	/**
	 * 获取龙虎
	 *
	 * @param number  1-5的数
	 * @param number2 5-10的数
	 * @param state   正反投
	 * @return '龙' or '虎'
	 */
	public static String getDoT(int number, int number2, boolean state) {
		if (state) {
			if (number > number2) {
				return "龙" ;
			}
			return "虎" ;
		}
		if (number > number2) {
			return "虎" ;
		}
		return "龙" ;
	}

	/**
	 * 获取冠亚和索引
	 *
	 * @param bet 冠亚和投注项
	 * @return 冠亚和索引
	 */
	public static Integer getChampionSumIndex(String bet) {
		for (int i = 0; i < CHAMPION_SUM.length; i++) {
			if (bet.equals(CHAMPION_SUM[i])) {
				return i;
			}
		}
		return null;
	}

	/**
	 * 获取大小索引
	 *
	 * @param size 大小
	 * @return 大小索引
	 */
	public static Integer sizeIndex(String size) {
		for (int i = 0; i < SIZE.length; i++) {
			if (size.equals(SIZE[i])) {
				return i;
			}
		}
		return null;
	}

	/**
	 * 获取单双索引
	 *
	 * @param parity 单双
	 * @return 单双索引
	 */
	public static Integer parityIndex(String parity) {
		for (int i = 0; i < PARITY.length; i++) {
			if (parity.equals(PARITY[i])) {
				return i;
			}
		}
		return null;
	}
	/**
	 * 获取龙虎索引
	 *
	 * @param dragon 龙虎
	 * @return 龙虎索引
	 */
	public static Integer dragonIndex(String dragon) {
		for (int i = 0; i < DRAGON.length; i++) {
			if (dragon.equals(DRAGON[i])) {
				return i;
			}
		}
		return null;
	}

	/**
	 * 根据类型获取对应索引的值
	 *
	 * @param typeKey 类型
	 * @param index   索引
	 * @return 值
	 */
	public static String getNumber(String typeKey, Integer index) {
		switch (typeKey) {
			case "SIZE":
				return SIZE[index];
			case "PARITY":
				return PARITY[index];
			case "DRAGON":
				return DRAGON[index];
			case "NUMBER":
				return (index + 1) + "" ;
			case "CHAMPION_SUM":
				return CHAMPION_SUM[index];
			default:
				return null;

		}
	}

	/**
	 * 根据游戏code找游戏id
	 *
	 * @param gameCode 游戏code
	 * @return 游戏id
	 */
	public static String findId(String gameCode) throws Exception {
		if (!GAME_ID.containsKey(gameCode)) {
			synchronized (GameTool.class) {
				if (!GAME_ID.containsKey(gameCode)) {
					String gameId = new IbmGameTService().findId(gameCode);
					if (StringTool.isEmpty(gameId)) {
						return null;
					}
					GAME_ID.put(gameCode, gameId);
				}
			}
		}
		return GAME_ID.get(gameCode);
	}
	/**
	 * 根据游戏id 找游戏code
	 *
	 * @param gameId 游戏id
	 * @return 游戏code
	 */
	public static IbmGameEnum findCode(String gameId) throws Exception {
		if (StringTool.isEmpty(gameId)) {
			return null;
		}
		if (!GAME_CODE.containsKey(gameId)) {
			synchronized (GameTool.class) {
				if (!GAME_CODE.containsKey(gameId)) {
					String gameCode = new IbmGameTService().findCode(gameId);
					if (StringTool.isEmpty(gameCode)) {
						return null;
					}
					GAME_CODE.put(gameId, gameCode);
				}
			}
		}
		return IbmGameEnum.valueOf(GAME_CODE.get(gameId));
	}

	/**
	 * 获取冷热数据
	 *
	 * @param basePeriod 期数
	 * @param game       游戏
	 * @return 冷热数据
	 */
	public static String[][] findHotAndCold(Object basePeriod, IbmGameEnum game) throws Exception {
		switch (game) {
			case PK10:
				return PK10Tool.findHotAndCold(NumberTool.getInteger(basePeriod));
			case XYFT:
				return XYFTTool.findHotAndCold(basePeriod.toString());
			default:
				throw new RuntimeException("不存在的游戏" + game.getName());
		}
	}
	/**
	 * 获取历史数据
	 *
	 * @param basePeriod 期数
	 * @param game       游戏
	 * @return 历史数据
	 */
	public static String[] getHistoryData(Object basePeriod, IbmGameEnum game) throws Exception {
		switch (game) {
			case PK10:
				return PK10Tool.getHistoryData(NumberTool.getInteger(basePeriod));
			case XYFT:
				return XYFTTool.getHistoryData(basePeriod.toString());
			default:
				throw new RuntimeException("不存在的游戏" + game.getName());
		}
	}
	/**
	 * 获取历史数据
	 *
	 * @param period 期数
	 * @param game   游戏
	 */
	public static void find20HistoryData(Object period, IbmGameEnum game) throws Exception {
		switch (game) {
			case PK10:
				PK10Tool.find20HistoryData(NumberTool.getInteger(period));
				break;
			case XYFT:
				XYFTTool.find20HistoryData(period.toString());
				break;
			default:
				throw new RuntimeException("不存在的游戏" + game.getName());
		}
	}
	/**
	 * 获取历史数据
	 *
	 * @param game 游戏
	 * @return 历史数据
	 */
	public static Map<Object, String[]> get20HistoryData(IbmGameEnum game) {
		switch (game) {
			case PK10:
				return PK10Tool.getHistoryData();
			case XYFT:
				return XYFTTool.getHistoryData();
			default:
				throw new RuntimeException("不存在的游戏" + game.getName());
		}
	}


}
