/**
* @version 2017-06-18 18:17:34
* @author fang
*
**/
var currentData = null;

funcList.onload = function() {

	//加载字典数据
    fangjs.loadDict("gender", function(data) {
        fangjs.initDict(data);

        //加载数据
        var act = fangjs.getParamFromURl("act");
        if (act && 'add' != act) {
            currentData = fangjs.getSessionStorage("employeeInfo");
            fangjs.showEntity(currentData, ['input', 'select', 'textarea']);
            fangjs.deleteSessionStorage("employeeInfo");

            $("#password").parent().remove();

            if ('read' == act) {
                $("select,input,textarea").attr("disabled", true);
                $("input").removeAttr("onclick");
                $(".btn").remove();
            }
        }
    })


};

/**
* 保存信息
* */
funcList.saveEmployee = function() {
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
    
	var url = $("#id").val()?"updateEmployee":"addEmployee";
    var callback = function(data) {
		if(data.code == '1'){
			fangjs.alert("操作成功！");
			fangjs.closeDialog();
		}else{
			fangjs.alert(data.message?data.message:"操作失败！请检查数据。");
		}
	};
    fangjs.execjava('/sys/employee/' + url, params, callback, false);
};

