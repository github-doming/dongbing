package c.a.util.activiti.util;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
public class ActUtil {
	public Deployment findDeployment(String resource) {
		Deployment deployment = null;
		RepositoryService rs = ActContext.findInstance().getRepositoryService();
		// log.trace("rs=" + rs);
		deployment = rs.createDeployment().addClasspathResource(resource).deploy();
		return deployment;
	}
	public List<ProcessDefinition> findProcessDefinitionList(String deploymentId) {
		RepositoryService repositoryService = ActContext.findInstance().getRepositoryService();
		List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
				.deploymentId(deploymentId).list();
		return processDefinitionList;
	}
}
