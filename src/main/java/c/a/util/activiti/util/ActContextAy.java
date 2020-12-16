package c.a.util.activiti.util;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
/**
 * 
 * 
 */
public class ActContextAy {
	private ProcessEngine processEngine = null;
	private RepositoryService repositoryService = null;
	private RuntimeService runtimeService = null;
	private TaskService taskService = null;
	private IdentityService identityService = null;
	private HistoryService historyService = null;
	private static ActContextAy instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造子
	 */
	private ActContextAy() {
	}
	public static ActContextAy findInstance() {
		synchronized (key) {
			if (instance == null) {
				instance = new ActContextAy();
				instance.init();
			}
		}
		return instance;
	}
	private void init() {
		// ProcessEngine processEngine = null;
		// RepositoryService repositoryService = null;
		// RuntimeService runtimeService = null;
		// TaskService taskService = null;
		// 加载spring配置文件，创建 ProcessEngine
		// StandaloneProcessEngineConfiguration
		// standaloneProcessEngineConfiguration = new
		// StandaloneProcessEngineConfiguration();
		StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration = (StandaloneProcessEngineConfiguration) ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();
		String jdbcDriver = "com.mysql.jdbc.Driver";
		// String jdbcUrl =
		// "jdbc:mysql://localhost:3306/activiti5.22?useSSL=false&useUnicode=true&amp;characterEncoding=utf-8&&zeroDateTimeBehavior=convertToNull";
		// String jdbcUrl = "jdbc:mysql://localhost:3306/activiti5.22";
		String jdbcUrl = "jdbc:mysql://localhost:3306/activiti5.22?useSSL=false&&useUnicode=true&&characterEncoding=utf-8&&zeroDateTimeBehavior=convertToNull";
		String jdbcUsername = "root";
		String jdbcPassword = "1";
		standaloneProcessEngineConfiguration.setJdbcDriver(jdbcDriver);
		standaloneProcessEngineConfiguration.setJdbcUrl(jdbcUrl);
		standaloneProcessEngineConfiguration.setJdbcUsername(jdbcUsername);
		standaloneProcessEngineConfiguration.setJdbcPassword(jdbcPassword);
		// 设置activiti配置信息（比如是否自动更新，是否使用历史，字体...）：
		standaloneProcessEngineConfiguration.setDatabaseSchemaUpdate("true");
		standaloneProcessEngineConfiguration.setDbHistoryUsed(true);
		standaloneProcessEngineConfiguration.setHistory("full");
		standaloneProcessEngineConfiguration.setActivityFontName("宋体");
		standaloneProcessEngineConfiguration.setJobExecutorActivate(false);
		// 设置activiti配置信息（比如是否自动更新，是否使用历史，字体...）：

		processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		runtimeService = processEngine.getRuntimeService();
		taskService = processEngine.getTaskService();
		identityService = processEngine.getIdentityService();
		historyService = processEngine.getHistoryService();
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
