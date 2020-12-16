package com.ibm.old.v1.cloud.core.action;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.request.RequestThreadLocal;
import com.ibm.old.v1.cloud.ibm_cloud_client.t.entity.IbmCloudClientT;
import com.ibm.old.v1.cloud.ibm_cloud_client.t.service.IbmCloudClientTService;
import com.ibm.old.v1.cloud.ibm_sys_client_ip.t.entity.IbmSysClientIpT;
import com.ibm.old.v1.cloud.ibm_sys_client_ip.t.service.IbmSysClientIpTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: 注册客户端
 * @Author: Dongming
 * @Date: 2018-12-18 16:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RegisterClientAction extends BaseAppAction {

	@Override public String run() throws Exception {

		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findDateMap();
		JsonResultBean threadJrb= LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()){
			return returnJson(threadJrb);
		}
		String clientId = BeanThreadLocal.find(dataMap.get("clientId"), "");
		if (StringTool.isEmpty(clientId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(bean);
		}

		try {
			IbmCloudClientTService clientTService = new IbmCloudClientTService();
			IbmCloudClientT clientT = clientTService.findByClientCode(clientId);
			if (clientT != null) {
				if (!IbmStateEnum.OPEN.name().equals(clientT.getState())) {
					clientTService.updateState(clientT.getIbmCloudClientId(),IbmStateEnum.OPEN.name(),this.getClass().getName());
				}
				bean.success();
				return returnJson(bean);
			}

			String ip = RequestThreadLocal.getThreadLocal().get().findIP(request);
			IbmSysClientIpTService clientIpTService = new IbmSysClientIpTService();
			IbmSysClientIpT clientIpT = clientIpTService.findByIP(ip);
			AppUserT userT = null;

			//不存在合法性ip，存在账号密码。
			if (clientIpT == null) {
				String accountName = BeanThreadLocal.find(dataMap.get("accountName"), "");
				String accountPass = BeanThreadLocal.find(dataMap.get("accountPass"), "");
				if (StringTool.isEmpty(accountName,accountPass)) {
					bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return returnJson(bean);
				}
				AppUserService appUserService = new AppUserService();
				userT = appUserService.findClientUser(accountName, accountPass);
				if (userT == null) {
					bean.putEnum(IbmCodeEnum.IBM_404_NOT_FIND_USER);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return returnJson(bean);
				}
			}

			clientT = new IbmCloudClientT();
			if (userT != null) {
				clientT.setAppUserId(userT);
			}
			if (clientIpT != null) {
				clientT.setClientIpId(clientIpT.getIbmSysClientIpId());
				clientT.setIp(clientIpT.getIp());
			}
			clientT.setClientCode(clientId);
			clientT.setCreateTime(new Date());
			clientT.setCreateTimeLong(clientT.getCreateTime().getTime());
			clientT.setUpdateTime(new Date());
			clientT.setCreateTimeLong(clientT.getUpdateTime().getTime());
			clientT.setState(IbmStateEnum.OPEN.name());
			clientTService.save(clientT);
			bean.success();
		} catch (Exception e) {
			log.error("注册客户端失败，客户端id为" + clientId);
			throw e;
		}

		return this.returnJson(bean);
	}
}
