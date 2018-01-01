/**
* @author fang
* @version 2017-12-26 10:51:17
* 
**/
var lastClickRow = null;
var dataColumnList = null;
var pageRowBoxCount = 0;//当前页共有多少行数据
var currentRowSelectedBoxCount = 0;//当前选中有哪些数据

var verifytypeMap = {'no':'只验证字段长度','noNull':'不能为空','number':'数字','positive':'正数','nature':'自然数（包含0的正数）','allInteger':'整数','positiveInt':'正整数','mail':'邮箱','mobile':'手机号码','phone':'固话','phoneORmobile':'手机及固话','idCard':'身份证号码','numberAndEnglish':'英文数字组合'};

funcList.onload = function() {
	funcList.funcControl();
	funcList.loadTree();
    
};

funcList.loadTree = function(){
    fangjs.execjava('custom/dataType/findDataTypeByParam', {}, function(data){
        funcList.showTree(data.rows)
    });
};

funcList.showTree = function(data){
    var htmlStr = "";
    for (var i = 0; i < data.length; i++) {
        htmlStr += '<li><a href="javascript:" click="checkTree" params="dataTypeId='
            + data[i].id + ',code=' + data[i].code + '"><span >'
            + data[i].name + '</span></a></li>';
    }
    $("#tree").html(htmlStr);

    $($("#tree")[0]).find("a")[0].click();
};

funcList.checkTree = function(params) {
    $('.menu li a').removeClass('active');
    $(this).addClass('active');
    selectParams = params;
    funcList.loadData(params);
};

funcList.loadCheckedData = function(){
    $('.lia li .active').click();
};

funcList.loadData = function(params){
	params = params||{};
	if (!params.page) params.page = page;
    if (!params.pageSize) params.pageSize = pageSize;
    var keywords=jQuery("#keywords").val().trim();
    if(keywords){
    	params.keywords = keywords;
    }
	fangjs.execjava('custom/dataColumn/findDataColumnByPage', params, funcList.showData);
};

funcList.showData = function(data) {
    lastClickRow = null;
    fangjs.cleanData('dataColumnList', 0);
    
    if(!data) return;
    var cols = [ "enName", "zhName", /*"isDefault", "isSort", "callbackFun", "sortNo", "isEdit",*/
        "formType",/* "colLen", */"verifytype",/* "verifyUrl", "minlen", "maxlen", "placeholder",*/
        "dataType", "isNullable"/*, "defaultValue", "indexType","createTime"*/];
        
    dataColumnList = data.rows;
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
        verifytype : function (td,tr,row) {
            td.innerHTML =  verifytypeMap[this.verifytype]?verifytypeMap[this.verifytype]:'';
        },
        isNullable : function (td,tr,row) {
            td.innerHTML =  (this.isNullable == 1?"不能为空":"可以为空");
        }
    };
    fangjs.dataView('dataColumnList', cols, dataColumnList, dataCount, start,null,callback);

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
	funcList.showDataColumnPage(params);
};

/**
 * param.type   view  查看   edit编辑
 * */
funcList.showDataColumnPage = function(param) {
    if(param.act == 'read' || param.act == 'edit') {
        if(lastClickRow == null) {
            fangjs.alert("请选择要操作的数据!");
            return;
        }
    	fangjs.setSessionStorage("dataColumnInfo",dataColumnList[lastClickRow]);
    }else if(selectParams){
        fangjs.setSessionStorage("dataColumnInfo",selectParams);
    }
    var condition = param.act ? "?act=" + param.act + "&row=" + escape(lastClickRow):"";
    fangjs.openDialog('custom/dataColumnAdd.html' + condition,"数据字段", "800px", 200 +  21 * 29 + "px",funcList.loadData);
};

/**
* 删除信息  单个删除或者批量删除
* */
funcList.delDataColumn = function(params) {
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
	
    var params = {'id' : dataColumnList[lastClickRow].id,'enable' : 1};
    var doDel = function(){
		fangjs.execjava('custom/dataColumn/updateDataColumnEnable', params, callback,false);
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


