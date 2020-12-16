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
 * 修改卡种树信息
 *
 * @Author: Dongming
 * @Date: 2020-06-19 10:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/treeEdit", method = HttpConfig.Method.POST)
public class CardTreeEditAction
		extends BaseUserAction {
	Date nowTime = new Date();
	String cardTreeName, cardTreeType;
	double cardTreePrice;
	int cardTreePoint;

	@Override
	public Object run() throws Exception {
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

		String cardTreeId = StringTool.getString(dataMap, "cardTreeId", "");
		cardTreeName = StringTool.getString(dataMap, "cardTreeName", "");
		cardTreeType = StringTool.getString(dataMap, "cardTreeType", "");
		String cardState = StringTool.getString(dataMap, "cardState", "");
		cardTreePrice = NumberTool.getDouble(dataMap.get("cardTreePrice"), 0);
		cardTreePoint = NumberTool.getInteger(dataMap, "cardTreePoint", 0);
		int cardTreeSn = NumberTool.getInteger(dataMap, "cardTreeSn", 0);
		String isDirect = StringTool.getString(dataMap.get("isDirect"), "false");
		long cardTreePriceT = NumberTool.longValueT(cardTreePrice);

		try {
			CardTreeService cardTreeService = new CardTreeService();
			Map<String, Object> cardInfo = cardTreeService.findInfo(cardTreeId);
			if (ContainerTool.isEmpty(cardInfo)) {
				bean.putEnum(CodeEnum.CLOUD_404_EXIST);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Type oldTreeType = Type.getTreeType(cardInfo.get("CARD_TREE_TYPE_").toString());
			Type treeType = Type.getTreeType(cardTreeType);
			if (oldTreeType == null || treeType == null) {
				bean.putEnum(CodeEnum.CLOUD_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			// 更新卡类表数据
			cardTreeService
					.updateCardTree(cardTreeId, cardTreePriceT, cardTreeName, cardTreePoint, cardTreeType, cardTreeSn,
							Boolean.parseBoolean(isDirect), cardState, nowTime);

			// 修改了类型
			if (!oldTreeType.equals(treeType)) {
				CardAdminService cardAdminService = new CardAdminService();
				CardAdminPriceService cardAdminPriceService = new CardAdminPriceService();
				// 升
				if (treeType.ordinal() < oldTreeType.ordinal()) {
					if (Type.PARTNER.equals(treeType)) {
						cardAdminPriceService.delByUserType(cardTreeId, Type.AGENT.getUserType(), nowTime);
					} else if (Type.ADMIN.equals(treeType)) {
						cardAdminPriceService.delByTreeId(cardTreeId, nowTime);
					}
				} else {
					List<Map<String, Object>> listAgentInfos = cardAdminService.listAgentInfo(userId);
					//不管是股东还是代理级都要添加管理对股东数据
					saveSubPrice(cardAdminPriceService, listAgentInfos, cardTreeId, cardTreePriceT);
					// 为下级代理添加价值信息
					if (Type.AGENT.equals(treeType)) {
						findSubUser(cardAdminService, cardAdminPriceService, listAgentInfos, cardTreeId, cardTreePriceT,
								treeType);
					}
				}

			}
			// 保存操作log
			saveLog(nowTime);

			bean.success();
		} catch (Exception e) {
			log.error("修改卡类出错",e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	private void saveLog(Date nowTime) throws Exception {
		String fmt = "修改卡种{%s,点数:%s,价格:%s,类型:%s}";
		CardOperateLog log = new CardOperateLog();
		log.setUserId(user.getAppUserId());
		log.setUserName(user.getUserName());
		log.setUserPath(user.getUserPath());
		log.setOpertType("TREE");
		log.setIp(findServletIp());
		log.setOpertContent(String.format(fmt, cardTreeName,cardTreePoint,cardTreePrice,cardTreeType));
		log.setCreateTime(nowTime);
		log.setCreateTimeLong(System.currentTimeMillis());
		log.setUpdateTime(nowTime);
		log.setUpdateTimeLong(System.currentTimeMillis());
		log.setState(StateEnum.OPEN.name());
		new CardOperateLogService().save(log);
	}

	/**
	 * 管理员卡种类型降级时 新增管理员给股东的默认价格
	 */
	private void saveSubPrice(CardAdminPriceService cardAdminPriceService, List<Map<String, Object>> listSubInfos,
							  String cardTreeId, long cardTreePriceT) throws Exception {
		for (Map<String, Object> listSubInfo : listSubInfos) {
			CardAdminPrice cardAdminPrice = new CardAdminPrice();
			cardAdminPrice.setCardTreeId(cardTreeId);
			cardAdminPrice.setUserId(Type.ADMIN.name());
			cardAdminPrice.setSubUserId(listSubInfo.get("APP_USER_ID_"));
			cardAdminPrice.setCardPriceT(cardTreePriceT);
			cardAdminPrice.setCreateTime(nowTime);
			cardAdminPrice.setCreateTimeLong(System.currentTimeMillis());
			cardAdminPrice.setUpdateTimeLong(System.currentTimeMillis());
			cardAdminPrice.setState(StateEnum.OPEN.name());
			cardAdminPriceService.save(cardAdminPrice);
		}
	}

	/**
	 * 创建下级用户的默认价格及其对应的给与下级的价格
	 */
	private void findSubUser(CardAdminService cardAdminService, CardAdminPriceService cardAdminPriceService,
							 List<Map<String, Object>> listAgentInfos, String cardTreeId, long cardTreePriceT, Type treeType)
			throws Exception {
		for (Map<String, Object> listSubInfo : listAgentInfos) {
			//添加代理对下级默认信息
			String userId = listSubInfo.get("APP_USER_ID_").toString();
			CardAdminPrice cardAdminPrice = new CardAdminPrice();
			cardAdminPrice.setCardTreeId(cardTreeId);
			cardAdminPrice.setUserId(userId);
			cardAdminPrice.setCardPriceT(cardTreePriceT);
			cardAdminPrice.setCreateTime(nowTime);
			cardAdminPrice.setCreateTimeLong(System.currentTimeMillis());
			cardAdminPrice.setState(StateEnum.OPEN.name());
			cardAdminPrice.setSubUserId(StateEnum.DEF.name());
			cardAdminPriceService.save(cardAdminPrice);
			List<Map<String, Object>> sublistAgentInfos = cardAdminService.listAgentInfo(userId);
			//如果是卡种类型为代理- 给股东下的代理添加价格信息
			if (ContainerTool.notEmpty(sublistAgentInfos) && Type.AGENT.equals(treeType)) {
				for (Map<String, Object> subInfo : sublistAgentInfos) {
					cardAdminPrice.setCardAdminPriceId(null);
					cardAdminPrice.setSubUserId(subInfo.get("APP_USER_ID_"));
					cardAdminPriceService.save(cardAdminPrice);
				}
				//添加子代理信息
				findSubUser(cardAdminService, cardAdminPriceService, sublistAgentInfos, cardTreeId, cardTreePriceT, treeType);
			}
		}
	}
}
