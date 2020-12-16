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
                <p>1.获取用户列表信息《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/list</a>
                </p>
                <p> 获取用户列表信息</p>
            </td>
            <td>
                <p> userName：用户名</p>
                <p> startTime：登录开始时间</p>
                <p> endTime：登录结束时间</p>
                <p> pageIndex：页数</p>
                <p> pageSize：条数</p>
            </td>
            <td>
                <p> userName：用户名</p>
                <p> startTime：登录开始时间</p>
                <p> endTime：登录结束时间</p>
                <p> userInfos：{APP_USER_NAME_:用户名,NICKNAME_:用户名称,USEABLE_POINT_:可用点数,</p>
                <p> END_TIME_:到期时间,AGENT_HANDICAP_:代理盘口,MEMBER_HANDICAP_:会员盘口,</p>
                <p>onlineAgent:在线代理数量,onlineMember:在线会员数量,APP_USER_TYPE_:用户类型,</p>
                <p>APP_USER_ID_:用户id,IP_:登录ip,STATE_:状态}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.用户新增页面表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/form</a>
                </p>
                <p> 添加用户</p>
            </td>
            <td>
            </td>
            <td>
                <p> agentUsable：代理可用盘口</p>
                <p> memberUsable：会员可用盘口</p>
                <p> gameUsable：可用游戏</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.添加用户《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/save</a>
                </p>
                <p> 添加用户</p>
            </td>
            <td>
                <p> accountName：用户名</p>
                <p> password：密码</p>
                <p> endTime：到期时间</p>
                <p> useablePoint：点数</p>
                <p> onlineHaNumberMax：最大在线代理总数</p>
                <p> onlineHmNumberMax：最大在线会员总数</p>
                <p> agentUsable：代理可用盘口(数据类型eg：{WS2,IDC,SGWIN},会员同理)</p>
                <p> memberUsable：会员可用盘口</p>
                <p> gameUsable：可用游戏</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.用户修改基本信息表达页面《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/infoForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/infoForm</a>
                </p>
                <p> 用户修改基本信息表达页面</p>
            </td>
            <td>
                <p> appUserId：用户id</p>
            </td>
            <td>
                <p> ACCOUNT_NAME_：用户名</p>
                <p> PASSWORD_：密码</p>
                <p> END_TIME_：到期时间</p>
                <p> USEABLE_POINT_：可用点数</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.用户修改盘口设置表单信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/handicapSetForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/handicapSetForm</a>
                </p>
                <p> 用户修改盘口设置表单信息</p>
            </td>
            <td>
                <p> appUserId：用户id</p>
            </td>
            <td>
                <p> availableGame：已有可用游戏</p>
                <p> allGameInfo：所有游戏信息</p>
                <p> AGENT_ONLINE_MAX_：代理最大在线数量</p>
                <p> MEMBER_ONLINE_MAX_：会员最大在线数量</p>
                <p> memberInfos：会员盘口信息</p>
                <p> agentInfos：代理盘口信息</p>
                <p> memberAllUsable：所有会员盘口</p>
                <p> agentAllUsable：所有代理盘口</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.更新用户信息《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/infoSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/infoSet</a>
                </p>
                <p> 更新用户信息</p>
            </td>
            <td>
                <p> appUserId：用户id</p>
                <p> password：密码</p>
                <p> endTime：到期时间</p>
                <p> useablePoint：点数</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>7.修改盘口信息《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/handicapSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/handicapSet</a>
                </p>
                <p> 修改盘口信息</p>
            </td>
            <td>
                <p> appUserId：用户id</p>
                <p> avaiableGame：可用游戏</p>
                <p> onlineHaNumberMax：最大代理在线数量</p>
                <p> onlineHmNumberMax：最大会员在线数量</p>
                <p> agentUsable：代理可用盘口</p>
                <p> memberUsable：会员可用盘口</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>8 .删除用户《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/delete?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/delete</a>
                </p>
                <p> 删除用户</p>
            </td>
            <td>
                <p> appUserId：用户id</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td colspan="4">会员列表接口</td>
        </tr>

        <tr>
            <td>
                <p>1.获取会员列表信息《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/member/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/member/list</a>
                </p>
                <p> 获取会员列表信息</p>
            </td>
            <td>
                <p> userName：用户名</p>
                <p> memberAccount：会员名</p>
                <p> handicapCode：盘口code</p>
                <p> onlineState：在线状态</p>
                <p> pageIndex：页数</p>
                <p> pageSize：条数</p>
            </td>
            <td>
                <p> userName：用户名</p>
                <p> memberAccount：会员名</p>
                <p> handicapCode：盘口code</p>
                <p> onlineState：在线状态</p>
                <p> pageIndex：页数</p>
                <p> pageSize：条数</p>

                <p>
                    memberInfos：{HANDICAP_MEMBER_ID_:盘口会员id,APP_USER_NAME_:用户名,HANDICAP_CODE_:盘口code,MEMBER_ACCOUNT_:会员账号,</p>
                <p> HANDICAP_URL_:导航地址,HANDICAP_CAPTCHA_:验证码,STATE_:登录状态,</p>
                <p>availableGame:可用游戏}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.会员上线《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/member/login?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/member/login</a>
                </p>
                <p> 会员上线</p>
            </td>
            <td>
                <p> handicapMemberId：盘口会员id</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.会员下线《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/member/logout?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/member/logout</a>
                </p>
                <p> 会员下线</p>
            </td>
            <td>
                <p> handicapMemberId：盘口会员id</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.会员设置详情表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/member/itemForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/member/itemForm</a>
                </p>
                <p> 会员设置详情表单</p>
            </td>
            <td>
                <p> handicapMemberId：盘口会员id</p>
            </td>
            <td>
                <p>
                    itemInfo：会员详情信息{MEMBER_ACCOUNT_:合并状态，HANDICAP_URL_:盘口地址，HANDICAP_CAPTCHA_:盘口校验码，LANDED_TIME_LONG_:定时登录时间，AUTO_LOGIN_STATE_:定时登录状态}</p>
                <p> availableGame：可用游戏</p>
                <p> allHandicapGame：所有游戏</p>
                <p> handicapSet：盘口设置信息{BET_MERGER_:合并状态，BET_RECORD_ROWS_:投注记录保存行数，BET_RATE_:投注比例，}</p>
                <p>PROFIT_LIMIT_MAX_:止盈上限，LOSS_LIMIT_MIN_:止损下限，PROFIT_LIMIT_MIN_:止盈下限}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.会员详情设置《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/member/itemSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/member/itemSet</a>
                </p>
                <p> 会员详情设置</p>
            </td>
            <td>
                <p> handicapMemberId：盘口会员id</p>
                <p> handicapUrl：盘口地址</p>
                <p> handicapCaptcha：盘口校验码</p>
                <p> autoLoginState：自动登录状态</p>
                <p> landedTime：自动登录时间</p>
                <p> handicapSet：盘口设置</p>
                <p> availableGame：可用游戏</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.会员投注表单信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/member/betForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/member/betForm</a>
                </p>
                <p> 会员投注表单信息</p>
            </td>
            <td>
                <p> handicapMemberId：盘口会员id</p>
            </td>
            <td>
                <p> GAME_NAME_：游戏名称</p>
                <p> GAME_CODE_：游戏code</p>
                <p> BET_STATE_：投注状态</p>
                <p> BET_AUTO_START_：自动开始投注状态</p>
                <p> BET_AUTO_START_TIME_LONG_：自动开始投注时间</p>
                <p> BET_AUTO_STOP_：自动停止状态</p>
                <p> BET_AUTO_STOP_TIME_LONG_：自动停止投注时间</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>7.会员投注设置《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/member/betSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/member/betSet</a>
                </p>
                <p> 会员投注设置</p>
            </td>
            <td>
                <p> handicapMemberId：盘口会员id</p>
                <p> gameBetSet：游戏投注设置</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>8.删除会员信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/member/delete?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/member/delete</a>
                </p>
                <p> 删除会员信息</p>
            </td>
            <td>
                <p> handicapMemberId：盘口会员id</p>
            </td>
            <td>
            </td>
        </tr>


        <tr>
            <td colspan="4">代理列表接口</td>
        </tr>

        <tr>
            <td>
                <p>1.代理列表信息《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/agent/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/agent/list</a>
                </p>
                <p> 代理列表信息</p>
            </td>
            <td>
                <p> userName：用户名</p>
                <p> agentAccount：代理账号</p>
                <p> handicapCode：盘口code</p>
                <p> onlineState：在线状态</p>
                <p> pageIndex：页数</p>
                <p> pageSize：条数</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.代理上线《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/agent/login?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/agent/login</a>
                </p>
                <p> 代理上线</p>
            </td>
            <td>
                <p> handicapAgentId：盘口代理id</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.代理下线《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/agent/logout?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/agent/logout</a>
                </p>
                <p> 代理下线</p>
            </td>
            <td>
                <p> handicapAgentId：盘口代理id</p>
            </td>
            <td>
            </td>
        </tr>


        <tr>
            <td>
                <p>4.代理详情表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/agent/itemForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/agent/itemForm</a>
                </p>
                <p> 代理详情表单</p>
            </td>
            <td>
                <p> handicapAgentId：盘口代理id</p>
            </td>
            <td>
                <p>
                    itemInfo：详情信息{AGENT_ACCOUNT_:代理账号,HANDICAP_URL_:盘口地址,HANDICAP_CAPTCHA_:验证码,LANDED_TIME_LONG_:自动登录时间,AUTO_LOGIN_STATE_:自动登录状态}</p>
                <p> availableGame：可用游戏</p>
                <p> allHandicapGame：所有游戏</p>

            </td>
        </tr>

        <tr>
            <td>
                <p>5.代理详情设置《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/agent/itemSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/agent/itemSet</a>
                </p>
                <p> 代理详情设置</p>
            </td>
            <td>
                <p> handicapAgentId：盘口代理id</p>
                <p> handicapUrl：盘口地址</p>
                <p> handicapCaptcha：验证码</p>
                <p> autoLoginState：自动登录状态</p>
                <p> landedTime：自动登录时间</p>
                <p> availableGame：可用游戏</p>
            </td>
            <td>
            </td>
        </tr>


        <tr>
            <td>
                <p>6.删除代理《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/agent/delete?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/agent/delete</a>
                </p>
                <p> 删除代理</p>
            </td>
            <td>
                <p> handicapAgentId：盘口代理id</p>
            </td>
            <td>
            </td>
        </tr>


        <tr>
            <td>
                <p>5.获取客户机列表信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/list</a>
                </p>
                <p> 获取所有的客户机信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> key：注册IP</p>
            </td>
            <td>
                <p> IBM_CLIENT_ID_：客户机id</p>
                <p> REGISTER_IP_：注册ip</p>
                <p> CLIENT_CODE_：客户端编码</p>
                <p> CLIENT_CAPACITY_MAX_：客户端最大容量</p>
                <p> CLIENT_CAPACITY_：客户端使用容量</p>
                <p> START_TIME_：开始时间</p>
                <p> END_TIME_：结束时间</p>
                <p> STATE_：状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.客户端盘口容量信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicapCapacity?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{clientId:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicapCapacity</a>
                </p>
                <p> 客户端盘口容量信息</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientId：客户机id</p>
            </td>
            <td>
                <p> IBM_CLIENT_HANDICAP_CAPACITY_ID_：客户端盘口容量记录主键</p>
                <p> CLIENT_CODE_：客户端编码</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> CAPACITY_HANDICAP_MAX_：盘口最大容量</p>
                <p> CAPACITY_HANDICAP_：盘口使用容量</p>
                <p> CAPACITY_HA_MAX_：盘口代理最大容量</p>
                <p> CAPACITY_HA_：盘口代理使用容量</p>
                <p> CAPACITY_HM_MAX_：盘口会员最大容量</p>
                <p> CAPACITY_HM_：盘口会员使用容量</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.盘口容量修改表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicapCapacityForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{handicapCapacityId:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicapCapacityForm</a>
                </p>
                <p> 盘口容量修改表单</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapCapacityId：IBM_客户端盘口容量记录主键</p>
            </td>
            <td>
                <p> IBM_CLIENT_HANDICAP_CAPACITY_ID_：IBM_客户端盘口容量记录主键</p>
                <p> CLIENT_CODE_：客户端编码</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> CAPACITY_HANDICAP_MAX_：盘口最大容量</p>
                <p> CAPACITY_HA_MAX_：盘口代理最大容量</p>
                <p> CAPACITY_HM_MAX_：盘口会员最大容量</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.客户端盘口容量设置《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicapCapacitySet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{handicapCapacityId:\'1\',capacityHandicapMax:\'PK10\',capacityHaMax:\'TRUE\',capacityHmMax:\'1565748925607\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicapCapacitySet</a>
                </p>
                <p> 客户端盘口容量设置</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapCapacityId：IBM_客户端盘口容量记录主键</p>
                <p> capacityHandicapMax：盘口最大容量</p>
                <p> capacityHaMax：盘口代理最大容量</p>
                <p> capacityHmMax：盘口会员最大容量</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.客户端容量修改表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/clientCapacityForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{clientId:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/clientCapacityForm</a>
                </p>
                <p> 客户端容量修改表单</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientId：客户端id</p>
            </td>
            <td>
                <p> CLIENT_ID_：客户端id</p>
                <p> CLIENT_CODE_：客户端编码</p>
                <p> CLIENT_CAPACITY_MAX_：客户端最大容量</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.客户端容量设置《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/clientCapacitySet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{clientCode:\'1\',clientCapacityMax:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/clientCapacitySet</a>
                </p>
                <p> 客户端容量设置</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
                <p> clientCapacityMax：客户端最大容量</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>7.客户机盘口迁移表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicapMigrateForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicapMigrateForm</a>
                </p>
                <p> 客户机盘口迁移表单</p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> client：客户端列表</p>
                <p> handicap：盘口列表</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>8.盘口迁移《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/replaceClient?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/replaceClient</a>
                </p>
                <p> 盘口迁移</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> customerType：客户类型</p>
                <p> handicapCode：盘口code</p>
                <p> sendClientCode：发送端客户机</p>
                <p> receiveClientCode：接收端客户机</p>
            </td>
            <td>
            </td>
        </tr>
        <!--<editor-fold desc="菜单">-->
        <tr>
            <td colspan="4">菜单列表接口 《cmd 必须添加项，每一个接口都添加 - show：查询、展示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- edit : 修改、新增》</td>
        </tr>
        <tr>
            <td>
                <p>1.菜单列表页面</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/menu/list?json={\'token\':\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/menu/list</a>
                </p>
                <p> 菜单列表页面 </p>
                <p> admin/manage/authority/menu/list </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> id：菜单ID</p>
                <p> pid：父菜单ID</p>
                <p> APP_MENU_NAME_：菜单名称</p>
                <p> ADMIN_URL_：菜单地址</p>
                <p> ADMIN_PIC_：菜单图片</p>
                <p> SN_：菜单排序</p>
                <p> PERMISSION_CODE_：菜单权限码</p>
                <p> STATE_：菜单状态</p>
                <p> MENU_TYPE_：菜单类型</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.菜单项展示</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/menu/show?json={\'token\':\'30301c8766674e90947d22136145b0ee\',data:{menuId:\'0f8820c6dd214130863e78bf10f8c6d9\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/menu/show</a>
                </p>
                <p> 菜单项展示 </p>
                <p> admin/manage/authority/menu/show </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> menuId：菜单ID</p>
            </td>
            <td>
                <p> menu-菜单详情</p>
                <p> APP_MENU_ID_ : 菜单ID</p>
                <p> APP_MENU_NAME_ : 菜单名称</p>
                <p> APP_MENU_CODE_ : 菜单编码</p>
                <p> ADMIN_URL_ : 菜单地址</p>
                <p> PERMISSION_CODE_：权限码</p>
                <p> ADMIN_PIC_ : 菜单图片</p>
                <p> PARENT_NAME_ : 父名称</p>
                <p> MENU_TYPE_ : 菜单类型</p>
                <p> SN_：菜单排序</p>
                <p> STATE_：菜单状态</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.新增新的菜单项</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/menu/add?json={\'token\':\'30301c8766674e90947d22136145b0ee\',data:{parentId:\'1\',menuName:\'测试10\',menuCode:\'test1\',url:\'1230\',permissionCode:\'123\',pic:\'123\',state:\'OPEN\',sn:\'1000\',permissionGrade:\'123\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/menu/add</a>
                </p>
                <p> 新增新的菜单项 (POST) </p>
                <p> admin/manage/authority/menu/add </p>
                <p> * - 必填项</p>
                <p> # - 所有标示项中必填一项</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> parentId : 父级主键 (*)</p>
                <p> menuName : 菜单名称 (*)</p>
                <p> menuCode : 菜单编码</p>
                <p> url : 菜单地址 (#)</p>
                <p> permissionCode：权限码 (#)</p>
                <p> permissionGrade：权限等级</p>
                <p> pic : 菜单图片</p>
                <p> sn：菜单排序</p>
                <p> state：菜单状态 (*)</p>
                <p> menuType : 菜单类型</p>
            </td>
            <td>
                <p> state-状态</p>
                <p> OPEN ： 启用</p>
                <p> OPEN ： 禁用</p>
                <p> menuType-菜单状态</p>
                <p> MENU ： 菜单 - 会展示在菜单列表中</p>
                <p> RESOURCES ： 资源 - 不做展示，进作为权限判断使用</p>
                <p> 自定义项 ： 具体CODE不指定，作为以后扩张项目 -默认</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.菜单项修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/menu/edit?json={\'token\':\'30301c8766674e90947d22136145b0ee\',data:{parentId:\'1\',menuId:\'6LR1\',menuName:\'测试2\',menuCode:\'test1\',url:\'123\',permissionCode:\'123\',pic:\'123\',state:\'OPEN\',sn:\'1000\',permissionGrade:\'123\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/menu/edit</a>
                </p>
                <p> 菜单项修改 (POST)</p>
                <p> admin/manage/authority/menu/edit </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> parentId : 父级主键 (*)</p>
                <p> menuId：菜单ID (*)</p>
                <p> menuName : 菜单名称</p>
                <p> menuCode : 菜单编码</p>
                <p> url : 菜单地址</p>
                <p> permissionCode：权限码</p>
                <p> permissionGrade：权限等级</p>
                <p> pic : 菜单图片</p>
                <p> sn：菜单排序</p>
                <p> state：菜单状态 (*)</p>
                <p> menuType : 菜单类型</p>
            </td>
            <td>
                <p> state-状态</p>
                <p> OPEN ： 启用</p>
                <p> OPEN ： 禁用</p>
                <p> menuType-菜单状态</p>
                <p> MENU ： 菜单 - 会展示在菜单列表中</p>
                <p> RESOURCES ： 资源 - 不做展示，进作为权限判断使用</p>
                <p> BUTTON ： 按钮 - 不做展示，进作为权限判断使用</p>
                <p> 自定义项 ： 具体CODE不指定，作为以后扩张项目 -默认</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.查询菜单的上级菜单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/menu/showParent?json={\'token\':\'30301c8766674e90947d22136145b0ee\',data:{menuId:\'0f8820c6dd214130863e78bf10f8c6d9\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/menu/showParent</a>
                </p>
                <p> 菜单项展示  </p>
                <p> admin/manage/authority/menu/showParent  </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> menuId：菜单ID</p>
            </td>
            <td>
                <p> PARENT_ID_ : 父类ID</p>
                <p> PARENT_NAME_ : 父类名称</p>
                <p> menuList - 菜单列表 </p>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.修改上级菜单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/menu/editParent?json={\'token\':\'30301c8766674e90947d22136145b0ee\',data:{parentId:\'Q6Q7\',menuId:\'6LR1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/menu/editParent</a>
                </p>
                <p> 菜单项修改 (POST)</p>
                <p> admin/manage/authority/menu/editParent </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> parentId : 父级主键</p>
                <p> menuId：菜单ID</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>7.删除菜单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/menu/del?json={\'token\':\'30301c8766674e90947d22136145b0ee\',data:{menuId:\'6LR1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/menu/del</a>
                </p>
                <p> 删除菜单 (POST)</p>
                <p> admin/manage/authority/menu/del </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> menuId：菜单ID</p>
            </td>
            <td>
            </td>
        </tr>
        <!--</editor-fold>-->

        <!--<editor-fold desc="角色">-->
        <tr>
            <td colspan="4">角色管理接口 《cmd 必须添加项，每一个接口都添加 - show：查询、展示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- edit : 修改、新增、删除</td>
        </tr>
        <tr>
            <td>
                <p>1.角色管理列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/role/list?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/role/list</a>
                </p>
                <p> 角色管理列表 </p>
                <p> admin/manage/authority/role/list </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> APP_GROUP_ID_：角色ID</p>
                <p> APP_GROUP_NAME_：角色名称</p>
                <p> APP_GROUP_CODE_：角色编码</p>
                <p> PERMISSION_GRADE_：角色等级</p>
                <p> STATE_：状态</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.角色详情信息展示 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/role/show?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\',data:{roleId:\'f940e119cc3945a2b2982fdf431f794e\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/role/show</a>
                </p>
                <p> 角色管理列表 </p>
                <p> admin/manage/authority/role/show </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleId：角色ID</p>
            </td>
            <td>
                <p> APP_GROUP_ID_：角色ID</p>
                <p> APP_GROUP_NAME_：角色名称</p>
                <p> APP_GROUP_CODE_：角色编码</p>
                <p> PERMISSION_GRADE_：角色等级</p>
                <p> STATE_：状态</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.新增新的角色 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/role/add?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{roleName:\'测试角色1\',roleCode:\'test01\',state:\'OPEN\',roleGrade:\'10\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/role/add</a>
                </p>
                <p> 新增新的角色 </p>
                <p> admin/manage/authority/role/add </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleName：角色名称</p>
                <p> roleCode：角色编码</p>
                <p> roleGrade：角色等级</p>
                <p> state：状态</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.角色修改 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/role/edit?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{roleId:\'7C2B8CB22BDA486D9AF58FF435077EFA\',roleName:\'测试角色1\',roleCode:\'test01\',state:\'OPEN\',roleGrade:\'8\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/role/edit</a>
                </p>
                <p> 角色修改 </p>
                <p> admin/manage/authority/role/edit </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleId：角色ID</p>
                <p> roleName：角色名称</p>
                <p> roleCode：角色编码</p>
                <p> roleGrade：角色等级</p>
                <p> state：状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.展示角色资源 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/role/resourcesShow?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\',data:{roleId:\'7C2B8CB22BDA486D9AF58FF435077EFA\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/role/resourcesShow</a>
                </p>
                <p> 展示角色资源 </p>
                <p> admin/manage/authority/role/resourcesShow </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleId：角色ID</p>
            </td>
            <td>
                <p> menuTree-菜单树（同菜单列表）</p>
                <p> roleId：角色ID</p>
                <p> roleName：角色名称</p>
                <p> roleMenuIds：角色拥有的菜单数组</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.修改保存角色资源 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/role/resourcesEdit?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{roleId:\'7C2B8CB22BDA486D9AF58FF435077EFA\',menuIds:[\'Q6Q7\',\'12\']}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/role/resourcesEdit</a>
                </p>
                <p> 修改保存角色资源 </p>
                <p> admin/manage/authority/role/resourcesEdit </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleId：角色ID</p>
                <p> menuIds：菜单ID数组</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>7.角色删除 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/role/del?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{roleId:\'7C2B8CB22BDA486D9AF58FF435077EFA\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/role/del</a>
                </p>
                <p> 角色删除 </p>
                <p> admin/manage/authority/role/del </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleId：角色ID</p>
            </td>
            <td>
            </td>
        </tr>
        <!--</editor-fold>-->

        <!--<editor-fold desc="用户">-->
        <tr>
            <td colspan="4">操作员管理接口 《cmd 必须添加项，每一个接口都添加 - show：查询、展示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- edit : 修改、新增、删除</td>
        </tr>
        <tr>
            <td>
                <p>1.操作人列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/user/list?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/user/list</a>
                </p>
                <p> 操作人列表 </p>
                <p> admin/manage/authority/user/list </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> userName：用户名称</p>
            </td>
            <td>
                <p> NICKNAME_：用户名称</p>
                <p> APP_USER_NAME_：用户账户</p>
                <p> APP_GROUP_NAME_：角色名称</p>
                <p> IP_：登录IP</p>
                <p> CREATE_TIME_：登录事件</p>
                <p> STATE_：状态</p>
                <p> APP_USER_ID_：用户主键</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.展示操作人信息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/user/show?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\',data:{userId:\'21ab311a0a814c7b9fa48d941df23f73\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/user/show</a>
                </p>
                <p> 展示操作人信息 </p>
                <p> admin/manage/authority/user/show </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> userId：用户主键</p>
            </td>
            <td>
                <p> NICKNAME_：用户名称</p>
                <p> APP_USER_NAME_：用户账户</p>
                <p> APP_GROUP_NAME_：角色名称</p>
                <p> STATE_：状态</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.操作员新增 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/user/add?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{userName:\'测试用户1\',userAccount:\'test01\',userPassWord:\'test01\',state:\'OPEN\',roleId:\'f940e119cc3945a2b2982fdf431f794e\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/user/add</a>
                </p>
                <p> 操作员新增 </p>
                <p> admin/manage/authority/user/add </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> userName：用户名称</p>
                <p> userAccount：用户账户</p>
                <p> userPassWord：用户密码</p>
                <p> roleId：角色ID</p>
                <p> state：状态</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.操作员修改 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/user/edit?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{userId:\'7C2B8CB22BDA486D9AF58FF435077EFA\',userName:\'测试角色1\',userPassWord:\'test01\',state:\'OPEN\',roleId:\'7C2B8CB22BDA486D9AF58FF435077EFA\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/user/edit</a>
                </p>
                <p> 操作员修改 </p>
                <p> admin/manage/authority/role/edit </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleId：角色ID</p>
                <p> userId：用户ID</p>
                <p> userName：用户名称</p>
                <p> userPassWord：用户密码</p>
                <p> state：状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>6

                    .操作人员删除 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/authority/user/del?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{userId:\'c205da6ed3104b0db09eba58bd91b319\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/user/del</a>
                </p>
                <p> 操作人员删除 </p>
                <p> admin/manage/authority/user/del </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> userId：用户ID</p>
            </td>
            <td>
            </td>
        </tr>
        <!--</editor-fold>-->

        <!--<editor-fold desc="账户管理">-->
        <tr>
            <td colspan="4">账户管理接口 《cmd 必须添加项，每一个接口都添加 - show：查询、展示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- edit : 修改、新增、删除</td>
        </tr>
        <tr>
            <td>
                <p>1.充值卡管理员信息 </p>
                <p> - 下级管理首页信息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/info?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/agent/info</a>
                </p>
                <p> 充值卡管理员信息 </p>
                <p> /ibm/admin/manage/card/agent/info </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> adminInfo - 管理员信息</p>
                <p> APP_USER_NAME_：用户账户</p>
                <p> APP_GROUP_NAME_：角色名称</p>
                <p> IS_ADD_：允许添加子代</p>
                <p> PARENT_USER_NAME_：上级代理</p>
                <p> STATE_：状态</p>
                <p> adminPrices - 管理员默认价格</p>

                <p> CARD_TREE_ID_：卡种ID</p>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_PRICE_：卡种价格</p>
                <p> SN_：排序</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.充值卡管理下级列表 </p>
                <p> 类似于菜单 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/subList?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\',data:{userId:\'21ab311a0a814c7b9fa48d941df23f73\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/agent/subList</a>
                </p>
                <p> 充值卡管理下级列表 </p>
                <p> ibm/admin/manage/card/agent/subList </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> userId：用户主键</p>
            </td>
            <td>
                <p> APP_USER_ID_：下级代理主键</p>
                <p> USER_NAME_：下级代理名称</p>
                <p> PARENT_USER_ID_：父级代理主键</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.子充值卡管理员信息 </p>
                <p> - 点击子代理显示信息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/subInfo?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\',data:{subAgentId:\'21ab311a0a814c7b9fa48d941df23f73\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/agent/subInfo</a>
                </p>
                <p> 充值卡管理员信息 </p>
                <p> /ibm/admin/manage/card/agent/info </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> subAgentId：子代理主键</p>
            </td>
            <td>
                <p> adminInfo - 管理员信息</p>
                <p> APP_USER_NAME_：用户账户</p>
                <p> APP_GROUP_NAME_：角色名称</p>
                <p> IS_ADD_：允许添加子代</p>
                <p> PARENT_USER_NAME_：上级代理</p>
                <p> STATE_：状态</p>
                <p> adminPrices - 管理员默认价格</p>

                <p> CARD_TREE_ID_：卡种ID</p>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_PRICE_：卡种价格</p>
                <p> SN_：排序</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.新增存充值卡管理员 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/save?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{accountName:\'test01\',password:\'test01\',state:\'OPEN\',isAdd:\'true\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/agent/save</a>
                </p>
                <p> 保存充值卡管理员 </p>
                <p> ibm/admin/manage/card/agent/save </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> accountName：用户名称</p>
                <p> password：用户密码</p>
                <p> isAdd：是否允许添加子代（true/false）</p>
                <p> state：状态(OPEN/CLOSE)</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.修改充值卡管理员基本信息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/editInfo?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{subAgentId:\'test01\',password:\'test01\',state:\'OPEN\',isAdd:\'true\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/user/editInfo</a>
                </p>
                <p> 修改充值卡管理员基本信息 </p>
                <p> ibm/admin/manage/card/agent/editInfo </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> subAgentId：用户名称(为空则修改自己)</p>
                <p> password：用户密码</p>
                <p> isAdd：是否允许添加子代（true/false）</p>
                <p> state：状态(OPEN/CLOSE)</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.修改充值卡管理员价格信息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/editPrice?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{subAgentId:\'test01\',password:\'test01\',state:\'OPEN\',isAdd:\'true\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/authority/user/editPrice</a>
                </p>
                <p> 修改充值卡管理员价格信息 </p>
                <p> ibm/admin/manage/card/agent/editPrice </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> subAgentId：用户名称(为空则修改自己)</p>
                <p> priceInfos：价格信息JSONArray</p>
                <p>[{cardTreeId:'',cardTreePrice:0},{cardTreeId:'',cardTreePrice:0}]</p>

            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>7.上级给自己设置的价格列表 </p>
                <p> - 上级给自己设置的价格列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/price?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/agent/price</a>
                </p>
                <p> 充值卡管理员信息 </p>
                <p> /ibm/admin/manage/card/agent/price </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> CARD_TREE_ID_：卡种ID</p>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_PRICE_：卡种价格</p>
                <p> SN_：排序</p>
            </td>
        </tr>
        <!--</editor-fold>-->

        <!--<editor-fold desc="客户端管理">-->
        <tr>
            <td colspan="4">客户端管理接口 《cmd 必须添加项，每一个接口都添加 - show：查询、展示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- edit : 修改、新增、删除</td>
        </tr>

        <tr>
            <td>
                <p>1.获取客户机列表信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/list</a>
                </p>
                <p> 获取所有的客户机信息</p>
                <p> /ibm/admin/manage/client/list</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> ip：注册IP</p>
                <p> clientCode：客户端编码</p>
                <p> pageIndex：页数</p>
                <p> pageSize：条数</p>
            </td>
            <td>
                <p> ip：注册IP</p>
                <p> clientCode：客户端编码</p>
                <p> {REGISTER_IP_：注册ip,CLIENT_CODE_：客户端编码,</p>
                <p> CLIENT_CAPACITY_MAX_：客户端最大容量,</p>
                <p> CLIENT_CAPACITY_：客户端使用容量,</p>
                <p> UPDATE_TIME_LONG_：末次检测时间,</p>
                <p> STATE_：状态}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.客户端容量修改表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/form</a>
                </p>
                <p> 客户端容量修改表单</p>
                <p> /ibm/admin/manage/client/form</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
            </td>
            <td>
                <p> ip：注册IP</p>
                <p> clientCode：客户端编码</p>
                <p> CLIENT_CAPACITY_MAX_：客户端最大容量</p>
                <p> STATE_：状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.客户端容量设置《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/edit</a>
                </p>
                <p> 客户端容量设置</p>
                <p> /ibm/admin/manage/client/edit</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
                <p> state：状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.删除客户端《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/del?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/del</a>
                </p>
                <p> 删除客户端</p>
                <p> /ibm/admin/manage/client/del</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.客户机心跳控制《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/heartbeat?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/heartbeat</a>
                </p>
                <p> 客户机心跳控制</p>
                <p> /ibm/admin/manage/client/heartbeat</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
                <p> heartbeatState：心跳状态（OPEN/CLOSE）</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.客户机切换表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/clientReplaceForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/clientReplaceForm</a>
                </p>
                <p> 客户机切换表单</p>
                <p> /ibm/admin/manage/client/clientReplaceForm</p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> CLIENT_CODE_：客户端编码</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>7.切换客户机《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/clientReplace?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/clientReplace</a>
                </p>
                <p> 切换客户机</p>
                <p> /ibm/admin/manage/client/clientReplace</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> sendClientCode：发送端客户机编码</p>
                <p> receiveClientCode：接收端客户机编码</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>8.盘口迁移表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicapMigrateForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicapMigrateForm</a>
                </p>
                <p> 盘口迁移表单</p>
                <p> /ibm/admin/manage/client/handicapMigrateForm</p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> customers：类型（MEMBER/AGENT）</p>
                <p> handicapCodes：盘口编码</p>
                <p> clientList：客户机编码</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>9.盘口迁移《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicapMigrate?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicapMigrate</a>
                </p>
                <p> 盘口迁移</p>
                <p> /ibm/admin/manage/client/handicapMigrate</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> customer：类型（MEMBER/AGENT）</p>
                <p> handicapCode：盘口编码</p>
                <p> sendClientCode：发送端客户机编码</p>
                <p> receiveClientCode：接收端客户机编码</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>10.盘口容量列表信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicap/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicap/list</a>
                </p>
                <p> 盘口容量列表信息</p>
                <p> /ibm/admin/manage/client/handicap/list</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
            </td>
            <td>
                <p> CLIENT_CODE_：客户端编码</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> CAPACITY_HANDICAP_MAX_：客户端盘口最大容量</p>
                <p> CAPACITY_HANDICAP_：盘口使用容量</p>
                <p> CAPACITY_HA_MAX_：客户端代理最大容量</p>
                <p> CAPACITY_HA_：代理使用容量</p>
                <p> CAPACITY_HM_MAX_：客户端会员最大容量</p>
                <p> CAPACITY_HM_：会员使用容量</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>11.盘口容量修改表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicap/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicap/form</a>
                </p>
                <p> 盘口容量修改表单</p>
                <p> /ibm/admin/manage/client/handicap/form</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
                <p> handicapCode：盘口编码</p>
            </td>
            <td>
                <p> CLIENT_CODE_：客户端编码</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> CAPACITY_HANDICAP_MAX_：客户端盘口最大容量</p>
                <p> CAPACITY_HA_MAX_：客户端代理最大容量</p>
                <p> CAPACITY_HM_MAX_：客户端会员最大容量</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>12.盘口容量设置《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicap/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicap/edit</a>
                </p>
                <p> 盘口容量设置</p>
                <p> /ibm/admin/manage/client/handicap/edit</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
                <p> handicapCode：盘口编码</p>
                <p> capacityHandicapMax：盘口最大容量</p>
                <p> capacityHaMax：代理最大容量</p>
                <p> capacityHmMax：会员最大容量</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>13.新增盘口容量信息《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicap/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicap/save</a>
                </p>
                <p> 新增盘口容量信息</p>
                <p> /ibm/admin/manage/client/handicap/save</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
                <p> handicapCode：盘口编码</p>
                <p> capacityHandicapMax：盘口最大容量</p>
                <p> capacityHaMax：代理最大容量</p>
                <p> capacityHmMax：会员最大容量</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>14.删除盘口容量信息《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicap/del?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicap/del</a>
                </p>
                <p> 删除盘口容量信息</p>
                <p> /ibm/admin/manage/client/handicap/del</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
                <p> handicapCode：盘口编码</p>
            </td>
            <td>
            </td>
        </tr>

        <!--</editor-fold>-->



        <!--<editor-fold desc="基础配置">-->
        <tr>
            <td colspan="4">基础配置接口 《cmd 必须添加项，每一个接口都添加 - show：查询、展示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- edit : 修改、新增、删除</td>
        </tr>

        <tr>
            <td>
                <p>1.获取盘口列表信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/list</a>
                </p>
                <p> 获取盘口列表信息</p>
                <p> /ibm/admin/manage/handicap/list</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapCode：盘口名称</p>
                <p> handicapCategory：盘口类别</p>
                <p> handicapType：盘口类型</p>
                <p> pageIndex：页数</p>
                <p> pageSize：条数</p>
            </td>
            <td>
                <p> handicapCode：盘口名称</p>
                <p> handicapCategory：盘口类别</p>
                <p> handicapType：盘口类型</p>
                <p> pageIndex：页数</p>
                <p> pageSize：条数</p>
                <p> handicapInfos：{IBM_HANDICAP_ID_：盘口id,HANDICAP_NAME_：盘口名称,</p>
                <p> HANDICAP_CODE_：盘口code,</p>
                <p> HANDICAP_CATEGORY_：盘口类别,</p>
                <p> HANDICAP_TYPE_：盘口类型,HANDICAP_WORTH_T_：盘口价值,CAPACITY_：已使用容量,</p>
                <p> CAPACITY_MAX_：总容量,AVAILABLE_GAME_:可用游戏,STATE_：状态}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.获取盘口表单信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/form</a>
                </p>
                <p> 获取盘口表单信息</p>
                <p> /ibm/admin/manage/handicap/form</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapId：盘口ID</p>
            </td>
            <td>
                <p> handicapInfo：盘口名称</p>
                <p> availableGame：可用游戏</p>
                <p> allGame：获取所有游戏</p>
                <p> handicapInfo：{IBM_HANDICAP_ID_：盘口id,HANDICAP_NAME_：盘口名称,</p>
                <p> HANDICAP_CODE_：盘口code,</p>
                <p> HANDICAP_CATEGORY_：盘口类别,</p>
                <p> HANDICAP_TYPE_：盘口类型,HANDICAP_WORTH_T_：盘口价值,</p>
                <p> STATE_：状态}</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>3.修改盘口信息《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/edit</a>
                </p>
                <p> 修改盘口信息</p>
                <p> /ibm/admin/manage/handicap/edit</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapId：盘口id</p>
                <p> handicapType：盘口类型</p>
                <p> handicapWorth：盘口价值</p>
                <p> handicapGame：盘口游戏</p>
                <p> state：盘口状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.新增盘口《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/save</a>
                </p>
                <p> 新增盘口</p>
                <p> /ibm/admin/manage/handicap/save</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapCode：盘口名称/盘口code</p>
                <p> handicapType：盘口类型</p>
                <p> handicapCategory：盘口类别</p>
                <p> handicapWorth：盘口价值</p>
                <p> handicapGame：盘口游戏</p>
                <p> sn：次序</p>
                <p> version：版本</p>
                <p> handicapState：盘口状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.盘口详情列表《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/item/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/item/list</a>
                </p>
                <p> 盘口详情列表</p>
                <p> /ibm/admin/manage/handicap/item/list</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapId：盘口ID</p>
            </td>
            <td>
                <p> HANDICAP_CODE_：盘口名称</p>
                <p> HANDICAP_CATEGORY_：盘口类别</p>
                <p> handicapItems：{IBM_HANDICAP_ITEM_ID_:盘口详情id,HANDICAP_URL_:网址,HANDICAP_CAPTCHA_:验证码,</p>
                <p> UPDATE_TIME_LONG_:最后使用时间}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.盘口详情表单信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/item/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/item/form</a>
                </p>
                <p> 盘口详情表单信息</p>
                <p> /ibm/admin/manage/handicap/item/form</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapItemId：盘口详情id</p>
            </td>
            <td>
                <p> HANDICAP_URL_：盘口地址</p>
                <p> HANDICAP_CAPTCHA_：验证码</p>
                <p> HANDICAP_ITEM_ID_：盘口详情id</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>7.盘口详情修改《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/item/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/item/edit</a>
                </p>
                <p> 盘口详情修改</p>
                <p> /ibm/admin/manage/handicap/item/edit</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapItemId：盘口详情id</p>
                <p> handicapUrl：网址</p>
                <p> handicapCaptcha：验证码</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>8.删除盘口详情《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/item/del?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/item/del</a>
                </p>
                <p> 盘口详情修改</p>
                <p> /ibm/admin/manage/handicap/item/del</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapItemId：盘口详情id</p>
            </td>
            <td>
            </td>
        </tr>


        <tr>
            <td>
                <p>9.游戏列表《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/game/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/game/list</a>
                </p>
                <p> 游戏列表</p>
                <p> /ibm/admin/manage/game/list</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> gameCode：游戏code</p>
            </td>
            <td>
                <p> gameCode：游戏code</p>
                <p> gameInfo：{GAME_ID_:游戏id,GAME_NAME_:游戏名称,GAME_CODE_:游戏code,</p>
                <p> ICON_:游戏图标,DRAW_TIME_:开奖时间,STATE_:状态}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>10.游戏表单页面《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/game/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/game/form</a>
                </p>
                <p> 游戏表单页面</p>
                <p> /ibm/admin/manage/game/form</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> gameId：游戏id</p>
            </td>
            <td>
                <p>{GAME_ID_:游戏id,GAME_NAME_:游戏名称,GAME_CODE_:游戏code,</p>
                <p> ICON_:游戏图标,DRAW_TIME_:开奖时间,STATE_:状态,REP_GRAB_TABLE_NAME_:开奖数据表名,REP_DRAW_TABLE_NAME_:开奖结果表名}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>11.游戏修改《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/game/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/game/edit</a>
                </p>
                <p> 游戏修改</p>
                <p> /ibm/admin/manage/game/edit</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> gameId：游戏id</p>
                <p> gameCode：游戏code</p>
                <p> icon：图标</p>
                <p> drawTime：开奖时间</p>
                <p> sn：次序</p>
                <p> repGrabTableName：开奖数据表名</p>
                <p> repDrawTableName：开奖结果表名</p>
                <p> state：状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>12.添加游戏《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/game/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/game/save</a>
                </p>
                <p> 添加游戏</p>
                <p> /ibm/admin/manage/game/save</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> gameName：游戏id</p>
                <p> gameCode：游戏code</p>
                <p> icon：图标</p>
                <p> drawTime：开奖时间</p>
                <p> sn：次序</p>
            </td>
            <td>
            </td>
        </tr>

        <!--</editor-fold>-->

        <!--<editor-fold desc="用户角色信息">-->
        <tr>
            <td colspan="4">用户角色信息 《cmd 必须添加项，每一个接口都添加 - show：查询、展示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- edit : 修改、新增、删除</td>
        </tr>

        <tr>
            <td>
                <p>1.用户角色信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/role/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/role/list</a>
                </p>
                <p> 用户角色信息</p>
                <p> /ibm/admin/manage/user/role/list</p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> IBM_EXP_ROLE_ID_：主键</p>
                <p> ROLE_NAME_：角色名称</p>
                <p> ROLE_CODE_：角色编码</p>
                <p> ROLE_LEVEL_：角色等级</p>
                <p> GAME_NAMES_：可用游戏</p>
                <p> MEMBER_HANDICAP_NAMES_：可用会员盘口</p>
                <p> AGENT_HANDICAP_NAMES_：可用代理盘口</p>
                <p> MEMBER_ONLINE_MAX_：会员最大在线数量</p>
                <p> AGENT_ONLINE_MAX_：代理最大在线数量</p>
                <p> HM_ONLINE_MAX_：单盘口会员最大在线数量</p>
                <p> HA_ONLINE_MAX_：单盘口代理最大在线数量</p>
                <p> STATE_：状态</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.用户角色信息展示《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/role/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/role/form</a>
                </p>
                <p> 用户角色信息展示</p>
                <p> /ibm/admin/manage/user/role/form</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> expRoleId：用户角色主键</p>
                <p> （可为空，为空则是新增界面，非空则是修改）</p>
            </td>
            <td>
                <p> memberHandicaps：会员盘口信息</p>
                <p> agentHandicaps：代理盘口信息</p>
                <p> gameInfos：游戏信息</p>
                <p> expRoleInfo：角色信息（同列表页面）</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.用户角色信息保存《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/role/save</a>
                </p>
                <p> 用户角色信息保存</p>
                <p> /ibm/admin/manage/user/role/save</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleName：角色名称（*）</p>
                <p> roleCode：角色编码（*）</p>
                <p> roleLevel：角色等级</p>
                <p> gameCodes：可用游戏</p>
                <p> memberHandicapCodes：会员盘口</p>
                <p> agentHandicapCodes：代理盘口</p>
                <p> memberOnlineMax：会员在线数</p>
                <p> agentOnlineMax：代理最大数</p>
                <p> hmOnlineMax：单盘会员最大数</p>
                <p> haOnlineMax：单盘代理最大数</p>
                <p> state：状态（*）</p>
                <p> 所有的Codes 是一个以 , 逗号，作为连接附的字符串</p>
            </td>
            <td>

            </td>
        </tr>
        <tr>
            <td>
                <p>4.用户角色信息修改《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/role/edit</a>
                </p>
                <p> 用户角色信息修改</p>
                <p> /ibm/admin/manage/user/role/edit</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> expRoleId：角色主键（*）</p>
                <p> 不传 roleCode 其余同保存</p>
            </td>
            <td>

            </td>
        </tr>
        <!--</editor-fold>-->


        <!--<editor-fold desc="盘口封盘时间信息>-->
        <tr>
            <td colspan="4">盘口封盘时间信息 《cmd 必须添加项，每一个接口都添加 - show：查询、展示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- edit : 修改、新增、删除</td>
        </tr>

        <tr>
            <td>
                <p>1.盘口封盘时间列表《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/seal/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/seal/list</a>
                </p>
                <p> 盘口封盘时间列表</p>
                <p> /ibm/admin/manage/client/seal/list</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapCode：盘口编码</p>
                <p> gameCode：游戏编码</p>

            </td>
            <td>
                <p> handicaps：盘口列表</p>
                <p> games：游戏列表</p>
                <p> （查询条件为空时，回传上面信息）</p>
                <p> IBM_CONFIG_ID_：配置主键</p>
                <p> HANDICAP_NAME：盘口名称</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> GAME_NAME_：游戏名称</p>
                <p> SEAL_TIME_：封盘时间</p>
                <p> STATE_：状态</p>

            </td>
        </tr>
        <tr>
            <td>
                <p>2.封盘时间详情《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/seal/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/seal/form</a>
                </p>
                <p> 封盘时间详情</p>
                <p> /ibm/admin/manage/client/seal/form</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> configId：配置主键</p>
                <p> （可为空，为空则是新增界面，非空则是修改）</p>
            </td>
            <td>
                <p> handicaps：盘口列表</p>
                <p> games：游戏列表</p>
                <p> sealTimeInfo：封盘时间信息（同列表页面）</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.封盘时间保存《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/seal/save</a>
                </p>
                <p> 封盘时间保存</p>
                <p> /ibm/admin/manage/client/seal/save</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapCode：盘口编码（*）</p>
                <p> gameCode：游戏编码（*）</p>
                <p> sealTime：封盘时间（*）</p>
                <p> state：状态（*）</p>
            </td>
            <td>

            </td>
        </tr>
        <tr>
            <td>
                <p>4.封盘时间修改《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/seal/edit</a>
                </p>
                <p> 用户角色信息修改</p>
                <p> /ibm/admin/manage/client/seal/edit</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> configId：配置主键（*）</p>
                <p> sealTime：封盘时间（*）</p>
                <p> state：状态（*）</p>
            </td>
            <td>

            </td>
        </tr>
        <!--</editor-fold>-->


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
