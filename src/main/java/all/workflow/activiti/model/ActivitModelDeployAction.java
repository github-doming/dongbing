package all.workflow.activiti.model;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import c.a.util.activiti.config.ActivitiConfig;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.path.PathUtil;
import c.x.platform.root.common.action.BaseAction;
public class ActivitModelDeployAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String modelId = request.getParameter("id");
		ProcessEngine processEngine = null;
		RepositoryService repositoryService = null;
		// 加载spring配置文件，创建 ProcessEngine
		processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(ActivitiConfig.url)
				.buildProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		Model modelData = repositoryService.getModel(modelId);
		ObjectNode modelNode = (ObjectNode) new ObjectMapper()
				.readTree(repositoryService.getModelEditorSource(modelData.getId()));
		byte[] bpmnBytes = null;
		BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		bpmnBytes = new BpmnXMLConverter().convertToXML(model);
		// 资源名称ResourceName
		String resourceName = modelData.getName() + ".bpmn20.xml";
		Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
				.addString(resourceName, new String(bpmnBytes, "utf-8")).deploy();
		String deploymentId = deployment.getId();
		//System.out.println("部署流程定义完成deployment id=" + deploymentId);
		List<String> msgList = new ArrayList<String>();
		msgList.add("信息");
		msgList.add("部署流程定义完成deployment id=" + deploymentId);
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}
}
