package com.ibm.follow.servlet.cloud.ibm_card_tree.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_card_tree.entity.IbmCardTree;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBM_充值卡卡类 服务类
 *
 * @author Robot
 */
public class IbmCardTreeService extends BaseServicePlus {

	/**
	 * 保存IBM_充值卡卡类 对象数据
	 *
	 * @param entity IbmCardTree对象数据
	 */
	public String save(IbmCardTree entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_card_tree 的 IBM_CARD_TREE_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_card_tree set state_='DEL' where IBM_CARD_TREE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CARD_TREE_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_card_tree 的 IBM_CARD_TREE_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_card_tree set state_='DEL' where IBM_CARD_TREE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_card_tree  的 IBM_CARD_TREE_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_card_tree where IBM_CARD_TREE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CARD_TREE_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_card_tree 的 IBM_CARD_TREE_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_card_tree where IBM_CARD_TREE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCardTree实体信息
	 *
	 * @param entity IBM_充值卡卡类 实体
	 */
	public void update(IbmCardTree entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_card_tree表主键查找 IBM_充值卡卡类 实体
	 *
	 * @param id ibm_card_tree 主键
	 * @return IBM_充值卡卡类 实体
	 */
	public IbmCardTree find(String id) throws Exception {
		return (IbmCardTree) this.dao.find(IbmCardTree.class, id);

	}

	/**
	 * 更新卡类信息
	 */
	public void updateCardTree(String cardTreeId, Long cardTreePriceT, String cardTreeName, int cardTreePoint, String cardTreeType, Integer cardTreeSn, String cardState, Date date) throws SQLException {
		String sql = "UPDATE `ibm_card_tree` SET `UPDATE_TIME_` = ?,`UPDATE_TIME_LONG_` = ?";
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(date);
		parameters.add(System.currentTimeMillis());
		if (StringTool.notEmpty(cardTreeType)) {
			sql += ",CARD_TREE_TYPE_ = ?";
			parameters.add(cardTreeType);
		}
		if (!cardTreePriceT.equals(0L)) {
			sql += ",CARD_PRICE_T_ = ?";
			parameters.add(cardTreePriceT);
		}
		if (StringTool.notEmpty(cardTreeName)) {
			sql += ",CARD_TREE_NAME_ = ?";
			parameters.add(cardTreeName);
		}
		if (cardTreePoint != 0) {
			sql += ",CARD_TREE_POINT_ = ?";
			parameters.add(cardTreePoint);
		}
		if (!cardTreeSn.equals(0)) {
			sql += ",SN_ = ?";
			parameters.add(cardTreeSn);
		}
		if (StringTool.notEmpty(cardState)) {
			sql += ",STATE_ = ?";
			parameters.add(cardState);
		}
		sql += " WHERE IBM_CARD_TREE_ID_ = ? and STATE_ != ?";
		parameters.add(cardTreeId);
		parameters.add(IbmStateEnum.DEL.name());
		dao.execute(sql, parameters);
	}

	/**
	 * 根据用户类型查询卡种列表
	 *
	 * @return 分类信息
	 */
	public List<Map<String, Object>> listCardTreeByAdmin() throws SQLException {

		String sql = "SELECT IBM_CARD_TREE_ID_,CARD_TREE_NAME_,CARD_TREE_POINT_,CARD_PRICE_T_,CARD_TREE_TYPE_,SN_,CREATER_NAME_,CREATE_TIME_LONG_,DESC_,STATE_ FROM `ibm_card_tree` " +
				"where  STATE_ != ? ORDER BY SN_";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(IbmStateEnum.DEL.name());
		return dao.findMapList(sql, parameters);
	}

	/**
	 * 根据用户类型查询卡种列表
	 *
	 * @return 只获取ID和NAME
	 */
	public List<Map<String, Object>> listCardTreeSimpleByAdmin() throws SQLException {
		String sql = "SELECT IBM_CARD_TREE_ID_,CARD_TREE_NAME_ FROM `ibm_card_tree` " +
				"where  STATE_ = ? ORDER BY SN_";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(IbmStateEnum.OPEN.name());
		return dao.findMapList(sql, parameters);
	}

	/**
	 * 拼接sql参数
	 *
	 * @param allTreeIds 分类ID数组
	 * @param sql        sql
	 */
	private void getParameterList(Set<String> allTreeIds, StringBuilder sql, List<Object> parameters) {
		if (!ContainerTool.isEmpty(allTreeIds)) {
			sql.append(" AND IBM_CARD_TREE_ID_ IN ( ");
			for (String treeId : allTreeIds) {
				parameters.add(treeId);
				sql.append("?,");
			}
			sql.deleteCharAt(sql.length() - 1).append(")");
		}
		sql.append(" ORDER BY SN_");
	}

	/**
	 * 根据分类ID列表查询卡种列表
	 *
	 * @return 只获取ID和NAME
	 */
	public List<Map<String, Object>> listCardTreeSimpleByTreeIds(Set<String> allTreeIds) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT IBM_CARD_TREE_ID_,CARD_TREE_NAME_ FROM `ibm_card_tree` " +
				" where  STATE_ = ?  ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.OPEN.name());
		getParameterList(allTreeIds, sql, parameters);
		return dao.findMapList(sql.toString(), parameters);
	}

	/**
	 * 获取卡种价格表
	 *
	 * @return 卡种价格表
	 */
	public List<Map<String, Object>> listPrice() throws SQLException {
		String sql = "SELECT CARD_TREE_NAME_,CARD_PRICE_T_,SN_,IBM_CARD_TREE_ID_ as CARD_TREE_ID_ FROM ibm_card_tree " +
				" WHERE STATE_ != ? ORDER BY SN_ ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.DEL.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 查询新增代理的 卡种信息列表
	 *
	 * @return 卡种信息
	 */
	public List<Map<String, Object>> listAddPartnerInfo() throws SQLException {
		String sql = "SELECT IBM_CARD_TREE_ID_,CARD_TREE_NAME_,CARD_PRICE_T_ FROM `ibm_card_tree` " +
				" where CARD_TREE_TYPE_ != ? and STATE_ != ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmTypeEnum.ADMIN.name());
		parameters.add(IbmStateEnum.DEL.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 获取指定的卡类型
	 */
	public IbmCardTree findAssignTree(String type) throws Exception {
		String sql = "SELECT * FROM `ibm_card_tree` WHERE DESC_ = ? AND STATE_ !=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(type);
		parameters.add(IbmStateEnum.DEL.name());
		return super.dao.findObject(IbmCardTree.class,sql,parameters);
}
}
