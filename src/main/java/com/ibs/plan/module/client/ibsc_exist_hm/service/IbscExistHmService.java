package com.ibs.plan.module.client.ibsc_exist_hm.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.client.ibsc_exist_hm.entity.IbscExistHm;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSC客户端_已存在盘口会员 服务类
 *
 * @author Robot
 */
public class IbscExistHmService extends BaseServiceProxy {

	/**
	 * 保存IBSC客户端_已存在盘口会员 对象数据
	 *
	 * @param entity IbscExistHm对象数据
	 */
	public String save(IbscExistHm entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsc_exist_hm 的 IBSC_EXIST_HM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_exist_hm set state_='DEL' where IBSC_EXIST_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_EXIST_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsc_exist_hm 的 IBSC_EXIST_HM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsc_exist_hm set state_='DEL' where IBSC_EXIST_HM_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsc_exist_hm  的 IBSC_EXIST_HM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_exist_hm where IBSC_EXIST_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_EXIST_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsc_exist_hm 的 IBSC_EXIST_HM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_exist_hm where IBSC_EXIST_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscExistHm实体信息
	 *
	 * @param entity IBSC客户端_已存在盘口会员 实体
	 */
	public void update(IbscExistHm entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_exist_hm表主键查找 IBSC客户端_已存在盘口会员 实体
	 *
	 * @param id ibsc_exist_hm 主键
	 * @return IBSC客户端_已存在盘口会员 实体
	 */
	public IbscExistHm find(String id) throws Exception {
		return dao.find(IbscExistHm.class, id);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT IBSC_EXIST_HM_ID_ as existHmId,HANDICAP_CODE_ as handicapCode FROM ibsc_exist_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}
	/**
	 * 获取状态集合
	 *
	 * @return 状态集合
	 */
	public Map<String, String> mapState() throws SQLException {
		String sql = "SELECT IBSC_EXIST_HM_ID_ as key_,HM_STATE_ as value_ FROM ibsc_exist_hm  where state_ != 'DEL'";
		return super.findKVMap(sql, null);
	}

	/**
	 * 获取重新加载的基础信息
	 *
	 * @return 会员账户信息
	 */
	public List<Map<String, Object>> listInfo2reload() throws SQLException {
		String sql =
				"SELECT IBSC_EXIST_HM_ID_,HANDICAP_CODE_,MEMBER_ACCOUNT_,MEMBER_PASSWORD_,HANDICAP_URL_,HANDICAP_CAPTCHA_ "
						+ " FROM `ibsc_exist_hm` ieh LEFT JOIN ibsc_handicap_member ihm ON ieh.IBSC_EXIST_HM_ID_ = ihm.EXIST_HM_ID_ "
						+ " WHERE ieh.STATE_ = ? AND ihm.STATE_ = ? ORDER BY HANDICAP_CODE_";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);

	}

	/**
	 * 获取存在信息
	 *
	 * @return 存在信息
	 */
	public Map<String, Map<String, Object>> mapExitsInfo() throws SQLException {
		String sql = "SELECT IBSC_EXIST_HM_ID_,HANDICAP_MEMBER_ID_,HANDICAP_CODE_ FROM ibsc_exist_hm where state_ = ? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(IbsStateEnum.OPEN.name());
		List<Map<String, Object>> infos = super.findMapList(sql, parameters);
		Map<String, Map<String, Object>> exitsInfos = new HashMap<>();
		for (Map<String, Object> info : infos) {
			String handicapCode = info.get("HANDICAP_CODE_").toString();
			if (exitsInfos.containsKey(handicapCode)) {
				exitsInfos.get(handicapCode)
						.put(info.get("IBSC_EXIST_HM_ID_").toString(), info.get("HANDICAP_MEMBER_ID_"));
			} else {
				Map<String, Object> exitsInfo = new HashMap<>();
				exitsInfo.put(info.get("IBSC_EXIST_HM_ID_").toString(), info.get("HANDICAP_MEMBER_ID_"));
				exitsInfos.put(handicapCode, exitsInfo);
			}
		}
		return exitsInfos;
	}

	/**
	 * 获取盘口code
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return 盘口code
	 */
	public String findHandicapCode(String existHmId) throws SQLException {
		String sql = "SELECT HANDICAP_CODE_ FROM ibsc_exist_hm where IBSC_EXIST_HM_ID_ = ? and state_ = ? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(existHmId);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findString("HANDICAP_CODE_", sql, parameters);
	}

	/**
	 * 清除盘口会员信息
	 *
	 * @param existHmId 已存在盘口会员id
	 */
	public void removeExistInfo(String existHmId) throws SQLException {
		String sql = "update %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where EXIST_HM_ID_ = ? and STATE_ != ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(IbsStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add("会员登出");
		parameters.add(existHmId);
		parameters.add(IbsStateEnum.DEL.name());
		//盘口会员
		super.execute(String.format(sql, "ibsc_handicap_member"), parameters);
		//会员投注
		super.execute(String.format(sql, "ibsc_bet"), parameters);
		super.execute(String.format(sql, "ibsc_bet_info"), parameters);
		super.execute(String.format(sql, "ibsc_bet_error"), parameters);
		super.execute(String.format(sql, "ibsc_bet_fail"), parameters);
		super.execute(String.format(sql, "ibsc_bet_item"), parameters);
		//盘口会员游戏设置
		super.execute(String.format(sql, "ibsc_hm_game_set"), parameters);
		//盘口会员信息
		super.execute(String.format(sql, "ibsc_hm_info"), parameters);
		//盘口会员设置
		super.execute(String.format(sql, "ibsc_hm_set"), parameters);
		//盘口会员方案
		super.execute(String.format(sql, "ibsc_hm_plan"), parameters);
		super.execute(String.format(sql, "ibsc_plan_group_result"), parameters);
		super.execute(String.format(sql, "ibsc_plan_item"), parameters);
		//已存在盘口会员
		sql = "update ibsc_exist_hm set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where IBSC_EXIST_HM_ID_ = ? and STATE_ != ?";
		super.execute(sql, parameters);
	}

	/**
	 * 根据盘口会员id查找存在主键
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 存在主键
	 */
	public String findIdByHmId(String handicapMemberId) throws SQLException {
		String sql = "SELECT IBSC_EXIST_HM_ID_ as key_ FROM ibsc_exist_hm where HANDICAP_MEMBER_ID_ = ? and state_!= ? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(handicapMemberId);
		parameters.add(IbsStateEnum.DEL.name());
		return super.findString(sql, parameters);
	}

	/**
	 * 登录
	 *
	 * @param existHmId 存在主键
	 * @param nowTime   更新时间
	 */
	public void login(String existHmId, Date nowTime) throws SQLException {
		String sql = "update ibsc_exist_hm set HM_STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where IBSC_EXIST_HM_ID_ = ? and STATE_ != ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(IbsStateEnum.LOGIN.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add("用户登录，修改登录信息");
		parameters.add(existHmId);
		parameters.add(IbsStateEnum.DEL.name());
		super.execute(sql, parameters);
	}

	/**
	 * 获取登录状态
	 *
	 * @param existHmId 存在会员id
	 * @return 登录状态
	 */
	public String findState(String existHmId) throws SQLException {
		String sql = "select HM_STATE_ from ibsc_exist_hm where IBSC_EXIST_HM_ID_=? and STATE_!=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(existHmId);
		parameters.add(IbsStateEnum.DEL.name());
		return super.findString("HM_STATE_", sql, parameters);
	}

	/**
	 * 删除定时任务信息
	 * @param existHmId	已存在盘口会员id
	 */
	public void removeQuertzInfo(String existHmId) throws SQLException {
		List<Object> parameters=new ArrayList<>();
		//定时任务处理
		String sql="select SELF_QUARTZ_TASK_ID_ from self_quartz_task where TASK_NAME_ like ? and STATE_!=?";
		parameters.add(existHmId.concat("%"));
		parameters.add(IbsStateEnum.DEL.name());
		List<String> quartzTaskIds=super.dao.findStringList("SELF_QUARTZ_TASK_ID_",sql,parameters);
		if(ContainerTool.notEmpty(quartzTaskIds)){
			parameters.clear();
			StringBuilder sqlBuilder=new StringBuilder();
			sql="update self_quartz_task set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where SELF_QUARTZ_TASK_ID_ in (";
			parameters.add(IbsStateEnum.DEL.name());
			parameters.add(new Date());
			parameters.add(System.currentTimeMillis());
			for(String quartzTaskId:quartzTaskIds){
				sqlBuilder.append("?,");
				parameters.add(quartzTaskId);
			}
			sqlBuilder.replace(sqlBuilder.length()-1,sqlBuilder.length(),")");
			super.dao.execute(sql.concat(sqlBuilder.toString()), parameters);
			//IBS_定时器运行设置
			sql="update self_quartz_simple set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where QUARTZ_TASK_ID_ in (";
			super.dao.execute(sql.concat(sqlBuilder.toString()), parameters);
			//IBS_定时器CRON
			sql="update self_quartz_cron set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where QUARTZ_TASK_ID_ in (";
			super.dao.execute(sql.concat(sqlBuilder.toString()), parameters);
		}
	}

	/**
	 * 获取存在会员列表
	 * @param handicapCode  盘口code
	 * @return
	 */
	public List<String> findByHandicapCode(HandicapUtil.Code handicapCode) throws SQLException {
		String sql="select ISMC_EXIST_HM_ID_ from ibsc_exist_hm where HANDICAP_CODE_=? and STATE_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(handicapCode);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findStringList("IBSC_EXIST_HM_ID_",sql,parameters);
	}

	/**
	 * 批量删除会员信息
	 * @param existHmIds    存在会员ids
	 */
	public void removeExistHmInfo(List<String> existHmIds) throws SQLException {
		StringBuilder sqlPlus=new StringBuilder();
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(IbsStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(IbsStateEnum.DEL.name());
		for(String existHmId:existHmIds){
			sqlPlus.append("?,");
			parameters.add(existHmId);
		}
		sqlPlus.replace(sqlPlus.length()-1,sqlPlus.length(),")");
		//已存在盘口会员
		String sql = "update ibsc_exist_hm set STATE_ =?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and IBMC_EXIST_HM_ID_ in(";
		super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
		//盘口会员
		sql = "update ibsc_handicap_member set STATE_= ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
		super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
		//会员投注
		sql = "update ibsc_hm_bet set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
		super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
		//会员投注异常
		sql = "update ibsc_hm_bet_error set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
		super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
		//会员投注失败
		sql = "update ibsc_hm_bet_fail set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
		super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
		//会员投注信息
		sql = "update ibsc_hm_bet_info set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
		super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
		//会员投注详情
		sql = "update ibsc_hm_bet_item set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
		super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
		//盘口会员游戏设置
		sql = "update ibsc_hm_game_set set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
		super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
		//盘口会员信息
		sql = "update ibsc_hm_info set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
		super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
		//盘口会员设置
		sql = "update ibsc_hm_set set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
		super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
		sql = "update ibsc_hm_plan set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
		super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
	}

}
