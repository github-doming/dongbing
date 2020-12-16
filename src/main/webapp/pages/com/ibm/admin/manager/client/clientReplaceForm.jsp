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
      action="${pageContext.request.contextPath}/ibm/admin/manage/client/clientReplace1"
      method="post">
    <table class="table  table-bordered table-hover" border="1">
        <tr>
            <td colspan="3"><input onclick="back();" class="btn"
                                   type="button" value="返回列表"/>
                <input onclick='update();' class="btn" type="button" value="编辑">
            </td>
        </tr>
        <tr>
            <td align="right">发送端客户机:</td>
            <td><input id="id_input$sendClientCode" placeholder="请输入发送端客户机" type="text" name="sendClientCode"></td>
            <td><span id="id_span$sendClientCode"></span></td>
        </tr>
        <tr>
            <td align="right">接收端客户机:</td>
            <td><input id="id_input$receiveClientCode" placeholder="请输入接收端客户机" type="text" name="receiveClientCode"></td>
            <td><span id="id_span$receiveClientCode"></span></td>
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
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/list1";
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
                document.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/list1";
            } else {
                alert(data.msg);
            }
        }, "json")
    }
</script>