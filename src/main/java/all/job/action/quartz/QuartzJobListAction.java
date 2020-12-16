package all.job.action.quartz;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.mysql.PageMysqlBean;
import c.a.util.job.QuartzUtil;
import c.x.platform.root.common.action.BaseRoleAction;
public class QuartzJobListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		List<JobDetail> jobDetailList = quartzUtil.findJobList();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (JobDetail jobDetail : jobDetailList) {
			Map<String, Object> map = new HashMap<String, Object>();
			JobKey jobKey = jobDetail.getKey();
			String jobName = jobKey.getName();
			String desc = jobDetail.getDescription();
			map.put("JOB_NAME_", jobName);
			map.put("JOB_CLASS_NAME_", jobDetail.getJobClass().getName());
			map.put("DESC_", desc);
			//
			// TriggerStateEnum triggerStateEnum=jobDetail.getTriggerState();
			// String triggeStateCn = triggerStateEnum.getMsgCn();
			// map.put("TRIGGER_STATE_CN_",triggeStateCn);
			// map.put("TRIGGER_STATE_",triggerStateEnum);
			mapList.add(map);
		}
		// 分页
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageIndexName),
				1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageSizeName),
				mapList.size());
		PageCoreBean<Map<String, Object>> basePage = new PageMysqlBean<Map<String, Object>>(pageIndex, pageSize);
		basePage.setList(mapList);
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", mapList);
		return CommViewEnum.Default.toString();
	}
}
