package com.ibs.plan.module.client.core.controller;

import com.common.core.JsonResultBeanPlus;
import com.common.handicap.MemberOption;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.client.ibsc_hm_game_set.service.IbscHmGameSetService;

import java.sql.SQLException;
/**
 * 会员设置控制器
 *
 * @Author: null
 * @Date: 2020-06-06 18:00
 * @Version: v1.0
 */
public class MemberConfigController {

	/**
	 * 游戏限额
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @param hmGameSetId  盘口会员游戏设置id
	 * @return 获取结果
	 */
	public JsonResultBeanPlus gameLimit(String existHmId, HandicapUtil.Code handicapCode, GameUtil.Code gameCode,
			String hmGameSetId) throws SQLException {
		MemberOption memberOption = handicapCode.getMemberFactory().memberOption(existHmId);
		JsonResultBeanPlus bean = memberOption.gameLimit(gameCode);
		if (!bean.isSuccess()) {
			return bean;
		}
		if (bean.getData() != null) {
			IbscHmGameSetService hmGameSetService = new IbscHmGameSetService();
			hmGameSetService.updateGameLimit(hmGameSetId, bean.getData().toString());
			bean.setData(null);
		}
		return bean.success();
	}
}
