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
    <h1>api接口说明(登陆端,周金桥)</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>接口名称</th>
            <th>进度</th>
            <th>接口定义</th>
            <th>接口调用示例</th>
            <th>参数说明</th>
            <th>返回说明</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>(改)得到session</td>
            <td></td>
            <td></td>
            <td><a
                    href="javascript:void(0);"
                    onClick="findUrl('${pageContext.request.contextPath}/ibm/app/user/token/t/session.do');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/app/user/token/t/session.do</a>
            </td>
            <td></td>
            <td></td>
        </tr>

        <tr>
            <td>(改)刷新验证码(图片)</td>
            <td></td>
            <td>{"session":"8947"}</td>
            <td><a
                    href="javascript:void(0);"
                    onClick="findUrl('${pageContext.request.contextPath}/ibm/app/user/token/verify/img.do?json={session:\'8edf5e875bc249bda6d8e8416792123b\'}');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/app/user/token/verify/img.do</a>
            </td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>(改)注册</td>
            <td></td>
            <td>{"session":"" ,"code":"1574","name": "cjx", "password":"cjx"}
            </td>
            <td><a
                    href="javascript:void(0);"
                    onClick="findUrl('${pageContext.request.contextPath}/ibm/app/user/token/register.do?json={session:\'8edf5e875bc249bda6d8e8416792123b\' ,code:\'1574\',name: \'cjx\',password:\'cjx\'}');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/app/user/token/register.do</a>
            </td>
            <td>
                <p>session：sessionId</p>
                <p>username：登陆用户名</p>
                <p>password:登陆密码</p>
                <p>code:验证码</p>
                <p>email:邮箱(可为空)</p>
            </td>
            <td></td>
        </tr>


        <tr>
            <td>(改)登录</td>
            <td></td>
            <td>{"session":" ","code":"1574","name": "cjx", "password":
                "cjx" }
            </td>
            <td><a
                    href="javascript:void(0);"
                    onClick="findUrl('${pageContext.request.contextPath}/ibm/app/user/token/login.do?json={session:\'8edf5e875bc249bda6d8e8416792123b\',code:\'1574\',name: \'cjx\',password:\'cjx\'}');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/app/user/token/login.do</a>
            </td>
            <td>
                <p>username：登陆用户名</p>
                <p>password:登陆密码</p>
                <p>code:验证码</p>
            </td>
            <td>
                <p>app400：用户名或密码出错</p>
            </td>
        </tr>






        <tr>

        </tr>
        <tr>
            <td>注册(不需要验证码)</td>
            <td>不需要做</td>
            <td>{"name": "cjx", "password": "cjx", }</td>
            <td><a
                    href="javascript:void(0);"
                    onClick="findUrl('${pageContext.request.contextPath}/ibm/all/app/token/register2.do?json={name: \'cjx\',password:\'cjx\'}');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/all/app/token/register2.do</a>
            </td>
            <td>
                <p>username：登陆用户名</p>
                <p>password:登陆密码</p>
            </td>
            <td></td>
        </tr>

        <tr>
            <td>验证密码</td>
            <td>安妮</td>
            <td>{ "token": "cjx","data":
                {"passwordOld": "当前密码" } }
            </td>
            <td><a
                    href="javascript:void(0);"
                    onClick="findUrl('${pageContext.request.contextPath}/ibm/all/app/account/password/verify.do?json={token: \'9ef7e7b06d6045bca8e09ee38299932a\',data: {passwordOld:\'1\'}}');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/all/app/account/password/verify.do</a>
            </td>
            <td>
                <p>token：app登陆令牌</p>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>通过appUserId查找用户</td>
            <td>敏婷</td>
            <td>{ "token": "cjx", "data": {appUserId:1 } }</td>
            <td><a
                    href="javascript:void(0);"
                    onClick="findUrl('${pageContext.request.contextPath}/ibm/all/app/user/form.do?json={token: \'adee0b83400546a5afa6e257b054c02f\',data: {appUserId:\'1\'}}');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/all/app/user/form.do</a>
            </td>
            <td>
                <p>token：app登陆令牌</p>
            </td>
            <td>
                <p>app401：token出错,找不到登录用户</p>
            </td>
        </tr>
        <tr>
            <td>通过token查找用户</td>
            <td>敏婷</td>
            <td>{ "token": "cjx", "data": { } }</td>
            <td><a
                    href="javascript:void(0);" ；


                    onClick="findUrl('${pageContext.request.contextPath}/ibm/all/app/token/form.do?json={token: \'b27fe7c21c594c8982e7dd1ff633afd3\',data: {}}');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/all/app/token/form.do</a>
            </td>
            <td>
                <p>token：app登陆令牌</p>
            </td>
            <td>
                <p>app401：token出错,找不到登录用户</p>
            </td>
        </tr>
        <tr>
            <td>通过token查找用户(tomcat8)</td>
            <td>敏婷</td>
            <td>{ "token": "cjx", "data": { } }</td>
            <td><a
                    href="javascript:void(0);"
                    onClick="findUrl('${pageContext.request.contextPath}/ibm/all/app/token/form.do?json={token: \'b27fe7c21c594c8982e7dd1ff633afd3\'}');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/all/app/token/form.do</a>
            </td>
            <td>
                <p>token：app登陆令牌</p>
            </td>
            <td>
                <p>app401：token出错,找不到登录用户</p>
            </td>
        </tr>

        <tr>
            <td>登录</td>
            <td>敏婷</td>
            <td>{"session":" ","code":"8947","name": "cjx", "password":
                "cjx" }
            </td>
            <td><a
                    href="javascript:void(0);"
                    onClick="findUrl('${pageContext.request.contextPath}/ibm/all/app/token/login.do?json={session:\'3556A8AA5CE551E986341E1589E5421A\',code:\'9980\',name: \'o1\',password:\'1\'}');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/all/app/token/login.do</a>
            </td>
            <td>
                <p>username：登陆用户名</p>
                <p>password:登陆密码</p>
                <p>code:验证码</p>
            </td>
            <td>
                <p>app400：用户名或密码出错</p>
            </td>
        </tr>
        <tr>
            <td>登录(不需要验证码)</td>
            <td>不需要做</td>
            <td>{"name": "cjx", "password": "cjx", }</td>
            <td><a
                    href="javascript:void(0);"
                    onClick="findUrl('${pageContext.request.contextPath}/ibm/all/app/token/login2.do?json={name: \'cjx\',password:\'cjx\'}');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/all/app/token/login2.do</a>
            </td>
            <td>
                <p>username：登陆用户名</p>
                <p>password:登陆密码</p>
            </td>
            <td>
                <p>app400：用户名或密码出错</p>
            </td>
        </tr>
        <tr>
            <td>游客登录</td>
            <td>敏婷</td>
            <td></td>
            <td><a
                    href="javascript:void(0);"
                    onClick="findUrl('${pageContext.request.contextPath}/ibm/all/app/token/loginGuest.do');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/all/app/token/loginGuest.do</a>
            </td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>注销登陆</td>
            <td>敏婷</td>
            <td>{ "token": "cjx", "data": { } }</td>
            <td><a
                    href="javascript:void(0);"
                    onClick="findUrl('${pageContext.request.contextPath}/ibm/all/app/token/logout.do?json={token: \'abc\'}');"
                    target="_blank">http://IP:PORT${pageContext.request.contextPath}/ibm/all/app/token/logout.do</a>
            </td>
            <td>
                <p>token：app登陆令牌</p>
            </td>
            <td>
                <p>app401：token出错,找不到登录用户</p>
            </td>
        </tr>


        </tbody>
    </table>
</div>
</body>
</html>
<script type="text/javascript">
    function findUrl(url) {
        url = encodeURI(url);
        window.open(url, "_blank");
        return url;
    }
</script>
<script type="text/javascript">
</script>
