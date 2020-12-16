package com.ibm.follow.connector.app.member.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.entity.IbmHmGameSet;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_log_hm.entity.IbmLogHm;
import com.ibm.follow.servlet.cloud.ibm_log_hm.service.IbmLogHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 修改游戏投注状态
 * @Author: null
 * @Date: 2019-11-26 16:35
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/member/gameBetState", method = HttpConfig.Method.GET)
public class GameBetStateAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }
        String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();

        String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

        String betState = dataMap.getOrDefault("BET_STATE_", "").toString();

        if (StringTool.isEmpty(handicapMemberId,gameCode)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        if(!IbmTypeEnum.booleanType(betState)){
            bean.putEnum(IbmCodeEnum.IBM_401_STATE);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
            //用户是否登录
            if (!IbmStateEnum.LOGIN.equal(new IbmHmInfoService().findLoginState(handicapMemberId))){
                bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            String gameId= GameUtil.id(gameCode);
            if(StringTool.isEmpty(gameId)){
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }

            IbmHmGameSetService hmGameSetService=new IbmHmGameSetService();
            IbmHmGameSet hmGameSet=hmGameSetService.findByHmIdAndGameCode(handicapMemberId,gameId);
            if(hmGameSet==null){
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return super.returnJson(bean);
            }
            hmGameSet.setBetState(betState);
            hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
            hmGameSetService.update(hmGameSet);

			//添加盘口会员日志信息
			saveHmLog(handicapMemberId,betState);

            //写入客户设置事件
            JSONObject content = new JSONObject();
            content.put("BET_STATE_",betState);
            content.put("GAME_CODE_",gameCode);
            content.put("SET_ITEM_", IbmMethodEnum.SET_BET_STATE.name());
            content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());

			String desc= this.getClass().getName().concat("，修改投注状态");
			boolean flag=EventThreadDefine.sendClientConfig(content,handicapMemberId,IbmTypeEnum.MEMBER,desc);
			if (!flag){
				bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
            bean.success();
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("修改游戏投注状态失败"),e);
            bean.error(e.getMessage());
        }
        return bean;
    }
    private void saveHmLog(String handicapMemberId,String betState) throws Exception {
        IbmLogHm logHm = new IbmLogHm();
        logHm.setHandicapMemberId(handicapMemberId);
        logHm.setAppUserId(appUserId);
        logHm.setHandleType("UPDATE");
        logHm.setHandleContent("BET_STATE_".concat(betState));
        logHm.setCreateTime(new Date());
        logHm.setCreateTimeLong(System.currentTimeMillis());
        logHm.setUpdateTimeLong(System.currentTimeMillis());
        logHm.setState(IbmStateEnum.OPEN.name());
        logHm.setDesc(this.getClass().getName());
        new IbmLogHmService().save(logHm);
    }
}
