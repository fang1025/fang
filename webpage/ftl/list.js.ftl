<#include "comment.ftl">
var lastClickRow = null;
var ${entityName?if_exists }List = null;
var pageRowBoxCount = 0;//当前页共有多少行数据
var currentRowSelectedBoxCount = 0;//当前选中有哪些数据

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
	fangjs.execjava('${packageName?if_exists}/${entityName?if_exists }/find${upperEntityName?if_exists }ByPage', params, funcList.showData);
};

funcList.showData = function(data) {
    lastClickRow = null;
    fangjs.cleanData('${entityName?if_exists }List', 0);
    
    if(!data) return;
    var cols = [<#list fieldInfos as info><#if info.fieldTitle??> "${info.fieldName}",</#if></#list>"createTime"];
        
    ${entityName?if_exists }List = data.rows;
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
    	}
    };
    fangjs.dataView('${entityName?if_exists }List', cols, ${entityName?if_exists }List, dataCount, start,null,callback);

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
	funcList.show${upperEntityName?if_exists }Page(params);
};

/**
 * param.type   view  查看   edit编辑
 * */
funcList.show${upperEntityName?if_exists }Page = function(param) {
    if(param.act == 'read' || param.act == 'edit') {
        if(lastClickRow == null) {
            fangjs.alert("请选择要操作的数据!");
            return;
        }
    	fangjs.setSessionStorage("${entityName?if_exists }Info",${entityName?if_exists }List[lastClickRow]);
    }
    var condition = param.act ? "?act=" + param.act + "&row=" + escape(lastClickRow):"";
    fangjs.openDialog('${packageName?if_exists}/${entityName?if_exists }Add.html' + condition,"${moduleName?if_exists }", "800px", 200 +  ${fieldInfos?size} * 29 + "px",funcList.loadData);
};

/**
* 删除信息  单个删除或者批量删除
* */
funcList.del${upperEntityName?if_exists } = function(params) {
  	if(lastClickRow == null) {
            fangjs.alert("请选择需要删除的数据！");
            return;
     }
  
    var callback = function(data) {
		if(data.code == '1'){
			funcList.loadData();
			fangjs.alert("删除成功！");
		}else{
			fangjs.alert(data.message?data.message:"操作失败！请检查数据。");
		}
		lastClickRow= null;
	};
	
    var params = {'id' : ${entityName?if_exists }List[lastClickRow].${idName},'enable' : 1};
    var doDel = function(){
		fangjs.execjava('${packageName?if_exists}/${entityName?if_exists }/update${upperEntityName?if_exists }Enable', params, callback,false);
	};
    fangjs.confirm("确定要删除此数据吗？", doDel)
    
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


