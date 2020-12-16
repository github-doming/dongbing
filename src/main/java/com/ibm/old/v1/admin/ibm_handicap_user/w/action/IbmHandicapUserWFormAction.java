package com.ibm.old.v1.admin.ibm_handicap_user.w.action;

import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.entity.IbmHandicapUserT;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;

/**
 * 
 * 
 * @Description: 查询角色信息
 * @date 2019年2月20日 上午11:08:17 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmHandicapUserWFormAction extends BaseAppAction{
	@Override
	public String run() throws Exception {
		String id = request.getParameter("id");
		if (id != null) {
			IbmHandicapUserTService handicapUserTService = new IbmHandicapUserTService();
			IbmHandicapUserT handicapUser = handicapUserTService.find(id);

			request.setAttribute("s", handicapUser);
			request.setAttribute("id", id);
		}
		return CommViewEnum.Default.toString();
	}

}
