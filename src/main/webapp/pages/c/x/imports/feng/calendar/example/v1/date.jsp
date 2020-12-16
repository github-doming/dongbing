<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<%
String version="1";
%>
</head>
<body>
http://localhost:8080/a/pages/c/x/imports/feng/calendar/example/v1/date.jsp
<p></p>
<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>
<table>
	<tr>
		<td>
<input id="id_input_a" type="text" value="a"></input> <img
			onclick=ayCalendarShow('id_input_a',event); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
	</tr>
	<tr>
		<td>
<input id="id_input_b" type="text" value="a"></input> <img
			onclick=ayCalendarShow('id_input_b',event); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
	</tr>
</table>
</body>
</html>
<script type="text/javascript">
ayCalendarNow('id_input_a');
ayCalendarNow('id_input_b');
</script>
