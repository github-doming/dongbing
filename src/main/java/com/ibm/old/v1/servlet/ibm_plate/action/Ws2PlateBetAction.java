package com.ibm.old.v1.servlet.ibm_plate.action;
import all.app.common.action.AppAction;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.servlet.ibm_plate.plate_ws2_bet.t.entity.PlateWs2BetT;
import com.ibm.old.v1.servlet.ibm_plate.service.Ws2PlateService;
import org.doming.core.tools.NumberTool;

import java.util.Date;
import java.util.Map;
/**
 * @Description: WS2盘口挡板投注类
 * http://IP:PORT/PROJECT/ibm_plate/getWs2PlateBet.do?t=010|0|1.983|10;013|1|1.983|30
 * @Author: Dongming
 * @Date: 2018-10-19 16:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Ws2PlateBetAction extends AppAction {
	@Override public String run() throws Exception {

		JsonResultBean jrb = new JsonResultBean();

		String betItemT = BeanThreadLocal.getThreadLocal().get().find(request, "t", "");
		String[] betItems = betItemT.split(";");

		try {
			Ws2PlateService ws2PlateService = new Ws2PlateService();
			for (String betItem : betItems) {
				String[] bet = betItem.split("\\|");


				String gamePlayCode = bet[0] + bet[1];
				System.out.println(gamePlayCode);
				Integer odds = NumberTool.intValueT(bet[2]);
				Map<String, Object> oddInfo = ws2PlateService.findOddInfoByCode(gamePlayCode);
				if (oddInfo == null || !odds.equals(oddInfo.get("ODDS_T_"))) {
					jrb.setCode(IbmCodeEnum.IBM_401_NOT_FIND_DATA.toString());
					jrb.setMsg(IbmCodeEnum.IBM_401_NOT_FIND_DATA.getMsgCn());
					jrb.setCodeSys(IbmCodeEnum.CODE_401.toString());
					jrb.setMessageSys(IbmCodeEnum.CODE_401.getMsgCn());
					jdbcTool = this.findJdbcToolLocal();
					transactionRoll(jdbcTool);
					return returnJson(jrb);
				}
				long amout = NumberTool.longValueT(bet[3]);
				PlateWs2BetT ws2BetT = new PlateWs2BetT();
				ws2BetT.setGameplayCode(gamePlayCode);
				ws2BetT.setGameCode("XYFT");
				ws2BetT.setOddsT(odds);
				ws2BetT.setAmoutT(amout);
				ws2BetT.setGameplayType((String) oddInfo.get("GAMEPLAY_TYPE_"));
				ws2BetT.setCreateTime(new Date());
				ws2BetT.setCreateTimeLong(ws2BetT.getCreateTime().getTime());
				ws2BetT.setUpdateTime(new Date());
				ws2BetT.setUpdateTimeLong(ws2BetT.getUpdateTime().getTime());
				ws2BetT.setState(IbmStateEnum.OPEN.name());
				ws2PlateService.save(ws2BetT);
			}
			jrb.setCode(IbmCodeEnum.IBM_200.toString());
			jrb.setMsg(IbmCodeEnum.IBM_200.getMsgCn());
			jrb.setCodeSys(IbmCodeEnum.CODE_200.toString());
			jrb.setMessageSys(IbmCodeEnum.CODE_200.getMsgCn());
			jrb.setSuccess(true);
		} catch (Exception e) {
			log.error(this.getClass(), e);
			jrb.setCodeSys(IbmCodeEnum.CODE_500.toString());
			jrb.setMessageSys(IbmCodeEnum.CODE_500.getMsgCn());
		}
		return returnJson(jrb);
	}
}
