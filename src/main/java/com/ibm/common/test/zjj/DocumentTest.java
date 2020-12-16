package com.ibm.common.test.zjj;

import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.member.ComMemberTool;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.StringTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * @Description: 页面解析Test
 * @Author: zjj
 * @Date: 2019-09-10 10:18
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class DocumentTest {

@Test public void test00(){
    String text = "07,03,04,08,19,11,18,06";
    if (text.startsWith("0")){
        text = text.substring(1);
    }
    text = text.replace(",0",",");
    System.out.println(text);

}
    @Test
    public void test01(){
        String html="\n"
				+ "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "\n"
				+ "\t\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
				+ "\t\t\t<title>title</title>\n"
				+ "\t\t\t<script> if (top.location == self.location) top.location.href = \"/\"; </script>\n"
				+ "\t\t\t<link href=\"/Styles/global.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "\t\t\t<link href=\"/Styles/base.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "\t\t\t<link rel=\"stylesheet\" href=\"/Styles/BallCss/ball_all.css\">\n"
				+ "\t\t\t<link id=\"Iframe_skin\" href=\"/Styles/Yellow/open.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "\t\t\t<script>var jsver=20170128;</script>\n"
				+ "\t\t\t<script src=\"/Scripts/sea.js\" type=\"text/javascript\"></script>\n"
				+ "\t\t\t<script src=\"/Scripts/seajs-css.js\" type=\"text/javascript\"></script>\n"
				+ "\t\t<script src=\"/Scripts/juicer-min.js\" type=\"text/javascript\"></script>\n"
				+ "\t\t\t<script src=\"/Scripts/config.js\" type=\"text/javascript\"></script>\n"
				+ "\t\t\t\n"
				+ "\n"
				+ "\t<script>\n"
				+ "\t\tvar masterid = '2';\n"
				+ "\t</script>\n"
				+ "\t<style>\n"
				+ "\t\t.MXbox{\n"
				+ "\t\t\twidth: 920px;\n"
				+ "\t\t}\n"
				+ "\t\t.MXbox .navBox{\n"
				+ "\t\t\twidth: 926px;\n"
				+ "\t\t}\n"
				+ "\t\t.infoList2\n"
				+ "\t\t{\n"
				+ "\t\t\twidth: 920px;\n"
				+ "\t\t\toverflow-y: auto;\n"
				+ "\t\t\toverflow-x: hidden;\n"
				+ "\t\t}\n"
				+ "\t</style>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "\t<div class=\"MXbox\">\n"
				+ "\t\t<div class=\"navBox\">\n"
				+ "\t\t\t<ul>\n"
				+ "\t\t\t\t<li class=\"tabBtn off\" onClick=\"javascript:location.href='Bet.aspx?masterId=1&page=1'\"\n"
				+ "\t\t\t\t\tstyle=\"cursor: pointer; display: none\">香港⑥合彩</li>\n"
				+ "\t\t\t\t<li class=\"tabBtn off\" onClick=\"javascript:location.href='Bet.aspx?masterId=2&page=1'\"\n"
				+ "\t\t\t\t\tstyle=\"cursor: pointer; display: none\">快 彩</li>\n"
				+ "\t\t\t</ul>\n"
				+ "\t\t</div>\n"
				+ "\t\t<div class=\"listBox\">\n"
				+ "\t\t\t<table class=\"infoList2\" id=\"box_six\" style=\"display: none;\">\n"
				+ "\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t<th width=\"190\">\n"
				+ "\t\t\t\t\t\t\t註單號/時間\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t\t<th width=\"150\">\n"
				+ "\t\t\t\t\t\t\t下註類型\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t\t<th width=\"300\">\n"
				+ "\t\t\t\t\t\t\t註單明細\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t\t<th width=\"140\">\n"
				+ "\t\t\t\t\t\t\t下註金額\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t\t<th width=\"140\">\n"
				+ "\t\t\t\t\t\t\t可贏金額\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\n"
				+ "\t\t\t\t\t<tr><td colspan=\"5\">無未結算記錄！</td></tr>\n"
				+ "\t\t\t   \n"
				+ "\t\t\t\t\t<tr class=\"tdbg\">\n"
				+ "\t\t\t\t\t\t<th colspan=\"2\">\n"
				+ "\t\t\t\t\t\t\t當前頁合計\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t\t<th>\n"
				+ "\t\t\t\t\t\t\t<b class=\"orange\">\n"
				+ "\t\t\t\t\t\t\t\t0</b>筆\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t\t<th class=\"orange\">\n"
				+ "\t\t\t\t\t\t\t0.0\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t\t<th>&nbsp;\n"
				+ "\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\t<tr class=\"tdbg\">\n"
				+ "\t\t\t\t\t\t<td colspan=\"5\" align=\"center\">\n"
				+ "\t\t\t\t\t\t\t\n"
				+ "\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t</tr>\n"
				+ "\t\t\t</table>\n"
				+ "\t\t\t<table class=\"infoList2\" id=\"box_kc\" style=\"display: none;\">\n"
				+ "\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t<th width=\"190\">\n"
				+ "\t\t\t\t\t\t註單號/時間\n"
				+ "\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t<th width=\"150\">\n"
				+ "\t\t\t\t\t\t下註類型\n"
				+ "\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t<th width=\"300\">\n"
				+ "\t\t\t\t\t\t註單明細\n"
				+ "\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t<th width=\"140\">\n"
				+ "\t\t\t\t\t\t下註金額\n"
				+ "\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t<th width=\"140\">\n"
				+ "\t\t\t\t\t\t可贏金額\n"
				+ "\t\t\t\t\t</th>\n"
				+ "\t\t\t\t</tr>\n"
				+ "\t\t\t\t\n"
				+ "\t\t\t\t<tr  class=\"\" onMouseOut=\"this.style.backgroundColor=''\" onMouseOver=\"this.style.backgroundColor='#FFFFA2'\">\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t05111101206982759384#<br />\n"
				+ "\t\t\t\t\t\t20-05-11 11:01:19\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t重慶時時彩 #<br />\n"
				+ "\t\t\t\t\t\t<b class=\"green\">\n"
				+ "\t\t\t\t\t\t\t20200511021</b>期\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t第一球單雙 <span class='blue'>【雙】</span>@<b class='orange'>1.9861</b>&nbsp;</td> <td>2&nbsp;</td>\n"
				+ "\t\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t\t2.0\n"
				+ "\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t</tr>\n"
				+ "\t\t\t\t\n"
				+ "\t\t\t\t<tr  class=\"\" onMouseOut=\"this.style.backgroundColor=''\" onMouseOver=\"this.style.backgroundColor='#FFFFA2'\">\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t05111101204170935096#<br />\n"
				+ "\t\t\t\t\t\t20-05-11 11:01:19\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t重慶時時彩 #<br />\n"
				+ "\t\t\t\t\t\t<b class=\"green\">\n"
				+ "\t\t\t\t\t\t\t20200511021</b>期\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t第一球單雙 <span class='blue'>【單】</span>@<b class='orange'>1.9861</b>&nbsp;</td> <td>2&nbsp;</td>\n"
				+ "\t\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t\t2.0\n"
				+ "\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t</tr>\n"
				+ "\t\t\t\t\n"
				+ "\t\t\t\t<tr  class=\"\" onMouseOut=\"this.style.backgroundColor=''\" onMouseOver=\"this.style.backgroundColor='#FFFFA2'\">\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t05111101201991515322#<br />\n"
				+ "\t\t\t\t\t\t20-05-11 11:01:19\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t重慶時時彩 #<br />\n"
				+ "\t\t\t\t\t\t<b class=\"green\">\n"
				+ "\t\t\t\t\t\t\t20200511021</b>期\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t第一球大小<span class='blue'>【小】</span>@<b class='orange'>1.9861</b>&nbsp;</td> <td>2&nbsp;</td>\n"
				+ "\t\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t\t2.0\n"
				+ "\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t</tr>\n"
				+ "\t\t\t\t\n"
				+ "\t\t\t\t<tr  class=\"\" onMouseOut=\"this.style.backgroundColor=''\" onMouseOver=\"this.style.backgroundColor='#FFFFA2'\">\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t05111101199963778686#<br />\n"
				+ "\t\t\t\t\t\t20-05-11 11:01:19\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t重慶時時彩 #<br />\n"
				+ "\t\t\t\t\t\t<b class=\"green\">\n"
				+ "\t\t\t\t\t\t\t20200511021</b>期\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t第一球大小<span class='blue'>【大】</span>@<b class='orange'>1.9801</b>&nbsp;</td> <td>2&nbsp;</td>\n"
				+ "\t\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t\t2.0\n"
				+ "\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t</tr>\n"
				+ "\t\t\t\t\n"
				+ "\t\t\t\t<tr  class=\"\" onMouseOut=\"this.style.backgroundColor=''\" onMouseOver=\"this.style.backgroundColor='#FFFFA2'\">\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t05111101062847906879#<br />\n"
				+ "\t\t\t\t\t\t20-05-11 11:01:05\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t廣東快樂十分 #<br />\n"
				+ "\t\t\t\t\t\t<b class=\"green\">\n"
				+ "\t\t\t\t\t\t\t2020051107</b>期\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t第一球尾數大小<span class='blue'>【小】</span>@<b class='orange'>1.9874</b>&nbsp;</td> <td>2&nbsp;</td>\n"
				+ "\t\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t\t2.0\n"
				+ "\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t</tr>\n"
				+ "\t\t\t\t\n"
				+ "\t\t\t\t<tr  class=\"\" onMouseOut=\"this.style.backgroundColor=''\" onMouseOver=\"this.style.backgroundColor='#FFFFA2'\">\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t05111101062065369393#<br />\n"
				+ "\t\t\t\t\t\t20-05-11 11:01:05\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t廣東快樂十分 #<br />\n"
				+ "\t\t\t\t\t\t<b class=\"green\">\n"
				+ "\t\t\t\t\t\t\t2020051107</b>期\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t第一球尾數大小<span class='blue'>【大】</span>@<b class='orange'>1.9874</b>&nbsp;</td> <td>2&nbsp;</td>\n"
				+ "\t\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t\t2.0\n"
				+ "\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t</tr>\n"
				+ "\t\t\t\t\n"
				+ "\t\t\t\t<tr  class=\"\" onMouseOut=\"this.style.backgroundColor=''\" onMouseOver=\"this.style.backgroundColor='#FFFFA2'\">\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t05111101061128021059#<br />\n"
				+ "\t\t\t\t\t\t20-05-11 11:01:05\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t廣東快樂十分 #<br />\n"
				+ "\t\t\t\t\t\t<b class=\"green\">\n"
				+ "\t\t\t\t\t\t\t2020051107</b>期\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t第一球合數單雙<span class='blue'>【雙】</span>@<b class='orange'>1.9874</b>&nbsp;</td> <td>2&nbsp;</td>\n"
				+ "\t\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t\t2.0\n"
				+ "\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t</tr>\n"
				+ "\t\t\t\t\n"
				+ "\t\t\t\t<tr  class=\"\" onMouseOut=\"this.style.backgroundColor=''\" onMouseOver=\"this.style.backgroundColor='#FFFFA2'\">\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t05111101060504122218#<br />\n"
				+ "\t\t\t\t\t\t20-05-11 11:01:05\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t廣東快樂十分 #<br />\n"
				+ "\t\t\t\t\t\t<b class=\"green\">\n"
				+ "\t\t\t\t\t\t\t2020051107</b>期\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t第一球合數單雙<span class='blue'>【單】</span>@<b class='orange'>1.9874</b>&nbsp;</td> <td>2&nbsp;</td>\n"
				+ "\t\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t\t2.0\n"
				+ "\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t</tr>\n"
				+ "\t\t\t\t\n"
				+ "\t\t\t\t<tr  class=\"\" onMouseOut=\"this.style.backgroundColor=''\" onMouseOver=\"this.style.backgroundColor='#FFFFA2'\">\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t05111101059569687232#<br />\n"
				+ "\t\t\t\t\t\t20-05-11 11:01:05\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t廣東快樂十分 #<br />\n"
				+ "\t\t\t\t\t\t<b class=\"green\">\n"
				+ "\t\t\t\t\t\t\t2020051107</b>期\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t第一球單雙 <span class='blue'>【雙】</span>@<b class='orange'>1.9874</b>&nbsp;</td> <td>2&nbsp;</td>\n"
				+ "\t\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t\t2.0\n"
				+ "\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t</tr>\n"
				+ "\t\t\t\t\n"
				+ "\t\t\t\t<tr  class=\"\" onMouseOut=\"this.style.backgroundColor=''\" onMouseOver=\"this.style.backgroundColor='#FFFFA2'\">\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t05111101058781555097#<br />\n"
				+ "\t\t\t\t\t\t20-05-11 11:01:05\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t廣東快樂十分 #<br />\n"
				+ "\t\t\t\t\t\t<b class=\"green\">\n"
				+ "\t\t\t\t\t\t\t2020051107</b>期\n"
				+ "\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t第一球單雙 <span class='blue'>【單】</span>@<b class='orange'>1.9874</b>&nbsp;</td> <td>2&nbsp;</td>\n"
				+ "\t\t\t\t\t\t<td>\n"
				+ "\t\t\t\t\t\t\t2.0\n"
				+ "\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t</tr>\n"
				+ "\t\t\t\t\n"
				+ "\t\t\t\t\t<tr class=\"tdbg\">\n"
				+ "\t\t\t\t\t\t<th colspan=\"2\">\n"
				+ "\t\t\t\t\t\t\t當前頁合計\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t\t<th>\n"
				+ "\t\t\t\t\t\t\t<b class=\"orange\">\n"
				+ "\t\t\t\t\t\t\t\t10</b>筆\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t\t<th class=\"orange\">\n"
				+ "\t\t\t\t\t\t\t20.0\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t\t<th>&nbsp;\n"
				+ "\n"
				+ "\t\t\t\t\t\t</th>\n"
				+ "\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\t<tr class=\"tdbg\">\n"
				+ "\t\t\t\t\t\t<td colspan=\"5\" align=\"center\">\n"
				+ "\t\t\t\t\t\t\t<span id='pager'>共 12 條記錄  分頁：1/2頁&nbsp;&nbsp;&nbsp;上一頁『<span class='font_c'>1</span>&nbsp;<a class='redLink' href='?page=2&masterId=2' title='第2頁'>2</a>&nbsp;』<a class='redLink' href='?page=2&masterId=2' title='下頁'>下一頁</a>&nbsp;&nbsp;<a class='redLink' href='?page=2&masterId=2' title='尾頁'>尾頁</a><input type=\"hidden\" value=\"1\" id=\"page\"></span><input type=\"hidden\" value=\"&masterId=2\" id=\"hdnPString\"></span>&nbsp;&nbsp;<input type='text' value='1' name='txtPager' id='txtPager' class='GOtext' onkeydown=\"javascript: if (event.keyCode ==13){ var txtPagerValue=document.getElementById('txtPager').value; var regs = /^\\d+$/; if(!regs.test(txtPagerValue)){alert('輸入錯誤');document.getElementById('txtPager').focus();return false;};var hdnPStringValue=document.getElementById('hdnPString').value; location.href='?page='+txtPagerValue+hdnPStringValue;};\" style=\"display:inline-block;vertical-align:middle;margin-bottom:2px;\" /><input type='button' class='GObtn' onclick=\"javascript:  var txtPagerValue=document.getElementById('txtPager').value; var regs = /^\\d+$/; if(!regs.test(txtPagerValue)){alert('輸入錯誤');document.getElementById('txtPager').focus();return false;};var hdnPStringValue=document.getElementById('hdnPString').value; location.href='?page='+txtPagerValue+hdnPStringValue;\" id=\"btnPager\" style=\"display:inline-block;vertical-align:middle;margin-bottom:2px;\" />\n"
				+ "\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t</tbody>\n"
				+ "\t\t\t</table>\n"
				+ "\t\t</div>\n"
				+ "\t</div>\n"
				+ "\t<script>\n"
				+ "\twindow.onload = function () {\n"
				+ "\t\tseajs.use(['jquery', 'Bet'], function ($) {\n"
				+ "\t\t\tvar dialog = top.dialog.get(window);\n"
				+ "\t\t\t// dialog.title('测试例子');\n"
				+ "\t\t\tdialog.height($('body').height());\n"
				+ "        \tdialog.width($('body').width());\n"
				+ "\t\t\t$(dialog.iframeNode).height($('body').height());\n"
				+ "\t\t\tdialog.reset();     // 重置对话框位置\n"
				+ "\t\t});\n"
				+ "\t};\n"
				+ "\t</script>\n"
				+ "</body>\n"
				+ "</html>\n";


		System.out.println(ComMemberTool.getIsSettleInfo(html, GameUtil.Code.GDKLC,"2020051107"));


    }

    @Test
	public void test11(){
	String html="\n"
			+ "<!DOCTYPE html>\n"
			+ "<html>\n"
			+ "<head>\n"
			+ "\n"
			+ "\t\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
			+ "\t\t\t<title>title</title>\n"
			+ "\t\t\t<script> if (top.location == self.location) top.location.href = \"/\"; </script>\n"
			+ "\t\t\t<link href=\"/Styles/global.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
			+ "\t\t\t<link href=\"/Styles/base.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
			+ "\t\t\t<link rel=\"stylesheet\" href=\"/Styles/BallCss/ball_all.css\">\n"
			+ "\t\t\t<link id=\"Iframe_skin\" href=\"/Styles/Yellow/open.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
			+ "\t\t\t<script>var jsver=20170128;</script>\n"
			+ "\t\t\t<script src=\"/Scripts/sea.js\" type=\"text/javascript\"></script>\n"
			+ "\t\t\t<script src=\"/Scripts/seajs-css.js\" type=\"text/javascript\"></script>\n"
			+ "\t\t<script src=\"/Scripts/juicer-min.js\" type=\"text/javascript\"></script>\n"
			+ "\t\t\t<script src=\"/Scripts/config.js\" type=\"text/javascript\"></script>\n"
			+ "\t\t\t\n"
			+ "\n"
			+ "\t<script>\n"
			+ "\t\tvar masterid = '2';\n"
			+ "\t</script>\n"
			+ "\t<style>\n"
			+ "\t\t.MXbox{\n"
			+ "\t\t\twidth: 920px;\n"
			+ "\t\t}\n"
			+ "\t\t.MXbox .navBox{\n"
			+ "\t\t\twidth: 926px;\n"
			+ "\t\t}\n"
			+ "\t\t.infoList2\n"
			+ "\t\t{\n"
			+ "\t\t\twidth: 920px;\n"
			+ "\t\t\toverflow-y: auto;\n"
			+ "\t\t\toverflow-x: hidden;\n"
			+ "\t\t}\n"
			+ "\t</style>\n"
			+ "</head>\n"
			+ "<body>\n"
			+ "\t<div class=\"MXbox\">\n"
			+ "\t\t<div class=\"navBox\">\n"
			+ "\t\t\t<ul>\n"
			+ "\t\t\t\t<li class=\"tabBtn off\" onClick=\"javascript:location.href='Bet.aspx?masterId=1&page=1'\"\n"
			+ "\t\t\t\t\tstyle=\"cursor: pointer; display: none\">香港⑥合彩</li>\n"
			+ "\t\t\t\t<li class=\"tabBtn off\" onClick=\"javascript:location.href='Bet.aspx?masterId=2&page=1'\"\n"
			+ "\t\t\t\t\tstyle=\"cursor: pointer; display: none\">快 彩</li>\n"
			+ "\t\t\t</ul>\n"
			+ "\t\t</div>\n"
			+ "\t\t<div class=\"listBox\">\n"
			+ "\t\t\t<table class=\"infoList2\" id=\"box_six\" style=\"display: none;\">\n"
			+ "\t\t\t\t\t<tr>\n"
			+ "\t\t\t\t\t\t<th width=\"190\">\n"
			+ "\t\t\t\t\t\t\t註單號/時間\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t\t<th width=\"150\">\n"
			+ "\t\t\t\t\t\t\t下註類型\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t\t<th width=\"300\">\n"
			+ "\t\t\t\t\t\t\t註單明細\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t\t<th width=\"140\">\n"
			+ "\t\t\t\t\t\t\t下註金額\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t\t<th width=\"140\">\n"
			+ "\t\t\t\t\t\t\t可贏金額\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t</tr>\n"
			+ "\t\t\t\t\n"
			+ "\t\t\t\t\t<tr><td colspan=\"5\">無未結算記錄！</td></tr>\n"
			+ "\t\t\t   \n"
			+ "\t\t\t\t\t<tr class=\"tdbg\">\n"
			+ "\t\t\t\t\t\t<th colspan=\"2\">\n"
			+ "\t\t\t\t\t\t\t當前頁合計\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t\t<th>\n"
			+ "\t\t\t\t\t\t\t<b class=\"orange\">\n"
			+ "\t\t\t\t\t\t\t\t0</b>筆\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t\t<th class=\"orange\">\n"
			+ "\t\t\t\t\t\t\t0.0\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t\t<th>&nbsp;\n"
			+ "\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t</tr>\n"
			+ "\t\t\t\t\t<tr class=\"tdbg\">\n"
			+ "\t\t\t\t\t\t<td colspan=\"5\" align=\"center\">\n"
			+ "\t\t\t\t\t\t\t\n"
			+ "\t\t\t\t\t\t</td>\n"
			+ "\t\t\t\t\t</tr>\n"
			+ "\t\t\t</table>\n"
			+ "\t\t\t<table class=\"infoList2\" id=\"box_kc\" style=\"display: none;\">\n"
			+ "\t\t\t\t<tr>\n"
			+ "\t\t\t\t\t<th width=\"190\">\n"
			+ "\t\t\t\t\t\t註單號/時間\n"
			+ "\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t<th width=\"150\">\n"
			+ "\t\t\t\t\t\t下註類型\n"
			+ "\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t<th width=\"300\">\n"
			+ "\t\t\t\t\t\t註單明細\n"
			+ "\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t<th width=\"140\">\n"
			+ "\t\t\t\t\t\t下註金額\n"
			+ "\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t<th width=\"140\">\n"
			+ "\t\t\t\t\t\t可贏金額\n"
			+ "\t\t\t\t\t</th>\n"
			+ "\t\t\t\t</tr>\n"
			+ "\t\t\t\t\n"
			+ "\t\t\t\t<tr><td colspan=\"5\">無未結算記錄！</td></tr>\n"
			+ "\t\t\t\t\n"
			+ "\t\t\t\t\t<tr class=\"tdbg\">\n"
			+ "\t\t\t\t\t\t<th colspan=\"2\">\n"
			+ "\t\t\t\t\t\t\t當前頁合計\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t\t<th>\n"
			+ "\t\t\t\t\t\t\t<b class=\"orange\">\n"
			+ "\t\t\t\t\t\t\t\t0</b>筆\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t\t<th class=\"orange\">\n"
			+ "\t\t\t\t\t\t\t0.0\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t\t<th>&nbsp;\n"
			+ "\n"
			+ "\t\t\t\t\t\t</th>\n"
			+ "\t\t\t\t\t</tr>\n"
			+ "\t\t\t\t\t<tr class=\"tdbg\">\n"
			+ "\t\t\t\t\t\t<td colspan=\"5\" align=\"center\">\n"
			+ "\t\t\t\t\t\t\t\n"
			+ "\t\t\t\t\t\t</td>\n"
			+ "\t\t\t\t\t</tr>\n"
			+ "\t\t\t\t</tbody>\n"
			+ "\t\t\t</table>\n"
			+ "\t\t</div>\n"
			+ "\t</div>\n"
			+ "\t<script>\n"
			+ "\twindow.onload = function () {\n"
			+ "\t\tseajs.use(['jquery', 'Bet'], function ($) {\n"
			+ "\t\t\tvar dialog = top.dialog.get(window);\n"
			+ "\t\t\t// dialog.title('测试例子');\n"
			+ "\t\t\tdialog.height($('body').height());\n"
			+ "        \tdialog.width($('body').width());\n"
			+ "\t\t\t$(dialog.iframeNode).height($('body').height());\n"
			+ "\t\t\tdialog.reset();     // 重置对话框位置\n"
			+ "\t\t});\n"
			+ "\t};\n"
			+ "\t</script>\n"
			+ "</body>\n"
			+ "</html>\n";

		Document document = Jsoup.parse(html);

		Elements trList =document.getElementById("box_kc").select("tbody>tr");


		trList.remove(0);
		trList.remove(trList.size()-1);
		trList.remove(trList.size()-1);

		System.out.println(trList.size());
		System.out.println(trList);

		if(trList.size()==1&& StringTool.isContains(trList.text(),"無未結算記錄")){
			System.out.println(trList.text());
		}

//		for(Element tr:trList){
//			String period=StringUtils.substringBetween(tr.child(1).text(),"# ","期");
//
//			System.out.println(period);
//			System.out.println(tr.child(2).text());
//			System.out.println(tr.child(3).text());
//		}
	}


    @Test
    public void test(){
        String html="\n"
                + "\n"
                + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n"
                + "   \n"
                + "   <meta name=\"renderer\" content=\"webkit\">\n"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=EDGE\">\n"
                + "    <title>註單明細</title>\n"
                + "    <link href=\"favicon.ico\" rel=\"shortcut icon\">\n"
                + "    <link rel=\"stylesheet\" href=\"/Styles/BallCss/ball_all.css\">\n"
                + "    <link id=\"Iframe_skin\" rel=\"stylesheet\" href=\"/Styles/Yellow/skin.css\">\n"
                + "    <link rel=\"stylesheet\" href=\"/Styles/global.css\">\n"
                + "    <script>var jsver=20170128;</script>\n"
                + "    <script>var isOpenUpper=\"0\";</script>\n"
                + "    <script src=\"/Scripts/json2.js\" type=\"text/javascript\"></script>\n"
                + "    <script src=\"/Scripts/sea.js\" type=\"text/javascript\"></script>\n"
                + "    <script src=\"/Scripts/seajs-css.js\" type=\"text/javascript\"></script>\n"
                + "    <script src=\"/Scripts/otherConfig.js\" type=\"text/javascript\"></script>\n"
                + "    \n"
                + "<style type=\"text/css\">\n"
                + "body{ background:#fff;}\n"
                + "</style></head>\n"
                + "<body>\n"
                + "<div id=\"windowLayer\">\n"
                + "    <div id=\"windowTopLayer1\"></div>\n"
                + "    <!--主体部分 begin-->\n"
                + "    <table class=\"t_list\" style=\"width:100%;\">\n"
                + "             <tbody class=\"list_hover\">\n"

                + "              \n"
                + "            </tbody>\n"
                + "\n"
                + "</table>\n"
                + "    <!--主体部分end-->\n"
                + "    <div class=\"windowPager\">\n"
                + "       <span id='pager'>共 2 條記錄  分頁：1/1頁&nbsp;&nbsp;&nbsp;上一頁『<span class='font_c'>1</span>&nbsp;』下一頁<input type=\"hidden\" value=\"1\" id=\"page\"></span><input type=\"hidden\" value=\"&t_LID=&t_FT=1&mID=&UP_ID=&FactSize=&t_Balance=1&userid=7ba83963-1724-4719-8dc4-d102366e36be&m_type=hy&gID=0&uID=&BeginDate=2020-04-27&EndDate=2020-04-27&q_type=&t_LT=9&sltTabletype=&sltPlaytype=\" id=\"hdnPString\"></span>&nbsp;&nbsp;<input type='text' value='1' name='txtPager' id='txtPager' class='GOtext' onkeydown=\"javascript: if (event.keyCode ==13){ var txtPagerValue=document.getElementById('txtPager').value; var regs = /^\\d+$/; if(!regs.test(txtPagerValue)){alert('輸入錯誤');document.getElementById('txtPager').focus();return false;};var hdnPStringValue=document.getElementById('hdnPString').value; location.href='?page='+txtPagerValue+hdnPStringValue;};\" style=\"display:inline-block;vertical-align:middle;margin-bottom:2px;\" /><input type='button' class='GObtn' onclick=\"javascript:  var txtPagerValue=document.getElementById('txtPager').value; var regs = /^\\d+$/; if(!regs.test(txtPagerValue)){alert('輸入錯誤');document.getElementById('txtPager').focus();return false;};var hdnPStringValue=document.getElementById('hdnPString').value; location.href='?page='+txtPagerValue+hdnPStringValue;\" id=\"btnPager\" style=\"display:inline-block;vertical-align:middle;margin-bottom:2px;\" />\n"
                + "    </div>\n"
                + "</div>\n"
                + "<script>\n"
                + "var myLayerIndex = '19841011';\n"
                + "var myLayerIndexArr = [];\n"
                + "var pathFolder = 'KC';\n"
                + "window.onload = function () {\n"
                + "    seajs.use(['jquery', 'listHover', 'reportCurrentphase', 'doubleMyLayer'], function ($) {\n"
                + "      \n"
                + "        \n"
                + "    });\n"
                + "}\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>\n";

        Document document = Jsoup.parse(html);

        Elements bodys=document.getElementsByClass("list_hover");

        Elements trs=document.select("tbody.list_hover>tr");

//        System.out.println(trs);

        for(Element tr:trs){
            System.out.println("注单号="+tr.child(0).text().split("#")[0]);
            System.out.println("下注明細="+tr.child(3).text().split("』")[0]);
            System.out.println("投注额="+tr.child(4).text());
        }
        Element pageElements = document.select("div.windowPager>span").first();
        System.out.println(pageElements.text());

        System.out.println(StringUtils.substringBetween(pageElements.text(),"共 "," 條記錄"));
        System.out.println(StringUtils.substringBetween(pageElements.text(),"分頁：1/","頁 "));
        //TODO 解析页面

    }

    @Test
    public void test05() {
        String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "<title>注单明细</title>\n" +
                "<link href=\"/js/jquery/new/jquery-ui.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "<link href=\"/default/css/agent/red/master.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "<link href=\"/default/css/agent/red/layout.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "<link href=\"/default/css/sweetalert.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "<link href=\"/default/css/loading.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"/js/jquery-ui.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"/js/jquery.ui.datepicker-zh-CN.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"/js/libs.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"/js/json2.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"/default/js/sweetalert.min.js\"></script>\n" +
                "<script>\n" +
                "  $(document).ready(function(){\n" +
                "    $(\".page-jump input\").on(\"keydown\", function(e){\n" +
                "      if(e.keyCode === 13){\n" +
                "        e.stopPropagation();\n" +
                "        $(this).blur();\n" +
                "        if($(this).val() <= 0 || $(this).parent().data(\"total-page\") < $(this).val()){\n" +
                "          alert(\"页面不存在\");\n" +
                "          return;\n" +
                "        } else{\n" +
                "          var navigatePage = $(this).parent().data(\"page-url\").replace(\"page=1\", \"page=\" + $(this).val());\n" +
                "          location.href = location.origin + location.pathname + navigatePage;\n" +
                "        }\n" +
                "      }\n" +
                "    })\n" +
                "  })\n" +
                "\n" +
                "</script><link href=\"/default/css/agent/bets.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "<link href=\"/default/css/betExtra.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "<script type=\"text/javascript\" src=\"/default/js/bets.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"/default/js/betExtra.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"main\">\n" +
                "<div class=\"top_info\">\n" +
                "<span class=\"title\">注单明细</span>\n" +
                "<span class=\"right\">\n" +
                "<a class=\"back\" href=\"javascript:history.go(-1)\">返回</a>\n" +
                "</span>\n" +
                "</div>\n" +
                "<div class=\"contents\">\n" +
                "<table class=\"data_table list\">\n" +
                "<thead>\n" +
                "<tr>\n" +
                "<th class=\"th-box\">注单号\n" +
                "</th>\n" +
                "<th>投注时间</th>\n" +
                "<th class=\"type th-box\">投注种类\n" +
                "</th>\n" +
                "<th>账号</th>\n" +
                "<th>投注内容</th>\n" +
                "<th class=\"range th-box\" style=\"width:250px\">\n" +
                "<div class=\"display_amount\">\n" +
                "下注金额\n" +
                "</div>\n" +
                "<div class=\"input_amount\" style=\"display:none\">\n" +
                "<input  type\"search\" placeholder=\"最小下注金额\" name=\"minAmount1\" value=\"\" style=\"width:80px\" />&nbsp;\n" +
                "<input type\"search\" placeholder=\"最大下注金额\" name=\"maxAmount1\" value=\"\" style=\"width:80px\"/>\n" +
                "</div>\n" +
                "<button class=\"th_icon1\"></button>\n" +
                "</th>\n" +
                "</th>\n" +
                "<th>退水</th>\n" +
                "<th>下注结果</th>\n" +
                "<th>本级占成</th>\n" +
                "<th class=\"result\">本级结果</th>\n" +
                "<th>占成明细</th>\n" +
                "</tr>\n" +
                "</thead>\n" +
                "<tbody>\n" +
                "</tbody>\n" +
                "<tfoot>\n" +
                "<tr><th colspan=\"5\">总计：0笔</th><td class=\"money\">0</td><td></td><td class=\"money color\"></td><td></td>\n" +
                "<td class=\"money color\"></td><td></td></tr>\n" +
                "</tfoot>\n" +
                "</table>\n" +
                "</div>\n" +
                "<div id=\"betExtraDialog\"></div>\n" +
                "<div class=\"page\"><div class=\"page_info\">\n" +
                "</div>\n" +
                "</div></div>\n" +
                "</body>\n" +
                "</html>";


        Document document = Jsoup.parse(html);

        Elements trElements = document.select("div.contents>table.data_table>tbody>tr");

        System.out.println(trElements);
        if (trElements.isEmpty()) {
            System.out.println("asdas");
        }

    }


}
