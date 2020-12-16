package com.common.handicap.un;


import com.common.handicap.BaseMemberFactory;
import com.common.handicap.Handicap;

/**
 * IDC盘口
 *
 * @Author: Dongming
 * @Date: 2020-05-11 15:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class UnMemberFactory extends BaseMemberFactory<UnMemberCrawler> {

	private static final UnMemberFactory INSTANCE = new UnMemberFactory();

	private UnMemberFactory() {
	}

	public static UnMemberFactory getInstance() {
		return INSTANCE;
	}

	@Override
	protected UnMemberCrawler newCrawler() {
		return new UnMemberCrawler();
	}

	@Override
	public Handicap handicap() {
		return Un.getInstance();
	}
}