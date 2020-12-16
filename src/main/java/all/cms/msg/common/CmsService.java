package all.cms.msg.common;
import java.util.Map;

import all.cms.msg.service.CmsMsgService;
import all.cms.msg.service.CmsMsgTopicService;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonTcpBean;
/**
 * 
 * 
 * @Description:
 * @ClassName:
 * @date 2011年9月19日 下午5:37:58
 * @author cxy
 * @Copyright (c)
 *
 */
public class CmsService {
	public JsonTcpBean doPost2JsonTcpBean(String topicId, JsonTcpBean _JsonTcpBean,String pageIndex,String pageSize ) throws Exception {
		// 发送消息
		CmsMsgService cmsMsgService = new CmsMsgService();
		try {
			/**
			 * 查询主题并发送
			 */
			Integer pageIndexInt = BeanThreadLocal.find(pageIndex, 1);
			Integer pageSizeInt = BeanThreadLocal.find(pageSize, 10);
			PageCoreBean<Map<String, Object>> page = cmsMsgService.findAll(topicId, _JsonTcpBean.getAppUserId(),
					pageIndexInt, pageSizeInt);
			// 返回json
			// 未读消息数量(总条数)
			String cnt = cmsMsgService.findStateNewCntTotalByCmsBoxMsg(_JsonTcpBean.getAppUserId());
			_JsonTcpBean.setTotal(Integer.parseInt(cnt));
			_JsonTcpBean.setData(page);
			_JsonTcpBean.setCodeSys(ReturnCodeEnum.code200.toString());
			_JsonTcpBean.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
			_JsonTcpBean.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			_JsonTcpBean.setCodeSys(ReturnCodeEnum.code500.toString());
			_JsonTcpBean.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
			_JsonTcpBean.setSuccess(false);
		}
		return _JsonTcpBean;
	}
	/**
	 * 发送主题
	 * @Title: sendTopic 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param topicUser
	 * @param _JsonTcpBean
	 * @return
	 * @throws Exception 
	 * 返回类型 JsonTcpBean
	 */
	public JsonTcpBean doTopic2JsonTcpBean(String topicUser, JsonTcpBean _JsonTcpBean,String pageIndex,String pageSize) throws Exception {
		CmsMsgTopicService cmsMsgTopicService = new CmsMsgTopicService();
		CmsMsgService cmsMsgService = new CmsMsgService();
		try {
			/**
			 * 查询主题并发送
			 */
			Integer pageIndexInt = BeanThreadLocal.find(pageIndex, 1);
			Integer pageSizeInt = BeanThreadLocal.find(pageSize, 10);
			PageCoreBean<Map<String, Object>> page = cmsMsgTopicService.findAll(topicUser, pageIndexInt, pageSizeInt);
			// 返回json
			// 未读消息数量(总条数)
			String cnt = cmsMsgService.findStateNewCntTotalByCmsBoxMsg(topicUser);
			_JsonTcpBean.setTotal(Integer.parseInt(cnt));
			_JsonTcpBean.setData(page);
			_JsonTcpBean.setCodeSys(ReturnCodeEnum.code200.toString());
			_JsonTcpBean.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
			_JsonTcpBean.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			_JsonTcpBean.setCodeSys(ReturnCodeEnum.code500.toString());
			_JsonTcpBean.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
			_JsonTcpBean.setSuccess(false);
		}
		return _JsonTcpBean;
	}
}
