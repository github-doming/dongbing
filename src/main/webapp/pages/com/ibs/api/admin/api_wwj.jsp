<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title>接口测试页</title>
</head>
<body>
<div class="container-fluid">
    <h1>api接口说明</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>接口名称</th>
            <th>接口调用示例</th>
            <th>入参说明</th>
            <th>返参说明</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="4">IBS后台管理</td>
        </tr>

        <tr>
            <td>
                <p>1.新建卡类《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/addCardTree?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/addCardTree</a>
                </p>
                <p> 新建卡类</p>
            </td>
            <td>
                <p> cardName : 名称</p>
                <p> cardType : 类型</p> ADMIN_PARTNER_AGENT
                <p> cardPrice : 价格(数字)</p>
                <P> cardTreePoint:点数(数字) </P>
                <P> cardSn : 次序</P>
            </td>
            <td>
            </td>
        </tr>



        </tbody>
    </table>
</div>
</body>
<script type="text/javascript">
    function findUrl(url) {
        console.log(url);
        url = encodeURI(url);
        window.open(url);
        return url;
    }
</script>
</html>
