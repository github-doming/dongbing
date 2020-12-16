package com.ibm.follow.servlet.cloud.ibm_card_user.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_card_user.entity.IbmCardUser;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.util.ArrayList;
import java.util.List;

/**
* IBS_充值卡使用用户 服务类
 * @author Robot
 */
public class IbmCardUserService extends BaseServiceProxy {

	/**
	 * 保存IBS_充值卡使用用户 对象数据
	 * @param entity IbmCardUser对象数据
	 */
	public String save(IbmCardUser entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_card_user 的 IBM_CARD_USER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_card_user set state_='DEL' where IBM_CARD_USER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CARD_USER_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_card_user 的 IBM_CARD_USER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_card_user set state_='DEL' where IBM_CARD_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_card_user  的 IBM_CARD_USER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_card_user where IBM_CARD_USER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CARD_USER_ID_主键id数组的数据
	 * @param idArray 要删除ibm_card_user 的 IBM_CARD_USER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_card_user where IBM_CARD_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCardUser实体信息
	 * @param entity IBS_充值卡使用用户 实体
	 */
	public void update(IbmCardUser entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_card_user表主键查找 IBS_充值卡使用用户 实体
	 * @param id ibm_card_user 主键
	 * @return IBS_充值卡使用用户 实体
	 */
	public IbmCardUser find(String id) throws Exception {
		return (IbmCardUser) dao.find(IbmCardUser.class,id);

	}

	/**
	 * 根据用户Id查询实体
	 * @param userId 用户Id
	 * @return 实体
	 */
	public IbmCardUser findByUserId(String userId) throws Exception {
		String sql = "SELECT * FROM ibm_card_user where USER_ID_ = ? AND STATE_ !=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.dao.findObject(IbmCardUser.class,sql,parameters);
	}

	/**
	 * 根据用户Id查询实体
	 * @param createUserId 用户创建者Id
	 * @return 实体
	 */
	public List<String> listUserId(String createUserId) throws Exception {
		String sql = "SELECT USER_ID_ FROM ibm_card_user where CREATE_USER_ID_ = ? AND STATE_ !=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(createUserId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.dao.findStringList("USER_ID_",sql,parameters);
	}
}
