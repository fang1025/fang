/**
* @version 2017-06-18 18:17:34
* @author fang
*
**/
var lastClickRow = null;
var employeeList = null;
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
	fangjs.execjava('sys/employee/findEmployeeByPage', params, funcList.showData);
};

funcList.showData = function(data) {
    lastClickRow = null;
    fangjs.cleanData('employeeList', 0);
    
    if(!data) return;
    var cols = [ "name", "loginName", "email", "mobile", "identityCard", "sexType", "birthDate", "entryDate", "leaveDate", "workStatus","createTime"];
        
    employeeList = data.rows;
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
    fangjs.dataView('employeeList', cols, employeeList, dataCount, start,null,callback);

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
	funcList.showEmployeePage(params);
};

/**
 * param.type   view  查看   edit编辑
 * */
funcList.showEmployeePage = function(param) {
    var extraHeight = 6 * 29;
    if(param.act == 'read' || param.act == 'edit') {
        if(lastClickRow == null) {
            fangjs.alert("请选择要操作的数据!");
            return;
        }
    	fangjs.setSessionStorage("employeeInfo",employeeList[lastClickRow]);
    }
    var condition = param.act ? "?act=" + param.act + "&row=" + escape(lastClickRow):"";
    fangjs.openDialog('sys/employeeAdd.html' + condition,"员工", "800px", 200 + extraHeight + "px",funcList.loadData);
};

/**
* 删除信息  单个删除或者批量删除
* */
funcList.delEmployee = function(params) {
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
	
    var params = {'id' : employeeList[lastClickRow].id,'enable' : 1};
    var doDel = function(){
		fangjs.execjava('sys/employee/updateEmployeeEnable', params, callback,false);
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

//分配角色
funcList.assignRole = function(){
	if(!lastClickRow){
        fangjs.alert('请选择需要分配角色的用户！');
        return;
    } 
	fangjs.openDialog("sys/assignRole.html?uid=" + employeeList[lastClickRow].id ,'分配角色',"650px","550px");
};

