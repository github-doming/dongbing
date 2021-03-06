package com.ibs.common.core;

import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.tools.EncryptTool;
import com.ibs.common.tools.MvcActionTool;
import com.ibs.plan.module.cloud.ibsp_sys_manage_time.service.IbspSysManageTimeService;
import com.ibs.plan.module.cloud.ibsp_user.entity.IbspUser;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.container.LruMap;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * MVC 用户执行类
 *
 * @Author: null
 * @Date: 2020-05-19 16:18
 * @Version: v1.0
 */
public abstract class BaseUserAction extends BaseMvcData {

	protected boolean isTime = true;

	protected String token = null;
	protected boolean encryptToken = false;
	protected IbspUser appUser = null;
	protected String appUserId = null;


	private boolean restrictAccess = false;
	private long interval;
	private int accessCount;
	protected static LruMap<String, Map<String, Long>> lruMap;

	/**
	 * 获取 请求中的JSON数据 并初始化 访问对象 - 请求数据
	 *
	 * @return 访问对象
	 */
	public IbspUser findAppUser() throws Exception {
		logAccessUrl();
		return this.findAppUserPlus();
	}


	/**
	 * 根据json初始化参数信息
	 *
	 * @return 访问对象
	 */
	private IbspUser findAppUserPlus() throws SQLException {
		findJsonPlus();
		if (StringTool.notEmpty(json)) {
			Map<String, Object> map = JSON.parseObject(json);
			if (ContainerTool.notEmpty(map)) {
				findAppUser(map);
				if (LogThreadLocal.isSuccess()) {
					findDateMap(map);
				}
			} else {
				log.info(IbsConfig.LOG_SIGN + "没有找到用户，所请求的request=" + getParamsString());
				MvcActionTool.returnCode400();
			}
		} else {
			log.info(IbsConfig.LOG_SIGN + "没有找到用户，所请求的request=" + getParamsString());
			MvcActionTool.returnCode400();
		}
		if (appUser == null && LogThreadLocal.isSuccess()) {
			MvcActionTool.returnCode401();
		}
		return appUser;
	}

	/**
	 * 查找token携带的数据
	 *
	 * @param map json整个翻译map
	 */
	private void findAppUser(Map<String, Object> map) throws SQLException {
		token = BeanThreadLocal.find(map.get("token"), "");
		//查询是否限制访问
		if (restrictAccess && restrictAccess(token)) {
			MvcActionTool.return403Restrict();
			return;
		}
		// 识别配置文件是否加密token
		String tokenRsa;
		try {
			tokenRsa = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.tokenRSA"), "");
		} catch (Exception e) {
			log.warn(IbsConfig.LOG_SIGN + "读取配置失败,没有找到token是否加密字段-comm.local.tokenRSA");
			MvcActionTool.returnCode500();
			return;
		}
		if (StringTool.notEmpty(tokenRsa)) {
			encryptToken = Boolean.parseBoolean(tokenRsa);
		}
		if (StringTool.notEmpty(token) && encryptToken) {
			// token解密
			String tokenDecode;
			try {
				tokenDecode = EncryptTool.decode(EncryptTool.Type.RSA, token);
			} catch (Exception e) {
				log.warn(IbsConfig.LOG_SIGN + "token解密失败,token=" + token);
				MvcActionTool.returnCode500();
				return;
			}
			if (StringTool.notEmpty(tokenDecode)) {
				token = tokenDecode;
			} else {
				log.warn(IbsConfig.LOG_SIGN + "token解密失败,解密的token为空" + tokenDecode);
				MvcActionTool.returnCode403Rsa();
				return;
			}
		}
		// 找到AppUserT
		try {
			appUser =  new IbspUserService().findUserByToken(token);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "查找用户失败", e);
			MvcActionTool.returnCode500();
			return;
		}
		if (appUser == null) {
			log.error(IbsConfig.LOG_SIGN + "查找用户为空，token为：" + token);
			MvcActionTool.returnCode401();
			return;
		} else {
			appUserId = appUser.getAppUserId();
		}
		if (isTime){
			long endTime = new IbspSysManageTimeService().getEndLongTime(appUserId);
			if (endTime < System.currentTimeMillis()) {
				MvcActionTool.return403Time();
				return;
			}
		}

		cmd = BeanThreadLocal.find(map.get("cmd"), "");
		LogThreadLocal.setLog(true, "处理成功");
	}

	/**
	 * 访问限制
	 *
	 * @param token 访问token
	 * @return 访问成功 false 失败 true
	 */
	private boolean restrictAccess(String token) {
		if (!restrictAccess) {
			return false;
		}
		Map<String, Long> accessInfo = lruMap.get(token);
		if (accessInfo == null) {
			accessInfo = new HashMap<>(2);
			accessInfo.put("count", 1L);
			accessInfo.put("time", System.currentTimeMillis());
			lruMap.put(token, accessInfo);
			return false;
		}
		long count = accessInfo.get("count");
		if (count > accessCount) {
			if (System.currentTimeMillis() - accessInfo.get("time") < interval) {
				accessInfo.put("count", ++count);
				return true;
			} else {
				accessInfo.put("count", 1L);
				accessInfo.put("time", System.currentTimeMillis());
			}
		} else {
			accessInfo.put("count", ++count);
		}
		return false;
	}

	/**
	 * 添加访问属性
	 *
	 * @param restrictAccess 是否进行访问限制
	 * @param interval       访问间隔时间
	 */
	protected void accessAttr(boolean restrictAccess, int accessCount, int interval) {
		this.restrictAccess = restrictAccess;
		this.accessCount = accessCount;
		this.interval = interval;

	}

}
