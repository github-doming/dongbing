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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import c.a.util.core.data_source.ay.JdbcDataSourceAy;
import c.a.util.core.data_source.ay.JdbcDataSourceListAy;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
/**
 * spring事务
 * 
 * 
 * 
 */
public abstract class TransactionSpringByAction extends WebContentGenerator implements Controller {
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
	public abstract JsonTcpBean executeTransaction() throws Exception;
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
			JsonTcpBean returnStr = null;
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
			modelAndView = new ModelAndView(returnStr.getMvcResult());
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
	public JsonTcpBean databaseNot() throws Exception {
		JsonTcpBean returnStr = this.executeTransaction();
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
	public JsonTcpBean transactionNot() throws Exception {
		JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance().findLocal();
		Connection connection = jdbcDataSource.getConnection();
		ServletContext servletContext = this.getServletContext();
		// 返回
		JsonTcpBean returnStr = null;
		// 注入数据源
		JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcDataSource);
		// 保存jdbcTemplate
		JdbcTemplateThreadLocal.findThreadLocalJdbcTemplate().set(jdbcTemplate);
		connection = jdbcDataSource.getConnection();
		// 保存连接到ThreadLocal
		try {
			// 业务开始
			returnStr = this.executeTransaction();
			// 业务结束
			// 如果连接可用则放回去,不可用则弃用
			// 连接回收，用spring数据源 回收连接
			DataSourceUtils.releaseConnection(connection, jdbcDataSource);
		} catch (java.lang.RuntimeException e) {
			// e.printStackTrace();
			// 用简单数据源 DataSource关闭连接
			jdbcDataSource.connectionRemove(connection);
			throw e;
		} catch (Exception e) {
			// e.printStackTrace();
			// 用简单数据源 DataSource关闭连接
			jdbcDataSource.connectionRemove(connection);
			throw e;
		} finally {
			// 从ThreadLocal中remove连接
			JdbcThreadLocal.findJdbcToolThreadLocal().remove();
			// 从ThreadLocal中remove 去掉jdbcTemplate
			JdbcTemplateThreadLocal.findThreadLocalJdbcTemplate().remove();
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
	public JsonTcpBean transaction() throws Exception {
		JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance().findLocal();
		Connection connection = jdbcDataSource.getConnection();
		ServletContext servletContext = this.getServletContext();
		// 返回
		JsonTcpBean returnStr = null;
		// 注入数据源
		JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcDataSource);
		// 保存jdbcTemplate
		JdbcTemplateThreadLocal.findThreadLocalJdbcTemplate().set(jdbcTemplate);
		DataSourceTransactionManager transactionManager = null;
		// 事务状态
		TransactionStatus transactionStatus = null;
		try {
			// 注入数据源到事务管理器
			transactionManager = new DataSourceTransactionManager(jdbcDataSource);
			// 事务定义类
			DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
			transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
			ConnectionHolder connectionHolder = (ConnectionHolder) TransactionSynchronizationManager
					.getResource(jdbcDataSource);
			connection = connectionHolder.getConnection();
			// 保存连接到ThreadLocal
			IJdbcTool jdbcTool = JdbcToolFactory.createApi(jdbcDataSource.getUrl());
			jdbcTool.getJdbcUtil().setConnection(connection);
			JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
			// 业务开始
			// BaseLog.trace("当前菜单名称="+this.findCurrentMenuName());
			request.setAttribute("menuDb_name", this.findCurrentMenuName());
			returnStr = this.executeTransaction();
			// 业务结束
			transactionManager.commit(transactionStatus);
			// 如果连接可用则放回去,不可用则弃用
			// 连接回收，用spring数据源 回收连接
			DataSourceUtils.releaseConnection(connection, jdbcDataSource);
		} catch (java.lang.RuntimeException e) {
			e.printStackTrace();
				if (transactionStatus.isCompleted()) {
				} else {
					transactionManager.rollback(transactionStatus);
				}
			// 用简单数据源 DataSource关闭连接
			jdbcDataSource.connectionRemove(connection);
			throw e;
		} catch (Exception e) {
			// e.printStackTrace();
			if (transactionStatus.isCompleted()) {
			} else {
				transactionManager.rollback(transactionStatus);
			}
			// 用简单数据源 DataSource关闭连接
			jdbcDataSource.connectionRemove(connection);
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
		}
		return returnStr;
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
