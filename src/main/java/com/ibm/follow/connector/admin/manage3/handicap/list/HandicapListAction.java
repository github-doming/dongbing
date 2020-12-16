package com.ibm.follow.connector.admin.manage3.handicap.list;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapGameService;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 获取盘口列表
 * @Author: Dongming
 * @Date: 2019-11-04 14:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */public class HandicapListAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}
		String handicapCategory = request.getParameter("HANDICAP_CATEGORY_");
		String handicapType = request.getParameter("HANDICAP_TYPE_");
		String key = request.getParameter("key");
		try {
			Map<String, Object> data = new HashMap<>(1);

			//类别
			List<Map<String, Object>> categories = new ArrayList<>(2);
			categories.add(HandicapTool.getTypeMap(IbmTypeEnum.AGENT));
			categories.add(HandicapTool.getTypeMap(IbmTypeEnum.MEMBER));
			data.put("categories", categories);

			//类型
			List<Map<String,Object>> types = new ArrayList<>(2);
			types.add(HandicapTool.getTypeMap(IbmTypeEnum.FREE));
			types.add(HandicapTool.getTypeMap(IbmTypeEnum.CHARGE));
			types.add(HandicapTool.getTypeMap(IbmTypeEnum.ADMIN));
			types.add(HandicapTool.getTypeMap(IbmTypeEnum.SYS));
			data.put("types", types);

			// 后台展示盘口信息
			IbmAdminHandicapGameService handicapGameService = new IbmAdminHandicapGameService();
			List<Map<String, Object>> handicapInfos = new IbmAdminHandicapService().listShow(handicapCategory,handicapType,key);
			for (Map<String, Object> handicapInfo : handicapInfos) {
				//盘口类别
				IbmTypeEnum category = IbmTypeEnum
						.valueCustomerTypeOf(handicapInfo.remove("HANDICAP_CATEGORY_").toString());
				if (category == null) {
					handicapInfo.put("HANDICAP_CATEGORY_", "未知");
				} else {
					handicapInfo.put("HANDICAP_CATEGORY_", category.getMsg());
				}

				//盘口类型
				IbmTypeEnum type = IbmTypeEnum.valueOf(handicapInfo.remove("HANDICAP_TYPE_").toString());
				handicapInfo.put("HANDICAP_TYPE_", type.getMsg());

				//盘口ID
				String handicapId = handicapInfo.get("IBM_HANDICAP_ID_").toString();
				handicapInfo.put("HANDICAP_ID_", handicapId);

				List<String> gameNames = handicapGameService.listGameName(handicapId);
				handicapInfo.put("GAME_NAMES_", String.join(",", gameNames));

				//盘口价值
				handicapInfo.put("HANDICAP_WORTH_", NumberTool.doubleT(handicapInfo.get("HANDICAP_WORTH_T_")));
			}
			data.put("handicapInfos", handicapInfos);


			//回显数据
			data.put("HANDICAP_CATEGORY_", handicapCategory);
			data.put("HANDICAP_TYPE_", handicapType);
			data.put("key", key);



			return new ModelAndView("/pages/com/ibm/admin/manager/handicap/list.jsp", data);
		} catch (Exception e) {
			log.error("获取盘口列表错误", e);
			return new JsonResultBeanPlus().error(e.getMessage());
		}
	}
}
