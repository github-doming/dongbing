package com.ibs.plan.connector.pc.main;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.plan.module.cloud.ibsp_sys_card_price.service.IbspSysCardPriceService;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
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
 * @Date: 2020-07-31 18:11
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/home/cardInfo", method = HttpConfig.Method.GET)
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
			return threadJrb;
		}
		try {
			Map<String, Object> data = new HashMap<>(6);

			// 用户信息
			Map<String, Object> userInfo =new IbspUserService().findHomeInfo(appUserId);
			userInfo.remove("ROW_NUM");
			data.put("userInfo", userInfo);

			JSONObject result = getUsersPointInfo();
			String point = "0";
			if (result.getBoolean("success")) {
				Map<String, Object> pointInfo = (Map<String, Object>) result.get("data");
				point = pointInfo.get("BALANCE_T_").toString();
			}
			userInfo.put("POINT_T_", NumberTool.doubleT(point));
			data.put("userInfo", userInfo);

			//获取卡种价格
			IbspSysCardPriceService cardPriceService=new IbspSysCardPriceService();
			List<Map<String, Object>> cardInfo=cardPriceService.findCardInfos();
			data.put("cardInfo", cardInfo);

			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN.concat("页面内容信息错误"), e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
	private JSONObject getUsersPointInfo(){
		//校验云接口数据
		try {
			JSONObject data = new JSONObject();
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("userId", appUserId);
			data.put("valiDate", Md5Tool.generate(time));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/point/repPoint";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			return JSON.parseObject(html);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN.concat("页面内容信息错误"), e);
			return new JSONObject();
		}
	}
}
