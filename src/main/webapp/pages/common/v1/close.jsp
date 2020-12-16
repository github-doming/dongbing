<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>





<html zh-cn>
  <head>
    <title>测试IOS与JS之前的互调</title>
    <style type="text/css">
      * {
        font-size: 40px;
      }
    </style>
    <script type="text/javascript">
      
      var jsFunc = function() {
        alert('Objective-C call js to show alert');
        // 不会调用，因此OCModel并不存在
       // JSModel.showAlertMsg('js title', 'js message');
      }
    
    var jsParamFunc = function(argument) {
      document.getElementById('jsParamFuncSpan').innerHTML
      = argument['name'];
    }
    
      </script>
    
  </head>
  
  
  
  <body>
    
    <div style="margin-top: 100px">
      <h1>Another page</h1>
      <input type="button" value="返回" onclick="webView.showAlertViewControllerWithoutParam()">
    </div>
    
    <div>
      <input type="button" value="JSCallObjcMethodAndObjcCallJSMethodWithoutParam" onclick="webView.JSCallObjcMethodAndObjcCallJSMethodWithoutParam()">
        <input type="button" value="JSCallObjcMethodAndObjcCallJSMethodWithTwoParams" onclick="webView.JSCallObjcMethodAndObjcCallJSMethodWithDictionary({'name': 'testname', 'age': 10, 'height': 170})">
    </div>
    <a href="test.html">back to previous page</a>
    
    <div>
      <span id="jsParamFuncSpan" style="color: red; font-size: 50px;"></span>
    </div>
    
<!--   调用失败，因此此时还没有注入OCModel对象 -->

  </body>
</html>