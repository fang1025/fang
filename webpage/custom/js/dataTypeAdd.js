/**
* @author fang
* @version 2017-12-26 10:50:27
* 
**/
var currentData = null;

var fileInfo = {};
funcList.onload = function() {

	var act = fangjs.getParamFromURl("act");
	if(act && 'add' != act){
		currentData = fangjs.getSessionStorage("dataTypeInfo");
		fangjs.showEntity(currentData, ['input', 'select', 'textarea']);
		fangjs.deleteSessionStorage("dataTypeInfo");
		
		if( 'read' == act){
			$("select,input,textarea").attr("disabled",true);
			$("input").removeAttr("onclick");
			$(":button").remove();
		}
	}

    $("#file").fileinput({
        uploadUrl: "/" + COMPONENT_APP + "/upload/onlyUploadSingle",
        maxFileCount: 1,
        // showBrowse:false,//显示选择按钮
        // showCaption:false,//显示文件名称（在底部）
        showRemove:false,//显示移除按钮
        showUpload:false,//显示上传按钮
        showCancel:false,//显示取消按钮
        // browseOnZoneClick: true,//
        autoReplace:true,//重新选择，替换当前文件
        showPreview: false,
        uploadExtraData:{},
        // previewClass:"hidden",
        allowedFileExtensions:["xls", "xlsx"],
        showClose:false,
        fileActionSettings:{showZoom:false},//预览详情
        language: 'zh'
    }).on("fileuploaded", function (event, data, previewId, index) {
        console.log(data);
        var result = data.response;
        if(result && result.code == 1){

            fileInfo.excelName = result.fileName;
            fileInfo.suffix = result.suffix;

            $(".input-group-btn").prepend('<a class="btn btn-default btn-secondary ">' +
                '<i type="checkbox" class="icon icon-checked" click="checkMe"></i>  ' +
                '<span class="hidden-xs">自动生成字段</span></a>')
        }

    }).on("fileerror", function (event, data, previewId, index) {
        console.log(data);
        alert(data);
    }).on("filebatchselected", function(event, files) {
        $("#file").fileinput("upload");
        $(".kv-upload-progress").css("display","none");
    });

};

/**
* 保存信息
* */
funcList.saveDataType = function() {
    if (!isCheck()) {
    fangjs.alert('信息不完整或格式不正确，请正确填写所有信息！');
        return;
    }
    var params = fileInfo || {};
    var inputs = $s('input'), input;
    for ( var i = 0, len = inputs.length; i < len; i++) {
        input = inputs[i];
        if(input.id) fangjs.setEntity(params, input.id, input.value.trim());
    }
    inputs=$s('textarea');
	for( var i = 0, len = inputs.length; i < len;i++){
		input = inputs[i];
		if(input.id)	fangjs.setEntity(params, input.id, input.value.trim());
	}
    var selects = $s('select'), select;
    for ( var i = 0, len = selects.length; i < len; i++) {
        select = selects[i];
        if(select.id) fangjs.setEntity(params, select.id, select.value);
    }
    if($('.icon-checked')){
        params.isExeclToTable = 1;
    }
	var url = $("#id").val()?"updateDataType":"addDataType";
    var callback = function(data) {
		if(data.code == '1'){
			fangjs.alert("操作成功！");
			fangjs.closeDialog();
		}else{
			fangjs.alert(data.message?data.message:"操作失败！请检查数据。");
		}
	};
    fangjs.execjava('/custom/dataType/' + url, params, callback, false);
};

