package com.ibm.old.v1.cloud.core.controller.mq;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import com.ibm.old.v1.common.doming.utils.mq.RabbitMqIbmUtil;
import net.sf.json.JSONObject;
import org.doming.core.common.thread.BaseCommThread;

import java.util.List;
import java.util.Map;
/**
 * @Description: 校验盘口会员状态控制器
 * @Author: zjj
 * @Date: 2019-04-13 13:38
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class CheckHmStateController extends BaseCommThread {
	private List<Map<String, Object>> listMap;
	public CheckHmStateController(List<Map<String, Object>> listMap) {
		this.listMap = listMap;
	}

	@Override public String execute(String ignore) throws Exception {
		JSONObject jsonObject = new JSONObject();
		for (Map<String, Object> map : listMap) {
			String handicapMemberId = map.get("HANDICAP_MEMBER_ID_").toString();
			String existHmId = map.get("CLIENT_EXIST_HM_ID_").toString();
			jsonObject.put("clientExistHmId", existHmId);
			jsonObject.put("method", IbmMethodEnum.CHECK.name());
			// 发送用户mq消息到客户端
			RabbitMqIbmUtil.sendExchange4Method(handicapMemberId, jsonObject.toString());
			jsonObject.clear();
		}
		return null;
	}
}
