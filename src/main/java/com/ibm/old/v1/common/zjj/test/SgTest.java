package com.ibm.old.v1.common.zjj.test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
/**
 * @Description:
 * @Author: zjj
 * @Date: 2019-08-07 14:57
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class SgTest {
	public static void main(String[] args) {
		String html="<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n"
				+ "<html>\n" + "<head>\n" + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
				+ "<title>Welcome</title>\n"
				+ "<link href=\"/default/css/login2.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>\n"
				+ "<script type=\"text/javascript\">\n" + "$(function() {\n"
				+ "\t$('.login .info input[title!=\"\"]').focus(function() {\n" + "\t\tvar t = $(this);\n"
				+ "\t\tif (t.hasClass('tip')) {\n" + "\t\t\tt.removeClass('tip').val('').attr('type', t.data('ot'));\n"
				+ "\t\t}\n" + "\t}).blur(function() {\n" + "\t\tvar t = $(this);\n" + "\t\tif (t.val() == '') {\n"
				+ "\t\t\tt.addClass('tip').val(t.attr('title')).data('ot', t.attr('type')).attr('type', 'text');\n"
				+ "\t\t}\n" + "\t}).each(function() {\n" + "\t\tvar t = $(this);\n" + "\t\tif (!t.attr('value')) {\n"
				+ "\t\t\tt.addClass('tip').val(t.attr('title')).data('ot', t.attr('type')).attr('type', 'text');\n"
				+ "\t\t}\n" + "\t});\n" + "\t$('.login .code img').click(function(){\n"
				+ "\t\t$(this).attr('src','code?_='+(new Date()).getTime());\n" + "\t});\n"
				+ "\t$('.fr.user_f').click(function(){\n" + "\t\t$('.info.facode').slideToggle();\n" + "\t});\n"
				+ "})\n" + "</script>\n" + "\n" + "</head>\n" + "<body>\n"
				+ "<div class=\"header\"><a href=\"http://www.sgwin138.com/\" target=\"_blank\"></a></div>\n"
				+ "<div class=\"main\">\n" + "<div class=\"panel\">\n" + "<div class=\"login\">\n" + "\n"
				+ "<form action=\"login\" method=\"post\">\n" + "\t<input type=\"hidden\" name=\"type\" value=\"1\">\n"
				+ "\t<div class=\"form_t\">\n" + "        <span class=\"fl user_t\">用户登录</span>\n" + "        \n"
				+ "    </div>\n"
				+ "\t<div class=\"info username\"><label>账号</label><input type=\"text\" name=\"account\" title=\"请输入您的账号\"></div>\n"
				+ "\t<div class=\"info password\"><label>密码</label><input type=\"password\" name=\"password\" title=\"您的密码\"></div>\n"
				+ "\t<div class=\"info code\"><label>验证码</label><input type=\"text\" name=\"code\" autocomplete=\"off\" title=\"验证码\"><img src=\"code?_=1565167713288\" alt=\"none\" title=\"看不清？点击更换一张验证图片\" /></div>\n"
				+ "\t\n" + "\t<div class=\"control\"><input type=\"submit\" value=\"登录\" ></div>\n" + "</form>\n"
				+ "</div>\n" + "\n" + "<div class=\"left_adv\">\n" + "  <div class=\"appqr-wrapper\">\n"
				+ "    <div class=\"appqrcode\">\n" + "      <img id=\"qr-code\">\n"
				+ "      <div class=\"text\">手机扫码安装<br>苹果安卓APP</div>\n" + "    </div>\n"
				+ "    <div id=\"barcodeURL\" class=\"appdlurl\"></div>\n" + "  </div>\n"
				+ "  <div class=\"appqr\" id=\"qr\"></div>\n" + "</div>\n"
				+ "<link href=\"/default/css/footer.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<script type=\"text/javascript\" src=\"/default/js/qrcode.min.js\"></script>\n"
				+ "<script type=\"text/javascript\">\n" + "  var qr = \"https://sgwinapp.com\";\n"
				+ "  if(qr !== \"\"){\n" + "    new QRCode(document.getElementById(\"qr\"), qr);\n"
				+ "    var n=qr.lastIndexOf('//');\n" + "    $('#barcodeURL').html(qr.substring(n+2));\n" + "  }\n"
				+ "</script>\n" + "</div>\n" + "</div>\n" + "</body>\n" + "</html>";




		Document document= Jsoup.parse(html);

		Element select=document.selectFirst("form");

		System.out.println(select);

		String action=select.attr("action");

		System.out.println("action="+action);

		System.out.println(select.select("img").attr("src"));


	}
}
