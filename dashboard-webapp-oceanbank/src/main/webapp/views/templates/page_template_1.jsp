<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><tiles:insertAttribute name="title" /></title>
	
	<!-- Bootstrap Core CSS -->
	<link href="<c:url value="/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
	<link href="<c:url value="/bootstrap/css/test-style.css" />" rel="stylesheet">
	<!-- jQuery Version 1.11.0 -->
	<script src="<c:url value="/bootstrap/js/jquery-1.11.0.js" />"></script>
	<script src="<c:url value="/bootstrap/js/bootstrap.min.js" />"></script>
	<!-- BootstrapValidator CSS and JS -->
	<link href="<c:url value="/bootstrap/validator/css/bootstrapValidator.min.css" />" rel="stylesheet">
	<script src="<c:url value="/bootstrap/validator/js/bootstrapValidator.min.js" />"></script>
	<!-- Fontawesome to be used with Bootstrap Validator -->
    <link href="<c:url value="/font-awesome/css/font-awesome.min.css" />" rel="stylesheet">

		
	<!-- Custom CSS 
    <style>
    body {
        padding-top: 70px;
        /* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
    }
    </style>
    -->
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

	<!-- Plain Body -->
	<tiles:insertAttribute name="clearBody" />

</body>
</html>