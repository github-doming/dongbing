package all.workflow.activiti.process_instance;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
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
public class ActivitProcessInstanceListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		
		String ip=RequestThreadLocal.findThreadLocal().get().findIPLocal();
		//log.trace("ip="+ip);
		ProcessEngine processEngine = null;
		RuntimeService runtimeService =null;
		// 加载spring配置文件，创建 ProcessEngine
		processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(ActivitiConfig.url)
				.buildProcessEngine();
		runtimeService = processEngine.getRuntimeService();
	
		List<ProcessInstance> processInstanceList = 	runtimeService .createProcessInstanceQuery().list();
		// 分页
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageIndexName),
				1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageSizeName),
				processInstanceList.size());
		PageCoreBean<ProcessInstance> basePage = new PageMysqlBean<ProcessInstance>(pageIndex, pageSize);
		basePage.setTotalCount(Long.valueOf(processInstanceList.size()));
		basePage.setList(processInstanceList);
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", processInstanceList);
		return CommViewEnum.Default.toString();
	}
}
