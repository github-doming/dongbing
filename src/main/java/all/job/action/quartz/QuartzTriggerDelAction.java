package all.job.action.quartz;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.job.QuartzUtil;
import c.x.platform.root.common.action.BaseAction;
public class QuartzTriggerDelAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String name = request.getParameter("id");
		// Quartz操作

		QuartzUtil qu = QuartzUtil.findInstance();
		qu.deleteTriggerByName(name);

		List<String> msgList = new ArrayList<String>();
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();

	}
}
