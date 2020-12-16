<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/pages/com/ibm/admin/manager/Period/static/moment-with-locales.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>

</head>
<body>
<div style="height: 40px; border: hidden;"></div>
<div>
    <button style="margin-left: 2.2%;"  class="btn btn-browm" onclick="choose()">选择游戏</button>
    <button style="margin-left: 5%;" class="btn btn-browm" onclick="savePeriodsCondition()">添加禁止开奖时间</button>
    <button style="margin-left: 8%;"  class="btn btn-browm" onclick="findPeriodsFind()">查询开奖信息</button>

    <table id="topic_title" style="width:25%;table-layout:fixed; position: absolute; top: 100px; left: 2%;
    text-align: center;" id="id_table" class="table  table-bordered table-hover">
        <thead>
        <tr>
            <th style="width: 50px;text-align: center;">游戏</th>
            <th style="width: 150px;text-align: center;">禁止开奖时间</th>
        </tr>
        </thead>
        <tbody class='body'>
        <c:forEach items="${notTime}" var="notTime">
            <tr>
                <td id="gameName" style="width: 50px;">${notTime.gameName}</td>
                <td id="time" style="width: 150px;">${notTime.one} <b>&#12288;</b> ${notTime.two}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table style="width: 60%;table-layout:fixed; position: absolute; top: 100px; left: 28%;
    text-align: center;" class="table  table-bordered table-hover">
        <thead>
        <tr>
            <th style="width: 50%; text-align: center;">期数</th>
            <th style="width: 50%; text-align: center;">开奖时间</th>
        </tr>
        </thead>
        <tbody id="periodsData" class='body'>

        </tbody>
    </table>
</div>


<script type="text/javascript">
    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: moment.locale('zh-cn'),
            defaultDate: "2019-10-01 00:00"
        });
    });
    $(function () {
        $('#datetimepicker2').datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: moment.locale('zh-cn'),
            defaultDate: "2019-10-07 23:59"
        });
    });
    $(function () {
        $('#datetimepicker3').datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: moment.locale('zh-cn'),
            defaultDate: "2019-10-12 10:10"
        });
    });

    function checkDate() {
        var formData = $("#formData");
        $.getJSON(formData.attr("action"), formData.serialize(), function (data) {
            if (data.success) {
                var contentDiv = $("#periodsData");
                contentDiv.html('');
                //循环获取数据
                $.each(data.data, function (index, periods) {
                    $("<tr>").appendTo(contentDiv);
                    var dataPeriod = $("<td style='width: 50%; border-bottom: 1px #DDDDDD solid;'></td>").appendTo(contentDiv);
                    dataPeriod.text(periods.periods);
                    var dataDrawTime = $("<td style='width: 50%; border-bottom: 1px #DDDDDD solid;'></td>").appendTo(contentDiv);
                    dataDrawTime.text(periods.drawTime);
                    $("</tr>").appendTo(contentDiv);
                    $("<hr style='filter: alpha(opacity=100,finishopacity=0,style=3)' width='100%' color='#6f5499' size='3'/>").appendTo(contentDiv);
                });
                $('#inputData').modal('hide');
            } else {
                alert(data.message);
            }
        });
    }

    function savePeriodsCondition() {
        $('#banTime').modal('show', true);
    }
    function findPeriodsFind() {
        $('#inputData').modal('show', true);
    }

    function savePeriods() {
        var formData = $("#editConfig");
        $.getJSON(formData.attr("action"), formData.serialize(), function (data) {
            if (data.success) {
                $('#banTime').modal('hide');
                alert('添加成功');
                window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/other/period ";
            }else {
                alert(data.message);
            }
        });
    }

    function choose() {
        $('#chooseGame').modal('show',true);
    }

    function chooseGame() {
        var gameCode = $("#gameCode").val();
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/other/period?gameCode="+gameCode;
        $('#chooseGame').modal('hide');
    }

</script>


<!------------------------------------------------------------------------- 禁止开奖时间 -->
<div style="width: 70%; margin-left: 10%; margin-top: 100px;" class="modal fade" id="banTime" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 600px;">
        <div class="modal-content">
            <form class="savePeriodsCondition" id="editConfig" action="${pageContext.request.contextPath}/ibm/admin/periods/savePeriodsCondition">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times;</button>
                    <h4 class="modal-title">
                        请输入禁止开奖时间段
                    </h4>
                </div>
                <div class="modal-body">
                    <div class='col-sm-6'>
                        <div class="form-group">
                            <!--指定 date标记-->
                            <div class='input-group date' id='datetimepicker1'>
                                <input type='text' class="form-control" name="firstTime" />
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class='col-sm-6' style="margin-top: -20px;">
                        <!--指定 date标记-->
                        <div class="form-group">
                            <div class='input-group date' id='datetimepicker2'>
                                <input type='text' class="form-control" name="endTime" />
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">选择游戏</span>
                        <select style="height: 35px; width: 100%;" name="gameCode">
                            <option value="PK10">北京赛车</option>
                            <option value="XYFT" selected="selected">幸运飞艇</option>
                        </select>
                    </div>
                    <br>

                </div>
                <div class="modal-footer">
                    <button class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" onclick="savePeriods()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 禁止开奖时间END -->

<!------------------------------------------------------------------------- inputData -->
<div style="width: 70%; margin-left: 10%; margin-top: 100px;" class="modal fade" id="inputData" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 500px;">
        <div class="modal-content">
            <form class="formData" id="formData" action="${pageContext.request.contextPath}/ibm/admin/periods/findPeriodsFind">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times;</button>
                    <h4 class="modal-title">
                        请输入查询时间-查询条数
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span style="float: left; margin-top: 8px; margin-left:20px;">选择时间：</span>
                        <!--指定 date标记-->
                        <div class="form-group" style="width: 400px;">
                            <div class='input-group date' id='datetimepicker3'>
                                <input type='text' class="form-control" name="time" />
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="input-group">
                        <span style="float: left; margin-top: 6px; margin-left:20px;">查询条数：</span>
                        <input style="width: 335px; height: 32px; border-radius: 3px;" type="number" value="15" name="number">
                    </div>
                    <br>

                    <div class="input-group">
                        <span style="float: left; margin-top: 20px; margin-left:20px;">选择游戏：</span>
                        <select style="width: 335px; margin-top:12px;  height: 32px; border-radius: 3px;" name="gameCode">
                            <option value="PK10" selected="selected">北京赛车</option>
                            <option value="XYFT">幸运飞艇</option>
                        </select>
                    </div>
                    <br>
                    <br>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" onclick="checkDate()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- inputData -->


<!------------------------------------------------------------------------- chooseGame -->
<div style="width: 70%; margin-left: 10%; margin-top: 100px;" class="modal fade" id="chooseGame" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 500px;">
        <div class="modal-content">
            <form id="choose" action="${pageContext.request.contextPath}/ibm/admin/manage/other/period">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times;</button>
                    <h4 class="modal-title">
                        选择游戏
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon">选择游戏</span>
                        <select style="height: 35px; width: 100%;" id="gameCode" name="gameCode">
                            <option value="PK10" selected="selected">北京赛车</option>
                            <option value="XYFT">幸运飞艇</option>
                        </select>
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" onclick="chooseGame()" class="btn btn-primary">确定</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- chooseGame -->
</body>
</html>
