package com.ibm.follow.connector.admin.manage2.cms;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 消息列表
 * @Author: wwj
 * @Date: 2020/4/2 14:53
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/cms/notify/list")
public class NotifyListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		// 消息类型
		String notifyType = StringTool.getString(dataMap.get("notifyType"), "").toUpperCase();
		// 消息状态
		String notifyState = StringTool.getString(dataMap.get("notifyState"), "");
		// 消息标题
		String notifyTitle = StringTool.getString(dataMap.get("notifyTitle"), "");
		// 消息接收人/创建人
		String userName = StringTool.getString(dataMap.get("userName"), "");

		long timeStart = NumberTool.getLong(dataMap.get("timeStart"), 0L);
		long timeEnd = NumberTool.getLong(dataMap.get("timeEnd"), 0L);
		Long startTime = null, endTime = null;
		if (timeStart != 0) {
			startTime = DateTool.getDayStart(new Date(timeStart)).getTime();
			if (timeEnd == 0) {
				timeEnd = System.currentTimeMillis();
			}
			endTime = DateTool.getDayEnd(new Date(timeEnd)).getTime();
		}
		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);

		Map<String, Object> data = new HashMap<>(3);
		try {
			PageCoreBean<Map<String, Object>> pageDate;
			IbmCmsNotifyService notifyService = new IbmCmsNotifyService();
			IbmTypeEnum type = IbmTypeEnum.valueOf(notifyType);
			switch (type) {
				case REMIND:
					pageDate = notifyService.listAllRemind(notifyTitle, userName, notifyState, startTime, endTime, pageIndex, pageSize);
					break;
				case ANNOUNCE:
					pageDate = notifyService.listAllAnnounce(notifyTitle, userName, notifyState, startTime, endTime, pageIndex, pageSize);
					break;
				case MESSAGE:
					pageDate = notifyService.listAllMessage(notifyTitle, userName, startTime, endTime, pageIndex, pageSize);
					break;
				default:
					throw new Exception("消息类型错误：" + notifyType);
			}
			data.put("rows", pageDate.getList());
			data.put("index", pageIndex);
			data.put("total", pageDate.getTotalCount());
		} catch (Exception e) {
			log.error("查询公告出错！", e);
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}
}
