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
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.service.IbspCmsUserNotifyService;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.HttpTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 首页 页面内容信息 2 min 一次刷新
 *
 * @Author: null
 * @Date: 2020-05-23 13:49
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/home/index", method = HttpConfig.Method.GET) public class HomeAction
		extends CommCoreAction {
	public HomeAction() {
		super.isTime = false;
	}
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		try {
			Map<String, Object> data = new HashMap<>(7);

			// 用户信息
			Map<String, Object> userInfo =new IbspUserService().findHomeInfo(appUserId);
			userInfo.remove("ROW_NUM");
			data.put("userInfo", userInfo);

			userInfo.put("POINT_T_", NumberTool.doubleT(getPoint()));

			// 用户系统消息
			IbspCmsNotifyService notifyService = new IbspCmsNotifyService();
			data.put("unread", new IbspCmsUserNotifyService().getUnreadMsgByUserId(appUserId));
			data.put("announce", notifyService.listTopAnnounce());
			data.put("remind", notifyService.listRemindShow(appUserId));

			// 会员盘口
			List<Map<String, Object>> memberHandicapInfos = new IbspHmUserService().listHandicapShowInfo(appUserId);
			data.put("memberHandicapInfos", memberHandicapInfos);

			IbspHmUserService hmUserService = new IbspHmUserService();
			// 用户最近打开盘口
			data.put("recentLogin", hmUserService.listRecentLogin(appUserId));

			// 用户正在托管盘口
			data.put("onHosting", hmUserService.listOnHosting(appUserId));

			// 游戏方案开启信息列表
			List<Map<String, Object>> gameInfos =	new IbspExpUserService().listGameShowInfo(appUserId);
			List<String> gameIds = new ArrayList<>(gameInfos.size());
			Map<String,Map<String,Object>> gameKeyInfo = new HashMap<>(gameInfos.size());
			gameInfos.forEach(info -> {
				String gameId = info.get("GAME_ID_").toString();
				info.remove("ROW_NUM");
				gameIds.add(gameId);

				gameKeyInfo.put(gameId,info);
			});
			Map<String, List<Map<String, Object>>> planInfo = new IbspPlanUserService().listHomeShowInfo(gameIds,appUserId);
			planInfo.forEach((gameId,info)->
					gameKeyInfo.get(gameId).put("planList",info)
			);
			data.put("gamePlan", gameKeyInfo.values());

			bean.success(data);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "页面内容信息错误", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}

	private String getPoint()  {
		JSONObject parameter = new JSONObject();
		parameter.put("userId", appUserId);
		String balance="0";
		try {
			String time = System.currentTimeMillis() + "";
			parameter.put("time", time);
			parameter.put("valiDate", Md5Tool.generate(time));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url+ "/cloud/user/api/point/repPoint";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(parameter));
			JSONObject result = JSON.parseObject(html);
			if (result.getBoolean("success")) {
				balance= result.getJSONObject("data").getString("BALANCE_T_");
			}
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "页面获取点数错误", e);
		}
		return balance;
	}
}
