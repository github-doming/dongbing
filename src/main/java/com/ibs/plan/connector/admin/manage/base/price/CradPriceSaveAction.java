package com.ibs.plan.connector.admin.manage.base.price;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_sys_card_price.entity.IbspSysCardPrice;
import com.ibs.plan.module.cloud.ibsp_sys_card_price.service.IbspSysCardPriceService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 新增点数价格
 * @Author: null
 * @Date: 2020-08-01 15:05
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/points/save", method = HttpConfig.Method.POST)
public class CradPriceSaveAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//暂时只使用点数和时长
		String priceName = StringTool.getString(dataMap, "priceName", "");
		String desc = StringTool.getString(dataMap, "desc", "");
		int sn = NumberTool.getInteger(dataMap, "sn", 0);
		int useTime = NumberTool.getInteger(dataMap, "useTime", 0);
		int currentPrice = NumberTool.getInteger(dataMap, "currentPrice", 0);
		if (StringTool.isEmpty(priceName, sn) || useTime <= 0 || currentPrice <= 0) {
			bean.putEnum(CodeEnum.IBS_403_DATA_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean.toJsonString();
		}
		try {

			IbspSysCardPriceService cardPriceService = new IbspSysCardPriceService();
			String cardPriceId = cardPriceService.findCrad(useTime, currentPrice);
			if (StringTool.notEmpty(cardPriceId)) {
				bean.putEnum(CodeEnum.IBS_403_EXIST);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean.toJsonString();
			}
			Date nowTime = new Date();
			IbspSysCardPrice cardPrice = new IbspSysCardPrice();
			cardPrice.setPriceName(priceName);
			cardPrice.setUseTime(useTime);
			cardPrice.setOriginalPrice(currentPrice);
			cardPrice.setCurrentPrice(currentPrice);
			cardPrice.setDiscount(0);
			cardPrice.setSn(sn);
			cardPrice.setCreateUser(appUser.getNickname());
			cardPrice.setCreateTime(nowTime);
			cardPrice.setCreateTimeLong(nowTime.getTime());
			cardPrice.setUpdateTime(nowTime);
			cardPrice.setUpdateTimeLong(nowTime.getTime());
			cardPrice.setState(StateEnum.OPEN.name());
			cardPrice.setDesc(desc);
			cardPriceService.save(cardPrice);

			bean.success();
		} catch (Exception e) {
			log.error("新增点数价格信息出错", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
