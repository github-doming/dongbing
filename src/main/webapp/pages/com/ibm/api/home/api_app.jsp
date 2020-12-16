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
            <td colspan="4">APP端接口</td>
        </tr>
        <tr>
            <td>
                <p>1.加载主题框架</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/home/main?json={token:\'aa83fe04ccdc40a485871da260bf0e02\'}');"
                    > http://IP:PORT/PROJECT/ibm/app/home/main</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> memberInfo：会员盘口信息</p>
                <p> agentInfo：代理盘口信息</p>
                <p> userInfo：用户信息</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.点击会员</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/home/clickMember?json={token:\'aa83fe04ccdc40a485871da260bf0e02\'}');"
                    > http://IP:PORT/PROJECT/ibm/app/home/clickMember</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> memberInfo：会员盘口信息</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.点击代理</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/home/clickAgent?json={token:\'aa83fe04ccdc40a485871da260bf0e02\'}');"
                    > http://IP:PORT/PROJECT/ibm/app/home/clickAgent</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> agentInfo：代理盘口信息</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.点击用户信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/home/clickUser?json={token:\'aa83fe04ccdc40a485871da260bf0e02\'}');"
                    > http://IP:PORT/PROJECT/ibm/app/home/clickUser</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> endTimeLong：软件到期时间</p>
                <p> cmsInfo：消息条数</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.盘口识别</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/handicap/itemDiscern?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/app/handicap/itemDiscern</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_CODE_：盘口编码(可为空)</p>
                <p> HANDICAP_URL_：盘口地址</p>
                <p> HANDICAP_CAPTCHA_：盘口验证码</p>
                <p> HANDICAP_CATEGORY_：盘口类别（MEMBER/AGENT）</p>
            </td>
            <td>
                <p> 盘口编码为空：识别盘口</p>
                <p> 盘口编码不为空：新增详情信息并返回结果</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> HANDICAP_NAME_：盘口名称</p>
                <p> HANDICAP_ITEM_ID_：盘口详情ID</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.盘口验证码修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/handicap/itemEdit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\'}}');"
                    > http://IP:PORT/PROJECT/ibm/app/handicap/itemEdit</a>
                </p>
                <p> 修改所属盘口详情的信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> HANDICAP_URL_：盘口地址</p>
                <p> HANDICAP_CAPTCHA_：盘口验证码</p>
                <p> HANDICAP_ITEM_ID_：盘口详情ID</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>7.登录盘口会员</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/login/member?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/app/login/member</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_CODE_：盘口编码</p>

                <p>类型一： </p>
                <p> MEMBER_ACCOUNT_：会员账户，MEMBER_PASSWORD_：会员密码</p>
                <p> HANDICAP_ITEM_ID_：盘口详情id</p>
                <p>如果该用户存在该账户，则会选择去修改信息，不存在则需要验证</p>

                <p>类型二： </p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id，LANDED_TIME_：登录事件戳</p>
                <p>加上登录时间即为定时登录，在选择类型二的同时，也可传出类型一，作用为修改</p>
            </td>
            <td>
                <p> eventId：登录结果请求id</p>
                <p> loginCode：登录类型code</p>
                <p> 登录类型code：</p>
                <p> landedLogin：定时登录-不需要循环请求</p>
                <p> valiLogin：验证登录-循环请求登录结果</p>
                <p> login：登录-循环请求登录结果</p>

            </td>
        </tr>
        <tr>
            <td>
                <p>8.登录结果</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/login/result?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/app/login/result</a>
                </p>
            </td>
            <td>
                <p> eventId：登录结果请求id</p>
                <p> loginCode：登录类型code</p>
            </td>
            <td>
                <p> Code说明：</p>
                <p> CODE_403：不存在的登录类型code</p>
                <p> CODE_202：登录中</p>
                <p> CODE_200：登录成功-读取data中的结果数据</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>9.盘口会员登出</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/logout/member?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/app/logout/member</a>
                </p>
            </td>
            <td>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>10.盘口代理登出</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/logout/agent?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/app/logout/agent</a>
                </p>
            </td>
            <td>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>11.盘口游戏期数</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/handicap/gamePeriod?json={token:\'78f3b6c758c44427829e714087eae858\',data:{ID_:\'88c2c1db44f343f9bf1177cf20ce9456\',GAME_CODE_:\'JS10\'}}');"
                    > http://IP:PORT/PROJECT/ibm/app/handicap/gamePeriod</a>
                </p>
            </td>
            <td>
                <p> ID_：盘口代理id/盘口会员id</p>
                <p> GAME_CODE_：游戏编码</p>
            </td>
            <td>
                <p> periods：最近10期</p>
                <p> period：期数</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>12.盘口代理详细注单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/agent/gameBetDetails?json={token:\'78f3b6c758c44427829e714087eae858\',data:{HANDICAP_AGENT_ID_:\'777977bd82b14551b898ac1d643df16d\',GAME_CODE_:\'JS10\',period:\'31455395\'}}');"
                    > http://IP:PORT/PROJECT/ibm/app/agent/gameBetDetails</a>
                </p>
            </td>
            <td>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> period：期数</p>
            </td>
            <td>
                <p> betInfos：投注信息列表</p>
                <p> drawInfo：开奖信息</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>13.盘口会员详细注单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/member/gameBetDetails?json={token:\'78f3b6c758c44427829e714087eae858\',data:{HANDICAP_MEMBER_ID_:\'88c2c1db44f343f9bf1177cf20ce9456\',GAME_CODE_:\'JS10\',period:\'31455395\'}}');"
                    > http://IP:PORT/PROJECT/ibm/app/member/gameBetDetails</a>
                </p>
            </td>
            <td>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> period：期数</p>
            </td>
            <td>
                <p> betInfos：投注信息列表</p>
                <p> drawInfo：开奖信息</p>
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
