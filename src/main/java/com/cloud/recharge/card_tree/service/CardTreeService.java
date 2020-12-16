package com.cloud.recharge.card_tree.service;

import com.cloud.recharge.card_tree.entity.CardTree;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 充值卡卡类 服务类
 *
 * @author Robot
 */
public class CardTreeService extends BaseServiceProxy {

	/**
	 * 保存充值卡卡类 对象数据
	 *
	 * @param entity CardTree对象数据
	 */
	public String save(CardTree entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除card_tree 的 CARD_TREE_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update card_tree set state_='DEL' where CARD_TREE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除CARD_TREE_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 card_tree 的 CARD_TREE_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update card_tree set state_='DEL' where CARD_TREE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 card_tree  的 CARD_TREE_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from card_tree where CARD_TREE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除CARD_TREE_ID_主键id数组的数据
	 *
	 * @param idArray 要删除card_tree 的 CARD_TREE_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from card_tree where CARD_TREE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CardTree实体信息
	 *
	 * @param entity 充值卡卡类 实体
	 */
	public void update(CardTree entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据card_tree表主键查找 充值卡卡类 实体
	 *
	 * @param id card_tree 主键
	 * @return 充值卡卡类 实体
	 */
	public CardTree find(String id) throws Exception {
		return dao.find(CardTree.class, id);
	}

	/**
	 * 获取卡种价格表
	 *
	 * @return 卡种价格表
	 */
	public List<Map<String, Object>> listPrice() throws SQLException {
		String sql = "SELECT CARD_TREE_ID_,CARD_TREE_NAME_,CARD_PRICE_T_,SN_ FROM card_tree WHERE STATE_ != ? ORDER BY SN_ ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(StateEnum.DEL.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 查询新增代理的 卡种信息列表
	 *
	 * @return 卡种信息
	 */
	public List<Map<String, Object>> listDefPartnerInfo() throws SQLException {
		String sql = "SELECT CARD_TREE_ID_,CARD_TREE_NAME_,CARD_PRICE_T_ FROM card_tree WHERE CARD_TREE_TYPE_ != ? AND STATE_ != ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(CardTool.Type.ADMIN.name());
		parameters.add(StateEnum.DEL.name());
		return super.findMapList(sql, parameters);
	}
	/**
	 * 查询新增代理的 卡种信息列表
	 *
	 * @return 卡种信息
	 */
	public List<Map<String, Object>> listDefAgentInfo() throws SQLException {
		String sql = "SELECT CARD_TREE_ID_,CARD_TREE_NAME_,CARD_PRICE_T_ FROM card_tree WHERE CARD_TREE_TYPE_ = ? AND STATE_ != ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(CardTool.Type.AGENT.name());
		parameters.add(StateEnum.DEL.name());
		return super.findMapList(sql, parameters);
	}
	/**
	 * 更新卡类信息
	 */
	public void updateCardTree(String cardTreeId, Long cardTreePriceT, String cardTreeName, int cardTreePoint,
			String cardTreeType, Integer cardTreeSn, Boolean isDirect, String cardState, Date nowTime)
			throws SQLException {
		String sql = "UPDATE card_tree SET UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? ";
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		if (StringTool.notEmpty(cardTreeType)) {
			sql += ",CARD_TREE_TYPE_ = ?";
			parameters.add(cardTreeType);
		}
		if (cardTreePriceT > 0) {
			sql += ",CARD_PRICE_T_ = ?";
			parameters.add(cardTreePriceT);
		}
		if (StringTool.notEmpty(cardTreeName)) {
			sql += ",CARD_TREE_NAME_ = ?";
			parameters.add(cardTreeName);
		}
		if (cardTreePoint > 0) {
			sql += ",CARD_TREE_POINT_ = ?";
			parameters.add(cardTreePoint);
		}
		if (cardTreeSn > 0) {
			sql += ",SN_ = ?";
			parameters.add(cardTreeSn);
		}
		if (isDirect != null) {
			sql += ",IS_DIRECT_ = ?";
			parameters.add(isDirect);
		}
		if (StringTool.notEmpty(cardState)) {
			sql += ",STATE_ = ?";
			parameters.add(cardState);
		}
		sql += " WHERE CARD_TREE_ID_ = ? and STATE_ != ?";
		parameters.add(cardTreeId);
		parameters.add(StateEnum.DEL.name());
		dao.execute(sql, parameters);
	}

	/**
	 * 根据用户类型查询卡种列表
	 *
	 * @return 分类信息
	 */
	public List<Map<String, Object>> listCardTree() throws SQLException {
		String sql =
				"SELECT CARD_TREE_ID_,CARD_TREE_NAME_,CARD_TREE_POINT_,CARD_PRICE_T_,CARD_TREE_TYPE_,SN_,CREATOR_NAME_, "
						+ " CREATE_TIME_LONG_,DESC_,IS_DIRECT_,STATE_ FROM card_tree WHERE STATE_ != ? ORDER BY SN_";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(StateEnum.DEL.name());
		return dao.findMapList(sql, parameters);
	}

	/**
	 * 所有卡种信息
	 *
	 * @return 卡种列表
	 */
	public Map<String, Object> allCardTreeInfo() throws SQLException {
		String sql = "SELECT CARD_TREE_ID_ key_,CARD_TREE_NAME_ value_ FROM card_tree WHERE STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(StateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);
	}


	/**
	 * 根据用户类型查询卡种下拉列表
	 *
	 * @return 分类信息
	 */
	public List<Map<String, Object>> listCardTreeSelect() throws SQLException {
		String sql = "SELECT CARD_TREE_ID_,CARD_TREE_NAME_ FROM card_tree WHERE STATE_ = ? ORDER BY SN_";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(StateEnum.OPEN.name());
		return dao.findMapList(sql, parameters);
	}



	/**
	 * 根据用户可直充卡种列表
	 *
	 * @return 卡种列表
	 */
	public List<Map<String, Object>> listCardTreeDirect() throws SQLException {
		String sql = "SELECT CARD_TREE_ID_,CARD_TREE_NAME_ FROM card_tree WHERE STATE_ = ? and IS_DIRECT_ = ? ORDER BY SN_";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(StateEnum.OPEN.name());
		parameters.add(true);
		return dao.findMapList(sql, parameters);
	}


	/**
	 * 获取卡种信息
	 *
	 * @param cardTreeId 卡种主键
	 * @return 卡种信息
	 */
	public Map<String, Object> findInfo(String cardTreeId) throws SQLException {
		String sql = "SELECT CARD_TREE_TYPE_,STATE_,CARD_TREE_NAME_ FROM card_tree WHERE CARD_TREE_ID_ = ? AND STATE_ != ? ORDER BY SN_";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(cardTreeId);
		parameters.add(StateEnum.DEL.name());
		return dao.findMap(sql, parameters);
	}

	/**
	 * 获取创建卡的卡种信息
	 *
	 * @param cardTreeId 卡种主键
	 * @return 卡种信息
	 */
	public Map<String, Object> findCreateCardInfo(String cardTreeId) throws SQLException {
		String sql = "SELECT CARD_PRICE_T_,CARD_TREE_POINT_,CARD_TREE_NAME_,CARD_TREE_TYPE_ FROM card_tree WHERE CARD_TREE_ID_ = ? AND STATE_ = ? ORDER BY SN_";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(cardTreeId);
		parameters.add(StateEnum.OPEN.name());
		return dao.findMap(sql, parameters);
	}

	/**
	 * 获取状态为开启的卡种主键
	 * @return 卡种主键列表
	 */
	public List<String> listOpenId() throws SQLException {
		String sql = "SELECT CARD_TREE_ID_ as key_ FROM card_tree WHERE STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(StateEnum.OPEN.name());
		return super.findStringList(sql, parameters);
	}
}