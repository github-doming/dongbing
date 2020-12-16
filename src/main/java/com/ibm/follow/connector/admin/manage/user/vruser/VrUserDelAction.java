package com.ibm.follow.connector.admin.manage.user.vruser;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.entity.IbmEventConfigSet;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import com.ibm.follow.servlet.cloud.vr_client_member.entity.VrClientMember;
import com.ibm.follow.servlet.cloud.vr_client_member.service.VrClientMemberService;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_member_game_set.service.VrMemberGameSetService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 新增虚拟用户
 * @Author: admin1
 * @Date: 2020/7/16 14:28
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/vruser/del")
public class VrUserDelAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String memberId = StringTool.getString(dataMap.get("MEMBER_ID_"), "");

		try {
			VrMemberService memberService = new VrMemberService();
			VrMember member = memberService.find(memberId);
			if (member == null) {
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean;
			}

			Map<String, Object> clientInfo = new VrClientMemberService().findInfo(memberId);
			//删除相关数据
			memberService.delMemberInfo(memberId);
			if(ContainerTool.notEmpty(clientInfo)){
				//发送至客户端
				//发送事件
				//写入客户设置事件 vrc_plan_item vrc_member_plan
				JSONObject content = new JSONObject();
				content.put("EXIST_MEMBER_VR_ID_", clientInfo.get("EXIST_MEMBER_VR_ID_"));
				content.put("USER_ID_", member.getVrMemberId());
				content.put("SET_ITEM_", IbmMethodEnum.DEL_MEMBER_VR.name());
				content.put("METHOD_", IbmMethodEnum.MANAGE_VR.name());

				IbmEventConfigSet configSet = new IbmEventConfigSet();
				configSet.setEventContent(content);
				configSet.setEventState(IbmStateEnum.SEND.name());
				configSet.setExecNumber(0);
				configSet.setCreateTime(new Date());
				configSet.setCreateTimeLong(System.currentTimeMillis());
				configSet.setUpdateTimeLong(System.currentTimeMillis());
				configSet.setState(IbmStateEnum.OPEN.name());
				String eventId = new IbmEventConfigSetService().save(configSet);

				CurrentTransaction.transactionCommit();
				content.put("EVENT_ID_", eventId);
				RabbitMqTool.sendClientConfig(content.toString(), clientInfo.get("CLIENT_CODE_").toString(), "set");
			}
			bean.success();
		} catch (Exception e) {
			log.error("用户列表错误", e);
			bean.error(e.getMessage());
			return bean;
		}
		return bean;
	}
}
