package all.gen.sys_file_ref.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_file_ref.t.entity.SysFileRefT;
import all.gen.sys_file_ref.t.service.SysFileRefTService;
import all.gen.sys_file_ref.t.vo.SysFileRefTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysFileRefTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysFileRefTService service = new SysFileRefTService();
		SysFileRefT entity = null;
		String id = request.getParameter("id");
		entity = (SysFileRefT) RequestThreadLocal.doRequest2EntityByJson(SysFileRefTVo.class, SysFileRefT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
