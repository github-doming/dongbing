package com.ibm.old.v1.cloud.core.controller.mq;
import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.ibm_cloud_client_capacity.t.service.IbmCloudClientCapacityTService;
import com.ibm.old.v1.cloud.ibm_cloud_client_handicap_capacity.t.service.IbmCloudClientHandicapCapacityTService;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.entity.IbmCloudClientHmT;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.service.IbmCloudClientHmTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.entity.IbmHandicapMemberT;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.utils.mq.RabbitMqIbmUtil;
import com.ibm.old.v1.pc.ibm_handicap_member.t.controller.LogoutHandicapController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.SQLException;
import java.util.Date;
/**
 * @Description: 退出盘口控制器, 关闭连接通道
 * @Author: Dongming
 * @Date: 2018-12-13 15:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CloseClientController implements CloudExecutor {

	@Override public JsonResultBeanPlus execute(String handicapMemberId) throws Exception {

		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		IbmHandicapMemberTService hmService = new IbmHandicapMemberTService();
		IbmHandicapMemberT handicapMemberT = hmService.find(handicapMemberId);
		if (handicapMemberT == null) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		// 查找是否存在客户端已存在盘口会员主键
		IbmCloudClientHmTService clientHmTService = new IbmCloudClientHmTService();
		IbmCloudClientHmT clientHmT = clientHmTService.findByHandicapMemberId(handicapMemberId);

		bean.success();
		// 中心端客户端盘口会员为空，即不已存在连接通道，不需要关闭
		if (clientHmT == null) {
			return bean;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("clientExistHmId", clientHmT.getClientExistHmId());
		jsonObject.put("method", IbmStateEnum.CLOSE.name());

		// 关闭结果
		String result = RabbitMqIbmUtil.sendExchange4Manage(clientHmT.getClientCode(), jsonObject.toString());
		JSONObject resultJson = JSONObject.fromObject(result);

		if (resultJson.getBoolean("success")) {
			// 更新用户信息存在机器表
			JSONObject data = resultJson.getJSONObject("data");
			updateClientInfo(clientHmT.getClientCode(), data);
			LogoutHandicapController logoutHandicapController=new LogoutHandicapController();
			logoutHandicapController.execute(handicapMemberId);
			return bean;
		}else{
			if (resultJson.getString("code").equals(IbmCodeEnum.IBM_404_CLIENT_HM_EXIST_ID.getCode())) {
				// 更新用户信息存在机器表
				JSONObject data = resultJson.getJSONObject("data");
				updateClientInfo(clientHmT.getClientCode(), data);
				updateHandicapMemberInfo(handicapMemberId);
				return bean;
			}
			//TODO 关闭失败
			bean.set(resultJson);
		}
		return bean;
	}

	/**
	 * 更新盘口会员用户信息
	 * @param handicapMemberId	盘口会员id
	 */
	private void updateHandicapMemberInfo(String handicapMemberId) throws SQLException {
		Date nowTime=new Date();
		//修改盘口用户信息
		IbmHandicapUserTService handicapUserTService=new IbmHandicapUserTService();
		handicapUserTService.updateLogout(handicapMemberId,nowTime);

		//修改登录状态
		IbmHandicapMemberTService handicapMemberTService=new IbmHandicapMemberTService();
		handicapMemberTService.updateLogout(handicapMemberId,nowTime);

		//修改投注状态
		IbmHmGameSetTService hmGameSetTService=new IbmHmGameSetTService();
		hmGameSetTService.updateBetState(handicapMemberId,nowTime,this.getClass().getName());
	}

	/**
	 * 更新客户端信息
	 *
	 * @param machineCode 客户端code
	 * @param data        更新数据
	 */
	private void updateClientInfo(String machineCode, JSONObject data) throws Exception {

		//客户端容量
		String exitsCount = data.getString("exitsCount");
		String capacityMax = data.getString("capacityMax");
		IbmCloudClientCapacityTService clientCapacityTService = new IbmCloudClientCapacityTService();
		clientCapacityTService.updateClientInfo(machineCode, exitsCount, capacityMax);

		//盘口客户端容量
		String handicapExitsCount = data.getString("handicapExitsCount");
		String handicapCapacityMax = data.getString("handicapCapacityMax");
		String handicapCode = data.getString("handicapCode");
		IbmCloudClientHandicapCapacityTService clientHandicapCapacityTService = new IbmCloudClientHandicapCapacityTService();
		clientHandicapCapacityTService
				.updateClientHandicapInfo(machineCode, handicapCode, handicapExitsCount, handicapCapacityMax);

		//客户端存在信息
		JSONArray exitsData = data.getJSONArray("exitsData");
		IbmCloudClientHmTService clientHmTService = new IbmCloudClientHmTService();
		clientHmTService.updateState(machineCode, exitsData,this.getClass().getName());

	}
}
