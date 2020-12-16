package c.a.util.activiti.util;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import c.a.util.core.data_source.DataSourceListUtil;
import c.a.util.core.data_source.DataSourceUtil;
/**
 * 
 * 
 */
public class ActContext {
	private ProcessEngine processEngine = null;
	private RepositoryService repositoryService = null;
	private RuntimeService runtimeService = null;
	private TaskService taskService = null;
	private IdentityService identityService = null;
	private HistoryService historyService = null;
	private static ActContext instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造子
	 */
	private ActContext() {
	}
	public static ActContext findInstance() {
		synchronized (key) {
			if (instance == null) {
				instance = new ActContext();
				instance.init();
			}
		}
		return instance;
	}
	private void init() {
		try {
			StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration = (StandaloneProcessEngineConfiguration) ProcessEngineConfiguration
					.createStandaloneProcessEngineConfiguration();
			
			
			// 设置activiti配置信息（比如是否自动更新，是否使用历史，字体...）：
			standaloneProcessEngineConfiguration.setDatabaseSchemaUpdate("true");
			standaloneProcessEngineConfiguration.setDbHistoryUsed(true);
			standaloneProcessEngineConfiguration.setHistory("full");
			standaloneProcessEngineConfiguration.setActivityFontName("宋体");
			standaloneProcessEngineConfiguration.setJobExecutorActivate(false);
			// 设置activiti配置信息（比如是否自动更新，是否使用历史，字体...）：
			
			
			DataSourceUtil 	jdbcDataSource = DataSourceListUtil.findInstance().findLocal();
			//设置数据库的DataSource
			standaloneProcessEngineConfiguration.setDataSource(jdbcDataSource.getDataSource());
			
			//设置事务管理工厂（CustomJdbcTransactionFactory这个方法时我自己写的，下面会详细介绍）：

	ActJdbcTransactionFactory jdbcTransactionFactory=
			                    new ActJdbcTransactionFactory();
			standaloneProcessEngineConfiguration.setTransactionFactory(jdbcTransactionFactory);
		
			
			//创建引擎：
			processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();
			repositoryService = processEngine.getRepositoryService();
			runtimeService = processEngine.getRuntimeService();
			taskService = processEngine.getTaskService();
			identityService = processEngine.getIdentityService();
			historyService = processEngine.getHistoryService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
	public RepositoryService getRepositoryService() {
		return repositoryService;
	}
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	public RuntimeService getRuntimeService() {
		return runtimeService;
	}
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}
	public TaskService getTaskService() {
		return taskService;
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	public IdentityService getIdentityService() {
		return identityService;
	}
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
	public HistoryService getHistoryService() {
		return historyService;
	}
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
}
