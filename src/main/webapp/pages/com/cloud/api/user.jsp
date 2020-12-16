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
            <td colspan="4">PC端接口</td>
        </tr>
        <tr>
            <td>
                <p>1.得到session</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/user/pc/session');"
                    > http://IP:PORT/PROJECT/cloud/user/pc/session</a>
                </p>
            </td>
            <td>
            </td>
            <td>
                <p> session：sessionId</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.获取验证码(图片)</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/user/pc/verifyImage?json={session:\'71E56BD4958446E2AC6496A89DE32DB1\'}');"
                    > http://IP:PORT/PROJECT/cloud/user/pc/verifyImage</a>
                </p>
            </td>
            <td>
                <p> session：sessionId</p>
            </td>
            <td>
                <p> 验证码图片</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.注册</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/user/pc/register?json={session:\'71E56BD4958446E2AC6496A89DE32DB1\',code:\'3931\''+
                               ',name:\'doming\',password:\'doming1\'}');"
                    > http://IP:PORT/PROJECT/cloud/user/pc/register</a>
                </p>
            </td>
            <td>
                <p> session：sessionId</p>
                <p> code:验证码</p>
                <p> name：用户名称</p>
                <p> password：用户密码</p>
            </td>
            <td>
                <p> 验证码图片</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.登录</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/user/pc/login?json={session:\'B3E96029FF824246B71229AB837F5C92\',code:\'3931\''+
                               ',name:\'doming\',password:\'doming1\'}');"
                    > http://IP:PORT/PROJECT/cloud/user/pc/user/login</a>
                </p>
            </td>
            <td>
                <p> session：sessionId</p>
                <p> code:验证码</p>
                <p> name：用户名称</p>
                <p> password：用户密码</p>
            </td>
            <td>
                <p> 登录接口</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.修改密码</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/user/pc/updatePassword?json={token:\'70BF422B560D4046B78A1B9BC150D973\',data:{'+
                               'oldPassword:\'doming1\',newPassword:\'doming2\'}}');"
                    > http://IP:PORT/PROJECT/cloud/user/pc/updatePassword</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> oldPassword：旧密码</p>
                <p> newPassword：新密码</p>
            </td>
            <td>
                <p>修改结果</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.登出</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/user/pc/logout?json={token:\'70BF422B560D4046B78A1B9BC150D973\'}');"
                    > http://IP:PORT/PROJECT/cloud/user/pc/logout</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
            </td>
            <td>
                <p>登出结果</p>
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
