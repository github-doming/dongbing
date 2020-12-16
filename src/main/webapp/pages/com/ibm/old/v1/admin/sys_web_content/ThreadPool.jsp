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
<div style="display: none;" id="id_div_msg">正在提交中...</div>
<center>
    <div style="color:white;width:100%;height:100px;background-color:rgb(29,41,57);padding-top:20px">
        <span style="font-size:30px;">线程池管理</span>
    </div>
</center>
<form action="" id="id_form_list" method="post">
    <table id="topic_title" style="width:90%;table-layout:fixed; margin: 0px auto;" id="id_table"
           class="table  table-bordered table-hover">
        <caption></caption>
        <thead>
        <tr>
            <th style="width: 10%;"><input id="id_checkbox_if_all" onclick=ayTableCheckAll(); type="checkbox"
                                           name="name_checkbox_if_all"/>全选
            </th>
            <th style="width: 10%;">操作</th>
            <th>线程池类型</th>
            <th>当前的线程数</th>
            <th>正在执行任务数</th>
            <th>线程池核心大小</th>
            <th>已安排执行的大致任务总数</th>
            <th>线程池最大容量</th>
            <th>线程池空闲时间</th>
        </tr>
        </thead>
        <tbody class='body'>


        </tbody>
    </table>
    <br/>
</form>
</body>
<script>
    var index = 0;
    $(function () {
        getList();
        setInterval('getList()', 1000);
    });

    function getList() {
        var url = "${pageContext.request.contextPath}/ibm/admin/content/thread/list.dm";

        jQuery.getJSON(url, function (data) {
            var body = $('.body');
            body.empty();
            var tr = $('<tr></tr>').appendTo(body);
            var checkboxTd = $('<td></td>').appendTo(tr);
            var checkbox = $('<input type="checkbox" name="name_checkbox_ids">').appendTo(checkboxTd);
            checkbox.val('core');

            var editTd = $('<td></td>').appendTo(tr);
            var editA = $('<a></a>').appendTo(editTd);
            editA.text('编辑');
            editA.attr('href', '${pageContext.request.contextPath}/pages/com/ibm/admin/sys_web_content/ThreadPoolForm.jsp?' +
                'code=core&corePoolSize=' + data.core.corePoolSize + '&maximumPoolSize=' + data.core.maximumPoolSize
                + '&keepAliveTimeSeconds=' + data.core.keepAliveTimeSeconds);

            $('<td></td>').text('core').appendTo(tr);
            $('<td></td>').text(data.core.poolSize).appendTo(tr);
            $('<td></td>').text(data.core.activeCount).appendTo(tr);
            $('<td></td>').text(data.core.corePoolSize).appendTo(tr);
            $('<td></td>').text(data.core.taskCount).appendTo(tr);
            $('<td></td>').text(data.core.maximumPoolSize).appendTo(tr);
            $('<td></td>').text(data.core.keepAliveTimeSeconds).appendTo(tr);


            tr = $('<tr></tr>').appendTo(body);
            checkboxTd = $('<td></td>').appendTo(tr);
            checkbox = $('<input type="checkbox" name="name_checkbox_ids">').appendTo(checkboxTd);
            checkbox.val('query');

            editTd = $('<td></td>').appendTo(tr);
            editA = $('<a></a>').appendTo(editTd);
            editA.text('编辑');
            editA.attr('href', '${pageContext.request.contextPath}/pages/com/ibm/admin/sys_web_content/ThreadPoolForm.jsp?' +
                'code=query&corePoolSize=' + data.query.corePoolSize + '&maximumPoolSize=' + data.query.maximumPoolSize
                + '&keepAliveTimeSeconds=' + data.query.keepAliveTimeSeconds);

            $('<td></td>').text('query').appendTo(tr);
            $('<td></td>').text(data.query.poolSize).appendTo(tr);
            $('<td></td>').text(data.query.activeCount).appendTo(tr);
            $('<td></td>').text(data.query.corePoolSize).appendTo(tr);
            $('<td></td>').text(data.query.taskCount).appendTo(tr);
            $('<td></td>').text(data.query.maximumPoolSize).appendTo(tr);
            $('<td></td>').text(data.query.keepAliveTimeSeconds).appendTo(tr);


        });
    }


</script>
</html>