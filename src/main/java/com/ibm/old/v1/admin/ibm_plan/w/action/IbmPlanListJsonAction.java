package com.ibm.old.v1.admin.ibm_plan.w.action;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;

import java.util.List;
import java.util.Map;
/**
 * @Description: 查询所有方案
 * @Author: zjj
 * @Date: 2019-08-13 13:43
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmPlanListJsonAction extends BaseAppAction {

	@Override public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		try {
			IbmPlanTService planTService = new IbmPlanTService();
			List<Map<String, Object>> map = planTService.findAllPlan();

			jrb.setData(map);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN+"获取方案失败",e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
		}
		return this.returnJson(jrb);
	}
}
