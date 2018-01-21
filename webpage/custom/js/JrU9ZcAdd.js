/**
 *创建时间:2016-09-26 15:57:24
 *作者 :fangjiebing
 *修订记录
 *功能说明：用freemarker创建,生成对应的文件,减少代码编写量
 *
 **/
var currentData = null;
var dataType = null;
funcList.onload = function () {
    dataType = fangjs.getSessionStorage("dataType");

    var boxhtml = funcList.getFormHtml(dataType.dataColumnList);
    if (boxhtml) {
        $(".box-body").html(boxhtml);
        var params = [];
        jQuery("[dictType]").each(function () {
            params.push(this.getAttribute("dictType"));
        });
        if(params.length > 0) fangjs.loadDict(params);
    }

    var act = fangjs.getParamFromURl("act");
    if (act && 'add' != act) {
        currentData = fangjs.getSessionStorage("dataInfo");
        fangjs.showEntity(currentData, ['input', 'select', 'textarea']);
        fangjs.deleteSessionStorage("dataInfo");

        if ('read' == act) {
            $("select,input,textarea").attr("disabled", true);
            $("input").removeAttr("onclick");
            $(":button").remove();
        }
    }

};

/**
 * 保存信息
 * */
funcList.save = function () {
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

    var url = $("#id").val() ? "update" : "insert";
    var callback = function (data) {
        if (data.code == '1') {
            fangjs.alert("操作成功！");
            fangjs.closeDialog();
        } else {
            fangjs.alert(data.message ? data.message : "操作失败！请检查数据。");
        }
    };
    params.tableName = dataType.tableName;
    params.code = dataType.code;
    fangjs.execjava('custom/commData/' + url, params, callback, false);
};


funcList.getFormHtml = function (dataColumnList) {
    var str = '<input type="hidden" id="id" class="m-text">', colCount = 0;
    for (var i = 0; i < dataColumnList.length; i++) {
        var item = dataColumnList[i];

        if (item.isEdit == 2) {
            str += '<input type="hidden" id="' + item.enName + '" class="m-text" >';
        } else if (item.isEdit == 1) {
            var tempStr = '';
            if (item.verifytype && item.verifytype.trim().length > 0) {
                tempStr += ' verifytype="' + item.verifytype + '"';
            }
            if (item.placeholder && item.placeholder.trim().length > 0) {
                tempStr += ' placeholder="' + item.placeholder + '"';
            }
            if (item.verifyUrl && item.verifyUrl.trim().length > 0) {
                tempStr += ' verifyUrl="' + item.verifyUrl + '"';
            }
            if (item.minlen) {
                tempStr += ' minlen="' + item.minlen + '"';
            }
            if (item.maxlen) {
                tempStr += ' maxlen="' + item.maxlen + '"';
            }
            str += '<div class="form-group col-sm-' + item.colLen + '">\n' +
                '<label>' + item.zhName + '</label>\n';
            if (item.dataType == "datetime") {
                str += '<input type="text" class="form-control Wdate" id="' + item.enName + '" ' + tempStr + ' click="showDate" ></div>'
            } else if (item.formType == "input") {
                str += '<input type="text" class="form-control" id="' + item.enName + '" ' + tempStr + ' /></div>';
            } else if (item.formType == "select") {
                str += '<select class="form-control" ' + (item.dictType ? 'dictType="' + item.dictType + '"' : '') + '  id="' + item.enName + '" ' + tempStr + '>';
                if (item.isNullable != 1 && item.isNullable != "1") {
                    str += '<option value="">请选择</option>';
                }
                if (item.options) {
                    var options = item.options.split(",");
                    for (var j = 0; j < options.length; j++) {
                        str += '<option value="' + options[j] + '">' + options[j] + '</option>';
                    }
                }
                if (item.dictType) {
                    dictType = "' +  item.dictType+ '"
                }

                str += '</select></div>';
            } else if (item.formType == "textarea") {
                str += '<textarea class="form-control" rows="3" id="' + item.enName + '" ' + tempStr + ' /></textarea></div>';
            }
            str += '</div>';
        }
    }
    return str;
}




