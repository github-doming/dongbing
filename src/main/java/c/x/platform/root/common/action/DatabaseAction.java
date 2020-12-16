package c.x.platform.root.common.action;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
import c.x.platform.root.menu_allow.action.MenuAllowAction;

/**
 * 
 * 
 * 只需要数据库操作
 * 
 * 不需要事务
 * 
 * 不需要登录
 * 
 * 
 * 不需要菜单允许
 * 
 * 
 */
// public abstract class BaseAction extends RepeatLoginAction {
public abstract class DatabaseAction extends MenuAllowAction {
	public DatabaseAction() {
		// 需要数据库操作
		this.database = true;
		// 不需要事务操作
		transaction = false;
		// 需要登录
		this.login = false;
		// 菜单允许
		// this.menu_allow = true;
		this.menuAllow = false;
		// 不需要token
		this.token = false;
	}

	// -- 下面添加新的方法 --/
	/**
	 * 
	 * @return
	 */
	public AyDao getDao() {
		return this.findDao();
	}

	/**
	 * 
	 * @return
	 */
	public AyDao findDao() {
		AyDao dao = new AyDao();
		return dao;
	}

	// -- 下面的方法不再更新 --//
	// {
	@Override
	public JsonTcpBean executeMenuAllow() throws Exception {
		String returnStr = SysConfig.configValueTrue;
		return this.returnJsonTcpBean(returnStr);
	}
	// }
	// -- 上面的方法不再更新 --/
}
