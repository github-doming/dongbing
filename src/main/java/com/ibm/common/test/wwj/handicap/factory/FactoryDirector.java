package com.ibm.common.test.wwj.handicap.factory;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 16:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class FactoryDirector {

//	/**
//	 * 获取代理爬虫
//	 * @return 代理爬虫
//	 */
//	public static CrawlerAgent getCrawlerAgent(HandicapUtil.Code handicap) throws HandicapException{
//
//		switch (handicap) {
//			case HQ:
//				HqAgentCrawler hqCrawler = new HqAgentCrawler();
//				hqCrawler.putPort(new HqAgentGrab());
//				return hqCrawler;
//			case SGWIN:
//				SgWinAgentCrawler swCrawler = new SgWinAgentCrawler();
//				swCrawler.putPort(new SgWinAgentGrab());
//				return swCrawler;
//			case IDC:
//			default:
//				throw new HandicapException("尚未完成盘口工厂");
//		}
//	}
//
//	/**
//	 * 获取会员爬虫
//	 * @return 会员爬虫
//	 */
//	public static CrawlerMember getCrawlerMember(HandicapUtil.Code handicap) throws HandicapException {
//		switch (handicap) {
//			case HQ:
//				HqMemberCrawler hqCrawler = new HqMemberCrawler();
//				hqCrawler.putPort(new HqMemberGrab());
//				return hqCrawler;
//			case SGWIN:
//				SgWinMemberCrawler swCrawler = new SgWinMemberCrawler();
//				swCrawler.putPort(new SgWinMemberGrab());
//				return swCrawler;
//			case IDC:
//			default:
//				throw new HandicapException("尚未完成盘口工厂");
//		}
//	}
}
