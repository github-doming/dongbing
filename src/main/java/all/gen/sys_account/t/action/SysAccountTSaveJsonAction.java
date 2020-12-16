package all.gen.sys_account.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_account.t.entity.SysAccountT;
import all.gen.sys_account.t.service.SysAccountTService;
import all.gen.sys_account.t.vo.SysAccountTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysAccountTService service = new SysAccountTService();
		SysAccountT entity = null;
		String id = request.getParameter("id");
		entity = (SysAccountT) RequestThreadLocal.doRequest2EntityByJson(SysAccountTVo.class, SysAccountT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
