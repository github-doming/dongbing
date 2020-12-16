package all.cms.msg.action;
import all.app.common.action.AppAction;
import all.gen.app_user.t.entity.AppUserT;
import all.gen.cms_box_msg.t.service.CmsBoxMsgTService;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.string.StringUtil;
/**
 * 消息删除
 * 
 * @Description:
 * @ClassName: CmsMsgDelAction
 * @date 2018年7月17日 下午6:11:10
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class CmsMsgDelAction extends AppAction {
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
			String msgId = BeanThreadLocal.find(dataMap.get("msgId"), "");
			if (StringUtil.isBlank(msgId)) {
				jrb.setMsg("消息msgId不能为空");
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			CmsBoxMsgTService service = new CmsBoxMsgTService();
			String[] msgIdArray = msgId.split(",");
			service.delAll(msgIdArray);
			// 返回json
			jrb.setData(msgId);
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
