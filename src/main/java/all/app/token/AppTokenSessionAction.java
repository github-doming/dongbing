package all.app.token;
import java.util.Date;

import all.app.ay.app_verify_account.service.AppVerifyAccountService;
import all.app.common.action.AppAction;
import all.gen.app_verify_account.t.entity.AppVerifyAccountT;
import c.a.config.SysConfig;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.uuid.Uuid;
import c.a.util.redis.RedisUtil;
import c.x.platform.root.util.CookieUtil;
import redis.clients.jedis.Jedis;
public class AppTokenSessionAction extends AppAction {
	@Override
	public String run() throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
		try {
			// 因为集群问题，验证码不能用redis保存，只能用数据库;想用redis最好把验证码做成微服务;
			if (false) {
				RedisUtil redisUtil = RedisUtil.findInstance();
				Jedis jedis = redisUtil.findJedis();
				String sessionId = redisUtil.findNewAppSession(jedis);
			}
			CookieUtil cookieUtil = new CookieUtil();
			String sessionId = Uuid.findInstance().toString();
			cookieUtil.saveCookie(response, SysConfig.CookieSession, sessionId);
			String code = "";
			AppVerifyAccountService appVerifyAccountService = new AppVerifyAccountService();
			AppVerifyAccountT appVerifyAccountT = new AppVerifyAccountT();
			appVerifyAccountT.setSessionId(sessionId);
			appVerifyAccountT.setCode(code);
			Date date = new Date();
			appVerifyAccountT.setCreateTime(date);
			appVerifyAccountT.setCreateTimeLong(date.getTime());
			String channelType = ChannelTypeEnum.APP.getCode();
			appVerifyAccountT.setType(channelType);
			appVerifyAccountService.save(appVerifyAccountT);
			// 返回json
			jrb.setData(sessionId);
			jrb.setCode(ReturnCodeEnum.app200Session.toString());
			jrb.setMsg(ReturnCodeEnum.app200Session.getMsgCn());
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
