package com.ibm.old.v1.common.zjj.test.handicap;
import com.ibm.old.v1.common.doming.configs.SgWinConfig;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.SgWinUtil;
import net.sf.json.JSONObject;
import org.doming.core.tools.LogTool;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @Description: SGWIN盘口测试类
 * @Author: zjj
 * @Date: 2019-08-07 14:18
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class SgWinTest {

	@Test public void test02() {
		String id = "123";

		String memberAccount = "cxz0001";
		String memberPassword = "Aa138138..";
		String handicapUrl = "https://138789.co/";
		String handicapCaptcha = "056118";
		String gameCode = IbmGameEnum.PK10.name();

		List<String> betItem=new ArrayList<>();
		betItem.add("第一名|大#10");
		betItem.add("第一名|大#10");
		betItem.add("第三名|虎#12");

//		String[] betItem = {"第一名|大#10", "第三名|虎#12"};

		String handicapType = "dobleSides";

		SgWinUtil sgWinUtil=SgWinUtil.findInstance();

		JsonResultBeanPlus loginBean =sgWinUtil.login(id, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
		System.out.println("登陆信息 = " + loginBean.toJsonString());
		System.out.println(LogTool.printHashtagM());

		sgWinUtil.firstLogin(id,loginBean);

		JsonResultBeanPlus userInfoBean = sgWinUtil.userInfo(id);
		System.out.println("用户信息 = " + userInfoBean.toJsonString());

		Map<String,String> userInfo = (Map<String, String>) userInfoBean.getData();
		System.out.println("账户名称 = " + userInfo.get("memberAccount"));
		System.out.println("账户余额 = " + userInfo.get("usedQuota"));
		System.out.println(LogTool.printHashtagM());
		//盘口游戏code
		String game=SgWinConfig.GAME_CODE_MAP.get(gameCode);

		JsonResultBeanPlus betInfoBean = sgWinUtil.gameInfo(id,game);
		System.out.println("游戏投注信息 = " + betInfoBean.toJsonString());
		JSONObject betInfo = (JSONObject) betInfoBean.getData();
		System.out.println("当前期数=" + betInfo.getString("drawNumber"));
		System.out.println("开奖时间=" + betInfo.get("drawTime"));
		System.out.println("封盘时间=" + betInfo.get("closeTime"));
		System.out.println(LogTool.printHashtagM());

		String drawNumber = betInfo.getString("drawNumber");

		JsonResultBeanPlus oddsInfo=sgWinUtil.oddsInfo(id,game,SgWinConfig.GAME_ODDS_CODE.get(handicapType));
		System.out.println("游戏赔率信息 = " + oddsInfo.toJsonString());
		System.out.println(LogTool.printHashtagM());
		JSONObject odds = (JSONObject) oddsInfo.getData();

		JsonResultBeanPlus  betBean=sgWinUtil.betting(id,game, drawNumber ,betItem,odds );
		System.out.println("投注结果信息 = " + betBean.toJsonString());

	}
	@Test public void test01() throws InterruptedException {
		String id = "123";

		String memberAccount = "cxz0001";
		String memberPassword = "Aa138138..";
		String handicapUrl = "https://138789.co/";
		String handicapCaptcha = "056118";
		String gameCode = IbmGameEnum.PK10.name();


		//		String[] betItem = {"第一名|大#10", "第三名|虎#12"};

		String handicapType = "dobleSides";

		SgWinUtil sgWinUtil=SgWinUtil.findInstance();

		JsonResultBeanPlus loginBean =sgWinUtil.login(id, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
		System.out.println("登陆信息 = " + loginBean.toJsonString());
		System.out.println(LogTool.printHashtagM());

		sgWinUtil.firstLogin(id,loginBean);
		//盘口游戏code
		String game=SgWinConfig.GAME_CODE_MAP.get(gameCode);

		JsonResultBeanPlus limitBean=sgWinUtil.gameLimit(id,game);

		System.out.println(limitBean.toJsonString());


	}

}
