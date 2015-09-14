<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><tiles:insertAttribute name="title" /></title>

	<!-- jQuery UI Version 1.11.2 -->
	<link href="<c:url value="/jquery/ui/1.11.2/jquery-ui.css" />" rel="stylesheet">
	<script src="<c:url value="/jquery/ui/1.11.2/external/jquery/jquery.js" />"></script>
	<script src="<c:url value="/jquery/ui/1.11.2/jquery-ui.js" />"></script>
	
	<!-- Bootstrap Version 3.3.2 -->
	<link href="<c:url value="/bootstrap/3.3.2/css/bootstrap.min.css" />" rel="stylesheet">
	<script src="<c:url value="/bootstrap/3.3.2/js/bootstrap.min.js" />" ></script>
	
	<!-- BootstrapValidator CSS and JS -->
	<link href="<c:url value="/bootstrap/validator/0.4.5/css/bootstrapValidator.min.css" />" rel="stylesheet">
	<script src="<c:url value="/bootstrap/validator/0.4.5/js/bootstrapValidator.min.js" />"></script>
	
	<!-- Fontawesome to be used with Bootstrap Validator -->
    <link href="<c:url value="/font-awesome/css/font-awesome.min.css" />" rel="stylesheet">
	
</head>
<body>

	<!-- Top menu bar -->
	<tiles:insertAttribute name="topMenuBar" />

	<!-- Page Content -->
	<div class="container">
		
		<!-- Page header -->
		<tiles:insertAttribute name="pageHeader" />
		
		<!-- Page Datatable -->
		<tiles:insertAttribute name="pageDataTable" />
		
	</div>
	
	
	

</body>
</html>