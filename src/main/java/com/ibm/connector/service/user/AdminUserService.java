package com.ibm.connector.service.user;

import c.x.platform.root.common.service.BaseService;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import org.doming.core.enums.StateEnum;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description: 后台管理用户服务类
 * @Author: Dongming
 * @Date: 2019-09-07 15:55
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AdminUserService extends BaseService {

	public AdminUser findUserByToken(String token) throws Exception {
		String sql = "SELECT iu.APP_USER_ID_ USER_ID_,NICKNAME_ USER_NAME_, iu.USER_TYPE_ FROM ibm_user iu LEFT JOIN ibm_user_token iut on " +
				" iu.APP_USER_ID_ = iut.APP_USER_ID_ and iut.STATE_ = ? where iut.VALUE_ = ? and iu.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(token);
		parameterList.add(StateEnum.OPEN.name());
		return super.dao.findObject(AdminUser.class, sql, parameterList);
	}

	/**
	 * 根据管理者token 和 所属类型 获取管理用户
	 *
	 * @param token  通讯token
	 * @param type 用户所属类型
	 * @return 管理用户
	 */
	public AdminUser find(String token, String tenant, String type) throws Exception {
		String sql;
		List<Object> parameterList = new ArrayList<>(4);
		if (IbmTypeEnum.ADMIN.equal(type)) {
			sql = "SELECT au.APP_USER_ID_ as USER_ID_,au.APP_USER_NAME_ as USER_NAME_,au.APP_USER_TYPE_ as USER_TYPE_  "
					+ " FROM `app_user` au  LEFT JOIN app_token at on `at`.APP_USER_ID_ = au.APP_USER_ID_ where "
					+ " au.TENANT_CODE_ = ? and au.APP_USER_TYPE_ = ? and `at`.VALUE_ = ? and `at`.STATE_ = ? and au.STATE_ = ?";
			parameterList.add(tenant);
			parameterList.add(type);
		} else if (IbmTypeEnum.SYS.equal(type)) {
			sql = "SELECT su.SYS_USER_ID_ as USER_ID_,su.SYS_USER_NAME_ as USER_NAME_,'SYS' as USER_TYPE_ FROM `sys_user` su "
					+ " LEFT JOIN sys_sso ss on `ss`.SYS_USER_ID_ = su.SYS_USER_ID_ where "
					+ " `ss`.VALUE_ = ? and `ss`.STATE_ = ? and  su.STATE_ = ?";
		} else {
			log.trace("不存在的请求平台类型".concat(type));
			return null;
		}
		parameterList.add(token);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return (AdminUser) super.dao.findObject(AdminUser.class, sql, parameterList);
	}
}
