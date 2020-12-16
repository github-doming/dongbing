package com.ibm.old.v1.admin.ibm_game.w.action;

import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Description: 获取所有游戏信息json
 * @date 2019年3月12日 下午3:41:51 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmGameListJsonAction extends BaseAppAction{

	@Override
	public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		
		try {
			IbmGameTService gameTService = new IbmGameTService();
			List<Map<String, Object>> list = gameTService.findAllGame();
			jrb.setData(list);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN,e);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
		}
		return returnJson(jrb);
	}

}
