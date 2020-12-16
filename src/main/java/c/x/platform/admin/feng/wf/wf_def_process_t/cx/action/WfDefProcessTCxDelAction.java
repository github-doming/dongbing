package c.x.platform.admin.feng.wf.wf_def_process_t.cx.action;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;

import c.x.platform.admin.feng.wf.wf_def_process_t.cx.service.WfDefProcessTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefProcessTCxDelAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		WfDefProcessTCxService service = new WfDefProcessTCxService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		return "index";
	}
}
