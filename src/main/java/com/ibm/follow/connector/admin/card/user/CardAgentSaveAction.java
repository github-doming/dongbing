package com.ibm.follow.connector.admin.card.user;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_card_admin.entity.IbmCardAdmin;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.entity.IbmCardAdminPrice;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.service.IbmCardAdminPriceService;
import com.ibm.follow.servlet.cloud.ibm_card_tree.service.IbmCardTreeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 保存充值卡管理员
 *
 * @Author: Dongming
 * @Date: 2020-04-11 11:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/agent/save")
public class CardAgentSaveAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String userAccount = StringTool.getString(dataMap.get("accountName"), "");
		String userPassWord = StringTool.getString(dataMap.get("password"), "");
		String nikeName = StringTool.getString(dataMap.get("nikeName"), "false");
		String isAdd = StringTool.getString(dataMap.get("isAdd"), "false");
		String userState = StringTool.getString(dataMap.get("userState"), "false");
		if (StringTool.isEmpty(userAccount, userPassWord,userState)) {
			bean.putEnum(IbmCodeEnum.IBM_404_EXIST);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean.toJsonString();
		}
		String regExpAccount = "[a-zA-Z0-9]{6,20}$";
		String regExpPwd = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}";
		if(!userAccount.matches(regExpAccount) || !userPassWord.matches(regExpPwd)){
			bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
			bean.putSysEnum(ReturnCodeEnum.code409);
			return bean;
		}
		try {
			IbmCardAdminService cardAdminService = new IbmCardAdminService();

			// 加载代理资料
			Map<String, Object> adminInfo = cardAdminService.findAdminInfo(adminUserId);
			if (ContainerTool.isEmpty(adminInfo)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			//是否允许添加子代
			if (!Boolean.parseBoolean(StringTool.getString(adminInfo, "IS_ADD_", "FALSE"))) {
				bean.putEnum(IbmCodeEnum.IBM_403_PERMISSION);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			String state = "false".equals(userState) ? IbmStateEnum.OPEN.name() : IbmStateEnum.CLOSE.name();
			AuthorityService authorityService = new AuthorityService();
			authorityService.saveUser(bean, userAccount,nikeName, userPassWord, getTenant(),state);
			if (!bean.isSuccess()) {
				return bean;
			}
			String userId = bean.getData().toString();

			/*
				1. 判断管理用户类型，添加角色关联信息
					如果是管理员的话，那么去寻找股东的角色ID，添加角色关联信息
					如果是股东的话，那么去寻找代理的角色ID，添加角色关联信息
					如果是代理的话，那么去寻找代理的角色ID，添加角色关联信息
				2. 添加充值卡用户信息
				3. 给子用户添加充值卡信息
					子用户为股东
					子用户为代理

			 */
			//获取角色类型
			Date nowTime = new Date();
			String userType;
			String userName = adminUser.getUserName();
			if (IbmTypeEnum.CARD_ADMIN.equal(adminInfo.get("USER_TYPE_"))) {
				userType = IbmTypeEnum.CARD_PARTNER.name();
				userName = IbmTypeEnum.ADMIN.name();
				adminUserId = IbmTypeEnum.ADMIN.name();
			} else {
				userType = IbmTypeEnum.CARD_AGENT.name();
			}

			//保存角色信息
			authorityService.saveUserRole(userId, authorityService.findRoleId(userType), adminUserId, nowTime);

			//添加充值卡用户信息
			IbmCardAdmin cardAdmin = new IbmCardAdmin();
			cardAdmin.setAppUserId(userId);
			cardAdmin.setUserName(userAccount);
			cardAdmin.setUserType(userType);
			cardAdmin.setParentUserId(adminUserId);
			cardAdmin.setParentUserName(userName);
			cardAdmin.setIsAdd(Boolean.parseBoolean(isAdd));
			cardAdmin.setCreateTime(nowTime);
			cardAdmin.setCreateTimeLong(System.currentTimeMillis());
			cardAdmin.setState(state);
			new IbmCardAdminService().save(cardAdmin);

			// 初始化价格信息
			IbmCardAdminPriceService adminPriceService = new IbmCardAdminPriceService();
			List<Map<String, Object>> cardTreeInfos;
			System.out.println(IbmTypeEnum.CARD_PARTNER.name());
			if (IbmTypeEnum.CARD_PARTNER.name().equals(userType)) {
				IbmCardTreeService cardTreeService = new IbmCardTreeService();
				cardTreeInfos = cardTreeService.listAddPartnerInfo();
			} else {
				cardTreeInfos = adminPriceService.listAddAgentInfo(adminUserId);
			}
			for (Map<String, Object> cardTreeInfo : cardTreeInfos) {
				IbmCardAdminPrice adminPrice = new IbmCardAdminPrice();
				adminPrice.setCardTreeId(cardTreeInfo.get("IBM_CARD_TREE_ID_"));
				adminPrice.setUserId(adminUserId);
				adminPrice.setUserName(userName);
				adminPrice.setSubUserId(userId);
				adminPrice.setCardTreeName(cardTreeInfo.get("CARD_TREE_NAME_"));
				adminPrice.setCardPriceT(cardTreeInfo.get("CARD_PRICE_T_"));
				adminPrice.setCreateTime(nowTime);
				adminPrice.setCreateTimeLong(System.currentTimeMillis());
				adminPrice.setState(IbmStateEnum.OPEN.name());
				adminPriceService.save(adminPrice);

				adminPrice.setIbmCardAdminPriceId(null);
				adminPrice.setUserId(userId);
				adminPrice.setUserName(userAccount);
				adminPrice.setSubUserId(IbmStateEnum.DEF.name());
				adminPriceService.save(adminPrice);


				cardTreeInfo.put("state", IbmStateEnum.OPEN.name());
			}
			bean.success(cardTreeInfos);
		} catch (Exception e) {
			log.error("保存充值卡管理员出错", e);
			throw e;
		}
		return bean;
	}

}
