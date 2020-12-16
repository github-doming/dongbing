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
<button style="margin: 20px 0px 10px 2.5%;" class="btn btn-browm" onclick="save()">添加用户</button>

<table id="topic_title" style="width:95%;table-layout:fixed; margin: 0px auto; text-align: center;" id="id_table"
       class="table  table-bordered table-hover">

    <thead>
        <tr>
            <th style="width: 150px;text-align: center;">用户名称</th>
            <th style="width: 150px;text-align: center;">盘口名称</th>
            <th style="width: 150px;text-align: center;">游戏名称</th>
            <th style="width: 200px;text-align: center;">创建时间</th>
            <th style="text-align: center;">操作</th>
        </tr>
    </thead>
    <tbody class='body'>
    <c:forEach items="${mapLists}" var="mapList">
        <tr>
            <td style="width: 150px;">${mapList.username}</td>
            <td style="width: 150px;">${mapList.handicapName}</td>
            <td style="width: 150px;">${mapList.gameName}</td>
            <td style="width: 200px;">${mapList.CREATE_TIME_}</td>
            <td>
                <button class="btn btn-brown" onclick="edit('${mapList.IBM_FORBIT_ID_}','${mapList.username}'
                        ,'${mapList.HANDICAP_CODE_}','${mapList.GAME_CODE_}')">修改</button>
                <button class="btn btn-danger" onclick="del('${mapList.IBM_FORBIT_ID_}')">删除</button>
            </td>
        </tr>

    </c:forEach>
    </tbody>

</table>

<script>
    function save() {
        $('#saveUser').modal("show",true);
    }

    function saveUser() {
        var _from = $("#saveUserForm");
        $.post(_from.attr("action"), _from.serialize(), function (data) {
            if (data.success) {
                window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/game/forbitList";
            } else {
                alert(data.message);
            }
        }, "json");
    }
    
    function edit(forbitId,username,handicapName,gameName) {
        $('#username').val(username);
        $('#handicapCode').val(handicapName);
        $('#gameCode').val(gameName);
        $('#forbitId').val(forbitId);
        $('#editUser').modal("show",true);
    }

    function editUser() {
        var _from = $("#editUserForm");
        $.post(_from.attr("action"), _from.serialize(), function (data) {
            if (data.success) {
                window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/game/forbitList";
            } else {
                alert(data.message);
            }
        }, "json");
    }

    function del(forbitId) {
        if(confirm("删除后该用户将可以获取到该盘口游戏，是否确认删除？")){
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/ibm/admin/manage/game/delForbitUser?forbitId=" + forbitId,
                success: function () {
                    window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/game/forbitList";
                },
                error : function (data) {
                    alert(data.message);
                }
            });
        }
    }
</script>

<!------------------------------------------------------------------------- 添加用户 -->
<div class="modal fade" style="margin-left: -10%;" id="saveUser"
     tabindex="-2" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/ibm/admin/manage/game/saveForbitUser" id="saveUserForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times;</button>
                    <h4 class="modal-title">
                        添加禁止获取游戏数据用户
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group"><span class="input-group-addon">用户名　</span>
                        <input type="text" class="form-control" name="username" required="required">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">盘口名称</span>
                        <input type="text" class="form-control browsers" name="handicapCode"
                               aria-describedby="basic-addon1" list="browsers1" required="required">
                        <datalist id="browsers1">
                            <option value="WS2">泰源</option>
                            <option value="IDC" selected>益力多</option>
                            <option value="SGWIN">大唐</option>
                            <option value="HQ">环球</option>
                        </datalist>
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">游戏名称</span>
                        <input type="text" class="form-control browsers" name="gameCode"
                               aria-describedby="basic-addon2" list="browsers2" required="required">
                        <datalist id="browsers2">
                            <option value="PK10">北京赛车</option>
                            <option value="XYFT" selected>幸运飞艇</option>
                            <option value="CQSSC">重庆时时彩</option>
                        </datalist>
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭 </button>
                    <button type="button" onclick="saveUser()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 添加用户END -->


<!------------------------------------------------------------------------- 修改用户 -->
<div class="modal fade" style="margin-left: -10%;" id="editUser"
     tabindex="-2" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/ibm/admin/manage/game/updateForbitUser" id="editUserForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times;</button>
                    <h4 class="modal-title">
                        修改禁止获取游戏数据用户
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group"><span class="input-group-addon">用户名　</span>
                        <input type="text" class="form-control" name="username" id="username" required="required">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">盘口名称</span>
                        <input type="text" class="form-control browsers" name="handicapCode" id="handicapCode"
                               aria-describedby="basic-addon1" list="browsers1" required="required">
                        <datalist id="browsers1">
                            <option value="WS2" />
                            <option value="IDC" selected />
                            <option value="SGWIN" />
                            <option value="HQ" />
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon">游戏名称</span>
                        <input type="text" class="form-control browsers" name="gameCode" id="gameCode"
                               aria-describedby="basic-addon2" list="browsers2" required="required">
                        <datalist id="browsers2">
                            <option value="PK10" />
                            <option value="XYFT" selected />
                            <option value="CQSSC" />
                        </datalist>
                    </div>
                    <br>
                    <input type="hidden" id="forbitId" name="forbitId">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭 </button>
                    <button type="button" onclick="editUser()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 修改用户END -->

</body>
</html>
