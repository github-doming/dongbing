package com.ibs.plan.module.client.ibsc_hm_coding_item.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.client.ibsc_hm_coding_item.entity.IbscHmCodingItem;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* IBSC客户端_方案执行详情 服务类
 * @author Robot
 */
public class IbscHmCodingItemService extends BaseServiceProxy {

	/**
	 * 保存IBSC客户端_方案执行详情 对象数据
	 * @param entity IbscHmCodingItem对象数据
	 */
	public String save(IbscHmCodingItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsc_hm_coding_item 的 IBSC_HM_CODING_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_hm_coding_item set state_='DEL' where IBSC_HM_CODING_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSC_HM_CODING_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除 ibsc_hm_coding_item 的 IBSC_HM_CODING_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsc_hm_coding_item set state_='DEL' where IBSC_HM_CODING_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsc_hm_coding_item  的 IBSC_HM_CODING_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_hm_coding_item where IBSC_HM_CODING_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSC_HM_CODING_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除ibsc_hm_coding_item 的 IBSC_HM_CODING_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_hm_coding_item where IBSC_HM_CODING_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscHmCodingItem实体信息
	 * @param entity IBSC客户端_方案执行详情 实体
	 */
	public void update(IbscHmCodingItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_hm_coding_item表主键查找 IBSC客户端_方案执行详情 实体
	 * @param id ibsc_hm_coding_item 主键
	 * @return IBSC客户端_方案执行详情 实体
	 */
	public IbscHmCodingItem find(String id) throws Exception {
		return dao.find(IbscHmCodingItem.class,id);
	}

	/**
	 * 添加会员编码详情
	 * @param existHmId	已存在会员id
	 * @param gameCode	游戏编码
	 * @param period		期数
	 * @return
	 */
	public void save(String existHmId, GameUtil.Code gameCode, Object period) throws SQLException {
		String sql="insert into ibsc_hm_coding_item(IBSC_HM_CODING_ITEM_ID_,EXIST_HM_ID_,GAME_CODE_,PERIOD_,"
				+ "CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) values (?,?,?,?,?,?,?,?,?)";
		List<Object> parameters=new ArrayList<>();
		parameters.add(RandomTool.getNumLetter32());
		parameters.add(existHmId);
		parameters.add(gameCode.name());
		parameters.add(period.toString());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(IbsStateEnum.OPEN.name());
		super.dao.execute(sql,parameters);
		//提交事务
		CurrentTransaction.transactionCommit();
	}

	/**
	 * 获取编码详情
	 * @param existHmId	已存在盘口会员id
	 * @param gameCode	游戏编码
	 * @param period		期数
	 * @return
	 */
	public boolean findCodingItem(String existHmId, String gameCode, Object period) throws SQLException {
		String sql="select IBSC_HM_CODING_ITEM_ID_ from ibsc_hm_coding_item where EXIST_HM_ID_=? and GAME_CODE_=?"
				+ " and PERIOD_=? and STATE_=?";
		List<Object> parameters=new ArrayList<>(4);
		parameters.add(existHmId);
		parameters.add(gameCode);
		parameters.add(period.toString());
		parameters.add(IbsStateEnum.OPEN.name());
		return ContainerTool.notEmpty(super.dao.findMap(sql,parameters));
	}
}
