package all.gen.sys_bytes.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_bytes.t.entity.SysBytesT;
import all.gen.sys_bytes.t.service.SysBytesTService;
import all.gen.sys_bytes.t.vo.SysBytesTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysBytesTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysBytesTService service = new SysBytesTService();
		SysBytesT entity = null;
		String id = request.getParameter("id");
		entity = (SysBytesT) RequestThreadLocal.doRequest2EntityByJson(SysBytesTVo.class, SysBytesT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
