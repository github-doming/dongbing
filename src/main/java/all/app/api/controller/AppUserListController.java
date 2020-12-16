package all.app.api.controller;
import java.util.List;
import java.util.Map;

import all.app.common.action.AppController;
import all.app.common.service.AppUserSysService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonTcpBean;
public class AppUserListController extends AppController {
	@Override
	public JsonTcpBean execute(Map<String, Object> dataMap, AppUserT appUserT) throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
		AppUserSysService service = new AppUserSysService();
	
		List<Map<String, Object>> listMap = service.findAll();

		// 返回json
		jrb.setData(listMap);
		jrb.setCodeSys(ReturnCodeEnum.code200.toString());
		jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
		jrb.setSuccess(true);
		return jrb;
	}
}
