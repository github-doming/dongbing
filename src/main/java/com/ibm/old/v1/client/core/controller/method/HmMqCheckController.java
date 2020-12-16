package com.ibm.old.v1.client.core.controller.method;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.ibm_client_hm_info.entity.IbmClientHmInfo;
import com.ibm.old.v1.client.ibm_client_hm_info.service.IbmClientHmInfoService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

/**
 * @Description: 盘口会员定时校验
 * @Author: zjj
 * @Date: 2019-04-12 17:19
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HmMqCheckController implements ClientExecutor {

	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {

		IbmClientHmInfoService hmInfoService = new IbmClientHmInfoService();
		IbmClientHmInfo hmInfo = hmInfoService.findByExistId(msgObj.getString("clientExistHmId"));

		if (ContainerTool.isEmpty(hmInfo)) {
			bean.putEnum(IbmCodeEnum.IBM_404_CLIENT_HM_EXIST_ID);
			bean.putEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		String code = hmInfo.getMemberInfoCode();

		JSONObject data = new JSONObject();

		JSONObject memberInfo = new JSONObject();
		if (IbmCodeEnum.CODE_200.getCode().equals(hmInfo.getMemberInfoCode())) {
			memberInfo.put("BALANCE", NumberTool.doubleT(hmInfo.getCreditQuotaT() + hmInfo.getUsedAmountT()));
			memberInfo.put("NICKNAME", hmInfo.getMemberAccount());
		} else {
			memberInfo.put("CODE", hmInfo.getMemberInfoCode());
			memberInfo.put("MESSAGE", hmInfo.getMemberInfo());
		}
		data.put("memberInfo", memberInfo);
		data.put("profit", NumberTool.doubleT(hmInfo.getProfitAmountT()));
		//判断校验的code
		if (IbmCodeEnum.IBM_200.getCode().equals(code)) {
			data.put("state", IbmStateEnum.TRACE.name());
		} else if (StringTool.contains(code, IbmHcCodeEnum.IBM_403_OTHER_PLACE_LOGIN.getCode(),
				IbmHcCodeEnum.IBM_403_USER_ACCOUNT.getCode(), IbmHcCodeEnum.IBM_403_USER_BAN_BET.getCode())) {
			data.put("state", IbmStateEnum.ERROR.name());
		} else {
			data.put("state", IbmStateEnum.INFO.name());
		}
		bean.setData(data);
		bean.success();
		return bean;
	}
}
