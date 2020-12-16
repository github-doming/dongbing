package com.ibm.old.v1.client.core.job;

import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.client.ibm_client_hm_info.service.IbmClientHmInfoService;
import com.ibm.old.v1.common.doming.configs.Ws2Config;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.Ws2Util;
import com.ibm.old.v1.common.doming.utils.job.QuartzIbmUtil;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.HashMap;
import java.util.Map;
/**
 * @author zjj
 * @ClassName: CheckWS2Job
 * @Description: 定时检验
 * @date 2019年3月4日 上午10:05:18
 */
public class CheckWs2Job extends BaseCommJob {
	private static Map<String, Integer> userInfoCount = new HashMap<>();
	public CheckWs2Job() {
		super.saveLog = false;
	}
	@Override public void executeJob(JobExecutionContext context) throws Exception {
		JobDataMap map = context.getMergedJobDataMap();
		String existHmId = map.getString("existHmId");
		String handicapCode = map.getString("handicapCode");
		log.trace("定时检验WS2盘口用户【" + existHmId + "】触发");
		CurrentTransaction.transactionEnd();
		try {
			JsonResultBeanPlus userBean;
			//查询盘口会员是否登陆
			IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
			if (!handicapMemberTService.isLogin(existHmId)) {
				log.info("定时检验WS2盘口用户【" + existHmId + "】失败，用户处于登出状态");
				return;
			}
			Ws2Util ws2Util = Ws2Util.findInstance();
			JsonResultBeanPlus bean = ws2Util.checkUser(existHmId);
			if (!bean.isSuccess()) {
				log.trace("定时检验WS2盘口用户【" + existHmId + "】失败，校验信息为：" + bean.toJsonString());
				//其他地方登陆，退出
				if (StringTool.contains(bean.getCode(), IbmHcCodeEnum.IBM_403_OTHER_PLACE_LOGIN.getCode(),
						IbmHcCodeEnum.IBM_403_USER_ACCOUNT.getCode(), IbmHcCodeEnum.IBM_403_USER_BAN_BET.getCode())) {
					QuartzIbmUtil.removeJob(existHmId, IbmHandicapEnum.valueOf(handicapCode));
					handicapMemberTService.updateLogin(existHmId, IbmStateEnum.LOGOUT);
				} else if (StringTool.contains(bean.getCode(), IbmHcCodeEnum.IBM_403_CHECK_CODE.getCode(),
						IbmHcCodeEnum.IBM_403_TIMING_CHECKOUT.getCode())) {
					//同个盘口不同盘口会员登录间隔不小于40s
					JsonResultBeanPlus jrb = ws2Util.checkLogin(existHmId);
					if (jrb != null) {
						log.info("定时检验WS2盘口用户【" + existHmId + "】失败，重新登陆信息 = " + jrb.toJsonString());
						ws2Util.userInfo(existHmId, Ws2Config.FIRST_GAME);
					}
					bean = ws2Util.checkUser(existHmId);
					log.info("定时检验WS2盘口用户【" + existHmId + "】，校验信息为 = " + bean.toJsonString());
				}
			} else {
				log.trace("定时检验WS2盘口用户【" + existHmId + "】成功，校验信息为：" + bean.toJsonString());
			}

			int runCount = getRunCount(existHmId);
			runCount++;
			if (runCount < RandomTool.getInt(6, 10)) {
				userInfoCount.put(existHmId, runCount);
			} else {
				// 获取用户信息
				userInfoCount.put(existHmId, 0);
				userBean = ws2Util.userInfo(existHmId, Ws2Config.FIRST_GAME);
				IbmClientHmInfoService hmInfoService = new IbmClientHmInfoService();
				hmInfoService.updateHmInfo(existHmId, bean, userBean);

			}

		} catch (Exception e) {
			log.error("定时检验WS2盘口用户【" + existHmId + "】异常，异常信息为：", e);
		} finally {
			CurrentTransaction.transactionBegin();
		}
	}

	/**
	 * 获取运行计数
	 *
	 * @param existHmId 存在盘口会员id
	 * @return 运行计数
	 */
	private int getRunCount(String existHmId) {
		if (userInfoCount.containsKey(existHmId)) {
			return userInfoCount.get(existHmId);
		} else {
			userInfoCount.put(existHmId, 0);
			return 0;
		}
	}
}
