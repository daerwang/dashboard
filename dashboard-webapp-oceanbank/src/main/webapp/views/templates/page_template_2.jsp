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
	<script src="<c:url value="/jquery/cookies/js.cookie.js" />"></script>

	<!-- Bootstrap Version 3.3.2 -->
	<link href="<c:url value="/bootstrap/3.3.2/css/bootstrap.min.css" />" rel="stylesheet">
	<script src="<c:url value="/bootstrap/3.3.2/js/bootstrap.min.js" />" ></script>
	
	<!-- BootstrapValidator CSS and JS -->
	<link href="<c:url value="/bootstrap/validator/0.4.5/css/bootstrapValidator.min.css" />" rel="stylesheet">
	<script src="<c:url value="/bootstrap/validator/0.4.5/js/bootstrapValidator.min.js" />"></script>
	
	<!-- Fontawesome to be used with Bootstrap Validator -->
    <link href="<c:url value="/font-awesome/css/font-awesome.min.css" />" rel="stylesheet">
    
    <!-- Datatables Version 3 -->
	<link href="<c:url value="/datatables/integration/bootstrap/3/dataTables.bootstrap.css" />" rel="stylesheet">
	<script src="<c:url value="/datatables/media/js/jquery.dataTables.min.js" />"></script>
	<script src="<c:url value="/datatables/integration/bootstrap/3/dataTables.bootstrap.js" />"></script>
	<link href="<c:url value="/bootstrap/3.3.2/css/select.bootstrap.min.css" />" rel="stylesheet">
	<script src="<c:url value="/datatables/media/js/dataTables.select.min.js" />"></script>

	<!-- Dialog 3 -->
	<link href="<c:url value="/bootstrap/dialog3/css/bootstrap-dialog.min.css" />" rel="stylesheet">
	<script src="<c:url value="/bootstrap/dialog3/js/bootstrap-dialog.min.js" />"></script>
	
	<!-- Timeout -->
	<script src="<c:url value="/bootstrap/timeout/bootstrap-session-timeout.min.js" />"></script>
	
	<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		
		var url = getContextPath() + '/login/keep-alive';
		
		$.sessionTimeout({
            keepAliveUrl: url,
            logoutUrl: getContextPath() + '/login?loggedout=loggedout',
            redirUrl: getContextPath() + '/login?loggedout=redirect',
            warnAfter: 450000,
            redirAfter: 600000,
            countdownMessage: 'Redirecting in {timer} seconds.'
        });
		
		
		function getContextPath() {
			return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
		}
	});
	</script>
</head>
<body>

	<!-- Top menu bar -->
	<tiles:insertAttribute name="topPageContainer" />

	<!-- Page Content -->
	<div class="container">
		
		<!-- Page Body -->
		<tiles:insertAttribute name="pageContainer" />
		
	</div>

</body>
</html>