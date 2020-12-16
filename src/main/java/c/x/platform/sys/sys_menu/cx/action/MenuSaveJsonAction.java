package c.x.platform.sys.sys_menu.cx.action;
import java.util.ArrayList;
import java.util.List;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import all.gen.sys_menu.t.entity.SysMenuT;
import c.x.platform.sys.sys_menu.cx.service.SysMenuCxService;
public class MenuSaveJsonAction extends BaseAction {
	public MenuSaveJsonAction() {
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		String jsonStr = this.request.getParameter("json");
		log.trace("json data=" + jsonStr);
		
		SysMenuCxService service = new SysMenuCxService();
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
