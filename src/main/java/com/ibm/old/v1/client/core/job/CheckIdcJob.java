package com.ibm.old.v1.client.core.job;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.client.ibm_client_hm_info.service.IbmClientHmInfoService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.IdcUtil;
import com.ibm.old.v1.common.doming.utils.job.QuartzIbmUtil;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.StringTool;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
/**
 * @Description: 定时检验IDC
 * @Author: zjj
 * @Date: 2019-03-09 14:17
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class CheckIdcJob extends BaseCommJob {

	public CheckIdcJob() {
		super.saveLog = false;
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		JobDataMap map = context.getMergedJobDataMap();
		String existHmId = map.getString("existHmId");
		String handicapCode = map.getString("handicapCode");
		log.trace("定时检验IDC盘口用户【" + existHmId + "】触发");
		CurrentTransaction.transactionEnd();
		try {
			//查询盘口会员是否登陆
			IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
			if (!handicapMemberTService.isLogin(existHmId)) {
				log.info("定时检验IDC盘口用户【" + existHmId + "】失败，用户处于登出状态");
				return;
			}
			IdcUtil idcUtil = IdcUtil.findInstance();
			JsonResultBeanPlus bean = idcUtil.toCheckIn(existHmId);
			JsonResultBeanPlus jrb = null;
			if (!bean.isSuccess()) {
				log.trace("定时检验IDC盘口用户【" + existHmId + "】失败，校验信息为：" + bean.toJsonString());
				//其他地方登陆，退出
				if (StringTool.isContains(bean.getCode(), IbmHcCodeEnum.IBM_403_OTHER_PLACE_LOGIN.getCode())) {
					QuartzIbmUtil.removeJob(existHmId, IbmHandicapEnum.valueOf(handicapCode));
					handicapMemberTService.updateLogin(existHmId, IbmStateEnum.LOGOUT);
				} else if (StringTool.contains(bean.getCodeSys(), IbmHcCodeEnum.CODE_403.getCode())) {
					//同个盘口不同盘口会员登录间隔不小于40s
					jrb = idcUtil.checkLogin(existHmId);
					if (jrb != null) {
						log.info("定时检验IDC盘口用户【" + existHmId + "】失败，重新登陆信息 = " + jrb.toJsonString());
					}
					bean = idcUtil.toCheckIn(existHmId);
					log.info("定时检验IDC盘口用户【" + existHmId + "】，校验信息为 = " + bean.toJsonString());
				} else if (StringTool.contains(bean.getCode(), IbmHcCodeEnum.CODE_500.getCode())) {
					bean = idcUtil.toCheckIn(existHmId);
					log.info("定时检验IDC盘口用户【" + existHmId + "】，校验信息为 = " + bean.toJsonString());
				}
			} else {
				log.trace("定时检验IDC盘口用户【" + existHmId + "】成功，校验信息为：" + bean.toJsonString());
			}
			JsonResultBeanPlus userBean = idcUtil.userInfo(existHmId);

			IbmClientHmInfoService hmInfoService = new IbmClientHmInfoService();
			hmInfoService.updateHmInfo(existHmId, bean,  userBean);

		} catch (Exception e) {
			log.error("定时检验IDC盘口用户【" + existHmId + "】异常，异常信息为：", e);
		} finally {
			CurrentTransaction.transactionBegin();
		}
	}
}
