package all.app.token;
import java.util.Map;

import all.app.common.action.AppAction;
import all.app.common.service.AppService;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.string.StringUtil;
/**
 * 
 * 弃用;
 * 
 * 不需要验证码;
 * 
 * @deprecated
 * @Description:
 * @ClassName: AppLogSaveAction
 * @date 2018年5月31日 上午9:56:45
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class AppTokenRegisterAction2 extends AppAction {
	@Override
	public String run() throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
		try {

			String json = request.getParameter("json");
			if (StringUtil.isBlank(json)) {
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			Map<String, Object> dataMapCustom = JsonThreadLocal.findThreadLocal().get().json2map(json);
			String accountName = BeanThreadLocal.find(dataMapCustom.get("name"), "");
			String password = BeanThreadLocal.find(dataMapCustom.get("password"), "");
			// 声明
			AppService appService = new AppService();
			appService.login(this.findCurrentUserTenantCode(), this.request, this.response,accountName, password);
			jrb = LogThreadLocal.findLog();
			return this.returnJson(jrb);
		} catch (Exception e) {
			e.printStackTrace();
			jrb.setCodeSys(ReturnCodeEnum.code500.toString());
			jrb.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
			jrb.setSuccess(false);
			return this.returnJson(jrb);
			//throw e;
		}
	}
}
