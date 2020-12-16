package com.ibm.follow.connector.admin.manage.user.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.entity.IbmHandicapMember;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.entity.IbmHmGameSet;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * @Description: 会员投注设置
 * @Author: null
 * @Date: 2020-03-19 16:16
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/member/betSet")
public class MemberBetSetAction extends CommAdminAction {
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
        //游戏投注设置
        String gameBetSet= StringTool.getString(dataMap.get("gameBetSet"), "");

        if(StringTool.isEmpty(handicapMemberId,gameBetSet)){
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        try{
            IbmHandicapMember handicapMember = new IbmHandicapMemberService().find(handicapMemberId);
            if (handicapMember == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();

            JSONArray array=JSONArray.parseArray(gameBetSet);
            IbmHmGameSet hmGameSet;
            for(Object jsonObj:array){
                JSONObject gameSet= (JSONObject) jsonObj;
                String gameId = GameUtil.id(gameSet.getString("GAME_CODE_"));
                hmGameSet = hmGameSetService.findByHmIdAndGameCode(handicapMemberId, gameId);
                if (hmGameSet == null) {
                    continue;
                }
                hmGameSet.setBetState(gameSet.getString("BET_STATE_"));
                hmGameSet.setBetAutoStart(gameSet.getString("BET_AUTO_START_"));
                hmGameSet.setBetAutoStartTimeLong(Long.parseLong(gameSet.getString("BET_AUTO_START_TIME_LONG_"))+5000L);
                hmGameSet.setBetAutoStop(gameSet.getString("BET_AUTO_STOP_"));
                hmGameSet.setBetAutoStopTimeLong(Long.parseLong(gameSet.getString("BET_AUTO_STOP_TIME_LONG_"))+5000L);
                hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
                hmGameSetService.update(hmGameSet);
            }
            String loginState = new IbmHmInfoService().findLoginState(handicapMemberId);
            if (StringTool.isEmpty(loginState) || !IbmStateEnum.LOGIN.name().equals(loginState)) {
                bean.success();
                return bean.toJsonString();
            }
            //发送游戏信息
            List<Map<String, Object>> gameInfo = hmGameSetService.findByHmId(handicapMember.getIbmHandicapMemberId());
            JSONObject content = new JSONObject();
            content.put("GAME_INFO_", gameInfo);
            content.put("SET_ITEM_", IbmMethodEnum.SET_GAME_INFO.name());
            content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());

			String desc= this.getClass().getName().concat("，发送会员游戏设置信息");
			boolean flag=EventThreadDefine.sendClientConfig(content,handicapMemberId, IbmTypeEnum.MEMBER,desc);
			if (!flag){
				bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putEnum(IbmCodeEnum.CODE_403);
				return bean;
			}

            bean.success();
        } catch (Exception e) {
            log.error("会员投注设置失败", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
