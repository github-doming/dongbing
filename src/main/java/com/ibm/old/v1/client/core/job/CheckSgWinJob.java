package com.ibm.old.v1.client.core.job;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.client.ibm_client_hm_info.service.IbmClientHmInfoService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.SgWinUtil;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
/**
 * @Description: 定时检验SgWin
 * @Author: zjj
 * @Date: 2019-08-09 14:02
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class CheckSgWinJob extends BaseCommJob {
	public CheckSgWinJob() {
		super.saveLog = false;
	}
	@Override public void executeJob(JobExecutionContext context) throws Exception {
		JobDataMap map = context.getMergedJobDataMap();
		String existHmId = map.getString("existHmId");
		log.trace("定时检验SgWin盘口用户【" + existHmId + "】触发");
		CurrentTransaction.transactionEnd();
		try {
			//查询盘口会员是否登陆
			IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
			if (!handicapMemberTService.isLogin(existHmId)) {
				log.info("定时检验SgWin盘口用户【" + existHmId + "】失败，用户处于登出状态");
				return;
			}

			SgWinUtil sgWinUtil = SgWinUtil.findInstance();
			JsonResultBeanPlus bean = sgWinUtil.toCheckIn(existHmId);
			if (!bean.isSuccess()) {
				log.trace("定时检验SgWin盘口用户【" + existHmId + "】失败，校验信息为：" + bean.toJsonString());
			} else {
				log.trace("定时检验SgWin盘口用户【" + existHmId + "】成功，校验信息为：" + bean.toJsonString());
			}
			JsonResultBeanPlus userBean = sgWinUtil.userInfo(existHmId);

			IbmClientHmInfoService hmInfoService = new IbmClientHmInfoService();
			hmInfoService.updateHmInfo(existHmId, bean, userBean);
		} catch (Exception e) {
			log.error("定时检验SgWin盘口用户【" + existHmId + "】异常，异常信息为：", e);
		} finally {
			CurrentTransaction.transactionBegin();
		}
	}

}
