package com.ibs.common.test.doming;

import c.a.util.core.test.CommTest;
import com.common.core.JsonResultBeanPlus;
import com.common.handicap.MemberOption;
import com.common.handicap.idc.IdcMemberFactory;
import com.ibs.common.utils.HandicapUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
/**
 * 盘口测试类
 *
 * @Author: null
 * @Date: 2020-05-11 16:15
 * @Version: v1.0
 */
public class HandicapTest extends CommTest {
	protected Logger httpLog = LogManager.getLogger("http_log");

	@Test public void test() {
		IdcMemberFactory member = HandicapUtil.Code.IDC.getMemberFactory();
		JsonResultBeanPlus bean = member.valiLogin("1", "2", "3", "4");


		System.out.println(bean);
		String existId = bean.getData().toString();
		MemberOption memberOption = member.memberOption(existId);


		bean = memberOption.checkInfo();
		System.out.println(bean);

	}

	@Test public void test02(){
		log.error("测试错误");
		for(int i = 0; i < 10000; i++) {
			httpLog.error("HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误HTML测试错误{}",i);
		}

	}
}
