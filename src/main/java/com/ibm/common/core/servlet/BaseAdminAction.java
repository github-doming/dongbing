package com.ibm.common.core.servlet;
import c.a.config.SysConfig;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.MvcActionTool;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.connector.service.user.AdminUser;
import com.ibm.connector.service.user.AdminUserService;
import org.doming.core.common.servlet.HttpServletTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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

	protected AdminUser adminUser;
	protected String adminUserId;
	protected String token = null, type = null;

	protected String authorityAddress;

	/**
	 * 获取 请求数据 并初始化 访问对象 - 请求数据
	 *
	 * @return 访问对象
	 */
	public AdminUser findAdminUser() throws Exception {
		logAccessUrl();
		return findAdminUserPlus();
	}

	/**
	 * 查找管理员对象
	 *
	 * @return 管理员对象
	 */
	private AdminUser findAdminUserPlus() throws Exception {

		Object tokenObj = null;
		Map<String, Cookie> cookieMap = HttpServletTool.findCookieInfo(request);
		if (ContainerTool.notEmpty(cookieMap)) {
			//获取cookie中存储的数据
			token = HttpServletTool.findCookieVal(cookieMap, SysConfig.CurrentSysUser);
			type = HttpServletTool.findCookieVal(cookieMap, SysConfig.CurrentType);
		}
		json =request.getParameter("json");
		if (token == null) {
			tokenObj = JSONObject.parseObject(json).get("token");
		}
		if (type == null) {
			type = ChannelTypeEnum.ADMIN.name();
		}
		if (StringTool.isEmpty(tokenObj)) {
			MvcActionTool.returnCode404();
			return adminUser;
		}
		token = tokenObj.toString();
		type = type.toUpperCase();
		//查找管理员用户
		AdminUserService userService = new AdminUserService();
		adminUser = userService.findUserByToken(token);
		if (adminUser == null) {
			MvcActionTool.returnCode401();
			return adminUser;
		}
		adminUserId = adminUser.getUserId();
		//权限校验
		boolean check = false;

		//用户属于系统级管理员
		if (ChannelTypeEnum.ADMIN.name().equals(adminUser.getUserType())||
				IbmTypeEnum.POWER_USER.name().equals(adminUser.getUserType())) {
			// 校验用户权限
			if (checkAuthority()) {
				check = true;
			}
		}
		//用户校验成功 或者为超级管理员
		if (check || ChannelTypeEnum.SYS.name().equals(adminUser.getUserType())) {
			// 解析数据
			analyzeDateMap();
			return adminUser;
		}
		MvcActionTool.returnCode403();
		return adminUser;
	}

	/**
	 * 校验用户权限
	 * @return 校验结果
	 */
	protected boolean checkAuthority() throws SQLException {
		// 从数据库读取出来权限，进行匹配
		String url = authorityAddress();
		AuthorityService authorityService = new AuthorityService();
		Map<String, Object> menu = authorityService.findUserMenu(adminUser.getUserId(), url);
		if (menu == null){
			//return false;
		}
		// TODO: 2020/3/27 匹配特殊权限
		return true;
	}

	/**
	 * 校验用户权限
	 * @return 校验结果
	 */
	protected boolean checkAuthority2() throws SQLException {
		// 从数据库读取出来权限，进行匹配
		String url = authorityAddress();
		String menuIds = request.getSession().getAttribute("ID").toString();
		AuthorityService authorityService = new AuthorityService();
		Map<String, Object> menu = authorityService.checkAuthority(menuIds, url);
		if (menu == null){
			return false;
		}
		// TODO: 2020/3/27 匹配特殊权限
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
				log.info(IbmMainConfig.LOG_SIGN + "没有找到数据，所请求的json=" + json);
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
