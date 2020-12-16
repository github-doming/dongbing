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
              action="${pageContext.request.contextPath}/ibm/admin/manage/user/list"
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
                <th style="width: 120px;text-align: center;">用户名称</th>
                <th style="width: 120px;text-align: center;">用户昵称</th>
                <th style="width: 80px;text-align: center;">剩余点数</th>
                <th style="width: 150px;text-align: center;">到期时间</th>
                <th style="width: 100px;text-align: center;">用户类型</th>
                <th class="text-center">拥有代理盘口</th>
                <th class="text-center">拥有会员盘口</th>
                <th style="width: 150px;text-align: center;">操作</th>
            </tr>
            </thead>
            <tbody class="user">
            <c:forEach items="${userInfos}" var="userInfo" varStatus="loop">
                <tr>
                    <td class="text-center">${userInfo.APP_USER_NAME_}</td>
                    <td class="text-center">
                        <a href="${pageContext.request.contextPath}/ibm/admin/manage/user/info?appUserId=${userInfo.APP_USER_ID_}">${userInfo.NICKNAME_}</a>
                    </td>
                    <td class="text-center">${userInfo.USEABLE_POINT_}</td>
                    <td class="text-center">${userInfo.END_TIME_}</td>
                    <td class="text-center">${userInfo.APP_USER_TYPE_}</td>
                    <td class="text-center">${userInfo.AGENT_HANDICAP_}</td>
                    <td class="text-center">${userInfo.MEMBER_HANDICAP_}</td>
                    <td style="padding: 4px;text-align: center;">
                        <div class="btn-group">
                            <button class="btn  btn-sm btn-brown dropdown-toggle " type="button" data-toggle="dropdown">
                                修改 <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" style="min-width: 100%;">
                                <li><a href="#"
                                       onclick="showAgent('${userInfo.APP_USER_ID_}','${userInfo.APP_USER_NAME_}')">
                                    <i class="glyphicon glyphicon-edit">代理盘口</i></a></li>
                                <li><a href="#"
                                       onclick="showMember('${userInfo.APP_USER_ID_}','${userInfo.APP_USER_NAME_}')">
                                    <i class="glyphicon glyphicon-tasks">会员盘口</i></a></li>
                            </ul>
                            <button class="btn btn-sm btn-danger" onclick="del('${userInfo.APP_USER_ID_}')">
                                删除
                            </button>
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
        <button type="button" class="btn btn-default" onclick="delExpireData()">清理用户</button>
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
                <input type="hidden" id="userId">
                <input type="hidden" id="category">
                <h4 class="modal-title">《<span class="userHandicap"></span>》的<span class="userCategory"></span>盘口信息</h4>
            </div>
            <div class="modal-body" style="padding: 5px 15px 5px 15px;">
                <table class="table " style="margin-bottom: 0;">
                    <thead>
                    <tr>
                        <th class='text-center'>盘口名称</th>
                        <th class='text-center'>在线数量</th>
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
                        onclick="handicapUserAddShow()">添加
                </button>
                <button type="button" class="btn btn-default" onclick="hideHandicap()">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 用户盘口END -->

<!------------------------------------------------------------------------- 修改用户盘口 -->
<div class="modal fade" id="handicapUserInfo" tabindex="-2" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/ibm/admin/manage/user/handicap/update"
                  class="updateHandicapForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"> &times;</button>
                    <h4 class="modal-title">修改《<span class="userHandicap"></span>》的<span class="userCategory"></span>盘口信息
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
                    <div class="form-inline">
                        <div class="input-group">
                            <span class="input-group-addon">在线数量</span>
                            <input type="text" class="form-control onlineCount" readonly>
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">最大在线</span>
                            <input type="number" oninput="if(value.length>2)value=value.slice(0,2)"
                                   placeholder="请输入不超过两位的数字"
                                   class="form-control browsers onlineNumberMax" name="ONLINE_NUMBER_MAX_">
                        </div>
                    </div>

                    <br>
                    <div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th class='text-center'>账户</th>
                                <th class='text-center'>盘口地址</th>
                                <th class='text-center'>盘口验证码</th>
                                <th class='text-center'>状态</th>
                                <th class='text-center'>操作</th>
                            </tr>
                            </thead>
                            <tbody class='itemInfo'>
                            </tbody>
                        </table>
                    </div>
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

<!------------------------------------------------------------------------- 添加用户盘口 -->
<div class="modal fade" id="handicapUserAdd" tabindex="-2" role="dialog">
    <div class="modal-dialog  modal-sm">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/ibm/admin/manage/user/handicap/add"
                  class="addHandicapForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"> &times;</button>
                    <h4 class="modal-title">添加《<span class="userHandicap"></span>》的<span class="userCategory"></span>盘口
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
                    <div class="input-group">
                        <span class="input-group-addon">最大在线</span>
                        <input type="number" oninput="if(value.length>2)value=value.slice(0,2)"
                               class="form-control browsers onlineNumberMax" name="ONLINE_NUMBER_MAX_">
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <input type="hidden" class="HANDICAP_NAME_" name="HANDICAP_NAME_">
                    <input type="hidden" class="USER_ID_" name="USER_ID_">
                    <button class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" onclick="handicapUserAdd()" class="btn btn-primary">提交更改</button>
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
    function showAgent(appUserId, userName) {
        $("span.userHandicap").text(userName);
        $("span.userCategory").text("代理");
        putUserHandicapData(appUserId, "AGENT");

    }

    /**
     * 显示会员信息
     * @param appUserId 用户主键
     * @param userName 用户名称
     */
    function showMember(appUserId, userName) {
        $("span.userHandicap").text(userName);
        $("span.userCategory").text("会员");
        putUserHandicapData(appUserId, "MEMBER");

    }

    function submitFrom() {
        var _from = $(".head-form");
        _from.submit();
    }
    function delExpireData() {
        if (confirm("将清理过期用户的所有信息！！！")) {
            $.get("${pageContext.request.contextPath}/ibm/admin/manage/user/deleteExpireData", function () {
                if (data.success) {
                    submitFrom();
                } else {
                    alert(data.msg);
                }
            });
        }
    }
    function del(appUserId) {
        if (confirm("将删除该用户的所有信息！！！")) {
            $.post("${pageContext.request.contextPath}/ibm/admin/manage/user/delete", {"appUserId": appUserId}, function (data) {
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
    /**
     * 放入用户盘口数据
     * @param appUserId  用户主键
     * @param category 盘口类别
     */
    function putUserHandicapData(appUserId, category) {
        $.getJSON("${pageContext.request.contextPath}/ibm/admin/manage/user/handicap/list", {
            "appUserId": appUserId,
            "userCategory": category
        }, function (data) {
            if (data.success) {
                var content = $(".handicapUser");
                content.empty();
                $.each(data.data, function (index, handicap) {
                    var tr = $("<tr></tr>").appendTo(content);
                    $("<td class='text-center'></td>").text(handicap.HANDICAP_NAME_).appendTo(tr);
                    $("<td class='text-center'></td>").text(handicap.ONLINE_COUNT_).appendTo(tr);
                    $("<td class='text-center'></td>").text(handicap.ONLINE_NUMBER_MAX_).appendTo(tr);
                    var btnGroup = $("<div class='btn-group'>");
                    $("<td style='padding: 4px;text-align: center;'></td>").append(btnGroup).appendTo(tr);
                    var editBtn = $("<button class='btn btn-sm btn-brown' type='button'>修改</button>").appendTo(btnGroup);
                    var delBtn = $("<button class='btn btn-sm btn-danger' type='button'>删除</button>").appendTo(btnGroup);
                    editBtn.click(function () {
                        handicapUserShow(handicap.HANDICAP_USER_ID_, category);
                    });
                    delBtn.click(function () {
                        handicapUserDelete(handicap.HANDICAP_USER_ID_, category, handicap.ONLINE_COUNT_);
                    });
                });
                $("#userId").val(appUserId);
                $("#category").val(category);

                $(".USER_ID_").val(appUserId);
                $(".CATEGORY").val(category);

                $('#handicapUserList').modal('show');
            } else {
                alert(data.msg);
            }
        });
    }

    function handicapUserAddShow() {
        var appUserId = $("#userId").val();
        var userCategory = $("#category").val();
        $.getJSON("${pageContext.request.contextPath}/ibm/admin/manage/user/handicap/no", {
            "appUserId": appUserId,
            "userCategory": userCategory
        }, function (data) {
            if (data.success) {
                var content = $("select#handicapList");
                content.empty();
                $("<option style='display: none' value=''></option>").appendTo(content);
                $(".HANDICAP_CODE_").val('');
                $(".HANDICAP_NAME_").val('');
                $.each(data.data, function (index, handicap) {
                    var opt = $("<option></option>").appendTo(content);
                    opt.val(handicap.IBM_HANDICAP_ID_);
                    opt.text(handicap.HANDICAP_NAME_);
                    opt.attr("data", handicap.HANDICAP_CODE_);
                });
                $('#handicapUserAdd').modal('show');
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

</script>
<script>
    function handicapUserShow(handicapUserId, category) {
        $.getJSON("${pageContext.request.contextPath}/ibm/admin/manage/user/handicap/info", {
            "handicapUserId": handicapUserId,
            "userCategory": category
        }, function (data) {
            if (data.success) {
                $("form.updateHandicapForm input.handicapName").val(data.data.info.HANDICAP_NAME_);
                $("form.updateHandicapForm input.handicapCode").val(data.data.info.HANDICAP_CODE_);
                $("form.updateHandicapForm input.onlineCount").val(data.data.info.ONLINE_COUNT_);
                $("form.updateHandicapForm input.onlineNumberMax").val(data.data.info.ONLINE_NUMBER_MAX_);

                var content = $("tbody.itemInfo");
                content.empty();
                if (data.data.itemInfos == null) {
                    var tr = $("<tr></tr>").appendTo(content);
                    $("<td class='text-center' colspan='5'></td>").text("该用户还没有托管过账号").appendTo(tr);
                } else {
                    $.each(data.data.itemInfos, function (index, itemInfo) {
                        var tr = $("<tr></tr>").appendTo(content);
                        $("<td class='text-center'></td>").text(itemInfo.ACCOUNT_).appendTo(tr);
                        $("<td class='text-center'></td>").text(itemInfo.HANDICAP_URL_).appendTo(tr);
                        $("<td class='text-center'></td>").text(itemInfo.HANDICAP_CAPTCHA_).appendTo(tr);
                        var state = $("<td class='text-center'></td>").appendTo(tr);
                        if (itemInfo.STATE_ === 'LOGIN') {
                            state.text("托管");
                        } else {
                            state.text("未托管");
                        }
                        var btnGroup = $("<div class='btn-group'>");
                        $("<td style='padding: 4px;text-align: center;'></td>").append(btnGroup).appendTo(tr);
                        var delBtn = $("<button class='btn btn-sm btn-danger' type='button'>删除</button>").appendTo(btnGroup);

                        delBtn.click(function () {
                            handicapAccountDelete(itemInfo.ACCOUNT_ID_, itemInfo.STATE_);
                        });
                    });
                }
                $(".HANDICAP_USER_ID_").val(handicapUserId);
                $(".USER_CATEGORY_").val(category);

                $("#handicapUserId").val(handicapUserId);
                $('#handicapUserInfo').modal('show');
            } else {
                alert(data.msg);
            }
        });

    }

    function handicapUserDelete(handicapUserId, category, onlineCount) {
        var msg;
        if (onlineCount > 0) {
            msg = "在线数量不为0，确认将该盘口中的所有托管退出后删除？"
        } else {
            msg = "删除盘口将清空在该盘口的所有数据！！！";
        }
        if (confirm(msg)) {
            $.post("${pageContext.request.contextPath}/ibm/admin/manage/user/handicap/delete", {
                "handicapUserId": handicapUserId,
                "userCategory": category
            }, function (data) {
                if (data.success) {
                    var appUserId = $("#userId").val();
                    putUserHandicapData(appUserId, category);
                } else {
                    alert(data.msg);
                }
            }, "json");
        }
    }

    function handicapUserUpdate() {
        var form = $(".updateHandicapForm");
        $.post(form.attr("action"), form.serialize(), function (data) {
            if (data.success) {
                $('#handicapUserInfo').modal('hide');
                var appUserId = $("#userId").val();
                var userCategory = $("#category").val();
                putUserHandicapData(appUserId, userCategory);
            } else {
                alert(data.msg);
            }
        }, "json");

    }

    function handicapAccountDelete(accountId, state) {
        var msg;
        if (state === 'LOGIN') {
            msg = "该账户处于托管中，删除账户将推出托管！！！"
        } else {
            msg = "删除账户将清空在该账户的所有数据！！！";
        }
        if (confirm(msg)) {
            $.post("${pageContext.request.contextPath}/ibm/admin/manage/user/handicap/account/delete", {
                "accountId": accountId,
                "userCategory": category
            }, function (data) {
                if (data.success) {
                    var appUserId = $("#userId").val();
                    var userCategory = $("#category").val();
                    putUserHandicapData(appUserId, userCategory);
                } else {
                    alert(data.msg);
                }
            }, "json");
        }
    }

    function handicapUserAdd() {
        var form = $(".addHandicapForm");
        $.post(form.attr("action"), form.serialize(), function (data) {
            if (data.success) {
                $('#handicapUserAdd').modal('hide');
                var appUserId = $("#userId").val();
                var userCategory = $("#category").val();
                putUserHandicapData(appUserId, userCategory);
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
