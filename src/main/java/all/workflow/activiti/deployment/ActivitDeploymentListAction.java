package all.workflow.activiti.deployment;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.config.SysConfig;
import c.a.util.activiti.config.ActivitiConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.mysql.PageMysqlBean;
import c.x.platform.root.common.action.BaseRoleAction;
public class ActivitDeploymentListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		ProcessEngine processEngine = null;
		RepositoryService repositoryService = null;
		// 加载spring配置文件，创建 ProcessEngine
		processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(ActivitiConfig.url)
				.buildProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		List<Deployment> deploymentList = repositoryService.createDeploymentQuery().list();
		// 分页
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageIndexName),
				1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageSizeName),
				deploymentList.size());
		PageCoreBean<Deployment> basePage = new PageMysqlBean<Deployment>(pageIndex, pageSize);
		basePage.setTotalCount(Long.valueOf(deploymentList.size()));
		basePage.setList(deploymentList);
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", deploymentList);
		return CommViewEnum.Default.toString();
	}
}
