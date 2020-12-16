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
<div class="page-head">
    <div class="col-xs-10 col-xs-offset-1">
        <form class="form-inline head-form"
              action="${pageContext.request.contextPath}/ibm/admin/manage/handicap/agent/list"
              style="padding:  15px 0 15px 0">
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">模糊搜索:</span>
                    <input type="text" class="form-control" id="in-se" placeholder="筛选关键词" style="width: 120px">
                </div>
            </div>
            <div style="float: right">
                <div class="form-group">
                    <label for="se-ha">盘口: </label>
                    <select name="HANDICAP_CODE_" onchange="submitFrom()" id="se-ha" style="width: 50px">
                        <option value=""  <c:if test="${empty HANDICAP_CODE_ }"> selected="selected"</c:if>>全部</option>
                        <c:forEach items="${handicaps}" var="handicap" varStatus="loop">
                            <option value="${handicap.code}"
                                    <c:if test="${ handicap.code eq HANDICAP_CODE_}"> selected="selected"</c:if> >${handicap.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <input type="text" name="key" value="${key}" class="form-control" placeholder="查询关键词"
                               style="width: 120px;">
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-brown" onclick="submitFrom()">查询</button>
                        </span>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>


<div class="content">
    <div class="col-xs-10 col-xs-offset-1">
        <table class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th class="text-center" style="width: 100px;">代理账号</th>
                <th class="text-center" style="width: 100px;">用户名称</th>
                <th class="text-center" style="width: 100px;">盘口名称</th>
<%--                <th class="text-center" style="width: 80px;">最大在线</th>--%>
<%--                <th class="text-center" >在线IDS</th>--%>
                <th class="text-center" style="width: 100px;">在线状态</th>
                <th class="text-center" style="width: 150px;">操作</th>

            </tr>
            </thead>
            <tbody class="handicap-agent">
            <c:forEach items="${handicapAgents}" var="handicapAgent" varStatus="loop">
                <tr>
                    <td class="text-center">${handicapAgent.AGENT_ACCOUNT_}</td>
                    <td class="text-center">${handicapAgent.APP_USER_NAME_}</td>
                    <td class="text-center">${handicapAgent.HANDICAP_NAME_}</td>
<%--                    <td class="text-center">${handicapAgent.ONLINE_NUMBER_MAX_}</td>--%>
<%--                    <td class="text-center">${handicapAgent.ONLINE_IDS_}</td>--%>
                    <td class="text-center">${handicapAgent.STATE_}</td>
                    <td class="text-center">
                        <div class="btn-group">
                            <button class="btn btn-primary"
                                    onclick="logout('${handicapAgent.IBM_HANDICAP_AGENT_ID_}')">登出
                            </button>

                            <button class="btn btn-danger"
                                    onclick="del('${handicapAgent.IBM_HANDICAP_AGENT_ID_}')">删除
                            </button>
                        </div>
                    </td>
                </tr>
            </c:forEach>

            </tbody>

        </table>

    </div>
</div>

<script>
    function submitFrom() {
        var _from = $(".head-form");
        _from.submit();
    }

    function logout(handicapAgentId) {
        if (confirm('确认登出该盘口代理？')) {
            $.get("${pageContext.request.contextPath}/ibm/admin/manage/handicap/agent/logout", {
                "handicapAgentId": handicapAgentId
            }, function (data) {
                if (data.success) {
                    submitFrom();
                } else {
                    alert(data.msg);
                }
            }, 'json');
        }

    }

    function del(handicapAgentId) {
        if (confirm( '确认删除该盘口代理的所有信息？')) {
            $.get("${pageContext.request.contextPath}/ibm/admin/manage/handicap/agent/delete", {
                "handicapAgentId": handicapAgentId
            }, function (data) {
                if (data.success) {
                    submitFrom();
                } else {
                    alert(data.msg);
                }
            }, 'json');
        }

    }
</script>
<script>
    // 模糊搜索
    $('#in-se').keyup(function () {
        var txt = $('#in-se').val();
        var reg = /	编辑|删除/;
        //过滤掉不包含txt的数据项
        var searchTr = $("tbody.handicap-agent tr").filter(":contains('" + txt + "')");
        if (searchTr) {
            $("tbody.handicap-agent tr").hide(); //全部隐藏
            if (searchTr.length && !reg.test(txt)) {
                searchTr.show();
            }
        }
    });

    $(function () {
        $('select').selectpicker({
            width: 80
        });
    });

</script>
</body>
</html>
