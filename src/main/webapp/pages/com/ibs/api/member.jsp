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
            <td colspan="5">主页信息</td>
        </tr>
        <tr>
            <td>
                <p>1.会员 获取盘口会员的基础信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/info</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
            </td>
            <td>
                <p> hmInfo：会员信息</p>
                <p> hmSetInfo：会员设置信息</p>
                <p> gameInfo：游戏信息</p>
                <p> profitInfo：盈亏信息</p>
                <p> profitInfoVr：模拟盈亏信息</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Login:客户未登录</p>
                <p> 404HandicapMember:盘口会员没有找到</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.会员 保存会员设置信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/hmSet</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> BET_RATE_：投注比例</p>
                <p> PROFIT_LIMIT_MAX_：止盈上限</p>
                <p> PROFIT_LIMIT_MIN_：止盈下限</p>
                <p> LOSS_LIMIT_MIN_：止损下限</p>
                <p> RESET_TYPE_：重置类型</p>
                <p> RESET_PROFIT_MAX_：重置盈利上限</p>
                <p> RESET_LOSS_MIN_：重置亏损下限</p>
                <p> MODE_CUTOVER_STATE_：模式切换状态</p>
                <p> CUTOVER_PROFIT_：切换盈利金额</p>
                <p> CUTOVER_LOSS_：切换亏损金额</p>
                <p> BLAST_STOP_：炸停止</p>
                <p> BET_MERGER_：投注合并</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Login:客户未登录</p>
                <p> 404HandicapMember:盘口会员没有找到</p>
                <p> 404Data:数据没有找到</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>3.会员 游戏开奖信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/gameDraw</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> check：条数</p>
            </td>
            <td>
                <p> periodInfo：期数信息</p>
                <p> drawInfos：开奖信息</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 403Error:请求获取错误</p>
                <p> 404Game:未能识别的游戏</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>4.会员 定时刷新盘口信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/refresh/info</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
            </td>
            <td>
                <p> hmInfo：会员信息</p>
                <p> gameInfo：游戏信息</p>
                <p> profitInfo：盈亏信息</p>
                <p> profitInfoVr：模拟盈亏信息</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404HandicapMember:盘口会员没有找到</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.会员 盘口会员投注信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/gameBetInfo</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> checkTime：校验时间</p>
            </td>
            <td>
                <p> waiting：等待投注数</p>
                <p> amount：当期投注总额</p>
                <p> number：当期投注总数</p>
                <p> period：期数</p>
                <p> betNewInfos：新投注信息</p>
                <p> betInfo：投注信息</p>
                <p> nextTime：时间</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404HandicapMember:盘口会员没有找到</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>6.会员 获取游戏设置信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/gameInfo</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏编码</p>
            </td>
            <td>
                <p> BET_STATE_：投注状态</p>
                <p> IS_AUTO_STOP_BET_：自动停止投注</p>
                <p> AUTO_STOP_BET_TIME_LONG_：自动停止投注时间</p>
                <p> IS_AUTO_START_BET_：自动开始投注</p>
                <p> AUTO_START_BET_TIME_LONG_：自动开始投注时间</p>
                <p> BET_MODE_：投注模式</p>
                <p> INCREASE_STATE_：新增状态</p>
                <p> INCREASE_STOP_TIME_LONG_：停止新增时间</p>
                <p> BET_SECOND_：每期投注时刻</p>
                <p> SPLIT_TWO_SIDE_AMOUNT_：双面分投额度</p>
                <p> SPLIT_NUMBER_AMOUNT_：号码分投额度</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Login:客户未登录</p>
                <p> 404Data:数据没有找到</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>7.会员 手动投注</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/manualBet</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> BET_CONTENT_：投注内容</p>
                <p> BET_FUNDS_：投注金额</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
                <p> 404Login:客户未登录</p>
                <p> 404Data:数据没有找到</p>
                <p> 404VrNotManualBet:虚拟投注暂未开放手动投注</p>
                <p> 404HandicapGame:盘口游戏没有找到</p>
                <p> 403ClosingTime:封盘时间</p>
                <p> 403DataError:数据错误</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>8.会员 修改自动投注状态和时间</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/gameAutoBetState</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> IS_AUTO_STOP_BET_：自动停止投注</p>
                <p> AUTO_STOP_BET_TIME_LONG_：自动停止投注时间</p>
                <p> IS_AUTO_START_BET_：自动开始投注</p>
                <p> AUTO_START_BET_TIME_LONG_：自动开始投注时间</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
                <p> 404Login:客户未登录</p>
                <p> 404Data:数据没有找到</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>9.会员 修改游戏投注状态</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/gameBetState</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> BET_STATE_：投注状态</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
                <p> 404Login:客户未登录</p>
                <p> 404Data:数据没有找到</p>
                <p> 403DataError:数据错误</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>10.会员 修改游戏详情信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/gameBetDetail</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> BET_MODE_：投注模式</p>
                <p> BET_SECOND_：每期投注时刻</p>
                <p> SPLIT_TWO_SIDE_AMOUNT_：双面分投额度</p>
                <p> SPLIT_NUMBER_AMOUNT_：号码分投额度</p>
                <p> INCREASE_STATE_：新增状态</p>
                <p> INCREASE_STOP_TIME_LONG_：停止新增时间</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
                <p> 404Login:客户未登录</p>
                <p> 404Data:数据没有找到</p>
                <p> 403DataError:数据错误</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>11.会员 修改游戏反投状态</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/gameInverse</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> IS_INVERSE_：反投状态（TRUE,FALSE）</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
                <p> 404Login:客户未登录</p>
                <p> 404Data:数据没有找到</p>
                <p> 403DataError:数据错误</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>12.会员 盘口模式切换信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/modeCutover</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
            </td>
            <td>
                <p> CUTOVER_GROUP_DATA_:切换组数据</p>
                <p> CUTOVER_KEY_:切换组key</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Data:数据没有找到</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>13.会员 盘口模式切换设置</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/member/modeCutoverSet</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员id</p>
                <p> CUTOVER_GROUP_DATA_:切换组数据</p>
                <p> CUTOVER_KEY_:切换组key</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
                <p> 404Data:数据没有找到</p>
            </td>
        </tr>


        <tr>
            <td colspan="5">盘口接口API：/handicap.jsp</td>
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
