<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script type="text/javascript" charset="utf-8">

	$(document).ready(function() {

		
		
	});

</script>
<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-6 col-md-4 col-md-offset-4">
				<h1 class="text-center login-title">
					Ocean Bank Dashboard
				</h1>
				
				<div class="account-wall">
					<img class="profile-img" src="<c:url value="/photos/login_photo.png" />" alt="">
					<form class="form-signin" name="f" action="j_spring_security_check" method="POST">
						<input type="text" class="form-control" placeholder="${username1}" name="j_username" required autofocus> 
						<input type="password" class="form-control" placeholder="${password1}" name="j_password" required>
						<button class="btn btn-lg btn-primary btn-block" type="submit"> Sign in </button>
						
						<label class="checkbox pull-left"><input type="checkbox"> 
							<spring:message code="login.label.rememberMe"/>
						</label> 
						
						<a href="resetPassword" class="pull-right new-account">${changePassword1}?</a><span class="clearfix"></span>
					</form>
				</div>
				
				<span class="text-center new-account"><a href="?lang=en"><spring:message code="label.form.loginEnglish"></spring:message></a>
							| <a href="?lang=es_ES"><spring:message code="label.form.loginSpanish"></spring:message></a></span>
			</div>
		</div>
	</div>

	<c:if test="${param.error eq 'badCredentials'}">
		<div class="container">
			<div class="span12">
				<div class="alert alert-error text-center" style="color:RED">
					<spring:message code="message.badCredentials"></spring:message>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${param.error eq 'badConnection'}">
		<div class="container">
			<div class="span12">
				<div class="alert alert-error text-center" style="color:RED">
					<spring:message code="message.badConnection"></spring:message>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${param.loggedout != null}">
		<div class="container">
			<div class="span12">
				<div class="alert alert-error text-center" style="color:GREEN;">
					You have been logged out.
				</div>
			</div>
		</div>
	</c:if>