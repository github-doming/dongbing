package com.cloud.recharge.connector.tree;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_admin_price.entity.CardAdminPrice;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_operate_log.entity.CardOperateLog;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.recharge.card_tree.entity.CardTree;
import com.cloud.recharge.card_tree.service.CardTreeService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool.Type;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 充值卡树创建
 *
 * @Author: Dongming
 * @Date: 2020-06-18 18:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/treeSave", method = HttpConfig.Method.POST) public class CardTreeSaveAction
		extends BaseUserAction {
	private String cardType,cardName,treeId;
	private double cardPrice;
	private int  cardTreePoint;
	private Date nowTime = new Date();
	@Override public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (!Type.isAdmin(user.getUserType())) {
			bean.putEnum(CodeEnum.CLOUD_403_PERMISSION);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		cardType = StringTool.getString(dataMap.get("cardType"), "");
		cardPrice = NumberTool.getDouble(dataMap.get("cardPrice"), 0);

		cardName = StringTool.getString(dataMap.get("cardName"), "");
		String desc = StringTool.getString(dataMap.get("desc"), "");
		cardTreePoint = NumberTool.getInteger(dataMap.get("cardTreePoint"), 0);
		int cardSn = NumberTool.getInteger(dataMap, "cardSn", 0);
		String isDirect = StringTool.getString(dataMap.get("isDirect"), "false");
		if (StringTool.isEmpty(cardName, cardType, cardSn)) {
			return bean.put401Data();
		}
		if (cardPrice <= 0 || cardTreePoint <= 0 || cardPrice >= Integer.MAX_VALUE
				|| cardTreePoint >= Integer.MAX_VALUE) {
			bean.putEnum(CodeEnum.CLOUD_403_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}

		try {
			CardTree entity = new CardTree();
			entity.setCardTreeType(cardType);
			entity.setCardTreeName(cardName);
			entity.setCardPriceT(NumberTool.longValueT(cardPrice));
			entity.setCardTreePoint(cardTreePoint);
			entity.setCreateUserId(userId);
			entity.setCreatorName(user.getUserName());
			entity.setSn(cardSn);
			entity.setIsDirect(Boolean.parseBoolean(isDirect));
			entity.setCreateTime(nowTime);
			entity.setCreateTimeLong(System.currentTimeMillis());
			entity.setUpdateTimeLong(System.currentTimeMillis());
			entity.setState(StateEnum.OPEN.name());
			entity.setDesc(desc);
			treeId = new CardTreeService().save(entity);

			// 卡种类型为管理员无需创建价值表 、并且用户类型不是admin
			if (Type.ADMIN.name().equals(cardType)) {
				bean.success();
				return bean;
			}
			CardAdminService adminService = new CardAdminService();
			CardAdminPriceService adminPriceService = new CardAdminPriceService();

			// 查找所有股东
			List<Map<String, Object>> cardAdminInfos = adminService.listCardAdminInfo(Type.PARTNER.getUserType(), "");
			for (Map<String, Object> cardAdminInfo : cardAdminInfos) {
				String subAgentUid = (String) cardAdminInfo.get("APP_USER_ID_");
				Long cardAdminId = (Long) cardAdminInfo.get("CARD_ADMIN_ID_");
				savePartnerCardInfo(adminService, adminPriceService, subAgentUid,cardAdminId);

			}
			saveLog();

			bean.success();
		} catch (Exception e) {
			log.error("新增卡种类型出错",e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	/**
	 * 创建下级股东价值表信息
	 * <p>
	 * 2条基本数据 ：1.上级给自己的定价 ，2.自己的默认定价
	 * 1条按需数据 ： 如果有下级新增下级数据
	 */
	private void savePartnerCardInfo(CardAdminService adminService, CardAdminPriceService adminPriceService,
			String subAgentUid,Long cardAdminId) throws Exception {
		List<Map<String, Object>> cardAdminInfos;//添加个人信息
		CardAdminPrice adminPrice = new CardAdminPrice();
		adminPrice.setCardTreeId(treeId);
		adminPrice.setCardAdminId(cardAdminId);
		adminPrice.setUserId(Type.ADMIN.name());
		adminPrice.setSubUserId(subAgentUid);
		adminPrice.setCardPriceT(NumberTool.longValueT(cardPrice));
		adminPrice.setCreateTime(nowTime);
		adminPrice.setCreateTimeLong(System.currentTimeMillis());
		adminPrice.setUpdateTimeLong(System.currentTimeMillis());
		adminPrice.setState(StateEnum.OPEN.name());
		adminPriceService.save(adminPrice);

		//添加个人默认信息
		adminPrice.setCardAdminPriceId(null);
		adminPrice.setUserId(subAgentUid);
		adminPrice.setSubUserId(StateEnum.DEF.name());
		adminPrice.setCreateTimeLong(System.currentTimeMillis());
		adminPrice.setUpdateTimeLong(System.currentTimeMillis());
		adminPriceService.save(adminPrice);

		if (Type.PARTNER.name().contains(cardType)) {
			return;
		}
		cardAdminInfos = adminService.listCardAdminInfo(Type.AGENT.getUserType(), subAgentUid);
		if (ContainerTool.notEmpty(cardAdminInfos)) {
			saveAgentCardInfo(adminPriceService, adminService, subAgentUid, cardAdminInfos);
		}
	}
	/**
	 * 创建下级代理价值表信息
	 * <p>
	 * 2条基本数据 ：1.上级给自己的定价 ，2.自己的默认定价
	 * 1条按需数据 ： 如果有下级新增下级数据
	 */
	private void saveAgentCardInfo(CardAdminPriceService adminPriceService, CardAdminService adminService,
			String agentId, List<Map<String, Object>> cardAdminInfos) throws Exception {

		for (Map<String, Object> cardAdminInfo : cardAdminInfos) {
			String subAgentUid = cardAdminInfo.get("APP_USER_ID_").toString();

			//添加个人信息
			CardAdminPrice adminPrice = new CardAdminPrice();
			adminPrice.setCardTreeId(treeId);
			adminPrice.setCardAdminId(cardAdminInfo.get("CARD_ADMIN_ID_"));
			adminPrice.setUserId(agentId);
			adminPrice.setSubUserId(subAgentUid);
			adminPrice.setCardPriceT(NumberTool.longValueT(cardPrice));
			adminPrice.setCreateTime(nowTime);
			adminPrice.setCreateTimeLong(System.currentTimeMillis());
			adminPrice.setUpdateTimeLong(System.currentTimeMillis());
			adminPrice.setState(StateEnum.OPEN.name());
			adminPriceService.save(adminPrice);

			//添加个人默认信息
			adminPrice.setCardAdminPriceId(null);
			adminPrice.setUserId(subAgentUid);
			adminPrice.setSubUserId(StateEnum.DEF.name());
			adminPrice.setCreateTimeLong(System.currentTimeMillis());
			adminPrice.setUpdateTimeLong(System.currentTimeMillis());
			adminPriceService.save(adminPrice);
			// 查找是否还有下级代理
			cardAdminInfos = adminService.listCardAdminInfo(Type.AGENT.name(), subAgentUid);
			if (ContainerTool.notEmpty(cardAdminInfos)) {
				saveAgentCardInfo(adminPriceService, adminService, subAgentUid, cardAdminInfos);
			}
		}

	}

	private void saveLog() throws Exception {
		String fmt = "新增卡种{%s,点数:%s,价格:%s,类型:%s}";
		CardOperateLog log = new CardOperateLog();
		log.setUserId(user.getAppUserId());
		log.setUserName(user.getUserName());
		log.setUserPath(user.getUserPath());
		log.setOpertType("TREE");
		log.setIp(findServletIp());
		log.setOpertContent(String.format(fmt,cardName,cardTreePoint,cardPrice,cardType));
		log.setCreateTime(nowTime);
		log.setCreateTimeLong(System.currentTimeMillis());
		log.setUpdateTime(nowTime);
		log.setUpdateTimeLong(System.currentTimeMillis());
		log.setState(StateEnum.OPEN.name());
		new CardOperateLogService().save(log);
	}
}
