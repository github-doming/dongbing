<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title></title>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
</head>


<body>
<div style="display: none;" id="id_div_msg">正在提交中...</div>

<div class="head">
    <div class="col-xs-10 col-xs-offset-1">
        <form class="form-inline head-form" style="padding:  15px 0 15px 0">
            <div class="form-group">
                <div class="input-group">
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-brown" onclick="back('${requestScope.type}')">返回</button>
                        </span>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="content">
    <div class="col-xs-10 col-xs-offset-1">
        <table align="left" border="0" class="table" contenteditable="true">
            <thead>
            </thead>
            <tbody>
            <tr>
                <td colspan="2" style="width: 60px; background-color: rgb(245, 245, 245); border-color: rgb(245, 245, 245);">反馈编码：${requestScope.code }</td>
                <td colspan="2" style="width: 85%; white-space: nowrap; text-align: left; vertical-align: middle; background-color: rgb(245, 245, 245); border-color: rgb(245, 245, 245);">反馈时间：${requestScope.time }</td>
            </tr>
            <tr>
                <td colspan="4" style="background-color: rgb(245, 245, 245); border-color: rgb(245, 245, 245);">反馈标题：${requestScope.title }</td>
            </tr>
            <tr>
                <td colspan="4" style="background-color: rgb(245, 245, 245); border-color: rgb(245, 245, 245);">反馈内容：${requestScope.info }</td>
            </tr>
            </tbody>
        </table>


        <form class="updatefb" id="updatefb"
              action="${pageContext.request.contextPath}/ibm/admin/manage/feedback/updatemsg">

            <br>
            <div class="input-group">
                <span>处理状态：</span>

                <select name="state" class="state" style="width: 335px; margin-top:20px;  height: 32px; border-radius: 1px;" >
                    <option value="DEFAULT">请选择状态</option>
                    <option value="OPEN">已处理</option>
                    <option value="BEGIN">处理中</option>
                </select>
            </div>
            <br>
            <div class="input-group">
                <span style="float:left;text-align:center;">反馈处理：</span>
                <textarea cols="60" rows="4" class="resultMsg" name="resultMsg"  placeholder="请输入处理结果"></textarea>
            </div>
            <br>

            <input type="hidden" name="pk" class="pk" value="${requestScope.pk }" >
            <input type="hidden" name="code" class="code" value="${requestScope.code }" >
            <div class="form-group">
                <p>
                      <button class="btn btn-mini btn-primary" type="button"  onclick="submitFrom('${requestScope.type}')" >  提交  </button>
                      <button class="btn btn-mini btn-default" type="reset">重置</button>
                </p>

            </div>
        </form>
    </div>


</div>
</div>


<script>


    function back(type) {
        console.log(type,"type")
        if("BUG" === type ){
            window.location.href = "${pageContext.request.contextPath}/pages/com/ibm/admin/manager/feedback/feedback_list_bug.jsp";
        }
        if("USERS" === type){
            window.location.href = "${pageContext.request.contextPath}/pages/com/ibm/admin/manager/feedback/feedback_list_user.jsp";
        }

    }

    function queryFeedbackDetails(code) {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/feedback/queryFeedbackCode?code=" + code;
    }

    function submitFrom(type) {
        var form = $(".updatefb");
        $.post(form.attr("action"), form.serialize(), function (data) {
            if (data.success) {
                console.log(type,"type")
                if("BUG" === type ){
                    window.location.href = "${pageContext.request.contextPath}/pages/com/ibm/admin/manager/feedback/feedback_list_bug.jsp";
                }
                if("USERS" === type){
                    window.location.href = "${pageContext.request.contextPath}/pages/com/ibm/admin/manager/feedback/feedback_list_user.jsp";
                }
                <%--window.location.href = "${pageContext.request.contextPath}/pages/com/ibm/admin/manager/feedback/feedback_list_bug.jsp";--%>
            } else {
                alert(data.msg);
            }
        }, "json");
    }


</script>



</body>
</html>
