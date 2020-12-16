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
    <script src="${pageContext.request.contextPath}/pages/com/ibm/admin/manager/Period/static/bootstrap-paginator.js"></script>

    <script src="${pageContext.request.contextPath}/pages/com/ibm/admin/manager/Period/static/moment-with-locales.min.js"></script>

</head>


<body>
<div style="display: none;" id="id_div_msg">正在提交中...</div>

<div class="head">
    <div class="col-xs-10 col-xs-offset-1">
        <form class="form-inline head-form"
              action="${pageContext.request.contextPath}/ibm/admin/manage/feedback/queryUserFeedback"
              style="padding:  15px 0 15px 0">
            <div class="form-group">
                <label>标题: </label>
                <div class="input-group">
                    <input type="text" name="feedbackTitle" class="form-control" placeholder="请输入"
                           style="width: 120px;">

                </div>
            </div>
            <div class="form-group" style="padding:0 15px 0 15px">
                <label>处理状态: </label>
                <select style="width: 335px; margin-top:12px;  height: 32px; border-radius: 3px;" name="state">
                    <option value="OPEN">请输入</option>
                    <option value="OPEN">已处理</option>
                    <option value="BEGIN">处理中</option>
                    <option value="START">待处理</option>
                </select>
            </div>
            <div class="form-group">
                <label>反馈编码: </label>
                <div class="input-group">
                    <input type="text" name="feedbackCode" class="form-control" placeholder="请输入" style="width: 120px;">

                </div>
            </div>

            <div class="form-group">
                <label> &nbsp 时间：</label>
                <div class='input-group date' id='datetimepicker0'>
                    <input type='text' class="form-control" name="timeStart"/>
                    <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                </div>
            </div>
            <div class="form-group">
                <label>至</label>
                <div class='input-group date' id='datetimepicker1'>
                    <input type='text' class="form-control" name="timeEnd"/>
                    <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-brown" onclick="submitFrom()">查询</button>
                        </span>
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                        <span class="input-group-btn">
                            <button type="reset" class="btn btn-brown">重置</button>
                        </span>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="content">
    <div class="col-xs-10 col-xs-offset-1">
        <table class="table  table-bordered table-hover" id="datas">
            <thead>
            <tr>
                <th style="width: 100px;text-align: center;">反馈人</th>
                <th style="width: 100px;text-align: center;">反馈编码</th>
                <th style="width: 300px;text-align: center;">反馈标题</th>
                <th style="width: 100px;text-align: center;">反馈时间</th>
                <th style="width: 100px;text-align: center;">处理情况</th>
                <th style="width: 40px;text-align: center;">操作</th>
            </tr>
            </thead>
            <tbody class="tbodyDatas">

            </tbody>

        </table>

            <div style="text-align:right;">
                <ul id="pages"></ul>
            </div>

    </div>
</div>

<script type="text/javascript">

    //ajax获取后台数据
    function initTable() {

        $.ajax({
            type: 'GET',
            dataType : "json",
            async:false,
            url: "${pageContext.request.contextPath}/ibm/admin/manage/feedback/queryAllUserfeedback",//请求的action路径页面
            data: {"flag":true},
            error: function () {//请求失败处理函数
                alert('请求失败');
            },
            success:function(data){ //请求成功后处理函数。
                var content = $(".tbodyDatas");
                content.empty();

                $.each(data.data.list, function(i, n) {
                    var tr = $("<tr></tr>").appendTo(content);
                    $("<td class='text-center'></td>").text(n.userName).appendTo(tr);
                    $("<td class='text-center'></td>").text(n.code).appendTo(tr);
                    $("<td class='text-center'></td>").text(n.title).appendTo(tr);

                    $("<td class='text-center'></td>").text(new Date(parseInt(n.time)).toLocaleString('chinese', {hour12: false})).appendTo(tr);
                    $("<td class='text-center'></td>").text(n.stateCn).appendTo(tr);
                    var aGroup = $("<div class='a'>");
                    $("<td style='padding: 4px;text-align: center;'></td>").append(aGroup).appendTo(tr);
                    var editBtn = $("<a style='text-decoration:none;cursor:pointer'>查看</a>").appendTo(aGroup);
                    editBtn.click(function () {
                        queryFeedbackDetails(n.code);
                    });
                    var span = $("<span>&nbsp;&nbsp;</span>").appendTo(editBtn);
                    var editBtn2 = $("<a style='text-decoration:none;cursor:pointer'>处理</a>").appendTo(aGroup);
                    editBtn2.click(function () {
                        updateFeedbackDetails(n.pk,n.state);
                    });
                });

                var pageCount = data.data.totalPages; //取到pageCount的值
                var currentPage = data.data.prePage; //得到currentPage
                console.log(currentPage,pageCount, "1211");
                var options = {
                    bootstrapMajorVersion: 3, //版本
                    currentPage: currentPage, //当前页数
                    totalPages: pageCount, //总页数
                    numberOfPages: 5,
                    itemTexts: function (type, page) {
                        switch (type) {
                            case "first":
                                return "首页";
                            case "prev":
                                return "上一页";
                            case "next":
                                return "下一页";
                            case "last":
                                return "末页";
                            case "page":
                                return page;
                        }
                    },//点击事件，用于通过Ajax来刷新整个list列表
                    onPageClicked: function (event, originalEvent, type, page) {
                        $.ajax({
                            url: "${pageContext.request.contextPath}/ibm/admin/manage/feedback/queryAllUserfeedback",
                            type: "GET",
                            dataType : "json",
                            data: "page=" + page,
                            success: function (data) {
                                content.empty();
                                $.each(data.data.list, function(i, n) {
                                    var tr = $("<tr></tr>").appendTo(content);
                                    $("<td class='text-center'></td>").text(n.userName).appendTo(tr);
                                    $("<td class='text-center'></td>").text(n.code).appendTo(tr);
                                    $("<td class='text-center'></td>").text(n.title).appendTo(tr);
                                    $("<td class='text-center'></td>").text(new Date(parseInt(n.time)).toLocaleString('chinese', {hour12: false})).appendTo(tr);
                                    $("<td class='text-center'></td>").text(n.stateCn).appendTo(tr);
                                    var aGroup = $("<div class='a'>");
                                    $("<td style='padding: 4px;text-align: center;'></td>").append(aGroup).appendTo(tr);
                                    var editBtn = $("<a style='text-decoration:none;cursor:pointer'>查看</a>").appendTo(aGroup);
                                    editBtn.click(function () {
                                        queryFeedbackDetails(n.code);
                                    });
                                    var span = $("<span>&nbsp;&nbsp;</span>").appendTo(editBtn);
                                    var editBtn2 = $("<a style='text-decoration:none;cursor:pointer'>处理</a>").appendTo(aGroup);
                                    editBtn2.click(function () {
                                        updateFeedbackDetails(n.pk,n.state);
                                    });
                                });
                            }
                        });
                    }
                };
                $('#pages').bootstrapPaginator(options);
            }
        });
    }

    $(document).ready(function(){
        initTable();
    });

</script>

<script>

    function queryFeedbackDetails(code) {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/feedback/queryFeedbackCode?code=" + code;
    }
    function updateFeedbackDetails(pk,state) {
        if ("OPEN"===state){
            window.alert("该反馈已处理！！！");
        } else {
            window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/feedback/queryFeedbackPk?pk=" + pk;
        }
    }
    function submitFrom() {
        var form = $("#query");
        console.log(form.serialize(),"sas");
        $.ajax({
            type: 'GET',
            dataType : "json",
            async:false,
            url: "${pageContext.request.contextPath}/ibm/admin/manage/feedback/screenFeedback",//请求的action路径页面
            data: form.serialize(),
            error: function () {//请求失败处理函数
                alert('请求失败');
            },
            success:function(data){ //请求成功后处理函数。
                var content = $(".tbodyDatas");
                content.empty();

                $.each(data.data.list, function(i, n) {
                    var tr = $("<tr></tr>").appendTo(content);
                    $("<td class='text-center'></td>").text(n.userName).appendTo(tr);
                    $("<td class='text-center'></td>").text(n.code).appendTo(tr);
                    $("<td class='text-center'></td>").text(n.title).appendTo(tr);

                    $("<td class='text-center'></td>").text(new Date(parseInt(n.time)).toLocaleString('chinese', {hour12: false})).appendTo(tr);
                    $("<td class='text-center'></td>").text(n.stateCn).appendTo(tr);
                    var aGroup = $("<div class='a'>");
                    $("<td style='padding: 4px;text-align: center;'></td>").append(aGroup).appendTo(tr);
                    var editBtn = $("<a style='text-decoration:none;cursor:pointer'>查看</a>").appendTo(aGroup);
                    editBtn.click(function () {
                        queryFeedbackDetails(n.code);
                    });
                    var span = $("<span>&nbsp;&nbsp;</span>").appendTo(editBtn);
                    var editBtn2 = $("<a style='text-decoration:none;cursor:pointer'>处理</a>").appendTo(aGroup);
                    editBtn2.click(function () {
                        updateFeedbackDetails(n.pk,n.state);
                    });
                });

                var pageCount = data.data.totalPages; //取到pageCount的值
                var currentPage = data.data.prePage; //得到currentPage
                console.log(currentPage,pageCount, "1211");
                var options = {
                    bootstrapMajorVersion: 3, //版本
                    currentPage: currentPage, //当前页数
                    totalPages: pageCount, //总页数
                    numberOfPages: 5,
                    itemTexts: function (type, page) {
                        switch (type) {
                            case "first":
                                return "首页";
                            case "prev":
                                return "上一页";
                            case "next":
                                return "下一页";
                            case "last":
                                return "末页";
                            case "page":
                                return page;
                        }
                    },//点击事件，用于通过Ajax来刷新整个list列表
                    onPageClicked: function (event, originalEvent, type, page) {
                        $.ajax({
                            url: "${pageContext.request.contextPath}/ibm/admin/manage/feedback/queryAllUserfeedback",
                            type: "GET",
                            dataType : "json",
                            data: "page=" + page,
                            success: function (data) {
                                content.empty();
                                $.each(data.data.list, function(i, n) {
                                    var tr = $("<tr></tr>").appendTo(content);
                                    $("<td class='text-center'></td>").text(n.userName).appendTo(tr);
                                    $("<td class='text-center'></td>").text(n.code).appendTo(tr);
                                    $("<td class='text-center'></td>").text(n.title).appendTo(tr);
                                    $("<td class='text-center'></td>").text(new Date(parseInt(n.time)).toLocaleString('chinese', {hour12: false})).appendTo(tr);
                                    $("<td class='text-center'></td>").text(n.stateCn).appendTo(tr);
                                    var aGroup = $("<div class='a'>");
                                    $("<td style='padding: 4px;text-align: center;'></td>").append(aGroup).appendTo(tr);
                                    var editBtn = $("<a style='text-decoration:none;cursor:pointer'>查看</a>").appendTo(aGroup);
                                    editBtn.click(function () {
                                        queryFeedbackDetails(n.code);
                                    });
                                    var span = $("<span>&nbsp;&nbsp;</span>").appendTo(editBtn);
                                    var editBtn2 = $("<a style='text-decoration:none;cursor:pointer'>处理</a>").appendTo(aGroup);
                                    editBtn2.click(function () {
                                        updateFeedbackDetails(n.pk,n.state);
                                    });
                                });
                            }
                        });
                    }
                };
                $('#pages').bootstrapPaginator(options);
            }
        });


    }

    /**
     * 放入用户盘口数据
     * @param appUserId  用户主键
     * @param category 盘口类别
     */
    function showWord(detailed, pk) {

        console.log(detailed.split(","))
        var data = detailed.split(",");
        console.log(data[3])
        $(".user").val(data[0]);
        $(".time").val(data[2]);
        $(".title").val(data[3]);
        $(".info").val(data[4]);
        $(".pk").val(pk);
        $('#feedbackEdit').modal('show');
    }

    $(function () {
        $('#datetimepicker0').datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: moment.locale('zh-cn'),
            defaultDate: new Date()
        });
    });
    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: moment.locale('zh-cn'),
            defaultDate: new Date()
        });
    });

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
