package com.ibm.old.v1.common.doming.utils.http.htmlunit;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageReader;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: ws2的htmlutil工具类
 * @Author: Dongming
 * @Date: 2018-08-02 11:49
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HtmlUnitWs2Tool {

	private static int size = 0;

	private static Map<String, Object> results = new HashMap<>(2);

	/**
	 * 获取执行结果
	 *
	 * @return 执行结果
	 */
	public static Map<String, Object> getResults() {
		return results;
	}

	/**
	 * 根据URL地址获取界面 获取失败会再次获取直到超过5次
	 *
	 * @param webClient 网页客户端
	 * @param url       路径
	 * @return URL地址获取界面
	 * @date 2018年7月25日 下午3:13:02
	 * @author Dongming
	 * @version v1.0
	 */
	private static HtmlPage openPage(WebClient webClient, String url) {
		size++;
		if (size > 5) {
			results = new HashMap<>(2);
			results.put("flag", false);
			results.put("mag", "打开页面失败:" + url);
			size = 0;
			return null;
		}
		try {
			return webClient.getPage(url);
		} catch (Exception e) {
			return openPage(webClient, url);
		}
	}

	/**
	 * 获取选择线路界面
	 *
	 * @param webClient 网页客户端
	 * @param mainUrl   主界面地址
	 * @param code      主界面验证词的
	 * @return 选择线路界面
	 * @date 2018年7月25日 下午3:12:02
	 * @author Dongming
	 * @version v1.0
	 */
	public static HtmlPage openRoutePage(WebClient webClient, String mainUrl, String code) {
		HtmlPage page = openPage(webClient, mainUrl);
		// 页面没有打开
		if (page == null) {
			return null;
		}
		HtmlInput submit;
		try {
			HtmlForm form = page.getForms().get(0);
			HtmlPasswordInput passWord = form.getInputByName("code");
			passWord.setText(code);
			submit = form.getInputByName("submit_bt");
		} catch (ElementNotFoundException e) {
			results = new HashMap<>(2);
			results.put("flag", false);
			results.put("mag", "主界面元素没有找到:" + mainUrl);
			e.printStackTrace();
			return null;
		}
		HtmlPage page2;
		try {
			page2 = submit.click();
		} catch (Exception e) {

			results.put("flag", false);
			results.put("mag", "进入线路选择界面失败:" + mainUrl);
			return null;
		}
		return page2;
	}

	public static List<HtmlListItem> listRoute(HtmlPage page) {
		List<HtmlListItem> allRoutes = null;
		try {
			// 所有线路
			DomNodeList<DomElement> nodeList = page.getElementsByTagName("ul");
			// 会员线路
			DomElement dom = nodeList.get(0);
			allRoutes = dom.getByXPath("//li");
		} catch (Exception e) {
			results.clear();
			results.put("flag", false);
			results.put("mag", "线路元素没有找到");
			return allRoutes;
		}
		return allRoutes;
	}

	/**
	 * 获取线路
	 *
	 * @param page 线路
	 * @return 线路
	 * @date 2018年7月25日 下午5:05:59
	 * @author Dongming
	 * @version v1.0
	 */
	public static List<String> listRouteByJsonp(HtmlPage page) {
		List<String> routes = null;
		try {
			Document doc = Jsoup.parse(page.asXml());
			Elements uiList = doc.select(".datalist>ul");
			Elements liList = uiList.get(0).select("li [href]");
			routes = new ArrayList<>();
			for (Element element : liList) {
				routes.add(element.attr("href"));
			}
		} catch (Exception e) {
			results.clear();
			results.put("flag", false);
			results.put("mag", "线路元素没有找到");
			return routes;
		}
		return routes;
	}

	/**
	 * 打开登录页面
	 *
	 * @param allRoutesLi 所有线路页面
	 * @param index       索引
	 * @return 登录页面
	 * @date 2018年7月31日 上午10:37:23
	 * @author Dongming
	 * @version v1.0
	 */
	public static HtmlPage getRoutePage(List<HtmlListItem> allRoutesLi, int index) {
		if (size > 2) {
			return null;
		}
		System.out.println(allRoutesLi.get(index).asXml());
		System.out.println(size + "-index：" + index);
		if (index == allRoutesLi.size()) {
			size++;
			index = 0;
		}
		try {
			HtmlElement route = allRoutesLi.get(index).getElementsByTagName("a").get(0);
			return route.click();
		} catch (Exception e) {
			return getRoutePage(allRoutesLi, index + 1);
		}
	}

	/**
	 * 打开线路页面
	 *
	 * @param routes    线路列表
	 * @param webClient 网页客户端
	 * @param page      选择线路页面
	 * @param index     索引
	 * @return 打开线路页面
	 * @date 2018年7月25日 下午4:07:42
	 * @author Dongming
	 * @version v1.0
	 */
	public static HtmlPage openLoginPage(List<String> routes, WebClient webClient, HtmlPage page, int index) {
		if (index == routes.size()) {
			size++;
			index = 0;
		}
		if (size > 5) {
			results.clear();
			results.put("flag", false);
			results.put("mag", "打开登陆页面失败：" + size);
			size = 0;
			return null;
		}
		try {
			return page.getAnchorByHref(routes.get(index)).click();
		} catch (Exception e) {
			return openLoginPage(routes, webClient, page, index + 1);
		}
	}

	public static HtmlPage openLoginPage(List<HtmlListItem> allRoutesLi, int index) {
		if (index == allRoutesLi.size()) {
			size++;
			index = 0;
		}
		if (size > 5) {
			results.clear();
			results.put("flag", false);
			results.put("mag", "打开登陆页面失败：" + size);
			size = 0;
			return null;
		}
		try {
			HtmlElement route = (HtmlElement) allRoutesLi.get(index).getElementsByTagName("a").get(0);
			HtmlPage page = route.click();
			return page;
		} catch (Exception e) {
			return getRoutePage(allRoutesLi, index + 1);
		}
	}

	public static HtmlPage login(HtmlPage page, String username, String password, String valicode) {
		HtmlTextInput usernameInput;
		HtmlPasswordInput passwordInput;
		HtmlInput valiCode;
		HtmlInput submitBtn;
		HtmlLabel htmlLabel;
		try {
			htmlLabel = (HtmlLabel) page.getElementById("sec").getParentNode();
			usernameInput = page.getElementByName("__name");
			passwordInput = page.getElementByName("password");
			valiCode = page.getElementByName("VerifyCode");
			submitBtn = page.getElementByName("submit");
		} catch (Exception e) {
			results.clear();
			results.put("flag", false);
			results.put("mag", "登陆信息元素没有找到");
			return null;
		}
		HtmlPage page4;
		try {
			htmlLabel.click();
			usernameInput.setText(username);
			passwordInput.setText(password);
			valiCode.click();
			valiCode.type(valicode);
			page4 = submitBtn.click();
		} catch (Exception e) {
			results = new HashMap<>(2);
			results.put("flag", false);
			results.put("mag", "打开home页面失败");
			return null;
		}
		return page4;
	}

	/**
	 * 验证码图片
	 *
	 * @param page
	 * @return
	 * @date 2018年7月25日 下午6:03:06
	 * @author Dongming
	 * @version v1.0
	 */
	public static HtmlImage valiCodeImg(HtmlPage page) {
		HtmlImage valiCodeImg;
		try {
			valiCodeImg = (HtmlImage) page.getElementById("img").getFirstChild();
		} catch (Exception e) {
			results.clear();
			results.put("flag", false);
			results.put("mag", "验证码元素没有找到");
			return null;
		}
		return valiCodeImg;
	}

	/**
	 * 解码验证码
	 *
	 * @param valiCodeImg 验证码
	 * @return 验证码
	 * @date 2018年7月25日 下午6:04:29
	 * @author Dongming
	 * @version v1.0
	 */
	public static String getValiCode(HtmlImage valiCodeImg) throws IOException {
		ImageReader imageReader = valiCodeImg.getImageReader();
		BufferedImage bufferedImage = imageReader.read(0);
		JFrame frame = new JFrame();
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(bufferedImage));
		frame.getContentPane().add(label);
		frame.setSize(100, 100);
		frame.setTitle("验证码");
		frame.setVisible(true);
		String valicodeStr = JOptionPane.showInputDialog("请输入验证码：");
		frame.setVisible(false);
		return valicodeStr;
	}

}
