<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- Custom CSS -->
<style>
body {
	padding-top: 70px;
	/* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
}
</style>

<script>

$(document).ready(function(){

	function getContextPath() {
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}

	$('#resetPasswordLink').click(function(e){
		e.preventDefault();
		$(location).attr('href', getContextPath() + '/users/resetPassword');
	});


});


</script>

<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<c:url value="/home"/>">Ocean Bank</a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			
			
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="isAuthenticated()">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">
							Welcome <sec:authentication property="principal.username" /> <span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
			            <li><a href="/users/resetPassword" id="resetPasswordLink">Reset Password</a></li>
			        </ul>
				</li>
				</sec:authorize>
				<sec:authorize access="! isAuthenticated()">
				<li>
					<a href="#" >Welcome Visitor</a>
				</li>
				</sec:authorize>

				<sec:authorize access="! isAuthenticated()">
					<li><a href="<c:url value="/login"/>">Login</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li><a href="<c:url value="/j_spring_security_logout" />">Logout</a></li>
				</sec:authorize>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container -->
</nav>