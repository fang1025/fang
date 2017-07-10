/**
 * @author fang	
 */
var currentData = null;



/**
* 保存信息
* */
funcList.save = function() {
    if (!isCheck()) {
    	fangjs.alert('信息不完整或格式不正确，请正确填写所有信息！');
        return;
    }
    $('#modelForm').attr("action",AJAX_ROOT + "/workflow/model/create");
    $('#modelForm').submit();
    setTimeout(function() {
        fangjs.closeDialog();
    }, 1000);
};

