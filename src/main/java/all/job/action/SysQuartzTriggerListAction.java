package all.job.action;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.job.service.SysQuartzTriggerService;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.mysql.PageMysqlBean;
import c.a.util.job.QuartzTriggerBean;
import c.a.util.job.QuartzUtil;
import c.a.util.job.TriggerStateEnum;
import c.x.platform.root.common.action.BaseRoleAction;
public class SysQuartzTriggerListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		SysQuartzTriggerService sysQuartzTriggerService = new SysQuartzTriggerService();
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		List<QuartzTriggerBean> quartzTriggerList = quartzUtil.findTriggerList();
		List<Map<String, Object>> sysTriggerList = sysQuartzTriggerService.findAll();
		for (Map<String, Object> sysTriggerMap : sysTriggerList) {
			String sys_triggerName = BeanThreadLocal.findThreadLocal().get()
					.find(sysTriggerMap.get("TRIGGER_NAME_"), "");
			String sys_sysTriggerName = BeanThreadLocal.findThreadLocal().get()
					.find(sysTriggerMap.get("SYS_TRIGGER_NAME_"), "");
			String sys_sysQuartzTriggerID=BeanThreadLocal.findThreadLocal().get()
					.find(sysTriggerMap.get("SYS_QUARTZ_TRIGGER_ID_"), "");
			sysTriggerMap.put("QUARTZ_CN_", "否");
			sysTriggerMap.put("TRIGGER_STATE_CN_", TriggerStateEnum.NONE.getMsgCn());
			for (QuartzTriggerBean quartzTriggerBean : quartzTriggerList) {
				String jobName = quartzTriggerBean.getJobName();
				String triggeName = quartzTriggerBean.getTriggerName();
				TriggerStateEnum triggerStateEnum=quartzTriggerBean.getTriggerState();
				if (sys_triggerName.equals(triggeName)) {
					sysTriggerMap.put("QUARTZ_CN_", "是");
					
					String triggeStateCn = triggerStateEnum.getMsgCn();
					sysTriggerMap.put("TRIGGER_STATE_CN_",triggeStateCn);
					sysTriggerMap.put("TRIGGER_STATE_",triggerStateEnum);
					break;
				}
			}
		}
		// 分页
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get()
				.find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get()
				.find(request.getParameter(SysConfig.pageSizeName), sysTriggerList.size());
		PageCoreBean<Map<String, Object>> basePage = new PageMysqlBean<Map<String, Object>>(pageIndex, pageSize);
		basePage.setTotalCount(Long.valueOf(sysTriggerList.size()));
		basePage.setList(sysTriggerList);
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", sysTriggerList);
		return CommViewEnum.Default.toString();
	}
}
