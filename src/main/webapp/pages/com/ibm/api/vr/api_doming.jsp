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
                <p>1点击虚拟会员盘口 GET</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/clickVrMemberHandicap');"
                    > http://IP:PORT/PROJECT/ibm/pc/home/clickVrMemberHandicap</a>
                </p>
            </td>
            <td>
            </td>
            <td>
                <p> onHostingInfo:正在跟投会员</p>
                <p> offHostingInfo:未跟投会员</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>1首页，页面内容信息（原有接口）新增参数rank GET</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/page');"
                    > http://IP:PORT/PROJECT/ibm/pc/home/page</a>
                </p>
            </td>
            <td>
            </td>
            <td>
                <p> rank:{VR_MEMBER_ID_:虚拟会员id,VR_USER_NAME_:虚拟用户名称,HANDICAP_CODE_:盘口编码,RANKING_:排名,PROFIT_PEAK_:盈亏峰值,
                    PROFIT_VALLEY_:盈亏谷值,BET_LEN_:投注数,WIN_RATE_:胜率,PROFIT_T_:输赢金额,RANKING_:名次}</p>
                <p>following:正在跟投的虚拟会员id数组</p>
            </td>
        </tr>



        <tr>
            <td>
                <p>1.跟投会员信息 GET</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/follow/member/followInfo');"
                    > http://IP:PORT/PROJECT/ibm/pc/follow/member/followInfo</a>
                </p>
            </td>
            <td>
                <p> VR_MEMBER_ID_：虚拟会员id</p>
            </td>
            <td>
                <p> rankInfo:{VR_USER_NAME_:虚拟用户名称,HANDICAP_CODE_:盘口编码,RANKING_:排名}</p>
                <p> gameProfitInfo:{GAME_NAME_:游戏名称,GAME_CODE_:游戏编码,BET_LEN_:投注长度,PROFIT_MAX_:最大盈利,LOSS_MAX_:最大亏损,WIN_RATE_:胜率,PROFIT_:输赢金额}</p>
                <p> gameInfo:{BET_STATE_:投注状态,GAME_NAME_:游戏名称,GAME_CODE_:游戏编码}</p>
                <p> gameSetInfos:{BET_STATE_:投注状态,GAME_NAME_:游戏名称,GAME_CODE_:游戏编码,BET_FOLLOW_MULTIPLE_:跟投倍数,
                    BET_LEAST_AMOUNT_:最低金额,BET_MOST_AMOUNT_:最高金额,BET_FILTER_NUMBER_:过滤数字,BET_FILTER_TWO_SIDE_:过滤双面,
                    NUMBER_OPPOSING_:数字反投,TWO_SIDE_OPPOSING_:双面反投,FILTER_PROJECT_:过滤项目,EXTENSION_SET_:扩展设置}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.虚拟会员详情信息 GET</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/follow/member/item?json={session:\'A545481C511E4DBFA6C9675986B6F6EB\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/follow/member/item</a>
                </p>
            </td>
            <td>
                <p> VR_MEMBER_ID_：虚拟会员id</p>
            </td>
            <td>
                <p> VR_USER_NAME_:虚拟用户名称</p>
                <p> VR_MEMBER_ACCOUNT_:虚拟账号名</p>
                <p> HANDICAP_CODE_:盘口编码</p>
                <p> gameInfo:游戏信息</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.游戏跟投设置 POST</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/follow/member/gameFollowSet?json={session:\'B3CB14300CC24FE2929D2F100E053ADB\',code:\'9722\''+
                               ',name:\'doming\',password:\'doming\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/follow/member/gameFollowSet</a>
                </p>
            </td>
            <td>
                <p> VR_MEMBER_ID_：虚拟会员id</p>
                <p> FOLLOW_SET_:跟投设置</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.游戏投注状态修改 POST</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/follow/member/gameBetState?json={session:\'B3CB14300CC24FE2929D2F100E053ADB\',code:\'8568\''+
                               ',name:\'doming\',password:\'doming\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/follow/member/gameBetState</a>
                </p>
            </td>
            <td>
                <p> VR_MEMBER_ID_：虚拟会员id</p>
                <p> GAME_CODE_:游戏编码</p>
                <p> BET_STATE_：投注状态</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.虚拟会员游戏投注详情 GET</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/follow/member/gameBetItem?json={token:\'55ac83ce935c4d518d9c7b9a912a91df\',data:{'+
                               'oldPassword:\'doming\',newPassword:\'doming1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/follow/member/gameBetItem</a>
                </p>
            </td>
            <td>
                <p> VR_MEMBER_ID_：虚拟会员id</p>
                <p> GAME_CODE_：游戏编码</p>
            </td>
            <td>
                <p> CREATE_TIME_LONG_:创建时间，BET_LEN_:投注数,PROFIT_MAX_:最大盈利,LOSS_MAX_:最大亏损,WIN_RATE_:胜率,PROFIT_:输赢金额</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.虚拟会员游戏投注信息 GET</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/follow/member/gameBetInfo?json={token:\'55ac83ce935c4d518d9c7b9a912a91df\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/follow/member/gameBetInfo</a>
                </p>
            </td>
            <td>
                <p> GAME_CODE_：游戏编码</p>
                <p> VR_MEMBER_ID_：虚拟会员id</p>
                <p> checkTime：上次检验时间</p>
            </td>
            <td>
                <p>betNewInfos:新的投注信息</p>
                <p>nextTime:下次检验时间</p>
                <p>betInfo:旧的投注信息</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>7.清空表格 GET</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/follow/member/clearForm');"
                    > http://IP:PORT/PROJECT/ibm/pc/follow/member/clearForm</a>
                </p>
            </td>
            <td>
                <p> GAME_CODE_：游戏编码</p>
                <p> VR_MEMBER_ID_：虚拟会员id</p>
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
