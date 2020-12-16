<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
</head>
<script type="text/javascript"
        src="<%=request.getContextPath() %>/pages/cosmos/lib/all/third/editor/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath() %>/pages/cosmos/lib/all/third/editor/kindeditor/lang/zh-CN.js"></script>
<body>
<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp" %>
<center>
    <div style="color:white;width:100%;height:100px;background-color:rgb(29,41,57);padding-top:20px">
		<span style="font-size:30px;">方案管理
			<c:choose>
                <c:when test="${requestScope.id==null}">添加</c:when>
                <c:otherwise>修改</c:otherwise>
            </c:choose>
		</span>
    </div>
</center>
<form id="id_form_save"
      action="${pageContext.request.contextPath}/ibm/admin/ibm_plan/w/save.do"
      method="post">
    <input style="width:100px;height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white;margin: 20px;"
           onclick="back();" class="btn"
           type="button" value="返回列表"></input>
    <table style="width: 80%;margin: 50px auto;text-align: center;" class="table  table-bordered table-hover"
           border="1">
        <tr>
            <td align="right">方案名称</td>
            <td align="left"><input id="topicId_input" type="hidden" name="IBM_PLAN_ID_"
                                    value="${requestScope.s.ibmPlanId}">
                <input id="id_input$ibm_input$PLAN_NAME_" placeholder="请输入方案名称" type="text" name="PLAN_NAME_"
                       value="${requestScope.s.planName}">
                <span id="id_span$ibm_plan$PLAN_NAME_"></span>

            </td>
        </tr>
        <tr>
            <td align="right">方案CODE</td>
            <td align="left"><input id="id_input$ibm_input$PLAN_CODE_" placeholder="请输入方案CODE" type="text"
                                    name="PLAN_CODE_"
                                    value="${requestScope.s.planCode}">
                <span id="id_span$ibm_plan$PLAN_CODE_"></span>
            </td>
        </tr>
        <tr>
            <td align="right">方案表名</td>
            <td align="left"><input id="id_input$ibm_input$PLAN_ITEM_TABLE_NAME_" placeholder="请输入方案表名" type="text"
                                    name="PLAN_ITEM_TABLE_NAME_"
                                    value="${requestScope.s.planItemTableName}">
                <span id="id_span$ibm_plan$PLAN_ITEM_TABLE_NAME_"></span>
            </td>
        </tr>
        <tr>
            <td align="right">所属游戏</td>
            <td align="left">
                <div id="div_game"></div>
                <span id="id_span$ibm_plan$GAME"></span>
            </td>
        </tr>
        <tr>
            <td align="right">方案类型</td>
            <td align="left"><input id="id_input$ibm_input$PLAN_TYPE_" placeholder="请输入方案类型" type="text"
                                    name="PLAN_TYPE_"
                                    value="${requestScope.s.planType}">
                <span id="id_span$ibm_plan$PLAN_TYPE_"></span>
            </td>
        </tr>
        <tr>
            <td align="right">方案说明</td>
            <td align="left"><input id="id_input$ibm_input$PLAN_EXPLAIN_" placeholder="请输入方案说明" type="text"
                                    name="PLAN_EXPLAIN_"
                                    value="${requestScope.s.planExplain}">
                <span id="id_span$ibm_plan$PLAN_EXPLAIN_"></span>
            </td>
        </tr>
        <tr>
            <td align="right">方案价值</td>
            <td align="left"><input id="id_input$ibm_input$PLAN_WORTH_T_" placeholder="请输入方案价值" type="text"
                                    name="PLAN_WORTH_T_"
                                    value="${requestScope.s.planWorthT}">
                <span id="id_span$ibm_plan$PLAN_WORTH_T_"></span>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <c:choose>
                    <c:when test="${requestScope.id==null}">
                        <input style="width:300px;height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white"
                               onclick='save();' class="btn" type="button" value="新增">
                    </c:when>
                    <c:otherwise>
                        <input style="width:300px;height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white"
                               onclick='save();' class="btn" type="button" value="编辑">
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</form>
</body>
<!-- 加载js -->
<script type="text/javascript">

    $(document).ready(function () {
        $.ajax({
            url: "<%=request.getContextPath()%>/ibm/admin/ibm_game/w/listJson.do",
            dataType: "json",
            type: "post",
            success: function (data) {
                var div_game = $("#div_game");
                $(data.data).each(function (index, value) {
                    var label = $('<label style="padding-left: 10px;"></label>').appendTo(div_game);
                    label.text(value.GAME_NAME_);
                    var check = $('<input type="checkbox" name="GAME_ID_"/>').appendTo(label);
                    check.val(value.IBM_GAME_ID_);
                    if (value.IBM_GAME_ID_ == '${requestScope.s.gameId}') {
                        check.attr('checked', 'checked')
                    }
                });
            },
            error: function () {
                alert("失败");
            }
        });
    });


    /**
     *
     返回
     */
    function back() {
        history.go(-1);
    }

    /**
     *
     检查
     */
    function check() {
        var a = "<font color=red>自定义信息</font>";
        a = null;
        var flag = null;
        var return_flag = true;
        flag = ayCheckColumn("方案名称", "id_span$ibm_plan$PLAN_NAME_", "id_input$ibm_input$PLAN_NAME_", "VARCHAR", "no", "64", "0", "0", a, true);
        if (flag) {
        } else {
            return_flag = false;
        }
        flag = ayCheckColumn("方案CODE", "id_span$ibm_plan$PLAN_CODE_", "id_input$ibm_input$PLAN_CODE_", "VARCHAR", "no", "64", "0", "0", a, true);
        if (flag) {
        } else {
            return_flag = false;
        }
        flag = ayCheckColumn("方案类型", "id_span$ibm_plan$PLAN_TYPE_", "id_input$ibm_input$PLAN_TYPE_", "VARCHAR", "no", "64", "0", "0", a, true);
        if (flag) {
        } else {
            return_flag = false;
        }
        flag = ayCheckColumn("方案说明", "id_span$ibm_plan$PLAN_EXPLAIN_", "id_input$ibm_input$PLAN_EXPLAIN_", "VARCHAR", "yes", "255", "0", "0", a, false);
        if (flag) {
        } else {
            return_flag = false;
        }
        flag = ayCheckColumn("方案价值", "id_span$ibm_plan$PLAN_WORTH_T_", "id_input$ibm_input$PLAN_WORTH_T_", "INT", "no", "3", "0", "0", a, true);
        if (flag) {
        } else {
            return_flag = false;
        }
        return return_flag;
    }

    /**
     *
     保存
     */
    function save() {
        var flag = check();
        if (flag) {
        } else {
            return false;
        }
        //提交
        var obj_form = document.getElementById('id_form_save');
//	editor.sync();
        obj_form.submit();
    }
</script>
<script type="text/javascript">
    //初始化日期
</script>
</html>