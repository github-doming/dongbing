package com.ibm.old.v1.cloud.core.entity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
/**
 * @Description: 组装方案组数据
 * @Author: Dongming
 * @Date: 2019-07-25 10:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SpliceGroupDate {

	private static final Logger log = LogManager.getLogger(SpliceGroupDate.class);

	private JSONObject planGroupItem;
	private Object basePeriod;
	private int monitor;
	private IbmGameEnum game;
	public SpliceGroupDate(JSONObject planGroupItem, Object basePeriod, int monitor, IbmGameEnum game) {
		this.planGroupItem = planGroupItem;
		this.basePeriod = basePeriod;
		this.monitor = monitor;
		this.game = game;

	}
	/**
	 * 组装-默认压缩
	 *
	 * @param planCode 方案 code
	 * @return 方案详情数据
	 */
	public JSONObject splice(PlanTool.Code planCode) throws Exception {
		return splice(planCode, true);
	}
	/**
	 * 组装
	 *
	 * @param planCode 方案 code
	 * @param isCompress 是否压缩
	 * @return 方案详情数据
	 */
	public JSONObject splice(PlanTool.Code planCode, boolean isCompress) throws Exception {
		switch (planCode) {
			case LOCATION_BET_NUMBER:
				return locationBetNumberSgd();
			case FOLLOW_TWO_SIDE:
				return followTwoSideSgd();
			case FOLLOW_OPEN_BET:
				return followOpenBetSgd(isCompress);
			case RANK_HOT_AND_COLD:
				return rankHotAndColdSgd();
			case NUMBER_TO_TRACK:
				return numberToTrackSgd();
			default:
				throw new RuntimeException("不存在的方案名称" + planCode.getName());

		}
	}

	/**
	 * 位置投注 - 组装方案组数据
	 *
	 * @return 方案详情数据
	 */
	private JSONObject locationBetNumberSgd() {
		String select = planGroupItem.getString("select");
		String bet = planGroupItem.getString("bet");
		//算出跟进期数
		//监控期数=0，即不监控，所有方案都按规则生成投注项
		if (monitor != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = locationBetNumberValiBet(select.split(","), bet.split(","), monitor, basePeriod);
			if (!flag) {
				return null;
			}
		}
		//方案组详情
		JSONObject groupData = new JSONObject();
		groupData.put("select", select);
		groupData.put("bet", bet);
		return groupData;
	}
	/**
	 * 跟上期双面 - 组装方案组数据
	 *
	 * @return 方案详情数据
	 */
	private JSONObject followTwoSideSgd() {
		boolean state = Boolean.parseBoolean(planGroupItem.getString("state"));
		String code = planGroupItem.getString("code");

		//监控期数=0，即不监控，所有方案都按规则生成投注项
		if (monitor != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = followTwoSideValiBet(state, code, monitor, basePeriod);
			if (!flag) {
				return null;
			}
		}
		//方案组详情
		JSONObject groupData = new JSONObject();
		groupData.put("state", planGroupItem.getString("state"));
		groupData.put("code", code);
		return groupData;
	}

	/**
	 * 开某投某 - 组装方案组数据
	 * @param isCompress 是否压缩
	 * @return 方案详情数据
	 * @throws Exception
	 */
	private JSONObject followOpenBetSgd(boolean isCompress) throws Exception {
		JSONArray selects = planGroupItem.getJSONArray("selects");
		String activeKey = planGroupItem.getString("activeKey");
		//监控期数=0，即不监控，所有方案都按规则生成投注项
		if (monitor != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = followOpenBetValiBet(selects, activeKey, monitor, basePeriod);
			if (!flag) {
				return null;
			}
		}
		String[] baseData = GameTool.getHistoryData(basePeriod, game);
		if(ContainerTool.isEmpty(baseData)){
			return null;
		}
		String open = baseData[Integer.parseInt(activeKey)];

		//方案组详情
		JSONObject groupData = new JSONObject();
		if (isCompress) {
			for (Object data : selects) {
				JSONObject item = (JSONObject) data;
				String select = item.getString("select");
				if (ContainerTool.isEmpty(select.split(",")) || select.split(",").length >= 2) {
					return null;
				}
				if (open.equals(select)) {
					if (groupData.containsKey("rank")) {
						groupData.put("bet", groupData.getString("bet").concat(",").concat(item.getString("bet")));
					} else {
						groupData.put("rank", activeKey);
						groupData.put("bet", item.getString("bet"));
					}
				}
			}
		} else {
			groupData.put("selects", selects);
			groupData.put("activeKey", activeKey);
		}
		return groupData;
	}

	/**
	 * 冷热排名 - 组装方案组数据
	 *
	 * @return 方案详情数据
	 */
	private JSONObject rankHotAndColdSgd() {
		String bet = planGroupItem.getString("bet");
		String rank = planGroupItem.getString("rank");
		//方案组详情
		JSONObject groupData = new JSONObject();
		groupData.put("bet", bet);
		groupData.put("rank", rank);
		return groupData;
	}

	private JSONObject numberToTrackSgd() {
		String track = planGroupItem.getString("track");
		String bet = planGroupItem.getString("bet");

		//监控期数=0，即不监控，所有方案都按规则生成投注项
		if (monitor != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = numberToTrackValiBet(track, bet.split(","), monitor, basePeriod);
			if (!flag) {
				return null;
			}
		}

		//方案组详情
		JSONObject groupData = new JSONObject();
		groupData.put("track", track);
		groupData.put("bet", bet);

		return groupData;
	}

	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功-位置投注
	 *
	 * @param selects    选择位置
	 * @param bets       投注位置
	 * @param monitor    监控
	 * @param basePeriod 基准期数
	 * @return 开启方案组验证通过 true
	 */
	private boolean locationBetNumberValiBet(String[] selects, String[] bets, int monitor, Object basePeriod) {
		try {
			//历史数据
			String[] baseData = GameTool.getHistoryData(basePeriod, game);
			if (ContainerTool.isEmpty(baseData)) {
				log.info("获取基准数据失败，基准期数为：" + basePeriod);
				return false;
			}
			//监控期数计算
			for (int i = 1; i <= monitor; i++) {
				Object monitorPeriod = PeriodTool.findBeforePeriod(game, basePeriod, i);
				String[] valiData = GameTool.getHistoryData(monitorPeriod, game);
				if (ContainerTool.isEmpty(valiData)) {
					log.info("获取验证数据失败，监控期数为：" + (monitorPeriod));
					return false;
				}
				//监控期数内，匹配成功失败一次，就返回
				if (GameTool.matchNum(baseData, valiData, selects, bets)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}
	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功 - 跟上期双面
	 *
	 * @param state      操作状态
	 * @param code       操作类型
	 * @param monitor    监控期数
	 * @param basePeriod 基准期数
	 * @return 开启方案组验证通过 true
	 */
	private boolean followTwoSideValiBet(boolean state, String code, int monitor, Object basePeriod) {
		try {
			//历史数据
			String[] baseData = GameTool.getHistoryData(basePeriod, game);
			if (ContainerTool.isEmpty(baseData)) {
				log.info("获取基准数据失败，基准期数为：" + basePeriod);
				return false;
			}
			//监控期数计算
			for (int i = 1; i <= monitor; i++) {
				Object monitorPeriod = PeriodTool.findBeforePeriod(game, basePeriod, i);
				String[] valiData = GameTool.getHistoryData(monitorPeriod, game);
				if (ContainerTool.isEmpty(valiData)) {
					log.info("获取验证数据失败，监控期数为：" + (monitorPeriod));
					return false;
				}
				try {
					//监控期数内，匹配失败一次，就返回
					if (GameTool.matchNum(baseData, valiData, state, code, 5)) {
						return false;
					}
				} catch (Exception e) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}

	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功-开某投某
	 *
	 * @param selects    方案组详情
	 * @param activeKey  名次
	 * @param monitor    监控期数
	 * @param basePeriod 基准期数
	 * @return 开启方案组验证通过 true
	 */
	private boolean followOpenBetValiBet(JSONArray selects, String activeKey, int monitor, Object basePeriod) {
		try {
			//历史数据
			String[] baseData = GameTool.getHistoryData(basePeriod, game);
			if (ContainerTool.isEmpty(baseData)) {
				log.info("获取基准数据失败，基准期数为：" + basePeriod);
				return false;
			}
			String open = baseData[Integer.parseInt(activeKey)];
			//监控期数计算
			for (int i = 1; i <= monitor; i++) {
				Object monitorPeriod = PeriodTool.findBeforePeriod(game, basePeriod, i);
				String[] valiData = GameTool.getHistoryData(monitorPeriod, game);
				if (ContainerTool.isEmpty(valiData)) {
					log.info("获取验证数据失败，监控期数为：" + (monitorPeriod));
					return false;
				}
				String vali = valiData[Integer.parseInt(activeKey)];
				//监控期数内，匹配失败一次，就返回
				if (GameTool.matchNum(open, vali, selects)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}

	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配 不中
	 *
	 * @param track      追踪号码
	 * @param bets       投注号码
	 * @param monitor    监控期数
	 * @param basePeriod 基准期数
	 * @return 开启方案组验证通过 true
	 */
	private boolean numberToTrackValiBet(String track, String[] bets, int monitor, Object basePeriod) {
		try {
			//历史数据
			String[] baseData = GameTool.getHistoryData(basePeriod, game);
			if (ContainerTool.isEmpty(baseData)) {
				log.info("获取基准数据失败，基准期数为：" + basePeriod);
				return false;
			}
			//监控期数计算
			for (int i = 1; i <= monitor; i++) {
				Object monitorPeriod = PeriodTool.findBeforePeriod(game, basePeriod, i);
				String[] valiData = GameTool.getHistoryData(monitorPeriod, game);
				if (ContainerTool.isEmpty(valiData)) {
					log.info("获取验证数据失败，验证期数为：" + (monitorPeriod));
					return false;
				}
				//监控期数内，匹配成功一次，就返回，中，不投注
				if (GameTool.matchNum(baseData, valiData, track, bets)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}
}
