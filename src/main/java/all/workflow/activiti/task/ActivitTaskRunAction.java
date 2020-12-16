package all.workflow.activiti.task;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.util.activiti.config.ActivitiConfig;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class ActivitTaskRunAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String taskId = request.getParameter("id");
		ProcessEngine processEngine = null;
		TaskService taskService = null;
		// 加载spring配置文件，创建 ProcessEngine
		processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(ActivitiConfig.url)
				.buildProcessEngine();
		taskService = processEngine.getTaskService();
		taskService.complete(taskId);
		
		System.out.println("任务完成=" + taskId);
		//System.out.println("部署流程定义完成deployment id=" + deploymentId);
		List<String> msgList = new ArrayList<String>();
		msgList.add("信息");
		msgList.add("任务完成,id=" + taskId);
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}
}
