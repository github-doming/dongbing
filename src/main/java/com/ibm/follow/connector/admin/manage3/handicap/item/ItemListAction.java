package com.ibm.follow.connector.admin.manage3.handicap.item;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 盘口详情列表
 * @Author: Dongming
 * @Date: 2019-11-07 14:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@Deprecated
@ActionMapping(value = "/ibm/admin/manage/handicap/item/list1", method = HttpConfig.Method.GET)
public class ItemListAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}
		String handicapCategory = request.getParameter("HANDICAP_CATEGORY_");
		String handicapCode = request.getParameter("HANDICAP_CODE_");
		String handicapId = null;
		String key = request.getParameter("key");

		if (StringTool.notEmpty(handicapCategory, handicapCode)) {
			handicapId = HandicapUtil.id(handicapCode, handicapCategory);
		}

		try {
			Map<String, Object> data = new HashMap<>(6);

			//盘口
			List<Map<String, Object>> handicaps = new ArrayList<>();
			for (HandicapUtil.Code code : HandicapUtil.codes()) {
				Map<String, Object> handicap = new HashMap<>(2);
				handicap.put("code", code.name());
				handicap.put("name", code.getName());
				handicaps.add(handicap);
			}
			data.put("handicaps", handicaps);

			//类别
			List<Map<String,Object>> categories = new ArrayList<>(2);
			categories.add(HandicapTool.getTypeMap(IbmTypeEnum.AGENT));
			categories.add(HandicapTool.getTypeMap(IbmTypeEnum.MEMBER));
			data.put("categories", categories);

			// 后台展示盘口详情信息
			IbmAdminHandicapItemService handicapItemService = new IbmAdminHandicapItemService();
			List<Map<String, Object>> handicapItems = handicapItemService.listShow(handicapCategory, handicapId, key);
			for (Map<String, Object> handicapItem : handicapItems) {
				//盘口类别
				IbmTypeEnum category = IbmTypeEnum
						.valueOf(handicapItem.remove("HANDICAP_CATEGORY_").toString());
				handicapItem.put("HANDICAP_CATEGORY_", category.getMsg());
				//使用数量
				String citationsNumber = handicapItemService
						.getCitations(handicapItem.get("HANDICAP_ITEM_ID_").toString(), category);
				handicapItem.put("CITATIONS_NUMBER_",citationsNumber);
			}
			data.put("handicapItems", handicapItems);

			//回显数据
			data.put("HANDICAP_CATEGORY_", handicapCategory);
			data.put("HANDICAP_CODE_", handicapCode);
			data.put("key", key);


			return new ModelAndView("/pages/com/ibm/admin/manager/handicap/item.jsp", data);

		} catch (Exception e) {
			log.error("盘口详情列表错误", e);
			return new JsonResultBeanPlus().error(e.getMessage());
		}
	}
}
