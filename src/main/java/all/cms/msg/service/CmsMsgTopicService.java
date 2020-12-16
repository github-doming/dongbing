package all.cms.msg.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.cms_msg_topic.t.service.CmsMsgTopicTService;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.enums.bean.cms.CmsMsgTypeEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
public class CmsMsgTopicService extends CmsMsgTopicTService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 查询消息(未读消息);
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
	public PageCoreBean<Map<String, Object>> findAll(String appUserId, Integer pageIndex, Integer pageSize)
			throws Exception {
		PageCoreBean<Map<String, Object>> page = this.findPage(appUserId, pageIndex, pageSize);
		List<Map<String, Object>> listMap = page.getList();
		for (Map<String, Object> map : listMap) {
			String topicId = map.get("CMS_MSG_TOPIC_ID_").toString();
			String stateNewCnt = this.findStateNewCntByCmsBoxMsg(appUserId, topicId);
			map.put("stateNewCnt", stateNewCnt);
		}
		return page;
	}
	/**
	 * 未读消息数量(会话主题下)
	 * 
	 * @Title: findStateNewCntByCmsBoxMsg
	 * @Description:
	 *
	 * 				参数说明
	 * @param appUserId
	 * @param cmsMsgTopicId
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findStateNewCntByCmsBoxMsg(String appUserId, String cmsMsgTopicId) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(TaskStateEnum.NEW.getCode());;
		parameterList.add(CmsMsgTypeEnum.PRIVATE.getCode());
		parameterList.add(appUserId);
		parameterList.add(cmsMsgTopicId);
		String sql = "SELECT count(*) AS  stateNewCnt " + "FROM cms_box_msg m  "
				+ "WHERE m.state_ =? AND m.MSG_TYPE_=? AND m.APP_USER_ID_ = ? AND m.CMS_MSG_TOPIC_ID_=? ";
		return dao.findString("stateNewCnt", sql, parameterList);
	}
	/**
	 * 查询;
	 * 
	 * @Title: findAll
	 * @Description:
	 *
	 * 				参数说明
	 * @param appUserId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             返回类型 PageCoreBean<Map<String,Object>>
	 */
	public PageCoreBean<Map<String, Object>> findPage(String appUserId, Integer pageIndex, Integer pageSize)
			throws Exception {
		List<Object> parameterListCount = new ArrayList<Object>();
		parameterListCount.add(appUserId);
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(TaskStateEnum.NEW.getCode());
		parameterList.add(TaskStateEnum.OPEN.getCode());
		parameterList.add(CmsMsgTypeEnum.PRIVATE.getCode());
		parameterList.add(appUserId);
		String sqlCount = null;
		String sql = null;
		sqlCount = "SELECT count(*) " + "FROM cms_box_topic t where  t.APP_USER_ID_=?";
		sql = "SELECT " + " t.APP_USER_ID_,t.CMS_MSG_TOPIC_ID_,t.TOPIC_TITLE_ as TITLE_,"
				+ " t.CMS_CONTENT_,t.SEND_USER_,t.RECEIVE_USER_,"
				+ " t.CREATE_TIME_LONG_,t.CREATE_TIME_,	t.UPDATE_TIME_LONG_,t.UPDATE_TIME_,"
				+ " 	u1.APP_USER_NAME_ AS USER_NAME_SEND_,u1.NICKNAME_ AS NICKNAME_SEND_,u1.HEAD_PORTRAIT_ AS HEAD_PORTRAIT_SEND_,"
				+ " 	u2.APP_USER_NAME_ AS USER_NAME_RECEIVE_,u2.NICKNAME_ AS NICKNAME_RECEIVE_,u2.HEAD_PORTRAIT_ AS HEAD_PORTRAIT_RECEIVE_ "
				+ "FROM cms_box_topic t " + " LEFT JOIN app_user u1 ON t.SEND_USER_ = u1.APP_USER_ID_ "
				+ " LEFT JOIN app_user u2 ON t.RECEIVE_USER_ = u2.APP_USER_ID_ "
				+ "where (t.STATE_=? or t.STATE_=? )   and t.TOPIC_TYPE_=? and t.APP_USER_ID_=? order by 	t.UPDATE_TIME_LONG_ DESC ";
		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameterList, pageIndex.intValue(),
				pageSize.intValue(), sqlCount, parameterListCount);
		return basePage;
	}
}
