/**
 * @author fang
 * @version 2017-06-25
 */
var lastClickRow = null;
var modelList = null;
var pageRowBoxCount = 0;//当前页共有多少行数据
var currentRowSelectedBoxCount = 0;//当前选中有哪些数据

funcList.onload = function() {
	funcList.funcControl();
	funcList.loadData();
    
};
funcList.loadData = function(params){
	params = params||{};
	fangjs.execjava('workflow/model/findModelAll', params, funcList.showData);
};

funcList.showData = function(data) {
    lastClickRow = null;
    fangjs.cleanData('modelList', 0);
    
    if(!data) return;
    var cols = [ "id", "key", "name", "version", "createTime","lastUpdateTime","metaInfo"];
        
    modelList = data.rows;

    var start = function(td, tr, row) {
    	td.innerHTML = (page - 1) * pageSize + row + 1;
		tr.setAtt({
			click : 'trClick',
//			dblclick : 'trDblClick',
			row : row
		});
    };
    var end = function(td, tr, row){
    	td.innerHTML = '导出(<a href="' + AJAX_ROOT + '/workflow/model/export/' + this.id + '/bpmn" target="_blank">BPMN</a>|&nbsp;' + 
    		'<a href="' + AJAX_ROOT + '/workflow/model/export/' + this.id + '/json" target="_blank">JSON</a>|&nbsp;' ;
    };
    var callback = {
    	createTime : function(td,tr,row){
    		td.innerHTML =  new Date(this.createTime).format('yyyy-MM-dd');
    	},
    	lastUpdateTime : function(td,tr,row){
    		td.innerHTML =  new Date(this.lastUpdateTime).format('yyyy-MM-dd');
    	}
    };
    fangjs.dataView('modelList', cols, modelList, 'no', start,end,callback);

};

function showSvgTip() {
	alert('点击"编辑"链接,在打开的页面中打开控制台执行\njQuery(".ORYX_Editor *").filter("svg")\n即可看到svg标签的内容.');
}

/**
* 行点击事件
* */
funcList.trClick = function() {
    var trs = this.getParentByTag('table').getElementsByTagName('tr');
    for ( var i = 0, len = trs.length; i < len; i++) trs[i].removeClass('click-tr');
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
	funcList.showModelPage(params);
};

/**
 * param.type   view  查看   edit编辑
 * */
funcList.showModelPage = function(param) {    
    fangjs.openDialog('workflow/modelAdd.html' ,"新增流程", "800px","340px",funcList.loadData);
};

/**
 * deploy部署
 */
funcList.deploy = function(){
	if (lastClickRow == null) {
		fangjs.alert("请选择需要部署的模型！");
		return;
	}

	var callback = function(data) {
		if (data.code == '1') {
			funcList.loadData();
			fangjs.alert("部署成功！");
		} else {
			fangjs.alert("部署失败！请检查数据。错误信息：\n" + data.message);
		}
	};

	var params = {
		'id' : modelList[lastClickRow].id
	};
	fangjs.execjava('workflow/model/deploy' , params, callback,false);
};

/**
 * 修改模型
 */
funcList.modify = function(param){
	if(lastClickRow == null) {
		fangjs.alert("请选择要操作的数据!");
		return;
	}
	window.open('../modeler.html?modelId=' + modelList[lastClickRow].id , "流程设计");
}

/**
* 删除信息  单个删除或者批量删除
* */
funcList.delModel = function(params) {
  	if(lastClickRow == null) {
            fangjs.alert("请选择需要删除的数据！");
            return;
     }
  
    var callback = function(data) {
		if(data.result == '1'){
			funcList.loadData();
			fangjs.alert("删除成功！");
		}else{
			fangjs.alert("操作失败！请检查数据。");
		}
	};
	
    var params = {'id' : modelList[lastClickRow].id};
    var doDel = function(){
		fangjs.execjava('../workflow/model/delete' , params, callback,false);
	};
    fangjs.confirm("确定要删除此数据吗？", doDel)
    lastClickRow= null;
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
    var params={keywords:keywords};
    funcList.loadData(params);
};


