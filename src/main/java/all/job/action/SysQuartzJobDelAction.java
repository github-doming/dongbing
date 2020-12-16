package all.job.action;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.job.service.SysQuartzJobService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzJobDelAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysQuartzJobService service = new SysQuartzJobService();
		service.delPhysical(id);
		
			List<String> msgList = new ArrayList<String>();
			msgList.add("信息");
			msgList.add("删除成功");
			request.setAttribute("msg", msgList);
			return CommViewEnum.Default.toString();
		

	}
}
