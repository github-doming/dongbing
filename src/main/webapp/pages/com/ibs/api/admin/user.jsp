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
            <td colspan="4">IBS后台管理-用户管理--用户</td>
        </tr>

        <tr>
            <td>
                <p>1.用户列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/list</a>
                </p>
                <p> 用户列表</p>
            </td>
            <td>
                <p> userName : 用户名</p>
                <p> startTime : 开始时间</p>
                <p> endTime : 结束时间</p>
                <P> pageIndex : 当前页 </P>
                <P> pageSize : 页大小</P>
            </td>
            <td>
                <p> userName：用户名</p>
                <p> startTime：登录开始时间</p>
                <p> endTime：登录结束时间</p>
                <p> userInfos：{APP_USER_NAME_:用户名,NICKNAME_:用户名称,USEABLE_POINT_:可用点数,</p>
                <p> CREATE_TIME_LONG_:注册时间,END_TIME_:到期时间,MEMBER_HANDICAP_:会员盘口,</p>
                <p> onlineMember:在线会员数量,APP_USER_ID_:用户id,IP_:登录ip,STATE_:状态}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.用户新增页面表单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/form</a>
                </p>
                <p> 用户新增页面表单</p>
            </td>
            <td>
            </td>
            <td>
                <p> allGameInfo：{GAME_CODE_：游戏code，GAME_NAME_：游戏名}</p>
                <p> memberAllUsable：{HANDICAP_CODE_：盘口code}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.获取方案列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/plan/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/plan/list</a>
                </p>
                <p> 获取计划列表</p>
            </td>

            <td>
                <p> appUserId : 用户Id</p>
            </td>
            <td>
                <p> IBSP_PLAN_ID_：方案Id</p>
                <p> PLAN_NAME_：方案名</p>
                <p> SN_：序号</p>
                <p> PLAN_CODE_：方案编码</p>
                <p> hasPlan:用户是否拥有</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.用户新增提交</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/save</a>
                </p>
                <p> 用户新增提交</p>
            </td>
            <td>
                <p>accountName:用户名</p>
                <p>password:密码</p>
                <p>endTime:到期时间</p>
                <p>useablePoint:点数</p>
                <p>memberUsable:可用盘口</p>
                <p>gameUsable:可用游戏</p>
                <p>planUsable:可用方案（不设置默认为空）</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.用户信息表单页面</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/infoForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/infoForm</a>
                </p>
                <p> 用户信息表单页面</p>
            </td>
            <td>
                <p>appUserId:用户名</p>
            </td>
            <td>
                <p>ACCOUNT_NAME_:用户名</p>
                <p>END_TIME_:到期时间</p>
                <p>USEABLE_POINT_:点数</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.用户信息表单提交</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/infoSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/infoSet</a>
                </p>
                <p> 用户信息表单提交</p>
            </td>
            <td>
                <p>userId:用户id</p>
                <p>password:密码(不修改传空值)</p>
                <p>endTime:到期时间</p>
                <p>useablePoint:点数</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.用户删除</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/delete?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/delete</a>
                </p>
                <p> 用户删除</p>
            </td>
            <td>
                <p>appUserId:用户id</p>

            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>7.用户盘口设置表单信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/handicapSetForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/handicapSetForm</a>
                </p>
                <p> 用户盘口设置表单信息</p>
            </td>
            <td>
                <p>appUserId:用户id</p>
            </td>
            <td>
                <p>userName:用户名</p>
                <p>allGameInfo:可用游戏</p>
                <p>memberAllUsable:所有盘口</p>
                <p>memberInfos:可用盘口</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>8.用户盘口设置提交</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/handicapSetForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/handicapSetForm</a>
                </p>
                <p> 用户盘口设置表单信息</p>
            </td>
            <td>
                <p>appUserId:用户id</p>
                <p>avaiableGame:可用游戏</p>
                <p>memberUsable:可用盘口</p>
                <p>appUserId:用户id</p>
            </td>
            <td>
                <p>userName:用户名</p>
                <p>allGameInfo:可用游戏</p>
                <p>memberAllUsable:所有盘口</p>
                <p>memberInfos:可用盘口</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>9.设置方案列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/plan/set?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/plan/set</a>
                </p>
                <p> 设置方案列表</p>
            </td>

            <td>
                <p> appUserId : 用户Id</p>
                <p> planUsable :{PLAN_CODE_,PLAN_CODE_,PLAN_CODE_}</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td colspan="4">IBS后台管理-用户管理--会员</td>
        </tr>

        <tr>
            <td>
                <p>1.会员列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/member/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/member/member/list</a>
                </p>
                <p> 会员列表</p>
            </td>
            <td>
                <p>userName:用户名</p>
                <p>memberAccount:会员名</p>
                <p>handicapCode:盘口</p>
                <p>onlineState:在线状态</p>
                <P> pageIndex : 当前页 </P>
                <P> pageSize : 页大小</P>
            </td>
            <td>
                <p> userName：用户名</p>
                <p> memberAccount：会员名</p>
                <p> handicapCode：盘口code</p>
                <p> onlineState：在线状态</p>
                <p> pageIndex：页数</p>
                <p> pageSize：条数</p>

                <p> {HANDICAP_MEMBER_ID_:盘口会员id,APP_USER_NAME_:用户名,HANDICAP_CODE_:盘口code,MEMBER_ACCOUNT_:会员账号,</p>
                <p> HANDICAP_URL_:导航地址,HANDICAP_CAPTCHA_:验证码,STATE_:登录状态,</p>
                <p>availableGame:可用游戏}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.用户设置页面表单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/member/itemForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/member/itemForm</a>
                </p>
                <p> 用户设置页面表单</p>
            </td>
            <td>
                <p> handicapMemberId：盘口会员Id</p>
            </td>
            <td>
                <p> availableGame：会员可用游戏信息</p>

                <p> allHandicapGame：所有可用盘口游戏</p>
                <p> itemInfo{APP_USER_NAME_：用户名,MEMBER_ACCOUNT_:会员名称,HANDICAP_URL_:地址,HANDICAP_CAPTCHA_:验证码,</p>
                <p> HANDICAP_ID_:盘口ID, AUTO_LOGIN_STATE_：自动登录状态,}</p>
                <p>
                    handicapSet{BET_MERGER_：投注合并,BET_RATE_:投注比例,PROFIT_LIMIT_MAX_:止盈上限,BET_MERGER_:投注合并,BLAST_STOP_:炸停止</p>
                <p> PROFIT_LIMIT_MIN_:止盈下限,LOSS_LIMIT_MIN_:止损下限,RESET_TYPE_:重置类型</p>
                <p> RESET_PROFIT_MAX_:重置盈利上限,RESET_LOSS_MIN_:重置亏损下限,MODE_CUTOVER_STATE_:模式切换状态}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.会员真实模拟切换信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/member/modeCutover/info?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/member/modeCutover/info</a>
                </p>
                <p> 会员真实模拟切换信息</p>
            </td>
            <td>
                <p> handicapMemberId：盘口会员Id</p>
            </td>
            <td>
                <p> CUTOVER_GROUP_DATA_：切换组数据
                    {"0":{"CURRENT_":"REAL","CUTOVER_":"VIRTUAL","CUTOVER_LOSS_":"-100","CUTOVER_PROFIT_":"100","RESET_PROFIT_":"FALSE"}}</p>
                <p> CUTOVER_KEY_：切换key</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.会员真实模拟切换信息设置</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/member/modeCutover/set?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/member/modeCutover/set</a>
                </p>
                <p> 会员真实模拟切换信息设置</p>
            </td>
            <td>
                <p> handicapMemberId：盘口会员Id</p>
                <p> cutoverKey：切换key</p>
                <p> groupData：切换组数据</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.会员删除</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/member/delete?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/member/delete</a>
                </p>
                <p> 会员真实模拟切换信息设置</p>
            </td>
            <td>
                <p> handicapMemberId：盘口会员Id</p>
                <p> cutoverKey：切换key</p>
                <p> groupData：切换组数据</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.会员投注表单信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/member/betForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/member/betForm</a>
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
                <p> IS_AUTO_START_BET_：自动开始投注状态</p>
                <p> AUTO_START_BET_TIME_LONG_：自动开始投注时间</p>
                <p> IS_AUTO_STOP_BET_：自动停止状态</p>
                <p> IS_INVERSE_：反投</p>
                <p> INCREASE_STATE_：停止新增</p>
                <p> INCREASE_STOP_TIME_LONG_：停止新增时间</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>7.会员投注设置</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/member/betSet?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/member/betSet</a>
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
            <td colspan="4">IBS后台管理-用户管理--角色</td>
        </tr>
        <tr>
            <td>
                <p>1.角色管理列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/role/lis?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/role/lis</a>
                </p>
                <p> 角色管理列表 </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> IBSP_EXP_ROLE_ID_：角色ID</p>
                <p> ROLE_NAME_：角色名称</p>
                <p> ROLE_CODE_：角色编码</p>
                <p> ROLE_LEVEL_：角色等级</p>
                <p> GAME_NAMES_：可用游戏</p>
                <p> HANDICAP_CODES_：可用盘口</p>
                <p> PLAN_CODES_：可用方案</p>
                <p> ONLINE_MAX_：最大在线</p>
                <p> HM_ONLINE_MAX_：会员最大在线</p>
                <p> STATE_：状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.角色信息表单 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/role/form?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/role/form</a>
                </p>
                <p> 角色信息表单 </p>
            </td>
            <td>
                <p> expRoleId：角色Id</p>
            </td>
            <td>
                <p> expRoleId：角色ID</p>
                <p> ROLE_NAME_：角色名称</p>
                <p> ROLE_CODE_：角色编码</p>
                <p> ROLE_LEVEL_：角色等级</p>
                <p> GAME_NAMES_：可用游戏</p>
                <p> HANDICAP_CODES_：可用盘口</p>
                <p> PLAN_CODES_：可用方案</p>
                <p> ONLINE_MAX_：最大在线</p>
                <p> HM_ONLINE_MAX_：会员最大在线</p>
                <p> STATE_：状态</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.用户角色信息修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/role/edit</a>
                </p>
                <p> 用户角色信息修改</p>
                <p> /ibs/sys/manage/user/role/edit</p>
            </td>
            <td>
                <p> expRoleId：角色主键（*）</p>
                <p> 不传 roleCode 其余同保存</p>
            </td>
            <td>

            </td>
        </tr>
        <tr>
            <td>
                <p>4.用户角色信息保存</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/role/save</a>
                </p>
                <p> 用户角色信息保存</p>
                <p> /ibs/sys/manage/user/role/save</p>
            </td>
            <td>
                <p> roleName：角色名称（*）</p>
                <p> roleCode：角色编码（*）</p>
                <p> roleLevel：角色等级</p>
                <p> gameCodes：可用游戏</p>
                <p> handicapCodes：会员盘口</p>
                <p> planCodes：方案</p>
                <p> hmOnlineMax：单盘会员最大数</p>
                <p> onlineMax：单盘最大数</p>
                <p> state：状态（*）</p>
                <p> 所有的Codes 是一个以 , 逗号，作为连接附的字符串</p>
            </td>
            <td>

            </td>
        </tr>

        <tr>
            <td>
                <p>5.用户角色方案列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/role/plan/list</a>
                </p>
                <p> 用户角色方案列表</p>
                <p> /ibs/sys/manage/user/role/plan/list</p>
            </td>
            <td>
            </td>
            <td>
                <p> IBSP_PLAN_ID_：方案Id</p>
                <p> PLAN_NAME_：方案名</p>
                <p> SN_：序号</p>
                <p> PLAN_CODE_：方案编码</p>
                <p> hasPlan:用户是否拥有</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.设置角色方案列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/user/role/plan/set?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/user/role/plan/set</a>
                </p>
                <p> 设置角色方案列表</p>
            </td>

            <td>
                <p> roleId : 角色Id</p>
                <p> planUsable :{PLAN_CODE_,PLAN_CODE_,PLAN_CODE_}</p>
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
