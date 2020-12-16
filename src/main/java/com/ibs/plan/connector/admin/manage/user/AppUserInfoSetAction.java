package com.ibs.plan.connector.admin.manage.user;

import c.a.config.SysConfig;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_log_manage_time.entity.IbspLogManageTime;
import com.ibs.plan.module.cloud.ibsp_log_manage_time.service.IbspLogManageTimeService;
import com.ibs.plan.module.cloud.ibsp_sys_manage_time.entity.IbspSysManageTime;
import com.ibs.plan.module.cloud.ibsp_sys_manage_time.service.IbspSysManageTimeService;
import com.ibs.plan.module.cloud.ibsp_user.entity.IbspUser;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 更新用户信息
 * @Author: null
 * @Date: 2020-03-13 15:26
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/infoSet")
public class AppUserInfoSetAction extends CommAdminAction {
	private String userId;
	private String password;
	private Long endTime;
	private String useablePoint;

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

		Date nowTime = new Date();
		try {
			IbspUserService userService = new IbspUserService();
			IbspUser user = userService.find(userId);
			if (user == null) {
				bean.putEnum(ReturnCodeEnum.app404Login);
				bean.putSysEnum(ReturnCodeEnum.code404);
				return bean;
			}
			JSONObject result;
			// 修改积分
			if (StringTool.notEmpty(useablePoint)) {
				result = editPoint();
				if (!result.getBoolean("success")) {
					return result;
				}
			}

			//获取积分记录
			result =  getPointRep();
			if (!result.getBoolean("success")) {
				return result;
			}
			String logId = result.getJSONObject("data").getString("REP_POINT_ID_");

			// 修改到期时间
			IbspSysManageTimeService timeService = new IbspSysManageTimeService();
			IbspSysManageTime time = timeService.findByUserId(userId);
			if (time.getEndTimeLong() - endTime != 0) {
				updateManageTime(timeService, time, NumberTool.longValueT(useablePoint), logId, nowTime);
			}

			// 修改密码
			if (StringTool.notEmpty(password)) {
				String regExpPwd = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}";
				if (!password.matches(regExpPwd)) {
					bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
					bean.putSysEnum(ReturnCodeEnum.code409);
					return bean;
				}
				result = editPassword();
				if (!result.getBoolean("success")) {
					return result;
				}
			}
			bean.success();
		} catch (Exception e) {
			log.error("修改用户信息失败，", e);
			throw e;
		}
		return bean;
	}

	private JSONObject editPassword() {
		//校验云接口数据
		try {
			JSONObject data = new JSONObject();
			data.put("userId",appUserId );
			data.put("token", token);
			data.put("editUserId", userId);
			data.put("userPassWord", password);
			String timeStr = System.currentTimeMillis() + "";
			data.put("time", timeStr);
			data.put("valiDate", Md5Tool.generate(timeStr));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/editPassword";
			String html= HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			return JSON.parseObject(html);
		} catch (Exception e) {
			log.error("修改用户信息失败，", e);
			return new JSONObject();
		}
	}

	private JSONObject getPointRep(){
		//校验云接口数据
		try {
			JSONObject data = new JSONObject();
			data.put("userId", userId);
			String timeStr = System.currentTimeMillis() + "";
			data.put("time", timeStr);
			data.put("valiDate", Md5Tool.generate(timeStr));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/point/repPoint";
			String html= HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			return JSON.parseObject(html);
		} catch (Exception e) {
			log.error("修改用户信息失败，", e);
			return new JSONObject();
		}
	}

	private JSONObject editPoint(){
		//校验云接口数据
		try {
			JSONObject data = new JSONObject();
			data.put("userId", userId);
			data.put("useablePoint", useablePoint);
			String timeStr = System.currentTimeMillis() + "";
			data.put("time", timeStr);
			data.put("valiDate", Md5Tool.generate(timeStr));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/point/editPoint";
			String html= HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			return JSON.parseObject(html);
		} catch (Exception e) {
			log.error("修改用户信息失败，", e);
			return new JSONObject();
		}
	}


	/**
	 * 修改到期时间
	 */
	private void updateManageTime(IbspSysManageTimeService timeService, IbspSysManageTime time, long changePoint, String logId, Date nowTime) throws Exception {
		//修改到期时间
		IbspLogManageTimeService logManageTimeService = new IbspLogManageTimeService();
		Map<String, Object> lastRepTime = logManageTimeService.findLastRepByUserId(userId);
		IbspLogManageTime logTime = new IbspLogManageTime();
		if(ContainerTool.notEmpty(lastRepTime)){
			logTime.setPreId(lastRepTime.get("preKey"));
			logTime.setRepEndTime(lastRepTime.get("END_TIME_"));
			logTime.setRepEndTimeLong(lastRepTime.get("END_TIME_LONG_"));
		}else{
			logTime.setPreId("FIRST");
			logTime.setRepEndTime(nowTime);
			logTime.setRepEndTimeLong(nowTime.getTime());
		}
		logTime.setLogPointId(logId);
		logTime.setAppUserId(userId);
		logTime.setAddTimeLong(changePoint);
		logTime.setStartTime(nowTime);
		logTime.setStartTimeLong(System.currentTimeMillis());
		logTime.setEndTime(new Date(endTime));
		logTime.setEndTimeLong(endTime);
		logTime.setCreateTime(nowTime);
		logTime.setCreateTimeLong(System.currentTimeMillis());
		logTime.setUpdateTime(nowTime);
		logTime.setUpdateTimeLong(System.currentTimeMillis());
		logTime.setState(IbsStateEnum.OPEN.name());
		String logTimeId = new IbspLogManageTimeService().save(logTime);

		time.setLogTimeId(logTimeId);
		time.setEndTime(new Date(endTime));
		time.setEndTimeLong(endTime);
		time.setUpdateUser(appUserId);
		timeService.update(time);
	}

	private boolean valiParameters() {
		userId = dataMap.getOrDefault("userId", "").toString();
		password = dataMap.getOrDefault("password", "").toString();
		endTime = NumberTool.getLong(dataMap.getOrDefault("endTime", 0));
		useablePoint = dataMap.getOrDefault("useablePoint", "").toString();

		return StringTool.isEmpty(appUserId) || endTime == 0;
	}

}
