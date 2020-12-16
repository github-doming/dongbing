package com.ibm.follow.connector.admin.card.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.connector.admin.card.CardTools;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.service.IbmCardAdminPriceService;
import com.ibm.follow.servlet.cloud.ibm_card_tree.service.IbmCardTreeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * 修改充值卡管理员价格信息
 *
 * @Author: Dongming
 * @Date: 2020-04-11 15:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/agent/editPrice")
public class CardAgentPriceEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		String subAgentId = StringTool.getString(dataMap.get("subAgentId"), "");
		String priceInfoStr = StringTool.getString(dataMap.get("priceInfos"), "");
		if (StringTool.isEmpty(priceInfoStr)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		JSONArray priceInfos = JSON.parseArray(priceInfoStr);
		if (ContainerTool.isEmpty(priceInfos)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		if (StringTool.isEmpty(subAgentId)) {
			subAgentId = adminUserId;
		}
		try {
			IbmCardAdminService cardAdminService = new IbmCardAdminService();
			if (cardAdminService.hasEditPermission(bean, subAgentId, adminUserId)) {
				return bean;
			}

			// 获取用户权限信息
			Map<String, Object> userRole = new AuthorityService().findUserRole(adminUserId);
			int permGrade = NumberTool.getInteger(userRole, "PERMISSION_GRADE_", 200);
			String cardAdminType = CardTools.selectUserGroup(permGrade);
			if (IbmTypeEnum.CARD_ADMIN.name().equals(cardAdminType)) {
				adminUserId = IbmTypeEnum.ADMIN.name();
			}

			Map<String, Object> data = (Map<String, Object>) bean.getData();
			Date nowTime = new Date();

			IbmCardAdminPriceService cardAdminPriceService = new IbmCardAdminPriceService();
			IbmCardTreeService cardTreeService = new IbmCardTreeService();
			for (int i = 0; i < priceInfos.size(); i++) {
				JSONObject priceInfo = priceInfos.getJSONObject(i);

				// 如果是管理员账号修改 自己的价格 , 需要修改分类树价格
				if ((boolean) data.get("isAdmin") && (boolean) data.get("isSelf")) {
					cardTreeService.updateCardTree(priceInfo.getString("cardTreeId"),
							NumberTool.longValueT(priceInfo.get("cardTreePrice")), null, 0, null, 0, null, nowTime);
				}

				if ((boolean) data.get("isSelf")) {
					// 对应修改给直属下级定的价格
					cardAdminPriceService.updateCardPrice(adminUserId, "", priceInfo.getString("cardTreeId"),
							NumberTool.longValueT(priceInfo.get("cardTreePrice")), nowTime);

				} else {
					// 修改指定下级的价格
					cardAdminPriceService.updateCardPrice(adminUserId, subAgentId, priceInfo.getString("cardTreeId"),
							NumberTool.longValueT(priceInfo.get("cardTreePrice")), nowTime);


				}
			}


			bean.success();
		} catch (Exception e) {
			log.error("修改充值卡管理员价格信息出错", e);
			throw e;
		}
		return bean;

	}

}
