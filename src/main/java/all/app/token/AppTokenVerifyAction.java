package all.app.token;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpSession;
import all.app.ay.app_verify_account.service.AppVerifyAccountService;
import all.app.common.action.AppAction;
import all.app.common.config.AppConfigSession;
import all.gen.app_verify_account.t.entity.AppVerifyAccountT;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.string.StringUtil;
import c.a.util.core.verify_code.VerifyCodeUtil;
import c.a.util.redis.RedisUtil;
import c.x.platform.root.boot.BootSessionContext;
import redis.clients.jedis.Jedis;
/**
 * 
 * 验证码
 * 
 * @Description: 
 * @ClassName: AppTokenVerifyAction 
 * @date 2018年8月25日 上午10:59:16 
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class AppTokenVerifyAction extends AppAction {
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
			Map<String, Object> dataMapCustom = JsonThreadLocal.findThreadLocal().get().json2map(json);
			String sessionId = BeanThreadLocal.find(dataMapCustom.get("session"), "");
			if (StringUtil.isBlank(sessionId)) {
				jrb.setCode(ReturnCodeEnum.app400Session.toString());
				jrb.setMsg(ReturnCodeEnum.app400Session.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			String code = VerifyCodeUtil.findVerifyCode();
			// 因为集群问题，验证码不能用redis保存，只能用数据库;想用redis最好把验证码做成微服务;
			if (false) {
				BootSessionContext bootSessionContext = BootSessionContext.findInstance();
				HttpSession httpSession = bootSessionContext.findSession(sessionId);
				if (httpSession == null) {
					jrb.setCode(ReturnCodeEnum.app404Session.toString());
					jrb.setMsg(ReturnCodeEnum.app404Session.getMsgCn());
					jrb.setCodeSys(ReturnCodeEnum.code400.toString());
					jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
					jrb.setSuccess(false);
					return this.returnJson(jrb);
				}
				httpSession.setAttribute(AppConfigSession.CodeVerify, code);
			}
			//更新
			AppVerifyAccountService appVerifyAccountService = new AppVerifyAccountService();
			AppVerifyAccountT appVerifyAccountT =appVerifyAccountService.findObjectBySessionId(sessionId);
			appVerifyAccountT.setSessionId(sessionId);
			appVerifyAccountT.setCode(code);
			Date date = new Date();
			appVerifyAccountT.setUpdateTime(date);
			appVerifyAccountT.setUpdateTimeLong(date.getTime());
			appVerifyAccountService.update(appVerifyAccountT);
			// 返回json
			jrb.setData(code);
			jrb.setCode(ReturnCodeEnum.app200Token.toString());
			jrb.setMsg(ReturnCodeEnum.app200Token.getMsgCn());
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
