<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
</head>
<script type="text/javascript"
        src="<%=request.getContextPath() %>/pages/cosmos/lib/all/third/editor/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath() %>/pages/cosmos/lib/all/third/editor/kindeditor/lang/zh-CN.js"></script>

<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.2/css/bootstrap3/bootstrap-switch.min.css"
      rel="stylesheet">

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.2/js/bootstrap-switch.min.js"></script>

<body>
<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp" %>
<center>
    <div style="color:white;width:100%;height:100px;background-color:rgb(29,41,57);padding-top:20px">
        <span style="font-size:30px;">线程池修改</span>
    </div>
</center>
<form id="id_form_save"
      action="${pageContext.request.contextPath}/ibm/sys/resource/cloud/event/edit.dm"
      method="post">
    <input style="width:100px;height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white;margin: 20px;"
           onclick="back();" class="btn"
           type="button" value="返回列表">
    <table style="width: 80%;margin: 50px auto;text-align: center;" class="table  table-bordered table-hover"
           border="1">
        <tr>
            <td align="right" width="45%">事件名称</td>
            <td align="left">
                <input id="id_input$name" readonly="readonly" placeholder="请输入事件名称" type="text"
                       value="<%=request.getAttribute("name")%>">
                <span id="id_span$name"></span>
            </td>
        </tr>
        <tr>
            <td align="right">事件编码</td>
            <td align="left"><input id="id_input$code" readonly="readonly" placeholder="请输入事件编码" type="text"
                                    name="code" value="<%=request.getAttribute("code")%>">
                <span id="id_span$code"></span>
            </td>
        </tr>
        <tr>
            <td align="right">事件池容量</td>
            <td align="left"><input id="id_input$size" placeholder="请输入事件池容量" type="text" name="size"
                                    value="<%=request.getAttribute("size")%>">
                <span id="id_span$size"></span>
            </td>
        </tr>
        <tr>
            <td align="right">事件状态</td>
            <td align="left">
                <label style="padding-right: 10px"> 开启 <input type="radio"   name="state"
                        <% Object state = request.getAttribute("state");
                            if (Boolean.parseBoolean(state.toString())) {
                                out.println("checked");
                            }
                        %> value="true"/></label>
                <label> 关闭 <input type="radio" name="state"
                        <% state = request.getAttribute("state");
                            if (!Boolean.parseBoolean(state.toString())) {
                                out.println("checked");
                            }
                        %> value="false"/></label>
                <span id="id_span$state"></span>
            </td>
        </tr>
        <tr>
            <td align="right">事件消息</td>
            <td align="left"><input id="id_input$message" readonly="readonly" placeholder="请输入事件消息" type="text"
                                    value="<%=request.getAttribute("message")%>">
                <span id="id_span$message"></span>
            </td>
        </tr>

        <tr>
            <td colspan="3">
                <input style="width:300px;height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white"
                       onclick='save();' class="btn" type="button" value="编辑">
            </td>
        </tr>
    </table>
</form>
</body>
<!-- 加载js -->
<script type="text/javascript">

    /**
     *
     返回
     */
    function back() {
        history.go(-1);
    }

    /**
     *
     检查
     */
    function check() {
        var a = "<font color=red>自定义信息</font>";
        a = null;
        var flag = null;
        var return_flag = true;
        flag = ayCheckColumn("事件池容量", "id_span$size", "id_input$size", "VARCHAR", "no", "10", "0", "0", a, true);
        if (flag) {
        } else {
            return_flag = false;
        }
        return return_flag;
    }

    /**
     *
     保存
     */
    function save() {
        var flag = check();
        if (flag) {
        } else {
            return false;
        }
        //提交
        var obj_form = document.getElementById('id_form_save');
        //	editor.sync();
        obj_form.submit();
    }
</script>
<script type="text/javascript">
    //初始化日期
</script>
</html>