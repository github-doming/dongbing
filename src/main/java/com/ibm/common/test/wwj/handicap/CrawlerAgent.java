package com.ibm.common.test.wwj.handicap;

import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.game.GameUtil;

import java.util.List;
import java.util.Map;
/**
 * @Description: 代理盘口爬虫
 * @Author: Dongming
 * @Date: 2019-11-22 10:24
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface CrawlerAgent extends CrawlerHandicap {


	/**
	 * 会员列表信息
	 *
	 * @param refresh 刷新数据
	 * @return 会员列表信息
	 * @throws HandicapException 盘口错误信息
	 */
	List<Map<String, String>> memberList(boolean refresh) throws HandicapException;

	/**
	 * 投注未结算摘要
	 *
	 * @param gameCode 游戏编码
	 * @return 未结算摘要
	 */
	JsonResultBeanPlus betSummary(GameUtil.Code gameCode);

	/**
	 * 投注未结算详情
	 *
	 * @param gameCode  游戏编码
	 * @param period    期数
	 * @param member    会员
	 * @param oddNumber 注单号
	 * @return 未结算详情
	 */
	JsonResultBeanPlus betDetail(GameUtil.Code gameCode, String period, Object member, String oddNumber);
}
