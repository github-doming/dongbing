package com.ibs.plan.module.client.core.job;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.HcCodeEnum;
import com.common.handicap.MemberOption;
import com.common.handicap.crawler.entity.MemberUser;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.tools.QuartzTool;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.controller.ClientDefineController;
import com.ibs.plan.module.client.ibsc_exist_hm.service.IbscExistHmService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.develop.container.LruMap;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
/**
 * 会员定时校验
 *
 * @Author: Dongming
 * @Date: 2020-05-14 14:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CheckMemberJob extends BaseCommJob {
	private static final LruMap<String, CheckArgs> CHECK_INFO = new LruMap<>(32);

	private static final String LOG_TITLE = "盘口会员【{}】定时校验【{}】盘口,{}";

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		JobDataMap map = context.getMergedJobDataMap();
		String existHmId = map.getString("existHmId");
		HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(map.get("handicapCode").toString());

		log.info(LOG_TITLE, existHmId, handicapCode, "开始");
		//用户的状态不在登录中
		if (!IbsStateEnum.LOGIN.equals(CustomerCache.stateInfo(existHmId))) {
			//数据库也不存在
			if (!IbsStateEnum.LOGIN.equal(new IbscExistHmService().findState(existHmId))) {
				log.error(LOG_TITLE, existHmId, handicapCode, "异常，清除定时检验");
				QuartzTool.removeCheckJob(existHmId, handicapCode.name());
				return;
			}
		}
		CheckArgs checkArgs;
		if (CHECK_INFO.containsKey(existHmId)) {
			checkArgs = CHECK_INFO.get(existHmId);
		} else {
			checkArgs = new CheckArgs();
			CHECK_INFO.put(existHmId, checkArgs);
		}
		if (checkArgs.isCheck()) {
			log.error(LOG_TITLE, existHmId, handicapCode, "异常，上一次检验尚未完成");
			return;
		}
		checkArgs.isCheck(true);
		MemberOption option = handicapCode.getMemberFactory().memberOption(existHmId);
		//校验对象为空
		if (option == null) {
			log.error(LOG_TITLE, existHmId, handicapCode, "错误：校验爬虫对象为空");
			if (checkArgs.checkError()) {
				sendError(existHmId, HcCodeEnum.IBS_404_CRAWLER.getCode(), HcCodeEnum.IBS_404_CRAWLER.getMsgCn());
			}
			checkArgs.isCheck(false);
			return;
		}
		JsonResultBeanPlus bean = option.checkInfo();
		if (bean.getCodeSys().equals(HcCodeEnum.CODE_403.getCode())) {
			log.error(LOG_TITLE, existHmId, handicapCode, "错误：校验用户信息错误：" + bean.toString());
			if (checkArgs.checkError()) {
				sendError(existHmId, bean.getCode(), bean.getMessage());

			}
			checkArgs.isCheck(false);
			return;
		}
		//判断其他错误
		if (!bean.isSuccess() || ContainerTool.isEmpty(bean.getData())) {
			log.error(LOG_TITLE, existHmId, handicapCode, "错误：校验用户信息错误：" + bean.toString());
			if (checkArgs.checkError()) {
				// 登录 - 其他地方不允许登录 - 根据结果判定是否获取个人信息
				option.needLogin();
			}
			checkArgs.isCheck(false);
			return;
		}
		MemberUser userInfo = (MemberUser) bean.getData();
		checkArgs.checkSuccess();
		//修改内存余额信息
		CustomerCache.usedQuota(existHmId, userInfo.usedQuota());
		new ClientDefineController().sendUserInfo(existHmId,userInfo);
		checkArgs.isCheck(false);
		log.info(LOG_TITLE, existHmId, handicapCode, ",校验完成");
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
		content.put("METHOD_", IbsMethodEnum.LOGOUT.name());
		content.put("message", message);
		content.put("code", code);
		RabbitMqTool.sendInfo(content.toString(), "member");
		log.error(LOG_TITLE, existHmId, "handicapCode", "发送错误信息到主服务器 content：" + content.toString());
	}

	class CheckArgs {
		/**
		 * 上次检验是否结束
		 */
		private boolean isCheck = false;
		private int checkError = 0;
		private long lastCheckTime = 0L;

		public boolean isCheck() {
			return this.isCheck;
		}
		public void isCheck(boolean isCheck) {
			this.isCheck = isCheck;
		}
		public boolean checkError() {
			if (System.currentTimeMillis() - lastCheckTime > DateTool.getMillisecondsMinutes(10)) {
				return true;
			}
			return ++checkError > 5;
		}
		public void checkSuccess() {
			checkError = 0;
			lastCheckTime = System.currentTimeMillis();
		}
	}
}
