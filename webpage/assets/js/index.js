var mainIframeHeight = 0;

$(function(){
	
	var setHeight = function(){
		var bodyHeight = $("body").height();
		var wrapperHeight = bodyHeight - 50;
		$(".content-wrapper").height(wrapperHeight);
	};
	setHeight();
	
	var loanMenu = function(){
		var functionUL = $('.sidebar-menu');
		var func = null, funcII = null, index = 0;
		var functionList = fangjs.getSessionStorage("functionList");
		for(var i = 0; i < functionList.length; i++){
			func = functionList[i];
			if(func.type == 1){
				var li = $('<li>');
				li.appendTo(functionUL);
				li.addClass("treeview" + (i == 0? "active" : ""));
				var str = ' <a href="#"><i class="' + (func.functionIcon?func.functionIcon:'fa fa-files-o') 
				+ '"></i><span>' + func.functionName 
				+ '</span><span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span></a>';
				li.append(str);
	            var twoList = [];
	            for(i++; i < functionList.length; i++){
	            	funcII = functionList[i];
					if(funcII.type == 2 && funcII.lft > func.lft && funcII.rgt < func.rgt) twoList.push(funcII);
					if (funcII.rgt > func.rgt) {
						i--;
						break;
					}
	            }
	            if(twoList.length ==0){
	            	continue;
	            }
	            var ul = $('<ul class="treeview-menu"></ul>');
				ul.appendTo(li);
				
	            for(var j = 0; j < twoList.length; j++){
	            	two = twoList[j];            	
	             	var liII = $('<li><a href="javascript:;" click="addPage" params="id=' + two.id + ',title=' + two.functionName + ',url=' + two.functionUrl + '" ><i class="' +  (func.functionIcon?func.functionIcon:'fa fa-circle-o') + '"></i> ' + two.functionName + '</a></li>'); 
	             	liII.appendTo(ul);
	            }
	            
			}
		}
	};
	//加载左侧菜单
	loanMenu();
	
	
	mainIframeHeight = $("#mainIframe").height();
	
	//加载用户信息
	var showUserInfo = function(){
		$("#userInfo").html(user.name);
	}
	showUserInfo();
	
});



//右侧增加tab表单、并打开二级页面方法
funcList.addPage = function(params){
	//去除ul-li的所有样式
	var iframesContent = $$('iframesContent');
	iframesContent.getChildNodes('iframe').foreach(function(){
		this.css({display : 'none'});
	});
	var fangTab = $$('fangTab');
	var lis = fangTab.getChildNodes('li'), li = null;;
	lis.foreach(function(){
		this.removeClass('pt-hover');
	});
	//如若新点击的li元素存在，则显示相应的ifram页面，方法结束
	for(var i = 0; i < lis.length; i++){
		li = lis[i];
		if(li.getAttribute('iframeId') == params.id){
			li.addClass('pt-hover');
			$$('fang-iframe-' + params.id).css({display : 'block'});
			return;
		}
	}
	li = $c('li');
	li.className = 'pt-hover';
	li.setAtt({click : 'showThis', iframeId : params.id});
	li.innerHTML = '<span>' + params.title + '</span><a href="javascript:" click="closePage">×</a>';
	fangTab.appendChild(li);
	var absoluteIframe = $c('iframe');
	absoluteIframe = $$('mainIframe').cloneNode(true);
	absoluteIframe.height = Math.ceil((mainIframeHeight - 30)/mainIframeHeight*100) + "%";
	absoluteIframe.id = 'fang-iframe-' + params.id;
	absoluteIframe.css({display : 'block'});
	absoluteIframe.src = params.url;
	iframesContent.appendChild(absoluteIframe);
	//将tab框控制在10个,多出部分会自动触发closePage()方法
	if(lis.length>9){
		removePage();
	}
};

//单击ul-li显示ifram页面
funcList.showThis = function(params){
	var iframe = $$('fang-iframe-' + this.getAttribute('iframeId'));
	if(!iframe) return;
	var iframes = $$('iframesContent').getChildNodes('iframe');
	iframes.foreach(function(){
		this.css({display : 'none'});
	});
	$$('fangTab').getChildNodes('li').foreach(function(){
		this.removeClass('pt-hover');
	});
	this.addClass('pt-hover');
	var getShowIframe = function(ele){
		var id = ele.id;
		if(!id) return ele;
		for(var i = 0, len = iframes.length; i < len; i++) if(iframes[i].getAttribute('parentid') == id) return getShowIframe(iframes[i]);
		return ele;
	};
	getShowIframe(iframe).css({display : 'block'});
};


//单击ul-li关闭ifram页面
funcList.closePage = function(params){
	var li = this.getParentByTag('li');
	var iframe = $$('fang-iframe-' + li.getAttribute('iframeId'));
	var removeChildIframe = function(id){
		var iframes = $$('iframesContent').getChildNodes('iframe'), ifr = null;
		for(var i = 0, len = iframes.length; i < len; i++){
			ifr = iframes[i];
			if(id == ifr.getAttribute('parentId')){
				ifr.parentNode.removeChild(ifr);
				removeChildIframe(ifr.id);
				break;
			}
		}
	};
	if(!li.hasClass('pt-hover')){
		li.parentNode.removeChild(li);
		iframe.parentNode.removeChild(iframe);
		removeChildIframe(iframe.id);
		return;
	}
	var nextLi = li.next('li') || li.previous('li');
	li.parentNode.removeChild(li);
	iframe.parentNode.removeChild(iframe);
	removeChildIframe(iframe.id);
	if(!nextLi){
		$$('mainIframe').css({display : 'block'});
		return;
	}
	var nextIframe = $$('fang-iframe-' + nextLi.getAttribute('iframeId'));
	nextLi.addClass('pt-hover');
	nextIframe.css({display : 'block'});
};


funcList.closeAll = function(){
	if($('#fangTab li a').length){
		doElementEvent($('#fangTab li a')[0], 'click')
		funcList.closeAll();
	}
};


//注销
funcList.logout = function(){
	fangjs.delCookie('userId');
	fangjs.deleteLocalStorage('user');
	fangjs.execjava('sys/user/userLogout', null, function(result){
		if(!result || result.code != 1){
			return;
		}
		window.location.href = 'login.html';
	});
};
