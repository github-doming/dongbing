package com.ibm.old.v1.admin.ibm_handicap_user.w.action;

import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.entity.IbmHandicapUserT;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

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
public class IbmHandicapUserWSaveAction extends BaseAppAction{
	@Override
	public String run() throws Exception {
		Date nowTime = new Date();
		IbmHandicapUserTService service = new IbmHandicapUserTService();
		IbmHandicapUserT entity = new IbmHandicapUserT();
		//盘口用户ID
		String id = request.getParameter("IBM_HANDICAP_USER_ID_");
		//盘口ID
		String handicapId = request.getParameter("IBM_HANDICAP_ID_");
		IbmHandicapTService handicapTService = new IbmHandicapTService();
		Map<String,Object> handicapMap = handicapTService.findNameAndCode(handicapId);

		//盘口名称
		String handicapName = handicapMap.get("HANDICAP_NAME_").toString();
		//盘口CODE
		String handicapCode = handicapMap.get("HANDICAP_CODE_").toString();
		//盘口CODE
		String appUserId = request.getParameter("APP_USER_ID_");
		String onlineNumberMax = request.getParameter("ONLINE_NUMBER_MAX_");
		entity.setIbmHandicapUserId(id);
		entity.setHandicapName(handicapName);
		entity.setHandicapId(handicapId);
		entity.setAppUserId(appUserId);
		entity.setHandicapCode(handicapCode);
		entity.setOnlineNumberMax(Integer.parseInt(onlineNumberMax));
		entity.setState("OPEN");
		entity.setUpdateTime(nowTime);
		entity.setUpdateTimeLong(nowTime.getTime());
		if (StringTool.isEmpty(id)) {
			entity.setOnlineMembersCount(0);
			entity.setFrequency(0);
			entity.setCreateTime(nowTime);
			entity.setCreateTimeLong(nowTime.getTime());
			service.save(entity);
		}else{
			IbmHandicapUserT ibmHandicapUser = service.find(id);
			entity.setCreateTime(ibmHandicapUser.getCreateTime());
			entity.setCreateTimeLong(ibmHandicapUser.getCreateTimeLong());
			entity.setOnlineMembersCount(ibmHandicapUser.getOnlineMembersCount());
			entity.setOnlineMembersIds(ibmHandicapUser.getOnlineMembersIds());
			entity.setFrequency(ibmHandicapUser.getFrequency());
			entity.setDesc(this.getClass().getName().concat("保存盘口信息"));
			service.update(entity);

		}
		return CommViewEnum.Default.toString();
	}

}
