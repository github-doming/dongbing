package com.ibs.plan.common.tools;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_cms_notify.entity.IbspCmsNotify;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.entity.IbspCmsUserNotify;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.service.IbspCmsUserNotifyService;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 消息工具类
 *
 * @Author: Dongming
 * @Date: 2020-06-02 18:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RecordNotifyTool {

	public static final String MESSAGE_FORMAT = "您的%s盘口会员于%s分%s,账号为%s;";
	public static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd HH:mm");

	/**
	 * 创建消息主体
	 *
	 * @param notifyService 消息主体服务类
	 * @param notifyType    消息类型
	 * @param notifyTitle   消息标题
	 * @param message       消息正文
	 * @param notifyLevel   消息等级
	 * @param notifySite    消息位置
	 * @param createUser    创建人
	 * @param nowTime       创建时间
	 * @return 主键
	 */
	private static String createNotify(IbspCmsNotifyService notifyService, String notifyType, String notifyTitle,
			String message, int notifyLevel, String notifySite, String createUser, Date nowTime) throws Exception {
		IbspCmsNotify notify = new IbspCmsNotify();
		notify.setNotifyType(notifyType);
		notify.setNotifyTitle(notifyTitle);
		notify.setNotifyContent(message);
		notify.setNotifyLevel(notifyLevel);
		notify.setNotifySite(notifySite);
		notify.setCreateUser(createUser);
		notify.setCreateTime(nowTime);
		notify.setCreateTimeLong(System.currentTimeMillis());
		notify.setUpdateTime(nowTime);
		notify.setUpdateTimeLong(System.currentTimeMillis());
		notify.setState(IbsStateEnum.OPEN.name());
		return notifyService.save(notify);
	}

	/**
	 * 添加提示用户消息
	 * @param notifyService 消息主体服务类
	 * @param userNotifyService 用户消息主体服务类
	 * @param notifyCode 用户消息编码
	 * @param message  消息正文
	 * @param userId 用户主键
	 * @param account 账户
	 * @param nowTime 创建时间
	 */
	public static void recordTriggerNotify(IbspCmsNotifyService notifyService, IbspCmsUserNotifyService userNotifyService,
			String notifyCode, String message, String userId, String account, Date nowTime) throws Exception {
		String notifyTitle = message.substring(0, Math.min(message.length(), 10)).concat("...");
		String cmsNotifyId = createNotify(notifyService, IbsTypeEnum.MESSAGE.name(), notifyTitle, message, 0, "", "SYS",
				nowTime);
		IbspCmsUserNotify userNotify = new IbspCmsUserNotify();
		userNotify.setCmsNotifyId(cmsNotifyId);
		userNotify.setNotifyCode(notifyCode);
		userNotify.setUserId(userId);
		userNotify.setUserName(account);
		userNotify.setIsRead(0);
		userNotify.setCreateTime(nowTime);
		userNotify.setCreateTimeLong(System.currentTimeMillis());
		userNotify.setState(IbsStateEnum.OPEN.name());
		userNotifyService.save(userNotify);

	}
}
