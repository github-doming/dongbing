package com.ibm.old.v1.common.doming.tools;
/**
 * @Description: 命令提示符
 * @Author: Dongming
 * @Date: 2019-08-02 15:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmCmdTool {

	/**
	 * 方案
	 */
	public enum Plan {
		/**
		 * 止盈止损
		 */
		PROFIT_LIMIT {
			@Override public String getName() {
				return "止盈止损";
			}
		},
		/**
		 * 投注和资金规则
		 */
		FUNDS_BET_RULE {
			@Override public String getName() {
				return "投注和资金规则";
			}
		},
		/**
		 * 投注详情
		 */
		BET_ITEM {
			@Override public String getName() {
				return "投注详情";
			}
		};

		public abstract String getName();
	}

	public enum Set {
		/**
		 * 盘口设置
		 */
		HANDICAP_SET {
			@Override public String getName() {
				return "盘口设置";
			}
		},
		/**
		 * 游戏设置
		 */
		GAME_SET {
			@Override public String getName() {
				return "游戏设置";
			}
		},
		/**
		 * 合并设置
		 */
		MERGE_SET {
			@Override public String getName() {
				return "合并设置";
			}
		},
		/**
		 * 投注模式设置
		 */
		BET_MODE_SET {
			@Override public String getName() {
				return "投注模式设置";
			}
		};
		public abstract String getName();
	}
}
