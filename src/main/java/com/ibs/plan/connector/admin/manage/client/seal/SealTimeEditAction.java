package com.ibs.plan.connector.admin.manage.client.seal;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_config.entity.IbspConfig;
import com.ibs.plan.module.cloud.ibsp_config.service.IbspConfigService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * 封盘时间修改
 *
 * @Author: Dongming
 * @Date: 2020-05-07 14:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/seal/edit", method = HttpConfig.Method.POST) public class SealTimeEditAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String configId = dataMap.getOrDefault("configId", "").toString();
		String sealTimeStr = dataMap.getOrDefault("sealTime", "").toString();
		String state = dataMap.getOrDefault("state", "").toString();
		long sealTime = NumberTool.getLong(sealTimeStr);
		if (StringTool.isEmpty(configId, state)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}

		try {
			IbspConfigService configService = new IbspConfigService();
			IbspConfig config = configService.find(configId);
			if (config != null) {
				String configKey = 	config.getConfigKey();
				//不属于 封盘时间修改
				if (!StringTool.isContains(configKey,"#SEAL_TIME")){
					bean.putEnum(CodeEnum.IBS_403_DATA_ERROR);
					bean.putSysEnum(CodeEnum.CODE_403);
					return bean;
				}
				if(sealTime!=0){
					config.setConfigValue(sealTime);
				}
				config.setUpdateTime(new Date());
				config.setUpdateTimeLong(System.currentTimeMillis());
				config.setState(state);
				configService.update(config);
				bean.success();
			} else {
				bean.putEnum(CodeEnum.IBS_404_EXIST);
				bean.putSysEnum(CodeEnum.CODE_404);
			}
		} catch (Exception e) {
			log.error("封盘时间修改错误");
			throw e;
		}
		return bean;
	}
}
