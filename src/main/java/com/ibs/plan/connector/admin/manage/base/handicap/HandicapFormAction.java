package com.ibs.plan.connector.admin.manage.base.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口表单
 * @Author: null
 * @Date: 2020-04-16 17:35
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/handicap/form",method = HttpConfig.Method.GET)
public class HandicapFormAction extends CommAdminAction {
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
			Map<String,Object> data = new HashMap<>(2);
			if(StringTool.notEmpty(handicapId)){
				//获取盘口信息
				Map<String, Object> handicapInfo = new IbspHandicapService().findInfo(handicapId);
				handicapInfo.put("HANDICAP_WORTH_T_", NumberTool.doubleT(handicapInfo.get("HANDICAP_WORTH_T_")));
				data.put("handicapInfo",handicapInfo);
			}
			List<Map<String, Object>> gameInfos = new IbspGameService().findGameInfo();
			data.put("gameInfos",gameInfos);

			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error("获取盘口修改表单页面信息错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
