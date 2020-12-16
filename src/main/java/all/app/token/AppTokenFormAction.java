package all.app.token;
import all.app.common.action.AppAction;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogThreadLocal;
/**
 * 
 * 通过token查找个人信息
 * 
 * @Description:
 * @ClassName: AppTokenFormAction
 * @date 2018年5月2日 下午12:07:36
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class AppTokenFormAction extends AppAction {
	public AppTokenFormAction() {
		this.async = true;
		// 需要数据库操作
		this.databaseAsync = true;
		// 需要事务操作
		transactionAsync = true;
		this.databaseAsyncId = "compare";
		//this.databaseId = "compare";
	}
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
			// AppUserT appUserT = this.findAppUserByJsonData();
			// AppUserT appUserT = this.findAppUserByJsonParameter();
			this.findAppUserByJsonParameter();
			if (appUserT == null) {
				jrb = LogThreadLocal.findLog();
				return this.returnJson(jrb);
			}
			// String amount =
			// BeanThreadLocal.find(dataMap.get("amount"),"");
			// System.out.println("amount=" + amount);
			// 返回json
			jrb.setData(appUserT);
			jrb.setCode(ReturnCodeEnum.app200TokenQuery.toString());
			jrb.setMsg(ReturnCodeEnum.app200TokenQuery.getMsgCn());
			jrb.setCodeSys(ReturnCodeEnum.code200.toString());
			jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
			jrb.setSuccess(true);
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
	@Override
	public JsonTcpBean executeAsync() throws Exception {
		this.saveAppLogRequest();
		for(int i=1;i<=10;i++){
			Thread.sleep(1000);
			System.out.println("System.currentTimeMillis="+System.currentTimeMillis());
			//System.out.println("request.getServletPath="+request.getServletPath());
			log.trace("System.currentTimeMillis="+System.currentTimeMillis());
		}
		return null;
	}
}
