package c.a.tools.crud.action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import c.a.config.SysConfig;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.mvc.action.MvcAction;
import c.a.tools.primary_key.PkSimpleTool;
import c.a.util.core.annotation.AnnotationTable;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.data_source.DataSourceListUtil;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
/**
 * 事务;
 * 
 * 只有一个数据库;
 * 
 * 
 * 
 */
public abstract class TransactionAction extends MvcAction {
	protected Logger log = LogManager.getLogger(TransactionAction.class);
	// protected Logger log = LogManager.getLogger(this.getClass());
	public boolean transaction = false;
	public boolean database = false;
	public String databaseId = DataSourceListUtil.Local;
	public boolean transactionAsync = false;
	public boolean databaseAsync = false;
	public String databaseAsyncId = DataSourceListUtil.Local;
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract JsonTcpBean executeTransaction() throws Exception;
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean executeBiz() throws Exception {
		return this.returnJsonTcpBean(this.execute());
	}
	/**
	 * 下一个action
	 * @Title: execute 
	 * @Description: 
	 *
	 * 参数说明 
	 * @return
	 * @throws Exception 
	 * 返回类型 String
	 */
	public abstract String execute() throws Exception;
	/**
	 * 实现父类方法
	 * 
	 */
	@Override
	public JsonTcpBean executeMvc() throws Exception {
		if (database) {
			if (transaction) {
				// 需要事务
				return this.transaction();
			} else {
				// 不需要事务
				return this.database();
			}
		} else {
			if (transaction) {
				// 需要事务
				return this.transaction();
			} else {
				// 不需要数据库操作
				return this.databaseNot();
			}
		}
	}
	/**
	 * 
	 * 需要事务
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean transaction() throws Exception {
		IJdbcTool jdbcTool = null;
		JsonTcpBean _JsonTcpBean = null;
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			_JsonTcpBean = executeTransaction();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
			// 重新抛出异常
			throw e;
		} finally {
			this.transactionClose(jdbcTool);
		}
		// AssertUtil.isBlank(returnStr, "返回不能为空");
		if (SysConfig.configValueTrue.equals(_JsonTcpBean.getMvcResult())) {
			try {
				jdbcTool = this.findJdbcTool(databaseId);
				this.transactionStart(jdbcTool);
				_JsonTcpBean = executeBiz();
				this.transactionEnd(jdbcTool);
			} catch (Exception e) {
				e.printStackTrace();
				this.transactionRoll(jdbcTool);
				// 重新抛出异常
				throw e;
			} finally {
				this.transactionClose(jdbcTool);
			}
		}
		return _JsonTcpBean;
	}
	/**
	 * 
	 * 不需要事务
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean database() throws Exception {
		IJdbcTool jdbcTool = null;
		JsonTcpBean _JsonTcpBean = null;
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			_JsonTcpBean = executeTransaction();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
			// 重新抛出异常
			throw e;
		} finally {
			this.transactionClose(jdbcTool);
		}
		// AssertUtil.isBlank(returnStr, "返回不能为空");
		if (SysConfig.configValueTrue.equals(_JsonTcpBean.getMvcResult())) {
			try {
				jdbcTool = this.findJdbcTool(databaseId);
				this.databaseStart(jdbcTool);
				_JsonTcpBean = executeBiz();
			} catch (Exception e) {
				e.printStackTrace();
				// 重新抛出异常
				throw e;
			} finally {
				this.databaseClose(jdbcTool);
			}
		}
		return _JsonTcpBean;
	}
	/**
	 * 
	 * 不需要数据库操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean databaseNot() throws Exception {
		IJdbcTool jdbcTool = null;
		JsonTcpBean _JsonTcpBean = null;
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			_JsonTcpBean = executeTransaction();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
			// 重新抛出异常
			throw e;
		} finally {
			this.transactionClose(jdbcTool);
		}
		// AssertUtil.isBlank(returnStr, "返回不能为空");
		if (SysConfig.configValueTrue.equals(_JsonTcpBean.getMvcResult())) {
			_JsonTcpBean = executeBiz();
		}
		return _JsonTcpBean;
	}
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
	 * 
	 * @return
	 */
	public AyDao findDao() {
		AyDao dao = new AyDao();
		return dao;
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean executeAsync() throws Exception {
		return null;
	}
	@Override
	public JsonTcpBean executeMvcAsynchronous() throws Exception {
		if (databaseAsync) {
			if (transactionAsync) {
				// 需要事务
				return this.transactionAsynchronous();
			} else {
				// 不需要事务
				return this.databaseAsynchronous();
			}
		} else {
			if (transactionAsync) {
				// 需要事务
				return this.transactionAsynchronous();
			} else {
				// 不需要数据库操作
				return this.databaseNotAsynchronous();
			}
		}
	}
	/**
	 * 
	 * 需要事务
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean transactionAsynchronous() throws Exception {
		IJdbcTool jdbcTool = null;
		JsonTcpBean returnStr = null;
		try {
			jdbcTool = this.findJdbcTool(this.databaseAsyncId);
			this.transactionStart(jdbcTool);
			executeAsync();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
			// 重新抛出异常
			throw e;
		} finally {
			this.transactionClose(jdbcTool);
		}
		return returnStr;
	}
	/**
	 * 
	 * 不需要事务
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean databaseAsynchronous() throws Exception {
		IJdbcTool jdbcTool = null;
		JsonTcpBean returnStr = null;
		try {
			jdbcTool = this.findJdbcTool(this.databaseAsyncId);
			this.databaseStart(jdbcTool);
			returnStr = executeAsync();
		} catch (Exception e) {
			e.printStackTrace();
			// 重新抛出异常
			throw e;
		} finally {
			this.databaseClose(jdbcTool);
		}
		return returnStr;
	}
	/**
	 * 
	 * 不需要数据库操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean databaseNotAsynchronous() throws Exception {
		JsonTcpBean returnStr = null;
		returnStr = executeAsync();
		return returnStr;
	}
}
