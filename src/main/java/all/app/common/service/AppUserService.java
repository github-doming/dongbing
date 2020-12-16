package all.app.common.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.eclipse.jetty.util.StringUtil;

import all.app_admin.root.login_not.current.CurrentAppUser;
import all.gen.app_user.t.entity.AppUserT;
import all.gen.app_user.t.service.AppUserTService;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
public class AppUserService extends AppUserTService {
	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 
	 * 查询AppUser(通过appUserId)
	 * 
	 * @return
	 * @throws Exception
	 */
	public AppUserT findAppUserByAppUserId(String appUserId) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "select * from app_user u where u.APP_USER_ID_=?";
		parameterList.add(appUserId);
		AppUserT entity = (AppUserT) this.dao.findObject(AppUserT.class, sql, parameterList);
		return entity;
	}
	/**
	 * 
	 * 查询AppUser(通过账户名称)
	 * 
	 * @return
	 * @throws Exception
	 */
	public AppUserT findAppUserByAccountName(String accountName) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "SELECT * FROM app_user u " + "LEFT JOIN app_account a ON u.APP_USER_ID_ = a.APP_USER_ID_ "
				+ "WHERE a.ACCOUNT_NAME_ = ?";
		parameterList.add(accountName);
		AppUserT entity = (AppUserT) this.dao.findObject(AppUserT.class, sql, parameterList);
		return entity;
	}
	/**
	 * 
	 * 查询AppUser
	 * 
	 * @return
	 * @throws Exception
	 */
	public AppUserT findAppUser(String tenantCode, String accountName, String passwordInput) throws Exception {
		String password = null;
		String commLocalASE = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
		String commLocalASE_key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
				"");
		if ("true".equals(commLocalASE)) {
			password = CommASEUtil.encode(commLocalASE_key, passwordInput.trim()).trim();
		}
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "SELECT * FROM app_user u " + "LEFT JOIN app_account a ON u.APP_USER_ID_ = a.APP_USER_ID_ "
				+ "WHERE a.TENANT_CODE_=? AND  a.ACCOUNT_NAME_ = ? AND a.PASSWORD_ = ?";
		parameterList.add(tenantCode);
		parameterList.add(accountName);
		parameterList.add(password);
		AppUserT entity = (AppUserT) this.dao.findObject(AppUserT.class, sql, parameterList);
		return entity;
	}
	/**
	 * 
	 * 查询AppUser(通过token)
	 * 
	 * @return
	 * @throws Exception
	 */
	public AppUserT findAppUserByToken(String token) throws Exception {
		AppUserT entity = null;
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(token.trim());
		String sql = "select APP_USER_ID_ from app_token where value_=?";
		String appUserId = this.dao.findString("APP_USER_ID_", sql, parameterList);
		if (StringUtil.isNotBlank(appUserId)) {
			parameterList = new ArrayList<Object>();
			parameterList.add(appUserId.trim());
			sql = "SELECT * FROM app_user  WHERE APP_USER_ID_ = ?";
			entity = (AppUserT) this.dao.findObject(AppUserT.class, sql, parameterList);
		}
		return entity;
	}
	/**
	 * 
	 * 分页
	 * 
	 * @param order
	 * @param order_property
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(CurrentAppUser currentUser, String sortOrderName,
			String sortFieldName, Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM APP_USER ";
			sql = "SELECT * FROM APP_USER  order by APP_USER_ID_ desc";
		} else {
			sql_count = "SELECT count(*) FROM APP_USER ";
			sql = "SELECT * FROM APP_USER  order by " + sortFieldName + " " + sortOrderName;
		}
		PageCoreBean<Map<String, Object>> page = dao.page(sql, parameters, pageIndex.intValue(), pageSize.intValue(),
				sql_count);
		List<Map<String, Object>> listMap = page.getList();
		for (Map<String, Object> map : listMap) {
			Object desc = map.get("DESC_");
			if (desc == null) {
				map.put("DESC_", "");
			}
		}
		return page;
	}

}
