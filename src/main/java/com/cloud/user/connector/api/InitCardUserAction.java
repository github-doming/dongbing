package com.cloud.user.connector.api;

import c.a.util.core.enums.ReturnCodeEnum;
import com.cloud.common.core.BaseApiAction;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.entity.CardAdmin;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_admin_price.entity.CardAdminPrice;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_tree.service.CardTreeService;
import com.cloud.recharge.connector.core.CardTool;
import com.cloud.user.app_token.service.AppTokenService;
import com.cloud.user.app_user.entity.AppUser;
import com.cloud.user.app_user.service.AppUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 初始化 开卡用户信息  ibs/ibm 开卡平台新增操作员时调用 调用者ADMIN
 * @Author: admin1
 * @Date: 2020/7/8 15:26
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/api/initCardUser", method = HttpConfig.Method.GET)
public class InitCardUserAction
		extends BaseApiAction {
	@Override
	protected Object execute() throws Exception {

		String addUserId = StringTool.trimAll(StringTool.getString(jsonData, "userId", ""));
		String token = StringTool.trimAll(StringTool.getString(jsonData, "token", ""));
		String userType = StringTool.trimAll(StringTool.getString(jsonData, "userType", ""));

		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(addUserId, token)) {
			return bean.put401Data();
		}

		try {
			String valiUserId = new AppTokenService().findUserIdByToken(token);
			if (StringTool.isEmpty(valiUserId)) {
				bean.putEnum(ReturnCodeEnum.app401Token);
				bean.putSysEnum(ReturnCodeEnum.code401);
				return bean;
			}
			CardAdminService cardAdminService = new CardAdminService();
			CardAdmin admin = cardAdminService.findUserByUserId(valiUserId);
			if(admin == null){
				bean.putEnum(ReturnCodeEnum.app401Token);
				bean.putSysEnum(ReturnCodeEnum.code401);
				return bean;
			}

			CardAdminPriceService adminPriceService = new CardAdminPriceService();
			List<Map<String, Object>> cardTreeInfos = new ArrayList<>();

			CardTool.Type adminType= CardTool.Type.getType(userType);

			int userLevel;
			if (CardTool.Type.ADMIN.name().equals(adminType.name())) {
				userLevel =  1;
			} else {
				CardTreeService cardTreeService = new CardTreeService();
				if (CardTool.Type.PARTNER.name().equals(adminType.name())) {
					userLevel =  20;
					cardTreeInfos = cardTreeService.listDefPartnerInfo();
				} else {
					userLevel =  80;
					cardTreeInfos = cardTreeService.listDefAgentInfo();
				}
			}


			Date nowTime = new Date();
			AppUser user = new AppUserService().find(addUserId);
			//添加充值卡用户信息
			CardAdmin cardAdmin = new CardAdmin();
			cardAdmin.setAppUserId(user.getAppUserId());
			cardAdmin.setUserName(user.getAppUserName());
			cardAdmin.setUserAlias(user.getNickname());
			cardAdmin.setUserType(adminType.getUserType());
			cardAdmin.setUserLevel(userLevel);
			cardAdmin.setUserPath(admin.getUserPath().concat(admin.getCardAdminId().toString()).concat("."));
			cardAdmin.setParentUserId(CardTool.Type.ADMIN.name());
			cardAdmin.setIsAdd(true);
			cardAdmin.setUserState(StateEnum.OPEN.name());
			cardAdmin.setCreateTime(nowTime);
			cardAdmin.setCreateTimeLong(System.currentTimeMillis());
			cardAdmin.setUpdateTime(nowTime);
			cardAdmin.setUpdateTimeLong(System.currentTimeMillis());
			cardAdmin.setState(StateEnum.OPEN.name());
			String cardAdminId = cardAdminService.save(cardAdmin);

			// 如果新建的是管理员用户不需要卡种价格信息
			if(!CardTool.Type.ADMIN.name().equalsIgnoreCase(adminType.name())){
				// 初始化价格信息
				for (Map<String, Object> cardTreeInfo : cardTreeInfos) {
					CardAdminPrice adminPrice = new CardAdminPrice();
					adminPrice.setCardTreeId(cardTreeInfo.remove("IBM_CARD_TREE_ID_"));
					adminPrice.setCardTreeId(cardTreeInfo.remove("CARD_TREE_ID_"));
					adminPrice.setCardAdminId(cardAdminId);
					adminPrice.setUserId(CardTool.Type.ADMIN.name());
					adminPrice.setSubUserId(addUserId);
					adminPrice.setCardPriceT(cardTreeInfo.remove("CARD_PRICE_T_"));
					adminPrice.setCreateTime(nowTime);
					adminPrice.setCreateTimeLong(System.currentTimeMillis());
					adminPrice.setState(StateEnum.OPEN.name());
					adminPriceService.save(adminPrice);
					//添加子用户默认
					adminPrice.setCardAdminPriceId(null);
					adminPrice.setUserId(addUserId);
					adminPrice.setSubUserId(StateEnum.DEF.name());
					adminPriceService.save(adminPrice);
				}
			}

		} catch (Exception e) {
			log.error("注册用户失败，{}", e.getMessage());
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}


}
