package com.ibm.old.v1.client.core.controller.manage;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.ibm_client_config.t.service.IbmClientConfigTService;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.service.IbmClientExistHmTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.utils.mq.RabbitMqIbmUtil;
import net.sf.json.JSONObject;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 盘口会员开启一个mq链接
 * @Author: Dongming
 * @Date: 2019-03-07 10:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HmMqOpenController implements ClientExecutor {

	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private String handicapMemberId;
	private String handicapCode;
	private String userId;
	private String handicapId;

	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {

		if (!valiParameter(msgObj)) {
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		IbmClientExistHmTService existHmTService = new IbmClientExistHmTService();
		String hmExistId = existHmTService.findExits(handicapMemberId);
		if (StringTool.notEmpty(hmExistId)) {
			//关闭盘口会员的客户机
			JSONObject json = new JSONObject();
			json.put("clientExistHmId",hmExistId);
			new HmMqCloseController().execute(json);
		}

		//查看本机是否可以开启队列
		IbmClientConfigTService configTService = new IbmClientConfigTService();
		Map<Object, Object> capacityMax = configTService.findMaxCapacity(handicapCode);

		Map<String, Object> exitsCount = existHmTService.findExitsCount(handicapCode);

		//服务器容量已达上限
		Map<String, Object> data = new HashMap<>(3);
		data.put("exitsData", exitsCount.get("exitsData"));
		if (NumberTool.compareInteger(exitsCount.get("exitsCount"), capacityMax.get("CAPACITY_MAX")) >= 0) {
			data.put("exitsCount", exitsCount.get("exitsCount"));
			data.put("capacityMax", capacityMax.get("CAPACITY_MAX"));
			bean.putEnum(IbmCodeEnum.IBM_403_MAX_CAPACITY);
			bean.putSysEnum(IbmCodeEnum.CODE_403);
			bean.setData(data);
			return bean;
		}
		//服务器盘口容量已达上限
		if (NumberTool
				.compareInteger(exitsCount.get("handicapExitsCount"), capacityMax.get(handicapCode + "_CAPACITY_MAX"))
				>= 0) {
			data.put("handicapCode", handicapCode);
			data.put("exitsCount", exitsCount.get("handicapExitsCount"));
			data.put("capacityMax", capacityMax.get(handicapCode + "_CAPACITY_MAX"));
			bean.putEnum(IbmCodeEnum.IBM_403_MAX_HANDICAP_CAPACITY);
			bean.putSysEnum(IbmCodeEnum.CODE_403);
			bean.setData(data);
			return bean;
		}
		// 开启队列

		RabbitMqIbmUtil.receiveExchange4HandicapMember(handicapMemberId);

		IbmClientExistHmT existT = new IbmClientExistHmT();
		existT.setHandicapMemberId(handicapMemberId);
		existT.setAppUserId(userId);
		existT.setHandicapId(handicapId);
		existT.setHandicapCode(handicapCode);
		existT.setCreateTime(new Date());
		existT.setCreateTimeLong(existT.getCreateTime().getTime());
		existT.setUpdateTime(new Date());
		existT.setUpdateTimeLong(existT.getUpdateTime().getTime());
		existT.setState(IbmStateEnum.OPEN.name());
		String clientExistHmId = existHmTService.save(existT);

		exitsCount = existHmTService.findExitsCount(handicapCode);
		data.put("handicapCode", handicapCode);
		data.put("clientExistHmId", clientExistHmId);
		data.put("exitsData", exitsCount.get("exitsData"));
		data.put("exitsCount", exitsCount.get("exitsCount"));
		data.put("handicapExitsCount", exitsCount.get("handicapExitsCount"));
		data.put("capacityMax", capacityMax.get("CAPACITY_MAX"));
		data.put("handicapCapacityMax", capacityMax.get(handicapCode + "_CAPACITY_MAX"));
		// 返回结果
		bean.setData(data);
		bean.success();
		return bean;
	}

	/**
	 * 初始化并验证参数
	 * 必须参数	{handicapCode，handicapMemberId,userId}
	 *
	 * @param msgObj 通讯消息
	 * @return 通过 true
	 */
	protected boolean valiParameter(JSONObject msgObj) {

		handicapCode = msgObj.getString("handicapCode");
		if (StringTool.isEmpty(handicapCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_HANDICAP_CODE);
			return false;
		}
		handicapMemberId = msgObj.getString("handicapMemberId");
		if (StringTool.isEmpty(handicapMemberId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			return false;
		}
		userId = msgObj.getString("userId");
		if (StringTool.isEmpty(userId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER_ID);
			return false;
		}
		handicapId = msgObj.getString("handicapId");
		if (StringTool.isEmpty(handicapId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_HANDICAP_ID);
			return false;
		}
		return true;
	}
}
