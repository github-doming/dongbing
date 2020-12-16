package com.ibm.old.v1.pc.ibm_handicap_member.t.action;

import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.old.v1.cloud.core.controller.mq.CheckLoginController;
import com.ibm.old.v1.cloud.core.controller.mq.LoginClientController;
import com.ibm.old.v1.cloud.ibm_cloud_receipt.t.service.IbmCloudReceiptTService;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.service.IbmHandicapGameTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.entity.IbmHandicapMemberT;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.thread.InitHandicapMemberThread;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.entity.IbmHmGameSetT;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.entity.IbmHmSetT;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.service.IbmPlanHmTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import com.ibm.old.v1.pc.ibm_handicap_member.t.controller.HandicapInfoController;
import com.ibm.old.v1.pc.ibm_handicap_member.t.service.IbmPcHandicapMemberTService;
import com.ibm.old.v1.pc.ibm_handicap_user.t.service.IbmPcHandicapUserTService;
import com.ibm.old.v1.pc.ibm_hm_set.t.service.IbmPcHmSetTService;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Description: 盘口会员登陆
 * @Author: Dongming
 * @Date: 2019-03-15 14:27
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmHandicapMemberLoginAction extends BaseAppAction {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private String memberAccount;
	private String memberPassword;
	private String handicapMemberId;
	private String handicapCode;
	private String saveAccount;
	private String landedTimeStr;

	@Override public String run() throws Exception {
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return super.returnJson(threadJrb);
		}
		if (!valiParameters()) {
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			IbmPcHandicapMemberTService handicapMemberTService = new IbmPcHandicapMemberTService();
			IbmPcHmSetTService hmSetTService = new IbmPcHmSetTService();

			//获取盘口id
			IbmHandicapTService handicapTService = new IbmHandicapTService();
			String handicapId = handicapTService.findIdByCode(handicapCode);
			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return super.returnJson(bean);
			}

			// 查用户盘口信息
			IbmPcHandicapUserTService handicapUserTService = new IbmPcHandicapUserTService();
			String handicapUserId = handicapUserTService.findId(handicapCode, super.appUserId);
			if (StringTool.isEmpty(handicapUserId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_USER);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(bean);
			}
			//获取全局最大在线会员限制数
			if (!handicapUserTService.limit(handicapId, super.appUserId)) {
				bean.putEnum(IbmCodeEnum.IBM_403_MAX_HANDICAP_CAPACITY);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return this.returnJson(bean);
			}
			//存在密码，需要加密
			String password = null;
			if (StringTool.notEmpty(memberPassword)) {
				String key = BeanThreadLocal
						.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"), "false");
				password = CommASEUtil.encode(key, memberPassword.trim()).trim();
			}
			IbmHandicapMemberT handicapMemberT;

			//定时登陆时间
			if (StringTool.notEmpty(landedTimeStr)) {
				if (StringTool.notEmpty(handicapMemberId)) {
					Date landedTime = DateTool.getTime(new SimpleDateFormat("HH:mm:ss").format(Long.parseLong(landedTimeStr)));
					handicapMemberT = handicapMemberTService.find(handicapMemberId);
					updateHandicapMember(password, landedTime, handicapMemberTService, handicapMemberT, hmSetTService);
					bean.setData(handicapMemberId);
					bean.success();
					return this.returnJson(bean);
				} else {
					bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return this.returnJson(bean);
				}
			}

			if (StringTool.notEmpty(handicapMemberId)) {
				handicapMemberT = handicapMemberTService.find(handicapMemberId);
				if (handicapMemberT == null) {
					bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return this.returnJson(bean);
				}
				if (IbmStateEnum.LOGIN.name().equals(handicapMemberT.getLoginState())) {
					// 已经登陆成功
					Map<String, Object> data = new HashMap<>(3);
					JSONObject hmInfo = new HandicapInfoController().execute(handicapMemberId).getJSONObject(0);
					data.put("hmInfo", hmInfo);
					bean.setData(data);
					bean.success();
					return this.returnJson(bean);
				}
				//修改盘口会员信息
				updateHandicapMember(password, null, handicapMemberTService, handicapMemberT, hmSetTService);
				if (StringTool.isEmpty(handicapMemberT.getHandicapItemId())) {
					//前端需要对这种情况进行处理	未存在盘口详情，输入网址验证码
					bean.setData(handicapMemberId);
					bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_ITEM);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					bean.setSuccess(true);
					return this.returnJson(bean);
				}
				//判断用户是否新用户登录
				if(IbmStateEnum.NEW.name().equals(handicapMemberT.getState())){
					//检验登录
					bean=new CheckLoginController().execute(handicapMemberId);
					if(!bean.isSuccess()){
						return returnJson(bean);
					}
					//检验登录成功后，修改用户状态
					handicapMemberTService.updateState(handicapMemberId);
					bean.setSuccess(false);
				}
				//启动mq
				bean = new LoginClientController().execute(handicapMemberId);
			} else {
				boolean flag = false;
				//查找数据库中该盘口会员是否存在
				handicapMemberT = handicapMemberTService.findByAccount(handicapId, appUserId, memberAccount);
				if (handicapMemberT == null) {
					//不存在账号——新增盘口会员信息
					insertHandicapMember(handicapId, handicapUserId, password, handicapMemberTService,hmSetTService);
				} else {
					//存在账号--更新数据
					updateHandicapMember(password, null, handicapMemberTService, handicapMemberT, hmSetTService);
					flag = StringTool.notEmpty(handicapMemberT.getHandicapItemId());
				}
				//已存在盘口详情id，直接启动mq
				if (flag) {
					//判断用户是否新用户登录
					if(IbmStateEnum.NEW.name().equals(handicapMemberT.getState())){
						//检验登录
						bean=new CheckLoginController().execute(handicapMemberId);
						if(!bean.isSuccess()){
							bean.setData(handicapMemberId);
							return returnJson(bean);
						}
						//检验登录成功后，修改用户状态
						handicapMemberTService.updateState(handicapMemberId);
						bean.setSuccess(false);
					}

					bean = new LoginClientController().execute(handicapMemberId);
				} else {
					//前端需要对这种情况进行处理	未存在盘口详情，输入网址验证码
					bean.setData(handicapMemberId);
					bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_ITEM);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					bean.setSuccess(true);
					return this.returnJson(bean);
				}
			}

			//登陆发送成功，且处理完成
			Map<String, Object> data = new HashMap<>(3);
			log.info("【盘口会员"+handicapMemberId+"】登录盘口【"+handicapCode+"】处理结果"+bean.toJsonString());
			if (bean.isSuccess()) {
				Object receiptId = bean.getData();
				//FINISH，已经登陆完成
				if (IbmCodeEnum.CODE_200.getCode().equals(bean.getCodeSys())) {
					if (StringTool.notEmpty(receiptId)) {
						IbmCloudReceiptTService receiptTService = new IbmCloudReceiptTService();
						String processResult = receiptTService.findProcessResult(receiptId.toString());
						//没有保存到盘口会员表中
						if (StringTool.isEmpty(processResult)) {
							bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
							bean.putSysEnum(IbmCodeEnum.CODE_404);
							bean.setSuccess(false);
							return returnJson(bean);
						}
						JSONObject jsonRe = JSONObject.fromObject(processResult);
						if (jsonRe.getBoolean("success")) {
							//同步盘口会员方案状态
							IbmPlanHmTService planHmTService = new IbmPlanHmTService();
							boolean exist=planHmTService.findByHmId(handicapMemberId);
							if(exist){
								ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil
										.findInstance().findLocal();
								ExecutorService executorService = threadExecutorService.findExecutorService();
								executorService.execute(new InitHandicapMemberThread(handicapMemberId));
							}

							JSONObject hmInfo = new HandicapInfoController().execute(handicapMemberId).getJSONObject(0);
							data.put("hmInfo", hmInfo);
						} else {
							data.put("message", jsonRe.get("data"));
						}
					} else {
						log.error("获取消息回执id失败");
					}
					//返回登录结果
				} else if (IbmCodeEnum.CODE_202.getCode().equals(bean.getCodeSys())) {
					data.put("receiptId", receiptId);
					data.put("handicapMemberId", handicapMemberId);
				}
				bean.setData(data);
				bean.success();
			}
			return this.returnJson(bean);
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			throw e;
		}
	}

	/**
	 * 新增盘口会员信息
	 */
	private void insertHandicapMember(String handicapId, String handicapUserId, String password,
			IbmPcHandicapMemberTService handicapMemberTService, IbmPcHmSetTService hmSetTService)
			throws Exception {
		IbmHandicapMemberT handicapMemberT = new IbmHandicapMemberT();
		handicapMemberT.setHandicapUserId(handicapUserId);
		handicapMemberT.setHandicapId(handicapId);
		handicapMemberT.setHandicapCode(handicapCode);
		handicapMemberT.setAppUserId(appUserId);
		handicapMemberT.setMemberAccount(memberAccount);
		handicapMemberT.setMemberPassword(password);
		handicapMemberT.setLoginState(IbmStateEnum.LOGOUT.name());
		handicapMemberT.setFrequency(0);
		handicapMemberT.setCreateTime(new Date());
		handicapMemberT.setCreateTimeLong(handicapMemberT.getCreateTime().getTime());
		handicapMemberT.setUpdateTime(new Date());
		handicapMemberT.setUpdateTimeLong(handicapMemberT.getUpdateTime().getTime());
		handicapMemberT.setState(IbmStateEnum.NEW.name());
		handicapMemberId = handicapMemberTService.save(handicapMemberT);
		//初始化配置信息
		initHandicapGameSet(handicapMemberId);
		initHandicapSet(handicapMemberId);
		//更新盘口会员配置信息
		hmSetTService.updateLoginSave(handicapMemberId, null, saveAccount);
		super.doTransactionPost();
	}

	/**
	 * 修改盘口会员信息
	 */
	private void updateHandicapMember(String password, Date landedTime,
			IbmPcHandicapMemberTService handicapMemberTService, IbmHandicapMemberT handicapMemberT,
			IbmPcHmSetTService hmSetTService) throws Exception {
		if (StringTool.notEmpty(memberAccount)) {
			handicapMemberT.setMemberAccount(memberAccount);
		}
		if (StringTool.notEmpty(password)) {
			handicapMemberT.setMemberPassword(password);
		}
		handicapMemberT.setUpdateTime(new Date());
		handicapMemberT.setUpdateTimeLong(handicapMemberT.getUpdateTime().getTime());
		handicapMemberTService.update(handicapMemberT);
		//更新盘口会员配置信息
		handicapMemberId = handicapMemberT.getIbmHandicapMemberId();
		hmSetTService.updateLoginSave(handicapMemberId, landedTime, saveAccount);
	}

	private boolean valiParameters() {

		//是否保存账号信息
		saveAccount = BeanThreadLocal.find(dataMap.get("SAVE_ACCOUNT_"), "FALSE");
		// 定时登陆时间
		landedTimeStr = BeanThreadLocal.find(dataMap.get("LANDED_TIME_"), "");

		// 盘口code
		handicapCode = BeanThreadLocal.find(dataMap.get("HANDICAP_CODE_"), "");
		if (StringTool.isEmpty(handicapCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_HANDICAP_CODE);
			return false;
		}
		//会员账号，会员密码,盘口会员id
		memberAccount = BeanThreadLocal.find(dataMap.get("MEMBER_ACCOUNT_"), "");
		memberPassword = BeanThreadLocal.find(dataMap.get("MEMBER_PASSWORD_"), "");
		handicapMemberId = BeanThreadLocal.find(dataMap.get("HANDICAP_MEMBER_ID_"), "");

		bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
		return StringTool.notEmpty(memberAccount, memberPassword) || StringTool.notEmpty(handicapMemberId);

	}

	/**
	 *
	 * @Description: 初始化盘口游戏设置
	 *
	 * 参数说明
	 * @param handicapMemberId 盘口会员ID
	 */
	private void initHandicapGameSet(String handicapMemberId) throws Exception {
		//根据盘口会员id获取盘口游戏信息
		IbmHandicapGameTService handicapGameTService = new IbmHandicapGameTService();
		List<Map<String, Object>> gameInfoList = handicapGameTService.listInfo4InitGameSet(handicapMemberId);
		//盘口不存在游戏
		if(ContainerTool.isEmpty(gameInfoList)){
			return ;
		}
		//已存在的游戏id列表
		IbmHmGameSetTService hmGameSetTService = new IbmHmGameSetTService();
		List<String> existedGameIds = hmGameSetTService.listHmGameId(handicapMemberId);

		//盘口游戏设置默认数据
		IbmHmGameSetT defSet = hmGameSetTService.findDefEntity();
		if(defSet==null){
			return;
		}
		Date nowTime = new Date();
		IbmHmGameSetT hmGameSetT;
		for (Map<String,Object> gameInfo : gameInfoList){
			String gameId = gameInfo.get("GAME_ID_").toString();
			//已存在,不进行初始化
			if (existedGameIds.contains(gameId)){
				continue;
			}
			hmGameSetT = new IbmHmGameSetT();
			hmGameSetT.setHandicapMemberId(handicapMemberId);
			hmGameSetT.setHandicapGameId(gameInfo.get("IBM_HANDICAP_GAME_ID_").toString());
			hmGameSetT.setHandicapId(gameInfo.get("HANDICAP_ID_").toString());
			hmGameSetT.setUserId(gameInfo.get("APP_USER_ID_").toString());
			hmGameSetT.setGameId(gameInfo.get("GAME_ID_").toString());
			hmGameSetT.setBetState(defSet.getBetState());
			hmGameSetT.setBetMode(defSet.getBetMode());
			hmGameSetT.setBetAutoStop(defSet.getBetAutoStop());
			hmGameSetT.setBetAutoStopTime(defSet.getBetAutoStopTime());
			hmGameSetT.setBetAutoStart(defSet.getBetAutoStart());
			hmGameSetT.setBetAutoStartTime(defSet.getBetAutoStartTime());
			hmGameSetT.setIncreaseState(defSet.getIncreaseState());
			hmGameSetT.setIncreaseStopTime(defSet.getIncreaseStopTime());
			hmGameSetT.setBetSecond(defSet.getBetSecond());
			hmGameSetT.setSplitTwoSideAmount(defSet.getSplitTwoSideAmount());
			hmGameSetT.setSplitNumberAmount(defSet.getSplitNumberAmount());
			hmGameSetT.setCreateTime(nowTime);
			hmGameSetT.setCreateTimeLong(nowTime.getTime());
			hmGameSetT.setUpdateTime(nowTime);
			hmGameSetT.setUpdateTimeLong(nowTime.getTime());
			hmGameSetT.setState(IbmStateEnum.OPEN.name());
			hmGameSetTService.save(hmGameSetT);

		}
	}


	/**
	 *
	 * @Description: 初始化盘口设置
	 *
	 * 参数说明
	 * @param handicapMemberId 盘口会员ID
	 */
	private void initHandicapSet(String handicapMemberId) throws Exception {
		Date nowTime = new Date();
		IbmHmSetTService ibmHmSetTService = new IbmHmSetTService();
		IbmHmSetT ibmHmSetT = ibmHmSetTService.findByHandicapMemberId(handicapMemberId);
		// 判断盘口会员设置是否存在
		if (ibmHmSetT == null) {
			IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
			Map<String, Object> idMap = handicapMemberTService.findIds(handicapMemberId);
			ibmHmSetT = ibmHmSetTService.findDefEntity();
			ibmHmSetT.setAppUserId(idMap.get("APP_USER_ID_"));
			ibmHmSetT.setHandicapMemberId(handicapMemberId);
			ibmHmSetT.setHandicapId(idMap.get("HANDICAP_ID_"));
			ibmHmSetT.setSaveAccount(IbmTypeEnum.FALSE.name());
			ibmHmSetT.setCreateTime(nowTime);
			ibmHmSetT.setCreateTimeLong(nowTime.getTime());
			ibmHmSetT.setUpdateTime(nowTime);
			ibmHmSetT.setUpdateTimeLong(nowTime.getTime());
			ibmHmSetT.setState(IbmStateEnum.OPEN.name());
			ibmHmSetTService.save(ibmHmSetT);
		}
	}

}
