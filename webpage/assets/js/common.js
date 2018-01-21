// JavaScript Document
var ROOT_PATH = '/';
var AJAX_ROOT = '/fang/';
var APP_ID = 'fang';
var CUSTOM_APP = 'custom',CACHE_APP='fang',COMPONENT_APP='fang';
var ctx = getRootPath();
var port = "";

//已启用的查询条件
var selectParams;

var filewebsite = "http://file.fang.com/";

//当前页码
var page = 1;
//每页显示数量
var pageSize = 10;
//数据总数量
var dataCount = 0;


//搜索条件
var searchParams = null;



window.onresize = function(){
	autoSize();
};
 

window.onload = function(){
    var body = document.body;
    //事件绑定
    body.onclick = function(){
        doElementEvent(getElement(), 'click');
    };
    body.onchange = function(){
        doElementEvent(getElement(), 'change');
    };
    body.onkeydown = function(){
    	keyEvent();
        doElementEvent(getElement(), 'keydown');
    };    
    body.ondblclick = function() {
        doElementEvent(getElement(), 'dblclick');
    };
    
	autoSize();
	//执行页面自定义onload方法
	if(typeof funcList.onload == 'function') funcList.onload.call();
	
	//文本框添加验证事件
	addVerify(body);

	//如果是弹出页面，
	if(window.name.indexOf("layui") != -1){
		$(".box").css("border-top", " 0px")
		$(".box-body").css("padding-bottom","50px");
	}
	
};

/**
 * 获取项目名称
 */
function getRootPath() {
    var pathName = window.document.location.pathname;
    return pathName.substring(0,pathName.substr(1).indexOf('/') + 1);
}

var getEvent = function(){
	var eve = window.event || arguments[0] || arguments.callee.caller.arguments[0] || getEvent.caller.arguments[0];
    if(eve) return eve;
    for(var fun = getEvent.caller; fun; fun = fun.caller){
    	eve = fun.arguments[0];
    	if(eve && (eve.constructor == Event || (typeof eve == 'object' && eve.preventDefault && eve.stopPropagation))) return eve;
    }
};

/**
 * 获取当前事件的节点 
 */
var getElement = function(){
    var evt = getEvent();
    return evt.target ? evt.target : evt.srcElement;    
};

/**
 * 执行指定元素上的指定事件.
 * 第一个参数是指定元素.
 * 第二个参数是指定事件参数名. 
 */
var doElementEvent = function(node, att){
    if(!node || node == document || !node.tagName || node.tagName.toLowerCase() == 'html') return;
    var fn = node.getAttribute(att);
    if(fn && (fn = fn.trim()) != ''){
	    var fns = fn.split(',');
	    for(var i = 0, len = fns.length; i < len; i++){
	        var fnName = fns[i].trim();
	        var fun = funcList[fnName];
	        if(typeof fun == 'function') fun.call(node, getParams(node));
	    }
    }
    doElementEvent(node.parentNode, att);
};

/**
 * 获取参数列表 
 */
var getParams = function(node){
    var paramStr = node.getAttribute('params');
    if(!paramStr || !(paramStr = paramStr.trim())) return null;
    var paramList = paramStr.split(','), params = {}, index, key, value;
    for (var i = 0, len = paramList.length; i  < len; i ++) {
    	paramStr = paramList[i];
    	index = paramStr.indexOf('=');
    	params[paramStr.substring(0, index)] = paramStr.substring(index + 1);
    };
    return params;
};

/**
 * 根据ID获取节点 
 */
var $$ = function(id){
	return 'string' == typeof id ? document.getElementById(id) : id;
};

/**
 * 根据tagName获取节点集合 
 */
var $s = function(tagName){
	return 'string' == typeof tagName ? document.getElementsByTagName(tagName) : tagName;
};

/**
 * 根据tagName创造节点
 */
var $c = function(tagName){
	return 'string' == typeof tagName ? document.createElement(tagName) : tagName;
};


/**
 * 当页面上需要删除某个节点，而该节点中包含验证框的时候调用此方法
 * @param {Object} ele 节点本身或者节点ID
 */
var delVerify = function(ele){
	ele = $$(ele);
	var del = function(element){
		var verifynum = element.getAttribute('verifynum');
		if(!verifynum) return;
		delete verifyArray[verifynum];
	};
	var tagName = ele.tagName.toLowerCase();
	if(tagName == 'input' || tagName == 'select'){
		del(ele);
		return;		
	}
	var inputs = ele.$s('input');if(inputs && inputs.length) for(var i = 0, len = inputs.length; i < len; i++) del(inputs[i]);
	var selects = ele.$s('select');
	if(selects && selects.length) for(var i = 0, len = selects.length; i < len; i++) del(selects[i]);
};

/**
 * 页面加载后又用javascript动态添加一些需要验证的文本框,或者对某些需要验证的文本框有赋值操作,后需要对这些文本框添加或者再次验证.<br />
 * 参数可以为需要添加或者再次验证的文本框节点,或者该节点ID,或者其上级节点,或者上级节点ID.<br />
 * 注:如果有批量文本框需要加或者再次验证,只需要传入包含了这些节点的任一上级节点或者该上级节点ID即可.
 */
var addVerify = function(ele){
	ele = $$(ele);
	var tagName = ele.tagName.toLowerCase();
	if(tagName == 'input' || tagName == 'select' || tagName == 'textarea'){
		verify(ele);
		return;
	}
	var inputs = ele.$s('input'), input, type, verifyType;
	if(inputs && inputs.length){
		for(var i = 0, len = inputs.length; i < len; i++){
			input = inputs[i];
			type = input.getAttribute('type');
			type = type ? type.toLowerCase() : 'text';
			verifyType = input.getAttribute('verifyType');
			if((type != 'text' && type != 'password') || !verifyType || !(verifyType = verifyType.trim())) continue;
			verify(input);
		}
	}
	var selects = ele.$s('select'), select;
	if(!selects || !selects.length) return;
	for(var i = 0, len = selects.length; i < len; i++) {
		select = selects[i];
		verifyType = select.getAttribute('verifyType');
		if(!verifyType || !verifyType.trim()) continue;
		verify(select);
	}
	
	var textareas = ele.$s('textarea'), textarea;
	if(!textareas && !textareas.length) return;
	for(var i = 0, len = textareas.length; i < len; i++){
		textarea = textareas[i];
		verifyType = textarea.getAttribute('verifyType');
		if(!verifyType || !verifyType.trim()) continue;
		verify(textarea);
	}
	
};

/**
 * 添加必须填写标志 (非通用页面重写此方法即可)
 */
var addNoNullMark = function(ele){
	var parentNode = ele.parentNode;
	if(parentNode.tagName.toLowerCase() == 'div'){
		if($(parentNode).html().indexOf("color:red") == -1) 
			$(ele).before('<b style="color:red;">*</b>');
		return;
	}
	var parentNode = ele.parentNode.parentNode;
	if(parentNode.tagName.toLowerCase() == 'td'){
		var td = parentNode.previous('td');
		if(td.innerHTML.indexOf('red') == -1) td.innerHTML += '<b style="color:red;">*</b>';
		return;
	}


};

/**
 * 给文本框增加验证 
 */
var verify = function(ele){
	if(!ele) return;
	var verifyType = ele.getAttribute('verifyType').trim();
    //设置过verifyType的表示都需要
    if(!verifyType.startWith("nullor") && verifyType != 'no'){
        addNoNullMark(ele);
    }
	var num = ele.getAttribute('verifyNum');
	if(!num){
		num = ele.id+'|'+fangjs.rndNum(6);
		ele.setAtt({verifyNum : num});
		if( ele.getAttribute('click') != 'showDate'){
			ele.onblur = validation;
		}
	}
	if((!ele.value || ele.value.trim() == '') && !verifyType.startWith('nullor')) verifyArray[num] = false;
	else validation(ele);
};

/**
 * 页面中需要验证的文本框验证结果集
 */
var verifyArray = {};

/**
 * 判断是否全部验证通过 
 */
var isCheck = function(){
	var getElementByVerifynum = function(verifynum){
		var inputs = $s('input'), input;
		for(var i = 0, len = inputs.length; i < len; i++){
			input = inputs[i];
			if(input.getAttribute('verifynum') == verifynum) return input;
		}
	};
	for(var num in verifyArray) if(!verifyArray[num]) {
		var id = num.split('|')[0];
		var ele = id ? $$(id) : getElementByVerifynum(num);
		if(!ele) continue;
		if(!validation(ele)) return false;
	}
	return true;
};

/**
 * 添加文本框验证的方法
 */
var validation = function(ele){
	var eve = typeof ele == 'undefined' ? getElement() : ele.target || ele.srcElement || ele;
	var verifyType = eve.getAttribute('verifyType').trim();
	var verifyUrl = eve.getAttribute('verifyUrl');
	var value = eve.value ? eve.value.trim() : '', isCorrect = true, remind = eve.getAttribute('remind'), msg = '';
	var validationLen = function(){
		var minlen = eve.getAttribute('minlen');
		minlen = minlen ? minlen.trim() : '';
		var maxlen = eve.getAttribute('maxlen');
		maxlen = maxlen ? maxlen.trim() : '';
		if(!minlen && !maxlen) return true;
		var temp = '';
		if((minlen && value.length < minlen) || (maxlen && value.length > maxlen)) 
			temp = (minlen ? '最小长度不能小于' + minlen + '！' : '') + (maxlen ? '最大长度不能大于' + maxlen + '！' : '');
		return temp ? temp : true;
	};
	var numberLimit = function(){
		var minvalue = eve.getAttribute('minvalue');
		minvalue = minvalue ? minvalue.trim() - 0 : null;
		var maxvalue = eve.getAttribute('maxvalue');
		maxvalue = maxvalue ? maxvalue.trim() - 0 : null;
		if(!minvalue && minvalue !== 0 && !maxvalue && maxvalue !== 0) return true;
		var temp = '';
		value = value - 0;
		if((minvalue !== null && value < minvalue) || (maxvalue !== null && value > maxvalue)) 
			temp = (minvalue !== null ? '数字最小不能小于' + minvalue + '！' : '') + (maxvalue !== null ? '数字最大不能大于' + maxvalue + '！' : '');
		return temp ? temp : true;
	};
	var nullorStr = '';
	if(verifyType.startWith('nullor') && value){
		nullorStr = '为空或';
		verifyType = verifyType.substring('nullor'.length);
	} 
	switch(verifyType){
		case 'no' :
			isCorrect = validationLen();
			if(isCorrect !== true){
				msg = isCorrect;
				isCorrect = false;
			}
			break;
		case 'noNull' :
			isCorrect = value;
			if(!isCorrect) msg = '不能为空！';
			var lenMsg = validationLen();
			if(lenMsg !== true){
				msg = lenMsg;
				isCorrect = false;
			}
			break;
		case 'number' :
			isCorrect = value && !isNaN(value);
			if(!isCorrect) msg = nullorStr + '必须为数字！';
			var lenMsg = numberLimit();
			if(lenMsg !== true){
				msg = lenMsg;
				isCorrect = false;
			}
			break;
		case 'positive' :
			isCorrect = /^(0|([1-9]\d*))(\.\d+)?$/.test(value);//正数
			if(isCorrect) isCorrect = Number(value) > 0;
			if(!isCorrect) msg = nullorStr + '必须为大于0的数字！';
			var lenMsg = numberLimit();
			if(lenMsg !== true){
				msg = lenMsg;
				isCorrect = false;
			}
			break;
		case 'nature' :
			isCorrect = /^(0|([1-9]\d*))(\.\d+)?$/.test(value);//自然数（包含0的正数）
			if(!isCorrect) msg = nullorStr + '必须为自然数！';
			var lenMsg = numberLimit();
			if(lenMsg !== true){
				msg = lenMsg;
				isCorrect = false;
			}
			break;
		case 'allInteger' :
			isCorrect = /^-?[0-9]\d*$/.test(value); //所有整数
			if(!isCorrect) msg = nullorStr + '必须为整数！';
			var lenMsg = numberLimit();
			if(lenMsg !== true){
				msg = lenMsg;
				isCorrect = false;
			}
			break;
		case 'integer' :
			isCorrect = /^[0-9]\d*$/.test(value); //正整数+0
			if(!isCorrect) msg = nullorStr + '必须为整数！';
			var lenMsg = numberLimit();
			if(lenMsg !== true){
				msg = lenMsg;
				isCorrect = false;
			}
			break;
		case 'positiveInt' :
			isCorrect = /^\d+$/.test(value); //正整数（不包含0）
			if(isCorrect) isCorrect = Number(value) > 0;
			if(!isCorrect) msg = nullorStr + '必须为正整数！';
			var lenMsg = numberLimit();
			if(lenMsg !== true){
				msg = lenMsg;
				isCorrect = false;
			}
			break;
		case 'mail' :
			isCorrect = /^[^\.@]+@[^\.@]+\.[a-z]+$/.test(value); //邮箱
			msg = nullorStr + '必须填写正确的邮箱格式！';
			break;
		case 'mobile' :
			isCorrect = /^1\d{10}$/.test(value); //手机
			msg = nullorStr + '必须填写正确的手机号码！';
			break;
		case 'phone' :
			isCorrect = /^(0\d{2,3}-)?\d{7,8}(-\d+)?$|^0\d{10,11}(-\d+)?$|^\d{7,8}(-\d+)?$/.test(value); //固话
			msg = nullorStr + '必须填写正确电话号码！';
			break;
		case 'phoneORmobile' :
			isCorrect = /^(0\d{2,3}-)?\d{7,8}(-\d+)?$|^0\d{10,11}(-\d+)?$|^\d{7,8}(-\d+)?$|^1\d{10}$/.test(value); //手机及固话
			msg = nullorStr + '必须填写正确的手机/电话号码！';
			break;
		case 'idCard' :
			function cidInfo(sId){
				if(sId.length != 18 && !(sId.endWith('x') && sId.length == 15)) return false;
				var aCity = {11:'北京',12:'天津',13:'河北',14:'山西',15:'内蒙古',21:'辽宁',22:'吉林',23:'黑龙江 ',31:'上海',32:'江苏',33:'浙江',34:'安徽',35:'福建',36:'江西',37:'山东',41:'河南',42:'湖北 ',43:'湖南',44:'广东',45:'广西',46:'海南',50:'重庆',51:'四川',52:'贵州',53:'云南',54:'西藏 ',61:'陕西',62:'甘肃',63:'青海',64:'宁夏',65:'新疆',71:'台湾',81:'香港',82:'澳门',91:'国外 '};
				var iSum = 0;
				//var initial = new RegExp('/^(/d{18,18}|/d{15,15}|/d{17,17}x)$/');
				//if(!initial.test(sId))return false;
				sId = sId.replace(/x$/i,'a');
				if(aCity[parseInt(sId.substr(0, 2))] == null) return false;//'Error:非法地区';
				sBirthday = sId.substr(6, 4) + '-' + Number(sId.substr(10, 2)) + '-' + Number(sId.substr(12, 2));
				var d = new Date(sBirthday.replace(/-/g, '/'));
				if(sBirthday != (d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()))return false;//'Error:非法生日';
				for(var i = 17; i >= 0; i --) iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
				if(iSum % 11 != 1) return false;//'Error:非法证号';
				return true;//aCity[parseInt(sId.substr(0, 2))] + ',' + sBirthday + ',' + (sId.substr(16, 1) % 2 ? '男' : '女');
			}
			isCorrect = cidInfo(value);
			msg = nullorStr + '必须填写正确的身份证号码！';
			break;
		case 'numberAndEnglish' : //英文数字组合
			//     /^[\u4e00-\u9fa5]/  判断是否是中文字符
			if(!value){
				isCorrect = false;
				msg = nullorStr + '内容必须为英文数字组合！';
				break;
			}
			for(var i = 0, len = value.length; i < len; i++){
				if(/^[a-zA-Z]/.test(value[i]) || !isNaN(value[i])) continue;
				isCorrect = false;
				msg = nullorStr + '内容必须为英文数字组合！';
				break;
			}
			var lenMsg = validationLen();
			if(lenMsg !== true){
				msg += lenMsg;
				isCorrect = false;
			}
			break;
		default:
			isCorrect = true;
	}
	if(verifyUrl&&fangjs.getParamFromURl("act")=='add'&& value){
		var p = getParams(eve)||{};
		p[eve.getAttribute('id').trim()] = value
		fangjs.execjava(verifyUrl, p, function(data){
			if(data.code != "1"){
				var verifyNum = eve.getAttribute('verifyNum');
				verifyArray[verifyNum] = false;
				isCorrect = false;
				var t = $(eve).offset().top - document.body.scrollTop;
				layer.tips(data.message?data.message : "与数据库数据重复！", eve, {
					  tips: [t > 50?1:3,'#3595CC'],
					  time: 4000
					});
				return isCorrect;
			}
		})
	}	
	var verifyNum = eve.getAttribute('verifyNum');
	verifyArray[verifyNum] = false;
	if(isCorrect) {
		verifyArray[verifyNum] = true;
		layer.closeAll();
	} else{
		msg = remind || msg;
		var t = $(eve).offset().top - document.body.scrollTop;
		layer.tips(msg, eve, {
			  tips: [t > 50?1:3,'#3595CC'],
			  time: 4000
			});
	}
	return isCorrect;
};

var addCheckBox = function(ele){
	ele = $$(ele);
	if(ele.tagName.toLowerCase() == 'i') ele.setAtt({'click' : 'checkBox'});
	else{
		var is = ele.getElementsByTagName('i');
		if(!is || !is.length) return;
		for(var i = 0, len = is.length; i < len; i++) if(is[i].className.indexOf('icon-check') > -1) is[i].setAtt({'click' : 'checkBox'});
	}
};

/**
 * 方法列表. 
 */
var funcList = {
	/**
	* 设置页码数方法
	*/
	setPageNumber : function(){
		var ul = $$('pageNumber');
		if(!ul) return;
		ul.clean();						
		dataCount = dataCount || 0;
		var pageinfo = $(".pagination-info");
		if(pageinfo) pageinfo.html("显示第 " + Math.min(((page-1)*pageSize +1),dataCount) + " 至 " + ((page * pageSize) < dataCount ? (page * pageSize) : dataCount) + "条记录，共 " + dataCount + " 条记录。");
		var maxPage = Math.max(Math.ceil(dataCount / pageSize),1);
		var len = maxPage >  5 ? 5 : maxPage;
		var start = page - 2 > 1 ? page - 2 : 1;
		start = maxPage - 5 > start ? start : (maxPage - 5 > 1 ? maxPage - 5 : 1);
		
		//首页
		li = $c('li');
		var a = $c('a');
		li.appendChild(a);
		a.setAtt({
			params : 'cmd=first',
			click : 'toPage'
		});
		a.innerHTML = "首页";
		if(maxPage == 1 || page == 1){
			li.addClass('disabled');
		}
		ul.appendChild(li);
		//上一页
		var li = $c('li');
		var a = $c('a');
		li.appendChild(a);
		a.setAtt({
			params : 'cmd=previous',
			click : 'toPage'
		});
		a.innerHTML = '上一页';
		if(page == 1){
			li.addClass('disabled');
			a.removeAttribute("click");
		}
		ul.appendChild(li);
		
		//是否从第一页开始,不是则显示点点点
		if(start > 1){
			li = $c('li');
			var a = $c('a');
			li.appendChild(a);
			a.innerHTML = '...';
			ul.appendChild(li);
		}
		//页码
		for(var i = start; i <= len + start -1; i++){
			li = $c('li');
			var a = $c('a');
			li.appendChild(a);
			a.setAtt({
				click : 'toPage',
				params : 'cmd=pageNum,pageNum=' + i
			});
			if(i == page) li.addClass('active');
			a.innerHTML = i;
			ul.appendChild(li);
		}
		//是否是最后一个页码,不是则显示...
		if(maxPage - start > 5){
			li = $c('li');
			var a = $c('a');
			li.appendChild(a);
			a.innerHTML = '...';
			ul.appendChild(li);
		}
		//下一页
		li = $c('li');
		var a = $c('a');
		li.appendChild(a);
		a.setAtt({
				params : 'cmd=next',
				click : 'toPage'
			});
		a.innerHTML = '下一页';
		if(page == maxPage){
			li.addClass('disabled');
		}
		ul.appendChild(li);
		//尾页
		li = $c('li');
		var a = $c('a');
		li.appendChild(a);
		a.setAtt({
			params : 'cmd=last',
			click : 'toPage'
		});
		a.innerHTML = "尾页";
		if(maxPage == 1 || page == maxPage){
			li.addClass('disabled');
		}
		ul.appendChild(li);
		
	},
	/**
	 * 分页相关方法.
	 * 使用人员需要在js中提供funcList.loadData方法;
	 * 翻页时会调用此方法查询数据,在该方法中可使用page\pageSize\dataCount等全局变量;
	 * 方法中需要设置数据总数量,即页面全局变量dataCount的值.
	 */
	toPage : function(params){
		if(!params || !params.cmd) return;
		var lastPage = Math.ceil(dataCount / pageSize);
		switch(params.cmd){
			case 'first' :
				if(page == 1) return;
				page = 1;
				break;
			case 'last' :
				if(page == lastPage) return;
				page = lastPage;
				break;
			case 'previous' :
				if(page == 1) return;
				page--;
				break;
			case 'next' :
				if(page == lastPage) return;
				page++;
				break;
			case 'set' :
				var pageNum = this.previous('input').value.trim();
				if(!pageNum || pageNum == page) return;
				if(!/^\d+$/.test(pageNum) || pageNum == '0' || pageNum > lastPage){
					alert('跳转页码必须为小于等于最大页码的正整数！');
					return;
				}
				page = pageNum;
				break;
			case 'setSize' :
				pageSize = this.value;
				page = 1;
				break;
			case 'pageNum' :
				var pageNum = params.pageNum;
				if(page == pageNum) return;
				page = pageNum;
				break;
			default:
				return;
		}
		if(typeof funcList.loadData != 'function') return;
		funcList.loadData.call();
		funcList.setPageNumber.call();
	},
	/**
	 * 日期控件调用方法 
	 */
	showDate : function(params){
		if(typeof WdatePicker == 'function') WdatePicker(params);
	},
	
	/**
	 * 树形展开折叠方法 
	 */
	showBranch : function(){
		var parent = this.parentNode;
		if(parent.className == 'branch'){
			var branch = this.next('div');
			this.src = this.src.indexOf('close.jpg') > -1 ? '../images/open.jpg' : '../images/close.jpg';
			branch.style.display = this.src.indexOf('open.jpg') == -1 ? 'none' : 'block';
		}
	},
	/**
	 * Checkbox默认点击方法
	 */
	checkMe : function(){
		var className = 'icon ' + (this.className == 'icon icon-check' ? 'icon-checked' : 'icon-check');
		this.className = className;
		var div = this.next('div');
        //var clickNodeLabel = jQuery(this).next();
        //clickNodeLabel.css("color",this.src.indexOf('no.gif') > -1?"black":"red");
		if(div){
			var checkboxs = div.getElementsByTagName('i'), checkbox;
			for(var i = 0, len = checkboxs.length; i < len; i++){
				checkbox = checkboxs[i];
				if(checkbox.getAttribute('type') == 'checkbox') checkbox.className = className;
			}
		}
		funcList.setCheckbox(document.body);
	},
	/**
	 * 设置属性机构中所有 Checkbox
	 */
	setCheckbox : function(obj){
		obj = $$(obj);
		if(!obj) return;
		var getCheckboxs = function(obj){
			obj = $$(obj);
			if(!obj) return obj;
			var is = obj.getElementsByTagName('i');
			if(!is || !is.length) return null;
			var newIs = [], ie;
			for(var i = 0, len = is.length; i < len; i++){
				ie = is[i];
				if(ie.getAttribute('type') == 'checkbox') newIs.push(ie);
			}
			return newIs;
		};
		var checkboxs = getCheckboxs(obj), checkbox, div;
		if(!checkboxs) return;
		for(var i = 0, len = checkboxs.length; i < len; i++){
			checkbox = checkboxs[i];
			div = checkbox.next('div');
			if(!div || div.className != 'leaf') continue;
			var cs = getCheckboxs(div), c, checkNum = 0;
			for(var j = 0, size = cs.length; j < size; j++){
				c = cs[j];
				if(c.className.indexOf('icon-checked') > -1 || (c.parent && c.parent.className == 'branch')) checkNum++;
			}
			if(!checkNum) checkbox.className = 'icon icon-check';
			else if(checkNum == cs.length) checkbox.className = 'icon icon-checked';
			else checkbox.className = 'icon icon-checkhalf';
		}
	},
	
	
	checkBox : function(){
		if(this.className.indexOf('icon-checked') > -1) {
			this.removeClass('icon-checked').addClass('icon-check');
			this.setAtt({value : 0});
		}else{
			this.removeClass('icon-check').addClass('icon-checked');
			this.setAtt({value : 1});
		}
	}, 
	dbCheckBoxTr:function(){
		var is = this.getElementsByTagName('i');
    	if(is.className.indexOf('icon-checked') > -1) {
			is.removeClass('icon-checked').addClass('icon-check');
			is.setAtt({value : 0});
		}else{
			is.removeClass('icon-check').addClass('icon-checked');
			is.setAtt({value : 1});
		}
	},
	previousMonth : function(){
		var str = $$('dateView').previous('thead').getElementsByTagName('tr')[0].getElementsByTagName('th')[1].innerHTML;
		var month = str.substring(0, str.indexOf('月'));
		month--;
		var temp = 0;
		if(!month){
			month = 12;
			temp = 1;
		}
		var year = str.substring(str.indexOf('月') + 1) - temp;
		fangjs.setDateView(year, month - 1);
	},
	nextMonth : function(){
		var str = $$('dateView').previous('thead').getElementsByTagName('tr')[0].getElementsByTagName('th')[1].innerHTML;
		var month = str.substring(0, str.indexOf('月'));
		month++;
		var temp = 0;
		if(month > 12){
			month = 1;
			temp = -1;
		}
		var year = str.substring(str.indexOf('月') + 1) - temp;
		fangjs.setDateView(year, month - 1);
	},
	closeDialog : function(params){
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); 
	},
};
/**
 *搜索框回车事件 
 */
funcList.searchKeydown = function(){
	var keynum = getEvent().keyCode;
//	alert(getEvent().keyCode + "-" + getEvent().charCode);
	if(keynum != 13) return;
    var searchStr = this.value.trim();
    if(typeof funcList.search == 'function') funcList.search.call(this);
};

/**
 * 刷新方法
 */
funcList.refresh = function(){
	
	if(typeof funcList.loadData == 'function') {
		funcList.loadData();
	}else{
		window.location.reload();
	}
};

var download_file = {};
funcList.download_file = function(url,i,host)
{
	i = i||0;
    var download_file_iframe = document.createElement("iframe");
    download_file_iframe.id = 'fang_download_file_' + i;
    download_file['iframe_' + i] = download_file_iframe;
    download_file['iframe_' + i].style.display = "none";
    document.body.appendChild(download_file['iframe_' + i]);
    host = host||filewebsite;
    download_file['iframe_' + i].src = host + url;




}


/**
 * String去空格方法. 
 */
String.prototype.trim = function(){    
    return this.replace(/(^\s*)|(\s*$)/g, '');    
};

/**
 * 批量全部替换 
 */
String.prototype.replaceAll = function(s1, s2){
	return this.replace(new RegExp(s1, 'gm'), s2);
};

/**
 * 判断字符串是否以指定字符串开头 
 */
String.prototype.startWith = function(compareStr){
    return this.indexOf(compareStr) == 0;
};

/**
 * 判断字符串是否以指定字符串结尾 
 */
String.prototype.endWith = function(compareStr){
    return this.length < compareStr.length ? false : this.lastIndexOf(compareStr) == this.length - compareStr.length;
};

/**
 * 获取元素在数组中首次出现的位置. 
 */
Array.prototype.indexOf = function(val){
    for (var i = 0, len = this.length; i < len; i++) {  
        if (this[i] == val) {  
            return i;  
        }  
    }  
    return -1;  
};  

/**
 * 从数组中删除指定元素 
 */
Array.prototype.remove = function(val){  
    var index = this.indexOf(val);  
    if (index > -1) this.splice(index, 1);  
};

/**
 * 遍历集合中所以元素并执行指定操作.
 * 参数为要对集合中元素执行的回调方法. 
 */
Array.prototype.foreach = function(fun){
    if(!this.length) return;
    for (var i = 0, len = this.length; i < len; i++) {
        if(typeof fun == 'function')fun.call(this[i], i);
    }
};


/**
 * getElementsByTagName
 */
Element.prototype.$s = function(tagName){
	return 'string' == typeof tagName ? this.getElementsByTagName(tagName) : null;
};

/**
 * 获取该节点下面所有子节点. 
 */
Element.prototype.getChildNodes = function(tag){
    var nodes = this.childNodes;
    var needNodes = [];
    var node = null;
    for(var i = 0, len = nodes.length; i < len; i++){
        node = nodes[i];
        if(node.nodeName != '#text' && node.nodeName != '#comment' &&(!tag || node.tagName.toLowerCase() == tag.toLowerCase())) needNodes.push(node);
    }
    return needNodes;
};

/**
 * 获取该节点下面第一个子节点. 
 */
Element.prototype.firstNode = function(tag){
	var needNodes = this.getChildNodes(tag);
	if(needNodes.length) return needNodes[0];
	return null;
};

/**
 * 获取该节点下面最后一个子节点. 
 */
Element.prototype.lastNode = function(tag){
	var needNodes = this.getChildNodes(tag);
	if(needNodes.length) return needNodes[needNodes.length - 1];
	return null;
};

/**
 * 获取该节点的前一tagName为指定tag节点,不传参数是返回前一非空节点. 
 */
Element.prototype.previous = function(tag){
    var previousNode = this.previousSibling;
    var getEle = function(node){
    	if(!node) return null;
    	if(node.nodeName == '#text' || node.nodeName == '#comment') return getEle(node.previousSibling);
    	return node;
    };
    previousNode = getEle(previousNode);
    if(!previousNode) return null;
    if(!tag || previousNode.tagName.toLowerCase() == tag.toLowerCase()) return previousNode;
    return previousNode.previous(tag);
};

/**
 * 获取该节点的下一tagName为指定tag节点,不传参数是返回下一非空节点. 
 */
Element.prototype.next = function(tag){
    var nextNode = this.nextSibling;
    var getEle = function(node){
	    if(!node) return null;
    	if(node.nodeName == '#text' || node.nodeName == '#comment') return getEle(node.nextSibling);
    	return node;
    };
    nextNode = getEle(nextNode);
    if(!nextNode) return null;
    if(!tag || nextNode.tagName.toLowerCase() == tag.toLowerCase()) return nextNode;
    return nextNode.next(tag);
};

/**
 * 向上获取该节点外层节点中的指定tagName的节点,不传参数是返回父节点. 
 */
Element.prototype.getParentByTag = function(tag){
    var parent = this.parentNode;
    if(!tag) return parent;
    if(!parent || parent.tagName.toLowerCase() == 'html') return null;
    if(parent.tagName.toLowerCase() == tag.toLowerCase()) return parent;
    return parent.getParentByTag(tag);
};

/**
 * 获取select被选中的option的内容
 */
Element.prototype.getSelectedText = function(){
    if(this.tagName.toLowerCase() != 'select') return '';
    if(!this.options || !this.options.length) return '';
    return this.options[this.selectedIndex].innerHTML;
};

/**
 * 设置指定元素的样式.参数为json数据格式,如:{width ： '100px', height : '100px'} 
 */
Element.prototype.css = function(style){
	if(!style || typeof style != 'object') return this;
	for(var name in style){
		try{
			this.style[name] = style[name];
		}catch(e){}
	}
	return this;
};


Element.prototype.addClass = function(className){
	if(!className || typeof className != 'string') return this;
	this.className += ' ' + className;
	return this;
};

Element.prototype.hasClass = function(className){
	if(!className || typeof className != 'string') return false;
	return new RegExp("(^|\\s)" + className + "(\\s|$)").test(this.className);;
};

Element.prototype.removeClass = function(className){
	if(!className || typeof className != 'string') return this;
	this.className = this.className.replaceAll(className, '');
	return this;
};

/**
 *  设置指定元素的属性.参数为json数据格式,如:{id ： 'id1', aaa : 'AAA'} 
 */
Element.prototype.setAtt = function(attributes){
	if(!attributes || typeof attributes != 'object') return this;
	for(var name in attributes){
		this.setAttribute(name, attributes[name]);
	}
	return this;
};

/**
 * 移除该节点中的指定元素,参数不传则移除该节点的所有子元素,参数可以为tag名称(如:'div'),或者名称数组(如:['div', 'span']). 
 */
Element.prototype.clean = function(tag, index){
	if(fangjs.isArray(tag)) for(var i = 0, len = tag.length, tagval = tag[i]; i < len; i++) this.clean(tagval, index);
	else if(!tag || typeof tag == 'string'){
		var nodes = this.getChildNodes(tag);
		if(nodes && nodes.length) for(var i = index || 0, len = nodes.length; i < len; i++) this.removeChild(nodes[i]);
	}
	return this;
};

/**
 * 格式日期.
 *  参数为日期格式,默认为yyyy-MM-dd
 */
Date.prototype.format = function(style) {
    if(!style || !(style = style.toString().trim())) style = 'yyyy-MM-dd hh:mm:ss';
    var o = {
        'M+' : this.getMonth() + 1, //month
        'd+' : this.getDate(),      //day
        'h+' : this.getHours(),     //hour
        'm+' : this.getMinutes(),   //minute
        's+' : this.getSeconds(),   //second
        'q+' : Math.floor((this.getMonth() + 3) / 3), //quarter
        'S' : this.getMilliseconds() //millisecond
    };
    if(/(y+)/.test(style)) style = style.replace(RegExp.$1, this.getFullYear().toString().substr(4 - RegExp.$1.length));
    for(var k in o) if(new RegExp('('+ k +')').test(style)) style = style.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(o[k].toString().length));
    return style;
};

/**
 * 获取N天后的时间 
 */
Date.prototype.addDate = function(days){
  return new Date(this.valueOf() + days * 24 * 60 * 60 * 1000);
};

var fangjs = function(){};

/**
 * 从URL中根据key获取参数值
 * 参数为key. 
 */
fangjs.getParamFromURl = function(key){
	var arr = document.location.search.substr(1).match(new RegExp('(^|&)' + key + '=([^&]*)(&|$)'));
    if(!arr || !arr.length) return null;
    return arr[2]; 
};

/**
 * 判断对象是否是Array类型.
 */
fangjs.isArray = function(obj){
    if(!obj) return false;
    return Object.prototype.toString.call(obj) === '[object Array]';
};


/**
 * html符号替换 
 */
fangjs.escapeHtml = function(original) {
    if(!original) return '';
    original = original.toString();
    return original.replace(/(\r)?\n/g, '<br/>').replace(/(["\\])/g, '\\$1').replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');

};


/**
 * 获取上下文路径. 
 */
fangjs.getContextPath = function(){
    var location = document.location;
    var path = location.protocol + '//' + location.hostname;
    if(location.port && location.port != 80){
        path += ':' + location.port;
    }
    var pathName = decodeURIComponent(document.location.pathname);
    var idx = pathName.substr(1).indexOf('/');
    path += pathName.substr(0, idx + 1) + '/';
    return path;
};


/**
 * 获取指定name的Cookie值
 * name: 键
 */
fangjs.getCookie = function(name){
    var arr = document.cookie.match(new RegExp('(^| )' + name + '=([^;]*)(;|$)'));
    if(!arr || !arr.length) return null;
    return unescape(arr[2]); 
    
};

/**
 * 设置Cookie值
 * name: 键
 * value: 值
 * days: 时间,单位为天,为0或者为空时不设定过期时间，浏览器关闭时cookie自动消失
 */
fangjs.setCookie = function(name, value, days, otherParams){
    var str = name + '=' + escape(value);
    if(!isNaN(days) && days > 0) str += '; expires=' + new Date().addDate(days).toGMTString();
    if(otherParams && typeof otherParams == 'object') for(var key in otherParams) str += ';' + key + '=' + otherParams[key];
    document.cookie = str;
};

/**
 * 删除Cookie值 
 * name: 键
 */
fangjs.delCookie = function(name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var value = fangjs.getCookie(name);
    if(value) document.cookie = name + '=' + escape(value) + ';expires=' + exp.toGMTString();
};

/**
 * 获取指定name的localStorage值 name: 键
 */
fangjs.getLocalStorage = function(name){
	var obj = window.localStorage.getItem(name);
	return obj && (obj.startWith('{') || obj.startWith('[')) ? eval('(' + obj + ')') : obj;
};

/**
 * 设置localStorage值 name: 键 value: 值
 */
fangjs.setLocalStorage = function(name, value){
	window.localStorage.setItem(name, JSON.stringify(value));
};

/**
 * 删除Cookie值 name: 键,不传则清楚所有
 */
fangjs.deleteLocalStorage = function(name){
	if(name) window.localStorage.removeItem(name);
	else window.localStorage.clear();
};

/**
 * 获取指定name的SessionStorage值 name: 键
 */
fangjs.getSessionStorage = function(name){
	var obj = window.sessionStorage.getItem(name);
	return obj && (obj.startWith('{') || obj.startWith('[')) ? eval('(' + obj + ')') : obj;
};

/**
 * 设置SessionStorage值 name: 键 value: 值
 */
fangjs.setSessionStorage = function(name, value){
	window.sessionStorage.setItem(name, JSON.stringify(value));
};

/**
 * 删除SessionStorage值 name: 键,不传则清楚所有
 */
fangjs.deleteSessionStorage = function(name){
	if(name) window.sessionStorage.removeItem(name);
	else fangjs.getSS().clear();
};

/*
 * 生产指定位数的随机数.
 * 参数为生成随机数的位数.
 */
fangjs.rndNum = function(n){
	n = n || 1;
	var rnd = '';
	for(var i = 0; i < n; i++) rnd += Math.floor(Math.random() * 10);
	return rnd - 0;
};

/**
 * 深度复制方法 
 */
fangjs.clone = function(obj){
	if(!obj || typeof obj != 'object') return obj;
	var cloneObj;
	if(fangjs.isArray(obj)){
		cloneObj = [];
		for(var i = 0, len = obj.length; i < len; i++) cloneObj[i] = fangjs.clone(obj[i]);
	}else{
		cloneObj = {};
		for(var key in obj) cloneObj[key] = fangjs.clone(obj[key]);
	}
	return cloneObj;
};
/**
 * 数据填充方法.<br />
 * 参数1, tbadyId:显示数据的tbady的ID;<br />
 * 参数2, colArray:数据属性显示顺序拍戏,即第一列显示哪个属性,第二列显示哪个属性......;<br />
 * 参数3, dataArray:要显示的数据的数组 ;<br />
 * 参数4, totalCount:数据总数量 ;totalCount='no' 不分页<br />
 * 参数5, startCallBack:每行的起始列的内容回调方法,如:选择列表中第一列需要显示checkbox选择框等;<br />
 * 参数6, endCallback:每行的结束列的内容回调方法,如:最后一列需要加入操作按钮,编辑\删除\等;<br />
 * 参数7, callbackArray:每一列的回调函数列表,key对应参数2数组中的值;<br />
 * 注1:所有传入的回调函数都有2个参数,参数1为该列td,参数2为该行tr;参数3为该行行号从0开始;
 * 并且在方法中可以直接使用this,this值为当前要显示的数据,即参数3数组中的某个元素.
 */
fangjs.dataView = function(tbadyId, colArray, dataArray, totalCount, startCallBack, endCallback, callbackArray){
//	$("#trhead").show();
	if(!tbadyId || !fangjs.isArray(colArray) || !fangjs.isArray(dataArray)) {
		if("no" == totalCount){// 不分页
			return;
		}
		dataCount = 0;
		funcList.setPageNumber.call();
		return;
	}
	var tbody = $$(tbadyId);
	var data, col, tr, td, cols, html;
	if (dataArray.length == 0) {
		tr = $c('tr');
		td = $c('td');
		if(endCallback)
			td.setAttribute('colspan', colArray.length+2);
		else
			td.setAttribute('colspan', colArray.length+1);
		tr.css({
			height : '23px'
		});
		td.innerHTML = "没有数据！";
		td.css({"text-align": "center"});
		tr.appendChild(td);
		tbody.appendChild(tr);
		if("no" == totalCount){// 不分页
			return;
		}
		if(totalCount || totalCount === 0) dataCount = totalCount;
		funcList.setPageNumber.call();
		return;
	}
	for(var i = 0, len = dataArray.length; i < len; i++){
		data = dataArray[i];
		tr = $c('tr');
		if(typeof startCallBack == 'function'){
			td = $c('td');
			startCallBack.call(data, td, tr, i);
			tr.appendChild(td);
		}
		for(var j = 0, size = colArray.length; j < size; j++){
			col = colArray[j];
			td = $c('td');
			if(callbackArray && typeof callbackArray[col] == 'function') callbackArray[col].call(data, td, tr, i);
			else {
			    cols = col.split('.');
			    html = data[cols[0]];
			    if(html){
				    for(var k = 1, l = cols.length; k < l; k++){
				        html = html[cols[k]];
				        if(!html) break;
				    }
			    }
			    td.innerHTML = fangjs.toString(html);
			    td.setAttribute("title",td.innerHTML);
			}
			tr.appendChild(td);
		}
		if(typeof endCallback == 'function'){
			td = $c('td');
			td.addClass('hidden-print');
			endCallback.call(data, td, tr, i);
			tr.appendChild(td);
		}
		tbody.appendChild(tr);
	}
	if("no" == totalCount){// 不分页
		return;
	}
	if(totalCount || totalCount === 0) dataCount = totalCount;
	funcList.setPageNumber.call();
	funcList.resetTableView.call();
};
//表头对齐
funcList.resetTableView = function(){//表头对齐
	if(!$(".t_head")||!$(".t_head").length) return;
	$(".t_head table")[0].width = $(".t_body table").width();
	if(!$$('trhead')) return;
	  var rTHeadList=$('#trhead th');
	  $.each($('.tr_head th'),function(k,v){
	    $(v).width(rTHeadList.eq(k).width());
	  });
	for(var i = 1; i <= 5; i++){
		if(!$$('trhead'+i)) return;
		  var rTHeadList=$('#trhead'+i+' th');
		  $.each($('.tr_head'+i+' th'),function(k,v){
		    $(v).width(rTHeadList.eq(k).width());
		  });
	}
};

var scrollMap = {"ToScroll":"ToScroll1","ToScroll1":"ToScroll","ToScroll-1":"ToScroll1-1","ToScroll1-1":"ToScroll-1","ToScroll-2":"ToScroll1-2","ToScroll1-2":"ToScroll-2"};
var resetScroll = function(id){
	document.getElementById(scrollMap[id]).scrollLeft = document.getElementById(id).scrollLeft;
};


/**
 * 清除分页信息 
 */
fangjs.cleanPage = function(){
	page = 1;
	dataCount = 0;
	funcList.setPageNumber.call();
};

/**
 * 移除显示的数据,参数为 tbady的ID.
 */
fangjs.cleanData = function(tbadyId, index){
	$$(tbadyId).clean(null, index);
};

/**
 * 处理IE下null和undifined变字符串问题.
 */
fangjs.toString = function(str){
	if((str || str === 0) && str != 'null' && str != 'NULL' && str != 'undefined'){
		if(isNaN(str)) return str;
		var index = (str = str.toString()).indexOf('.');
		var number = window.parent.setupMap && window.parent.setupMap.amountDigits ? window.parent.setupMap.amountDigits.value : 2;
	    if(index != -1 && str.length - index - 1 > number) str = (str - 0).toFixed(number);
	    return str;
	}
	return '';
};


/**
 * 组装实体方法 
 * @param {Object} entity 实体
 * @param {String} name 实体属性名称
 * @param {Object} value 实体属性值
 * @param {Object} callbackArray 回调函数列表
 */
fangjs.setEntity = function(entity, name, value, callbackArray){
    if(!name || value == '请选择') return;
    entity = entity || {};
    if(callbackArray && callbackArray[name]) callbackArray[name].call(entity, value);
    else{
	    if(name.indexOf('.') == -1){
	        entity[name] = fangjs.toString(value);
	        return;
	    }
	    var names = name.split('.'), obj = entity; 
	    for(var i = 0, len = names.length; i < len; i++){
	        if(len - i > 1) {
	            obj[names[i]] = obj[names[i]] || {};
	            obj = obj[names[i]];
	        }else{
	            obj[names[i]] = fangjs.toString(value);
	        }
	    }
    }
//    entity[names[0]] = entity[names[0]] || {};
//    entity[names[0]][names[1]] = value;
};

/**
 * 编辑页面战士实体属性 
 * @param {Object} entity 实体
 * @param {String/Array} tags 需要显示数据的标签名称
 * @param {String} dataStyle 日期显示格式
 * @param {Object} callbackArray 回调函数列表
 */
fangjs.showEntity = function(entity, tags, dataStyle, callbackArray){
    if(!entity || !tags) return;
    dataStyle = dataStyle || 'yyyy-MM-dd';
    var formatValue = function(key, value, ele){
    	if(key.endWith('Date') || key.endWith('Time') || ele.getAttribute('click') == 'showDate') return new Date(value).format(dataStyle);
        return fangjs.toString(value);
    };
    var showeles = function(eles){
        if(!eles || ! eles.length) return;
        var ele, id, keys;
        for(var i = 0, len = eles.length; i < len; i++){
            ele = eles[i];
            id = ele.id;
            if(!id) continue;
            if(callbackArray && callbackArray[id]){
            	callbackArray[id].call(ele, entity);
            }else{
	            if(id.indexOf('.') == -1) {
	            	 if(id in entity) ele.value = formatValue(id, entity[id], ele);//删除判断条件 if(entity[id] || entity[id] === 0)
	            }
	            else{
	                keys = id.split('.');
	                var obj = entity;
	                for(var j = 0, size = keys.length; j < size; j++){
	                    obj = obj[keys[j]];
	                    if(!obj) break;
	                }
	                if(obj || obj === 0) ele.value = formatValue(id, obj, ele);
	            }
            }
        }
    };
    if(typeof tags ==  'string') showeles($s(tags));
    else tags.foreach(function(){showeles($s(this.toString()));});
};

fangjs.loadSelectData = function(data){
    if(!data) return;
    var select, options, option;
    for(var key in data){
        select = $$(key);
        if(!select || select.tagName.toLowerCase() != 'select') continue;
        options = data[key];
        //if(!options || !fangjs.isArray(options) || !options.length) continue;
        if(options && fangjs.isArray(options) && options.length){
        	options.foreach(function(){
	            option = $c('option');
	            if(key.indexOf('.id') == -1) option.value = this.name;
	            else option.value = this.id;
	            option.innerHTML = this.name;
	            select.appendChild(option);
	        });
        }
        option = $c('option');
        option.value = '自定义';
        option.innerHTML = '自定义';
        select.appendChild(option);
        //select.setAtt({dictType : options[0].dictType});
        select.onchange = function(){
        	var ele = getElement();
        	if(ele.value != '自定义') return;
        	var verifytype = ele.getAttribute('verifytype');
        	var verifynum = ele.getAttribute('verifynum');
        	var input = $c('input');
        	input.setAtt({
        		type : 'text',
        		id : ele.getAttribute('id'),
        		dictType : ele.getAttribute('dictType'),
        		'class' : ele.className.replace('opt-i', 'm-text')
        	});
        	ele.removeAttribute('id');
        	ele.onblur = null;
        	if(verifytype && verifynum){
		    	ele.removeAttribute('verifytype');
		    	ele.removeAttribute('verifynum');
        		input.setAtt({
	    			verifytype : verifytype,
	    			verifynum : verifynum
	    		});
	        	input.onblur = validation;
				verifyArray[verifynum] = false;        		
        	}
	    	var parent = ele.parentNode;
	    	var next = ele.next();
	    	//parent.removeChild(ele);
	    	ele.addClass('hidden');
	    	if(next) parent.insertBefore(input, next);
	    	else parent.appendChild(input);
	    	input.focus();
        };
    }
};

/**
 * json 合并方法
 * 参数为要合并的json的集合
 */
fangjs.jsonMerge = function(jsonArray){
	if(!fangjs.isArray) return null;
	var result = {};
	jsonArray.foreach(function(){
		for(var key in this) result[key] = this[key];
	});
	return result;
};

/**
 *	 最后一次提示时间
 */
var lastAlertTime;

/**
 * ajax
 */
fangjs.ajax = function(url, paramstr, callback, async, method){
    var xmlhttp = null;
    //var ajaxConnection = null;
    if(window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest();
        //xmlhttp.overrideMimeType('text/xml');
        //ajaxConnection = 'Close';
    } else if(window.ActiveXObject){
        try{
            xmlhttp = new ActiveXObject('Microsoft.XMLHTTP');
        }catch(e){
            try{
                xmlhttp = new ActiveXObject('MSXML2.XMLHTTP');
            }catch(e){}
        }
        //ajaxConnection = 'keep-alive';
    }
    if(!xmlhttp){
        alert('对不起，您的浏览器不支持AJAX！');
        return null;
    } 
    var isPost = 'POST' == method;
    url += (!isPost && paramstr ? '?' + paramstr : '');
    try {
        //添加ajax请求时给页面上添加一个正在加载的功能。。。 扫行请求前显示加载框
        var parent = window.parent || window;
        var index = null;
		if (parent.layer&&async) {
			index = parent.layer.load(0, {
				shade : false
			});
		}else if(parent.layer){
			index = parent.layer.load(0, {
				shade: [0.3,'#fff']
			});
		}
        xmlhttp.onreadystatechange = function(){
            //当状态为４时　　隐藏加载框
            if(xmlhttp.readyState == 4 && index){
            	parent.layer.close(index);
            }
            if(xmlhttp.readyState == 4 && xmlhttp.status == 200 && typeof callback == 'function'){
        		var data = null;
        		var resultStr = null;
            	try{
            		resultStr = xmlhttp.responseText;
            		if(resultStr) data = resultStr && (resultStr.startWith('{') || resultStr.startWith('[')) ? eval('(' + resultStr + ')') : resultStr;
            	}catch(e){
			    	var msg = '返回值异常，请联系管理员！';
			    	//if(window.parent && typeof window.parent.parentAlert == 'function') window.parent.parentAlert(msg);
			    	//else alert(msg);
			    	alert(msg);
			    	return;
			    }
			    if(data) callback(data);
			    else callback(resultStr);
			}else if(xmlhttp.readyState == 4 && xmlhttp.status == 404){
				var msg = '系统异常，请联系管理员！';
		    	alert(msg);
		    	return;
			}else if(xmlhttp.readyState == 4 && xmlhttp.status == 998){
				var msg = '您没有该功能的操作权限，请联系管理员！' + xmlhttp.responseText + url;
//				window.parent.openWindow('noVisit.jsp?visitUrl=' + xmlhttp.responseText, '300', '100');
				if(!lastAlertTime || new Date().getTime() - lastAlertTime.getTime() > 1000*10){
					fangjs.alert(msg);
					lastAlertTime = new Date();
				}
				return;
			}else if(xmlhttp.readyState == 4 && xmlhttp.status == 999){
				window.location.href = ROOT_PATH + "login.html";
			}
        };
        xmlhttp.open(method, url, async);
//        xmlhttp.open(method, url, true);
        //xmlhttp.setRequestHeader('Connection', ajaxConnection);
    	xmlhttp.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
        if(isPost) xmlhttp.send(paramstr);
        else xmlhttp.send();
        if(!async) return xmlhttp.responseText;
    }
    catch(e){
        console.log(e);
    	var msg = '网络异常，请联系管理员！';
    	alert(msg);
    }
};

/**
 * ajax执行一个服务器端Action.<br />
 * 第1参数,url: 一个Action的描述,如:userBean.test;<br />
 * 第2参数,params: 参数列表,json格式,如{id:3,name:'张三'};<br />
 * 第3参数,callback: 回调函数;<br />
 * 第4参数,async: 异步标记，默认为异步(true);<br />
 * 第5参数,type: 执行程序的返回值类型,默认为json;<br />
 * 第6参数,httpMethod: http请求类型 GET/POST, 默认POST;<br />
 * 第7参数,loadDivId: 异步调用时，显示loading图片的div的id;<br />
 */
fangjs.execjava = function(url, params, callback, async, type, httpMethod, appId){
    if(!url) return null;
    if(!type) type = 'json';
    if(typeof async == 'undefined') async = true;
    if(!httpMethod) httpMethod = 'POST';
    if(searchParams){
    	if(!params) params = {};
    	for ( var key in searchParams) {
    		params[key] = searchParams[key];
		}
    }
    if(!appId) appId = APP_ID;
    var paramstr = params ? JSON.stringify(params) : null;
    var results = fangjs.ajax("/" + appId + "/" + url, paramstr, callback, async, httpMethod);
    if(async){
    	//if(loadDivId)
        //$$(loadDivId).innerHTML = "<table width='100%' height='100%' boder='0'><tr><td align='center' valign='middle'><img src='" + JsSupport.getUISPContextPath() + "/img/loading/001.gif'><img src='" + JsSupport.getUISPContextPath() + "/img/loading/Loading.gif'></td></tr></table>";
    }else{
        try{
        	results = results && (results.startWith('{') || results.startWith('[')) ? eval('(' + results + ')') : results;
            if(type == 'json') return results;
            else if(type == 'void') return;
            else return results;
        }
        catch(e){}
    }   
};

/**
 * 弹出框
 */
fangjs.openDialog = function(dialogUrl,title,width,height,callback,maxmin){
	title = title || 'FANG';
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
	width = (width.indexOf("px") != -1 && Number(width.replaceAll("px","")) > clientWidth)? '100%' :(width || '80%');
	height = (height.indexOf("px") != -1 && Number(height.replaceAll("px","")) > clientHeight)? '100%' : (height || '80%');
	maxmin = maxmin || false;
	window.parent.layer.open({
		  type: 2,
		  title: title,
		  shadeClose: false,
		  shade: 0.4,
//		  shift: 2,
		  maxmin : maxmin,
		  area: [width, height],
		  content: ROOT_PATH + dialogUrl,
		  end : function() {
			if (typeof callback == 'function') {
				callback.call();
			}
		  }
	}); 
}; 

fangjs.alert = function(text){
	var _window = window.parent || window;
	_window.layer.alert(text); 
}; 

fangjs.confirm = function(text,callback){
	var _window = window.parent || window;
	_window.layer.confirm(text, {}, function(index) {
		callback.call(this,index);
		layer.close(index);
	}, function(index) {
		layer.close(index);
	});
}

/**
 * 弹出框关闭方法
 */
fangjs.closeDialog = function(){
// parent.location.reload();
	 var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
     parent.layer.close(index);
}

funcList.closeDialog = function() {
	fangjs.closeDialog();
}

var moreTip = null;
var moreHtml = "";
//功能权限控制
funcList.funcControl = function(){
	var functionList = fangjs.getLocalStorage("functionList");
	if(!functionList) return;
	var preFunc = null, func = null, rgt = null;
	var moreMenu = null;
	var heigtVal = 0;
	var orignHtml = $(".btn-func").html();
	var htmlStr = "";
	for(var i = 0, len = functionList.length; i < len; i++){
		func = functionList[i];
		if(func.functionUrl && document.location.href.indexOf(func.functionUrl) != -1){
			preFunc = func;
			continue;
		}
		if(preFunc && func.lft > preFunc.lft && func.rgt < preFunc.rgt){
			if(rgt && func.rgt < rgt){
				continue;
			}
			rgt = func.rgt;
			htmlStr += '<a class="btn btn-default" click="' + func.functionId + '" ' + (func.notes?func.notes:'') + '><i class="glyphicon ' + func.functionClass + '"></i>' + func.functionName + '</a>';
			
			continue;
		}
		if(preFunc) break;
	}
	htmlStr += '<a class="btn btn-default" click="refresh" params="act=add"><i class="glyphicon glyphicon-refresh"></i>刷新</a>';
	htmlStr = htmlStr.replaceAll("&", "=");
	$(".btn-func").html(htmlStr + orignHtml);
};


/**
 * pageSizeChange
 */
funcList.pageSizeChange = function(){
	pageSize = this.value;
	page = 1;
	if(typeof funcList.loadData != 'function') return;
	funcList.loadData.call();
	funcList.setPageNumber.call();
};


/**
 * 按字段排序
 */
funcList.sortByColumn = function(){
	searchParams = searchParams||{};
	var colunmName = this.getAttribute("colunmName");
	if(!colunmName){
		return;
	}
	
	if(this.hasClass('glyphicon-chevron-down')){
		this.removeClass('glyphicon-chevron-down');
		this.addClass('glyphicon glyphicon-chevron-up');
		searchParams.order = 'asc';
	}else{
		this.removeClass('glyphicon-chevron-up');
		this.addClass('glyphicon glyphicon-chevron-down');
		searchParams.order = 'desc';
	}
	searchParams.sort = colunmName;
	funcList.loadData();
	
	var tr = this.getParentByTag("tr");
	var thList = tr.getChildNodes("th");
	for(var i = 0; i < thList.length; i++){
		if(this != thList[i]){
			thList[i].className = "";			
		}
	}
};



fangjs.headerSort =function(sortMap,colName)
{
	var imgid = colName + "_imgid";
	var myImg = $$(imgid);
	var pTd = $$("hrd_"+colName);	
	if(!myImg && !myImg){
		var img = $c('img');
		img.id = imgid; 
		pTd.appendChild(img);
		myImg = $$(imgid);
		myImg.style.paddingLeft = '10px';
	}
	myImg.css({
		display : 'inline-block',
		verticalAlign : 'middle'
	});
	var sort = sortMap[colName];
	if(!sort){
		sort = 'asc';
		myImg.src = "../images/up.png";
	}else if(sort == 'asc'){
		sort = 'desc';
		myImg.src = "../images/down.png";
	}else if(sort == 'desc'){
		sort = 'asc';
		myImg.src = "../images/up.png";
	}
	sortMap[colName]=sort;
	var tds = myImg.parentNode.parentNode.$s("th");
	for(var i = 0; i < tds.length; i++){
		var td = tds[i];
		var tmpImgs = td.$s("img");
		if(tmpImgs && tmpImgs.length){
			for(var j = 0; j < tmpImgs.length; j++){
				var tmpImg = tmpImgs[j];
				if(tmpImg.id && tmpImg.id!=imgid){
					td.removeChild(tmpImg);
				}
			}
		}
	}
	if(searchParams){
		searchParams.sortName=colName;
		searchParams.sortType=sort;
	}
	funcList.loadData({sortName:colName,sortType:sort});
};



/**
 * 树节点对象 
 * @param {String} text 显示文字.
 * @param {String} click 点击事件名称.
 * @param {String} params 点击事件参数列表.
 * @param {boolean} isShow 如果有子节点是否展开子节点.
 * @param {boolean} showCheckbox 是否显示checkbox.
 * @param {String} isChecked 是否选中:yes\no\half, 为null时默认为不选中.
 * @param {String} checkboxClick checkbox点击事件名称.
 * @param {String} checkboxParams checkbox点击事件参数列表.
 */
var branch = function(text, click, params, isShow, showCheckbox, isChecked, checkboxClick, checkboxParams){
	var clickStr = click ? ' click="' + click + '"' : '';
	var paramsStr = params ? ' params="' + params + '"' : '';
	var isdisplay = isShow ? '' : 'style="display: none;"';
	var pic = isShow ? 'open.jpg' : 'close.jpg';
	var checkboxStr = '';
	if(showCheckbox){
		var classRelation = {yes : 'icon-checked', no : 'icon-check', half: 'icon-checkhalf'};
		var className = 'icon ' + (isChecked ? classRelation[isChecked] : classRelation.no);
		var checkboxClickStr = checkboxClick ? ' click="checkMe,' + checkboxClick + '"' : 'click="checkMe"';
		var checkboxParamsStr = checkboxParams ? ' params="' + checkboxParams + '"' : '';
		//checkboxStr = '<img type="checkbox" src="' + isCheckedStr + '" ' + checkboxClickStr + checkboxParamsStr + '/>';
		checkboxStr = '<i type="checkbox" class="' + className + '" ' + checkboxClickStr + checkboxParamsStr + '></i>';
	}
	this.branches = new Array();
	this.add = function(leaf){
		this.branches[this.branches.length] = leaf;
	};
	this.html = function(){
		if(this.branches.length){
			var branchString = '<div class="branch">';
			branchString += '<img src="../images/' + pic + '" click="showBranch" />' + checkboxStr;
			branchString += '<label class="tree-lable" ' + clickStr + paramsStr + '>' + text + '</label>';
			branchString += '<div class="leaf" ' + isdisplay + '>';    
			for (var i = 0, len = this.branches.length; i < len; i++) branchString += this.branches[i].html();
			branchString += '</div></div>';
			return branchString;
		}else{
			var leafString = '<div class="leaf-node">';
			leafString += '<img src="../images/doc.jpg" border="0" />' + checkboxStr;
			leafString += '<label class="tree-lable" ' + clickStr + paramsStr + ' >' + text + '</label>';
			leafString += '</div>';
			return leafString;
		}
	};
};


//拦截F5、esc
var keyEvent = function(){
    var eve = getEvent();
    var keyCode = eve.keyCode;
	if(keyCode == 116 || keyCode == 27){
		eve.keyCode = 32;
		eve.cancelBubble = true;
		eve.returnValue = false;
        if(eve.preventDefault) eve.preventDefault();
        var parent = window.parent || window;
		if(keyCode == 116){
			if(parent == window) document.location.reload();
			else if(!parent.getCurrentDiv()) parent.document.location.reload();
			else parent.getCurrentDiv().getElementsByTagName('iframe')[0].contentWindow.document.location.reload();
		}else {
			if (window.name.startWith("layui-layer")) {
				funcList.closeDialog();
			}
		}
		return false;
	}
};

//-->

/**
 * 主要是为了解决在chrome下高度时忘删除console.log();而导致在ie下报js错误的问题
 * */
if(typeof console == "undefined"){
    window.console = {};
    console.log = function(msg){
//        alert("console.log:\r\n" + msg);
    };
};


/**
 * 打印
 */
funcList.print = function(params){
	PageSetup_del();
	window.print();
};

/**
 * 打印
 */
funcList.printSelected = function(params){
	PageSetup_del();
	
	// 获取div中的html内容
	var newhtml = "";
	$("[data-print='yes']").each(function(i){
		newhtml += this.outerHTML;
	});
	if(params.className){
		newhtml += $("." + params.className).prop("outerHTML");
	}
	if(params.id){
		newhtml += $("#" + params.id).prop("outerHTML");
	}


	// 获取原来的窗口界面body的html内容，并保存起来
	var oldhtml = document.body.innerHTML;

	// 给窗口界面重新赋值，赋自己拼接起来的html内容
	document.body.innerHTML = newhtml;
	$(".t_body").removeClass("t_body");
	$(".table").addClass("table-bordered");
	// 调用window.print方法打印新窗口
	window.print();

	// 将原来窗口body的html值回填展示
	document.body.innerHTML = oldhtml;
};


/**
 * 去掉页眉页脚
 */
function PageSetup_del(){ 
	  try{
	   var WSc=new ActiveXObject("WScript.Shell"); 
	   HKEY_Key="header"; 
	   WSc.RegWrite(HKEY_RootPath+HKEY_Key,""); 
	   HKEY_Key="footer"; 
	   WSc.RegWrite(HKEY_RootPath+HKEY_Key,"");
	  }catch(e){}
	} 

/**
 * 导出
 */
funcList.exportToExcel = function(params){
	fangjs.exportToExcel(params.id,params.name + new Date().format('yyyyMMdd'));
}

var templateHtml = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel"'
	+' xmlns="http://www.w3.org/TR/REC-html40"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">'
	+'<!--[if gte mso 9]><xml><x:ExcelWorkbook>'
	+'<x:ExcelWorksheets><x:ExcepWorksheet><x:Name>{{worksheet}}'
	+'</x:Name><x:WorksheetOptions><x:DisplayGridlines/>'
	+'</x:WorksheetOptions></x:ExcelWorksheet></x:ExcepWorksheets>'
	+'</x:ExcelWorkbook></xml><![endif]--></head><body>{{#eachtables}}'
	+ '</body></html>';

/**
 * 
 * @param tid 要到出的内容的div id。
 * @param fileName 在ie浏览器时，导出的文件名。在非IE浏览器时，没有使用
 */
fangjs.exportToExcel =function(tid,fileName){
	var htmltable=document.getElementById(tid);
	var html = htmltable.outerHTML;
	
	if(fangjs.isIE11()){
		html= html.replaceAll("<TH ","<TH style=\"border: 1px;border-style: solid;border-color: black;\" ");
		html= html.replaceAll("<TH>","<TH style=\"border: 1px;border-style: solid;border-color: black;\">");
		html= html.replaceAll("<TD","<TD style=\"border: 1px;border-style: solid;border-color: black;\" ");
		var winname=window.open('','_blank','top=10000');
		winname.document.open('text/html','replace');
		winname.document.writeln(html);
		winname.document.execCommand('saveas',false,fileName+'.xls');
		winname.close();
	}else if(fangjs.isIE()){
		html= html.replaceAll("<TH ","<TH style=\"border: 1px;border-style: solid;border-color: black;\" ");
		html= html.replaceAll("<TH>","<TH style=\"border: 1px;border-style: solid;border-color: black;\">");
		html= html.replaceAll("<TD","<TD style=\"border: 1px;border-style: solid;border-color: black;\" ");
		var winname=window.open('','_blank','top=10000');
		winname.document.open('application/vnd.ms-excel','replace');
		winname.document.writeln(html);
		winname.document.execCommand('saveas',false,fileName+'.xls');
		winname.close();
	}else{
		html= html.replaceAll("<th ","<th style=\"border: 1px;border-style: solid;border-color: black;\" ");
		html= html.replaceAll("<th>","<th style=\"border: 1px;border-style: solid;border-color: black;\">");
		html= html.replaceAll("<td","<TD style=\"border: 1px;border-style: solid;border-color: black;\" ");
		//window.open('data:application/vnd.ms-excel,'+encodeURIComponent(html));
		
		html = templateHtml.replace('{{#eachtables}}',html);
		var downLink = document.createElement("a");
		downLink.download = fileName + ".xls";
		downLink.href = 'data:application/vnd.ms-excel;base64,'+base64(html);
		document.body.appendChild(downLink);
		downLink.click();
		document.body.removeChild(downLink);
	}
};

function base64(string){
	return window.btoa(unescape(encodeURIComponent(string)));
}

function base64_decode( str )  {  
    if (window.atob) // Internet Explorer 10 and above  
        return decodeURIComponent(escape(window.atob( str )));  
    else  
    {  
        // Cross-Browser Method (compressed)  
      
        // Create Base64 Object  
        var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/\r\n/g,"\n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}  
        // Encode the String  
        return decodeURIComponent(escape(Base64.decode( str )));  
    }  
}

/**
 * 
 * @returns 返回当前浏览器是否是ie浏览器，true是ie浏览器，false是其他浏览器
 */
fangjs.isIE =function(){
	return(document.all&&window.ActiveXObject&&!window.opera)?true:false;
};
fangjs.isIE11 = function(){
	if("ActiveXObject" in window)
		return true;
	else return false;
};


/**
 * 输入必须问数字 
 * @param {Object} number
 */
fangjs.cleanNumber = function(number){
	var cleanNum = function(num) {
        if (!num || num.length == 1 || !num.startWith('0') || num.startWith('0.')) return num;// || num.startWith('0.')  为了解决输入库输入0.多的小数前面个0会被干掉的情况
        return cleanNum(num.substring(1));
    };
    var isNumber = function(num) {
        if (!isNaN(num))
            return num;
        num = num.substring(0, num.length - 1) || 0;
        return isNumber(num);
    };
    return cleanNum(isNumber(number));
};

/**
 * 换行方法
 * @param {Object} str 需要换行处理的字符串
 * @param {Object} len 多少字符一行
 */
fangjs.lineFeed = function(str, len){
	if('string' != typeof str || str.length < len) return str;
	len = len || 50;
	return str.substring(0, len) + '<br />' + fangjs.lineFeed(str.substring(len), len);
};

/**
 * 一般查询字典数据的方法
 * @param params
 */
fangjs.loadDict = function(params,callback){
	var dictMap = fangjs.getLocalStorage("dictMap")||{};
	var dictCodeList = [];
	if(fangjs.isArray(params)){
		for (var i = 0; i < params.length; i++) {
			if(!dictMap[params[i]]){
				dictCodeList.push(params[i]);
			}
		}
	}else{
		if(!dictMap[params]){
			dictCodeList.push(params);
		}
	}
	if(dictCodeList.length){
		fangjs.execjava('cache/findDictCachelByParam', {"dictCodeList":dictCodeList}, function(result){
			dictMap = fangjs.jsonMerge([dictMap,result.dictMap])
			fangjs.setLocalStorage("dictMap", dictMap);
		},false,null,null,CACHE_APP);
	}
	if(callback){
		callback(dictMap)
	}else{
		fangjs.initDict(dictMap);
	}
	
};

/**
 * 一般加载字典数据的方法
 */
fangjs.initDict = function(dictMap){
	for (var dictKey in dictMap) {
		var values = dictMap[dictKey];
		var dom = jQuery("[dictType='" + dictKey + "']");
		if(dom.length == 0) {
			dom = jQuery("#" + dictKey);
		} 
		if(dom.length == 0) continue;
		var options = [],val = dom.val();
		for(var key in values){
			options.push("<option " + ((val&&val == key)?"selected":"") + " value='" + key + "'>" + values[key] + "</option>");
		}
		jQuery(options.join("")).appendTo(dom);
		//dom.parent().find(".uew-select-text").text("请选择");
	}
};

/**
 * 清除LocalStorage里面的字典数据
 * @param dictCode
 */
fangjs.deleteDictStorage = function(dictCode){
    var dictMap = fangjs.getLocalStorage("dictMap")||{};
    if(!dictCode){
        dictMap[dictCode] = undefined;
    }else{
        fangjs.deleteLocalStorage("dictMap");
    }
};


fangjs.updateColumnDisplay = function(arr1,arr2){
    var codeList = [];
    arr1.foreach(function(){
        codeList.push(this.enName);
    });
    arr2.foreach(function(){
        if(codeList.indexOf(this.enName) == -1){
            arr1.push(this);
        }
    });
};

/**
 * 查询显示设置数据
 */
fangjs.loadDataType = function(params,callback){
    var columnDisplayMap = fangjs.getLocalStorage("columnDisplay")||{};
    //如果localStorage里面没有数据，则查询数据库
//	if(!columnDisplayMap[params.code]){
    fangjs.execjava('custom/dataType/findDataTypeByCodeFromCache/' + params.code, null, function(result){
        dataType =  result[params.code];
        if(!columnDisplayMap[params.code]){
            columnDisplayMap[params.code] = result[params.code].dataColumnList;
        }else{
            fangjs.updateColumnDisplay(columnDisplayMap[params.code],result[params.code].dataColumnList);
        }
        fangjs.setLocalStorage("columnDisplay", columnDisplayMap);
    },false,"json","get",CUSTOM_APP);
//	}
    if(callback){
        callback(columnDisplayMap[params.code],params)
    }else{
        fangjs.dealColumnDisplay(columnDisplayMap[params.code],params);
    }
};

var cols = [],ths = [];
//一般处理显示设置方法
fangjs.dealColumnDisplay = function(columnDisplay,params){
    if(!columnDisplay || !columnDisplay.length){
        return;
    }
    var ids = params.ids,cls = params.cls;
    if(ids) ids = ids.split(",");
    if(cls) cls = cls.split(",");
    cols = [];
    var htmlStr = params.start?params.start:"";
    for(var i = 0; i < columnDisplay.length; i++){
        var item = columnDisplay[i];
        if(item.show == 1 || (item.show != 0 && item.isDefault == 1)){
            if(params.hideCols && params.hideCols.indexOf(item.enName) != -1){
                continue;
            }
            cols.push(item.enName);
            ths.push(item.zhName?item.zhName:item.enName);
            if(item.callbackFun && item.callbackFun.trim() != ''){
                htmlStr += functionList[item.callbackFun].call();
            }else if(item.formType == 'select'){
                htmlStr += '<th colunmName="' + item.enName + '" click="showOptions" ' + (item.dictType?'data-dictType="' + item.dictType + '"':'') + ' data-options="' + item.options + '">' + (item.zhName?item.zhName:item.enName) + '</th>';
            }else{
                htmlStr += '<th colunmName="' + item.enName + '" ' + (item.isSort == 1?'click="sortByColumn" ':'') + '>' + (item.zhName?item.zhName:item.enName) + '</th>';
            }
        }
    }
    htmlStr += (params.end?params.end:"")
    if(ids && ids.length){
        for(var i = 0; i < ids.length; i++){
            $("#" + ids[i]).find("tr").html(htmlStr);
        }
    }
    if(cls && cls.length){
        for(var i = 0; i < ids.length; i++){
            $("." + cls[i]).find("tr").html(htmlStr);
        }
    }
};

funcList.showOptions = function(){
    var data_options = this.getAttribute("data-options");
    var options = data_options.split(",");
    var str = '<div>';
    for(var j = 0; j < options.length; j++){
        str += '<i param="' + options[j] + '" class="icon icon-check"></i><span>' + options[j] + '</span>';
    }
    str += '<button type="button" class="btn btn-default" click="determine">确定</button>';
}

/**
 * 打开配置列表页面
 */
funcList.showColumnSet = function(params){
    var openSetPage = function(data,p){
//		layer.tips("与数据库数据重复！", eve, {
//			  tips: [t > 50?1:3,'#3595CC'],
//			  time: 4000
//			});
        layer.closeAll();
        layer.open({
            type: 2,
//			  skin: 'columnSet',
            title: '<span style="margin-left: 130px;">隐藏列表</span><span style="margin-left: 330px;">显示列表</span>',
            shadeClose: false,
            shade: 0,
            offset: ['33px'],
//			  offset: 'r',
            scrollbar:false,
//			  anim : 1,
            move: false,
            tips:[3],
            resize : false,
            area: ['780px','80%'],
            content: '../base/columnSet.jsp?tableName=' + p.code
//			  end:functionList.loadData

        });
//		fangjs.openDialog('base/columnSet.jsp?tableName=' + p.tableName,"配置列表", "800px", "80%",functionList.loadData,false,1);
    }
    params = params||{};
    params.code = code;
    fangjs.loadDataType(params,openSetPage);
};




/**
 * 自动调节页面上指定元素的高度
 * resizeCallback 返回值示例 如下:
 * [{
		selector:"#heightauto",			元素jquery选择器
		hasPageBar:true,				是否考虑有分页条
		top:(81)						顶部偏移量
	},{
		selector:".info-body-l",
		top:(81)
	},...]
 */
function autoSize(){
	
	// 800 X 600
	// 1024 X 768
	// 1280 X 1024
	// 1366 X 768
	// 1440 X 900
	// 1600 X 900
	// 1920 X 1080
	//根据不同分辨率的高度来决定页面列表上一次显示多少条数据
	var clientHeight = document.body.clientHeight;

//	funcList.resetTableView();
	if(!$(".table-responsive")){
		return;
	}
	// 获取窗口高度
//	if (window.innerHeight)
//	winHeight = window.innerHeight;
//	else if ((document.body) && (document.body.clientHeight))
//	winHeight = document.body.clientHeight;
	
	var t_body_h = clientHeight - 90;
	if($(".half-noflex")){
		t_body_h = t_body_h - $(".half-noflex").height();
	}
	var tr_h = ($(".table-condensed"))?31:35.56
	pageSize = Math.max((Math.floor(t_body_h/tr_h)-1),5) ;
	$("#pageSize").append('<option value="' + pageSize + '">' + pageSize + '</option>');
	$("#pageSize").val(pageSize);	
	$(".table-responsive").css({ "height": t_body_h+"px"});
	
//	if(typeof funcList.onload == 'function') funcList.onload.call();
	
}



