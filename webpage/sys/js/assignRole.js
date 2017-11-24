var checkedRole = {};
var roleList = [];
var roleRow  = null;

funcList.onload = function() {
	funcList.loadData();
};

funcList.loadData = function() {
	var params = {};
   	params.userId = fangjs.getParamFromURl("uid");
	fangjs.execjava('sys/role/findForAssignRole', params, showData);
};

var showData = function(data) {
	if(!data || data.code != 1) return;
	fangjs.cleanData('datalist');
	var cols = [ 'roleName', 'roleCode', 'notes' ];
	roleList = data.rows;
	dataCount = data.total;
	var start = function(td, tr, row) {
		var classStr =this.existRole?"icon-checked" : "icon-check";
		td.innerHTML = "<i param='" + this.id + "' class='icon "+ classStr +"' ></i>";

	};

	fangjs.dataView('datalist', cols, roleList, 'no', start);
	addCheckBox(document.body);
};

funcList.determine = function(){
	var selectRows = jQuery("#datalist").find("i[class*='icon-checked']");
	var ids = "";
    for(var i=0;i<selectRows.length;i++){
    	ids += selectRows.eq(i).attr('param') + ",";
    }
    if(ids.length > 0){
    	ids = ids.substring(0,ids.length-1);
    }
	var params = {};
	params.userId = fangjs.getParamFromURl("uid");
	params.ids = ids;
	fangjs.execjava('sys/role/assignRole', params, function(data){
		if(data.code != '1'){
			fangjs.alert('操作失败！');
			return;
		}
		fangjs.alert("操作成功！");
		fangjs.closeDialog();
	});
};
