<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/res/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/res/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
<link href="${ctx}/res/dist/css/timeline.css" rel="stylesheet">
<link href="${ctx}/res/dist/css/sb-admin-2.css" rel="stylesheet">
<link href="${ctx}/res/bower_components/morrisjs/morris.css" rel="stylesheet">
<link href="${ctx}/res/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script src="${ctx}/res/bower_components/jquery/dist/jquery.min.js"></script>
<script src="${ctx}/res/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="${ctx}/res/bower_components/metisMenu/dist/metisMenu.min.js"></script>
<script src="${ctx}/res/bower_components/raphael/raphael-min.js"></script>
<script src="${ctx}/res/bower_components/morrisjs/morris.min.js"></script>
<script src="${ctx}/res/dist/js/sb-admin-2.js"></script>
<script src="${ctx}/res/dist/js/jquery.md5.js"></script>
<script type="text/javascript" src="${ctx}/res/bower_components/datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/res/bower_components/datetimepicker/locals/bootstrap-datetimepicker.zh-CN.js"></script> --%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/static/css/font-awesome.min.css" rel="stylesheet" >
<link href="${ctx}/static/css/bootstrap.css" rel="stylesheet">
<link href="${ctx}/static/datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="${ctx}/static/css/lrtk.css" rel="stylesheet">    
<link href="${ctx}/static/css/sb-admin-2.css" rel="stylesheet">
<style type="text/css">
.fs-body-row{margin:auto}
.fs-table-row{overflow-x: auto;min-height:360px}
.fs-form-inline{width:100px;text-align: right;}
</style>
<script src="${ctx}/static/js/jquery.min.js"></script>
<script src="${ctx}/static/js/jquery.md5.js"></script>
<script src="${ctx}/static/js/bootstrap.js"></script>
<script src="${ctx}/static/js/metisMenu.min.js"></script>
<script src="${ctx}/static/js/sb-admin-2.js"></script>
<script src="${ctx}/static/datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}/static/datetimepicker/locals/bootstrap-datetimepicker.zh-CN.js"></script>
<script>
	var toastErrorMsg = '${errorMsg}';
	if (toastErrorMsg != '') {
		alert(toastErrorMsg);
	}
	$(function(){
		$(".fs-form .form-inline").addClass("fs-form-inline").css("margin", "0 0 15px 0");
	})
</script>