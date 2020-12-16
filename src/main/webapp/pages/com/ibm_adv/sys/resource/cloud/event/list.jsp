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
        <span style="font-size:30px;">事件信息管理</span>
    </div>
</center>
<form action="" id="id_form_list" method="post">
    <table id="topic_title" style="width:90%;table-layout:fixed; margin: 0px auto;" id="id_table"
           class="table  table-bordered table-hover">
        <caption></caption>
        <thead>
        <tr>
            <th style="width: 30px;"><input id="id_checkbox_if_all" onclick=ayTableCheckAll(); type="checkbox"
                                            name="name_checkbox_if_all"/>
            </th>
            <th style="width: 90px;">操作</th>
            <th style="width: 50px;">状态</th>
            <th style="width: 90px;">事件名称</th>
            <th style="width: 90px;">事件编码</th>
            <th style="width: 60px;">执行数</th>
            <th style="width: 60px;">等待数</th>
            <th>消息</th>
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
        setInterval(getList, 5000);
    });

    function getList() {
        var url = "${pageContext.request.contextPath}/ibm/sys/resource/cloud/event/show.dm";

        jQuery.getJSON(url, function (showList) {
            var body = $('.body');
            body.empty();
            jQuery.each(showList, function (i, data) {
                var tr = $('<tr></tr>').appendTo(body);
                var checkboxTd = $('<td></td>').appendTo(tr);
                var checkbox = $('<input type="checkbox" name="name_checkbox_ids">').appendTo(checkboxTd);
                checkbox.val(data.code);

                var editTd = $('<td></td>').appendTo(tr);
                var editA = $('<a></a>').appendTo(editTd);
                editA.text('编辑');
                editA.attr('href', '${pageContext.request.contextPath}/ibm/sys/resource/cloud/event/form.dm?code=' + data.code);
                if ('停止' === data.state) {
                    $('<span>&nbsp;&nbsp;&nbsp;</span>').appendTo(editTd);
                    var openA = $("<a  href='#'></a>").appendTo(editTd);
                    openA.attr('href', '${pageContext.request.contextPath}/ibm/sys/resource/cloud/event/set.dm?cmd=state&state=true&code=' + data.code);
                    openA.text('开启');
                } else {
                    $('<span>&nbsp;&nbsp;&nbsp;</span>').appendTo(editTd);
                    var stopA = $("<a  href='#'></a>").appendTo(editTd);
                    stopA.attr('href', '${pageContext.request.contextPath}/ibm/sys/resource/cloud/event/set.dm?cmd=state&state=false&code=' + data.code);
                    stopA.text('停止');
                }

                $('<td></td>').text(data.state).appendTo(tr);
                $('<td></td>').text(data.name).appendTo(tr);
                $('<td></td>').text(data.code).appendTo(tr);
                $('<td></td>').text(data.active).appendTo(tr);
                $('<td></td>').text(data.wait).appendTo(tr);
                $('<td></td>').text(JSON.stringify(data.msg)).appendTo(tr);
            });
        });
    }


</script>
</html>