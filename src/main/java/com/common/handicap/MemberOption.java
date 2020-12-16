package com.common.handicap;


import com.common.core.JsonResultBeanPlus;
import com.common.handicap.crawler.entity.MemberUser;
import com.common.util.BaseGameUtil;

import java.util.List;

/**
 * 会员操作
 *
 * @Author: Dongming
 * @Date: 2020-05-11 17:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface MemberOption {


	/**
	 * 需要登录
	 */
	void needLogin();

	/**
	 * 会员基本信息
	 *
	 * @param refresh 刷新数据
	 * @return 基本信息
	 */
	MemberUser userInfo(boolean refresh);

	/**
	 * 检验信息
	 *
	 * @return 检验结果
	 */
	JsonResultBeanPlus checkInfo();

	/**
	 * 某个游戏的限额信息
	 *
	 * @param gameCode 游戏编码
	 * @return 游戏限额信息
	 */
	JsonResultBeanPlus gameLimit(BaseGameUtil.Code gameCode);

	/**
	 * 获取盘口赔率
	 *
	 * @param gameCode 游戏code
	 * @param betType  投注类型
	 * @return 盘口赔率信息
	 */
	JsonResultBeanPlus oddsInfo(BaseGameUtil.Code gameCode, String betType);

	/**
	 * 投注
	 *
	 * @param gameCode  游戏code
	 * @param period    期数字符串
	 * @param betType   投注类型
	 * @param betItems  投注信息
	 * @param betErrors 投注错误项
	 * @return 投注结果
	 */
	JsonResultBeanPlus betting(BaseGameUtil.Code gameCode, Object period, String betType, List<String> betItems,
							   List<String> betErrors);

}
