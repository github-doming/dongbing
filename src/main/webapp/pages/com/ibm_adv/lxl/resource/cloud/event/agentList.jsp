<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title></title>
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

                        <button class="btn btn-primary" data-toggle="modal" data-target="#myModal${loop.count-1}">
                                点击查看
                        </button>
                        <!-- 模态框 -->
                        <div class="modal fade" id="myModal${loop.count-1}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog" style="width: 80%;">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                            &times;
                                        </button>
                                        <h4 class="modal-title" id="myModalLabel">
                                            <button type="button" class="btn btn-darkblue" data-toggle="modal" data-target="#addModal${loop.count-1}">添加</button>-----------${item.HANDICAP_NAME_}
<!------------------------------------------------------------------------- 添加模态框 -->
                                            <div class="modal fade" id="addModal${loop.count-1}" tabindex="-2" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <form action="/a/ibm/admin/hadicapGame/save1" method="post">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                                    &times;
                                                                </button>
                                                                <h4 class="modal-title" id="myModalLabel${loop.count-1}">
                                                                    ${item.HANDICAP_NAME_}
                                                                </h4>
                                                            </div>
                                                            <div class="modal-body">
                                                                <div class="input-group">
                                                                    <span class="input-group-addon" id="basic-addon1">选择游戏</span>
                                                                    <input type="text" autocomplete="off" class="form-control browsers" placeholder="请选择或者输入一项游戏" name="GAME_NAME_AND_GAME_CODE" aria-describedby="basic-addon1" list="browsers1" required="required">
                                                                    <datalist id="browsers1">
                                                                        <c:forEach items="${list1}" var="item2" varStatus="loop1">
                                                                            <option >${item2.GAME_NAME_}-----${item2.GAME_CODE_}</option>
                                                                        </c:forEach>
                                                                    </datalist>
                                                                </div><br>

                                                                <div class="input-group">
                                                                    <span class="input-group-addon">封盘时间</span>
                                                                    <input type="number" οninput="if(value.length>2)value=value.slice(0,2)" placeholder="请输入不超过两位的数字" autocomplete="off" class="form-control browsers" name="CLOSE_TIME" required="required">

                                                                </div><br>

<%--                                                                <div class="input-group">--%>
<%--                                                                    <span class="input-group-addon" id="basic-addon2">游戏编码</span>--%>
<%--                                                                    <input type="text" autocomplete="off"  class="form-control browsers" name="GAME_CODE_" aria-describedby="basic-addon2" list="browsers2" required="required">--%>
<%--                                                                    <datalist id="browsers2">--%>
<%--                                                                        <c:forEach items="${list1}" var="item2" varStatus="loop1">--%>
<%--                                                                            <option value=${item2.GAME_CODE_}>--%>
<%--                                                                        </c:forEach>--%>
<%--                                                                    </datalist>--%>
<%--                                                                </div><br>--%>

                                                                <div class="input-group">
                                                                    <span class="input-group-addon" id="basic-addon9">描述　　</span>
                                                                    <textarea name="DESC_" class="form-control" aria-describedby="basic-addon9" rows="4" required="required"></textarea>
                                                                </div><br>

                                                            </div>
                                                            <div class="modal-footer">
                                                                <input type="hidden" name="id" value=${item.IBM_HANDICAP_ID_} >
                                                                <input type="hidden" name="HANDICAP_NAME_" value=${item.HANDICAP_NAME_} >
                                                                <input type="hidden" name="HANDICAP_CODE_" value=${item.HANDICAP_CODE_} >
                                                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                                                <button type="submit" class="btn btn-primary">提交更改</button>
                                                            </div>
                                                        </form>
                                                    </div><!-- /.modal-content -->
                                                </div><!-- /.modal -->
                                            </div>
                                        </h4>
                                    </div>
                                    <div class="modal-body" style="min-height:500px;">
                                        <c:forEach items="${lists[loop.count-1]}" var="item1" varStatus="gameloop">
                                            <div class="modal-body">
<!------------------------------------------------------------------------- 游戏展示 -->
                                                ${item1.GAME_NAME_}
                                                <button style="position: absolute; right: 70px; top: 0px;" data-toggle="modal" data-target="#updateModal${loop.count}${gameloop.count}"
                                                        type="button" class="btn btn-primary">修改</button>
<!------------------------------------------------------------------------- 修改游戏模态框 -->
                                                    <div class="modal fade" id="updateModal${loop.count}${gameloop.count}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <form action="/a/ibm/admin/hadicapGame/update1" method="post">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                                            &times;
                                                                        </button>
                                                                        <h4 class="modal-title" id="myModalLabel${gameloop.count}">
                                                                                ${item.HANDICAP_NAME_}
                                                                        </h4>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon" id="basic-addon7">游戏名称</span>
                                                                            <input type="text" class="form-control" name="GAME_NAME_" aria-describedby="basic-addon7" required="required" value=${item1.GAME_NAME_}>
                                                                        </div><br>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon">封盘时间</span>
                                                                            <input type="number" οninput="if(value.length>2)value=value.slice(0,2)" placeholder="请输入不超过两位的数字" autocomplete="off" class="form-control browsers" name="CLOSE_TIME" required="required"  value=${item1.CLOSE_TIME}>
                                                                        </div><br>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <input type="hidden" name="IBM_HANDICAP_GAME_ID_" value=${item1.IBM_HANDICAP_GAME_ID_}>
<%--                                                                            <input type="button" name="Submit" onclick="javascript:history.go(-1);" value="返回上一页">--%>
                                                                        <button type="submit" class="btn btn-default" data-dismiss="modal">关闭</button>
                                                                        <button type="submit" class="btn btn-primary">提交更改</button>
                                                                    </div>
                                                                </form>
                                                            </div><!-- /.modal-content -->
                                                        </div><!-- /.modal -->
                                                    </div>
                                                <form action="/a/ibm/admin/hadicapGame/delete" method="post">
                                                    <input type="hidden" name="IBM_HANDICAP_GAME_ID_" value=${item1.IBM_HANDICAP_GAME_ID_}>
                                                    <button type="submit" style="position: absolute; right: 0px; top: 0px;" class="btn btn-danger">删除</button>
                                                </form>
                                                <hr style="filter: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color="#6f5499" size="3" />
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                        <button type="button" class="btn btn-primary">提交更改</button>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal -->
                        </div>


<%--                        <select style="width: 120px; height: 35px; line-height: 35px; padding: 5px; ">--%>
<%--                            <c:forEach items="${lists[loop.count-1]}" var="item1">--%>
<%--                                <option style="color: #FF0000;">${item1.GAME_NAME_}</option>--%>
<%--                            </c:forEach>--%>
<%--                        </select>--%>
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

</body>
</html>