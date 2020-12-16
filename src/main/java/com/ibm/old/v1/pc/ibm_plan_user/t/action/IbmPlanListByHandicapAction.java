package com.ibm.old.v1.pc.ibm_plan_user.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_plan_user.t.service.IbmPcPlanUserTService;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * @author wck
 * @Description: 获取盘口所有方案
 * @date 2019年3月11日 上午10:29:51
 * @Email: 810160078@qq.com
 * @Version v1.0
 */
public class IbmPlanListByHandicapAction extends BaseAppAction {

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
			return returnJson(jrb);
		}
		//盘口CODE
		String handicapCode = BeanThreadLocal.find(dataMap.get("HANDICAP_CODE_"), "");
		if (StringTool.isEmpty(handicapCode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			IbmPcPlanUserTService ibmPlanUserTService = new IbmPcPlanUserTService();
			//查询盘口内的所有方案
			List<Map<String, Object>> data = ibmPlanUserTService.listByHandicap(handicapCode, appUserId);

			jrb.setData(data);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "方案重置失败", e);
			throw e;
		}
		return returnJson(jrb);
	}

}
