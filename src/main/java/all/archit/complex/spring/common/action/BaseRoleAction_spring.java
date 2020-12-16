package all.archit.complex.spring.common.action;
import all.archit.complex.spring.tool.menu_allow.action.MenuAllowAction_spring;
import c.a.util.core.json.JsonTcpBean;
/**
 * 
 * 需要角色
 * 
 * 
 */
public abstract class BaseRoleAction_spring extends MenuAllowAction_spring {
	public BaseRoleAction_spring() {
		// 需要数据库操作
		this.database = true;
		// 需要事务操作
		transaction = true;
		// 需要登录
		this.login = true;
		// 菜单允许
		this.menu_allow = true;
	}
	@Override
	public JsonTcpBean executeMenuAllow() throws Exception {
		return this.returnJsonTcpBean(this.execute());
	}
	public abstract String execute() throws Exception;
	// 添加新的方法
}
