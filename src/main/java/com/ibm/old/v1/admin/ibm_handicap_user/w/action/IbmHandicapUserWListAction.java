package com.ibm.old.v1.admin.ibm_handicap_user.w.action;

import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.root.common.action.BaseAction;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Description: 查询所有盘口用户
 * @date 2019年4月9日15:59:33
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmHandicapUserWListAction extends BaseAction{

	@Override
	public String execute() throws Exception {
		// 排序
		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);

		// 分页
		Integer pageNo = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageSizeName),
				10);
		String handicapName = BeanThreadLocal.findThreadLocal().get().find(request.getParameter("HANDICAP_NAME_"), "");
		
		IbmHandicapUserTService handicapTService = new IbmHandicapUserTService();
		PageCoreBean pageCore = handicapTService.find(handicapName,sortFieldName, sortOrderName, pageNo, pageSize);

		List<Map<String, Object>> listMap = pageCore.getList();

		IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
		for(Map<String,Object> map : listMap){
			if(StringTool.notEmpty(map.get("ONLINE_MEMBERS_IDS_"))){
				String[] onlineMemberIds = map.get("ONLINE_MEMBERS_IDS_").toString().split(",");
				String onlineMembers = handicapMemberTService.findAccountByIds(onlineMemberIds);
				map.remove("ONLINE_MEMBERS_IDS_");
				map.put("ONLINE_MEMBERS",onlineMembers);
			}
		}

		request.setAttribute("cPage", pageCore);
		request.setAttribute("list", listMap);
		request.setAttribute("handicapName", handicapName);

		return CommViewEnum.Default.toString();
	}

}
