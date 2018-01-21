<#include "comment.ftl">
var currentData = null;

funcList.onload = function() {

	var act = fangjs.getParamFromURl("act");
	if(act && 'add' != act){
		currentData = fangjs.getSessionStorage("${entityName?if_exists }Info");
		fangjs.showEntity(currentData, ['input', 'select', 'textarea']);
		fangjs.deleteSessionStorage("${entityName?if_exists }Info");
		
		if( 'read' == act){
			$("select,input,textarea").attr("disabled",true);
			$("input").removeAttr("onclick");
			$(":button").remove();
		}
	}

};

/**
* 保存信息
* */
funcList.save${upperEntityName?if_exists } = function() {
    if (!isCheck()) {
    fangjs.alert('信息不完整或格式不正确，请正确填写所有信息！');
        return;
    }
    var params = {};
	var inputs = $("input,textarea,select"), input;
	for (var i = 0, len = inputs.length; i < len; i++) {
		input = inputs[i];
		if (input.id) fangjs.setEntity(params, input.id, input.value.trim());
	}
	var url = $("#${idName}").val()?"update${upperEntityName?if_exists }":"add${upperEntityName?if_exists }";
    var callback = function(data) {
		if(data.code == '1'){
			fangjs.alert("操作成功！");
			fangjs.closeDialog();
		}else{
			fangjs.alert(data.message?data.message:"操作失败！请检查数据。");
		}
	};
    fangjs.execjava('/${packageName?if_exists}/${entityName?if_exists }/' + url, params, callback, false);
};

