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
                <p>1.盘口设置修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/member/hmSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_MEMBER_ID_:\'1\',BET_RECORD_ROWS_:\'200\',BET_RATE_:\'200\',PROFIT_LIMIT_MAX_:\'10000\',PROFIT_LIMIT_MIN_:\'100\',LOSS_LIMIT_MIN_:\'-100\',BET_MERGER_:\'TRUE\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/member/hmSet</a>
                </p>
                <p> 修改盘口设置信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> BET_RECORD_ROWS_：投注记录保存行数</p>
                <p> BET_RATE_：投注比例</p>
                <p> PROFIT_LIMIT_MAX_：止盈上限</p>
                <p> PROFIT_LIMIT_MIN_：止盈下限</p>
                <p> LOSS_LIMIT_MIN_：止损下限</p>
                <p> BET_MERGER_：投注合并(OPEN,CLOSE)</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.投注状态修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/member/gameBetState?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_MEMBER_ID_:\'1\',GAME_CODE_:\'PK10\',BET_STATE_:\'TRUE\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/member/gameBetState</a>
                </p>
                <p> 修改投注状态</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏code</p>
                <p> BET_STATE_：投注状态(TRUE,FALSE)</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.修改游戏详情信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/member/gameBetDetail?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_MEMBER_ID_:\'1\',GAME_CODE_:\'PK10\',BET_MODE_:\'REAL\',BET_SECOND_:\'200\',SPLIT_TWO_SIDE_AMOUNT_:\'200\',SPLIT_NUMBER_AMOUNT_:\'200\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/member/gameBetDetail</a>
                </p>
                <p> 修改游戏详情信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏code</p>
                <p> BET_MODE_：投注模式(REAL,VIRTUAL)</p>
                <p> BET_SECOND_：每期投注时刻</p>
                <p> SPLIT_TWO_SIDE_AMOUNT_：双面分投额度</p>
                <p> SPLIT_NUMBER_AMOUNT_：号码分投额度</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.修改自动停止投注状态</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/member/gameAutoBetState?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_MEMBER_ID_:\'1\',GAME_CODE_:\'PK10\',BET_AUTO_STOP_:\'TRUE\',BET_AUTO_STOP_TIME_LONG_:\'1565748925607\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/member/gameAutoBetState</a>
                </p>
                <p> 修改自动停止投注状态</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏code</p>
                <p> BET_AUTO_STOP_：自动停止投注状态(TRUE,FALSE)</p>
                <p> BET_AUTO_STOP_TIME_LONG_：自动停止投注时间</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.修改自动开始投注状态</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/member/gameAutoBetState?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_MEMBER_ID_:\'1\',GAME_CODE_:\'PK10\',BET_AUTO_START_:\'TRUE\',BET_AUTO_START_TIME_LONG_:\'1565748925607\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/member/gameAutoBetState</a>
                </p>
                <p> 修改自动开始投注状态</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏code</p>
                <p> BET_AUTO_START_：自动开始投注状态(TRUE,FALSE)</p>
                <p> BET_AUTO_START_TIME_LONG_：自动开始投注时间</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.定时刷新盘口信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/member/refresh/info?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_MEMBER_ID_:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/member/refresh/info</a>
                </p>
                <p> 修改自动开始投注状态</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
            </td>
            <td>
                <p> hmInfo：盘口会员信息</p>
                <p> profitInfo：真实盈亏</p>
                <p> profitInfoVr：模拟盈亏</p>
                <p> gameInfos：盘口会员游戏信息-投注状态，投注模式</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.手动投注《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/member/manualBet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_MEMBER_ID_:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/member/manualBet</a>
                </p>
                <p> 修改自动开始投注状态</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏Code</p>
                <p> BET_CONTENT_：投注正文</p>
                <p> BET_FUNDS_：单项投注金额</p>
                <p> 投注正文：[[第一名][大],[第一名][单]]</p>

            </td>
            <td>
                <p> 投注结果-定时刷新投注项信息刷新出来</p>
            </td>
        </tr>

        <tr>
            <td colspan="4">APP端接口</td>
        </tr>
        <tr>
            <td>
                <p>1.获取盘口会员的盘口信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/member/handicapInfo?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_MEMBER_ID_:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/app/member/handicapInfo</a>
                </p>
                <p> 修改盘口设置信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
            </td>
            <td>
                <p> hmInfo：会员信息</p>
                <p> hmSetInfo：会员设置信息</p>
                <p> gameInfo：游戏信息</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.盘口设置修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/member/hmSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_MEMBER_ID_:\'1\',BET_RATE_:\'200\',PROFIT_LIMIT_MAX_:\'10000\',PROFIT_LIMIT_MIN_:\'100\',LOSS_LIMIT_MIN_:\'-100\'}}');"
                    > http://IP:PORT/PROJECT/ibm/app/member/hmSet</a>
                </p>
                <p> 修改盘口设置信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> BET_RATE_：投注比例</p>
                <p> PROFIT_LIMIT_MAX_：止盈上限</p>
                <p> PROFIT_LIMIT_MIN_：止盈下限</p>
                <p> LOSS_LIMIT_MIN_：止损下限</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.修改游戏详情信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/member/gameBetDetail?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_MEMBER_ID_:\'1\',GAME_CODE_:\'PK10\',BET_AUTO_START_:\'TRUE\',BET_AUTO_START_TIME_LONG_:\'1565748925607\',BET_AUTO_STOP_:\'TRUE\',BET_AUTO_STOP_TIME_LONG_:\'1565748925607\',BET_SECOND_:\'200\',SPLIT_TWO_SIDE_AMOUNT_:\'200\',SPLIT_NUMBER_AMOUNT_:\'200\'}}');"
                    > http://IP:PORT/PROJECT/ibm/app/member/gameBetDetail</a>
                </p>
                <p> 修改游戏详情信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏code</p>
                <p> BET_AUTO_START_：自动开始投注状态</p>
                <p> BET_AUTO_START_TIME_LONG_：自动开始投注时间</p>
                <p> BET_AUTO_STOP_：自动停止投注状态</p>
                <p> BET_AUTO_STOP_TIME_LONG_：自动停止投注时间</p>
                <p> BET_SECOND_：每期投注时刻</p>
                <p> SPLIT_TWO_SIDE_AMOUNT_：双面分投额度</p>
                <p> SPLIT_NUMBER_AMOUNT_：号码分投额度</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.清除表格</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/member/clearForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{HANDICAP_MEMBER_ID_:\'1\',GAME_CODE_:\'PK10\',BET_AUTO_START_:\'TRUE\',BET_AUTO_START_TIME_LONG_:\'1565748925607\',BET_AUTO_STOP_:\'TRUE\',BET_AUTO_STOP_TIME_LONG_:\'1565748925607\',BET_SECOND_:\'200\',SPLIT_TWO_SIDE_AMOUNT_:\'200\',SPLIT_NUMBER_AMOUNT_:\'200\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/member/clearForm</a>
                </p>
                <p> 清除表格</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏code</p>
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
