package all.app.user.controller;
import java.util.Map;

import all.app.common.action.AppController;
import all.app.common.service.AppUserRedisService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
public class AppUserSaveController extends AppController {
	@Override
	public JsonTcpBean execute(Map<String, Object> dataMap, AppUserT appUserT) throws Exception {
		JsonTcpBean jrb=new JsonTcpBean();
		AppUserRedisService service = new AppUserRedisService();
		String nickName = BeanThreadLocal.find(dataMap.get("nickName"), "");
		if (StringUtil.isBlank(nickName)) {
//			jrb.setCodeSys(ReturnCodeEnum.code400.toString());
//			jrb.setMessageSys(ReturnCodeEnum.code400.getMsgCn());
//			jrb.setSuccess(false);
//			return jrb;
		}
		String headPortrait = BeanThreadLocal.find(dataMap.get("headPortrait"), "");
		if (StringUtil.isBlank(headPortrait)) {
//			jrb.setCodeSys(ReturnCodeEnum.code400.toString());
//			jrb.setMessageSys(ReturnCodeEnum.code400.getMsgCn());
//			jrb.setSuccess(false);
//			return jrb;
		}
		appUserT.setHeadPortrait(headPortrait);
		//System.out.println("nickName=" + nickName);
		appUserT.setNickname(nickName);
		service.update(appUserT);
		// AppUser保存在redis
		//appRedisService.saveAppUserByRedis(appUserT);
		// 返回json
		jrb.setCodeSys(ReturnCodeEnum.code200.toString());
		jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
		jrb.setSuccess(true);
		return jrb;
	}
}
