package c.x.platform.root.tree_table.action;
import c.a.config.SysConfig;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
/**
 * 
 * 
 * 
 * 
 * 
 * 支持事务;
 * 
 * 需要登录;
 * 
 * 不需要菜单允许;
 * 
 * 
 * 
 */
public abstract class BaseTreeTableAction extends TreeTableAxAction {
	public BaseTreeTableAction() {
		transaction = true;
		this.login = true;
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public JsonTcpBean executeMenuAllow() throws Exception {
		return this.returnJsonTcpBean(SysConfig.configValueTrue);
	}
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
