package com.ibm.common.utils.http.tools;

import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.http.utils.agent.*;
import com.ibm.common.utils.http.utils.member.*;

/**
 * 会员爬虫工厂
 *
 * @Author: Dongming
 * @Date: 2020-01-04 10:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CrawlerFactory {

	private static final CrawlerFactory FACTORY = new CrawlerFactory();
	private CrawlerFactory(){}
	public static CrawlerFactory getFactory(){
		return FACTORY;
	}

    /**
     * 获取会员爬虫信息
     * @param handicapCode 会员所在盘口
     * @return 会员爬虫
     */
    public BaseMemberUtil getMemberCrawler(HandicapUtil.Code handicapCode) {
        switch (handicapCode) {
            case IDC:
                return IdcMemberApiUtil.findInstance();
            case SGWIN:
                return SgWinMemberUtil.findInstance();
            case HQ:
                return HqMemberUtil.findInstance();
            case COM:
                return ComMemberUtil.findInstance();
			case NEWCC:
				return NewCcMemberUtil.findInstance();
			case UN:
				return UNMemberUtil.findInstance();
			case NEWWS:
				return NewWsMemberUtil.findInstance();
			case NEWMOA:
				return NewMoaMemberUtil.findInstance();
			case FC:
				return FCMemberUtil.findInstance();
            default:
                throw new IllegalArgumentException("尚未开发的盘口".concat(handicapCode.getName()));
        }
    }

	/**
	 * 获取代理爬虫信息
	 * @param handicapCode 代理所在盘口
	 * @return 代理爬虫
	 */
	public BaseAgentUtil getAgentCrawler(HandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
				return IdcAgentApiUtil.findInstance();
			case SGWIN:
				return SgWinAgentUtil.findInstance();
			case HQ:
				return HqAgentUtil.findInstance();
			case COM:
				return ComAgentUtil.findInstance();
			case NEWCC:
				return NewCcAgentUtil.findInstance();
			case NEWWS:
				return NewWsAgentUtil.findInstance();
			case NEWMOA:
				return NewMoaAgentUtil.findInstance();
			case UN:
				return UNAgentUtil.findInstance();
			case FC:
				return FCAgentUtil.findInstance();
			case NCOM1:
			case NCOM2:
			default:
				throw new IllegalArgumentException("尚未开发的盘口".concat(handicapCode.getName()));
		}


	}
}
