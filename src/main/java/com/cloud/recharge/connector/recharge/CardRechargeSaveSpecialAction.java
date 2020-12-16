package com.cloud.recharge.connector.recharge;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_operate_log.entity.CardOperateLog;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.recharge.card_recharge.entity.CardRecharge;
import com.cloud.recharge.card_recharge.service.CardRechargeService;
import com.cloud.recharge.card_recharge_daily.service.CardRechargeDailyService;
import com.cloud.recharge.card_tree.service.CardTreeService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.*;

/**
 * 新增充值卡--特殊开卡
 * 会直接记录开卡积分，开卡人的报表会直接写，接收者不写报表，直接在表中读取。
 *
 * @Author: Dongming
 * @Date: 2020-06-22 11:49
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/special", method = HttpConfig.Method.POST)
public class CardRechargeSaveSpecialAction extends BaseUserAction {
	@Override public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String cardTreeId = StringTool.getString(dataMap, "cardTreeId", "");
		Integer cardNum = NumberTool.getInteger(dataMap, "cardNum", 0);
		String desc = StringTool.getString(dataMap, "desc", "");
		String agentId = StringTool.getString(dataMap, "agentId", "");
		double cardPrice = NumberTool.getDouble(dataMap.get("cardPrice"), 0);
		if (StringTool.isEmpty(cardTreeId, agentId)) {
			return bean.put401Data();
		}
		if (cardNum < 1 || cardNum > 10) {
			return bean.put401Data();
		}

		try {
			//加载被开卡人信息
			CardAdminService adminService = new CardAdminService();
			Map<String, Object> agentInfo = adminService.findInfo4Record(agentId);
			if (ContainerTool.isEmpty(agentInfo)) {
				bean.putEnum(CodeEnum.CLOUD_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			// 加载卡种资料
			CardTreeService cardTreeService = new CardTreeService();
			Map<String, Object> cardTreeInfo = cardTreeService.findCreateCardInfo(cardTreeId);
			if (CardTool.checkCard(bean, cardTreeInfo,user.getUserType())){
				return bean;
			}
			CardAdminPriceService adminPriceService = new CardAdminPriceService();
			Long priceT;
			if (cardPrice != 0) {
				priceT = NumberTool.longValueT(cardPrice);
			} else {
				if (CardTool.Type.ADMIN.getUserType().equals(user.getUserType())) {
					priceT = NumberTool.getLong(cardTreeInfo, "CARD_PRICE_T_", 0L);
				} else {
					// 查询上级给用户设置的卡种价格信息 - 没有找到
					priceT = adminPriceService.findCardPriceT(cardTreeId, userId);
					if (priceT == null) {
						bean.putEnum(CodeEnum.CLOUD_404_DATA);
						bean.putSysEnum(CodeEnum.CODE_404);
						return bean;
					}
				}
			}
//			if (CardTool.Type.ADMIN.getUserType().equals(user.getUserType())) {
//				userId = CardTool.Type.ADMIN.name();
//			}
			String dailyTime = CardTool.dailyTime();
			Date nowTime = new Date();
			Map<String, String> cardPasswordInfo = new HashMap<>(cardNum);
			CardRechargeService cardRechargeService = new CardRechargeService();
			int point = NumberTool.getInteger(cardTreeInfo.get("CARD_TREE_POINT_"));
			String cardTreeName = cardTreeInfo.get("CARD_TREE_NAME_").toString();
			List<String> pwdList = new ArrayList<>();
			for (int i = 0; i < cardNum; i++) {
				String cardPassword = RandomTool.getNumLetter32();
				pwdList.add(cardPassword);
				CardRecharge cardRecharge = new CardRecharge();
				cardRecharge.setCardTreeId(cardTreeId);
				cardRecharge.setOwneUserId(agentId);
				cardRecharge.setOwnerName(agentInfo.get("USER_NAME_"));
				cardRecharge.setCardPassword(cardPassword);
				cardRecharge.setCardTreeName(cardTreeName);
				cardRecharge.setCardTreePoint(point);
				cardRecharge.setCardPriceT(priceT);
				cardRecharge.setCardRechargeState(CardTool.State.OPEN.name());
				cardRecharge.setCreateUserId(userId);
				cardRecharge.setCreatorName(user.getUserName());
				cardRecharge.setCreateTime(nowTime);
				cardRecharge.setCreateTimeLong(System.currentTimeMillis());
				cardRecharge.setUpdateTimeLong(System.currentTimeMillis());
				cardRecharge.setState(StateEnum.OPEN.name());
				cardRecharge.setDesc(desc);
				String cardId = cardRechargeService.save(cardRecharge);
				cardPasswordInfo.put(cardPassword, cardId);
			}

			// 写入报表
			recordDaily(new CardRechargeDailyService(), adminPriceService, cardTreeId, cardTreeName, userId, dailyTime,
					cardPasswordInfo.size(), point, priceT, nowTime);

			saveLog(nowTime,agentInfo.get("USER_NAME_"),cardTreeInfo.get("CARD_TREE_NAME_"),cardNum,NumberTool.doubleT(priceT));
			bean.success(pwdList);
		} catch (Exception e) {
			log.error("新增充值卡--特殊开卡出错",e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	private void saveLog(Date nowTime,Object userName,Object cardName,int cardNum,double priceT) throws Exception {
		String format ="给用户{%s}特殊开卡{%s,%s张},总价{%s}";
		CardOperateLog log = new CardOperateLog();
		log.setUserId(user.getAppUserId());
		log.setUserName(user.getUserName());
		log.setUserPath(user.getUserPath());
		log.setOpertType("RECHARGE");
		log.setIp(findServletIp());
		log.setOpertContent(String.format(format,userName,cardName,cardNum,cardNum*priceT));
		log.setCreateTime(nowTime);
		log.setCreateTimeLong(System.currentTimeMillis());
		log.setUpdateTime(nowTime);
		log.setUpdateTimeLong(System.currentTimeMillis());
		log.setState(StateEnum.OPEN.name());
		new CardOperateLogService().save(log);
	}

	/**
	 * 写入报表 - 给上级也写入报表
	 */
	private void recordDaily(CardRechargeDailyService rechargeDailyService, CardAdminPriceService adminPriceService,
			String cardTreeId, String cardTreeName, String adminUserId, String dailyTime, int size, int point,
			Long downPriceT, Date nowTime) throws SQLException {
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
					dailyTime, size,size, point, priceT, downPriceT, nowTime,"card_recharge_daily");
		}
		rechargeDailyService.recordDaily(cardTreeId, cardTreeName, adminUserId, info.get("USER_NAME_").toString(),
				dailyTime, size,size, point, priceT, downPriceT, nowTime,"card_recharge_daily_sum");
		if (CardTool.Type.ADMIN.getUserType().equals(info.get("USER_TYPE_"))) {
			rechargeDailyService.recordDaily(cardTreeId, cardTreeName, CardTool.Type.ADMIN.name(), CardTool.Type.ADMIN.name(),
					dailyTime, size, size, point, priceT, downPriceT, nowTime,"card_recharge_daily_sum");
			return;
		}
		recordDaily(rechargeDailyService, adminPriceService, cardTreeId, cardTreeName,
				info.get("PARENT_USER_ID_").toString(), dailyTime, size, point, priceT, nowTime);
	}
}
