package com.ibm.old.v1.admin.ibm_handicap.w.action;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import org.doming.core.tools.NumberTool;

import java.util.List;
import java.util.Map;
/**
 * @Description: 获取所有盘口
 * @Author: zjj
 * @Date: 2019-08-13 10:45
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmHandicapListAction extends BaseAppAction {

	@Override public String run() throws Exception {
		// 分页
		Integer pageIndex = BeanThreadLocal.find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.find(request.getParameter(SysConfig.pageSizeName), 10);

		String handicapName = BeanThreadLocal.find(request.getParameter("handicapName"), "");

		IbmHandicapTService handicapTService = new IbmHandicapTService();
		PageCoreBean pageCore = handicapTService.find(handicapName, pageIndex, pageSize);

		List<Map<String, Object>> listMap = pageCore.getList();
		for(Map<String, Object> map:listMap){
			map.put("HANDICAP_WORTH_T_", NumberTool.doubleT(map.get("HANDICAP_WORTH_T_")));
		}
		request.setAttribute("cPage", pageCore);
		request.setAttribute("list", listMap);
		request.setAttribute("handicapName", handicapName);

		return CommViewEnum.Default.toString();
	}
}
