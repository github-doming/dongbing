package com.ibm.old.v1.client.core.controller.set;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.client.ibm_client_hm.t.entity.IbmClientHmT;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import net.sf.json.JSONObject;
/**
 * @Description: 记录成员登陆信息
 * @Author: Dongming
 * @Date: 2018-12-04 17:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MemberLoginSetController implements ClientExecutor {

	/**
	 * 执行方法
	 *
	 * @param msgObj   输入消息
	 * @param existHmT 存在于服务的盘口会员信息
	 * @return 执行结果
	 * @throws Exception 执行异常
	 */
	@Override public JsonResultBeanPlus execute(JSONObject msgObj, IbmClientExistHmT existHmT) throws Exception {
		//查找用户
		String memberAccount = msgObj.getString("memberAccount");
		String existHmId = existHmT.getIbmClientExistHmId();

		IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
		IbmClientHmT handicapMemberT = handicapMemberTService.findExist(existHmId, memberAccount);
		SetToolController.updateHandicapMember(msgObj, existHmT, memberAccount, handicapMemberTService, handicapMemberT);

		return SetToolController.startLogin(existHmId, existHmT.getHandicapCode(), memberAccount);

	}

}
