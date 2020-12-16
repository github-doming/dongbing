package all.workflow.activiti.process_definition;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.util.activiti.config.ActivitiConfig;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class ActivitProcessDefinitionStartAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String processDefinitionId = request.getParameter("id");
		ProcessEngine processEngine = null;
		RuntimeService runtimeService =null;
		// 加载spring配置文件，创建 ProcessEngine
		processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(ActivitiConfig.url)
				.buildProcessEngine();
		runtimeService = processEngine.getRuntimeService();
		  ProcessInstance pi= runtimeService.startProcessInstanceById(processDefinitionId);
		String processInstanceId =pi.getId();
		System.out.println("启动流程完成=" + processInstanceId);
		//System.out.println("部署流程定义完成deployment id=" + deploymentId);
		List<String> msgList = new ArrayList<String>();
		msgList.add("信息");
		msgList.add("启动流程完成,id=" + processInstanceId);
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}
}
