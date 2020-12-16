package com.ibm.follow.connector.pc.member.game.setting;

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
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.service.IbmHandicapAgentMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.entity.IbmHmGameSet;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_log_hm.entity.IbmLogHm;
import com.ibm.follow.servlet.cloud.ibm_log_hm.service.IbmLogHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 修改游戏详情信息
 * @Author: zjj
 * @Date: 2019-09-09 15:47
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/member/gameBetDetail", method = HttpConfig.Method.POST) public class GameBetDetailAction
		extends CommCoreAction {

	private String handicapMemberId;
	private String gameCode;
	private String betMode;
	private String betSecond;
	private String splitTwoSideAmount;
	private String splitNumberAmount;

	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (!valiParameters()) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }
		try {
			//用户是否登录
			if (!IbmStateEnum.LOGIN.equal(new IbmHmInfoService().findLoginState(handicapMemberId))){
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
			Date nowTime=new Date();
            JSONObject content = new JSONObject();
			if(!hmGameSet.getBetMode().equals(betMode)){
                hmGameSet.setBetMode(betMode);
                //获取客户端盘口会员信息
                IbmClientHmService clientHmService = new IbmClientHmService();
                Map<String, Object> bindInfo = clientHmService.findBindInfo(handicapMemberId);
                if(ContainerTool.isEmpty(bindInfo)){
                    log.error("获取盘口会员【" + handicapMemberId + "】存在会员信息失败");
                    bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                    bean.putSysEnum(IbmCodeEnum.CODE_404);
                    return super.returnJson(bean);
                }

                content.put("EXIST_HM_ID_",bindInfo.get("EXIST_HM_ID_"));
                content.put("SET_ITEM_", IbmMethodEnum.SET_BET_MODE.name());
                content.put("METHOD_", IbmMethodEnum.MANAGE_SET.name());
                content.put("GAME_CODE_", gameCode);
                content.put("BET_MODE_",betMode);
                //获取绑定信息
                IbmHandicapAgentMemberService agentMemberService=new IbmHandicapAgentMemberService();
                List<String> handicapAgentIds=agentMemberService.findHaIdByHmId(handicapMemberId);
				String desc= this.getClass().getName().concat("，添加绑定数据设置");
                for(String handicapAgentId:handicapAgentIds){
					EventThreadDefine.sendClientConfig(content,handicapAgentId,IbmTypeEnum.AGENT,desc);
                }
                content.clear();
            }

			hmGameSet.setBetSecond(Integer.parseInt(betSecond));
			hmGameSet.setSplitTwoSideAmount(Integer.parseInt(splitTwoSideAmount));
			hmGameSet.setSplitNumberAmount(Integer.parseInt(splitNumberAmount));
			hmGameSet.setUpdateTimeLong(nowTime.getTime());
			hmGameSetService.update(hmGameSet);

			//添加盘口会员日志信息
			saveHmLog(hmGameSet,nowTime);
			//写入客户设置事件
			content.put("BET_STATE_", hmGameSet.getBetState());
			content.put("GAME_CODE_", gameCode);
			content.put("SET_ITEM_", IbmMethodEnum.SET_GAME.name());
			content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());
			content.put("BET_SECOND_", betSecond);
			content.put("SPLIT_TWO_SIDE_AMOUNT_", splitTwoSideAmount);
			content.put("SPLIT_NUMBER_AMOUNT_", splitNumberAmount);

			String desc= this.getClass().getName().concat("，修改投注状态");
			boolean flag=EventThreadDefine.sendClientConfig(content,handicapMemberId,IbmTypeEnum.MEMBER,desc);
			if (!flag){
				bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putEnum(IbmCodeEnum.CODE_403);
				return bean;
			}

			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("修改游戏详情信息失败")+e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}
	private void saveHmLog(IbmHmGameSet hmGameSet,Date nowTime) throws Exception {
		IbmLogHm logHm = new IbmLogHm();
		logHm.setHandicapMemberId(hmGameSet.getHandicapMemberId());
		logHm.setAppUserId(appUserId);
		logHm.setHandleType("UPDATE");
		logHm.setHandleContent("BET_MODE_:".concat(betMode).concat(",BET_SECOND_:").concat(betSecond)
				.concat(",SPLIT_TWO_SIDE_AMOUNT_:").concat(splitTwoSideAmount).concat(",SPLIT_NUMBER_AMOUNT_:")
				.concat(splitNumberAmount));
		logHm.setCreateTime(nowTime);
		logHm.setCreateTimeLong(nowTime.getTime());
		logHm.setUpdateTimeLong(nowTime.getTime());
		logHm.setState(IbmStateEnum.OPEN.name());
		logHm.setDesc(this.getClass().getName());
		new IbmLogHmService().save(logHm);
	}
	private boolean valiParameters() {
		handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		betMode = dataMap.getOrDefault("BET_MODE_", "").toString();
		betSecond = dataMap.getOrDefault("BET_SECOND_", "").toString();
		splitTwoSideAmount = dataMap.getOrDefault("SPLIT_TWO_SIDE_AMOUNT_", "").toString();
		splitNumberAmount = dataMap.getOrDefault("SPLIT_NUMBER_AMOUNT_", "").toString();

		if (StringTool.isEmpty(handicapMemberId, gameCode, betSecond, splitTwoSideAmount, splitNumberAmount)) {
			return false;
		}

		return IbmTypeEnum.betModelType(betMode);
	}
}
