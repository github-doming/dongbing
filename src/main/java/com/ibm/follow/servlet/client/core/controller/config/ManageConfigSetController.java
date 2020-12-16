package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.ibmc_agent_member_info.entity.IbmcAgentMemberInfo;
import com.ibm.follow.servlet.client.ibmc_agent_member_info.service.IbmcAgentMemberInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.LogTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.Date;
/**
 * @Description: 管理设置
 * @Author: Dongming
 * @Date: 2019-09-24 15:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ManageConfigSetController implements ClientExecutor {
	protected static final Logger log = LogManager.getLogger(ManageConfigSetController.class);
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		String existHaId = msgObj.getString("EXIST_HA_ID_");
		//验证内存
		if (CustomerCache.stateInfo(existHaId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
        String existHmId = msgObj.getString("EXIST_HM_ID_");
		switch (IbmMethodEnum.valueOf(msgObj.getString("SET_ITEM_"))) {
			case SET_BIND:
				setBind(existHaId,existHmId, msgObj);
				break;
			case SET_UNBIND:
				new IbmcAgentMemberInfoService().unbind(existHaId,existHmId);
                bean.success();
				break;
            case SET_BET_MODE:
                setBetMode(existHaId,existHmId, msgObj);
                break;
			default:
				bean.putEnum(CodeEnum.IBS_404_METHOD);
				bean.putSysEnum(CodeEnum.CODE_404);
				LogTool.error(log, this, "错误的代理配置方法接收".concat(msgObj.getString("SET_ITEM")));
		}

		return bean;
	}

    /**
     * 设置游戏投注模式
     * @param existHaId     已存在盘口代理id
     * @param existHmId     已存在盘口会员id
     * @param msgObj        设置信息
     */
    private void setBetMode(String existHaId, String existHmId, JSONObject msgObj) throws SQLException {
        String gameCode = msgObj.getString("GAME_CODE_");
        String betMode = msgObj.getString("BET_MODE_");
        IbmcAgentMemberInfoService agentMemberInfoService = new IbmcAgentMemberInfoService();
        String betModeInfo = agentMemberInfoService.findBetModeInfo(existHaId, existHmId);
        if(StringTool.isEmpty(betModeInfo)){
            bean.putEnum(CodeEnum.IBS_404_EXIST);
            bean.putSysEnum(CodeEnum.CODE_404);
            return;
        }
        JSONObject infoObj=JSONObject.parseObject(betModeInfo);
        infoObj.put(gameCode,betMode);

        agentMemberInfoService.updateBetModeInfo(existHaId, existHmId,infoObj);
        bean.success();
    }

    /**
     *设置绑定
     * @param existHaId     已存在盘口代理id
     * @param existHmId     已存在盘口会员id
     * @param msgObj        设置信息
     */
	private void setBind(String existHaId, String existHmId,JSONObject msgObj) throws Exception {
		JSONObject betModeInfo = msgObj.getJSONObject("BET_MODE_INFO_");
		String clientCode = msgObj.getString("CLIENT_CODE_");
        String memberHandicapCode=msgObj.getString("MEMBER_HANDICAP_CODE_");
        String memberAccount=msgObj.getString("MEMBER_ACCOUNT_");

		IbmcAgentMemberInfoService agentMemberInfoService = new IbmcAgentMemberInfoService();
		String agentMemberInfoId = agentMemberInfoService.findId(existHaId, existHmId);
		Date nowTime = new Date();
		if (StringTool.isEmpty(agentMemberInfoId)){
			IbmcAgentMemberInfo agentMemberInfo = new IbmcAgentMemberInfo();
			agentMemberInfo.setExistHaId(existHaId);
			agentMemberInfo.setExistHmId(existHmId);
			agentMemberInfo.setBetModeInfo(betModeInfo);
			agentMemberInfo.setMemberClientCode(clientCode);
            agentMemberInfo.setMemberHandicapCode(memberHandicapCode);
            agentMemberInfo.setMemberAccount(memberAccount);
			agentMemberInfo.setCreateTime(nowTime);
			agentMemberInfo.setCreateTimeLong(System.currentTimeMillis());
			agentMemberInfo.setUpdateTimeLong(System.currentTimeMillis());
			agentMemberInfo.setState(IbmStateEnum.OPEN.name());
			agentMemberInfo.setDesc("管理设置绑定代理会员");
			agentMemberInfoService.save(agentMemberInfo);
		}else{
			agentMemberInfoService.update(agentMemberInfoId,clientCode,betModeInfo,memberHandicapCode,memberAccount,nowTime);
		}
        bean.success();
	}
}
