package com.ibm.follow.servlet.cloud.core.thread;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.controller.process.LoginHaController;
import com.ibm.follow.servlet.cloud.core.controller.process.LoginHmController;
import com.ibm.follow.servlet.cloud.ibm_client_capacity.service.IbmClientCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service.IbmClientHandicapCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_client_close.entity.IbmEventClientClose;
import com.ibm.follow.servlet.cloud.ibm_event_client_close.service.IbmEventClientCloseService;
import com.ibm.follow.servlet.cloud.ibm_event_client_open.entity.IbmEventClientOpen;
import com.ibm.follow.servlet.cloud.ibm_event_client_open.service.IbmEventClientOpenService;
import com.ibm.follow.servlet.cloud.ibm_event_config_info.entity.IbmEventConfigInfo;
import com.ibm.follow.servlet.cloud.ibm_event_config_info.service.IbmEventConfigInfoService;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.entity.IbmEventConfigSet;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.ibm_event_login.entity.IbmEventLogin;
import com.ibm.follow.servlet.cloud.ibm_event_login.service.IbmEventLoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 事件线程工具类
 * @Author: Dongming
 * @Date: 2019-09-02 15:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class EventThreadDefine {
	protected static final Logger log = LogManager.getLogger(EventThreadDefine.class);
	/**
	 * 更新容量
	 *
	 * @param exitsInfo    客户端存在信息
	 * @param clientId     客户端id
	 * @param clientCode   客户端编码
	 * @param handicapCode 盘口code
	 */
	public static void updateCapacity(JSONObject exitsInfo, String clientId, String clientCode, String handicapCode)
			throws Exception {
		Date nowTime = new Date();
		//客户端最大容量
		int capacityMax = exitsInfo.getInteger("capacityMax");
		//客户端使用容量
		int exitsCount = exitsInfo.getInteger("exitsCount");
		//盘口最大容量
		int capacityHandicapMax = exitsInfo.getInteger("capacityHandicapMax");
		//盘口使用容量
		int capacityHandicap = exitsInfo.getInteger("capacityHandicap");
		//会员最大容量
		int capacityHmMax = exitsInfo.getInteger("capacityHmMax");
		//会员使用容量
		int capacityHm = exitsInfo.getInteger("capacityHm");
		//代理最大容量
		int capacityHaMax = exitsInfo.getInteger("capacityHaMax");
		//代理使用容量
		int capacityHa = exitsInfo.getInteger("capacityHa");

		//容量异常
		if (capacityMax < exitsCount || capacityHaMax < capacityHa || capacityHmMax < capacityHm
				|| capacityHandicapMax < capacityHandicap) {
			log.error("客户端容量错误,错误信息={}",exitsInfo.toString());
		}

		//客户端容量记录
		String clientCapacityId = new IbmClientCapacityService()
				.updateCapacity(clientId, clientCode, capacityMax, exitsCount, nowTime);
		//客户端盘口容量记录
		new IbmClientHandicapCapacityService()
				.updateCapacity(clientId, clientCode, handicapCode, clientCapacityId, capacityHandicapMax,
						capacityHandicap, capacityHaMax, capacityHa, capacityHmMax, capacityHm, nowTime);
	}

	/**
	 * 发送客户设置信息
	 *
	 * @param content      消息内容
	 * @param customerId   客户id
	 * @param customerType 客户类型
	 * @param desc         说明
	 * @return
	 */
	public static boolean sendClientConfig(JSONObject content,String customerId,IbmTypeEnum customerType,String desc) throws Exception {
		Map<String, Object> existInfo;
		String eventId;
		switch (customerType) {
			case MEMBER:
				existInfo=new IbmClientHmService().findExistHmId(customerId);
				if (ContainerTool.isEmpty(existInfo)){
					return false;
				}
				content.put("EXIST_HM_ID_", existInfo.get("EXIST_HM_ID_"));
				eventId= EventThreadDefine.saveMemberConfigSetEvent(content, new Date(),
						desc, new IbmEventConfigSetService());
				break;
			case AGENT:
				existInfo =  new IbmClientHaService().findExistHaId(customerId);
				if (ContainerTool.isEmpty(existInfo)){
					return false;
				}
				content.put("EXIST_HA_ID_", existInfo.get("EXIST_HA_ID_"));
				eventId= EventThreadDefine.saveAgentConfigSetEvent(content, new Date(),
						desc, new IbmEventConfigSetService());
				break;
			default:
				return false;
		}
		String clientCode = existInfo.remove("CLIENT_CODE_").toString();
		content.put("EVENT_ID_",eventId);
		RabbitMqTool.sendClientConfig(content.toString(), clientCode, "set");
		return true;
	}


	/**
	 * 登录-保存登录信息
	 *
	 * @param customerType 客户类型
	 * @param data         盘口用户信息
	 * @param content      消息内容
	 */
	public static String saveLoginResult(IbmTypeEnum customerType, Object data, JSONObject content) throws Exception {
		switch (customerType) {
			case MEMBER:
				String handicapMemberId = content.getString("HANDICAP_MEMBER_ID_");
				LoginHmController hmController = new LoginHmController(handicapMemberId);
				return hmController.execute(data);
			case AGENT:
				String handicapAgentId = content.getString("HANDICAP_AGENT_ID_");
				LoginHaController haController = new LoginHaController(handicapAgentId);
				return haController.execute(data);
			default:
				log.error("客户类型错误,错误信息={}",customerType.name());
				return null;
		}
	}
	/**
	 * 验证登录-添加客户信息
	 *
	 * @param data         返回信息
	 * @param customerType 客户类型
	 * @param content      消息内容
	 * @param clientId     客户端id
	 * @param clientCode   客户端编码
	 */
	public static String saveCustomerInfo(JSONObject data, IbmTypeEnum customerType, JSONObject content,
			String clientId, String clientCode) throws Exception {
		switch (customerType) {
			case MEMBER:
				IbmClientHmService clientHmService = new IbmClientHmService();
				String handicapMemberId = content.get("HANDICAP_MEMBER_ID_").toString();
				String existHmId = data.getString("EXIST_HM_ID_");
				clientHmService
						.save(clientId, handicapMemberId, existHmId, clientCode, content.getString("HANDICAP_CODE_"));
				//添加初始化盘口会员信息
				LoginHmController hmController = new LoginHmController(handicapMemberId);
				return hmController.execute(data.get("CUSTOMER_INFO_"));
			case AGENT:
				IbmClientHaService clientHaService = new IbmClientHaService();
				String handicapAgentId = content.get("HANDICAP_AGENT_ID_").toString();
				String existHaId = data.getString("EXIST_HA_ID_");
				clientHaService
						.save(clientId, handicapAgentId, existHaId, clientCode, content.getString("HANDICAP_CODE_"));
				//添加初始化盘口代理信息
				LoginHaController haController = new LoginHaController(handicapAgentId);
				return haController.execute(data.get("CUSTOMER_INFO_"));
			default:
				log.error("客户类型错误,错误信息={}",customerType.name());
				return null;
		}
	}
	/**
	 * 保存会员登录事件
	 *
	 * @param content           登录事件正文
	 * @param date              保存时间
	 * @param desc              描述
	 * @param eventLoginService 事件服务类
	 * @return 事件主键
	 */

	public static String saveMemberLoginEvent(JSONObject content, Date date, String desc,
			IbmEventLoginService eventLoginService, String handicapMemberId) throws Exception {
		return saveLoginEvent(IbmTypeEnum.MEMBER, content, date, desc, eventLoginService, handicapMemberId);
	}

	/**
	 * 保存代理登录事件
	 *
	 * @param content           登录事件正文
	 * @param date              保存时间
	 * @param desc              描述
	 * @param eventLoginService 事件服务类
	 * @return 事件主键
	 */

	public static String saveAgentLoginEvent(JSONObject content, Date date, String desc,
			IbmEventLoginService eventLoginService, String handicapAgentId) throws Exception {
		return saveLoginEvent(IbmTypeEnum.AGENT, content, date, desc, eventLoginService, handicapAgentId);
	}
	/**
	 * 保存登录事件
	 *
	 * @param type              设置类型
	 * @param content           登录事件正文
	 * @param date              保存时间
	 * @param desc              描述
	 * @param eventLoginService 事件服务类
	 * @return 事件主键
	 */
	private static String saveLoginEvent(IbmTypeEnum type, JSONObject content, Date date, String desc,
			IbmEventLoginService eventLoginService, String customerId) throws Exception {
		IbmEventLogin eventLogin = new IbmEventLogin();
		eventLogin.setCustomerId(customerId);
		eventLogin.setCustomerType(type.name());
		eventLogin.setEventContent(content);
		eventLogin.setEventState(IbmStateEnum.BEGIN.name());
		eventLogin.setExecNumber(0);
		eventLogin.setCreateTime(date);
		eventLogin.setCreateTimeLong(System.currentTimeMillis());
		eventLogin.setUpdateTimeLong(System.currentTimeMillis());
		eventLogin.setState(IbmStateEnum.OPEN.name());
		eventLogin.setDesc(desc);
		return eventLoginService.save(eventLogin);
	}

	/**
	 * 保存代理设置信息
	 *
	 * @param content               设置正文
	 * @param date                  事件时间
	 * @param desc                  事件描述
	 * @param eventConfigSetService 事件服务类
	 */
	public static String saveAgentConfigSetEvent(JSONObject content, Date date, String desc,
			IbmEventConfigSetService eventConfigSetService) throws Exception {
		return saveConfigSetEvent(IbmTypeEnum.AGENT, content, date, desc, eventConfigSetService);
	}
	/**
	 * 保存会员设置信息
	 *
	 * @param content               设置正文
	 * @param date                  事件时间
	 * @param desc                  事件描述
	 * @param eventConfigSetService 事件服务类
	 */
	public static String saveMemberConfigSetEvent(JSONObject content, Date date, String desc,
			IbmEventConfigSetService eventConfigSetService) throws Exception {
		return saveConfigSetEvent(IbmTypeEnum.MEMBER, content, date, desc, eventConfigSetService);
	}
	/**
	 * 保存设置信息
	 *
	 * @param type                  设置类型
	 * @param content               设置正文
	 * @param date                  事件时间
	 * @param desc                  事件描述
	 * @param eventConfigSetService 事件服务类
	 */
	private static String saveConfigSetEvent(IbmTypeEnum type, JSONObject content, Date date, String desc,
			IbmEventConfigSetService eventConfigSetService) throws Exception {
		IbmEventConfigSet eventConfigSet = new IbmEventConfigSet();
		eventConfigSet.setCustomerType(type.name());
		eventConfigSet.setEventContent(content);
		eventConfigSet.setEventState(IbmStateEnum.BEGIN.name());
		eventConfigSet.setExecNumber(0);
		eventConfigSet.setCreateTime(date);
		eventConfigSet.setCreateTimeLong(System.currentTimeMillis());
		eventConfigSet.setUpdateTimeLong(System.currentTimeMillis());
		eventConfigSet.setState(IbmStateEnum.OPEN.name());
		eventConfigSet.setDesc(desc);
		return eventConfigSetService.save(eventConfigSet);
	}
	/**
	 * 保存登出事件
	 *
	 * @param type               设置类别
	 * @param content            设置正文
	 * @param date               事件时间
	 * @param desc               事件描述
	 * @param clientCloseService 事件服务类
	 */
	private static String saveLogoutEvent(IbmTypeEnum type, JSONObject content, Date date, String desc,
			IbmEventClientCloseService clientCloseService) throws Exception {
		IbmEventClientClose clientClose = new IbmEventClientClose();
		clientClose.setCustomerType(type.name());
		clientClose.setEventContent(content);
		clientClose.setEventState(IbmStateEnum.BEGIN.name());
		clientClose.setExecNumber(0);
		clientClose.setCreateTime(date);
		clientClose.setCreateTimeLong(System.currentTimeMillis());
		clientClose.setUpdateTimeLong(System.currentTimeMillis());
		clientClose.setState(IbmStateEnum.OPEN.name());
		clientClose.setDesc(desc);
		return clientCloseService.save(clientClose);
	}
	/**
	 * 保存登出事件
	 *
	 * @param type    设置类别
	 * @param content 设置正文
	 * @param desc    事件描述
	 */
	public static String saveLogoutEvent(IbmTypeEnum type, JSONObject content, String desc) throws Exception {
		return saveLogoutEvent(type, content, new Date(), desc, new IbmEventClientCloseService());
	}

	/**
	 * 保存客户端配置信息
	 *
	 * @param content           设置正文
	 * @param date              事件时间
	 * @param method            事件方法
	 * @param desc              事件描述
	 * @param configInfoService 事件服务类
	 */
	public static String saveConfigInfoEvent(JSONObject content, Date date, IbmMethodEnum method, String desc,
			IbmEventConfigInfoService configInfoService) throws Exception {
		IbmEventConfigInfo eventConfigInfo = new IbmEventConfigInfo();
		eventConfigInfo.setEventContent(content);
		eventConfigInfo.setEventMethod(method.name());
		eventConfigInfo.setEventState(IbmStateEnum.BEGIN.name());
		eventConfigInfo.setExecNumber(0);
		eventConfigInfo.setCreateTime(date);
		eventConfigInfo.setCreateTimeLong(System.currentTimeMillis());
		eventConfigInfo.setUpdateTimeLong(System.currentTimeMillis());
		eventConfigInfo.setState(IbmStateEnum.OPEN.name());
		eventConfigInfo.setDesc(desc);
		return configInfoService.save(eventConfigInfo);
	}

	public static String saveOpenClientEvent(JSONObject content, IbmTypeEnum type,String desc, Object assignClientCode)
			throws Exception {
		IbmEventClientOpenService eventClientOpenService = new IbmEventClientOpenService();
		IbmEventClientOpen eventClientOpen = new IbmEventClientOpen();
		eventClientOpen.setClientCode(assignClientCode);
		eventClientOpen.setCustomerType(type.name());
		eventClientOpen.setEventContent(content);
		eventClientOpen.setEventState(IbmStateEnum.BEGIN.name());
		eventClientOpen.setExecNumber(0);
		eventClientOpen.setCreateTime(new Date());
		eventClientOpen.setCreateTimeLong(System.currentTimeMillis());
		eventClientOpen.setUpdateTimeLong(System.currentTimeMillis());
		eventClientOpen.setState(IbmStateEnum.OPEN.name());
		eventClientOpen.setDesc(desc);
		return eventClientOpenService.save(eventClientOpen);
	}
}
