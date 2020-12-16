package all.job.action;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_quartz_config.t.service.SysQuartzConfigTService;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.EnumThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.job.QuartzEnum;
import c.a.util.job.QuartzUtil;
import c.x.platform.root.common.action.BaseRoleAction;
public class SysQuartzConfigListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		// 排序
		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);
		// 分页
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get()
				.find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get()
				.find(request.getParameter(SysConfig.pageSizeName), 10);
		SysQuartzConfigTService service = new SysQuartzConfigTService();
		PageCoreBean<Map<String, Object>> basePage = service.find(sortFieldName, sortOrderName, pageIndex, pageSize);
		// 数据
		boolean inStandbyMode = QuartzUtil.findInstance().doSchedulerIsInStandbyMode();
		boolean started = QuartzUtil.findInstance().doSchedulerIsStart();
		List<Map<String, Object>> mapList = basePage.getList();
		for (Map<String, Object> map : mapList) {
			map.put("INSTANDBY_MODE_", inStandbyMode);
			map.put("STARTED_", started);
			String inStandbyModeCn = EnumThreadLocal.findThreadLocal().get()
					.findMsgCn(QuartzEnum.class, String.valueOf(inStandbyMode).toUpperCase()).toString();
			String startedCn = EnumThreadLocal.findThreadLocal().get()
					.findMsgCn(QuartzEnum.class, String.valueOf(started).toUpperCase()).toString();
			map.put("INSTANDBY_MODE_CN_", inStandbyModeCn);
			map.put("STARTED_CN_", startedCn);
		}
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", mapList);
		return CommViewEnum.Default.toString();
	}
}
