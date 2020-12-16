<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title></title>
</head>

<body>

<form class="userInfoForm" action="${pageContext.request.contextPath}/ibm/admin/manage/handicap/user/update">

    <div class="col-md-6 col-md-offset-3" style="width: 50%; margin-top: 50px;">
        <h3 class="red-text text-center" >修改《${userInfo.APP_USER_NAME_}》账户信息</h3>
        <div class="input-group">
            <span class="input-group-addon">会员名称</span>
            <input type="text" class="form-control" id="appUserName" name="APP_USER_NAME_"
                   value="${userInfo.APP_USER_NAME_}" readonly>
          </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon">用户昵称</span>
            <input type="text" class="form-control" id="nickName" name="NICKNAME_"
                   value="${userInfo.NICKNAME_}" readonly>
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon">代理盘口</span>
            <input type="text" class="form-control" name="HAVE_AGENT_HANDICAP_" value="${userInfo.AGENT_HANDICAP_}" readonly >
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon">会员盘口</span>
            <input type="text" class="form-control" name="HAVE_MEMBER_HANDICAP_" value="${userInfo.MEMBER_HANDICAP_}" readonly >
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon">剩余点数</span>
            <input type="text" class="form-control" name="USEABLE_POINT_"
                   value="${userInfo.USEABLE_POINT_}" placeholder="输入新值进行修改">
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon" id="basic-addon5">用户类型</span>
            <select class="form-control" name="USER_TYPE_">
                <c:forEach items="${types}" var="type">
                    <option value="${type.code}"
                            <c:if test="${ type.code eq userInfo.APP_USER_TYPE_}"> selected="selected"</c:if> >${type.name}</option>
                </c:forEach>
            </select>

        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">到期时间</span>
            <div class="input-group date form_datetime col-md-5" >
                <input class="form-control" size="16" type="text" name="END_TIME_" value="${userInfo.END_TIME_}" readonly>
                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
        </div>
        <br>

        <div class="col-md-offset-4">
            <input type="hidden" name="APP_USER_ID_" value=${userInfo.APP_USER_ID_}>
            <button type="button" class="btn btn-primary"
                    onclick="submitFrom()">提交
            </button>
            <button type="button" class="btn btn-default col-md-offset-2" onclick="back()">返回</button>
        </div>
    </div>
</form>
</body>

<script>
    $.fn.datetimepicker.dates['zh-CN'] = {
        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
        daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
        daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
        months: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
        monthsShort: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
        today: "今日",
        suffix: [],
        meridiem: []
    };

    $('.form_datetime').datetimepicker({
        format: 'yyyy-MM-dd hh:ii',
        todayBtn:  1, //
        startDate: new Date(),
        todayHighlight: true,
        autoclose: true, //当选择一个日期之后是否立即关闭此日期时间选择器
        language:'zh-CN'
    });

function submitFrom() {
    if (confirm("是否确定提交当前修改的内容！！！")) {
        var _from = $(".userInfoForm");
        $.post(_from.attr("action"), _from.serialize(), function (data) {
            if (data.success) {
                back();
            } else {
                alert(data.msg)
            }
        }, "json");
    }
}

function back() {
    window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/user/list";
    }

</script>
</html>