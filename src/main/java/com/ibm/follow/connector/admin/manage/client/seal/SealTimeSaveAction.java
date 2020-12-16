package com.ibm.follow.connector.admin.manage.client.seal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_config.entity.IbmConfig;
import com.ibm.follow.servlet.cloud.ibm_config.service.IbmConfigService;
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
@ActionMapping(value = "/ibm/admin/manage/client/seal/save", method = HttpConfig.Method.POST) public class SealTimeSaveAction
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
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			IbmConfigService configService = new IbmConfigService();
			long oldSealTime = configService.findSealTime(handicapCode, gameCode);
			if (oldSealTime == 0) {
				Date nowTime = new Date();

				IbmConfig config = new IbmConfig();
				config.setConfigKey(handicapCode.concat("#").concat(gameCode).concat("#SEAL_TIME"));
				config.setConfigValue(sealTime);
				config.setCreateUser(adminUser.getUserName());
				config.setCreateTime(nowTime);
				config.setCreateTimeLong(System.currentTimeMillis());
				config.setUpdateTime(nowTime);
				config.setUpdateTimeLong(System.currentTimeMillis());
				config.setState(state);
				configService.save(config);
				bean.success();
			} else {
				bean.putEnum(IbmCodeEnum.IBM_403_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
			}
		} catch (Exception e) {
			log.error("封盘时间保存错误");
			bean.error(e.getMessage());
		}
		return bean;
	}
}
