<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title></title>
</head>

<body>

<form class="handicapForm" action="${pageContext.request.contextPath}/ibm/admin/manage/handicap/update">
    <div class="col-md-6 col-md-offset-3" style="width: 50%; margin-top: 50px;">
        <div class="input-group">
            <span class="input-group-addon">盘口名称</span>
            <c:choose>
                <c:when test="${not empty handicaps}">
                    <input type="text" class="form-control browsers" name="HANDICAP_NAME_" list="handicap"
                           value="${handicapInfo.getHandicapName()}" onchange="selectHandicap(this)">
                    <datalist id="handicap">
                        <c:forEach items="${handicaps}" var="handicap">
                            <option data="${handicap.code}" value="${handicap.name}"></option>
                        </c:forEach>
                    </datalist>
                </c:when>
                <c:otherwise>
                    <input type="text" class="form-control" name="HANDICAP_NAME_"
                           value="${handicapInfo.getHandicapName()}">
                </c:otherwise>
            </c:choose>
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon">盘口编码</span>
            <input type="text" class="form-control" id="handicapCode" name="HANDICAP_CODE_"
                   value="${handicapInfo.getHandicapCode()}" readonly>
        </div>
        <br>


        <div class="input-group">
            <span class="input-group-addon">盘口说明</span>
            <input type="text" class="form-control" name="HANDICAP_EXPLAIN_"
                   value="${handicapInfo.getHandicapExplain()}" placeholder="此输入盘口的说明">
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon">盘口类别</span>
            <select class="form-control" name="HANDICAP_CATEGORY_">
                <c:forEach items="${categories}" var="category">
                    <option value="${category.code}"
                            <c:if test="${ category.code eq handicapInfo.getHandicapCategory()}"> selected="selected"</c:if> >${category.name}</option>
                </c:forEach>
            </select>
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon" id="basic-addon5">盘口类型</span>
            <select class="form-control" name="HANDICAP_TYPE_">
                <c:forEach items="${types}" var="type">
                    <option value="${type.code}"
                            <c:if test="${ type.code eq handicapInfo.getHandicapType()}"> selected="selected"</c:if> >${type.name}</option>
                </c:forEach>
            </select>
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon">盘口价值</span>
            <input type="number" class="form-control" name="HANDICAP_WORTH_" value="${handicapInfo.getHandicapWorth()}"
                   placeholder="请输入数字">
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon">次序　　</span>
            <input type="number" oninput="if(value.length>2)value=value.slice(0,2)"
                   placeholder="请输入不超过两位的数字" autocomplete="off" class="form-control" name="SN_"
                   value="${handicapInfo.getSn()}">
        </div>
        <br>
        <div class="col-md-offset-4">
            <input type="hidden" name="HANDICAP_ID_" value=${handicapInfo.getIbmHandicapId()}>
            <button type="button" class="btn btn-primary"
                    onclick="submitFrom()">提交
            </button>
            <button type="button" class="btn btn-default col-md-offset-2" onclick="back()">返回</button>
        </div>
    </div>
</form>
</body>

<script>


    function submitFrom() {
        var _from = $(".handicapForm");
        $.post(_from.attr("action"), _from.serialize(), function (data) {
            if (data.success) {
                back();
            } else {
                alert(data.msg)
            }
        }, "json");
    }

    function back() {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/handicap/list";
    }


    function selectHandicap(_this) {
        var obj = _this.value;
        $("#handicap option").each(function () {
            if (this.value === obj) {
                $("#handicapCode").val($(this).attr("data"));
            }
        });
    }

</script>
</html>