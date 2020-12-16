<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title>接口测试页</title>
</head>
<body>
<div class="container-fluid">
    <h1>api接口说明</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>接口名称</th>
            <th>接口调用示例</th>
            <th>入参说明</th>
            <th>返参说明</th>
            <th>错误码说明</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="5">主页信息</td>
        </tr>
        <tr>
            <td>
                <p>1.盘口 盘口识别</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"  > http://IP:PORT/PROJECT/ibs/pc/handicap/discern</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> HANDICAP_URL_：盘口地址</p>
                <p> HANDICAP_CAPTCHA_：盘口验证码</p>
            </td>
            <td>
                <p> HANDICAP_ITEM_ID_：盘口详情主键</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> HANDICAP_NAME_：盘口名称</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 403Error:验证码长度不足</p>
                <p> 404HandicapItem:未能识别的盘口详情</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.盘口 盘口详情修正</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"   > http://IP:PORT/PROJECT/ibs/pc/handicap/itemEdit</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_ITEM_ID_：盘口详情主键</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> HANDICAP_URL_：盘口地址</p>
                <p> HANDICAP_CAPTCHA_：盘口验证码</p>
            </td>
            <td>
                <p> HANDICAP_ITEM_ID_：盘口详情主键</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> HANDICAP_NAME_：盘口名称</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 403Error:验证码长度不足</p>
                <p> 404HandicapItem:未能识别的盘口详情</p>
                <p> 404Handicap:未能识别的盘口</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.盘口 删除账户</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"   > http://IP:PORT/PROJECT/ibs/pc/handicap/accountDel</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员主键</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 404HandicapMember:盘口会员没有找到</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.盘口 会员登录</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"   > http://IP:PORT/PROJECT/ibs/pc/handicap/accountDel</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> LANDED_TIME_：定时登录</p>
                <p> HANDICAP_MEMBER_ID_：盘口会员主键</p>
                <p> MEMBER_ACCOUNT_：盘口账户</p>
                <p> MEMBER_PASSWORD_：盘口密码</p>
                <p> HANDICAP_ITEM_ID_：盘口详情主键</p>
            </td>
            <td>
                <p> loginCode：登录码</p>
                <p> landedLogin：定时登录，login：登录，valiLogin：验证登录</p>
                <p> eventId：事件主键</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 503Time:服务器当前无法处理请求，</p>
                <p> 请七点以后再试</p>
                <p> 404HandicapMember:盘口会员没有找到</p>
                <p> 403Handicap:盘口资源错误</p>
                <p> 403Exist:资源已存在</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.盘口 处理结果</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"   > http://IP:PORT/PROJECT/ibs/pc/handicap/eventResult</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> eventId：事件主键</p>
            </td>
            <td>
                <p> 登录结果</p>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 202Event:事件处理中</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>7.盘口 会员登出</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"   > http://IP:PORT/PROJECT/ibs/pc/handicap/logout</a>
                </p>
            </td>
            <td>
                <p> token：登录令牌</p>
                <p> HANDICAP_MEMBER_ID_：会员主键</p>
            </td>
            <td>
            </td>
            <td>
                <p> 401Data:没有找到数据</p>
                <p> 503Time:服务器当前无法处理请求，请七点以后再试</p>
                <p> 404Login:客户未登录</p>
                <p> 404Data:数据没有找到</p>
            </td>
        </tr>

        <tr>
            <td colspan="5">盘口接口API：/handicap.jsp</td>
        </tr>
        </tbody>
    </table>
</div>
</body>
<script type="text/javascript">
    function findUrl(url) {
        console.log(url);
        url = encodeURI(url);
        window.open(url);
        return url;
    }
</script>
</html>
