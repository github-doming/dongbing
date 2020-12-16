package com.ibs.plan.connector.admin.manage.base.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_handicap.entity.IbspHandicap;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 修改盘口信息
 * @Author: null
 * @Date: 2020-04-16 17:57
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/handicap/edit",method = HttpConfig.Method.POST)
public class HandicapEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口id
		String handicapId = StringTool.getString(dataMap, "handicapId", "");
		//盘口类型
		String handicapType = StringTool.getString(dataMap, "handicapType", "");
		//盘口状态
		String state = StringTool.getString(dataMap, "state", "");
		//盘口价值
		double handicapWorth = NumberTool.getDouble(dataMap.get("handicapWorth"), 0);

		try {
			IbspHandicapService handicapService = new IbspHandicapService();
			IbspHandicap handicap = handicapService.find(handicapId);
			if (handicap == null) {
				bean.putEnum(CodeEnum.IBS_404_HANDICAP);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			if (!handicap.getState().equals(state)) {
				handicap.setState(state);
				//修改盘口游戏状态
				new IbspHandicapGameService().updateByHandicapId(handicap.getIbspHandicapId(), state, appUserId);
			}
			handicap.setHandicapType(handicapType);
			handicap.setHandicapWorthT(NumberTool.intValueT(handicapWorth));
			handicap.setUpdateUser(appUserId);
			handicap.setUpdateTime(new Date());
			handicap.setUpdateTimeLong(System.currentTimeMillis());
			handicapService.update(handicap);


			bean.success();
		} catch (Exception e) {
			log.error(" 修改盘口信息错误", e);
			throw e;
		}
		return bean;
	}

}
