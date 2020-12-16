package com.ibm.follow.connector.admin.manage.user;

import c.a.config.SysConfig;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import com.ibm.follow.servlet.cloud.ibm_cms_remind.service.IbmCmsRemindService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.entity.IbmManageTime;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_time.entity.IbmRepManageTime;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_time.service.IbmRepManageTimeService;
import com.ibm.follow.servlet.cloud.ibm_user.entity.IbmUser;
import com.ibm.follow.servlet.cloud.ibm_user.service.IbmUserService;
import org.doming.core.common.servlet.ActionMapping;
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
@ActionMapping(value = "/ibm/admin/manage/user/infoSet")
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
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		Date nowTime = new Date();
		try {
			IbmUserService userService = new IbmUserService();
			IbmUser user = userService.find(userId);
			if (user == null) {
				bean.putEnum(ReturnCodeEnum.app404Login);
				bean.putSysEnum(ReturnCodeEnum.code404);
				return bean;
			}
			String html;
			JSONObject result;
			// 修改密码
			if (StringTool.notEmpty(password)) {
				String regExpPwd = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}";
				if (!password.matches(regExpPwd)) {
					bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
					bean.putSysEnum(ReturnCodeEnum.code409);
					return bean;
				}
				html = editPassword();
				result = JSON.parseObject(html);
				if (!result.getBoolean("success")) {
					return result;
				}
			}

			// 修改积分
			if (StringTool.notEmpty(useablePoint)) {
				result =  editPoint();
				if (!result.getBoolean("success")) {
					return result;
				}
			}

			//获取积分记录
			result = getPointRep();
			if (!result.getBoolean("success")) {
				return result;
			}
			String logId = result.getJSONObject("data").getString("REP_POINT_ID_");

			// 修改到期时间
			IbmManageTimeService manageTimeService = new IbmManageTimeService();
			IbmManageTime time = manageTimeService.findByUserId(userId);
			if (time.getEndTimeLong() - endTime != 0) {
				updateManageTime(manageTimeService, time, NumberTool.longValueT(useablePoint), logId, nowTime);
			}

			bean.success();
		} catch (Exception e) {
			log.error("修改用户信息失败，", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}

	private String editPassword() throws Exception {
		//校验云接口数据
		JSONObject data = new JSONObject();
		data.put("userId",adminUserId );
		data.put("token", token);
		data.put("editUserId",userId );
		data.put("userPassWord", password);
		String timeStr = System.currentTimeMillis() + "";
		data.put("time", timeStr);
		data.put("valiDate", Md5Tool.generate(timeStr));
		Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
		String cloudUrl = url + "/cloud/user/api/editPassword";
		return HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
	}

	private JSONObject getPointRep() {
		//校验云接口数据
		JSONObject result;
		try {
			JSONObject data = new JSONObject();
			data.put("userId", userId);
			String timeStr = System.currentTimeMillis() + "";
			data.put("time", timeStr);
			data.put("valiDate", Md5Tool.generate(timeStr));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/point/repPoint";
			String html=HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			result = JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("修改用户信息获取点数失败，", e);
			result=new JSONObject();
		}
		return result;
	}

	private JSONObject editPoint()  {
		//校验云接口数据
		JSONObject result;
		try {
			JSONObject data = new JSONObject();
			data.put("userId", userId);
			data.put("useablePoint", useablePoint);
			String timeStr = System.currentTimeMillis() + "";
			data.put("time", timeStr);
			data.put("valiDate", Md5Tool.generate(timeStr));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/point/editPoint";
			String html=HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			result = JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("修改用户信息失败，", e);
			result=new JSONObject();
		}
		return result;
	}


	/**
	 * 修改到期时间
	 */
	private void updateManageTime(IbmManageTimeService manageTimeService, IbmManageTime time, long changePoint, String repId, Date nowTime) throws Exception {
		//修改到期时间
		IbmRepManageTimeService repManageTimeService = new IbmRepManageTimeService();
		Map<String, Object> lastRepTime = repManageTimeService.findLastRepByUserId(userId);
		if (time.getEndTimeLong() - endTime < 0) {
			// 查看是否有续费提醒消息
			String cmsNotifyId = new IbmCmsRemindService().findCmsNotifyId(userId);
			if (StringTool.notEmpty(cmsNotifyId)) {
				new IbmCmsNotifyService().updateNotify(IbmTypeEnum.REMIND, cmsNotifyId, IbmStateEnum.DEL.name(), new Date());
			}
		}
		IbmRepManageTime repTime = new IbmRepManageTime();
		repTime.setRepPointId(repId);
		repTime.setPreId(lastRepTime.get("preKey"));
		repTime.setUserId(userId);
		repTime.setUsedPointT(0);
		repTime.setAddTimeLong(changePoint);
		repTime.setStartTime(nowTime);
		repTime.setStartTimeLong(System.currentTimeMillis());
		repTime.setEndTime(new Date(endTime));
		repTime.setEndTimeLong(endTime);
		repTime.setRepEndTime(lastRepTime.get("END_TIME_"));
		repTime.setRepEndTimeLong(lastRepTime.get("END_TIME_LONG_"));
		repTime.setCreateTime(nowTime);
		repTime.setCreateTimeLong(System.currentTimeMillis());
		repTime.setUpdateTime(nowTime);
		repTime.setUpdateTimeLong(System.currentTimeMillis());
		repTime.setState(IbmStateEnum.OPEN.name());
		String repTimeId = repManageTimeService.save(repTime);

		time.setRepTimeId(repTimeId);
		time.setEndTime(new Date(endTime));
		time.setEndTimeLong(endTime);
		time.setUpdateUser(adminUser.getUserName());
		manageTimeService.update(time);
	}

	private boolean valiParameters() {
		userId = dataMap.getOrDefault("appUserId", "").toString();
		password = dataMap.getOrDefault("password", "").toString();
		endTime = NumberTool.getLong(dataMap.getOrDefault("endTime", 0));
		useablePoint = dataMap.getOrDefault("useablePoint", "").toString();

		return StringTool.isEmpty(userId, useablePoint) || endTime == 0;
	}

}
