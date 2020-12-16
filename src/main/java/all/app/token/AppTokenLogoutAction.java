package all.app.token;
import java.util.Date;
import java.util.Map;

import all.app.common.action.AppAction;
import all.gen.app_token.t.entity.AppTokenT;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.string.StringUtil;
import c.a.util.core.uuid.Uuid;
public class AppTokenLogoutAction extends AppAction {
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
			String token = BeanThreadLocal.find(dataMapCustom.get("token"), "");
			// AppToken
			AppTokenT appTokenT = appTokenRedisService.findAppTokenByToken(token);
			if (appTokenT == null) {
				jrb.setCode(ReturnCodeEnum.app401Token.toString());
				jrb.setMsg(ReturnCodeEnum.app401Token.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			// 从redis删除Token
			if (StringUtil.isNotBlank(token)) {
				// value sent to redis cannot be null
				appTokenRedisService.delTokenByRedis(token);
			}

			Date date = new Date();
			// 更新Token
			String tokenNew = Uuid.create().toString();
			// String tokenNew =null;
			appTokenT.setValue(tokenNew);
			appTokenT.setState(UserStateEnum.LOGOUT.getCode());
			appTokenT.setUpdateTime(date);
			appTokenT.setUpdateTimeLong(date.getTime());
			appTokenRedisService.update(appTokenT);
			// 返回json
			// jrb.setData(appTokenT);
			jrb.setCode(ReturnCodeEnum.app200Logout.toString());
			jrb.setMsg(ReturnCodeEnum.app200Logout.getMsgCn());
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
