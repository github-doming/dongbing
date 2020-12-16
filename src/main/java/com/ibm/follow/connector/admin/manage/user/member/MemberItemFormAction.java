package com.ibm.follow.connector.admin.manage.user.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 会员修改详情表单
 * @Author: null
 * @Date: 2020-03-19 16:40
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/member/itemForm")
public class MemberItemFormAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        //盘口会员id
        String handicapMemberId= StringTool.getString(dataMap.get("handicapMemberId"), "");

        try{
            Map<String,Object> map=new HashMap<>(4);
            //会员信息和自动登录信息
            Map<String,Object> itemInfo=new IbmHandicapMemberService().findItemInfo(handicapMemberId);
            if(ContainerTool.isEmpty(itemInfo)){
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            if(StringTool.isEmpty(itemInfo.get("LANDED_TIME_LONG_"))||
                    Long.parseLong(itemInfo.get("LANDED_TIME_LONG_").toString())==0){
                itemInfo.put("AUTO_LOGIN_STATE_", IbmTypeEnum.FALSE.name());
            }else{
                itemInfo.put("AUTO_LOGIN_STATE_", IbmTypeEnum.TRUE.name());
            }
            map.put("itemInfo",itemInfo);

            //会员可用游戏信息
            List<Map<String, Object>> availableGame=new IbmHmGameSetService().listGameInfo(handicapMemberId);
            for(Map<String,Object> gameInfo:availableGame){
                gameInfo.remove("BET_STATE_");
                gameInfo.remove("ICON_");
            }
            map.put("availableGame",availableGame);

            //所有可用盘口游戏信息
            List<Map<String, Object>> allHandicapGame=new IbmHandicapGameService().findHandicapInfo(itemInfo.remove("HANDICAP_ID_"));
            map.put("allHandicapGame",allHandicapGame);

            //盘口设置信息
            Map<String, Object> handicapSet=new IbmHmSetService().findShowInfo(handicapMemberId);
            handicapSet.put("BET_RATE_", NumberTool.doubleT(handicapSet.remove("BET_RATE_T_"))*100);
            map.put("handicapSet",handicapSet);

            bean.setData(map);
            bean.success();
        } catch (Exception e) {
            log.error("会员投注设置失败", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
