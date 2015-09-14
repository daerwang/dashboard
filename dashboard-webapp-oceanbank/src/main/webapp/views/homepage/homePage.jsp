<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>



<script type="text/javascript" charset="utf-8">

	$(document).ready(function() {


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
		</ul>
	</div>
</div>