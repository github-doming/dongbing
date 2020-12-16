package all.app.common.service;

import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_account.t.service.AppAccountTService;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.UserStateEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class AppAccountService extends AppAccountTService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String saveAppAccount(String commLocalTenantValue, AppAccountT entity) throws Exception {
		Date date = new Date();

		entity.setTenantCode(commLocalTenantValue);
		entity.setState(UserStateEnum.OPEN.getCode());
		entity.setCreateTime(date);
		entity.setCreateTimeLong(date.getTime());
		entity.setUpdateTime(date);
		entity.setUpdateTimeLong(date.getTime());
		return dao.save(entity);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void updateAppAccount(AppAccountT entity) throws Exception {
		Date date = new Date();
		entity.setUpdateTime(date);
		entity.setUpdateTimeLong(date.getTime());
		dao.update(entity);
	}
	/**
	 * 
	 * 查询AppAccount
	 * 
	 * @return
	 * @throws Exception
	 */
	public AppAccountT findAppAccount(String tenantCode, String accountName, String passwordInput) throws Exception {
		String password = null;
		String commLocalASE = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
		String commLocalASE_key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
				"");
		if ("true".equals(commLocalASE)) {
			password = CommASEUtil.encode(commLocalASE_key, passwordInput.trim()).trim();
		}
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "SELECT * FROM  app_account a "
				+ "WHERE a.TENANT_CODE_=? AND  a.ACCOUNT_NAME_ = ? AND a.PASSWORD_ = ? AND STATE_ = ?";
		parameterList.add(tenantCode);
		parameterList.add(accountName);
		parameterList.add(password);
		parameterList.add(UserStateEnum.OPEN.name());
		AppAccountT entity = (AppAccountT) this.dao.findObject(AppAccountT.class, sql, parameterList);
		return entity;
	}
	/**
	 * 
	 * 更新密码(通过appUserId)
	 * 
	 * @return
	 * @throws Exception
	 */
	public int updatePassword(String appUserId, String passwordInput) throws Exception {
		String password = null;
		String commLocalASE = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
		String commLocalASE_key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
				"");
		if ("true".equals(commLocalASE)) {
			password = CommASEUtil.encode(commLocalASE_key, passwordInput.trim()).trim();
		}
		List<Object> parameterList = new ArrayList<>();
		String sql = "update app_account set  PASSWORD_=? where APP_USER_ID_=?";
		parameterList.add(password);
		parameterList.add(appUserId);
        return this.dao.execute(sql, parameterList);
	}
	/**
	 * 
	 * 查询登录账户(通过密码)
	 * 
	 * @return
	 * @throws Exception
	 */
	public AppAccountT findAppAccountByPassword(String appUserId, String passwordInput) throws Exception {
		String password = null;
		String commLocalASE = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
		String commLocalASE_key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
				"");
		if ("true".equals(commLocalASE)) {
			password = CommASEUtil.encode(commLocalASE_key, passwordInput.trim()).trim();
		}
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "SELECT a.* from app_user u LEFT JOIN app_account a " + "on u.APP_USER_ID_=a.APP_USER_ID_  "
				+ "where a.APP_USER_ID_=? and a.PASSWORD_=?";
		parameterList.add(appUserId);
		parameterList.add(password);
		List<AppAccountT> list = this.dao.findObjectList(AppAccountT.class, sql, parameterList);
		if (list.size() > 0) {
			AppAccountT entity = list.get(0);
			return entity;
		} else {
			return null;
		}
	}
}
