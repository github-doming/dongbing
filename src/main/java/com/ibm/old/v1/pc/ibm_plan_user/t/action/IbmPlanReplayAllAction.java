package com.ibm.old.v1.pc.ibm_plan_user.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.service.IbmLogPlanUserTService;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_plan_user.t.service.IbmPcPlanUserSetService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Description: 重置所有方案
 * @date 2019年2月28日 下午5:30:46 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmPlanReplayAllAction extends BaseAppAction {

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
			return returnJson(jrb);
		}
		//方案ID
		String planIdArray = BeanThreadLocal.find(dataMap.get("PLAN_ID_"), "");
		
		if(StringTool.isEmpty(planIdArray)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(jrb);
		}
		try {
			List<String> planIdList = Arrays.asList(planIdArray.split(","));
			
			//获取方案详情表名列表
			IbmPlanTService ibmPlanTService = new IbmPlanTService();
			Map<Object,Object> planMap= ibmPlanTService.findTableById(planIdList);
			if(ContainerTool.isEmpty(planMap)){
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}
			//获取需要重置的方案详情表id
			IbmPcPlanUserSetService planUserSetService = new IbmPcPlanUserSetService();
			List<String> planItemTableIds =planUserSetService.resetPlanId(planMap,appUserId);
			if(ContainerTool.isEmpty(planItemTableIds)){
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}
			//重置方案投注项状态
			IbmExecPlanGroupTService execPlanGroupTService = new IbmExecPlanGroupTService();
			execPlanGroupTService.replayPlan(planItemTableIds);

			IbmLogPlanUserTService ibmLogPlanUserTService=new IbmLogPlanUserTService();
			//写入日志
			ibmLogPlanUserTService.save(planMap,appUserId);

			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "方案重置失败", e);
			throw e;
		}
		return returnJson(jrb);
	}

}
