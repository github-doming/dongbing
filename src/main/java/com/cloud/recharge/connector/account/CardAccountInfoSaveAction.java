package com.cloud.recharge.connector.account;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.common.tool.EncryptTool;
import com.cloud.recharge.card_admin.entity.CardAdmin;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_admin_price.entity.CardAdminPrice;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_operate_log.entity.CardOperateLog;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.recharge.card_tree.service.CardTreeService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import com.cloud.user.app_account.entity.AppAccount;
import com.cloud.user.app_account.service.AppAccountService;
import com.cloud.user.app_user.entity.AppUser;
import com.cloud.user.app_user.service.AppUserService;
import com.cloud.user.app_user_point.service.AppUserPointService;
import com.cloud.user.app_user_point_rep.service.AppUserPointRepService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保存充值卡管理员
 *
 * @Author: Dongming
 * @Date: 2020-06-18 14:27
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/account/save", method = HttpConfig.Method.POST)
public class CardAccountInfoSaveAction
		extends BaseUserAction {
	private String userAccount, userPassWord, aliasName;

	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		userAccount = StringTool.getString(dataMap, "accountName", "");
		userPassWord = StringTool.getString(dataMap, "password", "");
		aliasName = StringTool.getString(dataMap, "aliasName", "");
		String isAdd = StringTool.getString(dataMap, "isAdd", "false");
		String userState = StringTool.getString(dataMap, "userState", "false");
		if (StringTool.isEmpty(userAccount, userPassWord, userState)) {
			return bean.put401Data();
		}
		String regExpAccount = "[a-zA-Z0-9]{6,20}$";
		String regExpPwd = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}";
		if (!userAccount.matches(regExpAccount) || !userPassWord.matches(regExpPwd)) {
			bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
			bean.putSysEnum(ReturnCodeEnum.code409);
			return bean;
		}
		if (StringTool.isEmpty(aliasName)) {
			aliasName = userAccount;
		}
		try {
			// 加载代理资料
			if (user == null) {
				bean.putEnum(CodeEnum.CLOUD_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			//是否允许添加子代
			if (!user.getIsAdd()) {
				bean.putEnum(CodeEnum.CLOUD_403_PERMISSION);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			// 注册用户
			AppAccountService accountService = new AppAccountService();
			String isExistUser = accountService.findUserIdByName(userAccount);
			if (StringTool.notEmpty(isExistUser)) {
				bean.putEnum(ReturnCodeEnum.app409Register);
				bean.putSysEnum(ReturnCodeEnum.code409);
				return bean;
			}

			Date nowTime = new Date();
			String userType;
			int userLevel;
			if (CardTool.Type.ADMIN.getUserType().equals(user.getUserType())) {
				userType = CardTool.Type.PARTNER.getUserType();
				userId = CardTool.Type.ADMIN.name();
				userLevel = user.getUserLevel() + 20;
			} else {
				userType = CardTool.Type.AGENT.getUserType();
				if (CardTool.Type.PARTNER.getUserType().equals(user.getUserType())) {
					userLevel = user.getUserLevel() + 80;
				} else {
					userLevel = user.getUserLevel() + 1;
				}
			}

			String addUserId = initUser(accountService, userType);

			CardAdminService adminService = new CardAdminService();
			//添加充值卡用户信息
			CardAdmin cardAdmin = new CardAdmin();
			cardAdmin.setAppUserId(addUserId);
			cardAdmin.setUserName(userAccount);
			cardAdmin.setUserAlias(aliasName);
			cardAdmin.setUserType(userType);
			cardAdmin.setUserLevel(userLevel);

			cardAdmin.setParentUserId(userId);
			cardAdmin.setIsAdd(Boolean.parseBoolean(isAdd));
			cardAdmin.setUserState(userState);
			cardAdmin.setCreateTime(nowTime);
			cardAdmin.setCreateTimeLong(System.currentTimeMillis());
			cardAdmin.setUpdateTime(nowTime);
			cardAdmin.setUpdateTimeLong(System.currentTimeMillis());
			cardAdmin.setState(StateEnum.OPEN.name());
			String cardAdminId = adminService.save(cardAdmin);

			cardAdmin.setCardAdminId(cardAdminId);
			cardAdmin.setUserPath(user.getUserPath().concat(cardAdminId).concat("."));
			adminService.update(cardAdmin);

			// 初始化价格信息
			CardAdminPriceService adminPriceService = new CardAdminPriceService();
			List<Map<String, Object>> cardTreeInfos;
			if (CardTool.Type.PARTNER.getUserType().equals(userType)) {
				CardTreeService cardTreeService = new CardTreeService();
				cardTreeInfos = cardTreeService.listDefPartnerInfo();
			} else {
				cardTreeInfos = adminPriceService.listDefAgentInfo(userId);
			}
			for (Map<String, Object> cardTreeInfo : cardTreeInfos) {
				CardAdminPrice adminPrice = new CardAdminPrice();
				adminPrice.setCardTreeId(cardTreeInfo.get("CARD_TREE_ID_"));
				adminPrice.setCardAdminId(cardAdminId);
				adminPrice.setUserId(userId);
				adminPrice.setSubUserId(addUserId);
				adminPrice.setCardPriceT(cardTreeInfo.get("CARD_PRICE_T_"));
				adminPrice.setCreateTime(nowTime);
				adminPrice.setCreateTimeLong(System.currentTimeMillis());
				adminPrice.setState(StateEnum.OPEN.name());
				adminPriceService.save(adminPrice);
				//添加子用户默认
				adminPrice.setCardAdminPriceId(null);
				adminPrice.setUserId(addUserId);
				adminPrice.setSubUserId(StateEnum.DEF.name());
				adminPriceService.save(adminPrice);
				cardTreeInfo.put("state", StateEnum.OPEN.name());
			}
			// 保存操作log
			saveLog(nowTime);
			bean.success(cardTreeInfos);
		} catch (Exception e) {
			log.error("保存充值卡管理员出错");
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	private void saveLog(Date nowTime) throws Exception {
		String format ="新增用户{%s}";
		CardOperateLog log = new CardOperateLog();
		log.setUserId(user.getAppUserId());
		log.setUserName(user.getUserName());
		log.setUserPath(user.getUserPath());
		log.setOpertType("ADD");
		log.setIp(findServletIp());
		log.setOpertContent(String.format(format,userAccount));
		log.setCreateTime(nowTime);
		log.setCreateTimeLong(System.currentTimeMillis());
		log.setUpdateTime(nowTime);
		log.setUpdateTimeLong(System.currentTimeMillis());
		log.setState(StateEnum.OPEN.name());
		new CardOperateLogService().save(log);
	}

	private String initUser(AppAccountService accountService, String userType) throws Exception {
		Date date = new Date();
		AppUser appUser = new AppUser();
		appUser.setAppUserName(userAccount);
		appUser.setNickname(aliasName);
		appUser.setAppUserType(ChannelTypeEnum.ADMIN.name());
		appUser.setAppUserCode(userType);
		appUser.setCreateTime(date);
		appUser.setCreateTimeLong(System.currentTimeMillis());
		appUser.setUpdateTime(date);
		appUser.setUpdateTimeLong(System.currentTimeMillis());
		appUser.setState(StateEnum.OPEN.name());
		appUser.setTenantCode("Card");
		String appUserId = new AppUserService().save(appUser);

		//保存会员账户
		AppAccount appAccount = new AppAccount();
		userPassWord = EncryptTool.encode(EncryptTool.Type.ASE, userPassWord);
		appAccount.setAccountName(userAccount);
		appAccount.setPassword(userPassWord);
		appAccount.setAppUserId(appUserId);
		appAccount.setChannelType(userType);
		appAccount.setRegisterType(ChannelTypeEnum.ADMIN.name());
		appAccount.setCreateTime(date);
		appAccount.setCreateTimeLong(System.currentTimeMillis());
		appAccount.setUpdateTime(date);
		appAccount.setUpdateTimeLong(System.currentTimeMillis());
		appAccount.setState(StateEnum.OPEN.name());
		accountService.save(appAccount);

		if ("USER".equals(userType)) {
			//初始化点数
			String repId = new AppUserPointRepService().save(new HashMap<>(2), appUserId, 0, "初始化会员", date);
			new AppUserPointService().save(appUserId, repId, 0, date);
		}
		return appUserId;
	}
}
