package com.cloud.recharge.card_admin_price.service;

import com.cloud.recharge.card_admin_price.entity.CardAdminPrice;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 管理员卡种价格 服务类
 *
 * @author Robot
 */
public class CardAdminPriceService extends BaseServiceProxy {

	/**
	 * 保存管理员卡种价格 对象数据
	 *
	 * @param entity CardAdminPrice对象数据
	 */
	public String save(CardAdminPrice entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除card_admin_price 的 CARD_ADMIN_PRICE_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update card_admin_price set state_='DEL' where CARD_ADMIN_PRICE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除CARD_ADMIN_PRICE_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 card_admin_price 的 CARD_ADMIN_PRICE_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update card_admin_price set state_='DEL' where CARD_ADMIN_PRICE_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 card_admin_price  的 CARD_ADMIN_PRICE_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from card_admin_price where CARD_ADMIN_PRICE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除CARD_ADMIN_PRICE_ID_主键id数组的数据
	 *
	 * @param idArray 要删除card_admin_price 的 CARD_ADMIN_PRICE_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from card_admin_price where CARD_ADMIN_PRICE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CardAdminPrice实体信息
	 *
	 * @param entity 管理员卡种价格 实体
	 */
	public void update(CardAdminPrice entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据card_admin_price表主键查找 管理员卡种价格 实体
	 *
	 * @param id card_admin_price 主键
	 * @return 管理员卡种价格 实体
	 */
	public CardAdminPrice find(String id) throws Exception {
		return dao.find(CardAdminPrice.class, id);
	}

	/**
	 * 获取用户价格
	 *
	 * @param userId     父用户主键
	 * @param subAgentId 子用户主键
	 * @return 用户价格
	 */
	public List<Map<String, Object>> listInfo(String userId, String subAgentId, boolean isAdmin) throws SQLException {
		String sql = "SELECT ip.CARD_PRICE_T_, ip.CARD_TREE_ID_, ct.CARD_TREE_NAME_, ct.SN_ FROM card_admin_price ip "
				+ " LEFT JOIN card_tree ct ON ip.CARD_TREE_ID_ = ct.CARD_TREE_ID_ WHERE ";
		List<Object> parameters = new ArrayList<>();
		if (StringTool.notEmpty(userId)) {
			sql += " USER_ID_ = ? AND ";
			parameters.add(userId);
		}
		if (!isAdmin) {
			sql += " ip.STATE_ = ? AND ";
			parameters.add(StateEnum.OPEN.name());
		}
		sql += " SUB_USER_ID_ = ? AND ct.STATE_ = ? ORDER BY ct.SN_ ";
		parameters.add(subAgentId);
		parameters.add(StateEnum.OPEN.name());
		return dao.findMapList(sql, parameters);
	}

	/**
	 * 获取新增代理用户的 卡种信息列表
	 *
	 * @param userId 用户主键
	 * @return 卡种信息列表
	 */
	public List<Map<String, Object>> listDefAgentInfo(String userId) throws SQLException {
		String sql = "SELECT icr.CARD_TREE_ID_, icr.CARD_TREE_NAME_,icr.CARD_TREE_POINT_,icap.CARD_PRICE_T_ "
				+ " FROM card_admin_price icap LEFT JOIN card_tree icr ON icap.CARD_TREE_ID_ = icr.CARD_TREE_ID_ "
				+ " WHERE icap.USER_ID_ = ? AND icap.SUB_USER_ID_ = ? AND icr.CARD_TREE_TYPE_ = ? AND icap.STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(userId);
		parameters.add(StateEnum.DEF.name());
		parameters.add(CardTool.Type.AGENT.name());
		parameters.add(StateEnum.OPEN.name());
		return dao.findMapList(sql, parameters);
	}
	/**
	 * 更新充值卡价格
	 */
	public void updateCardPrice(String userId, String subUserId, String cardTreeId, long cardTreePriceT, Date nowTime)
			throws SQLException {
		String sql = "UPDATE card_admin_price set CARD_PRICE_T_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where CARD_TREE_ID_ = ? AND USER_ID_ = ? AND SUB_USER_ID_ = ? AND STATE_ != ? ";
		List<Object> parameters = new ArrayList<>(7);
		parameters.add(cardTreePriceT);
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(cardTreeId);
		parameters.add(userId);
		parameters.add(subUserId);
		parameters.add(StateEnum.DEL.name());
		super.execute(sql, parameters);
	}

	/**
	 * 查找用户的卡种列表信息
	 *
	 * @param userId 用户Id
	 * @return 卡种列表信息
	 */
	public List<Map<String, Object>> listCardTreeByUser(String userId) throws SQLException {
		String sql = "SELECT it.CARD_TREE_NAME_,ip.CARD_PRICE_T_,it.CARD_TREE_POINT_,it.CARD_TREE_TYPE_,it.SN_, "
				+ " it.CREATOR_NAME_,it.CREATE_TIME_LONG_,it.DESC_,it.IS_DIRECT_,it.STATE_ FROM card_admin_price ip "
				+ " LEFT JOIN card_tree it ON ip.CARD_TREE_ID_ = it.CARD_TREE_ID_ WHERE "
				+ " ip.SUB_USER_ID_ = ? AND ip.STATE_ = ? AND it.STATE_ = ? ORDER BY it.SN_";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(StateEnum.OPEN.name());
		return dao.findMapList(sql, parameterList);
	}

	/**
	 * 查找用户的卡种下拉列表信息
	 *
	 * @param userId 用户Id
	 * @return 卡种下拉列表信息
	 */
	public List<Map<String, Object>> listCardTreeSelect(String userId) throws SQLException {
		String sql = "SELECT it.CARD_TREE_NAME_,ip.CARD_TREE_ID_ FROM card_admin_price ip "
				+ " LEFT JOIN card_tree it ON ip.CARD_TREE_ID_ = it.CARD_TREE_ID_ WHERE "
				+ " ip.SUB_USER_ID_ = ? AND ip.STATE_ = ? AND it.STATE_ = ? ORDER BY it.SN_";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(StateEnum.OPEN.name());
		return dao.findMapList(sql, parameterList);
	}

	/**
	 * 根据用户可直充卡种列表
	 *
	 * @return 卡种列表
	 */
	public List<Map<String, Object>> listCardTreeDirect(String userId) throws SQLException {
		String sql = "SELECT it.CARD_TREE_NAME_,ip.CARD_TREE_ID_ FROM card_admin_price ip "
				+ " LEFT JOIN card_tree it ON ip.CARD_TREE_ID_ = it.CARD_TREE_ID_ WHERE "
				+ " ip.SUB_USER_ID_ = ? AND ip.STATE_ = ? AND it.STATE_ = ? AND it.IS_DIRECT_ = ? ORDER BY it.SN_";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(true);
		return dao.findMapList(sql, parameterList);
	}

	/**
	 * 根据用户信息删除卡种价格信息
	 *
	 * @param cardTreeId 卡种主键
	 * @param userType   用户类型
	 * @param nowTime    更新时间
	 */
	public void delByUserType(String cardTreeId, String userType, Date nowTime) throws SQLException {
		String sql = "SELECT APP_USER_ID_ as key_ FROM card_admin WHERE USER_TYPE_ = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(10);
		parameterList.add(userType);
		parameterList.add(StateEnum.DEL.name());
		List<String> appUserIds = super.findStringList(sql, parameterList);
		if (ContainerTool.isEmpty(appUserIds)) {
			return;
		}

		sql = "UPDATE card_admin_price SET state_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE state_ != ? CARD_TREE_ID_ = ? AND USER_ID_ IN (";
		parameterList.clear();
		parameterList.add(StateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(StateEnum.DEL.name());
		parameterList.add(cardTreeId);
		StringBuilder sqlPlus = new StringBuilder();

		for (String ignored : appUserIds) {
			sqlPlus.append("?,");
		}
		sqlPlus.deleteCharAt(sqlPlus.lastIndexOf(",")).append(")");
		sql += sqlPlus.toString() + " OR SUB_USER_ID_ IN (" + sqlPlus.toString();
		parameterList.addAll(appUserIds);
		parameterList.addAll(appUserIds);
		super.execute(sql, parameterList);
	}
	/**
	 * 根据分类ID删除
	 *
	 * @param cardTreeId 分类Id
	 */
	public void delByTreeId(String cardTreeId, Date nowTime) throws SQLException {
		String sql = "UPDATE card_admin_price SET state_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE CARD_TREE_ID_ = ? AND state_ != ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(StateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(cardTreeId);
		parameterList.add(StateEnum.DEL.name());
		super.execute(sql, parameterList);
	}

	/**
	 * 根据用户ID删除
	 *
	 * @param userId 分类Id
	 */
	public void delByUserId(String userId, Date nowTime) throws SQLException {
		String sql = "UPDATE card_admin_price SET state_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE USER_ID_ = ? AND state_ != ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(StateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(userId);
		parameterList.add(StateEnum.DEL.name());
		super.execute(sql, parameterList);
	}

	/**
	 * 获取用户上级给用户指定的某个卡种的价格
	 *
	 * @return 上级指定的价格
	 */
	public Long findCardPriceT(String cardTreeId, String userId) throws SQLException {
		String sql = "SELECT CARD_PRICE_T_ FROM card_admin_price where CARD_TREE_ID_ = ? AND SUB_USER_ID_ = ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(cardTreeId);
		parameters.add(userId);
		parameters.add(StateEnum.OPEN.name());

		return NumberTool.getLong(super.findMap(sql, parameters), "CARD_PRICE_T_", null);
	}
	/**
	 * 获取用户的卡种主键列表
	 *
	 * @return 卡种主键列表
	 */
	public List<String> listCardTreeIdByUser(String userId) throws SQLException {
		String sql = "SELECT CARD_TREE_ID_ as key_ FROM card_admin_price where  SUB_USER_ID_ = ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(userId);
		parameters.add(StateEnum.OPEN.name());
		return super.findStringList(sql, parameters);
	}

	/**
	 * 获取记录的主要信息
	 * @param cardTreeId 卡种主键
	 * @param userId 下级主键
	 * @return  CARD_PRICE_T_, USER_NAME_,PARENT_USER_ID_
	 */
	public Map<String, Object> findInfo4Record(String cardTreeId, String userId) throws SQLException {
		String sql = "SELECT USER_TYPE_,CARD_PRICE_T_, USER_NAME_,PARENT_USER_ID_ FROM card_admin_price cp "
				+ " LEFT JOIN card_admin ca ON cp.SUB_USER_ID_ = ca.APP_USER_ID_ "
				+ " WHERE CARD_TREE_ID_ = ? AND SUB_USER_ID_ = ? AND cp.STATE_ = ? AND ca.STATE_ = ?";
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(cardTreeId);
		parameters.add(userId);
		parameters.add(StateEnum.OPEN.name());
		parameters.add(StateEnum.OPEN.name());
		return super.findMap(sql,parameters);
	}
}
