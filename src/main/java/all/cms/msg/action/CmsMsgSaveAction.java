package all.cms.msg.action;
import all.app.common.action.AppAction;
import all.cms.msg.bean.CmsSendBean;
import all.cms.msg.service.CmsMsgService;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogThreadLocal;
public class CmsMsgSaveAction extends AppAction {
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
			// 标题
			// 内容
			String cmsMsgTopicId = BeanThreadLocal.find(dataMap.get("topicId"), "");
			String content = BeanThreadLocal.find(dataMap.get("content"), "");
			// 接收人
			String receiveUser = BeanThreadLocal.find(dataMap.get("receiveUser"), "");
			// 发送人
			String sendUser = appUserT.getAppUserId();

			CmsMsgService cmsMsgService = new CmsMsgService();
			CmsSendBean cmsSendBean = new CmsSendBean();
			cmsSendBean.setContent(content);
			cmsSendBean.setSendUser(sendUser);
			cmsSendBean.setReceiveUserList(receiveUser);
			cmsMsgService.send(cmsSendBean);
			// 返回json
			jrb.setData(cmsMsgTopicId);
			jrb.setCodeSys(ReturnCodeEnum.code200.toString());
			jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
			jrb.setSuccess(true);
			return this.returnJson(jrb);
		} catch (Exception e) {
			e.printStackTrace();
			jrb.setCodeSys(ReturnCodeEnum.code500.toString());
			jrb.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
			jrb.setSuccess(false);
			this.returnJson(jrb);
			throw e;
		}
	}
}
