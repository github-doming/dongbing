package com.ibs.plan.connector.admin.manage.base.handicap.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_handicap.entity.IbspHandicap;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口游戏列表
 * @Author: null
 * @Date: 2020-04-18 10:38
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/handicap/game/list")
public class HandicapGameListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口id
		String handicapId = StringTool.getString(dataMap, "handicapId", "");

		try {
			IbspHandicapService handicapService = new IbspHandicapService();
			IbspHandicap handicap = handicapService.find(handicapId);
			if (handicap == null) {
				bean.putEnum(CodeEnum.IBS_404_HANDICAP);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Map<String, Object> data = new HashMap<>(2);

			IbspHandicapGameService handicapGameService = new IbspHandicapGameService();
			List<Map<String, Object>> list = handicapGameService.findByHandicapId(handicapId);

			data.put("gameInfo", list);
			data.put("handicapName", handicap.getHandicapName());
			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error("盘口游戏列表错误", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
