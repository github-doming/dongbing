package all.job.action;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_quartz_log.t.service.SysQuartzLogTService;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.EnumThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.root.common.action.BaseRoleAction;
public class SysQuartzLogListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		SysQuartzLogTService service = new SysQuartzLogTService();
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
		PageCoreBean<Map<String, Object>> basePage = service.find(sortFieldName, sortOrderName, pageIndex, pageSize);
		List<Map<String, Object>> mapList = basePage.getList();
		for (Map<String, Object> map : mapList) {
			String state = map.get("STATE_").toString();
			String stateCn = EnumThreadLocal.findThreadLocal().get().findMsgCn(TaskStateEnum.class, String.valueOf(state))
					.toString();
			map.put("STATE_", stateCn);
			map.put("DESC_", BeanThreadLocal.findThreadLocal().get().find(map.get("DESC_"), ""));
		}
		basePage.setTotalCount(Long.valueOf(mapList.size()));
		basePage.setList(mapList);
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", mapList);
		return CommViewEnum.Default.toString();
	}
}
