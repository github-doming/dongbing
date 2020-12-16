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
</head>
<body>
<div style="display: none;" id="id_div_msg">正在提交中...</div>

<div class="head">
    <div class="col-xs-10 col-xs-offset-1">
        <form class="form-inline head-form" style="padding:  15px 0 15px 0">
            <p>
                  <button class="btn btn-mini btn-default" type="button"  onclick="back('${feedBackInfo.type}')" >返回</button>
                  <button class="btn btn-mini btn-primary" type="reset"   onclick="updateFeedbackDetails(${feedBackInfo.pk},'${feedBackInfo.type}','${feedBackInfo.state}')">反馈处理</button>
            </p>
        </form>
    </div>
</div>
<div class="content">
    <div class="col-xs-10 col-xs-offset-1">

        <table align="left" border="0" class="table" contenteditable="true">
            <thead>
            </thead>
            <tbody>
            <tr>
                <td colspan="2" style="width: 60px; background-color: rgb(245, 245, 245); border-color: rgb(245, 245, 245);">反馈编码：${feedBackInfo.code }</td>
                <td colspan="2" style="width: 85%; white-space: nowrap; text-align: left; vertical-align: middle; background-color: rgb(245, 245, 245); border-color: rgb(245, 245, 245);">反馈时间：${feedBackInfo.time }</td>
            </tr>
            <tr>
                <td colspan="4" style="background-color: rgb(245, 245, 245); border-color: rgb(245, 245, 245);">反馈标题：${feedBackInfo.title }</td>
            </tr>
            <tr>
                <td colspan="4" style="background-color: rgb(245, 245, 245); border-color: rgb(245, 245, 245);">反馈内容：${feedBackInfo.info }</td>
            </tr>
            </tbody>
        </table>

        <c:forEach items="${feedBackResults}" var="result" varStatus="loop">
            <table align="left" border="0" class="table table-bordered" contenteditable="true">
                <thead>
                </thead>
                <tbody>
                <tr>
                    <th colspan="4" style="width: 60px; background-color: rgb(245, 245, 245); border-color: rgb(245, 245, 245);">${result.time }</th>
                </tr>
                <tr>
                    <td colspan="2" style="width: 60px; border-color: rgb(255, 255, 255);">处理人：${result.updateUser }</td>
                    <td colspan="2" style="width: 85%; white-space: nowrap; text-align: left; vertical-align: middle; border-color: rgb(255, 255, 255);">
                        处理状态：${result.state }</td>
                </tr>
                <tr>
                    <td colspan="4" style="border-color: rgb(255, 255, 255);">处理结果：${result.result }</td>
                </tr>
                </tbody>
            </table>

        </c:forEach>
    </div>
</div>


<script>


    function back(type) {
        if("BUG" === type ){
            window.location.href = "${pageContext.request.contextPath}/pages/com/ibm/admin/manager/feedback/feedback_list_bug.jsp";
        }
        if("USERS" === type){
            window.location.href = "${pageContext.request.contextPath}/pages/com/ibm/admin/manager/feedback/feedback_list_user.jsp";
        }

    }

    function queryFeedbackDetails(code) {
        window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/feedback/queryFeedbackCode?code=" + code;
    }
    function updateFeedbackDetails(pk,type,state) {
        if ("OPEN"===state){
            window.alert("该反馈已处理！！！");
        } else {
            window.location.href = "${pageContext.request.contextPath}/ibm/admin/manage/feedback/queryFeedbackPk?pk=" + pk;
        }


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
