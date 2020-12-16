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
 * 封盘时间保存
 *
 * @Author: Dongming
 * @Date: 2020-05-07 14:25
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/seal/save", method = HttpConfig.Method.POST) public class SealTimeSaveAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapCode = dataMap.getOrDefault("handicapCode", "").toString();
		String gameCode = dataMap.getOrDefault("gameCode", "").toString();
		String sealTimeStr = dataMap.getOrDefault("sealTime", "").toString();
		String state = dataMap.getOrDefault("state", "").toString();
		long sealTime = NumberTool.getLong(sealTimeStr);
		if (StringTool.isEmpty(handicapCode, gameCode, sealTimeStr, state)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			IbspConfigService configService = new IbspConfigService();
			long oldSealTime = configService.findSealTime(handicapCode, gameCode);
			if (oldSealTime == 0) {
				Date nowTime = new Date();

				IbspConfig config = new IbspConfig();
				config.setConfigKey(handicapCode.concat("#").concat(gameCode).concat("#SEAL_TIME"));
				config.setConfigValue(sealTime);
				config.setCreateUser(appUserId);
				config.setCreateTime(nowTime);
				config.setCreateTimeLong(System.currentTimeMillis());
				config.setUpdateTime(nowTime);
				config.setUpdateTimeLong(System.currentTimeMillis());
				config.setState(state);
				configService.save(config);
				bean.success();
			} else {
				bean.putEnum(CodeEnum.IBS_403_EXIST);
				bean.putSysEnum(CodeEnum.CODE_403);
			}
		} catch (Exception e) {
			log.error("封盘时间保存错误");
			throw e;
		}
		return bean;
	}
}
