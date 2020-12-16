package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit.service.IbmHmProfitService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * @Description: 会员检验控制器
 * @Author: null
 * @Date: 2019-10-16 14:03
 * @Version: v1.0
 */
public class CheckMemberController {
    private JsonResultBeanPlus bean = new JsonResultBeanPlus();
    private String handicapMemberId;

    public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {

        IbmClientHmService clientHmService = new IbmClientHmService();
        handicapMemberId = clientHmService.findHmId(msgObj.getString("EXIST_HM_ID_"));
        if (StringTool.isEmpty(handicapMemberId, msgObj.getString("requestType"))) {
            bean.putEnum(CodeEnum.IBS_401_DATA);
            bean.putSysEnum(CodeEnum.CODE_401);
            return bean;
        }
        IbmStateEnum requestType = IbmStateEnum.valueOf(msgObj.getString("requestType"));
        switch (requestType) {
            case SUCCESS:
                updateMemberInfo(msgObj);
                break;
            case FAIL:
                //TODO 保存错误信息
                break;
            default:
                bean.putEnum(CodeEnum.IBS_404_METHOD);
                bean.putSysEnum(CodeEnum.CODE_404);
                break;
        }
        return bean;
    }

    /**
     * 修改会员信息
     *
     * @param msgObj 结果信息
     */
    private void updateMemberInfo(JSONObject msgObj) throws Exception {
        int number = new IbmHmInfoService()
                .updateAmountInfo(handicapMemberId, msgObj.getString("usedQuota"), msgObj.getString("profitAmount"));
        if (number <= 0) {
            bean.putEnum(CodeEnum.IBS_404_CUSTOMER_LOGIN);
            bean.putSysEnum(CodeEnum.CODE_404);
            return;
        }
        IbmHmProfitService hmProfitService = new IbmHmProfitService();
        Map<String, Object> profit = hmProfitService.findProfitLimit(handicapMemberId);
        if (ContainerTool.isEmpty(profit)) {
            bean.putEnum(CodeEnum.IBS_404_HANDICAP_MEMBER);
            bean.putSysEnum(CodeEnum.CODE_404);
            return;
        }
        long profitAmount = NumberTool.longValueT(msgObj.getString("profitAmount"));
        long profitMax = NumberTool.longValueT(profit.get("PROFIT_LIMIT_MAX_"));
        long profitMin = NumberTool.longValueT(profit.get("PROFIT_LIMIT_MIN_"));
        long lossMin = NumberTool.longValueT(profit.get("LOSS_LIMIT_MIN_"));

        boolean limitMin = profitAmount > 0 && profitMin != 0 && profitAmount < profitMin;
        //判断是否达到盈亏限额
        if (profitAmount > profitMax||limitMin||profitAmount < lossMin) {
            limitProcess();
        }

        hmProfitService.updateHandicapProfitInfo(handicapMemberId, profitAmount);
        bean.success();
    }

    /**
     * 限额处理
     */
    private void limitProcess() throws Exception {
        IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();
        List<Map<String, Object>> list=hmGameSetService.findHmGameIds(handicapMemberId);
        if(ContainerTool.isEmpty(list)){
            return ;
        }
		hmGameSetService.updateHmBetState(list);

        JSONObject content=new JSONObject();
        content.put("SET_ITEM_", IbmMethodEnum.SET_BET_STATE.name());
        content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());
        content.put("BET_STATE_", IbmTypeEnum.FALSE.name());
		String desc= this.getClass().getName().concat("，自动停止投注");
        for(Map<String,Object> map:list){
            content.put("GAME_CODE_", GameUtil.code(map.get("GAME_ID_")).name());

			EventThreadDefine.sendClientConfig(content,handicapMemberId,IbmTypeEnum.MEMBER,desc);
        }
    }
}
