var checkedRole = {};
var roleList = [];
var roleRow  = null;
var selectType = "user";

//funcList.onload = function() {
//	funcList.loadData();
//};

jQuery(function(){
	initLayer();
	closeDialog();
});
function initLayer (){
	 layer.open({
         type   : 1,
         closeBtn : 0,
         area   : ['650px', '440px'],
         content: '<table class="table "> <thead> <tr> <th id="name_th">角色名称</th> <th id="code_th">角色编号</th> </tr> </thead> <tbody id="datalist"> </tbody> </table> '  
     });
	// jQuery(".layui-layer-setwin").after('<div class="">' + pageHtml + '</div>');
     jQuery(".layui-layer-setwin").after('<div class="layui-layer-btn layui-layer-btn-">' + pageHtml + '<a click="determine">选择</a></div>');
     jQuery(".layui-layer-setwin").html('<a class="layui-layer-ico layui-layer-close layui-layer-close1" href="javascript:;" onclick="closeDialog();"></a>');
     jQuery(".layui-layer").css("height","515px" );
};

function showDialog(){
	jQuery('.layui-layer-shade').css('display','block'); 
	jQuery('.layui-layer').css('display','block'); 
};

function closeDialog(){
	jQuery('.layui-layer-shade').css('display','none'); 
	jQuery('.layui-layer').css('display','none'); 
};

//选择角色
function selectRole(){
	selectType = "role";
	jQuery("#name_th").html("角色名称");
	jQuery("#code_th").html("角色编号");
	funcList.loadData();
	showDialog();
};

//选择用户
function selectUser(){
	selectType = "user"
	jQuery("#name_th").html("姓名");
	jQuery("#code_th").html("登陆名");
	funcList.loadData();
	showDialog()
};

funcList.loadData = function(){
	if(selectType == "user"){
		funcList.loadData_user();
	}else{
		funcList.loadData_role();
	}
};

funcList.loadData_role = function() {
	var params = {};
	fangjs.execjava(AJAX_ROOT + 'sys/findRoleByParam', params, showData);
};

funcList.loadData_user = function() {
	var params = {};
	if (!params.page) params.page = page;
    if (!params.rows) params.rows = pageSize;
//    var keywords=jQuery("#keywords").val().trim();
//    if(keywords){
//    	params.keywords = keywords;
//    }
	fangjs.execjava(AJAX_ROOT + 'sys/findUserByPage', params, showData_user);
};

var showData = function(data) {
	if(!data || !data.ldata) return;
	fangjs.cleanData('datalist');
	var cols = [ 'role_name', 'role_code' ];
	roleList = data.ldata;
	dataCount = data.totalCount;

	var callback = {
			role_code : function(td, tr, row) {
			tr.setAtt({
				click : 'trClick', dblclick : 'trDblClick', row : row
			});
			td.innerHTML = this.role_code;
		}
		
	};
	fangjs.dataView('datalist', cols, roleList, 'no', null,null, callback);
};

var showData_user = function(data) {
	if(!data || !data.rows) return;
	fangjs.cleanData('datalist');
	var cols = [ 'user_name', 'login_name' ];
	roleList = data.rows;
	dataCount = data.total;

	var callback = {
			user_name : function(td, tr, row) {
			tr.setAtt({
				click : 'trClick', dblclick : 'trDblClick', row : row
			});
			td.innerHTML = this.user_name;
		}
		
	};
	fangjs.dataView('datalist', cols, roleList, dataCount, null,null, callback);
};


funcList.trClick = function() {
	var trs = this.getParentByTag('table').getElementsByTagName('tr');
	for ( var i = 0, len = trs.length; i < len; i++) trs[i].removeClass('click-tr');
	var tr = this.tagName.toLowerCase() == 'tr' ? this : this.getParentByTag('tr');
	tr.addClass('click-tr');
	roleRow = tr.getAttribute('row');
	roleRow = Number(roleRow);
	checkedRow = roleList[roleRow];
	
};
funcList.determine = function(){
	if(selectType == "user"){
		jQuery("#assigneeField").val(checkedRow.user_name);
	}else{
		jQuery("#groupField").val(checkedRow.role_code);
	}
	closeDialog();
};



var pageHtml = '<ul id="pageNumber" style="margin:0" class="pagination pull-left"></ul>';
