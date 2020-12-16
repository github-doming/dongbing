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
        <%-- <tr>
             <td>
                 <p>1.首页主体框架</p>
             </td>
             <td>
                 <p>
                     <a href="javascript:void(0);"
                        onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/home/d/main.do?json={\'token\':\'1\'}');"
                     > http://IP:PORT/PROJECT/cloud/t/home/main.do</a>
                 </p>
             </td>
             <td>
                 <p>{token : "用户token"}</p>
             </td>
             <td>
                 <p>{code : "请求结果代码",</p>
                 <p>data : {handicapInfo:{HANDICAP_NAME_:盘口名称,HANDICAP_CODE_:盘口编码,HANDICAP_ICON_:盘口图标,SN_:盘口顺序},</p>
                 <p>gameInfo:{GAME_NAME_:游戏名称,GAME_CODE_:游戏code,ICON_:游戏图标},endTimeLong:软件到期时间戳,unread:未读消息数 },</p>
                 <p>message : "请求结果消息"} </p>
             </td>
         </tr>
         <tr>
             <td>
                 <p>2.首页主体信息</p>
             </td>
             <td>
                 <p>
                     <a href="javascript:void(0);"
                        onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/home/d/page.do?json={\'token\':\'1\'}');"
                     > http://IP:PORT/PROJECT/cloud/t/home/page.do</a>
                 </p>
             </td>
             <td>
                 <p>{token : "用户token"}</p>
             </td>
             <td>
                 <p>{code : "请求结果代码",</p>
                 <p>data : {userInfo:{NICKNAME_:用户,POINT_:点数,软件到期时间戳},</p>
                 <p>cmsInfo:[{IBM_CMS_TOPIC_ID_:消息id,TITLE_:消息标题,CREATE_TIME_LONG_:创建时间戳}],</p>
                 <p>recentLogin:[{HANDICAP_CODE_:盘口code,HANDICAP_NAME_:盘口名称}],</p>
                 <p>onHosting:[{HANDICAP_CODE_:盘口code,HANDICAP_NAME_:盘口名称}],</p>
                 <p>gamePlan:[{GAME_NAME_:游戏名称,GAME_CODE_:游戏code,ICON_游戏图标:,SN_:游戏排行,</p>
                 <p>planList:[{PLAN_NAME_:方案名称,STATE_:方案状态}]}]},</p>
                 <p>message : "请求结果消息"} </p>
             </td>
         </tr>
         <tr>
             <td>
                 <p>3.选择盘口用户账号</p>
             </td>
             <td>
                 <p>
                     <a href="javascript:void(0);"
                        onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/handicap_user/t/selectHandicapAccount.do?json={\'token\':\'1\',data:{\'HANDICAP_CODE_\':\'WS2\'}}');"
                     > http://IP:PORT/PROJECT/ibm/cloud/handicap_user/t/selectHandicapAccount.do</a>
                 </p>
             </td>
             <td>
                 <p>{token : "用户token",data:</p>
                 <p>{HANDICAP_CODE_ : 盘口编码}}</p>
             </td>
             <td>
                 <p>{code : "请求结果代码",</p>
                 <p>data : {onHostingInfo:[{HANDICAP_MEMBER_ID_:盘口会员Id,MEMBER_ACCOUNT_:会员账户}],</p>
                 <p>allAccount:[{HANDICAP_MEMBER_ID_:盘口会员Id,MEMBER_ACCOUNT_:会员账户}],</p>
                 <p>handicapMemberInfo:见打开盘口会员用户接口},</p>
                 <p>message : "请求结果消息"}</p>
             </td>
         </tr>
         <tr>
             <td>
                 <p>4.识别验证码</p>
             </td>
             <td>
                 <p>
                     <a href="javascript:void(0);"
                        onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/handicap_user/t/discernHandicap.do?json={\'token\':\'1\',data:{\'HANDICAP_URL\':\'http://sf33.qr68.us/\',\'HANDICAP_CAPTCHA\':\'az311\'}}');"
                     > http://IP:PORT/PROJECT/ibm/cloud/handicap_user/t/discernHandicap.do</a>
                 </p>
             </td>
             <td>
                 <p>{token : "用户token",data:</p>
                 <p>{HANDICAP_URL : 盘口路径,</p>
                 <p>HANDICAP_CAPTCHA:盘口验证码}}</p>
             </td>
             <td>
                 <p>{code : "请求结果代码",</p>
                 <p>data : 见打开盘口接口,</p>
                 <p>message : "请求结果消息"}</p>
             </td>
         </tr>
         <tr>
             <td>
                 <p>5.用户方案列表</p>
             </td>
             <td>
                 <p>
                     <a href="javascript:void(0);"
                        onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/ibm_plan_user/t/list.do?json={\'token\':\'1\',data:{\'GAME_CODE_\':\'PK10\'}}');"
                     > http://IP:PORT/PROJECT/ibm/cloud/ibm_plan_user/t/list.do</a>
                 </p>
             </td>
             <td>
                 <p>{token : "用户token",data:</p>
                 <p>{GAME_CODE_ : 游戏编码}}</p>
             </td>
             <td>
                 <p>{code : "请求结果代码",</p>
                 <p>data : [{PLAN_CODE_:方案编码,PLAN_NAME_:方案名称,MONITOR_PERIOD_:监控期数,BET_MODE_:投注模式,SN_:排序,STATE_:状态}],</p>
                 <p>message : "请求结果消息"}</p>
             </td>
         </tr>
         <tr>
             <td>
                 <p>6.用户方案详情列表</p>
             </td>
             <td>
                 <p>
                     <a href="javascript:void(0);"
                        onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/ibm_plan_user/t/show.do?json={\'token\':\'1\',data:{\'GAME_CODE_\':\'PK10\',PLAN_CODE_:\'PLAN_LOCATION_BET_NUMBER\'}}');"
                     > http://IP:PORT/PROJECT/ibm/cloud/ibm_plan_user/t/show.do</a>
                 </p>
             </td>
             <td>
                 <p>{token : "用户token",data:</p>
                 <p>{GAME_CODE_ : 游戏编码,</p>
                 <p>PLAN_CODE_:方案编码}}</p>
             </td>
             <td>
                 <p>{code : "请求结果代码",</p>
                 <p>data : {fundSwapMode:[{name:资金切换模式名称,code:编码,sn:排序}],betMode:[{name:投注模式名称,code:编码,sn:排序}],</p>
                 <p>periodRollMode：[{name:期期滚模式名称,code:编码,sn:排序}],planItemInfo:{ID_:方案详情id,NAME_:编码},</p>
                 <p>showData:{PROFIT_LIMIT_MAX_:止盈上限,LOSS_LIMIT_MIN_:止损下限,FUNDS_LIST_资金列表:,MONITOR_PERIOD__:监控期数,</p>
                 <p>FOLLOW_PERIOD__:跟随期数,BET_MODE__:投注模式,FUND_SWAP_MODE_:资金切换模式,PERIOD_ROLL_MODE_:期期滚模式,</p>
                 <p>FUNDS_LIST_ID_:资金方案列表id,PLAN_GROUP_DATA_:方案组数据}},</p>
                 <p>message : "请求结果消息"}</p>
             </td>
         </tr>
         <tr>
             <td>
                 <p>7.方案详情修改</p>
             </td>
             <td>
                 <p>
                     http://IP:PORT/PROJECT/ibm/cloud/ibm_plan_user/t/edit.do
                 </p>
             </td>
             <td>
                 <p>{token : "用户token",data:</p>
                 <p>{ID_:方案详情id,NAME_:编码,方案数据整体}}</p>
             </td>
             <td>
                 <p>{code : "请求结果代码",</p>
                 <p>message : "请求结果消息"}</p>
             </td>
         </tr>
         <tr>
             <td>
                 <p>8.方案详情状态修改</p>
             </td>
             <td>
                 <p>
                     http://IP:PORT/PROJECT/ibm/cloud/ibm_plan_user/t/editState.do
                 </p>
             </td>
             <td>
                 <p>{token : "用户token",data:</p>
                 <p>{GAME_CODE_:方案详情id,PLAN_CODE_:编码,</p>
                 <p>方案数据整体,STATE_:状态(OPEN,STOP,DEL)}}</p>
             </td>
             <td>
                 <p>{code : "请求结果代码",</p>
                 <p>message : "请求结果消息"}</p>
             </td>
         </tr>
        <tr>
            <td>
                <p>1.历史统计-游戏列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm?' +
                               'json={\'token\':\'9fda21eacd50451ba8060147389be7ba\',\'cmd\':\'GAME_LIST\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm</a>
                </p>
            </td>
            <td>
                <p>{token : "用户token",cmd:"GAME_LIST"}</p>
                <p>注：GAME_LIST:游戏列表</p>
            </td>
            <td>
                <p>{code : "请求结果代码",</p>
                <p>data : {GAME_NAME_:游戏名称 , GAME_CODE_:游戏code},</p>
                <p>message : "请求结果消息"}</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.历史统计-方案列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm?' +
                               'json={\'token\':\'9fda21eacd50451ba8060147389be7ba\',\'cmd\':\'PLAN_LIST\',\'data\':{\'gameCode\''+
                               ':\'PK10\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm</a>
                </p>
            </td>
            <td>
                <p>{token : "用户token",cmd:"PLAN_LIST",data :{gameCode:游戏code}}</p>
                <p>注：PLAN_LIST:方案列表</p>
            </td>
            <td>
                <p>{code : "请求结果代码",</p>
                <p>data : {PLAN_NAME_:方案名称 , PLAN_CODE_:方案code},</p>
                <p>message : "请求结果消息"}</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.历史统计-开始统计</p></td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm?' +
                               'json={\'token\':\'9fda21eacd50451ba8060147389be7ba\',\'cmd\':\'STATISTICS\',' +
                               'data:{\'startDay\':\'2019-06-10\',\'endDay\':\'2019-06-12\',\'startTime\':\'14:30\',' +
                               '\'endTime\':\'17:30\',\'statisticalState\':\'CLOSE\',\'gameCode\':\'XYFT\',\'planCode\':' +
                               '\'FOLLOW_TWO_SIDE\',\'resetState\':\'OPEN\'}}');">
                        http://IP:PORT/PROJECT/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm</a>
                </p>
            </td>
            <td>
                <p>{token : "用户token",cmd:"STATISTICS",data :{startDay:开始日期，</p>
                <p>endDay：结束日期，startTime：开始时间，endTime：结束时间，odds:自定义赔率</p>
                <p>gameCode：游戏code,planCode:方案code,statisticalState：统计状态，resetState:重置资金状态}}</p>
                <p>注：PLAN_LIST:方案列表</p>
            </td>
            <td>
                <p>{code : "请求结果代码",</p>
                <p>data : "{eventId:事件id，time：事件时间}",</p>
                <p>message : "请求结果消息"} </p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.历史统计-统计结果</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm?' +
                               'json={\'token\':\'9fda21eacd50451ba8060147389be7ba\',\'cmd\':\'RESULT\',\'data\':{\'eventId\''+
                               ':\'3a1e3780020540f18a60fe34ef49b9ea\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm</a>
                </p>
            </td>
            <td>
                <p>{token : "用户token",cmd:"RESULT",data :{eventId:事件id}}</p>
                <p>注：RESULT:统计结果</p>
            </td>
            <td>
                <p>{code : "请求结果代码",</p>
                <p>data : {general:统计总计，detail：统计明细,groupTitle:组标题},</p>
                <p>message : "请求结果消息"}</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.历史统计-统计结果Excel</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm?' +
                               'json={\'token\':\'903e130e18384c35ab274b73effffca4\',\'cmd\':\'EXCEL\',\'data\':{\'eventId\''+
                               ':\'563cde376e004984b04c6a79528e6a44\',\'state\':\'file\'}}');"
                    > http://IP:PORT/PROJECT/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm</a>
                </p>
            </td>
            <td>
                <p>{token : "用户token",cmd:"RESULT",data :{eventId:事件id}}</p>
                <p>注：RESULT:统计结果</p>
            </td>
            <td>
                <p>{code : "请求结果代码",</p>
                <p>data : {general:统计总计，detail：统计明细,groupTitle:组标题},</p>
                <p>message : "请求结果消息"}</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.历史统计-启动统计</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm?' +
                               'json={\'token\':\'64396807ca3a4eacb2c00d28eed2f01d\',\'cmd\':\'START\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm</a>

                    ?json={'token':'aa629176bf734d1583a7d41fed73f680','cmd':'START'}
                </p>
            </td>
            <td>
                <p>{token : "用户token",cmd:"START",data :{eventId:事件id}}</p>
                <p>注：RESULT:统计结果</p>
            </td>
            <td>
                <p>{code : "请求结果代码",</p>
                <p>data : {general:统计总计，detail：统计明细,groupTitle:组标题},</p>
                <p>message : "请求结果消息"}</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.历史统计-停止统计</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/servlet/ibm_plan_statistics/planStatistics.dm?' +
                               'json={\'token\':\'27d4f8455a6741c89e8b487cc791e394\',\'cmd\':\'STOP\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm</a>
                </p>
            </td>
            <td>
                <p>{token : "用户token",cmd:"STOP",data :{eventId:事件id}}</p>
                <p>注：RESULT:统计结果</p>
            </td>
            <td>
                <p>{code : "请求结果代码",</p>
                <p>data : {general:统计总计，detail：统计明细,groupTitle:组标题},</p>
                <p>message : "请求结果消息"}</p>
            </td>
        </tr>--%>

        <tr>
            <td>
                <p>0.启动历史统计</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('http://132.232.130.99:9090/service/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm?json={\'token\':\'e98fdb18d38a4787bc0ccb0a1996ab29\',\'cmd\':\'START\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm</a>
                </p>
            </td>
            <td>

            </td>
            <td>

            </td>
        </tr>
        <tr>

            <td>
                <p>1.方案用户信息-展示</p>
            </td>
            <td>
                <p>
                    <a
                    <%--href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/ibm_plan_user/show.dm?' +
                               'json={\'token\':\'27d4f8455a6741c89e8b487cc791e394\',\'cmd\':\'PROFIT_LIMIT\',\'data\':{\'GAME_CODE_\''+
                               ':\'PK10\',\'PLAN_CODE_\':\'LOCATION_BET_NUMBER\'}}');"--%>
                    > http://IP:PORT/PROJECT/ibm/app/ibm_plan_user/show.dm</a>
                </p>
            </td>
            <td>
                <p>{token : "用户token",cmd:"展示参数",data :</p>
                <p>{GAME_CODE_:游戏code,PLAN_CODE_:方案code}}</p>
                <p>展示参数说明</p>
                <p>PROFIT_LIMIT：止盈止损</p>
                <p>FUNDS_BET_RULE：投注规则和资金规则</p>
                <p>BET_ITEM：投注详情</p>
            </td>
            <td>
                <p>恒定参数：-做修改使用</p>
                <p>PLAN_CODE_：方案code ； ID_：方案详情id</p>

                <p>止盈止损：</p>
                <p>PROFIT_LIMIT_MAX_：止盈上限 ； LOSS_LIMIT_MIN_：止盈下限</p>
                <p>投注规则和资金规则：</p>
                <p>FUNDS_LIST_：资金列表 ； FUNDS_LIST_ID_：资金方案ID ; FOLLOW_PERIOD_：跟进期数</p>
                <p>MONITOR_PERIOD_：监控次数 ； BET_MODE_：投注模式 ; FUND_SWAP_MODE_：资金切换方式</p>
                <p>PERIOD_ROLL_MODE_：期期滚选项</p>
                <p>投注详情：</p>
                <p>PLAN_GROUP_DATA_：方案组数据</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.方案用户信息-修改</p>
            </td>
            <td>
                <p>
                    <a
                    <%--href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/ibm_plan_user/edit.dm?' +
                               'json={\'token\':\'27d4f8455a6741c89e8b487cc791e394\',\'cmd\':\'PROFIT_LIMIT\',\'data\':{\'PLAN_CODE_\''+
                               ':\'LOCATION_BET_NUMBER\',\'ID_\':\'03366c35519842b7b1c9afdb2b03e117\'}}');"--%>
                    > http://IP:PORT/PROJECT/ibm/app/ibm_plan_user/edit.dm</a>
                </p>
            </td>
            <td>
                <p>{token : "用户token",cmd:"修改参数",data :</p>
                <p>{PLAN_CODE_:方案code,ID_:方案详情id,修改的信息map}}</p>
                <p>修改入参与展示返参一致</p>
            </td>
            <td>

            </td>
        </tr>
        <tr>
            <td>
                <p>3.盘口用户信息-展示</p>
            </td>
            <td>
                <p>
                    <a
                    <%-- href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/ibm_hm_set_show/show.dm?' +
                               'json={\'token\':\'27d4f8455a6741c89e8b487cc791e394\',\'cmd\':\'GAME_SET\',\'data\':{\'HANDICAP_MEMBER_ID_\''+
                               ':\'LOCATION_BET_NUMBER\',\'GAME_CODE_\':\'03366c35519842b7b1c9afdb2b03e117\'}}');"--%>
                    > http://IP:PORT/PROJECT/ibm/app/ibm_hm_set_show/edit.dm</a>
                </p>
            </td>
            <td>
                <p>{token : "用户token",cmd:"展示参数",data :</p>
                <p>{HANDICAP_MEMBER_ID_:盘口会员ID,GAME_CODE_:游戏Code}}</p>
                <p>展示参数说明</p>
                <p>HANDICAP_SET：盘口设置(不包含GAME_CODE_)</p>
                <p>GAME_SET：盘口游戏设置</p>
            </td>
            <td>
                <p>盘口设置 ：</p>
                <p>BET_RATE_：投注比例 ； PROFIT_LIMIT_MAX_：止盈上限 ； LOSS_LIMIT_MIN_：止损下限</p>
                <p>PROFIT_LIMIT_MIN_：止盈下限 ； RESET_TYPE_：每天重置-1，自定义重置-2 </p>
                <p>RESET_PROFIT_MAX_：重置盈利上限 ； RESET_LOSS_MIN_：重置亏损下限 </p>
                <p>盘口游戏设置：</p>
                <p>BET_AUTO_STOP_：自动停止投注 ； BET_AUTO_STOP_TIME_：自动停止投注时间 </p>
                <p>BET_AUTO_START_：自动开始投注； BET_AUTO_START_TIME_：自动开始投注时间</p>
                <p>INCREASE_STATE_：新增状态； INCREASE_STOP_TIME_：停止新增时间</p>
                <p>BET_SECOND_：每期投注时刻； SPLIT_TWO_SIDE_AMOUNT_：双面分投额度</p>
                <p>SPLIT_NUMBER_AMOUNT_：号码分投额度</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.盘口用户信息-修改</p>
            </td>
            <td>
                <p>
                    <a
                    <%--href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/app/ibm_hm_set_show/edit.dm?' +
                               'json={\'token\':\'27d4f8455a6741c89e8b487cc791e394\',\'cmd\':\'PROFIT_LIMIT\',\'data\':{\'PLAN_CODE_\''+
                               ':\'LOCATION_BET_NUMBER\',\'ID_\':\'03366c35519842b7b1c9afdb2b03e117\'}}');"--%>
                    > http://IP:PORT/PROJECT/ibm/app/ibm_hm_set/edit.dm</a>
                </p>
            </td>
            <td>
                <p>{token : "用户token",cmd:"修改参数",data :</p>
                <p>{HANDICAP_MEMBER_ID_:盘口会员ID,GAME_CODE_:游戏Code}}</p>
                <p>修改入参与展示返参一致</p>
                <p>追加参数</p>
                <p>MERGE_SET：合并设置(不包含GAME_CODE_)</p>
                <p>BET_MODE_SET：投注模式设置</p>
            </td>
            <td>
                <p>合并设置 ：</p>
                <p>BET_MERGER_：是否合并（OPEN-CLOSE）</p>
                <p>投注模式设置：</p>
                <p>BET_MODE_：投注模式（REAL-VIRTUAL） </p>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.盘口会员游戏信息</p>
            </td>
            <td>
                <p>
                    <a
                    > http://IP:PORT/PROJECT/ibm/app/ibm_handicap_member/hm_game.dm</a>
                </p>
            </td>
            <td>
                <p>{token : "用户token",data :{HANDICAP_MEMBER_ID_:盘口会员ID}}</p>
            </td>
            <td>
                <p>GAME_NAME_:游戏名称,GAME_CODE_:游戏Code</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.获取投注记录</p>
            </td>
            <td>
                <p>
                    <a
                    > http://IP:PORT/PROJECT/ibm/app/ibm_handicap_member/refresh_bet_result.dm</a>
                </p>
            </td>
            <td>
                <p>{token : "用户token",data :{HANDICAP_MEMBER_ID_:盘口会员ID,GAME_CODE_:游戏Code,</p>
                <p>checkNum:获取的期数数量（可为空）}}</p>
            </td>
            <td>
                <p>PERIOD_:期数,betSum:投注数</p>
                <p>winCount:赢,failCount:亏</p>
                <p>betAmount:投注金额,profit:输赢金额</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>A.重置方案</p>
            </td>
            <td>
                <p>
                    <a> http://IP:PORT/PROJECT/ibm/pc/ibm_hm_set/replayPlan.do</a>
                </p>
                <p>原：/ibm/pc/ibm_hm_plan_exec_item/replayAll.do</p>
                <p>原：/ibm/pc/ibm_hm_plan_exec_item/replay.do</p>
                <p>不弹窗选择方案，直接重置所有</p>
            </td>
            <td>
                <p>{token : "用户token",cmd:"修改参数",data :</p>
                <p>{HANDICAP_MEMBER_ID_:盘口会员ID,GAME_CODE_:游戏Code}}</p>
                <p>修改参数说明</p>
                <p>REPLAY_ALL：重置所有(不包含GAME_CODE_)</p>
                <p>REPLAY_GAME：重置当前游戏</p>
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
