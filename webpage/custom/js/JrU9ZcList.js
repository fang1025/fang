/**
*创建时间:2016-09-26 15:57:24
*作者 :fangjiebing
*修订记录
*功能说明：用freemarker创建,生成对应的文件,减少代码编写量
*
**/
var lastClickRow = null;
var dataList = null;
var pageRowBoxCount = 0;//当前页共有多少行数据
var currentRowSelectedBoxCount = 0;//当前选中有哪些数据
var code = '';
var dataType;

var dataViewcallback = {
    createTime : function(td,tr,row){
        td.innerHTML =  new Date(this.createTime).format('yyyy-MM-dd');
    }
};
	
funcList.onload = function() {
	code = fangjs.getParamFromURl("code");
	
	//加载显示列表
	var params = {"code":code,"ids":"trhead","cls":"tr_head","start":"<th>序号</th>"}
	fangjs.loadDataType(params);

    var dictP = [],colDictMap = {};
    jQuery("[ data-dictType]").each(function () {
        dictP.push(this.getAttribute("data-dictType"));
        colDictMap[this.getAttribute("data-dictType")] = this.getAttribute("colunmName");
    });
    if(dictP.length > 0) {
        fangjs.loadDict(dictP, function (dictMap) {
            for (var dictKey in colDictMap) {
                dataViewcallback[colDictMap[dictKey]] = function (td,tr,row) {
                    var values = dictMap[dictKey];
                        td.innerHTML =  values[this[colDictMap[dictKey]]];
                }
            }
        });
    }

	funcList.funcControl();
	funcList.loadData();
    // $("[data-toggle=dropdown]").dropdown()
};
funcList.loadData = function(params){
	params = params||{};
    if (!params.page) params.page = page;
    if (!params.pageSize) params.pageSize = pageSize;
    params.tableName = dataType.tableName;
//    var allCriteria = [];
//    var criteria = {"key":""};
	fangjs.execjava('custom/commData/findByPage', params, funcList.showData);
};

funcList.showData = function(data) {
    lastClickRow = null;
    fangjs.cleanData('dataList', 0);
    
    if(!data) return;
    
    dataList = data.rows;
    var dataCount = data.total;

    var start = function(td, tr, row) {
    	td.innerHTML = (page - 1) * pageSize + row + 1;
		tr.setAtt({
			click : 'trClick',
			dblclick : 'trDblClick',
			row : row
		});
    };

    fangjs.dataView('dataList', cols, dataList, dataCount, start,null,dataViewcallback);

};

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
	funcList.showPage(params);
};

/**
 * param.type   view  查看   edit编辑
 * */
funcList.showPage = function(param) {
    var extraHeight = 14 * 10;
    if(param.act == 'read' || param.act == 'edit') {
        if(lastClickRow == null) {
            fangjs.alert("请选择要操作的数据!");
            return;
        }
    }
    fangjs.setSessionStorage("dataInfo",dataList[lastClickRow]);
    fangjs.setSessionStorage("dataType",dataType)
    var condition = param.act ? "?act=" + param.act + "&row=" + escape(lastClickRow):"";
    fangjs.openDialog('custom/JrU9ZcAdd.html' + condition,dataType.name, "800px", 200 + extraHeight + "px",funcList.loadData);
};

/**
* 删除信息  单个删除或者批量删除
* */
funcList.delData = function(params) {
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
	};
	
    var params = {'id' : dataList[lastClickRow].id,'enable' : 1};
    var doDel = function(){
		fangjs.execjava('custom/commData/updateDeptEnable', params, callback,false);
	};
    fangjs.confirm("确定要删除此数据吗？", doDel)
    lastClickRow= null;
};

funcList.downloadExcel = function(params){
    params = params||{};
    params.tableName = dataType.tableName;
    params.code = dataType.code;
    params.path = "attachment/"
    params.pageSize = 10;
    fangjs.execjava('custom/commData/downloadExcel', params, function(data){
        console.log(data);
        var files = data.files;
        for(var i = 0; i < files.length; i++){
            funcList.download_file(files[i],i);
        }
    });
};

funcList.uploadExcel = function(params){
    params = params||{};
    params.tableName = dataType.tableName;
    params.code = dataType.code;

    layer.open({
        type: 1,
        area: ['326px', '428px'],
        shade: 0,
        content: '<div  style="margin: 10px" ><div class="file-loading" style="border: none"><input id="file" name="file" type="file"></div></div>' //这里content是一个普通的String
    });
    // fangjs.execjava('custom/commData/downloadExcel', params, function(data){
    //     console.log(data);
    // });
    $("#file").fileinput({
        uploadUrl: "/" + COMPONENT_APP + "/upload/onlyUploadSingle",
        maxFileCount: 1,
        // showBrowse:false,//显示选择按钮
        // showCaption:false,//显示文件名称（在底部）
        showRemove:false,//显示移除按钮
        // showUpload:false,//显示上传按钮
        showCancel:false,//显示取消按钮
        browseOnZoneClick: true,//
        autoReplace:true,//重新选择，替换当前文件
        // previewClass:"hidden",
        uploadExtraData:{"path":"attachment/"},
        allowedFileExtensions:['xls','xlsx'],
        showClose:false,
        // fileActionSettings:{showZoom:false},
        layoutTemplates:{"actionDelete":"","actionUpload":"","actionZoom":""},
        language: 'zh'
    }).on("fileuploaded", function (event, data, previewId, index) {
        console.log(data);
        var result = data.response;
        if(result && result.code == 1){
            var layer_i = layer.open({
                type: 1,
                closeBtn: 0, //不显示关闭按钮
                anim: 2,
                shade: 0.3,
                content: '<div style="padding:20px;">文件上传成功！<br>正在解析数据......</div>',
                title: false
            });
            var params = {};
            params.tableName = dataType.tableName;
            params.code = dataType.code;
            params.filename = 'attachment/' + result.fileName
            fangjs.execjava('custom/commData/readExcel', params, function (d) {
                funcList.loadData();
                layer.closeAll();
            },false);
        }else{
            layer.msg("上传失败，请重试一次！")
            $(".kv-file-remove").click();
            // $(".file-drop-zone-title").html("上传失败，请重试一次！");
        }

    });
    $('#file').on('fileerror', function(event, data, msg) {

        console.log(data);
        alert(data + msg);

    });
}


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


