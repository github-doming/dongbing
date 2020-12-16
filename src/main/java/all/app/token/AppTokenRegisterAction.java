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
import c.a.util.redis.RedisUtil;
import redis.clients.jedis.Jedis;
public class AppTokenRegisterAction extends AppAction {
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
			String code = BeanThreadLocal.find(dataMapCustom.get("code"), "");
			String sessionId = BeanThreadLocal.find(dataMapCustom.get("session"), "");
			if (StringUtil.isBlank(sessionId)) {
				jrb.setCode(ReturnCodeEnum.app400Session.toString());
				jrb.setMsg(ReturnCodeEnum.app400Session.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			RedisUtil redisUtil = RedisUtil.findInstance();
			Jedis jedis = redisUtil.findJedis();
			String codeSession = redisUtil.findAppVerifyCode(jedis, sessionId);
			if (StringUtil.isBlank(codeSession)) {
				jrb.setCode(ReturnCodeEnum.app404Session.toString());
				jrb.setMsg(ReturnCodeEnum.app404Session.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			if (codeSession.equals(code)) {
			} else {
				jrb.setCode(ReturnCodeEnum.app404VerifyCode.toString());
				jrb.setMsg(ReturnCodeEnum.app404VerifyCode.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			//删除redis
			redisUtil.delAppVerifyCode(jedis, sessionId);
			// 声明
			AppService appService = new AppService();
			//Register
			appService.doRegister(this.request,this.response,accountName, password);
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
