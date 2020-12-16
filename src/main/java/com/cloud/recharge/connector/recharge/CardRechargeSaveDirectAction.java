package com.cloud.recharge.connector.recharge;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_operate_log.entity.CardOperateLog;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.recharge.card_recharge.entity.CardRecharge;
import com.cloud.recharge.card_recharge.service.CardRechargeService;
import com.cloud.recharge.card_recharge_daily.service.CardRechargeDailyService;
import com.cloud.recharge.card_tree.service.CardTreeService;
import com.cloud.recharge.card_used_log.service.CardUsedLogService;
import com.cloud.recharge.card_user_relation.service.CardUserRelationService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import com.cloud.user.app_user.service.AppUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
/**
 * 新增充值卡--用户直充
 *
 * @Author: Dongming
 * @Date: 2020-06-19 18:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/direct", method = HttpConfig.Method.POST)
public class CardRechargeSaveDirectAction extends BaseUserAction {
	@Override public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String directUserId = StringTool.getString(dataMap, "directUserId", "");
		String directUserName = StringTool.getString(dataMap, "directUserName", "");
		String cardTreeId = StringTool.getString(dataMap, "cardTreeId", "");
		String desc = StringTool.getString(dataMap, "desc", "");
		if (StringTool.isEmpty(directUserId, cardTreeId, directUserName)) {
			return bean.put401Data();
		}
		try {
			// 加载卡种资料
			Map<String, Object> cardTreeInfo = new CardTreeService().findCreateCardInfo(cardTreeId);
			if (CardTool.checkCard(bean, cardTreeInfo,user.getUserType())){
				return bean;
			}

			//1.验证用户是否存在
			Map<String, Object> usedInfo = new AppUserService().findInfoByName(directUserId, directUserName, "USER");
			String nickName = usedInfo.get("NICKNAME_").toString();
			String subUserIds=userId;
			Long priceT;
			if (CardTool.Type.ADMIN.getUserType().equals(user.getUserType())) {
				priceT = NumberTool.getLong(cardTreeInfo, "CARD_PRICE_T_", 0L);
				subUserIds = CardTool.Type.ADMIN.name();
			} else {
				// 查询上级给用户设置的卡种价格信息 - 没有找到
				priceT = new CardAdminPriceService().findCardPriceT(cardTreeId, userId);
				if (priceT == null) {
					bean.putEnum(CodeEnum.CLOUD_404_DATA);
					bean.putSysEnum(CodeEnum.CODE_404);
					return bean;
				}
			}
			String dailyTime = CardTool.dailyTime();

			Date nowTime = new Date();
			String cardPassword = RandomTool.getNumLetter32();
			CardRecharge cardRecharge = new CardRecharge();
			cardRecharge.setCardTreeId(cardTreeId);
			cardRecharge.setOwneUserId(subUserIds);
			cardRecharge.setOwnerName(user.getUserName());
			cardRecharge.setUseUserId(directUserId);
			cardRecharge.setUserName(nickName);
			cardRecharge.setCardPassword(cardPassword);
			cardRecharge.setCardTreeName("直充");
			cardRecharge.setCardTreePoint(cardTreeInfo.get("CARD_TREE_POINT_"));
			cardRecharge.setCardPriceT(priceT);
			cardRecharge.setCardRechargeState(CardTool.State.ACTIVATE.name());
			cardRecharge.setCreateUserId(userId);
			cardRecharge.setCreatorName(user.getUserName());
			cardRecharge.setCreateTime(nowTime);
			cardRecharge.setCreateTimeLong(System.currentTimeMillis());
			cardRecharge.setUpdateTimeLong(System.currentTimeMillis());
			cardRecharge.setState(StateEnum.OPEN.name());
			cardRecharge.setDesc(desc);
			String cardId = new CardRechargeService().save(cardRecharge);

			//3.使用卡
			recordDaily(new CardRechargeDailyService(), new CardAdminPriceService(), cardTreeId,
					cardTreeInfo.get("CARD_TREE_NAME_").toString(), userId, dailyTime, cardRecharge.getCardTreePoint(),
					 nowTime);
			//4.记录使用信息

			new CardUsedLogService().save(directUserId, userId, cardId, nowTime);
			new CardUserRelationService().updateRelation(userId, directUserId, nickName, nowTime);

			//4.给用户写入点数
			CardTool.activateCardPassword(cardPassword, directUserId, cardRecharge.getCardTreePoint());

			saveLog(nowTime,directUserName,cardTreeInfo.get("CARD_TREE_NAME_").toString());
			bean.success();
		} catch (Exception e) {
			log.error("新增充值卡--用户直充出错",e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
	private void recordDaily(CardRechargeDailyService rechargeDailyService, CardAdminPriceService adminPriceService,
							 String cardTreeId, String cardTreeName, String adminUserId, String dailyTime, Integer point,
							 Date nowTime) throws SQLException {
		//不存在上级或者上级为管理员
		if (StringTool.isEmpty(adminUserId)) {
			return;
		}
		Map<String, Object> info = adminPriceService.findInfo4Record(cardTreeId, adminUserId);
		//不存在的用户
		if (ContainerTool.isEmpty(info)) {
			return;
		}
		Long priceT = NumberTool.getLong(info.get("CARD_PRICE_T_"));
		if(userId.equals(adminUserId)){
			rechargeDailyService.recordDaily(cardTreeId, cardTreeName, adminUserId, info.get("USER_NAME_").toString(),
					dailyTime, 1, 1, point, priceT, priceT, nowTime,"card_recharge_daily");
		}
		rechargeDailyService.recordDaily(cardTreeId, cardTreeName, adminUserId, info.get("USER_NAME_").toString(),
				dailyTime, 1, 1, point, priceT, priceT, nowTime,"card_recharge_daily_sum");
		if (CardTool.Type.ADMIN.getUserType().equals(info.get("USER_TYPE_"))) {
			rechargeDailyService.recordDaily(cardTreeId, cardTreeName, CardTool.Type.ADMIN.name(), CardTool.Type.ADMIN.name(),
					dailyTime, 1, 1, point, priceT, priceT, nowTime,"card_recharge_daily_sum");
			return;
		}
		recordDaily(rechargeDailyService, adminPriceService, cardTreeId, cardTreeName,
				info.get("PARENT_USER_ID_").toString(), dailyTime, point, nowTime);
	}

	private void saveLog(Date nowTime,String userName,String msg) throws Exception {
		String format ="给平台用户{%s}直充{%s}";
		CardOperateLog log = new CardOperateLog();
		log.setUserId(user.getAppUserId());
		log.setUserName(user.getUserName());
		log.setUserPath(user.getUserPath());
		log.setOpertType("RECHARGE");
		log.setIp(findServletIp());
		log.setOpertContent(String.format(format,userName,msg));
		log.setCreateTime(nowTime);
		log.setCreateTimeLong(System.currentTimeMillis());
		log.setUpdateTime(nowTime);
		log.setUpdateTimeLong(System.currentTimeMillis());
		log.setState(StateEnum.OPEN.name());
		new CardOperateLogService().save(log);
	}
}
