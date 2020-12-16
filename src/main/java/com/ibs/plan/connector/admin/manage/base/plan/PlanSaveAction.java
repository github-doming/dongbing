package com.ibs.plan.connector.admin.manage.base.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_plan.entity.IbspPlan;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import com.ibs.plan.module.cloud.ibsp_plan_item.service.IbspPlanItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @Description: 新增方案
 * @Author: admin1
 * @Date: 2020/6/22 11:09
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/plan/save", method = HttpConfig.Method.POST)
public class PlanSaveAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//方案名称
		String planName = StringTool.getString(dataMap, "PLAN_NAME_", "");
		//方案编号
		String planCode = StringTool.getString(dataMap, "PLAN_CODE_", "");
		//方案详情表名
		String planItemTableName = StringTool.getString(dataMap, "planTableName", "");
		//方案类型
		String planType = StringTool.getString(dataMap, "PLAN_TYPE_", "");
		//适用游戏类型
		String availableGameType = StringTool.getString(dataMap, "availableGameType", "");
		//方案组数据
		String planGroupDataInfo = StringTool.getString(dataMap, "planGroupDataInfo", "");
		//方案价值
		int planWorth = NumberTool.getInteger(dataMap, "PLAN_WORTH_", -1);
		//序号
		int sn = NumberTool.getInteger(dataMap, "SN_", -1);
		//备注
		String desc = StringTool.getString(dataMap, "DESC_", "");
		if (StringTool.isEmpty(planName, planCode, planItemTableName, planType, availableGameType, planGroupDataInfo) || planWorth == -1 || sn == -1) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		Date nowTime = new Date();
		try {
			//验证数据组数据格式
			JSONObject planGroupDataInfos = JSONObject.parseObject(planGroupDataInfo);
			List<String> gameTypes = Arrays.asList(availableGameType.split(","));
			if(dataFormatCheck(planGroupDataInfos,gameTypes)){
				bean.putEnum(CodeEnum.IBS_403_DATA_FORMAT);
				bean.putSysEnum(CodeEnum.CODE_403);
				return super.returnJson(bean);
			}
			//验证方案编码是否存在
			IbspPlanService planService = new IbspPlanService();
			String planId = planService.findId(planCode);
			if(StringTool.notEmpty(planId)){
				bean.putEnum(CodeEnum.IBS_403_EXIST);
				bean.putSysEnum(CodeEnum.CODE_403);
				return super.returnJson(bean);
			}

			IbspPlan plan = new IbspPlan();
			plan.setPlanName(planName);
			plan.setPlanCode(planCode);
			plan.setPlanItemTableName(planItemTableName.toUpperCase());
			plan.setPlanType(planType);
			plan.setAvailableGameType(availableGameType);
			plan.setPlanWorthT(planWorth);
			plan.setSn(sn);
			plan.setDesc(desc);
			plan.setState(IbsStateEnum.OPEN.name());
			plan.setCreateTime(nowTime);
			plan.setCreateTimeLong(System.currentTimeMillis());
			plan.setUpdateTimeLong(System.currentTimeMillis());
			planService.save(plan);
			//添加方案组详情数据
			new IbspPlanItemService().saveItem(new HashSet<>(gameTypes), plan.getPlanCode(), plan.getPlanItemTableName(), planGroupDataInfos, nowTime);
			bean.success();
		} catch (Exception e) {
			log.error(" 新增方案出错", e);
			throw e;
		}
		return bean;
	}

	/**
	 * 验证数据格式
	 * @param planGroupDataInfos 方案组数据
	 * @param gameTypes 适用游戏类型
	 */
	private boolean dataFormatCheck(JSONObject planGroupDataInfos,List<String> gameTypes){
		if(planGroupDataInfos.size()!=gameTypes.size()){
			return true;
		}
		for (String gameType:gameTypes){
			if(!planGroupDataInfos.containsKey(gameType)){
				return true;
			}
		}
		return false;
	}
}
