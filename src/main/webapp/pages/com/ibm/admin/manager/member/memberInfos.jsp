<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title></title>
</head>
<body>
<button style="margin: 20px 0px 10px 2.5%;" class="btn btn-browm" onclick="chooseHandicap()">选择盘口</button>

<table id="topic_title" style="width:95%;table-layout:fixed; margin: 0px auto; text-align: center;" class="table  table-bordered table-hover">

    <thead>
        <tr>
            <th style="width: 150px;text-align: center;">用户名称</th>
            <th style="width: 150px;text-align: center;">盘口编码</th>
            <th style="width: 150px;text-align: center;">会员账号</th>
            <th style="width: 150px;text-align: center;">登陆状态</th>
            <th style="width: 150px;text-align: center;">客户端IP</th>
            <th style="width: 300px;text-align: center;">操作</th>
        </tr>
    </thead>
    <tbody class='body'>
        <c:forEach items="${requestScope.memberInfos}" var="memberInfo">
            <tr>
                <td id="name" style="width: 150px;">${memberInfo.APP_USER_NAME_}</td>
                <td id="code" style="width: 150px;">${memberInfo.HANDICAP_CODE_}</td>
                <td style="width: 150px;">${memberInfo.MEMBER_ACCOUNT_}</td>
                <td>
                <c:if test="${memberInfo.STATE_ eq 'LOGIN' }">登陆中</c:if>
                <c:if test="${memberInfo.STATE_ eq 'LOGOUT' }">未登录</c:if>
                </td>
                <td id="catecory" style="width: 150px;">${memberInfo.REGISTER_IP_}</td>
                <td style="width: 150px;">
                    <button class="btn btn-primary" onclick="edit('${memberInfo.CLIENT_ID_}',
                            '${memberInfo.CLIENT_CODE_}','${memberInfo.HANDICAP_MEMBER_ID_}')"> 编辑用户 </button>
                    <button class="btn btn-danger" onclick="removeUser('${memberInfo.HANDICAP_MEMBER_ID_}')"> 移除用户 </button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<script type="text/javascript">
    /**
     *
     删除记录
     */
    function removeUser(handicapMemberId) {
        if (confirm("确定要登出吗？")) {
            window.location.href =  "${pageContext.request.contextPath}/ibm/admin/manage/member/logout?handicapMemberId=" + handicapMemberId;
        }
        else {
        }
    }
</script>
</body>
</html>
