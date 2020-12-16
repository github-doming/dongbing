package com.ibm.follow.servlet.client.core.controller.config;

import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.ibmc_hm_game_set.service.IbmcHmGameSetService;

import java.sql.SQLException;
/**
 * @Description: 设置工具类
 * @Author: null
 * @Date: 2019-09-18 16:31
 * @Version: v1.0
 */
public class SetToolController {

	/**
	 * 获取游戏限额
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @param hmGameSetId  盘口会员游戏设置id
	 * @return 游戏限额
	 */
	public static JsonResultBeanPlus gameLimit(String existHmId, HandicapUtil.Code handicapCode, GameUtil.Code gameCode,
			String hmGameSetId) throws SQLException {
		JsonResultBeanPlus bean=handicapCode.getMemberFactory().memberOption(existHmId).gameLimit(gameCode);
		if(!bean.isSuccess()){
			return bean;
		}
		if(bean.getData()!=null){
			IbmcHmGameSetService hmGameSetService=new IbmcHmGameSetService();
			hmGameSetService.updateGameLimit(hmGameSetId,bean.getData().toString());
			bean.setData(null);
		}
		bean.success();
		return bean;
	}
}
