package com.ibm.old.v1.servlet.ibm_plate.action;

import all.app.common.action.AppAction;
import c.a.util.core.json.JsonResultBean;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.servlet.ibm_plate.service.Ws2PlateService;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;
/**
 * 
 * @ClassName: PlateOddsAction
 *             http://IP:PORT/PROJECT/ibm/ibm_plate/getPlateOdds.do
 * @Description: 挡板获取赔率
 * @author zjj
 * @date 2019年2月18日 下午2:47:27
 *
 */
public class PlateOddsAction extends AppAction {

	@Override
	public String run() throws Exception {
		JsonResultBean jrb = new JsonResultBean();

		try {
			Ws2PlateService ws2PlateService = new Ws2PlateService();
			
			List<Map<String, Object>> oddInfo = ws2PlateService.findOddInfoByType();
			
			JSONObject data = new JSONObject();
			for (Map<String, Object> info : oddInfo) {
				JSONObject item = new JSONObject();
				item.put("odds", info.get("ODDS_T_"));
				item.put("gameplayCode", info.get("GAMEPLAY_CODE_"));
				data.put(info.get("GAMEPLAY_NAME_"), item);
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
