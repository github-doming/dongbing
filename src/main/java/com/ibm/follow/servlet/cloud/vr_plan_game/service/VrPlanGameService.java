package com.ibm.follow.servlet.cloud.vr_plan_game.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_plan_game.entity.VrPlanGame;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* VR_方案游戏 服务类
 * @author Robot
 */
public class VrPlanGameService extends BaseServiceProxy {

	/**
	 * 保存VR_方案游戏 对象数据
	 * @param entity VrPlanGame对象数据
	 */
	public String save(VrPlanGame entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vr_plan_game 的 VR_PLAN_GAME_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_plan_game set state_='DEL' where VR_PLAN_GAME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_PLAN_GAME_ID_主键id数组的数据
	 * @param idArray 要删除 vr_plan_game 的 VR_PLAN_GAME_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_plan_game set state_='DEL' where VR_PLAN_GAME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vr_plan_game  的 VR_PLAN_GAME_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_plan_game where VR_PLAN_GAME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_PLAN_GAME_ID_主键id数组的数据
	 * @param idArray 要删除vr_plan_game 的 VR_PLAN_GAME_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_plan_game where VR_PLAN_GAME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrPlanGame实体信息
	 * @param entity VR_方案游戏 实体
	 */
	public void update(VrPlanGame entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_plan_game表主键查找 VR_方案游戏 实体
	 * @param id vr_plan_game 主键
	 * @return VR_方案游戏 实体
	 */
	public VrPlanGame find(String id) throws Exception {
		return dao.find(VrPlanGame.class,id);
	}

	/**
	 * 删除游戏方案
	 * @param planCode 方案编码
	 * @param gameCodes 游戏编码
	 */
	public void delPlanGame(String planCode,List<String> gameCodes) throws SQLException {
		StringBuilder sql = new StringBuilder("UPDATE `vr_plan_game` SET  `UPDATE_TIME_`=?, `UPDATE_TIME_LONG_`=?, `STATE_`=?  WHERE  `PLAN_CODE_`=? AND GAME_CODE_ in(");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(planCode);
		for (String gameCode:gameCodes){
			sql.append("?,");
			parameterList.add(gameCode);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		dao.execute(sql.toString(),parameterList);
	}

	/**
	 * 获取盘口所有游戏编码
	 * @param planCode 盘口编码
	 * @return 游戏编码集合
	 */
	public List<String> listGameCode(String planCode) throws SQLException {
		String sql = "SELECT GAME_CODE_ key_ FROM `vr_plan_game` WHERE PLAN_CODE_=? and state_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(planCode);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findStringList(sql,parameterList);
	}

	/**
	 * 查询游戏拥有的方案详情
	 * @param gameCode 游戏编码
	 * @return 方案详情
	 */
	public List<Map<String, Object>> findGamePlanItem(String gameCode,String gameType) throws SQLException {
		String sql = "SELECT GAME_CODE_,PLAN_NAME_,vi.PLAN_CODE_,SN_,PLAN_ID_,PROFIT_LIMIT_MAX_,LOSS_LIMIT_MIN_,FUNDS_LIST_,FOLLOW_PERIOD_,MONITOR_PERIOD_,BET_MODE_,FUND_SWAP_MODE_,PERIOD_ROLL_MODE_,PLAN_GROUP_DATA_,PLAN_GROUP_ACTIVE_KEY_,EXPAND_INFO_" +
				" FROM `vr_plan_game` vg LEFT JOIN vr_plan_item vi ON vg.PLAN_CODE_ = vi.PLAN_CODE_ where GAME_CODE_ = ? AND vi.GAME_TYPE_ = ? AND vg.STATE_!=? AND vi.STATE_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(gameCode);
		parameterList.add(gameType);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMapList(sql,parameterList);
	}
}
