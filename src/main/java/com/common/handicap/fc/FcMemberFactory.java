package com.common.handicap.fc;

import com.common.handicap.BaseMemberFactory;
import com.common.handicap.Handicap;

/**
 * @Description: 盘口
 * @Author: null
 * @Date: 2020-07-13 16:28
 * @Version: v1.0
 */
public class FcMemberFactory extends BaseMemberFactory<FcMemberCrawler> {
	@Override
	protected FcMemberCrawler newCrawler() {
		return new FcMemberCrawler();
	}

	@Override
	public Handicap handicap() {
		return Fc.getInstance();
	}


	private static final FcMemberFactory INSTANCE = new FcMemberFactory();

	private FcMemberFactory() {
	}

	public static FcMemberFactory getInstance() {
		return INSTANCE;
	}

}
