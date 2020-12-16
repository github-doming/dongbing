package com.ibs.plan.connector.admin.manage.base.game.plan;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_game.entity.IbspGame;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_plan_game.service.IbspPlanGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 游戏方案详情列表
 * @Author: admin1
 * @Date: 2020/6/18 17:09
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/game/plan/list")
public class GamePlanListAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//游戏名称
		String gameId = StringTool.getString(dataMap, "gameId", "");

		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		Map<String, Object> data = new HashMap<>(3);
		try {
			IbspGame game = new IbspGameService().find(gameId);
			if(game==null){
				bean.putEnum(CodeEnum.IBS_404_EXIST);
				bean.putSysEnum(CodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			IbspPlanGameService planGameService = new IbspPlanGameService();

			PageCoreBean<Map<String, Object>> basePage = planGameService.listShow(gameId, pageIndex, pageSize);

			data.put("gameName",game.getGameName());
			data.put("rows", basePage.getList());
			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());
		} catch (Exception e) {
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}


}
