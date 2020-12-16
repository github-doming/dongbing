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
 * @Description: 修改卡种树信息
 * @Author: wwj
 * @Date: 2020/4/6 18:12
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/editCardTree", method = HttpConfig.Method.POST)
public class EditCardTreeAction extends CommAdminAction {
    private Date nowTime = new Date();

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String cardTreeName = StringTool.getString(dataMap, "cardTreeName", "");
        String cardTreeId = StringTool.getString(dataMap, "cardTreeId", "");
        String cardTreeType = StringTool.getString(dataMap, "cardTreeType", "");
        String cardState = StringTool.getString(dataMap, "cardState", "");
        double cardTreePrice = NumberTool.getDouble(dataMap.get("cardTreePrice"), 0);
        int cardTreeSn = NumberTool.getInteger(dataMap, "cardTreeSn", 0);
        int cardTreePoint = NumberTool.getInteger(dataMap, "cardTreePoint", 0);

        long cardTreePriceT = NumberTool.longValueT(cardTreePrice);
        try {

            if (!CardTools.CARD_ADMIN_TYPE.containsKey(cardTreeType)) {
                bean.putEnum(IbmCodeEnum.IBM_404_EXIST);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean.toJsonString();
            }
            // 获取用户权限信息
            Map<String, Object> userRole = new AuthorityService().findUserRole(adminUserId);
            int permGrade = NumberTool.getInteger(userRole, "PERMISSION_GRADE_", 200);
            String cardAdminType = CardTools.selectUserGroup(permGrade);

            if (!IbmTypeEnum.CARD_ADMIN.name().equals(cardAdminType)) {
                bean.putEnum(IbmCodeEnum.IBM_403_PERMISSION);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean.toJsonString();
            }
            IbmCardAdminService cardAdminService = new IbmCardAdminService();
            IbmCardAdminPriceService cardAdminPriceService = new IbmCardAdminPriceService();
            IbmCardTreeService cardTreeService = new IbmCardTreeService();
            IbmCardTree cardTree = cardTreeService.find(cardTreeId);
            if (cardTree == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_EXIST);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean.toJsonString();
            }

            // 更新卡类表数据
            cardTreeService.updateCardTree(cardTreeId, cardTreePriceT, cardTreeName, cardTreePoint, cardTreeType, cardTreeSn, cardState, nowTime);
            // 更新分类对应的价格
            cardAdminPriceService.updateAdminPriceInfo(cardTreeId, cardTreePriceT, cardTreeName, cardState, nowTime);

            // 修改对应代理的卡种信息
            String oldCardTreeType = cardTree.getCardTreeType();

            // 修改了类型
            if (!oldCardTreeType.equals(cardTreeType)) {
                // 升
                if (CardTools.CARD_ADMIN_TYPE.get(cardTreeType) < CardTools.CARD_ADMIN_TYPE.get(oldCardTreeType)) {
                    // AGENT ---> PARTNER 删除代理的该卡类信息
                    if(IbmTypeEnum.CARD_PARTNER.name().contains(cardTreeType)){
                        cardAdminPriceService.delByCardTreeType(cardTreeId,IbmTypeEnum.CARD_AGENT.name());
                    }else if(IbmTypeEnum.CARD_ADMIN.name().contains(cardTreeType)){
                        cardAdminPriceService.delByTreeId(cardTreeId);
                    }
                } else {
                    List<Map<String, Object>> listAgentInfos = cardAdminService.listAgentInfo(adminUserId);
                    if(IbmTypeEnum.CARD_ADMIN.name().contains(oldCardTreeType)){
                        saveSubPrice(cardAdminPriceService, listAgentInfos, cardTreeId, cardTreePriceT, cardTreeName);
                    }
                    // 为下级代理添加价值信息
                    findSubUser(cardAdminService, cardAdminPriceService, listAgentInfos, cardTreeId, cardTreePriceT, cardTreeType, cardTreeName);
                }
            }

            bean.success();
        } catch (Exception e) {
            log.error("修改卡类出错", e);
            throw e;
        }
        return bean.toJsonString();
    }

    /**
     * 管理员卡种类型降级时 新增管理员给股东的默认价格
     */
    private void saveSubPrice(IbmCardAdminPriceService cardAdminPriceService,List<Map<String, Object>> listSubInfos,String cardTreeId, long cardTreePriceT,String cardTreeName ) throws Exception {
        for (Map<String, Object> info : listSubInfos) {
            IbmCardAdminPrice cardAdminPrice = new IbmCardAdminPrice();
            cardAdminPrice.setCardTreeId(cardTreeId);
            cardAdminPrice.setUserId(adminUserId);
            cardAdminPrice.setUserName(adminUser.getUserName());
            cardAdminPrice.setSubUserId(info.get("APP_USER_ID_"));
            cardAdminPrice.setCardTreeName(cardTreeName);
            cardAdminPrice.setCardPriceT(cardTreePriceT);
            cardAdminPrice.setCreateTime(nowTime);
            cardAdminPrice.setCreateTimeLong(System.currentTimeMillis());
            cardAdminPrice.setState(IbmStateEnum.OPEN.name());
            cardAdminPriceService.save(cardAdminPrice);
        }
    }

    /**
     * 创建下级用户的默认价格及其对应的给与下级的价格
     */
    private void findSubUser(IbmCardAdminService cardAdminService, IbmCardAdminPriceService cardAdminPriceService,
                             List<Map<String, Object>> listAgentInfos, String cardTreeId, long cardTreePriceT, String cardTreeType, String cardTreeName) throws Exception {

        for (Map<String, Object> info : listAgentInfos) {
            String userId = info.get("APP_USER_ID_").toString();
            IbmCardAdminPrice cardAdminPrice = new IbmCardAdminPrice();
            cardAdminPrice.setCardTreeId(cardTreeId);
            cardAdminPrice.setUserId(userId);
            cardAdminPrice.setUserName(info.get("USER_NAME_"));
            cardAdminPrice.setCardTreeName(cardTreeName);
            cardAdminPrice.setCardPriceT(cardTreePriceT);
            cardAdminPrice.setCreateTime(nowTime);
            cardAdminPrice.setCreateTimeLong(System.currentTimeMillis());
            cardAdminPrice.setState(IbmStateEnum.OPEN.name());
            cardAdminPrice.setSubUserId(IbmStateEnum.DEF.name());
            cardAdminPriceService.save(cardAdminPrice);

            List<Map<String, Object>> sublistAgentInfos = cardAdminService.listAgentInfo(userId);
            if (ContainerTool.notEmpty(sublistAgentInfos) && !IbmTypeEnum.CARD_PARTNER.name().contains(cardTreeType)) {
                for (Map<String, Object> subInfo : sublistAgentInfos) {
                    cardAdminPrice.setIbmCardAdminPriceId(null);
                    cardAdminPrice.setSubUserId(subInfo.get("APP_USER_ID_"));
                    cardAdminPriceService.save(cardAdminPrice);
                }
                findSubUser(cardAdminService, cardAdminPriceService, sublistAgentInfos, cardTreeId, cardTreePriceT, cardTreeType, cardTreeName);
            }
        }
    }

}
