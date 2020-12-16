package com.ibm.common.test.doming;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.FileTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-26 09:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HttpFileTest {
	@Test public void test() throws IOException {
		WebClient webClient = getWebClient();

		String path = "F:/Doming/1/";
		String article = "http://www.022003.com/69_69665";
		String menuHtml = HttpClientTool.doGet(article + "/69_69665");
		Document root = Jsoup.parse(menuHtml);
		Elements menus = root.select("div#list dl dd");

		Document document;
		String html, chapter = null, chapterName = null;
		for (Element menu : menus) {
			try {

				chapterName = menu.text();
				chapter = StringUtils.substringBetween(menu.html(), "<a href=\"", "\">");
				HtmlPage page = webClient.getPage(article + chapter);
				//等待js脚本执行完成
				webClient.waitForBackgroundJavaScript(100);

				String chapterHtml = page.asXml();
				document = Jsoup.parse(chapterHtml);
				Element element = document.getElementById("content");
				html = element.html().replace("<br>", "").replace("&nbsp;", "");
				String[] lines = html.split("\n");
				String[] linesNew = new String[lines.length - 3];
				System.arraycopy(lines, 1, linesNew, 0, lines.length - 3);

				String fileName = path + chapterName + ".txt";
				if (FileTool.isExist(fileName)) {
					fileName = path + chapterName + "_1.txt";
				}
				FileTool.writerFileByLines(fileName, linesNew);
			} catch (IOException | FailingHttpStatusCodeException e) {
				System.out.println("没有抓取到的章节：" + chapterName + ",地址：{" + article + chapter + "}");
				System.out.println("错误信息：" + e.getMessage());
				System.out.println("############################");
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
			}

		}

		webClient.close();
	}

	@Test public void test01() throws IOException {

		String path = "F:/Doming/1/";
		String chapterName = "第三百六十章 今夕何夕";

		WebClient webClient = getWebClient();
		String url = "http://www.022003.com/69_69665/23500766.html";
		HtmlPage page = webClient.getPage(url);
		//等待js脚本执行完成
		webClient.waitForBackgroundJavaScript(100);

		String chapterHtml = page.asXml();
		Document document = Jsoup.parse(chapterHtml);
		Element element = document.getElementById("content");
		String html = element.html().replace("<br>", "").replace("&nbsp;", "");
		String[] lines = html.split("\n");
		String[] linesNew = new String[lines.length - 3];
		System.arraycopy(lines, 1, linesNew, 0, lines.length - 3);

		String fileName = path + chapterName + ".txt";
		if (FileTool.isExist(fileName)) {
			fileName = path + chapterName + "_1.txt";
		}
		FileTool.writerFileByLines(fileName, linesNew);

		System.out.println("OK");

	}

	@Test public void test02() {

		Map<Integer, File> map = new HashMap<>(1626);

		Map<String, Integer> index = new HashMap<>();
		String title = "第%s章";

		List<String> list = new ArrayList<>();
		for (int i = 0; i <= 1625; i++) {
			String cn;
			if (i == 0) {
				cn = "零";
			} else if (i >= 10 && i <= 19) {
				cn = NumberTool.getCn(i).replace("一十", "十");
			} else if (i >= 1010 && i <= 1099) {
				cn = NumberTool.getCn(i).replace("一千零", "一千");
			} else {
				cn = NumberTool.getCn(i);
			}
			index.put(String.format(title, cn), i);
			list.add(String.format(title, cn));
		}

		String path = "F:/Doming/1/";
		//获取其file对象
		File dir = new File(path);
		//遍历path下的文件和目录，放在File数组中
		File[] files = dir.listFiles();
		for (File file : files) {
			title = file.getName().split(" ")[0];

			map.put(index.get(title), file);
			if (!list.remove(title)) {
				System.out.println(file.getName());
			}
		}
		System.out.println(list);

		String txt = "F:/Doming/123.txt";

		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(txt), StandardCharsets.UTF_8))) {
			for (int i = 0; i <= 1625; i++) {
				File file = map.get(i);
				writer.write(file.getName().replace(".txt", ""));
				writer.write("\r\n");
				try (BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
					String tempString;
					// 一次读入一行，直到读入null为文件结束
					while ((tempString = reader.readLine()) != null) {
						if (tempString.contains("http://www.022003.com")) {
							continue;
						}
						if (StringTool.notEmpty(tempString)) {
							writer.write(tempString);
							writer.write("\r\n");
						}
					}
				}
				writer.write("\r\n");
				writer.write("\r\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test public void test11() throws IOException {
		WebClient webClient = getWebClient();

		String path = "F:/Doming/1/";
		String article = "http://www.xbiquge.la";
		String menuHtml = HttpClientTool.doGet("http://www.xbiquge.la/100000/15409/");
		Document root = Jsoup.parse(menuHtml);
		Elements menus = root.select("div#list dl dd");

		Document document;
		String html, chapter = null, chapterName = null;
		for (Element menu : menus) {
			for(int j = 0; j < 5; j++) {
				try {
					chapterName = menu.text();
					chapter = StringUtils.substringBetween(menu.html(), "<a href=\"", "\">");
					String index = StringUtils.substringBetween(chapter, "15409/", ".html");
					HtmlPage page = webClient.getPage(article + chapter);
					//等待js脚本执行完成
					webClient.waitForBackgroundJavaScript(1000);

					String chapterHtml = page.asXml();
					document = Jsoup.parse(chapterHtml);
					Element element = document.getElementById("content");
					html = element.html().replace("<br>", "").replace("&nbsp;", "");
					String[] lines = html.split("\n");
					String[] linesNew = new String[lines.length - 3];
					System.arraycopy(lines, 1, linesNew, 0, lines.length - 3);

					String fileName = path + index + "___" + chapterName + ".txt";
					if (FileTool.isExist(fileName)) {
						fileName = path + chapterName + "_1.txt";
					}
					FileTool.writerFileByLines(fileName, linesNew);
					break;
				} catch (IOException | FailingHttpStatusCodeException e) {
					String error = "没有抓取到的章节：" + chapterName + ",地址：{" + article + chapter + "}";
					FileTool.addLines2File(path+"错误.txt",new String[]{error,"############################"});
				}
			}


		}
	}


	@Test public void test12() {

		String path = "F:/Doming/1/";
		//获取其file对象
		File dir = new File(path);
		//遍历path下的文件和目录，放在File数组中
		File[] files = dir.listFiles();

		String txt = "F:/Doming/A牧神记.txt";
		for (File file :files){

			System.out.println(file.getName());
		}

		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(txt), StandardCharsets.UTF_8))) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];

				writer.write(StringUtils.substringBetween(file.getName(),"___",".txt"));
				writer.write("\r\n");
				try (BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
					String tempString;
					// 一次读入一行，直到读入null为文件结束
					while ((tempString = reader.readLine()) != null) {
						if (StringTool.contains(tempString,"章节小说","http","网址")){
							continue;
						}
						if (StringTool.notEmpty(tempString)) {
							writer.write(tempString);
							writer.write("\r\n");
						}
					}
				}
				writer.write("\r\n");
				writer.write("\r\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private WebClient getWebClient() {
		WebClient webClient = new WebClient();
		//运行js脚本执行
		webClient.getOptions().setJavaScriptEnabled(true);
		//设置支持AJAX
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		//忽略css
		webClient.getOptions().setCssEnabled(false);
		//ssl安全访问
		webClient.getOptions().setUseInsecureSSL(true);
		//解析js出错时不抛异常
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		//超时时间  ms
		webClient.getOptions().setTimeout(10000);

		LogFactory.getFactory()
				.setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		return webClient;
	}
}
