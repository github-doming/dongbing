package all.cms.msg.action;
import all.app.common.action.AppAction;
import all.cms.msg.service.CmsMsgService;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.string.StringUtil;
/**
 * 
 * 2个人之间的会话主题
 * 
 * @Description:
 * @ClassName: CmsMsgTopicAction
 * @date 2018年6月4日 下午12:23:00
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class CmsMsgTopicAction extends AppAction {
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
			// Map<String, Object> dataMap =
			// JsonThreadLocal.findThreadLocal().get().json2map(this.dataJSONObject.toString());
			// token
			// 接收人
			String chatAppUserId = BeanThreadLocal.find(dataMap.get("receiveUser"), "");
			// 发送人
			String appUserId = appUserT.getAppUserId();
			if (StringUtil.isBlank(chatAppUserId)) {
				jrb.setMsg("接收人Id不能为空");
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			CmsMsgService cmsMsgService = new CmsMsgService();
			String cmsTopicId = cmsMsgService.findCmsBoxTopicIdByAppUserId(appUserId, chatAppUserId);
			// 返回json
			jrb.setData(cmsTopicId);
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
