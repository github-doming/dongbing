package com.ibm.old.v1.pc.ibm_cloud_receipt.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.old.v1.cloud.ibm_cloud_receipt.t.entity.IbmCloudReceiptT;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.entity.IbmHandicapMemberT;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.thread.InitHandicapMemberThread;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.ibm_cloud_receipt.t.service.IbmPcCloudReceiptTService;
import com.ibm.old.v1.pc.ibm_handicap_member.t.controller.HandicapInfoController;
import net.sf.json.JSONObject;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Description: 响应登录状态
 * @Author: wck
 * @Date: 2019-03-18 18:01
 * @Email: 810160078@qq.com
 * @Version: v1.0
 */
public class IbmHmLoginReceiptAction extends BaseAppAction {

	@Override public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_404_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_404);
			return this.returnJson(jrb);
		}
		//会员id
		String handicapMemberId = BeanThreadLocal.find(dataMap.get("HANDICAP_MEMBER_ID_"), "");
		//回执消息id
		String receiptId = BeanThreadLocal.find(dataMap.get("RECEIPT_ID_"), "");
		//执行的方法
		String method = BeanThreadLocal.find(dataMap.get("MESSAGE_METHOD_"), "");

		if (StringTool.isEmpty(handicapMemberId, method, receiptId)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}

		if (!IbmStateEnum.LOGIN.name().equals(method)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_METHOD);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}

		try {

			//获取回执信息
			IbmPcCloudReceiptTService receiptTService = new IbmPcCloudReceiptTService();
			IbmCloudReceiptT receiptT = receiptTService.find(receiptId);
			if (receiptT == null) {
				jrb.putEnum(IbmCodeEnum.IBM_404_RECEIPT);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}

			//执行的方法是否为LOGIN
			if (!receiptT.getHandicapMemberId().equals(handicapMemberId)
					||!IbmMethodEnum.LOGIN.name().equals(receiptT.getMessageMethod())) {
				jrb.putEnum(IbmCodeEnum.IBM_404_RECEIPT);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}

			//处理完成
			if (IbmStateEnum.FINISH.name().equals(receiptT.getReceiptState())) {
				Map<String, Object> data = new HashMap<>(3);
				JSONObject jsonRe = JSONObject.fromObject(receiptT.getProcessResult());
				if (jsonRe.getBoolean("success")) {
					//同步盘口会员方案状态
					IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
					IbmHandicapMemberT handicapMemberT = handicapMemberTService.find(handicapMemberId);
					if(IbmStateEnum.NEW.name().equals(handicapMemberT.getState())){
						ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance().findLocal();
						ExecutorService executorService = threadExecutorService.findExecutorService();
						executorService.execute(new InitHandicapMemberThread(handicapMemberId));
						handicapMemberT.setState(IbmStateEnum.OPEN.name());
						handicapMemberTService.update(handicapMemberT);
					}
					JSONObject hmInfo = new HandicapInfoController().execute(handicapMemberId).getJSONObject(0);
					data.put("hmInfo", hmInfo);
				} else {
					data.put("message", jsonRe.get("data"));
				}
				//登陆结果
				//回传登陆结果，为空前端需要再次请求
				jrb.setData(data);
			}
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "响应失败", e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
		}
		return returnJson(jrb);
	}
}
