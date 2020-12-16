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
                <p>1.加载主题框架</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/main?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/home/main</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> memberHandicapInfo：会员盘口信息</p>
                <p> agentHandicapInfo：代理盘口信息</p>
                <p> userInfo：用户信息</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.加载页面内容信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/page?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/home/page</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> memberHandicapInfo：会员盘口信息</p>
                <p> agentHandicapInfo：代理盘口信息</p>
                <p> userInfo：用户信息</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.盘口识别</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/handicap/itemDiscern?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/handicap/itemDiscern</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_CODE_：盘口编码（可为空）</p>
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
                <p>4.盘口验证码修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/handicap/itemEdit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/handicap/itemEdit</a>
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
                <p>5.点击会员盘口</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/clickMemberHandicap?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/home/clickMemberHandicap</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_CODE_：盘口编码</p>
            </td>
            <td>
                <p> onHostingInfo：托管会员信息</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id，MEMBER_ACCOUNT_：盘口会员账户</p>
                <p> offHostingInfo：未托管会员信息</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id，MEMBER_ACCOUNT_：盘口会员账户</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.开启会员盘口</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/openMemberHandicap?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\',HANDICAP_MEMBER_ID_:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/home/openMemberHandicap</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
            </td>
            <td>
                <p> account：账户信息</p>
                <p> MEMBER_ACCOUNT_：盘口会员账户，HANDICAP_ITEM_ID_：盘口详情id</p>
                <p> allAccount：用户未登录会员</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id，MEMBER_ACCOUNT_：盘口会员账户</p>
                <p> hmInfo：盘口会员信息</p>
                <p> MEMBER_ACCOUNT_：盘口账户，AMOUNT_：盘口余额，HANDICAP_PROFIT_：盘口盈亏</p>
                <p> STATE_：登录状态</p>
                <p> hmSetInfo：盘口会员设置</p>
                <p> BET_MERGER_：投注合并，BET_RECORD_ROWS_：表格保存，BET_RATE_：投注比例</p>
                <p> PROFIT_LIMIT_MAX_：止盈上限，LOSS_LIMIT_MIN_：止损下限，PROFIT_LIMIT_MIN_：止盈下限</p>
                <p> profitInfo：真实盈亏《登录》</p>
                <p> PROFIT_：盈亏，BET_FUNDS_T_：投注金额，BET_LEN_：投注数</p>
                <p> profitInfoVr：模拟盈亏《登录》</p>
                <p> PROFIT_：盈亏，BET_FUNDS_T_：投注金额，BET_LEN_：投注数</p>
                <p> gameInfo：游戏信息《登录》</p>
                <p> GAME_CODE_：游戏编码，GAME_NAME_：游戏名称，ICON_：游戏图标</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>7.点击代理盘口</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/clickAgentHandicap?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/home/clickAgentHandicap</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_CODE_：盘口编码</p>
            </td>
            <td>
                <p> onHostingInfo：托管代理信息</p>
                <p> 托管代理信息-HANDICAP_AGENT_ID_：盘口代理id，AGENT_ACCOUNT_：盘口代理账户</p>
                <p> offHostingInfo：未托管代理信息</p>
                <p> 未托管代理信息-HANDICAP_AGENT_ID_：盘口代理id，AGENT_ACCOUNT_：盘口代理账户</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>8.开启代理盘口</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/openAgentHandicap?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\',HANDICAP_AGENT_ID_:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/home/openAgentHandicap</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
            </td>
            <td>
                <p> account：账户信息</p>
                <p> AGENT_ACCOUNT_：盘口会员账户，HANDICAP_ITEM_ID_：盘口详情id</p>
                <p> allAccount：用户未登录代理</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id，AGENT_ACCOUNT_：盘口代理账户</p>
                <p> haInfo：盘口代理信息</p>
                <p> STATE_：登录状态</p>
                <p> haSetInfo：盘口代理设置《登录》</p>
                <p> FOLLOW_MEMBER_TYPE_：跟随类型，MEMBER_LIST_INFO_：会员列表，FOLLOW_MEMBER_LIST_INFO_：跟随列表</p>
                <p> haGameSetInfos：盘口代理游戏设置</p>
                <p> GAME_CODE_：游戏编码，GAME_NAME_：游戏名称，BET_FOLLOW_MULTIPLE_：跟投倍数</p>
                <p> BET_LEAST_AMOUNT_：最低金额，BET_MOST_AMOUNT_：最高金额，BET_STATE_：状态</p>
                <p> BET_FILTER_NUMBER_：过滤号码，BET_FILTER_TWO_SIDE_：过滤双面</p>
                <p> NUMBER_OPPOSING_：数字反投，TWO_SIDE_OPPOSING_：双面反投，</p>
                <p> FILTER_PROJECT_：过滤项，EXTENSION_SET_：扩展设置，BET_RECORD_ROWS_：表格保存行</p>
                <p> gameInfo：游戏信息《登录》</p>
                <p> GAME_CODE_：游戏编码，GAME_NAME_：游戏名称，ICON_：游戏图标</p>
            </td>
        </tr>
        <tr>
            <td colspan="4">
                <p> 流程：盘口详情id->登录->循环获取登录结果</p>
                <p> 说明</p>
                <p> 1.账户密码登录《不传递会员或代理ID》</p>
                <p> - 如果用户登录的账户已存在，则会更新传递的内容后进行登录</p>
                <p> - 如果用户登录的账户不存在，存储传递的信息进行校验用户的合法性</p>
                <p> 2.ID登录《信息都可传，传输即为修改》</p>
                <p> - 打开盘口选择一个已经存在记录客户即可使用此方法登录，在登录的同时可以去修改保存的信息</p>

            </td>
        </tr>
        <tr>
            <td>
                <p>9.登录盘口会员</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/login/member?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/login/member</a>
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
                <p>8.登录盘口代理《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/login/agent?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/login/agent</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_CODE_：盘口编码</p>

                <p>类型一： </p>
                <p> AGENT_ACCOUNT_：代理账户，AGENT_PASSWORD_：代理密码</p>
                <p> HANDICAP_ITEM_ID_：盘口详情id</p>
                <p>如果该用户存在该账户，则会选择去修改信息，不存在则需要验证</p>

                <p>类型二： </p>
                <p> HANDICAP_AGENT_ID_：盘口代理id，LANDED_TIME_：登录事件戳</p>
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
                <p>9.登录结果</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/login/result?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/login/result</a>
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
                <p>10.获取盘口会员信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/member/handicapInfo?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\',HANDICAP_MEMBER_ID_:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/member/handicapInfo</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
            </td>
            <td>
                <p> hmInfo：盘口会员信息</p>
                <p> hmSetInfo：盘口会员设置</p>
                <p> profitInfo：真实盈亏</p>
                <p> profitInfoVr：模拟盈亏</p>
                <p> gameInfo：游戏列表</p>

            </td>
        </tr>
        <tr>
            <td>
                <p>11.获取盘口代理信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/agent/handicapInfo?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\',HANDICAP_AGENT_ID_:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/agent/handicapInfo</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
            </td>
            <td>

                <p> haInfo：盘口代理信息</p>
                <p> haSetInfo：盘口代理设置</p>
                <p> haGameSetInfos：盘口代理游戏设置</p>
                <p> gameInfo：游戏列表</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>12.获取游戏设置信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/member/gameInfo?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\',HANDICAP_AGENT_ID_:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/member/gameInfo</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏编码</p>
            </td>
            <td>
                <p> 游戏设置信息</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>13.游戏开奖信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/member/gameInfo?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_CODE_:\'WS2\',HANDICAP_AGENT_ID_:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/member/gameInfo</a>
                </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> check：获取条数（可为空）</p>
            </td>
            <td>
                <p> drawInfos：开奖数据</p>
                <p> periodInfo：期数信息</p>
            </td>
        </tr>
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
                <p> memberHandicapInfo：会员盘口信息</p>
                <p> memberInfo：会员信息</p>
                <p> agentHandicapInfo：代理盘口信息</p>
                <p> agentInfo：代理信息</p>
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
                <p> memberHandicapInfo：会员盘口信息</p>
                <p> memberInfo：会员信息</p>
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
                <p> agentHandicapInfo：代理盘口信息</p>
                <p> agentInfo：代理信息</p>
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
                <p> cmsInfo：消息</p>
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
