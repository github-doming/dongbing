package com.ibs.plan.connector.admin.manage.client.seal;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_config.entity.IbspConfig;
import com.ibs.plan.module.cloud.ibsp_config.service.IbspConfigService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封盘时间详情
 *
 * @Author: Dongming
 * @Date: 2020-05-07 14:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/seal/form", method = HttpConfig.Method.GET) public class SealTimeFormAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String configId = dataMap.getOrDefault("configId", "").toString();
		try {
			Map<String, Object> data = new HashMap<>(3);

			if (StringTool.notEmpty(configId)) {

				IbspConfigService configService = new IbspConfigService();
				IbspConfig config = configService.find(configId);
				String configKey = config.getConfigKey();
				Map<String, Object> sealTimeInfo = new HashMap<>(3);
				sealTimeInfo.put("HANDICAP_NAME", HandicapUtil.Code.valueOf(configKey.split("#")[0]).getName());
				sealTimeInfo.put("GAME_CODE_", configKey.split("#")[1]);
				sealTimeInfo.put("GAME_NAME_", GameUtil.Code.valueOf(configKey.split("#")[1]).getName());
				sealTimeInfo.put("SEAL_TIME_", config.getConfigValue());
				sealTimeInfo.put("STATE_", config.getState());
				data.put("sealTimeInfo", sealTimeInfo);
			}

			List<Map<String, Object>> handicaps = new ArrayList<>();
			for (HandicapUtil.Code code : HandicapUtil.Code.values()) {
				Map<String, Object> handicap = new HashMap<>(2);
				handicap.put("code", code.name());
				handicap.put("name", code.getName());
				handicaps.add(handicap);
			}
			data.put("handicaps", handicaps);

			List<Map<String, Object>> games = new ArrayList<>();
			for (GameUtil.Code code : GameUtil.Code.values()) {
				Map<String, Object> game = new HashMap<>(2);
				game.put("code", code.name());
				game.put("name", code.getName());
				handicaps.add(game);
			}
			data.put("games", games);

			bean.success(data);
		} catch (Exception e) {
			log.error("盘口封盘时间详情错误", e);
			return bean.error(e.getMessage());
		}
		return bean;
	}
}
