package com.ibs.plan.connector.pc.main;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.TypeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_cms_notify.entity.IbspCmsNotify;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import com.ibs.plan.module.cloud.ibsp_cms_remind.service.IbspCmsRemindService;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.entity.IbspCmsUserNotify;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.service.IbspCmsUserNotifyService;
import com.ibs.plan.module.cloud.ibsp_log_manage_point.entity.IbspLogManagePoint;
import com.ibs.plan.module.cloud.ibsp_log_manage_point.service.IbspLogManagePointService;
import com.ibs.plan.module.cloud.ibsp_log_manage_time.entity.IbspLogManageTime;
import com.ibs.plan.module.cloud.ibsp_log_manage_time.service.IbspLogManageTimeService;
import com.ibs.plan.module.cloud.ibsp_sys_card_price.service.IbspSysCardPriceService;
import com.ibs.plan.module.cloud.ibsp_sys_manage_time.entity.IbspSysManageTime;
import com.ibs.plan.module.cloud.ibsp_sys_manage_time.service.IbspSysManageTimeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.HttpTool;

import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * @Description: 使用积分
 * @Author: null
 * @Date: 2020-07-27 15:12
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/home/usePoint", method = HttpConfig.Method.POST)
public class UsePointAction extends CommCoreAction {
	public UsePointAction() {
		super.isTime = false;
	}


	private int proLongDay;
	private int pointTotal;

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		proLongDay = NumberTool.getInteger(dataMap, "proLongMonth", 0);
		pointTotal = NumberTool.getInteger(dataMap, "pointTotal", 0);

		if (pointTotal==0 || proLongDay==0) {
			return bean.put401Data();
		}

		try {
			IbspSysCardPriceService cardPriceService=new IbspSysCardPriceService();
			String cardPriceId=cardPriceService.findCard(proLongDay,pointTotal);
			if(StringTool.isEmpty(cardPriceId)){
				bean.putEnum(CodeEnum.IBS_401_DATA);
				bean.putSysEnum(CodeEnum.CODE_401);
				return bean;
			}
			//修改到期时间
			IbspSysManageTimeService manageTimeService = new IbspSysManageTimeService();
			IbspSysManageTime manageTime = manageTimeService.findByUserId(appUserId);
			if (manageTime == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			JSONObject parameter = new JSONObject();
			parameter.put("userId", appUserId);
			String time = System.currentTimeMillis() + "";
			parameter.put("time", time);
			parameter.put("valiDate", Md5Tool.generate(time));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/point/repPoint";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(parameter));
			JSONObject result = JSON.parseObject(html);
			if (!result.getBoolean("success")) {
				bean.putEnum(CodeEnum.IBS_401_DATA);
				bean.putSysEnum(CodeEnum.CODE_401);
				return bean;
			}
			int pointT = NumberTool.getInteger(result.getJSONObject("data").getString("BALANCE_T_"));
			if (pointT < NumberTool.intValueT(pointTotal)) {
				bean.putEnum(CodeEnum.IBS_403_INSUFFICIENT_POINT);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			int remainPointT = pointT - NumberTool.intValueT(pointTotal);
			parameter.put("useablePoint", NumberTool.doubleT(remainPointT));
			time = System.currentTimeMillis() + "";
			parameter.put("time", time);
			parameter.put("valiDate", Md5Tool.generate(time));
			cloudUrl = url + "/cloud/user/api/point/editPoint";
			html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(parameter));
			result = JSON.parseObject(html);
			if (!result.getBoolean("success")) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			long endTime = updateUserPoint(pointT, remainPointT, manageTime);

			// 查看是否有续费提醒消息
			String cmsNotifyId = new IbspCmsRemindService().findCmsNotifyId(appUserId);
			if (StringTool.notEmpty(cmsNotifyId)) {
				new IbspCmsNotifyService().updateNotify(IbsTypeEnum.REMIND, cmsNotifyId, StateEnum.DEL.name(), new Date());
			}

			bean.success(endTime);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "点击会员盘口", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}

	/**
	 * 更新时间
	 *
	 * @param pointT       减前点数
	 * @param remainPointT 剩余点数
	 * @param manageTime   可用时长信息
	 * @return
	 * @throws Exception
	 */
	private long updateUserPoint(int pointT, int remainPointT, IbspSysManageTime manageTime) throws Exception {
		Date nowTime = new Date();
		int usePoint = NumberTool.intValueT(pointTotal);

		IbspLogManagePointService logManagePointService = new IbspLogManagePointService();
		Map<String, Object> lastRepPoint = logManagePointService.findLastRepByUserId(appUserId);
		IbspLogManagePoint logManagePoint = new IbspLogManagePoint();
		if(ContainerTool.notEmpty(lastRepPoint)){
			logManagePoint.setPreId(lastRepPoint.get("preKey"));
		}
		logManagePoint.setAppUserId(appUserId);
		logManagePoint.setTitle("续费时长《" + proLongDay + "》天,消费点数：" + pointTotal);
		logManagePoint.setPreT(pointT);
		logManagePoint.setNumberT(usePoint);
		logManagePoint.setBalanceT(remainPointT);
		logManagePoint.setCreateTime(nowTime);
		logManagePoint.setCreateTimeLong(nowTime.getTime());
		logManagePoint.setUpdateTime(nowTime);
		logManagePoint.setUpdateTimeLong(nowTime.getTime());
		logManagePoint.setState(StateEnum.OPEN.name());
		logManagePoint.setDesc(appUserId);
		String repId = logManagePointService.save(logManagePoint);


		long endTime = manageTime.getEndTimeLong();
		// 续费2种情况 --  1.账号已到期、2.账号还没到期
		long oneDayDateLong = 24 * 60 * 60 * 1000L*proLongDay;
		if (endTime < System.currentTimeMillis()) {
			endTime = (System.currentTimeMillis() + oneDayDateLong );
		} else {
			endTime += oneDayDateLong;
		}
		IbspLogManageTimeService logManageTimeService = new IbspLogManageTimeService();
		Map<String, Object> lastRepTime = logManageTimeService.findLastRepByUserId(appUserId);
		IbspLogManageTime logManageTime = new IbspLogManageTime();

		if(ContainerTool.notEmpty(lastRepTime)){
			logManageTime.setPreId(lastRepTime.get("preKey"));
			logManageTime.setRepEndTime(lastRepTime.get("END_TIME_"));
			logManageTime.setRepEndTimeLong(lastRepTime.get("END_TIME_LONG_"));
		}else{
			logManageTime.setPreId("FIRST");
			logManageTime.setRepEndTime(nowTime);
			logManageTime.setRepEndTimeLong(nowTime.getTime());
		}
		logManageTime.setLogPointId(repId);
		logManageTime.setAppUserId(appUserId);
		logManageTime.setUsedPointT(usePoint);
		logManageTime.setAddTimeLong(proLongDay);
		logManageTime.setStartTime(nowTime);
		logManageTime.setStartTimeLong(System.currentTimeMillis());
		logManageTime.setEndTime(new Date(endTime));
		logManageTime.setEndTimeLong(endTime);
		logManageTime.setCreateTime(nowTime);
		logManageTime.setCreateTimeLong(System.currentTimeMillis());
		logManageTime.setUpdateTime(nowTime);
		logManageTime.setUpdateTimeLong(System.currentTimeMillis());
		logManageTime.setState(StateEnum.OPEN.name());
		String repTimeId = new IbspLogManageTimeService().save(logManageTime);

		manageTime.setLogTimeId(repTimeId);
		manageTime.setEndTime(new Date(endTime));
		manageTime.setEndTimeLong(endTime);
		manageTime.setUpdateUser(appUserId);
		new IbspSysManageTimeService().update(manageTime);

		// 记录用户提醒消息
		recordTriggerNotify(new IbspCmsNotifyService(), new IbspCmsUserNotifyService(), logManagePoint.getTitle(), nowTime);

		return endTime;
	}


	private void recordTriggerNotify(IbspCmsNotifyService ibspCmsNotifyService, IbspCmsUserNotifyService ibspCmsUserNotifyService,
									 String title, Date nowTime) throws Exception {
		IbspCmsNotify cmsNotify = new IbspCmsNotify();
		cmsNotify.setNotifyType(TypeEnum.MESSAGE.name());
		cmsNotify.setNotifyTitle(title);
		cmsNotify.setNotifyContent(title);
		cmsNotify.setNotifyLevel(0);
		cmsNotify.setNotifySite("");
		cmsNotify.setCreateUser(appUser.getNickname());
		cmsNotify.setCreateTime(nowTime);
		cmsNotify.setCreateTimeLong(System.currentTimeMillis());
		cmsNotify.setUpdateTime(nowTime);
		cmsNotify.setUpdateTimeLong(System.currentTimeMillis());
		cmsNotify.setState(StateEnum.OPEN.name());
		String cmsNotifyId = ibspCmsNotifyService.save(cmsNotify);

		String code = String.valueOf(System.currentTimeMillis()).substring(0, 10) + new Random().nextInt(10);
		String notifyCode = "M_" + "USE" + code;

		IbspCmsUserNotify cmsUserNotify = new IbspCmsUserNotify();
		cmsUserNotify.setCmsNotifyId(cmsNotifyId);
		cmsUserNotify.setNotifyCode(notifyCode);
		cmsUserNotify.setUserName(appUser.getNickname());
		cmsUserNotify.setUserId(appUserId);
		cmsUserNotify.setIsRead(0);
		cmsUserNotify.setCreateTime(nowTime);
		cmsUserNotify.setCreateTimeLong(System.currentTimeMillis());
		cmsUserNotify.setState(StateEnum.OPEN.name());
		ibspCmsUserNotifyService.save(cmsUserNotify);
	}
}
