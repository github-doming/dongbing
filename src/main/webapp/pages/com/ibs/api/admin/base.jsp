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
            <td colspan="4">IBS后台管理-主页面验证接口</td>
        </tr>
       <tr>
            <td>
                <p>1.判断用户访问权限</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/main/index?json={token:\'64e03c1450234e168537df37ed4cd9d1\',name:\'aa1234\'');"
                    > http://IP:PORT/PROJECT/ibs/sys/main/index</a>
                </p>
                <p> 判断用户访问权限</p>
            </td>
            <td>
                <p> name：用户名</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.加载主菜单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/main/menu/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/main/menu/list</a>
                </p>
                <p> 加载主菜单</p>
            </td>
            <td>
            </td>
            <td>
                <p>id : id，APP_MENU_NAME_ : 菜单名，APP_MENU_CODE_ : 菜单编码，ADMIN_PIC_ : 图标</p>
                <p>htmlUrl : html路径，jsUrl : js路径，SN_ : 序号，pid : 父Id，STATE_ : 状态，STATE_ : 状态，</p>
                <p>subMenu：{子菜单信息}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.用户登出</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/main/logout?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/main/logout</a>
                </p>
                <p> 用户登出</p>
            </td>
            <td>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td colspan="4">IBS后台管理-基础配置--盘口</td>
        </tr>

        <tr>
            <td>
                <p>1.盘口列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/handicap/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/handicap/list</a>
                </p>
                <p> 盘口列表</p>
            </td>
            <td>
                <p> handicapName : 盘口名称</p>
                <p> handicapType : 盘口类型</p>
                <p> pageIndex : 当前页</p>
                <P> pageSize: 页大小 </P>
            </td>
            <td>
                <p> handicapName : 盘口名称</p>
                <p> handicapType : 盘口类型</p>
                <p> IBSP_HANDICAP_ID_ : 盘口Id</p>
                <p> HANDICAP_NAME_ : 盘口名称</p>
                <p> HANDICAP_CODE_ : 盘口Code</p>
                <p> HANDICAP_TYPE_ : 盘口类型</p>
                <p> CAPACITY_ : 已使用容量</p>
                <p> CAPACITY_MAX_ : 总容量</p>
                <p> STATE_ : 状态</p>
                <p> HANDICAP_WORTH_T_ : 盘口价值</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.盘口容量修改表单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/handicap/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/client/handicap/form</a>
                </p>
                <p> 盘口容量修改表单</p>
            </td>
            <td>
                <p> handicapId：盘口id</p>
            </td>
            <td>
                <p> HANDICAP_NAME_：盘口名称</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> HANDICAP_TYPE_：盘口类型</p>
                <p> HANDICAP_WORTH_T_：盘口价值</p>
                <p> STATE_：盘口状态</p>
                <p> gameInfos{GAME_CODE_:游戏code,GAME_NAME_:游戏名称}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.修改盘口信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/handicap/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/handicap/edit</a>
                </p>
                <p> 修改盘口信息</p>
            </td>
            <td>
                <p> handicapId：盘口id</p>
                <p> handicapType：盘口类型</p>
                <p> handicapWorth：盘口价值</p>
                <p> state：盘口状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.新增盘口</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/handicap/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/handicap/save</a>
                </p>
                <p> 新增盘口</p>
                <p> /ibs/sys/manage/handicap/save</p>
            </td>
            <td>
                <p> handicapCode：盘口名称/盘口code</p>
                <p> handicapType：盘口类型</p>
                <p> handicapIcon：盘口图标</p>
                <p> handicapWorth：盘口价值</p>
                <p> handicapGame：盘口游戏</p>
                <p> sn：次序</p>
                <p> version：版本</p>
                <p> handicapState：盘口状态</p>
                <p> handicapUrl：导航地址</p>
                <p> handicapCaptcha：验证码</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td colspan="4">IBS后台管理-基础配置--盘口---盘口详情</td>
        </tr>
        <tr>
            <td>
                <p>1.盘口详情列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/handicap/item/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/handicap/item/list</a>
                </p>
                <p> 盘口详情列表</p>
            </td>
            <td>
                <p> handicapId：盘口ID</p>
                <p> pageIndex：</p>
                <p> pageSize：</p>
            </td>
            <td>
                <p> HANDICAP_CODE_：盘口名称</p>
                <p> rows：{IBSP_HANDICAP_ITEM_ID_:盘口详情id,HANDICAP_URL_:网址,HANDICAP_CAPTCHA_:验证码,</p>
                <p> UPDATE_TIME_LONG_:最后使用时间}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.盘口详情表单信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/handicap/item/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/handicap/item/form</a>
                </p>
                <p> 盘口详情表单信息</p>
            </td>
            <td>
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
                <p>3.盘口详情修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/handicap/item/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/handicap/item/edit</a>
                </p>
                <p> 盘口详情修改</p>
            </td>
            <td>
                <p> handicapItemId：盘口详情id</p>
                <p> handicapUrl：网址</p>
                <p> handicapCaptcha：验证码</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.删除盘口详情</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/handicap/item/del?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/handicap/item/del</a>
                </p>
                <p> 盘口详情修改</p>
            </td>
            <td>
                <p> handicapItemId：盘口详情id</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td colspan="4">IBS后台管理-基础配置--盘口---盘口游戏</td>
        </tr>
        <tr>
            <td>
                <p>1.盘口游戏列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/handicap/game/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/handicap/game/list</a>
                </p>
                <p> 盘口游戏列表</p>
            </td>
            <td>
                <p> handicapId：盘口id</p>
            </td>
            <td>
                <p> handicapName：盘口名称</p>
                <p> gameInfo：{GAME_ID_:游戏id,GAME_NAME_:游戏名称,GAME_CODE_:游戏code,</p>
                <p> ICON_:游戏图标,DRAW_TIME_:开奖时间,STATE_:状态}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.盘口游戏表单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/handicap/game/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/handicap/game/form</a>
                </p>
                <p> 盘口游戏表单</p>
            </td>
            <td>
                <p> handicapGameId：盘口游戏id</p>
                <p> handicapId：盘口id</p>
            </td>
            <td>
                <p>HANDICAP_GAME_ID_:游戏ID</p>
                <p>HANDICAP_GAME_NAME_:游戏名称</p>
                <p>GAME_CODE_:游戏CODE</p>
                <p>ICON_:图标</p>
                <p>GAME_DRAW_TYPE_:开奖时间</p>
                <p>CLOSE_TIME_:封盘时间</p>
                <p>STATE_:状态</p>
                <p>SN_:序号</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.盘口游戏修改/p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/handicap/game/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/handicap/game/edit</a>
                </p>
                <p> 盘口游戏修改</p>
            </td>
            <td>
                <p>handicapGameId:盘口游戏id</p>
                <p>handicapGameName:盘口游戏名</p>
                <p>type:游戏类型</p>
                <p>icon:盘口游戏图标</p>
                <p>state:状态</p>
                <p>sn:次序</p>
                <p>closeTime:封盘时间</p>
                <p>drawTime:开奖时间</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.添加盘口游戏</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/handicap/game/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/handicap/game/save</a>
                </p>
                <p> 添加盘口游戏</p>
            </td>
            <td>
                <p>handicapId:盘口id</p>
                <p>gameCodes:游戏codes</p>
            </td>
            <td>
            </td>
        </tr>



        <tr>
            <td colspan="4">IBS后台管理-基础配置--游戏---游戏列表</td>
        </tr>
        <tr>
            <td>
                <p>1.游戏列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/game/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/game/list</a>
                </p>
                <p> 游戏列表</p>
            </td>
            <td>
                <p> gameName：游戏名称</p>
            </td>
            <td>
                <p> gameName：游戏名称</p>
                <p> gameInfo：{GAME_ID_:游戏id,GAME_NAME_:游戏名称,GAME_CODE_:游戏code,</p>
                <p> ICON_:游戏图标,DRAW_TIME_:开奖时间,STATE_:状态}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.游戏表单页面</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/game/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/game/form</a>
                </p>
                <p> 游戏表单页面</p>
            </td>
            <td>
                <p> gameId：游戏id</p>
            </td>
            <td>
                <p>GAME_ID_:游戏ID</p>
                <p>GAME_NAME_:游戏名称</p>
                <p>GAME_CODE_:游戏CODE</p>
                <p>ICON_:图标</p>
                <p>DRAW_TIME_:开奖时间</p>
                <p>STATE_:状态</p>
                <p>REP_DRAW_TABLE_NAME_:开奖表</p>
                <p>SN_:序号</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.游戏修改/p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/game/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/game/edit</a>
                </p>
                <p> 游戏修改</p>
            </td>
            <td>
                <p>GAME_ID_:游戏ID</p>
                <p>ICON_:图标</p>
                <p>DRAW_TIME_:开奖时间</p>
                <p>STATE_:状态</p>
                <p>REP_DRAW_TABLE_NAME_:开奖表</p>
                <p>SN_:序号</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.添加游戏</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/game/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/game/save</a>
                </p>
                <p> 添加游戏</p>
            </td>
            <td>
                <p>gameCode:游戏code</p>
                <p>gameName:游戏名称</p>
                <p>icon:图标</p>
                <p>drawTime:开奖时间</p>
                <p>sn:序号</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>5. 游戏方案添加表单页面</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/gamePlan/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/gamePlan/form</a>
                </p>
                <p> 游戏方案添加表单页面</p>
            </td>
            <td>
                <p> GAME_ID_：游戏ID</p>
            </td>
            <td>
                <p> PLAN_NAME_：方案名称</p>
                <p> PLAN_CODE_：方案编码</p>
                <p> SN_：序号</p>
                <p> STATE_：状态</p>
                <p> has：是否有</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.游戏方案新增</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/gamePlan/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/gamePlan/save</a>
                </p>
                <p> 游戏方案新增</p>
            </td>
            <td>
                <p> gameId：游戏Id</p>
                <p> planCodes："方案1,方案2,..."</p>
            </td>
            <td>
            </td>
        </tr>


        <tr>
            <td colspan="4">IBS后台管理-基础配置--游戏---游戏方案</td>
        </tr>

        <tr>
            <td>
                <p>1.游戏方案列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/game/plan/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/game/plan/list</a>
                </p>
                <p> 游戏方案列表</p>
            </td>
            <td>
                <p> gameId：游戏id</p>
                <p> pageIndex：</p>
                <p> pageSize：</p>
            </td>
            <td>
                <p> PLAN_GAME_ID_：方案游戏ID</p>
                <p> PLAN_NAME_：方案名称</p>
                <p> PLAN_CODE_：方案编码</p>
                <p> SN_：序号</p>
                <p> STATE_：状态</p>
                <p> PLAN_TYPE_：类型</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2. 游戏方案详情表单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/game/plan/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/game/plan/form</a>
                </p>
                <p> 游戏方案详情表单</p>
            </td>
            <td>
                <p> PLAN_GAME_ID_：方案游戏ID</p>
            </td>
            <td>
                <p> PLAN_NAME_：名称</p>
                <p> PLAN_CODE_：方案编码</p>
                <p> PLAN_GAME_ID_：方案游戏ID</p>
                <p> GAME_PLAN_NAME_：游戏方案ID</p>
                <p> SN_：序号</p>
                <p> STATE_：状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.游戏方案修改/p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/game/plan/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/game/plan/edit</a>
                </p>
                <p> 游戏修改</p>
            </td>
            <td>
                <p> planGameId：游戏方案id</p>
                <p> planName：方案名称</p>
                <p> sn：序号</p>
                <p> state：状态 (单独修改状态时 sn、planName传空)</p>
            </td>
            <td>
            </td>
        </tr>



        <tr>
            <td colspan="4">IBS后台管理-基础配置--方案</td>
        </tr>
        <tr>
            <td>
                <p>1.方案列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/plan/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/plan/list</a>
                </p>
                <p> 方案列表</p>
            </td>
            <td>
                <p> planName：方案名称</p>
                <p> pageIndex：</p>
                <p> pageSize：</p>
            </td>
            <td>
                <p> IBSP_PLAN_ID_：方案ID</p>
                <p> PLAN_NAME_：方案名称</p>
                <p> PLAN_CODE_：方案编码</p>
                <p> SN_：序号</p>
                <p> STATE_：状态</p>
                <p> DESC_：备注</p>
                <p> PLAN_WORTH_：方案价值</p>
                <p> PLAN_TYPE_：类型</p>
                <p> AVAILABLE_GAME_TYPE_：游戏类别</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.方案表单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/plan/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/plan/form</a>
                </p>
                <p> 方案详情表单</p>
            </td>
            <td>
                <p> PLAN_ID_：方案ID</p>
            </td>
            <td>
                <p> PLAN_ID_：方案ID</p>
                <p> PLAN_NAME_：方案名称</p>
                <p> PLAN_CODE_：方案编码</p>
                <p> SN_：序号</p>
                <p> STATE_：状态</p>
                <p> DESC_：备注</p>
                <p> PLAN_WORTH_：方案价值</p>
                <p> PLAN_TYPE_：类型</p>
                <p> AVAILABLE_GAME_TYPE_：游戏类别</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.方案修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/plan/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/plan/edit</a>
                </p>
                <p> 方案修改</p>
            </td>
            <td>
                <p> PLAN_ID_：方案ID</p>
                <p> PLAN_NAME_：方案名称</p>
                <p> SN_：序号</p>
                <p> STATE_：状态</p>
                <p> DESC_：备注</p>
                <p> PLAN_WORTH_：方案价值</p>
                <p> PLAN_TYPE_：类型</p>
                <p> planTableName：方案表名</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.方案新增</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/plan/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/plan/save</a>
                </p>
                <p> 方案新增</p>
            </td>
            <td>
                <p> PLAN_CODE_：方案编码</p>
                <p> PLAN_NAME_：方案名称</p>
                <p> PLAN_TYPE_：类型</p>
                <p> SN_：序号</p>
                <p> DESC_：备注</p>
                <p> PLAN_WORTH_：方案价值</p>
                <p> planTableName：方案详情表名</p>
                <p> availableGameType：适用游戏类型</p>
                <p> planGroupDataInfo：方案组数据</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.方案状态修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/plan/update?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/plan/update</a>
                </p>
                <p> 方案状态修改</p>
            </td>
            <td>
                <p> PLAN_ID_：方案ID</p>
                <p> STATE_：状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.方案详情</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/plan/item/info?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/plan/item/info</a>
                </p>
                <p> 方案详情</p>
            </td>
            <td>
                <p> planId：方案Id</p>
            </td>
            <td>
                <p> PLAN_ID_：方案ID</p>
                <p> PLAN_NAME_：方案名称</p>
                <p> PLAN_CODE_：方案编码</p>
                <p> SN_：序号</p>
                <p> STATE_：状态</p>
                <p> DESC_：备注</p>
                <p> PLAN_WORTH_：方案价值</p>
                <p> PLAN_TYPE_：类型</p>
                <p> AVAILABLE_GAME_TYPE_：游戏类别</p>

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
