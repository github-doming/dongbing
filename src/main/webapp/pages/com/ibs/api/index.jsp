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
            <th>错误码说明</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="5">登录页面</td>
        </tr>
        <tr>
            <td>
                <p>1.得到session</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/pc/user/session');"
                    > http://IP:PORT/PROJECT/ibs/pc/user/session</a>
                </p>
            </td>
            <td>
            </td>
            <td>
                <p> session：sessionId</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.获取验证码(图片)</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/pc/user/verifyImage?json={session:\'8E8333DC0C064B59B30A1EE6F6B8672A\'}');"
                    > http://IP:PORT/PROJECT/ibs/pc/user/verifyImage</a>
                </p>
            </td>
            <td>
                <p> session：sessionId</p>
            </td>
            <td>
                <p> 验证码图片:只可用一次</p>
            </td>
            <td>
                <p> app400Session:Session参数有错</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.注册</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/pc/user/register?json={session:\'8E8333DC0C064B59B30A1EE6F6B8672A\',code:\'9168\''+
                               ',name:\'doming\',password:\'doming\'}');"
                    > http://IP:PORT/PROJECT/ibs/pc/user/register</a>
                </p>
            </td>
            <td>
                <p> session：sessionId</p>
                <p> code:验证码</p>
                <p> name：用户名称</p>
                <p> password：用户密码</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> app409RegisterMatcher:用户名或密码验证失败</p>
                <p> app400VerifyCode:验证码输入有错</p>
                <p> app409Register:账户已存在</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.登录</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/pc/user/login?json={session:\'B3CB14300CC24FE2929D2F100E053ADB\',code:\'8568\''+
                               ',name:\'doming\',password:\'doming\'}');"
                    > http://IP:PORT/PROJECT/ibs/pc/user/login</a>
                </p>
            </td>
            <td>
                <p> session：sessionId</p>
                <p> code:验证码</p>
                <p> name：用户名称</p>
                <p> password：用户密码</p>
            </td>
            <td>
                <p> 登录结果</p>
                <p> token：登录令牌</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> app400VerifyCode:验证码输入有错</p>
                <p> app400Login:用户名或密码输入有错</p>
                <p> app404LoginDisable:用户被禁用</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.修改密码</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/pc/user/updatePassword?json={token:\'55ac83ce935c4d518d9c7b9a912a91df\',data:{'+
                               'oldPassword:\'doming\',newPassword:\'doming1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/pc/user/updatePassword</a>
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
            <td>
                <p> 401Data:没有找到数据</p>
                <p> app409RegisterMatcher:用户名或密码验证失败</p>
                <p> 403Error:请求获取错误</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.登出</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/pc/user/logout?json={token:\'55ac83ce935c4d518d9c7b9a912a91df\'}');"
                    > http://IP:PORT/PROJECT/ibs/pc/user/logout</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> isHosting：是否托管，可为空</p>
            </td>
            <td>
                <p>登出结果</p>
            </td>
            <td>
                <p> app401Token:token有错,</p>
                <p> 找不到登录用户(当前请求需要用户验证)</p>
            </td>
        </tr>
        <tr>
            <td colspan="5">首页接口API：/home.jsp</td>
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
