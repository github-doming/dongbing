package com.common.handicap.sgwin;


import com.common.handicap.BaseMemberFactory;
import com.common.handicap.Handicap;

/**
 *  SgWin 盘口
 * @Author: Dongming
 * @Date: 2020-06-09 14:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SgWinMemberFactory extends BaseMemberFactory<SgWinMemberCrawler> {

	private static final SgWinMemberFactory INSTANCE = new SgWinMemberFactory();
	private SgWinMemberFactory() {
	}
	public static SgWinMemberFactory getInstance() {
		return INSTANCE;
	}
	@Override protected SgWinMemberCrawler newCrawler() {
		return new SgWinMemberCrawler();
	}
	@Override public Handicap handicap() {
		return SgWin.getInstance();
	}
}
