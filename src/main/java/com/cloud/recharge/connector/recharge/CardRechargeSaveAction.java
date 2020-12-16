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
 * 新增充值卡--制卡
 *
 * @Author: Dongming
 * @Date: 2020-06-19 15:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/save", method = HttpConfig.Method.POST)
public class CardRechargeSaveAction extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String cardTreeId = StringTool.getString(dataMap, "cardTreeId", "");
		Integer cardNum = NumberTool.getInteger(dataMap, "cardNum", 0);
		String cardState = StringTool.getString(dataMap, "cardState", "");
		String desc = StringTool.getString(dataMap, "desc", "");
		if (StringTool.isEmpty(cardState, cardTreeId)) {
			return bean.put401Data();
		}
		if (cardNum < 1 || cardNum > 10) {
			return bean.put401Data();
		}
		if (!CardTool.isCreateState(cardState)) {
			return bean.put401Data();
		}
		try {
			// 加载卡种资料
			Map<String, Object> cardTreeInfo = new CardTreeService().findCreateCardInfo(cardTreeId);
			if (CardTool.checkCard(bean, cardTreeInfo, user.getUserType())) {
				return bean;
			}
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
			Map<String, String> cardPasswordInfo = new HashMap<>(cardNum);
			List<String> pwdList = new ArrayList<>();
			for (int i = 0; i < cardNum; i++) {
				String cardPassword = RandomTool.getNumLetter32();
				pwdList.add(cardPassword);
				CardRecharge cardRecharge = new CardRecharge();
				cardRecharge.setCardTreeId(cardTreeId);
				cardRecharge.setOwneUserId(subUserIds);
				cardRecharge.setOwnerName(user.getUserName());
				cardRecharge.setCardPassword(cardPassword);
				cardRecharge.setCardTreeName(cardTreeInfo.get("CARD_TREE_NAME_"));
				cardRecharge.setCardTreePoint(cardTreeInfo.get("CARD_TREE_POINT_"));
				cardRecharge.setCardPriceT(priceT);
				cardRecharge.setCardRechargeState(cardState);
				cardRecharge.setCreateUserId(userId);
				cardRecharge.setCreatorName(user.getUserName());
				cardRecharge.setCreateTime(nowTime);
				cardRecharge.setCreateTimeLong(System.currentTimeMillis());
				cardRecharge.setUpdateTimeLong(System.currentTimeMillis());
				cardRecharge.setState(StateEnum.OPEN.name());
				cardRecharge.setDesc(desc);
				String cardId = new CardRechargeService().save(cardRecharge);
				cardPasswordInfo.put(cardPassword, cardId);
			}

			saveLog(nowTime,cardTreeInfo.get("CARD_TREE_NAME_").toString(),cardNum,NumberTool.doubleT(priceT));
			// 写入报表
			recordDaily(new CardRechargeDailyService(), new CardAdminService(), cardTreeId, userId,
					cardTreeInfo.get("CARD_TREE_NAME_").toString(), dailyTime, cardPasswordInfo.size(), nowTime);

			bean.success(pwdList);
		} catch (Exception e) {
			log.error("新增充值卡出错",e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	private void saveLog(Date nowTime,String cardName,int cardNum,double priceT) throws Exception {
		String format ="新增充值卡{%s,%s张},总价{%s}";
		CardOperateLog log = new CardOperateLog();
		log.setUserId(user.getAppUserId());
		log.setUserName(user.getUserName());
		log.setUserPath(user.getUserPath());
		log.setOpertType("RECHARGE");
		log.setIp(findServletIp());
		log.setOpertContent(String.format(format,cardName,cardNum,cardNum*priceT));
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
	private void recordDaily(CardRechargeDailyService rechargeDailyService, CardAdminService adminService,
							 String cardTreeId, String adminUserId, String cardTreeName, String dailyTime, int cardNum, Date nowTime) throws SQLException {
		//不存在上级或者上级为管理员
		if (StringTool.isEmpty(adminUserId)) {
			return;
		}
		Map<String, Object> info = adminService.findInfo4Record(adminUserId);
		//不存在的用户
		if (ContainerTool.isEmpty(info)) {
			return;
		}
		if(userId.equals(adminUserId)){
			rechargeDailyService.recordDaily(cardTreeId, cardTreeName, adminUserId, info.get("USER_NAME_").toString(),
					dailyTime, cardNum, nowTime,"card_recharge_daily");
		}
		rechargeDailyService.recordDaily(cardTreeId, cardTreeName, adminUserId, info.get("USER_NAME_").toString(),
				dailyTime, cardNum, nowTime,"card_recharge_daily_sum");
		if (CardTool.Type.ADMIN.getUserType().equals(info.get("USER_TYPE_"))) {
			rechargeDailyService.recordDaily(cardTreeId, cardTreeName, CardTool.Type.ADMIN.name(), CardTool.Type.ADMIN.name(),
					dailyTime, cardNum, nowTime,"card_recharge_daily_sum");
			return;
		}
		String parentUserId = info.get("PARENT_USER_ID_").toString();
		recordDaily(rechargeDailyService, adminService, cardTreeId, parentUserId, cardTreeName, dailyTime, cardNum, nowTime);
	}
}
