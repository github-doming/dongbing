package com.common.handicap.newcc;


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
public class NewCcMemberFactory extends BaseMemberFactory<NewCcMemberCrawler> {

	private static final NewCcMemberFactory INSTANCE = new NewCcMemberFactory();
	private NewCcMemberFactory() {
	}
	public static NewCcMemberFactory getInstance() {
		return INSTANCE;
	}

	@Override protected NewCcMemberCrawler newCrawler() {
		return new NewCcMemberCrawler();
	}

	@Override public Handicap handicap() {
		return NewCc.getInstance();
	}
}