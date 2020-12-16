package all.app.log;
import java.util.Map;
import all.app.common.action.AppAction;
import all.gen.app_log.t.entity.AppLogT;
import all.gen.app_log.t.service.AppLogTService;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.string.StringUtil;
/**
 * 
 * 
 * @Description:
 * @ClassName: AppLogSaveAction
 * @date 2018年5月31日 上午9:56:45
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class AppLogSaveAction extends AppAction {
	@Override
	public String run() throws Exception {
		// http://localhost:8080/a/all/app/log/save.do?json={logContent:%27abc%27}
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
			String traceId= BeanThreadLocal.find(dataMapCustom.get("traceId"), "");
			String developerName= BeanThreadLocal.find(dataMapCustom.get("developerName"), "");
			String logFunction = BeanThreadLocal.find(dataMapCustom.get("logFunction"), "");
			String logLevel = BeanThreadLocal.find(dataMapCustom.get("logLevel"), "");
			String logContent = BeanThreadLocal.find(dataMapCustom.get("logContent"), "");
			// 声明
			AppLogTService appLogTService = new AppLogTService();
			AppLogT entity = new AppLogT();
			entity.setJson(json);
			entity.setTraceId(traceId);
			entity.setDeveloperName(developerName);
			entity.setRequestFunctionCode(logFunction);
			entity.setLogLevel(logLevel);
			entity.setLogContent(logContent);
			appLogTService.save(entity);
			// jrb = LogThreadLocal.findLog();
			//jrb.setCode(ReturnCodeEnum.app200TokenQuery.toString());
			//jrb.setMsg(ReturnCodeEnum.app200TokenQuery.getMsgCn());
			jrb.setCodeSys(ReturnCodeEnum.code200.toString());
			jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
			jrb.setSuccess(true);
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
