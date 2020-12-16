package com.ibm.follow.connector.pc.home;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage2.cms.RecordNotifyDefine;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import com.ibm.follow.servlet.cloud.ibm_cms_remind.service.IbmCmsRemindService;
import com.ibm.follow.servlet.cloud.ibm_cms_user_notify.service.IbmCmsUserNotifyService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.entity.IbmManageTime;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_point.entity.IbmRepManagePoint;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_point.service.IbmRepManagePointService;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_time.entity.IbmRepManageTime;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_time.service.IbmRepManageTimeService;
import com.ibm.follow.servlet.cloud.ibm_sys_card_price.service.IbmSysCardPriceService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.HttpTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 使用积分
 * @Author: wwj
 * @Date: 2020/4/21 18:09
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/home/usePoint", method = HttpConfig.Method.POST)
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
            return returnJson(threadJrb);
        }
		proLongDay = NumberTool.getInteger(dataMap, "proLongMonth", 0);
		pointTotal = NumberTool.getInteger(dataMap, "pointTotal", 0);

        if (pointTotal==0 || proLongDay==0) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        try {
        	//判断是否存在该卡种
			IbmSysCardPriceService cardPriceService=new IbmSysCardPriceService();
			String cardPriceId=cardPriceService.findCrad(proLongDay,pointTotal);
			if(StringTool.isEmpty(cardPriceId)){
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean;
			}

        	IbmManageTimeService manageTimeService=new IbmManageTimeService();
			IbmManageTime manageTime=manageTimeService.findByUserId(appUserId);
			if(manageTime==null){
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean;
			}
			JSONObject result = getPoint();
			if (result==null||!result.getBoolean("success")) {
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean;
			}
			int pointT = NumberTool.getInteger(result.getJSONObject("data").getString("BALANCE_T_"));
			if (pointT < NumberTool.intValueT(pointTotal)) {
				bean.putEnum(IbmCodeEnum.IBM_403_INSUFFICIENT_POINT);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			int remainPointT = pointT - NumberTool.intValueT(pointTotal);
			if(!editPoint(remainPointT)){
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean;
			}
			long endTime = updateUserPoint(pointT, remainPointT, manageTime);

            // 查看是否有续费提醒消息
            String cmsNotifyId = new IbmCmsRemindService().findCmsNotifyId(appUserId);
            if(StringTool.notEmpty(cmsNotifyId)){
                new IbmCmsNotifyService().updateNotify(IbmTypeEnum.REMIND,cmsNotifyId,IbmStateEnum.DEL.name(),new Date());
            }
            bean.success(endTime);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("充值卡充值错误"), e);
            bean.error(e.getMessage());
        }
        return bean;

    }

	private boolean editPoint(int remainPointT) {
		try {
			JSONObject parameter = new JSONObject();
			String time = System.currentTimeMillis() + "";
			parameter.put("userId", appUserId);
			parameter.put("useablePoint", NumberTool.doubleT(remainPointT));
			parameter.put("time", time);
			parameter.put("valiDate", Md5Tool.generate(time));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/point/editPoint";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(parameter));
			JSONObject result = JSON.parseObject(html);
			return result.getBoolean("success");
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("充值卡修改点数错误"), e);
			return false;
		}
	}

	private JSONObject getPoint()  {
		try {
			JSONObject parameter = new JSONObject();
			parameter.put("userId", appUserId);
			String time = System.currentTimeMillis() + "";
			parameter.put("time", time);
			parameter.put("valiDate", Md5Tool.generate(time));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/point/repPoint";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(parameter));
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("充值卡获取点数错误"), e);
			return null;
		}
	}
    /**
     * 更新积分,保存记录
     */
    private long updateUserPoint(int point, int remainPointT, IbmManageTime manageTime) throws Exception {
		Date nowTime = new Date();
		int usePoint = NumberTool.intValueT(pointTotal);

        IbmRepManagePointService repManagePointService = new IbmRepManagePointService();
        Map<String, Object> lastRepPoint = repManagePointService.findLastRepByUserId(appUserId);
		IbmRepManagePoint repPoint = new IbmRepManagePoint();
		if(ContainerTool.notEmpty(lastRepPoint)){
			repPoint.setPreId(lastRepPoint.get("preKey"));
		}
        repPoint.setAppUserId(appUserId);
        repPoint.setTitle("续费时长《" + proLongDay + "》天,消费点数：" + pointTotal);
        repPoint.setPreT(point);
        repPoint.setNumberT(usePoint);
        repPoint.setBalanceT(remainPointT);
        repPoint.setCreateTime(nowTime);
        repPoint.setCreateTimeLong(nowTime.getTime());
        repPoint.setUpdateTime(nowTime);
        repPoint.setUpdateTimeLong(nowTime.getTime());
        repPoint.setState(IbmStateEnum.OPEN.name());
        repPoint.setDesc(appUserId);
        String repId = repManagePointService.save(repPoint);

		long endTime = manageTime.getEndTimeLong();
		// 续费2种情况 --  1.账号已到期、2.账号还没到期
		long oneDayDateLong = 24 * 60 * 60 * 1000L* proLongDay;
		if (endTime < System.currentTimeMillis()) {
			endTime = (System.currentTimeMillis() + oneDayDateLong);
		} else {
			endTime += oneDayDateLong;
		}
        //修改到期时间
        IbmRepManageTimeService repManageTimeService = new IbmRepManageTimeService();
        Map<String, Object> lastRepTime = repManageTimeService.findLastRepByUserId(appUserId);
		IbmRepManageTime repTime = new IbmRepManageTime();
		if(ContainerTool.notEmpty(lastRepTime)){
			repTime.setPreId(lastRepTime.get("preKey"));
		}

        repTime.setRepPointId(repId);
        repTime.setUserId(appUserId);
        repTime.setUsedPointT(usePoint);
        repTime.setAddTimeLong(proLongDay);
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
        String repTimeId = new IbmRepManageTimeService().save(repTime);

		manageTime.setRepTimeId(repTimeId);
		manageTime.setEndTime(new Date(endTime));
		manageTime.setEndTimeLong(endTime);
		manageTime.setUpdateUser(appUser.getNickname());
        new IbmManageTimeService().update(manageTime);

        // 记录用户提醒消息
        RecordNotifyDefine.recordTriggerNotify(new IbmCmsNotifyService(), new IbmCmsUserNotifyService(), appUserId,appUser.getNickname(), repPoint.getTitle(),"USE", nowTime);

        return endTime;
    }
}