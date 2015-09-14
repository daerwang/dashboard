<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- Custom CSS -->
<style>
body {
	padding-top: 70px;
	/* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
}
</style>


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
				<li><a href="#">Welcome 
						<sec:authorize access="isAuthenticated()">
							<sec:authentication property="principal.user.firstname" />
						</sec:authorize>
						<sec:authorize access="! isAuthenticated()">
							Visitor
						</sec:authorize>
					</a>
				</li>
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