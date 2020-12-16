package com.ibm.follow.connector.admin.card.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_card.service.IbmCardService;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.service.IbmCardAdminPriceService;
import com.ibm.follow.servlet.cloud.ibm_card_tree.service.IbmCardTreeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 充值卡管理下级列表
 *
 * @Author: Dongming
 * @Date: 2020-04-10 19:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/agent/subList")
public class CardAgentSubListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			// TODO: 2020/4/10 加载下级代理列表
			IbmCardAdminService cardAdminService = new IbmCardAdminService();
			Map<String, Object> adminInfo = cardAdminService.findAdminInfo(adminUserId);
			if (ContainerTool.isEmpty(adminInfo)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			List<Map<String, Object>> agentInfos;
			if (IbmTypeEnum.cardType(adminInfo.get("USER_TYPE_"))) {
				agentInfos = new ArrayList<>();
				agentInfos(cardAdminService, agentInfos,adminUserId);
			} else {
				agentInfos = cardAdminService.listAllAgentInfo();

			}
			bean.success(agentInfos);
		} catch (Exception e) {
			log.error("下级管理列表出错", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 获取子代理信息
	 * @param cardAdminService 服务类
	 * @param agentInfos 回传列表
	 * @param adminUserId 查询父id
	 */
	private void agentInfos(IbmCardAdminService cardAdminService, List<Map<String, Object>> agentInfos, String adminUserId) throws SQLException {
		List<Map<String, Object>> childAgentInfo = cardAdminService.listAgentInfo(adminUserId);
		if (ContainerTool.notEmpty(childAgentInfo)) {
			agentInfos.addAll(childAgentInfo);
			for (Map<String, Object> agentInfo : childAgentInfo) {
				agentInfos(cardAdminService, agentInfos,agentInfo.get("APP_USER_ID_").toString());
			}
		}
	}
}
