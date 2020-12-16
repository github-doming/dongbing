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
<button style="margin: 20px 0px 10px 2.5%;" class="btn btn-browm" onclick="chooseHandicap()">选择盘口</button>

<table id="topic_title" style="width:95%;table-layout:fixed; margin: 0px auto; text-align: center;" id="id_table"
       class="table  table-bordered table-hover">

    <thead>
        <tr>
            <th style="width: 150px;text-align: center;">用户名称</th>
            <th style="width: 150px;text-align: center;">盘口编码</th>
            <th style="width: 150px;text-align: center;">盘口名称</th>
            <th style="width: 150px;text-align: center;">在线会员数量</th>
            <th style="width: 150px;text-align: center;">最大会员在线数量</th>
            <th style="width: 150px;text-align: center;">使用频次</th>
            <th style="width: 150px;text-align: center;">创建时间</th>
            <th style="width: 300px;text-align: center;">操作</th>
        </tr>
    </thead>
    <tbody class='body'>
        <c:forEach items="${memberUsers}" var="memberUser">
            <tr>
                <td id="name" style="width: 150px;">${memberUser.APP_USER_NAME_}</td>
                <td id="code" style="width: 150px;">${memberUser.HANDICAP_CODE_}</td>
                <td style="width: 150px;">${memberUser.HANDICAP_NAME_}</td>
                <td style="width: 150px;">${memberUser.ONLINE_MEMBERS_COUNT_}</td>
                <td id="catecory" style="width: 150px;">${memberUser.ONLINE_NUMBER_MAX_}</td>
                <td id="type" style="width: 150px;">${memberUser.FREQUENCY_}</td>
                <td style="width: 150px;">${memberUser.CREATE_TIME_LONG_}</td>
                <td style="width: 150px;">
                    <button class="btn btn-primary" onclick="edit('${memberUser.IBM_HM_USER_ID_}',
                            '${memberUser.ONLINE_MEMBERS_COUNT_}','${memberUser.ONLINE_NUMBER_MAX_}')"> 编辑用户 </button>
                    <button class="btn btn-danger" onclick="removeUser('${memberUser.IBM_HM_USER_ID_}')"> 移除用户 </button>
                </td>
            </tr>

        </c:forEach>
    </tbody>

</table>

<script type="text/javascript">
    function chooseHandicap() {
        $('#chooseHandicap').modal('show',true);
    }

    function sure() {
        var handicapCode = $('#handicapCode').val();
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/handicap/memberUser?handicapCode="+handicapCode;
    }

    function edit(hmUserId,onlineMembersCount,onlineNumberMax) {
        $('#onlineMembersCount').val(onlineMembersCount);
        $('#onlineNumberMax').val(onlineNumberMax);
        $('#ibmHmUserId').val(hmUserId);
        $('#updateHandicapUser').modal('show',true);
    }

    function editUser() {
        var _from = $("#editUser");
        $.post(_from.attr("action"), _from.serialize(), function (data) {
            if (data.success) {
                window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/handicap/memberUser";
            } else {
                alert(data.message);
            }
        }, "json");
    }

    function removeUser(ibmHmUserId) {
        if(confirm("将移除该盘口的该用户？")){
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/ibm/admin/manage/handicap/memberUserRemove?ibmHmUserId=" + ibmHmUserId,
                success: function () {
                    window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/handicap/memberUser";
                }
            });
        }
    }
</script>


<!------------------------------------------------------------------------- 选择盘口 -->
<div class="modal fade" style="margin-left: -10%;" id="chooseHandicap"
     tabindex="-2" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times;</button>
                <h4 class="modal-title">
                    选择盘口
                </h4>
            </div>
            <div class="modal-body">
                <div class="input-group">
                    <span class="input-group-addon">盘口名称</span>
                    <select style="height: 35px; width: 100%;" id="handicapCode">
                        <option value="">--请选择--</option>
                        <c:forEach items="${codes}" var="code">
                            <option value="${code.handicapCode}" selected>${code.handicapName}</option>
                        </c:forEach>
                    </select>
                </div>
                <br>

            </div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="sure()" class="btn btn-primary">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 选择盘口END -->


<!------------------------------------------------------------------------- 修改盘口用户信息 -->
<div class="modal fade" style="margin-left: -10%; margin-top: 50px;" id="updateHandicapUser"
     tabindex="-2" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form id="editUser" action="${pageContext.request.contextPath}/ibm/admin/manage/handicap/updateMemberUser">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times;</button>
                    <h4 class="modal-title">
                        修改信息
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon">在线会员数量　　</span>
                        <input type="number" class="form-control" id="onlineMembersCount" name="onlineMembersCount">
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">最大会员在线数量</span>
                        <input type="number" class="form-control" id="onlineNumberMax" name="onlineNumberMax">
                    </div>
                    <br>
                    <input type="hidden" class="form-control" id="ibmHmUserId" name="ibmHmUserId">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" onclick="editUser()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 修改盘口用户信息END -->
</body>
</html>
