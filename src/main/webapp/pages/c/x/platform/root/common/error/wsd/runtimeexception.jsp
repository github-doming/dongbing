<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>操作失败</title>
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
        .error{
            position: relative;
            width: 120px;
            margin: 10% auto 2% auto;
            height: 120px;
            border-radius: 100%;
            border: 2px solid #e64d3e;
        }
        .error:after{
            content:'×';
            position: absolute;
            top: -12px;
            left: 24px;
            font-size: 100px;
            color: #e64d3e;
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
            .error{
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

    <div class="error fade"></div>
    <div class="tc fade">
        <h1>操作失败!</h1>
        <input type="button" value="返回" onclick="locationHref()" id="back-btn">
    </div>
</body>
<script>
    function locationHref(){
          window.location.href = 'http://localhost:8080/cds/';
      }
</script>
</html>