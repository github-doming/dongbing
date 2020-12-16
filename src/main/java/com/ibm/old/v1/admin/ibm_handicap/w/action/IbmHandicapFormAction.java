package com.ibm.old.v1.admin.ibm_handicap.w.action;
import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_handicap.t.entity.IbmHandicapT;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.service.IbmHandicapGameTService;
import com.ibm.old.v1.cloud.ibm_handicap_item.t.entity.IbmHandicapItemT;
import com.ibm.old.v1.cloud.ibm_handicap_item.t.service.IbmHandicapItemTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.List;
/**
 * @Description: 跳转新增页面
 * @Author: zjj
 * @Date: 2019-08-13 11:03
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmHandicapFormAction extends BaseAppAction {

	@Override public String run() throws Exception {
		String id = request.getParameter("id");
		if(StringTool.isEmpty(id)){
			return CommViewEnum.Default.toString();
		}
		//获取盘口
		IbmHandicapTService handicapTService = new IbmHandicapTService();
		IbmHandicapT handicap = handicapTService.find(id);
		//获取盘口详情信息
		IbmHandicapItemTService handicapItemTService = new IbmHandicapItemTService();
		IbmHandicapItemT handicapItemT = handicapItemTService.findByHandicapId(id);

		double worth = NumberTool.doubleT(handicap.getHandicapWorthT());
		handicap.setHandicapWorthT((int)worth);
		//获取盘口游戏
		IbmHandicapGameTService handicapGameTService = new IbmHandicapGameTService();
		List<String> list = handicapGameTService.listGameId(id);
		StringBuilder gameIds = new StringBuilder();
		for (String gameId : list) {
			gameIds.append(gameId);
		}

		request.setAttribute("s", handicap);
		request.setAttribute("i", handicapItemT);
		request.setAttribute("gameIds", gameIds.toString());
		request.setAttribute("id", id);

		return CommViewEnum.Default.toString();
	}
}
