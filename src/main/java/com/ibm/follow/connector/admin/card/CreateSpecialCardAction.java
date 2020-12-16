package com.ibm.follow.connector.admin.card;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_card.entity.IbmCard;
import com.ibm.follow.servlet.cloud.ibm_card.service.IbmCardService;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import com.ibm.follow.servlet.cloud.ibm_card_operate_log.entity.IbmCardOperateLog;
import com.ibm.follow.servlet.cloud.ibm_card_operate_log.service.IbmCardOperateLogService;
import com.ibm.follow.servlet.cloud.ibm_card_report.entity.IbmCardReport;
import com.ibm.follow.servlet.cloud.ibm_card_report.service.IbmCardReportService;
import com.ibm.follow.servlet.cloud.ibm_card_tree.entity.IbmCardTree;
import com.ibm.follow.servlet.cloud.ibm_card_tree.service.IbmCardTreeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 新增充值卡--特殊制卡
 * @Author: wwj
 * @Date: 2020/4/4 18:20
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/specialAddCard", method = HttpConfig.Method.POST)
public class CreateSpecialCardAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String cardTreeId = StringTool.getString(dataMap, "cardTreeId", "");
        String cardTreeName = StringTool.getString(dataMap, "cardTreeName", "");
        Integer cardNum = NumberTool.getInteger(dataMap, "cardNum", 1);
        String desc = StringTool.getString(dataMap, "desc", "");

        String agentId = StringTool.getString(dataMap, "agentId", "");
        double cardPrice = NumberTool.getDouble(dataMap.get("cardPrice"), 0);

        if (StringTool.isEmpty(cardNum, cardTreeId, agentId, cardTreeName)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean.toJsonString();
        }
        if (cardPrice >= Integer.MAX_VALUE || cardNum > 1000) {
            bean.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return bean.toJsonString();
        }

        try {

            // 获取用户权限信息
            Map<String, Object> userRole = new AuthorityService().findUserRole(adminUserId);
            int permGrade = NumberTool.getInteger(userRole, "PERMISSION_GRADE_", 200);
            String cardAdminType = CardTools.selectUserGroup(permGrade);

            // 权限码超过110的用户没有权限 ADMIN:0-99 PARTNER:100 AGENT:110
            if (IbmTypeEnum.FALSE.name().equals(cardAdminType)) {
                bean.putEnum(IbmCodeEnum.IBM_403_PERMISSION);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean.toJsonString();
            }
            // 查询卡类
            IbmCardTreeService cardTreeService = new IbmCardTreeService();
            IbmCardTree cardTree = cardTreeService.find(cardTreeId);
            if (cardTree == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_EXIST);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean.toJsonString();
            }

            Map<String, Object> agentInfo = new IbmCardAdminService().findAdminInfo(agentId);
            String ownerName = StringTool.getString(agentInfo, "APP_USER_NAME_", "admin");
            String userName =adminUser.getUserName() ;
            if (IbmTypeEnum.CARD_ADMIN.name().equals(cardAdminType)) {
                userName = IbmTypeEnum.ADMIN.name();
                adminUserId = IbmTypeEnum.ADMIN.name();
            }

            IbmCardService cardService = new IbmCardService();
            IbmCardReportService cardReportService = new IbmCardReportService();
            IbmCardAdminService cardAdminService = new IbmCardAdminService();

            int cardPoint = cardTree.getCardTreePoint();
            Date date = new Date();
            List<String> pwdList = new ArrayList<>();
            for (int i = 0; i < cardNum; i++) {
                String pwdCode = RandomTool.getNumLetter32();
                pwdList.add(pwdCode);

                IbmCard card = new IbmCard();
                card.setCardTreeId(cardTreeId);
                card.setOwneUserId(agentId);
                card.setOwnerName(ownerName);
                card.setCardPassword(pwdCode);
                card.setCardTreeName(cardTreeName);
                card.setCardTreePoint(cardPoint);
                card.setCardPriceT(NumberTool.longValueT(cardPrice));
                card.setCreateUserId(adminUserId);
                card.setCreaterName(userName);
                card.setCreateTime(date);
                card.setCreateTimeLong(System.currentTimeMillis());
                card.setState(IbmStateEnum.ALLOT.name());
                card.setDesc(desc);
                String cardId = cardService.save(card);
                // 保存操作log
                saveLog(cardId);

                // 记录创建人关于自己的报表信息
                checkReport(cardReportService, card, adminUserId, adminUser.getUserName(), IbmTypeEnum.SELF.name());
                // 记录创建人及其上级代理交付给上级的报表信息
                recordReport(cardReportService,cardAdminService, card, adminUserId, adminUser.getUserName());
            }

            bean.success(pwdList);
        } catch (Exception e) {
            log.error("新增充值卡出错", e);
            throw e;
        }
        return bean.toJsonString();
    }

    /**
     * 迭代记录报表情况
     * 总价格、总点数、激活数量在充值卡激活是写入
     */
    private void recordReport(IbmCardReportService cardReportService, IbmCardAdminService cardAdminService, IbmCard card, String parentUserId,
                              String parentUserName) throws Exception {

        checkReport(cardReportService, card, parentUserId, parentUserName, IbmTypeEnum.HIGHER_UP.name());
        Map<String, Object> parentInfo = cardAdminService.findParentInfo(parentUserId);
        if (ContainerTool.isEmpty(parentInfo)) {
            return;
        }
        String parentId = parentInfo.get("PARENT_USER_ID_").toString();
        String parentName = parentInfo.get("PARENT_USER_NAME_").toString();
        recordReport(cardReportService,cardAdminService, card, parentId, parentName);
    }

    /**
     * 校验用户报表信息
     */
    private void checkReport(IbmCardReportService cardReportService, IbmCard card, String userId, String userName, String target) throws Exception {
        IbmCardReport cardReport = cardReportService.findByCardTreeId(card.getCardTreeId(), userId, target);
        if (cardReport == null) {
            cardReport = new IbmCardReport();
            cardReport.setCardTreeId(card.getCardTreeId());
            cardReport.setCardTreeName(card.getCardTreeName());
            cardReport.setUserId(userId);
            cardReport.setUserName(userName);
            cardReport.setPointTotal(0);
            cardReport.setPriceTotalT(0);
            cardReport.setCardTotal(1);
            cardReport.setCardActivateTotal(0);
            cardReport.setEdition(1);
            cardReport.setTarget(target);
            cardReport.setReportTimeLong(System.currentTimeMillis());
            cardReport.setCreateTime(card.getCreateTime());
            cardReport.setCreateTimeLong(System.currentTimeMillis());
            cardReport.setState(IbmStateEnum.OPEN.name());
            cardReportService.save(cardReport);
        } else {
            cardReportService.updateReportEdition(cardReport.getIbmCardReportId());
        }
    }

    /**
     * 记录日志
     */
    private void saveLog(String cardId) throws Exception {
        IbmCardOperateLog operateLog = new IbmCardOperateLog();
        operateLog.setCardId(cardId);
        operateLog.setUserId(adminUserId);
        operateLog.setOperateType("ADD");
        operateLog.setOperateContect("新增充值卡");
        operateLog.setCreateTime(new Date());
        operateLog.setCreateTimeLong(System.currentTimeMillis());
        operateLog.setState(IbmStateEnum.OPEN.name());
        new IbmCardOperateLogService().save(operateLog);
    }
}
