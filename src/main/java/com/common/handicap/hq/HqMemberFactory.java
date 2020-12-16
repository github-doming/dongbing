package com.common.handicap.hq;


import com.common.handicap.BaseMemberFactory;
import com.common.handicap.Handicap;

/**
 * Hq 盘口
 * @Author: Dongming
 * @Date: 2020-06-09 14:27
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HqMemberFactory  extends BaseMemberFactory<HqMemberCrawler> {

	private static final HqMemberFactory INSTANCE = new HqMemberFactory();
	private HqMemberFactory() {
	}
	public static HqMemberFactory getInstance() {
		return INSTANCE;
	}
	@Override protected HqMemberCrawler newCrawler() {
		return new HqMemberCrawler();
	}
	@Override public Handicap handicap() {
		return Hq.getInstance();
	}
}
