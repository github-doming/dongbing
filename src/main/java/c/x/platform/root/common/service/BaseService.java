package c.x.platform.root.common.service;
import java.sql.SQLException;

import c.a.config.SysConfig;
import c.a.tools.jdbc.transaction.TransactionBase;
import c.a.tools.primary_key.PkSimpleTool;
import c.a.util.core.annotation.AnnotationTable;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.dao.BaseDao;
import c.x.platform.root.compo.tree_load.UpdatePathAll;
public class BaseService extends TransactionBase {
	// 机构名
	protected String tenantCode = null;
	protected BaseDao dao = new BaseDao();
	protected JsonTcpBean returnCodeJsonTcpBean = new JsonTcpBean();
	public BaseService() {
	}
	/**
	 * 
	 * 保存(自己保存主键)
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String savePkNot(Object entity) throws Exception {
		return dao.savePkNot(entity);
	}
	/**
	 * 更新所有path
	 * 
	 */
	public void updatePath(String tableName, String pk_key, String parentId)
			throws ClassNotFoundException, SQLException {
		UpdatePathAll updatePathAll = new UpdatePathAll();
		updatePathAll.update(tableName, pk_key, parentId);
	}
	/**
	 * 查找主键
	 * 
	 */
	public String findPK(Object object) throws Exception {
		// 机器key
		String machine_key = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalMachine), "");
		if (StringUtil.isNotBlank(machine_key)) {
			// 得到表名
			AnnotationTable table = (AnnotationTable) object.getClass().getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			// 主键
			String pk = PkSimpleTool.findInstance().findPk(machine_key, tableName);
			return pk;
		} else {
			return null;
		}
	}
	/**
	 * 查找主键
	 * 
	 */
	public String findPKSimple(Object object) throws Exception {
		// 机器key
		String machine_key = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalMachine), "");
		if (StringUtil.isNotBlank(machine_key)) {
			// 得到表名
			AnnotationTable table = (AnnotationTable) object.getClass().getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			// 主键
			String pk = PkSimpleTool.findInstance().findPk(machine_key, tableName);
			return pk;
		} else {
			return null;
		}
	}
	/**
	 * 查找主键
	 * 
	 */
	public String findPKSimple(String tableName) throws Exception {
		// 机器key
		String machine_key = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalMachine), "");
		if (StringUtil.isNotBlank(machine_key)) {
			// 主键
			String pk = PkSimpleTool.findInstance().findPk(machine_key, tableName);
			return pk;
		} else {
			return null;
		}
	}
}
