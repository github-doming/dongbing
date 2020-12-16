<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title></title>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
</head>
<body>
<div style="display: none;" id="id_div_msg">正在提交中...</div>

<div class="head">
    <div class="col-xs-10 col-xs-offset-1">
        <form class="form-inline head-form"
              action="${pageContext.request.contextPath}/ibm/admin/manage/handicap/list"
              style="padding:  15px 0 15px 0">
            <div class="input-group">
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-brown" onclick="init()">初始化内存信息</button>
                        </span>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">模糊搜索:</span>
                    <input type="text" class="form-control" id="in-se" placeholder="筛选关键词" style="width: 120px">
                </div>
            </div>
            <div style="float: right">
                <div class="form-group" style="padding:0 15px 0 15px">
                    <label for="se-ca">类别: </label>
                    <select name="HANDICAP_CATEGORY_" onchange="submitFrom()" id="se-ca">
                        <option value=""  <c:if test="${empty HANDICAP_CATEGORY_ }"> selected="selected"</c:if>>全部
                        </option>
                        <c:forEach items="${categories}" var="category" varStatus="loop">
                            <option value="${category.code}"
                                    <c:if test="${ category.code eq HANDICAP_CATEGORY_}"> selected="selected"</c:if> >${category.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="se-ty">类型: </label>
                    <select name="HANDICAP_TYPE_" onchange="submitFrom()" id="se-ty" style="width: 50px">
                        <option value=""  <c:if test="${empty HANDICAP_TYPE_ }"> selected="selected"</c:if>>全部</option>
                        <c:forEach items="${types}" var="type" varStatus="loop">
                            <option value="${type.code}"
                                    <c:if test="${ type.code eq HANDICAP_TYPE_}"> selected="selected"</c:if> >${type.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <input type="text" name="key" value="${key}" class="form-control" placeholder="查询关键词"
                               style="width: 120px;">
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-brown" onclick="submitFrom()">查询</button>
                        </span>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="content">
    <div class="col-xs-10 col-xs-offset-1">
        <table class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th style="width: 150px;text-align: center;">盘口名称</th>
                <th style="width: 100px;text-align: center;">盘口编码</th>
                <th style="width: 80px;text-align: center;">盘口类别</th>
                <th style="width: 80px;text-align: center;">盘口类型</th>
                <th style="width: 80px;text-align: center;">盘口价值</th>
                <th class='text-center'>盘口游戏</th>
                <th class='text-center'>盘口说明</th>
                <th style="width: 150px;text-align: center;">操作</th>
            </tr>
            </thead>
            <tbody class="handicap">
            <c:forEach items="${handicapInfos}" var="handicapInfo" varStatus="loop">
                <tr>
                    <td class="text-center">${handicapInfo.HANDICAP_NAME_}</td>
                    <td class="text-center">${handicapInfo.HANDICAP_CODE_}</td>
                    <td class="text-center">${handicapInfo.HANDICAP_CATEGORY_}</td>
                    <td class="text-center">${handicapInfo.HANDICAP_TYPE_}</td>
                    <td class="text-center">${handicapInfo.HANDICAP_WORTH_}</td>
                    <td class="text-center">${handicapInfo.GAME_NAMES_} </td>
                    <td class="text-center">${handicapInfo.HANDICAP_EXPLAIN_}</td>
                    <td style="padding: 4px;text-align: center;">
                        <div class="btn-group">
                            <button class="btn  btn-sm btn-brown dropdown-toggle " type="button" data-toggle="dropdown">
                                修改 <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" style="min-width: 100%;">
                                <li><a href="#" onclick="showGame('${handicapInfo.IBM_HANDICAP_ID_}')">
                                    <i class="glyphicon glyphicon-edit">盘口游戏</i></a></li>
                                <li><a href="#" onclick="showInfo('${handicapInfo.IBM_HANDICAP_ID_}')">
                                    <i class="glyphicon glyphicon-tasks">盘口信息</i></a></li>
                            </ul>
                            <button class="btn  btn-sm btn-danger" onclick="del('${handicapInfo.IBM_HANDICAP_ID_}')">删除</button>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div class="foot">

    <div class="col-xs-10 col-xs-offset-1">
        <a class="btn btn-default" href="${pageContext.request.contextPath}/ibm/admin/manage/handicap/info" role="button">添加</a>
    </div>

</div>

<!------------------------------------------------------------------------- 盘口游戏 -->
<div style="width: 60%; margin-left: 10%;" class="modal fade" id="handicapGameList" tabindex="-1" role="dialog">
    <div class="modal-dialog" style="width: 80%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    &times;
                </button>
                <input type="hidden" id="handicapId">
                <h4 class="modal-title">《<span class="handicapName"></span>》的游戏信息</h4>
            </div>
            <div class="modal-body" style="padding: 5px 15px 5px 15px;">
                <table class="table " style="margin-bottom: 0;">
                    <thead>
                    <tr>
                        <th class='text-center'>游戏名称</th>
                        <th class='text-center'>游戏编码</th>
                        <th class='text-center'>封盘时间</th>
                        <th class='text-center'>类&nbsp;&nbsp;型</th>
                        <th class='text-center'>投注执行表名</th>
                        <th class='text-center'>操作</th>
                    </tr>
                    </thead>
                    <tbody class='handicapGame'>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button style="position: absolute; left: 20px;" type="button" class="btn btn-darkblue"
                        onclick="handicapGameAddShow()">添加
                </button>
                <button type="button" class="btn btn-default" onclick="hideGame()">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 盘口游戏END -->


<!------------------------------------------------------------------------- 修改盘口游戏 -->
<div class="modal fade" id="handicapGameInfo" tabindex="-2" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/ibm/admin/manage/handicap/game/update"
                  class="updateGameForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"> &times;</button>
                    <h4 class="modal-title">修改《<span class="handicapName"></span>》的【<span class="gameName"></span>】游戏
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group"><span class="input-group-addon" id="basic-gameName">游戏名称</span>
                        <input type="text" class="form-control gameName" name="GAME_NAME"
                               aria-describedby="basic-gameName" required="required">
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">封盘时间</span>
                        <input type="number" oninput="if(value.length>2)value=value.slice(0,2)"
                               placeholder="请输入不超过两位的数字"
                               class="form-control browsers gameCloseTime" name="CLOSE_TIME">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">&nbsp;类&nbsp;&nbsp;&nbsp;&nbsp;型&nbsp;</span>
                        <input type="text" class="form-control gameType" name="TYPE">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">详情表名</span>
                        <input type="text" class="form-control gameTableName" name="TABLE_NAME">
                    </div>
                    <br>


                    <div class="input-group"><span class="input-group-addon" id="basic-gameIcon">游戏图标</span>
                        <input type="text" class="form-control gameIcon" name="ICON"
                        >
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon" id="basic-gameSn">游戏次序</span>
                        <input type="number" class="form-control gameSn" name="SN"
                               required="required">
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <input type="hidden" name="HANDICAP_GAME_ID_" class="handicapGameId">
                    <button class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" onclick="handicapGameUpdate()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 修改盘口游戏END -->


<!------------------------------------------------------------------------- 添加盘口游戏 -->
<div class="modal fade" style="margin-left: -10%;" id="handicapGameAdd" tabindex="-2" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/ibm/admin/manage/handicap/game/add"
                  class="handicapGameAddForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"> &times;</button>
                    <h4 class="modal-title">
                        添加《<span class="handicapName"></span>》游戏
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon">选择游戏</span>
                        <select type="text" id="gameList" name="GAME_ID_" class="form-control browsers"
                                placeholder="请选择或者输入一项游戏">
                        </select>
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">封盘时间</span>
                        <input type="number" oninput="if(value.length>2)value=value.slice(0,2)"
                               placeholder="请输入不超过两位的数字"
                               class="form-control browsers gameSaveCloseTime" name="CLOSE_TIME" required="required">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">&nbsp;类&nbsp;&nbsp;&nbsp;&nbsp;型&nbsp;</span>
                        <input type="text" class="form-control gameSaveType" name="TYPE"
                        >
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">详情表名</span>
                        <input type="text" class="form-control gameSaveTableName" name="TABLE_NAME"
                        >
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">游戏图标</span>
                        <input type="text" class="form-control gameSaveIcon" name="ICON">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">游戏次序</span>
                        <input type="number" oninput="if(value.length>2)value=value.slice(0,2)"
                               placeholder="请输入不超过两位的数字" class="form-control browsers gameSaveSn" name="SN"
                               required="required">
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">描述　　</span>
                        <textarea name="DESC_" class="form-control" rows="4"
                                  required="required"></textarea>
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <input type="hidden" class="HANDICAP_ID" name="HANDICAP_ID">
                    <button class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" onclick="handicapGameAdd()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 添加盘口游戏END -->


<script>
    /**
     *
     初始化内存信息
     */
    function init() {
        if (confirm("将初始化所有服务器盘口游戏内存信息！！！")) {
            $.get("${pageContext.request.contextPath}/ibm/admin/manage/handicap/init", function (data) {
                if (data.success) {
                    submitFrom();
                } else {
                    alert(data.msg);
                }
            }, 'json');
        }
    }

    function showGame(handicapId) {
        $.getJSON("${pageContext.request.contextPath}/ibm/admin/manage/handicap/game/list?handicapId=" + handicapId, function (data) {
            if (data.success) {
                var content = $(".handicapGame");
                content.empty();
                $.each(data.data.handicapGameInfos, function (index, game) {
                    var tr = $("<tr></tr>").appendTo(content);
                    $("<td class='text-center'></td>").text(game.GAME_NAME_).appendTo(tr);
                    $("<td class='text-center'></td>").text(game.GAME_CODE_).appendTo(tr);
                    $("<td class='text-center'></td>").text(game.CLOSE_TIME_).appendTo(tr);
                    $("<td class='text-center'></td>").text(game.TYPE_).appendTo(tr);
                    $("<td class='text-center'></td>").text(game.SUB_IHBI_TABLE_NAME_).appendTo(tr);
                    var btnGroup = $("<div class='btn-group'>");
                    $("<td style='padding: 4px;text-align: center;'></td>").append(btnGroup).appendTo(tr);

                    var editBtn = $("<button class='btn btn-sm btn-brown' type='button'>修改</button>").appendTo(btnGroup);
                    var delBtn = $("<button class='btn btn-sm btn-danger' type='button'>删除</button>").appendTo(btnGroup);
                    editBtn.click(function () {
                        handicapGameShow(game.HANDICAP_GAME_ID_);
                    });
                    delBtn.click(function () {
                        handicapGameDelete(game.HANDICAP_GAME_ID_);
                    });
                });
                $("span.handicapName").text(data.data.handicapInfo.HANDICAP_NAME_);
                $("#handicapId").val(handicapId);
                $(".HANDICAP_ID").val(handicapId);

                $('#handicapGameList').modal('show');
            } else {
                alert(data.msg);
            }

        });
    }

    function showInfo(handicapId) {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/handicap/info?handicapId=" + handicapId;
    }

    function submitFrom() {
        var _from = $(".head-form");
        _from.submit();
    }

    function del(handicapId) {
        if (confirm("将删除该盘口的所有信息！！！")) {
            $.post("${pageContext.request.contextPath}/ibm/admin/manage/handicap/delete", {"handicapId": handicapId}, function (data) {
                if (data.success) {
                    submitFrom();
                } else {
                    alert(data.msg);
                }
            }, 'json');
        }
    }


</script>
<script>
    function hideGame() {
        $('#handicapGameList').modal('hide');
        submitFrom();
    }

    function handicapGameShow(handicapGameId) {
        $.getJSON("${pageContext.request.contextPath}/ibm/admin/manage/handicap/game/info?handicapGameId=" + handicapGameId, function (data) {
            if (data.success) {
                $("form.updateGameForm input.gameName").val(data.data.GAME_NAME_);
                $("form.updateGameForm input.gameCloseTime").val(data.data.CLOSE_TIME_);
                $("form.updateGameForm input.gameType").val(data.data.TYPE_);
                $("form.updateGameForm input.gameTableName").val(data.data.TABLE_NAME_);
                $("form.updateGameForm input.gameIcon").val(data.data.ICON_);
                $("form.updateGameForm input.gameSn").val(data.data.SN_);
                $("form.updateGameForm input.handicapGameId").val(handicapGameId);
                $("span.gameName").text(data.data.GAME_NAME_);
                $('#handicapGameInfo').modal('show');
            } else {
                alert(data.msg);

            }
        });
    }

    function handicapGameUpdate() {
        var form = $(".updateGameForm");
        $.post(form.attr("action"), form.serialize(), function (data) {
            if (data.success) {
                $('#handicapGameInfo').modal('hide');
                var handicapId = $("#handicapId").val();
                showGame(handicapId);
            } else {
                alert(data.msg);
            }
        }, "json");
    }

    function handicapGameDelete(handicapGameId) {
        if (confirm("将删除该盘口游戏的所有信息！！！")) {
            $.post("${pageContext.request.contextPath}/ibm/admin/manage/handicap/game/delete", {"handicapGameId": handicapGameId}, function (data) {
                if (data.success) {
                    var handicapId = $("#handicapId").val();
                    showGame(handicapId);
                } else {
                    alert(data.msg);
                }
            }, "json");
        }

    }

    function handicapGameAddShow() {
        var handicapId = $("#handicapId").val();
        $.getJSON("${pageContext.request.contextPath}/ibm/admin/manage/handicap/game/no?handicapId=" + handicapId, function (data) {
            if (data.success) {
                var content = $("select#gameList");
                content.empty();
                $.each(data.data, function (index, game) {
                    var opt = $("<option></option>").appendTo(content);
                    opt.val(game.GAME_ID_);
                    opt.text(game.GAME_NAME_);
                    opt.attr("data", game.GAME_CODE_);
                });
                $('#handicapGameAdd').modal('show');
            } else {
                alert(data.msg);
            }
        });
    }

    function handicapGameAdd() {
        var form = $(".handicapGameAddForm");
        $.post(form.attr("action"), form.serialize(), function (data) {
            if (data.success) {
                $('#handicapGameAdd').modal('hide');
                var handicapId = $("#handicapId").val();
                showGame(handicapId);
            } else {
                alert(data.msg);
            }
        }, "json");
    }

</script>
<script>
    // 模糊搜索
    $('#in-se').keyup(function () {
        var txt = $('#in-se').val();
        var reg = /编辑|删除/;
        //过滤掉不包含txt的数据项
        var searchTr = $("tbody.handicap tr").filter(":contains('" + txt + "')");
        if (searchTr) {
            $("tbody.handicap tr").hide(); //全部隐藏
            if (searchTr.length && !reg.test(txt)) {
                searchTr.show();
            }
        }
    });

    $(function () {
        $('div.form-group select').selectpicker({
            width: 80
        });
    });

</script>
</body>
</html>
