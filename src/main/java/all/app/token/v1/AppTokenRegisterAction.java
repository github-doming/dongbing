package all.app.token.v1;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import all.app.common.action.AppAction;
import all.app.common.config.AppConfigSession;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_account.t.service.AppAccountTService;
import all.gen.app_token.t.entity.AppTokenT;
import all.gen.app_user.t.entity.AppUserT;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.tools.NickNameTool;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.enums.bean.UserTypeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.random.RandomUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.uuid.Uuid;
import c.x.platform.root.boot.BootSessionContext;
public class AppTokenRegisterAction extends AppAction {
	@Override
	public String run() throws Exception {
		String channelType=ChannelTypeEnum.APP.getCode();
		JsonTcpBean jrb = new JsonTcpBean();
		try {
			String json = request.getParameter("json");
			if (StringUtil.isBlank(json)) {
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
			if (StringUtil.isBlank(sessionId)) {
				jrb.setCode(ReturnCodeEnum.app400Session.toString());
				jrb.setMsg(ReturnCodeEnum.app400Session.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			BootSessionContext bootSessionContext = BootSessionContext.findInstance();
			HttpSession httpSession = bootSessionContext.findSession(sessionId);
			if (httpSession == null) {
				jrb.setCode(ReturnCodeEnum.app404Session.toString());
				jrb.setMsg(ReturnCodeEnum.app404Session.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code404.toString());
				jrb.setMsgSys(ReturnCodeEnum.code404.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			String codeSession = (String) httpSession.getAttribute(AppConfigSession.CodeVerify);
			if (StringUtil.isBlank(codeSession)) {
				jrb.setCode(ReturnCodeEnum.app404Session.toString());
				jrb.setMsg(ReturnCodeEnum.app404Session.getMsgCn());
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
			// 查找用户
			AppUserT appUserT = appUserRedisService.findAppUserByAccountName(accountName);
			if (appUserT != null) {
				jrb.setCode(ReturnCodeEnum.app409Register.toString());
				jrb.setMsg(ReturnCodeEnum.app409Register.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code409.toString());
				jrb.setMsgSys(ReturnCodeEnum.code409.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			// 声明对象
			AppAccountT appAccountT = new AppAccountT();
			AppAccountTService appAccountTService = new AppAccountTService();
			appUserT = new AppUserT();
			AppTokenT appTokenT = new AppTokenT();
			Date date = new Date();
			// 创建用户
			String headPortrait = "/common/img/avatar/avatar" + RandomUtil.findRandomInteger(25) + ".png";
			appUserT.setAppUserName(accountName);
			// 昵称
			appUserT.setNickname(NickNameTool.findNickName());
			// 头像
			appUserT.setHeadPortrait(headPortrait);
			appUserT.setCreateTime(date);
			appUserT.setCreateTimeLong(date.getTime());
			appUserT.setUpdateTime(date);
			appUserT.setUpdateTimeLong(date.getTime());
			appUserT.setState(UserStateEnum.OPEN.getCode());
			appUserT.setTenantCode(tenantCode);
			String appUserId = appUserRedisService.save(appUserT);
			appUserT.setAppUserId(appUserId);
			// 创建账户
			appAccountT.setChannelType(channelType);
			appAccountT.setAccountName(accountName);
			String commLocalASE = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
			String commLocalASE_key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
					"");
			if("true".equals(commLocalASE)){
				password= CommASEUtil.encode(commLocalASE_key, password.trim()).trim();
			}
			appAccountT.setPassword(password);
			appAccountT.setAppUserId(appUserId);
			appAccountT.setCreateTime(date);
			appAccountT.setCreateTimeLong(date.getTime());
			appAccountT.setUpdateTime(date);
			appAccountT.setUpdateTimeLong(date.getTime());
			appAccountT.setState(UserStateEnum.OPEN.getCode());
			appAccountT.setTenantCode(tenantCode);
			appAccountTService.save(appAccountT);
			// 创建Token
			String token = Uuid.create().toString();
			appTokenT.setChannelType(channelType);
			appTokenT.setAppUserId(appUserId);
			appTokenT.setValue(token);
			appTokenT.setUserType(UserTypeEnum.TOURIST.getCode());
			appTokenT.setCreateTime(date);
			appTokenT.setCreateTimeLong(date.getTime());
			appTokenT.setUpdateTime(date);
			appTokenT.setUpdateTimeLong(date.getTime());
			appTokenT.setState(UserStateEnum.OPEN.getCode());
			appTokenT.setTenantCode(tenantCode);
			appTokenRedisService.save(appTokenT);
			// Token保存在redis
			appTokenRedisService.saveTokenByRedis(token, appUserId);
			// AppUser保存在redis
			// appUserRedisService.saveAppUserByRedis(appUserT);
			// 返回json
			jrb.setData(token);
			jrb.setCode(ReturnCodeEnum.app200Register.toString());
			jrb.setMsg(ReturnCodeEnum.app200Register.getMsgCn());
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
