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
              action="${pageContext.request.contextPath}/ibm/admin/manage/client/handicapCapacity1"
              style="padding:  15px 0 15px 0">
            <tr>
                <td colspan="3"><input onclick="back();" class="btn"
                                       type="button" value="返回列表"/>
                </td>
            </tr>

        </form>
    </div>
</div>
<div class="content">
    <div class="col-xs-10 col-xs-offset-1">
        <table class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th style="width: 150px;text-align: center;">操作</th>
                <th style="width: 150px;text-align: center;">客户端编码</th>
                <th style="width: 100px;text-align: center;">盘口编码</th>
                <th class='text-center'>盘口最大容量</th>
                <th class='text-center'>盘口使用容量</th>
                <th class='text-center'>代理最大容量</th>
                <th class='text-center'>代理使用容量</th>
                <th class='text-center'>会员最大容量</th>
                <th class='text-center'>会员使用容量</th>
            </tr>
            </thead>
            <tbody class="handicap">
            <c:forEach items="${list}" var="list" varStatus="loop">
                <tr>
                    <td style="padding: 4px;text-align: center;">
                        <div class="btn-group">
                            <button class="btn  btn btn-primar" onclick="updateRecord('${list.CLIENT_CODE_}','${list.HANDICAP_CODE_}')">编辑</button>
                        </div>
                    </td>
                    <td class="text-center">${list.CLIENT_CODE_}</td>
                    <td class="text-center">${list.HANDICAP_CODE_}</td>
                    <td class="text-center">${list.CAPACITY_HANDICAP_MAX_}</td>
                    <td class="text-center">${list.CAPACITY_HANDICAP_}</td>
                    <td class="text-center">${list.CAPACITY_HA_MAX_}</td>
                    <td class="text-center">${list.CAPACITY_HA_}</td>
                    <td class="text-center">${list.CAPACITY_HM_MAX_}</td>
                    <td class="text-center">${list.CAPACITY_HM_}</td>
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
    function updateRecord(clientCode,handicapCode) {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/handicapCapacityForm1?clientCode=" + clientCode+"&handicapCode="+handicapCode;
    }
    /**
     *
     返回
     */
    function back() {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/list1";
    }

</script>
</body>
</html>
