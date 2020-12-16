package all.job.action.quartz;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.mysql.PageMysqlBean;
import c.a.util.job.QuartzTriggerBean;
import c.a.util.job.QuartzUtil;
import c.a.util.job.TriggerStateEnum;
import c.x.platform.root.common.action.BaseRoleAction;
public class QuartzTriggerListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		List<QuartzTriggerBean> triggerList = quartzUtil.findTriggerList();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (QuartzTriggerBean quartzTriggerBean : triggerList) {
			Map<String, Object> map = new HashMap<String, Object>();
			String jobName = quartzTriggerBean.getJobName();
			String triggeName = quartzTriggerBean.getTriggerName();
			String desc = quartzTriggerBean.getTriggerDescription();
			map.put("JOB_NAME_", jobName);
			map.put("JOB_CLASS_NAME_", quartzTriggerBean.getJobClassName());
			map.put("DESC_", desc);
			map.put("TRIGGER_NAME_", triggeName);
			map.put("CRON_EXPRESSION_", quartzTriggerBean.getCronExpression());
			TriggerStateEnum triggerStateEnum=quartzTriggerBean.getTriggerState();
			String triggeStateCn = triggerStateEnum.getMsgCn();
			map.put("TRIGGER_STATE_CN_",triggeStateCn);
			map.put("TRIGGER_STATE_",triggerStateEnum);
			mapList.add(map);
		}
		// 分页
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get()
				.find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get()
				.find(request.getParameter(SysConfig.pageSizeName), mapList.size());
		PageCoreBean<Map<String, Object>> basePage = new PageMysqlBean<Map<String, Object>>(pageIndex, pageSize);
		basePage.setTotalCount(Long.valueOf( mapList.size()));
		basePage.setList(mapList);
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", mapList);
		return CommViewEnum.Default.toString();
	}
}
