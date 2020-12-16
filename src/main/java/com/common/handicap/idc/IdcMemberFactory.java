package com.common.handicap.idc;


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
public class IdcMemberFactory extends BaseMemberFactory<IdcMemberCrawler> {

	private static final IdcMemberFactory INSTANCE = new IdcMemberFactory();
	private IdcMemberFactory() {
	}
	public static IdcMemberFactory getInstance() {
		return INSTANCE;
	}

	@Override protected IdcMemberCrawler newCrawler() {
		return new IdcMemberCrawler();
	}

	@Override public Handicap handicap() {
		return Idc.getInstance();
	}
}