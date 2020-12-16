<!-- 
gen_version=${gen_version} 
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<style type="text/css">
* {
	margin: 0px;
	padding: 0px;
}

BODY {
	FONT-SIZE: 12px;
	FONT-FAMILY: Tahoma, Verdana;
	BACKGROUND: #fff;
	COLOR: #333;
}

/*表css*/
.pop_tb {
/*解决ie6下空白的问题*/

/*width: 400px;*/

width: 400px;
	BORDER-COLLAPSE: collapse;
}

/*表中间*/
.pop_tb .pop_content {
	background-color: #fff;
}

/*表border*/
.pop_tb .pop_topleft,.pop_topright,.pop_bottomleft,.pop_bottomright,.pop_border
	{
	width: 10px;
	height: 10px;
	opacity: 0.5;
	filter: alpha(opacity =   50);
	/*background-image: url(pop_border.gif);*/
	overflow: hidden;
}

/*表border*/
.pop_tb .pop_topleft {
	background-position: 0px 0px;
}

.pop_tb .pop_topright {
	background-position: -10 0px;
}

.pop_tb .pop_bottomleft {
	background-position: 0 -10px;
}

.pop_tb .pop_bottomright {
	background-position: -10 -10px;
}

.pop_tb .pop_border {
	background-position: -20px -20px;
}

/*表中间*/
.pop_content {
	width: 400px;
}

.pop_content .dialog_title { /*  BORDER: #005eac 1px solid; 蓝色*/
	/*  BORDER: #357d02 1px solid; 绿色*/
	BORDER: #357d02 1px solid;
	FONT-SIZE: 14px;
	font-weight: bold;
	/*BACKGROUND: #6d84b4; 蓝色*/ 
	
	/*BACKGROUND: #357d02; 绿色*/
	BACKGROUND: #6d84b4;
	COLOR: #fff;
	padding: 7px;
}

.pop_content .dialog_body {
	FONT-SIZE: 13px;
	FONT-FAMILY: Tahoma, Verdana;
	color: #555;
	PADDING-RIGHT: 10px;
	PADDING-LEFT: 10px;
	PADDING-BOTTOM: 10px;
	PADDING-TOP: 10px;
	BORDER-BOTTOM: #ccc 1px solid
}

.pop_content .dialog_content {
	BORDER-RIGHT: #555 1px solid;
	BACKGROUND: #fff;
	BORDER-LEFT: #555 1px solid;
	BORDER-BOTTOM: #555 1px solid
}

.pop_content .dialog_buttons {
	PADDING-RIGHT: 8px;
	PADDING-LEFT: 8px;
	BACKGROUND: #f2f2f2;
	PADDING-BOTTOM: 8px;
	PADDING-TOP: 8px;
	TEXT-ALIGN: right
}

.pop_content .input-submit {
	BORDER-TOP: #b8d4e8 1px solid;
	BORDER-LEFT: #b8d4e8 1px solid;
	BORDER-RIGHT: #124680 1px solid;
	BORDER-BOTTOM: #124680 1px solid;
	PADDING-TOP: 5px;
	PADDING-RIGHT: 15px;
	PADDING-LEFT: 15px;
	FONT-SIZE: 12px;
	CURSOR: pointer;
	COLOR: #fff;
	/*BACKGROUND:  #005eac; 蓝色*/
	 /*BACKGROUND: #357d02; 绿色*/
	BACKGROUND-COLOR: #005eac;
	TEXT-ALIGN: center;
}

/*=======================================*/ /*阻塞id_block*/ /*{*/
#id_block {
	background-color: #999;
	/*--  background-color: #FFFFFF;--*/ /*--定义透明度,支持ie--*/
	filter: alpha(opacity =   80, Style =   0);
	/*--定义透明度,支持firefox--*/
	opacity: 0.8;
	width: 100%;
	height: 100%;
	left: 0px;
	top: 0px;
	  POSITION:fixed;
}
/*}*/ /*阻塞id_block*/
</style>













<div   id="id_block" style="display: none;">
<!--<DIV style="position: absolute; left: 10%; top: 10%; z-index: 99;">-->
<DIV style="position: absolute; left: 1%; top: 1%; z-index: 99;">

<TABLE class="pop_tb" id='id_table_pop' >
	<TR>
		<TD class="pop_topleft"></TD>
		<TD class="pop_border"></TD>
		<TD class="pop_topright"></TD>
	</TR>
	<TR>
		<TD class="pop_border"></TD>
		<TD class="pop_content">
		<div>
		<div class="dialog_title" id="id_alert_title"><SPAN>新消息</SPAN></div>
		<DIV class="dialog_content">
		<DIV class="dialog_body" id="id_alert_msg">吃饭</DIV>
		<DIV class="dialog_buttons"><INPUT id="id_input_submit" class="input-submit"
			onclick="ay$alert$onclick_block_hide('submit','id_block');"
			type="button" value="确定" /> <INPUT id="id_input_esc"
			class="input-submit"
			onclick="ay$alert$onclick_block_hide('esc','id_block');" type="button"
			value="取消" /></DIV>
		</DIV>
		</div>
		</TD>
		<TD class="pop_border"></TD>
	</TR>
	<TR>
		<TD class="pop_bottomleft"></TD>
		<TD class="pop_border"></TD>
		<TD class="pop_bottomright"></TD>
	</TR>
</TABLE>




</DIV>
</div>
