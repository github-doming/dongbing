package all.task.tms.tms_function.t.action;

import all.task.tms.tms_function.t.entity.TmsFunctionT;
import all.task.tms.tms_function.t.service.TmsFunctionTService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.string.StringUtil;
import c.a.util.core.enums.bean.CommViewEnum;
public class TmsFunctionTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TmsFunctionTService service = new TmsFunctionTService();
		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		if (id == null) {
			// 选择上一级功能接口
			String parent_id = (String) request.getParameter("parent_id");
			if (StringUtil.isBlank(parent_id)) {
			} else {
				parent_id = parent_id.trim();
				TmsFunctionT p = service.find(parent_id);
				request.setAttribute("p", p);
			}
		}
		if (id != null) {
			id = id.trim();
			// 本身
			TmsFunctionT s = service.find(id);
			request.setAttribute("s", s);
			// 选择上一级功能接口
			String parentId = null;
			TmsFunctionT p = null;
			// 上一级
			parentId = s.getParent();
			p = service.find(parentId);
			request.setAttribute("p", p);
		}
		return CommViewEnum.Default.toString();
	}
}
