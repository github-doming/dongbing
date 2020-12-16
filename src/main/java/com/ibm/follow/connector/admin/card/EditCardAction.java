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
import com.ibm.follow.servlet.cloud.ibm_card_operate_log.entity.IbmCardOperateLog;
import com.ibm.follow.servlet.cloud.ibm_card_operate_log.service.IbmCardOperateLogService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 修改充值卡状态
 * @Author: wwj
 * @Date: 2020/4/6 15:20
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/editCard",method = HttpConfig.Method.POST)
public class EditCardAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        String cardState = StringTool.getString(dataMap.get("cardState"), "");
        String desc = StringTool.getString(dataMap, "desc", "");
        String cardId = StringTool.getString(dataMap.get("cardId"), "false");
        Date date = new Date();
        try{
            // 获取用户权限信息
            Map<String, Object> userRole = new AuthorityService().findUserRole(adminUserId);
            int permGrade = NumberTool.getInteger(userRole, "PERMISSION_GRADE_", 200);
            String cardAdminType = com.ibm.follow.connector.admin.card.CardTools.selectUserGroup(permGrade);

            // 权限码超过110的用户没有权限 ADMIN:0-99 PARTNER:100 AGENT:110
            if (IbmTypeEnum.FALSE.name().equals(cardAdminType)) {
                bean.putEnum(IbmCodeEnum.IBM_403_PERMISSION);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean.toJsonString();
            }
            IbmCardService cardService = new IbmCardService();
            IbmCard card = cardService.find(cardId);
            if(card == null){
                bean.putEnum(IbmCodeEnum.IBM_404_EXIST);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean.toJsonString();
            }
            card.setState(cardState);
            card.setUpdateTime(date);
            card.setUpdateTimeLong(System.currentTimeMillis());
            card.setDesc(desc);
            cardService.update(card);

            saveLog(cardId,cardState,date);
            bean.success();
        }catch (Exception e){
            log.error("修改充值卡状态出错",e);
            throw e;
        }
        return bean.toJsonString();
    }

    private void saveLog(String cardId,String cardState,Date date) throws Exception {
        IbmCardOperateLogService cardOperateLogService = new IbmCardOperateLogService();
        IbmCardOperateLog operateLog = cardOperateLogService.findByCardId(cardId);
        if(operateLog !=null){
            operateLog.setOperateType(cardState);
            operateLog.setOperateContect(cardState+"充值卡");
            operateLog.setUpdateTime(date);
            operateLog.setUpdateTimeLong(System.currentTimeMillis());
            cardOperateLogService.update(operateLog);
        }else{
            operateLog = new IbmCardOperateLog();
            operateLog.setCardId(cardId);
            operateLog.setUserId(adminUserId);
            operateLog.setOperateType(cardState);
            operateLog.setOperateContect(cardState+"充值卡");
            operateLog.setCreateTime(date);
            operateLog.setCreateTimeLong(System.currentTimeMillis());
            operateLog.setState(IbmStateEnum.OPEN.name());
            cardOperateLogService.save(operateLog);
        }

    }
}


