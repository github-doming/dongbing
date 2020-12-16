package com.cloud.recharge.card_operate_log.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.cloud.recharge.card_operate_log.entity.CardOperateLog;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 开卡平台操作日志 服务类
 *
 * @author Robot
 */
public class CardOperateLogService extends BaseServiceProxy {

	/**
	 * 保存开卡平台操作日志 对象数据
	 *
	 * @param entity CardOperateLog对象数据
	 */
	public String save(CardOperateLog entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除card_operate_log 的 CARD_OPERATE_LOG_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update card_operate_log set state_='DEL' where CARD_OPERATE_LOG_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除CARD_OPERATE_LOG_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 card_operate_log 的 CARD_OPERATE_LOG_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update card_operate_log set state_='DEL' where CARD_OPERATE_LOG_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 card_operate_log  的 CARD_OPERATE_LOG_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from card_operate_log where CARD_OPERATE_LOG_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除CARD_OPERATE_LOG_ID_主键id数组的数据
	 *
	 * @param idArray 要删除card_operate_log 的 CARD_OPERATE_LOG_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from card_operate_log where CARD_OPERATE_LOG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CardOperateLog实体信息
	 *
	 * @param entity 开卡平台操作日志 实体
	 */
	public void update(CardOperateLog entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据card_operate_log表主键查找 开卡平台操作日志 实体
	 *
	 * @param id card_operate_log 主键
	 * @return 开卡平台操作日志 实体
	 */
	public CardOperateLog find(String id) throws Exception {
		return dao.find(CardOperateLog.class, id);
	}

	/**
	 * 保存操作日志
	 *
	 * @param userId       用户主键
	 * @param opertType    操作类型
	 * @param opertContent 操作正文
	 * @param nowTime      保存时间
	 */
	public void save(String userId,String ip,String userName, String opertType, String opertContent, Date nowTime) throws SQLException {
		String sql = "INSERT INTO card_operate_log (USER_ID_,USER_NAME_,OPERT_TYPE_,OPERT_CONTENT_, "
				+ " IP_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_LONG_,STATE_) VALUES (?,?,?,?,?,?,?,?,?) ";
		List<Object> parameters = new ArrayList<>(8);
		parameters.add(userId);
		parameters.add(userName);
		parameters.add(opertType);
		parameters.add(opertContent);
		parameters.add(ip);
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(System.currentTimeMillis());
		parameters.add(StateEnum.OPEN.name());
		super.execute(sql, parameters);

	}

	public PageCoreBean<Map<String, Object>> listLog(String userPath,String opertType,Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount = "SELECT count(*) from (";
		String sql = "SELECT CARD_OPERATE_LOG_ID_ ID_,USER_NAME_,IP_,OPERT_CONTENT_,CREATE_TIME_LONG_,OPERT_TYPE_ FROM `card_operate_log` " +
				"WHERE USER_PATH_ like ? AND STATE_ !=?";

		List<Object> parameters = new ArrayList<>(2);
		parameters.add(userPath.concat("%"));
		parameters.add(StateEnum.DEL.name());
		if(StringTool.notEmpty(opertType)){
			sql+=" and OPERT_TYPE_=?";
			parameters.add(opertType);
		}
		sql+=" ORDER BY CARD_OPERATE_LOG_ID_ DESC";
		sqlCount = sqlCount + sql + ") AS t  ";
		return dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
	}

}
