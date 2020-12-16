package all.cms.msg.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.cms.msg.bean.CmsSendBean;
import all.gen.cms_box_msg.t.entity.CmsBoxMsgT;
import all.gen.cms_box_topic.t.entity.CmsBoxTopicT;
import all.gen.cms_msg.t.entity.CmsMsgT;
import all.gen.cms_msg.t.service.CmsMsgTService;
import all.gen.cms_msg_topic.t.entity.CmsMsgTopicT;
import all.gen.cms_send_box_draft.t.entity.CmsSendBoxDraftT;
import all.gen.cms_topic_user.t.entity.CmsTopicUserT;
import c.a.util.core.annotation.AnnotationTable;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.enums.bean.cms.CmsMsgTypeEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.string.StringUtil;
public class CmsMsgService extends CmsMsgTService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 查询消息;
	 * 
	 * @Title:
	 * @Description:
	 *
	 * 				参数说明
	 * @param topicId
	 * @param createTimeLong
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             返回类型 PageCoreBean<Map<String, Object>>
	 */
	public PageCoreBean<Map<String, Object>> findAll(String topicId, String appUserId, Integer pageIndex,
			Integer pageSize) throws Exception {
		PageCoreBean<Map<String, Object>> basePage = findPage(topicId, appUserId, pageIndex, pageSize);
		List<String> boxMsgIdList = new ArrayList<String>();
		List<Map<String, Object>> listMap = basePage.getList();
		for (Map<String, Object> map : listMap) {
			boxMsgIdList.add(map.get("CMS_BOX_MSG_ID_").toString());
		}
		// 更新状态(未读改成已读)
		updateStateNew2OpenByCmsBoxMsg(appUserId, boxMsgIdList);
		this.doTransactionPost();
		basePage = findPage(topicId, appUserId, pageIndex, pageSize);
		return basePage;
	}
	/**
	 * 消息已读,更新状态
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param boxMsgIdList
	 * @throws Exception
	 *             返回类型 void
	 */
	public void updateStateNew2OpenByCmsBoxMsg(String appUserId, List<String> boxMsgIdList) throws Exception {
		if (boxMsgIdList != null && boxMsgIdList.size() > 0) {
			List<Object> parameterList = new ArrayList<Object>();
			parameterList.add(appUserId);
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : boxMsgIdList) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "update cms_box_msg set  STATE_='OPEN' where STATE_='NEW' AND APP_USER_ID_=? and CMS_BOX_MSG_ID_ in("
					+ stringBuffer.toString() + ")";
			dao.execute(sql, parameterList);
		}
	}
	/**
	 * 私聊未读消息数量(总条数)
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param appUserId
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findStateNewCntTotalByCmsBoxMsg(String appUserId) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(TaskStateEnum.NEW.getCode());;
		parameterList.add(CmsMsgTypeEnum.PRIVATE.getCode());
		parameterList.add(appUserId);
		String sql = "SELECT count(*) AS statNewCntTotal " + "FROM cms_box_msg m  "
				+ "WHERE m.state_ =?   AND m.MSG_TYPE_=? AND m.APP_USER_ID_ = ?";
		return dao.findString("statNewCntTotal", sql, parameterList);
	}
	/**
	 * 发送消息
	 * 
	 * @Title: send
	 * @Description:
	 *
	 * 				参数说明
	 * @param title
	 * @param content
	 * @param sendUser
	 * @param receiveUserList
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public void send(CmsSendBean cmsSendBean) throws Exception {
		String type = cmsSendBean.getType();
		String title = cmsSendBean.getTitle();
		String content = cmsSendBean.getContent();
		String sendUser = cmsSendBean.getSendUser();
		String receiveUserList = cmsSendBean.getReceiveUserList();
		Date date = cmsSendBean.getDate();
		String[] receiveUserArray = receiveUserList.split(",");
		if (StringUtil.isBlank(content)) {
			returnCodeJsonTcpBean.setMsg("内容不能为空");
			returnCodeJsonTcpBean.setCodeSys(ReturnCodeEnum.code400.toString());
			returnCodeJsonTcpBean.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
			returnCodeJsonTcpBean.setSuccess(false);
			LogThreadLocal.setLog(returnCodeJsonTcpBean);
		}
		if (StringUtil.isBlank(sendUser)) {
			returnCodeJsonTcpBean.setMsg("输入参数sendUser不能为空");
			returnCodeJsonTcpBean.setSuccess(false);
			LogThreadLocal.setLog(returnCodeJsonTcpBean);
		}
		if (StringUtil.isBlank(receiveUserList)) {
			returnCodeJsonTcpBean.setMsg("接收人Id不能为空");
			returnCodeJsonTcpBean.setCodeSys(ReturnCodeEnum.code400.toString());
			returnCodeJsonTcpBean.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
			returnCodeJsonTcpBean.setSuccess(false);
			LogThreadLocal.setLog(returnCodeJsonTcpBean);

		}
		if (receiveUserArray.length == 1) {
			// 发送者跟接收人不能相同
			if (sendUser.trim().equals(receiveUserArray[0].trim())) {
				returnCodeJsonTcpBean.setCode(ReturnCodeEnum.app409CmsSend.getCode());
				returnCodeJsonTcpBean.setMsg(ReturnCodeEnum.app409CmsSend.getMsgCn());
				returnCodeJsonTcpBean.setCodeSys(ReturnCodeEnum.code409.getCode());
				returnCodeJsonTcpBean.setMsgSys(ReturnCodeEnum.code409.getMsgCn());
				returnCodeJsonTcpBean.setSuccess(false);
				LogThreadLocal.setLog(returnCodeJsonTcpBean);

			}
		}
		if (date == null) {
			date = new Date();
			cmsSendBean.setDate(date);
		}
		// 取内容前十个字作为标题
		AssertUtil.isBlank(content, "内容不能为空");
		if (StringUtil.isBlank(title)) {
			if (content.length() > 10) {
				title = content.substring(0, 10);
			} else {
				title = content;
			}
			cmsSendBean.setTitle(title);
		}
		// 得到表名
		AnnotationTable topicTable = (AnnotationTable) CmsMsgTopicT.class.getAnnotation(AnnotationTable.class);
		String topicTableName = topicTable.name();
		cmsSendBean.setTopicTableName(topicTableName);
		// 得到表名
		AnnotationTable msgTable = (AnnotationTable) CmsMsgT.class.getAnnotation(AnnotationTable.class);
		String msgTableName = msgTable.name();
		cmsSendBean.setMsgTableName(msgTableName);
		if (StringUtil.isBlank(type)) {
			cmsSendBean.setType(CmsMsgTypeEnum.PRIVATE.getCode());
		}
		CmsSendBoxDraftT cmsSendBoxDraftT = new CmsSendBoxDraftT();
		cmsSendBoxDraftT.setTableName(topicTableName);
		cmsSendBoxDraftT.setTopicType(type);
		cmsSendBoxDraftT.setTopicTitle(title);
		cmsSendBoxDraftT.setCmsContent(content);
		cmsSendBoxDraftT.setSendUser(sendUser);
		cmsSendBoxDraftT.setReceiveUserList(receiveUserList);
		cmsSendBoxDraftT.setState(TaskStateEnum.OPEN.getCode());
		cmsSendBoxDraftT.setCreateTime(date);
		cmsSendBoxDraftT.setCreateTimeLong(date.getTime());
		cmsSendBoxDraftT.setUpdateTime(date);
		cmsSendBoxDraftT.setUpdateTimeLong(date.getTime());
		String cmsSendBoxDraftTId = dao.save(cmsSendBoxDraftT);
		for (String receiveUser : receiveUserArray) {
			if (sendUser.trim().equals(receiveUser.trim())) {
				continue;
			}
			saveOrUpdate(cmsSendBean, receiveUser, cmsSendBoxDraftTId);
		}

	}
	/**
	 * 
	 * 发布主题;
	 * 
	 * 更新主题,发布消息;
	 * 
	 * @throws Exception
	 */
	public void saveOrUpdate(CmsSendBean cmsSendBean, String receiveUser, String cmsSendBoxDraftTId) throws Exception {
		String sendUser = cmsSendBean.getSendUser();
		String type = cmsSendBean.getType();
		AssertUtil.isBlank(type, "消息类型不能为空");
		/**
		 * 1 是否有主题 2 没有主题 3 创建主题 5 创建cms_topic_user
		 * 
		 */
		/**
		 * 1 是否有主题 2 有主题 3 更新主题 5 不需要更新cms_topic_user
		 * 
		 */
		// 查询用户与主题
		String cmsTopicId = this.findCmsTopicId(type, sendUser, receiveUser);
		if (StringUtil.isBlank(cmsTopicId)) {
			this.save(cmsSendBean, receiveUser, cmsSendBoxDraftTId);
		} else {
			this.update(cmsTopicId, cmsSendBean, receiveUser);
		}
	}
	/**
	 * 
	 * 新增主题或者消息
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(CmsSendBean cmsSendBean, String receiveUser, String cmsSendBoxDraftTId) throws Exception {
		String type = cmsSendBean.getType();
		String title = cmsSendBean.getTitle();
		String content = cmsSendBean.getContent();
		String sendUser = cmsSendBean.getSendUser();
		String topicTableName = cmsSendBean.getTopicTableName();
		String msgTableName = cmsSendBean.getMsgTableName();
		Date date = cmsSendBean.getDate();
		CmsTopicUserT cmsTopicUserT = new CmsTopicUserT();
		CmsMsgTopicT cmsMsgTopicT = new CmsMsgTopicT();
		CmsMsgT cmsMsgT = new CmsMsgT();
		CmsBoxTopicT cmsSendBoxTopicT = new CmsBoxTopicT();
		CmsBoxTopicT cmsReceiveBoxTopicT = new CmsBoxTopicT();
		CmsBoxMsgT cmsSendBoxMsgT = new CmsBoxMsgT();
		CmsBoxMsgT cmsReceiveBoxMsgT = new CmsBoxMsgT();
		// 主题
		cmsMsgTopicT.setMsgTopicType(type);
		cmsMsgTopicT.setMsgTopicTitle(title);
		cmsMsgTopicT.setCmsContent(content);
		cmsMsgTopicT.setSendUser(sendUser);
		cmsMsgTopicT.setReceiveUser(receiveUser);
		cmsMsgTopicT.setState(TaskStateEnum.OPEN.getCode());
		cmsMsgTopicT.setCreateTime(date);
		cmsMsgTopicT.setCreateTimeLong(date.getTime());
		cmsMsgTopicT.setUpdateTime(date);
		cmsMsgTopicT.setUpdateTimeLong(date.getTime());
		String cmsMsgTopicId = dao.save(cmsMsgTopicT);
		// 发件箱主题(私聊更新，其它消息类型不更新)
		cmsSendBoxTopicT.setAppUserId(sendUser);
		cmsSendBoxTopicT.setCmsSendBoxDraftId(cmsSendBoxDraftTId);
		cmsSendBoxTopicT.setCmsMsgTopicId(cmsMsgTopicId);
		cmsSendBoxTopicT.setTableName(topicTableName);
		cmsSendBoxTopicT.setTableId(cmsMsgTopicId);
		cmsSendBoxTopicT.setTopicType(type);
		cmsSendBoxTopicT.setTopicTitle(title);
		cmsSendBoxTopicT.setCmsContent(content);
		cmsSendBoxTopicT.setSendUser(sendUser);
		cmsSendBoxTopicT.setReceiveUser(receiveUser);
		cmsSendBoxTopicT.setState(TaskStateEnum.NEW.getCode());
		cmsSendBoxTopicT.setCreateTime(date);
		cmsSendBoxTopicT.setCreateTimeLong(date.getTime());
		cmsSendBoxTopicT.setUpdateTime(date);
		cmsSendBoxTopicT.setUpdateTimeLong(date.getTime());
		String cmsSendBoxTopicId = dao.save(cmsSendBoxTopicT);
		// 收件箱主题(私聊更新，其它消息类型不更新)
		cmsReceiveBoxTopicT.setAppUserId(receiveUser);
		cmsReceiveBoxTopicT.setCmsSendBoxDraftId(cmsSendBoxDraftTId);
		cmsReceiveBoxTopicT.setCmsMsgTopicId(cmsMsgTopicId);
		cmsReceiveBoxTopicT.setTableName(topicTableName);
		cmsReceiveBoxTopicT.setTableId(cmsMsgTopicId);
		cmsReceiveBoxTopicT.setTopicType(type);
		cmsReceiveBoxTopicT.setTopicTitle(title);
		cmsReceiveBoxTopicT.setCmsContent(content);
		cmsReceiveBoxTopicT.setSendUser(sendUser);
		cmsReceiveBoxTopicT.setReceiveUser(receiveUser);
		cmsReceiveBoxTopicT.setState(TaskStateEnum.NEW.getCode());
		cmsReceiveBoxTopicT.setCreateTime(date);
		cmsReceiveBoxTopicT.setCreateTimeLong(date.getTime());
		cmsReceiveBoxTopicT.setUpdateTime(date);
		cmsReceiveBoxTopicT.setUpdateTimeLong(date.getTime());
		String cmsReceiveBoxTopicId = dao.save(cmsReceiveBoxTopicT);
		// 帖子
		cmsMsgT.setCmsMsgTopicId(cmsMsgTopicId);
		cmsMsgT.setMsgType(type);
		cmsMsgT.setMsgTitle(title);
		cmsMsgT.setCmsContent(content);
		cmsMsgT.setSendUser(sendUser);
		cmsMsgT.setReceiveUser(receiveUser);
		cmsMsgT.setState(TaskStateEnum.OPEN.getCode());
		cmsMsgT.setCreateTime(date);
		cmsMsgT.setCreateTimeLong(date.getTime());
		cmsMsgT.setUpdateTime(date);
		cmsMsgT.setUpdateTimeLong(date.getTime());
		String cmsMsgId = dao.save(cmsMsgT);
		// 发件箱 帖子
		cmsSendBoxMsgT.setExtTableName(cmsSendBean.getExtTableName());
		cmsSendBoxMsgT.setExtTableId(cmsSendBean.getExtTableId());
		cmsSendBoxMsgT.setExtTableDesc(cmsSendBean.getExtTableDesc());
		cmsSendBoxMsgT.setAppUserId(sendUser);
		cmsSendBoxMsgT.setCmsBoxTopicId(cmsSendBoxTopicId);
		cmsSendBoxMsgT.setCmsMsgTopicId(cmsMsgTopicId);
		cmsSendBoxMsgT.setTableName(msgTableName);
		cmsSendBoxMsgT.setTableId(cmsMsgId);
		cmsSendBoxMsgT.setMsgType(type);
		cmsSendBoxMsgT.setMsgTitle(title);
		cmsSendBoxMsgT.setCmsContent(content);
		cmsSendBoxMsgT.setSendUser(sendUser);
		cmsSendBoxMsgT.setReceiveUser(receiveUser);
		cmsSendBoxMsgT.setState(TaskStateEnum.NEW.getCode());
		cmsSendBoxMsgT.setCreateTime(date);
		cmsSendBoxMsgT.setCreateTimeLong(date.getTime());
		cmsSendBoxMsgT.setUpdateTime(date);
		cmsSendBoxMsgT.setUpdateTimeLong(date.getTime());
		dao.save(cmsSendBoxMsgT);
		// 收件箱帖子
		cmsReceiveBoxMsgT.setExtTableName(cmsSendBean.getExtTableName());
		cmsReceiveBoxMsgT.setExtTableId(cmsSendBean.getExtTableId());
		cmsReceiveBoxMsgT.setExtTableDesc(cmsSendBean.getExtTableDesc());
		cmsReceiveBoxMsgT.setAppUserId(receiveUser);
		cmsReceiveBoxMsgT.setCmsBoxTopicId(cmsReceiveBoxTopicId);
		cmsReceiveBoxMsgT.setCmsMsgTopicId(cmsMsgTopicId);
		cmsReceiveBoxMsgT.setTableName(msgTableName);
		cmsReceiveBoxMsgT.setTableId(cmsMsgId);
		cmsReceiveBoxMsgT.setMsgType(type);
		cmsReceiveBoxMsgT.setMsgTitle(title);
		cmsReceiveBoxMsgT.setCmsContent(content);
		cmsReceiveBoxMsgT.setSendUser(sendUser);
		cmsReceiveBoxMsgT.setReceiveUser(receiveUser);
		cmsReceiveBoxMsgT.setState(TaskStateEnum.NEW.getCode());
		cmsReceiveBoxMsgT.setCreateTime(date);
		cmsReceiveBoxMsgT.setCreateTimeLong(date.getTime());
		cmsReceiveBoxMsgT.setUpdateTime(date);
		cmsReceiveBoxMsgT.setUpdateTimeLong(date.getTime());
		dao.save(cmsReceiveBoxMsgT);
		// 用户与主题(权限访问)
		cmsTopicUserT.setTopicType(type);
		cmsTopicUserT.setTableName(topicTableName);
		cmsTopicUserT.setCreateTime(date);
		cmsTopicUserT.setCreateTimeLong(date.getTime());
		cmsTopicUserT.setUpdateTime(date);
		cmsTopicUserT.setUpdateTimeLong(date.getTime());
		// sendUser
		cmsTopicUserT.setAppUserId(sendUser);
		cmsTopicUserT.setCmsTopicId(cmsMsgTopicId);
		dao.save(cmsTopicUserT);
		// receiveUser
		cmsTopicUserT.setAppUserId(receiveUser);
		cmsTopicUserT.setCmsTopicId(cmsMsgTopicId);
		dao.save(cmsTopicUserT);
		return cmsMsgTopicId;
	}
	/**
	 * 
	 * 更新消息主题和发消息
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String update(String cmsTopicId, CmsSendBean cmsSendBean, String receiveUser) throws Exception {
		String type = cmsSendBean.getType();
		String title = cmsSendBean.getTitle();
		String content = cmsSendBean.getContent();
		String sendUser = cmsSendBean.getSendUser();
		String topicTableName = cmsSendBean.getTopicTableName();
		String msgTableName = cmsSendBean.getMsgTableName();
		Date date = cmsSendBean.getDate();
		CmsMsgTopicT cmsMsgTopicT = new CmsMsgTopicT();
		CmsMsgT cmsMsgT = new CmsMsgT();
		CmsBoxMsgT cmsSendBoxMsgT = new CmsBoxMsgT();
		CmsBoxMsgT cmsReceiveBoxMsgT = new CmsBoxMsgT();
		// 更新主题(最新的消息)
		cmsMsgTopicT.setCmsMsgTopicId(cmsTopicId);
		cmsMsgTopicT.setMsgTopicType(type);
		cmsMsgTopicT.setMsgTopicTitle(title);
		cmsMsgTopicT.setCmsContent(content);
		cmsMsgTopicT.setSendUser(sendUser);
		cmsMsgTopicT.setReceiveUser(receiveUser);
		cmsMsgTopicT.setState(TaskStateEnum.OPEN.getCode());
		cmsMsgTopicT.setUpdateTime(date);
		cmsMsgTopicT.setUpdateTimeLong(date.getTime());
		dao.update(cmsMsgTopicT);
		// 发件箱更新主题(最新的消息)
		CmsBoxTopicT cmsSendBoxTopicT = this.findCmsBoxTopicT(topicTableName, cmsTopicId, sendUser);
		if (CmsMsgTypeEnum.PRIVATE.getCode().equals(type)) {
			cmsSendBoxTopicT.setTopicTitle(title);
			cmsSendBoxTopicT.setCmsContent(content);
			cmsSendBoxTopicT.setState(TaskStateEnum.NEW.getCode());
			cmsSendBoxTopicT.setUpdateTime(date);
			cmsSendBoxTopicT.setUpdateTimeLong(date.getTime());
			dao.update(cmsSendBoxTopicT);
		}
		// 收件箱更新主题(最新的消息)
		CmsBoxTopicT cmsReceiveBoxTopicT = this.findCmsBoxTopicT(topicTableName, cmsTopicId, receiveUser);
		if (CmsMsgTypeEnum.PRIVATE.getCode().equals(type)) {
			cmsReceiveBoxTopicT.setTopicTitle(title);
			cmsReceiveBoxTopicT.setCmsContent(content);
			cmsReceiveBoxTopicT.setState(TaskStateEnum.NEW.getCode());
			cmsReceiveBoxTopicT.setUpdateTime(date);
			cmsReceiveBoxTopicT.setUpdateTimeLong(date.getTime());
			dao.update(cmsReceiveBoxTopicT);
		}
		// 帖子
		cmsMsgT.setCmsMsgTopicId(cmsTopicId);
		cmsMsgT.setMsgType(type);
		cmsMsgT.setMsgTitle(title);
		cmsMsgT.setCmsContent(content);
		cmsMsgT.setSendUser(sendUser);
		cmsMsgT.setReceiveUser(receiveUser);
		cmsMsgT.setState(TaskStateEnum.OPEN.getCode());
		cmsMsgT.setCreateTime(date);
		cmsMsgT.setCreateTimeLong(date.getTime());
		cmsMsgT.setUpdateTime(date);
		cmsMsgT.setUpdateTimeLong(date.getTime());
		String cmsMsgId = dao.save(cmsMsgT);
		// 发件箱帖子(私聊更新，其它消息类型不更新)
		cmsSendBoxMsgT.setExtTableName(cmsSendBean.getExtTableName());
		cmsSendBoxMsgT.setExtTableId(cmsSendBean.getExtTableId());
		cmsSendBoxMsgT.setExtTableDesc(cmsSendBean.getExtTableDesc());
		cmsSendBoxMsgT.setAppUserId(sendUser);
		cmsSendBoxMsgT.setCmsBoxTopicId(cmsSendBoxTopicT.getCmsBoxTopicId());
		cmsSendBoxMsgT.setCmsMsgTopicId(cmsTopicId);
		cmsSendBoxMsgT.setTableName(msgTableName);
		cmsSendBoxMsgT.setTableId(cmsMsgId);
		cmsSendBoxMsgT.setMsgType(type);
		cmsSendBoxMsgT.setMsgTitle(title);
		cmsSendBoxMsgT.setCmsContent(content);
		cmsSendBoxMsgT.setSendUser(sendUser);
		cmsSendBoxMsgT.setReceiveUser(receiveUser);
		cmsSendBoxMsgT.setState(TaskStateEnum.NEW.getCode());
		cmsSendBoxMsgT.setCreateTime(date);
		cmsSendBoxMsgT.setCreateTimeLong(date.getTime());
		cmsSendBoxMsgT.setUpdateTime(date);
		cmsSendBoxMsgT.setUpdateTimeLong(date.getTime());
		dao.save(cmsSendBoxMsgT);
		// 收件箱帖子(私聊更新，其它消息类型不更新)
		cmsReceiveBoxMsgT.setExtTableName(cmsSendBean.getExtTableName());
		cmsReceiveBoxMsgT.setExtTableId(cmsSendBean.getExtTableId());
		cmsReceiveBoxMsgT.setExtTableDesc(cmsSendBean.getExtTableDesc());
		cmsReceiveBoxMsgT.setAppUserId(receiveUser);
		cmsReceiveBoxMsgT.setCmsBoxTopicId(cmsReceiveBoxTopicT.getCmsBoxTopicId());
		cmsReceiveBoxMsgT.setCmsMsgTopicId(cmsTopicId);
		cmsReceiveBoxMsgT.setTableName(msgTableName);
		cmsReceiveBoxMsgT.setTableId(cmsMsgId);
		cmsReceiveBoxMsgT.setMsgType(type);
		cmsReceiveBoxMsgT.setMsgTitle(title);
		cmsReceiveBoxMsgT.setCmsContent(content);
		cmsReceiveBoxMsgT.setSendUser(sendUser);
		cmsReceiveBoxMsgT.setReceiveUser(receiveUser);
		cmsReceiveBoxMsgT.setState(TaskStateEnum.NEW.getCode());
		cmsReceiveBoxMsgT.setCreateTime(date);
		cmsReceiveBoxMsgT.setCreateTimeLong(date.getTime());
		cmsReceiveBoxMsgT.setUpdateTime(date);
		cmsReceiveBoxMsgT.setUpdateTimeLong(date.getTime());
		dao.save(cmsReceiveBoxMsgT);
		return cmsTopicId;
	}
	/**
	 * 查询消息;
	 * 
	 * @Title:
	 * @Description:
	 *
	 * 				参数说明
	 * @param topicId
	 * @param createTimeLong
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             返回类型 PageCoreBean<Map<String, Object>>
	 */
	public PageCoreBean<Map<String, Object>> findPage(String topicId, String appUserId, Integer pageIndex,
			Integer pageSize) throws Exception {
		List<Object> parameterListCount = new ArrayList<Object>();
		parameterListCount.add(TaskStateEnum.NEW.getCode());
		parameterListCount.add(TaskStateEnum.OPEN.getCode());
		parameterListCount.add(CmsMsgTypeEnum.PRIVATE.getCode());
		parameterListCount.add(topicId);
		parameterListCount.add(appUserId);
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(TaskStateEnum.NEW.getCode());
		parameterList.add(TaskStateEnum.OPEN.getCode());
		parameterList.add(CmsMsgTypeEnum.PRIVATE.getCode());
		parameterList.add(topicId);
		parameterList.add(appUserId);
		String sqlCount = null;
		String sql = null;
		sqlCount = "SELECT count(*) from cms_box_msg m where  (m.STATE_=? or m.STATE_=? ) and m.MSG_TYPE_=? "
				+ " and m.CMS_MSG_TOPIC_ID_=? and  m.APP_USER_ID_=? ";
		sql = "SELECT 	u1.NICKNAME_ AS NICKNAME_SEND_,u1.HEAD_PORTRAIT_ AS HEAD_PORTRAIT_SEND_,"
				+ " u2.NICKNAME_ AS NICKNAME_RECEIVE_,u2.HEAD_PORTRAIT_ AS HEAD_PORTRAIT_RECEIVE_," + " m.* "
				+ "FROM cms_box_msg m " + "LEFT JOIN app_user u1 ON m.SEND_USER_ = u1.APP_USER_ID_ "
				+ "LEFT JOIN app_user u2 ON m.RECEIVE_USER_ = u2.APP_USER_ID_  "
				+ "where (m.STATE_=? or m.STATE_=? ) and m.MSG_TYPE_=? and m.CMS_MSG_TOPIC_ID_=?  and m.APP_USER_ID_=?  order by m.UPDATE_TIME_LONG_ desc";
		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameterList, pageIndex.intValue(),
				pageSize.intValue(), sqlCount, parameterListCount);
		return basePage;
	}

	/**
	 * 通过发送者id和接收者id查找最近的聊天消息;
	 * 
	 * @Title: findCmsMsg
	 * @Description:
	 *
	 * 				参数说明
	 * @param sendUser
	 * @param receiveUser
	 * @return
	 * @throws Exception
	 *             返回类型 Map<String,Object>
	 */
	public Map<String, Object> findCmsMsg(String sendUser, String receiveUser) throws Exception {
		// 查询CmsBoxTopicId
		String cmsBoxTopicId = this.findCmsBoxTopicIdByAppUserId(sendUser, receiveUser);
		if (StringUtil.isBlank(cmsBoxTopicId)) {
			return null;
		} else {
			return this.findCmsMsgByTopicId(cmsBoxTopicId);
		}
	}
	/**
	 * 通过主题id查找最近的聊天消息;
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param cmsBoxTopicId
	 * @return
	 * @throws Exception
	 *             返回类型 Map<String,Object>
	 */
	public Map<String, Object> findCmsMsgByTopicId(String cmsBoxTopicId) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(CmsMsgTypeEnum.PRIVATE.getCode());
		parameterList.add(cmsBoxTopicId);
		String sql = "SELECT 	u1.NICKNAME_ AS NICKNAME_SEND_,u1.HEAD_PORTRAIT_ AS HEAD_PORTRAIT_SEND_,"
				+ " u2.NICKNAME_ AS NICKNAME_RECEIVE_,u2.HEAD_PORTRAIT_ AS HEAD_PORTRAIT_RECEIVE_," + " m.* "
				+ "FROM cms_box_msg m " + "LEFT JOIN app_user u1 ON m.SEND_USER_ = u1.APP_USER_ID_ "
				+ "LEFT JOIN app_user u2 ON m.RECEIVE_USER_ = u2.APP_USER_ID_  "
				+ "where m.MSG_TYPE_=? and m.CMS_BOX_TOPIC_ID_=?  order by m.CREATE_TIME_LONG_ desc";
		Map<String, Object> map = dao.findMap(sql, parameterList);
		return map;
	}

	/**
	 * 
	 * 查询主题CMS_TOPIC_ID_
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findCmsTopicId(String type, String sendUser, String receiveUser) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "SELECT t1.APP_USER_ID_ AS APP_USER_ID_SEND_, t2.APP_USER_ID_ AS APP_USER_ID_RECEIVE_,"
				+ "t1.CMS_TOPIC_ID_ FROM cms_topic_user t1 "
				+ "LEFT JOIN cms_topic_user t2 ON t1.CMS_TOPIC_ID_ = t2.CMS_TOPIC_ID_ "
				+ "WHERE t1.TOPIC_TYPE_=? AND  t1.APP_USER_ID_ =? AND t2.APP_USER_ID_ =?";
		parameterList.add(type);
		parameterList.add(sendUser);
		parameterList.add(receiveUser);
		Map<String, Object> cmsTopicUserT = this.dao.findMap(sql, parameterList);
		if (cmsTopicUserT == null) {
			return null;
		} else {
			String cmsTopicId = cmsTopicUserT.get("CMS_TOPIC_ID_").toString();
			return cmsTopicId;
		}
	}
	/**
	 * 
	 * 
	 * 通过发送者id和接收者id查询未读消息数量(会话主题下)
	 * 
	 * @Title: findCmsBoxTopicId
	 * @Description:
	 *
	 * 				参数说明
	 * @param appUserId
	 *            本人
	 * @param userId
	 *            聊天的AppUserId
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findStateNewCntByAppUserId(String appUserId, String userId) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "SELECT count(*) as stateNewCnt FROM cms_box_msg m WHERE " + " m.MSG_TYPE_=?  AND m.STATE_=?  "
				+ " AND m.APP_USER_ID_ = ? " + " AND (m.SEND_USER_ = ? OR m.RECEIVE_USER_ = ? )";
		parameterList.add(CmsMsgTypeEnum.PRIVATE.getCode());
		parameterList.add(TaskStateEnum.NEW.getCode());
		parameterList.add(appUserId);
		parameterList.add(userId);
		parameterList.add(userId);
		String stateNewCnt = this.dao.findString("stateNewCnt", sql, parameterList);
		return stateNewCnt;
	}
	/**
	 * 通过发送者id和接收者id查询主题CMS_BOX_TOPIC_ID_
	 * 
	 * @Title: findCmsBoxTopicId
	 * @Description:
	 *
	 * 				参数说明
	 * @param appUserId
	 *            本人
	 * @param userId
	 *            聊天的AppUserId
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findCmsBoxTopicIdByAppUserId(String appUserId, String userId) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "SELECT t.CMS_BOX_TOPIC_ID_ FROM cms_box_topic t " + "WHERE t.APP_USER_ID_ = ? "
				+ "AND (t.SEND_USER_ = ? OR t.RECEIVE_USER_ = ?)";
		parameterList.add(appUserId);
		parameterList.add(userId);
		parameterList.add(userId);
		Map<String, Object> cmsTopicUserT = this.dao.findMap(sql, parameterList);
		if (cmsTopicUserT == null) {
			return null;
		} else {
			String cmsTopicId = cmsTopicUserT.get("CMS_BOX_TOPIC_ID_").toString();
			return cmsTopicId;
		}
	}
	/**
	 * 
	 * 查询CmsBoxTopicT
	 * 
	 * @return
	 * @throws Exception
	 */
	public CmsBoxTopicT findCmsBoxTopicT(String tableName, String tableId, String appUserId) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "select * from cms_box_topic where TABLE_NAME_=? and TABLE_ID_=? " + " and APP_USER_ID_=?";
		parameterList.add(tableName);
		parameterList.add(tableId);
		parameterList.add(appUserId);
		CmsBoxTopicT entity = (CmsBoxTopicT) this.dao.findObject(CmsBoxTopicT.class, sql, parameterList);
		return entity;
	}

}
