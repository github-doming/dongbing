package c.x.platform.root.sso.action;
import java.util.Map;

import c.a.config.SysConfig;
import c.a.config.login.LoginUrlDy;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.compo.token.TokenAction;
import c.x.platform.root.util.SysUtil;
/**
 * 单点登录
 * 
 * @Description: 
 * @ClassName: SSOAction 
 * @date 2017年9月18日 下午3:01:21 
 * @author cxy
 * @Email: 
 * @Copyright 
 *
 */
public abstract class SSOAction extends TokenAction {
	public boolean sso = false;
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract JsonTcpBean executeSSO() throws Exception;
	/**
	 * 实现父类方法
	 * 
	 */
	/**
	 * 实现父类方法
	 * 
	 */
	@Override
	public JsonTcpBean executeToken() throws Exception {
		if (sso) {
			/**
			 * 
			 * 需要sso
			 */
			return this.sso();
		} else {
			/**
			 * 
			 * 不需要sso
			 */
			return this.ssoNot();
		}
	}
	/**
	 * 
	 * 不需要 sso
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean ssoNot() throws Exception {
		JsonTcpBean returnStr = this.executeSSO();
		return returnStr;
	}
	/**
	 * 
	 * 需要sso
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean sso() throws Exception {
		// 是否跳转到下一个过滤器
		boolean isGotoNextFilter = true;
		SysUtil sysUtil = new SysUtil();
		String value = request.getParameter("sso");
		if (StringUtil.isNotBlank(value)) {
			Map<String, Object> resultMap = sysUtil.findSSO2MapBySSO(value);
			if (resultMap != null) {
				// 得到表的id
				String userId = (String) resultMap.get("SYS_USER_ID_");
				sysUtil.saveCurrentSysUserByUserId(request, this.response,userId);
			} else {
				isGotoNextFilter = false;
			}
		}
		if (isGotoNextFilter) {
			// 跳转
			JsonTcpBean returnStr = this.executeSSO();
			return returnStr;
		} else {
			String contextPath = (String) SysConfig.findInstance().findMap().get(SysConfig.contextLocalPath);
			String returnPage = LoginUrlDy.RequestJspLoginSessionNot;
			response.sendRedirect(contextPath + returnPage);
		}
		return null;
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
