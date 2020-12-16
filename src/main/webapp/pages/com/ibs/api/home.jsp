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
                <p>1.首页 加载主体框架</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/pc/home/main?json={token:\'55ac83ce935c4d518d9c7b9a912a91df\'}');"
                    > http://IP:PORT/PROJECT/ibs/pc/home/main</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
            </td>
            <td>
                <p> memberHandicapInfos：用户拥有的盘口列表</p>
                <p> gameInfos：用户拥有的游戏列表</p>
                <p> endTimeLong：到期时间戳</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.首页 页面内容信息</p>
                <p>2 min 一次刷新</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/pc/home/index?json={token:\'55ac83ce935c4d518d9c7b9a912a91df\'}');"
                    > http://IP:PORT/PROJECT/ibs/pc/home/index</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
            </td>
            <td>
                <p> userInfo：用户主页信息</p>
                <p> unread：用户未读消息条数</p>
                <p> announce：顶部公告列表</p>
                <p> remind：用户提醒列表</p>
                <p> recentLogin：最近登录的盘口</p>
                <p> onHosting：正在托管的盘口信息</p>
                <p> gamePlan：方案列表信息</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.首页 自动刷新</p>
                <p>30s 一次</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/pc/home/info?json={token:\'55ac83ce935c4d518d9c7b9a912a91df\'}');"
                    > http://IP:PORT/PROJECT/ibs/pc/home/info</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
            </td>
            <td>
                <p> userInfo：用户主页信息</p>
                <p> unread：用户未读消息条数</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.首页 点击会员盘口</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/pc/home/clickMember'
                               +'?json={token:\'55ac83ce935c4d518d9c7b9a912a91df\',data:{HANDICAP_CODE_:\'IDC\'}');"
                    > http://IP:PORT/PROJECT/ibs/pc/home/clickMember</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_CODE_：盘口编码</p>
            </td>
            <td>
                <p> onHostingInfo：会员在线信息</p>
                <p> offHostingInfo：会员离线信息</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404Data:数据没有找到</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.首页 开启会员盘口</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/pc/home/openMember'
                               +'?json={token:\'55ac83ce935c4d518d9c7b9a912a91df\',data:{HANDICAP_MEMBER_ID_:\'1234\'}');"
                    > http://IP:PORT/PROJECT/ibs/pc/home/openMember</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员主键</p>
            </td>
            <td>
                <p> account：会员账户信息</p>
                <p> allAccount：所有账户</p>
                <p> hmInfo：会员信息</p>
                <p> profitInfo：盈亏信息</p>
                <p> profitInfoVr：模拟盈亏信息</p>
                <p> gameInfo：游戏信息</p>
                <p> hmSetInfo：会员基础设置</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404HandicapMember:盘口会员没有找到</p>
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
