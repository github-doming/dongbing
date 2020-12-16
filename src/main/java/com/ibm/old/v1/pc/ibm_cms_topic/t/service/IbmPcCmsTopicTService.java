package com.ibm.old.v1.pc.ibm_cms_topic.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.old.v1.cloud.ibm_cms_topic.t.service.IbmCmsTopicTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmPcCmsTopicTService extends IbmCmsTopicTService {
	/**
	 * 获取未读消息数量
	 *
	 * @param userId 用户id
	 * @return 未读消息数量
	 */
	@Override
	public int findUnread(String userId) throws SQLException {
		String sql = "SELECT count(*) as cnt FROM ibm_cms_message_content where RECEIVE_USER_ID_ = ? and READING_STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.UNREAD.getVal());
		String count = super.dao.findString("cnt",sql,parameterList);
		return Integer.parseInt(count);
	}

	/**
	 * 获取主页面系统消息
	 *
	 * @param userId 用户id
	 */
	public List<Map<String, Object>> listHomeInfo(String userId) throws SQLException {
		String sql = "SELECT IBM_CMS_TOPIC_ID_,TITLE_,CREATE_TIME_LONG_,READ_STATE_ FROM `ibm_cms_message` where USER_ID_ = ? "
				+ " and state_ = ? ORDER BY READ_STATE_,CREATE_TIME_LONG_ desc LIMIT ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(4);
		return super.dao.findMapList(sql,parameterList);
	}

	/**
	 * @param appUserId 用户id
	 * @param pageIndex 当前页
	 * @param pageSize 每页行数
	 * @return 消息分页
	 * @Description: 根据用户ID查询
	 * <p>
	 * 参数说明
	 */
	public PageCoreBean listByUserId(String appUserId, int pageIndex, int pageSize) throws SQLException {
		List<Object> parameterList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlCount = new StringBuilder();
		sql.append("select IBM_CMS_TOPIC_ID_,TOPIC_ID_,TITLE_,READ_STATE_,TOPIC_CREATE_TIME_ from ibm_cms_message where STATE_ != 'DEL' ");
		sqlCount.append("select count(IBM_CMS_TOPIC_ID_) from ibm_cms_message where STATE_ != 'DEL' ");
		if(!StringTool.isEmpty(appUserId)){
			sql.append("and USER_ID_=? ");
			sqlCount.append("and USER_ID_=? ");
			parameterList.add(appUserId);
		}
		sql.append("order by READ_STATE_, TOPIC_CREATE_TIME_LONG_ desc");
		return dao.page(sql.toString(), parameterList, pageIndex, pageSize,sqlCount.toString(),parameterList);
	}

	/**
	 * @param pageIndex 当前页
	 * @param pageSize 每页行数
	 * @return 消息分页
	 * @Description: 根据用户ID查询
	 * <p>
	 * 参数说明
	 */
	public PageCoreBean listByUserId(int pageIndex, int pageSize) throws SQLException {
		String sql =
				"select ict.IBM_CMS_TOPIC_ID_,u.APP_USER_NAME_,ict.TITLE_,ict.TOPIC_CREATE_USER_NAME_,ict.TOPIC_CREATE_TIME_ from "
						+ "ibm_cms_message ict LEFT JOIN app_user u on ict.USER_ID_ = u.APP_USER_ID_ where ict.STATE_ != 'DEL'"
						+ " and u.STATE_ != 'DEL' order by ict.TOPIC_CREATE_TIME_LONG_ desc ";
		String sqlCount = "select count(ict.IBM_CMS_TOPIC_ID_) from ibm_cms_message ict LEFT JOIN app_user u on ict.USER_ID_ = u.APP_USER_ID_ where ict.STATE_ != 'DEL' and u.STATE_ != 'DEL' order by ict.TOPIC_CREATE_TIME_LONG_";
		return dao.page(sql, null, pageIndex, pageSize, sqlCount, null);
	}

	/**
	 * @param appUserId 用户id
	 * @return 未读消息数
	 * @Description: 未读消息数
	 * <p>
	 * 参数说明
	 */
	public String findCountByReadState(String appUserId) throws SQLException {
		List<Object> parameterList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select count(IBM_CMS_TOPIC_ID_) from ibm_cms_message where STATE_ != 'DEL' and READ_STATE_ = 1 ");
		if(!StringTool.isEmpty(appUserId)){
			sql.append("and USER_ID_=? ");
			parameterList.add(appUserId);
		}
		return dao.findString("count(IBM_CMS_TOPIC_ID_)", sql.toString(), parameterList);
	}

	/**
	 * @param userId 用户id
	 * @Description: 清空消息
	 */
	public void delAll(String userId) throws SQLException {
		String sql = "update ibm_cms_message ict LEFT JOIN cms_topic ct on ct.CMS_TOPIC_ID_ = ict.TOPIC_ID_ set ict.state_=? where USER_ID_ =? AND ict.READ_STATE_= ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(userId);
		parameterList.add(2);
		dao.execute(sql, parameterList);
	}

	/**
	 * @param userId 用户id
	 * @throws SQLException 返回类型 void
	 * @Description: 一键已阅
	 */
	public void updateReadStateAll(String userId) throws SQLException {
		String sql = "update ibm_cms_message ict LEFT JOIN cms_topic ct on ct.CMS_TOPIC_ID_ = ict.TOPIC_ID_ set READ_STATE_ = ?  where ct.state_ != ? and USER_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.READ.getVal());
		parameterList.add(IbmStateEnum.UNREAD.name());
		parameterList.add(userId);
		dao.execute(sql, parameterList);
	}

	/**
	 * @param userId  用户ID
	 * @param topicId 主题ID
	 * @Description: 修改阅读状态
	 * <p>
	 * 参数说明
	 */
	public void updateReadState(String userId, String topicId) throws SQLException {
		String sql = "UPDATE ibm_cms_message ict LEFT JOIN cms_topic ct ON ct.CMS_TOPIC_ID_ = ict.TOPIC_ID_ SET READ_STATE_ = ? WHERE USER_ID_ = ? AND TOPIC_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.READ.getVal());
		parameterList.add(userId);
		parameterList.add(topicId);
		dao.execute(sql, parameterList);
	}

}
