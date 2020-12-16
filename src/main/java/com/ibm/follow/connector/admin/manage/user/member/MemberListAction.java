package com.ibm.follow.connector.admin.manage.user.member;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 获取会员列表信息
 * @Author: null
 * @Date: 2020-03-19 13:42
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/member/list") public class MemberListAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//用户名
		String userName = StringTool.getString(dataMap.get("userName"), "");
		//会员名
		String memberAccount = StringTool.getString(dataMap.get("memberAccount"), "");
		//盘口
		String handicapCode = StringTool.getString(dataMap.get("handicapCode"), "");
		//在线状态
		String onlineState = StringTool.getString(dataMap.get("onlineState"), "");

		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);

		Map<String, Object> data = new HashMap<>(3);
		try {

			IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();
			//获取所有会员信息
			IbmHmInfoService hmInfoService = new IbmHmInfoService();
			PageCoreBean<Map<String, Object>> basePage = hmInfoService
					.findShow(userName, memberAccount, handicapCode, onlineState, pageIndex, pageSize);
			List<Map<String, Object>> memberInfos = basePage.getList();
			for (Map<String, Object> memberInfo : memberInfos) {
				String handicapMemberId = memberInfo.get("HANDICAP_MEMBER_ID_").toString();

				StringBuilder availableGame = new StringBuilder();
				List<Map<String, Object>> gameInfos = hmGameSetService.listGameInfo(handicapMemberId);
				for (Map<String, Object> gameInfo : gameInfos) {
					availableGame.append(gameInfo.get("GAME_NAME_")).append(",");
				}
				if (availableGame.length()>1){
					availableGame.substring(0,availableGame.length()-1);
				}

				memberInfo.put("availableGame", availableGame.toString());
				//登录状态
				if (IbmStateEnum.LOGIN.name().equals(memberInfo.get("STATE_"))) {
					memberInfo.put("STATE_", "在线");
				} else {
					memberInfo.put("STATE_", "离线");
				}
			}
			data.put("rows", memberInfos);

			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());
		} catch (Exception e) {
			log.error("获取会员列表错误", e);
			data.clear();
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return new JSONObject(data).toString();
	}
}
