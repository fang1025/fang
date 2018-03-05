var row = null;
funcList.trClick = function() {
	var trs = this.getParentByTag('table').getElementsByTagName('tr');
	for ( var i = 0, len = trs.length; i < len; i++) trs[i].removeClass('click-tr');
	var tr = this.tagName.toLowerCase() == 'tr' ? this : this.getParentByTag('tr');
	tr.addClass('click-tr');
	row = tr.getAttribute('row');
};

funcList.editFunction = function() {
	if (!row) {
		alert('请选择需要修改的功能！');
		return;
	}
	fangjs.openDialog('sys/functionAdd.html?act=update&id=' + dataArray[row].id+'&parentName='
			+ escape(selectParams.functionName),'编辑功能信息',"800px","560px",funcList.loadAndRefresh());
	
};
funcList.trDblClick = function() {
	if (!row) {
		alert('请选择需要查看的功能！');
		return;
	}
	fangjs.openDialog('sys/functionAdd.html?view=true&act=update&id='
			+ dataArray[row].id+'&parentName='
			+ escape(selectParams.functionName),'功能信息',"800px","460px");
};


funcList.addFunction = function() {
	if (!selectParams) {
		alert('请选择上级节点！');
		return;
	}
	fangjs.openDialog('sys/functionAdd.html?lft=' + selectParams.rgt
			+ '&parentName=' + escape(selectParams.functionName),'编辑功能信息',"800px","460px",funcList.loadAndRefresh);
	
};

funcList.delFunction = function() {
	if (!row) {
		alert('请选择需要删除的功能！');
		return;
	}
	
	funcList.deleteSingleCallback = function() {
		fangjs.execjava("sys/function/deleteFunctionByParam", dataArray[row], function(data) {
			if (data&&data.code == '1') {
				alert('删除功能成功!');
				funcList.loadData();
				funcList.refreshData();
			} else {
				alert('删除功能失败!');
			}
		},false);
	};
	if (window.confirm('确定删除此功能及其所有子目录？ ')) {
		funcList.deleteSingleCallback();
	}

};

funcList.onload = function() {
//	funcList.funcControl();
	funcList.loadData();
};

funcList.loadData = function(){
	var callback = function(data) {
		if (!data)
			return;
		var rows = data.rows;
		var node = rows[0];
		var root = new branch(node.functionName, 'checkTree', 'id=' + node.id
				+ ',lft=' + node.lft + ',rgt=' + node.rgt, true);
		root.rgt = node.rgt;
		if (node.rgt - node.lft > 1)
			addBranch(root, rows, 1);
		$$('tree').innerHTML = root.html();
	};
	var addBranch = function(parentNode, nodes, index) {
		var node;
		for ( var i = index, len = nodes.length; i < len; i++) {
			node = nodes[i];
			var leaf = new branch(node.functionName, 'checkTree', 'id='
					+ node.id + ',lft=' + node.lft + ',rgt=' + node.rgt, false);
			leaf.rgt = node.rgt;
			if (node.rgt - node.lft > 1)
				i = addBranch(leaf, nodes, i + 1);
			parentNode.add(leaf);
			if (parentNode.rgt - node.rgt == 1)
				return i;
		}
	};
	fangjs.execjava('sys/function/findChildrenByParentId', {hasAllChild:1}, callback,false);
};

var dataArray;
var showData = function(data) {
	if (!data || !data.rows)
		return;
	var colArray = [ 'functionName', 'functionUrl', 'type',/*'enable',*/ 'lastUpdateTime',
			'notes' ];
	
	dataArray = data.rows;
	var dataCount = data.total;

	var startCallback = function(td, tr, row) {
		td.innerHTML = (page - 1) * pageSize + row + 1;
		tr.setAtt({
            click : 'trClick', 
            dblclick : 'trDblClick',
            row : row
        });
	};
	
	var endCallback = function(td, tr, row) {
		var functionIdv = dataArray[row].id;

		var jsonStrUp = "id="+functionIdv + ",move=-1,lft=" + dataArray[row].lft;
		var jsonStrDown = "id="+functionIdv + ",move=1,rgt=" + dataArray[row].rgt;
		var str="";
		str += ((page - 1) * pageSize + row + 1) == 1 ? "<a><i class='tf0 glyphicon'></i></a>" : "<a id='upMove' href='javascript:' click='moveFunction' params='" + jsonStrUp + "'><i class='tf0 glyphicon glyphicon-chevron-up'></i></a>";
		str += ((page - 1) * pageSize + row + 1) == dataCount ? "<a class='lmr5'  style='width:13px;'><i class='glyphicon'></i></a>" : "<a  class='lmr5' id='downMove' href='javascript:' click='moveFunction' params='" + jsonStrDown + "'><i class='tf0 glyphicon glyphicon-chevron-down'></a>";
		td.innerHTML = str;
	};
	
	var callbackArray = {
		enable : function(td) {
			switch (Number(this.enable)) {
			case 1:
				td.innerHTML = "正常";
				break;
			case 3:
				td.innerHTML = '禁用';
				break;
			}
		}, lastUpdateTime : function(td) {
			td.innerHTML = new Date(this.lastUpdateTime).format();
		},
		type : function(td){
    		switch(Number(this.type)){
    			case 1 :
    				td.innerHTML = '一级功能点';
    				break;
    			case 2 :
    				td.innerHTML = '二级功能点';
    				break;
    			case 3 :
    				td.innerHTML = '按钮级功能点';
    				break;
    			default : 
    				td.innerHTML = '四级功能点';
    		}
    	}
	};
	fangjs.dataView('datalist', colArray, dataArray, dataCount, startCallback,
			endCallback, callbackArray);
};

var selectParams = null;
var selectNode;
funcList.checkTree = function(params) {
	var cleanTree = function(obj) {
		var ele = $$(obj);
		var labels = ele.getElementsByTagName('label'), label;
		for ( var i = 0, len = labels.length; i < len; i++) {
			label = labels[i];
			if (label.className.indexOf('tree-lable') > -1)
				label.className = 'tree-lable';
			if(getParams(selectNode).id == getParams(label).id){
				selectNode = label;
			}
		}
	};
	if("refesh" != params.act){
		selectNode =this;
	}
	cleanTree('tree');
	selectNode.addClass('tree-selected');
	fangjs.cleanData('datalist', 0);
	params = getParams(selectNode);
	selectParams = params;
	selectParams.functionName = selectNode.innerHTML;
	params.hasAllChild=0;
	fangjs.execjava('sys/function/findChildrenByParentId', params, showData,false);
};

funcList.moveFunction = function(params){
	fangjs.execjava("sys/function/moveFunction", params, function(data) {
		if (data.result == 1) {
			//alert("移动功能成功!");
			funcList.loadData();
			funcList.refreshData();
		} 
	});
};
funcList.loadAndRefresh = function(){
    funcList.loadData();
    setTimeout(funcList.refreshData(),1000)
    // funcList.refreshData();
};
funcList.refreshData = function(){
	var params = {};
    params.act = "refesh";
    funcList.checkTree(params);
};


//根据浏览器窗口来决定div的高度
function resizeCallback(){
	var object = [{
		selector:"#heightauto",
		hasPageBar:true,
		top:(50)
	},{
		selector:"#tree",
		top:(50)
	}];
	return object;
}