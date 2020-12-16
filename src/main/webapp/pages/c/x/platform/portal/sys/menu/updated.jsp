<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">




<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>






<script>


	alert("操作成功");
	window.returnValue=true;
	
	
	//alert("window.opener="+window.opener);
	if (window.opener != undefined) {
	       //for chrome
	       //for firefox
	      // alert("for chrome");
	       window.opener.returnValue = "window.opener.returnValue";
	}
	else {

		  // alert("not chrome");
	       window.returnValue = "window.returnValue";
	}


	//window.opener=null;
window.close();
	//window.parent.close();
	//window.parent.parent.close();

	
</script>
