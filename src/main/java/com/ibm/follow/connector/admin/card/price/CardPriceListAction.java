package com.ibm.follow.connector.admin.card.price;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.connector.admin.card.CardTools;
import com.ibm.follow.servlet.cloud.ibm_sys_card_price.service.IbmSysCardPriceService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpConfig;

import java.util.List;
import java.util.Map;

/**
 * @Description: 点数价格列表
 * @Author: null
 * @Date: 2020-07-31 17:04
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/points/list" ,method = HttpConfig.Method.GET)
public class CardPriceListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			// 获取用户权限信息
			Map<String, Object> userRole = new AuthorityService().findUserRole(adminUserId);
			int permGrade = NumberTool.getInteger(userRole, "PERMISSION_GRADE_", 200);
			String cardAdminType = CardTools.selectUserGroup(permGrade);

			if (IbmTypeEnum.FALSE.name().equals(cardAdminType)) {
				bean.putEnum(IbmCodeEnum.IBM_403_PERMISSION);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean.toJsonString();
			}
			IbmSysCardPriceService cardPriceService=new IbmSysCardPriceService();
			List<Map<String, Object>> pointsInfos=cardPriceService.findAllInfo();

			bean.success(pointsInfos);
		} catch (Exception e) {
			log.error("获取点数价格列表出错", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
