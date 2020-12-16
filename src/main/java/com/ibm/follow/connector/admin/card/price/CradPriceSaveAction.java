package com.ibm.follow.connector.admin.card.price;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
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
 * @Description: 新增点数价格信息
 * @Author: null
 * @Date: 2020-07-31 17:15
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/points/save" ,method = HttpConfig.Method.POST)
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
		if (StringTool.isEmpty(priceName,sn)||useTime <= 0 || currentPrice <= 0) {
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
			String cardPriceId=cardPriceService.findCrad(useTime,currentPrice);
			if(StringTool.notEmpty(cardPriceId)){
				bean.putEnum(IbmCodeEnum.IBM_403_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean.toJsonString();
			}
			Date nowTime=new Date();
			IbmSysCardPrice cardPrice=new IbmSysCardPrice();
			cardPrice.setPriceName(priceName);
			cardPrice.setUseTime(useTime);
			cardPrice.setOriginalPrice(currentPrice);
			cardPrice.setCurrentPrice(currentPrice);
			cardPrice.setDiscount(0);
			cardPrice.setSn(sn);
			cardPrice.setCreateUser(adminUser.getUserName());
			cardPrice.setCreateTime(nowTime);
			cardPrice.setCreateTimeLong(nowTime.getTime());
			cardPrice.setUpdateTime(nowTime);
			cardPrice.setUpdateTimeLong(nowTime.getTime());
			cardPrice.setState(IbmStateEnum.OPEN.name());
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
