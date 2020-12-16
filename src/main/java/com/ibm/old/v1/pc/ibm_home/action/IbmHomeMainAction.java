package com.ibm.old.v1.pc.ibm_home.action;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_available_time.t.service.IbmAvailableTimeTService;
import com.ibm.old.v1.cloud.ibm_cms_message_content.t.service.IbmCmsMessageContentTService;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_handicap_user.t.service.IbmPcHandicapUserTService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 首页，加载主题框架活动
 * @Author: Dongming
 * @Date: 2019-01-07 17:12
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmHomeMainAction extends BaseAppAction {

	@Override public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb= LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()){
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			Map<String,Object> data = new HashMap<>(4);

			//用户盘口
			IbmPcHandicapUserTService handicapUserTService = new IbmPcHandicapUserTService();
			data.put("handicapInfo", handicapUserTService.listUserHandicap(appUserId));

			//游戏列表
			IbmGameTService gameTService = new IbmGameTService();
			data.put("gameInfo",gameTService.listInfo());

			// 用户剩余时间
			IbmAvailableTimeTService availableTimeTService = new IbmAvailableTimeTService();
			data.put("endTimeLong",availableTimeTService.findEndTimeLong(appUserId));


			IbmCmsMessageContentTService messageContentTService=new IbmCmsMessageContentTService();
			//未读消息数
			String unReadCount =messageContentTService.findUnreadCount(appUserId);
			data.put("unread",unReadCount);

			jrb.success();
			jrb.setData(data);
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN,e);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return returnJson(jrb);
	}
}
