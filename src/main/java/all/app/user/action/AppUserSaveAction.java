package all.app.user.action;
import all.app.common.action.AppAction;
import all.app.common.action.AppController;
import all.app.common.service.AppTokenRedisService;
import all.app.common.service.AppUserRedisService;
import all.app.user.controller.AppUserSaveController;
import all.app.user.controller.AppUserSaveHeadPortraitController;
import all.app.user.controller.AppUserSaveNickNameController;
import all.app.user.controller.AppUserSavePasswordController;
import all.app.user.controller.AppUserSaveSexController;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
public class AppUserSaveAction extends AppAction {
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
			if (cmdFromJson.equals("update")) {
				AppController appController = new AppUserSaveController();
				jrb = appController.execute(dataMap, appUserT);
				isExecute = true;
			}
			if (cmdFromJson.equals("updateNickName")) {
				AppController appController = new AppUserSaveNickNameController();
				jrb = appController.execute(dataMap, appUserT);
				isExecute = true;
			}
			if (cmdFromJson.equals("updateSex")) {
				AppController appController = new AppUserSaveSexController();
				jrb = appController.execute(dataMap, appUserT);
				isExecute = true;
			}
			if (cmdFromJson.equals("updateHeadPortrait")) {
				AppController appController = new AppUserSaveHeadPortraitController();
				jrb = appController.execute(dataMap, appUserT);
				isExecute = true;
			}
			if (cmdFromJson.equals("updatePassword")) {
				AppController appController = new AppUserSavePasswordController();
				jrb = appController.execute(dataMap, appUserT);
				isExecute = true;
			}
			if (isExecute) {
				// AppUser更新redis
				appUserRedisService.updateAppUser(this.findCurrentUserTenantCode(), appUserT);
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
