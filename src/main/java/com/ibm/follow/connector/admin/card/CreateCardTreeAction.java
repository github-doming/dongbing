package com.ibm.follow.connector.admin.card;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.entity.IbmCardAdminPrice;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.service.IbmCardAdminPriceService;
import com.ibm.follow.servlet.cloud.ibm_card_tree.entity.IbmCardTree;
import com.ibm.follow.servlet.cloud.ibm_card_tree.service.IbmCardTreeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 新增卡种
 * @Author: wwj
 * @Date: 2020/4/4 18:20
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/addCardTree", method = HttpConfig.Method.POST)
public class CreateCardTreeAction extends CommAdminAction {
    private String cardName, cardType, treeId,desc;
    private double cardPrice;
    private int cardTreePoint, cardSn;
    private Date nowTime = new Date();

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        cardName = StringTool.getString(dataMap.get("cardName"), "");
        cardType = StringTool.getString(dataMap.get("cardType"), "");
        desc = StringTool.getString(dataMap.get("desc"), "");
        cardPrice = NumberTool.getDouble(dataMap.get("cardPrice"), 0);
        cardTreePoint = NumberTool.getInteger(dataMap.get("cardTreePoint"), 0);
        cardSn = NumberTool.getInteger(dataMap, "cardSn", 0);
        if (StringTool.isEmpty(cardName, cardType, cardSn)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean.toJsonString();
        }
        if (cardPrice <= 0 || cardTreePoint <= 0 || cardPrice >= Integer.MAX_VALUE || cardTreePoint >= Integer.MAX_VALUE) {
            bean.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return bean.toJsonString();
        }
        try {

            // 获取用户权限信息
            Map<String, Object> userRole = new AuthorityService().findUserRole(adminUserId);
            int permGrade = NumberTool.getInteger(userRole, "PERMISSION_GRADE_", 200);
            String cardAdminType = CardTools.selectUserGroup(permGrade);

            // 权限码超过100的用户没有权限 ADMIN:0-50 show 50-70  PARTNER:80-90 AGENT:90-100
            if (IbmTypeEnum.FALSE.name().equals(cardAdminType) ||  permGrade > 50 ) {
                bean.putEnum(IbmCodeEnum.IBM_403_PERMISSION);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean.toJsonString();
            }


            IbmCardTree entity = new IbmCardTree();
            entity.setCardTreeType(cardType);
            entity.setCardTreeName(cardName);
            entity.setCardPriceT(NumberTool.longValueT(cardPrice));
            entity.setCardTreePoint(cardTreePoint);
            entity.setCreateUserId(adminUserId);
            entity.setCreaterName(adminUser.getUserName());
            entity.setSn(cardSn);
            entity.setCreateTime(nowTime);
            entity.setCreateTimeLong(System.currentTimeMillis());
            entity.setState(IbmStateEnum.OPEN.name());
            entity.setDesc(desc);
            treeId = new IbmCardTreeService().save(entity);


            // 卡种类型为管理员无需创建价值表 、并且用户类型不是admin
            if (IbmTypeEnum.ADMIN.name().equals(cardType)) {
                bean.success();
                return bean.toJsonString();
            }

            IbmCardAdminService cardAdminService = new IbmCardAdminService();
            IbmCardAdminPriceService adminPriceService = new IbmCardAdminPriceService();

            // 查找所有股东
            List<Map<String, Object>> cardAdminInfos = cardAdminService.listCardAdminInfo(IbmTypeEnum.CARD_PARTNER.name(), "");

            for (Map<String, Object> cardAdminInfo : cardAdminInfos) {
                String subAgentUid = (String) cardAdminInfo.get("APP_USER_ID_");
                String subName = (String) cardAdminInfo.get("USER_NAME_");
                IbmCardAdminPrice adminPrice = new IbmCardAdminPrice();
                adminPrice.setCardTreeId(treeId);
                adminPrice.setUserId(IbmTypeEnum.ADMIN.name());
                adminPrice.setUserName(IbmTypeEnum.ADMIN.name());
                adminPrice.setSubUserId(subAgentUid);
                adminPrice.setCardTreeName(cardName);
                adminPrice.setCardPriceT(NumberTool.longValueT(cardPrice));
                adminPrice.setCreateTime(nowTime);
                adminPrice.setCreateTimeLong(System.currentTimeMillis());
                adminPrice.setState(IbmStateEnum.OPEN.name());
                adminPriceService.save(adminPrice);

                adminPrice.setIbmCardAdminPriceId(null);
                adminPrice.setUserId(subAgentUid);
                adminPrice.setUserName(subName);
                adminPrice.setSubUserId(IbmStateEnum.DEF.name());
                adminPriceService.save(adminPrice);


                if (IbmTypeEnum.CARD_PARTNER.name().contains(cardType)) {
                    continue;
                }
                cardAdminInfos = cardAdminService.listCardAdminInfo(IbmTypeEnum.CARD_AGENT.name(), subAgentUid);

                if (ContainerTool.notEmpty(cardAdminInfos)) {
                    saveAgentCardInfo(adminPriceService, cardAdminService, subAgentUid, subName, cardAdminInfos);
                }
            }

            bean.success();
        } catch (Exception e) {
            log.error("新增卡种类型出错", e);
            throw e;
        }
        return bean.toJsonString();
    }

    /**
     * 创建下级代理价值表信息
     * <p>
     * 2条基本数据 ：1.上级给自己的定价 ，2.自己的默认定价
     * 1条按需数据 ： 如果有下级新增下级数据
     */
    private void saveAgentCardInfo(IbmCardAdminPriceService adminPriceService, IbmCardAdminService cardAdminService,
                                   String agentId, String agentName, List<Map<String, Object>> cardAdminInfos) throws Exception {

        for (Map<String, Object> cardAdminInfo : cardAdminInfos) {
            String subAgentUid = (String) cardAdminInfo.get("APP_USER_ID_");
            String subName = (String) cardAdminInfo.get("USER_NAME_");
            IbmCardAdminPrice adminPrice = new IbmCardAdminPrice();
            adminPrice.setCardTreeId(treeId);
            adminPrice.setUserId(agentId);
            adminPrice.setUserName(agentName);
            adminPrice.setSubUserId(subAgentUid);
            adminPrice.setCardTreeName(cardName);
            adminPrice.setCardPriceT(NumberTool.longValueT(cardPrice));
            adminPrice.setCreateTime(nowTime);
            adminPrice.setCreateTimeLong(System.currentTimeMillis());
            adminPrice.setState(IbmStateEnum.OPEN.name());
            adminPriceService.save(adminPrice);

            adminPrice.setIbmCardAdminPriceId(null);
            adminPrice.setUserId(subAgentUid);
            adminPrice.setUserName(subName);
            adminPrice.setSubUserId(IbmStateEnum.DEF.name());
            adminPriceService.save(adminPrice);

            // 查找是否还有下级代理
            cardAdminInfos = cardAdminService.listCardAdminInfo(IbmTypeEnum.CARD_AGENT.name(), subAgentUid);
            if (ContainerTool.notEmpty(cardAdminInfos)) {
                saveAgentCardInfo(adminPriceService, cardAdminService, subAgentUid, subName, cardAdminInfos);
            }
        }
    }

}
