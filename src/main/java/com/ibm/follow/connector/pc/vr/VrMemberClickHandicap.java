package com.ibm.follow.connector.pc.vr;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.vr_fm_game_set.service.VrFmGameSetService;
import com.ibm.follow.servlet.cloud.vr_user_follow_member.service.VrUserFollowMemberService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 点击虚拟会员盘口
 * @Author: null
 * @Date: 2020-07-23 09:40
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/home/clickVrMemberHandicap", method = HttpConfig.Method.GET)
public class VrMemberClickHandicap extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			Map<String, Object> data = new HashMap<>(2);
			VrFmGameSetService gameSetService=new VrFmGameSetService();

			VrUserFollowMemberService followMemberService=new VrUserFollowMemberService();
			List<Map<String,Object>> vrMemberInfos=followMemberService.listVrMemberInfos(appUserId);
			List<Map<String, Object>> onlineInfo=new ArrayList<>();
			List<Map<String, Object>> offlineInfo=new ArrayList<>();
			for(Map<String,Object> vrMemberInfo:vrMemberInfos){
				if(gameSetService.findGameInfo(appUserId,vrMemberInfo.get("VR_MEMBER_ID_"), IbmTypeEnum.TRUE)){
					offlineInfo.add(vrMemberInfo);
				}else{
					onlineInfo.add(vrMemberInfo);
				}
			}
			data.put("onHostingInfo", onlineInfo);
			data.put("offHostingInfo", offlineInfo);

			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("点击会员盘口错误"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
