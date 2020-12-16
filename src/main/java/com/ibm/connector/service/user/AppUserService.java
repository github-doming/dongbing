package com.ibm.connector.service.user;

import all.app_admin.app.app_user.t.service.AppUserTService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户服务类
 * @Author: Dongming
 * @Date: 2019-08-28 17:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AppUserService extends AppUserTService {

	/**
	 * 根据用户类型登录用户
	 *
	 * @param tenantCode    机构码
	 * @param accountName   账户
	 * @param passwordInput 密码
	 * @param type          用户类型
	 * @return 登录用户
	 */
	public AppUserT findAppUser(String tenantCode, String accountName, String passwordInput, String type) throws Exception {
		String password = null;
		String commLocalAse = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
		String commLocalAseKey = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
				"");
		if ("true".equals(commLocalAse)) {
			password = CommASEUtil.encode(commLocalAseKey, passwordInput.trim()).trim();
		}
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "SELECT * FROM app_user u " + "LEFT JOIN app_account a ON u.APP_USER_ID_ = a.APP_USER_ID_ "
				+ "WHERE a.TENANT_CODE_=? AND  a.ACCOUNT_NAME_ = ? AND a.PASSWORD_ = ? and u.APP_USER_TYPE_ = ? AND u.STATE_ != ? AND a.STATE_ = ?";
		parameterList.add(tenantCode);
		parameterList.add(accountName);
		parameterList.add(password);
		parameterList.add(type);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return (AppUserT) this.dao.findObject(AppUserT.class, sql, parameterList);
	}


	/**
	 * 根据账户名称获取 用户实体类
	 *
	 * @param accountName 账户名称
	 * @return 用户实体类
	 */
	public AppUserT findByAccountName(String accountName) throws Exception {
		String sql = "SELECT * FROM app_user u  LEFT JOIN app_account a ON u.APP_USER_ID_ = a.APP_USER_ID_ "
				+ "WHERE a.ACCOUNT_NAME_ = ? AND a.STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(accountName);
		parameterList.add(IbmStateEnum.DEL.name());
		return (AppUserT) this.dao.findObject(AppUserT.class, sql, parameterList);
	}

	/**
	 * 根据用户类型获取用户id
	 *
	 * @param types 用户类型列表
	 * @return 用户id列表
	 */
	public List<String> listIdByTypes(List<IbmTypeEnum> types) throws SQLException {
		if (ContainerTool.isEmpty(types)) {
			return null;
		}
		StringBuilder sql = new StringBuilder("SELECT APP_USER_ID_ FROM app_user where APP_USER_TYPE_ in (");
		List<Object> parameterList = new ArrayList<>(types.size());
		for (IbmTypeEnum type : types) {
			sql.append("?,");
			parameterList.add(type.name());
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		return dao.findStringList("APP_USER_ID_", sql.toString(), parameterList);
	}

	/**
	 * 获取初始化盘口信息的 用户信息
	 *
	 * @param type 用户类型
	 * @return 用户信息
	 */
	public List<Map<String, Object>> listInfo2InitHandicap(IbmTypeEnum type) throws SQLException {
		String sql = "SELECT APP_USER_ID_ FROM `app_user` where APP_USER_TYPE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(type.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);

	}


}
