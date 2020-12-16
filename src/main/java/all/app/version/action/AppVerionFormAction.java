package all.app.version.action;
import all.app.common.action.AppAction;
import all.app.version.service.AppVerionService;
import all.gen.app_config.t.entity.AppConfigT;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
public class AppVerionFormAction extends AppAction {
	@Override
	public String run() throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
		try {
			if(false){
				if (this.isFindJson()) {
				} else {
					jrb.setCode(ReturnCodeEnum.app400JSON.toString());
					jrb.setMsg(ReturnCodeEnum.app400JSON.getMsgCn());
					jrb.setCodeSys(ReturnCodeEnum.code400.toString());
					jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
					jrb.setSuccess(false);
					return this.returnJson(jrb);
				}
				// AppUserT appUserT = this.findAppUserByJsonData();
				// AppUserT appUserT = this.findAppUserByJsonParameter();
				this.findAppUserByJsonParameter();
				if (appUserT == null) {
					jrb.setCode(ReturnCodeEnum.app401Token.toString());
					jrb.setMsg(ReturnCodeEnum.app401Token.getMsgCn());
					jrb.setCodeSys(ReturnCodeEnum.code401.toString());
					jrb.setMsgSys(ReturnCodeEnum.code401.getMsgCn());
					jrb.setSuccess(false);
					return this.returnJson(jrb);
				}
			}
			AppVerionService service = new AppVerionService();
			AppConfigT entity = service.findVerson();
			// 返回json
			jrb.setData(entity);
			jrb.setCodeSys(ReturnCodeEnum.code200.toString());
			jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
			jrb.setSuccess(true);
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
