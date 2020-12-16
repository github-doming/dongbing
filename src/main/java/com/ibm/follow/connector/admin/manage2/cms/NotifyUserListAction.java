package com.ibm.follow.connector.admin.manage2.cms;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import com.ibm.follow.servlet.cloud.ibm_cms_remind.entity.IbmCmsRemind;
import com.ibm.follow.servlet.cloud.ibm_cms_remind.service.IbmCmsRemindService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @Description: 获取需要提醒的用户列表
 * @Author: wwj
 * @Date: 2020/4/2 16:51
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/cms/notify/userList")
public class NotifyUserListAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String notifyCategory = StringTool.getString(dataMap.get("notifyCategory"), "").toUpperCase();
		if (StringTool.isEmpty(notifyCategory)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 15);
		try {
			IbmManageTimeService manageTimeService = new IbmManageTimeService();
			List<Map<String, Object>> userInfos = new ArrayList<>();
			if ("PAY".equals(notifyCategory)) {
				// -已过期用户 - 15天内到期用户
				userInfos = manageTimeService.listNearExpiredUser(c.getTime().getTime());
				IbmCmsNotifyService notifyService = new IbmCmsNotifyService();
				IbmCmsRemindService remindService = new IbmCmsRemindService();
				String notifyTitle = "续费提醒";
				String notifyContent = "您的软件到期时间为：%s,请及时续费以免影响使用！";
				Date date = new Date();
				// 查询用户是否已有续费通知消息
				for (Map<String, Object> map : userInfos) {
					String userId = map.get("APP_USER_ID_").toString();

					if (StringTool.isEmpty(remindService.findCmsNotifyId(userId))) {
						String timeStr = DateTool.getCnStr(NumberTool.getLong(map.get("END_TIME_LONG_")));
						String notifyCode = RecordNotifyDefine.getNotifyCode(IbmTypeEnum.REMIND, notifyCategory);
						String cmsNotifyId = RecordNotifyDefine.createNotify(notifyService, IbmTypeEnum.REMIND.name(), notifyTitle,
								String.format(notifyContent,timeStr), 0, "", adminUser.getUserName(), date);

						IbmCmsRemind cmsRemind = new IbmCmsRemind();
						cmsRemind.setCmsNotifyId(cmsNotifyId);
						cmsRemind.setNotifyCode(notifyCode);
						cmsRemind.setUserId(userId);
						cmsRemind.setUserName(map.get("APP_USER_NAME_").toString());
						cmsRemind.setRemindCategory(notifyCategory);
						cmsRemind.setCreateTime(date);
						cmsRemind.setCreateTimeLong(System.currentTimeMillis());
						cmsRemind.setUpdateTime(date);
						cmsRemind.setUpdateTimeLong(System.currentTimeMillis());
						cmsRemind.setState(IbmStateEnum.OPEN.name());
						remindService.save(cmsRemind);
					}
				}
			}

			bean.success(userInfos);
		} catch (Exception e) {
			log.error("获取需要提醒的用户列表", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
