package com.common.util;

import com.common.plan.HighKillNumber;
import com.common.plan.Plan;
import com.common.plan.RandomPeriodKillNumber;
import com.common.plan.auto.RandomBet;
import com.common.plan.firstsecond.FirstAndSecondFollowOpenBet;
import com.common.plan.firstsecond.FirstAndSecondNumberBet;
import com.common.plan.follow.*;
import com.common.plan.location.*;
import com.common.plan.lost.LostMoreThanSetUp;
import com.common.plan.number.*;
import com.common.plan.rank.RankHotAndCold;
import com.common.plan.rank.RankHotAndColdToDouble;
import com.common.plan.twoside.*;
import com.common.plan.way.HighCustomMadeWay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @Description: 方案工具类
 * @Author: null
 * @Date: 2020-05-19 11:37
 * @Version: v1.0
 */
public class BasePlanUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	private static volatile BasePlanUtil instance = null;

	public BasePlanUtil() {
	}

	public static BasePlanUtil findInstance() {
		if (instance == null) {
			synchronized (BasePlanUtil.class) {
				if (instance == null) {
					BasePlanUtil planUtil = new BasePlanUtil();
					planUtil.init();
					instance = planUtil;
				}
			}
		}
		return instance;
	}

	private void init() {
	}

	public enum Code {
		/**
		 * 方案
		 */
		LOCATION_BET_NUMBER("位置投注", "IBSP_PI_LOCATION_BET_NUMBER") {
			@Override
			public Plan getPlan() {
				return LocationBetNumber.getInstance();
			}
		}, FOLLOW_TWO_SIDE("跟上期双面", "IBSP_PI_FOLLOW_TWO_SIDE") {
			@Override
			public Plan getPlan() {
				return FollowTwoSide.getInstance();
			}
		}, FOLLOW_OPEN_BET("开某投某", "IBSP_PI_FOLLOW_OPEN_BET") {
			@Override
			public Plan getPlan() {
				return FollowOpenBet.getInstance();
			}
		}, NUMBER_TO_TRACK("号码追踪", "IBSP_PI_NUMBER_TO_TRACK") {
			@Override
			public Plan getPlan() {
				return NumberToTrack.getInstance();
			}
		}, RANK_HOT_AND_COLD("冷热排名", "IBSP_PI_RANK_HOT_AND_COLD") {
			@Override
			public Plan getPlan() {
				return RankHotAndCold.getInstance();
			}
		}, RANDOM_BET("随机内容投注", "IBSP_PI_RANDOM_BET") {
			@Override
			public Plan getPlan() {
				return RandomBet.getInstance();
			}
		},NUMBER_BET("号码投注", "IBSP_PI_NUMBER_BET") {
			@Override
			public Plan getPlan() {
				return NumberBet.getInstance();
			}
		},FIX_TWO_SIDE_BET("固定双面投注", "IBSP_PI_FIX_TWO_SIDE_BET") {
			@Override
			public Plan getPlan() {
				return FixTwoSideBet.getInstance();
			}
		},NUMBER_TO_TRACK_OPEN_POSITION("号码追踪开位投位", "IBSP_PI_NUMBER_TO_TRACK_OPEN_POSITION") {
			@Override
			public Plan getPlan() {
				return NumberToTrackOpenPosition.getInstance();
			}
		},HIGH_LOCATION_ADD("高级位置加号", "IBSP_PI_HIGH_LOCATION_ADD") {
			@Override
			public Plan getPlan() {
				return HighLocationAdd.getInstance();
			}
		},TWO_SIDE_AND_WAY("双面组合路子", "IBSP_PI_TWO_SIDE_AND_WAY") {
			@Override
			public Plan getPlan() {
				return TwoSideAndWay.getInstance();
			}
		},NUMBER_TO_TRACK_CUTOVER("号码追踪切换", "IBSP_PI_NUMBER_TO_TRACK_CUTOVER") {
			@Override
			public Plan getPlan() {
				return NumberToTrackCutover.getInstance();
			}
		},LOCATION_PAIR_BET_LOCATION("位置对子投位置", "IBSP_PI_LOCATION_PAIR_BET_LOCATION") {
			@Override
			public Plan getPlan() {
				return LocationPairBetLocation.getInstance();
			}
		},UN_FOLLOW_DOUBLE_SIDE("跟反长龙", "IBSP_PI_UN_FOLLOW_DOUBLE_SIDE") {
			@Override
			public Plan getPlan() {
				return UnFollowDoubleSide.getInstance();
			}
		},LOST_MORE_THAN_SET_UP("遗漏大于设定", "IBSP_PI_LOST_MORE_THAN_SET_UP") {
			@Override
			public Plan getPlan() {
				return LostMoreThanSetUp.getInstance();
			}
		},LOCATION_LOST_CUTOVER("位置号码遗漏切换", "IBSP_PI_LOCATION_LOST_CUTOVER") {
			@Override
			public Plan getPlan() {
				return LocationLostCutover.getInstance();
			}
		},NUMBER_IN_LOCATION_LOST("号码在位置遗漏", "IBSP_PI_NUMBER_IN_LOCATION_LOST") {
			@Override
			public Plan getPlan() {
				return NumberInLocationLost.getInstance();
			}
		},DOUBLE_LOCATION_LOST_CUTOVER("双面位置遗漏切换", "IBSP_PI_DOUBLE_LOCATION_LOST_CUTOVER") {
			@Override
			public Plan getPlan() {
				return DoubleLocationLostCutover.getInstance();
			}
		},NUMBER_LOCATION_CUTOVER_BET("号码位置切换投注", "IBSP_PI_NUMBER_LOCATION_CUTOVER_BET") {
			@Override
			public Plan getPlan() {
				return NumberLocationCutoverBet.getInstance();
			}
		},LOCATION_BET_CUTOVER("位置投注切换", "IBSP_PI_LOCATION_BET_CUTOVER") {
			@Override
			public Plan getPlan() {
				return LocationBetCutover.getInstance();
			}
		},LOCATION_PAIR_FOLLOW_OPEN_BET("位置对子号码开某投某", "IBSP_PI_LOCATION_PAIR_FOLLOW_OPEN_BET") {
			@Override
			public Plan getPlan() {
				return LocationPairFollowOpenBet.getInstance();
			}
		},PAIR_NUMBER_BET_NUMBER("对子号码投号码", "IBSP_PI_PAIR_NUMBER_BET_NUMBER") {
			@Override
			public Plan getPlan() {
				return PairNumberBetNumber.getInstance();
			}
		},LOCATION_BET_NUMBER_TO_DOUBLE("位置投注一起翻倍", "IBSP_PI_LOCATION_BET_NUMBER_TO_DOUBLE") {
			@Override
			public Plan getPlan() {
				return LocationBetNumberToDouble.getInstance();
			}
		},NUMBER_BET_TO_DOUBLE("号码投注一起翻倍", "IBSP_PI_NUMBER_BET_TO_DOUBLE") {
			@Override
			public Plan getPlan() {
				return NumberBetToDouble.getInstance();
			}
		},FOLLOW_OPEN_BET_TO_DOUBLE("开某投某一起翻倍", "IBSP_PI_FOLLOW_OPEN_BET_TO_DOUBLE") {
			@Override
			public Plan getPlan() {
				return FollowOpenBetToDouble.getInstance();
			}
		},NUMBER_TO_TRACK_TO_DOUBLE("号码追踪一起翻倍", "IBSP_PI_NUMBER_TO_TRACK_TO_DOUBLE") {
			@Override
			public Plan getPlan() {
				return NumberToTrackToDouble.getInstance();
			}
		},RANK_HOT_AND_COLD_TO_DOUBLE("冷热排名一起翻倍", "IBSP_PI_RANK_HOT_AND_COLD_TO_DOUBLE") {
			@Override
			public Plan getPlan() {
				return RankHotAndColdToDouble.getInstance();
			}
		},FOLLOW_TWO_SIDE_TO_DOUBLE("跟上期双面一起翻倍", "IBSP_PI_FOLLOW_TWO_SIDE_TO_DOUBLE") {
			@Override
			public Plan getPlan() {
				return FollowTwoSideToDouble.getInstance();
			}
		},FIX_TWO_SIDE_BET_TO_DOUBLE("固定双面一起翻倍", "IBSP_PI_FIX_TWO_SIDE_BET_TO_DOUBLE") {
			@Override
			public Plan getPlan() {
				return FixTwoSideBetToDouble.getInstance();
			}
		},RANDOM_PERIOD_KILL_NUMBER("任意期数杀号", "IBSP_PI_RANDOM_PERIOD_KILL_NUMBER") {
			@Override
			public Plan getPlan() {
				return RandomPeriodKillNumber.getInstance();
			}
		},HIGH_KILL_NUMBER("高级杀号", "IBSP_PI_HIGH_KILL_NUMBER") {
			@Override
			public Plan getPlan() {
				return HighKillNumber.getInstance();
			}
		},FOLLOW_OPEN_BET_TO_NUMBER("开某投某指定位置", "IBSP_PI_FOLLOW_OPEN_BET_TO_NUMBER") {
			@Override
			public Plan getPlan() {
				return FollowOpenBetToNumber.getInstance();
			}
		},HIGH_FOLLOW_OPEN_BET("高级开某投某", "IBSP_PI_HIGH_FOLLOW_OPEN_BET") {
			@Override
			public Plan getPlan() {
				return HighFollowOpenBet.getInstance();
			}
		},FIRST_AND_SECOND_NUMBER_BET("冠亚和号码投注", "IBSP_PI_FIRST_AND_SECOND_NUMBER_BET") {
			@Override
			public Plan getPlan() {
				return FirstAndSecondNumberBet.getInstance();
			}
		},FIRST_AND_SECOND_FOLLOW_OPEN_BET("冠亚和开某投某", "IBSP_PI_FIRST_AND_SECOND_FOLLOW_OPEN_BET") {
			@Override
			public Plan getPlan() {
				return FirstAndSecondFollowOpenBet.getInstance();
			}
		},TOTAL_NUMBER_TO_NUMBER("统计号码下号码", "IBSP_PI_TOTAL_NUMBER_TO_NUMBER") {
			@Override
			public Plan getPlan() {
				return TotalNumberToNumber.getInstance();
			}
		},HIGH_CUSTOM_MADE_WAY("高级自定义路子", "IBSP_PI_HIGH_CUSTOM_MADE_WAY") {
			@Override
			public Plan getPlan() {
				return HighCustomMadeWay.getInstance();
			}
		},TRACK_FOLLOW_OPEN_BET_FIRST("号码追踪开某投某方式一", "IBSP_PI_TRACK_FOLLOW_OPEN_BET_FIRST") {
			@Override
			public Plan getPlan() {
				return TrackFollowOpenBetFirst.getInstance();
			}
		},TRACK_FOLLOW_OPEN_BET_SECOND("号码追踪开某投某方式二", "IBSP_PI_TRACK_FOLLOW_OPEN_BET_SECOND") {
			@Override
			public Plan getPlan() {
				return TrackFollowOpenBetSecond.getInstance();
			}
		},TRACK_FOLLOW_OPEN_BET("号码追踪下开某投某", "IBSP_PI_TRACK_FOLLOW_OPEN_BET") {
			@Override
			public Plan getPlan() {	return TrackFollowOpenBet.getInstance();}
		},TRACK_LOCATION_OPEN_BET("号码追踪开位投某", "IBSP_PI_TRACK_LOCATION_OPEN_BET") {
			@Override
			public Plan getPlan() {	return TrackLocationOpenBet.getInstance();}
		},TRACK_LOCATION_OPEN_BET_SIMPLE("普通号码追踪开位投某", "IBSP_PI_TRACK_LOCATION_OPEN_BET_SIMPLE") {
			@Override
			public Plan getPlan() {	return TrackLocationOpenBetSimple.getInstance();}
		},TRACK_NUMBER_HOT_AND_COLD("号码追踪冷热排名", "IBSP_PI_TRACK_NUMBER_HOT_AND_COLD") {
			@Override
			public Plan getPlan() {	return TrackNumberHotAndCold.getInstance();}
		},TRACK_LOCATION_HOT_AND_COLD("号码追踪位置冷热排名", "IBSP_PI_TRACK_LOCATION_HOT_AND_COLD") {
			@Override
			public Plan getPlan() {	return TrackLocationHotAndCold.getInstance();}
		};

		String name;
		String tableName;

		Code(String name, String tableName) {
			this.name = name;
			this.tableName = tableName;
		}

		/**
		 * 获取方案表名
		 *
		 * @return 方案表名
		 */
		public String getName() {
			return name;
		}

		/**
		 * 获取方案详情表名
		 *
		 * @return 方案详情表名
		 */
		public String getTableName() {
			return tableName;
		}

		/**
		 * 获取方案实例
		 *
		 * @return
		 */
		public abstract Plan getPlan();
	}
}
