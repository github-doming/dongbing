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
                <p>1.获取盘口代理的盘口信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/agent/handicapInfo?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\'}}}');"
                    > http://IP:PORT/PROJECT/ibm/app/agent/handicapInfo</a>
                </p>
                <p> 获取盘口代理的盘口信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
            </td>
            <td>
                <p> haInfo：代理状态信息</p>
                <p> followAmount：跟投会员数量</p>
                <p> gameInfo：游戏信息</p>
                <p> haGameSetInfos：游戏设置信息</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.获取跟投会员信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/agent/followMemberInfo?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\'}}}');"
                    > http://IP:PORT/PROJECT/ibm/app/agent/followMemberInfo</a>
                </p>
                <p> 获取跟投会员信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
            </td>
            <td>
                <p> FOLLOW_MEMBER_TYPE_：跟投类型</p>
                <p> MEMBER_LIST_INFO_：会员列表信息</p>
                <p> FOLLOW_MEMBER_LIST_INFO_：跟投会员列表信息</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.保存跟投会员设置</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/agent/followMemberSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\'}}}');"
                    > http://IP:PORT/PROJECT/ibm/app/agent/followMemberSet</a>
                </p>
                <p> 保存跟投会员设置</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> FOLLOW_MEMBER_TYPE_：跟投类型</p>
                <p> MEMBER_LIST_：跟投会员列表信息</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.设置跟投倍数</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/agent/followMultipleSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\'}}}');"
                    > http://IP:PORT/PROJECT/ibm/app/agent/followMultipleSet</a>
                </p>
                <p> 设置跟投倍数</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> GAME_CODE_：游戏code</p>
                <p> BET_FOLLOW_MULTIPLE_：跟投倍数</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.修改游戏投注状态</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/agent/gameBetState?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\'}}}');"
                    > http://IP:PORT/PROJECT/ibm/app/agent/gameBetState</a>
                </p>
                <p> 修改游戏投注状态</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> GAME_CODE_：游戏code</p>
                <p> BET_STATE_：投注状态</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.设置过滤信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/agent/gameFilterSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\'}}}');"
                    > http://IP:PORT/PROJECT/ibm/app/agent/gameFilterSet</a>
                </p>
                <p> 设置过滤信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> GAME_CODE_：游戏code</p>
                <p> FILTER_PROJECT_：过滤项目</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>7.修改游戏跟投设置</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/agent/gameFollowSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\'}}}');"
                    > http://IP:PORT/PROJECT/ibm/app/agent/gameFollowSet</a>
                </p>
                <p> 修改游戏跟投设置</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> GAME_CODE_：游戏code</p>
                <p> BET_FOLLOW_MULTIPLE_：跟投倍数</p>
                <p> BET_LEAST_AMOUNT_：最低金额</p>
                <p> BET_MOST_AMOUNT_：最高金额</p>
                <p> BET_FILTER_NUMBER_：过滤数字</p>
                <p> BET_FILTER_TWO_SIDE_：过滤双面</p>
                <p> NUMBER_OPPOSING_：数字反投</p>
                <p> TWO_SIDE_OPPOSING_：双面反投</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>8.获取游戏设置信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/agent/gameInfo?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\'}}}');"
                    > http://IP:PORT/PROJECT/ibm/app/agent/gameInfo</a>
                </p>
                <p> 获取游戏设置信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> GAME_CODE_：游戏code</p>
            </td>
            <td>
                <p> BET_STATE_：投注状态</p>
                <p> BET_FOLLOW_MULTIPLE_：跟投倍数</p>
                <p> BET_LEAST_AMOUNT_：最低金额</p>
                <p> BET_MOST_AMOUNT_：最高金额</p>
                <p> BET_FILTER_NUMBER_：过滤数字</p>
                <p> BET_FILTER_TWO_SIDE_：过滤双面</p>
                <p> NUMBER_OPPOSING_：数字反投</p>
                <p> TWO_SIDE_OPPOSING_：双面反投</p>
                <p> FILTER_PROJECT_：过滤项目</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>9.获取游戏过滤项目</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/agent/getFilterProject?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\'}}}');"
                    > http://IP:PORT/PROJECT/ibm/app/agent/getFilterProject</a>
                </p>
                <p> 获取游戏过滤项目</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> GAME_CODE_：游戏code</p>
            </td>
            <td>
                <p> FILTER_PROJECT_：过滤项目</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>10.盘口代理投注信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/agent/gameBetInfo?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\'}}}');"
                    > http://IP:PORT/PROJECT/ibm/app/agent/gameBetInfo</a>
                </p>
                <p> 盘口代理投注信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> GAME_CODE_：游戏code</p>
                <p> checkTime：检验时间</p>
            </td>
            <td>
                <p> {HA_FOLLOW_PERIOD_ID_：id,</p>
                <p> PERIOD_：期数,</p>
                <p> BET_LEN_：投注数,</p>
                <p> BET_FUNDS_：投注额,</p>
                <p> FOLLOW_FUND_：跟投金额,</p>
                <p> EXEC_STATE_：投注状态}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>11.定时刷新代理信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/agent/refresh/info?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\'}}}');"
                    > http://IP:PORT/PROJECT/ibm/app/agent/refresh/info</a>
                </p>
                <p> 定时刷新代理信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
            </td>
            <td>
                <p> {haInfo：代理登录状态信息,</p>
                <p> FOLLOW_MEMBER_TYPE_：会员跟投类型,</p>
                <p> followAmount：会员跟投数量,</p>
                <p> gameInfo：游戏信息,</p>
                <p> haGameSetInfos：游戏设置信息,</p>
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
