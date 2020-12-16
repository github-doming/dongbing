package all.app.token;
import java.util.Map;
import all.app.ay.app_verify_account.service.AppVerifyAccountService;
import all.app.common.action.AppAction;
import all.app.common.service.AppService;
import all.gen.app_token.t.entity.AppTokenT;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.string.StringUtil;
import c.a.util.redis.RedisUtil;
import redis.clients.jedis.Jedis;
public class AppTokenLoginAction extends AppAction {
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
			String accountName = BeanThreadLocal.find(dataMapCustom.get("name"), "");
			String password = BeanThreadLocal.find(dataMapCustom.get("password"), "");
			String verifyCode = BeanThreadLocal.find(dataMapCustom.get("code"), "");
			String sessionId = BeanThreadLocal.find(dataMapCustom.get("session"), "");
			// 因为集群问题，验证码不能用redis保存，只能用数据库;想用redis最好把验证码做成微服务;
			if (false) {
				RedisUtil redisUtil = RedisUtil.findInstance();
				Jedis jedis = redisUtil.findJedis();
				String codeSession = redisUtil.findAppVerifyCode(jedis, sessionId);
			}
			AppVerifyAccountService appVerifyAccountService = new AppVerifyAccountService();
			String verifyCodeSession = appVerifyAccountService.findCodeBySessionId(sessionId);
			if (StringUtil.isBlank(verifyCodeSession)) {
				jrb.setCode(ReturnCodeEnum.app400VerifyCode.toString());
				jrb.setMsg(ReturnCodeEnum.app400VerifyCode.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			if (verifyCodeSession.equals(verifyCode)) {
			} else {
				jrb.setCode(ReturnCodeEnum.app404VerifyCode.toString());
				jrb.setMsg(ReturnCodeEnum.app404VerifyCode.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			// 因为集群问题，验证码不能用redis保存，只能用数据库;想用redis最好把验证码做成微服务;
			if (false) {
				RedisUtil redisUtil = RedisUtil.findInstance();
				Jedis jedis = redisUtil.findJedis();
				// 删除redis的验证码
				redisUtil.delAppVerifyCode(jedis, sessionId);
			}
			// 声明
			AppService appService = new AppService();
			// login
			AppTokenT appTokenT = appService.login(this.findCurrentUserTenantCode(), this.request, this.response,
					accountName, password);
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
