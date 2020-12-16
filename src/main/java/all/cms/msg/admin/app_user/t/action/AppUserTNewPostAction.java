package all.cms.msg.admin.app_user.t.action;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;

import all.app.common.service.AppTokenService;
import all.gen.app_token.t.entity.AppTokenT;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppUserTNewPostAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		String userIds = StringUtils.join(ids, ",");
		String _CurrentAppUserId = this.findCurrentAppUserId();
		if (StringUtil.isBlank(_CurrentAppUserId)) {
			_CurrentAppUserId = "1";
		}
		AppTokenService _AppTokenService=new AppTokenService();
		AppTokenT _AppTokenT=_AppTokenService.findAppTokenByAppUserId(_CurrentAppUserId);
		
		System.out.println("userIds=" + userIds);
		System.out.println("CurrentAppUserId=" + _CurrentAppUserId);
		request.setAttribute("userIds", userIds);
		if(_AppTokenT!=null){
			request.setAttribute("token",_AppTokenT.getValue());
		}
	

		// 跳转
		return CommViewEnum.Default.toString();
	}
}
