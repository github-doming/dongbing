package all.app.user.controller;
import java.util.Map;

import all.app.common.action.AppController;
import all.app.common.service.AppAccountService;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
public class AppUserSavePasswordController extends AppController {
	@Override
	public JsonTcpBean execute(Map<String, Object> dataMap, AppUserT appUserT) throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
	
		String passwordOld = BeanThreadLocal.find(dataMap.get("passwordOld"), "");
		String passwordNew = BeanThreadLocal.find(dataMap.get("passwordNew"), "");
		if (StringUtil.isBlank(passwordOld)) {
			jrb.setCode(ReturnCodeEnum.app400Password.toString());
			jrb.setMsg(ReturnCodeEnum.app400Password.getMsgCn());
			jrb.setCodeSys(ReturnCodeEnum.code400.toString());
			jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
			jrb.setSuccess(false);
			return jrb;
		}
		if (StringUtil.isBlank(passwordNew)) {
			jrb.setCode(ReturnCodeEnum.app400Password.toString());
			jrb.setMsg(ReturnCodeEnum.app400Password.getMsgCn());
			jrb.setCodeSys(ReturnCodeEnum.code400.toString());
			jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
			jrb.setSuccess(false);
			return jrb;
		}
		AppAccountService service = new AppAccountService();
		AppAccountT appAccountT=service.findAppAccountByPassword(appUserT.getAppUserId(), passwordOld);
		if(appAccountT==null){
			jrb.setCode(ReturnCodeEnum.app400PasswordOld.toString());
			jrb.setMsg(ReturnCodeEnum.app400PasswordOld.getMsgCn());
			jrb.setCodeSys(ReturnCodeEnum.code400.toString());
			jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
			jrb.setSuccess(false);
			return jrb;
		}
		service.updatePassword(appUserT.getAppUserId(), passwordNew);
		// 返回json
		jrb.setCodeSys(ReturnCodeEnum.code200.toString());
		jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
		jrb.setSuccess(true);
		return jrb;
	}
}
