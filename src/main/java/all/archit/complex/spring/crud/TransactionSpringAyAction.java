package all.archit.complex.spring.crud;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.WebContentGenerator;
import all.archit.complex.spring.jdbc.threadlocal.JdbcTemplateThreadLocal;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.tools.log.custom.common.BaseLog;
import c.a.util.core.data_source.ay.JdbcDataSourceAy;
import c.a.util.core.data_source.ay.JdbcDataSourceListAy;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
/**
 * spring事务
 * 
 * 
 * 
 */
public abstract class TransactionSpringAyAction extends WebContentGenerator implements Controller {
	protected Logger log = LogManager.getLogger(this.getClass());
	public static String properties_url = "/config/platform/core.properties";
	public static String driverClassName = null;
	public static String jdbc_url = null;
	public static String jdbc_username = null;
	public static String jdbc_password = null;
	public static Boolean transaction = false;
	public static Boolean database = false;
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String executeTransaction() throws Exception;
	/**
	 * 实现父类方法
	 * 
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = null;
		try {
			// 初始化
			this.servletContext = this.getServletContext();
			this.request = request;
			this.response = response;
			String returnStr = null;
			if (database) {
				if (transaction) {
					/**
					 * 
					 * 需要事务
					 */
					returnStr = this.transaction();
				} else {
					/**
					 * 
					 * 不需要事务
					 */
					returnStr = this.transactionNot();
				}
			} else {
				/**
				 * 
				 * 不需要数据库操作
				 */
				returnStr = this.databaseNot();
			}
			modelAndView = new ModelAndView(returnStr);
		} catch (Exception e) {
			String logStr = "TransactionAction_spring 出错，业务出错=";
			// BaseLog.trace(logStr);
			e.printStackTrace();
			// 写日志
			this.log2database(logStr, this.getServletContext(), e, request.getServletPath());
			this.exception_forward_jsp(request, response);
		}
		return modelAndView;
	}
	/*
	 * 日志
	 * 
	 * @param cServletContext
	 * 
	 * @param exception
	 * 
	 * @param servletPath
	 * 
	 * @throws ServletException
	 * 
	 * @throws IOException
	 */
	public void log2database(String logStr, ServletContext cServletContext, Exception exception, String servletPath)
			throws ServletException, IOException {
		log.error("TransactionAction_spring出错");
		log.error(logStr, exception);
	}
	/**
	 * Exception异常时，返回jsp页面
	 * 
	 * @param request
	 * @param response
	 */
	public void exception_forward_jsp(HttpServletRequest request, HttpServletResponse response) {
		log.error("exception_forward_jsp");
		// 判断是否已经提交，如果提交不重复提交
		if (response.isCommitted()) {
		} else {
			// httpServletResponse.sendError(404);
			String returnPage_relative = "/pages/co/xinyu/mvc/error/500_mvc.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(returnPage_relative);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * 不需要数据库操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String databaseNot() throws Exception {
		String returnStr = this.executeTransaction();
		return returnStr;
	}
	/**
	 * 
	 * 不需要事务;
	 * 
	 * 每次取的连接都不同;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String transactionNot() throws Exception {
		JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance().findLocal();
		Connection connection = jdbcDataSource.getConnection();
		ServletContext servletContext = this.getServletContext();
		// 返回
		String returnStr = null;
		// 注入数据源
		JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcDataSource);
		// 保存jdbcTemplate
		JdbcTemplateThreadLocal.findThreadLocalJdbcTemplate().set(jdbcTemplate);
		if (true) {
			connection = jdbcDataSource.getConnection();
			// 保存连接到ThreadLocal
		}
		try {
			// 业务开始
			returnStr = this.executeTransaction();
			// 业务结束
			// 如果连接可用则放回去,不可用则弃用
			// 连接回收，用spring数据源 回收连接
			DataSourceUtils.releaseConnection(connection, jdbcDataSource);
			BaseLog.trace("用spring数据源关闭连接 1");
		} catch (java.lang.RuntimeException e) {
			// e.printStackTrace();
			System.out.println("RuntimeException");
			// 用简单数据源 DataSource关闭连接
			jdbcDataSource.connectionRemove(connection);
			BaseLog.trace("用简单数据源 DataSource关闭连接 2");
			throw e;
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Exception");
			// 用简单数据源 DataSource关闭连接
			jdbcDataSource.connectionRemove(connection);
			BaseLog.trace("用简单数据源 DataSource关闭连接 3");
			throw e;
		} finally {
			// 从ThreadLocal中remove连接
			JdbcThreadLocal.findJdbcToolThreadLocal().remove();
			// 从ThreadLocal中remove 去掉jdbcTemplate
			JdbcTemplateThreadLocal.findThreadLocalJdbcTemplate().remove();
			/**
			 * 结论 finally内部使用 return 语句是一种很不好的习惯，
			 * 
			 * 如果try中还有return语句，它会覆盖了try 区域中return语句的返回值，程序的可读性差。
			 * 
			 * 面对上述情况，其实更合理的做法是，既不在tryblock内部中使用return语句，
			 * 
			 * 也不在finally内部使用 return语句，而应该在 finally
			 * 
			 * 语句之后使用return来表示函数的结束和返回。
			 */
			// return returnStr;
		}
		return returnStr;
	}
	/**
	 * 
	 * 需要事务;
	 * 
	 * 每次取的连接都相同;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String transaction() throws Exception {
		JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance().findLocal();
		Connection connection = jdbcDataSource.getConnection();
		ServletContext servletContext = this.getServletContext();
		// 返回
		String returnStr = null;
		// 注入数据源
		JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcDataSource);
		// 保存jdbcTemplate
		JdbcTemplateThreadLocal.findThreadLocalJdbcTemplate().set(jdbcTemplate);
		DataSourceTransactionManager transactionManager = null;
		// 事务状态
		TransactionStatus transactionStatus = null;
		try {
			// if (false) {
			if (true) {
				// 注入数据源到事务管理器
				transactionManager = new DataSourceTransactionManager(jdbcDataSource);
				// 事务定义类
				DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
				if (false) {
					/**
					 * org.springframework.transaction.
					 * CannotCreateTransactionException:
					 * 
					 * Could not open JDBC Connection for transaction; nested
					 * 
					 * exception is
					 * 
					 * com.mysql.jdbc.exceptions.jdbc4.CommunicationsException :
					 * 
					 * Communications link failure
					 */
					/**
					 * org.springframework.web.util.NestedServletException:
					 * 
					 * Request processing failed; nested exception is
					 * 
					 * org.springframework
					 * .transaction.CannotCreateTransactionException:
					 * 
					 * Could not open JDBC Connection for transaction; nested
					 * 
					 * exception is
					 * 
					 * 
					 * com.mysql.jdbc.exceptions.jdbc4.CommunicationsException:
					 * 
					 * Communications link failure
					 */
					/**
					 * The last packet sent successfully to the server was 0
					 * 
					 * milliseconds ago. The driver has not received any packets
					 * 
					 * from the server.
					 */
					/**
					 * Caused by:
					 * 
					 * org.springframework.transaction.
					 * CannotCreateTransactionException:
					 * 
					 * Could not open JDBC Connection for transaction; nested
					 * 
					 * exception is
					 * 
					 * com.mysql.jdbc.exceptions.jdbc4.CommunicationsException:
					 * 
					 * Communications link failure
					 */
					/**
					 * Caused by:
					 * 
					 * 
					 * com.mysql.jdbc.exceptions.jdbc4.CommunicationsException:
					 * 
					 * Communications link failure
					 * 
					 * The last packet sent successfully to the server was 0
					 * 
					 * milliseconds ago. The driver has not received any packets
					 * 
					 * from the server.
					 * 
					 * Caused by: java.net.ConnectException: Connection refused:
					 * 
					 * 
					 * connect
					 */
				}
				transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
			}
			if (true) {
				ConnectionHolder connectionHolder = (ConnectionHolder) TransactionSynchronizationManager
						.getResource(jdbcDataSource);
				connection = connectionHolder.getConnection();
				// 保存连接到ThreadLocal
				IJdbcTool jdbcTool = JdbcToolFactory.createApi(jdbcDataSource.getUrl());
				jdbcTool.getJdbcUtil().setConnection(connection);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
			}
			// 业务开始
			// BaseLog.trace("当前菜单名称="+this.findCurrentMenuName());
			request.setAttribute("menuDb_name", this.findCurrentMenuName());
			returnStr = this.executeTransaction();
			// 业务结束
			if (true) {
				transactionManager.commit(transactionStatus);
			}
			// 如果连接可用则放回去,不可用则弃用
			// 连接回收，用spring数据源 回收连接
			DataSourceUtils.releaseConnection(connection, jdbcDataSource);
			BaseLog.trace("用spring数据源 关闭连接 5");
		} catch (java.lang.RuntimeException e) {
			e.printStackTrace();
			BaseLog.trace("RuntimeException 事务");
			if (true) {
				if (transactionStatus.isCompleted()) {
				} else {
					transactionManager.rollback(transactionStatus);
				}
			}
			// 用简单数据源 DataSource关闭连接
			jdbcDataSource.connectionRemove(connection);
			BaseLog.trace("用简单数据源 DataSource关闭连接 6");
			throw e;
		} catch (Exception e) {
			// e.printStackTrace();
			BaseLog.trace("Exception 事务");
			if (true) {
				if (transactionStatus.isCompleted()) {
				} else {
					transactionManager.rollback(transactionStatus);
				}
			}
			// 用简单数据源 DataSource关闭连接
			jdbcDataSource.connectionRemove(connection);
			BaseLog.trace("用简单数据源 DataSource关闭连接 7");
			throw e;
		} finally {
			// 从ThreadLocal中remove连接
			JdbcThreadLocal.findJdbcToolThreadLocal().remove();
			// 从ThreadLocal中remove 去掉jdbcTemplate
			JdbcTemplateThreadLocal.findThreadLocalJdbcTemplate().remove();
		}
		return returnStr;
	}
	protected ServletContext servletContext;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
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
	/**
	 * 
	 * 上面的方法不再更新;
	 * 
	 * 下面添加新的方法;
	 */
	/**
	 * 当前路径下的菜单名称
	 * 
	 * @throws SQLException
	 */
	public String findCurrentMenuName() throws Exception {
		String returnStr = null;
		List<Object> parameterList = new ArrayList<Object>();
		Map<String, Object> map = null;
		String sql = null;
		String menuDb_id = request.getParameter("menuDb_id");
		BaseLog.trace("当前 menu_path=" + menuDb_id);
		if (StringUtil.isNotBlank(menuDb_id)) {
			parameterList.add(menuDb_id);
			sql = "SELECT name_ FROM SYS_MENU  where sys_menu_id_=?";
		} else {
			String servletPath = request.getServletPath();
			// BaseLog.trace("当前url路径="+servletPath);
			parameterList.add(servletPath);
			sql = "SELECT name_ FROM SYS_MENU  where url_=?";
		}
		map = this.findDao().findMap(sql, parameterList);
		if (map != null) {
			returnStr = (String) map.get("name_");
		} else {
			// BaseLog.trace("map is null");
		}
		return returnStr;
	}
}
