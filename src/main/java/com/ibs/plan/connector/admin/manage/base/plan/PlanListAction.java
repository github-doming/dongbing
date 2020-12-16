package com.ibs.plan.connector.admin.manage.base.plan;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 方案列表
 * @Author: admin1
 * @Date: 2020/6/20 13:46
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/plan/list")
public class PlanListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//方案名称
		String planName = StringTool.getString(dataMap, "planName", "");
		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		Map<String, Object> data = new HashMap<>(4);
		try {
			IbspPlanService planService = new IbspPlanService();
			PageCoreBean<Map<String, Object>> basePage = planService.listShow(planName,pageIndex,pageSize);
			List<Map<String,Object>> planList =  basePage.getList();
			// NUMBER BALL HAPPY
			for (Map<String,Object> plan:planList){
				plan.put("PLAN_WORTH_",NumberTool.doubleT(plan.get("PLAN_WORTH_T_")));
				String gameType = plan.get("AVAILABLE_GAME_TYPE_").toString();
				plan.put("AVAILABLE_GAME_TYPE_",getGameTypeCh(gameType));
			}

			data.put("planName", planName);
			data.put("rows", planList);
			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());
		} catch (Exception e) {
			log.error("方案列表错误", e);
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}

	private String getGameTypeCh(String gameType){
		StringBuilder sb = new StringBuilder();
		String[] arr = gameType.split(",");
		for (String type:arr){
			if(IbsTypeEnum.NUMBER.name().equals(type)){
				sb.append("竞速").append(",");
			}else if(IbsTypeEnum.BALL.name().equals(type)){
				sb.append("时时彩").append(",");
			}else{
				sb.append("快乐彩").append(",");
			}
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

}
