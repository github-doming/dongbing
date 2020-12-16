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
        <form class="form-inline head-form"
              action="${pageContext.request.contextPath}/ibm/admin/manage/handicap/list"
              style="padding:  15px 0 15px 0">
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">模糊搜索:</span>
                    <input type="text" class="form-control" id="in-se" placeholder="筛选关键词" style="width: 120px">
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
                <th style="width: 150px;text-align: center;">表格名称</th>
                <th style="width: 100px;text-align: center;">数据总条数</th>
                <th style="width: 150px;text-align: center;">最近7天的数据条数</th>
                <th style="width: 150px;text-align: center;">最早数据的时间</th>
<%--                <th style="width: 150px;text-align: center;">最早数据的时间</th>--%>

            </tr>
            </thead>
            <tbody class="tables">
            <c:forEach items="${tableInfos}" var="tableInfo" varStatus="loop">
                <tr>
                    <td class="text-center">${tableInfo.tableName}</td>
                    <td class="text-center">${tableInfo.total}</td>
                    <td class="text-center">${tableInfo.abortTotal}</td>
                    <td class="text-center">${tableInfo.createTime}</td>
<%--                    <td class="text-center">${tableInfo.dates}</td>--%>

                </tr>
            </c:forEach>
            </tbody>


        </table>
        <div class="modal-footer">
            <button style="margin-left: 2.2%;"  class="btn btn-browm" onclick="del('bet')">默认清理</button>

        </div>
    </div>
</div>



<script>
    function del(type) {
        if (confirm("将清空7天以前的所有投注数据！！！")) {
            $.get("${pageContext.request.contextPath}/ibm/admin/manage/sys/cleanData", {"type": type}, function (data) {
                if (data.success) {
                    submitFrom();
                } else {
                    alert(data.msg);
                }
            }, 'json');
        }
        window.location.reload();
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
