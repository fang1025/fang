
jQuery(document).ready(function() {

    //$.backstretch("assets/img/background/1.jpg");

    $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });
    
    /**
	 * 分发所有的键盘按下事件
	 */
	document.body.onkeydown = function() {
		doElementEvent(getElement(), 'keyDown');
	};
    
    var loginName = $$('loginName');
    loginName.focus();
    var cookieName = fangjs.getCookie('loginId');
    if(cookieName&&cookieName!='undefined') loginName.value = cookieName;
    
    
    
    /**
     * 登录事件
     */
    $("#loginBtn").on("click", function(){
    	if(!loginCheck()) return;
    	var param = {};
    	param.loginName = $$('loginName').value;
    	param.password = $$('password').value;
    	fangjs.execjava('sys/user/userLogin', param, function(data){
    		var pasconot = $('#pasconot');
    		if(!data || data.code != '1'){
    			var msg = data ? data.message : '登陆出现错误！';
    			pasconot.html(msg);
    		}else if (data.code == '1') {
    			fangjs.setCookie('loginId', param.loginName, 7);
    			var user = data.user;
    			fangjs.setCookie('userId', user.id);
    			fangjs.setLocalStorage('user', user);
    			if(data.functionList){
    				fangjs.setLocalStorage("functionList", data.functionList)
    			}
    			var from = fangjs.getParamFromURl("from");
    			if(from && from != "signup.html"){
    				window.history.back(-1);
    			}else{
    				window.location.href = "index.html"
    			}
    			
    		}else pasconot.html('未知错误，请联系网站管理员！');
    	});
    });
    
    var loginCheck = function(){
    	var username = $('#loginName').val();
        var password = $('#password').val();
        var pasconot = $('#pasconot');
        if (!username) {
    		pasconot.html('登录名不能为空');
    		return false;
        }else if (!password) {
            pasconot.html('密码不能为空');
            return false;
        }
    	pasconot.html('&nbsp;');
    	return true;
    };
    
});





var one = {
	"particles" : {
		"number" : {
			"value" : 80,
			"density" : {
				"enable" : true,
				"value_area" : 800
			}
		},
		"color" : {
			"value" : "#ffffff"
		},
		"shape" : {
			"type" : "circle",
			"stroke" : {
				"width" : 0,
				"color" : "#000000"
			},
			"polygon" : {
				"nb_sides" : 5
			},
			"image" : {
				"src" : "img/github.svg",
				"width" : 100,
				"height" : 100
			}
		},
		"opacity" : {
			"value" : 0.5,
			"random" : false,
			"anim" : {
				"enable" : false,
				"speed" : 1,
				"opacity_min" : 0.1,
				"sync" : false
			}
		},
		"size" : {
			"value" : 5,
			"random" : true,
			"anim" : {
				"enable" : false,
				"speed" : 40,
				"size_min" : 0.1,
				"sync" : false
			}
		},
		"line_linked" : {
			"enable" : true,
			"distance" : 150,
			"color" : "#ffffff",
			"opacity" : 0.4,
			"width" : 1
		},
		"move" : {
			"enable" : true,
			"speed" : 6,
			"direction" : "none",
			"random" : false,
			"straight" : false,
			"out_mode" : "out",
			"attract" : {
				"enable" : false,
				"rotateX" : 600,
				"rotateY" : 1200
			}
		}
	},
	"interactivity" : {
		"detect_on" : "canvas",
		"events" : {
			"onhover" : {
				"enable" : true,
				"mode" : "repulse"
			},
			"onclick" : {
				"enable" : true,
				"mode" : "push"
			},
			"resize" : true
		},
		"modes" : {
			"grab" : {
				"distance" : 400,
				"line_linked" : {
					"opacity" : 1
				}
			},
			"bubble" : {
				"distance" : 400,
				"size" : 40,
				"duration" : 2,
				"opacity" : 8,
				"speed" : 3
			},
			"repulse" : {
				"distance" : 200
			},
			"push" : {
				"particles_nb" : 4
			},
			"remove" : {
				"particles_nb" : 2
			}
		}
	},
	"retina_detect" : true,
	"config_demo" : {
		"hide_card" : false,
		"background_color" : "#b61924",
		"background_image" : "",
		"background_position" : "50% 50%",
		"background_repeat" : "no-repeat",
		"background_size" : "cover"
	}
};

particlesJS('particles-js', one);
