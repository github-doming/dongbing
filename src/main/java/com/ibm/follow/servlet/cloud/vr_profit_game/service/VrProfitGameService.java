package com.ibm.follow.servlet.cloud.vr_profit_game.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_profit_game.entity.VrProfitGame;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* VR_游戏盈亏 服务类
 * @author Robot
 */
public class VrProfitGameService extends BaseServiceProxy {

	/**
	 * 保存VR_游戏盈亏 对象数据
	 * @param entity VrProfitGame对象数据
	 */
	public String save(VrProfitGame entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vr_profit_game 的 VR_PROFIT_GAME_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_profit_game set state_='DEL' where VR_PROFIT_GAME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_PROFIT_GAME_ID_主键id数组的数据
	 * @param idArray 要删除 vr_profit_game 的 VR_PROFIT_GAME_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_profit_game set state_='DEL' where VR_PROFIT_GAME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vr_profit_game  的 VR_PROFIT_GAME_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_profit_game where VR_PROFIT_GAME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_PROFIT_GAME_ID_主键id数组的数据
	 * @param idArray 要删除vr_profit_game 的 VR_PROFIT_GAME_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_profit_game where VR_PROFIT_GAME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrProfitGame实体信息
	 * @param entity VR_游戏盈亏 实体
	 */
	public void update(VrProfitGame entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_profit_game表主键查找 VR_游戏盈亏 实体
	 * @param id vr_profit_game 主键
	 * @return VR_游戏盈亏 实体
	 */
	public VrProfitGame find(String id) throws Exception {
		return dao.find(VrProfitGame.class,id);
	}

	/**
	 * 获取游戏盈利信息
	 * @param vrMemberId		虚拟会员id
	 * @param startTime		开始时间
	 * @param endTime			结束时间
	 * @return
	 */
	public List<Map<String, Object>> getProfitInfo(String vrMemberId, Date startTime, Date endTime) throws SQLException {
		String sql="select GAME_NAME_,GAME_CODE_,BET_LEN_,PROFIT_MAX_T_,LOSS_MAX_T_,WIN_RATE_T_,PROFIT_T_ from vr_profit_game"
				+ " where VR_MEMBER_ID_=? and CREATE_TIME_LONG_>? and CREATE_TIME_LONG_<? and STATE_=?  LIMIT 0,5";
		List<Object> parameters=new ArrayList<>();
		parameters.add(vrMemberId);
		parameters.add(startTime.getTime());
		parameters.add(endTime.getTime());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findMapList(sql,parameters);
	}

	/**
	 * 获取2周内游戏盈亏信息
	 * @param vrMemberId		虚拟会员id
	 * @param gameCode		游戏编码
	 * @param startTime		两周前时间
	 * @param endTime			结束时间
	 * @return
	 */
	public List<Map<String, Object>> getGameProfitInfo(String vrMemberId, String gameCode, Date startTime, Date endTime) throws SQLException {
		String sql="select CREATE_TIME_LONG_,BET_LEN_,PROFIT_MAX_T_,LOSS_MAX_T_,WIN_RATE_T_,PROFIT_T_ from vr_profit_game"
				+ " where VR_MEMBER_ID_=? and GAME_CODE_=? and CREATE_TIME_LONG_>? and CREATE_TIME_LONG_<? and STATE_=? order by CREATE_TIME_LONG_ desc";
		List<Object> parameters=new ArrayList<>();
		parameters.add(vrMemberId);
		parameters.add(gameCode);
		parameters.add(startTime.getTime());
		parameters.add(endTime.getTime());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findMapList(sql,parameters);
	}
}
