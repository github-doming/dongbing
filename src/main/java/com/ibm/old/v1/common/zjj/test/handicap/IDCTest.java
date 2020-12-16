package com.ibm.old.v1.common.zjj.test.handicap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * @Description: 返回结果数据解析
 * @Author: zjj
 * @Date: 2019-03-11 10:12
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IDCTest {

	public static void main(String[] args) {
		String data = "$@<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"color:#000;font-family:Verdana,'宋体',Arial,Sans;height:66px;min-width:228px; word-break: break-all; word-wrap: break-word;border-bottom:1px solid #e9a884;\"><tr style=\"height:16px;line-height:16px;\"><td align=center style=\"width:26%;height:16px;line-height:16px;\">注单号：</td><td width=\"74%\" align=left style=\"height:16px;line-height:16px;\">699002621#</td></tr><tr style=\"height:16px;line-height:16px;\"><td style=\"height:16px;line-height:16px;\" colSpan=2 align=center><span style=\"color:blue;\">第三名龙虎  虎</span>@&nbsp;<span style=\"color:red;\">1.9831</span></td></tr><tr style=\"height:16px;line-height:16px;\"><td style=\"height:16px;line-height:16px;width:58px;\" align=center>下注额：</td><td align=left style=\"height:16px;line-height:16px;\">4.00</td></tr><tr style=\"height:16px;line-height:16px;\"><td style=\"height:16px;line-height:16px;width:58px;\" align=center>可赢额：</td><td align=left style=\"height:16px;line-height:16px;\">3.93</td></tr></table><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"color:#000;font-family:Verdana,'宋体',Arial,Sans;height:66px;min-width:228px; word-break: break-all; word-wrap: break-word;border-bottom:1px solid #e9a884;\"><tr style=\"height:16px;line-height:16px;\"><td align=center style=\"width:26%;height:16px;line-height:16px;\">注单号：</td><td width=\"74%\" align=left style=\"height:16px;line-height:16px;\">699002620#</td></tr><tr style=\"height:16px;line-height:16px;\"><td style=\"height:16px;line-height:16px;\" colSpan=2 align=center><span style=\"color:blue;\">冠军大小  大</span>@&nbsp;<span style=\"color:red;\">1.9831</span></td></tr><tr style=\"height:16px;line-height:16px;\"><td style=\"height:16px;line-height:16px;width:58px;\" align=center>下注额：</td><td align=left style=\"height:16px;line-height:16px;\">4.00</td></tr><tr style=\"height:16px;line-height:16px;\"><td style=\"height:16px;line-height:16px;width:58px;\" align=center>可赢额：</td><td align=left style=\"height:16px;line-height:16px;\">3.93</td></tr></table><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"color:#000;font-family:Verdana,'宋体',Arial,Sans;height:66px;min-width:228px; word-break: break-all; word-wrap: break-word;\"><tr style=\"height:16px;line-height:16px;\"><td align=center style=\"width:26%;height:16px;line-height:16px;\">注单号：</td><td width=\"74%\" align=left style=\"height:16px;line-height:16px;\">699002619#</td></tr><tr style=\"height:16px;line-height:16px;\"><td style=\"height:16px;line-height:16px;\" colSpan=2 align=center><span style=\"color:blue;\">冠军  01</span>@&nbsp;<span style=\"color:red;\">9.914</span></td></tr><tr style=\"height:16px;line-height:16px;\"><td style=\"height:16px;line-height:16px;width:58px;\" align=center>下注额：</td><td align=left style=\"height:16px;line-height:16px;\">2.00</td></tr><tr style=\"height:16px;line-height:16px;\"><td style=\"height:16px;line-height:16px;width:58px;\" align=center>可赢额：</td><td align=left style=\"height:16px;line-height:16px;\">17.83</td></tr></table>\n";

		Document document = Jsoup.parse(data);

		Elements tables = document.select("table");

		for (Element table : tables) {
			String tStr = table.text();
			System.out.println(tStr);

			tStr = tStr.replaceAll("\\s*", "");
			System.out.println(tStr);
			String zdh = tStr.substring(tStr.indexOf("注单号：") + 4, tStr.indexOf("#"));
			String zd = tStr.substring(tStr.indexOf("#") + 1, tStr.indexOf("@"));
			String pl = tStr.substring(tStr.indexOf("@") + 1, tStr.indexOf("下注额："));
			String xze = tStr.substring(tStr.indexOf("下注额：") + 4, tStr.indexOf("可赢额："));
			String kye=tStr.substring(tStr.indexOf("可赢额：")+4);
			System.out.println(zdh + "\t" + zd + "\t" + pl + "\t" + xze + "\t" + kye);
			System.out.println("________________________");
		}

	}
}
