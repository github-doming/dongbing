<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <script type="text/javascript">
    </script>
</head>
<body>
<form id="id_form_save"
      action="${pageContext.request.contextPath}/ibm/admin/manage/sealTime/sealTimeSet"
      method="post">
    <table class="table  table-bordered table-hover" border="1">
        <tr>
            <td colspan="3"><input onclick="back();" class="btn"
                                   type="button" value="返回列表"/>
                <input onclick='update();' class="btn" type="button" value="编辑">
            </td>
        </tr>
        <tr>
            <td align="right">配置KEY:
            </td>
            <td><input id="id_input$key" type="text" name="key"
                       value="${requestScope.key }">
            </td>
            <td><span id="id_span$key"></span>
            </td>
        </tr>
        <tr>
            <td align="right">封盘时间:
            </td>
            <td><input id="id_input$value" placeholder="请输入封盘时间" type="text"
                       name="value" value="${requestScope.value }">
            </td>
            <td><span id="id_span$value"></span>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<!-- 加载js -->
<script type="text/javascript">
    /**
     *
     返回
     */
    function back() {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/sealTime/list";
    }

    /**
     *
     保存
     */
    function update() {
        //提交
        var obj_form = $('#id_form_save');
        $.post(obj_form.attr("action"), obj_form.serialize(), function (data) {
            console.log(data);
            if (data.success) {
                document.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/sealTime/list";
            } else {
                alert(data.msg);
            }
        }, "json")
    }
</script>