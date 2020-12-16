package com.ibm.follow.servlet.vrc.vrc_bet.service;

import com.common.enums.TypeEnum;
import com.common.util.BaseGameUtil;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.vrc.vrc_bet.entity.VrcBet;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
* VRC客户端_投注 服务类
 * @author Robot
 */
public class VrcBetService extends BaseServiceProxy {

	/**
	 * 保存VRC客户端_投注 对象数据
	 * @param entity VrcBet对象数据
	 */
	public String save(VrcBet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vrc_bet 的 VRC_BET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vrc_bet set state_='DEL' where VRC_BET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VRC_BET_ID_主键id数组的数据
	 * @param idArray 要删除 vrc_bet 的 VRC_BET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vrc_bet set state_='DEL' where VRC_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vrc_bet  的 VRC_BET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vrc_bet where VRC_BET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VRC_BET_ID_主键id数组的数据
	 * @param idArray 要删除vrc_bet 的 VRC_BET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vrc_bet where VRC_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrcBet实体信息
	 * @param entity VRC客户端_投注 实体
	 */
	public void update(VrcBet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vrc_bet表主键查找 VRC客户端_投注 实体
	 * @param id vrc_bet 主键
	 * @return VRC客户端_投注 实体
	 */
	public VrcBet find(String id) throws Exception {
		return dao.find(VrcBet.class,id);
	}
	/**
	 *
	 * @param gameCode 游戏编码
	 * @param drawType 类型
	 * @param period   期数
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> mapBetInfo(GameUtil.Code gameCode, String drawType, Object period) throws SQLException {
		String sql = "SELECT VRC_BET_ID_ AS BET_ID_, EXIST_MEMBER_VR_ID_,PLAN_CODE_,PLAN_GROUP_KEY_,FUND_GROUP_KEY_, "
				+ " BET_FUND_T_, BET_CONTENT_, BET_CONTENT_LEN_,PERIOD_ FROM vrc_bet WHERE GAME_CODE_ = ? and GAME_DRAW_TYPE_=?"
				+ " AND PERIOD_ = ? AND STATE_ =? ORDER BY EXIST_MEMBER_VR_ID_";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(gameCode.name());
		parameterList.add(drawType);
		parameterList.add(period);
		parameterList.add(IbmStateEnum.OPEN.name());
		List<Map<String, Object>> betList = super.findMapList(sql, parameterList);
		if (ContainerTool.isEmpty(betList)) {
			return new HashMap<>(1);
		}
		Map<String, List<Map<String, Object>>> mapExecBet = new HashMap<>(betList.size() / 4);
		for (Map<String, Object> bet : betList) {
			String existHmId = bet.remove("EXIST_MEMBER_VR_ID_").toString();
			if (mapExecBet.containsKey(existHmId)) {
				mapExecBet.get(existHmId).add(bet);
			} else {
				List<Map<String, Object>> execBetItems = new ArrayList<>(4);
				execBetItems.add(bet);
				mapExecBet.put(existHmId, execBetItems);
			}
		}
		return mapExecBet;
	}
	/**
	 * 批量更新结算信息
	 *
	 * @param betList    投注结果信息
	 * @param drawNumber 开奖号码
	 */
	public void updateResult(List<Map<String, Object>> betList, String drawNumber) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("update vrc_bet set DRAW_NUMBER_ =?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ =?,");
		sql.append("PROFIT_T_= CASE VRC_BET_ID_");
		List<Object> parameters = new ArrayList<>();
		parameters.add(drawNumber);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		StringBuilder sqlPlus = new StringBuilder();

		List<Object> parametersPlus = new ArrayList<>();
		for (Map<String, Object> map : betList) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(map.get("BET_ID_"));
			parameters.add(map.get("PROFIT_T_"));

			sqlPlus.append("?,");
			parametersPlus.add(map.get("BET_ID_"));
		}
		sql.append(" end where VRC_BET_ID_ in (");
		sql.append(sqlPlus);
		parameters.addAll(parametersPlus);
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
	}

	/**
	 * 批量添加
	 * @param existMemberVrId		已存在虚拟会员id
	 * @param gameCode			游戏编码
	 * @param drawType			开奖类型
	 * @param period				期数
	 * @param betItems			投注内容
	 * @return
	 */
	public void batchSave(String existMemberVrId, BaseGameUtil.Code gameCode, String drawType,
								  Object period, List<Map<String, Object>> betItems) throws SQLException {
		if (StringTool.isContains(period.toString(), "-")) {
			period = period.toString().substring(4);
		}
		StringBuilder sql = new StringBuilder("insert into vrc_bet ");
		sql.append(" (VRC_BET_ID_,EXIST_MEMBER_VR_ID_,PLAN_CODE_,GAME_CODE_,GAME_DRAW_TYPE_,PERIOD_,PLAN_GROUP_KEY_"
				+ " ,FUND_GROUP_KEY_,BET_TYPE_,BET_CONTENT_LEN_,BET_CONTENT_,BET_FUND_T_,CREATE_TIME_, "
				+ " CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) values");
		List<Object> parameters = new ArrayList<>();
		Date nowTime = new Date();
		for (Map<String, Object> betItem : betItems) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			String betId = RandomTool.getNumLetter32();
			betItem.put("clientBetId", betId);
			parameters.add(betId);
			parameters.add(existMemberVrId);
			parameters.add(betItem.get("planCode"));
			parameters.add(gameCode.name());
			parameters.add(drawType);
			parameters.add(period);
			parameters.add(betItem.get("activeKey"));
			parameters.add(betItem.get("fundsKey"));
			parameters.add(TypeEnum.PLAN.name());
			parameters.add(betItem.get("betContentLen"));
			parameters.add(betItem.get("betContent"));
			parameters.add(betItem.get("betFundT"));
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(IbmStateEnum.OPEN.name());
		}
		sql.delete(sql.length() - 1, sql.length());
		super.execute(sql, parameters);
	}
}
