package com.ibm.old.v1.common.doming.core;
import all.app.common.service.AppService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.config.SysConfig;
import c.a.security.util.CommRSAUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.return_code.ReturnCodeThreadLocal;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.servlet.BaseAsynMvcTransactions;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-12-19 10:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseAsynMvcAction extends BaseAsynMvcTransactions {

	protected String json = null;
	protected String cmd = null;
	protected String token = null;
	protected AppUserT appUserT = null;
	protected String appUserId = null;
	protected Map<String, Object> dataMap = null;
	protected JSONObject dataJSONObject = null;

	protected JsonResultBeanPlus jrbThreadLocal = new JsonResultBeanPlus();

	protected boolean encryptToken = false;
	protected boolean encryptDate = false;

	/**
	 * 根据json初始化请求数据
	 *
	 * @return 访问对象
	 */
	public AppUserT findAppUser() throws Exception {
		String[] jsons = parameterMap.get("json");
		json = asyncContext.getRequest().getParameter("json");
		if (StringTool.isEmpty(json)  || ContainerTool.isEmpty(jsons) || jsons.length != 1 || !jsons[0].equals(json)) {
			System.out.println("jsons=" + Arrays.toString(jsons));
			System.out.println("json=" + json);
		}
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
		String[] jsons = parameterMap.get("json");
		json = asyncContext.getRequest().getParameter("json");
		if (jsons == null || jsons.length != 1 || !jsons[0].equals(json)) {
			System.out.println("jsons=" + Arrays.toString(jsons));
			System.out.println("json=" + json);
		}
		logAccessUrl();
		log.debug("所访问的json=" + json);
		return this.findDateMapByJsonPlus(json);
	}

	/**
	 * 根据json初始化请求数据
	 *
	 * @return 请求数据
	 */
	public String findJson() {
		json = asyncContext.getRequest().getParameter("json");
		log.debug("所访问的json=" + json);
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
				log.error(IbmConfig.LOG_SIGN + "没有找到用户，所请求的request=" + getParamsString());
				returnCode400();
			}
		} else {
			log.error(IbmConfig.LOG_SIGN + "没有找到用户，所请求的request=" + getParamsString());
			returnCode400();
		}
		if (appUserT != null){
			appUserId = appUserT.getAppUserId();
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
				log.warn(IbmConfig.LOG_SIGN + "没有找到数据，所请求的json=" + json);
				returnCode400();
			}
		} else {
			log.error(IbmConfig.LOG_SIGN + "没有找到数据，所请求的request=" + getParamsString());
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
		token = BeanThreadLocal.find(map.get("token"), "");
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
		if (StringTool.notEmpty(token)  ) {
			// token解密
			if (encryptToken){
				String tokenDecode;
				try {
					tokenDecode = CommRSAUtil.decode(token);
				} catch (Exception e) {
					log.warn(IbmConfig.LOG_SIGN + "token解密失败,token=" + token);
					returnCode500();
					return;
				}
				if (StringTool.notEmpty(tokenDecode)) {
					token = tokenDecode;
				} else {
					log.warn(IbmConfig.LOG_SIGN + "token解密失败,解密的token为空" + tokenDecode);
					returnCode403();
					return;
				}
			}
			// 找到AppUserT
			AppService appService = new AppService();
			try {
				appUserT = appService.findAppUserByToken(token);
			} catch (Exception e) {
				log.error(IbmConfig.LOG_SIGN + "查找用户失败", e);
				returnCode500();
				return;
			}
			if (appUserT == null) {
				log.error(IbmConfig.LOG_SIGN + "查找用户为空，token为：" + token);
				returnCode401();
				return;
			}
		}
		cmd = BeanThreadLocal.find(map.get("cmd"), "");
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
		cmd = BeanThreadLocal.find(map.get("cmd"), "");
		LogThreadLocal.setLog(true, "处理成功");
	}

	/**
	 * 访问地址
	 */
	private void logAccessUrl() throws Exception {
		String commLocalProject = SysConfig.findInstance().findMap().get("comm.local.type").toString();
		String stringBuffer =
				("项目comm.local.type=" + commLocalProject) + ";" + "当前IP=" + findServletIp() + ";" + "ServletPath="
						+ findServletPath() + ";" + "tokenAction=" + this.getClass().getName() + ";" ;
		log.info(stringBuffer);
	}

	private void returnCode400() {
		jrbThreadLocal.putEnum(ReturnCodeEnum.app400JSON);
		jrbThreadLocal.putSysEnum(ReturnCodeEnum.code400);
		ReturnCodeThreadLocal.setReturnCode(jrbThreadLocal);
	}
	private void returnCode401() {
		jrbThreadLocal.putEnum(ReturnCodeEnum.app401Token);
		jrbThreadLocal.putSysEnum(ReturnCodeEnum.code401);
		ReturnCodeThreadLocal.setReturnCode(jrbThreadLocal);
	}
	private void returnCode403() {
		jrbThreadLocal.putEnum(ReturnCodeEnum.app403TokenRSA);
		jrbThreadLocal.putSysEnum(ReturnCodeEnum.code403);
		ReturnCodeThreadLocal.setReturnCode(jrbThreadLocal);
	}
	private void returnCode404() {
		jrbThreadLocal.putEnum(ReturnCodeEnum.code404);
		jrbThreadLocal.putSysEnum(ReturnCodeEnum.code404);
		ReturnCodeThreadLocal.setReturnCode(jrbThreadLocal);
	}
	private void returnCode500() {
		jrbThreadLocal.putEnum(ReturnCodeEnum.app500VerifyCode);
		jrbThreadLocal.putSysEnum(ReturnCodeEnum.code500);
		ReturnCodeThreadLocal.setReturnCode(jrbThreadLocal);
	}
}
