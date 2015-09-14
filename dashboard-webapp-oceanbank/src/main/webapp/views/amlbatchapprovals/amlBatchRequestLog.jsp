<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style>
.user-success-dialog .modal-dialog {width: 300px;}
.modal .modal-dialog { width: 600px; }
</style>
<script type="text/javascript" charset="utf-8">
	
$(document).ready(function() {

	var requestId = "${requestId1}";
	var amlBatchRequestPage = "<c:url value="${amlBatchRequestPage}"/>";

	$('#informationLink').on('click', function(e) {
		
		e.preventDefault();	
		$(this).prop('href', amlBatchRequestPage + '?id=' + requestId);
		window.open(this.href, '_self');

	});   // History Button
	
	var amlRequestId = "${amlRequestId}";
	var dashboardLogDatatable = "<c:url value="${dashboardLogDatatable}"/>";
	
	var table = $('#dashboardLogDatatable').dataTable({
		"processing" : true,
		"serverSide" : true,
		"ajax" : {
            "url": dashboardLogDatatable,
            "data": function ( d ) {
                d.amlRequestId = amlRequestId;
            }
        },
        "filter": false
		 
	});
    
});

</script>



<div class="page-header text-center">
	<h3 id="referenceIdHeader">${amlBatchRequest.requestId}</h3>

</div>

<ul class="nav nav-tabs">
  <li role="presentation"><a href="#" id="informationLink">Information</a></li>
  <li role="presentation" class="active"><a href="#">History</a></li>
</ul>
<br/>

<div>

	<h4>Audit Log</h4>
	<p>The table shows the history of this AML Batch request.</p>
	<hr>

	
	<table id="dashboardLogDatatable" class="table table-striped table-bordered" cellspacing="0" width="100%">
		<thead>
			<tr>
				<th>User</th>
				<th>Description</th>
				<th>Date</th>
			</tr>
		</thead>
	</table>
</div>



