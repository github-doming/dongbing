package com.ibm.old.v1.client.core.controller.set;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.client.ibm_client_hm.t.entity.IbmClientHmT;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import net.sf.json.JSONObject;
/**
 * @Description: 登陆
 * @Author: Dongming
 * @Date: 2019-03-09 16:36
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MemberLogonController implements ClientExecutor {

	@Override public JsonResultBeanPlus execute(JSONObject msgObj, IbmClientExistHmT existHmT) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String memberAccount = msgObj.getString("memberAccount");
		String existHmId = existHmT.getIbmClientExistHmId();

		IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
		IbmClientHmT handicapMemberT = handicapMemberTService.findExist(existHmId, memberAccount);

		//不存在历史盘口会员信息
		if (handicapMemberT == null) {
			bean.putEnum(IbmCodeEnum.IBM_404_CLIENT_HM_EXIST);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		return SetToolController.startLogin(existHmId, existHmT.getHandicapCode(), memberAccount);
	}

}
