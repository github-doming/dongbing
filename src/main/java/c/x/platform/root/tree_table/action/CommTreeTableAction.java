package c.x.platform.root.tree_table.action;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
/**
 * 
 * 支持事务;
 * 
 * 不需要登录;
 * 
 * 不需要菜单允许;
 * 
 * 
 * 
 */
public abstract class CommTreeTableAction extends TreeTableAxAction {
	public CommTreeTableAction() {
		transaction = true;
		this.login = false;
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public JsonTcpBean executeMenuAllow() throws Exception {
		return this.returnJsonTcpBean(this.execute());
	}
	public abstract String execute() throws Exception;
	// 添加新的方法
	public AyDao getDao() {
		return this.findDao();
	}
	public AyDao findDao() {
		AyDao dao = new AyDao();
		return dao;
	}
}
