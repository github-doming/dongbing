package com.ibs.plan.connector.admin.manage.user;

import c.a.config.SysConfig;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.core.controller.init.RegisterInitController;
import com.ibs.plan.module.cloud.ibsp_exp_role.entity.IbspExpRole;
import com.ibs.plan.module.cloud.ibsp_exp_role.service.IbspExpRoleService;
import com.ibs.plan.module.cloud.ibsp_exp_user.entity.IbspExpUser;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import com.ibs.plan.module.cloud.ibsp_user.entity.IbspUser;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.HttpTool;

import java.util.*;

/**
 * @Description: 添加用户 用于pc端
 * @Author: null
 * @Date: 2020-03-14 14:03
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/save", method = HttpConfig.Method.POST)
public class AppUserSaveAction extends CommAdminAction {
	private String accountName, password, memberUsable, gameUsable, planUsable;
	private long endTime,useablePoint;


	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (valiParameters()) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		String regExpAccount = "[a-zA-Z0-9]{6,20}$";
		String regExpPwd = "[a-zA-Z](?![a-zA-Z]+$)\\w{5,20}";
		if (!accountName.matches(regExpAccount) || !password.matches(regExpPwd)) {
			bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
			bean.putSysEnum(ReturnCodeEnum.code409);
			return bean;
		}
		try {

			//校验云接口数据
			JSONObject data = new JSONObject();
			data.put("userId", appUserId);
			data.put("token", token);
			data.put("channelType", ChannelTypeEnum.PC);
			data.put("userAccount", accountName);
			data.put("userPassWord", password);
			data.put("state", IbsStateEnum.OPEN.name());
			data.put("tenantCode", IbsTypeEnum.SYS.name());
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			Object url= SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/registerUser";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			JSONObject result = JSON.parseObject(html);

			if (!result.getBoolean("success")) {
				return result;
			}
			JSONObject resultData = result.getJSONObject("data");
			String userId = resultData.getString("userId");
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(CodeEnum.IBS_403_ERROR);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			// 注册用户
			Date nowTime = new Date();
			IbspUser user = new IbspUser();
			user.setAppUserId(userId);
			user.setNickname(accountName);
			user.setUserType(IbsTypeEnum.USER);
			user.setCreateTime(nowTime);
			user.setCreateTimeLong(System.currentTimeMillis());
			user.setUpdateTime(nowTime);
			user.setUpdateTimeLong(System.currentTimeMillis());
			user.setState(IbsStateEnum.OPEN.name());
			new IbspUserService().save(user);

			//注册初始化信息
			initUserInfo(userId, nowTime);

			bean.success();
		} catch (Exception e) {
			log.error("创建用户失败，", e);
			throw e;
		}
		return bean.toString();
	}

	private void initUserInfo(String appUserId, Date nowTime) throws Exception {
		IbspExpRoleService expRoleService = new IbspExpRoleService();
		IbspExpRole expRole = expRoleService.findByCode(IbsTypeEnum.FREE);

		//初始化用户角色信息,暂定为用户收费等级为1
		IbspExpUser expUser = new IbspExpUser();
		expUser.setAppUserId(appUserId);
		expUser.setExpRoleId(expRole.getIbspExpRoleId());
		expUser.setAvailableGame(gameUsable);
		expUser.setAvailablePlan(planUsable);
		expUser.setAvailableHandicap(memberUsable);
		expUser.setOnline(0);
		expUser.setOnlineMax(expRole.getOnlineMax());
		expUser.setCreateTime(nowTime);
		expUser.setCreateTimeLong(System.currentTimeMillis());
		expUser.setUpdateTime(nowTime);
		expUser.setUpdateTimeLong(System.currentTimeMillis());
		expUser.setState(IbsStateEnum.OPEN.name());
		new IbspExpUserService().save(expUser);

		//会员盘口
		Set<String> handicapCodes = new HashSet<>(Arrays.asList(memberUsable.split(",")));
		//获取用户拥有的所有盘口codes
		IbspHmUserService hmUserService = new IbspHmUserService();
		List<String> allHandicapCodes = hmUserService.findHandicapCodeByUserId(appUserId);
		for (String handicapCode : allHandicapCodes) {
			handicapCodes.remove(handicapCode);
		}
		//获取用户可开启的盘口信息
		List<Map<String, Object>> handicaps = new IbspHandicapService().listInfoByCodes(handicapCodes);
		if (ContainerTool.notEmpty(handicaps)) {
			hmUserService.saveRegister(handicaps, appUserId, expRole.getHmOnlineMax(), nowTime);
		}

		RegisterInitController controller = new RegisterInitController();
		//更新方案
		controller.initPlan(appUserId, expUser);
		//初始化点数、时间
		controller.initPointAndTime(appUserId, nowTime, useablePoint, endTime);

	}

	private boolean valiParameters() {
		//用户名
		accountName = StringTool.getString(dataMap, "accountName", "");
		//密码
		password = StringTool.getString(dataMap, "password", "");

		//会员可用盘口 memberUsable
		memberUsable = StringTool.getString(dataMap, "memberUsable", "");
		//可用游戏
		gameUsable = StringTool.getString(dataMap, "gameUsable", "");
		//可用方案
		planUsable = StringTool.getString(dataMap, "planUsable", "");

		//点数到期时间
		endTime = NumberTool.getLong(dataMap, "endTime", -1L);
		//点数
		useablePoint =  NumberTool.getLong(dataMap, "useablePoint", -1L);
		return StringTool.isEmpty(accountName, password, endTime, useablePoint) || endTime<0||useablePoint<0;
	}

}
