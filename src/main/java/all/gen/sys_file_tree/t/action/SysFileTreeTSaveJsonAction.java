package all.gen.sys_file_tree.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_file_tree.t.entity.SysFileTreeT;
import all.gen.sys_file_tree.t.service.SysFileTreeTService;
import all.gen.sys_file_tree.t.vo.SysFileTreeTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysFileTreeTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysFileTreeTService service = new SysFileTreeTService();
		SysFileTreeT entity = null;
		String id = request.getParameter("id");
		entity = (SysFileTreeT) RequestThreadLocal.doRequest2EntityByJson(SysFileTreeTVo.class, SysFileTreeT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
