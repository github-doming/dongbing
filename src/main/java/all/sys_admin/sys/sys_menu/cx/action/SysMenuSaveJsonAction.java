package all.sys_admin.sys.sys_menu.cx.action;
import java.util.ArrayList;
import java.util.List;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import all.gen.sys_menu.t.entity.SysMenuT;
import all.sys_admin.sys.sys_menu.cx.service.SysMenuService;
public class SysMenuSaveJsonAction extends BaseAction {
	public SysMenuSaveJsonAction() {
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		String jsonStr = this.request.getParameter("json");
		log.trace("json data=" + jsonStr);
		
		SysMenuService service = new SysMenuService();
		List<Object> listObject = new ArrayList<Object>();
		List<Object> list = (List<Object>) JsonThreadLocal.findThreadLocal().get().findObjectList(jsonStr,
				SysMenuT.class, listObject, "children");
		for (Object obj : list) {
			SysMenuT entity = (SysMenuT) obj;
			if (StringUtil.isBlank(entity.getSysMenuId())) {
				entity.setParent(entity.getParent());
				service.save(entity);
			} else {
				service.update(entity);
			}
		}
		return this.returnJson(true, "保存成功");
	}
}
