package all.app.token;
import all.app.common.action.AppAction;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
public class AppTokenJsonpAction extends AppAction {
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
			AppUserT appUserT = this.findAppUserByJsonParameter();
			if (appUserT == null) {
				jrb.setCode(ReturnCodeEnum.app401Token.toString());
				jrb.setMsg(ReturnCodeEnum.app401Token.getMsgCn());

				jrb.setCodeSys(ReturnCodeEnum.code401.toString());
				jrb.setMsgSys(ReturnCodeEnum.code401.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJsonp(jrb);
			}
			// 返回json
			jrb.setCode(ReturnCodeEnum.app200Login.toString());
			jrb.setMsg(ReturnCodeEnum.app200Login.getMsgCn());
			jrb.setCodeSys(ReturnCodeEnum.code200.toString());
			jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
			jrb.setSuccess(true);
			return this.returnJsonp(jrb);
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
