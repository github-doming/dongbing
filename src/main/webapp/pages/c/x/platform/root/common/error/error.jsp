http://localhost:8080/a/platform/root/core/error/error.jsp

<p></p>


<%


if(false){
	
	//如果没有过滤器,可以跳转到http://localhost:8080/a/platform/root/core/error/exception.jsp 
	
	
	int i=1/0;
	
}


//if(true){
if(false){	
	//如果没有过滤器,可以跳转到http://localhost:8080/a/platform/root/core/error/exception.jsp 
	
	
	throw new Exception("Exception");
	
}


%>