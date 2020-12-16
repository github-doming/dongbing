package all.app.common.service;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.random.RandomUtil;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.string.StringUtil;
import c.a.util.core.uuid.Uuid;
public class AppService {
	protected Logger log = LogManager.getLogger(this.getClass());
	// 机构名
	protected String tenantCode = null;
	public AppService() {
		if (tenantCode == null) {
			try {
				tenantCode = BeanThreadLocal.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalTenant),
						"");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 通过token查找AppUserT
	 * 
	 * @Title: findAppUserByToken
	 * @Description:
	 *
	 * 				参数说明
	 * @param tokenFromJson
	 * @return
	 * @throws Exception
	 *             返回类型 AppUserT
	 */
	public AppUserT findAppUserByToken(String tokenFromJson) throws Exception {
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		AppTokenRedisService appTokenRedisService = new AppTokenRedisService();
		AppUserT appUserT = null;
		String appUserId = null;
		String appUserIdRedis = null;
		if (StringUtil.isNotBlank(tokenFromJson)) {
			// 去redis查找token
			appUserIdRedis = appTokenRedisService.findAppUserIdByToken(tokenFromJson);
			if (StringUtil.isNotBlank(appUserIdRedis)) {
				appUserT = appUserRedisService.findAppUser(appUserIdRedis);
				if (appUserT != null) {
					// 得到用户的appUserId
					appUserId = appUserT.getAppUserId();
					if (appUserId != null) {
						String appUserType = BeanThreadLocal.find(appUserT.getAppUserType(), "");
						appUserT.setAppUserType(appUserType);
					}
				}
			}
		}
		return appUserT;
	}
	/**
	 * 
	 * @Title: doRegister
	 * @Description:
	 *
	 * 				参数说明
	 * @param accountName
	 * @param password
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public AppTokenT doRegister(HttpServletRequest request, HttpServletResponse response, String accountName,
			String password) throws Exception {
		String channelType = ChannelTypeEnum.APP.getCode();
		return this.doRegister(request, response, accountName, password, channelType);
	}
	/**
	 * 
	 * @Title: doRegister
	 * @Description:
	 *
	 * 				参数说明
	 * @param accountName
	 * @param password
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public AppTokenT doRegister(HttpServletRequest request, HttpServletResponse response, String accountName,
			String password, String channelType) throws Exception {
		// 声明
		JsonTcpBean returnCodeJsonTcpBean = new JsonTcpBean();
		AppTokenRedisService appTokenRedisService = new AppTokenRedisService();
		AppAccountTService appAccountTService = new AppAccountTService();
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		Date date = new Date();
		// 查找用户
		AppUserT appUserT = appUserRedisService.findAppUserByAccountName(accountName);
		if (appUserT != null) {
			returnCodeJsonTcpBean.setCode(ReturnCodeEnum.app409Register.toString());
			returnCodeJsonTcpBean.setMsg(ReturnCodeEnum.app409Register.getMsgCn());
			returnCodeJsonTcpBean.setCodeSys(ReturnCodeEnum.code409.toString());
			returnCodeJsonTcpBean.setMsgSys(ReturnCodeEnum.code409.getMsgCn());
			returnCodeJsonTcpBean.setSuccess(false);
			LogThreadLocal.setLog(returnCodeJsonTcpBean);
			return null;
		}
		// 声明对象
		AppAccountT appAccountT = new AppAccountT();
		appUserT = new AppUserT();
		AppTokenT appTokenT = new AppTokenT();
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
		if ("true".equals(commLocalASE)) {
			password = CommASEUtil.encode(commLocalASE_key, password.trim()).trim();
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
		returnCodeJsonTcpBean.setData(token);
		returnCodeJsonTcpBean.setCode(ReturnCodeEnum.app200Register.toString());
		returnCodeJsonTcpBean.setMsg(ReturnCodeEnum.app200Register.getMsgCn());
		returnCodeJsonTcpBean.setCodeSys(ReturnCodeEnum.code200.toString());
		returnCodeJsonTcpBean.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
		returnCodeJsonTcpBean.setSuccess(true);
		LogThreadLocal.setLog(returnCodeJsonTcpBean);
		return appTokenT;
	}
	/**
	 * 
	 * @Title: loginByPC
	 * @Description:
	 *
	 * 				参数说明
	 * @param request
	 * @param response
	 * @param accountName
	 * @param password
	 * @return
	 * @throws Exception
	 *             返回类型 AppTokenT
	 */
	public AppTokenT loginByPC(String currentUserTenantCode, HttpServletRequest request, HttpServletResponse response,
			String accountName, String password) throws Exception {
		String channelType = ChannelTypeEnum.PC.getCode();
		return this.login(currentUserTenantCode, request, response, accountName, password, channelType);
	}

	/**
	 * 
	 * @Title: login
	 * @Description:
	 *
	 * 				参数说明
	 * @param accountName
	 * @param password
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public AppTokenT login(String currentUserTenantCode, HttpServletRequest request, HttpServletResponse response,
			String accountName, String password) throws Exception {
		String channelType = ChannelTypeEnum.APP.getCode();
		return this.login(currentUserTenantCode, request, response, accountName, password, channelType);
	}
	/**
	 * 
	 * @Title: login
	 * @Description:
	 *
	 * 				参数说明
	 * @param accountName
	 * @param password
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public AppTokenT login(String currentUserTenantCode, HttpServletRequest request, HttpServletResponse response,
			String accountName, String password, String channelType) throws Exception {
		// 声明
		JsonTcpBean returnCodeJsonTcpBean = new JsonTcpBean();
		AppTokenRedisService appTokenRedisService = new AppTokenRedisService();
		AppAccountService appAccountService = new AppAccountService();
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		Date date = new Date();
		// 查找AppTokenT
		AppTokenT appTokenT = appTokenRedisService.findAppToken(this.tenantCode, accountName, password, channelType);
		// 查找账号
		AppAccountT appAccountT = appAccountService.findAppAccount(this.tenantCode, accountName, password);
		if (appAccountT == null) {
			returnCodeJsonTcpBean.setCode(ReturnCodeEnum.app400Login.toString());
			returnCodeJsonTcpBean.setMsg(ReturnCodeEnum.app400Login.getMsgCn());
			returnCodeJsonTcpBean.setCodeSys(ReturnCodeEnum.code400.toString());
			returnCodeJsonTcpBean.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
			returnCodeJsonTcpBean.setSuccess(false);
			LogThreadLocal.setLog(returnCodeJsonTcpBean);
			return null;
		}
		// 查找用户
		AppUserT appUserT = appUserRedisService.findAppUser(this.tenantCode, accountName, password);
		if (appUserT == null) {
			returnCodeJsonTcpBean.setCode(ReturnCodeEnum.app400Login.toString());
			returnCodeJsonTcpBean.setMsg(ReturnCodeEnum.app400Login.getMsgCn());
			returnCodeJsonTcpBean.setCodeSys(ReturnCodeEnum.code400.toString());
			returnCodeJsonTcpBean.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
			returnCodeJsonTcpBean.setSuccess(false);
			LogThreadLocal.setLog(returnCodeJsonTcpBean);
			return null;
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
			appTokenT.setState(UserStateEnum.LOGIN.getCode());
			appTokenT.setTenantCode(tenantCode);
			// 新增redis的token和数据库的token
			appTokenT = appTokenRedisService.saveAppToken(currentUserTenantCode, appTokenT);
		} else {
			appTokenT.setState(UserStateEnum.OPEN.getCode());
			appTokenT.setTenantCode(tenantCode);
			// 更新redis的token和数据库的token
			appTokenT = appTokenRedisService.updateAppToken(currentUserTenantCode, appTokenT);
		}
		// AppUser保存在redis
		appUserRedisService.saveAppUserByRedis(appUserT);
		// 返回json
		returnCodeJsonTcpBean.setAppUserId(appUserT.getAppUserId());
		returnCodeJsonTcpBean.setData(appTokenT);
		returnCodeJsonTcpBean.setCode(ReturnCodeEnum.app200Login.toString());
		returnCodeJsonTcpBean.setMsg(ReturnCodeEnum.app200Login.getMsgCn());
		returnCodeJsonTcpBean.setCodeSys(ReturnCodeEnum.code200.toString());
		returnCodeJsonTcpBean.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
		returnCodeJsonTcpBean.setSuccess(true);
		LogThreadLocal.setLog(returnCodeJsonTcpBean);
		return appTokenT;
	}
}
