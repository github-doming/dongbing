package com.ibm.old.v1.common.doming.test;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.doming.core.tools.*;
import org.doming.develop.http.jsoup.HttpJsoupTool;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Description: 用户信息解析
 * @Author: Dongming
 * @Date: 2018-09-21 10:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class StringTest extends CommTest {

	@Test public void test01() throws ParseException {
		System.out.println(DateTool.getTime("15:30:12"));
	}

	@Test public void test02() {
		String scriptStr = "    var version = \"2018-1122-1400\";\n" + "    var systemversion = \"4_6\";" ;
		getScript(scriptStr, "version", "systemversion");

	}

	@Test public void test03() {
		String[] arr = {"manage"};
		String str = "manage" ;
		System.out.println(ContainerTool.loopContain(arr, str));
	}

	@Test public void test04() {
		System.out.println(NumberTool.compareInteger("1", "2"));
	}

	@Test public void test05() {
		String str = "," ;
		System.out.println(str.split(",").length);
	}

	@Test public void test06() throws IOException {
		String url = "http://ibs.130888.top:9081/ia/ibs_net/app/t/appUserCheckUser.do" ;

		String param = "{%27token%27:%27EbVuRvNsBQyYKA3R5V6yV9r8pXGspbip3bER%2fJRFlpuOjbZKvFicUiBFiNw9Q92mRGLDWQ3gJ5Qlh1pmbAI%2fnJMnZvexPBJ2lTt90FixZnzgJE374%2fwED477UzaQT6%2fS9GTzu96VejEOawy4tQWX8XfXba4%2fw8TD7b7QVXpr1IvZikggf9j7xVFPfiwe%2faXxQr3%2bRY6mdup5KKxXfCzB3bV%2b1%2f1x%2bVXXtiIJ52t4PBWfDy%2b%2fMIG%2bKL%2bVZyIz82Xv0RVSWI5muHfhRRJ49Ud3T45ElZzDwcDtbQYQQ9LKvUO8VPM5v%2fxAq0XFRMK98WV6pUiAAay%2fWO33ZRJMMwF8tQ%3d%3d%27,%27data%27:%27aKkrtZ9j1GVo8Xbc7mdCt9V1ivpfySXgIxNYUS6Fx%2by98WmGHxiZ0Aed%2b%2fZUVBGfMxm7f7VvvI3Yo8Z4h9m4OoGosfEG4wykb7QsugYmnvQl6zAVFO9M6mhJCMLtT%2bLCU%2fWZL2ssTmutNpwMFqzgMKcSpmxn5NW0AD6MkX99ix0rK4jrgd40EAIaDalxh%2fb4YnTU7At%2f%2b6ABAu%2bLXOX3sG0G5YayXvqKhkgsi2Tp4k%2fwpK26kDCw8IVpwBBEJYmdCI4YCsqn7yPKrqT4HoRHvz9yEH1njygsfgufSMWEdarq6NAbwH6YF0tLSGcaX0pggFOCPLogYhXjhfL%2bevP9WA%3d%3d%27}" ;

		param = "json=" + URLEncoder.encode(param, "UTF-8");
		System.out.println(url);
		System.out.println(HttpJsoupTool.doGetJson(url, param));
	}

	@Test public void test07() throws IOException {

		//1、创建实例
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		//2、创建请求
		String url = "http://13undgw.yzcx928.com/cp6-8-mb/checknum.aspx?ts=1551345756871" ;
		HttpGet httpGet = new HttpGet(url);
		//3、执行
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
		//4、获取实体
		HttpEntity httpEntity = closeableHttpResponse.getEntity();

		String image = generateImage(EntityUtils.toByteArray(httpEntity), "1.png");

		HttpPost httpPost = new HttpPost("http://115.159.55.225/Code/GetVerifyCode");

		List<NameValuePair> list = new ArrayList<>(2);
		list.add(new BasicNameValuePair("type", "IDC"));
		list.add(new BasicNameValuePair("content", image));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
		httpPost.setEntity(entity);

		closeableHttpResponse = closeableHttpClient.execute(httpPost);

		System.out.println(EntityUtils.toString(closeableHttpResponse.getEntity()));
		generateImage(image, "2.png");
		//		System.out.println(image);
	}

	@Test public void test08() throws Exception {
		String encode = "cCv4i8uBsjlVFo1qD9cIhA==" ;
		String commLocalASEKey = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"), "");

		String decode = CommASEUtil.decode(commLocalASEKey, encode.trim()).trim();
		System.out.println("encode=" + encode);
		System.out.println("decode=" + decode);

	}

	@Test public void test09() {
		String str = "Z2019032604400018469" ;
		System.out.println(str.substring(str.length() - 8));

	}

	@Test public void test10() {

		String str = "Hello" ;
		str = str.concat(" ").concat("World").concat("!");
		System.out.println(str);
	}

	private static void getScript(String scriptStr, String... strs) {
		if (strs.length == 0) {
			return;
		}
		String patternStr, selectStr, findStr;
		String[] strMap;
		Pattern pattern;
		scriptStr = scriptStr.replace("\n", "");
		for (String key : strs) {
			selectStr = "var " + key + " = \"" ;
			System.out.println(StringUtils.substringBetween(scriptStr, selectStr, "\";"));
			if (scriptStr.contains(selectStr)) {
				patternStr = selectStr + "(.*?)\";" ;
				pattern = Pattern.compile(patternStr);
				Matcher matcher = pattern.matcher(scriptStr);
				if (matcher.find()) {
					findStr = matcher.group().replace(selectStr, "").replace("\"", "").replace(";", "");
					System.out.println(findStr);

				}
			}
		}

	}

	/**
	 * 字符串转图片
	 *
	 * @param imgStr   --->图片字符串
	 * @param filename --->图片名
	 * @return
	 */
	public static String generateImage(byte[] imgStr, String filename) {

		if (imgStr == null) {
			return null;
		}
		try {
			// 处理数据
			OutputStream out = new FileOutputStream("D:/" + filename);
			out.write(imgStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 解密
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(imgStr);

	}

	/**
	 * 字符串转图片
	 *
	 * @param imgStr   --->图片字符串
	 * @param filename --->图片名
	 * @return
	 */
	public static boolean generateImage(String imgStr, String filename) {

		if (imgStr == null) {
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream("D:/" + filename);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static void main(String[] args) {
		String gameCode = IbmGameEnum.XYFT.name();
		try {
			for (int i = 0; i < 100; i++) {
				new Thread(() -> {
					try {
						for (int j = 0; j < 100; j++) {
							System.out.println(PeriodTool.getDrawTime(gameCode));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}).run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test public void test11() {
		String gameCode = IbmGameEnum.XYFT.name();
		try {
			for (int i = 0; i < 100; i++) {
				new Thread(() -> {
					try {
						for (int j = 0; j < 1000; j++) {
							PeriodTool.getDrawTime(gameCode);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				});

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test public void test12() {
		int i = 0;
		if (i++ > 5) {
			System.out.println("A");
		} else if (i++ > 5) {
			System.out.println("B");

		} else if (++i > 2) {
			System.out.println("C");
		} else if (i++ > 3) {
			System.out.println("D");
		} else {
			System.out.println("E");
		}
		System.out.println(i);

	}

	@Test public void test13() {
		System.out.println(RandomTool.getNumLetter32());
	}

	@Test public void test14() {
		Set<String> set = new HashSet<>(10);

		set.add("d");
		set.add("o");
		set.add("m");
		set.add("i");
		set.add("n");
		set.add("g");
		set.add("o");

		System.out.println(set);

	}
}
