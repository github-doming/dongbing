package com.ibm.old.v1.client.ibm_client_bet_error.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.client.ibm_client_bet_error.t.entity.IbmClientBetErrorT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmClientBetErrorTService extends BaseService {

	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmClientBetErrorT对象数据
	 */
	public String save(IbmClientBetErrorT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_client_bet_error的 IBM_CLIENT_BET_ERROR_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_bet_error set state_='DEL' where IBM_CLIENT_BET_ERROR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_BET_ERROR_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_bet_error的 IBM_CLIENT_BET_ERROR_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_client_bet_error set state_='DEL' where IBM_CLIENT_BET_ERROR_ID_ in(" + stringBuilder
							.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_client_bet_error的 IBM_CLIENT_BET_ERROR_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_bet_error where IBM_CLIENT_BET_ERROR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_BET_ERROR_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_bet_error的 IBM_CLIENT_BET_ERROR_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_client_bet_error where IBM_CLIENT_BET_ERROR_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientBetErrorT实体信息
	 *
	 * @param entity IbmClientBetErrorT实体
	 */
	public void update(IbmClientBetErrorT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client_bet_error表主键查找IbmClientBetErrorT实体
	 *
	 * @param id ibm_client_bet_error 主键
	 * @return IbmClientBetErrorT实体
	 */
	public IbmClientBetErrorT find(String id) throws Exception {
		return (IbmClientBetErrorT) this.dao.find(IbmClientBetErrorT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_client_bet_error where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_bet_error  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_bet_error  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClientBetErrorT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmClientBetErrorT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_bet_error where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_bet_error  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_bet_error  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmClientBetErrorT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_bet_error  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClientBetErrorT数据信息
	 *
	 * @return 可用<IbmClientBetErrorT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_bet_error  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmClientBetErrorT.class, sql);
	}

	/**
	 * 保存异常投注项
	 *
	 * @param existHmId         存在盘口会员id
	 * @param gameCode          游戏code
	 * @param period            期数
	 * @param nowTime           现在时间
	 * @param existBetId        存在投注id
	 * @param errorInfo         异常信息
	 * @param execBetItemId     执行投注项
	 * @param cloudReceiptBetId 消息回执id
	 * @param handicapMemberId  盘口会员id
	 */
	public void save(String existHmId, String gameCode, String period, String existBetId, String execBetItemId,
			String cloudReceiptBetId, String handicapMemberId, String errorInfo, Date nowTime) throws Exception {
		IbmClientBetErrorT betErrorT = new IbmClientBetErrorT();
		betErrorT.setClientExistHmId(existHmId);
		betErrorT.setClientExistBetId(existBetId);
		betErrorT.setExecBetItemId(execBetItemId);
		betErrorT.setCloudReceiptBetId(cloudReceiptBetId);
		betErrorT.setHandicapMemberId(handicapMemberId);
		betErrorT.setGameCode(gameCode);
		betErrorT.setPeriod(period);
		betErrorT.setErrorBetInfo(errorInfo);
		betErrorT.setCreateTime(nowTime);
		betErrorT.setCreateTimeLong(System.currentTimeMillis());
		betErrorT.setState(IbmStateEnum.OPEN.name());
		save(betErrorT);
	}

	/**
	 * 获取异常需要回传的信息
	 *
	 * @param existHmId 客户端已存在盘口会员主键
	 * @param period    期数
	 * @param gameCode  游戏Code
	 * @return 异常信息
	 */
	public Map<String, List<String>> listErrorBetInfo(String existHmId, String period, String gameCode)
			throws SQLException {
		//需要回传的消息
		String sql = "SELECT IBM_CLIENT_BET_ERROR_ID_, EXEC_BET_ITEM_ID_, ERROR_BET_INFO_ FROM "
				+ " ibm_client_bet_error WHERE CLIENT_EXIST_HM_ID_ = ? AND GAME_CODE_ = ? AND PERIOD_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(existHmId);
		parameterList.add(gameCode);
		parameterList.add(period);
		parameterList.add(IbmStateEnum.OPEN.name());
		List<Map<String, Object>> errorBetInfos = this.dao.findMapList(sql, parameterList);
		if (ContainerTool.isEmpty(errorBetInfos)) {
			return null;
		}
		Map<String, List<String>> errorBetItem = new HashMap<>(errorBetInfos.size());
		 List<String> betErrorIds = new ArrayList<>();
		for (Map<String, Object> errorBetInfo : errorBetInfos) {
			betErrorIds.add(errorBetInfo.get("IBM_CLIENT_BET_ERROR_ID_").toString());
			List<String> errorItems = Arrays
					.asList(errorBetInfo.get("ERROR_BET_INFO_").toString().split(","));
			errorBetItem.put(errorBetInfo.get("EXEC_BET_ITEM_ID_").toString(),errorItems);
		}
		//标示消息已回传
		sql = "update `ibm_client_bet_error`  set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where "
				+ "IBM_CLIENT_BET_ERROR_ID_ in ('" + String.join("','", betErrorIds) + "') AND STATE_ = ?";
		parameterList.clear();
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
		return errorBetItem;
	}
}
