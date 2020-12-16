package com.common.handicap.newws;


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
public class NewWsMemberFactory extends BaseMemberFactory<NewWsMemberCrawler> {

	private static final NewWsMemberFactory INSTANCE = new NewWsMemberFactory();

	private NewWsMemberFactory() {
	}

	public static NewWsMemberFactory getInstance() {
		return INSTANCE;
	}

	@Override
	protected NewWsMemberCrawler newCrawler() {
		return new NewWsMemberCrawler();
	}

	@Override
	public Handicap handicap() {
		return NewWs.getInstance();
	}
}