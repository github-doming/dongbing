package com.ibs.plan.module.client.ibsc_bet_fail.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.client.ibsc_bet_fail.entity.IbscBetFail;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBSC客户端_投注失败 服务类
 *
 * @author Robot
 */
public class IbscBetFailService extends BaseServiceProxy {

	/**
	 * 保存IBSC客户端_投注失败 对象数据
	 *
	 * @param entity IbscBetFail对象数据
	 */
	public String save(IbscBetFail entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsc_bet_fail 的 IBSC_BET_FAIL_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_bet_fail set state_='DEL' where IBSC_BET_FAIL_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_BET_FAIL_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsc_bet_fail 的 IBSC_BET_FAIL_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsc_bet_fail set state_='DEL' where IBSC_BET_FAIL_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsc_bet_fail  的 IBSC_BET_FAIL_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_bet_fail where IBSC_BET_FAIL_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_BET_FAIL_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsc_bet_fail 的 IBSC_BET_FAIL_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_bet_fail where IBSC_BET_FAIL_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscBetFail实体信息
	 *
	 * @param entity IBSC客户端_投注失败 实体
	 */
	public void update(IbscBetFail entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_bet_fail表主键查找 IBSC客户端_投注失败 实体
	 *
	 * @param id ibsc_bet_fail 主键
	 * @return IBSC客户端_投注失败 实体
	 */
	public IbscBetFail find(String id) throws Exception {
		return dao.find(IbscBetFail.class, id);

	}
	/**
	 * 保存错误投注信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param betInfoId 投注信息主键
	 * @param gameCode  游戏code
	 * @param period    期数
	 * @param betInfo   投注信息
	 * @param msg       错误信息
	 */
	public void save(String existHmId, String betInfoId, GameUtil.Code gameCode, Object period, String betInfo,
					 String betType,String msg, Date nowTime) throws Exception {
		IbscBetFail hmBetFail = new IbscBetFail();
		hmBetFail.setExistHmId(existHmId);
		hmBetFail.setBetInfoId(betInfoId);
		hmBetFail.setGameCode(gameCode.name());
		hmBetFail.setPeriod(period);
		hmBetFail.setBetType(betType);
		hmBetFail.setFailBetContent(betInfo);
		hmBetFail.setCreateTime(nowTime);
		hmBetFail.setCreateTimeLong(System.currentTimeMillis());
		hmBetFail.setUpdateTime(nowTime);
		hmBetFail.setUpdateTimeLong(System.currentTimeMillis());
		hmBetFail.setState(IbsStateEnum.OPEN.name());
		hmBetFail.setDesc(msg);
		save(hmBetFail);
	}

	/**
	 * 获取补投信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param period    期数
	 * @param gameCode  游戏编码
	 * @return 补投信息
	 */
	public List<Map<String, Object>> listAgainBetInfo(String existHmId, Object period, GameUtil.Code gameCode)
			throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select IBSC_BET_FAIL_ID_,BET_INFO_ID_ as betInfoId,FAIL_BET_CONTENT_ as betInfo,BET_TYPE_ as betType")
				.append(" from ibsc_bet_fail where EXIST_HM_ID_ = ? and GAME_CODE_ = ? and PERIOD_ = ? and STATE_ = ?");
		List<Object> parameters = new ArrayList<>();
		parameters.add(existHmId);
		parameters.add(gameCode.name());
		parameters.add(period);
		parameters.add(IbsStateEnum.OPEN.name());
		List<Map<String, Object>> list = super.dao.findMapList(sql.toString(), parameters);
		if (ContainerTool.isEmpty(list)) {
			return list;
		}
		sql.delete(0, sql.length());
		parameters.clear();
		sql.append(
				"update ibsc_bet_fail set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where IBSC_BET_FAIL_ID_ in(");
		parameters.add(IbsStateEnum.CLOSE.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add("复投");
		for (Map<String, Object> map : list) {
			map.remove("ROW_NUM");
			sql.append("?,");
			parameters.add(map.remove("IBSC_HM_BET_FAIL_ID_"));
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
		return list;
	}
}
