package com.ibs.common.core;

import c.a.config.SysConfig;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.tools.MvcActionTool;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import com.ibs.plan.module.cloud.ibsp_user.entity.IbspUser;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import org.doming.core.common.servlet.HttpServletTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import javax.servlet.http.Cookie;
import java.sql.SQLException;
import java.util.Map;

/**
 * @Description: MVC 后台管理 执行类
 * @Author: Dongming
 * @Date: 2019-09-07 15:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseAdminAction extends BaseMvcData {

	public IbspUser appUser;
	protected String appUserId;
	protected String token = null;

	protected String authorityAddress;

	/**
	 * 获取 请求数据 并初始化 访问对象 - 请求数据
	 *
	 * @return 访问对象
	 */
	public IbspUser findAdminUser() throws Exception {
		logAccessUrl();
		return findAdminUserPlus();
	}

	/**
	 * 查找管理员对象
	 *
	 * @return 管理员对象
	 */
	private IbspUser findAdminUserPlus() throws Exception {

		Object tokenObj = null;
		Map<String, Cookie> cookieMap = HttpServletTool.findCookieInfo(request);
		if (ContainerTool.notEmpty(cookieMap)) {
			//获取cookie中存储的数据
			token = HttpServletTool.findCookieVal(cookieMap, SysConfig.CurrentSysUser);
		}
		json = request.getParameter("json");
		if (token == null) {
			tokenObj = JSONObject.parseObject(json).get("token");
		}

		if (StringTool.isEmpty(tokenObj)) {
			MvcActionTool.returnCode400();
			return appUser;
		}
		token = tokenObj.toString();

		//查找管理员用户

		appUser = new IbspUserService().findUserByToken(token);
		if (appUser == null) {
			MvcActionTool.returnCode401();
			return appUser;
		}
		appUserId = appUser.getAppUserId();
		//权限校验
		boolean check = false;

		//用户属于系统级管理员
		if (ChannelTypeEnum.ADMIN.name().equals(appUser.getUserType())
				|| IbsTypeEnum.POWER_USER.name().equals(appUser.getUserType())) {
			// 校验用户权限
			if (checkAuthority()) {
				check = true;
			}
		}
		//用户校验成功 或者为超级管理员
		if (check || ChannelTypeEnum.SYS.name().equals(appUser.getUserType())) {
			// 解析数据
			analyzeDateMap();
			return appUser;
		}
		MvcActionTool.returnCode403();
		return appUser;
	}

	/**
	 * 校验用户权限
	 *
	 * @return 校验结果
	 */
	protected boolean checkAuthority() throws SQLException {
		// 从数据库读取出来权限，进行匹配
		String url = authorityAddress();
		AuthorityService authorityService = new AuthorityService();
		Map<String, Object> menu = authorityService.findUserMenu(appUserId, url);
		if (menu == null) {
//			return false;
		}
		return true;
	}

	/**
	 * 解析数据集
	 * json={cmd:"";data={}}
	 */
	private void analyzeDateMap() {
		if (StringTool.notEmpty(json)) {
			Map<String, Object> map = JSON.parseObject(json);
			if (ContainerTool.notEmpty(map)) {
				findDateMap(map);
			} else {
				log.info(IbsConfig.LOG_SIGN + "没有找到数据，所请求的json=" + json);
			}
		}
		LogThreadLocal.setLog(true, "处理成功");
	}

	/**
	 * 获取权限地址
	 *
	 * @return 权限地址输入 或者 请求地址
	 */
	private String authorityAddress() {
		return authorityAddress != null ? authorityAddress : findServletPath();
	}


}
