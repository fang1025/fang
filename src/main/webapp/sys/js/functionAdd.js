
funcList.onload = function() {
	var act = fangjs.getParamFromURl("act");
	var parentName = fangjs.toString(unescape(fangjs.getParamFromURl('parentName')));
	if (act == "update") {
		var id = fangjs.getParamFromURl("id");
		var params = {};
		params.id = id;
		fangjs.execjava('sys/function/findFunctionByParam', params, function(data) {
			fangjs.showEntity(data.rows[0], [ 'input', 'select' ]);
		});
	}
	$$('parentName').value = parentName;
	var viewStatus = fangjs.getParamFromURl('view');
	if(viewStatus)
	{
		$$('confirm').style.display="none";
		$("select input").attr("disabled",true);
		$("input").removeAttr("click");
	}

};


/* 
 * 保存方法
 */

funcList.save = function(){
	if(!isCheck()){
		fangjs.alert("未填写完成或内容不符合规范");
		return;
	}
	var params={};
	var inputs=$s('input'),input;
	
	for( var i = 0, len = inputs.length; i < len;i++){
		input = inputs[i];
		if(input.id)	fangjs.setEntity(params, input.id, input.value.trim());
	}
	inputs=$s('textarea');
	for( var i = 0, len = inputs.length; i < len;i++){
		input = inputs[i];
		if(input.id)	fangjs.setEntity(params, input.id, input.value.trim());
	}
	
	var selects = $s('select'),select;
	for(var i = 0,len = selects.length;i<len;i++){
		select = selects[i];
		if(select.id)	fangjs.setEntity(params, select.id,select.value);
	}
	var callback = function(data) {
		if(data.code == '1'){
			fangjs.closeDialog();
		}else{
			fangjs.alert("操作失败！错误信息："  +  data.message);
		}
		
	};
	if (params.id != "" && params.id != null) {
		fangjs.execjava('sys/function/updateFunction', params, callback,false);
	}else{
		var lft = fangjs.getParamFromURl('lft');
		params.lft = lft;
		params.rgt = lft - 0 + 1;
		fangjs.execjava('sys/function/addFunction' , params, callback,false);
	}
	
}