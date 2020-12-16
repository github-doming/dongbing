package com.ibm.old.v1.pc.ibm_handicap_user.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_handicap_member.t.controller.HandicapInfoController;
import com.ibm.old.v1.pc.ibm_handicap_member.t.service.IbmPcHandicapMemberTService;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description: 开启盘口
 * @date 2019年2月21日 下午3:18:41 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class OpenHandicapAction extends BaseAppAction {

	@Override public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		String handicapMemberIds = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("HANDICAP_MEMBER_ID_"), "");
		//盘口code
		String handicapCode = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("HANDICAP_CODE_"), "");
		//盘口详情id
		String handicapItemId =  BeanThreadLocal.findThreadLocal().get().find(dataMap.get("HANDICAP_ITEM_ID_"), "");
		
		if (StringTool.isEmpty(handicapMemberIds,handicapCode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			IbmHandicapTService handicapTService = new IbmHandicapTService();
			String handicapId = handicapTService.findIdByCode(handicapCode);
			if(StringTool.isEmpty(handicapId)){
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}
			Map<String, Object> data = new HashMap<>(3);
			//打开所有盘口
			HandicapInfoController controller = new HandicapInfoController();
			data.put("handicapMemberInfo", controller.execute(handicapMemberIds));
			//获取该用户所有盘口会员账号
			IbmPcHandicapMemberTService handicapMemberTService = new IbmPcHandicapMemberTService();
			List<Map<String, Object>> allAccount = handicapMemberTService.listAllAccount(appUserId, handicapId);
			data.put("allAccount", allAccount);
			data.put("handicapItemId", handicapItemId);
			jrb.setData(data);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "获取已开启的用户信息失败", e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return returnJson(jrb);
	}

}
