<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>操作成功</title>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }
        body{
            background-color: #f8f8f8;
        }
        .tc{
            text-align: center;   
        }
        h1{
            font-size: 32px;
            color: #666;
        }
        .success{
            position: relative;
            width: 120px;
            margin: 10% auto 2% auto;
            height: 120px;
            border-radius: 100%;
            border: 2px solid #58d58d;
        }
        .success:after{
            content:'';
            position: absolute;
            width: 35px;
            height: 64px;
            top: 24px;
            left: 18px;
            box-sizing: border-box;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            border-right: 4px solid #58d58d;
            border-bottom: 4px solid #58d58d;
            -webkit-transform-origin: 100% 100%;
            -moz-transform-origin: 100% 100%;
            -ms-transform-origin: 100% 100%;
            transform-origin: 100% 100%;
            -webkit-transform: rotateZ(42deg);
            -moz-transform: rotateZ(42deg);
            -ms-transform: rotateZ(42deg);
            transform: rotateZ(42deg);
        }
        .fade{
            animation: fade-animation 1s;
            -webkit-animation: fade-animation 1s;
            -moz-animation: fade-animation 1s;
            -ms-animation: fade-animation 1s;
        }
        @keyframes fade-animation{
            0%{
                opacity: 0;
            };
            100%{
                opacity: 1;
            }
        }
        @-webkit-keyframes fade-animation{
            0%{
                opacity: 0;
            };
            100%{
                opacity: 1;
            }
        }
        @-moz-keyframes fade-animation{
            0%{
                opacity: 0;
            };
            100%{
                opacity: 1;
            }
        }
        @-ms-keyframes fade-animation{
            0%{
                opacity: 0;
            };
            100%{
                opacity: 1;
            }
        }
        #back-btn{
            width: 80px;
            height: 36px;
            border-radius: 4px;
            margin-top: 5%;
            outline: none;
            border: 0;
            color: #449fe6;
            border: 1px solid #449fe6;
            background-color: transparent;
        }
        @media screen and (max-width: 768px){
            .success{
                margin: 22% auto 5% auto;
            }
            h1{
                font-size: 18px;
            }
            #back-btn{
                margin-top: 10%;
            }
        }
    </style>
</head>
<body>

    <div class="success fade"></div>
    <div class="tc fade">
        <h1>操作成功!</h1>
        <input type="button" value="返回" onclick="webView.showAlertViewControllerWithoutParam()" id="back-btn">
    </div>
        
    <!-- <div>
        <input type="button" value="JSCallObjcMethodAndObjcCallJSMethodWithoutParam" onclick="webView.JSCallObjcMethodAndObjcCallJSMethodWithoutParam()">
        <input type="button" value="JSCallObjcMethodAndObjcCallJSMethodWithTwoParams" onclick="webView.JSCallObjcMethodAndObjcCallJSMethodWithDictionary({'name': 'testname', 'age': 10, 'height': 170})">
    </div> -->
    <div>
        <span id="jsParamFuncSpan" style="color: red; font-size: 50px;"></span>
    </div>
    <!--   调用失败，因此此时还没有注入OCModel对象 -->
</body>
<script type="text/javascript">
    
      var jsFunc = function() {
        alert('Objective-C call js to show alert');
        // 不会调用，因此OCModel并不存在
      // JSModel.showAlertMsg('js title', 'js message');
      }
    
      var jsParamFunc = function(argument) {
        document.getElementById('jsParamFuncSpan').innerHTML = argument['name'];
      }
  
  </script>
  
</html>