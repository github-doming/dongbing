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
<button style="margin: 20px 0px 10px 2.5%;" class="btn btn-browm" onclick="save()">添加游戏</button>

<table id="topic_title" style="width:95%;table-layout:fixed; margin: 0px auto; text-align: center;" id="id_table"
       class="table  table-bordered table-hover">

    <thead>
        <tr>
            <th style="width: 150px;text-align: center;">游戏名称</th>
            <th style="width: 150px;text-align: center;">游戏编码</th>
            <th style="width: 150px;text-align: center;">游戏图标</th>
            <th style="width: 150px;text-align: center;">开奖时间</th>
            <th style="width: 150px;text-align: center;">次序</th>
            <th style="width: 200px;text-align: center;">游戏状态</th>
            <th style="text-align: center;">操作</th>
        </tr>
    </thead>
    <tbody class='body'>
    <c:forEach items="${gameLists}" var="gameList">
        <tr>
            <td style="width: 150px;">${gameList.GAME_NAME_}</td>
            <td style="width: 150px;">${gameList.GAME_CODE_}</td>
            <td style="width: 150px;"><img style="width: 50px; height: 50px;" src="${pageContext.request.contextPath}${gameList.ICON_}" ></td>
            <td style="width: 150px;">${gameList.DRAW_TIME_}</td>
            <td style="width: 150px;">${gameList.SN_}</td>
            <td style="width: 200px;">
                &nbsp; <c:choose>
                    <c:when test="${'OPEN' eq gameList.STATE_}">开启</c:when>
                    <c:when test="${'CLOSE' eq gameList.STATE_}">关闭</c:when>
                </c:choose>
            </td>
            <td>
                <button class="btn btn-brown" onclick="edit('${gameList.IBM_GAME_ID_}','${gameList.GAME_NAME_}',
                        '${gameList.GAME_CODE_}','${gameList.ICON_}','${gameList.DRAW_TIME_}','${gameList.SN_}')">修改</button>
                <button class="btn btn-danger" onclick="del('${gameList.IBM_GAME_ID_}')">删除</button>
            </td>
        </tr>

    </c:forEach>
    </tbody>

</table>

<script>
    //更新游戏信息1
    function edit(gameId,gameName,gameCode,gameIcon,drawTime,gameSn) {
        $('.gameName').text(gameName);
        $('#gameName').val(gameName);
        $('#gameCode').val(gameCode);
        $('#gameIcon').val(gameIcon);
        $('#drawTime').val(drawTime);
        $('#gameSn').val(gameSn);
        $('#gameId').val(gameId);
        $('#updateGame').modal("show",true);
    }
    //更新游戏信息2
    function updateGame() {
        if(confirm("是否确认修改？")){
            var _from = $("#gameForm");
            $.post(_from.attr("action"), _from.serialize(), function (data) {
                if (data.success) {
                    window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/game/list";
                } else {
                    alert(data.message);
                }
            }, "json");
        }
    }

    //删除游戏信息
    function del(gameId) {
        if(confirm("将移除有关该游戏的所有信息！！！")){
            $.getJSON("${pageContext.request.contextPath}/ibm/admin/manage/game/delGame?gameId=" + gameId, function (data) {
                if (!data.success) {
                    alert(data.message);
                } else {
                    window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/game/list";
                }
            });
        }
    }

    //添加游戏信息1
    function save() {
        $.getJSON("${pageContext.request.contextPath}/ibm/admin/manage/game/no", {
        }, function (data) {
            if (data.success) {
                console.log(data.data);
                var content = $("select#gameList");
                content.empty();
                $("<option style='display: none' value=''></option>").appendTo(content);
                $.each(data.data, function (index, map) {
                    var opt = $("<option></option>").appendTo(content);
                    opt.val(map.name);
                    opt.text(map.name);
                    opt.attr("data", map.code);
                });
                $('#saveGame').modal("show",true);
            } else {
                alert(data.msg);
            }
        });




    }
    //添加游戏信息2
    function saveGame() {
        var _from = $("#saveGameForm");
        $.post(_from.attr("action"), _from.serialize(), function (data) {
            if (data.success) {
                window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/game/list";
            } else {
                alert(data.message);
            }
        }, "json");
    }

    function selectHandicap(_this) {
        var obj = _this.value;
        $("#gameList option").each(function () {
            if (this.value === obj) {
                $(".gameName").val($(this).text());
                $(".gameCode").val($(this).attr("data"));
            }
        });
    }

</script>


<!------------------------------------------------------------------------- 修改游戏 -->
<div class="modal fade" style="margin-left: -10%;" id="updateGame"
     tabindex="-2" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/ibm/admin/manage/game/updateGame" id="gameForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times;</button>
                    <h4 class="modal-title">
                       <span class="gameName"></span> 修改
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group"><span class="input-group-addon">游戏名称</span>
                        <input type="text" class="form-control" id="gameName" name="gameName" required="required">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">游戏编码</span>
                        <input type="text" class="form-control" id="gameCode" name="gameCode" readonly>
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">游戏图标</span>
                        <input type="text" class="form-control" id="gameIcon" name="gameIcon" required="required">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">开奖时间</span>
                        <input type="number" class="form-control" id="drawTime" name="drawTime" required="required">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">游戏次序</span>
                        <input type="number" class="form-control" id="gameSn" name="gameSn" required="required">
                    </div>
                    <br>
                    <input type="hidden" name="gameId" id="gameId">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭 </button>
                    <button type="button" onclick="updateGame()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 修改游戏END -->


<!------------------------------------------------------------------------- 添加游戏 -->
<div class="modal fade" style="margin-left: -10%;" id="saveGame"
     tabindex="-2" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/ibm/admin/manage/game/saveGame" id="saveGameForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times;</button>
                    <h4 class="modal-title">
                        <span class="gameName"></span> 修改
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group"><span class="input-group-addon">游戏名称　　</span>
                        <select type="text" id="gameList" name="gameName" class="form-control browsers"
                                onchange="selectHandicap(this)">
                        </select>
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">游戏编码　　</span>
                        <input type="text" class="form-control gameCode" name="gameCode" readonly>
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">游戏图标　　</span>
                        <input type="text" class="form-control" name="gameIcon" required="required">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">开奖数据表名</span>
                        <input type="text" class="form-control" name="repGrabTableName" required="required">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">开奖结果表名</span>
                        <input type="text" class="form-control" name="repDrawTableName" required="required">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">开奖时间　　</span>
                        <input type="number" class="form-control" name="drawTime" required="required">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">游戏次序　　</span>
                        <input type="number" class="form-control" name="gameSn" required="required">
                    </div>
                    <br>
                    <input type="hidden" name="gameId">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭 </button>
                    <button type="button" onclick="saveGame()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 添加游戏END -->

</body>
</html>
