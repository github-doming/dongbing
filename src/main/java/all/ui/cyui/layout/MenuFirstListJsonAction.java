package all.ui.cyui.layout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import all.app_admin.root.layout.main.action.AppMainAction;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.common.action.BaseAction;
import c.x.platform.root.layout.main.action.SysMainAction;
public class MenuFirstListJsonAction extends BaseAction {
	public MenuFirstListJsonAction() {
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
		String type = this.findCurrentUserType();
		if ("sys".equals(type)) {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			SysMainAction mainAction = new SysMainAction();
			mainAction.setRequest(request);
			mainAction.setResponse(response);
			List<Map<String, Object>> mapList = mainAction.findSysMenuFirst_RoleShowList();
			for (Map<String, Object> map : mapList) {
				map.put("id", map.get("SYS_MENU_ID_"));
				map.put("text", map.get("SYS_MENU_NAME_"));
			}
			jsonMap.put("menu_first_list", mapList);
			jsonMap.put("menu_id_00010001", mainAction.findSysMenuChildId(mapList));
			jrb.setData(jsonMap);
		}
		if ("app".equals(type)) {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			AppMainAction mainAction = new AppMainAction();
			mainAction.setRequest(request);
			mainAction.setResponse(response);
			List<Map<String, Object>> mapList = mainAction.findAppMenuFirst_RoleShowList_ByApp();
			for (Map<String, Object> map : mapList) {
				map.put("id", map.get("APP_MENU_ID_"));
				map.put("text", map.get("APP_MENU_NAME_"));
			}
			jsonMap.put("menu_first_list", mapList);
			jsonMap.put("menu_id_00010001", mainAction.findAppMenuChild_ID_ByApp(mapList));
			jrb.setData(jsonMap);
		}
		jrb.setCode(ReturnCodeEnum.code200.toString());
		jrb.setMsg(ReturnCodeEnum.code200.getMsgCn());
		jrb.setCodeSys(ReturnCodeEnum.code200.toString());
		jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
		jrb.setSuccess(true);
		return returnJson(jrb);
	}
}
