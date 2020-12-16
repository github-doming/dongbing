package all.cms.msg.admin.sys_user.t.action;
import org.apache.commons.lang3.StringUtils;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysUserTNewTopicAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		String userTopic = StringUtils.join(ids, ",");
		String _CurrentSysUserId = this.findCurrentSysUserId();
		System.out.println("userTopic=" + userTopic);
		System.out.println("CurrentSysUserId=" + _CurrentSysUserId);
		request.setAttribute("userTopic", userTopic);
		request.setAttribute("CurrentSysUserId", _CurrentSysUserId);
		
		
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
