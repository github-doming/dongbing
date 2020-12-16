$(function() {

	findSession();

	if($.cookie('setLoginList')) {

		getLoginList = JSON.parse($.cookie('setLoginList'));

		// console.log(getLoginList);

		setLoginListDiv(getLoginList);
	}
	if($.cookie('setLoginObj')) {

		var getLoginObj = JSON.parse($.cookie('setLoginObj'));

		$.each(getLoginList, function(index, item) {
			
			if(getLoginObj.name == item.name) {
				
				$('#account').val(getLoginObj.name);

				$('#password').val(getLoginObj.pwd);

				loginObj = {
					'name': getLoginObj.name,
					'pwd': getLoginObj.pwd,
				};

				$('#forget[type="checkbox"]').prop('checked', true);
				
				return false;
			}
		});

	}

});
var session = null;


$('#account').keyup(function () {

    if ($('#account').val().length <= 0) {

        $('.serNameList').slideDown();

        $('#forget[type="checkbox"]').prop('checked',false);

    } else {

        $('.serNameList').slideUp();

        $('#password').val('');
    }

});
$('#account').focus(function () {

    $('.serNameList').slideDown();

});
$('#account').blur(function () {

    $('.serNameList').slideUp();

});

function findSession() {
    //得到session
    jQuery.ajax({
        type: "post",
        url: context_path + 'all/app/token/session.do',
        async: false,
        //cache: true,
        dataType: 'json',
        contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
        success: function (dataObj) {
            if (dataObj.codeSys == "200") {
                session = dataObj.data;
                var text_img = context_path + "all/app/token/verify/img.do?json={session:'" + dataObj.data + "'}";
                $('.from .text .text_right >img').attr('src', encodeURI(text_img + "&id=" + Math.random()));
            } else {
                error(dataObj.messageSys);
            }
        },
        error: function (request, data) {
            // console.log('session', request, data);
        }
    });
}

//点击刷新图片验证码
$('.from .text .text_right .lost_').click(function () {
    $('#VerifyCode').val('');
    findSession();
});

// 错误提示
function error(text_) {
    $('.login .login_text .from .error').text(text_);
    $('.login .login_text .from .error').css({'display': 'block'});
    setTimeout(function () {
        $('.login .login_text .from .error').css({'display': 'none'});
    }, 1000);
}
//点击登录
$('.btn_login').click(function () {
	
	c_post();
});

$('.btn_login_').click(function () {
    var Account = $('#account').val();
    var Password = $('#password').val();
    var VerifyCode = $('#VerifyCode').val();
    if (Account == '') {
        error('请输入账号');
    } else if (Password == '') {
        error('请输入密码');
    } else if (VerifyCode == '') {
        error('请输入验证码');
    } else {
        if ($('#forget[type="checkbox"]').prop('checked') == true) {
            var login_text = {
                'account': $('#account').val(),
                'password': $('#password').val(),
            };
            localStorage.setItem('login_text', JSON.stringify(login_text));
        } else if ($('#forget[type="checkbox"]').prop('checked') == false) {
            localStorage.removeItem('login_text');
        }
        var json = {
            'code': VerifyCode,
            'name': Account,
            'password': Password,
            'session': session
        };
        jQuery.ajax({
            type: 'get',
            url: context_path + 'all/pc/token/login.do',
            data: {
                json: JSON.stringify(json)
            },
            async: false,
            // cache: true,
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
            success: function (data) {
                // console.log('登录', data);
                if (data.code == 'app200Login') {

                    $.cookie('setLoginObj', JSON.stringify(loginObj), {expires: 30, path: '/'});
                    
                    $.cookie('setLoginList', JSON.stringify(getLoginList), {expires: 30,path: '/'});

                    setTimeout(function () {
                        $('#account').val('');
                        $('#password').val('');
                        $('#VerifyCode').val('');
                    }, 1000);

                    localStorage.benyipc_token = data.data.value;
                    // 跳转页面
                    if (data.data.userType == "OWNER" || data.data.userType == "ROOMAGENT") {
                        // getURL('../index/welcome.html');
                        getURL('../index/index.html');
                        error('立即登录');
                    } else {
                        error('没有权限');
                    }
                } else if (data.code == 'app404Login') {
                    error('用户名不存在，请前往注册');
                    $('#account').val('');
                    $('#password').val('');
                    $('#VerifyCode').val('');
                } else if (data.code == 'app400Login') {
                    error('用户名或密码输入出错，请重新输入');
                    findSession();
                    $('#account').val('');
                    $('#password').val('');
                    $('#VerifyCode').val('');
                } else if (data.code == 'app404VerifyCode') {
                    error('验证码错误，请重新输入');
                    findSession();
                    $('#VerifyCode').val('');
                }
            },
            error: function (request) {
                console.log('登录失败')
            }
        })
    }
});

var loginObj = {};

var getLoginList = [];

function setLoginListDiv(myCookie) {

    $.each(myCookie, function (index, item) {

        var li_ = $('<li></li>').text(item.name);

        $('.serNameList').append(li_);

        li_.click(function () {

            $('#account').val($(this).text());

            $('#password').val(myCookie[index].pwd);

            $('.serNameList').slideUp();

            $('#forget[type="checkbox"]').prop('checked',true);

            loginObj = {
                'name': $('#account').val(),
                'pwd': $('#password').val(),
            };

        });
    });

}

$('.forget input').click(function() {

	if($('#forget[type="checkbox"]').prop('checked') == true) {

		if($('#account').val() != '' && $('#password').val() != '') {

			loginObj = {
				'name': $('#account').val(),
				'pwd': $('#password').val(),
			};

			//console.log(loginObj, 66);

			getLoginList.push(loginObj);

			console.log(getLoginList);
		}

	} else {
		//console.log(getLoginList, 55);

		$.each(getLoginList, function(index, item) {
			
			console.log(item.name);

			if(loginObj.name == item.name) {

				console.log(index);

				getLoginList.splice(index, 1);
				
				console.log(getLoginList);
				
				return false;
			}
		});

		//getLoginList.pop(loginObj);

	}

});
