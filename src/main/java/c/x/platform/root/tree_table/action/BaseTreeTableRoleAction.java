package c.x.platform.root.tree_table.action;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
/**
 * 
 * 支持事务;
 * 
 * 需要登录;
 * 
 * 需要菜单允许;
 * 
 * 
 * 
 */
public abstract class BaseTreeTableRoleAction extends TreeTableAxAction {
	public BaseTreeTableRoleAction() {
		transaction = true;
		this.login = true;
		// 菜单允许
		this.menuAllow = true;
	}
	@Override
	public JsonTcpBean executeMenuAllow() throws Exception {
		return this.returnJsonTcpBean(this.execute());
	}
	public abstract String execute() throws Exception;
	// 添加新的方法
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
}
