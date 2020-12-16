package com.ibm.old.v1.common.doming.test.handicap;
import com.ibm.old.v1.common.doming.configs.IdcConfig;
import com.ibm.old.v1.common.doming.core.BaseTest;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.IdcTool;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.IdcUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.FileTool;
import org.doming.core.tools.LogTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
/**
 * @Description: IDC盘口
 * @Author: Dongming
 * @Date: 2019-02-28 10:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IdcTest extends BaseTest {

	@Test public void testHtml() throws InterruptedException, IOException {
		String id = "123";

		String memberAccount = "gbr4848";
		String memberPassword = "asas1313v";
		String handicapUrl = "http://am2.ukk556.com";
		String handicapCaptcha = "21223";

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		//登陆界面
		//登陆界面需要解析信息
		Map<String, String> loginDateMap;
		for (int i = 0; i < 100; i++) {
			String loginHtml = IdcTool.getLoginHtml(httpConfig, handicapUrl, handicapCaptcha);
			if (StringTool.isEmpty(loginHtml)) {
				continue;
			}
			httpConfig.httpContext(HttpClientContext.create());
			String loginSrc = IdcTool.getLoginSrc(loginHtml);
			loginDateMap = IdcTool.getLoginData(httpConfig, loginSrc);
			System.out.println(loginDateMap);
			System.out.println(LogTool.printHashtagM());

			httpConfig.headers(null);
			httpConfig.httpContext(null);
		}

	}

	@Test public void test04() throws IOException, KeyManagementException, NoSuchAlgorithmException {
		String url = "https://62x-dgw.51ttyin.com/cp11-1-mb/?pagegroup=CP11_1_MB&idcode=21223&rnd=87441&key=5C8F275F3629AD50AD92F307C0DC0E&ts=637010481530000000";
		System.out.println(url.substring(url.indexOf("://") + 3, url.indexOf(".")));
		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
		System.out.println(html);

	}

	@Test public void test02() {
		String id = "123";

		String memberAccount = "gbr4848";
		String memberPassword = "asas1313v";
		String handicapUrl = "http://am2.ukk556.com";
		String handicapCaptcha = "21223";

		IdcUtil idcUtil = IdcUtil.findInstance();
		for (int i = 0; i < 10; i++) {
			JsonResultBeanPlus loginBean = idcUtil
					.login(id, memberAccount, memberPassword, handicapUrl, handicapCaptcha);
			if (!loginBean.isSuccess()) {
				System.out.println(LogTool.printHashtagM());
				System.out.println("登陆信息 = " + loginBean.toJsonString());
			} else {
				idcUtil.removeExistHmInfo(id);
				System.out.println("***************************");
				System.out.println("登陆信息 = " + loginBean.toJsonString());
			}
		}

	}

	@Test public void test() {

		String id = "123";

		String memberAccount = "gbr4848";
		String memberPassword = "asas1313v";
		String handicapUrl = "am2.ukk556.com";
		String handicapCaptcha = "21223";
		String codeType = "PK10_NUMBER";
		String[] betItem = {"第一名|大#2"};

		IdcUtil idcUtil = IdcUtil.findInstance();

		JsonResultBeanPlus loginBean = idcUtil.login(id, memberAccount, memberPassword, handicapUrl, handicapCaptcha);

		System.out.println("登陆信息 = " + loginBean.toJsonString());
		System.out.println(LogTool.printHashtagM());

		JsonResultBeanPlus userInfoBean = idcUtil.userInfo(id);

		System.out.println("用户信息 = " + userInfoBean.toJsonString());
		JSONObject userInfo =JSONObject.fromObject( userInfoBean.getData());
		System.out.println("会员帐号 = " + userInfo.getString("memberAccount"));
		System.out.println("账户名称 = " + userInfo.getString("memberAccount"));
		System.out.println("信用额度 = " + userInfo.getString("creditQuota"));
		System.out.println("可用金额 = " + userInfo.getString("usedQuota"));
		System.out.println(LogTool.printHashtagM());

		JsonResultBeanPlus betInfoBean = idcUtil
				.betInfo(id, IdcConfig.GAME_BET_CODE.get("PK10"), idcUtil.getBetType(codeType));

		System.out.println("游戏投注信息 = " + betInfoBean.toJsonString());
		if (!betInfoBean.isSuccess()) {
			System.out.println("游戏投注信息 = " + betInfoBean.toJsonString());
			return;
		}
		Map<String, JSONObject> betInfo = (Map<String, JSONObject>) betInfoBean.getData();
		JSONObject drawsInfo = betInfo.get("drawsInfo");
		JSONObject oddInfo = betInfo.get("oddInfo");
		System.out.println("当前期数=" + drawsInfo.getString("cd"));
		System.out.println("开奖时间=" + drawsInfo.getString("cwTs"));
		System.out.println("封盘时间=" + drawsInfo.getString("ceTs"));
		System.out.println(LogTool.printHashtagM());

		JsonResultBeanPlus betBean = idcUtil
				.betting(id, IdcConfig.GAME_BET_CODE.get("PK10"), idcUtil.getBallCodeType(codeType), drawsInfo, oddInfo,
						JSONArray.fromObject(betItem), drawsInfo.getString("cd"));

		System.out.println("投注结果 = " + betBean.toJsonString());
		System.out.println(LogTool.printHashtagM());

	}

	@Test public void test03() throws IOException {
		String str1 = "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a\n"
				+ "HBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy\n"
				+ "MjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAeAEYDASIA\n"
				+ "AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\n"
				+ "AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\n"
				+ "ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\n"
				+ "p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\n"
				+ "AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\n"
				+ "BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\n"
				+ "U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\n"
				+ "uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwBun22r\n"
				+ "fE/xNcQS301ppsQeWNMl1iUsQAoJGcnj2xgcAAQrd614A8U3fh6G8N5Ew/dIU3LvKho3CNnDBtp/\n"
				+ "CnaJba5rmv6lrPgi2k0xN21kMi7OckjJ46gHbg4z1HFanw2jt5vG16uupOfEsbl43uTuHHDjGR82\n"
				+ "3oeRjkdOfOi3Jq27e59lVkqcakZWcIx+Cyunpq/Tq7siXxT8UWUsLebAGf8AjzTp7cc1f8D/ABD1\n"
				+ "6+1ef+23efT44iGeG2HyPkYyVHoGrd+Lus6jo+iWf2C5aFbmR4ZgAPmUoeOf6VF4Pjm0LwtYaRpU\n"
				+ "CyaxfRi7nkb7sKv0Zj7LjA/x52tKNSyk9DzKlWEsC6s6UE5OysrPzZvaj4vs9I8NpqjXovUUuAUg\n"
				+ "YGbGdqE9EflSSRyFYhQPu82ul+N/ElrHq91r0Oi+YP8ARraKPBVWIwGbryQvGT247Vo6hqsngm2h\n"
				+ "tH0t71JP39zdyMQjSsTuwduM8d+cYrG8R+P4tfVNL8PSLEZhtn1CZzGkSkEEY6twx6gjPIBIBHVU\n"
				+ "hKylPr0XX7jLB4euoRlSgve+07Oy9On5sl8H+MLy48Ia3ca7cRT/ANmODFcyRbg7cleONxDBSOh5\n"
				+ "HTrTvBfxE1LxL4ii0+5gtokZJXcJuJ4wVx2xg46nOM8cVzNtpcOq3ln4Q0y4NxounlrzUbuGNgZ2\n"
				+ "JJOFySSBhAB3zgdKueH447P453VvboscIMqBFHAHl5wPyrmUpxcVfS9v69NjvnhcO41W4+9ZyS7J\n"
				+ "Ky0897dNNj2Mqd4bcQACNvGD059f/wBdFVJNQhtriYXVzBbxpsCiVlUknJ3Z3dDggAgHKN1GKK7n\n"
				+ "KKdmz5ttLdnknw6+IGj+F/Dlxp+pJcCYTtKvlR7twIAx2weO9O+G1nfeIfiDeeKWh8m1V5ZCexd8\n"
				+ "gKPXAPP0969SuvCPh6+nE91o9pNMOskkYLN7serH3Oa1Le2gs7dLe1hjggjG1I4kCqo9ABwK5IUJ\n"
				+ "e7zPRHqVszotVJUYNSqaNt7enqeXfGx5BZ6OqDdl5srsDfwqP6nntUSeJdJ02xOo6fqF3p9/HCkd\n"
				+ "xZT2XLlVA5yAuc5ORjr09fVZFnMiGKSNUH3g0ZYnkdDkY43DoeSD2wYLjTraaZp/s1sbhtqtJJCH\n"
				+ "JQHkdu2cehwcHGK1VFc7k+pywxVJ0Y0qsX7t9na9++jPn6+8YR+JtXMviGS8/s5ZFdbK1bhjwpOW\n"
				+ "bC/KM8cE5+7kmuotfHkOoOmjeFfDFtCqIzq9wpfCqMsdiKWJwOxLHtzivXVs7ZBtS3hUegQCmxwN\n"
				+ "DJuVY23OdxwF2LjgLgc8gdT3Jz0FTGlNO7l/XY7amZUZrljSsktFfRPvZJX+Z5VY/CbW7aNvL8UC\n"
				+ "0kb/AFi2qvgnryQwJPPcVyKeHdRT4kPoaaxc/bd+030YbecoGJPzZxg889B+FfQ5MnmqAqmMqdzF\n"
				+ "uQeMADHI6854wODnipHpdkt6b57O0N7uJ+0LAA+DkD5uTnbwTnn26UpYSLSt+pVHOa65nU1unbRb\n"
				+ "99tRdLVE02BY5ZZl25MkpYsWJO7IY7gc5+U/d6cYxRWT4y1TTdJ0iK41QagYGuAi/YZ3ifdtY8lX\n"
				+ "Q4wDxn04orpcrbnkN6n/2Q==";
		String str2 = "<string xmlns=http://schemas.microsoft.com/2003/10/Serialization/>8724</string>";
		BASE64Encoder encoder = new BASE64Encoder();
		BASE64Decoder decoder = new BASE64Decoder();
		FileTool.writerFileByBytes("1.jpg", decoder.decodeBuffer(str1));

		str2 = StringUtils.substringBetween(str2, "/>", "</string>");
		System.out.println(str2);
	}




}
