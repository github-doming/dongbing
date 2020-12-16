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
                <p>1.跟投设置修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/agent/gameFollowSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\',FOLLOW_SET_:{GAME_CODE_:\'1\',}}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/agent/gameFollowSet</a>
                </p>
                <p> 跟投设置修改</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> FOLLOW_SET_：{</p>
                <p> GAME_CODE_：游戏code,</p>
                <p> BET_STATE_：投注状态,</p>
                <p> BET_FOLLOW_MULTIPLE_：跟投倍数,</p>
                <p> BET_LEAST_AMOUNT_：最低金额,</p>
                <p> BET_MOST_AMOUNT_：最高金额,</p>
                <p> BET_FILTER_NUMBER_：过滤数字,</p>
                <p> BET_FILTER_TWO_SIDE_：过滤双面,</p>
                <p> NUMBER_OPPOSING_：数字反投,</p>
                <p> TWO_SIDE_OPPOSING_：双面反投,</p>
                <p> FILTER_PROJECT_：过滤项目,</p>
                <p> EXTENSION_SET_：扩展设置}</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.游戏跟投状态修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/agent/gameBetState?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\',GAME_CODE_:\'PK10\',BET_STATE_:\'TRUE\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/agent/gameBetState</a>
                </p>
                <p> 修改游戏跟投状态</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> GAME_CODE_：游戏code</p>
                <p> BET_STATE_：投注状态(TRUE,FALSE)</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.跟投会员设置</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/agent/followMemberSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\',FOLLOW_MEMBER_TYPE_:\'PK10\',MEMBER_LIST_:\'REAL\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/agent/followMemberSet</a>
                </p>
                <p> 跟投会员设置</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> FOLLOW_MEMBER_TYPE_：跟随会员类型</p>
                <p> MEMBER_LIST_：会员列表</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.定时刷新会员列表信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/agent/refresh/info?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_AGENT_ID_:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/agent/refresh/info</a>
                </p>
                <p> 定时刷新会员列表信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
            </td>
            <td>
                <p> haInfo：代理信息</p>
                <p> MEMBER_LIST_：会员列表</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>5.清除表格</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/agent/clearForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_MEMBER_ID_:\'1\',GAME_CODE_:\'PK10\',BET_AUTO_START_:\'TRUE\',BET_AUTO_START_TIME_LONG_:\'1565748925607\',BET_AUTO_STOP_:\'TRUE\',BET_AUTO_STOP_TIME_LONG_:\'1565748925607\',BET_SECOND_:\'200\',SPLIT_TWO_SIDE_AMOUNT_:\'200\',SPLIT_NUMBER_AMOUNT_:\'200\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/agent/clearForm</a>
                </p>
                <p> 清除表格</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_AGENT_ID_：盘口代理id</p>
                <p> GAME_CODE_：游戏code</p>
            </td>
            <td>
            </td>
        </tr>


        </tbody>



    </table>
</div> 6
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
