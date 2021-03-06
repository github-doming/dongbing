package all.workflow.activiti.model;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.util.activiti.config.ActivitiConfig;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class ActivitModelDelAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		ProcessEngine processEngine = null;
		RepositoryService repositoryService = null;
		// 加载spring配置文件，创建 ProcessEngine
		processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(ActivitiConfig.url)
				.buildProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		repositoryService.deleteModel(id);
		List<String> msgList = new ArrayList<String>();
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}
}
