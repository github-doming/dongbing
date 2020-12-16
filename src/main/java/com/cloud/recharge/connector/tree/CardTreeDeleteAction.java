package com.cloud.recharge.connector.tree;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_operate_log.entity.CardOperateLog;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.recharge.card_tree.service.CardTreeService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool.Type;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;
/**
 * 删除卡种信息
 *
 * @Author: Dongming
 * @Date: 2020-06-19 14:12
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/treeDelete", method = HttpConfig.Method.POST) public class CardTreeDeleteAction
		extends BaseUserAction {
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
		String cardTreeId = StringTool.getString(dataMap, "cardTreeId", "");
		if (StringTool.isEmpty(cardTreeId)) {
			return bean.put401Data();
		}
		try {
			CardTreeService cardTreeService = new CardTreeService();
			Map<String, Object> cardInfo = cardTreeService.findInfo(cardTreeId);
			if (ContainerTool.isEmpty(cardInfo)) {
				bean.putEnum(CodeEnum.CLOUD_404_EXIST);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			if (StateEnum.CLOSE.name().equals(cardInfo.get("STATE_"))) {
				bean.setMessage("想要被删除的卡种必须先禁用才能被删除");
				bean.putEnum(CodeEnum.CLOUD_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Date nowTime = new Date();
			new CardTreeService().del(cardTreeId);
			new CardAdminPriceService().delByTreeId(cardTreeId, nowTime);

			// 保存操作log
			saveLog(nowTime,cardInfo.get("CARD_TREE_NAME_").toString());
			bean.success();
		} catch (Exception e) {
			log.error("删除卡种信息出错",e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}

		return bean;
	}

	private void saveLog(Date nowTime,String cardTreeName) throws Exception {
		String fmt = "删除卡种{%s}";
		CardOperateLog log = new CardOperateLog();
		log.setUserId(user.getAppUserId());
		log.setUserName(user.getUserName());
		log.setUserPath(user.getUserPath());
		log.setOpertType("TREE");
		log.setIp(findServletIp());
		log.setOpertContent(String.format(fmt,cardTreeName));
		log.setCreateTime(nowTime);
		log.setCreateTimeLong(System.currentTimeMillis());
		log.setUpdateTime(nowTime);
		log.setUpdateTimeLong(System.currentTimeMillis());
		log.setState(StateEnum.OPEN.name());
		new CardOperateLogService().save(log);
	}
}
