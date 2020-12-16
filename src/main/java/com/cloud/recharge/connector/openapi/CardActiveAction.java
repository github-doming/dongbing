package com.cloud.recharge.connector.openapi;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.BaseMvcData;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_operate_log.entity.CardOperateLog;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.recharge.card_recharge.service.CardRechargeService;
import com.cloud.recharge.card_recharge_daily.service.CardRechargeDailyService;
import com.cloud.recharge.card_used_log.service.CardUsedLogService;
import com.cloud.recharge.card_user_relation.service.CardUserRelationService;
import com.cloud.recharge.connector.core.CardTool;
import com.cloud.user.app_token.service.AppTokenService;
import com.cloud.user.app_user.service.AppUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * 充值卡激活对外接口
 *
 * @Author: Dongming
 * @Date: 2020-06-22 15:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/out/cardActive", method = HttpConfig.Method.POST)
public class CardActiveAction extends BaseMvcData {
	private static final String OPERT_CONTENT_FORMAT = "{useUserId:%s,userName:%s,cardId:%s,cardPassword:%s}";
	private String ownerUserId;

	@Override
	public Object run() throws Exception {
		super.findJson();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JSONObject jsonData = JSON.parseObject(json);
		String token = StringTool.trimAll(StringTool.getString(jsonData, "token", ""));
		String channelType = StringTool.trimAll(StringTool.getString(jsonData, "channelType", ""));
		String name = StringTool.trimAll(StringTool.getString(jsonData, "name", ""));
		String cardPassword = StringTool.trimAll(StringTool.getString(jsonData, "cardPassword", ""));
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(token, channelType, name, cardPassword)) {
			return bean.put401Data();
		}
		try {
			//校验充值卡是否可以使用
			CardRechargeService rechargeService = new CardRechargeService();
			Map<String, Object> cardInfo = rechargeService.findInfo4Active(cardPassword);
			if (ContainerTool.isEmpty(cardInfo)) {
				bean.putEnum(CodeEnum.CLOUD_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			//验证用户是否存在
			String useUserId = new AppTokenService().findUserIdByToken(token, channelType);
			if (StringTool.isEmpty(useUserId)) {
				bean.putEnum(CodeEnum.CLOUD_403_PERMISSION);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			Map<String, Object> info = new AppUserService().findInfoByName(useUserId, name);
			if (!StateEnum.OPEN.name().equals(info.get("STATE_"))) {
				bean.putEnum(ReturnCodeEnum.app404LoginDisable);
				bean.putSysEnum(ReturnCodeEnum.code404);
				return bean;
			}
			String useUserName = info.get("NICKNAME_").toString();
			String dailyTime = CardTool.dailyTime();
			Date nowTime = new Date();
			String cardId = cardInfo.get("CARD_RECHARGE_ID_").toString();
			ownerUserId = cardInfo.get("OWNER_USER_ID_").toString();


			//激活
			rechargeService.updateActive(cardId, useUserId, useUserName,nowTime);

			// 保存操作log
			saveLog(nowTime,cardInfo.get("OWNER_NAME_").toString(),useUserName,cardPassword,cardInfo.get("CARD_TREE_NAME_").toString(),channelType);

			//保存关联信息
			new CardUsedLogService().save(useUserId, ownerUserId, cardId, nowTime);
			new CardUserRelationService().updateRelation(ownerUserId, useUserId, useUserName, nowTime);
			int point = NumberTool.getInteger(cardInfo.get("CARD_TREE_POINT_"));
			long priceT = NumberTool.getLong(cardInfo.get("CARD_PRICE_T_"));

			//记录报表信息 - 自己产生的卡
			if (ownerUserId.equals(cardInfo.get("CREATE_USER_ID_"))||CardTool.Type.ADMIN.name().equals(ownerUserId)) {
				recordDaily(new CardRechargeDailyService(), new CardAdminPriceService(),
						cardInfo.get("CARD_TREE_ID_").toString(), cardInfo.get("CARD_TREE_NAME_").toString(), ownerUserId,
						dailyTime, point, priceT, nowTime);
			}
			//给用户写入点数
			double pointD = CardTool.activateCardPassword(cardPassword, useUserId, point);

			bean.success(pointD);
		} catch (Exception e) {
			log.error("充值卡激活对外接口出错",e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	private void saveLog(Date nowTime,String ownerName,String useUserName,String cardPassword,String cardName,String channelType) throws Exception {
		Map<String, Object> userInfo = new CardAdminService().findUserInfo(ownerUserId,ownerName);
		String fmt = "用户{%s}在{%s}平台激活充值卡{%s},卡密:{%s}}";
		CardOperateLog log = new CardOperateLog();
		log.setUserId(ownerUserId);
		log.setUserName(userInfo.get("USER_NAME_"));
		log.setUserPath(userInfo.get("USER_PATH_"));
		log.setOpertType("ACTIVE");
		log.setIp(findServletIp());
		log.setOpertContent(String.format(fmt,useUserName,channelType,cardName,cardPassword));
		log.setCreateTime(nowTime);
		log.setCreateTimeLong(System.currentTimeMillis());
		log.setUpdateTime(nowTime);
		log.setUpdateTime(System.currentTimeMillis());
		log.setState(StateEnum.OPEN.name());
		new CardOperateLogService().save(log);
	}

	/**
	 * 写入报表 - 给上级也写入报表
	 */
	private void recordDaily(CardRechargeDailyService rechargeDailyService, CardAdminPriceService adminPriceService,
							 String cardTreeId, String cardTreeName, String userId, String dailyTime, int point, long downPriceT,
							 Date nowTime) throws SQLException {
		//不存在上级或者上级为管理员
		if (StringTool.isEmpty(userId)) {
			return;
		}
		Map<String, Object> info = adminPriceService.findInfo4Record(cardTreeId, userId);
		//不存在的用户
		if (ContainerTool.isEmpty(info)) {
			return;
		}
		Long priceT = NumberTool.getLong(info.get("CARD_PRICE_T_"));
		if(ownerUserId.equals(userId)){
			rechargeDailyService.recordDaily(cardTreeId, cardTreeName, userId, info.get("USER_NAME_").toString(),
					dailyTime, 0, 1, point, priceT, downPriceT, nowTime,"card_recharge_daily");
		}
		rechargeDailyService.recordDaily(cardTreeId, cardTreeName, userId, info.get("USER_NAME_").toString(),
				dailyTime, 0, 1, point, priceT, downPriceT, nowTime,"card_recharge_daily_sum");
		if (CardTool.Type.ADMIN.getUserType().equals(info.get("USER_TYPE_"))) {
			return;
		}
		recordDaily(rechargeDailyService, adminPriceService, cardTreeId, cardTreeName, info.get("PARENT_USER_ID_").toString(),
				dailyTime, point, priceT, nowTime);
	}
}
