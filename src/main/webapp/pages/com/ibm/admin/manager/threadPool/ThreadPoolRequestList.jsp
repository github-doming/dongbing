<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title></title>
    <style type="text/css">
        html {
            overflow-y: hidden;
        }
    </style>
</head>
<body>
<div style="height: 50px; border: hidden;"></div>

<table id="topic_title" style="width:95%;table-layout:fixed; margin: 0px auto; text-align: center;" id="id_table"
       class="table  table-bordered table-hover">
    <thead>
    <tr>
        <th style="width: 150px;text-align: center;">线程池类型</th>
        <th style="width: 150px;text-align: center;">当前的线程数</th>
        <th style="width: 150px;text-align: center;">正在执行任务数</th>
        <th style="width: 150px;text-align: center;">线程池核心大小</th>
        <th style="width: 150px;text-align: center;">在执行的任务总数</th>
        <th style="width: 150px;text-align: center;">线程池最大容量</th>
        <th style="width: 150px;text-align: center;">线程池空闲时间</th>
        <th style="text-align: center;">操作</th>
    </tr>
    </thead>
    <tbody class='body'>
    <c:forEach items="${executorConfigs}" var="executorConfig" varStatus="loop">
        <tr>
            <td id="name" style="width: 150px;">${executorConfig.name}</td>
            <td id="poolSize" style="width: 150px;">${executorConfig.poolSize}</td>
            <td id="activeCount" style="width: 150px;">${executorConfig.activeCount}</td>
            <td id="corePoolSize" style="width: 150px;">${executorConfig.corePoolSize}</td>
            <td id="taskCount" style="width: 150px;">${executorConfig.taskCount}</td>
            <td id="maximumPoolSize" style="width: 150px;">${executorConfig.maximumPoolSize}</td>
            <td id="keepAliveTime" style="width: 150px;">${executorConfig.keepAliveTimeSeconds}（s）</td>
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
    function edit() {
        var _from = $("#editConfig");
        _from.attr("action");
        console.log(_from.serialize());
        _from.serialize();
        $.post(_from.attr("action"), _from.serialize(), function (data) {
            if (data.success) {
                window.location.href = "${pageContext.request.contextPath}/ibm/admin/threadPool/requestList";
            } else {
                alert(data.messageSys);
            }
        }, "json");
    }

    function updateThreadPool(name, corePoolSize, maximumPoolSize, keepAliveTimeSeconds,state) {
        $(".threadPoolName").text(name);
        $("#threadPoolName").val(name);
        $("#threadPoolCorePoolSize").val(corePoolSize);
        $("#threadPoolMaximumPoolSize").val(maximumPoolSize);
        $("#threadPoolKeepAliveTimeSeconds").val(keepAliveTimeSeconds);
        $("#threadPoolState").val(state);
        $('#updateThreadPool').modal('show', true);
    }

</script>

<!------------------------------------------------------------------------- 修改线程池配置 -->
<div class="modal fade" style="margin-left: -10%;" id="updateThreadPool"
     tabindex="-2" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form class="updateThreadPool" id="editConfig" action="${pageContext.request.contextPath}/ibm/admin/threadPool/editRequestConfig">
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
                        <span class="input-group-addon"  id="basic-corePoolSize">线程池核心大小</span>
                        <input type="text" class="form-control" name="corePoolSize" id="threadPoolCorePoolSize">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon" id="basic-maximumPoolSize">线程池最大容量</span>
                        <input type="text" class="form-control" name="maximumPoolSize" id="threadPoolMaximumPoolSize">
                    </div>
                    <br>
                    <div class="input-group"><span class="input-group-addon" id="basic-keepAliveTimeSeconds">线程池空闲时间</span>
                        <input type="text" class="form-control" name="keepAliveTimeSeconds" id="threadPoolKeepAliveTimeSeconds">
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
