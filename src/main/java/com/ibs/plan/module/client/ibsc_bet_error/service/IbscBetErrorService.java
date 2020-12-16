package com.ibs.plan.module.client.ibsc_bet_error.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.client.ibsc_bet_error.entity.IbscBetError;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBSC客户端_投注异常 服务类
 *
 * @author Robot
 */
public class IbscBetErrorService extends BaseServiceProxy {

	/**
	 * 保存IBSC客户端_投注异常 对象数据
	 *
	 * @param entity IbscBetError对象数据
	 */
	public String save(IbscBetError entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsc_bet_error 的 IBSC_BET_ERROR_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_bet_error set state_='DEL' where IBSC_BET_ERROR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_BET_ERROR_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsc_bet_error 的 IBSC_BET_ERROR_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsc_bet_error set state_='DEL' where IBSC_BET_ERROR_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsc_bet_error  的 IBSC_BET_ERROR_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_bet_error where IBSC_BET_ERROR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_BET_ERROR_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsc_bet_error 的 IBSC_BET_ERROR_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_bet_error where IBSC_BET_ERROR_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscBetError实体信息
	 *
	 * @param entity IBSC客户端_投注异常 实体
	 */
	public void update(IbscBetError entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_bet_error表主键查找 IBSC客户端_投注异常 实体
	 *
	 * @param id ibsc_bet_error 主键
	 * @return IBSC客户端_投注异常 实体
	 */
	public IbscBetError find(String id) throws Exception {
		return dao.find(IbscBetError.class, id);

	}
	/**
	 * 保存投注异常信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param betInfoId 投注信息主键
	 * @param gameCode  游戏code
	 * @param period    期数
	 * @param betInfo   投注内容
	 * @param msg       错误投注信息
	 * @param nowTime   时间
	 * @return 异常信息主键
	 */
	public String save(String existHmId, String betInfoId, GameUtil.Code gameCode, Object period, String betInfo,
			String msg, Date nowTime) throws Exception {
		IbscBetError hmBetError = new IbscBetError();
		hmBetError.setExistHmId(existHmId);
		hmBetError.setBetInfoId(betInfoId);
		hmBetError.setGameCode(gameCode.name());
		hmBetError.setPeriod(period);
		hmBetError.setFailBetContent(betInfo);
		hmBetError.setErrorBetInfo(msg);
		hmBetError.setCreateTime(nowTime);
		hmBetError.setCreateTimeLong(System.currentTimeMillis());
		hmBetError.setUpdateTime(nowTime);
		hmBetError.setUpdateTimeLong(System.currentTimeMillis());
		hmBetError.setState(IbsStateEnum.OPEN.name());
		return dao.save(hmBetError);
	}

	/**
	 * 获取错误信息
	 *
	 * @param betInfoIds 投注信息主键数组
	 * @return 错误信息
	 */
	public List<Map<String, Object>> findErrorInfo(List<String> betInfoIds) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT ibe.BET_INFO_ID_,ibi.BET_ID_LIST_,ibe.FAIL_BET_CONTENT_, ")
				.append(" ibe.ERROR_BET_INFO_ FROM ibsc_bet_error ibe LEFT JOIN ibsc_bet_info ibi ")
				.append(" ON ibe.BET_INFO_ID_ = ibi.IBSC_BET_INFO_ID_ WHERE ibe.BET_INFO_ID_ in(");
		List<Object> parameters = new ArrayList<>();
		for (String betInfoId : betInfoIds) {
			sql.append("?,");
			parameters.add(betInfoId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.findMapList(sql, parameters);
	}
}
