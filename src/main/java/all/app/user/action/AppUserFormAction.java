package all.app.user.action;
import all.app.common.action.AppAction;
import all.app.common.service.AppTokenRedisService;
import all.app.common.service.AppUserRedisService;
import all.app.common.service.AppService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
/**
 * 
 * 通过userId查找个人信息
 * 
 * @Description:
 * @ClassName: AppTokenFormAction
 * @date 2018年5月2日 下午12:07:36
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class AppUserFormAction extends AppAction {
	@Override
	public String run() throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
		try {
			if (this.isFindJson()) {
			} else {
				jrb.setCode(ReturnCodeEnum.app400JSON.toString());
				jrb.setMsg(ReturnCodeEnum.app400JSON.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			this.findAppUserByJsonParameter();
			if (appUserT == null) {
				jrb.setCode(ReturnCodeEnum.app401Token.toString());
				jrb.setMsg(ReturnCodeEnum.app401Token.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code401.toString());
				jrb.setMsgSys(ReturnCodeEnum.code401.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			// appUserId
			String appUserId = BeanThreadLocal.find(dataMap.get("appUserId"), "");
			if (StringUtil.isBlank(appUserId)) {
				jrb.setMsg("appUserId不能为空");
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}


			AppUserT appUserT = appUserRedisService.findAppUserByAppUserId(appUserId);
			// 返回json
			jrb.setData(appUserT);
			jrb.setCode(ReturnCodeEnum.app200UserQuery.toString());
			jrb.setMsg(ReturnCodeEnum.app200UserQuery.getMsgCn());
			jrb.setCodeSys(ReturnCodeEnum.code200.toString());
			jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
			jrb.setSuccess(true);
			return this.returnJson(jrb);
		} catch (Exception e) {
			e.printStackTrace();
			jrb.setCodeSys(ReturnCodeEnum.code500.toString());
			jrb.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
			jrb.setSuccess(true);
			return this.returnJson(jrb);
		}
	}
}
