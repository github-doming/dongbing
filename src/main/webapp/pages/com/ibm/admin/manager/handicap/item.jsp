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
              action="${pageContext.request.contextPath}/ibm/admin/manage/handicap/item/list"
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
                <div class="form-group" style="padding:0 15px 0 15px">
                    <label for="se-ca">类别: </label>
                    <select name="HANDICAP_CATEGORY_" onchange="submitFrom()" id="se-ca">
                        <option value=""  <c:if test="${empty HANDICAP_CATEGORY_ }"> selected="selected"</c:if>>全部
                        </option>
                        <c:forEach items="${categories}" var="category" varStatus="loop">
                            <option value="${category.code}"
                                    <c:if test="${ category.code eq HANDICAP_CATEGORY_}"> selected="selected"</c:if> >${category.name}</option>
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
                <th class="text-center" style="width: 150px;">盘口名称</th>
                <th class="text-center">盘口地址</th>
                <th class="text-center" style="width: 150px;">盘口校验码</th>
                <th class="text-center" style="width: 80px;">盘口类别</th>
                <th class="text-center" style="width: 80px;">引用次数</th>
                <th class="text-center" style="width: 150px;">操作</th>

            </tr>
            </thead>
            <tbody class="handicap-item">
            <c:forEach items="${handicapItems}" var="handicapItem" varStatus="loop">
                <tr>
                    <td class="text-center">${handicapItem.HANDICAP_NAME_}</td>
                    <td class="text-center">${handicapItem.HANDICAP_URL_}</td>
                    <td class="text-center">${handicapItem.HANDICAP_CAPTCHA_}</td>
                    <td class="text-center">${handicapItem.HANDICAP_CATEGORY_}</td>
                    <td class="text-center">${handicapItem.CITATIONS_NUMBER_}</td>
                    <td style="padding: 4px;text-align: center;">
                        <div class="btn-group">
                            <button class="btn btn-sm btn-danger"
                                    onclick="del('${handicapItem.HANDICAP_ITEM_ID_}',${handicapItem.CITATIONS_NUMBER_})">
                                删除
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

    function del(handicapItemId, citationsNumber) {
        var msg;
        if (citationsNumber > 0) {
            msg = '该盘口详情还在被用户所引用，确认删除？';
        } else {
            msg = '确认删除该盘口详情的所有信息？';
        }
        if (confirm(msg)) {
            $.post("${pageContext.request.contextPath}/ibm/admin/manage/handicap/item/delete", {"handicapItemId": handicapItemId}, function (data) {
                if (data.success) {
                    var _from = $(".head-form");
                    _from.submit();
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
        var searchTr = $("tbody.handicap-item tr").filter(":contains('" + txt + "')");
        if (searchTr) {
            $("tbody.handicap-item tr").hide(); //全部隐藏
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
