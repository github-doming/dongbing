package com.ibs.plan.connector.app.main;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import com.ibs.plan.module.cloud.ibsp_sys_manage_time.service.IbspSysManageTimeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 加载主题框架
 * @Author: null
 * @Date: 2019-11-23 15:09
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/main/home", method = HttpConfig.Method.GET)
public class HomeMainAction extends CommCoreAction {
	public HomeMainAction() {
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
			Map<String, Object> data = new HashMap<>(4);
			//会员盘口信息
			findMemberInfo(data);
			// 用户信息
			data.put("endTimeLong", new IbspSysManageTimeService().getEndLongTime(appUserId));
			data.put("cmsInfo",new IbspCmsNotifyService().listTopAnnounce());

			IbspPlanUserService planUserService = new IbspPlanUserService();
			List<String> gameIds = planUserService.listGameIds(appUserId);
			List<Map<String,Object>> gameInfos = new ArrayList<>();
			Map<String,Object> map ;
			GameUtil.Code gameCode;
			for (String gameId :gameIds){
				map = new HashMap<>(2);
				gameCode = GameUtil.code(gameId);
				map.put("gameName",gameCode.getName());
				map.put("gameCode",gameCode.name());
				gameInfos.add(map);
			}
			data.put("planInfos", planUserService.findPlanCodeByUserId(appUserId));
			data.put("gameInfos", gameInfos);
			bean.success();
			bean.setData(data);
		} catch (Exception e) {
			log.error("加载主题框架错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	private void findMemberInfo(Map<String, Object> data) throws SQLException {
		IbspHmUserService hmUserService = new IbspHmUserService();
		IbspHmInfoService hmInfoService = new IbspHmInfoService();
		IbspHandicapMemberService hmService = new IbspHandicapMemberService();

		List<Map<String, Object>> memberHandicapInfos = hmUserService.listHandicapShowInfo(appUserId);


		List<Map<String, Object>> offlineInfo;
		List<Map<String, Object>> onlineInfo;
		for (Map<String, Object> map : memberHandicapInfos) {
			map.remove("ROW_NUM");
			String handicapCode = map.get("HANDICAP_CODE_").toString();
			String handicapId = HandicapUtil.id(handicapCode, IbsTypeEnum.MEMBER);
			//获取在线会员主键数组
			String onlineMembersIds = hmUserService.findOnlineMembersId(appUserId, handicapId);
			if (StringTool.notEmpty(onlineMembersIds)) {
				//已托管会员
				onlineInfo =  hmInfoService.listOnlineInfo(onlineMembersIds.split(","));
				offlineInfo = hmService.listOfflineInfo(appUserId, handicapId,
						ContainerTool.getValSet4Key(onlineInfo, "HANDICAP_MEMBER_ID_"));
			} else {
				onlineInfo=new ArrayList<>();
				offlineInfo = hmService.listOfflineInfo(appUserId, handicapId, null);
			}
			map.put("onHostingInfo", onlineInfo);
			map.put("offHostingInfo", offlineInfo);
		}

		data.put("memberInfo", memberHandicapInfos);

	}


}
