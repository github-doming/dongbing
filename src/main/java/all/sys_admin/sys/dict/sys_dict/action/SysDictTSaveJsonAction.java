package all.sys_admin.sys.dict.sys_dict.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import all.sys_admin.sys.dict.sys_dict.entity.SysDictT;
import all.sys_admin.sys.dict.sys_dict.service.SysDictTService;
import all.sys_admin.sys.dict.sys_dict.vo.SysDictTVo;
public class SysDictTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysDictTService service = new SysDictTService();
		SysDictT entity = null;
		String id = request.getParameter("id");
		entity = (SysDictT) RequestThreadLocal.doRequest2EntityByJson(SysDictTVo.class, SysDictT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
