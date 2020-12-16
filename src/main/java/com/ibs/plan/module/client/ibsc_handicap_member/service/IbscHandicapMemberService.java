package com.ibs.plan.module.client.ibsc_handicap_member.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.client.ibsc_handicap_member.entity.IbscHandicapMember;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * IBSC客户端_盘口会员 服务类
 *
 * @author Robot
 */
public class IbscHandicapMemberService extends BaseServiceProxy {

	/**
	 * 保存IBSC客户端_盘口会员 对象数据
	 *
	 * @param entity IbscHandicapMember对象数据
	 */
	public String save(IbscHandicapMember entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsc_handicap_member 的 IBSC_HANDICAP_MEMBER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_handicap_member set state_='DEL' where IBSC_HANDICAP_MEMBER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_HANDICAP_MEMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsc_handicap_member 的 IBSC_HANDICAP_MEMBER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsc_handicap_member set state_='DEL' where IBSC_HANDICAP_MEMBER_ID_ in(" + stringBuilder
							.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsc_handicap_member  的 IBSC_HANDICAP_MEMBER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_handicap_member where IBSC_HANDICAP_MEMBER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_HANDICAP_MEMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsc_handicap_member 的 IBSC_HANDICAP_MEMBER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibsc_handicap_member where IBSC_HANDICAP_MEMBER_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscHandicapMember实体信息
	 *
	 * @param entity IBSC客户端_盘口会员 实体
	 */
	public void update(IbscHandicapMember entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_handicap_member表主键查找 IBSC客户端_盘口会员 实体
	 *
	 * @param id ibsc_handicap_member 主键
	 * @return IBSC客户端_盘口会员 实体
	 */
	public IbscHandicapMember find(String id) throws Exception {
		return dao.find(IbscHandicapMember.class, id);

	}

	/**
	 * 根据存在主键查找会员信息
	 *
	 * @param existId 存在主键
	 * @return 会员信息
	 */
	public IbscHandicapMember findByExistId(String existId) throws Exception {
		String sql = "SELECT * FROM `ibsc_handicap_member` where EXIST_HM_ID_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(existId);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbscHandicapMember.class, sql, parameters);
	}
}
