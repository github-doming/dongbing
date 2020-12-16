package c.a.util.core.thread.common;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.tools.jdbc.transaction.DatabaseBase;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogUtil;
public abstract class TransactionThread extends DatabaseBase implements java.lang.Runnable {
	protected Logger log = LogManager.getLogger(this.getClass());
	protected boolean transaction = false;
	protected boolean database = false;
	/**
	 * key 最好用Object
	 */
	final Object lock_sendTask = new Object();
	// private final static String key = "key";
	protected final static Object key = new Object();
	@Override
	public void run() {
		this.executeTime();
	}
	public void executeTime() {
		// Calendar calendar = Calendar.getInstance();
		// long timeStart = calendar.getTimeInMillis();
		long timeStart = System.currentTimeMillis();
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		try {
			// log4j日志
			LogUtil.findInstance().init(SysConfig.servletContextPath);
			if (database) {
				if (transaction) {
					// 需要事务
					this.transaction();
				} else {
					// 不需要事务
					this.transactionNot();
				}
			} else {
				if (transaction) {
					// 需要事务
					this.transaction();
				} else {
					// 不需要数据库操作
					this.databaseNot();
				}
			}
		} catch (Exception e) {
			log.error("线程出错", e);
			System.out.println("线程出错");
			e.printStackTrace();
		}
		contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil != null) {
			contextUtil.remove();
			ContextThreadLocal.findThreadLocal().remove();
		}
		// calendar = Calendar.getInstance();
		// long timeEnd = calendar.getTimeInMillis();
		long timeEnd = System.currentTimeMillis();
		long timeSpend = timeEnd - timeStart;
		// BaseLog.trace(Thread.currentThread().getName() + " 花费时间timeSpend=" +
		// timeSpend);
		log.trace(Thread.currentThread().getName() + " 花费时间timeSpend=" + timeSpend);
	}
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
	 * 不需要数据库操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean databaseNot() throws Exception {
		JsonTcpBean returnStr = this.executeTransaction();
		return returnStr;
	}
	/**
	 * 
	 * 不需要事务
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean transactionNot() throws Exception {
		IJdbcTool jdbcTool = null;
		try {
			jdbcTool = this.findJdbcToolLocal();
			JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
			// 启动
			this.databaseStart(jdbcTool);
			JsonTcpBean returnStr = executeTransaction();
			return returnStr;
		} catch (Exception e) {
			e.printStackTrace();
			// 重新抛出异常
			throw e;
		} finally {
			this.databaseClose(jdbcTool);
			// 从ThreadLocal中remove连接
			JdbcThreadLocal.findJdbcToolThreadLocal().remove();
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
		try {
			jdbcTool = this.findJdbcToolLocal();
			JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
			// 启动事务
			this.transactionStart(jdbcTool);
			JsonTcpBean returnStr = executeTransaction();
			// 事务提交
			this.transactionEnd(jdbcTool);
			return returnStr;
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
			// 重新抛出异常
			throw e;
		} finally {
			this.transactionClose(jdbcTool);
			// 从ThreadLocal中remove连接
			JdbcThreadLocal.findJdbcToolThreadLocal().remove();
		}
	}
	/**
	 * 返回JsonTcpBean
	 * @Title: returnJsonTcpBean 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param mvcResult
	 * @return
	 * @throws Exception 
	 * 返回类型 JsonTcpBean
	 */
	public JsonTcpBean returnJsonTcpBean(String mvcResult) throws Exception {
		JsonTcpBean _JsonTcpBean=new JsonTcpBean();
		_JsonTcpBean.setMvcResult(mvcResult);
		return _JsonTcpBean;
	}
}
