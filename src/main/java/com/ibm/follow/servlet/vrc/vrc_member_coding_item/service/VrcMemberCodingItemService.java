package com.ibm.follow.servlet.vrc.vrc_member_coding_item.service;


import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.vrc.vrc_member_coding_item.entity.VrcMemberCodingItem;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* VRC客户端_方案编码详情 服务类
 * @author Robot
 */
public class VrcMemberCodingItemService extends BaseServiceProxy {

	/**
	 * 保存VRC客户端_方案编码详情 对象数据
	 * @param entity VrcMemberCodingItem对象数据
	 */
	public String save(VrcMemberCodingItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vrc_member_coding_item 的 VRC_MEMBER_CODING_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vrc_member_coding_item set state_='DEL' where VRC_MEMBER_CODING_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VRC_MEMBER_CODING_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除 vrc_member_coding_item 的 VRC_MEMBER_CODING_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vrc_member_coding_item set state_='DEL' where VRC_MEMBER_CODING_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vrc_member_coding_item  的 VRC_MEMBER_CODING_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vrc_member_coding_item where VRC_MEMBER_CODING_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VRC_MEMBER_CODING_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除vrc_member_coding_item 的 VRC_MEMBER_CODING_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vrc_member_coding_item where VRC_MEMBER_CODING_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrcMemberCodingItem实体信息
	 * @param entity VRC客户端_方案编码详情 实体
	 */
	public void update(VrcMemberCodingItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vrc_member_coding_item表主键查找 VRC客户端_方案编码详情 实体
	 * @param id vrc_member_coding_item 主键
	 * @return VRC客户端_方案编码详情 实体
	 */
	public VrcMemberCodingItem find(String id) throws Exception {
		return dao.find(VrcMemberCodingItem.class,id);
	}

	/**
	 * 添加会员编码详情
	 * @param existHmId		已存在虚拟会员id
	 * @param gameCode		游戏编码
	 * @param period			期数
	 */
	public void save(String existHmId, GameUtil.Code gameCode, Object period) throws SQLException {
		String sql="insert into vrc_member_coding_item(VRC_MEMBER_CODING_ITEM_ID_,EXIST_MEMBER_VR_ID_,GAME_CODE_,PERIOD_,"
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
		parameters.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql,parameters);
		//提交事务
		CurrentTransaction.transactionCommit();
	}
}
