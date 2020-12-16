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
              action="${pageContext.request.contextPath}/ibm/admin/manage/user/role"
              style="padding:  15px 0 15px 0">
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">模糊搜索:</span>
                    <input type="text" class="form-control" id="in-se" placeholder="筛选关键词" style="width: 120px">
                </div>
            </div>
            <div style="float: right">
                <div class="form-group">
                    <label for="se-ty">类型: </label>
                    <select name="USER_TYPE_" onchange="submitFrom()" id="se-ty" style="width: 50px">
                        <option value=""  <c:if test="${empty USER_TYPE_ }"> selected="selected"</c:if>>全部</option>
                        <c:forEach items="${types}" var="type" varStatus="loop">
                            <option value="${type.code}"
                                    <c:if test="${ type.code eq USER_TYPE_}"> selected="selected"</c:if> >${type.name}</option>
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
                <th style="width: 120px;text-align: center;">角色类型</th>

                <th class="text-center">代理盘口</th>
                <th class="text-center">会员盘口</th>
                <th style="width: 120px;text-align: center;">最大代理在线数</th>
                <th style="width: 120px;text-align: center;">最大会员在线数</th>
                <th style="width: 80px;text-align: center;">操作</th>
            </tr>
            </thead>
            <tbody class="user">
            <c:forEach items="${roleInfos}" var="roleInfo" varStatus="loop">
                <tr>
                    <td class="text-center">${roleInfo.ROLE_NAME_}</td>
                    <td class="text-center">${roleInfo.agentHandicapNames}</td>
                    <td class="text-center">${roleInfo.memberHandicapNames}</td>
                    <td class="text-center">${roleInfo.ONLINE_HA_NUMBER_MAX_}</td>
                    <td class="text-center">${roleInfo.ONLINE_HM_NUMBER_MAX_}</td>
                    <td style="padding: 4px;text-align: center;">
                        <div class="btn-group">
                            <button class="btn  btn-sm btn-brown dropdown-toggle " type="button" data-toggle="dropdown">
                                修改 <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" style="min-width: 100%;">
                                <li><a href="#"
                                       onclick="showAgent('${roleInfo.IBM_MANAGE_ROLE_ID_}','${roleInfo.ROLE_NAME_}')">
                                    <i class="glyphicon glyphicon-edit">代理盘口</i></a></li>
                                <li><a href="#"
                                       onclick="showMember('${roleInfo.IBM_MANAGE_ROLE_ID_}','${roleInfo.ROLE_NAME_}')">
                                    <i class="glyphicon glyphicon-tasks">会员盘口</i></a></li>
                            </ul>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>


<!------------------------------------------------------------------------- 用户盘口 -->
<div class="modal fade" id="handicapUserList" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    &times;
                </button>
                <input type="hidden" id="roleId">
                <input type="hidden" id="category">
                <h4 class="modal-title">《<span class="roleHandicap"></span>》的<span class="userCategory"></span>盘口信息</h4>
            </div>
            <div class="modal-body" style="padding: 5px 15px 5px 15px;">
                <table class="table " style="margin-bottom: 0;">
                    <thead>
                    <tr>
                        <th class='text-center'>盘口名称</th>
                        <th class='text-center'>最大在线数</th>
                        <th class='text-center'>操作</th>
                    </tr>
                    </thead>
                    <tbody class='handicapUser'>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button style="position: absolute; left: 20px;" type="button" class="btn btn-darkblue"
                        onclick="handicapAddShow()">添加
                </button>
                <button type="button" class="btn btn-default" onclick="hideHandicap()">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 用户盘口END -->

<!------------------------------------------------------------------------- 修改角色盘口 -->
<div class="modal fade" id="handicapUserInfo" tabindex="-2" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/ibm/admin/manage/user/handicap/update"
                  class="updateHandicapForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"> &times;</button>
                    <h4 class="modal-title">修改《<span class="roleHandicap"></span>》的<span class="userCategory"></span>盘口信息
                    </h4>

                    <input type="hidden" id="handicapUserId">
                </div>
                <div class="modal-body">
                    <div class="form-inline">
                        <div class="input-group"><span class="input-group-addon">盘口名称</span>
                            <input type="text" class="form-control handicapName" readonly>
                        </div>
                        <div class="input-group"><span class="input-group-addon">盘口编码</span>
                            <input type="text" class="form-control handicapCode" readonly>
                        </div>

                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <input type="hidden" name="HANDICAP_USER_ID_" class="HANDICAP_USER_ID_">
                    <input type="hidden" name="USER_CATEGORY_" class="USER_CATEGORY_">
                    <button class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" onclick="handicapUserUpdate()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 修改用户盘口END -->

<!------------------------------------------------------------------------- 添加盘口 -->
<div class="modal fade" id="handicapAdd" tabindex="-2" role="dialog">
    <div class="modal-dialog  modal-sm">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/ibm/admin/manage/user/handicap/role/add"
                  class="addHandicapForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"> &times;</button>
                    <h4 class="modal-title">添加《<span class="roleHandicap"></span>》的<span class="userCategory"></span>盘口
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon">选择盘口</span>
                        <select type="text" id="handicapList" name="HANDICAP_ID" class="form-control browsers"
                                onchange="selectHandicap(this)">
                        </select>
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">盘口编码</span>
                        <input type="text" class="form-control HANDICAP_CODE_" name="HANDICAP_CODE_" readonly>
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <input type="hidden" class="HANDICAP_NAME_" name="HANDICAP_NAME_">
                    <input type="hidden" class="roleId" name="roleId">
                    <button class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" onclick="handicapAdd()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 添加用户盘口END -->

<!------------------------------------------------------------------------- 修改盘口 -->
<div class="modal fade" id="handicapUpdate" tabindex="-2" role="dialog">
    <div class="modal-dialog  modal-sm">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/ibm/admin/manage/user/role/handicap/update"
                  class="updateRoleHandicapForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"> &times;</button>
                    <h4 class="modal-title">修改《<span class="roleHandicap"></span>》的<span class="userCategory"></span>盘口
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon">盘口名称</span>
                        <input type="text" class="form-control HANDICAP_NAME_" name="HANDICAP_NAME_" readonly>
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">最大在线</span>
                        <input type="number" class="form-control browsers HANDICAP_MAX_NUM_" name="HANDICAP_MAX_NUM_">
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <input type="hidden" class="roleId" name="roleId">
                    <input type="hidden" class="handicapId" name="handicapId">
                    <input type="hidden" class="category" name="category">
                    <button class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" onclick="handicapRoleUpdate()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 添加用户盘口END -->


<script>
    /**
     * 显示代理信息
     * @param appUserId 用户主键
     * @param userName 用户名称
     */
    function showAgent(key, userName) {
        $("span.roleHandicap").text(userName+"类型");
        $("span.userCategory").text("代理");
        putUserHandicapData(key, "AGENT");

    }

    /**
     * 显示会员信息
     * @param appUserId 用户主键
     * @param userName 用户名称
     */
    function showMember(key, userName) {
        $("span.roleHandicap").text(userName);
        $("span.userCategory").text("会员");
        putUserHandicapData(key, "MEMBER");

    }

    function submitFrom() {
        var _from = $(".head-form");
        _from.submit();
    }


</script>


<script>
    /**
     * 放入用户盘口数据
     * @param appUserId  用户主键
     * @param category 盘口类别
     */
    function putUserHandicapData(key, category) {
        $.getJSON("${pageContext.request.contextPath}/ibm/admin/manage/user/handicap/roleList", {
            "key": key, // 角色ID
            "userCategory": category // 盘口类型
        }, function (data) {
            if (data.success) {
                var content = $(".handicapUser");
                content.empty();
                $.each(data.data, function (index, handicap) {
                    console.log(handicap)
                    var tr = $("<tr></tr>").appendTo(content);
                    $("<td class='text-center'></td>").text(handicap.HANDICAP_NAME_).appendTo(tr);
                    $("<td class='text-center'></td>").text(handicap.ONLINE_NUMBER_MAX_).appendTo(tr);
                    var btnGroup = $("<div class='btn-group'>");
                    $("<td style='padding: 4px;text-align: center;'></td>").append(btnGroup).appendTo(tr);
                    var editBtn = $("<button class='btn btn-sm btn-brown' type='button'>修改</button>").appendTo(btnGroup);
                    editBtn.click(function () {
                        roleHandicapShow(handicap.IBM_HANDICAP_ID_, category,handicap.HANDICAP_NAME_,handicap.ONLINE_NUMBER_MAX_);
                    });

                });
                $("#roleId").val(key);
                $("#category").val(category);

                $(".roleId").val(key);
                $(".CATEGORY").val(category);

                $('#handicapUserList').modal('show');
            } else {
                alert(data.msg);
            }
        });
    }

    function handicapAddShow() {
        var roleId = $("#roleId").val();
        var userCategory = $("#category").val();
        $.getJSON("${pageContext.request.contextPath}/ibm/admin/manage/user/role/handicap/no", {
            "roleId": roleId,
            "userCategory": userCategory
        }, function (data) {
            if (data.success) {
                var content = $("select#handicapList");
                content.empty();
                $("<option style='display: none' value=''></option>").appendTo(content);
                $(".HANDICAP_CODE_").val('');
                $(".HANDICAP_NAME_").val('');
                $(".roleId").val(roleId);
                $.each(data.data, function (index, handicap) {
                    var opt = $("<option></option>").appendTo(content);
                    opt.val(handicap.IBM_HANDICAP_ID_);
                    opt.text(handicap.HANDICAP_NAME_);
                    opt.attr("data", handicap.HANDICAP_CODE_);
                });
                $('#handicapAdd').modal('show');
            } else {
                alert(data.msg);
            }
        });
    }

    function selectHandicap(_this) {
        var obj = _this.value;
        $("#handicapList option").each(function () {
            if (this.value === obj) {
                $(".HANDICAP_CODE_").val($(this).attr("data"));
                $(".HANDICAP_NAME_").val($(this).text());
            }
        });
    }

    function hideHandicap() {
        $('#handicapUserList').modal('hide');
        submitFrom();
    }

    function roleHandicapShow(handicapId, category,handicapName,maxNum) {
        console.log(handicapName)
        $(".handicapId").val(handicapId);
        $(".category").val(category);
        $(".HANDICAP_NAME_").val(handicapName);
        $(".HANDICAP_MAX_NUM_").val(maxNum);
        $('#handicapUpdate').modal('show');
    }

</script>
<script>

    function handicapRoleUpdate() {
        var form = $(".updateRoleHandicapForm");
        $.post(form.attr("action"), form.serialize(), function (data) {
            if (data.success) {
                console.log("ss=="+data.data)
                alert("修改成功"+data.data+"位用户的盘口信息,详情请查看《盘口管理》-《用户管理》");
                $('#handicapUpdate').modal('hide');
                var appUserId = $("#roleId").val();
                var userCategory = $("#category").val();
                putUserHandicapData(appUserId, userCategory);
            } else {
                alert(data.msg);
            }
        }, "json");
    }


    function handicapUserUpdate() {
        var form = $(".updateHandicapForm");
        $.post(form.attr("action"), form.serialize(), function (data) {
            if (data.success) {
                $('#handicapUpdate').modal('hide');
                var appUserId = $("#roleId").val();
                var userCategory = $("#category").val();
                putUserHandicapData(appUserId, userCategory);
            } else {
                alert(data.msg);
            }
        }, "json");

    }

    function handicapAdd() {
        if (confirm("是否确定提交当前修改的内容！！！")) {
            var form = $(".addHandicapForm");
            $.post(form.attr("action"), form.serialize(), function (data) {
                if (data.success) {
                    $('#handicapAdd').modal('hide');
                    var roleId = $("#roleId").val();
                    var userCategory = $("#category").val();
                    putUserHandicapData(roleId, userCategory);
                } else {
                    alert(data.msg);
                }
            }, "json");
        }
    }

</script>
<script>
    // 模糊搜索
    $('#in-se').keyup(function () {
        var txt = $('#in-se').val();
        var reg = /编辑|删除/;
        //过滤掉不包含txt的数据项
        var searchTr = $("tbody.user tr").filter(":contains('" + txt + "')");
        if (searchTr) {
            $("tbody.user tr").hide(); //全部隐藏
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
