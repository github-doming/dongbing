package com.ibm.follow.connector.pc.home;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage2.cms.RecordNotifyDefine;
import com.ibm.follow.servlet.cloud.ibm_card.entity.IbmCard;
import com.ibm.follow.servlet.cloud.ibm_card.service.IbmCardService;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.service.IbmCardAdminPriceService;
import com.ibm.follow.servlet.cloud.ibm_card_log.entity.IbmCardLog;
import com.ibm.follow.servlet.cloud.ibm_card_log.service.IbmCardLogService;
import com.ibm.follow.servlet.cloud.ibm_card_operate_log.entity.IbmCardOperateLog;
import com.ibm.follow.servlet.cloud.ibm_card_operate_log.service.IbmCardOperateLogService;
import com.ibm.follow.servlet.cloud.ibm_card_report.entity.IbmCardReport;
import com.ibm.follow.servlet.cloud.ibm_card_report.service.IbmCardReportService;
import com.ibm.follow.servlet.cloud.ibm_card_user.entity.IbmCardUser;
import com.ibm.follow.servlet.cloud.ibm_card_user.service.IbmCardUserService;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import com.ibm.follow.servlet.cloud.ibm_cms_user_notify.service.IbmCmsUserNotifyService;
import com.ibm.follow.servlet.cloud.ibm_manage_point.entity.IbmManagePoint;
import com.ibm.follow.servlet.cloud.ibm_manage_point.service.IbmManagePointService;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_point.entity.IbmRepManagePoint;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_point.service.IbmRepManagePointService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 充值积分
 * @Author: wwj
 * @Date: 2020/4/21 14:35
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/home/rechargePoint", method = HttpConfig.Method.POST)
public class RechargePointAction extends CommCoreAction {
    public RechargePointAction() {
        super.isTime = false;
    }

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String cardPassword = StringTool.getString(dataMap, "cardPassword", "");
        if (StringTool.isEmpty(cardPassword)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        Date nowTime = new Date();
        try {
            IbmCardService cardService = new IbmCardService();
            IbmCard card = cardService.findEntityByCardPwd(cardPassword);
            if (card == null) {
                bean.putEnum(IbmCodeEnum.IBM_403_CARD_PASSWORD);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }

            if (IbmStateEnum.ACTIVATE.name().equals(card.getState()) || IbmStateEnum.DISABLE.name().equals(card.getState())) {
                bean.putEnum(IbmCodeEnum.IBM_403_CARD_PASSWORD);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            IbmCardOperateLog operateLog = new IbmCardOperateLogService().findByCardId(card.getIbmCardId());

            if (operateLog == null) {
                bean.putEnum(IbmCodeEnum.IBM_401_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_401);
                return bean;
            }
            // 更新充值卡状态
            cardService.useCard(card.getIbmCardId(), appUserId, appUser.getNickname(), nowTime);
            // 记录用户-开卡代理关联关系
            cardUserRecord(card.getIbmCardId(),card.getCreateUserId(),nowTime);

            String createUserId = card.getCreateUserId();
            String cardTreeId = card.getCardTreeId();
            int point = card.getCardTreePoint();
            long price = card.getCardPriceT();

            // 更新积分
            updateUserPoint(point, card.getCardTreeName(), nowTime);

            // 记录报表
            IbmCardReportService cardReportService = new IbmCardReportService();
            IbmCardReport cardReport = cardReportService.findByCardTreeIdAndDate(cardTreeId, createUserId, IbmTypeEnum.SELF.name(), card.getCreateTime());
            if (cardReport != null) {
                cardReportService.updateReportUseState(cardReport.getIbmCardReportId(), point, price);
            }

            updateReport(cardReportService, new IbmCardAdminService(), new IbmCardAdminPriceService(), createUserId, cardTreeId, point, price, card.getCreateTime());

            cardLog(card.getIbmCardId(), nowTime);
            bean.success(point);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("充值卡充值错误"), e);
            bean.error(e.getMessage());
        }
        return bean;

    }

    private void cardUserRecord(String ibmCardId, String createUserId, Date nowTime) throws Exception {
        IbmCardUserService cardUserService = new IbmCardUserService();
        IbmCardUser cardUser = cardUserService.findByUserId(appUserId);
        if(cardUser==null){
            cardUser = new IbmCardUser();
            cardUser.setCardId(ibmCardId);
            cardUser.setCreateUserId(createUserId);
            cardUser.setUserId(appUserId);
            cardUser.setCreateTime(nowTime);
            cardUser.setCreateTimeLong(System.currentTimeMillis());
            cardUser.setUpdateTime(nowTime);
            cardUser.setUpdateTimeLong(System.currentTimeMillis());
            cardUser.setState(IbmStateEnum.OPEN.name());
            cardUserService.save(cardUser);
        }else{
            cardUser.setCardId(ibmCardId);
            cardUser.setCreateUserId(createUserId);
            cardUser.setUpdateTime(nowTime);
            cardUser.setUpdateTimeLong(System.currentTimeMillis());
            cardUserService.update(cardUser);
        }
    }

    private void cardLog(String ibmCardId, Date nowTime) throws Exception {
        IbmCardLog cardLog = new IbmCardLog();
        cardLog.setCardId(ibmCardId);
        cardLog.setUseUserId(appUserId);
        cardLog.setCreateTime(nowTime);
        cardLog.setCreateTimeLong(System.currentTimeMillis());
        cardLog.setState(IbmStateEnum.OPEN.name());
        new IbmCardLogService().save(cardLog);

    }


    /**
     * 更新积分,保存记录
     */
    private void updateUserPoint(int point, String cardTreeName, Date nowTime) throws Exception {

        IbmManagePointService managePointService = new IbmManagePointService();
        IbmManagePoint managePoint = managePointService.findByUserId(appUserId);

        IbmRepManagePointService repManagePointService = new IbmRepManagePointService();
        Map<String, Object> lastRepPoint = repManagePointService.findLastRepByUserId(appUserId);

        IbmRepManagePoint repPoint = new IbmRepManagePoint();
        repPoint.setPreId(lastRepPoint.get("preKey"));
        repPoint.setAppUserId(appUserId);
        repPoint.setTitle("激活充值卡《" + cardTreeName + "》,充值点数：" + point);
        repPoint.setPreT(managePoint.getUseablePointT());
        repPoint.setNumberT(NumberTool.intValueT(point));
        repPoint.setBalanceT(NumberTool.intValueT(point) + managePoint.getUseablePointT());
        repPoint.setCreateTime(nowTime);
        repPoint.setCreateTimeLong(nowTime.getTime());
        repPoint.setUpdateTime(nowTime);
        repPoint.setUpdateTimeLong(nowTime.getTime());
        repPoint.setState(IbmStateEnum.OPEN.name());
        repPoint.setDesc(appUserId);
        String repId = repManagePointService.save(repPoint);

        managePoint.setRepPointId(repId);
        managePoint.setTotalPointT(NumberTool.intValueT(point) + managePoint.getTotalPointT());
        managePoint.setUseablePointT(NumberTool.intValueT(point) + managePoint.getUseablePointT());
        managePoint.setUpdateUser(appUser.getNickname());
        managePoint.setUpdateTime(nowTime);
        managePoint.setUpdateTimeLong(nowTime.getTime());
        managePointService.update(managePoint);
        // 记录用户提醒消息
        RecordNotifyDefine.recordTriggerNotify(new IbmCmsNotifyService(), new IbmCmsUserNotifyService(), appUserId,appUser.getNickname(), repPoint.getTitle(),"RECHARGE", nowTime);
    }

    /**
     * 记录报表信息
     *
     * @param createUserId 创建者Id / 上级Id
     * @param cardTreeId   卡种分类Id
     * @param point        积分
     * @param price        价格
     * @param createDate   充值卡创建时间
     */
    private void updateReport(IbmCardReportService cardReportService, IbmCardAdminService cardAdminService, IbmCardAdminPriceService cardAdminPriceService,
                              String createUserId, String cardTreeId, int point, long price,  Date createDate) throws Exception {

        IbmCardReport cardReport = cardReportService.findByCardTreeIdAndDate(cardTreeId, createUserId, IbmTypeEnum.HIGHER_UP.name(), createDate);
        if (cardReport != null) {
            long selfPrice = cardAdminPriceService.listParentInfo(cardTreeId, createUserId);
            selfPrice = selfPrice != 0 ? selfPrice : price;

            cardReportService.updateReportUseState(cardReport.getIbmCardReportId(), point, selfPrice);
        }

        Map<String, Object> parentInfo = cardAdminService.findParentInfo(createUserId);
        if (ContainerTool.isEmpty(parentInfo)) {
            return;
        }
        createUserId = parentInfo.get("PARENT_USER_ID_").toString();
        long selfPrice = cardAdminPriceService.listParentInfo(cardTreeId, createUserId);
        selfPrice = selfPrice != 0 ? selfPrice : price;

        updateReport(cardReportService, cardAdminService, cardAdminPriceService, createUserId, cardTreeId, point, selfPrice, createDate);

    }


}
