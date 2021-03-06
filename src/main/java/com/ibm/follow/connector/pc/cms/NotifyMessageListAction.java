package com.ibm.follow.connector.pc.cms;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import com.ibm.follow.servlet.cloud.ibm_cms_user_notify.service.IbmCmsUserNotifyService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 查询用户消息列表
 * @Author: wwj
 * @Date: 2020/4/2 11:13
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/cms/notify/list")
public class NotifyMessageListAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		// 消息类型
		String notifyType = StringTool.getString(dataMap.get("notifyType"), "").toUpperCase();
		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		Map<String, Object> data = new HashMap<>(3);
		try {
			IbmTypeEnum type = IbmTypeEnum.valueOf(notifyType);
			IbmCmsNotifyService notifyService = new IbmCmsNotifyService();
			PageCoreBean<Map<String, Object>> pageDate;
			switch (type) {
				case ANNOUNCE:
					pageDate = notifyService.listAllAnnounce(null, null, null, null, null, pageIndex, pageSize);
					break;
				case MESSAGE:
				case REMIND:
					pageDate = notifyService.listNotify(appUserId, pageIndex, pageSize);
					break;
				default:
					throw new Exception("消息类型错误：" + notifyType);
			}
			// 查询用户提示消息未读条数
			int messageCount = new IbmCmsUserNotifyService().getUnreadMsgByUserId(appUserId);
			// 查询提醒消息
			List<Map<String, Object>> remind = notifyService.findRemindByUserId(appUserId);

			data.put("remind", remind);
			data.put("messageCount", messageCount);
			data.put("rows", pageDate.getList());
			data.put("index", pageIndex);
			data.put("total", pageDate.getTotalCount());
		} catch (Exception e) {
			log.error("查询用户提醒消息出错！", e);
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}
}
