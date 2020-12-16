package all.app.token;
import java.util.Date;
import java.util.Map;
import all.app.common.action.AppAction;
import all.app.common.service.AppUserRedisService;
import all.app.common.service.AppService;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_account.t.service.AppAccountTService;
import all.gen.app_token.t.entity.AppTokenT;
import all.gen.app_token.t.service.AppTokenTService;
import all.gen.app_user.t.entity.AppUserT;
import all.gen.app_user.t.service.AppUserTService;
import c.a.tools.NickNameTool;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.enums.bean.UserTypeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.random.RandomUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.uuid.Uuid;
/**
 * 
 * 游客登录
 * 
 * @Description:
 * @ClassName: AppTokenLoginGuestAction
 * @date 2018年5月25日 下午5:58:49
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class AppTokenLoginGuestAction extends AppAction {
	@Override
	public String run() throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
		try {
			// 声明对象
			AppTokenT appTokenT = new AppTokenT();
			AppTokenTService appTokenTService = new AppTokenTService();
			appUserT = new AppUserT();
			AppUserRedisService appUserRedisService = new AppUserRedisService();
			Date date = new Date();
			// 创建用户
			String headPortrait = "/common/img/avatar/avatar" + RandomUtil.findRandomInteger(25) + ".png";
			String nickName=NickNameTool.findNickName();
			appUserT.setAppUserName(nickName);
			appUserT.setNickname(nickName);
			appUserT.setAppUserType(UserTypeEnum.GUEST.getCode());
			// 头像
			appUserT.setHeadPortrait(headPortrait);
			appUserT.setCreateTime(date);
			appUserT.setCreateTimeLong(date.getTime());
			appUserT.setUpdateTime(date);
			appUserT.setUpdateTimeLong(date.getTime());
			appUserT.setState(UserStateEnum.OPEN.getCode());
			String appUserId = appUserRedisService.save(appUserT);
			appUserT.setAppUserId(appUserId);
			// 更新Token
			String token = Uuid.create().toString();
			appTokenT.setAppUserId(appUserId);
			appTokenT.setValue(token);
			appTokenT.setUserType(UserTypeEnum.GUEST.getCode());
			appTokenTService.save(appTokenT);
			// 返回json
			jrb.setData(appTokenT);
			jrb.setCode(ReturnCodeEnum.app200Login.toString());
			jrb.setMsg(ReturnCodeEnum.app200Login.getMsgCn());
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
