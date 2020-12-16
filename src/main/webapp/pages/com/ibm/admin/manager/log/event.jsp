<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html; charset=UTF-8"
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
        <form class="form-inline head-form"
              action="${pageContext.request.contextPath}/ibm/admin/manage/log/event" style="padding:  15px 0 15px 0">
            <div class="form-group" style="float: right">
                <div class="input-group">
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-brown" onclick="submitFrom()">查询</button>
                        </span>
                    <input type="text" name="key" value="${key}" class="form-control" placeholder="查询关键词"
                           style="width:181px;vertical-align:middle;">
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
                <th style="width: 100px;text-align: center;">线程名称</th>
                <th class='text-center'>事件编码</th>
                <th class='text-center'>错误内容</th>
                <th style="width: 200px;text-align: center;">操作时间</th>
            </tr>
            </thead>
            <tbody class="handicap">
            <c:forEach items="${list}" var="list" varStatus="loop">
                <tr>
                    <td class="text-center">${list.THREAD_NAME_}</td>
                    <td class="text-center">${list.EVENT_CODE_}</td>
                    <td class="text-center">${list.ERROR_CONTEXT_}</td>
                    <td class="text-center">${list.CREATE_TIME_}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    /**
     *
     请求更新
     */
    function updateRecord(clientId) {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/clientCapacityForm?clientId=" + clientId;
    }
    /**
     *
     停止心跳
     */
    function stopHeartbeat(clientId) {
        if (confirm("确定停止该客户机心跳？？？")) {
            $.get("${pageContext.request.contextPath}/ibm/admin/manage/client/stopHeartbeat", {"clientId": clientId}, function (data) {
                if (data.success) {
                    submitFrom();
                } else {
                    alert(data.msg);
                }
            }, 'json');
        }
        window.location.reload();
    }
    /**
     *
     查询
     */
    function find(clientId) {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/handicapCapacity?clientId=" + clientId;
    }
    function submitFrom() {
        var _from = $(".head-form");
        _from.submit();
    }

    function del(clientId) {
        if (confirm("将停用该客户机所有盘口信息！！！")) {
            $.post("${pageContext.request.contextPath}/ibm/admin/manage/handicap/delete", {"handicapId": handicapId}, function (data) {
                if (data.success) {
                    submitFrom();
                } else {
                    alert(data.msg);
                }
            }, 'json');
        }
    }

    /**
     *
     查询
     */
    function migrateFrom() {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/handicapMigrateForm";
    }

    /**
     * 切换客户机
     */
    function replaceFrom() {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/clientReplaceForm";
    }


</script>
<script>
    // 模糊搜索
    $('#in-se').keyup(function () {
        var txt = $('#in-se').val();
        var reg = /编辑|删除/;
        //过滤掉不包含txt的数据项
        var searchTr = $("tbody.handicap tr").filter(":contains('" + txt + "')");
        if (searchTr) {
            $("tbody.handicap tr").hide(); //全部隐藏
            if (searchTr.length && !reg.test(txt)) {
                searchTr.show();
            }
        }
    });

    $(function () {
        $('div.form-group select').selectpicker({
            width: 80
        });
    });

</script>
</body>
</html>
