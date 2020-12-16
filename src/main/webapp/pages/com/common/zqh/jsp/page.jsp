<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<div><!-- 
	隐藏table
style="display: none;"
	-->

	<!-- 分页 -->
	<tr>
		<td align="center"><!-- 首页 --> <input class="btn"  id="id_input_page_first"
			style="cursor: pointer;" type="button"
			title="首页" value="首页"></input> <!-- 首页 --> <!-- 上一页 --> 
				<input class="btn"  style="cursor: pointer;" type="button" id="id_input_page_pre"
					title="上一页" value="上一页"></input>

		 <!-- 上一页 --> 
		<!-- 下一页 -->
				<input class="btn"  style="cursor: pointer;" type="button" id="id_input_page_next"
					title="下一页" value="下一页"></input>
<!-- 下一页 --> 
		<!-- 末页--> <input class="btn"  style="cursor: pointer;" type="button" id="id_input_page_last"
			title="末页" value="末页"></input> <!-- 末页--></td>
	</tr>
	<tr>
		<td align="center">每页 <select   style="width:60px;" name="pageSizeName"
			id="id_selectpageSize" onchange="pages(1)">
			<!-- 下拉列表选择每页记录数 -->
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
			<!-- 下拉列表选择每页记录数 -->
		</select>条记录 &nbsp;&nbsp;<B id="Bid"></B>&nbsp;&nbsp;
		当前第<input  style="width:60px;" id="id_gopage" name="name_gopage" type="text"
			style="width: 100px;" oninput="value=value.replace(/[^\d]/g,'')"></input>页<a id="id_tz" href="#">跳转</a><B id="Bid2"> &nbsp;&nbsp;</B>
		</td>
	</tr>
	<!-- 分页 -->
</table>
<script type="text/javascript">
	function page(pageIndex,totalPage,totalSize){
		//清除按钮禁用属性
		$("#id_input_page_first").removeAttr("disabled");
		$("#id_input_page_pre").removeAttr("disabled");
		$("#id_input_page_next").removeAttr("disabled");
		$("#id_input_page_last").removeAttr("disabled");
		
		//给分页button添加点击事件
		//首页
		$("#id_input_page_first").attr("onclick","pages(1)");
		//上一页
		var prePage = pageIndex == 1?1:pageIndex-1;
		$("#id_input_page_pre").attr("onclick","pages("+prePage+")");
		//下一页
		var nexPage = pageIndex == totalPage?totalPage:pageIndex+1;
		$("#id_input_page_next").attr("onclick","pages("+nexPage+")");
		//末页
		$("#id_input_page_last").attr("onclick","pages("+totalPage+")");
		//选择记录数
		//$("#id_input_page_last").attr("onchange","pages("+pageIndex+")");
		//共几条记录
		document.getElementById("Bid").innerHTML="共"+totalSize+"条记录";
		//第几页
		$("#id_gopage").val(pageIndex);
		//共几页
		document.getElementById("Bid2").innerHTML="共"+totalPage+"页";
		//跳转
		$("#id_tz").attr("onclick","tz("+totalPage+")");
		
		//设置按钮禁用
		if(totalPage <= 1){
			//全部禁用
			$("#id_input_page_first").attr("disabled","true");
			$("#id_input_page_pre").attr("disabled","true");
			$("#id_input_page_next").attr("disabled","true");
			$("#id_input_page_last").attr("disabled","true");
		}else if(pageIndex == 1){
			//禁用首页和上一页
			$("#id_input_page_first").attr("disabled","true");
			$("#id_input_page_pre").attr("disabled","true");
		}else if(pageIndex == totalPage &&　totalPage > 1){
			//禁用下一页和末页
			$("#id_input_page_next").attr("disabled","true");
			$("#id_input_page_last").attr("disabled","true");
		}

	}
	//跳转页面
	function tz(totalPage){
		var pageVal = $("#id_gopage").val();
		if(pageVal > totalPage){
			pageVal = totalPage;
		}
		pages(pageVal);
	}
</script>
</div>
