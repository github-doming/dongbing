<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<style type="text/css">
</style>
<div style="margin: 6px;" class="class_font_size_12px"><!-- 
	隐藏table
style="display: none;"
	-->
<table style="display: none;" border="1">
	<tr>
		<td>第几页</td>
		<td><input type="text" id="id_page" name="pageIndexName" value="1">
		</td>
	</tr>
	<tr>
		<td>是否显示全部</td>
		<td><input type="text" id="id_show_all" name="name_show_all"
			value="${requestScope.value_show_all}"></td>
	</tr>
</table>
<table>
	<!-- 分页 -->
	<tr>
		<td>共${cPage.totalCount}条记录, 当前第${cPage.pageIndex}页, 共
		${cPage.totalPages}页</td>
		<td>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><!-- 首页 --> <a class="class_a" id="id_input_page_first"
			style="cursor: pointer;"
			onclick="ayPageSelect('${pageContext.request.contextPath}','1');"
			title="首页" value="首页">首页</a> <!-- 首页 --> <!-- 上一页 --> <c:choose>
			<c:when test="${cPage.hasPreviousPage}">
				<a class="class_a" style="cursor: pointer;"
					onclick="ayPageSelect('${pageContext.request.contextPath}','${cPage.prePage}');"
					title="上一页" value="上一页">上一页</a>
			</c:when>
			<c:otherwise>
				<label class="class_a" disabled="disabled"
					onclick="ayPageSelect('${pageContext.request.contextPath}','${cPage.prePage}');"
					title="上一页" value="上一页">上一页</label>
			</c:otherwise>
		</c:choose> <!-- 上一页 --> <!-- 下一页 --> <c:choose>
			<c:when test="${cPage.hasNextPage}">
				<a class="class_a" style="cursor: pointer;"
					onclick="ayPageSelect('${pageContext.request.contextPath}','${cPage.nextPage}');"
					title="下一页" value="下一页">下一页</a>
			</c:when>
			<c:otherwise>
				<label class="class_a" disabled="disabled"
					onclick="ayPageSelect('${pageContext.request.contextPath}','${cPage.nextPage}');"
					title="下一页" value="下一页">下一页</label>
			</c:otherwise>
		</c:choose> <!-- 下一页 --> <!-- 末页--> <a class="class_a" style="cursor: pointer;"
			onclick="ayPageSelect('${pageContext.request.contextPath}','${cPage.totalPages}');"
			title="末页" value="末页">末页</a> <!-- 末页--> 跳转到<input
			style="width: 60px;" id="id_gopage" name="name_gopage" type="text"
			value="${cPage.pageIndex}" style="width: 100px;"></input>页 <input
			value='GO' type='button'
			onclick="ayPageGo('${pageContext.request.contextPath}');"></input>
		</td>
	</tr>
	<!-- 分页 -->
</table>
<!-- 
	隐藏table
style="display: none;"
	-->
<table style="display: none;" border="1">
	<tr>
		<td align="center">每页 <select style="width: 60px;"
			name="pageIndexName_size" id="id_selectpageSize">
			<!-- 下拉列表选择每页记录数 -->
			<c:choose>
				<c:when test="${cPage.pageSize==10}">
					<option value="10" selected="selected">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
					<!-- 	<option value="1">1</option> -->
				</c:when>
				<c:when test="${cPage.pageSize==20}">
					<option value="10">10</option>
					<option value="20" selected="selected">20</option>
					<option value="50">50</option>
					<!-- 	<option value="1">1</option> -->
				</c:when>
				<c:when test="${cPage.pageSize==50}">
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50" selected="selected">50</option>
					<!-- 	<option value="1">1</option> -->
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${requestScope.value_show_all=='show_all_true'}">
							<option value="10">10</option>
							<option value="20">20</option>
							<option value="50">50</option>
							<!-- 	<option value="1">1</option> -->
							<option value="${cPage.pageSize}" selected="selected">${cPage.pageSize}</option>
						</c:when>
						<c:otherwise>
							<option value="10">10</option>
							<option value="20">20</option>
							<option value="50">50</option>
							<!-- 	<option value="1">1</option> -->
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<!-- 下拉列表选择每页记录数 -->
		</select>条记录 &nbsp;&nbsp;<B>共${cPage.totalCount}条记录</B>&nbsp;&nbsp; 当前第<input
			style="width: 60px;" id="id_gopage" name="name_gopage" type="text"
			value="${cPage.pageIndex}" style="width: 100px;"></input>页 <a
			onclick="ayPageGo('${pageContext.request.contextPath}');" href="#">跳转</a>
		<B> &nbsp;&nbsp;共 ${cPage.totalPages}页</B></td>
	</tr>
</table>
<!-- 
	隐藏table
style="display: none;"
	--></div>
