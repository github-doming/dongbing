package com.ibm.old.v1.common.doming.core;
import all.app.common.action.AppAction;
import all.app.common.service.AppService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.config.SysConfig;
import c.a.security.util.CommRSAUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import net.sf.json.JSONObject;
import org.doming.core.tools.StringTool;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-12-19 10:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseAppAction extends AppAction {

	private static boolean encryptToken = false;
	private static boolean encryptDate = false;

	public BaseAppAction() {
		if (StringUtil.isBlank(tenantCode)) {
			try {
				tenantCode = BeanThreadLocal
						.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalTenant), "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 根据json初始化请求数据
	 *
	 * @return 访问对象
	 */
	public AppUserT findAppUser() throws Exception {
		json = request.getParameter("json");
		logAccessUrl();
		log.info("所访问的json=" + json);
		return this.findAppUserByJsonPlus(json);
	}

	/**
	 * 根据json初始化请求数据
	 *
	 * @return 请求数据
	 */
	public Map<String, Object> findDateMap() throws Exception {
		json = request.getParameter("json");
		logAccessUrl();
		log.info("所访问的json=" + json);
		return this.findDateMapByJsonPlus(json);
	}

	/**
	 * 根据json初始化请求数据
	 *
	 * @return 请求数据
	 */
	public String findJson() {
		json = request.getParameter("json");
		log.info("所访问的json=" + json);
		return json;
	}

	/**
	 * 根据json初始化参数信息
	 *
	 * @param json 请求json
	 * @return 访问对象
	 */
	private AppUserT findAppUserByJsonPlus(String json) {
		if (StringTool.notEmpty(json)) {
			Map<String, Object> map = JsonThreadLocal.findThreadLocal().get().json2map(json);
			if (map != null && map.size() >= 1) {
				findAppUser(map);
				findDateMap(map);
			} else {
				log.error(IbmConfig.LOG_SIGN + "没有找到用户，json不合法，所请求的request=" + getParamsStringByNames(request));
				returnCode400();
			}
		} else {
			log.error(IbmConfig.LOG_SIGN + "没有找到用户，json为空，所请求的request=" + getParamsStringByNames(request));
			returnCode400();
		}

		return appUserT;
	}

	/**
	 * 根据json初始化参数信息
	 *
	 * @param json 请求json
	 * @return 请求数据
	 */
	private Map<String, Object> findDateMapByJsonPlus(String json) {
		if (StringTool.notEmpty(json)) {
			Map<String, Object> map = JsonThreadLocal.findThreadLocal().get().json2map(json);
			if (map != null && map.size() >= 1) {
				findDateMap(map);
			} else {
				log.error(IbmConfig.LOG_SIGN + "没有找到数据，json不合法，所请求的json=" + json);
				returnCode400();
			}
		} else {
			log.error(IbmConfig.LOG_SIGN + "没有找到数据，json为空，所请求的request=" + getParamsStringByNames(request));
			returnCode400();
		}
		return dataMap;
	}

	/**
	 * 查找token携带的数据
	 *
	 * @param map json整个翻译map
	 */
	private void findAppUser(Map<String, Object> map) {
		tokenFromJson = BeanThreadLocal.find(map.get("token"), "");
		// 识别配置文件是否加密token
		String commLocalTokenRSA;
		try {
			commLocalTokenRSA = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.tokenRSA"), "");
		} catch (Exception e) {
			log.warn(IbmConfig.LOG_SIGN + "读取配置失败,没有找到token是否加密字段-comm.local.tokenRSA");
			returnCode500();
			return;
		}
		if (StringTool.notEmpty(commLocalTokenRSA)) {
			encryptToken = Boolean.valueOf(commLocalTokenRSA);
		}
		if (StringTool.notEmpty(tokenFromJson) && encryptToken) {
			// token解密
			String tokenDecode;
			try {
				tokenDecode = CommRSAUtil.decode(tokenFromJson);
			} catch (Exception e) {
				log.warn(IbmConfig.LOG_SIGN + "token解密失败,tokenFromJson=" + tokenFromJson);
				returnCode500();
				return;
			}
			if (StringTool.notEmpty(tokenDecode)) {
				tokenFromJson = tokenDecode;
			} else {
				log.warn(IbmConfig.LOG_SIGN + "token解密失败,解密的token为空" + tokenDecode);
				returnCode403();
				return;
			}
		}
		// 找到AppUserT
		AppService appService = new AppService();
		try {
			appUserT = appService.findAppUserByToken(tokenFromJson);
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "查找用户失败", e);
			returnCode500();
			return;
		}
		if (appUserT == null) {
			log.error(IbmConfig.LOG_SIGN + "查找用户为空，token为：" + tokenFromJson);
			returnCode401();
			return;
		}
		this.appUserId = appUserT.getAppUserId();
		this.appUserType = appUserT.getAppUserType();
		cmdFromJson = BeanThreadLocal.find(map.get("cmd"), "");
		LogThreadLocal.setLog(true, "处理成功");
	}

	/**
	 * 查找数据
	 *
	 * @param map 数据
	 */
	private void findDateMap(Map<String, Object> map) {
		Object dataObject = map.get("data");
		// 识别配置文件是否加密data
		String commLocalDataRSA;
		try {
			commLocalDataRSA = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.dataRSA"), "");
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "读取配置失败");
			returnCode500();
			return;
		}
		if (StringTool.notEmpty(commLocalDataRSA)) {
			encryptDate = Boolean.valueOf(commLocalDataRSA);
		}
		if (dataObject != null) {
			// 加密
			if (encryptDate) {
				String decode;
				try {
					decode = CommRSAUtil.decode(dataObject.toString());
				} catch (Exception e) {
					log.warn(IbmConfig.LOG_SIGN + "data解密失败,dataObject=" + dataObject.toString());
					returnCode403();
					return;
				}
				if (StringTool.notEmpty(decode)) {
					decode = StringTool.trimAll(decode);
				} else {
					log.warn(IbmConfig.LOG_SIGN + "data解密失败,解密的data为空");
					returnCode404();
					return;
				}

				try {
					dataJSONObject = JSONObject.fromObject(decode);
					dataMap = JsonThreadLocal.findThreadLocal().get().json2map(this.dataJSONObject.toString());
				} catch (Exception e) {
					log.warn(IbmConfig.LOG_SIGN + "data转化失败,data解码=" + decode);
					returnCode404();
					return;
				}
			} else {
				String data = dataObject.toString();
				data = StringTool.trimAll(data);
				dataJSONObject = JSONObject.fromObject(data);
				dataMap = JsonThreadLocal.findThreadLocal().get().json2map(this.dataJSONObject.toString());
			}
		}
		LogThreadLocal.setLog(true, "处理成功");
	}

	/**
	 * 访问地址
	 */
	private void logAccessUrl() throws Exception {
		String commLocalProject = SysConfig.findInstance().findMap().get("comm.local.type").toString();
		String ip = RequestThreadLocal.getThreadLocal().get().findIP(request);
		String stringBuffer =
				("项目comm.local.type=" + commLocalProject) + ";" + "当前IP=" + ip + ";" + "ServletPath=" + request
						.getServletPath() + ";" + "tokenAction=" + this.getClass().getName() + ";";

		log.info(stringBuffer);
	}

	/**
	 * 打印request 字符串
	 *
	 * @param request 请求request
	 * @return request.getParameterMap 字符串
	 */
	private String getParamsStringByMaps(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		StringBuilder mapStr = new StringBuilder("{");
		for (Map.Entry entry : parameterMap.entrySet()) {
			mapStr.append(entry.getKey()).append(":");
			if (entry.getValue() instanceof String[]) {
				mapStr.append(Arrays.toString((String[]) entry.getValue()));
			} else {
				mapStr.append(entry.getValue());
			}
			mapStr.append(";");
		}
		mapStr.deleteCharAt(mapStr.length() - 1);
		mapStr.append("}");
		return mapStr.toString();
	}

	/**
	 * 打印request 字符串
	 *
	 * @param request 请求request
	 * @return request.getParameter 字符串
	 */
	private String getParamsStringByNames(HttpServletRequest request) {
		Enumeration<String> parameterNames = request.getParameterNames();
		StringBuilder parameterStr = new StringBuilder("{");
		if (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			parameterStr.append(parameterName).append(":").append(request.getParameter(parameterName)).append(";");
		}
		parameterStr.deleteCharAt(parameterStr.length() - 1);
		parameterStr.append("}");
		return parameterStr.toString();
	}

	/**
	 * 返回加密字符串
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public String returnEncodeJson(JsonResultBean bean) throws Exception {
		return returnJson(CommRSAUtil.encode(JsonThreadLocal.bean2json(bean)));
	}

	private void returnCode400() {
		jrbThreadLocal.setCode(ReturnCodeEnum.app400JSON.getCode());
		jrbThreadLocal.setMsg(ReturnCodeEnum.app400JSON.getMsgCn());
		jrbThreadLocal.setCodeSys(ReturnCodeEnum.code400.getCode());
		jrbThreadLocal.setMessageSys(ReturnCodeEnum.code400.getMsgCn());
		LogThreadLocal.setLog(jrbThreadLocal);
	}
	private void returnCode401() {
		jrbThreadLocal.setCode(ReturnCodeEnum.app401Token.toString());
		jrbThreadLocal.setMsg(ReturnCodeEnum.app401Token.getMsgCn());
		jrbThreadLocal.setCodeSys(ReturnCodeEnum.code401.toString());
		jrbThreadLocal.setMessageSys(ReturnCodeEnum.code401.getMsgCn());
		LogThreadLocal.setLog(jrbThreadLocal);
	}
	private void returnCode403() {
		jrbThreadLocal.setCode(ReturnCodeEnum.app403TokenRSA.getCode());
		jrbThreadLocal.setMsg(ReturnCodeEnum.app403TokenRSA.getMsgCn());
		jrbThreadLocal.setCodeSys(ReturnCodeEnum.code403.getCode());
		jrbThreadLocal.setMessageSys(ReturnCodeEnum.code403.getMsgCn());
		LogThreadLocal.setLog(jrbThreadLocal);
	}
	private void returnCode404() {
		jrbThreadLocal.setCode(ReturnCodeEnum.code404.getCode());
		jrbThreadLocal.setMsg(ReturnCodeEnum.code404.getMsgCn());
		jrbThreadLocal.setCodeSys(ReturnCodeEnum.code404.getCode());
		jrbThreadLocal.setMessageSys(ReturnCodeEnum.code404.getMsgCn());
		LogThreadLocal.setLog(jrbThreadLocal);
	}
	private void returnCode500() {
		jrbThreadLocal.setCode(ReturnCodeEnum.app500VerifyCode.getCode());
		jrbThreadLocal.setMsg(ReturnCodeEnum.app500VerifyCode.getMsgCn());
		jrbThreadLocal.setCodeSys(ReturnCodeEnum.code500.getCode());
		jrbThreadLocal.setMessageSys(ReturnCodeEnum.code500.getMsgCn());
		LogThreadLocal.setLog(jrbThreadLocal);
	}
}
