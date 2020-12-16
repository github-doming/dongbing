package all.workflow.activiti.task;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.config.SysConfig;
import c.a.util.activiti.config.ActivitiConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.mysql.PageMysqlBean;
import c.a.util.core.request.RequestThreadLocal;
import c.x.platform.root.common.action.BaseRoleAction;
public class ActivitTaskListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String ip = RequestThreadLocal.findThreadLocal().get().findIPLocal();
		// log.trace("ip="+ip);
		ProcessEngine processEngine = null;
		TaskService taskService = null;
		// 加载spring配置文件，创建 ProcessEngine
		processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(ActivitiConfig.url)
				.buildProcessEngine();
		taskService = processEngine.getTaskService();
		List<Task> taskList = taskService.createTaskQuery().list();
		// 分页
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageIndexName),
				1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageSizeName),
				taskList.size());
		PageCoreBean<Task> basePage = new PageMysqlBean<Task>(pageIndex, pageSize);
		basePage.setTotalCount(Long.valueOf(taskList.size()));
		basePage.setList(taskList);
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", taskList);
		return CommViewEnum.Default.toString();
	}
}
