package all.task.tms.tms_task.t.action;

import all.task.tms.tms_task.t.entity.TmsTaskT;
import all.task.tms.tms_task.t.service.TmsTaskTService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.string.StringUtil;
import c.a.util.core.enums.bean.CommViewEnum;
public class TmsTaskTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TmsTaskTService service = new TmsTaskTService();
		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		if (id == null) {
			// 选择上一级任务
			String parent_id = (String) request.getParameter("parent_id");
			if (StringUtil.isBlank(parent_id)) {
			} else {
				parent_id = parent_id.trim();
				TmsTaskT p = service.find(parent_id);
				request.setAttribute("p", p);
			}
		}
		if (id != null) {
			id = id.trim();
			// 本身
			TmsTaskT s = service.find(id);
			request.setAttribute("s", s);
			// 选择上一级任务
			String parentId = null;
			TmsTaskT p = null;
			// 上一级
			parentId = s.getParent();
			p = service.find(parentId);
			request.setAttribute("p", p);
		}
		return CommViewEnum.Default.toString();
	}
}
