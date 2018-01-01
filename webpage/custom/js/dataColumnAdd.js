/**
* @author fang
* @version 2017-12-26 10:51:17
* 
**/
var currentData = null;

funcList.onload = function() {

	var act = fangjs.getParamFromURl("act");
	currentData = fangjs.getSessionStorage("dataColumnInfo");
	if(currentData){
		fangjs.showEntity(currentData, ['input', 'select', 'textarea']);
		fangjs.deleteSessionStorage("dataColumnInfo");
		
	}
    if ('read' == act) {
        $("select,input,textarea").attr("disabled", true);
        $("input").removeAttr("onclick");
        $(":button").remove();
    }

};

/**
* 保存信息
* */
funcList.saveDataColumn = function() {
    if (!isCheck()) {
    fangjs.alert('信息不完整或格式不正确，请正确填写所有信息！');
        return;
    }
    var params = {};
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
    
	var url = $("#id").val()?"updateDataColumn":"addDataColumn";
    var callback = function(data) {
		if(data.code == '1'){
			fangjs.alert("操作成功！");
			fangjs.closeDialog();
		}else{
			fangjs.alert(data.message?data.message:"操作失败！请检查数据。");
		}
	};
    fangjs.execjava('/custom/dataColumn/' + url, params, callback, false);
};


funcList.formTypeChange = function(){
    if($("#formType").val() == 'select'){
        $(".optionsDiv").removeClass("hidden");
    }else {
        $(".optionsDiv").addClass("hidden");
    }

}

funcList.zhNameChange = function(params){
    var str = pinyin.getFullChars(this.value);
    str = str.substring(0,1).toLowerCase() + str.substring(1);
    $("#enName").val(str);
};