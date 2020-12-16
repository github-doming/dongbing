package com.ibm.old.v1.cloud.core.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_cloud_client.t.entity.IbmCloudClientT;
import com.ibm.old.v1.cloud.ibm_cloud_client.t.service.IbmCloudClientTService;
import com.ibm.old.v1.cloud.ibm_cloud_client_capacity.t.service.IbmCloudClientCapacityTService;
import com.ibm.old.v1.cloud.ibm_cloud_client_handicap_capacity.t.service.IbmCloudClientHandicapCapacityTService;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.service.IbmCloudClientHmTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;

/**
 * @Description: 关闭客户端
 * @Author: Dongming
 * @Date: 2019-01-02 15:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CloseClientAction extends BaseAppAction {
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
			//获取客户端相关盘口会员id
			IbmCloudClientHmTService cloudClientHmTService = new IbmCloudClientHmTService();
			List<String> handicapMemberIdList = cloudClientHmTService.listHandicapMemberId(clientId);

			IbmHandicapUserTService handicapUserTService = new IbmHandicapUserTService();
			IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
			IbmHmGameSetTService hmGameSetTService = new IbmHmGameSetTService();
			Date nowTime = new Date();
			//移除客户端中的盘口会员
			for (String handicapMemberId : handicapMemberIdList){
				//修改在线会员数量
				handicapUserTService.updateLogout(handicapMemberId,nowTime);

				//修改登陆状态
				handicapMemberTService.updateLogout(handicapMemberId,nowTime);

				//修改投注状态
				hmGameSetTService.updateBetState(handicapMemberId,nowTime,this.getClass().getName());
			}

			//删除客户端
			IbmCloudClientTService clientTService = new IbmCloudClientTService();
			IbmCloudClientT clientT = clientTService.findByClientCode(clientId);
			if(clientT == null){
				bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(bean);
			}
			clientT.setState(IbmStateEnum.DEL.name());
			clientT.setUpdateTime(new Date());
			clientT.setUpdateTimeLong(clientT.getUpdateTime().getTime());
			clientTService.update(clientT);

			//删除客户端信息
			new IbmCloudClientHmTService().delByClientCode(clientId,this.getClass().getName());
			new IbmCloudClientHandicapCapacityTService().delByClientCode(clientId,this.getClass().getName());
			new IbmCloudClientCapacityTService().delByClientCode(clientId,this.getClass().getName());

			bean.success();
		} catch (Exception e) {
			log.error("关闭客户端失败，客户端id为" + clientId);
			throw e;
		}

		return this.returnJson(bean);
	}
}
