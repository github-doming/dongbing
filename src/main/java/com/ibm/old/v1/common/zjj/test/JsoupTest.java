package com.ibm.old.v1.common.zjj.test;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @Description: 解析页面
 * @Author: zjj
 * @Date: 2019-08-09 14:53
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class JsoupTest {
	public static void main(String[] args) throws ParseException {
		String html="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
				+ "<title>Welcome</title> <link href=\"/js/jquery/new/jquery-ui.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/agent/red/master.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/agent/red/layout.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/sweetalert.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/loading.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/js/jquery-ui.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/js/jquery.ui.datepicker-zh-CN.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/js/libs.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/js/json2.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/default/js/sweetalert.min.js\"></script>\n" + "\n"
				+ "<link href=\"/default/css/agent/user.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<script type=\"text/javascript\" src=\"/default/js/user.js\"></script>\n" + "</head>\n" + "<body>\n"
				+ "\t<div class=\"main\">\n"
				+ "        <input type=\"hidden\" id=\"oid\" value=\"c2423a06d191e0763f0f846fa8de0dff1a77a08d\"/>\n"
				+ "\t\t<div class=\"top_info\">\n" + "\t\t\t<span class=\"title\">qq369369qq -> 直属会员</span>\n"
				+ "\t\t\t<div class=\"center\">\n" + "\t\t\t\t<div class=\"query_panel\">\n"
				+ "\t\t\t\t\t<span class=\"input_panel\">\n"
				+ "\t\t\t\t\t<label>模式：<select id=\"\" name=\"resetType\" >\n"
				+ "        <option value=\"\" selected=\"selected\">全部</option>\n"
				+ "        <option value=\"0\">信用</option>\n" + "        <option value=\"1\">现金</option>\n"
				+ "</select>\n" + "</label>\n" + "\t\t\t\t\t<label>状态：<select id=\"\" name=\"status\" >\n"
				+ "        <option value=\"\" selected=\"selected\">全部</option>\n"
				+ "        <option value=\"0\">启用</option>\n" + "        <option value=\"1\">冻结</option>\n"
				+ "        <option value=\"2\">停用</option>\n" + "</select>\n"
				+ "</label> <label>搜索：</label> 账号或名称：<input name=\"name\" class=\"input\" />\n" + "\t\t\t\t\t</span>\n"
				+ "\t\t\t\t\t<input type=\"button\" value=\"查找\" class=\"query\" />\n" + "\t\t\t\t\t\n"
				+ "\t\t\t\t</div>\n"
				+ "\t\t\t\t <a class=\"add\" href=\"edit?parent=qq369369qq&type=2\">新增六级代理</a>  <a class=\"add\" href=\"edit?parent=qq369369qq&type=1\">新增会员</a> \n"
				+ "\t\t\t</div>\n" + "\t\t\t<div class=\"right\">\n"
				+ "\t\t\t\t<a class=\"back\" onclick=\"history.back()\">返回</a>\n" + "\t\t\t</div>\n" + "\t\t</div>\n"
				+ "\t\t<div class=\"contents\">\n" + "\t\t\t<ul class=\"left_panel\">\n"
				+ "\t\t\t\t<li class=\"title\">[qq369369qq] 下线管理</li>\n" + "\n"
				+ "\t\t\t\t<li><a href=\"list?parent=qq369369qq&lv=0&type=2\">直属代理</a><span>1</span></li>\n"
				+ "\t\t\t\t<li><a href=\"list?parent=qq369369qq&lv=0&type=1\">直属会员</a><span>4</span></li> \n"
				+ "\t\t\t\t<li><a href=\"list?parent=qq369369qq&type=2\">全部代理</a><span id=\"t2All\">0</span></li> \n"
				+ "\t\t\t\t<li class=\"t2\"><a href=\"list?parent=qq369369qq&type=2&lv=6\">六级代理</a><span>1</span></li>\n"
				+ "\t\t\t\t<li class=\"t1\"><a href=\"list?parent=qq369369qq&type=1&lv=\">全部会员</a><span>4</span></li>\n"
				+ "\t\t\t</ul>\n" + "\t\t\t<div class=\"user_list\">\n" + "\t\t\t\t<table class=\"data_table list\">\n"
				+ "\t\t\t\t\t<thead>\n" + "\t\t\t\t\t\t<tr>\n" + "\t\t\t\t\t\t\t<th class=\"online\">在线</th>\n"
				+ "\t\t\t\t\t\t\t<th class=\"online\">APP</th>\n" + "\t\t\t\t\t\t\t<th class=\"parent\">上级账号</th>\n"
				+ "\t\t\t\t\t\t\t<th class=\"type\">用户类型</th>\n" + "\t\t\t\t\t\t\t<th class=\"username\">账号</th> \n"
				+ "\t\t\t\t\t\t\t<th class=\"account\">快开彩额度</th> \n" + "\t\t\t\t\t\t\t<th class=\"share\">占成</th> \n"
				+ "\t\t\t\t\t\t\t<th class=\"range\">盘口</th> \n" + "\t\t\t\t\t\t\t<th class=\"created\">新增日期</th>\n"
				+ "\t\t\t\t\t\t\t<th class=\"status\">状态</th>\n" + "\t\t\t\t\t\t\t<th class=\"op\">功能</th>\n"
				+ "\t\t\t\t\t\t</tr>\n" + "\t\t\t\t\t</thead>\n" + "\t\t\t\t\t<tbody>\n"
				+ "\t\t\t\t\t\t \t\t\t\t\t\t<tr>\n" + "\t\t\t\t\t\t\t<td class=\"online\"><span class=\"s0\"></span>\n"
				+ "\t\t\t\t\t\t\t</td>\n" + "\t\t\t\t\t\t\t<td class=\"online\"><span class=\"s0\"></span>\n"
				+ "\t\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"parent\"><a href=\"list?parent=qq369369qq&lv=0\">qq369369qq</a>\n"
				+ "\t\t\t\t\t\t\t</td>\n" + "\t\t\t\t\t\t\t<td class=\"type\">信用<br/>会员</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"username\">cxz0002<span> [cxz0002]</span>\n" + "\t\t\t\t\t\t\t</td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"account\">\n"
				+ "\t\t\t\t\t\t\t\t<span class=\"max_limit\"><a userid=\"cxz0002\" type=\"0\">1,000</a></span>\n"
				+ "\t\t\t\t\t\t\t</td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"share\"><a userid=\"cxz0002\" username=\"cxz0002\">明细</a></td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"range\">A盘</td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"created\">2019-08-07</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"status\"><input type=\"button\" class=\"s0\" onclick=\"showChangeStatusPanel(this,'cxz0002',0)\" value=\"启用\" /></td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"op\"><a\n"
				+ "\t\t\t\t\t\t\t\thref=\"edit?username=cxz0002\" class=\"modify\">修改</a> <a href=\"param?username=cxz0002\" class=\"commission\">退水</a>\n"
				+ "\t\t\t\t\t\t\t\t<a href=\"../loginLogs?id=cxz0002\" class=\"login_log info\">日志</a> <a href=\"../logs?id=cxz0002\" class=\"op_log\">记录</a>\n"
				+ "\t\t\t\t\t\t\t\t</td>\n" + "\t\t\t\t\t\t</tr>\n" + "\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t<td class=\"online\"><span class=\"s0\"></span>\n" + "\t\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"online\"><span class=\"s0\"></span>\n" + "\t\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"parent\"><a href=\"list?parent=qq369369qq&lv=0\">qq369369qq</a>\n"
				+ "\t\t\t\t\t\t\t</td>\n" + "\t\t\t\t\t\t\t<td class=\"type\">信用<br/>会员</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"username\">cxz0001<span> [cxz0001]</span>\n" + "\t\t\t\t\t\t\t</td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"account\">\n"
				+ "\t\t\t\t\t\t\t\t<span class=\"max_limit\"><a userid=\"cxz0001\" type=\"0\">1,000</a></span>\n"
				+ "\t\t\t\t\t\t\t</td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"share\"><a userid=\"cxz0001\" username=\"cxz0001\">明细</a></td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"range\">A盘</td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"created\">2019-08-07</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"status\"><input type=\"button\" class=\"s0\" onclick=\"showChangeStatusPanel(this,'cxz0001',0)\" value=\"启用\" /></td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"op\"><a\n"
				+ "\t\t\t\t\t\t\t\thref=\"edit?username=cxz0001\" class=\"modify\">修改</a> <a href=\"param?username=cxz0001\" class=\"commission\">退水</a>\n"
				+ "\t\t\t\t\t\t\t\t<a href=\"../loginLogs?id=cxz0001\" class=\"login_log info\">日志</a> <a href=\"../logs?id=cxz0001\" class=\"op_log\">记录</a>\n"
				+ "\t\t\t\t\t\t\t\t</td>\n" + "\t\t\t\t\t\t</tr>\n" + "\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t<td class=\"online\"><span class=\"s0\"></span>\n" + "\t\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"online\"><span class=\"s0\"></span>\n" + "\t\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"parent\"><a href=\"list?parent=qq369369qq&lv=0\">qq369369qq</a>\n"
				+ "\t\t\t\t\t\t\t</td>\n" + "\t\t\t\t\t\t\t<td class=\"type\">信用<br/>会员</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"username\">cxz0003<span> [cxz0003]</span>\n" + "\t\t\t\t\t\t\t</td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"account\">\n"
				+ "\t\t\t\t\t\t\t\t<span class=\"max_limit\"><a userid=\"cxz0003\" type=\"0\">1,000</a></span>\n"
				+ "\t\t\t\t\t\t\t</td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"share\"><a userid=\"cxz0003\" username=\"cxz0003\">明细</a></td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"range\">A盘</td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"created\">2019-08-07</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"status\"><input type=\"button\" class=\"s1\" onclick=\"showChangeStatusPanel(this,'cxz0003',1)\" value=\"冻结\" /></td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"op\"><a\n"
				+ "\t\t\t\t\t\t\t\thref=\"edit?username=cxz0003\" class=\"modify\">修改</a> <a href=\"param?username=cxz0003\" class=\"commission\">退水</a>\n"
				+ "\t\t\t\t\t\t\t\t<a href=\"../loginLogs?id=cxz0003\" class=\"login_log info\">日志</a> <a href=\"../logs?id=cxz0003\" class=\"op_log\">记录</a>\n"
				+ "\t\t\t\t\t\t\t\t</td>\n" + "\t\t\t\t\t\t</tr>\n" + "\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t<td class=\"online\"><span class=\"s0\"></span>\n" + "\t\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"online\"><span class=\"s0\"></span>\n" + "\t\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"parent\"><a href=\"list?parent=qq369369qq&lv=0\">qq369369qq</a>\n"
				+ "\t\t\t\t\t\t\t</td>\n" + "\t\t\t\t\t\t\t<td class=\"type\">信用<br/>会员</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"username\">cxz0004<span> [cc9001]</span>\n" + "\t\t\t\t\t\t\t</td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"account\">\n"
				+ "\t\t\t\t\t\t\t\t<span class=\"max_limit\"><a userid=\"cxz0004\" type=\"0\">1,000</a></span>\n"
				+ "\t\t\t\t\t\t\t</td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"share\"><a userid=\"cxz0004\" username=\"cxz0004\">明细</a></td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"range\">A盘</td> \n"
				+ "\t\t\t\t\t\t\t<td class=\"created\">2019-08-08</td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"status\"><input type=\"button\" class=\"s2\" onclick=\"showChangeStatusPanel(this,'cxz0004',2)\" value=\"停用\" /></td>\n"
				+ "\t\t\t\t\t\t\t<td class=\"op\"><a\n"
				+ "\t\t\t\t\t\t\t\thref=\"edit?username=cxz0004\" class=\"modify\">修改</a> <a href=\"param?username=cxz0004\" class=\"commission\">退水</a>\n"
				+ "\t\t\t\t\t\t\t\t<a href=\"../loginLogs?id=cxz0004\" class=\"login_log info\">日志</a> <a href=\"../logs?id=cxz0004\" class=\"op_log\">记录</a>\n"
				+ "\t\t\t\t\t\t\t\t</td>\n" + "\t\t\t\t\t\t</tr>\n" + "\t\t\t\t\t</tbody>\n" + "\t\t\t\t</table>\n"
				+ "\t\t\t\t<div class=\"page\"><script>\n" + "  $(document).ready(function(){\n"
				+ "    $(\".page-jump input\").on(\"keydown\", function(e){\n" + "      if(e.keyCode === 13){\n"
				+ "        e.stopPropagation();\n" + "        $(this).blur();\n"
				+ "        if($(this).val() <= 0 || $(this).parent().data(\"total-page\") < $(this).val()){\n"
				+ "          alert(\"页面不存在\");\n" + "          return;\n" + "        } else{\n"
				+ "          var navigatePage = $(this).parent().data(\"page-url\").replace(\"page=1\", \"page=\" + $(this).val());\n"
				+ "          location.href = location.origin + location.pathname + navigatePage;\n" + "        }\n"
				+ "      }\n" + "    })\n" + "  })\n" + "\n" + "</script><div class=\"page_info\">\n"
				+ "<span class=\"record\">共 4 条记录</span>\n" + "<span class=\"page_count\">共 1 页</span>\n"
				+ "<span class=\"page_control\">\n" + "  <a class=\"previous\">首页</a> |\n"
				+ "<span class=\"page-jump\" data-total-page=\"1\" data-page-url=\"?\n" + "parent=qq369369qq&\n" + "\n"
				+ "type=1&\n" + "\n" + "lv=&\n" + "page=1\n"
				+ "\">跳转至<input type=\"number\" style=\"width: 50px\" maxlength=\"3\"/>页 </span>\n"
				+ "<a class=\"previous\">前一页</a>『\n" + "<span class=\"current\">1</span>\n"
				+ "』<a class=\"next\">后一页</a> |\n" + "  <a class=\"next\">末页</a>\n" + "</span>\n" + "</div>\n"
				+ "</div>\n" + "\t\t\t</div>\n" + "\t\t</div>\n" + "\t</div>\n" + "\n"
				+ "    <div id=\"apiDialog\" style=\"display: none;\">\n" + "        <div class=\"user_list\">\n"
				+ "            <table class=\"data_table list\">\n" + "                <thead>\n"
				+ "                <tr>\n" + "                    <th>API平台</th>\n"
				+ "                    <th>余额</th>\n" + "                    <th>操作</th>\n" + "                </tr>\n"
				+ "                </thead>\n" + "                <tbody>\n" + "                <tr>\n"
				+ "                    <td>双赢棋牌</td>\n" + "                    <td id=\"apiBalance\"></td>\n"
				+ "                    <td id=\"transferButton\"></td>\n" + "                </tr>\n"
				+ "                </tbody>\n" + "            </table>\n" + "        </div>\n" + "    </div>\n" + "\n"
				+ "    <div class=\"loading-wrapper\"></div>\n" + "</body>\n" + "</html>";

		Document document = Jsoup.parse(html);

		Element tbody = document.selectFirst("tbody");

		Elements trs = tbody.select("tr");

		JSONArray array=new JSONArray();
		JSONObject object=new JSONObject();
		for(Element tr:trs){
			Elements tds = tr.getElementsByTag("td");
			//s0未登录，s1登录
			String online=tds.get(0).selectFirst("span").attr("class");
//			System.out.println("online:"+online);

			String appOnline=tds.get(1).selectFirst("span").attr("class");
//			System.out.println("appOnline:"+appOnline);
//
//			System.out.println("parent:"+tds.get(2).text());
//
//			System.out.println("username:"+tds.get(4).text());
//			System.out.println("account:"+tds.get(5).text());
//			System.out.println("range:"+tds.get(7).text());
			//s0:启用，s1冻结，s2停用
//			System.out.println("status:"+tds.get(9).selectFirst("input").val());

			object.put("online",online);
			object.put("appOnline",appOnline);
			object.put("parent",tds.get(2).text());
			object.put("userName",tds.get(4).text());
			object.put("limit",tds.get(5).text());
			object.put("range",tds.get(7).text());
			object.put("status",tds.get(9).selectFirst("input").val());
			array.add(object);
			object.clear();
		}
		System.out.println(array);
	}

	@Test public void demo() throws ParseException {
		String html="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
				+ "<script type=\"text/javascript\"> var currentUserName= \"qq369369qq\";</script>\n"
				+ "<title>Welcome</title><link href=\"/js/jquery/new/jquery-ui.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/agent/red/master.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/agent/red/layout.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/sweetalert.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/loading.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/js/jquery-ui.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/js/jquery.ui.datepicker-zh-CN.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/js/libs.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/js/json2.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/default/js/sweetalert.min.js\"></script>\n"
				+ "<link href=\"/default/css/agent/report.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<script type=\"text/javascript\" src=\"/js/stupidtable.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/default/js/report.js?v161004\"></script>\n"
				+ "<script type=\"text/javascript\">$(function(){LIBS.colorMoney('.color','minus')})</script>\n"
				+ "</head>\n" + "<!-- \n" + " -->\n" + "<body>\n" + "    <div class=\"main\">\n"
				+ "        <div class=\"top_info\">\n"
				+ "            <span class=\"title\">五级代理（qq369369qq） - 交收报表 [2019-08-20 — 2019-08-20] </span>\n"
				+ "            <span class=\"right\"><a class=\"back\" href=\"javascript:history.go(-1)\">返回</a></span>\n"
				+ "        </div>\n" + "        <div class=\"contents\">\n"
				+ "<table class=\"data_table list report \">\n" + "<caption>合计</caption>\n"
				+ "<thead><tr class=\"shead\">\n"
				+ "<th rowspan=\"2\">级别</th><th rowspan=\"2\" data-sort=\"string\" class=\"sortable sortdefault\">账号</th><th rowspan=\"2\">名称</th><th rowspan=\"2\">余额</th><th rowspan=\"2\" class=\"count sortable\" data-sort=\"number\">笔数</th><th rowspan=\"2\" data-sort=\"number\" class=\"sortable\">下注金额</th><th rowspan=\"2\">有效金额</th>\n"
				+ "<th colspan=\"3\">会员输赢</th><th colspan=\"7\">五级代理输赢</th>\n"
				+ "<th rowspan=\"2\">上交货量</th><th rowspan=\"2\" data-sort=\"number\" class=\"sortable ca\">上级交收</th>\n"
				+ "</tr>\n" + "<tr class=\"shead\"><th>输赢</th><th>退水</th><th>盈亏结果</th>\n"
				+ "<th data-sort=\"number\" class=\"sortable self ca\">应收下线</th><th>占成</th><th>实占金额</th><th>实占结果</th><th>实占退水</th><th>赚水</th><th class=\"ca\">盈亏结果</th></tr>\n"
				+ "</thead>\n" + "<tbody>\n" + "<tr>\n" + "<td class=\"info\">会员</td>\n"
				+ "<td class=\"info\"><a href=\"        javascript:pick('cxz0001',1)\n" + "\">cxz0001</a></td>\n"
				+ "<td class=\"info\">cxz0001</td>\n" + "<td class=\"info\">980</td>\n"
				+ "<td>2</td><td><a href=\"        javascript:pick('cxz0001',1)\n" + "\">20</a></td><td>20</td>\n"
				+ "<td>0</td><td>0</td><td>0</td>\n" + "<td class=\"result color\">0</td>\n" + "<td>80%</td>\n"
				+ "<td>16</td>\n" + "<td>0</td>\n" + "<td>0</td>\n" + "<td>0</td>\n"
				+ "<td class=\"result color\">0</td>\n" + "<td>4</td><td class=\"result color\">0</td>\n" + "</tr>\n"
				+ "</tbody>\n" + "</table>\n"
				+ "                        <p class=\"more_detail\"><a onclick=\"location.href=LIBS.url({detail:true})\">+ 显示各彩种明细</a></p>\n"
				+ "        </div>\n" + "        <div class=\"data_footer\"></div>\n" + "    </div>\n" + "</body>\n"
				+ "</html>";

		Document document = Jsoup.parse(html);

		Element tbody=document.selectFirst("tbody");

		Elements trs=tbody.select("tr");

		for(Element tr:trs){

			Elements tds=tr.getElementsByTag("td");

			System.out.println(tds.get(1).text());

			System.out.println(tds.get(4).text());

			System.out.println(tds.get(5).text());

		}
	}

	@Test
	public void test03(){
		String html="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
				+ "<title>注单明细</title>\n"
				+ "<link href=\"/js/jquery/new/jquery-ui.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/agent/red/master.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/agent/red/layout.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/sweetalert.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/loading.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/js/jquery-ui.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/js/jquery.ui.datepicker-zh-CN.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/js/libs.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/js/json2.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/default/js/sweetalert.min.js\"></script>\n" + "<script>\n"
				+ "  $(document).ready(function(){\n" + "    $(\".page-jump input\").on(\"keydown\", function(e){\n"
				+ "      if(e.keyCode === 13){\n" + "        e.stopPropagation();\n" + "        $(this).blur();\n"
				+ "        if($(this).val() <= 0 || $(this).parent().data(\"total-page\") < $(this).val()){\n"
				+ "          alert(\"页面不存在\");\n" + "          return;\n" + "        } else{\n"
				+ "          var navigatePage = $(this).parent().data(\"page-url\").replace(\"page=1\", \"page=\" + $(this).val());\n"
				+ "          location.href = location.origin + location.pathname + navigatePage;\n" + "        }\n"
				+ "      }\n" + "    })\n" + "  })\n" + "\n"
				+ "</script><link href=\"/default/css/agent/bets.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<link href=\"/default/css/betExtra.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "<script type=\"text/javascript\" src=\"/default/js/bets.js\"></script>\n"
				+ "<script type=\"text/javascript\" src=\"/default/js/betExtra.js\"></script>\n" + "</head>\n"
				+ "<body>\n" + "<div class=\"main\">\n" + "<div class=\"top_info\">\n"
				+ "<span class=\"title\">注单明细</span>\n" + "<span class=\"right\">\n"
				+ "<a class=\"back\" href=\"javascript:history.go(-1)\">返回</a>\n" + "</span>\n" + "</div>\n"
				+ "<div class=\"contents\">\n" + "<table class=\"data_table list\">\n" + "<thead>\n" + "<tr>\n"
				+ "<th class=\"th-box\">注单号\n" + "</th>\n" + "<th>投注时间</th>\n" + "<th class=\"type th-box\">投注种类\n"
				+ "</th>\n" + "<th>账号</th>\n" + "<th>投注内容</th>\n"
				+ "<th class=\"range th-box\" style=\"width:250px\">\n" + "<div class=\"display_amount\">\n" + "下注金额\n"
				+ "</div>\n" + "<div class=\"input_amount\" style=\"display:none\">\n"
				+ "<input  type\"search\" placeholder=\"最小下注金额\" name=\"minAmount1\" value=\"\" style=\"width:80px\" />&nbsp;\n"
				+ "<input type\"search\" placeholder=\"最大下注金额\" name=\"maxAmount1\" value=\"\" style=\"width:80px\"/>\n"
				+ "</div>\n" + "<button class=\"th_icon1\"></button>\n" + "</th>\n" + "</th>\n" + "<th>退水</th>\n"
				+ "<th>下注结果</th>\n" + "<th>本级占成</th>\n" + "<th class=\"result\">本级结果</th>\n" + "<th>占成明细</th>\n"
				+ "</tr>\n" + "</thead>\n" + "<tbody>\n" + "<tr>\n"
				+ "<td><a href='javascript:queryBetExtraDetail(\"cxz0001\", \"2019082017165024346017270001\", \"BJPK10\")'>2019082017165024346017270001#</a> </span></td>\n"
				+ "<td>2019-08-20 17:16:50 星期二</td>\n"
				+ "<td class=\"period\">北京赛车(PK10)<div class=\"drawNumber1\">737776期</div></td>\n"
				+ "<td>cxz0001<div>A盘</div></td>\n"
				+ "<td><span class=\"drawNumber1\">第四名『8』</span> @ <span class=\"odds\">9.91</span>\n" + "</td>\n"
				+ "<td class=\"money\">8</td>\n" + "<td class=\"commission\">0.76%</td>\n"
				+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">80%</td>\n"
				+ "<td class=\"money color\"></td>\n"
				+ "<td class=\"detail\"><a bid=\"2019082017165024346017270001\">明细</a></td>\n" + "</tr>\n" + "<tr>\n"
				+ "<td><a href='javascript:queryBetExtraDetail(\"cxz0001\", \"2019082017161724346005240001\", \"BJPK10\")'>2019082017161724346005240001#</a> </span></td>\n"
				+ "<td>2019-08-20 17:16:17 星期二</td>\n"
				+ "<td class=\"period\">北京赛车(PK10)<div class=\"drawNumber1\">737776期</div></td>\n"
				+ "<td>cxz0001<div>A盘</div></td>\n"
				+ "<td><span class=\"drawNumber1\">冠亚军和『11』</span> @ <span class=\"odds\">8.32</span>\n" + "</td>\n"
				+ "<td class=\"money\">10</td>\n" + "<td class=\"commission\">0.76%</td>\n"
				+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">80%</td>\n"
				+ "<td class=\"money color\"></td>\n"
				+ "<td class=\"detail\"><a bid=\"2019082017161724346005240001\">明细</a></td>\n" + "</tr>\n" + "<tr>\n"
				+ "<td><a href='javascript:queryBetExtraDetail(\"cxz0001\", \"2019082017160324345999340002\", \"BJPK10\")'>2019082017160324345999340002#</a> </span></td>\n"
				+ "<td>2019-08-20 17:16:03 星期二</td>\n"
				+ "<td class=\"period\">北京赛车(PK10)<div class=\"drawNumber1\">737776期</div></td>\n"
				+ "<td>cxz0001<div>A盘</div></td>\n"
				+ "<td><span class=\"drawNumber1\">亚军『5』</span> @ <span class=\"odds\">9.91</span>\n" + "</td>\n"
				+ "<td class=\"money\">10</td>\n" + "<td class=\"commission\">0.76%</td>\n"
				+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">80%</td>\n"
				+ "<td class=\"money color\"></td>\n"
				+ "<td class=\"detail\"><a bid=\"2019082017160324345999340002\">明细</a></td>\n" + "</tr>\n" + "<tr>\n"
				+ "<td><a href='javascript:queryBetExtraDetail(\"cxz0001\", \"2019082017160324345999340001\", \"BJPK10\")'>2019082017160324345999340001#</a> </span></td>\n"
				+ "<td>2019-08-20 17:16:03 星期二</td>\n"
				+ "<td class=\"period\">北京赛车(PK10)<div class=\"drawNumber1\">737776期</div></td>\n"
				+ "<td>cxz0001<div>A盘</div></td>\n"
				+ "<td><span class=\"drawNumber1\">冠军『1』</span> @ <span class=\"odds\">9.91</span>\n" + "</td>\n"
				+ "<td class=\"money\">10</td>\n" + "<td class=\"commission\">0.76%</td>\n"
				+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">80%</td>\n"
				+ "<td class=\"money color\"></td>\n"
				+ "<td class=\"detail\"><a bid=\"2019082017160324345999340001\">明细</a></td>\n" + "</tr>\n"
				+ "</tbody>\n" + "<tfoot>\n"
				+ "<tr><th colspan=\"5\">总计：4笔</th><td class=\"money\">38</td><td></td><td class=\"money color\"></td><td></td>\n"
				+ "<td class=\"money color\"></td><td></td></tr>\n" + "</tfoot>\n" + "</table>\n" + "</div>\n"
				+ "<div id=\"betExtraDialog\"></div>\n" + "<div class=\"page\"><div class=\"page_info\">\n"
				+ "<span class=\"record\">共 4 笔注单</span>\n" + "<span class=\"page_count\">共 1 页</span>\n"
				+ "<span class=\"page_control\">\n" + "  <a class=\"previous\">首页</a> |\n"
				+ "<span class=\"page-jump\" data-total-page=\"1\" data-page-url=\"?\n" + "username=cxz0001&\n" + "\n"
				+ "lottery=BJPK10&\n" + "\n" + "begin=2019-08-20&\n" + "\n" + "end=2019-08-20&\n" + "\n"
				+ "settle=false&\n" + "page=1\n"
				+ "\">跳转至<input type=\"number\" style=\"width: 50px\" maxlength=\"3\"/>页 </span>\n"
				+ "<a class=\"previous\">前一页</a>『\n" + "<span class=\"current\">1</span>\n"
				+ "』<a class=\"next\">后一页</a> |\n" + "  <a class=\"next\">末页</a>\n" + "</span>\n" + "</div>\n"
				+ "</div></div>\n" + "</body>\n" + "</html>";

		Document document=Jsoup.parse(html);

		Element tbody=document.selectFirst("tbody");

		Elements trs=tbody.select("tr");

		for(Element tr:trs){
			Elements tds=tr.getElementsByTag("td");

			System.out.println("注单号："+tds.get(0).text());

			System.out.println("投注类型："+tds.get(2).text().split(" ")[1]);

			System.out.println("投注内容:"+tds.get(4).text().split(" ")[0]);

			System.out.println("投注金额:"+tds.get(5).text());
		}
	}
	@Test
	public void test04(){
		String str="冠亚军和『11』 @ 8.32";

		String[] sts=str.split(" ");
//		System.out.println(sts[0]);
//		System.out.println(sts[2]);
//		System.out.println(Arrays.asList(str));
		List<String> list=new ArrayList<>();
		list.add("qwe");
		list.add("asd");
		list.add("zxc");
//		list.forEach(System.out::println);

//		Arrays.asList( "a", "b", "d" ).forEach(System.out::println);

		Arrays.asList( "a", "b", "d" ).forEach( e -> {
			System.out.print( e );
			System.out.print( e );
		} );

//		String separator = ",";
//		Arrays.asList( "a", "b", "d" ).forEach(
//				( String e ) -> System.out.print( e + separator ) );
//
		final String separator = ",";
		Arrays.asList( "a", "b", "d" ).forEach(
				( String e ) -> System.out.print( e + separator ) );

		Arrays.asList( "a", "b", "d" ).sort(String::compareTo);

		Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> {
			int result = e1.compareTo( e2 );
			return result;
		} );

	}
}
