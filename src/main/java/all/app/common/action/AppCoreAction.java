package all.app.common.action;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import all.app.common.service.AppService;
import all.app.common.service.AppTokenRedisService;
import all.app.common.service.AppUserRedisService;
import all.gen.app_log_request.t.entity.AppLogRequestT;
import all.gen.app_log_request.t.service.AppLogRequestTService;
import all.gen.app_token.t.entity.AppTokenT;
import all.gen.app_user.t.entity.AppUserT;
import c.a.config.SysConfig;
import c.a.security.util.CommRSAUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.random.RandomUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.request.RequestUtil;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.string.StringUtil;
import c.a.util.core.uuid.Uuid;
import c.x.platform.root.common.action.CommAction;
import net.sf.json.JSONObject;
public abstract class AppCoreAction extends CommAction {
	// 机构名
	protected String tenantCode = null;
	public boolean appToken = false;
	protected String json = null;
	protected String tokenFromJson = null;
	// 操作或方法
	protected String cmdFromJson = null;
	// 追踪ID
	private String traceId = null;
	// 追踪贯穿ID SPAN_ID_
	private String spanId = null;
	// 追踪贯穿父ID SPAN_PARENT_ID_
	private String spanParentId = null;
	protected JSONObject dataJSONObject = null;
	protected String data = null;
	protected Map<String, Object> dataMap = null;
	protected AppTokenT appTokenT = null;
	// app_token表的用户类型(游客,会员)
	// protected String appTokenUserType = null;
	protected AppUserT appUserT = null;
	protected String appUserId = null;
	// app_user表的用户类型
	protected String appUserType = null;
	protected JsonTcpBean jrbThreadLocal = new JsonTcpBean();
	protected AppUserRedisService appUserRedisService = new AppUserRedisService();
	protected AppTokenRedisService appTokenRedisService = new AppTokenRedisService();
	public AppCoreAction() {
		if (StringUtil.isBlank(tenantCode)) {
			try {
				tenantCode = BeanThreadLocal.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalTenant),
						"");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String run() throws Exception;
	/**
	 * 实现父类方法
	 * 
	 */
	/**
	 * 实现父类方法
	 * 
	 */
	@Override
	public String execute() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		// response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT,
		// OPTIONS, DELETE");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		// JsonTcpBean jrb = new JsonTcpBean();
		// if (this.isFindJson()) {
		// } else {
		// jrb.setCode(ReturnCodeEnum.app400JSON.toString());
		// jrb.setMsg(ReturnCodeEnum.app400JSON.getMsgCn());
		// jrb.setCodeSys(ReturnCodeEnum.code400.toString());
		// jrb.setMessageSys(ReturnCodeEnum.code400.getMsgCn());
		// jrb.setSuccess(false);
		// return this.returnJson(jrb);
		// }
		if (appToken) {
			/**
			 * 
			 * 需要 appToken
			 */
			return this.appToken();
		} else {
			/**
			 * 
			 * 不需要 appToken
			 */
			return this.appTokenNot();
		}
	}
	/**
	 * 
	 * 不需要appToken
	 * 
	 * @return
	 * @throws Exception
	 */
	public String appTokenNot() throws Exception {
		String returnStr = this.run();
		return returnStr;
	}
	/**
	 * 
	 * 需要appToken
	 * 
	 * @return
	 * @throws Exception
	 */
	public String appToken() throws Exception {
		String returnStr = this.run();
		return returnStr;
	}
	/**
	 * 是否找到json
	 * 
	 * @Title: isFindJson
	 * @Description:
	 *
	 * 				参数说明
	 * @return
	 * @throws Exception
	 *             返回类型 boolean
	 */
	public boolean isFindJson() throws Exception {
		json = (String) request.getParameter("json");
		if (StringUtil.isNotBlank(json)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 查找登录用户
	 * 
	 * @Title: findAppUserFromJson
	 * @Description:
	 *
	 * 				参数说明
	 * @return
	 * @throws Exception
	 *             返回类型 AppUserT
	 */
	public AppUserT findAppUserByJsonData() throws Exception {
		json = RequestThreadLocal.findThreadLocal().get().findData(request);
		java.lang.StringBuilder stringBuilder =new StringBuilder ();
		stringBuilder.append("findData,所访问的json=");
		stringBuilder.append(json);
		stringBuilder.append("{");
		stringBuilder.append("url ServletPath=");
		stringBuilder.append(request.getServletPath());
		stringBuilder.append("}");
		log.info(stringBuilder.toString());
		return this.findAppUserByJson(json);
	}
	/**
	 * 查找登录用户
	 * 
	 * @Title: findAppUser
	 * @Description:
	 *
	 * 				参数说明
	 * @return
	 * @throws Exception
	 *             返回类型 AppUserT
	 */
	public AppUserT findAppUserByJsonParameter() throws Exception {
		json = (String) request.getParameter("json");
		java.lang.StringBuilder stringBuilder =new StringBuilder ();
		stringBuilder.append("所访问的json=");
		stringBuilder.append(json);
		stringBuilder.append("{");
		stringBuilder.append("url ServletPath=");
		stringBuilder.append(request.getServletPath());
		stringBuilder.append("}");
		log.info(stringBuilder.toString());
		return this.findAppUserByJson(json);
	}
	/**
	 * 查找用户
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param json
	 * @return
	 * @throws Exception
	 *             返回类型 AppUserT
	 */
	public AppUserT findAppUserByJson(String json) throws Exception {
		if (StringUtil.isNotBlank(json)) {
			Map<String, Object> map = JsonThreadLocal.findThreadLocal().get().json2map(json);
			if (map != null & map.size() >= 1) {
				// System.out.println("token="+map.get("token"));
				tokenFromJson = BeanThreadLocal.find(map.get("token"), "");
				// String commLoaltokenRSA =
				// SysConfig.findInstance().findMap().get("comm.local.tokenRSA").toString();
				String commLoaltokenRSA = BeanThreadLocal
						.find(SysConfig.findInstance().findMap().get("comm.local.tokenRSA"), "");
				if ("true".equals(commLoaltokenRSA)) {
					// 解密
					String tokenDecode = null;
					try {
						tokenDecode = CommRSAUtil.decode(tokenFromJson);
					} catch (Exception e) {
						e.printStackTrace();
						log.warn("token没有加密,tokenFromJson=" + tokenFromJson);
						jrbThreadLocal.setCode(ReturnCodeEnum.app403TokenRSA.getCode());
						jrbThreadLocal.setMsg(ReturnCodeEnum.app403TokenRSA.getMsgCn());
						jrbThreadLocal.setCodeSys(ReturnCodeEnum.code403.getCode());
						jrbThreadLocal.setMsgSys(ReturnCodeEnum.code403.getMsgCn());
						jrbThreadLocal.setSuccess(false);
						LogThreadLocal.setLog(jrbThreadLocal);
						return null;
					}
					if (StringUtil.isNotBlank(tokenDecode)) {
						tokenFromJson = tokenDecode;
					} else {
						jrbThreadLocal.setCode(ReturnCodeEnum.app403TokenRSA.getCode());
						jrbThreadLocal.setMsg(ReturnCodeEnum.app403TokenRSA.getMsgCn());
						jrbThreadLocal.setCodeSys(ReturnCodeEnum.code403.getCode());
						jrbThreadLocal.setMsgSys(ReturnCodeEnum.code403.getMsgCn());
						jrbThreadLocal.setSuccess(false);
						LogThreadLocal.setLog(jrbThreadLocal);
						return null;
					}
				}
				cmdFromJson = BeanThreadLocal.find(map.get("cmd"), "");
				// 追踪ID
				traceId = BeanThreadLocal.find(map.get("traceId"), "");
				spanParentId = BeanThreadLocal.find(map.get("spanId"), traceId);
				spanId = Uuid.findInstance().toString();
				JsonTcpBean logJsonTcpBean = LogThreadLocal.findLog();
				logJsonTcpBean.setTraceId(traceId);
				logJsonTcpBean.setSpanParentId(spanParentId);
				logJsonTcpBean.setSpanId(spanId);
				LogThreadLocal.setLog(logJsonTcpBean);
				this.saveAppLogRequest();
				// 通过token查找AppUserT
				AppService appService = new AppService();
				appUserT = appService.findAppUserByToken(tokenFromJson);
				if (appUserT == null) {
					jrbThreadLocal.setCode(ReturnCodeEnum.app401Token.toString());
					jrbThreadLocal.setMsg(ReturnCodeEnum.app401Token.getMsgCn());
					jrbThreadLocal.setCodeSys(ReturnCodeEnum.code401.toString());
					jrbThreadLocal.setMsgSys(ReturnCodeEnum.code401.getMsgCn());
					jrbThreadLocal.setSuccess(false);
					LogThreadLocal.setLog(jrbThreadLocal);
					return appUserT;
				}
				this.appUserId = appUserT.getAppUserId();
				this.appUserType = appUserT.getAppUserType();
				Object dataObject = map.get("data");
				if (JsonThreadLocal.findThreadLocal().get().isNull(dataObject)) {
				} else {
					if (dataObject instanceof JSONObject) {
						dataJSONObject = (JSONObject) dataObject;
						// log.trace("dataJSONObject="+dataJSONObject);
						dataMap = JsonThreadLocal.findThreadLocal().get().json2map(this.dataJSONObject.toString());
					}
					if (dataObject instanceof String) {
						data = dataObject.toString();
					}
				}
			}
		}
		return appUserT;
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
	/**
	 * 保存日志
	 * 
	 * @Title: saveAppLogRequest
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void saveAppLogRequest() throws Exception {
		String commLocalAppRequest = BeanThreadLocal
				.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalAppLogRequest), "false");
		if ("true".equals(commLocalAppRequest)) {
			String commLocalProject = SysConfig.findInstance().findMap().get("comm.local.type").toString();
			String ip = null;
			String servletPath = null;
			String mac = null;
			if (request != null) {
				ip = RequestThreadLocal.getThreadLocal().get().findIP(request);
				servletPath = request.getServletPath();
				mac = RequestThreadLocal.getThreadLocal().get().findMAC();
			}
			StringBuilder stringBuffer = new StringBuilder();
			stringBuffer.append("项目comm.local.type=" + commLocalProject);
			stringBuffer.append(";");
			stringBuffer.append("当前IP=" + ip);
			stringBuffer.append(";");
			stringBuffer.append("ServletPath=" + servletPath);
			stringBuffer.append(";");
			log.trace(stringBuffer.toString());
			Date date = new Date();
			AppLogRequestTService service = new AppLogRequestTService();
			AppLogRequestT entity = new AppLogRequestT();
			// 追踪ID
			JsonTcpBean logJsonTcpBean = LogThreadLocal.findLog();
			if (logJsonTcpBean == null) {
				logJsonTcpBean = new JsonTcpBean();
			}
			String traceId = logJsonTcpBean.getTraceId();
			//String spanParentId = logJsonTcpBean.getSpanParentId();
			//String spanId = logJsonTcpBean.getSpanId();
			entity.setTraceId(traceId);
			//entity.setSpanId(spanId);
			//entity.setSpanParentId(spanParentId);
			// set
			if (request != null) {
				//entity.setAppUserId(this.findCurrentAppUserId());
				//entity.setAppUserName(this.findCurrentAppUserName());
				RequestUtil requestUtil = RequestThreadLocal.findThreadLocal().get();
				// String requestDataStr =requestUtil.findData(request);
				// entity.setData(requestDataStr);
				entity.setRequestParameter(requestUtil.findParameter(request));
				entity.setRequestUrl(request.getRequestURL().toString());
				String servletContextPath = request.getServletContext().getRealPath("/");
				//entity.setServletContextPath(servletContextPath);
			}
			entity.setCreateTime(date);
			entity.setUpdateTime(date);
			entity.setCreateTimeLong(date.getTime());
			entity.setUpdateTimeLong(date.getTime());
			entity.setName(RandomUtil.findRandomInteger(1000000).toString());
			entity.setThread(Thread.currentThread().getName());
			entity.setIp(ip);
			//entity.setMac(mac);
			// 系统属性
			Properties properties = System.getProperties();
			String userDir = properties.getProperty("user.dir");
			//entity.setTomcatBin(userDir);
			entity.setState(TaskStateEnum.OPEN.getCode());
			service.save(entity);
		}
	}
}
