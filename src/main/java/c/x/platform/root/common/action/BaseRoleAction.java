package c.x.platform.root.common.action;
import c.a.config.SysConfig;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
import c.x.platform.root.menu_allow.action.MenuAllowAction;
/**
 * 
 * 支持事务;
 * 
 * 需要登录;
 * 
 * 
 * 需要菜单允许;
 * 
 * 
 * 
 * 
 */
public abstract class BaseRoleAction extends MenuAllowAction {
	public BaseRoleAction() {
		// 需要数据库操作
		this.database = true;
		// 需要事务操作
		transaction = true;
		// 需要登录
		this.login = true;
		// 菜单允许
		this.menuAllow = true;
		// 需要token
		// this.token = true;
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
