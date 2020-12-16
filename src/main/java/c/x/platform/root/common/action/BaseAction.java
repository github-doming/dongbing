package c.x.platform.root.common.action;
import c.a.config.SysConfig;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.menu_allow.action.MenuAllowAction;
/**
 * 
 * 支持事务
 * 
 * 需要登录
 * 
 * 不需要菜单允许
 * 
 * 
 */
// public abstract class BaseAction extends RepeatLoginAction {
public abstract class BaseAction extends MenuAllowAction {
	public BaseAction() {
		// 需要数据库操作
		this.database = true;
		// 需要事务操作
		transaction = true;
		// 需要登录
		this.login = true;
		// 不需要菜单允许
		// this.menu_allow = true;
		this.menuAllow = false;
		// 需要token
		// this.token = true;
	}
	@Override
	public JsonTcpBean executeMenuAllow() throws Exception {
		String returnStr = SysConfig.configValueTrue;
		return this.returnJsonTcpBean(returnStr);
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
