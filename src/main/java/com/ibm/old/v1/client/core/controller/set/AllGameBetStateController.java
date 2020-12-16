package com.ibm.old.v1.client.core.controller.set;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.client.ibm_client_hm_game_set.t.service.IbmClientHmGameSetTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import net.sf.json.JSONObject;
/**
 * @Description: 所有盘口游戏投注状态设置
 * @Author: zjj
 * @Date: 2019-06-20 17:59
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AllGameBetStateController implements ClientExecutor {

	@Override public JsonResultBeanPlus execute(JSONObject msgObj, IbmClientExistHmT existHmT) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		IbmClientHmGameSetTService hmGameSetTService = new IbmClientHmGameSetTService();
		int number=hmGameSetTService.findAllExist(existHmT.getIbmClientExistHmId());

		if(number==0){
			bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		bean.success();
		return bean;
	}
}
