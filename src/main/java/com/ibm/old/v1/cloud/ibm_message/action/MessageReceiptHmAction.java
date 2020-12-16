package com.ibm.old.v1.cloud.ibm_message.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.service.IbmCloudClientHmTService;
import com.ibm.old.v1.cloud.ibm_cms_message_content.t.entity.IbmCmsMessageContentT;
import com.ibm.old.v1.cloud.ibm_cms_message_content.t.service.IbmCmsMessageContentTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.entity.IbmHandicapMemberT;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.cloud.ibm_message.controller.CheckController;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: 盘口会员异常状态处理
 * @Author: zjj
 * @Date: 2019-04-09 14:59
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class MessageReceiptHmAction extends BaseAppAction {

	@Override public String run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findDateMap();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String existHmId = BeanThreadLocal.find(dataMap.get("existHmId"), "");
		String receiptState = BeanThreadLocal.find(dataMap.get("receiptState"), "");

		if (ContainerTool.isEmpty(dataMap.get("bean")) || StringTool.isEmpty(existHmId, receiptState)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(bean);
		}
		// 查找是否存在盘口会员主键
		IbmCloudClientHmTService clientHmTService = new IbmCloudClientHmTService();
		String handicapMemberId = clientHmTService.findHandicapMemberId(existHmId);

		if (StringTool.isEmpty(handicapMemberId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_CLIENT_HM_EXIST_ID);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(bean);
		}
		IbmMethodEnum method = IbmMethodEnum.getMethod(receiptState);
		if (method == null) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_METHOD);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(bean);
		}
		CloudExecutor execute;
		switch (method) {
			case CHECK:
				execute = new CheckController();
				execute.execute(handicapMemberId, dataMap.get("bean").toString());
				break;
			//添加其他异常处理
			case BET:
				messageReceiptHmBet(handicapMemberId, dataMap.get("bean").toString());
				break;
			default:
				bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_METHOD);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return returnJson(bean);
		}
		return null;
	}

	private void messageReceiptHmBet(String handicapMemberId, String msg) throws Exception {
		IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
		JSONObject bean = JSONObject.fromObject(msg);
		Date nowTime = new Date();
		if (IbmCodeEnum.CODE_403.getCode().equals(bean.get("codeSys"))){
			Object code =  bean.get("code");
			//更新盘口会员信息
			JSONObject memberInfo = new JSONObject();
			memberInfo.put("MESSAGE",bean.get("data"));
			memberInfo.put("CODE",code);
			handicapMemberTService.updateMemberInfo(handicapMemberId,memberInfo);

			//用户已停用
			if (IbmHcCodeEnum.IBM_403_USER_STATE.getCode().equals(code)){
				IbmHandicapMemberT handicapMemberT=handicapMemberTService.find(handicapMemberId);
				JSONObject jsonObject=JSONObject.fromObject(handicapMemberT.getMemberInfo());

				IbmCmsMessageContentTService messageContentTService=new IbmCmsMessageContentTService();
				IbmCmsMessageContentT messageContentT=new IbmCmsMessageContentT();
				messageContentT.setReceiveUserId(handicapMemberT.getAppUserId());
				messageContentT.setTitle("盘口【"+handicapMemberT.getHandicapCode()+"】账号【"+handicapMemberT.getMemberAccount()+"】消息");
				messageContentT.setContent(jsonObject.getString("MESSAGE"));
				messageContentT.setReplyState(IbmStateEnum.PROCESS.name());
				messageContentT.setReadingState(IbmStateEnum.UNREAD.name());
				messageContentT.setKeyword(IbmStateEnum.LOGOUT_EXCEPTION.getMsgCn());
				messageContentT.setMessageType("系统消息");
				messageContentT.setCreateTime(new Date());
				messageContentT.setCreateTimeLong(messageContentT.getCreateTime().getTime());
				messageContentT.setUpdateTime(new Date());
				messageContentT.setUpdateTimeLong(messageContentT.getUpdateTime().getTime());
				messageContentT.setState(IbmStateEnum.OPEN.name());
				messageContentTService.save(messageContentT);

				IbmHmGameSetTService hmGameSetTService = new IbmHmGameSetTService();
				hmGameSetTService.updateBetState(handicapMemberId,nowTime,this.getClass().getName());
				handicapMemberTService.updateLogout(handicapMemberId,nowTime);
				IbmHandicapUserTService handicapUserTService = new IbmHandicapUserTService();
				handicapUserTService.updateLogout(handicapMemberId,nowTime);
			}

		}
	}
}
