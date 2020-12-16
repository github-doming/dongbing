package com.ibm.old.v1.client.ibm_client_bet_fail.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.client.ibm_client_bet_fail.t.entity.IbmClientBetFailT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmClientBetFailTService extends BaseService {

	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmClientBetFailT对象数据
	 */
	public String save(IbmClientBetFailT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_client_bet_fail的 IBM_CLIENT_BET_FAIL_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_bet_fail set state_='DEL' where IBM_CLIENT_BET_FAIL_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_BET_FAIL_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_bet_fail的 IBM_CLIENT_BET_FAIL_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_client_bet_fail set state_='DEL' where IBM_CLIENT_BET_FAIL_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_client_bet_fail的 IBM_CLIENT_BET_FAIL_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_bet_fail where IBM_CLIENT_BET_FAIL_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_BET_FAIL_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_bet_fail的 IBM_CLIENT_BET_FAIL_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_client_bet_fail where IBM_CLIENT_BET_FAIL_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientBetFailT实体信息
	 *
	 * @param entity IbmClientBetFailT实体
	 */
	public void update(IbmClientBetFailT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client_bet_fail表主键查找IbmClientBetFailT实体
	 *
	 * @param id ibm_client_bet_fail 主键
	 * @return IbmClientBetFailT实体
	 */
	public IbmClientBetFailT find(String id) throws Exception {
		return (IbmClientBetFailT) this.dao.find(IbmClientBetFailT.class, id);

	}

	/**
	 * 获取分页Map数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_bet_fail where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_bet_fail  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_bet_fail  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClientBetFailT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmClientBetFailT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_bet_fail where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_bet_fail  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_bet_fail  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmClientBetFailT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_bet_fail  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClientBetFailT数据信息
	 *
	 * @return 可用<IbmClientBetFailT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_bet_fail  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmClientBetFailT.class, sql);
	}

	/**
	 * 查找复投项
	 *
	 * @param existHmId 客户端已存在盘口会员主键
	 * @param gameCode  游戏Code
	 * @param period    期数
	 * @return 待投注内容
	 */
	public List<Map<String, Object>> findAgainBetItem(String existHmId, String period, String gameCode)
			throws SQLException {
		String sql = "SELECT IBM_CLIENT_BET_FAIL_ID_,EXEC_BET_ITEM_ID_, HANDICAP_MEMBER_ID_, CLOUD_RECEIPT_BET_ID_,"
				+ " CLIENT_EXIST_BET_ID_,BET_TYPE_,FAIL_BET_INFO_ FROM IBM_CLIENT_BET_FAIL WHERE CLIENT_EXIST_HM_ID_ = ? "
				+ " AND GAME_CODE_ = ? AND PERIOD_ = ? AND STATE_ = ? ORDER BY BET_TYPE_ DESC";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(existHmId);
		parameterList.add(gameCode);
		parameterList.add(period);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 更新投注项
	 *
	 * @param betFailIds 投注项id列表
	 */
	public void updateBet(List<String> betFailIds) throws SQLException {
		String sql = "update `ibm_client_bet_fail`  set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		StringBuilder sqlBuilder = new StringBuilder("IBM_CLIENT_BET_FAIL_ID_  in (");
		for (String betInfoExistId : betFailIds) {
			sqlBuilder.append("?,");
			parameterList.add(betInfoExistId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(") and STATE_ = ?");
		parameterList.add(IbmStateEnum.OPEN.name());
		sql += sqlBuilder.toString();
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 保存失败投注项
	 *
	 * @param existHmId         存在盘口会员id
	 * @param gameCode          游戏code
	 * @param period            期数
	 * @param betHandicap       投注盘口
	 * @param existBetId        存在投注id
	 * @param execBetItemId     执行投注项
	 * @param cloudReceiptBetId 消息回执id
	 * @param handicapMemberId  盘口会员id
	 * @param failInfo          失败信息
	 * @param nowTime           现在时间
	 */
	public void save(String existHmId, String gameCode, String period, Object betHandicap, String existBetId,
			String execBetItemId, String cloudReceiptBetId, String handicapMemberId, String failInfo, Date nowTime)
			throws Exception {
		IbmClientBetFailT betFailT = new IbmClientBetFailT();
		betFailT.setClientExistHmId(existHmId);
		betFailT.setClientExistBetId(existBetId);
		betFailT.setExecBetItemId(execBetItemId);
		betFailT.setCloudReceiptBetId(cloudReceiptBetId);
		betFailT.setHandicapMemberId(handicapMemberId);
		betFailT.setGameCode(gameCode);
		betFailT.setPeriod(period);
		betFailT.setBetType(betHandicap);
		betFailT.setFailBetInfo(failInfo);
		betFailT.setCreateTime(nowTime);
		betFailT.setCreateTimeLong(System.currentTimeMillis());
		betFailT.setState(IbmStateEnum.OPEN.name());
		save(betFailT);
	}
}
