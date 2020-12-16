package com.ibm.follow.connector.pc.agent;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_ha_set.service.IbmHaSetService;
import com.ibm.follow.servlet.cloud.ibm_log_ha.entity.IbmLogHa;
import com.ibm.follow.servlet.cloud.ibm_log_ha.service.IbmLogHaService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;
/**
 * @Description: 跟投会员设置
 * @Author: null
 * @Date: 2019-09-12 16:50
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/agent/followMemberSet", method = HttpConfig.Method.POST) public class FollowMemberSetAction
		extends CommCoreAction {

	@Override public Object run() throws Exception {
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
		String handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();

		String followMemberType = dataMap.getOrDefault("FOLLOW_MEMBER_TYPE_", "").toString();

		String memberList = dataMap.getOrDefault("MEMBER_LIST_", "").toString();

		if (StringTool.isEmpty(handicapAgentId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		if (IbmTypeEnum.followType(followMemberType)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			//用户是否登录
			if (!IbmStateEnum.LOGIN.equal(new IbmHaInfoService().findLoginState(handicapAgentId))){
				bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			IbmHaSetService haSetService = new IbmHaSetService();
			Map<String, Object> haSet = haSetService.findShowInfo(handicapAgentId);
			if (ContainerTool.isEmpty(haSet)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			//格式化跟随会员列表信息
			if (StringTool.notEmpty(memberList)){
				JSONObject memberInfo = new JSONObject();
				JSONArray memberInfoList = JSON.parseArray(memberList);
				for(int i = 0; i < memberInfoList.size(); i++) {
					JSONArray info = memberInfoList.getJSONArray(i);
					if (StringTool.isEmpty(info.get(0))){
						continue;
					}
					memberInfo.put(info.getString(0),info.get(1));
				}
				memberList = memberInfo.toString();
			}
			haSetService.updateFollowList(handicapAgentId, followMemberType, memberList);
			//添加盘口代理日志信息
			saveHaLog(handicapAgentId, followMemberType, memberList);

			JSONObject content = new JSONObject();
			//指定跟随
			if (ContainerTool.notEmpty(memberList)) {
				content.put("FOLLOW_MEMBER_LIST_INFO_", memberList);
			}
			content.put("FOLLOW_MEMBER_TYPE_", followMemberType);
			content.put("SET_ITEM_", IbmMethodEnum.SET_HANDICAP.name());
			content.put("METHOD_",IbmMethodEnum.AGENT_SET.name());

			String desc= this.getClass().getName().concat("，跟投会员设置");
			boolean flag=EventThreadDefine.sendClientConfig(content,handicapAgentId,IbmTypeEnum.AGENT,desc);
			if (!flag){
				bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putEnum(IbmCodeEnum.CODE_403);
				return bean;
			}

		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("修改跟投信息失败"),e);
			bean.error(e.getMessage());
		}
		bean.success();
		return bean;
	}
	private void saveHaLog(String handicapAgentId, String followMemberType, String memberList) throws Exception {
		IbmLogHa logHa = new IbmLogHa();
		logHa.setHandicapAgentId(handicapAgentId);
		logHa.setAppUserId(appUserId);
		logHa.setHandleType("UPDATE");
		logHa.setHandleContent("FOLLOW_MEMBER_TYPE_:".concat(followMemberType).concat(",FOLLOW_MEMBER_LIST_INFO_:")
				.concat(memberList));
		logHa.setCreateTime(new Date());
		logHa.setCreateTimeLong(System.currentTimeMillis());
		logHa.setUpdateTimeLong(System.currentTimeMillis());
		logHa.setState(IbmStateEnum.OPEN.name());
		logHa.setDesc(this.getClass().getName());
		new IbmLogHaService().save(logHa);
	}
}
