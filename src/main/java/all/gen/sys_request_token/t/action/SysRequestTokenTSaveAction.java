package all.gen.sys_request_token.t.action;

import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_request_token.t.entity.SysRequestTokenT;
import all.gen.sys_request_token.t.service.SysRequestTokenTService;
import all.gen.sys_request_token.t.vo.SysRequestTokenTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysRequestTokenTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysRequestTokenTService service = new SysRequestTokenTService();
		SysRequestTokenT entity = null;
	
		
			String id = request.getParameter("sys_request_token.sysRequestTokenId");
			entity = (SysRequestTokenT) RequestThreadLocal.findThreadLocal().get()
					.doRequest2Entity(SysRequestTokenTVo.class,
							SysRequestTokenT.class, request);
			if (StringUtil.isBlank(id)) {
				service.save(entity);
			} else {
				service.update(entity);
			}
		return CommViewEnum.Default.toString();
		
	}
}
