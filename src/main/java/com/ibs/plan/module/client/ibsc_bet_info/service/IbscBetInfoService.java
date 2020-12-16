package com.ibs.plan.module.client.ibsc_bet_info.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.client.ibsc_bet_info.entity.IbscBetInfo;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * IBSC客户端_投注信息 服务类
 *
 * @author Robot
 */
public class IbscBetInfoService extends BaseServiceProxy {

	/**
	 * 保存IBSC客户端_投注信息 对象数据
	 *
	 * @param entity IbscBetInfo对象数据
	 */
	public String save(IbscBetInfo entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsc_bet_info 的 IBSC_BET_INFO_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_bet_info set state_='DEL' where IBSC_BET_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_BET_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsc_bet_info 的 IBSC_BET_INFO_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsc_bet_info set state_='DEL' where IBSC_BET_INFO_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsc_bet_info  的 IBSC_BET_INFO_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_bet_info where IBSC_BET_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_BET_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsc_bet_info 的 IBSC_BET_INFO_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_bet_info where IBSC_BET_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscBetInfo实体信息
	 *
	 * @param entity IBSC客户端_投注信息 实体
	 */
	public void update(IbscBetInfo entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_bet_info表主键查找 IBSC客户端_投注信息 实体
	 *
	 * @param id ibsc_bet_info 主键
	 * @return IBSC客户端_投注信息 实体
	 */
	public IbscBetInfo find(String id) throws Exception {
		return dao.find(IbscBetInfo.class, id);

	}
	/**
	 * 保存会员投注信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param betIds    已存会员投注信息主键list
	 * @param period    期数
	 * @param gameCode  游戏code
	 * @param betItems  投注内容
	 * @param funds     跟投金额
	 * @param nowTime   时间
	 * @return 投注信息主键
	 */
	public String save(String existHmId, String betIds, Object period, GameUtil.Code gameCode, List<String> betItems,
			long funds, Date nowTime) throws Exception {
		IbscBetInfo betInfo = new IbscBetInfo();
		betInfo.setExistHmId(existHmId);
		betInfo.setBetIdList(betIds);
		betInfo.setPeriod(period);
		betInfo.setGameCode(gameCode.name());
		betInfo.setBetContent(String.join("#", betItems));
		betInfo.setBetFundT(funds);
		betInfo.setExecState(IbsStateEnum.PROCESS.name());
		betInfo.setCreateTime(nowTime);
		betInfo.setCreateTimeLong(System.currentTimeMillis());
		betInfo.setUpdateTime(nowTime);
		betInfo.setUpdateTimeLong(System.currentTimeMillis());
		betInfo.setState(IbsStateEnum.OPEN.name());
		return super.dao.save(betInfo);
	}

	/**
	 * 更新状态信息
	 *
	 * @param betInfoId 主键
	 * @param state     状态
	 * @param msg       描述
	 * @param nowTime   时间
	 */
	public void update(String betInfoId, IbsStateEnum state, String msg, Date nowTime) throws SQLException {
		String sql = "update ibsc_bet_info set EXEC_STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where IBSC_BET_INFO_ID_ = ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(state.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(msg);
		parameters.add(betInfoId);
		parameters.add(IbsStateEnum.OPEN.name());
		super.dao.execute(sql, parameters);

	}

	/**
	 * 获取投注信息主键
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param period    期数
	 * @param gameCode  游戏code
	 * @param state     状态
	 * @return 投注信息主键
	 */
	public List<String> findBetInfoIds(String existHmId, Object period, GameUtil.Code gameCode, String state)
			throws SQLException {
		String sql = "select IBSC_BET_INFO_ID_ from ibsc_bet_info where EXIST_HM_ID_ = ? "
				+ " and GAME_CODE_ = ? and PERIOD_ = ? and EXEC_STATE_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(existHmId);
		parameters.add(gameCode.name());
		parameters.add(period);
		parameters.add(state);
		List<String> list = super.dao.findStringList("IBSC_BET_INFO_ID_", sql, parameters);
		if (ContainerTool.isEmpty(list)) {
			return list;
		}
		updateState(list, IbsStateEnum.FINISH.name());
		return list;
	}

	/**
	 * 获取投注信息主键
	 *
	 * @param existHmId   已存在盘口会员id
	 * @param period      期数
	 * @param gameCode    游戏code
	 * @return 投注信息主键
	 */
	public List<String> findBetInfoIds(String existHmId, Object period, GameUtil.Code gameCode) throws SQLException {
		String sql=" select IBSC_BET_INFO_ID_ from ibsc_bet_info where EXIST_HM_ID_ = ? "
				+" and GAME_CODE_ = ? and PERIOD_ = ?  and (EXEC_STATE_ = ? or EXEC_STATE_ = ?)";
		List<Object> parameters = new ArrayList<>();
		parameters.add(existHmId);
		parameters.add(gameCode.name());
		parameters.add(period);
		parameters.add(IbsStateEnum.FAIL.name());
		parameters.add(IbsStateEnum.AGAIN.name());
		List<String> list= super.dao.findStringList("IBSC_BET_INFO_ID_",sql, parameters);
		if(ContainerTool.isEmpty(list)){
			return list;
		}
		updateState(list, IbsStateEnum.FINISH.name());
		return list;
	}

	/**
	 * 修改执行状态
	 *
	 * @param hmBetInfoIds 投注信息ids
	 * @param state        执行状态
	 */
	private void updateState(List<String> hmBetInfoIds, String state) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("update ibsc_bet_info set STATE_ = ? where IBSC_BET_INFO_ID_ in (");
		List<Object> parameters = new ArrayList<>();
		parameters.add(state);
		for (String hmBetInfoId : hmBetInfoIds) {
			sql.append("?,");
			parameters.add(hmBetInfoId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
	}

	/**
	 * 获取投注主键
	 *
	 * @param betInfoIds 投注信息主键
	 * @return 投注主键列表
	 */
	public List<String> findBetIds(List<String> betInfoIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select BET_ID_LIST_ from ibsc_bet_info where IBSC_BET_INFO_ID_ in (");
		List<Object> parameters = new ArrayList<>();
		for (String betInfoId : betInfoIds) {
			sql.append("?,");
			parameters.add(betInfoId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		List<String> betIdLists = super.findStringList("BET_ID_LIST_", sql, parameters);
		if (ContainerTool.isEmpty(betIdLists)) {
			return betIdLists;
		}
		List<String> betIds = new ArrayList<>();
		betIdLists.forEach(betIdList -> betIds.addAll(Arrays.asList(betIdList.split(","))));
		return betIds;
	}

}
