package com.ibm.follow.connector.admin.manage.user.vruser;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.util.BaseGameUtil;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_member_game_set.service.VrMemberGameSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 虚拟用户列表
 * @Author: admin1
 * @Date: 2020/7/16 10:57
 * @Version: v1.0
 */

@ActionMapping(value = "/ibm/admin/manage/vruser/list")
public class VrUserListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String userName = StringTool.getString(dataMap.get("userName"), "");
		String memberName = StringTool.getString(dataMap.get("memberName"), "");
		String handicapCode = StringTool.getString(dataMap.get("handicapCode"), "");


		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		Map<String, Object> data = new HashMap<>(3);

		try {
			VrMemberService memberService = new VrMemberService();
			VrMemberGameSetService gameSetService = new VrMemberGameSetService();
			PageCoreBean<Map<String, Object>> basePage = memberService.listVrMember(userName, memberName, handicapCode, pageIndex, pageSize);

			List<Map<String, Object>> memberList = basePage.getList();
			for (Map<String, Object> map : memberList) {
				String memberId = map.get("MEMBER_ID_").toString();
				String gameCodes = gameSetService.getMemberBetGame(memberId);
				if(StringTool.notEmpty(gameCodes)){
					StringBuilder gameName = new StringBuilder();
					for (String gameCode : gameCodes.split(",")){
						gameName.append(BaseGameUtil.value(gameCode).getName()).append(",");
					}
					gameName.deleteCharAt(gameName.lastIndexOf(","));
					map.put("gameName", gameName.toString());
				}


			}
			List<String> memberAllUsable = new IbmHandicapService().findHandicapCode(IbmTypeEnum.MEMBER);
			data.put("memberAllUsable", memberAllUsable);
			data.put("rows", memberList);
			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());
		} catch (Exception e) {
			log.error("用户列表错误", e);
			data.clear();
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}
}
