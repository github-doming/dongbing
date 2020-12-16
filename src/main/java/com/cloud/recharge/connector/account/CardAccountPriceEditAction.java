package com.cloud.recharge.connector.account;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_operate_log.entity.CardOperateLog;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.recharge.card_tree.service.CardTreeService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * 修改充值卡管理员价格信息
 *
 * @Author: Dongming
 * @Date: 2020-06-18 16:12
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/account/editPrice", method = HttpConfig.Method.POST)
public class CardAccountPriceEditAction
		extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String subUserId = StringTool.getString(dataMap.get("subUserId"), "");
		String priceInfoStr = StringTool.getString(dataMap.get("priceInfos"), "");
		if (StringTool.isEmpty(priceInfoStr)) {
			return bean.put401Data();
		}
		JSONArray priceInfos = JSON.parseArray(priceInfoStr);
		if (ContainerTool.isEmpty(priceInfos)) {
			return bean.put401Data();
		}
		if (StringTool.isEmpty(subUserId)) {
			subUserId = userId;
		}

		try {
			CardAdminService cardAdminService = new CardAdminService();
			if (cardAdminService.hasEditPermission(bean, subUserId, userId)) {
				return bean;
			}
			Map<String, Object> data = (Map<String, Object>) bean.getData();
			Date nowTime = new Date();

			CardAdminPriceService cardAdminPriceService = new CardAdminPriceService();
			CardTreeService cardTreeService = new CardTreeService();
			boolean isAdmin = (boolean) data.get("isAdmin"), isSelf = (boolean) data.get("isSelf");
			if (isAdmin) {
				userId = CardTool.Type.ADMIN.name();
			}
			Map<String,Object> cardTreeInfo = cardTreeService.allCardTreeInfo();
			StringBuilder sb = new StringBuilder();


			String cardTreeId,cardTreePrice;
			JSONObject priceInfo;
			for (int i = 0; i < priceInfos.size(); i++) {
				priceInfo = priceInfos.getJSONObject(i);
				cardTreeId = priceInfo.getString("cardTreeId");
				cardTreePrice = priceInfo.getString("cardTreePrice");
				// 如果是管理员账号修改 自己的价格 , 需要修改分类树价格
				if (isAdmin && isSelf) {
					cardTreeService.updateCardTree(cardTreeId, NumberTool.longValueT(cardTreePrice), null,
							0, null, 0, null, null, nowTime);
				} else if (isSelf) {
					// 对应修改给自己的默认定的价格
					cardAdminPriceService.updateCardPrice(userId, StateEnum.DEF.name(), cardTreeId,
									NumberTool.longValueT(cardTreePrice), nowTime);
				} else {
					// 修改指定下级的价格
					cardAdminPriceService.updateCardPrice(userId, subUserId, cardTreeId,
							NumberTool.longValueT(cardTreePrice), nowTime);

				}
				sb.append("卡:").append(cardTreeInfo.get(cardTreeId)).append(",价格：").append(cardTreePrice).append(",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));

			Map<String, Object> subAdminInfo = (Map<String, Object>) data.get("subInfo");

			saveLog(nowTime,subAdminInfo.get("USER_NAME_"),sb.toString());
			bean.success();
		} catch (Exception e) {
			log.error("修改充值卡管理员价格信息出错", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	private void saveLog(Date nowTime,Object subName,String msg) throws Exception {
		String format ="修改用户{%s}卡种价格信息:{%s}";
		CardOperateLog log = new CardOperateLog();
		log.setUserId(user.getAppUserId());
		log.setUserName(user.getUserName());
		log.setUserPath(user.getUserPath());
		log.setOpertType("UPDATE");
		log.setIp(findServletIp());
		log.setOpertContent(String.format(format,subName,msg));
		log.setCreateTime(nowTime);
		log.setCreateTimeLong(System.currentTimeMillis());
		log.setUpdateTime(nowTime);
		log.setUpdateTimeLong(System.currentTimeMillis());
		log.setState(StateEnum.OPEN.name());
		new CardOperateLogService().save(log);
	}


}
