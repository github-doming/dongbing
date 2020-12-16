package c.x.platform.root.compo.token;
import java.util.Date;
import java.util.Map;

import all.gen.sys_request_token.t.entity.SysRequestTokenT;
import all.sys.ay.sys_request_token.service.SysRequestTokenService;
import c.a.tools.crud.action.TransactionAction;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
import c.a.util.core.uuid.Uuid;
public abstract class TokenAction extends TransactionAction {
	public boolean token = false;
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract JsonTcpBean executeToken() throws Exception;
	/**
	 * 实现父类方法
	 * 
	 */
	@Override
	public JsonTcpBean executeTransaction() throws Exception {
		if (token) {
			/**
			 * 
			 * 需要token
			 */
			return this.token();
		} else {
			/**
			 * 
			 * 不需要token
			 */
			return this.tokenNot();
		}
	}
	/**
	 * 
	 * 不需要token
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean tokenNot() throws Exception {
		JsonTcpBean returnStr = this.executeToken();
		return returnStr;
	}
	/**
	 * 
	 * 需要token
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean token() throws Exception {
		Date 	date = new Date();
		SysRequestTokenT sysRequestToken = new SysRequestTokenT();
		sysRequestToken.setToken(Uuid.create().toString());
		sysRequestToken.setVersion(1);
		sysRequestToken.setServletPath(request.getServletPath());
		sysRequestToken.setExpiredTime(date);
		sysRequestToken.setCreateTime(date);
		sysRequestToken.setUpdateTime(date);
		sysRequestToken.setExpiredTimeLong(date.getTime());
		sysRequestToken.setCreateTimeLong(date.getTime());
		sysRequestToken.setUpdateTimeLong(date.getTime());
		SysRequestTokenService biz = new SysRequestTokenService();
		biz.save(sysRequestToken);
		request.setAttribute("token", sysRequestToken.getToken());
		request.setAttribute("form_token_key", "form_token_key");
		JsonTcpBean returnStr = this.executeToken();
		return returnStr;
	}
	public RequestTokenContext findRequestTokenContext() throws Exception {
		RequestTokenContext context = new RequestTokenContext();
		context.setSuccess(false);
		String token = BeanThreadLocal.findThreadLocal().get().find(request.getParameter("form_token_key"), "");
		if (StringUtil.isBlank(token)) {
			// log.trace("token is null ");
		} else {
			SysRequestTokenService biz = new SysRequestTokenService();
			Map<String, Object> map = biz.findByToken(token);
			if (map == null) {
				// log.trace("map is null ");
			} else {
				int version = BeanThreadLocal.findThreadLocal().get().find(map.get("version"), 0);
				String id = BeanThreadLocal.findThreadLocal().get().find(map.get("id"), "");
				if (StringUtil.isBlank(id)) {
					// log.trace("id is null ");
				} else {
					if (version == 1) {
						int count = biz.updateVersion(token, 1);
						if (count == 0) {
							context.setCode(1);
							context.setMsg("error_1");
							log.trace("不要重复提交 ,并发失败退出,error_1");
						} else {
							context.setSuccess(true);
							context.setCode(0);
							context.setMsg("ok");
							log.trace("成功提交 ");
							log.trace("成功退出");
						}
					} else {
						context.setCode(2);
						context.setMsg("error_2");
						log.trace("不要重复提交 ,error_2");
					}
				}
			}
		}
		return context;
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
	
}
