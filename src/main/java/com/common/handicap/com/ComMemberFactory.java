package com.common.handicap.com;


import com.common.handicap.BaseMemberFactory;
import com.common.handicap.Handicap;

/**
 * Com 盘口
 *
 * @Author: Dongming
 * @Date: 2020-06-10 16:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ComMemberFactory extends BaseMemberFactory<ComMemberCrawler> {

	private static final ComMemberFactory INSTANCE = new ComMemberFactory();
	private ComMemberFactory() {
	}
	public static ComMemberFactory getInstance() {
		return INSTANCE;
	}
	@Override protected ComMemberCrawler newCrawler() {
		return new ComMemberCrawler();
	}
	@Override public Handicap handicap() {
		return Com.getInstance();
	}
}
