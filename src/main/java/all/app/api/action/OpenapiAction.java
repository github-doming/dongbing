package all.app.api.action;
import all.app.api.controller.AppUserListController;
import all.app.common.action.AppAction;
import all.app.common.action.AppController;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
public class OpenapiAction extends AppAction {
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
				jrb.setCode(ReturnCodeEnum.app401Token.toString());
				jrb.setMsg(ReturnCodeEnum.app401Token.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code401.toString());
				jrb.setMsgSys(ReturnCodeEnum.code401.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			if (StringUtil.isBlank(cmdFromJson)) {
				jrb.setCode(ReturnCodeEnum.app400Cmd.toString());
				jrb.setMsg(ReturnCodeEnum.app400Cmd.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			boolean isExecute = false;
			if (cmdFromJson.equals("queryAllUser")) {
				AppController appController = new AppUserListController();
				jrb = appController.execute(dataMap, appUserT);
				isExecute = true;
			}
			if (isExecute) {
			} else {
				jrb.setCode(ReturnCodeEnum.app400Cmd.toString());
				jrb.setMsg(ReturnCodeEnum.app400Cmd.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			return this.returnJson(jrb);
		} catch (Exception e) {
			e.printStackTrace();
			jrb.setCodeSys(ReturnCodeEnum.code500.toString());
			jrb.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
			jrb.setSuccess(true);
			return this.returnJson(jrb);
		}
	}
}
