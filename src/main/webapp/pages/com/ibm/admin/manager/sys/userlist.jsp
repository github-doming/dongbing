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
                <th style="width: 150px;text-align: center;">用户名</th>
                <th style="width: 100px;text-align: center;">昵称</th>
                <th style="width: 150px;text-align: center;">用户等级</th>
                <th style="width: 150px;text-align: center;">最近登录时间</th>

            </tr>
            </thead>
            <tbody class="tables">
            <c:forEach items="${userInfos}" var="userInfo" varStatus="loop">
                <tr>
                    <td class="text-center">${userInfo.APP_USER_NAME_}</td>
                    <td class="text-center">${userInfo.NICKNAME_}</td>
                    <td class="text-center">${userInfo.APP_USER_TYPE_}</td>
                    <td class="text-center">${userInfo.UPDATE_TIME_}</td>

                </tr>
            </c:forEach>
            </tbody>


        </table>
        <div class="modal-footer">
            <button style="margin-left: 2.2%;"  class="btn btn-browm" onclick="del('expires')">默认清理</button>

        </div>
    </div>
</div>



<script>
    function del(type) {
        if (confirm("将清空60天内未登录的所有用户信息！！！")) {
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
