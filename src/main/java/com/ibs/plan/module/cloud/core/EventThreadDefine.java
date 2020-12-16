package com.ibs.plan.module.cloud.core;

import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_event_config_set.entity.IbspEventConfigSet;
import com.ibs.plan.module.cloud.ibsp_event_config_set.service.IbspEventConfigSetService;
import com.ibs.plan.module.cloud.ibsp_event_login.entity.IbspEventLogin;
import com.ibs.plan.module.cloud.ibsp_event_login.service.IbspEventLoginService;
import com.ibs.plan.module.cloud.ibsp_event_login_vali.entity.IbspEventLoginVali;
import com.ibs.plan.module.cloud.ibsp_event_login_vali.service.IbspEventLoginValiService;
import com.ibs.plan.module.cloud.ibsp_event_logout.entity.IbspEventLogout;
import com.ibs.plan.module.cloud.ibsp_event_logout.service.IbspEventLogoutService;

import java.util.Date;
/**
 * 事件线程工具类
 *
 * @Author: null
 * @Date: 2020-05-22 14:52
 * @Version: v1.0
 */
public class EventThreadDefine {

	/**
	 * 保存客户端登录事件
	 *
	 * @param eventLoginService 客户端登录事件服务类
	 * @param handicapMemberId  盘口会员主键
	 * @param content           事件正文
	 * @param nowTime           更新时间
	 * @return 事件主键
	 */
	public static String saveLoginEvent(IbspEventLoginService eventLoginService, String handicapMemberId,
			JSONObject content, Date nowTime) throws Exception {
		IbspEventLogin eventLogin = new IbspEventLogin();
		eventLogin.setEventContent(content.toString());
		eventLogin.setHandicapMemberId(handicapMemberId);
		eventLogin.setEventState(IbsStateEnum.BEGIN.name());
		eventLogin.setCreateTime(nowTime);
		eventLogin.setCreateTimeLong(System.currentTimeMillis());
		eventLogin.setUpdateTimeLong(System.currentTimeMillis());
		eventLogin.setState(IbsStateEnum.OPEN.name());
		return eventLoginService.save(eventLogin);
	}

	/**
	 * 保存客户端登出事件
	 *
	 * @param eventLogoutService 客户端登出事件服务类
	 * @param handicapMemberId   盘口会员主键
	 * @param content            事件正文
	 * @param nowTime            更新时间
	 * @return 事件主键
	 */
	public static String saveLogoutEvent(IbspEventLogoutService eventLogoutService, String handicapMemberId,
			JSONObject content, Date nowTime) throws Exception {
		IbspEventLogout eventLogout = new IbspEventLogout();
		eventLogout.setHandicapMemberId(handicapMemberId);
		eventLogout.setEventContent(content.toString());
		eventLogout.setEventState(IbsStateEnum.BEGIN.name());
		eventLogout.setCreateTime(nowTime);
		eventLogout.setCreateTimeLong(System.currentTimeMillis());
		eventLogout.setUpdateTimeLong(System.currentTimeMillis());
		eventLogout.setState(IbsStateEnum.OPEN.name());
		return eventLogoutService.save(eventLogout);
	}

	/**
	 * 保存验证登录
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @param content          事件正文
	 * @param nowTime          更新时间
	 * @param desc             事件描述
	 * @return 事件主键
	 */
	public static String saveLoginValiEvent(String handicapMemberId, JSONObject content, Date nowTime, String desc)
			throws Exception {
		IbspEventLoginVali eventLoginVali = new IbspEventLoginVali();
		eventLoginVali.setHandicapMemberId(handicapMemberId);
		eventLoginVali.setEventContent(content);
		eventLoginVali.setEventState(IbsStateEnum.BEGIN.name());
		eventLoginVali.setCreateTime(nowTime);
		eventLoginVali.setCreateTimeLong(System.currentTimeMillis());
		eventLoginVali.setUpdateTimeLong(System.currentTimeMillis());
		eventLoginVali.setState(IbsStateEnum.OPEN.name());
		eventLoginVali.setDesc(desc);
		return new IbspEventLoginValiService().save(eventLoginVali);
	}

	/**
	 * 添加会员设置信息
	 *
	 * @param content 消息内容
	 * @param nowTime 当前时间
	 * @return 会员设置信息
	 */
	public static String saveMemberConfigSetEvent(JSONObject content, Date nowTime) throws Exception {
		return saveMemberConfigSetEvent(content, nowTime, "添加会员设置信息", new IbspEventConfigSetService());
	}

	/**
	 * 添加会员设置信息
	 *
	 * @param content               消息内容
	 * @param nowTime               当前时间
	 * @param desc                  事件描述
	 * @param eventConfigSetService 设置事件服务类
	 * @return 会员设置信息
	 */
	public static String saveMemberConfigSetEvent(JSONObject content, Date nowTime, String desc,
			IbspEventConfigSetService eventConfigSetService) throws Exception {
		IbspEventConfigSet eventConfigSet = new IbspEventConfigSet();
		eventConfigSet.setEventContent(content);
		eventConfigSet.setEventState(IbsStateEnum.BEGIN.name());
		eventConfigSet.setCreateTime(nowTime);
		eventConfigSet.setCreateTimeLong(nowTime.getTime());
		eventConfigSet.setUpdateTimeLong(nowTime.getTime());
		eventConfigSet.setState(IbsStateEnum.OPEN.name());
		eventConfigSet.setDesc(desc);
		return eventConfigSetService.save(eventConfigSet);
	}
}
