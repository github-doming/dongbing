package com.ibm.old.v1.cloud.ibm_plan_item.t.service;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool.Code;
import com.ibm.old.v1.cloud.ibm_pi_follow_open_bet.t.service.IbmPiFollowOpenBetTService;
import com.ibm.old.v1.cloud.ibm_pi_follow_two_side.t.service.IbmPiFollowTwoSideTService;
import com.ibm.old.v1.cloud.ibm_pi_location_bet_number.t.service.IbmPiLocationBetNumberTService;
import com.ibm.old.v1.cloud.ibm_pi_number_to_track.t.service.IbmPiNumberToTrackTService;
import com.ibm.old.v1.cloud.ibm_pi_rank_hot_and_cold.t.service.IbmPiRankHotAndColdTService;
import com.ibm.old.v1.cloud.ibm_plan_item.t.entity.IbmPlanItemMain;
import com.ibm.old.v1.cloud.ibm_plan_item.t.entity.IbmPlanItemT;
import net.sf.json.JSONObject;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * @Description: 方案详情服务类
 * @Author: Dongming
 * @Date: 2019-01-15 14:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmPlanItemService {

	/**
	 * 获取显示页面
	 *
	 * @param planItemInfo 方案详情信息
	 * @return 显示页面 回显信息
	 */
	public Map<String, Object> findShow(Map<String, Object> planItemInfo) throws SQLException {
		Code planCode = Code.valueOf(planItemInfo.get("Code").toString());
		String id = planItemInfo.get("ID_").toString();

		Map<String, Object> showData;
		switch (planCode) {
			case LOCATION_BET_NUMBER:
				showData = new IbmPiLocationBetNumberTService().findShow(id);
				break;
			case FOLLOW_TWO_SIDE:
				showData = new IbmPiFollowTwoSideTService().findShow(id);
				break;
			case FOLLOW_OPEN_BET:
				showData = new IbmPiFollowOpenBetTService().findShow(id);
				break;
			case NUMBER_TO_TRACK:
				showData = new IbmPiNumberToTrackTService().findShow(id);
				break;
			case RANK_HOT_AND_COLD:
				showData = new IbmPiRankHotAndColdTService().findShow(id);
				break;
			default:
				throw new RuntimeException("不存在的盘口详情表名称" + planCode.getName());
		}
		showData.put("PLAN_GROUP_DATA_", JSONObject.fromObject(showData.get("PLAN_GROUP_DATA_")));
		showData.put("PROFIT_LIMIT_MAX_", NumberTool.doubleT(showData.get("PROFIT_LIMIT_MAX_T_")));
		showData.put("LOSS_LIMIT_MIN_", NumberTool.doubleT(showData.get("LOSS_LIMIT_MIN_T_")));
		showData.remove("PROFIT_LIMIT_MAX_T_");
		showData.remove("LOSS_LIMIT_MIN_T_");
		return showData;

	}
	/**
	 * 更新数据
	 *
	 * @param planItemMain 主要盘口详情信息
	 * @return 更新结果
	 */
	public boolean update(IbmPlanItemMain planItemMain) throws Exception {
		planItemMain.setPlanGroupActiveKey(PlanTool.extractState(planItemMain.getPlanGroupData()));
		switch (planItemMain.gePlanCode()) {
			case LOCATION_BET_NUMBER:
				return new IbmPiLocationBetNumberTService().update(planItemMain);
			case FOLLOW_TWO_SIDE:
				return new IbmPiFollowTwoSideTService().update(planItemMain);
			case FOLLOW_OPEN_BET:
				return new IbmPiFollowOpenBetTService().update(planItemMain);
			case NUMBER_TO_TRACK:
				return new IbmPiNumberToTrackTService().update(planItemMain);
			case RANK_HOT_AND_COLD:
				return new IbmPiRankHotAndColdTService().update(planItemMain);
			default:
				throw new RuntimeException("不存在的盘口详情表名称" + planItemMain.gePlanCode().getName());
		}
	}
	/**
	 * 更新状态
	 *
	 * @param planItemInfo 方案详情信息
	 * @param state        状态
	 */
	public void updateState(Map<String, Object> planItemInfo, String state) throws SQLException {
		Code planCode = Code.valueOf(planItemInfo.get("CODE_").toString());
		String id = planItemInfo.get("ID_").toString();
		switch (planCode) {
			case LOCATION_BET_NUMBER:
				new IbmPiLocationBetNumberTService().updateState(id, state,this.getClass().getName());
				break;
			case FOLLOW_TWO_SIDE:
				new IbmPiFollowTwoSideTService().updateState(id, state,this.getClass().getName());
				break;
			case FOLLOW_OPEN_BET:
				new IbmPiFollowOpenBetTService().updateState(id, state,this.getClass().getName());
				break;
			case NUMBER_TO_TRACK:
				new IbmPiNumberToTrackTService().updateState(id, state,this.getClass().getName());
				break;
			case RANK_HOT_AND_COLD:
				new IbmPiRankHotAndColdTService().updateState(id, state,this.getClass().getName());
				break;
			default:
				throw new RuntimeException("不存在的方案名称" + planCode.getName());
		}

	}
	/**
	 * 更新选中方案状态
	 *
	 * @param planItemInfos 方案详情信息集合
	 * @param state         状态
	 */
	public void updateAllState(List<Map<String, Object>> planItemInfos, String state) throws SQLException {
		for (Map<String, Object> planItemInfo : planItemInfos) {
			updateState(planItemInfo, state);
		}
	}

	/**
	 * 初始化方案详情
	 *
	 * @param planCode 方案code
	 * @param userId   用户id
	 * @return 方案详情
	 */
	public IbmPlanItemT initPlanItem(Code planCode, String userId) throws Exception {
		switch (planCode) {
			case LOCATION_BET_NUMBER:
				return new IbmPiLocationBetNumberTService().copyDef(userId);
			case FOLLOW_TWO_SIDE:
				return new IbmPiFollowTwoSideTService().copyDef(userId);
			case FOLLOW_OPEN_BET:
				return new IbmPiFollowOpenBetTService().copyDef(userId);
			case NUMBER_TO_TRACK:
				return new IbmPiNumberToTrackTService().copyDef(userId);
			case RANK_HOT_AND_COLD:
				return new IbmPiRankHotAndColdTService().copyDef(userId);
			default:
				throw new RuntimeException("不存在的方案名称" + planCode.getName());
		}
	}
	/**
	 * 获取方案详情信息
	 *
	 * @param planCode        方案编码
	 * @param planItemTableId 方案详情id
	 * @return 方案详情信息
	 */
	public IbmPlanItemMain findPlanItem(Code planCode, String planItemTableId) throws Exception {
		Map<String, Object> planItemInfo;
		switch (planCode) {
			case LOCATION_BET_NUMBER:
				planItemInfo = new IbmPiLocationBetNumberTService().findPlanItem(planItemTableId);
				break;
			case FOLLOW_TWO_SIDE:
				planItemInfo = new IbmPiFollowTwoSideTService().findPlanItem(planItemTableId);
				break;
			case FOLLOW_OPEN_BET:
				planItemInfo = new IbmPiFollowOpenBetTService().findPlanItem(planItemTableId);
				break;
			case NUMBER_TO_TRACK:
				planItemInfo = new IbmPiNumberToTrackTService().findPlanItem(planItemTableId);
				break;
			case RANK_HOT_AND_COLD:
				planItemInfo = new IbmPiRankHotAndColdTService().findPlanItem(planItemTableId);
				break;
			default:
				throw new RuntimeException("不存在的方案名称" + planCode.getName());
		}
		IbmPlanItemMain planItemMain = new IbmPlanItemMain(planItemInfo);
		planItemMain.setId(planItemTableId);
		planItemMain.setName(planCode.getTableName());
		return planItemMain;
	}

	/**
	 * 根据id列表获取已开启基础信息列表
	 *
	 * @param itemIds 详情id列表
	 * @return 已开启基础信息列表
	 */
	public List<Map<String, Object>> listInfo(Code planCode, Set<String> itemIds) throws SQLException {
		switch (planCode) {
			case LOCATION_BET_NUMBER:
				return new IbmPiLocationBetNumberTService().listInfo(itemIds);
			case FOLLOW_TWO_SIDE:
				return new IbmPiFollowTwoSideTService().listInfo(itemIds);
			case FOLLOW_OPEN_BET:
				return new IbmPiFollowOpenBetTService().listInfo(itemIds);
			case NUMBER_TO_TRACK:
				return new IbmPiNumberToTrackTService().listInfo(itemIds);
			case RANK_HOT_AND_COLD:
				return new IbmPiRankHotAndColdTService().listInfo(itemIds);
			default:
				throw new RuntimeException("不存在的方案名称" + planCode.getName());

		}
	}

	/**
	 * 获取资金方案信息
	 *
	 * @param itemIds 位置投注_号码主键数组
	 * @return key-主键	value-资金方案
	 */
	public Map<Object, Object> mapFundsList(Code planCode, Set<String> itemIds) throws SQLException {
		switch (planCode) {
			case LOCATION_BET_NUMBER:
				return new IbmPiLocationBetNumberTService().mapFundsList(itemIds);
			case FOLLOW_TWO_SIDE:
				return new IbmPiFollowTwoSideTService().mapFundsList(itemIds);
			case FOLLOW_OPEN_BET:
				return new IbmPiFollowOpenBetTService().mapFundsList(itemIds);
			case NUMBER_TO_TRACK:
				return new IbmPiNumberToTrackTService().mapFundsList(itemIds);
			case RANK_HOT_AND_COLD:
				return new IbmPiRankHotAndColdTService().mapFundsList(itemIds);
			default:
				throw new RuntimeException("不存在的方案名称" + planCode.getName());

		}
	}
}