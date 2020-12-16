<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>





<%@ include file="/pages/c/x/platform/root/common/common.jsp"%> 




<!-- 加载js -->


<script type="text/javascript">



function delAllRecord(id){
	var url = "${pageContext.request.contextPath}/admin/gen/single_simple/del_all.do";


	var objs = document.getElementsByName("checkboxIds");

	var checkedNumber = 0;

	for (var i = 0; i < objs.length; i++) {

		if (objs[i].checked) {
			checkedNumber = checkedNumber + 1;

		}

	}

	if (checkedNumber == 0) {



	alert('请先选择要删除的行');
	}else{



		if(confirm("确定要删除吗？"))
		{
			document.getElementById("id_form_list").action=url;
			document.getElementById("id_form_list").submit();
		}
		else
		{
		       
		}


		
	}

	
	
	return false;
}



function delRecord(id){


	if(confirm("确定要删除吗？"))
	{

		var url = "${pageContext.request.contextPath}/admin/gen/single_simple/del.do?id="+id;
	//alert(url);
		window.location.href= url;
	}
	else
	{
	       
	}



	
	

}



function updateRecord(id){
	var url= "${pageContext.request.contextPath}/admin/gen/single_simple/form.do?id="+id;
 	//alert(url);
 	window.location.href=url;
}



function newRecord(){
 	var url= "${pageContext.request.contextPath}/admin/gen/single_simple/form.do";
 	//alert(url);
 	window.location.href=url;

}
	</script>
	
	
	
	

	
	<title></title>
	
</head>
<body>
<div style="display: none;" id="id_div_msg">

正在提交中...
</div>


<form id="id_form_list" method="post">
	
	
<table  class="table table-hover" border="0">
  <caption></caption>
  <thead>
    <tr>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
      
      <div  align="center" valign="middle">
			
				菜单名称
	<input  type="text" name="menu_name"	value="${requestScope.value_menu_name}">
	<input class="btn btn-info" type="submit" value="搜索">
			
			</div>
      
      </td>

    </tr>
    
    
     <tr>
      <td>
      
     			
<input class="btn" id="id_input_new" type="button" value="新增" onclick="newRecord()">






       
          
          
<input class="btn" id="id_input_new" type="button" value="删除"
	onclick="delAllRecord()">
      
      
      </td>

    </tr>
    
    
    
  </tbody>
</table>


<table  style="display: none;"   align="center"">


	<tr>
		<td >升降序</td>
		<td><input value="${requestScope.sortFieldValue}" id="sortFieldId" name="sortFieldName" /></td>
		<td >排序的列值</td>
		<td><input value="${requestScope.sortOrderValue}" id="sortOrderId"
			name="sortOrderName" /></td>
	</tr>
	
</table>





<table id="id_table" class="table  table-bordered table-hover">
  <caption></caption>
  <thead>
  
  
 <tr>

		<th><input id="ifAll" onclick=ayTableCheckAll();
			type="checkbox" name="" />全选</th>


		<th>操作</th>
		<th
			onclick="ayPageOrderEvent('${pageContext.request.contextPath}','id');"
			title="按[id]排序" style="cursor: pointer;">id</th>
		<th title="按[名称]排序" style="cursor: pointer;">名称</th>
		<th style="display: none; cursor: pointer;">path</th>
		<th onclick="ayPageOrderEvent('${pageContext.request.contextPath}','tree_code');" style="display: none; cursor: pointer;">tree_code</th>
		<th onclick="ayPageOrderEvent('${pageContext.request.contextPath}','url');" style="cursor: pointer;">url</th>
		<th class="class_color"><!--隐藏列事件--> <select
			onchange="ayTableHiddenColumn(this,'id_table',2);">
			<option value=" &nbsp;" selected="selected">&nbsp;</option>

			<option value="hit.name">名称</option>
			<option value="hit.path">path</option>
		<option value="hit.tree_code">tree_code</option>
		</select> <!--隐藏列事件--></th>
	</tr>
    
    
    
  </thead>
  <tbody>
    
    
    
    
    
	<%
		java.util.ArrayList<c.x.platform.e.admin.feng.bs.sys.gen.single_simple.entity.GenSingleSimple> listMap = (java.util.ArrayList<c.x.platform.e.admin.feng.bs.sys.gen.single_simple.entity.GenSingleSimple>) request
				.getAttribute("list");

		for (c.x.platform.e.admin.feng.bs.sys.gen.single_simple.entity.GenSingleSimple info : listMap) {
	%>


	<tr>


		<td><input value="<%=info.getUuid()%>" type="checkbox"
			name="checkboxIds"></td>




		<td>
		
		

		
		
				 <a 
		onclick="delRecord('<%=info.getUuid()%>')" href="#"> 删除 </a>
			
			
			
			 <a 
			onclick="updateRecord('<%=info.getUuid()%>')" href="#"> 编辑 </a></td>


		<td>&nbsp;<%=info.getUuid()%></td>
		<td id="hit.name">&nbsp;<%=info.getName()%></td>
		<td id="hit.path" style="display: none;">&nbsp;<%=info.getPath()%></td>
		<td id="hit.tree_code" style="display: none;">&nbsp;<%=info.getTreeCode()%></td>
		<td>&nbsp;<%=info.getUrl()%></td>

		<td>&nbsp;</td>
	</tr>




	<%
		}
	%>
    
 
    
  </tbody>
</table>
<%@ include file="/pages/c/x/platform/root/common/page/page.jsp"%> 




</form>


</body>
</html>