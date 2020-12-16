package com.ibs.plan.common.enums;

import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 任务状态或数据状态
 * @Author: zjj
 * @Date: 2020-05-18 17:11
 * @Version: v1.0
 */
public enum IbsModeEnum {
	//TODO 投注模式
	/**
	 * Bet mode - regular
	 */
	BET_MODE_REGULAR("Bet mode - regular", 0) {
		@Override public String getMsgCn() {
			return "常规";
		}
	},
	/**
	 * Bet mode - period roll
	 */
	BET_MODE_PERIOD_ROLL("Bet mode - period roll", 1) {
		@Override public String getMsgCn() {
			return "期期滚";
		}
	},

	//TODO 资金切换方式
	/**
	 * Fund swap mode - no swap on reset
	 */
	FUND_SWAP_MODE_NO_SWAP_ON_RESET("Fund swap mode - no swap on reset", 0) {
		@Override public String getMsgCn() {
			return "不中切换中重置";
		}
	},
	/**
	 * Fund swap mode - on swap no reset
	 */
	FUND_SWAP_MODE_ON_SWAP_NO_RESET("Fund swap mode - on swap no reset", 1) {
		@Override public String getMsgCn() {
			return "中切换不中重置";
		}
	},
	/**
	 * Fund swap mode - no swap on stop
	 */
	FUND_SWAP_MODE_NO_SWAP_ON_STOP("Fund swap mode - no swap on stop", 2) {
		@Override public String getMsgCn() {
			return "不中切换中停留";
		}
	},
	/**
	 * Fund swap mode - on swap no stop
	 */
	FUND_SWAP_MODE_ON_SWAP_NO_STOP("Fund swap mode - on swap no stop", 3) {
		@Override public String getMsgCn() {
			return "中切换不中停留";
		}
	},
	/**
	 * Fund swap mode - swap
	 */
	FUND_SWAP_MODE_SWAP("Fund swap mode - swap", 4) {
		@Override public String getMsgCn() {
			return "不管输赢一直切换";
		}
	},
	/**
	 * Fund swap mode - random
	 */
	FUND_SWAP_MODE_RANDOM("Fund swap mode - random", 5) {
		@Override public String getMsgCn() {
			return "随机切换";
		}
	},

	//TODO 期期滚切换方式
	/**
	 * Period roll mode - all
	 */
	PERIOD_ROLL_MODE_ALL("Period roll mode - all", 0) {
		@Override public String getMsgCn() {
			return "不管重复不重复";
		}
	},
	/**
	 * Period roll mode - no repeat
	 */
	PERIOD_ROLL_MODE_NO_REPEAT("Period roll mode - no repeat", 1) {
		@Override public String getMsgCn() {
			return "重复内容不投注";
		}
	};

	private String msg;
	private int sn;

	/**
	 * 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	 *
	 * @param msg msg
	 * @param sn  sn
	 */
	IbsModeEnum(String msg, int sn) {
		this.msg = msg;
		this.sn = sn;
	}

	public String getMsg() {
		return msg;
	}
	public int getSn() {
		return sn;
	}

	/**
	 * 获取中文消息
	 *
	 * @return 中文消息
	 */
	public abstract String getMsgCn();

	/**
	 * 比较
	 *
	 * @param obj 对象Obj
	 * @return 比较
	 */
	public boolean equal(Object obj) {
		return this.name().equalsIgnoreCase(obj.toString());
	}

	/**
	 * 是否属于投注模式
	 *
	 * @param code 编码
	 * @return 是 true
	 */
	public static boolean isBetMode(String code) {
		if (!StringTool.isContains(code, "BET_MODE_")) {
			return false;
		}
		for (IbsModeEnum mode : IbsModeEnum.values()) {
			if (StringTool.isContains(mode.name(), "BET_MODE_")) {
				if (code.equals(mode.name())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 是否属于资金切换方式模式
	 *
	 * @param code 编码
	 * @return 是 true
	 */
	public static boolean isFundSwapMode(String code) {
		if (!StringTool.isContains(code, "FUND_SWAP_MODE_")) {
			return false;
		}
		for (IbsModeEnum mode : IbsModeEnum.values()) {
			if (StringTool.isContains(mode.name(), "FUND_SWAP_MODE_")) {
				if (code.equals(mode.name())) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 是否属于期期滚模式
	 *
	 * @param betMode 投注模式
	 * @param code    编码
	 * @return 是 true
	 */
	public static boolean isPeriodRollMode(String betMode, String code) {
		if (!StringTool.isContains(code, "PERIOD_ROLL_") || !betMode.equals(BET_MODE_PERIOD_ROLL.name())) {
			return false;
		}
		for (IbsModeEnum mode : IbsModeEnum.values()) {
			if (StringTool.isContains(mode.name(), "PERIOD_ROLL_MODE_")) {
				if (code.equals(mode.name())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 根据资金切换模式 获取匹配模式
	 *
	 * @param code 资金切换模式
	 * @return 匹配成功    true
	 */
	public static boolean swapMode(String code) {
		switch (code) {
			case "FUND_SWAP_MODE_ON_SWAP_NO_RESET":
			case "FUND_SWAP_MODE_ON_SWAP_NO_STOP":
				return true;
			case "FUND_SWAP_MODE_NO_SWAP_ON_RESET":
			case "FUND_SWAP_MODE_NO_SWAP_ON_STOP":
			default:
				return false;
		}
	}

	/**
	 * 资金切换方式
	 *
	 * @param code    资金切换code
	 * @param fundKey 资金key
	 * @param result  上把结果
	 * @return 资金key
	 */
	public static String fundSwap(String code, String fundKey, boolean result, int fundsLen) {
		switch (code) {
			case "FUND_SWAP_MODE_ON_SWAP_NO_RESET":
				return result ? StringTool.addOne(fundKey) : "0";
			case "FUND_SWAP_MODE_NO_SWAP_ON_RESET":
				return result ? "0" : StringTool.addOne(fundKey);
			case "FUND_SWAP_MODE_ON_SWAP_NO_STOP":
				return result ? StringTool.addOne(fundKey) : fundKey;
			case "FUND_SWAP_MODE_NO_SWAP_ON_STOP":
				return result ? fundKey : StringTool.addOne(fundKey);
			case "FUND_SWAP_MODE_SWAP":
				return StringTool.addOne(fundKey);
			case "FUND_SWAP_MODE_RANDOM":
				return RandomTool.getInt(fundsLen).toString();
			default:
				return null;
		}
	}
	/**
	 * 资金切换方式
	 *
	 * @param code    资金切换code
	 * @param fundKey 资金key
	 * @param result  上把结果
	 * @return 资金key
	 */
	public static int fundSwap(String code, int fundKey, boolean result, int fundsLen) {
		switch (code) {
			case "FUND_SWAP_MODE_ON_SWAP_NO_RESET":
				//中切换不中重置
				return result ? ++fundKey : 0;
			case "FUND_SWAP_MODE_NO_SWAP_ON_RESET":
				//不中切换中重置
				return result ? 0 : ++fundKey;
			case "FUND_SWAP_MODE_ON_SWAP_NO_STOP":
				//中切换不中停留
				return result ? ++fundKey : fundKey;
			case "FUND_SWAP_MODE_NO_SWAP_ON_STOP":
				//不中切换中停留
				return result ? fundKey : ++fundKey;
			case "FUND_SWAP_MODE_SWAP":
				//不管输赢一直切换
				return ++fundKey;
			case "FUND_SWAP_MODE_RANDOM":
				//随机切换
				return RandomTool.getInt(fundsLen);
			default:
				return 0;
		}
	}

	/**
	 * 获取投注模式列表
	 *
	 * @return 投注模式列表
	 */
	public static List<Map<String, Object>> listBetMode() {
		List<Map<String, Object>> betMode = new ArrayList<>(2);
		Map<String, Object> map;
		for (IbsModeEnum mode : IbsModeEnum.values()) {
			if (StringTool.isContains(mode.name(), "BET_MODE")) {
				map = new HashMap<>(3);
				map.put("code", mode.name());
				map.put("name", mode.getMsgCn());
				map.put("sn", mode.getSn());
				betMode.add(map);
			}
		}
		return betMode;

	}

	/**
	 * 获取金额切换模式列表
	 *
	 * @return 金额切换模式列表
	 */
	public static List<Map<String, Object>> getFundSwapMode() {
		List<Map<String, Object>> fundSwapMode = new ArrayList<>(6);
		Map<String, Object> map;
		for (IbsModeEnum mode : IbsModeEnum.values()) {
			if (StringTool.isContains(mode.name(), "FUND_SWAP_MODE_")) {
				map = new HashMap<>(3);
				map.put("code", mode.name());
				map.put("name", mode.getMsgCn());
				map.put("sn", mode.getSn());
				fundSwapMode.add(map);
			}
		}
		return fundSwapMode;
	}
	/**
	 * 获取期期滚切换方式模式列表
	 *
	 * @return 期期滚切换方式模式列表
	 */
	public static List<Map<String, Object>> getPeriodRollMode() {
		List<Map<String, Object>> periodRollMode = new ArrayList<>(2);
		Map<String, Object> map;
		for (IbsModeEnum mode : IbsModeEnum.values()) {
			if (StringTool.isContains(mode.name(), "PERIOD_ROLL_MODE")) {
				map = new HashMap<>(3);
				map.put("code", mode.name());
				map.put("name", mode.getMsgCn());
				map.put("sn", mode.getSn());
				periodRollMode.add(map);
			}
		}
		return periodRollMode;
	}
}
