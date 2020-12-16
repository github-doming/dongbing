package com.common.handicap;
/**
 * 会员工厂
 *
 * @Author: Dongming
 * @Date: 2020-05-11 18:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface MemberFactory{

	/**
	 * 会员操作
	 *
	 * @param existId         存在主键
	 * @return 会员操作对象
	 */
	MemberOption memberOption(String existId);

}
