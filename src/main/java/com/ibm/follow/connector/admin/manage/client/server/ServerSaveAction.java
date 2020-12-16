package com.ibm.follow.connector.admin.manage.client.server;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.entity.IbmSysServletIp;
import com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.service.IbmSysServletIpService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 新增服务器
 * @Author: wwj
 * @Date: 2020/5/9 15:30
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/server/save", method = HttpConfig.Method.POST)
public class ServerSaveAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String serverIp = dataMap.getOrDefault("ip", "").toString();
		long timeStart = NumberTool.getLong(dataMap.get("timeStart"), 0L);
		long timeEnd = NumberTool.getLong(dataMap.get("timeEnd"), 0L);

		if (StringTool.isEmpty(serverIp, timeStart, timeEnd)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			IbmSysServletIpService service = new IbmSysServletIpService();
			Map<String, Object> serverInfo = service.findServletInfo(serverIp);
			if (ContainerTool.notEmpty(serverInfo)) {
				bean.putEnum(IbmCodeEnum.IBM_403_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			IbmSysServletIp entity = new IbmSysServletIp();
			entity.setServletIp(serverIp);
			entity.setStartTime(new Date(timeStart));
			entity.setStartTimeLong(timeStart);
			entity.setEndTime(new Date(timeEnd));
			entity.setEndTimeLong(timeEnd);
			entity.setCreateTime(new Date());
			entity.setCreateTimeLong(System.currentTimeMillis());
			entity.setCreateUser(adminUser.getUserName());
			entity.setUpdateTime(new Date());
			entity.setUpdateTimeLong(System.currentTimeMillis());
			entity.setState(IbmStateEnum.OPEN.name());
			entity.setDesc("初始化");
			service.save(entity);

			bean.success();
		} catch (Exception e) {
			log.error("封盘时间保存错误");
			bean.error(e.getMessage());
		}
		return bean;
	}
}
