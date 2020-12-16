package com.ibm.old.v1.admin.ibm_plan_user.w.action;

import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_plan_user.t.entity.IbmPlanUserT;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * 
 * 
 * @Description: 保存盘口信息
 * @date 2019年2月22日 上午10:45:47 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmPlanUserWSaveAction extends BaseAppAction{
	@Override
	public String run() throws Exception {
		Date nowTime = new Date();
		IbmPlanUserTService service = new IbmPlanUserTService();
		IbmPlanUserT entity = new IbmPlanUserT();
		//盘口用户ID
		String id = request.getParameter("IBM_HANDICAP_USER_ID_");
		//盘口ID
		String handicapId = request.getParameter("IBM_HANDICAP_ID_");
		//盘口CODE
		String appUserId = request.getParameter("APP_USER_ID_");
		entity.setIbmPlanUserId(id);
		entity.setPlanId(handicapId);
		entity.setAppUserId(appUserId);
		entity.setState("OPEN");
		entity.setUpdateTime(nowTime);
		entity.setUpdateTimeLong(nowTime.getTime());
		if (StringTool.isEmpty(id)) {
			entity.setCreateTime(nowTime);
			entity.setCreateTimeLong(nowTime.getTime());
			service.save(entity);
		}else{
			IbmPlanUserT ibmPlanUser = service.find(id);
			entity.setCreateTime(ibmPlanUser.getCreateTime());
			entity.setCreateTimeLong(ibmPlanUser.getCreateTimeLong());
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}

}
