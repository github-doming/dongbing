package com.ibm.old.v1.servlet.ibm_plate.action;
import all.app.common.action.AppAction;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.servlet.ibm_plate.service.Ws2PlateService;
import net.sf.json.JSONObject;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;
/**
 * @Description: WS2盘口挡板获取赔率类
 * http://IP:PORT/PROJECT/ibm_plate/getWs2PlateOdds.do?play=bothSides
 * @Author: Dongming
 * @Date: 2018-10-20 09:49
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Ws2PlateOddAction extends AppAction {
	@Override public String run() throws Exception {
		JsonResultBean jrb = new JsonResultBean();
		String play = BeanThreadLocal.getThreadLocal().get().find(request, "play", "");
		if (StringTool.isEmpty(play)) {
			jrb.setCode(IbmCodeEnum.IBM_401_NOT_FIND_DATA.toString());
			jrb.setMsg(IbmCodeEnum.IBM_401_NOT_FIND_DATA.getMsgCn());
			jrb.setCodeSys(IbmCodeEnum.CODE_401.toString());
			jrb.setMessageSys(IbmCodeEnum.CODE_401.getMsgCn());
			return returnJson(jrb);
		}

		try {
			Ws2PlateService ws2PlateService = new Ws2PlateService();
			List<Map<String, Object>> oddInfo;
			switch (play) {
				case "bothSides":
					oddInfo = ws2PlateService.findOddInfoByType("bothSides");
					break;
				case "ballNO":
					oddInfo = ws2PlateService.findOddInfoByType("ballNO");
					break;
				case "sumDT":
					oddInfo = ws2PlateService.findOddInfoByType("sumDT");
					break;
				default:
					jrb.setCode(IbmCodeEnum.IBM_401_NOT_FIND_DATA.toString());
					jrb.setMsg("暂未开放");
					jrb.setCodeSys(IbmCodeEnum.CODE_404.toString());
					jrb.setMessageSys(IbmCodeEnum.CODE_404.getMsgCn());
					return returnJson(jrb);
			}
			JSONObject data = new JSONObject();
			for (Map<String,Object> info : oddInfo){
				JSONObject item = new JSONObject();
				item.put("odds",info.get("ODDS_T_"));
				item.put("gameplayCode",info.get("GAMEPLAY_CODE_"));
				data.put(info.get("GAMEPLAY_NAME_"),item);
			}
			jrb.setCode(IbmCodeEnum.IBM_200.toString());
			jrb.setMsg(IbmCodeEnum.IBM_200.getMsgCn());
			jrb.setCodeSys(IbmCodeEnum.CODE_200.toString());
			jrb.setMessageSys(IbmCodeEnum.CODE_200.getMsgCn());
			jrb.setData(data);
			jrb.setSuccess(true);
		} catch (Exception e) {
			log.error(this.getClass(), e);
			jrb.setCodeSys(IbmCodeEnum.CODE_500.toString());
			jrb.setMessageSys(IbmCodeEnum.CODE_500.getMsgCn());
		}
		return returnJson(jrb);
	}
}
