package com.ibm.follow.servlet.cloud.core.job;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage2.cms.RecordNotifyDefine;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import com.ibm.follow.servlet.cloud.ibm_cms_remind.entity.IbmCmsRemind;
import com.ibm.follow.servlet.cloud.ibm_cms_remind.service.IbmCmsRemindService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

import java.util.*;

/**
 * @Description: 检测软件使用权即将过期的用户，发送续费提醒
 * @Author: wwj
 * @Date: 2020/5/13 14:39
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class NotifyUserRemindJob extends BaseCommJob {
	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		c.add(Calendar.DATE, 15);

		IbmManageTimeService manageTimeService = new IbmManageTimeService();
		List<Map<String, Object>> userInfos =  manageTimeService.listNearExpiredUser(c.getTime().getTime());
		IbmCmsNotifyService notifyService = new IbmCmsNotifyService();
		IbmCmsRemindService remindService = new IbmCmsRemindService();
		String notifyTitle = "续费提醒";
		String notifyContent = "您的软件到期时间为：%s,请及时续费以免影响使用！";

		// 查询用户是否已有续费通知消息
		for (Map<String, Object> map : userInfos) {
			String userId = map.get("APP_USER_ID_").toString();
			String userName = map.get("APP_USER_NAME_").toString();
			if (StringTool.isEmpty(remindService.findCmsNotifyId(userId))) {
				String timeStr = DateTool.getCnStr(NumberTool.getLong(map.get("END_TIME_LONG_")));
				String notifyCode = RecordNotifyDefine.getNotifyCode(IbmTypeEnum.REMIND, "PAY");
				String cmsNotifyId = RecordNotifyDefine.createNotify(notifyService, IbmTypeEnum.REMIND.name(), notifyTitle,
						String.format(notifyContent, timeStr), 0, "", IbmTypeEnum.ADMIN.name(), date);

				IbmCmsRemind cmsRemind = new IbmCmsRemind();
				cmsRemind.setCmsNotifyId(cmsNotifyId);
				cmsRemind.setNotifyCode(notifyCode);
				cmsRemind.setUserId(userId);
				cmsRemind.setUserName(userName);
				cmsRemind.setRemindCategory("PAY");
				cmsRemind.setCreateTime(date);
				cmsRemind.setCreateTimeLong(System.currentTimeMillis());
				cmsRemind.setUpdateTime(date);
				cmsRemind.setUpdateTimeLong(System.currentTimeMillis());
				cmsRemind.setState(IbmStateEnum.OPEN.name());
				remindService.save(cmsRemind);
			}
		}

	}
}
