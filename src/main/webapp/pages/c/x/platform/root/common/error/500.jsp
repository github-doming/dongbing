<!DOCTYPE html>
<html>
<head>
<title>Page Not Found</title>
<meta charset="utf-8">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" /> 
<meta name="renderer" content="webkit" />
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<script src="${pageContext.request.contextPath}/resources2/pangolin/js/jq.js"></script>
<!-- 
<link href="${pageContext.request.contextPath}/resources2/css/errorPage.css"  rel="stylesheet" />
-->
<!--[if lt IE 8]>
<script src="resources/js/selectivizr.js" type="text/javascript"></script>
<script src="resources/js/respond.min.js" type="text/javascript"></script>
<![endif]-->
</head>
<style>
body { background:#f5f5f5; font-family:'Microsoft YaHei'; color:#393939; }
hr { width:90%; border:1px solid #e9e9e9; margin-top:10px; }
.page_error { width:100%; }
.error_title { width:300px; height:40px; line-height:40px; color:#777; font-size:28px; margin:0 auto; margin-top:5px; }
.error_title span { float:left; color:#428bca; }
.emain { margin-top:15px; width:610px; font-size:18px; margin:0 auto; }
.esearch { width:410px; margin:0 auto; height:40px; line-height:40px; margin-top:10px; position:relative; } 
.einput { float:left; height:30px; border:1px solid #eee; line-height:30px; width:350px; }
.ebutton { float:right; background-color:#428bca; width:55px; height:30px; color:#fff; text-align:center; line-height:30px; position:absolute; top:1px; right:0; cursor:pointer; }
.ebottom { position:fixed; bottom:10px; width:100%; height:45px; }
.ebottom_width { width:260px; margin:0 auto; height:100%;}
.ebottom_width span { width:125px; float:left; height:45px; line-height:45px; background-color:#a0a0a0; text-align:center; color:#fff; cursor:pointer; }
</style>
<body>
<div class="page_error">
	<div class="error_title"><span>500</span>&nbsp;&nbsp;Server Error</div><hr>
	<div class="emain">There was an error. Please try again later. That's all we know.</div>
	<div class="esearch"><input type="text" class="einput" placeholder="Please write..." /> <span class="ebutton">Go</span></div>
</div>
<div class="ebottom">
	<div class="ebottom_width"><span>Go back</span><span style="margin-left:2px;background-color:#428bca;">Close</span></div>
</div>
</body>
</html>