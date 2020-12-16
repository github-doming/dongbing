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
      action="${pageContext.request.contextPath}/ibm/admin/manage/client/handicapCapacitySet1"
      method="post">
    <table class="table  table-bordered table-hover" border="1">
        <tr>
            <td colspan="3"><input onclick="back('${requestScope.CLIENT_CODE_}');" class="btn"
                                   type="button" value="返回列表"/>
                <input onclick='update();' class="btn" type="button" value="编辑">
            </td>
        </tr>
        <tr>
            <td align="right">客户端编码:
            </td>
            <td><input id="id_input$clientCode" type="text"
                       name="clientCode" value="${requestScope.CLIENT_CODE_ }" readonly>
            </td>
            <td><span id="id_span$clientCode"></span>
            </td>
        </tr>
        <tr>
            <td align="right">盘口编码:
            </td>
            <td><input id="id_input$handicapCode"
                       type="text"
                       name="handicapCode"
                       value="${requestScope.HANDICAP_CODE_ }" readonly>
            </td>
            <td><span id="id_span$handicapCode"></span>
            </td>
        </tr>

        <tr>
            <td align="right">盘口最大容量:
            </td>
            <td><input id="id_input$capacityHandicapMax"
                       placeholder="请输入容量" type="text"
                       name="capacityHandicapMax" value="${requestScope.CAPACITY_HANDICAP_MAX_ }">
            </td>
            <td><span id="id_span$capacityHandicapMax"></span>
            </td>
        </tr>
        <tr>
            <td align="right">代理最大容量:
            </td>
            <td><input id="id_input$capacityHaMax"
                       placeholder="请输入容量" type="text"
                       name="capacityHaMax"
                       value="${requestScope.CAPACITY_HA_MAX_ }">
            </td>
            <td><span id="id_span$capacityHaMax"></span>
            </td>
        </tr>

        <tr>
            <td align="right">会员最大容量:
            </td>
            <td><input id="id_input$capacityHmMax"
                       placeholder="请输入容量" type="text"
                       name="capacityHmMax"
                       value="${requestScope.CAPACITY_HM_MAX_ }">
            </td>
            <td><span id="id_span$capacityHmMax"></span>
            </td>
        </tr>
        <tr>
            <td align="center" colspan="3">
                <input type="hidden" name="handicapCapacityId"
                       value="${requestScope.IBM_CLIENT_HANDICAP_CAPACITY_ID_ }">
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
    function back(clientCode) {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/handicapCapacity1?clientCode=" + clientCode;
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
                document.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/handicapCapacity1?clientCode=" + data.data;
            } else {
                alert(data.msg);
            }
        }, "json")
    }
</script>