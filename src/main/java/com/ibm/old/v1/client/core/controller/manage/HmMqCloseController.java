package com.ibm.old.v1.client.core.controller.manage;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.ibm_client_config.t.service.IbmClientConfigTService;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.service.IbmClientExistHmTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.IdcUtil;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.SgWinUtil;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.Ws2Util;
import com.ibm.old.v1.common.doming.utils.job.QuartzIbmUtil;
import com.ibm.old.v1.common.doming.utils.mq.RabbitMqIbmUtil;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 盘口会员关闭一个mq链接
 * @Author: Dongming
 * @Date: 2019-03-07 10:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HmMqCloseController implements ClientExecutor {

	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	private String clientExistHmId;
	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		if (!valiParameter(msgObj)) {
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		IbmClientExistHmTService existHmTService = new IbmClientExistHmTService();
		IbmClientExistHmT existHmT = existHmTService.find(clientExistHmId);
		if (existHmT == null) {
			bean.putEnum(IbmCodeEnum.IBM_404_CLIENT_HM_EXIST_ID);
			bean.putEnum(IbmCodeEnum.CODE_404);
			return bean;
		}

		IbmHandicapEnum handicapCode = IbmHandicapEnum.valueOf(existHmT.getHandicapCode());

		if(ContainerTool.isEmpty(handicapCode)){
			bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
			bean.putEnum(IbmCodeEnum.CODE_404);
			return bean;
		}

		switch (handicapCode) {
			case WS2:
				Ws2Util ws2Util = Ws2Util.findInstance();
				ws2Util.removeExistHmInfo(clientExistHmId);
				break;
			case IDC:
				IdcUtil idcUtil=IdcUtil.findInstance();
				idcUtil.removeExistHmInfo(clientExistHmId);
				break;
			case SGWIN:
				SgWinUtil sgWinUtil=SgWinUtil.findInstance();
				sgWinUtil.removeExistHmInfo(clientExistHmId);
				break;
			default:
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
				bean.putEnum(IbmCodeEnum.CODE_404);
				return bean;
		}

		QuartzIbmUtil.removeJob(clientExistHmId,existHmT.getHandicapCodeEnum());

		//销毁mq通道
		RabbitMqIbmUtil.destroyExchange4HandicapMember(existHmT.getHandicapMemberId());

		//关闭盘口会员的客户机
		boolean exist = existHmTService.closeExistHm(clientExistHmId);

		//容量更新
		IbmClientConfigTService configTService = new IbmClientConfigTService();
		Map<Object, Object> capacityMax = configTService.findMaxCapacity(existHmT.getHandicapCode());
		Map<String, Object> exitsCount = existHmTService.findExitsCount(existHmT.getHandicapCode());
		Map<String, Object> data = new HashMap<>(6);
		data.put("exitsData", exitsCount.get("exitsData"));
		data.put("exitsCount", exitsCount.get("exitsCount"));
		data.put("handicapExitsCount", exitsCount.get("handicapExitsCount"));
		data.put("handicapCode", existHmT.getHandicapCode());
		data.put("capacityMax", capacityMax.get("CAPACITY_MAX"));
		data.put("handicapCapacityMax", capacityMax.get(existHmT.getHandicapCode() + "_CAPACITY_MAX"));
		bean.setData(data);
		if (exist) {
			bean.success();
		} else {
			//关闭失败
			bean.putEnum(IbmCodeEnum.IBM_404_CLIENT_HM_EXIST_ID);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
		}
		return bean;
	}
	/**
	 * 初始化并验证参数
	 * 必须参数	{handicapCode，handicapMemberId,userId}
	 *
	 * @param msgObj 通讯消息
	 * @return 通过 true
	 */
	private boolean valiParameter(JSONObject msgObj) {

		clientExistHmId = msgObj.getString("clientExistHmId");
		if (StringTool.isEmpty(clientExistHmId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_CLIENT_HM_EXIST_ID);
			return false;
		}
		return true;
	}
}
