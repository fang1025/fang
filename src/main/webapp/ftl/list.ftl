<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		<title>${moduleName?if_exists }</title>
		<!-- css -->
		<link href="../assets/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
  		<!-- Font Awesome -->
  		<link rel="stylesheet" href="../assets/css/font-awesome.min.css">
  		<!-- Ionicons -->
  		<link rel="stylesheet" href="../assets/css/ionicons.min.css">		
  		<!-- Theme style -->
		<link rel="stylesheet" href="../assets/css/AdminLTE.css">
		<link rel="stylesheet" href="../assets/css/style.css">
   
	</head>

	<body>
		<div class="box box-primary margin-bottom-none">
			<div class="box-header with-border">
				<h3 class="box-title">${moduleName?if_exists }</h3>

				<div class="box-tools pull-right">

					<div class="has-feedback pull-right">
						<input type="text" class="form-control input-sm" placeholder="输入关键字" id="keywords">
						<span class="glyphicon glyphicon-search form-control-feedback"></span>
					</div>
					
					<div class="pull-right btn-group btn-func">
					</div>
				</div>
			</div>
			
			<div class="box-body no-padding" >
				<div class = "table-responsive">
					<table  class="table table-striped table-condensed margin-bottom-none">
						<thead id="trhead">
							<tr>
								<th>序号</th>
                        		<#list fieldInfos as info>
                        		<#if info.fieldTitle??>
                        		<th>${info.fieldTitle}</th>
                        		</#if>
                        		</#list>
                        	<th>创建时间</th>
							</tr>
						</thead>
						<tbody id="${entityName?if_exists }List">

						</tbody>
					</table>
				</div>
			</div>
			
			<div class="box-footer no-padding">
                  <!--#include virtual="/common/page.html"-->
            </div>
		</div>

		<script src="../assets/js/jquery-1.9.1.min.js"></script>
		<script src="../assets/lib/bootstrap/js/bootstrap.js"></script>
		<script src="../assets/lib/layer/layer.js"></script>
		<script src="../assets/js/jquery.scrollTo.min.js"></script>
		<script src="../assets/js/jquery.nicescroll.js"></script>
		<script src="../assets/js/common.js"></script>
		<script src="js/${entityName?if_exists }List.js"></script>
	</body>
</html>
