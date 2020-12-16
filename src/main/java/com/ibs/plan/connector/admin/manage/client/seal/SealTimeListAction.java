package com.ibs.plan.connector.admin.manage.client.seal;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_config.service.IbspConfigService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 盘口封盘时间列表
 *
 * @Author: Dongming
 * @Date: 2020-05-07 11:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/seal/list")
public class SealTimeListAction
		extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapCode = dataMap.getOrDefault("handicapCode", "").toString();
		String gameCode = dataMap.getOrDefault("gameCode", "").toString();

		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		Map<String, Object> data = new HashMap<>(3);
		try {
			IbspConfigService configService = new IbspConfigService();
			PageCoreBean basePage = configService.pageSealTimeInfo(handicapCode, gameCode, pageIndex, pageSize);
			List<Map<String, Object>> sealTimeInfos = basePage.getList();
			for (Map<String, Object> sealTimeInfo : sealTimeInfos) {
				String configKey = sealTimeInfo.remove("CONFIG_KEY_").toString();
				sealTimeInfo.put("HANDICAP_CODE",configKey.split("#")[0]);
				sealTimeInfo.put("HANDICAP_NAME", HandicapUtil.Code.valueOf(configKey.split("#")[0]).getName());
				sealTimeInfo.put("GAME_CODE_", configKey.split("#")[1]);
				sealTimeInfo.put("GAME_NAME_", GameUtil.Code.valueOf(configKey.split("#")[1]).getName());
				sealTimeInfo.put("SEAL_TIME_", sealTimeInfo.remove("CONFIG_VALUE_"));
			}

			if (StringTool.isEmpty(handicapCode) && StringTool.isEmpty(gameCode)) {
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
					games.add(game);
				}
				data.put("games", games);
			}
			data.put("rows", basePage.getList());
			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());

		} catch (Exception e) {
			log.error("盘口封盘时间列表错误", e);
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
			data.put("handicaps", null);
			data.put("games", null);
		}
		return data;

	}
}
