package com.ibs.plan.connector.admin.manage.base.price;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_sys_card_price.entity.IbspSysCardPrice;
import com.ibs.plan.module.cloud.ibsp_sys_card_price.service.IbspSysCardPriceService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-08-01 15:13
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/points/update", method = HttpConfig.Method.POST)
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
		String state = StringTool.getString(dataMap, "state", "");
		if (StringTool.isEmpty(cardPriceId, state) || useTime <= 0 || currentPrice <= 0) {
			bean.putEnum(CodeEnum.IBS_403_DATA_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean.toJsonString();
		}
		try {
			IbspSysCardPriceService cardPriceService = new IbspSysCardPriceService();
			IbspSysCardPrice cardPrice = cardPriceService.find(cardPriceId);
			if (cardPrice == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
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
