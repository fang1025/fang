/**
 * 
 */
var lastClickRow = null;
var processList = null;
var pageRowBoxCount = 0;//当前页共有多少行数据
var currentRowSelectedBoxCount = 0;//当前选中有哪些数据

funcList.onload = function() {
	funcList.loadData();
    
};
funcList.loadData = function(params){
	params = params||{};
	if (!params.page) params.page = page;
    if (!params.rows) params.rows = pageSize;
	fangjs.execjava('workflow/activiti/processList', params, funcList.showData,false);
};

funcList.showData = function(data) {
    lastClickRow = null;
    fangjs.cleanData('processList', 0);
    
    if(!data || data.total == 0) return;
    var cols = [ "id", "deploymentId", "name", "key", "version",
                 "resourceName","diagramResourceName","suspended"];
        
    processList = data.rows;


    var end = function(td, tr, row){
    	td.innerHTML = '<a href="' + AJAX_ROOT + 'workflow/process/delete?deploymentId=' + this.deploymentId + '">删除</a>';
    };
    var callback = {
    	suspended : function(td,tr,row){
    		if(this.suspended) {
    			td.innerHTML =  '<a href=' + AJAX_ROOT + '"/workflow/activiti/processdefinition/update/suspend/' + this.id + '">挂起</a>';
    		}else{
    			td.innerHTML =  '<a href=' + AJAX_ROOT + '"/workflow/activiti/processdefinition/update/active/' + this.id + '">激活</a>';
    		}
    	},
    	lastUpdateTime : function(td,tr,row){
    		td.innerHTML =  new Date(this.lastUpdateTime).format('yyyy-MM-dd');
    	},
    	resourceName : function(td,tr,row){
    		td.innerHTML =  '<a target="_blank" href="' + AJAX_ROOT + '/workflow/activiti/resource/read?processDefinitionId=' + this.id + '&resourceType=xml">' + this.resourceName + '</a>';
    	},
    	diagramResourceName : function(td,tr,row){
    		td.innerHTML =  '<a target="_blank" href="' + AJAX_ROOT + '/workflow/activiti/resource/read?processDefinitionId=' + this.id + '&resourceType=image">' + this.diagramResourceName + '</a>';
    	}
    	
    };
    fangjs.dataView('processList', cols, processList, data.total, null,end,callback);
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
	funcList.showProcessPage(params);
};

/**
 * param.type   view  查看   edit编辑
 * */
funcList.showProcessPage = function(param) {
    var extraHeight = 14 * 10;
    if(param.act == 'read' || param.act == 'edit') {
        if(lastClickRow == null) {
            fangjs.alert("请选择要操作的数据!");
            return;
        }
    }
    fangjs.setSessionStorage("processInfo",processList[lastClickRow]);
    var condition = param.act ? "?act=" + param.act + "&row=" + escape(lastClickRow):"";
    fangjs.openDialog('workflow/processAdd.jsp' + condition,"新增流程", "800px", 200 + extraHeight + "px",funcList.loadData);
};

/**
* 删除信息  单个删除或者批量删除
* */
funcList.delProcess = function(params) {
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
	
    var params = {'id' : processList[lastClickRow].id,'enable' : 1};
    var doDel = function(){
		fangjs.execjava('../base/updateProcessEnable/checkPrivilege', params, callback,false);
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
    funcList.resetTableView.call();
};


