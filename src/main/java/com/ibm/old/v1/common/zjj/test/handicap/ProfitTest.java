package com.ibm.old.v1.common.zjj.test.handicap;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.IdcUtil;
import net.sf.json.JSONObject;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.LogTool;
import org.junit.Test;

import java.util.Date;
/**
 * @Description:
 * @Author: zjj
 * @Date: 2019-03-28 10:40
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class ProfitTest {
	@Test public void test() throws InterruptedException {

		String id = "123";

		String memberAccount = "qmdb010";
		String memberPassword = "Aa138138.";
		String handicapUrl = "http://tk7.atf668.com/";
		String handicapCaptcha = "55883";
		String codeType="PK10_NUMBER";
		String[][] betItem = {{"第一名|大", "20"}, {"第三名|虎", "20"}};

		IdcUtil idcUtil = IdcUtil.findInstance();

		JsonResultBeanPlus loginBean = idcUtil.login(id, memberAccount, memberPassword, handicapUrl, handicapCaptcha);
		//判断登录状态
		if(!loginBean.isSuccess()){

		}
		System.out.println("登陆信息 = " + loginBean.toJsonString());
		System.out.println(LogTool.printHashtagM());

		JsonResultBeanPlus userInfoBean = idcUtil.userInfo(id);
		//错误处理
		if(!userInfoBean.isSuccess()){

		}
		//获取日期字符串
		String date = DateTool.getDate(new Date(1553654103000L));
		System.out.println("用户信息 = " + userInfoBean.toJsonString());
		JSONObject userInfo = (JSONObject) userInfoBean.getData();
		System.out.println("会员帐号 = " + userInfo.getString("memberno"));
		System.out.println("账户名称 = " + userInfo.getString("membername"));
		System.out.println("信用额度 = " + userInfo.getString("creditquota"));
		System.out.println("可用金额 = " + userInfo.getString("allowcreditquota"));
		System.out.println(LogTool.printHashtagM());

		JsonResultBeanPlus bean=idcUtil.toCheckIn(id);


//		JsonResultBeanPlus bean = idcUtil
//				.profitLoss(id, IdcConfig.GAME_BET_CODE.get("XYFT"), date,"", 1, 100);
//
		System.out.println(bean.toJsonString());


	}
}
