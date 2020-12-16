package all.job.action.quartz;
import java.util.ArrayList;
import java.util.List;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.job.QuartzUtil;
import c.x.platform.root.common.action.BaseAction;
public class QuartzJobPauseAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String name = (String) request.getParameter("id");
		if (name != null) {
			QuartzUtil qu = QuartzUtil.findInstance();
			qu.doJobPause(name);
		}
		List<String> msgList = new ArrayList<String>();
		msgList.add("信息");
		msgList.add("执行成功");
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}
}
