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
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 删除点数信息
 * @Author: null
 * @Date: 2020-08-01 15:10
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/points/del",method = HttpConfig.Method.POST)
public class CardPriceDelAction extends CommAdminAction {
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
		if (StringTool.isEmpty(cardPriceId)) {
			bean.putEnum(CodeEnum.IBS_403_DATA_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean.toJsonString();
		}
		try {
			IbspSysCardPriceService cardPriceService=new IbspSysCardPriceService();
			IbspSysCardPrice cardPrice=cardPriceService.find(cardPriceId);
			if(cardPrice==null){
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean.toJsonString();
			}
			cardPrice.setUpdateTime(new Date());
			cardPrice.setUpdateTimeLong(System.currentTimeMillis());
			cardPrice.setState(StateEnum.DEL.name());
			cardPriceService.update(cardPrice);

			bean.success();
		} catch (Exception e) {
			log.error("删除点数价格信息出错", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
