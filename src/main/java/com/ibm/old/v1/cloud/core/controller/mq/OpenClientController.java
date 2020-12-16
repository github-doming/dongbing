package com.ibm.old.v1.cloud.core.controller.mq;

import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.ibm_cloud_client.t.service.IbmCloudClientTService;
import com.ibm.old.v1.cloud.ibm_cloud_client_capacity.t.entity.IbmCloudClientCapacityT;
import com.ibm.old.v1.cloud.ibm_cloud_client_capacity.t.service.IbmCloudClientCapacityTService;
import com.ibm.old.v1.cloud.ibm_cloud_client_handicap_capacity.t.service.IbmCloudClientHandicapCapacityTService;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.entity.IbmCloudClientHmT;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.service.IbmCloudClientHmTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.entity.IbmHandicapMemberT;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.utils.mq.RabbitMqIbmUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.Date;
/**
 * @Description: 登陆盘口控制器, 创建连接通道
 * @Author: Dongming
 * @Date: 2018-12-13 15:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class OpenClientController implements CloudExecutor {

	@Override public JsonResultBeanPlus execute(String handicapMemberId) throws Exception {

		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		IbmHandicapMemberTService hmService = new IbmHandicapMemberTService();
		IbmHandicapMemberT handicapMemberT = hmService.find(handicapMemberId);
		if (handicapMemberT == null) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_HANDICAP_MEMBER_ID);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		// 查找是否存在客户端已存在盘口会员主键
		IbmCloudClientHmTService clientHmTService = new IbmCloudClientHmTService();
		IbmCloudClientHmT clientHmT = clientHmTService.findByHandicapMemberId(handicapMemberId);

		// 中心端客户端盘口会员不为空，即已存在连接通道，不需要再次创建通道
		if (clientHmT != null) {
			bean.putSysEnum(IbmCodeEnum.CODE_200);
			bean.setSuccess(true);
			return bean;
		}

		IbmCloudClientTService clientTService = new IbmCloudClientTService();
		String machineCode = clientTService.findReadyClient(handicapMemberT.getHandicapCode());
		// 判断是否有可用客户端
		if (StringTool.isEmpty(machineCode)) {
			bean.putEnum(IbmCodeEnum.IBM_404_CLIENT_EXIST);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("handicapMemberId", handicapMemberT.getIbmHandicapMemberId());
		jsonObject.put("userId", handicapMemberT.getAppUserId());
		jsonObject.put("handicapId", handicapMemberT.getHandicapId());
		jsonObject.put("handicapCode", handicapMemberT.getHandicapCode());
		jsonObject.put("method", IbmMethodEnum.OPEN.name());
		String result = RabbitMqIbmUtil.sendExchange4Manage(machineCode, jsonObject.toString());

		// 处理返回结果
		if ( StringTool.isEmpty(result)) {
			bean.putEnum(IbmCodeEnum.IBM_404_MQ_CONNECTION_ERROR);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}

		JSONObject resultJson = JSONObject.fromObject(result);

		if (resultJson.getBoolean("success")) {
			bean.success();
			if (resultJson.getString("code").equals(IbmCodeEnum.IBM_403_ALREADY_EXISTS.getCode())) {
				return bean;
			}
			// 更新用户信息存在机器表
			JSONObject data = resultJson.getJSONObject("data");
			updateClientInfo(machineCode, data, handicapMemberT);
			return bean;
		} else {
			if (resultJson.getString("code").equals(IbmCodeEnum.IBM_403_MAX_CAPACITY.getCode())) {
				JSONObject data = resultJson.getJSONObject("data");
				updateCapacity(machineCode, data);
				updateHmState(machineCode, data);
			}
			// 更新客户端机器表，将该机器的状态修改为已达到最大值
			if (resultJson.getString("code").equals(IbmCodeEnum.IBM_403_MAX_HANDICAP_CAPACITY.getCode())) {
				JSONObject data = resultJson.getJSONObject("data");
				updateHandicapCapacity(machineCode, data);
				updateHmState(machineCode, data);
			}
			return execute(handicapMemberId);
		}

	}

	/**
	 * 更新中心端客户端盘口会员状态
	 *
	 * @param machineCode 客户端code
	 * @param data        更新数据
	 */
	private void updateHmState(String machineCode, JSONObject data) throws SQLException {
		JSONArray exitsData = data.getJSONArray("exitsData");
		IbmCloudClientHmTService clientHmTService = new IbmCloudClientHmTService();
		clientHmTService.updateState(machineCode, exitsData,this.getClass().getName());
	}

	/**
	 * 更新客户端信息
	 *
	 * @param machineCode     客户端code
	 * @param data            更新数据
	 * @param handicapMemberT 会员盘口信息
	 */
	private void updateClientInfo(String machineCode, JSONObject data, IbmHandicapMemberT handicapMemberT)
			throws Exception {
		IbmCloudClientCapacityT clientCapacityT = updateCapacity(machineCode, data);
		String cloudClientHandicapCapacityId = updateHandicapCapacity(machineCode, data);

		// 新增存在信息
		String handicapCode = data.getString("handicapCode");
		String clientExistHmId = data.getString("clientExistHmId");
		IbmCloudClientHmTService clientHmTService = new IbmCloudClientHmTService();
		IbmCloudClientHmT clientHmT = new IbmCloudClientHmT();
		clientHmT.setCloudClientHandicapCapacityId(cloudClientHandicapCapacityId);
		clientHmT.setCloudClientCapacityId(clientCapacityT.getIbmCloudClientCapacityId());
		clientHmT.setCloudClientId(clientCapacityT.getCloudClientId());
		clientHmT.setClientCode(machineCode);
		clientHmT.setClientExistHmId(clientExistHmId);
		clientHmT.setHandicapMemberId(handicapMemberT.getIbmHandicapMemberId());
		clientHmT.setHandicapId(handicapMemberT.getHandicapId());
		clientHmT.setHandicapCode(handicapCode);
		clientHmT.setAppUserId(handicapMemberT.getAppUserId());
		clientHmT.setCreateTime(new Date());
		clientHmT.setCreateTimeLong(clientHmT.getCreateTime().getTime());
		clientHmT.setUpdateTime(new Date());
		clientHmT.setUpdateTimeLong(clientHmT.getUpdateTime().getTime());
		clientHmT.setState(IbmStateEnum.OPEN.name());
		clientHmTService.save(clientHmT);
	}

	/**
	 * 更新客户端盘口信息
	 *
	 * @param clientCode 客户端code
	 * @param data       更新数据
	 * @return IBM_中心端客户端盘口主键
	 */
	private String updateHandicapCapacity(String clientCode, JSONObject data) throws Exception {
		String handicapExitsCount = data.getString("handicapExitsCount");
		String handicapCapacityMax = data.getString("handicapCapacityMax");
		String handicapCode = data.getString("handicapCode");
		IbmCloudClientHandicapCapacityTService clientHandicapCapacityTService = new IbmCloudClientHandicapCapacityTService();
		return clientHandicapCapacityTService
				.updateClientHandicapInfo(clientCode, handicapCode, handicapExitsCount, handicapCapacityMax);
	}
	/**
	 * 更新客户端信息
	 *
	 * @param clientCode 客户端code
	 * @param data       更新数据
	 * @return 中心端客户端主键
	 */
	private IbmCloudClientCapacityT updateCapacity(String clientCode, JSONObject data) throws Exception {
		String exitsCount = data.getString("exitsCount");
		String capacityMax = data.getString("capacityMax");
		IbmCloudClientCapacityTService clientCapacityTService = new IbmCloudClientCapacityTService();
		return clientCapacityTService.updateClientInfo(clientCode, exitsCount, capacityMax);
	}

}
