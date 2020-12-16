package c.x.platform.admin.feng.wf.wf_ins_field_t.cx.action;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import c.x.platform.admin.feng.wf.wf_ins_field_t.cx.entity.WfInsFieldTCx;
import c.x.platform.admin.feng.wf.wf_ins_field_t.cx.service.WfInsFieldTCxService;
import c.x.platform.root.common.action.BaseRoleAction;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.bean.BeanThreadLocal;
public class WfInsFieldTCxListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {

		// 排序

		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request
				.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);

		// 分页
		Integer pageNo = BeanThreadLocal.findThreadLocal().get().find(
				request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(
				request.getParameter(SysConfig.pageSizeName), 10);

		WfInsFieldTCxService service = new WfInsFieldTCxService();
		PageCoreBean<Map<String, Object>> basePage = service.find(sortOrderName,
				sortFieldName, pageNo, pageSize);

		List<Map<String, Object>> listMap = basePage.getList();

		request.setAttribute("cPage", basePage);
		request.setAttribute("list", listMap);

		return "index";
	}
}
