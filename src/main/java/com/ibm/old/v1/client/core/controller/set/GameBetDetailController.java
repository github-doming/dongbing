package com.ibm.old.v1.client.core.controller.set;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.client.ibm_client_hm_game_set.t.entity.IbmClientHmGameSetT;
import com.ibm.old.v1.client.ibm_client_hm_game_set.t.service.IbmClientHmGameSetTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import net.sf.json.JSONObject;

import java.util.Date;
/**
 * @Description: 设置盘口游戏投注详情
 * @Author: Dongming
 * @Date: 2019-03-12 10:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GameBetDetailController implements ClientExecutor {
	@Override public JsonResultBeanPlus execute(JSONObject msgObj, IbmClientExistHmT existHmT) throws Exception {
		String gameId = msgObj.getString("gameId");

		IbmClientHmGameSetTService hmGameSetTService = new IbmClientHmGameSetTService();
		IbmClientHmGameSetT hmGameSetT = hmGameSetTService.findExist(existHmT.getIbmClientExistHmId(), gameId);
		//盘口会员已存在历史信息
		if (hmGameSetT != null) {
			//用户已存在与该服务中	<id匹配，状态open>
			hmGameSetT.setBetSecond(msgObj.get("betSecond"));
			hmGameSetT.setSplitTwoSideAmount(msgObj.get("splitTwoSideAmount"));
			hmGameSetT.setSplitNumberAmount(msgObj.get("splitNumberAmount"));
			hmGameSetT.setUpdateTime(new Date());
			hmGameSetT.setUpdateTimeLong(hmGameSetT.getUpdateTime().getTime());
			hmGameSetT.setState(IbmStateEnum.OPEN.name());
			hmGameSetTService.update(hmGameSetT);
		} else {
			//不存在历史盘口会员信息
			hmGameSetT = new IbmClientHmGameSetT();
			hmGameSetT.setClientExistHmId(existHmT.getIbmClientExistHmId());
			hmGameSetT.setHandicapMemberId(existHmT.getHandicapMemberId());
			hmGameSetT.setGameId(gameId);
			hmGameSetT.setGameCode(msgObj.get("gameCode"));
			hmGameSetT.setBetSecond(msgObj.get("betSecond"));
			hmGameSetT.setSplitTwoSideAmount(msgObj.get("splitTwoSideAmount"));
			hmGameSetT.setSplitNumberAmount(msgObj.get("splitNumberAmount"));
			hmGameSetT.setCreateTime(new Date());
			hmGameSetT.setCreateTimeLong(hmGameSetT.getCreateTime().getTime());
			hmGameSetT.setUpdateTime(new Date());
			hmGameSetT.setCreateTimeLong(hmGameSetT.getUpdateTime().getTime());
			hmGameSetT.setState(IbmStateEnum.OPEN.name());
			hmGameSetTService.save(hmGameSetT);
		}
		return SetToolController.gameLimit(existHmT, msgObj, gameId, hmGameSetTService);
	}
}
