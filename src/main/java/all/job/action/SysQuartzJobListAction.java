package all.job.action;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.quartz.JobDetail;
import all.job.service.SysQuartzJobService;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.mysql.PageMysqlBean;
import c.a.util.job.QuartzUtil;
import c.x.platform.root.common.action.BaseRoleAction;
public class SysQuartzJobListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		SysQuartzJobService sysQuartzJobService = new SysQuartzJobService();
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		List<JobDetail> quartzJobList = quartzUtil.findJobList();
		List<Map<String, Object>> sysJobMapList = sysQuartzJobService.findAll();
		for (Map<String, Object> sysJobMap : sysJobMapList) {
			String jobName = BeanThreadLocal.findThreadLocal().get().find(sysJobMap.get("JOB_NAME_"), "");
			String jobClassName = BeanThreadLocal.findThreadLocal().get().find(sysJobMap.get("JOB_CLASS_NAME_"), "");
			sysJobMap.put("QUARTZ_CN_", "否");
			for (JobDetail quartzJob : quartzJobList) {
				String quartzJobName = quartzJob.getKey().getName();
				if (jobName.equals(quartzJobName)) {
					sysJobMap.put("JOB_NAME_", quartzJob.getKey().getName());
					sysJobMap.put("JOB_CLASS_NAME_", quartzJob.getJobClass().getName());
					sysJobMap.put("QUARTZ_CN_", "是");
					break;
				}
			}
		}
		// 分页
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get()
				.find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get()
				.find(request.getParameter(SysConfig.pageSizeName), sysJobMapList.size());
		PageCoreBean<Map<String, Object>> basePage = new PageMysqlBean<Map<String, Object>>(pageIndex, pageSize);
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", sysJobMapList);
		return CommViewEnum.Default.toString();
	}
}
