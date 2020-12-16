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
                <p>1.方案 用户方案列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/plan/list</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> GAME_CODE_：游戏编码</p>
            </td>
            <td>
                <p> PLAN_CODE_：方案编码</p>
                <p> SN_：次序</p>
                <p> PLAN_NAME_：方案名称</p>
                <p> BET_MODE_：投注模式</p>
                <p> MONITOR_PERIOD_：监控期数</p>
                <p> PLAN_STATE_：方案状态</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Game:未能识别的游戏</p>
                <p> 403Permission:权限等级不够，请求被拒绝</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.方案 修改用户方案状态</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/plan/state</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> PLAN_STATE_：方案状态</p>
                <p> PLAN_CODE_：方案编码</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Data:数据没有找到</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>3.方案 批量修改用户方案状态</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/plan/batchState</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> PLAN_CODES_：方案编码列表</p>
                <p> PLAN_STATE_：方案状态</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Data:数据没有找到</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>4.方案 方案详情信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/plan/itemInfo</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> PLAN_CODE_：方案编码</p>
            </td>
            <td>
                <p> PROFIT_LIMIT_MAX_：止盈上限</p>
                <p> LOSS_LIMIT_MIN_：止损下限</p>
                <p> FUNDS_LIST_：资金列表</p>
                <p> FOLLOW_PERIOD_：跟随期数</p>
                <p> MONITOR_PERIOD_：监控期数</p>
                <p> BET_MODE_：投注模式</p>
                <p> FUND_SWAP_MODE_：资金模式</p>
                <p> PERIOD_ROLL_MODE_：期期滚模式</p>
                <p> PLAN_GROUP_DATA_：方案组信息</p>
                <p> PLAN_GROUP_ACTIVE_KEY_：开启方案组key</p>
                <p> EXPAND_INFO_：扩展信息</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Data:数据没有找到</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.方案 修改方案详情信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/plan/itemDetail</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> GAME_CODE_：游戏编码（不为空）</p>
                <p> PLAN_CODE_：方案编码（不为空）</p>
                <p> PROFIT_LIMIT_MAX_：止盈上限（不为空）</p>
                <p> LOSS_LIMIT_MIN_：止损下限（不为空）</p>
                <p> FUNDS_LIST_：资金列表</p>
                <p> PLAN_GROUP_DATA_：方案组数据（不为空）</p>
                <p> PLAN_GROUP_ACTIVE_KEY_：开启的方案组key(都没开启的情况则为空)</p>
                <p> FOLLOW_PERIOD_：跟随期数（若方案没有这设置，则不传）</p>
                <p> MONITOR_PERIOD_：监控期数（若方案没有这设置，则不传）</p>
                <p> BET_MODE_：投注模式（若方案没有这设置，则不传）</p>
                <p> FUND_SWAP_MODE_：资金模式（若方案没有这设置，则不传）</p>
                <p> PERIOD_ROLL_MODE_：期期滚模式（若方案没有这设置，则不传）</p>
                <p> EXPAND_INFO_：扩展信息(存放一些方案特有的，例如龙虎和不投注，反投这些)</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Data:数据没有找到</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>6.方案 删除用户方案</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/plan/del</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> GAME_CODE_：游戏编码（不为空）</p>
                <p> PLAN_CODE_：方案编码（不为空）</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Data:数据没有找到</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>7.方案 清除用户方案列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/plan/clear</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> GAME_CODE_：游戏编码（不为空）</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Data:数据没有找到</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
            </td>
        </tr>



        <tr>
            <td>
                <p>8.方案 添加方案 POST</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/plan/save</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> GAME_CODE_：游戏编码（不为空）</p>
                <p> PLAN_CODE_：方案编码（不为空）</p>
                <p> SN_：次序（不为空）</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Data:数据没有找到</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>9.方案 用户方案库 GET</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/plan/library</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> GAME_CODE_：游戏编码（不为空）</p>
            </td>
            <td>
                <p> PLAN_NAME_:方案名称</p>
                <p> PLAN_CODE_:方案编码</p>
                <p> SN_:次序</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Data:数据没有找到</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>10.方案 用户方案重置 POST</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/plan/reset</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> GAME_CODE_：游戏编码（不为空）</p>
                <p> PLAN_CODES_：方案编码（不为空）</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
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
