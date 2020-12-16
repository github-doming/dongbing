package com.cloud.user.connector.core;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.cloud.common.core.BaseMvcData;
import com.cloud.common.tool.EncryptTool;
import com.cloud.common.tool.MvcActionTool;
import com.cloud.user.app_user.entity.AppUser;
import com.cloud.user.app_user.service.AppUserService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.Connection;
import java.util.Map;
/**
 * MVC 用户执行类
 * @Author: Dongming
 * @Date: 2020-06-24 15:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseUserAction extends BaseMvcData {
	public BaseUserAction() {
		// 事物等级
		super.transaction = Connection.TRANSACTION_REPEATABLE_READ;
	}
	protected AppUser user;
	protected String userId = null;
	/**
	 * 获取 请求中的JSON数据 并初始化 访问对象 - 请求数据
	 *
	 * @return 访问对象
	 */
	public AppUser findUser() throws Exception {
		logAccessUrl();
		return this.findUserPlus();
	}

	/**
	 * 根据json初始化参数信息
	 *
	 * @return 访问对象
	 */
	private AppUser findUserPlus() {
		findJsonPlus();
		if (StringTool.notEmpty(json)) {
			Map<String, Object> map = JSON.parseObject(json);
			if (ContainerTool.notEmpty(map)) {
				findUser(map);
				if (LogThreadLocal.isSuccess()) {
					findDateMap(map);
				}
			} else {
				log.info("没有找到用户，所请求的request=" + getParamsString());
				MvcActionTool.returnCode400();
			}
		} else {
			log.info("没有找到用户，所请求的request=" + getParamsString());
			MvcActionTool.returnCode400();
		}
		if (user == null && LogThreadLocal.isSuccess()) {
			MvcActionTool.returnCode401();
		}
		return user;
	}

	/**
	 * 查找token携带的数据
	 *
	 * @param map json整个翻译map
	 */
	private void findUser(Map<String, Object> map) {
		token = BeanThreadLocal.find(map.get("token"), "");
		// 识别配置文件是否加密token
		String tokenRsa;
		try {
			tokenRsa = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.tokenRSA"), "");
		} catch (Exception e) {
			log.warn("读取配置失败,没有找到token是否加密字段-comm.local.tokenRSA");
			MvcActionTool.returnCode500();
			return;
		}
		boolean encryptToken = false;
		if (StringTool.notEmpty(tokenRsa)) {
			encryptToken = Boolean.parseBoolean(tokenRsa);
		}
		if (StringTool.notEmpty(token) && encryptToken) {
			// token解密
			String tokenDecode;
			try {
				tokenDecode = EncryptTool.decode(EncryptTool.Type.RSA, token);
			} catch (Exception e) {
				log.warn("token解密失败,token=" + token);
				MvcActionTool.returnCode500();
				return;
			}
			if (StringTool.notEmpty(tokenDecode)) {
				token = tokenDecode;
			} else {
				log.warn("token解密失败,解密的token为空" + tokenDecode);
				MvcActionTool.returnCode403Rsa();
				return;
			}
		}
		// 找到 CardAdmin
		try {

			user = new AppUserService().findUserByToken(token);
		} catch (Exception e) {
			log.error("查找用户失败", e);
			MvcActionTool.returnCode500();
			return;
		}
		if (user == null) {
			log.error("查找用户为空，token为：" + token);
			MvcActionTool.returnCode401();
			return;
		} else {
			userId = user.getAppUserId();
		}
		cmd = BeanThreadLocal.find(map.get("cmd"), "");
		LogThreadLocal.setLog(true, "处理成功");
	}

}
