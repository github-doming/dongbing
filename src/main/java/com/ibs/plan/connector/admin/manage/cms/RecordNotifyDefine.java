package com.ibs.plan.connector.admin.manage.cms;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_cms_notify.entity.IbspCmsNotify;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.entity.IbspCmsUserNotify;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.service.IbspCmsUserNotifyService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Description: 消息
 * @Author: admin1
 * @Date: 2020/4/1 14:15
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class RecordNotifyDefine {
	protected static final Logger log = LogManager.getLogger(RecordNotifyDefine.class);
	private static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd HH:mm");
	protected static final String contentFormat = "您的%s于%s分%s%s盘口,账号为%s;";

	/**
	 * 创建消息主体
	 *
	 * @param notifyType    消息类型
	 * @param notifyTitle   消息标题
	 * @param notifyContent 消息正文
	 * @param notifyLevel   消息等级
	 * @param notifySite    消息位置
	 * @param createUser    创建人
	 * @param date          创建时间
	 */
	public static String createNotify(IbspCmsNotifyService cmsNotifyService, String notifyType, String notifyTitle,
									  String notifyContent, int notifyLevel, String notifySite, String createUser, Date date) throws Exception {
		IbspCmsNotify cmsNotify = new IbspCmsNotify();
		cmsNotify.setNotifyType(notifyType);
		cmsNotify.setNotifyTitle(notifyTitle);
		cmsNotify.setNotifyContent(notifyContent);
		cmsNotify.setNotifyLevel(notifyLevel);
		cmsNotify.setNotifySite(notifySite);
		cmsNotify.setCreateUser(createUser);
		cmsNotify.setCreateTime(date);
		cmsNotify.setCreateTimeLong(System.currentTimeMillis());
		cmsNotify.setUpdateTime(date);
		cmsNotify.setUpdateTimeLong(System.currentTimeMillis());
		cmsNotify.setState(IbsStateEnum.OPEN.name());
		String cmsNotifyId = cmsNotifyService.save(cmsNotify);
		return cmsNotifyId;
	}

	/**
	 * 添加盘口提示用户消息
	 *
	 * @param type         提示类型
	 * @param triggerEvent 提示事件
	 * @param handicapCode 盘口
	 * @param userId       用户id
	 * @param userName     触发人
	 * @param eventDate    时间
	 */
	public static void recordTriggerNotify(IbspCmsNotifyService cmsNotifyService, IbspCmsUserNotifyService cmsUserNotifyService,
										   String type, String triggerEvent, String handicapCode, String userId, String userName, Date eventDate) throws Exception {

		String text = String.format(contentFormat, type, SDF.format(eventDate), triggerEvent, handicapCode, userName);
		String notifyTitle = text.replace(text.substring(text.lastIndexOf("盘")), "..");

		String cmsNotifyId = createNotify(cmsNotifyService, IbsTypeEnum.MESSAGE.name(), notifyTitle, text, 0, "", "SYS", eventDate);

		String module = triggerEvent.indexOf("登录") > 0 ? "LOGIN" : "BET";
		String notifyCode = RecordNotifyDefine.getNotifyCode(IbsTypeEnum.MESSAGE, module);
		IbspCmsUserNotify cmsUserNotify = new IbspCmsUserNotify();
		cmsUserNotify.setCmsNotifyId(cmsNotifyId);
		cmsUserNotify.setNotifyCode(notifyCode);
		cmsUserNotify.setUserId(userId);
		cmsUserNotify.setUserName(userName);
		cmsUserNotify.setIsRead(false);
		cmsUserNotify.setCreateTime(eventDate);
		cmsUserNotify.setCreateTimeLong(System.currentTimeMillis());
		cmsUserNotify.setState(IbsStateEnum.OPEN.name());
		cmsUserNotifyService.save(cmsUserNotify);

	}

	/**
	 * 添加提醒消息
	 *
	 * @param userId    用户id
	 * @param userName  触发人
	 * @param title     消息主体
	 * @param module    模块
	 * @param eventDate 时间
	 */
	public static void recordTriggerNotify(IbspCmsNotifyService cmsNotifyService, IbspCmsUserNotifyService cmsUserNotifyService, String userId, String userName, String title, String module, Date eventDate) throws Exception {
		String cmsNotifyId = createNotify(cmsNotifyService, IbsTypeEnum.MESSAGE.name(), title, title, 0, "", "SYS", eventDate);

		String notifyCode = RecordNotifyDefine.getNotifyCode(IbsTypeEnum.MESSAGE, module);
		IbspCmsUserNotify cmsUserNotify = new IbspCmsUserNotify();
		cmsUserNotify.setCmsNotifyId(cmsNotifyId);
		cmsUserNotify.setNotifyCode(notifyCode);
		cmsUserNotify.setUserId(userId);
		cmsUserNotify.setUserName(userName);
		cmsUserNotify.setIsRead(false);
		cmsUserNotify.setCreateTime(eventDate);
		cmsUserNotify.setCreateTimeLong(System.currentTimeMillis());
		cmsUserNotify.setState(IbsStateEnum.OPEN.name());
		cmsUserNotifyService.save(cmsUserNotify);

	}

	/**
	 * 获取消息编码
	 *
	 * @param notifyType 消息类型
	 * @param module     消息模块
	 */
	public static String getNotifyCode(IbsTypeEnum notifyType, String module) throws Exception {
		String notifyCode;
		String code = String.valueOf(System.currentTimeMillis()).substring(0, 10) + new Random().nextInt(10);
		switch (notifyType) {
			case REMIND:
				notifyCode = "R_" + module + code;
				break;
			case ANNOUNCE:
				notifyCode = "A_" + module + code;
				break;
			case MESSAGE:
				notifyCode = "M_" + module + code;
				break;
			default:
				throw new Exception("错误的消息类型：" + notifyType);
		}

		return notifyCode;
	}
}
