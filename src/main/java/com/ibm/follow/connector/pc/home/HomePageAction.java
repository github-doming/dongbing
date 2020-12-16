package com.ibm.follow.connector.pc.home;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import com.ibm.follow.servlet.cloud.ibm_cms_user_notify.service.IbmCmsUserNotifyService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
import com.ibm.follow.servlet.cloud.ibm_user.service.IbmUserService;
import com.ibm.follow.servlet.cloud.vr_fm_game_set.service.VrFmGameSetService;
import com.ibm.follow.servlet.cloud.vr_rank_daily.service.VrRankDailyService;
import com.ibm.follow.servlet.cloud.vr_user_follow_member.service.VrUserFollowMemberService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.HttpTool;

import java.util.*;

/**
 * @Description: 首页，页面内容信息 首页自动刷新时调用
 * @Author: Dongming
 * @Date: 2019-08-29 18:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/home/page", method = HttpConfig.Method.GET)
public class HomePageAction
		extends CommCoreAction {
	public HomePageAction() {
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
			IbmCmsNotifyService notifyService = new IbmCmsNotifyService();

			data.put("messageCount", new IbmCmsUserNotifyService().getUnreadMsgByUserId(appUserId));
			data.put("announceInfo", notifyService.listAllAnnounce(null, null, null, null, null, 1, 4).getList());
			data.put("remind", notifyService.findRemindByUserId(appUserId));

			// 用户正在投注会员盘口
			data.put("onHostingMh", new IbmHmUserService().listOnHostingInfo(appUserId));

			// 用户正在投注代理盘口
			data.put("onHostingAh", new IbmHaUserService().listOnHostingInfo(appUserId));

			VrFmGameSetService gameSetService = new VrFmGameSetService();
			//获取会员正在跟投的会员
			List<Map<String, Object>> vrMemberInfos = new VrUserFollowMemberService().listVrMemberInfos(appUserId);
			List<String> onlineInfo = new ArrayList<>();
			for (Map<String, Object> vrMemberInfo : vrMemberInfos) {
				if (!gameSetService.findGameInfo(appUserId, vrMemberInfo.get("VR_MEMBER_ID_"), IbmTypeEnum.TRUE)) {
					onlineInfo.add(vrMemberInfo.get("VR_MEMBER_ID_").toString());
				}
			}
			Date time = new Date();
			Date startTime = DateTool.getBetTime(DateTool.getDate(DateTool.getYesterdayStart()));
			Date endTime=DateTool.getBetTime(DateTool.getDate(time));
			//牛人收益排行榜
			VrRankDailyService rankDailyService = new VrRankDailyService();
			List<Map<String, Object>> rankList = rankDailyService.getRank(startTime, endTime);
			for (Map<String, Object> map : rankList) {
				map.put("WIN_RATE_", NumberTool.doubleT(map.remove("WIN_RATE_T_")));
				if (onlineInfo.contains(map.get("VR_MEMBER_ID_").toString())) {
					map.put("FOLLOW_STATE_", "TRUE");
				} else {
					map.put("FOLLOW_STATE_", "FALSE");
				}
			}
			data.put("rank", rankList);

			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("页面内容信息错误"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	private JSONObject getUsersPointInfo(String userId) {
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
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("页面内容信息错误"), e);
			return new JSONObject();
		}

	}
}
