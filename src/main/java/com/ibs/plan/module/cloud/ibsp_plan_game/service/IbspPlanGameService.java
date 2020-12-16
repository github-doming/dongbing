package com.ibs.plan.module.cloud.ibsp_plan_game.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_plan_game.entity.IbspPlanGame;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSP_方案游戏 服务类
 *
 * @author Robot
 */
public class IbspPlanGameService extends BaseServiceProxy {

	/**
	 * 保存IBSP_方案游戏 对象数据
	 *
	 * @param entity IbspPlanGame对象数据
	 */
	public String save(IbspPlanGame entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_plan_game 的 IBSP_PLAN_GAME_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_plan_game set state_='DEL' where IBSP_PLAN_GAME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_PLAN_GAME_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_plan_game 的 IBSP_PLAN_GAME_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_plan_game set state_='DEL' where IBSP_PLAN_GAME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_plan_game  的 IBSP_PLAN_GAME_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_plan_game where IBSP_PLAN_GAME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_PLAN_GAME_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_plan_game 的 IBSP_PLAN_GAME_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_plan_game where IBSP_PLAN_GAME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspPlanGame实体信息
	 *
	 * @param entity IBSP_方案游戏 实体
	 */
	public void update(IbspPlanGame entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_plan_game表主键查找 IBSP_方案游戏 实体
	 *
	 * @param id ibsp_plan_game 主键
	 * @return IBSP_方案游戏 实体
	 */
	public IbspPlanGame find(String id) throws Exception {
		return dao.find(IbspPlanGame.class, id);
	}

	/**
	 * 获取方案游戏信息
	 *
	 * @param planCodes 方案codes
	 * @return
	 */
	public Map<String, List<Object>> listInfoByCodes(Set<String> planCodes) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT PLAN_CODE_,GAME_ID_ FROM ibsp_plan_game WHERE STATE_ =? AND PLAN_CODE_ IN (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String planCode : planCodes) {
			sql.append("?,");
			parameterList.add(planCode);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		List<Map<String, Object>> list = super.dao.findMapList(sql.toString(), parameterList);
		if (ContainerTool.isEmpty(list)) {
			return new HashMap<>(1);
		}
		Map<String, List<Object>> planGameInfos = new HashMap<>(10);

		for (Map<String, Object> map : list) {
			String planCode = map.remove("PLAN_CODE_").toString();
			if (planGameInfos.containsKey(planCode)) {
				planGameInfos.get(planCode).add(map.get("GAME_ID_"));
			} else {
				List<Object> gameIds = new ArrayList<>();
				gameIds.add(map.get("GAME_ID_"));
				planGameInfos.put(planCode, gameIds);
			}
		}
		return planGameInfos;
	}

	/**
	 * 获取方案游戏信息
	 *
	 * @param availablePlanCodes 可用方案编码
	 * @param gameId             游戏id
	 * @return
	 */
	public List<Map<String, Object>> findPlanGameInfo(Set<Object> availablePlanCodes, String gameId) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT PLAN_NAME_,PLAN_CODE_,SN_ FROM ibsp_plan_game WHERE GAME_ID_=? and STATE_=? AND PLAN_CODE_ in(");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		for (Object planCode : availablePlanCodes) {
			sql.append("?,");
			parameterList.add(planCode);
		}
		sql.replace(sql.length() - 1, sql.length(), ") ORDER BY SN_");
		return super.dao.findMapList(sql.toString(), parameterList);
	}

	/**
	 * 获取游戏方案信息
	 *
	 * @param planCode 方案编码
	 * @param gameId   游戏id
	 * @return
	 */
	public Map<String, Object> findPlanGame(String planCode, String gameId) throws SQLException {
		String sql = "SELECT PLAN_ID_,PLAN_NAME_ FROM ibsp_plan_game "
				+ " WHERE GAME_ID_=? AND PLAN_CODE_=? AND STATE_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(gameId);
		parameterList.add(planCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取分页信息
	 *
	 * @param gameId    游戏Id
	 * @param pageIndex 页数
	 * @param pageSize  条数
	 * @return 游戏方案列表
	 */
	public PageCoreBean<Map<String, Object>> listShow(String gameId, Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount = "select count(*) from ibsp_plan_game where  GAME_ID_=? AND STATE_!=? ";
		String sql = "SELECT ipg.IBSP_PLAN_GAME_ID_ PLAN_GAME_ID_,ipg.PLAN_NAME_,ipg.PLAN_CODE_,ipg.SN_,ipg.STATE_,ip.PLAN_TYPE_"
				+ " from ibsp_plan_game ipg LEFT JOIN ibsp_plan ip on ip.IBSP_PLAN_ID_=ipg.PLAN_ID_ where ipg.GAME_ID_=? AND ipg.STATE_!=?  order by SN_";
		ArrayList<Object> parameterList = new ArrayList<>(2);

		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.DEL.name());
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}

	/**
	 * 获取游戏方案信息
	 *
	 * @param planGameId 方案游戏Id
	 * @return 游戏方案信息
	 */
	public Map<String, Object> findPlanGameItem(String planGameId) throws SQLException {
		String sql = "SELECT ipg.IBSP_PLAN_GAME_ID_ PLAN_GAME_ID_,ipg.PLAN_NAME_ GAME_PLAN_NAME_,ipg.PLAN_CODE_,ipg.SN_,ipg.STATE_,ip.PLAN_NAME_ "
				+ " FROM ibsp_plan_game ipg LEFT JOIN ibsp_plan ip on ip.IBSP_PLAN_ID_=ipg.PLAN_ID_ "
				+ " WHERE ipg.IBSP_PLAN_GAME_ID_=?  AND ipg.STATE_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(planGameId);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取游戏方案信息
	 *
	 * @param gameId 游戏Id
	 * @return 游戏方案信息
	 */
	public List<String> findPlanCodeByGameId(String gameId) throws SQLException {
		String sql = "SELECT PLAN_CODE_ FROM ibsp_plan_game  WHERE  GAME_ID_=?  AND  STATE_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findStringList("PLAN_CODE_",sql,parameterList);
	}
}
