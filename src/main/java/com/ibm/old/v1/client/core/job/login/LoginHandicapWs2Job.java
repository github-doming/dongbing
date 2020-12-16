package com.ibm.old.v1.client.core.job.login;

import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import com.ibm.old.v1.client.ibm_client_hm.t.entity.IbmClientHmT;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.client.ibm_client_hm_info.service.IbmClientHmInfoService;
import com.ibm.old.v1.common.doming.configs.Ws2Config;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.Ws2Util;
import net.sf.json.JSONObject;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description: Ws2登陆盘口定时器
 * @Author: Dongming
 * @Date: 2018-12-07 13:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LoginHandicapWs2Job extends BaseCommJob {

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		JobDataMap map = context.getMergedJobDataMap();
		String existHmId = map.getString("existHmId");
		String memberAccount = map.getString("memberAccount");
		log.trace("定时登陆WS2盘口用户【" + existHmId + "】账号【" + memberAccount + "】触发");
		try {
			JsonResultBeanPlus bean = execute(existHmId, memberAccount);
			if (bean.isSuccess()) {
				log.info("定时登陆WS2盘口用户【" + existHmId + "】账号【" + memberAccount + "】成功:" + bean.toJsonString());
			} else {
				log.error("定时登陆WS2盘口用户【" + existHmId + "】账号【" + memberAccount + "】失败:" + bean.toJsonString());
			}
		} catch (Exception e) {
			log.error("定时登陆WS2盘口用户【" + existHmId + "】账号【" + memberAccount + "】异常:" + e.getMessage());
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
			Map<String, Object> info = new HashMap<>(2);

			IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
			IbmClientHmT handicapMemberT = handicapMemberTService.findExist(existHmId, memberAccount);
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
			Ws2Util ws2Util = Ws2Util.findInstance();
			bean = ws2Util.login(existHmId, handicapMemberT.getHandicapUrl(), handicapMemberT.getHandicapCaptcha(),
					memberAccount, password);
			//登陆失败
			if (!bean.isSuccess()) {
				info.put("CODE", bean.getCode());
				info.put("MESSAGE", bean.getMessage());
				bean.setData(info);
				return bean;
			}
			//首次登陆
			ws2Util.firstLogin(existHmId, bean);

			//获取用户是否停押状态
			JsonResultBeanPlus betInfoBean = ws2Util
					.betInfo(existHmId, Ws2Config.GAME_CODE_MAP.get("PK10"), Ws2Config.getBetCode("PK10", "ballNO15"),
							IbmStateEnum.LOGIN);
			//获取到游戏投注信息时判定用户状态是否为停押，获取失败就跳过暂不处理，由投注时进行在次获取
			if (betInfoBean.isSuccess()) {
				log.info("WS2盘口会员【" + existHmId + "】游戏投注信息 = " + betInfoBean.toJsonString());
				JSONObject betInfo = (JSONObject) betInfoBean.getData();
				JSONObject dataJson = betInfo.getJSONObject("data");
				if (dataJson.getInt("user_status") == 2) {
					ws2Util.removeExistHmInfo(existHmId);
					info.put("CODE", IbmHcCodeEnum.IBM_403_USER_BAN_BET.getCode());
					info.put("MESSAGE", IbmHcCodeEnum.IBM_403_USER_BAN_BET.getMsgCn());
					bean.putEnum(IbmHcCodeEnum.IBM_403_USER_BAN_BET);
					bean.putSysEnum(IbmHcCodeEnum.CODE_403);
					bean.setSuccess(false);
					bean.setData(info);
					return bean;
				}
			}
			//获取用户信息
			bean = ws2Util.userInfo(existHmId, Ws2Config.FIRST_GAME);

			// 存储用户信息
			IbmClientHmInfoService hmInfoService = new IbmClientHmInfoService();
			info = hmInfoService.updateHmInfo(existHmId, bean, bean);

			//更新登陆状态
			handicapMemberTService.updateLogin(handicapMemberT.getIbmClientHmId());

			bean.setData(info);
			return bean;
		} finally {
			CurrentTransaction.transactionBegin();
		}
	}
}
