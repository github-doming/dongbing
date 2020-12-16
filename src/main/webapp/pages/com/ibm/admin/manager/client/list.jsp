<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html; charset=UTF-8"
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
              action="${pageContext.request.contextPath}/ibm/admin/manage/client/list1" style="padding:  15px 0 15px 0">
            <div class="form-group">

                <div class="input-group">
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-brown" onclick="migrateFrom()">盘口迁移</button>
                        </span>
                </div>
                <div class="input-group">
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-brown" onclick="replaceFrom()">切换客户机</button>
                        </span>
                </div>
            </div>
            <div class="form-group" style="float: right">
                    <div class="input-group">
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-brown" onclick="submitFrom()">查询</button>
                        </span>
                        <input type="text" name="key" value="${key}" class="form-control" placeholder="查询关键词"
                               style="width:181px;vertical-align:middle;">
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
                <th style="width: 150px;text-align: center;">修改</th>
                <th style="width: 150px;text-align: center;">注册IP</th>
                <th style="width: 100px;text-align: center;">客户端编码</th>
                <th class='text-center'>客户端最大容量</th>
                <th class='text-center'>客户端使用容量</th>
                <th style="width: 200px;text-align: center;">开始时间</th>
                <th style="width: 200px;text-align: center;">结束时间</th>
                <th style="width: 80px;text-align: center;">状态</th>
                <th style="width: 150px;text-align: center;">心跳操作</th>
            </tr>
            </thead>
            <tbody class="handicap">
            <c:forEach items="${list}" var="list" varStatus="loop">
                <tr>
                    <td style="padding: 4px;text-align: center;">
                        <div class="btn-group">
                            <button class="btn  btn btn-primar" onclick="find('${list.CLIENT_CODE_}')">查看</button>
                            <button class="btn  btn btn-danger" onclick="updateRecord('${list.CLIENT_CODE_}')">编辑</button>
                        </div>
                    </td>
                    <td class="text-center">${list.REGISTER_IP_}</td>
                    <td class="text-center">${list.CLIENT_CODE_}</td>
                    <td class="text-center">${list.CLIENT_CAPACITY_MAX_}</td>
                    <td class="text-center">${list.CLIENT_CAPACITY_}</td>
                    <td class="text-center">${list.START_TIME_}</td>
                    <td class="text-center">${list.END_TIME_} </td>
                    <td class="text-center">
                        <c:if test="${list.STATE_=='STOP'}">停用</c:if>
                        <c:if test="${list.STATE_=='OPEN'}">开启</c:if>
                    </td>
                    <td style="padding: 4px;text-align: center;">
                        <div class="btn-group">
                            <button class="btn  btn btn-primar" onclick="startHeartbeat('${list.CLIENT_CODE_}')">开始</button>
                            <button class="btn  btn btn-danger" onclick="stopHeartbeat('${list.CLIENT_CODE_}')">停止</button>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    /**
     *
     请求更新
     */
    function updateRecord(clientCode) {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/clientCapacityForm1?clientCode=" + clientCode;
    }
    /**
     *
     停止心跳
     */
    function stopHeartbeat(clientCode) {
        if (confirm("确定停止该客户机心跳？？？")) {
            $.get("${pageContext.request.contextPath}/ibm/admin/manage/client/stopHeartbeat1", {"clientCode": clientCode}, function (data) {
                if (data.success) {
                    submitFrom();
                } else {
                    alert(data.msg);
                }
            }, 'json');
        }
        window.location.reload();
    }
    /**
     *
     停止心跳
     */
    function startHeartbeat(clientCode) {
        if (confirm("确定开启该客户机心跳？？？")) {
            $.get("${pageContext.request.contextPath}/ibm/admin/manage/client/startHeartbeat1", {"clientCode": clientCode}, function (data) {
                if (data.success) {
                    submitFrom();
                } else {
                    alert(data.msg);
                }
            }, 'json');
        }
        window.location.reload();
    }
    /**
     *
     查询
     */
    function find(clientCode) {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/handicapCapacity1?clientCode=" + clientCode;
    }
    function submitFrom() {
        var _from = $(".head-form");
        _from.submit();
    }

    function del(clientId) {
        if (confirm("将停用该客户机所有盘口信息！！！")) {
            $.post("${pageContext.request.contextPath}/ibm/admin/manage/handicap/delete1", {"handicapId": handicapId}, function (data) {
                if (data.success) {
                    submitFrom();
                } else {
                    alert(data.msg);
                }
            }, 'json');
        }
    }

    /**
     *
     查询
     */
    function migrateFrom() {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/handicapMigrateForm1";
    }

    /**
     * 切换客户机
     */
    function replaceFrom() {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/client/clientReplaceForm1";
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
