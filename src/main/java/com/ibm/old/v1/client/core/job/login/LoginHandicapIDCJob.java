package com.ibm.old.v1.client.core.job.login;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import com.ibm.old.v1.client.ibm_client_hm.t.entity.IbmClientHmT;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.client.ibm_client_hm_info.service.IbmClientHmInfoService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.IdcUtil;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description: IDC登陆盘口定时器
 * @Author: Dongming
 * @Date: 2019-03-08 17:39
 * @Version: v1.0
 */
public class LoginHandicapIDCJob extends BaseCommJob {

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		JobDataMap map = context.getMergedJobDataMap();
		String existHmId = map.getString("existHmId");
		String memberAccount = map.getString("memberAccount");
		log.trace("定时登陆IDC盘口用户【" + existHmId + "】账号【" + memberAccount + "】触发");
		try {
			JsonResultBeanPlus bean = execute(existHmId, memberAccount);
			if (bean.isSuccess()) {
				log.info("定时登陆IDC盘口用户【" + existHmId + "】账号【" + memberAccount + "】成功:" + bean.toJsonString());
			} else {
				log.error("定时登陆IDC盘口用户【" + existHmId + "】账号【" + memberAccount + "】失败:" + bean.toJsonString());
			}
		} catch (Exception e) {
			log.error("定时登陆IDC盘口用户【" + existHmId + "】账号【" + memberAccount + "】异常:" + e.getMessage());
			throw e;
		}
	}
	/**
	 * 执行
	 *
	 * @param existHmId     存在盘口会员id
	 * @param memberAccount 盘口会员账户
	 * @return 执行结果，失败date存code，成功data存信息
	 */
	public JsonResultBeanPlus execute(String existHmId, String memberAccount) throws Exception {
		CurrentTransaction.transactionEnd();
		try {
			JsonResultBeanPlus bean = new JsonResultBeanPlus();

			IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
			IbmClientHmT handicapMemberT = handicapMemberTService.findExist(existHmId, memberAccount);
			Map<String, Object> info = new HashMap<>(2);
			if (handicapMemberT == null) {
				bean.setData(IbmCodeEnum.IBM_404_CLIENT_HM_EXIST.getCode());
				bean.putEnum(IbmCodeEnum.IBM_404_CLIENT_HM_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				info.put("CODE", bean.getCode());
				info.put("MESSAGE", bean.getMessage());
				bean.setData(info);
				return bean;
			}

			//解密密码
			String commLocalASEKey = BeanThreadLocal
					.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"), "");
			String password = CommASEUtil.decode(commLocalASEKey, handicapMemberT.getMemberPassword().trim());

			//登陆
			IdcUtil idcUtil = IdcUtil.findInstance();
			bean = idcUtil.login(existHmId, memberAccount, password, handicapMemberT.getHandicapUrl(),
					handicapMemberT.getHandicapCaptcha());

			//登陆失败
			if (!bean.isSuccess()) {
				info.put("CODE", bean.getCode());
				info.put("MESSAGE", bean.getMessage());
				bean.setData(info);
				return bean;
			}
			//获取用户信息
			bean = idcUtil.userInfo(existHmId);

			//获取信息失败
			if (!bean.isSuccess()) {
				info.put("CODE", bean.getCode());
				info.put("MESSAGE", bean.getMessage());
				bean.setData(info);
				return bean;
			}

			//更新登陆状态
			handicapMemberTService.updateLogin(handicapMemberT.getIbmClientHmId());

			IbmClientHmInfoService hmInfoService = new IbmClientHmInfoService();
			Map<String, Object> userInfo =	hmInfoService.updateHmInfo(existHmId, bean,  bean);
			bean.setData(userInfo);
			return bean;
		} finally {
			CurrentTransaction.transactionBegin();
		}
	}
}
