package com.ibm.old.v1.pc.ibm_handicap_member.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_handicap_member.t.service.IbmPcHandicapMemberTService;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Description: 当前盘口用户所有盘口会员账号信息
 * @date 2019年3月7日 上午10:27:04 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmHmAllAccountAction extends BaseAppAction{

	@Override
	public String run() throws Exception {
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
		//盘口code
		String handicapCode = BeanThreadLocal.find(dataMap.get("HANDICAP_CODE_"), "");
		if(StringTool.isEmpty(handicapCode)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		
		try {
			//获取盘口会员ID
			IbmHandicapTService handicapTService = new IbmHandicapTService();
			String handicapId = handicapTService.findIdByCode(handicapCode);
			if(StringTool.isEmpty(handicapId)){
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}
			//获取该用户所有盘口会员账号
			IbmPcHandicapMemberTService handicapMemberTService = new IbmPcHandicapMemberTService();
			List<Map<String, Object>> list = handicapMemberTService.listAllAccount(appUserId, handicapId);
			
			jrb.setData(list);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return this.returnJson(jrb);
	}

}
