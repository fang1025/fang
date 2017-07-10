/**
* @version 2017-06-12 16:19:34
* @author fang
*
**/
var lastClickRow = null;
var dictList = null;
var pageRowBoxCount = 0;//当前页共有多少行数据
var currentRowSelectedBoxCount = 0;//当前选中有哪些数据
var selectParams;

funcList.onload = function() {
	funcList.funcControl();
	funcList.loadData();
    
};
funcList.loadData = function(params){
	params = params||{};
	if (!params.page) params.page = page;
    if (!params.pageSize) params.pageSize = pageSize;
    var keywords=jQuery("#keywords").val().trim();
    if(keywords){
    	params.keywords = keywords;
    }
    fangjs.execjava('sys/dict/findDictionaryByCode', params, funcList.showData);
};

funcList.showData = function(data) {
	if(data&&data.codeList) funcList.showTree(data.codeList);
    lastClickRow = null;
    fangjs.cleanData('dictList', 0);
    
    if(!data) return;
    var cols = [ "dictCode", "dictName", "dictValue", "dictText", "enable","createTime"];
        
    dictList = data.rows;
    var dataCount = data.total;

    var start = function(td, tr, row) {
    	td.innerHTML = (page - 1) * pageSize + row + 1;
		tr.setAtt({
			click : 'trClick',
			dblclick : 'trDblClick',
			row : row
		});
    };
    var callback = {
    	createTime : function(td,tr,row){
    		td.innerHTML =  new Date(this.createTime).format('yyyy-MM-dd');
    	},
    	enable : function(td,tr,row){
    		td.innerHTML =  this.enable == 1?"禁用" : "启用";
    	}
    };
    fangjs.dataView('dictList', cols, dictList, dataCount, start,null,callback);
};

funcList.showTree = function(data){
	var htmlStr = "";
	for (var i = 0; i < data.length; i++) {
		htmlStr += '<li><a href="javascript:" click="checkTree" params="dictCode=' 
			+ data[i].dictCode + ',dictName=' + data[i].dictName + '" ' + (i==0?'class="active"':'') + ' ><span  >' 
			+ data[i].dictName + '</span></a></li>';
	}
	$("#tree").html(htmlStr);
};

funcList.checkTree = function(params) {
	if(params){
		$('.menu li a').removeClass('active');
		$(this).addClass('active');
		selectParams = params;
	}
	funcList.loadData(selectParams);
};

/**
* 行点击事件
* */
funcList.trClick = function() {
    var trs = this.getParentByTag('table').getElementsByTagName('tr');
    for ( var i = 0, len = trs.length; i < len; i++) trs[i].removeClass('click');
    var tr = this.tagName.toLowerCase() == 'tr' ? this : this.getParentByTag('tr');
    tr.addClass('click-tr');
    lastClickRow = tr.getAttribute('row');
    
};

funcList.trDblClick = function() {
	if (!lastClickRow) {
		fangjs.alert('请选择需要查看的数据！');
		return;
	}
	var params={act:'read'};
	funcList.showDictPage(params);
};

/**
 * param.type   view  查看   edit编辑
 * */
funcList.showDictPage = function(param) {
    var extraHeight = 5 * 20;
    if(param.act == 'read' || param.act == 'edit') {
        if(lastClickRow == null) {
            fangjs.alert("请选择要操作的数据!");
            return;
        }
    	fangjs.setSessionStorage("dictInfo",dictList[lastClickRow]);
    } else if(param.act == 'add' && selectParams){
    	fangjs.setSessionStorage("dictInfo",selectParams);
    }
    var condition = param.act ? "?act=" + param.act + "&row=" + escape(lastClickRow):"";
    fangjs.openDialog('sys/dictAdd.html' + condition,"字典", "800px", 200 + extraHeight + "px",funcList.checkTree);
};

/**
* 删除信息  单个删除或者批量删除
* */
funcList.delDict = function(params) {
  	if(lastClickRow == null) {
            fangjs.alert("请选择需要操作的数据！");
            return;
     }
  	var msg = dictList[lastClickRow].enable == 1? "启用":"禁用";
  
    var callback = function(data) {
		if(data.code == '1'){
			funcList.loadData(selectParams);
			fangjs.alert(msg + "成功！");
		}else{
			fangjs.alert(data.message?data.message:"操作失败！请检查数据。");
		}
		lastClickRow= null;
	};
	
    var params = {'id' : dictList[lastClickRow].id};
    var doDel = function(){
		fangjs.execjava('sys/dict/updateDictEnable', params, callback,false);
	};
    fangjs.confirm("确定要" + msg + "此数据吗？", doDel)
    
};

var lastkeywords;
/**
* 快捷键搜索功能
*/
funcList.search = function(){
    var keywords=jQuery("#keywords").val().trim();
    if(keywords == lastkeywords){
    	return;
    }
	lastkeywords = keywords;
    page = 1;
    funcList.loadData();
};


