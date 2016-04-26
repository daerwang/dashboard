<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>



<script type="text/javascript" charset="utf-8">

	$(document).ready(function() {

		if(!Cookies.get('restToken')){
			setToken();
		}

		if(!Cookies.get('resetReminder')){
			checkAccountExpiry();
		}

		function setToken(){
			$.ajax({
				type: 'GET',
				url: 'users/getApiToken',
				success: function(data){
					Cookies.set('restToken', data.accessToken);
					Cookies.set('restApi', data.restApi);
					Cookies.set('userName', data.userName);

				}
			});
		}
		
		function getContextPath() {
			return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
		}
		
		function checkAccountExpiry(){
			$.ajax({
				type: 'GET',
				url: 'users/daysBeforeAccountExpiration',
				success: function(data){
					console.log('its is ' + data);
					var remaining = 60 - data;
					if(remaining < 14){
						BootstrapDialog.show({
					        type : BootstrapDialog.TYPE_WARNING,
				            title: 'Warning',
				            message: 'Your account is about to expire in ' + remaining + ' days. Please reset your password.',
				            closable: false,
				            buttons: [{
				                label: 'Cancel',
				                action: function(dialogRef){
				                    dialogRef.close();
				                    Cookies.set('resetReminder', 'yes');
				                }
				            }, {
				                label: 'Reset Password',
				                cssClass: 'btn-warning',
				                action: function(dialogRef){
				                	$(location).attr('href', getContextPath() + '/users/resetPassword');
				                }
				            }]
				         });

					}
					
				}
			});
		}
	});

</script>


<!-- Custom CSS -->
<style>
body {
	padding-top: 70px;
	/* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
}
</style>

<div class="row">
	<div class="col-lg-12 text-center">
		<h1>Welcome to Ocean Bank Dashboard</h1>
		<p class="lead">A centralized web portal supported by IBM.</p>
		<ul class="list-unstyled">
			<sec:authorize access="hasAnyRole('Administrator','AML_Batch')">
				<li><a href="<c:url value="${kycBatchApprovalUrl}"/>" id="kycBatchApproval">AML Batch Approval and Disapproval</a></li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('Administrator')">
				<li><a href="<c:url value="${administrationUrl}"/>" id="manageDashboard">Manage Users and Roles</a></li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('Administrator','1042S_Form')">
				<li><a href="<c:url value="${irs1042sFormUrl}"/>" id="irs1042Form">IRS 1042-s Forms for 2014</a></li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('Administrator','Excel_Converter')">
				<li><a href="<c:url value="${excelConverterUrl}"/>" id="excelConverter">Advisor File Converter</a></li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('Administrator','1042S_Form')">
				<li><a href="<c:url value="${w8BeneFormUrl}"/>" id="w8BeneForm">W-8BEN-E Forms (Manual)</a></li>
				<li><a href="w8beneformDirect" id="w8BeneFormDirect">W-8BEN-E Forms (Direct)</a></li>
			</sec:authorize>
		</ul>
	</div>
</div>