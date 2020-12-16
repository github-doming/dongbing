package all.app.token;
import all.app.common.action.AppAction;
import all.app.common.service.AppTokenRedisService;
import all.gen.app_token.t.entity.AppTokenT;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
/**
 * 
 * 测试事务回滚
 * 
 * http://localhost:8080/a//all/app/token/test/save.do?json=a
 * 
 * @Description:
 * @date 2018年5月2日 下午12:07:36
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class AppTokenTestSaveAction extends AppAction {
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
			AppTokenRedisService appTokenRedisService = new AppTokenRedisService();
			AppTokenT appTokenT = new AppTokenT();
			appTokenT.setValue("000000abc");
			appTokenRedisService.save(appTokenT);
			int i = 1 / 0;
			jrb.setCode(ReturnCodeEnum.app200TokenQuery.toString());
			jrb.setMsg(ReturnCodeEnum.app200TokenQuery.getMsgCn());
			jrb.setCodeSys(ReturnCodeEnum.code200.toString());
			jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
			jrb.setSuccess(true);
			//return "index";
			return this.returnJson(jrb);
		} catch (Exception e) {
			e.printStackTrace();
			jrb.setCodeSys(ReturnCodeEnum.code500.toString());
			jrb.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
			jrb.setSuccess(false);
			this.returnJson(jrb);
			throw e;
		}
	}
}
