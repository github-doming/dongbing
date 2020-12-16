package all.gen.sys_pk.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_pk.t.entity.SysPkT;
import all.gen.sys_pk.t.service.SysPkTService;
import all.gen.sys_pk.t.vo.SysPkTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysPkTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysPkTService service = new SysPkTService();
		SysPkT entity = null;
		String id = request.getParameter("id");
		entity = (SysPkT) RequestThreadLocal.doRequest2EntityByJson(SysPkTVo.class, SysPkT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
