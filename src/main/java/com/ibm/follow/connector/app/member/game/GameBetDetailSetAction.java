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
 * @Description: 修改游戏设置详情
 * @Author: null
 * @Date: 2019-11-23 17:51
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/member/gameBetDetail", method = HttpConfig.Method.POST)
public class GameBetDetailSetAction extends CommCoreAction {

    private String handicapMemberId;
    private String gameCode;
    private String betAutoStart;
    private String betAutoStartTime;
    private String betAutoStop;
    private String betAutoStopTime;
    private String betSecond;
    private String splitTwoSideAmount;
    private String splitNumberAmount;

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
        if (valiParameters()) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        try {
            //用户是否登录
            if (!IbmStateEnum.LOGIN.equal(new IbmHmInfoService().findLoginState(handicapMemberId))) {
                bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            String gameId = GameUtil.id(gameCode);
            if (StringTool.isEmpty(gameId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();
            IbmHmGameSet hmGameSet = hmGameSetService.findByHmIdAndGameCode(handicapMemberId, gameId);
            if (hmGameSet == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return super.returnJson(bean);
            }
            hmGameSet.setBetAutoStop(betAutoStop);
            hmGameSet.setBetAutoStopTimeLong(Long.parseLong(betAutoStopTime) + 5000L);
            hmGameSet.setBetAutoStart(betAutoStart);
            hmGameSet.setBetAutoStartTimeLong(Long.parseLong(betAutoStartTime) + 5000L);
            hmGameSet.setBetSecond(Integer.parseInt(betSecond));
            hmGameSet.setSplitTwoSideAmount(Integer.parseInt(splitTwoSideAmount));
            hmGameSet.setSplitNumberAmount(Integer.parseInt(splitNumberAmount));
            hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
            hmGameSetService.update(hmGameSet);

			//添加盘口会员日志信息
			saveHmLog();

            //写入客户设置事件
            JSONObject content = new JSONObject();
            content.put("BET_STATE_", hmGameSet.getBetState());
            content.put("GAME_CODE_", gameCode);
            content.put("SET_ITEM_", IbmMethodEnum.SET_GAME.name());
            content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());
            content.put("BET_MODE_", hmGameSet.getBetMode());
            content.put("BET_SECOND_", betSecond);
            content.put("SPLIT_TWO_SIDE_AMOUNT_", splitTwoSideAmount);
            content.put("SPLIT_NUMBER_AMOUNT_", splitNumberAmount);

			String desc= this.getClass().getName().concat("，修改游戏设置");
			boolean flag=EventThreadDefine.sendClientConfig(content,handicapMemberId,IbmTypeEnum.MEMBER,desc);
			if (!flag){
				bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putEnum(IbmCodeEnum.CODE_403);
				return bean;
			}

            bean.success();
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("修改游戏详情信息失败"),e);
            bean.error(e.getMessage());
        }
        return bean;
    }
    private void saveHmLog() throws Exception {
        IbmLogHm logHm = new IbmLogHm();
        logHm.setHandicapMemberId(handicapMemberId);
        logHm.setAppUserId(appUserId);
        logHm.setHandleType("UPDATE");
        logHm.setHandleContent("BET_AUTO_START_:".concat(betAutoStart).concat(",BET_AUTO_START_TIME_LONG_:")
                .concat(betAutoStartTime).concat(",BET_AUTO_STOP_:").concat(betAutoStop)
                .concat(",BET_AUTO_STOP_TIME_LONG_:").concat(betAutoStopTime).concat(",BET_SECOND_:").concat(betSecond)
                .concat(",SPLIT_TWO_SIDE_AMOUNT_:").concat(splitTwoSideAmount).concat(",SPLIT_NUMBER_AMOUNT_:")
                .concat(splitNumberAmount));
        logHm.setCreateTime(new Date());
        logHm.setCreateTimeLong(System.currentTimeMillis());
        logHm.setUpdateTimeLong(System.currentTimeMillis());
        logHm.setState(IbmStateEnum.OPEN.name());
        logHm.setDesc(this.getClass().getName());
        new IbmLogHmService().save(logHm);
    }
    private boolean valiParameters() {
        handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
        gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

        betAutoStart = dataMap.getOrDefault("BET_AUTO_START_", "").toString();
        betAutoStartTime = dataMap.getOrDefault("BET_AUTO_START_TIME_LONG_", "").toString();

        betAutoStop = dataMap.getOrDefault("BET_AUTO_STOP_", "").toString();
        betAutoStopTime = dataMap.getOrDefault("BET_AUTO_STOP_TIME_LONG_", "").toString();

        betSecond = dataMap.getOrDefault("BET_SECOND_", "").toString();
        splitTwoSideAmount = dataMap.getOrDefault("SPLIT_TWO_SIDE_AMOUNT_", "").toString();
        splitNumberAmount = dataMap.getOrDefault("SPLIT_NUMBER_AMOUNT_", "").toString();
        if (StringTool.isEmpty(handicapMemberId, gameCode)) {
            return true;
        }
        if (StringTool.isEmpty(betAutoStopTime, betAutoStopTime, betSecond,
                splitTwoSideAmount, splitNumberAmount)) {
            return true;
        }
        return !IbmTypeEnum.booleanType(betAutoStop) || !IbmTypeEnum.booleanType(betAutoStart);
    }
}
