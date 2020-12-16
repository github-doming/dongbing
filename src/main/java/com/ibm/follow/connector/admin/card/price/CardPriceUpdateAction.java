package com.ibm.follow.connector.admin.card.price;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.connector.admin.card.CardTools;
import com.ibm.follow.servlet.cloud.ibm_sys_card_price.entity.IbmSysCardPrice;
import com.ibm.follow.servlet.cloud.ibm_sys_card_price.service.IbmSysCardPriceService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 修改点数信息
 * @Author: null
 * @Date: 2020-07-31 17:25
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/points/update",method = HttpConfig.Method.POST)
public class CardPriceUpdateAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//暂时只使用点数和时长
		String cardPriceId = StringTool.getString(dataMap, "cardPriceId", "");
		String priceName = StringTool.getString(dataMap, "priceName", "");
		String desc = StringTool.getString(dataMap, "desc", "");
		int sn = NumberTool.getInteger(dataMap, "sn", 0);
		int useTime = NumberTool.getInteger(dataMap, "useTime", 0);
		int currentPrice = NumberTool.getInteger(dataMap, "currentPrice", 0);
		String state= StringTool.getString(dataMap, "state", "");
		if (StringTool.isEmpty(cardPriceId,state)||useTime <= 0 || currentPrice <= 0) {
			bean.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
			bean.putSysEnum(IbmCodeEnum.CODE_403);
			return bean.toJsonString();
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
			IbmSysCardPrice cardPrice=cardPriceService.find(cardPriceId);
			if(cardPrice==null){
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean.toJsonString();
			}
			cardPrice.setPriceName(priceName);
			cardPrice.setUseTime(useTime);
			cardPrice.setOriginalPrice(currentPrice);
			cardPrice.setCurrentPrice(currentPrice);
			cardPrice.setSn(sn);
			cardPrice.setUseTime(useTime);
			cardPrice.setCurrentPrice(currentPrice);
			cardPrice.setUpdateTime(new Date());
			cardPrice.setUpdateTimeLong(System.currentTimeMillis());
			cardPrice.setState(state);
			cardPrice.setDesc(desc);
			cardPriceService.update(cardPrice);

			bean.success();
		} catch (Exception e) {
			log.error("修改点数信息出错", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
