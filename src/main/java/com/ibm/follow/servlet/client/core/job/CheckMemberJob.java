package com.ibm.follow.servlet.client.core.job;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.HcCodeEnum;
import com.common.handicap.MemberOption;
import com.common.handicap.crawler.entity.MemberUser;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
import com.ibm.follow.servlet.client.ibmc_hm_info.service.IbmcHmInfoService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.container.LruMap;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 * @Description: 会员定时校验
 * @Author: Dongming
 * @Date: 2019-10-14 16:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CheckMemberJob extends BaseCommJob {
	private static final LruMap<String, CheckArgs> CHECK_INFO = new LruMap<>(32);

	private static final String LOG_TITLE = "盘口会员【{}】定时校验【{}】盘口,{}";

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		JobDataMap map = context.getMergedJobDataMap();
		String existHmId = map.getString("existHmId");
        HandicapUtil.Code handicapCode;
        if(map.get("handicapCode") instanceof Enum){
            handicapCode = (HandicapUtil.Code) map.get("handicapCode");
        }else{
            handicapCode=HandicapUtil.Code.valueOf(map.get("handicapCode").toString());
        }
		log.trace(logTitle(),existHmId, handicapCode, "开始");
		//用户的状态不在登录中
        if(!IbmStateEnum.LOGIN.equal(CustomerCache.stateInfo(existHmId))){
			//数据库也不存在
			if(!IbmStateEnum.LOGIN.equal(new IbmcExistHmService().findState(existHmId))){
				log.error(logTitle(),existHmId, handicapCode, "异常，清除定时检验");
				QuartzTool.removeCheckJob(existHmId, handicapCode);
				return;
			}
		}
        if(StringTool.isEmpty(new IbmcExistHmService().findState(existHmId))){
			log.error(logTitle(),existHmId, handicapCode, "异常，清除定时检验");
            QuartzTool.removeCheckJob(existHmId, handicapCode);
            return;
        }
		CheckArgs checkArgs;
		if (CHECK_INFO.containsKey(existHmId)) {
			checkArgs = CHECK_INFO.get(existHmId);
		} else {
			checkArgs = new CheckArgs(existHmId);
			CHECK_INFO.put(existHmId, checkArgs);
		}
		if(checkArgs.getLastCheck()){
			log.error(logTitle(),existHmId, handicapCode, "异常，上一次检验尚未完成");
			return;
		}
		checkArgs.setLastCheck(true);
		MemberOption option = handicapCode.getMemberFactory().memberOption(existHmId);
		//校验对象为空
		if (option == null) {
			log.error(logTitle(), existHmId, handicapCode, "错误：校验爬虫对象为空");
			if (checkArgs.checkError()) {
				sendError(existHmId, HcCodeEnum.IBS_404_CRAWLER.getCode(), HcCodeEnum.IBS_404_CRAWLER.getMsgCn());
			}
			checkArgs.isCheck(false);
			return;
		}
		JsonResultBeanPlus bean =option.checkInfo();
		//校验对象为空
		if (bean == null) {
			log.error(LOG_TITLE,existHmId, handicapCode, "错误：校验结果对象为空");
			checkArgs.checkError();
			checkArgs.setLastCheck(false);
			return;
		}
		if (bean.getCodeSys().equals(HcCodeEnum.CODE_403.getCode())) {
			log.error(LOG_TITLE, existHmId, handicapCode, "错误：校验用户信息错误：" + bean.toString());
			if (checkArgs.checkError()) {
				sendError(existHmId, bean.getCode(), bean.getMessage());
			}
			checkArgs.isCheck(false);
			return;
		}
		/*
			1.判断失败累加- code403
				->如果累加次数超过某个数据则发信息到主服务器
				->主服务器接受信息，写入数据，写入登出事件，登出
			2.判断其他错误
				checkArgs.checkError();
			3.正常记录 -
				并且只要成功-清理掉错误累加
		 */
		//判断失败累加
		if (bean.equalSysCode(HcCodeEnum.CODE_403)) {
			//如果累加次数超过某个数据则发信息到主服务器
			if (checkArgs.checkFatal()) {
				// 发送错误信息到主服务器
				sendError(existHmId, bean.getCode(), bean.getMessage());
			}
			checkArgs.setLastCheck(false);
			return;
		}
		//判断其他错误
		if (!bean.isSuccess() || ContainerTool.isEmpty(bean.getData())) {
			if(checkArgs.checkError()){
				log.error(logTitle(),existHmId, handicapCode, "校验结果长时间出错："+bean.toJsonString());
				option.needLogin();
			}
			checkArgs.setLastCheck(false);
			return;
		}
		MemberUser userInfo = (MemberUser) bean.getData();
		checkArgs.checkSuccess();

		IbmcHmInfoService hmInfoService = new IbmcHmInfoService();

		hmInfoService.updateAmoutInfo(existHmId,userInfo.getCreditQuota(), userInfo.getUsedQuota(),
				userInfo.getProfitAmount());


		//修改内存余额信息
		CustomerCache.usedQuota(existHmId, userInfo.getUsedQuota());
		sendUserInfo(existHmId,userInfo);

		checkArgs.setLastCheck(false);
		log.debug(LOG_TITLE,existHmId, handicapCode, ",校验完成");
	}

	/**
	 * 保存用户信息
	 * @param existHmId 存在会员主键
	 * @param userInfo 用户信息
	 */
	public void sendUserInfo(String existHmId, MemberUser userInfo) throws Exception {
		JSONObject content = new JSONObject();
		content.put("EXIST_HM_ID_", existHmId);
		content.put("usedQuota", userInfo.getUsedQuota());
		content.put("profitAmount", userInfo.getProfitAmount());
		content.put("method", IbmMethodEnum.CUSTOMER_INFO.name());
		content.put("requestType", IbmStateEnum.SUCCESS.name());
		RabbitMqTool.sendInfoReceipt(content.toString(), "member");
	}

	private String logTitle() {
		return "盘口会员【{}】定时校验【{}】盘口,{}";
	}
	/**
	 * 发送错误信息到主服务器
	 *
	 * @param existHmId 存在会员主键
	 * @param code      错误编码
	 */
	private void sendError(String existHmId, String code, String message) throws Exception {
		// 发送错误信息到主服务器
		JSONObject content = new JSONObject();
		content.put("EXIST_HM_ID_", existHmId);
		content.put("method",IbmMethodEnum.LOGOUT.name());
		content.put("message",message);
		content.put("code",code);
		RabbitMqTool.sendInfoReceipt(content.toString(), "member");
		log.error(logTitle(), existHmId, "handicapCode", "发送错误信息到主服务器 content：" + content.toString());
	}
}
