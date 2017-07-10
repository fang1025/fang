
var roleId = null;
var roleName = null;
var funcIds = [];
var finalFuncIds = [];

funcList.onload = function() {
	roleId = fangjs.getParamFromURl("roleId");
	roleName = fangjs.getParamFromURl("roleName");
	var html = '正在为角色【' + decodeURIComponent(roleName) + '】分配权限';
    jQuery("#roleName").html(html);
    var params = {};
    params.roleId = roleId;
	var callback = function(data) {
		if (!data)
			return;
		var resultData = data.rows;
		var node = resultData[0];
		if(node.isfinal == "1"){
				finalFuncIds.push(node.id);
		}
		var root = new branch(node.functionName, 'checkTree', 'id=' + node.id
				+ ',lft=' + node.lft + ',rgt=' + node.rgt, true,true);
		root.rgt = node.rgt;
		if (node.rgt - node.lft > 1)
			addBranch(root, resultData, 1);
		$$('tree').innerHTML = root.html();
		funcList.setCheckbox(document.body);
	};
	var addBranch = function(parentNode, nodes, index) {
		var node;
		for ( var i = index, len = nodes.length; i < len; i++) {
			node = nodes[i];
			var isChecked = "";
			//如果为不可取消功能权限
			if(node.isfinal == "1"){
				finalFuncIds.push(node.id);
			}
			if(node.existFunc){
				isChecked = (node.rgt - node.lft > 1)? "no" : "yes";
			}else{
				isChecked = "no";
			}
			var leaf = new branch(node.functionName, 'checkTree', 'id='
					+ node.id + ',lft=' + node.lft + ',rgt=' + node.rgt, false,true,isChecked);
			leaf.rgt = node.rgt;
			if (node.rgt - node.lft > 1)
				i = addBranch(leaf, nodes, i + 1);
			parentNode.add(leaf);
			// if (parentNode.rgt - node.rgt == 1)
				// return i;
			if(i == len - 1 || parentNode.rgt - node.rgt == 1 || nodes[i+1].lft > parentNode.rgt){
				return i;
			}
		}
	};
	fangjs.execjava('sys/function/findFunctionByRoleForPrivilege', params, callback,false);

};


funcList.checkTree = function(params) {
	var cleanTree = function(obj) {
		var ele = $$(obj);
		var labels = ele.getElementsByTagName('label'), label;
		for ( var i = 0, len = labels.length; i < len; i++) {
			label = labels[i];
			if (label.className.indexOf('tree-lable') > -1)
				label.className = 'tree-lable';
		}
	};
	cleanTree('tree');
	this.addClass('tree-selected');
};

//确定
funcList.determine = function() {
	var selectRows = jQuery("#tree").find("i").filter(".icon-checked,.icon-checkhalf");
	for(var i = 0; i < selectRows.length; i++){
		var dom = selectRows[i];
		dom.css("border", "1px solid green");
		var lable = dom.next('label');
		var params = getParams(lable);	
		if(finalFuncIds.indexOf(params.id) == -1){
			funcIds.push(params.id);	
		}
	}
	
	var params = {};
    params.ids = funcIds.join(",");
    params.roleId = roleId;
    var callback = function(data){
        if("1" != data.code){
            fangjs.alert('保存失败！失败原因： ' + data.massege||'');
            return;
        }
        fangjs.alert('保存成功！');
        fangjs.closeDialog();
    };
    fangjs.execjava("sys/role/grantPrivilege",params,callback,false);
};
