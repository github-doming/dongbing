package com.ibs.plan.module.cloud.ibsp_cms_notify.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_cms_notify.entity.IbspCmsNotify;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBSP消息列表 服务类
 *
 * @author Robot
 */
public class IbspCmsNotifyService extends BaseServiceProxy {

	/**
	 * 保存IBSP消息列表 对象数据
	 *
	 * @param entity IbspCmsNotify对象数据
	 */
	public String save(IbspCmsNotify entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_cms_notify 的 IBSP_CMS_NOTIFY_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_cms_notify set state_='DEL' where IBSP_CMS_NOTIFY_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_CMS_NOTIFY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_cms_notify 的 IBSP_CMS_NOTIFY_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_cms_notify set state_='DEL' where IBSP_CMS_NOTIFY_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_cms_notify  的 IBSP_CMS_NOTIFY_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_cms_notify where IBSP_CMS_NOTIFY_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_CMS_NOTIFY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_cms_notify 的 IBSP_CMS_NOTIFY_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_cms_notify where IBSP_CMS_NOTIFY_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspCmsNotify实体信息
	 *
	 * @param entity IBSP消息列表 实体
	 */
	public void update(IbspCmsNotify entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_cms_notify表主键查找 IBSP消息列表 实体
	 *
	 * @param id ibsp_cms_notify 主键
	 * @return IBSP消息列表 实体
	 */
	public IbspCmsNotify find(String id) throws Exception {
		return dao.find(IbspCmsNotify.class, id);
	}

	/**
	 * 删除一条消息
	 */
	public void delUserMsg(String cmsNotifyId, Date date) throws SQLException {
		String sql = "UPDATE `ibsp_cms_notify` SET `STATE_`=?,`UPDATE_TIME_`=?, `UPDATE_TIME_LONG_`=? WHERE `IBSP_CMS_NOTIFY_ID_`= ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsStateEnum.DEL.name());
		parameters.add(date);
		parameters.add(System.currentTimeMillis());
		parameters.add(cmsNotifyId);
		dao.execute(sql, parameters);
	}

	/**
	 * 删除全部用户消息
	 */
	public void delAllMessage(String appUserId, Date date) throws SQLException {
		String sql = "UPDATE `ibsp_cms_notify` icn LEFT JOIN ibsp_cms_user_notify icun ON icn.IBSP_CMS_NOTIFY_ID_ = icun.CMS_NOTIFY_ID_ " +
				" SET icn.`STATE_`=?, icn.`UPDATE_TIME_`=? , icn.`UPDATE_TIME_LONG_`=? ,icun.`STATE_`=?,icun.`UPDATE_TIME_` =?,icun.`UPDATE_TIME_LONG_`=? " +
				"  WHERE icun.`USER_ID_`= ? AND icn.`STATE_`=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsStateEnum.DEL.name());
		parameters.add(date);
		parameters.add(System.currentTimeMillis());
		parameters.add(IbsStateEnum.DEL.name());
		parameters.add(date);
		parameters.add(System.currentTimeMillis());
		parameters.add(appUserId);
		parameters.add(IbsStateEnum.OPEN.name());
		dao.execute(sql, parameters);
	}


	/**
	 * 获取顶部公告列表
	 *
	 * @return 获取顶部
	 */
	public List<Map<String, Object>> listTopAnnounce() throws SQLException {
		String sql = "SELECT icn.NOTIFY_TITLE_,icn.IBSP_CMS_NOTIFY_ID_ as CMS_NOTIFY_ID_,ica.NOTIFY_CODE_,ica.CANCEL_TIME_LONG_,ica.CREATE_TIME_LONG_ "
				+ " FROM `ibsp_cms_notify` icn LEFT JOIN ibsp_cms_announce ica ON icn.IBSP_CMS_NOTIFY_ID_ = ica.CMS_NOTIFY_ID_ "
				+ " WHERE icn.NOTIFY_TYPE_ = ? AND ica.state_ = ? AND icn.state_ != ? "
				+ " ORDER BY ica.UPDATE_TIME_LONG_ DESC LIMIT 0,4";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(IbsTypeEnum.ANNOUNCE.name());
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.DEL.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 获取用户提醒
	 *
	 * @param appUserId 用户Id
	 */
	public List<Map<String, Object>> listRemindShow(String appUserId) throws SQLException {
		String sql =
				"SELECT icn.NOTIFY_CONTENT_,icn.CREATE_TIME_LONG_,icr.IBSP_CMS_REMIND_ID_ FROM ibsp_cms_notify icn "
						+ " LEFT JOIN ibsp_cms_remind icr ON icr.CMS_NOTIFY_ID_ = icn.IBSP_CMS_NOTIFY_ID_ "
						+ " WHERE icn.NOTIFY_TYPE_ = ? AND icr.USER_ID_ = ? AND IS_CANCEL_ != ? AND ICN.STATE_ = ? ORDER BY icn.CREATE_TIME_LONG_ DESC";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsTypeEnum.REMIND.name());
		parameters.add(appUserId);
		parameters.add(true);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 获取用户消息列表
	 *
	 * @param userId    用户Id
	 * @param pageIndex 起始页
	 * @param pageSize  页大小
	 * @return 分页信息
	 */
	public PageCoreBean<Map<String, Object>> listNotify(String userId, Integer pageIndex, Integer pageSize) throws SQLException {
		String sql = "SELECT icn.NOTIFY_TITLE_,icn.NOTIFY_CONTENT_,icn.CREATE_TIME_LONG_,icun.IS_READ_ " +
				" ,icun.CMS_NOTIFY_ID_ FROM `ibsp_cms_notify` icn " +
				" LEFT JOIN ibsp_cms_user_notify icun ON icun.CMS_NOTIFY_ID_ = icn.IBSP_CMS_NOTIFY_ID_ " +
				" WHERE icn.NOTIFY_TYPE_ = ? AND icun.USER_ID_= ?  AND ICN.STATE_ = ? ORDER BY icn.UPDATE_TIME_LONG_ desc";
		String sqlCount = "SELECT count(*) FROM( ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsTypeEnum.MESSAGE.name());
		parameters.add(userId);
		parameters.add(IbsStateEnum.OPEN.name());
		sqlCount = sqlCount + sql + ") AS t  ";
		return super.dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
	}

	/**
	 * 提醒列表
	 *
	 * @param notifyTitle 标题
	 * @param userName    创建者
	 * @param notifyState 状态
	 * @param startTime   开始时间
	 * @param endTime     结束时间
	 * @param pageIndex   起始页
	 * @param pageSize    页大小
	 * @return 列表
	 */
	public PageCoreBean<Map<String, Object>> listAllRemind(String notifyTitle, String userName, String notifyState, Long startTime, Long endTime, int pageIndex, int pageSize) throws SQLException {
		String sql = "SELECT icn.NOTIFY_TITLE_,icn.NOTIFY_CONTENT_,icn.CREATE_TIME_LONG_,icn.IBSP_CMS_NOTIFY_ID_," +
				" icr.NOTIFY_CODE_,icr.USER_ID_,icr.USER_NAME_,icr.REMIND_CATEGORY_,icr.STATE_ FROM `ibsp_cms_notify` icn " +
				" LEFT JOIN ibsp_cms_remind icr ON icn.IBSP_CMS_NOTIFY_ID_ = icr.CMS_NOTIFY_ID_ " +
				" WHERE icn.NOTIFY_TYPE_ = ? AND icr.state_!= ? ";
		String sqlCount = "SELECT count(*) FROM( ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsTypeEnum.REMIND.name());
		parameters.add(IbsStateEnum.DEL.name());
		if (StringTool.notEmpty(notifyTitle)) {
			sql += " AND icn.NOTIFY_TITLE_ like ?";
			parameters.add("%" + notifyTitle + "%");
		}
		if (StringTool.notEmpty(userName)) {
			sql += " AND icr.USER_NAME_ like ?";
			parameters.add("%" + userName + "%");
		}
		if (StringTool.notEmpty(notifyState)) {
			sql += " AND icr.STATE_ = ?";
			parameters.add(notifyState);
		}
		if (startTime != null) {
			sql += " AND icn.CREATE_TIME_LONG_ BETWEEN ? AND ? ";
			parameters.add(startTime);
			parameters.add(endTime);
		}
		sql += "ORDER BY icr.UPDATE_TIME_LONG_ desc";
		sqlCount = sqlCount + sql + ") AS t  ";
		return super.dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);

	}

	/**
	 * 公告列表
	 *
	 * @param notifyTitle 标题
	 * @param userName    创建者
	 * @param notifyState 状态
	 * @param startTime   开始时间
	 * @param endTime     结束时间
	 * @param pageIndex   起始页
	 * @param pageSize    页大小
	 * @return 列表
	 */
	public PageCoreBean<Map<String, Object>> listAllAnnounce(String notifyTitle, String userName, String notifyState, Long startTime, Long endTime, int pageIndex, int pageSize) throws SQLException {
		String sql = "SELECT  icn.NOTIFY_TITLE_,icn.CREATE_TIME_LONG_,icn.IBSP_CMS_NOTIFY_ID_ CMS_NOTIFY_ID_,icn.CREATE_USER_ USER_NAME_," +
				" ica.NOTIFY_CODE_,ica.CANCEL_TIME_LONG_,ica.STATE_ FROM `ibsp_cms_notify` icn " +
				" LEFT JOIN ibsp_cms_announce ica ON icn.IBSP_CMS_NOTIFY_ID_ = ica.CMS_NOTIFY_ID_ " +
				" WHERE icn.NOTIFY_TYPE_ = ? AND ica.state_!= ? ";
		String sqlCount = "SELECT count(*) FROM( ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsTypeEnum.ANNOUNCE.name());
		parameters.add(IbsStateEnum.DEL.name());
		if (StringTool.notEmpty(notifyTitle)) {
			sql += " AND icn.NOTIFY_TITLE_ like ?";
			parameters.add("%" + notifyTitle + "%");
		}
		if (StringTool.notEmpty(userName)) {
			sql += " AND icn.CREATE_USER_ like ?";
			parameters.add("%" + userName + "%");
		}
		if (StringTool.notEmpty(notifyState)) {
			sql += " AND ica.STATE_ = ?";
			parameters.add(notifyState);
		}
		if (startTime != null) {
			sql += " AND icn.CREATE_TIME_LONG_ BETWEEN ? AND ? ";
			parameters.add(startTime);
			parameters.add(endTime);
		}
		sql += "ORDER BY ica.UPDATE_TIME_LONG_ desc";
		sqlCount = sqlCount + sql + ") AS t  ";
		return super.dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);

	}

	/**
	 * 通知列表
	 *
	 * @param notifyTitle 标题
	 * @param userName    接收人
	 * @param startTime   开始时间
	 * @param endTime     结束时间
	 * @param pageIndex   起始页
	 * @param pageSize    页大小
	 * @return 列表
	 */
	public PageCoreBean<Map<String, Object>> listAllMessage(String notifyTitle, String userName, Long startTime, Long endTime, Integer pageIndex, Integer pageSize) throws SQLException {

		String sql = "SELECT icn.NOTIFY_TITLE_,icn.NOTIFY_CONTENT_,icn.CREATE_TIME_LONG_,icn.IBSP_CMS_NOTIFY_ID_," +
				" icu.NOTIFY_CODE_,icu.USER_ID_,icu.USER_NAME_,icu.IS_READ_,icu.STATE_ FROM `ibsp_cms_notify` icn " +
				"LEFT JOIN ibsp_cms_user_notify icu ON icn.IBSP_CMS_NOTIFY_ID_ = icu.CMS_NOTIFY_ID_ " +
				"WHERE icn.NOTIFY_TYPE_ = ? AND icu.state_!=? ";
		String sqlCount = "SELECT count(*) FROM( ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsTypeEnum.MESSAGE.name());
		parameters.add(IbsStateEnum.DEL.name());
		if (StringTool.notEmpty(notifyTitle)) {
			sql += " AND icn.NOTIFY_TITLE_ like ?";
			parameters.add("%" + notifyTitle + "%");
		}
		if (StringTool.notEmpty(userName)) {
			sql += " AND icu.USER_NAME_ like ?";
			parameters.add("%" + userName + "%");
		}
		if (startTime != null) {
			sql += " AND icn.CREATE_TIME_LONG_ BETWEEN ? AND ? ";
			parameters.add(startTime);
			parameters.add(endTime);
		}
		sql += "ORDER BY icu.UPDATE_TIME_LONG_ desc";
		sqlCount = sqlCount + sql + ") AS t  ";
		return super.dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
	}

	/**
	 * 查询公告详情
	 */
	public Map<String, Object> announceInfo(String notifyId) throws SQLException {
		String sql = "SELECT icn.CREATE_USER_,icn.NOTIFY_TITLE_,icn.NOTIFY_SITE_,icn.NOTIFY_CONTENT_,icn.NOTIFY_LEVEL_,ica.NOTIFY_CODE_,ica.CANCEL_TIME_LONG_,ica.ANNOUNCE_CHANNEL_,ica.STATE_ " +
				" FROM `ibsp_cms_notify` icn LEFT JOIN ibsp_cms_announce ica on icn.IBSP_CMS_NOTIFY_ID_ = ica.CMS_NOTIFY_ID_ WHERE icn.IBSP_CMS_NOTIFY_ID_ =? AND ica.STATE_!=? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(notifyId);
		parameters.add(IbsStateEnum.DEL.name());
		return super.dao.findMap(sql, parameters);

	}

	/**
	 * 查询提醒详情
	 */
	public Map<String, Object> remindInfo(String notifyId) throws SQLException {
		String sql = "SELECT icn.NOTIFY_TITLE_,icn.NOTIFY_CONTENT_,icn.NOTIFY_LEVEL_,icr.NOTIFY_CODE_,icr.REMIND_CATEGORY_,icr.USER_NAME_,icr.UPDATE_TIME_LONG_,icr.STATE_ " +
				" FROM `ibsp_cms_notify` icn LEFT JOIN ibsp_cms_remind icr on icn.IBSP_CMS_NOTIFY_ID_ = icr.CMS_NOTIFY_ID_ WHERE icn.IBSP_CMS_NOTIFY_ID_ =? AND icr.STATE_!=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(notifyId);
		parameters.add(IbsStateEnum.DEL.name());
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * 查询信息详情
	 */
	public Map<String, Object> messageInfo(String notifyId) throws SQLException {
		String sql = "SELECT icn.NOTIFY_TITLE_,icn.NOTIFY_CONTENT_,icun.NOTIFY_CODE_,icun.IS_READ_,icun.USER_NAME_,icun.STATE_,icun.UPDATE_TIME_LONG_ " +
				" FROM `ibsp_cms_notify` icn LEFT JOIN ibsp_cms_user_notify icun on icn.IBSP_CMS_NOTIFY_ID_ = icun.CMS_NOTIFY_ID_ WHERE icn.IBSP_CMS_NOTIFY_ID_ =? AND icun.STATE_!=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(notifyId);
		parameters.add(IbsStateEnum.DEL.name());
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * 更新提醒消息状态
	 *
	 * @param notifyId 消息主键
	 * @param state    状态
	 * @param date     时间
	 */
	public void updateNotify(IbsTypeEnum notifyType, String notifyId, String state, Date date) throws SQLException {
		List<Object> parameters = new ArrayList<>();
		String tableName = "";

		if (IbsTypeEnum.MESSAGE.equal(notifyType)) {
			tableName = "ibsp_cms_user_notify";
		} else if (IbsTypeEnum.REMIND.equal(notifyType)) {
			tableName = "ibsp_cms_remind";
		}
		if (IbsTypeEnum.ANNOUNCE.equal(notifyType)) {
			tableName = "ibsp_cms_announce";
		}
		String sql = "UPDATE %s SET `UPDATE_TIME_`=?, `UPDATE_TIME_LONG_`=?,STATE_=?  WHERE `CMS_NOTIFY_ID_`=? AND STATE_!=?";

		parameters.add(date);
		parameters.add(System.currentTimeMillis());
		parameters.add(state);
		parameters.add(notifyId);
		parameters.add(IbsStateEnum.DEL.name());
		super.dao.execute(String.format(sql, tableName), parameters);

		sql = "UPDATE `ibsp_cms_notify` SET `UPDATE_TIME_`=?, `UPDATE_TIME_LONG_`=?,`STATE_`=? WHERE `IBSP_CMS_NOTIFY_ID_`= ? AND STATE_!=?";
		super.dao.execute(sql, parameters);
	}


}
