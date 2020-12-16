<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title></title>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.2/css/bootstrap3/bootstrap-switch.min.css"
          rel="stylesheet">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.2/js/bootstrap-switch.min.js"></script>

</head>
<body>

<div class="row" >
    <div  style="padding: 5px 5px 5px 50px;">
        <button class="btn btn-primary"  onclick="editAll('true')">开启所有线程池</button>
        <button class="btn btn-brown" onclick="editAll('false')">关闭所有线程池</button>
    </div>
</div>


<table id="topic_title" style="width:95%;table-layout:fixed; margin: 0px auto; text-align: center;" id="id_table"
       class="table  table-bordered table-hover">
    <thead>
    <tr>
        <th style="text-align: center;">状态</th>
        <th style="width: 150px;text-align: center;">线程池类型</th>
        <th style="width: 150px;text-align: center;">正在执行任务数</th>
        <th style="width: 150px;text-align: center;">线程池核心大小</th>
        <th style="width: 150px;text-align: center;">当前的线程数</th>
        <th style="width: 150px;text-align: center;">执行任务的总数</th>
        <th style="width: 150px;text-align: center;">线程池最大容量</th>
        <th style="width: 150px;text-align: center;">线程池空闲时间</th>
        <th style="text-align: center;">线程消息</th>
        <th style="text-align: center;">操作</th>
    </tr>
    </thead>
    <tbody class='body'>
    <c:forEach items="${executorConfigs}" var="executorConfig" varStatus="loop">
        <tr>
            <td id="state" style="width: 150px;">HqMemberTool
                <input type="checkbox" class="threadState"
                       <c:if test="${executorConfig.state eq 'true'}">checked</c:if>
                       onchange="changeStatus('${executorConfig.name}',this)">
            </td>
            <td id="name" style="width: 150px;">${executorConfig.name}</td>
            <td id="activeCount" style="width: 150px;">${executorConfig.activeCount}</td>
            <td id="poolSize" style="width: 150px;">${executorConfig.poolSize}</td>
            <td id="corePoolSize" style="width: 150px;">${executorConfig.corePoolSize}</td>
            <td id="taskCount" style="width: 150px;">${executorConfig.taskCount}</td>
            <td id="maximumPoolSize" style="width: 150px;">${executorConfig.maximumPoolSize}</td>
            <td id="keepAliveTime" style="width: 150px;">${executorConfig.keepAliveTimeSeconds}（s）</td>
            <td style="width: 150px;">
                <button class="btn btn-primary" onclick="showEvent('${executorConfig.name}')">查看</button>
            </td>
            <td>
                <button class="btn btn-brown"
                        onclick="updateThreadPool('${executorConfig.name}','${executorConfig.corePoolSize}',
                                '${executorConfig.maximumPoolSize}','${executorConfig.keepAliveTimeSeconds}',
                                '${executorConfig.state}')">
                    修改
                </button>
            </td>
        </tr>
    </c:forEach>
    </tbody>

</table>
<script type="text/javascript">
    $(function () {
        $(".threadState").bootstrapSwitch();
    });

    function editAll(state) {
        if (confirm("确定要进行操作？")) {
            $.getJSON("${pageContext.request.contextPath}/ibm/admin/threadPool/changeState?name=ALL&state=" + state, function (data) {
                if (data.success) {
                    window.location.href = "${pageContext.request.contextPath}/ibm/admin/threadPool/eventList";
                } else {
                    alert(data.msg);
                }
            });
        } else
            return false;
    }

    function edit() {
        var _from = $("#editConfig");
        console.log(_from.serialize());
        _from.serialize();
        $.post(_from.attr("action"), _from.serialize(), function (data) {
            if (data.success) {
                window.location.href = "${pageContext.request.contextPath}/ibm/admin/threadPool/eventList";
            } else {
                alert(data.messageSys);
            }
        }, "json");
    }

    function changeStatus(name, _this) {
        $.getJSON("${pageContext.request.contextPath}/ibm/admin/threadPool/changeState?name=" + name + "&state=" + _this.checked , function (data) {
            if (data.success) {
                window.location.href = "${pageContext.request.contextPath}/ibm/admin/threadPool/eventList";
            } else {
                alert(data.msg);
            }
        });

    }
    function updateThreadPool(name, corePoolSize, maximumPoolSize, keepAliveTimeSeconds, state) {
        $(".threadPoolName").text(name);
        $("#threadPoolName").val(name);
        $("#threadPoolCorePoolSize").val(corePoolSize);
        $("#threadPoolMaximumPoolSize").val(maximumPoolSize);
        $("#threadPoolKeepAliveTimeSeconds").val(keepAliveTimeSeconds);
        $("#threadPoolState").val(state);
        $('#updateThreadPool').modal('show', true);
    }

    function showEvent(threadPoolCode) {
        $.getJSON("${pageContext.request.contextPath}/ibm/admin/threadPool/msg?threadPoolCode=" + threadPoolCode, function (data) {
            if (!data.success) {
                alert(data.msg);
            } else {
                var contentDiv = $("#threadPoolCode");
                contentDiv.html('');
                //提示栏
                $("<div style='float: left; margin: 3px 0 3px 3%;  width: 200px; text-align: center;'></div>").appendTo(contentDiv).text("线程名");
                $("<div style='float: left; margin: 3px 0 3px 3%;  width: 250px; text-align: center;'></div>").appendTo(contentDiv).text("事件消息");

                $("<hr style='filter: alpha(opacity=100,finishopacity=0,style=3)' width='100%' color='#6f5499' size='3'/>").appendTo(contentDiv);
                //循环获取数据
                $.each(data.data, function (index, event) {
                    var eventCustomerType = $("<div style='float: left; margin: 3px 0 3px 3%; width: 200px; word-wrap:break-word; overflow:hidden; text-align: center;'></div>").appendTo(contentDiv);
                    eventCustomerType.text(event.threadName);
                    var eventEventContent = $("<div style='float: left; margin: 3px 0 3px 3%; width: 250px; word-wrap:break-word; overflow:hidden; text-align: center;'></div>").appendTo(contentDiv);
                    eventEventContent.text(event.threadMessage);
                    $("<hr style='filter: alpha(opacity=100,finishopacity=0,style=3)' width='100%' color='#6f5499' size='3'/>").appendTo(contentDiv);
                });
                $(".threadPoolCode").text(threadPoolCode);
                $('#showThreadPoolCode').modal('show', true);
            }
        });


    }
</script>

<!------------------------------------------------------------------------- 事务 -->
<div style="width: 70%; margin-left: 10%;" class="modal fade" id="showThreadPoolCode" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title"> 事件　---　<span class="threadPoolCode"></span></h4>
            </div>
            <div class="modal-body" style="min-height:500px;">
                <div id="threadPoolCode">

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 事务end -->

<!------------------------------------------------------------------------- 修改线程池配置 -->
<div class="modal fade" style="margin-left: -10%;" id="updateThreadPool"
     tabindex="-2" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form class="updateThreadPool" id="editConfig"
                  action="${pageContext.request.contextPath}/ibm/admin/threadPool/editEventConfig">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times;</button>
                    <h4 class="modal-title">
                        修改配置　---　<span class="threadPoolName"></span></h4>
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group"><span class="input-group-addon" id="basic-name">线程池类型　　</span>
                        <input type="text" class="form-control" name="name" id="threadPoolName" readonly>
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-corePoolSize">线程池核心大小</span>
                        <input type="text" class="form-control" name="corePoolSize" id="threadPoolCorePoolSize">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon" id="basic-maximumPoolSize">线程池最大容量</span>
                        <input type="text" class="form-control" name="maximumPoolSize" id="threadPoolMaximumPoolSize">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon"
                                                   id="basic-keepAliveTimeSeconds">线程池空闲时间</span>
                        <input type="text" class="form-control" name="keepAliveTimeSeconds"
                               id="threadPoolKeepAliveTimeSeconds">
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" onclick="edit()" class="btn btn-primary">提交更改</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!------------------------------------------------------------------------- 修改线程池配置END -->

</body>
</html>
