<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
<%--    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
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
    <div style="margin-top: 50px;">
<%--        <span style="font-size:30px;">线程池修改</span>--%>
    </div>
</center>

<form name="ibmhandicap" autocomplete="off"  action="/a/ibm/admin/hadicap/save2" method="post">
    <div style="width: 50%; margin-left: 25%;">
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">盘口名称</span>
            <input type="text" class="form-control browsers" name="HANDICAP_NAME_" aria-describedby="basic-addon1" list="browsers1" required="required">
            <datalist id="browsers1">
                <option value="泰源">
                <option value="益力多">
                <option value="大唐">
            </datalist>
        </div><br>

        <div class="input-group">
            <span class="input-group-addon" id="basic-addon2">盘口编码</span>
            <input type="text" class="form-control browsers" name="HANDICAP_CODE_" aria-describedby="basic-addon2" list="browsers2" required="required">
            <datalist id="browsers2">
                <option value="WS2">
                <option value="IDC">
                <option value="SGWIN">
            </datalist>
        </div><br>

        <div class="input-group">
            <span class="input-group-addon" id="basic-addon3">盘口说明</span>
            <div class="form-control" aria-describedby="basic-addon3"></div>
            <textarea name="HANDICAP_EXPLAIN_" class="form-control" aria-describedby="basic-addon3" rows="4" required="required"></textarea>
        </div><br>

        <div class="input-group">
            <span class="input-group-addon" id="basic-addon4">盘口类别</span>
            <input type="text" class="form-control browsers" name="HANDICAP_CATEGORY_" aria-describedby="basic-addon4" list="browsers4">
            <datalist id="browsers4">
                <option value="MEMBER">会员</option>
                <option value="AGENT">代理</option>
            </datalist>
        </div><br>

        <div class="input-group">
            <span class="input-group-addon" id="basic-addon5">盘口类型</span>
            <input type="text" class="form-control browsers" name="HANDICAP_TYPE_" aria-describedby="basic-addon5" list="browsers5" required="required">
            <datalist id="browsers5">
                <option value="FREE">免费</option>
                <option value="CHARGE">收费</option>
            </datalist>
        </div><br>

        <div class="input-group">
            <span class="input-group-addon" id="basic-addon6">盘口价值</span>
            <input type="text" class="form-control" name="HANDICAP_WORTH_T_" aria-describedby="basic-addon6" required="required">
        </div><br>

        <div class="input-group">
            <span class="input-group-addon" id="basic-addon7">盘口版本</span>
            <input type="text" class="form-control" name="HANDICAP_VERSION_" aria-describedby="basic-addon7" required="required">
<%--            <input type="text" name="IBM_HANDICAP_ID_"  value=${HANDICAP_VERSION_}>--%>
        </div><br>


        <div class="input-group">
            <span class="input-group-addon" id="basic-addon8">创建者　</span>
            <input type="text" class="form-control" name="CREATE_USER_" aria-describedby="basic-addon8" required="required">
        </div><br>

        <div class="input-group">
            <span class="input-group-addon" id="basic-addon9">描述　　</span>
            <textarea name="DESC_" class="form-control" aria-describedby="basic-addon9" rows="4" required="required"></textarea>
        </div><br>

    </div><br>

    <div style="margin-left: 45%;">

        <div style="margin: 20px; float: left;">
            <input type="submit" style="padding: 10px 20px 10px 20px;" value="提交" />
        </div>
        <div  style="margin: 20px; float: left;">
            <input type="button" name="Submit" style="padding: 10px 20px 10px 20px;"
                   onclick="back()" value="返回">
        </div>

    </div>

</form>
</body>

<script type="text/javascript">
    function back() {
        window.location.href="/a/ibm/admin/hadicap/list";
    }
</script>
</html>