package com.ibm.old.v1.cloud.ibm_cms_message_content.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_cms_message_content.t.entity.IbmCmsMessageContentT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmCmsMessageContentTService extends BaseService {

	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmCmsMessageContentT对象数据
	 */
	public String save(IbmCmsMessageContentT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_cms_message_content的 IBM_CMS_MESSAGE_CONTENT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_cms_message_content set state_='DEL' where IBM_CMS_MESSAGE_CONTENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CMS_MESSAGE_CONTENT_ID_主键id数组的数据
	 * @param idArray 要删除ibm_cms_message_content的 IBM_CMS_MESSAGE_CONTENT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_cms_message_content set state_='DEL' where IBM_CMS_MESSAGE_CONTENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_cms_message_content的 IBM_CMS_MESSAGE_CONTENT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_cms_message_content where IBM_CMS_MESSAGE_CONTENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CMS_MESSAGE_CONTENT_ID_主键id数组的数据
	 * @param idArray 要删除ibm_cms_message_content的 IBM_CMS_MESSAGE_CONTENT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_cms_message_content where IBM_CMS_MESSAGE_CONTENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCmsMessageContentT实体信息
	 * @param entity IbmCmsMessageContentT实体
	 */
	public void update(IbmCmsMessageContentT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_cms_message_content表主键查找IbmCmsMessageContentT实体
	 * @param id ibm_cms_message_content 主键
	 * @return IbmCmsMessageContentT实体
	 */
	public IbmCmsMessageContentT find(String id) throws Exception {
		return (IbmCmsMessageContentT) this.dao.find(IbmCmsMessageContentT. class,id);

	}

	/**
	 * 获取分页Map数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_cms_message_content where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cms_message_content  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cms_message_content  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmCmsMessageContentT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmCmsMessageContentT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_cms_message_content where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cms_message_content  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cms_message_content  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmCmsMessageContentT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_cms_message_content  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmCmsMessageContentT数据信息
	 * @return 可用<IbmCmsMessageContentT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_cms_message_content  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmCmsMessageContentT. class,sql);
	}
	/**
	 * 未读消息数
	 * @param appUserId	用户id
	 * @return
	 */
	public String findUnreadCount(String appUserId) throws SQLException {
		String sql="select count(IBM_CMS_MESSAGE_CONTENT_ID_) from ibm_cms_message_content where RECEIVE_USER_ID_=? and READING_STATE_=?"
				+ " and STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.UNREAD.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return dao.findString("count(IBM_CMS_MESSAGE_CONTENT_ID_)", sql, parameterList);
	}
	/**
	 * 分页信息
	 * @param appUserId	用户id
	 * @param pageIndex	页数
	 * @param pageSize	条数
	 * @return
	 */
	public PageCoreBean findByUserId(String appUserId, int pageIndex, int pageSize) throws SQLException {
		String sql="select IBM_CMS_MESSAGE_CONTENT_ID_,TITLE_,READING_STATE_,CREATE_TIME_LONG_ from ibm_cms_message_content"
				+ " where STATE_ != 'DEL' and RECEIVE_USER_ID_=? order by CREATE_TIME_LONG_ DESC";
		String sqlCount="select count(IBM_CMS_MESSAGE_CONTENT_ID_) from ibm_cms_message_content where"
				+ " STATE_ != 'DEL' and RECEIVE_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		return dao.page(sql, parameterList, pageIndex, pageSize,sqlCount,parameterList);
	}
	/**
	 * 通过id获取消息内容
	 * @param messageContentId	消息内容id
	 * @return
	 */
	public Map<String, Object> findContentById(String messageContentId,String className) throws SQLException {
		String sql="select TITLE_,CONTENT_,CREATE_TIME_ from ibm_cms_message_content where IBM_CMS_MESSAGE_CONTENT_ID_=? and STATE_ != 'DEL'";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(messageContentId);
		Map<String, Object> map=dao.findMap(sql,parameterList);
		if(ContainerTool.isEmpty(map)){
			return null;
		}
		Date nowTime=new Date();
		parameterList.clear();
		//修改阅读状态
		sql="update ibm_cms_message_content set READING_STATE_=?,DESC_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_CMS_MESSAGE_CONTENT_ID_=?";
		parameterList.add(IbmStateEnum.READ.name());
		parameterList.add(className+"修改阅读状态");
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(messageContentId);
		dao.execute(sql, parameterList);
		return map;
	}
	/**
	 * 逻辑删除当前用户所有消息
	 * @param appUserId	用户id
	 */
	public void delAll(String appUserId,String className) throws SQLException {
		String sql="update ibm_cms_message_content set STATE_=?,DESC_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where RECEIVE_USER_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(5);
		Date nowTime=new Date();
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(className+"逻辑删除当前用户所有消息");
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		dao.execute(sql, parameterList);
	}
	/**
	 * 一键已阅
	 * @param appUserId	用户id
	 * @throws SQLException
	 */
	public void updateReadStateAll(String appUserId,String className) throws SQLException {
		String sql="update ibm_cms_message_content set READING_STATE_=?,DESC_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where RECEIVE_USER_ID_=? and STATE_!=? ";
		List<Object> parameterList = new ArrayList<>(5);
		Date nowTime=new Date();
		parameterList.add(IbmStateEnum.READ.name());
		parameterList.add(className+"一键已阅");
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		dao.execute(sql, parameterList);
	}
	/**
	 * 获取主页面系统消息
	 *
	 * @param userId 用户id
	 */
	public List<Map<String, Object>> listHomeInfo(String userId) throws SQLException {
		String sql = "SELECT IBM_CMS_MESSAGE_CONTENT_ID_,TITLE_,CREATE_TIME_LONG_,READING_STATE_ FROM `ibm_cms_message_content` where RECEIVE_USER_ID_ = ? "
				+ " and STATE_ = ? ORDER BY CREATE_TIME_LONG_ desc LIMIT ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(4);
		return super.dao.findMapList(sql,parameterList);
	}
}
