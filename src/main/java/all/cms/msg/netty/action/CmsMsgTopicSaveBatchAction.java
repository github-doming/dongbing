package all.cms.msg.netty.action;
import java.util.ArrayList;
import java.util.List;

import all.app.common.action.AppAction;
import all.cms.msg.bean.CmsSendBean;
import all.cms.msg.common.CmsService;
import all.cms.msg.service.CmsMsgService;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.netty.core.TcpNettyUtil;
/**
 * 
 * 不用队列;
 * 
 * 调用WebSocket;
 * 
 * @Description:
 * @ClassName: CmsMsgTopicSaveBatchAction
 * @date 2018年8月10日 下午6:34:38
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class CmsMsgTopicSaveBatchAction extends AppAction {
	@Override
	public String run() throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
		try {
			if (this.isFindJson()) {
			} else {
				jrb.setCode(ReturnCodeEnum.app400JSON.toString());
				jrb.setMsg(ReturnCodeEnum.app400JSON.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			this.findAppUserByJsonParameter();
			if (appUserT == null) {
				jrb = LogThreadLocal.findLog();
				return this.returnJson(jrb);
			}
			CmsService _CmsService = new CmsService();
			String pageIndex = BeanThreadLocal.find(dataMap.get("pageIndex"), "");
			String pageSize = BeanThreadLocal.find(dataMap.get("pageSize"), "");
			// token
			// 标题
			// 内容
			String content = BeanThreadLocal.find(dataMap.get("content"), "");
			// 接收人
			String receiveUserList = BeanThreadLocal.find(dataMap.get("receiveUser"), "");
			// 发送人
			String sendUser = appUserT.getAppUserId();
			CmsMsgService cmsMsgService = new CmsMsgService();
			CmsSendBean cmsSendBean = new CmsSendBean();
			cmsSendBean.setContent(content);
			cmsSendBean.setSendUser(sendUser);
			cmsSendBean.setReceiveUserList(receiveUserList);
			cmsMsgService.send(cmsSendBean);
			/**
			 * 发送人主题
			 */
			jrb =_CmsService.doTopic2JsonTcpBean(appUserId, jrb, pageIndex, pageSize);
			List<String> userSendList = new ArrayList<String>();
			userSendList.add(appUserId);
			jrb.setUserSend(userSendList);
			//WebSocketUtil.findInstance().send(jrb);
			TcpNettyUtil.findInstance().send(jrb);
			/**
			 * 接收人主题
			 */
			String[] receiveUserArray = receiveUserList.split(",");
			for (String receviceUser : receiveUserArray) {
				jrb =_CmsService.doTopic2JsonTcpBean(receviceUser, jrb, pageIndex, pageSize);
				userSendList = new ArrayList<String>();
				userSendList.add(receviceUser);
				jrb.setUserSend(userSendList);
				//WebSocketUtil.findInstance().send(jrb);
				TcpNettyUtil.findInstance().send(jrb);
			}
			// 返回json
			jrb.setData(null);
			jrb.setCodeSys(ReturnCodeEnum.code200.toString());
			jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
			jrb.setSuccess(true);
			return this.returnJson(jrb);
		} catch (Exception e) {
			e.printStackTrace();
			jrb.setCodeSys(ReturnCodeEnum.code500.toString());
			jrb.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
			jrb.setSuccess(false);
			return this.returnJson(jrb);
		}
	}

}
