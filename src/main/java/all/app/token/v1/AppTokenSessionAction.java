package all.app.token.v1;
import javax.servlet.http.HttpSession;

import all.app.common.action.AppAction;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.boot.BootSessionContext;
public class AppTokenSessionAction extends AppAction {
	@Override
	public String run() throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
		try {
			HttpSession session=request.getSession();
			String sessionId=session.getId();
			BootSessionContext.findInstance().addSession(session);
			// 返回json
			jrb.setData(sessionId);
			jrb.setCode(ReturnCodeEnum.app200Session.toString());
			jrb.setMsg(ReturnCodeEnum.app200Session.getMsgCn());
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
