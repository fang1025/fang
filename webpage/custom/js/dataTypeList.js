/**
* @author fang
* @version 2017-12-26 10:50:27
* 
**/
var lastClickRow = null;
var dataTypeList = null;
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
	fangjs.execjava('custom/dataType/findDataTypeByPage', params, funcList.showData);
};

funcList.showData = function(data) {
    lastClickRow = null;
    fangjs.cleanData('dataTypeList', 0);
    
    if(!data) return;
    var cols = [ "code", "name", "tableName","excelName","createTime"];
        
    dataTypeList = data.rows;
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
        excelName : function(td,tr,row){
    	    var str = this.excelName?
        '<a href="' + filewebsite + 'templet/' + this.excelName  + '" target="_blank">' + this.name + '.' + this.suffix + '</a>'
        :
        '';
            td.innerHTML =  str;
        }
    };
    fangjs.dataView('dataTypeList', cols, dataTypeList, dataCount, start,null,callback);

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
	funcList.showDataTypePage(params);
};

/**
 * param.type   view  查看   edit编辑
 * */
funcList.showDataTypePage = function(param) {
    if(param.act == 'read' || param.act == 'edit') {
        if(lastClickRow == null) {
            fangjs.alert("请选择要操作的数据!");
            return;
        }
    	fangjs.setSessionStorage("dataTypeInfo",dataTypeList[lastClickRow]);
    }
    var condition = param.act ? "?act=" + param.act + "&row=" + escape(lastClickRow):"";
    fangjs.openDialog('custom/dataTypeAdd.html' + condition,"数据类型", "800px", 200 +  3 * 29 + "px",funcList.loadData);
};

/**
* 删除信息  单个删除或者批量删除
* */
funcList.delDataType = function(params) {
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
	
    var params = {'id' : dataTypeList[lastClickRow].id,'enable' : 1};
    var doDel = function(){
		fangjs.execjava('custom/dataType/updateDataTypeEnable', params, callback,false);
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


