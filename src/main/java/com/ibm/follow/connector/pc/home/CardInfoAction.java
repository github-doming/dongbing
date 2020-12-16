package com.ibm.follow.connector.pc.home;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.follow.servlet.cloud.ibm_sys_card_price.service.IbmSysCardPriceService;
import com.ibm.follow.servlet.cloud.ibm_user.service.IbmUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.HttpTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 续费页面信息
 * @Author: null
 * @Date: 2020-07-31 13:56
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/home/cardInfo", method = HttpConfig.Method.GET)
public class CardInfoAction extends CommCoreAction {
	public CardInfoAction() {
		super.isTime = false;
	}

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			Map<String, Object> data = new HashMap<>(6);

			// 用户信息
			IbmUserService userService = new IbmUserService();
			Map<String, Object> userInfo = userService.findHomeInfo(appUserId);

			JSONObject result = getUsersPointInfo(appUserId);
			String point = "0";
			if (result.getBoolean("success")) {
				Map<String, Object> pointInfo = (Map<String, Object>) result.get("data");
				point = pointInfo.get("BALANCE_T_").toString();
			}
			userInfo.put("POINT_T_", NumberTool.doubleT(point));
			data.put("userInfo", userInfo);

			//获取卡种价格
			IbmSysCardPriceService cardPriceService = new IbmSysCardPriceService();
			List<Map<String, Object>> cardInfo = cardPriceService.findCardInfos();
			data.put("cardInfo", cardInfo);

			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("页面内容信息错误"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	private JSONObject getUsersPointInfo(String userId){
		//校验云接口数据
		try {
			JSONObject data = new JSONObject();
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("userId", userId);
			data.put("valiDate", Md5Tool.generate(time));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/point/repPoint";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			return JSON.parseObject(html);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("页面内容信息错误"), e);
			return  new JSONObject();
		}
	}
}
