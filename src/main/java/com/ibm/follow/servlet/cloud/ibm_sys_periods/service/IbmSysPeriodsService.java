package com.ibm.follow.servlet.cloud.ibm_sys_periods.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_sys_periods.entity.IbmSysPeriods;
import org.apache.commons.collections.map.HashedMap;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * IBM_期数管理 服务类
 *
 * @author Robot
 */
public class IbmSysPeriodsService extends BaseServicePlus {

	/**
	 * 保存IBM_期数管理 对象数据
	 *
	 * @param entity IbmSysPeriods对象数据
	 */
	public String save(IbmSysPeriods entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_sys_periods 的 IBM_SYS_PERIODS_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_periods set state_='DEL' where IBM_SYS_PERIODS_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_SYS_PERIODS_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_sys_periods 的 IBM_SYS_PERIODS_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_sys_periods set state_='DEL' where IBM_SYS_PERIODS_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_sys_periods  的 IBM_SYS_PERIODS_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_periods where IBM_SYS_PERIODS_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_PERIODS_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_sys_periods 的 IBM_SYS_PERIODS_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_sys_periods where IBM_SYS_PERIODS_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysPeriods实体信息
	 *
	 * @param entity IBM_期数管理 实体
	 */
	public void update(IbmSysPeriods entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_periods表主键查找 IBM_期数管理 实体
	 *
	 * @param id ibm_sys_periods 主键
	 * @return IBM_期数管理 实体
	 */
	public IbmSysPeriods find(String id) throws Exception {
		return (IbmSysPeriods) this.dao.find(IbmSysPeriods.class, id);

	}

	/**
	 * 获取期数管理中的所有数据，根据游戏分类
	 *
	 * @return 期数管理数据
	 */
	public Map<GameUtil.Code, List<Map<String, Object>>> mapPauseAll() throws SQLException {
		String sql = "SELECT GAME_CODE_,PAUSE_START_TIME_LONG_,PAUSE_END_TIME_LONG_ FROM `ibm_sys_periods` "
				+ " where STATE_ != ? ORDER BY PAUSE_START_TIME_LONG_";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(IbmStateEnum.DEL.name());
		//获取期数管理中的所有数据
		List<Map<String, Object>> periodsList = super.findMapList(sql, parameters);
		//根据游戏分类
		Map<GameUtil.Code, List<Map<String, Object>>> pauseData = new HashedMap(GameUtil.codes().length);
		if (ContainerTool.isEmpty(periodsList)) {
			return pauseData;
		}
		for (Map<String, Object> periodsInfo : periodsList) {
			GameUtil.Code gameCode = GameUtil.Code.valueOf(periodsInfo.get("GAME_CODE_").toString());
			if (pauseData.containsKey(gameCode)) {
				pauseData.get(gameCode).add(periodsInfo);
			} else {
				List<Map<String, Object>> list = new ArrayList<>();
				list.add(periodsInfo);
				pauseData.put(gameCode, list);
			}
		}
		return pauseData;
	}
	/**
	 * 获取游戏的暂停时间列表
	 * @param gameCode 游戏
	 * @return 暂停时间列表
	 */
	public List<Map<String, Object>> listPause(GameUtil.Code gameCode) throws SQLException {
		String sql = "SELECT GAME_CODE_,PAUSE_START_TIME_LONG_,PAUSE_END_TIME_LONG_ FROM ibm_sys_periods "
				+ " where GAME_CODE_ = ? and STATE_ != ? ORDER BY PAUSE_START_TIME_LONG_";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(gameCode.name());
		parameters.add(IbmStateEnum.DEL.name());
		return super.findMapList(sql,parameters);
	}
}
