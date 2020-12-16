<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title></title>
    <style type="text/css">

    </style>
</head>
<body>

<div style="display: none;" id="id_div_msg">正在提交中...</div>

<div style="border: 1px #666666 solid; background: #666666; margin-top: 20px;
            margin-left: 2.5%; padding: 5px; width: 100px; text-align: center; border-radius: 2px; ">
    <a style=" width: 100%; height: 100%; color: #FFFFFF;" href="/a/ibm/admin/hadicap/save1">
        <div>
            添加
        </div>
    </a>
</div>

    <table id="topic_title" style="width:95%;table-layout:fixed; margin: 0px auto;" id="id_table"
           class="table  table-bordered table-hover">
        <caption></caption>
        <thead>
        <tr>
            <th style="width: 150px;">盘口名称</th>
            <th style="width: 150px;">盘口编码</th>
            <th style="width: 150px;">盘口说明</th>
            <th style="width: 150px;">盘口类别</th>
            <th style="width: 150px;">盘口类型</th>
            <th style="width: 150px;">盘口价值</th>
            <th style="width: 150px;">盘口游戏</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody class='body'>
            <c:forEach items="${list}" var="item" varStatus="loop">
                <tr >
                    <td style="width: 150px;">${item.HANDICAP_NAME_}</td>
                    <td style="width: 150px;">${item.HANDICAP_CODE_}</td>
                    <td style="width: 150px;">${item.HANDICAP_EXPLAIN_}</td>
                    <td style="width: 150px;">
                        <c:choose>
                            <c:when test="${item.HANDICAP_CATEGORY_ == 'AGENT'}">
                                代理
                            </c:when>
                            <c:when test="${item.HANDICAP_CATEGORY_ == 'MEMBER'}">
                                会员
                            </c:when>
                            <c:otherwise>
                                会员
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="width: 150px;">
                        <c:choose>
                            <c:when test="${item.HANDICAP_TYPE_ == 'FREE'}">
                                免费
                            </c:when>
                            <c:when test="${item.HANDICAP_TYPE_ == 'CHARGE'}">
                                收费
                            </c:when>
                            <c:otherwise>
                                管理员
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="width: 150px;">${item.HANDICAP_WORTH_T_}</td>
                    <td style="width: 150px;">

<%--                        <button id="hidd1" style="border: 5px #FFFFFF solid; border-radius: 5px; background-color: #FFE4C4; color: #2d2d2d;" class="btn btn-primary" data-toggle="modal" data-target="#myModal${loop.count-1}">--%>

<%--                        </button>--%>
                        <a href="#" onclick="f('ycbc')"; >点击查看</a>
<%--                        <div id="ycbc" style="display:none">隐藏的内容</div>--%>
                        <!-- 模态框 -->
                        <div id="ycbc"  style="display:none">
                            隐藏的内容
<%--                            <iframe src="/a/pages/com/ibm_adv/lxl/resource/cloud/event/selectGame.jsp" />--%>
                        </div>



<!----------------------------------------------------------------->
                    </td>
                    <td>
                        <form style="float: left;" action="/a/ibm/admin/hadicap/update1" method="get">
                            <input type="hidden" name="id" value=${item.IBM_HANDICAP_ID_} >
                            <input type="hidden" name="HANDICAP_NAME_" value=${item.HANDICAP_NAME_} >
                            <input type="hidden" name="HANDICAP_CODE_" value=${item.HANDICAP_CODE_} >
                            <input type="hidden" name="HANDICAP_EXPLAIN_" value=${item.HANDICAP_EXPLAIN_} >
                            <input type="hidden" name="HANDICAP_TYPE_" value=${item.HANDICAP_TYPE_} >
                            <input type="hidden" name="HANDICAP_WORTH_T_" value=${item.HANDICAP_WORTH_T_} >
                            <input type="hidden" name="HANDICAP_CATEGORY_" value=${item.HANDICAP_CATEGORY_} >
                            <input type="hidden" name="HANDICAP_VERSION_" value=${item.HANDICAP_VERSION_} >
                            <input type="hidden" name="CREATE_USER_" value=${item.CREATE_USER_} >
                            <input type="hidden" name="DESC_" value=${item.DESC_} >
                            <input type="hidden" name="CREATE_TIME_"  value=${item.CREATE_TIME_}>
                            <input type="hidden" name="CREATE_TIME_LONG_"  value=${item.CREATE_TIME_LONG_}>
                            <input type="submit" value="修改" />
                        </form>
                        <form style="float: left; margin-left: 10px;" action="/a/ibm/admin/hadicap/delete" method="post">
                            <input type="hidden" name="id" value=${item.IBM_HANDICAP_ID_} >
                            <input type="submit" onclick= 'return confirm( "确定要删除吗? ") ' value="刪除" />
                        </form>

                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br/>
<script type="text/javascript" src="js/public-method.js"></script>
<script type="text/javascript" src="js/layer.js"></script>
<script type="text/javascript">
    var layer = new Layer({
        layerclass:"layerContianer2",//存放页面的容器类名
        width:500,
        height:350,
        alerttit:"提示框提示",
        setOverflow:"overflow-y:none",//设置滚动的属性 overflow-y：设置竖向  overflow-x:设置横向
        callback:function(){
            $(".inp_btn").click(function(){
                layer.show();
            });
        }
    });
    $("input").click(function(){
        layer.show();
    });
</script>
</body>
</html>