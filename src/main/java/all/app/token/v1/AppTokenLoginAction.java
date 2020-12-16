package all.app.token.v1;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpSession;
import all.app.common.action.AppAction;
import all.app.common.config.AppConfigSession;
import all.app.common.service.AppAccountService;
import all.app.common.service.AppTokenRedisService;
import all.app.common.service.AppUserRedisService;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_token.t.entity.AppTokenT;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.boot.BootSessionContext;
public class AppTokenLoginAction extends AppAction {
	@Override
	public String run() throws Exception {
		String channelType = ChannelTypeEnum.APP.getCode();
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
			Map<String, Object> dataMapCustom = JsonThreadLocal.findThreadLocal().get().json2map(json);
			String accountName = BeanThreadLocal.find(dataMapCustom.get("name"), "");
			String password = BeanThreadLocal.find(dataMapCustom.get("password"), "");
			String code = BeanThreadLocal.find(dataMapCustom.get("code"), "");
			String sessionId = BeanThreadLocal.find(dataMapCustom.get("session"), "");
			BootSessionContext bootSessionContext = BootSessionContext.findInstance();
			HttpSession httpSession = bootSessionContext.findSession(sessionId);
			if (httpSession == null) {
				jrb.setCode(ReturnCodeEnum.app400Session.toString());
				jrb.setMsg(ReturnCodeEnum.app400Session.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			String codeSession = (String) httpSession.getAttribute(AppConfigSession.CodeVerify);
			if (StringUtil.isBlank(codeSession)) {
				jrb.setCode(ReturnCodeEnum.app400VerifyCode.toString());
				jrb.setMsg(ReturnCodeEnum.app400VerifyCode.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			if (codeSession.equals(code)) {
			} else {
				jrb.setCode(ReturnCodeEnum.app404VerifyCode.toString());
				jrb.setMsg(ReturnCodeEnum.app404VerifyCode.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			// 声明
			AppAccountService appAccountService = new AppAccountService();
			Date date = new Date();
			// 查找AppTokenT
			AppTokenT appTokenT = appTokenRedisService.findAppToken(this.tenantCode,accountName, password, channelType);
			// 查找账号
			AppAccountT appAccountT = appAccountService.findAppAccount(this.tenantCode,accountName, password);
			if (appAccountT == null) {
				jrb.setCode(ReturnCodeEnum.app400Login.toString());
				jrb.setMsg(ReturnCodeEnum.app400Login.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			// 查找用户
			AppUserT appUserT = appUserRedisService.findAppUser(this.tenantCode,accountName, password);
			if (appUserT == null) {
				jrb.setCode(ReturnCodeEnum.app400Login.toString());
				jrb.setMsg(ReturnCodeEnum.app400Login.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			// 更新登录时间
			appAccountT.setTenantCode(tenantCode);
			appAccountT.setLoginTime(date);
			appAccountT.setLoginTimeLong(date.getTime());
			appAccountService.update(appAccountT);
			appUserT.setTenantCode(tenantCode);
			appUserT.setLoginTime(date);
			appUserT.setLoginTimeLong(date.getTime());
			appUserRedisService.update(appUserT);
			// 声明对象
			if (appTokenT == null) {
				appTokenT = new AppTokenT();
				appTokenT.setAppUserId(appUserT.getAppUserId());
				appTokenT.setUserType(appUserT.getAppUserType());
				appTokenT.setChannelType(channelType);
				appTokenT.setState(UserStateEnum.OPEN.getCode());
				appTokenT.setTenantCode(tenantCode);
				// 新增redis的token和数据库的token
				appTokenRedisService.saveAppToken(this.findCurrentUserTenantCode(),appTokenT);
			} else {
				//appTokenT.setState(UserStateEnum.OPEN.getCode());
				appTokenT.setState(UserStateEnum.ON_LINE.getCode());
				appTokenT.setTenantCode(tenantCode);
				// 更新redis的token和数据库的token
				appTokenRedisService.updateAppToken(this.findCurrentUserTenantCode(),appTokenT);
			}
			// AppUser保存在redis
			appUserRedisService.saveAppUserByRedis(appUserT);
			// 返回json
			jrb.setData(appTokenT);
			jrb.setCode(ReturnCodeEnum.app200Login.toString());
			jrb.setMsg(ReturnCodeEnum.app200Login.getMsgCn());
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
