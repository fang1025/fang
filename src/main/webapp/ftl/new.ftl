<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		<title>${moduleName?if_exists }</title>
		<!-- css -->
		<link href="../assets/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
  		<!-- Theme style -->
		<link rel="stylesheet" href="../assets/css/AdminLTE.css">
		<link rel="stylesheet" href="../assets/css/style.css">
	</head>

	<body>
	
		<div class="box margin-bottom-none">
			<input type="hidden" id="${idName}" class="m-text" >
			
			<div class="box-body">
			<#list fieldInfos as info>
				<#if info.formType??>
				<div class="form-group col-sm-6">
					<label>${info.fieldTitle}</label>
					<#if info.formType == 'select'> 
					<select type="text" class="form-control" id="${info.fieldName}" <#if info.fieldLength??>maxlen="${info.fieldLength}"</#if><#if info.fieldNull??>verifyType="noNull"</#if> >
						<option value="">请选择</option>
						<#if info.options??> 
						<#list info.options as option><option value="${option.val}">${option.name}</option></#list>
						</#if>
					</select>
					
					<#elseif info.formType == 'input'> 
					<input type="text" class="form-control" id="${info.fieldName}" <#if info.fieldLength??>maxlen="${info.fieldLength}"</#if><#if info.fieldNull??>verifyType="noNull"</#if> /> 
					<#elseif info.formType == 'textarea'>
					<textarea class="form-control" id="${info.fieldName}" <#if info.fieldLength??>maxlen="${info.fieldLength}"</#if><#if info.fieldNull??>verifyType="noNull"</#if> ></textarea>
					<#elseif info.formType == 'date'> 
					<div class="has-feedback">
						<input type="text" class="form-control" id="${info.fieldName}" click="showDate" <#if info.fieldNull??>verifyType="noNull"</#if> />
						<span class="glyphicon glyphicon-calendar form-control-feedback"></span>
					</div> 
					</#if>
				</div>
				</#if>
			</#list>
			</div>

			<div class="box-footer fixed-bottom">
				<div class=" col-sm-12">
					<a class="btn btn-default pull-right" click="save${upperEntityName?if_exists }"> 保存 </a>
				</div>
			</div>
		</div>
	
		<script src="../assets/js/jquery-1.9.1.min.js"></script>
		<script src="../assets/lib/bootstrap/js/bootstrap.js"></script>
		<script src="../assets/lib/layer/layer.js"></script>
		<script src="../assets/js/common.js"></script>
		<script src="js/${entityName?if_exists }Add.js"></script>

</body>
</html>